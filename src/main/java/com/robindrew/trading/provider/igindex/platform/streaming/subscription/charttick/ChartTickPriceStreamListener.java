package com.robindrew.trading.provider.igindex.platform.streaming.subscription.charttick;

import static com.robindrew.common.collect.PopulatingMap.createConcurrentMap;
import static com.robindrew.common.text.Strings.number;
import static com.robindrew.trading.provider.igindex.platform.streaming.subscription.charttick.ChartTickPriceStream.FIELD_BID;
import static com.robindrew.trading.provider.igindex.platform.streaming.subscription.charttick.ChartTickPriceStream.FIELD_OFR;
import static com.robindrew.trading.provider.igindex.platform.streaming.subscription.charttick.ChartTickPriceStream.FIELD_UTM;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lightstreamer.ls_client.HandyTableListener;
import com.lightstreamer.ls_client.UpdateInfo;
import com.robindrew.common.collect.PopulatingMap;
import com.robindrew.common.concurrent.IEventConsumer;
import com.robindrew.common.concurrent.LoopingEventConsumerThread;
import com.robindrew.common.date.Delay;
import com.robindrew.common.util.Check;
import com.robindrew.trading.platform.streaming.latest.ILatestPrice;
import com.robindrew.trading.platform.streaming.latest.LatestPrice;
import com.robindrew.trading.price.candle.IPriceCandle;
import com.robindrew.trading.price.candle.io.stream.sink.subscriber.PriceCandleSubscriberStreamSink;

public class ChartTickPriceStreamListener extends PriceCandleSubscriberStreamSink implements HandyTableListener, AutoCloseable {

	private static final Logger log = LoggerFactory.getLogger(ChartTickPriceStreamListener.class);

	private static final PopulatingMap<String, AtomicLong> countMap = createConcurrentMap(key -> new AtomicLong(0));

	private final ChartTickPriceStreamConfig config;

	private final LatestPrice latest = new LatestPrice();
	private final AtomicBoolean active = new AtomicBoolean(true);
	private final Delay loggingDelay = new Delay(1, TimeUnit.MINUTES);

	private final LoopingEventConsumerThread<OnUpdate> eventConsumer;

	public ChartTickPriceStreamListener(ChartTickPriceStreamConfig config) {
		super(config.getInstrument());
		this.config = Check.notNull("config", config);

		String threadName = "ChartTickConsumer[" + config.getInstrument().getName() + "]";
		this.eventConsumer = new LoopingEventConsumerThread<OnUpdate>(threadName, new OnUpdateConsumer());
	}

	public void start() {
		eventConsumer.start();
	}

	public ILatestPrice getLatestPrice() {
		return latest;
	}

	@Override
	public void onUpdate(int itemPos, String itemName, UpdateInfo updateInfo) {
		eventConsumer.publishEvent(new OnUpdate(itemPos, itemName, updateInfo));
	}

	private long increment(String key) {
		AtomicLong count = countMap.get(key, true);
		return count.incrementAndGet();
	}

	private boolean isInvalid(String text) {
		return text == null || text.isEmpty() || text.equals("null") || text.equals("NULL");
	}

	@Override
	public void onSnapshotEnd(int itemPos, String itemName) {
		log.info("onSnapshotEnd({} , {})", itemPos, itemName);
	}

	@Override
	public void onRawUpdatesLost(int itemPos, String itemName, int lostUpdates) {
		log.info("onRawUpdatesLost({}, {}, lostUpdates={})", itemPos, itemName, lostUpdates);
	}

	@Override
	public void onUnsubscr(int itemPos, String itemName) {
		log.info("onUnsubscr(" + itemPos + ", " + itemName + ")");
		active.set(false);
	}

	@Override
	public void onUnsubscrAll() {
		log.info("onUnsubscrAll()");
		active.set(false);
	}

	public boolean isActive() {
		return active.get();
	}

	@Override
	public void close() {
		super.close();
		eventConsumer.close();
	}

	private class OnUpdate {

		private final int itemPos;
		private final String itemName;
		private final UpdateInfo updateInfo;

		public OnUpdate(int itemPos, String itemName, UpdateInfo updateInfo) {
			this.itemPos = itemPos;
			this.itemName = itemName;
			this.updateInfo = updateInfo;
		}

		public void execute() {
			long count = increment(itemName);

			// Extract the information we are interested in
			String timestamp = updateInfo.getNewValue(FIELD_UTM);
			String bid = updateInfo.getNewValue(FIELD_BID);
			String offer = updateInfo.getNewValue(FIELD_OFR);

			boolean bidInvalid = isInvalid(bid);
			boolean offerInvalid = isInvalid(offer);

			// Invalid update?
			if (isInvalid(timestamp) || (bidInvalid && offerInvalid)) {
				log.debug("[Invalid Tick] - onUpdate(" + itemPos + ", " + itemName + ", " + updateInfo + ")");
				return;
			}

			if (bidInvalid) {
				bid = updateInfo.getOldValue(FIELD_BID);
				if (isInvalid(bid)) {
					log.debug("[Invalid Tick] - onUpdate(" + itemPos + ", " + itemName + ", " + updateInfo + ")");
					return;
				}
			}
			if (offerInvalid) {
				offer = updateInfo.getOldValue(FIELD_OFR);
				if (isInvalid(offer)) {
					log.debug("[Invalid Tick] - onUpdate(" + itemPos + ", " + itemName + ", " + updateInfo + ")");
					return;
				}
			}

			// Enqueue tick ...
			final ChartTick tick;
			try {
				tick = new ChartTick(config.getInstrument(), config.getPrecision(), itemName, timestamp, bid, offer);
			} catch (Exception e) {
				log.warn("[Invalid Tick] - onUpdate(" + itemPos + ", " + itemName + ", " + updateInfo + ")", e);
				return;
			}

			// Consume tick
			try {

				IPriceCandle next = tick.toPriceCandle();
				latest.update(next, tick.getTimestamp());
				putNextCandle(next);

			} catch (Exception e) {
				log.error("Error consuming tick", e);
			}

			// As these are ticks (spam!) only log after a period of time
			if (loggingDelay.expired(true)) {
				log.info("onUpdate(" + itemName + ") called " + number(count) + " times");
			}
		}
	}

	private class OnUpdateConsumer implements IEventConsumer<OnUpdate> {

		@Override
		public void consumeEvents(List<OnUpdate> events) {
			for (OnUpdate event : events) {
				event.execute();
			}
		}
	}

}

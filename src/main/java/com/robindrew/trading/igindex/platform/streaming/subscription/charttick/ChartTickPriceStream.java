package com.robindrew.trading.igindex.platform.streaming.subscription.charttick;

import static com.robindrew.common.collect.PopulatingMap.createConcurrentMap;
import static com.robindrew.common.text.Strings.number;
import static com.robindrew.trading.igindex.platform.streaming.subscription.charttick.ChartTickFields.FIELD_BID;
import static com.robindrew.trading.igindex.platform.streaming.subscription.charttick.ChartTickFields.FIELD_LTV;
import static com.robindrew.trading.igindex.platform.streaming.subscription.charttick.ChartTickFields.FIELD_OFR;
import static com.robindrew.trading.igindex.platform.streaming.subscription.charttick.ChartTickFields.FIELD_TTV;
import static com.robindrew.trading.igindex.platform.streaming.subscription.charttick.ChartTickFields.FIELD_UTM;
import static com.robindrew.trading.igindex.platform.streaming.subscription.charttick.ChartTickFields.getSubscriptionKey;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lightstreamer.ls_client.ExtendedTableInfo;
import com.lightstreamer.ls_client.HandyTableListener;
import com.lightstreamer.ls_client.SubscribedTableKey;
import com.lightstreamer.ls_client.UpdateInfo;
import com.robindrew.common.collect.PopulatingMap;
import com.robindrew.common.concurrent.IEventConsumer;
import com.robindrew.common.concurrent.LoopingEventConsumerThread;
import com.robindrew.common.date.Delay;
import com.robindrew.common.util.Check;
import com.robindrew.trading.igindex.IIgIndexInstrument;
import com.robindrew.trading.igindex.platform.streaming.lightstreamer.ExtendedTableInfoBuilder;
import com.robindrew.trading.igindex.platform.streaming.lightstreamer.LoggingTableListener;
import com.robindrew.trading.igindex.platform.streaming.subscription.IIgIndexInstrumentPriceStream;
import com.robindrew.trading.platform.streaming.InstrumentPriceStream;
import com.robindrew.trading.price.candle.IPriceCandle;
import com.robindrew.trading.price.precision.IPricePrecision;

public class ChartTickPriceStream extends InstrumentPriceStream<IIgIndexInstrument> implements IIgIndexInstrumentPriceStream {

	private static final Logger log = LoggerFactory.getLogger(ChartTickPriceStream.class);

	private static boolean isInvalid(String text) {
		return text == null || text.isEmpty() || text.equals("null") || text.equals("NULL");
	}

	private static String getValue(UpdateInfo update, String fieldName, AtomicReference<String> cached) {
		String value = update.getNewValue(fieldName);
		if (!isInvalid(value)) {
			cached.set(value);
			return value;
		}
		value = update.getOldValue(fieldName);
		if (!isInvalid(value)) {
			cached.set(value);
			return value;
		}
		return cached.get();
	}

	/**
	 * Maintain a count of all updates.
	 */
	private static final PopulatingMap<String, AtomicLong> countMap = createConcurrentMap(key -> new AtomicLong(0));

	private final AtomicBoolean active = new AtomicBoolean(true);
	private final IPricePrecision precision;

	private final LoopingEventConsumerThread<OnUpdate> eventConsumer;
	private final Delay loggingDelay = new Delay(1, TimeUnit.MINUTES);

	private final HandyTableListener tableListener;
	private final ExtendedTableInfo tableInfo;
	private volatile SubscribedTableKey key = null;

	private final AtomicReference<String> cachedBid = new AtomicReference<>();
	private final AtomicReference<String> cachedOffer = new AtomicReference<>();

	public ChartTickPriceStream(IIgIndexInstrument instrument) {
		super(instrument);
		this.precision = instrument.getPrecision();

		// Create the event consumer
		String threadName = "ChartTickConsumer[" + getInstrument().getName() + "]";
		this.eventConsumer = new LoopingEventConsumerThread<OnUpdate>(threadName, new OnUpdateConsumer());

		// Lightstreamer: Create the table listener
		this.tableListener = new TableListener();

		// Lightstreamer: Create the table info
		ExtendedTableInfoBuilder builder = new ExtendedTableInfoBuilder();
		builder.setDistinctMode();
		builder.addItem(getSubscriptionKey(getInstrument()));
		builder.addFields(FIELD_UTM, FIELD_BID, FIELD_OFR, FIELD_TTV, FIELD_LTV);
		builder.setSnapshot(true);
		this.tableInfo = builder.build();
	}

	public IPricePrecision getPrecision() {
		return precision;
	}

	@Override
	public ExtendedTableInfo getExtendedTableInfo() {
		return tableInfo;
	}

	@Override
	public HandyTableListener getHandyTableListener() {
		return tableListener;
	}

	@Override
	public void close() {
		super.close();
		eventConsumer.close();
	}

	/**
	 * This stream will not consume price events until it is started.
	 */
	public void start() {
		eventConsumer.start();
	}

	@Override
	public SubscribedTableKey getSubscribedTableKey() {
		if (key == null) {
			throw new IllegalStateException("key not set");
		}
		return key;
	}

	@Override
	public void setKey(SubscribedTableKey key) {
		this.key = Check.notNull("key", key);
	}

	public boolean isActive() {
		return active.get();
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

		private long increment(String key) {
			AtomicLong count = countMap.get(key, true);
			return count.incrementAndGet();
		}

		public void execute() {
			long count = increment(itemName);

			// Extract the information we are interested in
			String timestamp = updateInfo.getNewValue(FIELD_UTM);

			// Bid
			String bid = getValue(updateInfo, FIELD_BID, cachedBid);
			String offer = getValue(updateInfo, FIELD_OFR, cachedOffer);

			/** Last Traded Volume. */
			// String volume = updateInfo.getNewValue(FIELD_LTV);
			/** Incremental Trading Volume. */
			// String incremental = updateInfo.getNewValue(FIELD_TTV);

			// Invalid update?
			if (isInvalid(timestamp) || isInvalid(bid) || isInvalid(offer)) {
				log.debug("[Invalid Tick] - onUpdate({}, {}, {})", itemName, itemPos, updateInfo);
				return;
			}

			// Enqueue tick ...
			final ChartTick tick;
			try {
				log.debug("[Tick] - onUpdate({}, {}, {})", itemName, itemPos, updateInfo);
				tick = new ChartTick(getInstrument(), getPrecision(), itemName, timestamp, bid, offer);
			} catch (Exception e) {
				log.warn("[Invalid Tick] - onUpdate(" + itemPos + ", " + itemName + ", " + updateInfo + ")", e);
				return;
			}

			// Consume tick
			try {

				IPriceCandle next = tick.toPriceTick();
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

	private class TableListener extends LoggingTableListener {

		@Override
		public void onUpdate(int itemPos, String itemName, UpdateInfo updateInfo) {
			eventConsumer.publishEvent(new OnUpdate(itemPos, itemName, updateInfo));
		}

		@Override
		public void onUnsubscr(int itemPos, String itemName) {
			super.onUnsubscr(itemPos, itemName);
			active.set(false);
		}

		@Override
		public void onUnsubscrAll() {
			super.onUnsubscrAll();
			active.set(false);
		}
	}

}

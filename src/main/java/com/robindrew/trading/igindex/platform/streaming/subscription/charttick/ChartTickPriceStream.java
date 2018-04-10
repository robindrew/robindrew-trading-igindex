package com.robindrew.trading.igindex.platform.streaming.subscription.charttick;

import static com.robindrew.common.collect.PopulatingMap.createConcurrentMap;
import static com.robindrew.common.text.Strings.number;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lightstreamer.ls_client.ExtendedTableInfo;
import com.lightstreamer.ls_client.HandyTableListener;
import com.lightstreamer.ls_client.SubscrException;
import com.lightstreamer.ls_client.SubscribedTableKey;
import com.lightstreamer.ls_client.UpdateInfo;
import com.robindrew.common.collect.PopulatingMap;
import com.robindrew.common.concurrent.IEventConsumer;
import com.robindrew.common.concurrent.LoopingEventConsumerThread;
import com.robindrew.common.date.Delay;
import com.robindrew.common.util.Check;
import com.robindrew.trading.IInstrument;
import com.robindrew.trading.igindex.platform.IgException;
import com.robindrew.trading.igindex.platform.streaming.subscription.IIgInstrumentPriceStream;
import com.robindrew.trading.platform.streaming.InstrumentPriceStream;
import com.robindrew.trading.price.precision.IPricePrecision;
import com.robindrew.trading.price.tick.IPriceTick;

public class ChartTickPriceStream extends InstrumentPriceStream implements IIgInstrumentPriceStream {

	private static final Logger log = LoggerFactory.getLogger(ChartTickPriceStream.class);

	public static final String FIELD_BID = "BID";
	public static final String FIELD_OFR = "OFR";
	public static final String FIELD_LTP = "LTP";
	public static final String FIELD_LTV = "LTV";
	public static final String FIELD_UTM = "UTM";
	public static final String FIELD_DAY_OPEN_MID = "DAY_OPEN_MID";
	public static final String FIELD_DAY_PERC_CHG_MID = "DAY_PERC_CHG_MID";
	public static final String FIELD_DAY_HIGH = "DAY_HIGH";
	public static final String FIELD_DAY_LOW = "DAY_LOW";

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

	public ChartTickPriceStream(IInstrument instrument, IPricePrecision precision) {
		super(instrument);
		this.precision = Check.notNull("precision", precision);

		// Create the event consumer
		String threadName = "ChartTickConsumer[" + getInstrument().getName() + "]";
		this.eventConsumer = new LoopingEventConsumerThread<OnUpdate>(threadName, new OnUpdateConsumer());

		// Lightstreamer: Create the table listener
		this.tableListener = new TableListener();

		// Lightstreamer: Create the table info
		try {
			String[] items = new String[] { getSubscriptionKey(getInstrument()) };
			String mode = "DISTINCT";
			String[] fields = new String[] { FIELD_UTM, FIELD_BID, FIELD_OFR };
			boolean snapshot = true;
			this.tableInfo = new ExtendedTableInfo(items, mode, fields, snapshot);
		} catch (SubscrException e) {
			throw new IgException(e);
		}
	}

	public IPricePrecision getPrecision() {
		return precision;
	}

	public String getSubscriptionKey(IInstrument instrument) {
		return "CHART:" + instrument.getName() + ":TICK";
	}

	public ExtendedTableInfo getExtendedTableInfo() {
		return tableInfo;
	}

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

	public SubscribedTableKey getSubscribedTableKey() {
		if (key == null) {
			throw new IllegalStateException("key not set");
		}
		return key;
	}

	public void setKey(SubscribedTableKey key) {
		this.key = Check.notNull("key", key);
	}

	public boolean isActive() {
		return active.get();
	}

	private class TableListener implements HandyTableListener {

		@Override
		public void onUpdate(int itemPos, String itemName, UpdateInfo updateInfo) {
			eventConsumer.publishEvent(new OnUpdate(itemPos, itemName, updateInfo));
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
	}

	private static AtomicReference<String> cachedBid = new AtomicReference<>();
	private static AtomicReference<String> cachedOffer = new AtomicReference<>();

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

			// Invalid update?
			if (isInvalid(timestamp) || isInvalid(bid) || isInvalid(offer)) {
				log.info("[Invalid Tick] - onUpdate({}, {}, {})", itemName, itemPos, updateInfo);
				return;
			}

			// Enqueue tick ...
			final ChartTick tick;
			try {
				log.info("[Tick] - onUpdate({}, {}, {})", itemName, itemPos, updateInfo);
				tick = new ChartTick(getInstrument(), getPrecision(), itemName, timestamp, bid, offer);
			} catch (Exception e) {
				log.warn("[Invalid Tick] - onUpdate(" + itemPos + ", " + itemName + ", " + updateInfo + ")", e);
				return;
			}

			// Consume tick
			try {

				IPriceTick next = tick.toPriceTick();
				putNextTick(next);

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

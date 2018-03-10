package com.robindrew.trading.provider.igindex.platform.streaming.subscription.charttick;

import static com.google.common.collect.Lists.newArrayList;

import java.util.List;

import com.lightstreamer.ls_client.ExtendedTableInfo;
import com.lightstreamer.ls_client.HandyTableListener;
import com.lightstreamer.ls_client.SubscrException;
import com.lightstreamer.ls_client.SubscribedTableKey;
import com.robindrew.common.util.Check;
import com.robindrew.trading.IInstrument;
import com.robindrew.trading.platform.streaming.InstrumentPriceStream;
import com.robindrew.trading.provider.igindex.platform.IgException;
import com.robindrew.trading.provider.igindex.platform.streaming.subscription.FieldSet;
import com.robindrew.trading.provider.igindex.platform.streaming.subscription.IIgInstrumentPriceStream;

public class ChartTickPriceStream extends InstrumentPriceStream implements IIgInstrumentPriceStream {

	public static final String FIELD_BID = "BID";
	public static final String FIELD_OFR = "OFR";
	public static final String FIELD_LTP = "LTP";
	public static final String FIELD_LTV = "LTV";
	public static final String FIELD_UTM = "UTM";
	public static final String FIELD_DAY_OPEN_MID = "DAY_OPEN_MID";
	public static final String FIELD_DAY_PERC_CHG_MID = "DAY_PERC_CHG_MID";
	public static final String FIELD_DAY_HIGH = "DAY_HIGH";
	public static final String FIELD_DAY_LOW = "DAY_LOW";

	private final ChartTickPriceStreamConfig config;
	private final FieldSet fields;
	private final ChartTickPriceStreamListener listener;
	private volatile SubscribedTableKey key = null;

	public ChartTickPriceStream(ChartTickPriceStreamConfig config, ChartTickPriceStreamListener listener, List<String> fields) {
		super(config.getInstrument(), listener.getLatestPrice());
		if (fields.isEmpty()) {
			throw new IllegalArgumentException("fields is empty");
		}
		this.config = Check.notNull("config", config);
		this.listener = Check.notNull("listener", listener);
		this.fields = new FieldSet(fields);
	}

	public ChartTickPriceStream(ChartTickPriceStreamConfig config, ChartTickPriceStreamListener listener) {
		this(config, listener, newArrayList(FIELD_UTM, FIELD_BID, FIELD_OFR));
	}

	public String getSubscriptionKey(IInstrument instrument) {
		return "CHART:" + instrument.getName() + ":TICK";
	}

	public String getType() {
		return "DISTINCT";
	}

	@Override
	public ExtendedTableInfo getExtendedTableInfo() {
		try {
			String subscriptionKey = getSubscriptionKey(config.getInstrument());
			return new ExtendedTableInfo(new String[] { subscriptionKey }, "DISTINCT", fields.toArray(), true);
		} catch (SubscrException e) {
			throw new IgException(e);
		}
	}

	@Override
	public HandyTableListener getHandyTableListener() {
		return listener;
	}

	@Override
	public void close() {
		listener.close();
	}

	public ChartTickPriceStreamConfig getConfig() {
		return config;
	}

	@Override
	public ChartTickPriceStreamListener getListener() {
		return listener;
	}

	public void start() {
		listener.start();
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

}

package com.robindrew.trading.igindex.platform.streaming.subscription.charttick;

import com.robindrew.trading.IInstrument;

public class ChartTickFields {

	/** Bid Price. */
	public static final String FIELD_BID = "BID";
	/** Offer Price. */
	public static final String FIELD_OFR = "OFR";
	/** Last Traded Price. */
	public static final String FIELD_LTP = "LTP";
	/** Last Traded Volume. */
	public static final String FIELD_LTV = "LTV";
	/** Incremental Trading Volume. */
	public static final String FIELD_TTV = "TTV";
	/** Update Time Millis. */
	public static final String FIELD_UTM = "UTM";
	/** Open Mid Price (for the day). */
	public static final String FIELD_DAY_OPEN_MID = "DAY_OPEN_MID";
	/** Percentage Change Price (for the day). */
	public static final String FIELD_DAY_PERC_CHG_MID = "DAY_PERC_CHG_MID";
	/** High Price (for the day). */
	public static final String FIELD_DAY_HIGH = "DAY_HIGH";
	/** Low Price (for the day). */
	public static final String FIELD_DAY_LOW = "DAY_LOW";

	public static String getSubscriptionKey(IInstrument instrument) {
		return "CHART:" + instrument.getName() + ":TICK";
	}

}

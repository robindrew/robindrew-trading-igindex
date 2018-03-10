package com.robindrew.trading.provider.igindex.platform.rest.executor.getmarketnavigation;

import java.math.BigDecimal;

import com.robindrew.common.json.IJson;
import com.robindrew.trading.provider.igindex.platform.rest.executor.IgJsonObject;

public class Market extends IgJsonObject {

	private final int delayTime;
	private final String epic;
	private final BigDecimal netChange;
	private final Integer lotSize;
	private final String expiry;
	private final String instrumentType;
	private final String instrumentName;
	private final BigDecimal high;
	private final BigDecimal low;
	private final BigDecimal percentageChange;
	private final String updateTime;
	private final String updateTimeUTC;
	private final BigDecimal bid;
	private final BigDecimal offer;
	private final Boolean otcTradeable;
	private final boolean streamingPricesAvailable;
	private final String marketStatus;
	private final int scalingFactor;

	public Market(IJson object) {
		delayTime = object.getInt("delayTime");
		epic = object.get("epic");
		netChange = object.getBigDecimal("netChange", true);
		lotSize = object.getInt("lotSize", true);
		expiry = object.get("expiry");
		instrumentType = object.get("instrumentType");
		instrumentName = object.get("instrumentName");
		high = object.getBigDecimal("high", true);
		low = object.getBigDecimal("low", true);
		percentageChange = object.getBigDecimal("percentageChange", true);
		updateTime = object.get("updateTime", true);
		updateTimeUTC = object.get("updateTimeUTC", true);
		bid = object.getBigDecimal("bid", true);
		offer = object.getBigDecimal("offer", true);
		otcTradeable = object.getBoolean("otcTradeable", true);
		streamingPricesAvailable = object.getBoolean("streamingPricesAvailable");
		marketStatus = object.get("marketStatus");
		scalingFactor = object.getInt("scalingFactor");
	}

	public int getDelayTime() {
		return delayTime;
	}

	public String getEpic() {
		return epic;
	}

	public BigDecimal getNetChange() {
		return netChange;
	}

	public int getLotSize() {
		return lotSize;
	}

	public String getExpiry() {
		return expiry;
	}

	public String getInstrumentType() {
		return instrumentType;
	}

	public String getInstrumentName() {
		return instrumentName;
	}

	public BigDecimal getHigh() {
		return high;
	}

	public BigDecimal getLow() {
		return low;
	}

	public BigDecimal getPercentageChange() {
		return percentageChange;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public String getUpdateTimeUTC() {
		return updateTimeUTC;
	}

	public BigDecimal getBid() {
		return bid;
	}

	public BigDecimal getOffer() {
		return offer;
	}

	public boolean isOtcTradeable() {
		return otcTradeable;
	}

	public boolean isStreamingPricesAvailable() {
		return streamingPricesAvailable;
	}

	public String getMarketStatus() {
		return marketStatus;
	}

	public int getScalingFactor() {
		return scalingFactor;
	}

}

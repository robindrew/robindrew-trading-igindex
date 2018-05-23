package com.robindrew.trading.igindex.platform.rest.executor.getmarketnavigation.response;

import java.math.BigDecimal;

import com.robindrew.trading.igindex.platform.rest.executor.IgIndexJsonObject;

public class Market extends IgIndexJsonObject {

	private int delayTime;
	private String epic;
	private BigDecimal netChange;
	private Integer lotSize;
	private String expiry;
	private String instrumentType;
	private String instrumentName;
	private BigDecimal high;
	private BigDecimal low;
	private BigDecimal percentageChange;
	private String updateTime;
	private String updateTimeUTC;
	private BigDecimal bid;
	private BigDecimal offer;
	private Boolean otcTradeable;
	private boolean streamingPricesAvailable;
	private String marketStatus;
	private int scalingFactor;

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

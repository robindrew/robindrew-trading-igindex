package com.robindrew.trading.igindex.platform.rest.executor.getpositions;

import java.math.BigDecimal;
import java.time.LocalTime;

import com.robindrew.trading.igindex.platform.rest.executor.IgJsonObject;

public class Market extends IgJsonObject {

	private String instrumentName;
	private String expiry;
	private String epic;
	private InstrumentType instrumentType;
	private BigDecimal lotSize;
	private BigDecimal high;
	private BigDecimal low;
	private BigDecimal percentageChange;
	private BigDecimal netChange;
	private BigDecimal bid;
	private BigDecimal offer;
	private String updateTime; // ":"23:07:47",
	private String updateTimeUTC; // ":"23:07:47",
	private Integer delayTime;
	private Boolean streamingPricesAvailable;
	private MarketStatus marketStatus;
	private Integer scalingFactor;

	public String getInstrumentName() {
		return instrumentName;
	}

	public String getExpiry() {
		return expiry;
	}

	public String getEpic() {
		return epic;
	}

	public InstrumentType getInstrumentType() {
		return instrumentType;
	}

	public BigDecimal getLotSize() {
		return lotSize;
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

	public BigDecimal getNetChange() {
		return netChange;
	}

	public BigDecimal getBid() {
		return bid;
	}

	public BigDecimal getOffer() {
		return offer;
	}

	public LocalTime getUpdateTimeUTC() {
		return LocalTime.parse(updateTimeUTC);
	}

	public MarketStatus getMarketStatus() {
		return marketStatus;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public Integer getDelayTime() {
		return delayTime;
	}

	public Boolean getStreamingPricesAvailable() {
		return streamingPricesAvailable;
	}

	public Integer getScalingFactor() {
		return scalingFactor;
	}

	public BigDecimal getSpread() {
		return getOffer().subtract(getBid());
	}

	public BigDecimal getMid() {
		return getBid().add(getOffer()).divide(new BigDecimal(2));
	}
}

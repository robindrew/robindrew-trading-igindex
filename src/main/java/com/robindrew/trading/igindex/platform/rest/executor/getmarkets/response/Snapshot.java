package com.robindrew.trading.igindex.platform.rest.executor.getmarkets.response;

import java.math.BigDecimal;
import java.time.LocalTime;

import com.robindrew.trading.igindex.platform.rest.executor.IgJsonObject;
import com.robindrew.trading.igindex.platform.rest.executor.getpositions.MarketStatus;
import com.robindrew.trading.price.Mid;

public class Snapshot extends IgJsonObject {

	private MarketStatus marketStatus;
	private BigDecimal netChange;
	private BigDecimal percentageChange;
	private String updateTime;
	private BigDecimal delayTime;
	private BigDecimal bid;
	private BigDecimal offer;
	private BigDecimal high;
	private BigDecimal low;
	private BigDecimal binaryOdds;
	private BigDecimal decimalPlacesFactor;
	private BigDecimal scalingFactor;
	private BigDecimal controlledRiskExtraSpread;

	public MarketStatus getMarketStatus() {
		return marketStatus;
	}

	public BigDecimal getNetChange() {
		return netChange;
	}

	public BigDecimal getPercentageChange() {
		return percentageChange;
	}

	public LocalTime getUpdateTime() {
		return LocalTime.parse(updateTime);
	}

	public BigDecimal getDelayTime() {
		return delayTime;
	}

	public BigDecimal getBid() {
		return bid;
	}

	public BigDecimal getOffer() {
		return offer;
	}

	public BigDecimal getMid() {
		return Mid.getMid(getBid(), getOffer());
	}

	public BigDecimal getHigh() {
		return high;
	}

	public BigDecimal getLow() {
		return low;
	}

	public BigDecimal getBinaryOdds() {
		return binaryOdds;
	}

	public BigDecimal getDecimalPlacesFactor() {
		return decimalPlacesFactor;
	}

	public BigDecimal getScalingFactor() {
		return scalingFactor;
	}

	public BigDecimal getControlledRiskExtraSpread() {
		return controlledRiskExtraSpread;
	}

	public BigDecimal getSpread() {
		return getOffer().subtract(getBid());
	}

}

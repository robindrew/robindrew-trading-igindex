package com.robindrew.trading.igindex.platform.rest.executor.getmarkets;

import java.math.BigDecimal;
import java.time.LocalTime;

import com.robindrew.common.json.IJson;
import com.robindrew.trading.igindex.platform.rest.executor.IgJsonObject;
import com.robindrew.trading.igindex.platform.rest.executor.getpositions.MarketStatus;
import com.robindrew.trading.price.Mid;

public class Snapshot extends IgJsonObject {

	private final MarketStatus marketStatus;
	private final BigDecimal netChange;
	private final BigDecimal percentageChange;
	private final LocalTime updateTime;
	private final BigDecimal delayTime;
	private final BigDecimal bid;
	private final BigDecimal offer;
	private final BigDecimal high;
	private final BigDecimal low;
	private final BigDecimal binaryOdds;
	private final BigDecimal decimalPlacesFactor;
	private final BigDecimal scalingFactor;
	private final BigDecimal controlledRiskExtraSpread;

	public Snapshot(IJson object) {
		marketStatus = object.getEnum("marketStatus", MarketStatus.class);
		netChange = object.getBigDecimal("netChange");
		updateTime = object.getLocalTime("updateTime");
		percentageChange = object.getBigDecimal("percentageChange");
		delayTime = object.getBigDecimal("delayTime");
		bid = object.getBigDecimal("bid");
		offer = object.getBigDecimal("offer");
		high = object.getBigDecimal("high", true);
		low = object.getBigDecimal("low", true);
		binaryOdds = object.getBigDecimal("binaryOdds", true);
		decimalPlacesFactor = object.getBigDecimal("decimalPlacesFactor");
		scalingFactor = object.getBigDecimal("scalingFactor");
		controlledRiskExtraSpread = object.getBigDecimal("controlledRiskExtraSpread");
	}

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
		return updateTime;
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

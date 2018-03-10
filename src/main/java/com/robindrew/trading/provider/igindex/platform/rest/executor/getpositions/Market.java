package com.robindrew.trading.provider.igindex.platform.rest.executor.getpositions;

import java.math.BigDecimal;
import java.time.LocalTime;

import com.robindrew.common.json.IJson;
import com.robindrew.trading.provider.igindex.platform.rest.executor.IgJsonObject;

public final class Market extends IgJsonObject {

	private final String instrumentName;
	private final String expiry;
	private final String epic;
	private final InstrumentType instrumentType;
	private final BigDecimal lotSize;
	private final BigDecimal high;
	private final BigDecimal low;
	private final BigDecimal percentageChange;
	private final BigDecimal netChange;
	private final BigDecimal bid;
	private final BigDecimal offer;
	// private LocalTime updateTime; //":"23:07:47",
	private final LocalTime updateTimeUTC; // ":"23:07:47",
	// "delayTime":0,
	// "streamingPricesAvailable":true,
	private final MarketStatus marketStatus;
	// "scalingFactor":1

	public Market(IJson object) {
		instrumentName = object.get("instrumentName");
		expiry = object.get("expiry");
		epic = object.get("epic");
		instrumentType = object.getEnum("instrumentType", InstrumentType.class);
		lotSize = object.getBigDecimal("lotSize");
		high = object.getBigDecimal("high", true);
		low = object.getBigDecimal("low", true);
		percentageChange = object.getBigDecimal("percentageChange");
		netChange = object.getBigDecimal("netChange");
		bid = object.getBigDecimal("bid");
		offer = object.getBigDecimal("offer");
		updateTimeUTC = object.getLocalTime("updateTimeUTC");
		marketStatus = object.getEnum("marketStatus", MarketStatus.class);
	}

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
		return updateTimeUTC;
	}

	public MarketStatus getMarketStatus() {
		return marketStatus;
	}

	public BigDecimal getSpread() {
		return getOffer().subtract(getBid());
	}

	public BigDecimal getMid() {
		return getBid().add(getOffer()).divide(new BigDecimal(2));
	}
}

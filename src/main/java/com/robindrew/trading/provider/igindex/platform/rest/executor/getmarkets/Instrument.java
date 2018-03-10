package com.robindrew.trading.provider.igindex.platform.rest.executor.getmarkets;

import java.math.BigDecimal;
import java.util.List;

import com.google.common.collect.ImmutableList;
import com.robindrew.common.json.IJson;
import com.robindrew.trading.provider.igindex.platform.rest.executor.IgJsonObject;
import com.robindrew.trading.provider.igindex.platform.rest.executor.getpositions.InstrumentType;

public class Instrument extends IgJsonObject {

	private final String epic;
	private final String expiry;
	private final String name;
	private final boolean forceOpenAllowed;
	private final boolean stopsLimitsAllowed;
	private final BigDecimal lotSize;
	private final TradeUnit unit;
	private final InstrumentType type;
	private final boolean controlledRiskAllowed;
	private final boolean streamingPricesAvailable;
	private final String marketId;

	private final BigDecimal marginFactor;
	private final MarginFactorUnit marginFactorUnit;
	private final List<Currency> currencies;
	private final List<MarginDepositBand> marginDepositBands;

	private final String newsCode;
	private final String chartCode;
	private final String country;

	public Instrument(IJson object) {
		epic = object.get("epic");
		expiry = object.get("expiry");
		name = object.get("name");
		forceOpenAllowed = object.getBoolean("forceOpenAllowed");
		stopsLimitsAllowed = object.getBoolean("stopsLimitsAllowed");
		lotSize = object.getBigDecimal("lotSize");
		unit = object.getEnum("unit", TradeUnit.class);
		type = object.getEnum("type", InstrumentType.class);
		controlledRiskAllowed = object.getBoolean("controlledRiskAllowed");
		streamingPricesAvailable = object.getBoolean("streamingPricesAvailable");
		marketId = object.get("marketId", true);
		currencies = object.getList("currencies", element -> new Currency(element));
		// sprintMarketsMinimumExpiryTime
		// sprintMarketsMaximumExpiryTime
		marginDepositBands = object.getList("marginDepositBands", element -> new MarginDepositBand(element));
		marginFactor = object.getBigDecimal("marginFactor");
		marginFactorUnit = object.getEnum("marginFactorUnit", MarginFactorUnit.class);
		// slippageFactor
		// openingHours
		// expiryDetails
		// rolloverDetails
		newsCode = object.get("newsCode", true);
		chartCode = object.get("chartCode", true);
		country = object.get("country", true);
		// valueOfOnePip
		// onePipMeans
		// contractSize
		// specialInfo
	}

	public String getEpic() {
		return epic;
	}

	public String getExpiry() {
		return expiry;
	}

	public String getName() {
		return name;
	}

	public boolean isForceOpenAllowed() {
		return forceOpenAllowed;
	}

	public boolean isStopsLimitsAllowed() {
		return stopsLimitsAllowed;
	}

	public BigDecimal getLotSize() {
		return lotSize;
	}

	public TradeUnit getUnit() {
		return unit;
	}

	public InstrumentType getType() {
		return type;
	}

	public boolean isControlledRiskAllowed() {
		return controlledRiskAllowed;
	}

	public boolean isStreamingPricesAvailable() {
		return streamingPricesAvailable;
	}

	public String getMarketId() {
		return marketId;
	}

	public BigDecimal getMarginFactor() {
		return marginFactor;
	}

	public MarginFactorUnit getMarginFactorUnit() {
		return marginFactorUnit;
	}

	public List<Currency> getCurrencies() {
		return ImmutableList.copyOf(currencies);
	}

	public List<MarginDepositBand> getMarginDepositBands() {
		return ImmutableList.copyOf(marginDepositBands);
	}

	public String getNewsCode() {
		return newsCode;
	}

	public String getChartCode() {
		return chartCode;
	}

	public String getCountry() {
		return country;
	}

}

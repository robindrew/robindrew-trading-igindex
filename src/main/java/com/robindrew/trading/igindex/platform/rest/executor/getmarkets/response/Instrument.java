package com.robindrew.trading.igindex.platform.rest.executor.getmarkets.response;

import java.math.BigDecimal;
import java.util.List;

import com.robindrew.trading.igindex.platform.rest.executor.IgIndexJsonObject;
import com.robindrew.trading.igindex.platform.rest.executor.getpositions.InstrumentType;

public class Instrument extends IgIndexJsonObject {

	private String epic;
	private String expiry;
	private String name;
	private boolean forceOpenAllowed;
	private boolean stopsLimitsAllowed;
	private BigDecimal lotSize;
	private TradeUnit unit;
	private InstrumentType type;
	private boolean controlledRiskAllowed;
	private boolean streamingPricesAvailable;
	private String marketId;
	private List<Currency> currencies;
	private String sprintMarketsMinimumExpiryTime;
	private String sprintMarketsMaximumExpiryTime;
	private List<MarginDepositBand> marginDepositBands;

	private BigDecimal marginFactor;
	private MarginFactorUnit marginFactorUnit;
	private UnitValue slippageFactor;
	private LimitedRiskPremium limitedRiskPremium;
	private OpeningHours openingHours;
	private ExpiryDetails expiryDetails;
	private RolloverDetails rolloverDetails;

	private String newsCode;
	private String chartCode;
	private String country;
	private String valueOfOnePip;
	private String onePipMeans;
	private String contractSize;
	private List<String> specialInfo;

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

	public List<Currency> getCurrencies() {
		return currencies;
	}

	public String getSprintMarketsMinimumExpiryTime() {
		return sprintMarketsMinimumExpiryTime;
	}

	public String getSprintMarketsMaximumExpiryTime() {
		return sprintMarketsMaximumExpiryTime;
	}

	public List<MarginDepositBand> getMarginDepositBands() {
		return marginDepositBands;
	}

	public BigDecimal getMarginFactor() {
		return marginFactor;
	}

	public MarginFactorUnit getMarginFactorUnit() {
		return marginFactorUnit;
	}

	public UnitValue getSlippageFactor() {
		return slippageFactor;
	}

	public LimitedRiskPremium getLimitedRiskPremium() {
		return limitedRiskPremium;
	}

	public OpeningHours getOpeningHours() {
		return openingHours;
	}

	public ExpiryDetails getExpiryDetails() {
		return expiryDetails;
	}

	public RolloverDetails getRolloverDetails() {
		return rolloverDetails;
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

	public String getValueOfOnePip() {
		return valueOfOnePip;
	}

	public String getOnePipMeans() {
		return onePipMeans;
	}

	public String getContractSize() {
		return contractSize;
	}

	public List<String> getSpecialInfo() {
		return specialInfo;
	}

}

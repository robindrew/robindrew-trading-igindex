package com.robindrew.trading.igindex.platform.rest.executor.getpositions;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.robindrew.common.locale.CurrencyCode;
import com.robindrew.trading.igindex.platform.rest.executor.IgIndexJsonObject;
import com.robindrew.trading.igindex.platform.rest.executor.getmarkets.response.LimitedRiskPremium;
import com.robindrew.trading.trade.TradeDirection;

public class Position extends IgIndexJsonObject {

	private BigDecimal contractSize;
	private String createdDate;
	private String createdDateUTC;
	private String dealId;
	private String dealReference;
	private BigDecimal size;
	private TradeDirection direction;
	private BigDecimal limitLevel;
	private BigDecimal level;
	private CurrencyCode currency;
	private boolean controlledRisk;
	private BigDecimal stopLevel;
	private BigDecimal trailingStep;
	private BigDecimal trailingStopDistance;
	private LimitedRiskPremium limitedRiskPremium;

	public BigDecimal getContractSize() {
		return contractSize;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public String getCreatedDateUTC() {
		return createdDateUTC;
	}

	public String getCreatedDateFormatted() {
		// "createdDateUTC":"2016-12-21T22:31:42"
		return getCreatedDate().toString().replace('T', ' ');
	}

	public LocalDateTime getCreatedDateTime() {
		return LocalDateTime.parse(getCreatedDateUTC());
	}

	public String getDealId() {
		return dealId;
	}

	public String getDealReference() {
		return dealReference;
	}

	/**
	 * The actual size of the deal.
	 */
	public BigDecimal getSize() {
		return size;
	}

	public TradeDirection getDirection() {
		return direction;
	}

	public BigDecimal getLimitLevel() {
		return limitLevel;
	}

	public BigDecimal getLevel() {
		return level;
	}

	public CurrencyCode getCurrency() {
		return currency;
	}

	public boolean isControlledRisk() {
		return controlledRisk;
	}

	public BigDecimal getStopLevel() {
		return stopLevel;
	}

	public BigDecimal getTrailingStep() {
		return trailingStep;
	}

	public BigDecimal getTrailingStopDistance() {
		return trailingStopDistance;
	}

	public LimitedRiskPremium getLimitedRiskPremium() {
		return limitedRiskPremium;
	}

}

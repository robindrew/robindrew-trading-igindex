package com.robindrew.trading.igindex.platform.rest.executor.getpositions;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.robindrew.common.json.IJson;
import com.robindrew.common.locale.CurrencyCode;
import com.robindrew.trading.igindex.platform.rest.executor.IgJsonObject;
import com.robindrew.trading.trade.TradeDirection;

public final class Position extends IgJsonObject {

	private final BigDecimal contractSize;
	private final String createdDateUTC; // "createdDateUTC":"2016-12-21T22:31:42",
	private final String dealId;
	private final String dealReference;
	private final BigDecimal size;
	private final TradeDirection direction;
	private final BigDecimal limitLevel;
	private final BigDecimal level;
	private final CurrencyCode currency;
	private final boolean controlledRisk;
	private final BigDecimal stopLevel;

	// "createdDate":"2016/12/21 22:31:42:000",
	// "trailingStep":null,
	// "trailingStopDistance":null

	public Position(IJson object) {
		contractSize = object.getBigDecimal("contractSize");
		createdDateUTC = object.get("createdDateUTC");
		dealId = object.get("dealId");
		dealReference = object.get("dealReference");
		size = object.getBigDecimal("size");
		direction = object.getEnum("direction", TradeDirection.class);
		limitLevel = object.getBigDecimal("limitLevel", true);
		level = object.getBigDecimal("level");
		currency = object.getEnum("currency", CurrencyCode.class);
		controlledRisk = object.getBoolean("currency");
		stopLevel = object.getBigDecimal("stopLevel", true);
	}

	/**
	 * The contract size (what is this?)
	 */
	public BigDecimal getContractSize() {
		return contractSize;
	}

	public String getCreatedDateUTC() {
		return createdDateUTC;
	}

	public String getCreatedDateFormatted() {
		return getCreatedDate().toString().replace('T', ' ');
	}

	public LocalDateTime getCreatedDate() {
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

}

package com.robindrew.trading.provider.igindex.platform.rest.executor.getmarkets;

import java.math.BigDecimal;

import com.robindrew.common.json.IJson;
import com.robindrew.common.locale.CurrencyCode;
import com.robindrew.trading.provider.igindex.platform.rest.executor.IgJsonObject;

public class Currency extends IgJsonObject {

	private final CurrencyCode code;
	private final String symbol;
	private final BigDecimal baseExchangeRate;
	private final BigDecimal exchangeRate;
	private final boolean isDefault;

	public Currency(IJson object) {
		code = object.getEnum("code", CurrencyCode.class);
		symbol = object.get("symbol");
		baseExchangeRate = object.getBigDecimal("baseExchangeRate");
		exchangeRate = object.getBigDecimal("exchangeRate");
		isDefault = object.getBoolean("isDefault");
	}

	public CurrencyCode getCode() {
		return code;
	}

	public String getSymbol() {
		return symbol;
	}

	public BigDecimal getBaseExchangeRate() {
		return baseExchangeRate;
	}

	public BigDecimal getExchangeRate() {
		return exchangeRate;
	}

	public boolean isDefault() {
		return isDefault;
	}

}

package com.robindrew.trading.provider.igindex.platform.rest.executor.getmarkets;

import java.math.BigDecimal;

import com.robindrew.common.json.IJson;
import com.robindrew.common.locale.CurrencyCode;
import com.robindrew.trading.provider.igindex.platform.rest.executor.IgJsonObject;

public class MarginDepositBand extends IgJsonObject {

	private final BigDecimal min;
	private final BigDecimal max;
	private final BigDecimal margin;
	private final CurrencyCode currency;

	public MarginDepositBand(IJson object) {
		min = object.getBigDecimal("min");
		max = object.getBigDecimal("max", true);
		margin = object.getBigDecimal("margin");
		currency = object.getEnum("currency", CurrencyCode.class);
	}

	public BigDecimal getMin() {
		return min;
	}

	public BigDecimal getMax() {
		return max;
	}

	public BigDecimal getMargin() {
		return margin;
	}

	public CurrencyCode getCurrency() {
		return currency;
	}

}

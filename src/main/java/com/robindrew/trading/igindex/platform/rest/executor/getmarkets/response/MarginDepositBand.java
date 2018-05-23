package com.robindrew.trading.igindex.platform.rest.executor.getmarkets.response;

import java.math.BigDecimal;

import com.robindrew.common.json.IJson;
import com.robindrew.common.locale.CurrencyCode;
import com.robindrew.trading.igindex.platform.rest.executor.IgIndexJsonObject;

public class MarginDepositBand extends IgIndexJsonObject {

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

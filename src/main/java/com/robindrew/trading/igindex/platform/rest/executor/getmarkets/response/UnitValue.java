package com.robindrew.trading.igindex.platform.rest.executor.getmarkets.response;

import java.math.BigDecimal;

import com.robindrew.trading.igindex.platform.rest.executor.IgJsonObject;

public class UnitValue extends IgJsonObject {

	private Unit unit;
	private BigDecimal value;

	public Unit getUnit() {
		return unit;
	}

	public BigDecimal getValue() {
		return value;
	}

}

package com.robindrew.trading.igindex.platform.rest.executor.getmarkets.response;

import java.math.BigDecimal;

import com.robindrew.trading.igindex.platform.rest.executor.IgIndexJsonObject;

public class UnitValue extends IgIndexJsonObject {

	private Unit unit;
	private BigDecimal value;

	public Unit getUnit() {
		return unit;
	}

	public BigDecimal getValue() {
		return value;
	}

}

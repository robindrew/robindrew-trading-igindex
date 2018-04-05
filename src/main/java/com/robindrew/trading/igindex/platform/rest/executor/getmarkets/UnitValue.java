package com.robindrew.trading.igindex.platform.rest.executor.getmarkets;

import java.math.BigDecimal;

import com.robindrew.common.json.IJson;
import com.robindrew.trading.igindex.platform.rest.executor.IgJsonObject;

public class UnitValue extends IgJsonObject {

	private final UnitType unit;
	private final BigDecimal value;

	public UnitValue(IJson object) {
		unit = object.getEnum("unit", UnitType.class);
		value = object.getBigDecimal("value");
	}

	public UnitType getUnit() {
		return unit;
	}

	public BigDecimal getValue() {
		return value;
	}

}

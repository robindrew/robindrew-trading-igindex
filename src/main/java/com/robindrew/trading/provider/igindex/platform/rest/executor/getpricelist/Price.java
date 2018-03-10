package com.robindrew.trading.provider.igindex.platform.rest.executor.getpricelist;

import java.math.BigDecimal;

import com.robindrew.common.json.IJson;
import com.robindrew.trading.provider.igindex.platform.rest.executor.IgJsonObject;

public class Price extends IgJsonObject {

	private final BigDecimal bid;
	private final BigDecimal ask;
	private final BigDecimal lastTraded;

	public Price(IJson object) {
		bid = object.getBigDecimal("bid", true);
		ask = object.getBigDecimal("ask", true);
		lastTraded = object.getBigDecimal("lastTraded", true);
	}

	public BigDecimal getBid() {
		return bid;
	}

	public BigDecimal getAsk() {
		return ask;
	}

	public BigDecimal getLastTraded() {
		return lastTraded;
	}

}

package com.robindrew.trading.igindex.platform.rest.executor.openposition;

import java.math.BigDecimal;

import com.robindrew.common.locale.CurrencyCode;
import com.robindrew.trading.igindex.platform.rest.executor.openposition.request.OrderType;
import com.robindrew.trading.trade.TradeDirection;

public class OpenPositionRequest {

	private final String epic;
	private final String expiry = "DFB";
	private final TradeDirection direction;
	private final BigDecimal size;
	private final OrderType orderType = OrderType.MARKET;
	private final BigDecimal stopDistance;
	private final boolean guaranteedStop;
	private final BigDecimal limitDistance;
	private final boolean forceOpen;
	private final CurrencyCode currencyCode;

	public OpenPositionRequest(String epic, TradeDirection direction, BigDecimal size, BigDecimal stopDistance, BigDecimal limitDistance) {
		if (epic.isEmpty()) {
			throw new IllegalArgumentException("epic is empty");
		}
		if (direction == null) {
			throw new NullPointerException("direction");
		}
		if (size == null) {
			throw new NullPointerException("size");
		}

		this.epic = epic;
		this.direction = direction;
		this.size = size;
		this.guaranteedStop = false;
		this.stopDistance = stopDistance;
		this.forceOpen = true;
		this.currencyCode = CurrencyCode.GBP;
		this.limitDistance = limitDistance;
	}

	public String getEpic() {
		return epic;
	}

	public String getExpiry() {
		return expiry;
	}

	public TradeDirection getDirection() {
		return direction;
	}

	public BigDecimal getSize() {
		return size;
	}

	public OrderType getOrderType() {
		return orderType;
	}

	public boolean isGuaranteedStop() {
		return guaranteedStop;
	}

	public boolean isForceOpen() {
		return forceOpen;
	}

	public CurrencyCode getCurrencyCode() {
		return currencyCode;
	}

	public BigDecimal getStopDistance() {
		return stopDistance;
	}

	public BigDecimal getLimitDistance() {
		return limitDistance;
	}

}

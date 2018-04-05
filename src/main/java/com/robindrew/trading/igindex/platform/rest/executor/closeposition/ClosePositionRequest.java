package com.robindrew.trading.igindex.platform.rest.executor.closeposition;

import java.math.BigDecimal;

import com.robindrew.trading.igindex.platform.rest.executor.IgJsonObject;
import com.robindrew.trading.igindex.platform.rest.executor.getpositions.MarketPosition;
import com.robindrew.trading.position.IPosition;
import com.robindrew.trading.trade.TradeDirection;

public class ClosePositionRequest extends IgJsonObject {

	public static ClosePositionRequest forPosition(MarketPosition position) {
		String dealId = position.getPosition().getDealId();
		BigDecimal size = position.getPosition().getSize();
		TradeDirection direction = position.getPosition().getDirection().invert();
		OrderType orderType = OrderType.MARKET;
		return new ClosePositionRequest(dealId, size, direction, orderType);
	}

	public static ClosePositionRequest forPosition(IPosition position) {
		String dealId = position.getId();
		BigDecimal size = position.getTradeSize();
		TradeDirection direction = position.getDirection().invert();
		OrderType orderType = OrderType.MARKET;
		return new ClosePositionRequest(dealId, size, direction, orderType);
	}

	private final String dealId;
	private final BigDecimal size;
	private final TradeDirection direction;
	private final OrderType orderType;

	public ClosePositionRequest(String dealId, BigDecimal size, TradeDirection direction, OrderType orderType) {
		this.dealId = dealId;
		this.size = size;
		this.direction = direction;
		this.orderType = orderType;
	}

	public String getDealId() {
		return dealId;
	}

	public BigDecimal getSize() {
		return size;
	}

	public TradeDirection getDirection() {
		return direction;
	}

	public OrderType getOrderType() {
		return orderType;
	}

}

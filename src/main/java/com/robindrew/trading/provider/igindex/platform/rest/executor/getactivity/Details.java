package com.robindrew.trading.provider.igindex.platform.rest.executor.getactivity;

import java.math.BigDecimal;
import java.util.List;

import com.robindrew.common.json.IJson;
import com.robindrew.trading.provider.igindex.platform.rest.executor.IgJsonObject;
import com.robindrew.trading.trade.TradeDirection;

public class Details extends IgJsonObject {

	private final String dealReference;
	private final List<Action> actions;
	private final String marketName;
	private final String goodTillDate;
	private final String currency;
	private final BigDecimal size;
	private final TradeDirection direction;
	private final BigDecimal level;
	private final Integer stopLevel;
	private final Integer stopDistance;
	private final boolean guaranteedStop;
	private final Integer trailingStopDistance;
	private final Integer trailingStep;
	private final Integer limitLevel;
	private final Integer limitDistance;

	public Details(IJson object) {
		this.dealReference = object.get("dealReference", true);
		this.actions = Action.toList(object.getObjectList("actions"));
		this.marketName = object.get("marketName");
		this.goodTillDate = object.get("goodTillDate", true);
		this.currency = object.get("currency");
		this.size = object.getBigDecimal("size");
		this.direction = object.getEnum("direction", TradeDirection.class);
		this.level = object.getBigDecimal("level");
		this.stopLevel = object.getInt("stopLevel", true);
		this.stopDistance = object.getInt("stopDistance", true);
		this.guaranteedStop = object.getBoolean("guaranteedStop");
		this.trailingStopDistance = object.getInt("trailingStopDistance", true);
		this.trailingStep = object.getInt("trailingStep", true);
		this.limitLevel = object.getInt("limitLevel", true);
		this.limitDistance = object.getInt("limitDistance", true);
	}

	public String getDealReference() {
		return dealReference;
	}

	public List<Action> getActions() {
		return actions;
	}

	public String getMarketName() {
		return marketName;
	}

	public String getGoodTillDate() {
		return goodTillDate;
	}

	public String getCurrency() {
		return currency;
	}

	public BigDecimal getSize() {
		return size;
	}

	public TradeDirection getDirection() {
		return direction;
	}

	public BigDecimal getLevel() {
		return level;
	}

	public int getStopLevel() {
		return stopLevel;
	}

	public int getStopDistance() {
		return stopDistance;
	}

	public boolean isGuaranteedStop() {
		return guaranteedStop;
	}

	public Integer getTrailingStopDistance() {
		return trailingStopDistance;
	}

	public Integer getTrailingStep() {
		return trailingStep;
	}

	public Integer getLimitLevel() {
		return limitLevel;
	}

	public Integer getLimitDistance() {
		return limitDistance;
	}
}

package com.robindrew.trading.igindex.platform.rest.executor.getactivity.response;

import java.math.BigDecimal;
import java.util.List;

import com.robindrew.trading.igindex.platform.rest.executor.IgJsonObject;
import com.robindrew.trading.trade.TradeDirection;

public class Details extends IgJsonObject {

	private String dealReference;
	private List<Action> actions;
	private String marketName;
	private String goodTillDate;
	private String currency;
	private BigDecimal size;
	private TradeDirection direction;
	private BigDecimal level;
	private BigDecimal stopLevel;
	private BigDecimal stopDistance;
	private boolean guaranteedStop;
	private BigDecimal trailingStopDistance;
	private BigDecimal trailingStep;
	private BigDecimal limitLevel;
	private BigDecimal limitDistance;

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

	public BigDecimal getStopLevel() {
		return stopLevel;
	}

	public BigDecimal getStopDistance() {
		return stopDistance;
	}

	public boolean isGuaranteedStop() {
		return guaranteedStop;
	}

	public BigDecimal getTrailingStopDistance() {
		return trailingStopDistance;
	}

	public BigDecimal getTrailingStep() {
		return trailingStep;
	}

	public BigDecimal getLimitLevel() {
		return limitLevel;
	}

	public BigDecimal getLimitDistance() {
		return limitDistance;
	}
}

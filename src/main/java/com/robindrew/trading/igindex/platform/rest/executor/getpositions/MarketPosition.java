package com.robindrew.trading.igindex.platform.rest.executor.getpositions;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.apache.commons.lang3.builder.CompareToBuilder;

import com.robindrew.common.locale.CurrencyCode;
import com.robindrew.trading.igindex.IgIndexInstrument;
import com.robindrew.trading.igindex.platform.rest.executor.IgIndexJsonObject;
import com.robindrew.trading.position.Positions;
import com.robindrew.trading.position.market.IMarketPosition;
import com.robindrew.trading.position.market.IMarketPrice;
import com.robindrew.trading.position.market.MarketPrice;
import com.robindrew.trading.trade.TradeDirection;

public class MarketPosition extends IgIndexJsonObject implements IMarketPosition, Comparable<MarketPosition> {

	private Position position;
	private Market market;

	public Position getPosition() {
		return position;
	}

	public Market getMarket() {
		return market;
	}

	@Override
	public String getId() {
		return getPosition().getDealId();
	}

	public String getEpic() {
		return getMarket().getEpic();
	}

	@Override
	public TradeDirection getDirection() {
		return getPosition().getDirection();
	}

	@Override
	public LocalDateTime getOpenDate() {
		return getPosition().getCreatedDateTime();
	}

	@Override
	public CurrencyCode getTradeCurrency() {
		return getPosition().getCurrency();
	}

	@Override
	public BigDecimal getOpenPrice() {
		return getPosition().getLevel();
	}

	@Override
	public BigDecimal getTradeSize() {
		return getPosition().getSize();
	}

	@Override
	public BigDecimal getProfit() {
		return Positions.getProfit(this, getMarketPrice());
	}

	@Override
	public BigDecimal getLoss() {
		return Positions.getLoss(this, getMarketPrice());
	}

	@Override
	public boolean isProfit() {
		return Positions.isProfit(this, getMarketPrice());
	}

	@Override
	public boolean isLoss() {
		return !isProfit();
	}

	@Override
	public IgIndexInstrument getInstrument() {
		return IgIndexInstrument.forEpic(getEpic());
	}

	@Override
	public int compareTo(MarketPosition that) {
		CompareToBuilder compare = new CompareToBuilder();
		compare.append(that.getOpenDate(), this.getOpenDate());
		compare.append(this.getEpic(), that.getEpic());
		compare.append(this.getId(), that.getId());
		return compare.toComparison();
	}

	@Override
	public IMarketPrice getMarketPrice() {
		return new MarketPrice(getMarket().getBid(), getMarket().getOffer());
	}

	@Override
	public BigDecimal getProfitLimitPrice() {
		return getPosition().getLimitLevel();
	}

	@Override
	public BigDecimal getStopLossPrice() {
		return getPosition().getStopLevel();
	}

}

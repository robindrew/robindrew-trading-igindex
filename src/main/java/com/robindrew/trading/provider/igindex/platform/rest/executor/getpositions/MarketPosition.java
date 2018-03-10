package com.robindrew.trading.provider.igindex.platform.rest.executor.getpositions;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.apache.commons.lang3.builder.CompareToBuilder;

import com.robindrew.common.json.IJson;
import com.robindrew.common.locale.CurrencyCode;
import com.robindrew.trading.IInstrument;
import com.robindrew.trading.position.Positions;
import com.robindrew.trading.position.market.IMarketPosition;
import com.robindrew.trading.position.market.IMarketPrice;
import com.robindrew.trading.position.market.MarketPrice;
import com.robindrew.trading.provider.igindex.IgInstrument;
import com.robindrew.trading.provider.igindex.platform.rest.executor.IgJsonObject;
import com.robindrew.trading.trade.TradeDirection;

public class MarketPosition extends IgJsonObject implements IMarketPosition, Comparable<MarketPosition> {

	private final Position position;
	private final Market market;

	public MarketPosition(IJson object) {
		this.position = new Position(object.getObject("position"));
		this.market = new Market(object.getObject("market"));
	}

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
		return getPosition().getCreatedDate();
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

	public com.robindrew.trading.InstrumentType getInstrumentType() {
		throw new UnsupportedOperationException();
	}

	@Override
	public IInstrument getInstrument() {
		return new IgInstrument(getEpic(), getInstrumentType());
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

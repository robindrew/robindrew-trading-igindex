package com.robindrew.trading.provider.igindex.platform.streaming.subscription.charttick;

import java.math.BigDecimal;

import com.robindrew.trading.IInstrument;
import com.robindrew.trading.price.candle.IPriceCandle;
import com.robindrew.trading.price.candle.format.pcf.FloatingPoint;
import com.robindrew.trading.price.precision.IPricePrecision;
import com.robindrew.trading.price.tick.PriceTick;

public class ChartTick {

	private final IInstrument instrument;
	private final IPricePrecision precision;
	private final long timestamp;
	private final BigDecimal bid;
	private final BigDecimal ask;

	public ChartTick(IInstrument instrument, IPricePrecision precision, String itemName, String timestamp, String bid, String ask) {
		this.timestamp = Long.parseLong(timestamp);
		this.bid = new BigDecimal(bid);
		this.ask = new BigDecimal(ask);
		this.instrument = instrument;
		this.precision = precision;
	}

	public IInstrument getInstrument() {
		return instrument;
	}

	public IPricePrecision getPrecision() {
		return precision;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public BigDecimal getBid() {
		return bid;
	}

	public BigDecimal getAsk() {
		return ask;
	}

	public IPriceCandle toPriceCandle() {
		BigDecimal bid = getBid();
		BigDecimal ask = getAsk();
		int decimalPlaces = precision.getDecimalPlaces();
		int bidPrice = FloatingPoint.toBigInt(bid, decimalPlaces);
		int askPrice = FloatingPoint.toBigInt(ask, decimalPlaces);
		return new PriceTick(bidPrice, askPrice, getTimestamp(), decimalPlaces);
	}

}

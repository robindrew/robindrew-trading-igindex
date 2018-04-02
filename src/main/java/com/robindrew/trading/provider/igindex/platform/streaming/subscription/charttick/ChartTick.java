package com.robindrew.trading.provider.igindex.platform.streaming.subscription.charttick;

import java.math.BigDecimal;

import com.robindrew.trading.IInstrument;
import com.robindrew.trading.price.candle.IPriceCandle;
import com.robindrew.trading.price.candle.PriceCandleInstant;
import com.robindrew.trading.price.candle.format.pcf.FloatingPoint;
import com.robindrew.trading.price.precision.IPricePrecision;
import com.robindrew.trading.price.tick.PriceTick;

public class ChartTick extends PriceTick {

	private final IInstrument instrument;
	private final IPricePrecision precision;

	public ChartTick(IInstrument instrument, IPricePrecision precision, String itemName, String timestamp, String bid, String ask) {
		super(Long.parseLong(timestamp), new BigDecimal(bid), new BigDecimal(ask));
		this.instrument = instrument;
		this.precision = precision;
	}

	public IInstrument getInstrument() {
		return instrument;
	}

	public IPriceCandle toPriceCandle() {
		BigDecimal bid = getBid();
		BigDecimal ask = getAsk();
		int decimalPlaces = precision.getDecimalPlaces();
		int bidPrice = FloatingPoint.toBigInt(bid, decimalPlaces);
		int askPrice = FloatingPoint.toBigInt(ask, decimalPlaces);
		return new PriceCandleInstant(bidPrice, askPrice, getTimestamp(), decimalPlaces);
	}

}

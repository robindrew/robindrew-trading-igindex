package com.robindrew.trading.provider.igindex.platform.streaming.subscription.charttick;

import com.robindrew.common.util.Check;
import com.robindrew.trading.IInstrument;
import com.robindrew.trading.platform.streaming.IStreamingSubscriptionConfig;
import com.robindrew.trading.price.precision.IPricePrecision;

public class ChartTickPriceStreamConfig implements IStreamingSubscriptionConfig {

	private final IInstrument instrument;
	private final IPricePrecision precision;

	public ChartTickPriceStreamConfig(IInstrument instrument, IPricePrecision precision) {
		this.instrument = Check.notNull("instrument", instrument);
		this.precision = Check.notNull("precision", precision);
	}

	@Override
	public IInstrument getInstrument() {
		return instrument;
	}

	public IPricePrecision getPrecision() {
		return precision;
	}

}

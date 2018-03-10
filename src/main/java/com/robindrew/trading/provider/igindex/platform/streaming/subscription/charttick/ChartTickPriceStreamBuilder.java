package com.robindrew.trading.provider.igindex.platform.streaming.subscription.charttick;

import com.robindrew.trading.IInstrument;
import com.robindrew.trading.price.precision.IPricePrecision;

public class ChartTickPriceStreamBuilder {

	public ChartTickPriceStream build(IInstrument instrument, IPricePrecision precision) {
		ChartTickPriceStreamConfig config = new ChartTickPriceStreamConfig(instrument, precision);
		ChartTickPriceStreamListener listener = new ChartTickPriceStreamListener(config);
		return new ChartTickPriceStream(config, listener);
	}

}

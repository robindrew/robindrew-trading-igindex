package com.robindrew.trading.igindex.tool;

import java.io.File;

import com.robindrew.common.util.Threads;
import com.robindrew.trading.IInstrument;
import com.robindrew.trading.igindex.IgInstrument;
import com.robindrew.trading.igindex.platform.streaming.subscription.charttick.ChartTickPriceStream;
import com.robindrew.trading.price.candle.io.stream.sink.PriceCandleFileSink;
import com.robindrew.trading.price.precision.IPricePrecision;
import com.robindrew.trading.price.precision.PricePrecision;

public class StreamingSubscriptionTool {

	public static void main(String[] args) {

		// Configuration
		String priceDirectory = "c:/temp/prices";
		IInstrument instrument = IgInstrument.SPOT_BITCOIN;
		IPricePrecision precision = new PricePrecision(2);

		// Create the stream
		try (ChartTickPriceStream priceStream = new ChartTickPriceStream(instrument, precision)) {
			priceStream.start();

			PriceCandleFileSink outputFile = new PriceCandleFileSink(instrument, new File(priceDirectory));
			outputFile.start();

			priceStream.register(outputFile);

			Threads.sleepForever();
		}
	}

}

package com.robindrew.trading.igindex.tool;

import java.io.File;

import com.robindrew.common.util.Threads;
import com.robindrew.trading.igindex.IgInstrument;
import com.robindrew.trading.igindex.platform.streaming.subscription.charttick.ChartTickPriceStream;
import com.robindrew.trading.price.candle.io.stream.sink.PriceCandleFileSink;

public class StreamingSubscriptionTool {

	public static void main(String[] args) {

		// Configuration
		String priceDirectory = "c:/temp/prices";
		IgInstrument instrument = IgInstrument.SPOT_BITCOIN;

		// Create the stream
		try (ChartTickPriceStream priceStream = new ChartTickPriceStream(instrument)) {
			priceStream.start();

			PriceCandleFileSink outputFile = new PriceCandleFileSink(instrument, new File(priceDirectory));
			outputFile.start();

			priceStream.register(outputFile);

			Threads.sleepForever();
		}
	}

}

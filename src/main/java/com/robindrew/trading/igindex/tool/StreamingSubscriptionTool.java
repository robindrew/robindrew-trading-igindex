package com.robindrew.trading.igindex.tool;

import java.io.File;

import com.robindrew.common.util.Threads;
import com.robindrew.trading.IInstrument;
import com.robindrew.trading.igindex.IgInstrument;
import com.robindrew.trading.igindex.platform.streaming.subscription.charttick.ChartTickPriceStream;
import com.robindrew.trading.price.precision.IPricePrecision;
import com.robindrew.trading.price.precision.PricePrecision;
import com.robindrew.trading.price.tick.io.stream.sink.PriceTickFileSink;

public class StreamingSubscriptionTool {

	public static void main(String[] args) {

		// Configuration
		String priceDirectory = "c:/temp/prices";
		IInstrument instrument = IgInstrument.SPOT_BITCOIN;
		IPricePrecision precision = new PricePrecision(2, 900, 90000);

		// Create the stream
		try (ChartTickPriceStream priceStream = new ChartTickPriceStream(instrument, precision)) {
			priceStream.start();

			PriceTickFileSink outputFile = new PriceTickFileSink(instrument, new File(priceDirectory));
			outputFile.start();

			priceStream.register(outputFile);

			Threads.sleepForever();
		}
	}

}

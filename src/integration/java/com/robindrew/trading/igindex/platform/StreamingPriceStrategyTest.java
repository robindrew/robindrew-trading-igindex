package com.robindrew.trading.igindex.platform;

import org.junit.Test;

import com.robindrew.common.util.Threads;
import com.robindrew.trading.IInstrument;
import com.robindrew.trading.igindex.IgInstrument;
import com.robindrew.trading.igindex.platform.rest.IIgRestService;
import com.robindrew.trading.igindex.platform.rest.IgRestService;
import com.robindrew.trading.igindex.platform.streaming.subscription.charttick.ChartTickPriceStream;
import com.robindrew.trading.platform.ITradingPlatform;
import com.robindrew.trading.platform.streaming.IStreamingService;
import com.robindrew.trading.price.candle.IPriceCandle;
import com.robindrew.trading.price.precision.PricePrecision;
import com.robindrew.trading.strategy.LatestPriceTradingStrategy;

public class StreamingPriceStrategyTest {

	@Test
	public void testStreamingPriceStrategy() {

		IgInstrument instrument = IgInstrument.SPOT_USD_JPY;

		String apiKey = "9d07a6a968efc5721f6787ff206d463d2676b095";
		String username = "robindrew2002";
		String password = "Lvigux123";

		IgCredentials credentials = new IgCredentials(apiKey, username, password);
		IgEnvironment environment = IgEnvironment.DEMO;
		IgSession session = new IgSession(credentials, environment);
		IIgRestService rest = new IgRestService(session);
		rest.login();

		IgTradingPlatform platform = new IgTradingPlatform(rest);
		
		TestTradingStrategy strategy = new TestTradingStrategy(platform, instrument);
		strategy.start();

		// Create the underlying stream
		ChartTickPriceStream priceStream = new ChartTickPriceStream(instrument, new PricePrecision(2, 900, 90000));
		priceStream.start();

		// Register the stream to make it available through the platform
		IStreamingService streamingService = platform.getStreamingService();
		streamingService.register(priceStream);

		// Register all the sinks
		priceStream.register(strategy);
		
		streamingService.connect();
		
		Threads.sleepForever();
	}

	class TestTradingStrategy extends LatestPriceTradingStrategy {

		public TestTradingStrategy(ITradingPlatform platform, IInstrument instrument) {
			super("TestTradingStrategy", platform, instrument);
		}

		@Override
		public void handleLatestCandle(IPriceCandle candle) {
		}

	}
}

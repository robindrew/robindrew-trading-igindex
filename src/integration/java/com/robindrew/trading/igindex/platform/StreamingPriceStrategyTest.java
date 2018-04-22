package com.robindrew.trading.igindex.platform;

import org.junit.Test;

import com.robindrew.common.util.Threads;
import com.robindrew.trading.IInstrument;
import com.robindrew.trading.igindex.IIgInstrument;
import com.robindrew.trading.igindex.IgInstrument;
import com.robindrew.trading.igindex.platform.rest.IIgRestService;
import com.robindrew.trading.igindex.platform.rest.IgRestService;
import com.robindrew.trading.igindex.platform.streaming.IgStreamingService;
import com.robindrew.trading.log.ITransactionLog;
import com.robindrew.trading.log.StubTransactionLog;
import com.robindrew.trading.platform.streaming.IInstrumentPriceStream;
import com.robindrew.trading.price.candle.IPriceCandle;
import com.robindrew.trading.strategy.LatestPriceTradingStrategy;

public class StreamingPriceStrategyTest {

	@Test
	public void testStreamingPriceStrategy() {

		IgInstrument instrument = IgInstrument.SPOT_USD_JPY;

		String apiKey = System.getProperty("apiKey");
		String username = System.getProperty("username");
		String password = System.getProperty("password");

		IgCredentials credentials = new IgCredentials(apiKey, username, password);
		IgEnvironment environment = IgEnvironment.DEMO;
		IgSession session = new IgSession(credentials, environment);
		ITransactionLog log = new StubTransactionLog();
		IIgRestService rest = new IgRestService(session, log);
		rest.login();

		IgTradingPlatform platform = new IgTradingPlatform(rest);

		TestTradingStrategy strategy = new TestTradingStrategy(platform, instrument);
		strategy.start();

		// Register the stream to make it available through the platform
		IgStreamingService streaming = platform.getStreamingService();
		IInstrumentPriceStream<IIgInstrument> priceStream = streaming.getPriceStream(instrument);

		// Register all the sinks
		priceStream.register(strategy);

		// Connect!
		streaming.connect();

		Threads.sleepForever();
	}

	class TestTradingStrategy extends LatestPriceTradingStrategy<IIgInstrument> {

		public TestTradingStrategy(IgTradingPlatform platform, IInstrument instrument) {
			super("TestTradingStrategy", platform, instrument);
		}

		@Override
		public void handleLatestCandle(IPriceCandle candle) {
		}

	}
}

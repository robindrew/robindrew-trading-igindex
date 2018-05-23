package com.robindrew.trading.igindex.platform;

import org.junit.Test;

import com.robindrew.common.util.Threads;
import com.robindrew.trading.IInstrument;
import com.robindrew.trading.igindex.IIgIndexInstrument;
import com.robindrew.trading.igindex.IgIndexInstrument;
import com.robindrew.trading.igindex.platform.rest.IIgIndexRestService;
import com.robindrew.trading.igindex.platform.rest.IgIndexRestService;
import com.robindrew.trading.igindex.platform.streaming.IIgIndexStreamingService;
import com.robindrew.trading.log.ITransactionLog;
import com.robindrew.trading.log.StubTransactionLog;
import com.robindrew.trading.platform.streaming.IInstrumentPriceStream;
import com.robindrew.trading.price.candle.IPriceCandle;
import com.robindrew.trading.strategy.LatestPriceTradingStrategy;

public class StreamingPriceStrategyTest {

	@Test
	public void testStreamingPriceStrategy() {

		IgIndexInstrument instrument = IgIndexInstrument.SPOT_USD_JPY;

		String apiKey = System.getProperty("apiKey");
		String username = System.getProperty("username");
		String password = System.getProperty("password");

		IgIndexCredentials credentials = new IgIndexCredentials(apiKey, username, password);
		IgIndexEnvironment environment = IgIndexEnvironment.DEMO;
		IgIndexSession session = new IgIndexSession(credentials, environment);
		ITransactionLog log = new StubTransactionLog();
		IIgIndexRestService rest = new IgIndexRestService(session, log);
		rest.login();

		IgIndexTradingPlatform platform = new IgIndexTradingPlatform(rest);

		TestTradingStrategy strategy = new TestTradingStrategy(platform, instrument);
		strategy.start();

		// Register the stream to make it available through the platform
		IIgIndexStreamingService streaming = platform.getStreamingService();
		IInstrumentPriceStream<IIgIndexInstrument> priceStream = streaming.getPriceStream(instrument);

		// Register all the sinks
		priceStream.register(strategy);

		// Connect!
		streaming.connect();

		Threads.sleepForever();
	}

	class TestTradingStrategy extends LatestPriceTradingStrategy<IIgIndexInstrument> {

		public TestTradingStrategy(IgIndexTradingPlatform platform, IInstrument instrument) {
			super("TestTradingStrategy", platform, instrument);
		}

		@Override
		public void handleLatestCandle(IPriceCandle candle) {
		}

	}
}

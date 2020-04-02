package com.robindrew.trading.igindex.platform.streaming;

import static com.robindrew.trading.provider.TradingProvider.IGINDEX;

import java.util.concurrent.atomic.AtomicReference;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lightstreamer.ls_client.ConnectionInfo;
import com.lightstreamer.ls_client.ConnectionListener;
import com.lightstreamer.ls_client.PushConnException;
import com.lightstreamer.ls_client.PushServerException;
import com.robindrew.common.util.Check;
import com.robindrew.common.util.Java;
import com.robindrew.common.util.Quietly;
import com.robindrew.trading.igindex.IIgIndexInstrument;
import com.robindrew.trading.igindex.platform.IIgIndexSession;
import com.robindrew.trading.igindex.platform.rest.IIgIndexRestService;
import com.robindrew.trading.igindex.platform.rest.executor.getmarkets.response.Markets;
import com.robindrew.trading.igindex.platform.rest.executor.getmarkets.response.Snapshot;
import com.robindrew.trading.igindex.platform.streaming.lightstreamer.LighstreamerConnection;
import com.robindrew.trading.igindex.platform.streaming.subscription.charttick.ChartTickPriceStream;
import com.robindrew.trading.platform.streaming.AbstractStreamingService;
import com.robindrew.trading.platform.streaming.IInstrumentPriceStream;
import com.robindrew.trading.price.candle.tick.ITickPriceCandle;
import com.robindrew.trading.price.candle.tick.TickPriceCandle;
import com.robindrew.trading.price.decimal.Decimal;
import com.robindrew.trading.price.decimal.IDecimal;

public class IgIndexStreamingService extends AbstractStreamingService<IIgIndexInstrument> implements IIgIndexStreamingService {

	private static final Logger log = LoggerFactory.getLogger(IgIndexStreamingService.class);

	private final IIgIndexRestService rest;
	private final AtomicReference<LighstreamerConnection> serviceConnection = new AtomicReference<>();

	public IgIndexStreamingService(IIgIndexRestService rest) {
		super(IGINDEX);
		this.rest = Check.notNull("rest", rest);
	}

	@Override
	public boolean isConnected() {
		return serviceConnection.get() != null;
	}

	@Override
	public boolean subscribe(IIgIndexInstrument instrument) {
		if (isSubscribed(instrument)) {
			return true;
		}

		// Initialise by getting the latest price
		ITickPriceCandle candle = getLatestPrice(instrument);

		// Create the underlying stream
		ChartTickPriceStream stream = new ChartTickPriceStream(instrument);
		stream.getPrice().update(candle);

		registerStream(stream);
		stream.start();

		// Subscribe
		LighstreamerConnection connection = serviceConnection.get();
		if (connection != null) {
			connection.subscribe(stream);
		}
		return true;
	}

	private ITickPriceCandle getLatestPrice(IIgIndexInstrument instrument) {
		int decimalPlaces = instrument.getPrecision().getDecimalPlaces();
		Markets markets = rest.getMarkets(instrument.getEpic(), true);
		Snapshot snapshot = markets.getSnapshot();
		IDecimal bid = new Decimal(snapshot.getBid(), decimalPlaces);
		IDecimal ask = new Decimal(snapshot.getOffer(), decimalPlaces);
		long timestamp = System.currentTimeMillis();
		return new TickPriceCandle(bid.getValue(), ask.getValue(), timestamp, decimalPlaces);
	}

	@Override
	public boolean unsubscribe(IIgIndexInstrument instrument) {
		if (!isSubscribed(instrument)) {
			return false;
		}

		IInstrumentPriceStream<IIgIndexInstrument> stream = getPriceStream(instrument);
		super.unregisterStream(stream.getInstrument());

		LighstreamerConnection connection = serviceConnection.get();
		if (connection != null) {
			connection.unsubscribe(stream);
		}
		return true;
	}

	@Override
	public synchronized void close() {

		// Close Streams
		super.close();

		// Close Connection
		LighstreamerConnection connection = serviceConnection.get();
		if (connection != null) {
			Quietly.close(connection);
			serviceConnection.set(null);
		}
	}

	@Override
	public synchronized void connect() {
		try {

			// Connect
			log.info("Connecting ...");
			IIgIndexSession session = rest.getSession();
			LighstreamerConnection connection = new LighstreamerConnection(session);
			connection.connect(new Listener(connection.getInfo()));
			serviceConnection.set(connection);

			// Subscribe existing subscriptions
			for (IInstrumentPriceStream<IIgIndexInstrument> stream : getPriceStreams()) {
				connection.subscribe(stream);
			}

		} catch (Exception e) {
			log.warn("Failed to connect client", e);
			serviceConnection.set(null);
			throw Java.propagate(e);
		}
	}

	private class Listener implements ConnectionListener {

		private final ConnectionInfo info;

		public Listener(ConnectionInfo info) {
			this.info = info;
		}

		@Override
		public void onConnectionEstablished() {
			log.info("Connection Established: " + info.user + "@" + info.pushServerUrl);
		}

		@Override
		public void onSessionStarted(boolean isPolling) {
			log.info("Session Started (" + (isPolling ? "Polling" : "Non-Polling") + ")");
		}

		@Override
		public void onNewBytes(long bytes) {
			log.trace("onNewBytes(" + bytes + ")");
		}

		@Override
		public void onDataError(PushServerException e) {
			log.warn("Data Error", e);
		}

		@Override
		public void onActivityWarning(boolean warningOn) {
			log.warn("Activity Warning (" + (warningOn ? "Started" : "Finished") + ")");
		}

		@Override
		public void onClose() {
			log.info("Connection Closed");
			close();
		}

		@Override
		public void onEnd(int cause) {
			log.warn("onEnd(" + cause + ")");
		}

		@Override
		public void onFailure(PushServerException e) {
			log.error("onFailure()", e);
		}

		@Override
		public void onFailure(PushConnException e) {
			log.error("onFailure()", e);
		}
	}

}

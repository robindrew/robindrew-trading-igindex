package com.robindrew.trading.igindex.platform.streaming;

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
import com.robindrew.trading.igindex.IIgInstrument;
import com.robindrew.trading.igindex.platform.IgSession;
import com.robindrew.trading.igindex.platform.rest.IIgRestService;
import com.robindrew.trading.igindex.platform.rest.executor.getmarkets.Markets;
import com.robindrew.trading.igindex.platform.rest.executor.getmarkets.Snapshot;
import com.robindrew.trading.igindex.platform.streaming.subscription.charttick.ChartTickPriceStream;
import com.robindrew.trading.platform.streaming.IInstrumentPriceStream;
import com.robindrew.trading.platform.streaming.StreamingService;
import com.robindrew.trading.price.candle.ITickPriceCandle;
import com.robindrew.trading.price.candle.TickPriceCandle;
import com.robindrew.trading.price.decimal.Decimal;
import com.robindrew.trading.price.decimal.IDecimal;

public class IgStreamingService extends StreamingService<IIgInstrument> implements IIgStreamingService {

	private static final Logger log = LoggerFactory.getLogger(IgStreamingService.class);

	private final IIgRestService rest;
	private final AtomicReference<IgStreamingServiceConnection> serviceConnection = new AtomicReference<>();

	public IgStreamingService(IIgRestService rest) {
		this.rest = Check.notNull("rest", rest);
	}

	@Override
	public boolean isConnected() {
		return serviceConnection.get() != null;
	}

	@Override
	public boolean subscribe(IIgInstrument instrument) {
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
		IgStreamingServiceConnection connection = serviceConnection.get();
		if (connection != null) {
			connection.subscribe(stream);
		}
		return true;
	}

	private ITickPriceCandle getLatestPrice(IIgInstrument instrument) {
		int decimalPlaces = instrument.getPrecision().getDecimalPlaces();
		Markets markets = rest.getMarkets(instrument.getEpic(), true);
		Snapshot snapshot = markets.getSnapshot();
		IDecimal bid = new Decimal(snapshot.getBid(), decimalPlaces);
		IDecimal ask = new Decimal(snapshot.getOffer(), decimalPlaces);
		long timestamp = System.currentTimeMillis();
		return new TickPriceCandle(bid.getValue(), ask.getValue(), timestamp, decimalPlaces);
	}

	@Override
	public boolean unsubscribe(IIgInstrument instrument) {
		Check.notNull("instrument", instrument);

		IInstrumentPriceStream<IIgInstrument> stream = getPriceStream(instrument);
		super.unregisterStream(stream.getInstrument());

		IgStreamingServiceConnection connection = serviceConnection.get();
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
		IgStreamingServiceConnection connection = serviceConnection.get();
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
			IgSession session = rest.getSession();
			IgStreamingServiceConnection connection = new IgStreamingServiceConnection(session);
			connection.connect(new Listener(connection.getInfo()));
			serviceConnection.set(connection);

			// Subscribe existing subscriptions
			for (IInstrumentPriceStream<IIgInstrument> stream : getPriceStreams()) {
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

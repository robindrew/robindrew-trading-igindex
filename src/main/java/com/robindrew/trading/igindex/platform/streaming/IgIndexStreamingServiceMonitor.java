package com.robindrew.trading.igindex.platform.streaming;

import static java.util.concurrent.TimeUnit.MINUTES;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.robindrew.common.concurrent.LoopingRunnableThread;
import com.robindrew.common.date.UnitTime;
import com.robindrew.common.util.Check;
import com.robindrew.trading.igindex.platform.IIgIndexTradingPlatform;
import com.robindrew.trading.igindex.platform.rest.IIgIndexRestService;

public class IgIndexStreamingServiceMonitor implements AutoCloseable {

	private static final Logger log = LoggerFactory.getLogger(IgIndexStreamingServiceMonitor.class);

	private final IIgIndexTradingPlatform platform;
	private final LoopingRunnableThread thread;

	public IgIndexStreamingServiceMonitor(IIgIndexTradingPlatform platform) {
		this.platform = Check.notNull("platform", platform);
		this.thread = new LoopingRunnableThread("IgStreamingServiceMonitor", new Reconnector());
		this.thread.setPause(new UnitTime(1, MINUTES));
	}

	public void start() {
		thread.start();
	}

	@Override
	public void close() {
		thread.close();
	}

	private class Reconnector implements Runnable {

		@Override
		public void run() {
			try {
				IIgIndexStreamingService streaming = platform.getStreamingService();
				if (!streaming.isConnected()) {
					IIgIndexRestService rest = platform.getRestService();

					// Login
					rest.login();

					// Reconnect
					streaming.connect();
				}
			} catch (Exception e) {
				log.warn("Failed to reconnect", e);
			}
		}
	}
}

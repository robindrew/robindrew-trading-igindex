package com.robindrew.trading.igindex.platform.streaming;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lightstreamer.ls_client.ConnectionListener;
import com.lightstreamer.ls_client.PushConnException;
import com.lightstreamer.ls_client.PushServerException;

public class LoggingConnectionListener implements ConnectionListener {

	private static final Logger log = LoggerFactory.getLogger(LoggingConnectionListener.class);

	@Override
	public void onConnectionEstablished() {
		log.debug("onConnectionEstablished()");
	}

	@Override
	public void onSessionStarted(boolean isPolling) {
		log.info("onSessionStarted(" + isPolling + ")");
	}

	@Override
	public void onNewBytes(long bytes) {
		log.debug("onNewBytes(" + bytes + ")");
	}

	@Override
	public void onDataError(PushServerException e) {
		log.error("onDataError()", e);
	}

	@Override
	public void onActivityWarning(boolean warningOn) {
		log.warn("onActivityWarning(" + warningOn + ")");
	}

	@Override
	public void onClose() {
		log.info("onClose()");
	}

	@Override
	public void onEnd(int cause) {
		log.warn("onEnd(" + cause + ")");
	}

	@Override
	public void onFailure(PushServerException e) {
		log.error("onFailure()", e);
		throw new RuntimeException("onFailure " + e);
	}

	@Override
	public void onFailure(PushConnException e) {
		log.error("onFailure()", e);
		throw new RuntimeException("onFailure " + e);
	}

}

package com.robindrew.trading.igindex.platform.streaming.lightstreamer;

import com.robindrew.trading.TradingException;

public class LightstreamerException extends TradingException {

	private static final long serialVersionUID = -8603125223776495530L;

	public LightstreamerException(String message) {
		super(message);
	}

	public LightstreamerException(Throwable cause) {
		super(cause);
	}

	public LightstreamerException(String message, Throwable cause) {
		super(message, cause);
	}

}

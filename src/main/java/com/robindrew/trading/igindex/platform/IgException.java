package com.robindrew.trading.igindex.platform;

import com.robindrew.trading.TradingException;

public class IgException extends TradingException {

	private static final long serialVersionUID = -6849116617539789277L;

	public IgException(String message) {
		super(message);
	}

	public IgException(Throwable cause) {
		super(cause);
	}

	public IgException(String message, Throwable cause) {
		super(message, cause);
	}

}

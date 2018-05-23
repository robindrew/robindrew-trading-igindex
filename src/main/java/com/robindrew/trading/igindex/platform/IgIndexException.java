package com.robindrew.trading.igindex.platform;

import com.robindrew.trading.TradingException;

public class IgIndexException extends TradingException {

	private static final long serialVersionUID = -6849116617539789277L;

	public IgIndexException(String message) {
		super(message);
	}

	public IgIndexException(Throwable cause) {
		super(cause);
	}

	public IgIndexException(String message, Throwable cause) {
		super(message, cause);
	}

}

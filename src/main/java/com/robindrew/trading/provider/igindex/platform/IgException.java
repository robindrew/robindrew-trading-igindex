package com.robindrew.trading.provider.igindex.platform;

public class IgException extends RuntimeException {

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

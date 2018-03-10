package com.robindrew.trading.provider.igindex.platform.rest.executor;

public class ServiceNotAvailableException extends RuntimeException {

	private static final long serialVersionUID = 9005067175105711088L;

	public ServiceNotAvailableException(String message) {
		super(message);
	}

}

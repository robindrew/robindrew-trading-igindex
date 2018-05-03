package com.robindrew.trading.igindex.platform;

public enum IgEnvironment {

	/** Production Environment. */
	PROD("https://api.ig.com/gateway/deal"),
	/** Demo Environment. */
	DEMO("https://demo-api.ig.com/gateway/deal");

	private final String restUrl;

	private IgEnvironment(String restUrl) {
		this.restUrl = restUrl;
	}

	public String getRestUrl() {
		return restUrl;
	}
}

package com.robindrew.trading.igindex.platform;

public enum IgIndexEnvironment {

	/** Production Environment. */
	PROD("https://api.ig.com/gateway/deal"),
	/** Demo Environment. */
	DEMO("https://demo-api.ig.com/gateway/deal");

	private final String restUrl;

	private IgIndexEnvironment(String restUrl) {
		this.restUrl = restUrl;
	}

	public String getRestUrl() {
		return restUrl;
	}
}

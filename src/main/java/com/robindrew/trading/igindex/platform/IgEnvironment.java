package com.robindrew.trading.igindex.platform;

public enum IgEnvironment {

	/** Production Environment. */
	PROD("https://api.ig.com/gateway/deal"),
	/** Demo Environment. */
	DEMO("https://demo-api.ig.com/gateway/deal");

	private final String url;

	private IgEnvironment(String url) {
		this.url = url;
	}

	public String getUrl() {
		return url;
	}
}

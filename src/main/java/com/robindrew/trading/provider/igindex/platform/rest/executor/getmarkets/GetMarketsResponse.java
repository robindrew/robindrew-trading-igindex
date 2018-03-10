package com.robindrew.trading.provider.igindex.platform.rest.executor.getmarkets;

import com.robindrew.common.json.IJson;

public class GetMarketsResponse {

	private final Markets markets;

	public GetMarketsResponse(IJson object) {
		markets = new Markets(object);
	}

	public Markets getMarkets() {
		return markets;
	}
}

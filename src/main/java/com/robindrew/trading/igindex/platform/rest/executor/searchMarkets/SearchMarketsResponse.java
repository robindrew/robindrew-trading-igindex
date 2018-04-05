package com.robindrew.trading.igindex.platform.rest.executor.searchMarkets;

import com.robindrew.common.json.IJson;
import com.robindrew.trading.igindex.platform.rest.executor.getmarketnavigation.Markets;

public class SearchMarketsResponse {

	private final Markets markets;

	public SearchMarketsResponse(IJson object) {
		markets = new Markets(object.getObjectList("markets"));
	}

	public Markets getMarkets() {
		return markets;
	}
}

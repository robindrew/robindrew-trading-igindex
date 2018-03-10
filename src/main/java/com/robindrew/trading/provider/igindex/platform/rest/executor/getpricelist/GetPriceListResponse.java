package com.robindrew.trading.provider.igindex.platform.rest.executor.getpricelist;

import com.robindrew.common.json.IJson;

public class GetPriceListResponse {

	private final PriceList priceList;

	public GetPriceListResponse(IJson object) {
		priceList = new PriceList(object);
	}

	public PriceList getPriceList() {
		return priceList;
	}

}

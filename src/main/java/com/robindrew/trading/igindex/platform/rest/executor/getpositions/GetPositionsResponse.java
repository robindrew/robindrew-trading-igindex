package com.robindrew.trading.igindex.platform.rest.executor.getpositions;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.ImmutableList;
import com.robindrew.common.json.IJson;

public class GetPositionsResponse {

	private final List<MarketPosition> marketPositions = new ArrayList<>();

	public GetPositionsResponse(IJson object) {
		for (IJson element : object.getObjectList("positions")) {
			marketPositions.add(new MarketPosition(element));
		}
	}

	public List<MarketPosition> getMarketPositions() {
		return ImmutableList.copyOf(marketPositions);
	}

}

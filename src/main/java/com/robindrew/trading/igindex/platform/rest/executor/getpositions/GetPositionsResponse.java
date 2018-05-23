package com.robindrew.trading.igindex.platform.rest.executor.getpositions;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.ImmutableList;
import com.robindrew.trading.igindex.platform.rest.executor.IgIndexJsonObject;

public class GetPositionsResponse extends IgIndexJsonObject {

	private List<MarketPosition> positions = new ArrayList<>();

	public List<MarketPosition> getMarketPositions() {
		return ImmutableList.copyOf(positions);
	}

}

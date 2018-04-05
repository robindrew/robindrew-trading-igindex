package com.robindrew.trading.provider.igindex.platform.rest.executor.closeposition;

import com.robindrew.common.json.IJson;

public class ClosePositionResponse {

	private final String dealReference;

	public ClosePositionResponse(IJson object) {
		dealReference = object.get("dealReference");
	}

	public String getDealReference() {
		return dealReference;
	}

}

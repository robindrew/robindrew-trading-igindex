package com.robindrew.trading.provider.igindex.platform.rest.executor.openposition;

import com.robindrew.common.json.IJson;

public class OpenPositionResponse {

	private final String dealReference;

	public OpenPositionResponse(IJson object) {
		dealReference = object.get("dealReference");
	}

	public String getDealReference() {
		return dealReference;
	}

}

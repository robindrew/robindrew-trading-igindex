package com.robindrew.trading.igindex.platform.rest.executor;

import com.robindrew.trading.igindex.platform.rest.IgRestJson;

public class IgJsonObject {

	@Override
	public String toString() {
		return IgRestJson.toJson(this);
	}

}

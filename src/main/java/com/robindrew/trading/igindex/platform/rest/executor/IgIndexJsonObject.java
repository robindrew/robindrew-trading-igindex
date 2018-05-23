package com.robindrew.trading.igindex.platform.rest.executor;

import com.robindrew.trading.igindex.platform.rest.IgIndexRestJson;

public class IgIndexJsonObject {

	@Override
	public String toString() {
		return IgIndexRestJson.toJson(this);
	}

}

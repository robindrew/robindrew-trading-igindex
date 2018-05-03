package com.robindrew.trading.igindex.platform.rest.executor;

import com.google.gson.GsonBuilder;

public class IgJsonObject {

	@Override
	public String toString() {
		return new GsonBuilder().serializeNulls().create().toJson(this);
	}

}

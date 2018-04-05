package com.robindrew.trading.igindex.platform.rest.executor;

import com.google.gson.Gson;

public class IgJsonObject {

	@Override
	public String toString() {
		return new Gson().toJson(this);
	}

}

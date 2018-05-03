package com.robindrew.trading.igindex.platform.rest.executor.getactivity.response;

import com.robindrew.trading.igindex.platform.rest.executor.IgJsonObject;

public class Action extends IgJsonObject {

	private String actionType;
	private String affectedDealId;

	public String getActionType() {
		return actionType;
	}

	public String getAffectedDealId() {
		return affectedDealId;
	}

}

package com.robindrew.trading.igindex.platform.rest.executor.getactivity.response;

import com.robindrew.trading.igindex.platform.rest.executor.IgIndexJsonObject;

public class Action extends IgIndexJsonObject {

	private String actionType;
	private String affectedDealId;

	public String getActionType() {
		return actionType;
	}

	public String getAffectedDealId() {
		return affectedDealId;
	}

}

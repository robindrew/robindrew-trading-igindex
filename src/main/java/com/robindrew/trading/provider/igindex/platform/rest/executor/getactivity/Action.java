package com.robindrew.trading.provider.igindex.platform.rest.executor.getactivity;

import java.util.ArrayList;
import java.util.List;

import com.robindrew.common.json.IJson;
import com.robindrew.trading.provider.igindex.platform.rest.executor.IgJsonObject;

public class Action extends IgJsonObject {

	public static List<Action> toList(List<IJson> list) {
		List<Action> actions = new ArrayList<>();
		for (IJson element : list) {
			actions.add(new Action(element));
		}
		return actions;
	}

	private final String actionType;
	private final String affectedDealId;

	public Action(IJson object) {
		this.actionType = object.get("actionType");
		this.affectedDealId = object.get("affectedDealId");
	}

	public String getActionType() {
		return actionType;
	}

	public String getAffectedDealId() {
		return affectedDealId;
	}

}

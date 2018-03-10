package com.robindrew.trading.provider.igindex.platform.rest.executor.getactivity;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.ImmutableList;
import com.robindrew.common.json.IJson;
import com.robindrew.trading.provider.igindex.platform.rest.executor.IgJsonObject;

public class GetActivityResponse extends IgJsonObject {

	private final List<Activity> activities = new ArrayList<>();

	public GetActivityResponse(IJson object) {
		for (IJson element : object.getObjectList("activities")) {
			activities.add(new Activity(element));
		}
	}

	public List<Activity> getActivities() {
		return ImmutableList.copyOf(activities);
	}

}

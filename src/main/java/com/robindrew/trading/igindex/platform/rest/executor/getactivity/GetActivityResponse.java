package com.robindrew.trading.igindex.platform.rest.executor.getactivity;

import java.util.ArrayList;
import java.util.List;

import com.robindrew.trading.igindex.platform.rest.executor.IgIndexJsonObject;
import com.robindrew.trading.igindex.platform.rest.executor.getactivity.response.Activity;
import com.robindrew.trading.igindex.platform.rest.executor.getactivity.response.ActivityList;
import com.robindrew.trading.igindex.platform.rest.executor.getactivity.response.Metadata;

public class GetActivityResponse extends IgIndexJsonObject {

	private List<Activity> activities = new ArrayList<>();
	private Metadata metadata;

	public ActivityList getActivities() {
		return new ActivityList(activities);
	}

	public Metadata getMetadata() {
		return metadata;
	}

}

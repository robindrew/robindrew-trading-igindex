package com.robindrew.trading.igindex.platform.rest.executor.getactivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.google.common.collect.ImmutableList;

public class ActivityList implements Iterable<Activity> {

	private final List<Activity> activityList;

	public ActivityList(List<Activity> list) {
		this.activityList = ImmutableList.copyOf(list);
	}

	public ActivityList() {
		this.activityList = Collections.emptyList();
	}

	public boolean isEmpty() {
		return activityList.isEmpty();
	}

	public List<Activity> toList() {
		return activityList;
	}

	public ActivityList withDealReference(String dealReference) {
		List<Activity> list = new ArrayList<>();

		for (Activity activity : toList()) {
			Details details = activity.getDetails();
			if (details != null && dealReference.equals(details.getDealReference())) {
				list.add(activity);
			}
		}

		return new ActivityList(list);
	}

	public ActivityList closedPositions() {
		List<Activity> list = new ArrayList<>();

		for (Activity activity : toList()) {
			Details details = activity.getDetails();
			if (details == null) {
				continue;
			}
			for (Action action : details.getActions()) {
				if ("POSITION_CLOSED".equals(action.getActionType())) {
					list.add(activity);
					break;
				}
			}
		}

		return new ActivityList(list);
	}

	@Override
	public Iterator<Activity> iterator() {
		return activityList.iterator();
	}

}

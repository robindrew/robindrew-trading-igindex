package com.robindrew.trading.provider.igindex.platform.rest.executor.getactivity;

import java.time.LocalDateTime;

import com.robindrew.common.json.IJson;
import com.robindrew.trading.provider.igindex.platform.rest.executor.IgJsonObject;

public class Activity extends IgJsonObject {

	private final String date;
	private final String epic;
	private final String period;
	private final String dealId;
	private final String channel;
	private final String type;
	private final String status;
	private final String description;
	private final Details details;

	public Activity(IJson object) {
		date = object.get("date");
		epic = object.get("epic");
		period = object.get("period");
		dealId = object.get("dealId");
		channel = object.get("channel");
		type = object.get("type");
		status = object.get("status");
		description = object.get("description");
		details = object.contains("details") ? new Details(object.getObject("details")) : null;
	}

	public String getDate() {
		return date;
	}

	public LocalDateTime getActivityDate() {
		return LocalDateTime.parse(date);
	}

	public String getActivityDateFormatted() {
		return getActivityDate().toString().replace('T', ' ');
	}

	public String getEpic() {
		return epic;
	}

	public String getPeriod() {
		return period;
	}

	public String getDealId() {
		return dealId;
	}

	public String getChannel() {
		return channel;
	}

	public String getType() {
		return type;
	}

	public String getStatus() {
		return status;
	}

	public String getDescription() {
		return description;
	}

	public Details getDetails() {
		return details;
	}

}

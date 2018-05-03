package com.robindrew.trading.igindex.platform.rest.executor.getactivity.response;

import java.time.LocalDateTime;

import com.robindrew.trading.igindex.platform.rest.executor.IgJsonObject;

public class Activity extends IgJsonObject {

	private String date;
	private String epic;
	private String period;
	private String dealId;
	private String channel;
	private String type;
	private String status;
	private String description;
	private Details details;

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

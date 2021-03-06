package com.robindrew.trading.igindex.platform.rest.executor.getactivity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;

import com.robindrew.common.date.Dates;
import com.robindrew.trading.igindex.platform.rest.IIgIndexRestService;
import com.robindrew.trading.igindex.platform.rest.executor.IgIndexRestExecutor;

public class GetActivityExecutor extends IgIndexRestExecutor<GetActivityResponse> {

	private final LocalDateTime date;

	public GetActivityExecutor(IIgIndexRestService service, LocalDate date) {
		this(service, Dates.toLocalDateTime(date));
	}

	public GetActivityExecutor(IIgIndexRestService service, LocalDateTime date) {
		super(service);
		this.date = date;
	}

	@Override
	public int getRequestVersion() {
		return 3;
	}

	@Override
	public HttpUriRequest createRequest() {
		HttpGet request = new HttpGet(getUrl("/history/activity?detailed=true&pageSize=500&from=" + date));
		addStandardHeaders(request);
		return request;
	}

	@Override
	protected Class<GetActivityResponse> getResponseType() {
		return GetActivityResponse.class;
	}

}

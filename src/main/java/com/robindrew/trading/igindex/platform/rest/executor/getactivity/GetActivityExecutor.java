package com.robindrew.trading.igindex.platform.rest.executor.getactivity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;

import com.robindrew.common.date.Dates;
import com.robindrew.common.json.IJson;
import com.robindrew.trading.igindex.platform.rest.IIgRestService;
import com.robindrew.trading.igindex.platform.rest.executor.HttpJsonRestExecutor;

public class GetActivityExecutor extends HttpJsonRestExecutor<GetActivityResponse> {

	private final LocalDateTime date;

	public GetActivityExecutor(IIgRestService service, LocalDate date) {
		this(service, Dates.toLocalDateTime(date));
	}

	public GetActivityExecutor(IIgRestService service, LocalDateTime date) {
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
		addHeaders(request);
		return request;
	}

	@Override
	public GetActivityResponse createResponse(IJson json) {
		return new GetActivityResponse(json);
	}

}

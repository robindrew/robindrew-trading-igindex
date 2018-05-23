package com.robindrew.trading.igindex.platform.rest.executor.getpositions;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;

import com.robindrew.trading.igindex.platform.rest.IIgIndexRestService;
import com.robindrew.trading.igindex.platform.rest.executor.IgIndexRestExecutor;

public class GetPositionsExecutor extends IgIndexRestExecutor<GetPositionsResponse> {

	public GetPositionsExecutor(IIgIndexRestService service) {
		super(service);
	}

	@Override
	public int getRequestVersion() {
		return 2;
	}

	@Override
	public HttpUriRequest createRequest() {
		HttpGet request = new HttpGet(getUrl("/positions"));
		addStandardHeaders(request);
		return request;
	}

	@Override
	protected Class<GetPositionsResponse> getResponseType() {
		return GetPositionsResponse.class;
	}

}

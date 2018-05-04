package com.robindrew.trading.igindex.platform.rest.executor.getpositions;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;

import com.robindrew.trading.igindex.platform.rest.IIgRestService;
import com.robindrew.trading.igindex.platform.rest.executor.IgRestExecutor;

public class GetPositionsExecutor extends IgRestExecutor<GetPositionsResponse> {

	public GetPositionsExecutor(IIgRestService service) {
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

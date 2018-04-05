package com.robindrew.trading.igindex.platform.rest.executor.getpositions;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;

import com.robindrew.common.json.IJson;
import com.robindrew.trading.igindex.platform.rest.IIgRestService;
import com.robindrew.trading.igindex.platform.rest.executor.HttpJsonRestExecutor;

public class GetPositionsExecutor extends HttpJsonRestExecutor<GetPositionsResponse> {

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
		addHeaders(request);
		return request;
	}

	@Override
	public GetPositionsResponse createResponse(IJson json) {
		return new GetPositionsResponse(json);
	}

}

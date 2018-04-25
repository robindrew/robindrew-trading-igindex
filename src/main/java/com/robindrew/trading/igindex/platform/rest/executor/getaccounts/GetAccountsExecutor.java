package com.robindrew.trading.igindex.platform.rest.executor.getaccounts;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;

import com.robindrew.common.json.IJson;
import com.robindrew.trading.igindex.platform.rest.IIgRestService;
import com.robindrew.trading.igindex.platform.rest.executor.IgRestExecutor;

public class GetAccountsExecutor extends IgRestExecutor<GetAccountsResponse> {

	public GetAccountsExecutor(IIgRestService service) {
		super(service);
	}

	@Override
	public int getRequestVersion() {
		return 1;
	}

	@Override
	public HttpUriRequest createRequest() {
		HttpGet request = new HttpGet(getUrl("/accounts"));
		addHeaders(request);
		return request;
	}

	@Override
	public GetAccountsResponse createResponse(IJson json) {
		return new GetAccountsResponse(json);
	}

}

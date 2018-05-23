package com.robindrew.trading.igindex.platform.rest.executor.getaccounts;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;

import com.robindrew.trading.igindex.platform.rest.IIgIndexRestService;
import com.robindrew.trading.igindex.platform.rest.executor.IgIndexRestExecutor;

public class GetAccountsExecutor extends IgIndexRestExecutor<GetAccountsResponse> {

	public GetAccountsExecutor(IIgIndexRestService service) {
		super(service);
	}

	@Override
	public int getRequestVersion() {
		return 1;
	}

	@Override
	public HttpUriRequest createRequest() {
		HttpGet request = new HttpGet(getUrl("/accounts"));
		addStandardHeaders(request);
		return request;
	}

	@Override
	protected Class<GetAccountsResponse> getResponseType() {
		return GetAccountsResponse.class;
	}

}

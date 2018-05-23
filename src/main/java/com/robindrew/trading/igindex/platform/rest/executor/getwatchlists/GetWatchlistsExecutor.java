package com.robindrew.trading.igindex.platform.rest.executor.getwatchlists;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;

import com.robindrew.trading.igindex.platform.rest.IIgIndexRestService;
import com.robindrew.trading.igindex.platform.rest.executor.IgIndexRestExecutor;

public class GetWatchlistsExecutor extends IgIndexRestExecutor<Boolean> {

	public GetWatchlistsExecutor(IIgIndexRestService platform) {
		super(platform);
	}

	@Override
	public int getRequestVersion() {
		return 1;
	}

	@Override
	public HttpUriRequest createRequest() {
		HttpGet request = new HttpGet(getUrl("/watchlists"));
		addStandardHeaders(request);
		return request;
	}

	@Override
	protected Class<Boolean> getResponseType() {
		return Boolean.class;
	}
}

package com.robindrew.trading.igindex.platform.rest.executor.getwatchlists;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;

import com.robindrew.trading.igindex.platform.rest.IIgRestService;
import com.robindrew.trading.igindex.platform.rest.executor.IgRestExecutor;

public class GetWatchlistsExecutor extends IgRestExecutor<Boolean> {

	public GetWatchlistsExecutor(IIgRestService platform) {
		super(platform);
	}

	@Override
	public int getRequestVersion() {
		return 1;
	}

	@Override
	public HttpUriRequest createRequest() {
		HttpGet request = new HttpGet(getUrl("/watchlists"));
		addHeaders(request);
		return request;
	}

	@Override
	protected Class<Boolean> getResponseType() {
		return Boolean.class;
	}
}

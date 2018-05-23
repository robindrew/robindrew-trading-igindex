package com.robindrew.trading.igindex.platform.rest.executor.getmarketnavigation;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;

import com.robindrew.trading.igindex.platform.rest.IIgIndexRestService;
import com.robindrew.trading.igindex.platform.rest.executor.IgIndexRestExecutor;

public class GetMarketNavigationExecutor extends IgIndexRestExecutor<GetMarketNavigationResponse> {

	public static final int ROOT_ID = 0;

	private final int id;

	public GetMarketNavigationExecutor(IIgIndexRestService platform, int id) {
		super(platform);
		this.id = id;
	}

	public GetMarketNavigationExecutor(IIgIndexRestService platform) {
		this(platform, ROOT_ID);
	}

	@Override
	public int getRequestVersion() {
		return 1;
	}

	@Override
	public HttpUriRequest createRequest() {

		// HTTP
		HttpGet request = new HttpGet(getUrl("/marketnavigation" + (id == ROOT_ID ? "" : "/" + id)));
		addStandardHeaders(request);
		return request;
	}

	@Override
	protected Class<GetMarketNavigationResponse> getResponseType() {
		return GetMarketNavigationResponse.class;
	}
}

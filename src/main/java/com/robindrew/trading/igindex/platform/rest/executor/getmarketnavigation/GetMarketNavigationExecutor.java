package com.robindrew.trading.igindex.platform.rest.executor.getmarketnavigation;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;

import com.robindrew.common.json.IJson;
import com.robindrew.trading.igindex.platform.rest.IIgRestService;
import com.robindrew.trading.igindex.platform.rest.executor.HttpJsonRestExecutor;

public class GetMarketNavigationExecutor extends HttpJsonRestExecutor<GetMarketNavigationResponse> {

	public static final int ROOT_ID = 0;

	private final int id;

	public GetMarketNavigationExecutor(IIgRestService platform, int id) {
		super(platform);
		this.id = id;
	}

	public GetMarketNavigationExecutor(IIgRestService platform) {
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
		addHeaders(request);
		return request;
	}

	@Override
	public GetMarketNavigationResponse createResponse(IJson json) {
		return new GetMarketNavigationResponse(json);
	}
}

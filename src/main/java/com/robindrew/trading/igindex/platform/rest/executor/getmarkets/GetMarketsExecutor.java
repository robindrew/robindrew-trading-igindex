package com.robindrew.trading.igindex.platform.rest.executor.getmarkets;

import static com.robindrew.common.util.Check.notEmpty;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;

import com.robindrew.trading.igindex.platform.rest.IIgIndexRestService;
import com.robindrew.trading.igindex.platform.rest.executor.IgIndexRestExecutor;

public class GetMarketsExecutor extends IgIndexRestExecutor<GetMarketsResponse> {

	private final String epic;

	public GetMarketsExecutor(IIgIndexRestService service, String epic) {
		super(service);
		this.epic = notEmpty("epic", epic);
	}

	@Override
	public int getRequestVersion() {
		return 3;
	}

	@Override
	public HttpUriRequest createRequest() {
		HttpGet request = new HttpGet(getUrl("/markets/" + epic));
		addStandardHeaders(request);
		return request;
	}

	@Override
	protected Class<GetMarketsResponse> getResponseType() {
		return GetMarketsResponse.class;
	}

}

package com.robindrew.trading.igindex.platform.rest.executor.openposition;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;

import com.robindrew.trading.igindex.platform.rest.IIgIndexRestService;
import com.robindrew.trading.igindex.platform.rest.executor.IgIndexRestExecutor;

public class OpenPositionExecutor extends IgIndexRestExecutor<OpenPositionResponse> {

	private final OpenPositionRequest request;

	public OpenPositionExecutor(IIgIndexRestService service, OpenPositionRequest request) {
		super(service);
		if (request == null) {
			throw new NullPointerException("request");
		}
		this.request = request;
	}

	@Override
	public int getRequestVersion() {
		return 2;
	}

	@Override
	public HttpUriRequest createRequest() {
		HttpPost http = new HttpPost(getUrl("/positions/otc"));
		addStandardHeaders(http);
		setJsonContent(http, request);
		return http;
	}

	@Override
	protected Class<OpenPositionResponse> getResponseType() {
		return OpenPositionResponse.class;
	}

}

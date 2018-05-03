package com.robindrew.trading.igindex.platform.rest.executor.closeposition;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;

import com.robindrew.trading.igindex.platform.rest.IIgRestService;
import com.robindrew.trading.igindex.platform.rest.executor.IgRestExecutor;

public class ClosePositionExecutor extends IgRestExecutor<ClosePositionResponse> {

	private final ClosePositionRequest jsonRequest;

	public ClosePositionExecutor(IIgRestService service, ClosePositionRequest jsonRequest) {
		super(service);
		if (jsonRequest == null) {
			throw new NullPointerException("jsonRequest");
		}
		this.jsonRequest = jsonRequest;
	}

	@Override
	public int getRequestVersion() {
		return 1;
	}

	@Override
	public HttpUriRequest createRequest() {
		HttpPost request = new HttpPost(getUrl("/positions/otc"));
		addDeleteHeader(request);
		addHeaders(request);
		setJsonContent(request, jsonRequest);
		return request;
	}

	@Override
	protected Class<ClosePositionResponse> getResponseType() {
		return ClosePositionResponse.class;
	}
}

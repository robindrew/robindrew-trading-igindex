package com.robindrew.trading.igindex.platform.rest.executor.closeposition;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;

import com.robindrew.trading.igindex.platform.rest.IIgIndexRestService;
import com.robindrew.trading.igindex.platform.rest.executor.IgIndexRestExecutor;

public class ClosePositionExecutor extends IgIndexRestExecutor<ClosePositionResponse> {

	private final ClosePositionRequest jsonRequest;

	public ClosePositionExecutor(IIgIndexRestService service, ClosePositionRequest jsonRequest) {
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
		addStandardHeaders(request);
		setJsonContent(request, jsonRequest);
		return request;
	}

	@Override
	protected Class<ClosePositionResponse> getResponseType() {
		return ClosePositionResponse.class;
	}
}

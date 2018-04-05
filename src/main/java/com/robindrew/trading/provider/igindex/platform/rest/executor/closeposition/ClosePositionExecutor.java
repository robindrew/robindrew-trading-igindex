package com.robindrew.trading.provider.igindex.platform.rest.executor.closeposition;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;

import com.robindrew.common.json.IJson;
import com.robindrew.trading.provider.igindex.platform.rest.IIgRestService;
import com.robindrew.trading.provider.igindex.platform.rest.executor.HttpJsonRestExecutor;

public class ClosePositionExecutor extends HttpJsonRestExecutor<ClosePositionResponse> {

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
		setRequestContent(request, jsonRequest);
		return request;
	}

	@Override
	public ClosePositionResponse createResponse(IJson json) {
		return new ClosePositionResponse(json);
	}
}

package com.robindrew.trading.igindex.platform.rest.executor.openposition;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;

import com.robindrew.common.json.IJson;
import com.robindrew.trading.igindex.platform.rest.IIgRestService;
import com.robindrew.trading.igindex.platform.rest.executor.HttpJsonRestExecutor;

public class OpenPositionExecutor extends HttpJsonRestExecutor<OpenPositionResponse> {

	private final OpenPositionRequest request;

	public OpenPositionExecutor(IIgRestService service, OpenPositionRequest request) {
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
		addHeaders(http);
		setRequestContent(http, request);
		return http;
	}

	@Override
	public OpenPositionResponse createResponse(IJson json) {
		return new OpenPositionResponse(json);
	}

}

package com.robindrew.trading.igindex.platform.rest.executor.openposition;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;

import com.robindrew.trading.igindex.platform.rest.IIgRestService;
import com.robindrew.trading.igindex.platform.rest.executor.IgRestExecutor;

public class OpenPositionExecutor extends IgRestExecutor<OpenPositionResponse> {

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
		setJsonContent(http, request);
		return http;
	}

	@Override
	protected Class<OpenPositionResponse> getResponseType() {
		return OpenPositionResponse.class;
	}

}

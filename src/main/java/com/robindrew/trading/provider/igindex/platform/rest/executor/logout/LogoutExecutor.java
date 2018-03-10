package com.robindrew.trading.provider.igindex.platform.rest.executor.logout;

import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpUriRequest;

import com.robindrew.common.json.IJson;
import com.robindrew.trading.provider.igindex.platform.rest.IIgRestService;
import com.robindrew.trading.provider.igindex.platform.rest.executor.HttpJsonRestExecutor;

public class LogoutExecutor extends HttpJsonRestExecutor<Boolean> {

	public LogoutExecutor(IIgRestService platform) {
		super(platform);
	}

	@Override
	public int getRequestVersion() {
		return 1;
	}

	@Override
	public HttpUriRequest createRequest() {

		// HTTP
		HttpDelete request = new HttpDelete("/session");
		addHeaders(request);
		return request;
	}

	@Override
	public Boolean createResponse(IJson json) {
		return true;
	}
}

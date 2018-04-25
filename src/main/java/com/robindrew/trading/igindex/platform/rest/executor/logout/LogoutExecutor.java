package com.robindrew.trading.igindex.platform.rest.executor.logout;

import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpUriRequest;

import com.robindrew.common.json.IJson;
import com.robindrew.trading.igindex.platform.rest.IIgRestService;
import com.robindrew.trading.igindex.platform.rest.executor.IgRestExecutor;

public class LogoutExecutor extends IgRestExecutor<Boolean> {

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

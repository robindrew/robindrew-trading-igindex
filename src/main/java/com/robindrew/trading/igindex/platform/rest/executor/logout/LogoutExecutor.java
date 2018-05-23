package com.robindrew.trading.igindex.platform.rest.executor.logout;

import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpUriRequest;

import com.robindrew.trading.igindex.platform.rest.IIgIndexRestService;
import com.robindrew.trading.igindex.platform.rest.executor.IgIndexRestExecutor;

public class LogoutExecutor extends IgIndexRestExecutor<Boolean> {

	public LogoutExecutor(IIgIndexRestService platform) {
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
		addStandardHeaders(request);
		return request;
	}

	@Override
	public Class<Boolean> getResponseType() {
		return Boolean.class;
	}
}

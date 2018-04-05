package com.robindrew.trading.igindex.platform.rest.executor;

import org.apache.http.client.methods.HttpUriRequest;

import com.robindrew.common.json.IJson;

public interface IHttpJsonRestExecutor<R> extends IRestExecutor<R> {

	HttpUriRequest createRequest();

	R createResponse(IJson json);

}

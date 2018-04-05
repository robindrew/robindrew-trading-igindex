package com.robindrew.trading.igindex.platform.rest.executor;

import com.robindrew.trading.igindex.platform.IgSession;

public interface IRestExecutor<R> {

	IgSession getSession();

	int getRequestVersion();

	R execute();

}

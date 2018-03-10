package com.robindrew.trading.provider.igindex.platform.rest.executor;

import com.robindrew.trading.provider.igindex.platform.IgSession;

public interface IRestExecutor<R> {

	IgSession getSession();

	int getRequestVersion();

	R execute();

}

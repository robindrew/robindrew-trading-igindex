package com.robindrew.trading.provider.igindex.platform.rest.executor.searchMarkets;

import static com.robindrew.common.util.Check.notEmpty;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;

import com.robindrew.common.json.IJson;
import com.robindrew.common.text.Strings;
import com.robindrew.trading.provider.igindex.platform.rest.IIgRestService;
import com.robindrew.trading.provider.igindex.platform.rest.executor.HttpJsonRestExecutor;

public class SearchMarketsExecutor extends HttpJsonRestExecutor<SearchMarketsResponse> {

	private final String searchTerm;

	public SearchMarketsExecutor(IIgRestService service, String searchTerm) {
		super(service);
		this.searchTerm = notEmpty("searchTerm", searchTerm);
	}

	@Override
	public int getRequestVersion() {
		return 1;
	}

	@Override
	public HttpUriRequest createRequest() {
		HttpGet request = new HttpGet(getUrl("/markets?searchTerm=" + Strings.urlEncode(searchTerm)));
		addHeaders(request);
		return request;
	}

	@Override
	public SearchMarketsResponse createResponse(IJson json) {
		return new SearchMarketsResponse(json);
	}

}

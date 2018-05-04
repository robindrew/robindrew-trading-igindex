package com.robindrew.trading.igindex.platform.rest.executor.searchMarkets;

import static com.robindrew.common.util.Check.notEmpty;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;

import com.robindrew.common.text.Strings;
import com.robindrew.trading.igindex.platform.rest.IIgRestService;
import com.robindrew.trading.igindex.platform.rest.executor.IgRestExecutor;

public class SearchMarketsExecutor extends IgRestExecutor<SearchMarketsResponse> {

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
		addStandardHeaders(request);
		return request;
	}

	@Override
	protected Class<SearchMarketsResponse> getResponseType() {
		return SearchMarketsResponse.class;
	}

}

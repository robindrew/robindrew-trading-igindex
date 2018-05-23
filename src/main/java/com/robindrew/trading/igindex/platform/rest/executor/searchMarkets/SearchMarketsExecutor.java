package com.robindrew.trading.igindex.platform.rest.executor.searchMarkets;

import static com.robindrew.common.util.Check.notEmpty;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;

import com.robindrew.common.text.Strings;
import com.robindrew.trading.igindex.platform.rest.IIgIndexRestService;
import com.robindrew.trading.igindex.platform.rest.executor.IgIndexRestExecutor;

public class SearchMarketsExecutor extends IgIndexRestExecutor<SearchMarketsResponse> {

	private final String searchTerm;

	public SearchMarketsExecutor(IIgIndexRestService service, String searchTerm) {
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

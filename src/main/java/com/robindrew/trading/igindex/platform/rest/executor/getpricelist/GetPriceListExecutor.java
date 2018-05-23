package com.robindrew.trading.igindex.platform.rest.executor.getpricelist;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;

import com.robindrew.trading.igindex.IgIndexInstrument;
import com.robindrew.trading.igindex.platform.rest.IIgIndexRestService;
import com.robindrew.trading.igindex.platform.rest.executor.IgIndexRestExecutor;

public class GetPriceListExecutor extends IgIndexRestExecutor<GetPriceListResponse> {

	private final GetPriceListRequest request;

	public GetPriceListExecutor(IIgIndexRestService service, GetPriceListRequest request) {
		super(service);
		if (request == null) {
			throw new NullPointerException("instrument");
		}
		this.request = request;
	}

	@Override
	public int getRequestVersion() {
		return 3;
	}

	@Override
	public HttpUriRequest createRequest() {
		IgIndexInstrument instrument = request.getInstrument();

		HttpGet request = new HttpGet(getUrl("/prices/") + instrument.getEpic() + getQuery());
		addStandardHeaders(request);
		return request;
	}

	private String getQuery() {
		QueryBuilder query = new QueryBuilder();
		query.append("resolution", request.getResolution());
		if (request.getMax() > 0) {
			query.append("max", request.getMax());
		}
		if (request.getPageSize() > 0) {
			query.append("pageSize", request.getPageSize());
		}
		if (request.getPageNumber() > 0) {
			query.append("pageNumber", request.getPageNumber());
		}
		if (request.getFrom() != null) {
			query.append("from", request.getFrom());
		}
		if (request.getTo() != null) {
			query.append("to", request.getTo());
		}
		System.out.println("query=" + query);
		return query.toString();
	}

	@Override
	protected Class<GetPriceListResponse> getResponseType() {
		return GetPriceListResponse.class;
	}

}

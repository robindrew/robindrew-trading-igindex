package com.robindrew.trading.igindex.platform.rest.executor.login;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.robindrew.trading.igindex.platform.rest.IIgRestService;
import com.robindrew.trading.igindex.platform.rest.executor.IgRestExecutor;

public class LoginExecutor extends IgRestExecutor<LoginResponse> {

	private static final Logger log = LoggerFactory.getLogger(LoginExecutor.class);

	public LoginExecutor(IIgRestService platform) {
		super(platform);
	}

	@Override
	public int getRequestVersion() {
		return 2;
	}

	@Override
	protected boolean isLoginAttempt() {
		return true;
	}

	@Override
	public HttpUriRequest createRequest() {

		// JSON
		String username = getSession().getCredentials().getUsername();
		String password = getSession().getCredentials().getPassword();
		LoginRequest jsonRequest = new LoginRequest(username, password);

		// HTTP
		HttpPost request = new HttpPost(getUrl("/session"));
		addStandardHeaders(request);
		setJsonContent(request, jsonRequest);
		return request;
	}

	protected LoginResponse handleResponse(HttpUriRequest request, HttpResponse response) {
		LoginResponse details = super.handleResponse(request, response);

		log.info("clientId: " + details.getClientId());
		log.info("accountType: " + details.getAccountType());
		log.info("timezoneOffset: " + details.getTimezoneOffset());
		log.info("dealingEnabled: " + details.isDealingEnabled());
		log.info("currencySymbol: " + details.getCurrencySymbol());
		log.info("currencyIsoCode: " + details.getCurrencyIsoCode());
		log.info("currentAccountId: " + details.getCurrentAccountId());
		log.info("trailingStopsEnabled: " + details.isTrailingStopsEnabled());
		log.info("hasActiveDemoAccounts: " + details.isHasActiveDemoAccounts());
		log.info("hasActiveLiveAccounts: " + details.isHasActiveLiveAccounts());
		log.info("lightstreamerEndpoint: " + details.getLightstreamerEndpoint());

		// Lightstreamer Endpoint
		getSession().setLightstreamerEndpoint(details.getLightstreamerEndpoint());

		return details;
	}

	@Override
	public Class<LoginResponse> getResponseType() {
		return LoginResponse.class;
	}
}

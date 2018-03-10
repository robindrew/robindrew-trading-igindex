package com.robindrew.trading.provider.igindex.platform.rest.executor.login;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.robindrew.common.json.IJson;
import com.robindrew.trading.provider.igindex.platform.rest.IIgRestService;
import com.robindrew.trading.provider.igindex.platform.rest.executor.HttpJsonRestExecutor;

public class LoginExecutor extends HttpJsonRestExecutor<LoginResponse> {

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
		addHeaders(request);
		setRequestContent(request, jsonRequest);
		return request;
	}

	@Override
	public LoginResponse createResponse(IJson json) {
		LoginResponse response = new LoginResponse(json);
		LoginDetails details = response.getDetails();

		log.info("clientId: " + details.getClientId());
		log.info("accountType: " + details.getAccountType());
		log.info("timezoneOffset: " + details.getTimezoneOffset());
		log.info("dealingEnabled: " + details.getDealingEnabled());
		log.info("currencySymbol: " + details.getCurrencySymbol());
		log.info("currencyIsoCode: " + details.getCurrencyIsoCode());
		log.info("currentAccountId: " + details.getCurrentAccountId());
		log.info("trailingStopsEnabled: " + details.getTrailingStopsEnabled());
		log.info("hasActiveDemoAccounts: " + details.getHasActiveDemoAccounts());
		log.info("hasActiveLiveAccounts: " + details.getHasActiveLiveAccounts());
		log.info("lightstreamerEndpoint: " + details.getLightstreamerEndpoint());

		// Lightstreamer Endpoint
		getSession().setLightstreamerEndpoint(details.getLightstreamerEndpoint());

		return response;
	}
}

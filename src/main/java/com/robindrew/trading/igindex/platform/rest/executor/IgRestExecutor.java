package com.robindrew.trading.igindex.platform.rest.executor;

import static com.robindrew.common.util.Check.notNull;

import java.util.Optional;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.robindrew.common.json.Gsons;
import com.robindrew.common.json.IJson;
import com.robindrew.common.text.Strings;
import com.robindrew.trading.httpclient.HttpClientException;
import com.robindrew.trading.httpclient.HttpClientExecutor;
import com.robindrew.trading.httpclient.HttpClients;
import com.robindrew.trading.httpclient.HttpRetryException;
import com.robindrew.trading.igindex.platform.IgException;
import com.robindrew.trading.igindex.platform.IgSession;
import com.robindrew.trading.igindex.platform.rest.IIgRestService;
import com.robindrew.trading.igindex.platform.rest.IgRestError;
import com.robindrew.trading.igindex.platform.rest.IgRestJson;
import com.robindrew.trading.log.ITransactionLog;

public abstract class IgRestExecutor<R> extends HttpClientExecutor<R> {

	private static final Logger log = LoggerFactory.getLogger(IgRestExecutor.class);

	private final IIgRestService rest;

	protected IgRestExecutor(IIgRestService rest) {
		this.rest = notNull("rest", rest);;
	}

	public String getName() {
		String name = getClass().getSimpleName();
		if (name.endsWith("Executor")) {
			name = name.substring(0, name.length() - "Executor".length());
		}
		return name;
	}

	protected boolean logRequest() {
		return false;
	}

	protected boolean logResponse() {
		return false;
	}

	protected int getRetryAttemptLimit() {
		return 1;
	}

	protected ITransactionLog getTransactionLog() {
		return rest.getTransactionLog();
	}

	public IgSession getSession() {
		return rest.getSession();
	}

	protected boolean isLoginAttempt() {
		return false;
	}

	protected String getUrl(String path) {
		return getSession().getEnvironment().getRestUrl() + path;
	}

	protected void addDeleteHeader(HttpPost request) {
		request.addHeader("_method", "DELETE");
	}

	protected void addStandardHeaders(HttpUriRequest request) {

		// Basic Headers
		request.addHeader("Accept", "application/json; charset=UTF-8");
		request.addHeader("Accept-Encoding", "gzip, deflate, sdch, br");
		request.addHeader("Accept-Language", "en-GB,en-US;q=0.8,en;q=0.6,et;q=0.4,it;q=0.2,mt;q=0.2,sv;q=0.2");
		request.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
		request.addHeader("Host", request.getURI().getHost());

		// IG Index Headers
		request.addHeader("X-IG-API-KEY", getSession().getCredentials().getApiKey());
		request.addHeader("Version", Integer.toString(getRequestVersion()));

		// Account Security Token
		if (getSession().hasAccountSecurityToken()) {
			request.addHeader("X-SECURITY-TOKEN", getSession().getAccountSecurityToken());
		}

		// Client Security Token
		if (getSession().hasClientSecurityToken()) {
			request.addHeader("CST", getSession().getClientSecurityToken());
		}
	}

	protected HttpResponse execute(HttpClient client, HttpUriRequest request) throws Exception {
		logRequest(request);
		return super.execute(client, request);
	}

	protected R handleResponse(HttpUriRequest request, HttpResponse response) {

		// Get Account Security Token
		String token = HttpClients.getHeader(response, "X-SECURITY-TOKEN");
		if (token != null) {
			getSession().setAccountSecurityToken(token);
		}

		// Get Client Security Token
		token = HttpClients.getHeader(response, "CST");
		if (token != null) {
			getSession().setClientSecurityToken(token);
		}

		// Failed!
		if (response.getStatusLine().getStatusCode() != 200) {
			throw getFailureException(request, response);
		}

		// Get the content
		String json = HttpClients.getTextContent(response.getEntity());
		logResponse(request, response, json);

		// Parse the JSON
		Class<R> responseType = getResponseType();
		R jsonObject = IgRestJson.fromJson(json, responseType);

		// Sanity check (TODO: remove this?)
		String objectJson = jsonObject.toString();
		if (!objectJson.equals(json)) {
			log.warn("JSON Object does not JSON String");
			log.warn("JSON String: {}", json);
			log.warn("JSON Object: {}", objectJson);
		}
		return jsonObject;

	}

	private HttpClientException getFailureException(HttpUriRequest request, HttpResponse response) {

		// Check the content
		HttpEntity entity = response.getEntity();
		if (entity == null) {
			throw new HttpClientException("Entity content missing");
		}

		// Parse the JSON
		String content = HttpClients.getTextContent(response.getEntity());
		IJson json = Gsons.parseJson(content);

		// Check for the error code
		Optional<IgRestError> errorCode = IgRestError.getRestError(json);
		if (!errorCode.isPresent()) {
			return new HttpClientException("Response content: " + content);
		}

		IgRestError code = errorCode.get();
		log.warn("[HTTP Error] " + code);

		// We only attempt to retry if not logged in
		if (!code.isLoginInvalid()) {
			return new HttpClientException("Invalid request: " + request.getRequestLine() + " -> " + code);
		}

		// Only attempt login if this is not already an attempt!
		if (isLoginAttempt()) {
			return new HttpClientException("Invalid request: " + request.getRequestLine() + " -> " + code);
		}

		// Login!
		try {
			rest.login();
		} catch (IgException e) {
			return new HttpClientException("Invalid request: " + request.getRequestLine() + " -> " + code, e);
		}

		// Retry!
		return new HttpRetryException("Logged in");
	}

	private void logResponse(HttpUriRequest request, HttpResponse response, String json) {
		if (logResponse()) {
			log.info("[HTTP Response]\n" + HttpClients.toString(response, json));
		}

		// Transaction Log
		json = Strings.json(json);
		getTransactionLog().log(request.getURI().toString(), json);
	}

	private void logRequest(HttpUriRequest request) {
		if (logRequest()) {
			log.info("[HTTP Request]\n" + HttpClients.toString(request));
		}
	}

	public void setJsonContent(HttpEntityEnclosingRequestBase request, Object content) {

		// Build the JSON
		String json = Strings.json(content, true);

		// Set the HTTP content (N.B. content type & length headers are automatically added)
		HttpClients.setJsonContent(request, json);
	}

	protected abstract int getRequestVersion();

	protected abstract Class<R> getResponseType();

}

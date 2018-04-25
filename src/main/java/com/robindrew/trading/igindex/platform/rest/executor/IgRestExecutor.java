package com.robindrew.trading.igindex.platform.rest.executor;

import java.io.UnsupportedEncodingException;
import java.util.Optional;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.robindrew.common.json.Gsons;
import com.robindrew.common.json.IJson;
import com.robindrew.common.text.LineBuilder;
import com.robindrew.common.text.Strings;
import com.robindrew.trading.httpclient.HttpClientException;
import com.robindrew.trading.httpclient.HttpClientExecutor;
import com.robindrew.trading.httpclient.HttpClients;
import com.robindrew.trading.httpclient.HttpRetryException;
import com.robindrew.trading.igindex.platform.IgException;
import com.robindrew.trading.igindex.platform.IgSession;
import com.robindrew.trading.igindex.platform.rest.IIgRestService;
import com.robindrew.trading.igindex.platform.rest.IgRestError;
import com.robindrew.trading.log.ITransactionLog;

public abstract class IgRestExecutor<R> extends HttpClientExecutor<R> {

	private static final Logger log = LoggerFactory.getLogger(IgRestExecutor.class);

	private final IIgRestService service;

	protected IgRestExecutor(IIgRestService service) {
		if (service == null) {
			throw new NullPointerException("service");
		}
		this.service = service;
	}

	protected boolean logRequest() {
		return true;
	}

	protected boolean logResponse() {
		return true;
	}

	protected int getRetryAttemptLimit() {
		return 1;
	}

	protected ITransactionLog getTransactionLog() {
		return service.getTransactionLog();
	}

	public IgSession getSession() {
		return service.getSession();
	}

	protected boolean isLoginAttempt() {
		return false;
	}

	protected String getUrl(String path) {
		return getSession().getEnvironment().getUrl() + path;
	}

	protected void addDeleteHeader(HttpPost request) {
		request.addHeader("_method", "DELETE");
	}

	protected void addHeaders(HttpUriRequest request) {
		String host = request.getURI().getHost();
		if (host == null) {
			throw new IllegalStateException("host not available in URI '" + request.getURI() + "'");
		}

		// Basic Headers
		request.addHeader("Accept", "application/json; charset=UTF-8");
		request.addHeader("Accept-Encoding", "gzip, deflate, sdch, br");
		request.addHeader("Accept-Language", "en-GB,en-US;q=0.8,en;q=0.6,et;q=0.4,it;q=0.2,mt;q=0.2,sv;q=0.2");
		request.addHeader("X-IG-API-KEY", getSession().getCredentials().getApiKey());
		request.addHeader("Version", Integer.toString(getRequestVersion()));
		request.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
		request.addHeader("Host", host);

		if (request instanceof HttpEntityEnclosingRequestBase) {
			request.addHeader("Content-Type", "application/json; charset=UTF-8");
		}

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

		// Parse the JSON
		String content = HttpClients.getTextContent(response.getEntity());
		logResponse(request, response, content);
		IJson json = Gsons.parseJson(content);

		// Create the response
		return createResponse(json);
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
			service.login();
		} catch (IgException e) {
			return new HttpClientException("Invalid request: " + request.getRequestLine() + " -> " + code, e);
		}

		// Retry!
		return new HttpRetryException("Logged in");
	}

	private void logResponse(HttpUriRequest request, HttpResponse response, String json) {

		// HTTP response
		LineBuilder text = new LineBuilder();
		text.appendLine(response.getStatusLine());
		for (Header header : response.getAllHeaders()) {
			text.append(header.getName()).append(": ").append(header.getValue()).appendLine();
		}
		text.appendLine();
		text.append(json);
		if (logResponse()) {
			log.debug("[HTTP Response]\n{}", text);
		}

		// Transaction Log
		json = Strings.json(json);
		getTransactionLog().log(request.getURI().toString(), json);
	}

	private void logRequest(HttpUriRequest request) {
		LineBuilder text = new LineBuilder();
		text.appendLine(request.getRequestLine());
		for (Header header : request.getAllHeaders()) {
			text.append(header.getName()).append(": ").append(header.getValue()).appendLine();
		}
		if (request instanceof HttpEntityEnclosingRequestBase) {
			HttpEntityEnclosingRequestBase base = (HttpEntityEnclosingRequestBase) request;
			text.appendLine();
			text.append(HttpClients.getTextContent(base.getEntity()));
		}
		if (logRequest()) {
			log.debug("[HTTP Request]\n" + text);
		}
	}

	protected void setRequestContent(HttpEntityEnclosingRequestBase request, Object jsonObject) {
		try {
			String json = (jsonObject instanceof String) ? jsonObject.toString() : new Gson().toJson(jsonObject);
			request.setEntity(new StringEntity(json));
		} catch (UnsupportedEncodingException e) {
			throw new HttpClientException(e);
		}
	}

	protected abstract int getRequestVersion();

	protected abstract R createResponse(IJson json);

}

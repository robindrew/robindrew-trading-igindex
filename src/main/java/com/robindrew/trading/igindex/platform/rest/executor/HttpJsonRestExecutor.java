package com.robindrew.trading.igindex.platform.rest.executor;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Charsets;
import com.google.common.base.Stopwatch;
import com.google.common.io.CharStreams;
import com.google.gson.Gson;
import com.robindrew.common.json.Gsons;
import com.robindrew.common.json.IJson;
import com.robindrew.common.text.LineBuilder;
import com.robindrew.common.text.Strings;
import com.robindrew.common.util.Java;
import com.robindrew.trading.igindex.platform.IgException;
import com.robindrew.trading.igindex.platform.IgSession;
import com.robindrew.trading.igindex.platform.rest.IIgRestService;
import com.robindrew.trading.igindex.platform.rest.IgRestError;

public abstract class HttpJsonRestExecutor<R> implements IHttpJsonRestExecutor<R> {

	private static final Logger log = LoggerFactory.getLogger(HttpJsonRestExecutor.class);

	private final IIgRestService service;

	protected HttpJsonRestExecutor(IIgRestService service) {
		if (service == null) {
			throw new NullPointerException("service");
		}
		this.service = service;
	}

	protected int getRetryAttemptLimit() {
		return 1;
	}

	protected boolean isLoginAttempt() {
		return false;
	}

	protected boolean logRequest() {
		return true;
	}

	protected boolean logResponse() {
		return true;
	}

	@Override
	public IgSession getSession() {
		return service.getSession();
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

	@Override
	public final R execute() {
		log.info("[Executing] " + getName());
		Stopwatch timer = Stopwatch.createStarted();

		HttpUriRequest request = createRequest();
		int retryAttemptCount = 0;

		try {
			while (true) {

				// Create a client
				HttpClient client = HttpClientBuilder.create().build();

				// Execute the request
				logRequest(request);
				HttpResponse response = client.execute(request);
				String content = getContent(response.getEntity());
				if (response.getStatusLine().getStatusCode() != 200) {
					throw new ServiceNotAvailableException("Response: '" + response.getStatusLine().toString() + "', Content: " + content);
				}
				logResponse(request, response, content);

				// Get Account Security Token
				String token = getHeader(response, "X-SECURITY-TOKEN");
				if (token != null) {
					getSession().setAccountSecurityToken(token);
				}

				// Get Client Security Token
				token = getHeader(response, "CST");
				if (token != null) {
					getSession().setClientSecurityToken(token);
				}

				// Response JSON Content
				IJson json = Gsons.parseJson(content);

				// Do we retry if the request failed?
				if (retryRequest(request, json, retryAttemptCount)) {
					retryAttemptCount++;

					// Create a new request, as the login details may have changed
					request = createRequest();
					continue;
				}

				// The response should only be allowed out if the status is OK
				int statusCode = response.getStatusLine().getStatusCode();
				if (statusCode != 200) {
					throw new IgException("Status code not OK: " + statusCode);
				}

				timer.stop();
				log.info("[Executed] " + getName() + " in " + timer);
				return createResponse(json);
			}

		} catch (Exception e) {
			throw new IgException("Failed to execute request: " + request.getRequestLine(), e);
		}
	}

	public String getName() {
		String name = getClass().getSimpleName();
		if (name.endsWith("Executor")) {
			name = name.substring(0, name.indexOf("Executor"));
		}
		return name;
	}

	private boolean retryRequest(HttpUriRequest request, IJson object, int retryAttemptCount) {
		Optional<IgRestError> errorCode = IgRestError.getRestError(object);
		if (!errorCode.isPresent()) {
			return false;
		}
		IgRestError code = errorCode.get();
		log.warn("[HTTP Error] " + code);

		// We limit the number of retries ...
		if (retryAttemptCount > getRetryAttemptLimit()) {
			throw new IgException("Invalid request: " + request.getRequestLine() + " -> " + code);
		}

		// We only attempt to retry if not logged in
		if (!code.isLoginInvalid()) {
			throw new IgException("Invalid request: " + request.getRequestLine() + " -> " + code);
		}

		// Only attempt login if this is not already an attempt!
		if (isLoginAttempt()) {
			throw new IgException("Invalid request: " + request.getRequestLine() + " -> " + code);
		}

		// Login!
		try {
			service.login();
		} catch (IgException e) {
			throw new IgException("Invalid request: " + request.getRequestLine() + " -> " + code, e);
		}

		// Retry!
		log.warn("[HTTP Retry] Attempt #" + retryAttemptCount);
		return true;
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
			log.info("[HTTP Response]\n{}", text);
		}

		// Transaction Log
		json = Strings.json(json);
		service.getTransactionLog().log(request.getURI().toString(), json);
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
			text.append(getContent(base.getEntity()));
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
			throw new IgException(e);
		}
	}

	protected String getContent(HttpEntity entity) {
		try {
			InputStream stream = entity.getContent();
			if (stream == null) {
				return "";
			}
			return CharStreams.toString(new InputStreamReader(stream, Charsets.UTF_8));
		} catch (IOException e) {
			throw Java.propagate(e);
		}
	}

	protected String getHeader(HttpResponse response, String name) {
		Header[] headers = response.getHeaders(name);
		if (headers != null) {
			for (Header header : headers) {
				String value = header.getValue();
				if (value != null && !value.isEmpty()) {
					return value;
				}
			}
		}
		return null;
	}

}

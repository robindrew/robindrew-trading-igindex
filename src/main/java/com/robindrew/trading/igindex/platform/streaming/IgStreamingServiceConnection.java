package com.robindrew.trading.igindex.platform.streaming;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lightstreamer.ls_client.ConnectionInfo;
import com.lightstreamer.ls_client.ConnectionListener;
import com.lightstreamer.ls_client.ExtendedTableInfo;
import com.lightstreamer.ls_client.HandyTableListener;
import com.lightstreamer.ls_client.LSClient;
import com.lightstreamer.ls_client.SubscribedTableKey;
import com.robindrew.trading.igindex.platform.IgException;
import com.robindrew.trading.igindex.platform.IgSession;
import com.robindrew.trading.igindex.platform.streaming.subscription.IIgInstrumentPriceStream;
import com.robindrew.trading.platform.streaming.IInstrumentPriceStream;

public class IgStreamingServiceConnection implements AutoCloseable {

	private static final Logger log = LoggerFactory.getLogger(IgStreamingServiceConnection.class);

	private static ConnectionInfo createInfo(IgSession session) {
		ConnectionInfo info = new ConnectionInfo();
		info.user = session.getCredentials().getUsername();
		info.password = "CST-" + session.getClientSecurityToken() + "|XST-" + session.getAccountSecurityToken();
		info.pushServerUrl = session.getLightstreamerEndpoint();
		log.info("Username: {}", info.user);
		log.info("Server: {}", info.pushServerUrl);
		return info;
	}

	private final IgSession session;
	private final LSClient client = new LSClient();
	private final ConnectionInfo info;

	public IgStreamingServiceConnection(IgSession session) {
		if (session == null) {
			throw new NullPointerException("session");
		}
		this.session = session;
		this.info = createInfo(session);
	}

	public IgSession getSession() {
		return session;
	}

	public ConnectionInfo getInfo() {
		return info;
	}

	public void connect(ConnectionListener listener) {
		try {
			ConnectionInfo info = getInfo();
			client.openConnection(info, listener);
		} catch (Exception e) {
			throw new IgException(e);
		}
	}

	public void subscribe(IInstrumentPriceStream stream) {
		if (stream instanceof IIgInstrumentPriceStream) {
			subscribe((IIgInstrumentPriceStream) stream);
		} else {
			throw new IllegalArgumentException("Subscription not supported: " + stream);
		}
	}

	public void unsubscribe(IInstrumentPriceStream stream) {
		if (stream instanceof IIgInstrumentPriceStream) {
			unsubscribe((IIgInstrumentPriceStream) stream);
		} else {
			throw new IllegalArgumentException("Subscription not supported: " + stream);
		}
	}

	public void subscribe(IIgInstrumentPriceStream stream) {
		try {
			ExtendedTableInfo tableInfo = stream.getExtendedTableInfo();
			HandyTableListener listener = stream.getHandyTableListener();
			SubscribedTableKey key = client.subscribeTable(tableInfo, listener, false);
			stream.setKey(key);
		} catch (Exception e) {
			throw new IgException(e);
		}
	}

	public void unsubscribe(IIgInstrumentPriceStream stream) {
		try {
			SubscribedTableKey key = stream.getSubscribedTableKey();
			client.unsubscribeTable(key);
		} catch (Exception e) {
			throw new IgException(e);
		}
	}

	@Override
	public void close() {
		client.closeConnection();
	}
}

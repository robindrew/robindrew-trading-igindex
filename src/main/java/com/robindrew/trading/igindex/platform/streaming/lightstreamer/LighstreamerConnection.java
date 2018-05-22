package com.robindrew.trading.igindex.platform.streaming.lightstreamer;

import com.lightstreamer.ls_client.ConnectionInfo;
import com.lightstreamer.ls_client.ConnectionListener;
import com.lightstreamer.ls_client.ExtendedTableInfo;
import com.lightstreamer.ls_client.HandyTableListener;
import com.lightstreamer.ls_client.LSClient;
import com.lightstreamer.ls_client.SubscribedTableKey;
import com.robindrew.trading.igindex.IIgInstrument;
import com.robindrew.trading.igindex.platform.IIgSession;
import com.robindrew.trading.igindex.platform.streaming.subscription.IIgInstrumentPriceStream;
import com.robindrew.trading.platform.streaming.IInstrumentPriceStream;

public class LighstreamerConnection implements AutoCloseable {

	private final IIgSession session;
	private final LSClient client = new LSClient();
	private final ConnectionInfo info;

	public LighstreamerConnection(IIgSession session) {
		if (session == null) {
			throw new NullPointerException("session");
		}
		this.session = session;
		this.info = new ConnectionInfoBuilder(session).build();
	}

	public IIgSession getSession() {
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
			throw new LightstreamerException(e);
		}
	}

	public void subscribe(IInstrumentPriceStream<IIgInstrument> stream) {
		if (stream instanceof IIgInstrumentPriceStream) {
			subscribe((IIgInstrumentPriceStream) stream);
		} else {
			throw new IllegalArgumentException("Subscription not supported: " + stream);
		}
	}

	public void unsubscribe(IInstrumentPriceStream<IIgInstrument> stream) {
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
			throw new LightstreamerException(e);
		}
	}

	public void unsubscribe(IIgInstrumentPriceStream stream) {
		try {
			SubscribedTableKey key = stream.getSubscribedTableKey();
			client.unsubscribeTable(key);
		} catch (Exception e) {
			throw new LightstreamerException(e);
		}
	}

	@Override
	public void close() {
		client.closeConnection();
	}
}

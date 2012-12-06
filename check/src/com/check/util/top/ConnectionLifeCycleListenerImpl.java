package com.check.util.top;

import com.taobao.api.internal.stream.connect.ConnectionLifeCycleListener;

public class ConnectionLifeCycleListenerImpl implements ConnectionLifeCycleListener {

	public void onBeforeConnect() {
	}

	public void onException(Throwable e) {
		e.printStackTrace();
	}

	public void onMaxReadTimeoutException() {
	}

}

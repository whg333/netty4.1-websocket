package com.whg.websocket.server.framework.thread.impl;

import java.util.concurrent.atomic.AtomicInteger;

import com.whg.websocket.server.framework.thread.ThreadPoolSelector;

public class TakeTurnPoolSelector implements ThreadPoolSelector {
	
	private final int poolNum;
	private final AtomicInteger poolIndex = new AtomicInteger();

	public TakeTurnPoolSelector(int poolNum) {
		this.poolNum = poolNum;
	}

	public int selectPool() {
		return poolNum == 1 ? 0 : poolIndex.getAndIncrement() % poolNum;
	}
	
}

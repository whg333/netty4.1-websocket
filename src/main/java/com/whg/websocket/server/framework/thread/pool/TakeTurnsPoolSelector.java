package com.whg.websocket.server.framework.thread.pool;

import java.util.concurrent.atomic.AtomicInteger;

public class TakeTurnsPoolSelector implements ThreadPoolSelector {
	
	private PoolState[] pools;
	private final AtomicInteger poolIndex = new AtomicInteger();

	public int selectPool() {
		return this.poolIndex.getAndIncrement() % this.pools.length;
	}
	
	public void setPoolsState(PoolState[] pools) {
		this.pools = pools;
	}
	
}

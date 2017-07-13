package com.whg.websocket.server.framework.thread.pool;

import java.util.concurrent.atomic.AtomicInteger;

public class TakeTurnPoolSelector implements ThreadPoolSelector {
	
	private final PoolState[] pools;
	private final AtomicInteger poolIndex = new AtomicInteger();

	public TakeTurnPoolSelector(PoolState[] pools) {
		this.pools = pools;
	}

	public int selectPool() {
		return poolIndex.getAndIncrement() % pools.length;
	}
	
}

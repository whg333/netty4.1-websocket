package com.whg.websocket.server.framework.thread.pool;

import com.whg.websocket.server.framework.thread.config.PoolConfig;

public class GlobalThreadPool implements LogicThreadPool {
	
	private final int poolNum;
	private final GlobalThreadPoolState[] pools;
	private final ThreadPoolSelector selector;

	public GlobalThreadPool(PoolConfig poolConfig) {
		this(poolConfig.getPoolNum(), poolConfig.getMinThreadNum(), poolConfig.getMaxThreadNum(),
				poolConfig.getQueueTaskNum(), poolConfig.getName());
	}

	public GlobalThreadPool(int poolNum, int minThreadNum, int maxThreadNum, int queueTaskNum, String name) {
		this(poolNum, minThreadNum, maxThreadNum, queueTaskNum, name, new TakeTurnsPoolSelector());
	}

	public GlobalThreadPool(int poolNum, int minThreadNum, int maxThreadNum, int queueTaskNum, String name,
			ThreadPoolSelector poolSelector) {
		this.poolNum = poolNum;
		this.pools = new GlobalThreadPoolState[poolNum];
		for (int i = 0; i < poolNum; i++) {
			this.pools[i] = new GlobalThreadPoolState(minThreadNum, maxThreadNum, queueTaskNum, name + "-" + i + "-");
		}
		poolSelector.setPoolsState(this.pools);
		this.selector = poolSelector;
	}

	public void excute(Runnable task) {
		if (this.poolNum == 1) {
			this.pools[0].execute(task);
		} else {
			int index = this.selector.selectPool();
			this.pools[index].execute(task);
		}
	}

	public PoolState[] getPoolsState() {
		PoolState[] states = new PoolState[this.pools.length];
		for (int i = 0; i < this.pools.length; i++) {
			states[i] = new PoolStateWrap(this.pools[i]);
		}
		return states;
	}
}

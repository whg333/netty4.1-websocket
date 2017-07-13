package com.whg.websocket.server.framework.thread.pool;

import com.whg.websocket.server.framework.thread.config.PoolConfig;

public class GlobalThreadPool implements LogicThreadPool {
	
	private final int poolNum;
	private final BusinessThreadPool[] pools;
	private final ThreadPoolSelector selector;

	public GlobalThreadPool(PoolConfig poolConfig) {
		this(poolConfig.getPoolNum(), poolConfig.getMinThreadNum(), poolConfig.getMaxThreadNum(),
				poolConfig.getQueueTaskNum(), poolConfig.getName());
	}

	public GlobalThreadPool(int poolNum, int minThreadNum, int maxThreadNum, int queueTaskNum, String name) {
		if(poolNum <= 0){
			throw new IllegalArgumentException("poolNum="+poolNum);
		}
		this.poolNum = poolNum;
		pools = new BusinessThreadPool[poolNum];
		for (int i = 0; i < poolNum; i++) {
			pools[i] = new BusinessThreadPool(minThreadNum, maxThreadNum, queueTaskNum, name + "-business-" + i + "-");
		}
		selector = new TakeTurnPoolSelector(this.pools);
	}

	@Override
	public void execute(Runnable task) {
		if (poolNum == 1) {
			pools[0].execute(task);
		} else {
			int index = selector.selectPool();
			pools[index].execute(task);
		}
	}

	public PoolState[] getPoolStateList() {
		PoolState[] states = new PoolState[pools.length];
		for (int i = 0; i < pools.length; i++) {
			states[i] = new PoolStateWrapper(pools[i]);
		}
		return states;
	}
}

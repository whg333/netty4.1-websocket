package com.whg.websocket.server.framework.thread.impl;

import java.util.ArrayList;
import java.util.List;

import com.whg.websocket.server.framework.thread.PoolConfig;
import com.whg.websocket.server.framework.thread.PoolState;
import com.whg.websocket.server.framework.thread.ThreadPool;
import com.whg.websocket.server.framework.thread.ThreadPoolSelector;

public class GlobalThreadPool implements ThreadPool {
	
	private final BusinessThreadPool[] pools;
	private final ThreadPoolSelector selector;

	public GlobalThreadPool(PoolConfig poolConfig) {
		this(poolConfig.poolNum, poolConfig.minThreadNum, poolConfig.maxThreadNum,
				poolConfig.queueTaskNum, poolConfig.name);
	}

	public GlobalThreadPool(int poolNum, int minThreadNum, int maxThreadNum, int queueTaskNum, String name) {
		if(poolNum <= 0){
			throw new IllegalArgumentException("poolNum="+poolNum);
		}
		
		pools = new BusinessThreadPool[poolNum];
		for (int i = 0; i < poolNum; i++) {
			pools[i] = new BusinessThreadPool(minThreadNum, maxThreadNum, queueTaskNum, name + (i+1) + "-");
		}
		selector = new TakeTurnPoolSelector(poolNum);
	}

	@Override
	public void execute(Runnable task) {
		int index = selector.selectPool();
		pools[index].execute(task);
	}

	public List<PoolState> getPoolStateList() {
		List<PoolState> states = new ArrayList<PoolState>(pools.length);
		for (int i = 0; i < pools.length; i++) {
			states.add(new PoolStateWrapper(pools[i]));
		}
		return states;
	}
}

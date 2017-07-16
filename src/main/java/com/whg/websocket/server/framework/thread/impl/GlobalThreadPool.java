package com.whg.websocket.server.framework.thread.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;

import com.whg.websocket.server.framework.thread.PoolConfig;
import com.whg.websocket.server.framework.thread.PoolState;

public class GlobalThreadPool implements Executor {
	
	private final AtomicInteger poolIndex = new AtomicInteger();
	private final BusinessThreadPool[] pools;

	public GlobalThreadPool(PoolConfig poolConfig) {
		this(poolConfig.poolNum, poolConfig.minThreadNum, poolConfig.maxThreadNum,
				poolConfig.queueTaskNum, poolConfig.name);
	}

	public GlobalThreadPool(int poolNum, int minThreadNum, int maxThreadNum, int queueTaskNum, String name) {
		if(poolNum <= 0){
			throw new IllegalArgumentException("poolNum=0");
		}
		
		pools = new BusinessThreadPool[poolNum];
		for (int i = 0; i < poolNum; i++) {
			pools[i] = new BusinessThreadPool(minThreadNum, maxThreadNum, queueTaskNum, name + (i+1) + "-");
		}
	}

	@Override
	public void execute(Runnable task) {
		int index = pools.length == 1 ? 0 
				: poolIndex.getAndIncrement() % pools.length;
		Executor executor = pools[index];
		executor.execute(task);
	}

	public List<PoolState> getPoolStateList() {
		List<PoolState> states = new ArrayList<PoolState>(pools.length);
		for (int i = 0; i < pools.length; i++) {
			//PoolState只暴露了访问数据的getXxx接口，不需要包装了
			//states.add(new PoolStateWrapper(pools[i]));
			states.add(pools[i]);
		}
		return states;
	}

}

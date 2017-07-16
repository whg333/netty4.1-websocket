package com.whg.websocket.server.framework.thread.impl;

import com.whg.websocket.server.framework.thread.PoolState;

//PoolState只暴露了访问数据的getXxx接口，不需要包装了
@Deprecated
class PoolStateWrapper implements PoolState {
	
	private final PoolState poolState;

	public PoolStateWrapper(PoolState poolState) {
		this.poolState = poolState;
	}

	@Override
	public int getActiveThreadNum() {
		return poolState.getActiveThreadNum();
	}

	@Override
	public long getCompleteTaskNum() {
		return poolState.getCompleteTaskNum();
	}

	@Override
	public int getMaxThreadNumInHistory() {
		return poolState.getMaxThreadNumInHistory();
	}

	@Override
	public int getQueueTaskNum() {
		return poolState.getQueueTaskNum();
	}

	@Override
	public long getScheduleTaskNum() {
		return poolState.getScheduleTaskNum();
	}

	@Override
	public int getThreadNum() {
		return poolState.getThreadNum();
	}
}

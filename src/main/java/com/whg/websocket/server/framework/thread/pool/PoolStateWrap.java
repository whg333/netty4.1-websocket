package com.whg.websocket.server.framework.thread.pool;

class PoolStateWrap implements PoolState {
	
	private final PoolState poolState;

	public PoolStateWrap(PoolState poolState) {
		this.poolState = poolState;
	}

	public int getActiveThreadNum() {
		return this.poolState.getActiveThreadNum();
	}

	public long getCompleteTaskNum() {
		return this.poolState.getCompleteTaskNum();
	}

	public int getMaxThreadNumInHistory() {
		return this.poolState.getMaxThreadNumInHistory();
	}

	public int getQueueTaskNum() {
		return this.poolState.getQueueTaskNum();
	}

	public long getScheduleTaskNum() {
		return this.poolState.getScheduleTaskNum();
	}

	public int getThreadNum() {
		return this.poolState.getThreadNum();
	}
}

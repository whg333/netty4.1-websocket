package com.whg.websocket.server.framework.thread;

public interface PoolState {

	int getActiveThreadNum();

	int getQueueTaskNum();

	long getCompleteTaskNum();

	long getScheduleTaskNum();

	int getMaxThreadNumInHistory();

	int getThreadNum();

}

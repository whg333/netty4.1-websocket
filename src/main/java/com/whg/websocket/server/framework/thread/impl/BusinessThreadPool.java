package com.whg.websocket.server.framework.thread.impl;

import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.whg.websocket.server.framework.thread.PoolState;
import com.whg.websocket.server.framework.thread.ThreadNameFactory;

public class BusinessThreadPool implements Executor, PoolState {
	
	private final ThreadPoolExecutor executor;

	public BusinessThreadPool(int minThreadNum, int maxThreadNum, int queueTaskNum) {
		this(minThreadNum, maxThreadNum, queueTaskNum, "BusinessThreadPool");
	}

	public BusinessThreadPool(int minThreadNum, int maxThreadNum, int queueTaskNum, String name) {
		executor = new ThreadPoolExecutor(minThreadNum, maxThreadNum, 30000L, TimeUnit.MILLISECONDS,
				new LinkedBlockingQueue<Runnable>(queueTaskNum), new ThreadNameFactory(name));
	}
	
	@Override
	public void execute(Runnable task) {
		executor.execute(task);
	}

	@Override
	public int getActiveThreadNum() {
		return executor.getActiveCount();
	}

	@Override
	public int getQueueTaskNum() {
		return executor.getQueue().size();
	}

	@Override
	public long getCompleteTaskNum() {
		return executor.getCompletedTaskCount();
	}

	@Override
	public long getScheduleTaskNum() {
		return executor.getTaskCount();
	}

	@Override
	public int getMaxThreadNumInHistory() {
		return executor.getLargestPoolSize();
	}

	@Override
	public int getThreadNum() {
		return executor.getPoolSize();
	}

}

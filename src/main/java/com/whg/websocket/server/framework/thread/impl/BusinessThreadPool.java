package com.whg.websocket.server.framework.thread.impl;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import com.whg.websocket.server.framework.thread.ThreadPool;
import com.whg.websocket.server.framework.thread.PoolState;

public class BusinessThreadPool implements ThreadPool, PoolState {
	
	private final ThreadPoolExecutor executor;

	public BusinessThreadPool(int minThreadNum, int maxThreadNum, int queueTaskNum) {
		this(minThreadNum, maxThreadNum, queueTaskNum, "BusinessThreadPool");
	}

	public BusinessThreadPool(int minThreadNum, int maxThreadNum, int queueTaskNum, String name) {
		executor = new ThreadPoolExecutor(minThreadNum, maxThreadNum, 30000L, TimeUnit.MILLISECONDS,
				new LinkedBlockingQueue<Runnable>(queueTaskNum), new ThreadPoolFactory(name));
	}
	
	private static class ThreadPoolFactory implements ThreadFactory {
        private static final AtomicInteger poolNumber = new AtomicInteger(1);
        private final ThreadGroup group;
        private final AtomicInteger threadNumber = new AtomicInteger(1);
        private final String namePrefix;

        ThreadPoolFactory(String name) {
            SecurityManager s = System.getSecurityManager();
            group = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
            namePrefix = name+"-pool-" + poolNumber.getAndIncrement() + "-thread-";
        }

        public Thread newThread(Runnable r) {
            Thread t = new Thread(group, r, namePrefix + threadNumber.getAndIncrement(), 0);
            if (t.isDaemon())
                t.setDaemon(false);
            if (t.getPriority() != Thread.NORM_PRIORITY)
                t.setPriority(Thread.NORM_PRIORITY);
            return t;
        }
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

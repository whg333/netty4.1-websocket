package com.whg.websocket.server.framework.thread.pool;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class GlobalThreadPoolState implements PoolState {
	
	private final ThreadPoolExecutor executor;

	public GlobalThreadPoolState(int minThreadNum, int maxThreadNum, int queueTaskNum) {
		this(minThreadNum, maxThreadNum, queueTaskNum, "SynFightGlobalBusinessThreadPool-thread-");
	}

	public GlobalThreadPoolState(int minThreadNum, int maxThreadNum, int queueTaskNum, String prefix) {
		this.executor = new ThreadPoolExecutor(minThreadNum, maxThreadNum, 30000L, TimeUnit.MILLISECONDS,
				new LinkedBlockingQueue<Runnable>(queueTaskNum), new SynFightGlobalBusinessThreadFactory(prefix));
	}

	static class SynFightGlobalBusinessThreadFactory implements ThreadFactory {
		final ThreadGroup group;
		final AtomicInteger threadNumber = new AtomicInteger(1);
		final String namePrefix;

		SynFightGlobalBusinessThreadFactory(String namePrefix) {
			SecurityManager s = System.getSecurityManager();
			this.group = (s != null ? s.getThreadGroup() : Thread.currentThread().getThreadGroup());
			this.namePrefix = namePrefix;
		}

		public Thread newThread(Runnable r) {
			Thread t = new Thread(this.group, r, this.namePrefix + this.threadNumber.getAndIncrement(), 0L);
			if (t.isDaemon()) {
				t.setDaemon(false);
			}
			if (t.getPriority() != 5) {
				t.setPriority(5);
			}
			return t;
		}
	}

	public int getActiveThreadNum() {
		return this.executor.getActiveCount();
	}

	public int getQueueTaskNum() {
		return this.executor.getQueue().size();
	}

	public long getCompleteTaskNum() {
		return this.executor.getCompletedTaskCount();
	}

	public long getScheduleTaskNum() {
		return this.executor.getTaskCount();
	}

	public int getMaxThreadNumInHistory() {
		return this.executor.getLargestPoolSize();
	}

	public int getThreadNum() {
		return this.executor.getPoolSize();
	}

	public void execute(Runnable task) {
		this.executor.execute(task);
	}
}

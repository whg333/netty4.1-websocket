package com.whg.websocket.server.framework.thread.config;

public class PoolConfig {
	
	private String name;
	private int poolNum;
	private int minThreadNum;
	private int maxThreadNum;
	private int queueTaskNum;

	public PoolConfig() {
		
	}

	public PoolConfig(String name, int poolNum, int minThreadNum, int maxThreadNum, int queueTaskNum) {
		this.name = name;
		this.poolNum = poolNum;
		this.minThreadNum = minThreadNum;
		this.maxThreadNum = maxThreadNum;
		this.queueTaskNum = queueTaskNum;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPoolNum() {
		return poolNum;
	}

	public void setPoolNum(int poolNum) {
		this.poolNum = poolNum;
	}

	public int getMinThreadNum() {
		return minThreadNum;
	}

	public void setMinThreadNum(int minThreadNum) {
		this.minThreadNum = minThreadNum;
	}

	public int getMaxThreadNum() {
		return maxThreadNum;
	}

	public void setMaxThreadNum(int maxThreadNum) {
		this.maxThreadNum = maxThreadNum;
	}

	public int getQueueTaskNum() {
		return queueTaskNum;
	}

	public void setQueueTaskNum(int queueTaskNum) {
		this.queueTaskNum = queueTaskNum;
	}
}

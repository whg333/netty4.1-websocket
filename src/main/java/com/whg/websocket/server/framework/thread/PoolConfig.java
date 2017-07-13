package com.whg.websocket.server.framework.thread;

public class PoolConfig {
	
	public String name;
	public int poolNum;
	public int minThreadNum;
	public int maxThreadNum;
	public int queueTaskNum;

	public PoolConfig(String name, int poolNum, int minThreadNum, int maxThreadNum, int queueTaskNum) {
		this.name = name;
		this.poolNum = poolNum;
		this.minThreadNum = minThreadNum;
		this.maxThreadNum = maxThreadNum;
		this.queueTaskNum = queueTaskNum;
	}

}

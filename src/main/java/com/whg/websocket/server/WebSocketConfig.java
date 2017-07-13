package com.whg.websocket.server;

public class WebSocketConfig {

	public final int port;
	public final boolean keepalive;
	public final int backLog;
	
	public final int bossCount;
	public final int workerCount;

	public WebSocketConfig(int port, boolean keepalive, int backLog, int bossCount, int workerCount) {
		this.port = port;
		this.keepalive = keepalive;
		this.backLog = backLog;
		this.bossCount = bossCount;
		this.workerCount = workerCount;
	}
	
}

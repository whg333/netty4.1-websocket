package com.whg.websocket.server.framework.thread;

public interface ThreadPool {

	void execute(Runnable task);
	
}

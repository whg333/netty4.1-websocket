package com.whg.websocket.server.framework.thread;

public interface OrderedExecutor {

	void execute(int key, Runnable task);
	
}

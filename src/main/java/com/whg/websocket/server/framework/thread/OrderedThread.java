package com.whg.websocket.server.framework.thread;

public interface OrderedThread {

	void execute(int key, Runnable task);
	
}

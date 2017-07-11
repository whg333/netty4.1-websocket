package com.whg.websocket.server.framework.request;

public interface Request {

	String service();
	String method();
	String serviceMethod();
	Object[] args();
	
}

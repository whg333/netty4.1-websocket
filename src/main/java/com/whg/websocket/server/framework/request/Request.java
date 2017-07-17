package com.whg.websocket.server.framework.request;

import com.whg.websocket.server.framework.Player;

public interface Request {

	String service();
	String method();
	String serviceMethod();
	
	int argsCount();
	Object[] methodArgs(Player player, Class<?>[] argTypes) throws Exception;
	
}

package com.whg.websocket.server.framework.exception;

import com.whg.websocket.server.framework.Player;

public interface ExceptionHandler {

	void handleException(Player player, Throwable t);
	
}

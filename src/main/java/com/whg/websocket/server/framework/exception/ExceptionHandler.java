package com.whg.websocket.server.framework.exception;

import com.whg.websocket.server.framework.SynPlayer;

public interface ExceptionHandler {

	void handleException(SynPlayer player, Throwable t);
	
}

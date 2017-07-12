package com.whg.backend.bo.exception;

import org.springframework.stereotype.Component;

import com.whg.websocket.server.framework.Player;
import com.whg.websocket.server.framework.exception.ExceptionHandler;

@Component("exceptionHandler")
public class ExceptionHandlerImpl implements ExceptionHandler {

	@Override
	public void handleException(Player player, Throwable t) {
		if(player == null){
			return ;
		}
//		if(t instanceof BusinessException){
//			player.write(((BusinessException)t).getCommad());
//		}else{
//			player.write(new JsonErrMsg(ResponeText.ERROR, t.getMessage()));
//		}
		
		//player.write(new JsonErrMsg(ResponeText.ERROR, t.getMessage()));
		t.printStackTrace();
	}

}

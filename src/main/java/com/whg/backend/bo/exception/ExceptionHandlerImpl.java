package com.whg.backend.bo.exception;

import org.springframework.stereotype.Component;

import com.whg.websocket.server.framework.SynPlayer;
import com.whg.websocket.server.framework.exception.ExceptionHandler;

@Component("exceptionHandler")
public class ExceptionHandlerImpl implements ExceptionHandler {

	@Override
	public void handleException(SynPlayer synPlayer, Throwable t) {
		if(synPlayer == null){
			return ;
		}
//		if(t instanceof BusinessException){
//			synPlayer.write(((BusinessException)t).getCommad());
//		}else{
//			synPlayer.write(new JsonErrMsg(ResponeText.ERROR, t.getMessage()));
//		}
		
		//synPlayer.write(new JsonErrMsg(ResponeText.ERROR, t.getMessage()));
		t.printStackTrace();
	}

}

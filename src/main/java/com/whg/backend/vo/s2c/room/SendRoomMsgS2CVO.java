package com.whg.backend.vo.s2c.room;

import com.whg.websocket.server.framework.response.JsonResponse;

public class SendRoomMsgS2CVO implements JsonResponse{

	public final String name;
	public final String msg;
	
	public SendRoomMsgS2CVO(String name, String msg) {
		this.name = name;
		this.msg = msg;
	}
	
}

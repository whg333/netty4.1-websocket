package com.whg.backend.vo.s2c.room;

import com.whg.websocket.server.framework.response.JsonResponse;

public class JoinRoomS2CVO implements JsonResponse{

	public final String name;

	public JoinRoomS2CVO(String name) {
		this.name = name;
	}
	
}

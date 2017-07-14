package com.whg.backend.vo.s2c.room;

import com.whg.websocket.server.framework.Room;
import com.whg.websocket.server.framework.response.JsonResponse;

public class RoomS2CVO implements JsonResponse{

	public final Room room;

	public RoomS2CVO(Room room) {
		this.room = room;
	}
	
}

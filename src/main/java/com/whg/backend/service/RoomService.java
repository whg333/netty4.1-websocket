package com.whg.backend.service;

import com.whg.websocket.server.framework.Player;

public interface RoomService {

	void joinRoom(Player player, int roomId);
	
	void quitRoom(Player player, int roomId);
	
	void sendRoomMsg(Player player, int roomId, String msg);
	
}

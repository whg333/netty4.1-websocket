package com.whg.backend.service;

import com.whg.websocket.server.framework.Player;

public interface RoomService {

	void joinRoom(Player player, Integer roomId);
	
	void quitRoom(Player player, Integer roomId);
	
	void sendRoomMsg(Player player, Integer roomId, String msg);
	
}

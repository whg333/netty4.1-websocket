package com.whg.backend.service;

import java.util.Map;

import com.whg.websocket.server.framework.Player;

public interface UserService {

	Map<String, Object> login(Player player, String openid, String token);
	
	Map<String, Object> createRoom(Player player, int roomType);
	
	Map<String, Object> joinRoom(Player player, int roomNumber);
	
}

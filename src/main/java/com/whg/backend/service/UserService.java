package com.whg.backend.service;

import java.util.Map;

import com.whg.websocket.server.framework.SynPlayer;

public interface UserService {

	Map<String, Object> login(SynPlayer player, String openid, String token);
	
	Map<String, Object> createRoom(SynPlayer player, int roomType);
	
	Map<String, Object> joinRoom(SynPlayer player, int roomNumber);
	
}

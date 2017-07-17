package com.whg.backend.service;

import com.whg.websocket.server.framework.Player;

public interface UserService {

	void login(Player player, String openid, String token);
	
	void createRoom(Player player, Integer roomType);
	
}

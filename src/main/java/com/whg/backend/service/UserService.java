package com.whg.backend.service;

import com.whg.websocket.server.framework.SynPlayer;

public interface UserService {

	void login(SynPlayer player, String openid, String token);
	
	void createRoom(SynPlayer player, int roomType);
	
	void joinRoom(SynPlayer player, int roomNumber);
	
}

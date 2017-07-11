package com.whg.backend.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.whg.backend.service.UserService;
import com.whg.websocket.server.framework.SynPlayer;

@Service("userService")
public class UserServiceImpl implements UserService {

	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Override
	public void login(SynPlayer player, String openid, String token) {
		logger.info("login({}, {})", openid, token);
	}

	@Override
	public void createRoom(SynPlayer player, int roomType) {
		logger.info("createRoom({}, {})", player, roomType);
	}

	@Override
	public void joinRoom(SynPlayer player, int roomNumber) {
		logger.info("joinRoom({}, {})", player, roomNumber);
	}

}

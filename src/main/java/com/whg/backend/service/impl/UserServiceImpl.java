package com.whg.backend.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.whg.backend.bo.user.User;
import com.whg.backend.service.UserService;
import com.whg.util.exception.BusinessException;
import com.whg.util.exception.ErrorCode;
import com.whg.websocket.server.framework.SynPlayer;

@Service("userService")
public class UserServiceImpl implements UserService {

	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Override
	public Map<String, Object> login(SynPlayer player, String openid, String token) {
		logger.info("login({}, {})", openid, token);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("user", new User(1001, "whg"));
		return result;
	}

	@Override
	public Map<String, Object> createRoom(SynPlayer player, int roomType) {
		logger.info("createRoom({}, {})", player, roomType);
		Map<String, Object> result = new HashMap<String, Object>();
		//return result;
		throw new BusinessException(ErrorCode.BUILD_BUILDING_CAN_NOT_LEVEL_UP_IN_IMPAIRED);
	}

	@Override
	public Map<String, Object> joinRoom(SynPlayer player, int roomNumber) {
		logger.info("joinRoom({}, {})", player, roomNumber);
		Map<String, Object> result = new HashMap<String, Object>();
		//return result;
		throw new IllegalArgumentException("不可能出现的情况");
	}

}

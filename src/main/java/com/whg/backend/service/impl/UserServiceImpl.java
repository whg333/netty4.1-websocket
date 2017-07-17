package com.whg.backend.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whg.backend.bo.user.User;
import com.whg.backend.service.UserService;
import com.whg.backend.vo.s2c.room.RoomS2CVO;
import com.whg.backend.vo.s2c.user.LoginS2CVO;
import com.whg.util.annotation.GlobalScope;
import com.whg.websocket.bo.user.UserInfo;
import com.whg.websocket.server.framework.Player;
import com.whg.websocket.server.framework.Room;
import com.whg.websocket.server.framework.RoomContext;

@GlobalScope
@Service("userService")
public class UserServiceImpl implements UserService {

	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Autowired
	private RoomContext roomContext;
	
	@Override
	public void login(Player player, String openid, String token) {
		logger.info("login({}, {})", openid, token);
		User user = new User(1001, "whg");
		LoginS2CVO userMsg = new LoginS2CVO(user);
		player.fill(user);
		player.write(userMsg);
	}

	@Override
	public void createRoom(Player player, Integer roomType) {
		logger.info("createRoom({}, {})", player, roomType);
		Room room = roomContext.newRoom(player);
		RoomS2CVO roomMsg = new RoomS2CVO(room);
		player.write(roomMsg);
	}

}

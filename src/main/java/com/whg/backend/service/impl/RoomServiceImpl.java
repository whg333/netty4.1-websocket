package com.whg.backend.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whg.backend.service.RoomService;
import com.whg.backend.vo.s2c.room.JoinRoomS2CVO;
import com.whg.backend.vo.s2c.room.RoomS2CVO;
import com.whg.backend.vo.s2c.room.SendRoomMsgS2CVO;
import com.whg.util.annotation.GlobalScope;
import com.whg.websocket.server.framework.Player;
import com.whg.websocket.server.framework.Room;
import com.whg.websocket.server.framework.RoomContext;

@GlobalScope
@Service("roomService")
public class RoomServiceImpl implements RoomService {

	private static final Logger logger = LoggerFactory.getLogger(RoomServiceImpl.class);
	
	@Autowired
	private RoomContext roomContext;
	
	@Override
	public void joinRoom(Player player, Integer roomId) {
		logger.info("joinRoom({}, {})", player, roomId);
		
		Room room = roomContext.getRoom(roomId);
		room.addPlayer(player);
		
		RoomS2CVO roomMsg = new RoomS2CVO(room);
		player.write(roomMsg);
		
		JoinRoomS2CVO joinRoomMsg = new JoinRoomS2CVO(player.userName());
		room.writeExclude(joinRoomMsg, player.getId());
	}

	@Override
	public void quitRoom(Player player, Integer roomId) {
		// TODO Auto-generated method stub

		throw new IllegalArgumentException("不可能出现的情况");
		//throw new BusinessException(ErrorCode.BUILD_BUILDING_CAN_NOT_LEVEL_UP_IN_IMPAIRED);
	}

	@Override
	public void sendRoomMsg(Player player, Integer roomId, String msg) {
		logger.info("joinRoom({}, {}, {})", player, roomId, msg);
		Room room = roomContext.getRoom(roomId);
		SendRoomMsgS2CVO sendRoomMsg = new SendRoomMsgS2CVO(player.userName(), msg);
		room.writeAll(sendRoomMsg);
	}

}

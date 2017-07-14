package com.whg.websocket.server.framework;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class RoomContext {

	private final AtomicInteger roomGenerator = new AtomicInteger(0);
	
	private final ConcurrentHashMap<Integer, Room> roomMap = new ConcurrentHashMap<Integer, Room>();
	
	public Room newRoom(Player player){
		int roomId = roomGenerator.incrementAndGet();
		Room room = new Room(roomId, player);
		Assert.isNull(roomMap.putIfAbsent(roomId, room));
		return room;
	}
	
	public boolean removeRoom(int roomId){
		return roomMap.remove(roomId) != null;
	}
	
	public Room getRoom(int roomId){
		Room room = roomMap.get(roomId);
		if(room == null){
			throw new NullPointerException("未找到对应的roomId="+roomId+"的房间");
		}
		return room;
	}
	
	public int getMaxRoomId(){
		return roomGenerator.get();
	}
	
}

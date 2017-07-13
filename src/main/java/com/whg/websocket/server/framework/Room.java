package com.whg.websocket.server.framework;

import com.whg.util.collection.map.IntHashMap;

public class Room {

	protected final IntHashMap<Player> playerMap = new IntHashMap<Player>();
	protected final int id;
	
	public Room(int id) {
		this.id = id;
	}
	
	public boolean addPlayer(Player player){
		return playerMap.put(player.getPlayerId(), player) == null;
	}
	
	public boolean removePlayer(int playerId){
		return playerMap.remove(playerId) != null;
	}
	
	public Player findPlayer(int playerId){
		return playerMap.get(playerId);
	}
	
	public int playerSize(){
		return playerMap.size();
	}
	
	public void notify(int playerId, Object msg){
		Player player = findPlayer(playerId);
		if(player != null){
			player.write(msg);
		}
	}
	
	public void notifyAll(Object msg){
		for(Player player:playerMap.values()){
			player.write(msg);
		}
	}
	
}

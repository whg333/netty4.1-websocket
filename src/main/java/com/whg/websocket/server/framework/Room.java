package com.whg.websocket.server.framework;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.util.Assert;

import com.whg.backend.bo.user.User;

public class Room {

	protected final ConcurrentHashMap<Integer, Player> playerMap = new ConcurrentHashMap<Integer, Player>();
	
	protected final int id;
	protected final Player lord;
	
	public Room(int id, Player player) {
		this.id = id;
		lord = player;
		Assert.isTrue(addPlayer(lord));
	}
	
	public boolean addPlayer(Player player){
		return playerMap.putIfAbsent(player.getId(), player) == null;
	}
	
	public boolean removePlayer(int playerId){
		return playerMap.remove(playerId) != null;
	}
	
	public Player getPlayer(int playerId){
		return playerMap.get(playerId);
	}
	
	public int playerSize(){
		return playerMap.size();
	}
	
	public void writeAll(Object msg){
		for(Player player:playerMap.values()){
			player.write(msg);
		}
	}
	
	public void writeInclude(Object msg, int... playerIds){
		for(int playerId:playerIds){
			Player player = getPlayer(playerId);
			if(player != null){
				player.write(msg);
			}
		}
	}
	
	public void writeExclude(Object msg, int... playerIds){
		for(Player player:playerMap.values()){
			boolean exclude = false;
			for(int playerId:playerIds){
				if(playerId == player.getId()){
					exclude = true;
					break;
				}
			}
			if(!exclude){
				player.write(msg);
			}
		}
	}
	
	public Collection<User> getUsers(){
		List<User> users = new ArrayList<User>(playerMap.size());
		for(Player player:playerMap.values()){
			users.add(player.getUser());
		}
		return users;
	}
	
	public int getId(){
		return id;
	}
	public User getLord(){
		return lord.getUser();
	}
	
}

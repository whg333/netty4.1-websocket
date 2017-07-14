package com.whg.websocket.server.framework;

import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class GlobalContext {

	private final ConcurrentHashMap<Integer, Player> connectPlayerMap = new ConcurrentHashMap<Integer, Player>();
	private final ConcurrentHashMap<Integer, Player> loginPlayerMap = new ConcurrentHashMap<Integer, Player>();

	public boolean addConnect(Player player) {
		return null == this.connectPlayerMap.putIfAbsent(player.getId(), player);
	}

	public boolean removeConnect(int id) {
		return null != connectPlayerMap.remove(id);
	}
	
	public Player getConnect(int id) {
		return connectPlayerMap.get(id);
	}

	public ConcurrentHashMap<Integer, Player> connectPlayers() {
		return connectPlayerMap;
	}

	public boolean addLogin(int userId, Player player){
		return null == loginPlayerMap.putIfAbsent(userId, player);
	}
	
	public boolean removeLogin(int id) {
		return null != loginPlayerMap.remove(id);
	}
	
	public Player getLogin(int id) {
		return loginPlayerMap.get(id);
	}
	
	public ConcurrentHashMap<Integer, Player> loginPlayers() {
		return loginPlayerMap;
	}

	
	public Player replaceLogin(int userId, Player player){
		Player loginPlayer = loginPlayerMap.remove(userId);
		Assert.notNull(loginPlayer);
		Assert.isTrue(player.getId() != loginPlayer.getId());
		Assert.isTrue(userId == loginPlayer.userId());
		Assert.isNull(loginPlayerMap.putIfAbsent(userId, player));
		loginPlayer.repeatLogin();
		loginPlayer.closeRepeatLogin();
		return loginPlayer;
	}
}

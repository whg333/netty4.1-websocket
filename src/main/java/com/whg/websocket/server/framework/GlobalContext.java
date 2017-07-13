package com.whg.websocket.server.framework;

import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class GlobalContext {

	private final ConcurrentHashMap<Integer, Player> connectPlayers = new ConcurrentHashMap<Integer, Player>();
	private final ConcurrentHashMap<Integer, Player> loginPlayers = new ConcurrentHashMap<Integer, Player>();

	public boolean addConnect(Player player) {
		return null == this.connectPlayers.putIfAbsent(player.getPlayerId(), player);
	}

	public Player getConnect(int id) {
		return connectPlayers.get(id);
	}
	
	public boolean removeConnect(int id) {
		return null != connectPlayers.remove(id);
	}

	public ConcurrentHashMap<Integer, Player> connectPlayers() {
		return connectPlayers;
	}

	public boolean addLogin(int userId, Player player){
		return null == loginPlayers.putIfAbsent(userId, player);
	}
	
	public Player getLogin(int id) {
		return loginPlayers.get(id);
	}

	public boolean removeLogin(int id) {
		return null != loginPlayers.remove(id);
	}
	
	public ConcurrentHashMap<Integer, Player> loginPlayers() {
		return loginPlayers;
	}

	
	public Player replaceLogin(int userId, Player player){
		Player loginPlayer = loginPlayers.remove(userId);
		Assert.notNull(loginPlayer);
		Assert.isTrue(player.getPlayerId() != loginPlayer.getPlayerId());
		Assert.isTrue(userId == loginPlayer.getUserId());
		Assert.isNull(loginPlayers.putIfAbsent(userId, player));
		loginPlayer.repeatLogin();
		loginPlayer.closeRepeatLogin();
		return loginPlayer;
	}
}

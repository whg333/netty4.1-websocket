package com.whg.websocket.server.framework;

import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class GlobalServer {

	private final ConcurrentHashMap<Integer, SynPlayer> connectPlayers = new ConcurrentHashMap<Integer, SynPlayer>();
	private final ConcurrentHashMap<Integer, SynPlayer> loginPlayers = new ConcurrentHashMap<Integer, SynPlayer>();

	public boolean addConnect(SynPlayer synPlayer) {
		return null == this.connectPlayers.putIfAbsent(synPlayer.getPlayerId(), synPlayer);
	}

	public SynPlayer getConnect(int id) {
		return connectPlayers.get(id);
	}
	
	public boolean removeConnect(int id) {
		return null != connectPlayers.remove(id);
	}

	public ConcurrentHashMap<Integer, SynPlayer> connectPlayers() {
		return connectPlayers;
	}

	public boolean addLogin(int userId, SynPlayer synPlayer){
		return null == loginPlayers.putIfAbsent(userId, synPlayer);
	}
	
	public SynPlayer getLogin(int id) {
		return loginPlayers.get(id);
	}

	public boolean removeLogin(int id) {
		return null != loginPlayers.remove(id);
	}
	
	public ConcurrentHashMap<Integer, SynPlayer> loginPlayers() {
		return loginPlayers;
	}

	
	public SynPlayer replaceLogin(int userId, SynPlayer player){
		SynPlayer loginPlayer = loginPlayers.remove(userId);
		Assert.notNull(loginPlayer);
		Assert.isTrue(player.getPlayerId() != loginPlayer.getPlayerId());
		Assert.isTrue(userId == loginPlayer.getUserId());
		Assert.isNull(loginPlayers.putIfAbsent(userId, player));
		loginPlayer.repeatLogin();
		loginPlayer.closeRepeatLogin();
		return loginPlayer;
	}
}

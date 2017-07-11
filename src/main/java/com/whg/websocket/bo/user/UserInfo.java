package com.whg.websocket.bo.user;

import com.whg.backend.bo.user.User;

public class UserInfo {
	
	private final User user;
	
	public UserInfo(User user) {
		this.user = user;
	}

	public long getUserId() {
		return user.getId();
	}

}

package com.whg.backend.vo.s2c.user;

import com.whg.backend.bo.user.User;
import com.whg.websocket.server.framework.response.JsonResponse;

public class UserS2CVO implements JsonResponse{

	public final User user;

	public UserS2CVO(User user) {
		this.user = user;
	}
	
}

package com.whg.backend.vo.s2c.user;

import com.whg.backend.bo.user.User;
import com.whg.protobuf.S2CProtobuf.LoginS2CVOProto;
import com.whg.websocket.server.framework.response.ProtobufResponse;

public class LoginS2CVO implements ProtobufResponse<LoginS2CVOProto>{

	public final User user;

	public LoginS2CVO(User user) {
		this.user = user;
	}

	@Override
	public byte[] toByteArray() {
		return copyTo().toByteArray();
	}

	@Override
	public LoginS2CVOProto copyTo() {
		LoginS2CVOProto.Builder builder = LoginS2CVOProto.newBuilder();
		builder.setUser(user.copyTo());
		return builder.build();
	}
	
}

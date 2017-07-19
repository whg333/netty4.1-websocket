package com.whg.backend.bo.user;

import com.google.protobuf.InvalidProtocolBufferException;
import com.whg.backend.bo.BoFactory;
import com.whg.protobuf.BoProtobuf.UserProto;
import com.whg.websocket.server.framework.protobuf.ProtobufSerializable;

public class User implements ProtobufSerializable<UserProto>{
	
	private long id;
	private String openid = "";
	
	private String name;
	private String image;
	
	public User(){
		
	}
	
	public User(long id, String name){
		this.id = id;
		this.name = name;
	}
	
	public User(BoFactory boFactory, byte[] bytes) {
	}

	@Override
	public void parseFrom(byte[] bytes) {
		try {
			copyFrom(UserProto.parseFrom(bytes));
		} catch (InvalidProtocolBufferException ex) {
			throw new IllegalArgumentException(ex);
		}
	}

	@Override
	public void copyFrom(UserProto proto) {
		id = proto.getId();
		openid = proto.getOpenid();
		name = proto.getName();
		image = proto.getImage();
	}

	@Override
	public byte[] toByteArray() {
		return copyTo().toByteArray();
	}

	@Override
	public UserProto copyTo() {
		UserProto.Builder builder = UserProto.newBuilder();
		builder.setId(id);
		builder.setOpenid(openid);
		if(name != null){
			builder.setName(name);
		}
		if(image != null){
			builder.setImage(image);
		}
		return builder.build();
	}

	public long getId() {
		return id;
	}

	public String getOpenid() {
		return openid;
	}

	public String getName() {
		return name;
	}

	public String getImage() {
		return image;
	}

	public boolean refresh() {
		return false;
	}

}

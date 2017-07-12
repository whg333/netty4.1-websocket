package com.whg.backend.bo.user;

public class User{
	
	private String openid = "";
	private long id;
	
	private String name;
	private String img;
	
	public User(){
		
	}
	
	public User(long id, String name){
		this.id = id;
		this.name = name;
	}
	
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}

}

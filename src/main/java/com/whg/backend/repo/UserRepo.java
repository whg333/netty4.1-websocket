package com.whg.backend.repo;

import com.whg.backend.bo.user.User;

public interface UserRepo {

long getUserNum();
	
	long getMaxUserId();
	
	void checkUserExist(long userId);
	
	boolean userExist(long userId);
	
	void checkUserExist(String openid);
	
	boolean userExist(String openid);
	
	long getUniqueId();
	
	void addOpenid(long userId, String openid);
	
	String findOpenid(long userId);
	
	long findUserId(String openid);
	
	void addUser(User user);
	
	User findUser(long userId);
	
	boolean saveUser(User user);
	
}

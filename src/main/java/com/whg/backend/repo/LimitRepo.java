package com.whg.backend.repo;

public interface LimitRepo {

	boolean addOperateTime(long userId, long time);
	
	byte[] findOperateTime(long userId);
	
	void saveOpenrateTime(long userId, long time);
	
	boolean removeOperateTime(long userId);
	
}

package com.whg.backend.repo.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.skylinematrix.jredis.JRedis;
import com.whg.backend.repo.LimitRepo;
import com.whg.backend.repo.key.UserKey;
import com.whg.util.bit.BitConverter;

@Repository
public class LimitRepoImpl implements LimitRepo {

	@Autowired
    private JRedis jredis;
	
	@Override
	public boolean addOperateTime(long userId, long time) {
		String key = UserKey.getOperateTimeKey(userId);
		if(jredis.exists(key)){
			return false;
		}
		return jredis.setnx(key, BitConverter.long2bytes(time));
	}

	@Override
	public byte[] findOperateTime(long userId) {
		String key = UserKey.getOperateTimeKey(userId);
		return jredis.get(key);
	}

	@Override
	public void saveOpenrateTime(long userId, long time) {
		String key = UserKey.getOperateTimeKey(userId);
		jredis.set(key, BitConverter.long2bytes(time)); 
	}
	
	@Override
	public boolean removeOperateTime(long userId) {
		String key = UserKey.getOperateTimeKey(userId);
		return jredis.del(key);
	}

}

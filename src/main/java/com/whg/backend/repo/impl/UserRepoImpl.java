package com.whg.backend.repo.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.skylinematrix.jredis.JRedis;
import com.whg.backend.bo.BoFactory;
import com.whg.backend.bo.user.User;
import com.whg.backend.repo.UserRepo;
import com.whg.backend.repo.key.ServerKey;
import com.whg.backend.repo.key.UserKey;
import com.whg.util.bit.BitConverter;
import com.whg.util.exception.BusinessException;
import com.whg.util.exception.ErrorCode;
import com.whg.util.string.StringUtil;

@Repository("userRepo")
public class UserRepoImpl implements UserRepo {
	
	@Autowired
	private JRedis jredis;
	
	@Autowired
	private BoFactory boFactory;
	
	@Override
	public long getUserNum(){
		return getMaxUserId() - UserKey.BEGIN_USER_ID;
	}
	
	@Override
    public long getMaxUserId() {
		String key = ServerKey.getUniqueIdKey();
        return Long.parseLong(StringUtil.encode(jredis.get(key)));
    }
	
	@Override
    public long getUniqueId() {
		String key = ServerKey.getUniqueIdKey();
		if(!jredis.exists(key)){
			if(!jredis.setnx(key, StringUtil.encode(UserKey.BEGIN_USER_ID_STR))){
				throw new BusinessException(ErrorCode.COMMON_ADD_KEY_ERROR);
			}
		}
        long newId = jredis.incr(key);
        if (newId < 0) {
            throw new BusinessException(ErrorCode.COMMON_SYSTEM_ERROR);
        }
        return newId;
    }
	
	@Override
	public void checkUserExist(long userId) {
		if(!userExist(userId)){
			throw new BusinessException(ErrorCode.LOGIN_USER_IS_NULL.code, ErrorCode.LOGIN_USER_IS_NULL.msg+", userId="+userId);
		}
	}
	
	@Override
	public boolean userExist(long userId) {
		String uid2Oidkey = ServerKey.getUid2OidKey();
		byte[] userIdBytes = BitConverter.long2bytes(userId);
        return jredis.hexists(uid2Oidkey, userIdBytes);
	}
	
	@Override
	public void checkUserExist(String openid) {
		if(!userExist(openid)){
			throw new BusinessException(ErrorCode.LOGIN_USER_IS_NULL.code, ErrorCode.LOGIN_USER_IS_NULL.msg+", openid="+openid);
		}
	}
	
	@Override
	public boolean userExist(String openid) {
		String oid2Uidkey = ServerKey.getOid2UidKey();
		String oid2UidSubKey = openid;
        return jredis.hexists(oid2Uidkey, oid2UidSubKey);
	}
	
	@Override
    public void addOpenid(long userId, String openid) {
		String oid2Uidkey = ServerKey.getOid2UidKey();
        byte[] userIdBytes = BitConverter.long2bytes(userId);
        if(!jredis.hsetnx(oid2Uidkey, openid, userIdBytes)){
        	throw new BusinessException(ErrorCode.COMMON_ADD_KEY_ERROR);
        }
        String uid2OidKey = ServerKey.getUid2OidKey();
        if(!jredis.hsetnx(uid2OidKey, userIdBytes, StringUtil.encode(openid))){
        	throw new BusinessException(ErrorCode.COMMON_ADD_KEY_ERROR);
        }
    }
	
	@Override
	public String findOpenid(long userId) {
		String uid2OidKey = ServerKey.getUid2OidKey();
        byte[] userIdSubKey = BitConverter.long2bytes(userId);
        byte[] bytes = jredis.hget(uid2OidKey, userIdSubKey);
        if(bytes == null){
        	throw new BusinessException(ErrorCode.COMMON_STORAGE_ERROR);
        }
		return StringUtil.encode(bytes);
	}
	
	@Override
	public long findUserId(String openid) {
		String oid2Uidkey = ServerKey.getOid2UidKey();
        String oid2UidSubKey = openid;
        byte[] bytes = jredis.hget(oid2Uidkey, oid2UidSubKey);
        if(bytes == null){
        	throw new BusinessException(ErrorCode.COMMON_STORAGE_ERROR);
        }
        return BitConverter.bytes2long(bytes);
    }

	@Override
	public void addUser(User user) {
		long userId = user.getId();
		if(userExist(userId)){
			throw new BusinessException(ErrorCode.COMMON_EXIST_KEY_ERROR);
		}
		if(userExist(user.getOpenid())){
			throw new BusinessException(ErrorCode.COMMON_EXIST_KEY_ERROR);
		}
		String key = UserKey.getUserKey(userId);
		if(!jredis.setnx(key, user.toByteArray())){
			throw new BusinessException(ErrorCode.COMMON_ADD_KEY_ERROR);
		}
	}
	
	@Override
	public User findUser(long userId) {
		String key = UserKey.getUserKey(userId);
		byte[] bytes = jredis.get(key);
		User user;
		if(bytes == null){
			//throw new BusinessException(ErrorCode.LOGIN_USER_IS_NULL.code, ErrorCode.LOGIN_USER_IS_NULL.msg+", userId="+userId);
			user = new User();
			saveUser(user);
		}else{
			user = new User(boFactory, bytes);
		}
		
		if(user.refresh()){
			saveUser(user);
		}
		return user;
	}
	
	@Override
	public boolean saveUser(User user) {
		String key = UserKey.getUserKey(user.getId());
		boolean result = jredis.set(key, user.toByteArray());
		if(result){
			
		}
		return result;
	}

}

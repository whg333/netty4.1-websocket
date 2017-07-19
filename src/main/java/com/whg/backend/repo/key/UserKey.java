
package com.whg.backend.repo.key;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.skylinematrix.jredis.JRedisKey;
import com.whg.util.bit.BitConverter;

public class UserKey {
	
	public static final long BEGIN_USER_ID = 1000;
	public static final String BEGIN_USER_ID_STR = BEGIN_USER_ID+"";
	
	/** 把long形式的userId构造成用户的key */
    public static List<String> getLongUserKeys(Collection<Long> userIds){
    	List<String> keys = new ArrayList<String>(userIds.size());
    	for(Long userId:userIds){
    		keys.add(getUserKey(userId));
    	}
    	return keys;
	}
    
    /** 把byte[]形式的userId构造成用户的key */
    public static List<String> getByteUserKeys(Collection<byte[]> userIdBytesSet){
    	List<String> userKeys = new ArrayList<String>(userIdBytesSet.size());
    	for(byte[] bytes:userIdBytesSet){
    		userKeys.add(getUserKey(BitConverter.bytes2long(bytes)));
    	}
		return userKeys;
	}
	
    public static String getUserKey(long userId) {
    	return JRedisKey.newKey("user:"+KeyInfo.version, userId).toString();
    }
    
    public static String getUserRecordKey(long userId) {
    	return JRedisKey.newKey("userRecord:"+KeyInfo.version, userId).toString();
    }
    
    public static String getOperateTimeKey(long userId){
    	return JRedisKey.newKey("operateTime:"+KeyInfo.version, userId).toString();
    }
    
    public static String getAccountStateKey(long userId){
    	return JRedisKey.newKey("accountState:"+KeyInfo.version, userId).toString();
    }
    
    public static String getUserStatisKey(long userId){
    	return JRedisKey.newKey("userStatis:"+KeyInfo.version, userId).toString();
    }
    
    /** 把long形式的userId构造成岛屿的key */
    public static List<String> getLandKeys(Collection<Long> userIds){
    	List<String> keys = new ArrayList<String>(userIds.size());
    	for(Long userId:userIds){
    		keys.add(getLandKey(userId));
    	}
    	return keys;
	}
    
    public static String getLandKey(long userId) {
    	return JRedisKey.newKey("land:"+KeyInfo.version, userId).toString();
    }
    
    /** 把long形式的userId构造成小游戏的key */
    public static List<String> getMiniGamesKeys(Collection<Long> userIds){
    	List<String> keys = new ArrayList<String>(userIds.size());
    	for(Long userId:userIds){
    		keys.add(getMiniGamesKey(userId));
    	}
    	return keys;
	}
    
    public static String getMiniGamesKey(long userId) {
    	return JRedisKey.newKey("miniGames:"+KeyInfo.version, userId).toString();
    }

}

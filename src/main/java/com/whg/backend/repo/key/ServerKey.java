package com.whg.backend.repo.key;

import com.skylinematrix.jredis.JRedisKey;

public class ServerKey {
	
	private static final String prefix_server = "server:";
	
	public static String getAnnounceKey() {
        return JRedisKey.newKey(prefix_server+"announce"+KeyInfo.version).toString();
    }

	public static String getGlobalVariableKey() {
		return JRedisKey.newKey(prefix_server+"gloVar"+KeyInfo.version).toString();
	}
	
	/** openid -> userId */
	public static String getOid2UidKey(){
		return JRedisKey.newKey(prefix_server+"oid2uidMap"+KeyInfo.version).toString();
	}
	
    /** userId -> openid */
	public static String getUid2OidKey() {
    	return JRedisKey.newKey(prefix_server+"uid2oidMap"+KeyInfo.version).toString();
    }
	
	public static String getUniqueIdKey() {
		return JRedisKey.newKey(prefix_server+"dcUidIncr"+KeyInfo.version).toString(); //DragonComming UniqueId for short
	}
	
	/** 岛屿不为空时，可以被攻击的userId集合 */
	public static String getAttackUserSetKey(){
		return JRedisKey.newKey(prefix_server+"attUidSet"+KeyInfo.version).toString();
	}
	
	public static String getAttackNpcSetKey(){
		return JRedisKey.newKey(prefix_server+"attNpcSet"+KeyInfo.version).toString();
	}
	
	public static String getGuideNpcSetKey(int shield){
		return JRedisKey.newKey(prefix_server+"guideNpcSet"+KeyInfo.version+shield).toString();
	}
    
	public static String getLikeLandNumKey() {
		return JRedisKey.newKey(prefix_server+"likeLandNum"+KeyInfo.version).toString();
	}
	
    public static void main(String[] args) {
		System.out.println("whg".hashCode());
		System.out.println(sumASCII("whg"));
		System.out.println(sumASCII("w"));
		System.out.println(sumASCII("h"));
		System.out.println(sumASCII("g"));
	}
	
    /** 把传递的String、参数进行ASCII码加和 */
	public static final long sumASCII(String s){
		long sumASCII = 0;
		for(char c:s.toCharArray()){
			sumASCII += c;
		}
		return sumASCII;
	}
	
}

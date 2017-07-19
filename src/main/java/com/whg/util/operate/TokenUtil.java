package com.whg.util.operate;

import com.whg.util.string.StringUtil;
import com.whg.util.time.TimeUtil;

public class TokenUtil {
	
    private static final String produceTokenAttachStr = "             dragonComming@skylinematrix.com          ";
    
    private static final String worldBattleProduceTokenAttachStr = "           worldbattle.sango@skylinematrix.com          ";
    
    private static final String worldBattleProduceTokenAttachStrV2 = "           worldbattleV2.sango@skylinematrix.com          ";
    
    private static final String weixinProduceTokenAttachStr = "            weixin.dragonComming@skylinematrix.com            ";
    
    private static final String businessProduceTokenAttachStr = "      startbusiness.dragonComming@skylinematrix.com         ";
    
    public static String produceToken(String userIdStr) {
        return StringUtil.encryptToMd5(userIdStr + produceTokenAttachStr);
    }
    
    public static String produceWorldBattleToken(String userIdStr) {
        if(userIdStr == null) {
            userIdStr = "";
        }
        return StringUtil.encryptToMd5(userIdStr + worldBattleProduceTokenAttachStr);
    }
    
    public static String produceWorldBattleTokenV2(String userIdStr) {
        if(userIdStr == null) {
            userIdStr = "";
        }
        return StringUtil.encryptToMd5(userIdStr + worldBattleProduceTokenAttachStrV2);
    }
    
    public static String produceGetUserInfoToken(String openid) {
        return StringUtil.encryptToMd5(openid + produceTokenAttachStr);
    }
    
    public static String produceLoginIdentifyingCodeToken(String userIdStr) {
        return StringUtil.encryptToMd5(userIdStr + produceTokenAttachStr + "skyline&*^*matrix~@12"+TimeUtil.currentTimeMillis());
    }
    
    public static String produceBusinessToken(String userIdStr) {
        return StringUtil.encryptToMd5(userIdStr + businessProduceTokenAttachStr + "skylinematrix&*^*huai~@12"+TimeUtil.currentTimeMillis());
    }
    
    public static String produceWeiXinToken(){
    	return StringUtil.encryptToMd5(weixinProduceTokenAttachStr + "skylinematrix&*^*huai~@12"+TimeUtil.currentTimeMillis());
    }
    
    public static void main(String[] args) {
    	for(int i=0;i<5;i++){
    		System.out.println(produceWeiXinToken());
    	}
	}
}

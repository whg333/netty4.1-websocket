package com.whg.util.operate;

import com.whg.util.Constant;
import com.whg.util.string.StringUtil;
import com.whg.util.time.TimeUtil;

public class AuthValidation {
	
	private static final String prefix = "skyline!@#$%^";
    private static final String endfix = "!@$%^matrix";
    private static final int expireHour = 12;

    private long userId;
    private long currentTime;

    private String auth;
    private long genearatedTime;

    public AuthValidation(long userId) {
        this.userId = userId;
        currentTime = TimeUtil.currentTimeHour();
    }

    public AuthValidation(long userId, String auth) {
        this(userId);

        int index = auth.lastIndexOf(Constant.separator);
        genearatedTime = Long.parseLong(auth.substring(index + 1));

        this.auth = auth.substring(0, index);
    }

    public String currentAuth() {
        return generateAuth(currentTime) + Constant.separator + currentTime;
    }
    
    public void checkAuth() {
        if (!isAuthValid()) {
            throw new RuntimeException("secret validate fail");
        }
    }
    
    public boolean isAuthValid() {
        if (this.genearatedTime < currentTime - expireHour){
        	return false;
        }
        return generateAuth(genearatedTime).equals(auth);
    }
    
    private String generateAuth(long time) {
        return StringUtil.encryptToMd5(prefix + userId + Constant.separator + time + endfix).substring(8, 24);
    }

}

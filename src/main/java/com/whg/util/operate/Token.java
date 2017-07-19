package com.whg.util.operate;

import org.springframework.util.Assert;

import com.whg.util.exception.BusinessException;
import com.whg.util.exception.ErrorCode;

/**
 * <p>封装前端请求传递的token，后端用来做校验</p>
 * @author wanghg
 * @date 2017年5月31日 下午2:59:13
 */
public class Token {
	
	private static final String separator = ":";
	
	public final long userId;
	public final String auth;
	public final String idCode;
	
	public Token(long userId) {
		this(userId, null, null);
	}
	
	public Token(long userId, String auth, String idCode) {
		this.userId = userId;
		this.auth = auth;
		this.idCode = idCode;
	}
	
	public Token(long userId, String tokenStr){
		this.userId = userId;
		String[] tokenStrArray = checkAndParse(tokenStr);
		Assert.isTrue(tokenStrArray.length == 2);
		this.auth = tokenStrArray[0];
		this.idCode = tokenStrArray[1];
	}
	
	private String[] checkAndParse(String tokenStr){
		if(tokenStr == null){
			throw new BusinessException(ErrorCode.LOGIN_IS_EXPIRED, "token is null，请刷新游戏或重新登录");
		}
		String[] tokenStrArray = tokenStr.split(separator);
		if(tokenStrArray.length != 2){
			throw new BusinessException(ErrorCode.LOGIN_IS_EXPIRED, "token validate fail，请刷新游戏或重新登录");
		}
		return tokenStrArray;
	}
	
	public String toClientStr(){
		return auth + separator + idCode;
	}

}

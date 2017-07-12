package com.whg.util.exception;

public class BusinessExceptionJson {

	private final BusinessException be;

	public BusinessExceptionJson(BusinessException be) {
		this.be = be;
	}
	
	public int getErrorCode(){
		return be.code();
	}
	public String getErrorMsg(){
		return be.msg();
	}
	
}

package com.whg.util.exception;

@SuppressWarnings("serial")
public class BusinessException extends RuntimeException {

	private final ErrorCode errorCode;
	
	public BusinessException(ErrorCode errorCode){
		super(errorCode.msg);
		this.errorCode = errorCode;
	}
	
	public BusinessException(int code, String msg){
		super(msg);
		this.errorCode = ErrorCode.get(code);
	}

	public int code() {
		return errorCode.code;
	}
	public String msg() {
		return super.getMessage();
	}
	
}

package com.whg.websocket.server.framework.method;

public interface MethodInvoker {

	String name();
	
	Class<?>[] argTypes();
	
	Object invoke(Object[] args);
	
	public static String name(String service, String method){
		return service+"."+method;
	}
	
}

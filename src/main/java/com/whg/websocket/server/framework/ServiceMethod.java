package com.whg.websocket.server.framework;

import org.springframework.cglib.reflect.FastMethod;

public class ServiceMethod {

	private final String name;
	private final Object service;
	private final FastMethod fastMethod;
	private final int argsLength;

	public ServiceMethod(String name, Object service, FastMethod fastMethod) {
		this.name = name;
		this.service = service;
		this.fastMethod = fastMethod;
		this.argsLength = fastMethod.getParameterTypes().length;
	}

	public void invoke(Object[] args) throws Exception {
		if (args.length != this.argsLength) {
			throw new IllegalArgumentException("ServiceMethod Mismatch args length! ServiceMethod=" + name
					+ " expect " + (this.argsLength - 1) + " but Front Pass actual " + (args.length - 1));
		}
		this.fastMethod.invoke(this.service, args);
	}

	@Override
	public String toString() {
		return name + ":" + service.getClass().getName() + "." + fastMethod.getName();
	}

	public static String name(String service, String method){
		return service+"."+method;
	}
	
}

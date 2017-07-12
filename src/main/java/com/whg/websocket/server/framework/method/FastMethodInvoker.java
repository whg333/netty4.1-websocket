package com.whg.websocket.server.framework.method;

import java.lang.reflect.InvocationTargetException;

import org.springframework.cglib.reflect.FastMethod;

public class FastMethodInvoker implements MethodInvoker{

	private final String name;
	private final Object service;
	private final FastMethod fastMethod;
	private final int argsLength;

	public FastMethodInvoker(String serviceName, String methodName, Object service, FastMethod fastMethod) {
		this.name = MethodInvoker.name(serviceName, methodName);
		this.service = service;
		this.fastMethod = fastMethod;
		this.argsLength = fastMethod.getParameterTypes().length;
	}
	
	@Override
	public String name(){
		return name;
	}

	@Override
	public void invoke(Object[] args) {
		if (args.length != this.argsLength) {
			throw new IllegalArgumentException("ServiceMethod Mismatch args length! ServiceMethod=" + name
					+ " expect " + (this.argsLength - 1) + " but Front Pass actual " + (args.length - 1));
		}
		try {
			this.fastMethod.invoke(this.service, args);
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String toString() {
		return /*name + ":" + */service.getClass().getName() + "." + fastMethod.getName();
	}

}

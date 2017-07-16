package com.whg.websocket.server.framework.method;

import java.lang.reflect.InvocationTargetException;

import org.springframework.cglib.reflect.FastMethod;

public class FastMethodInvoker implements MethodInvoker{

	private final String name;
	private final Object service;
	private final FastMethod fastMethod;
	private final Class<?>[] argTypes;

	public FastMethodInvoker(String serviceName, String methodName, Object service, FastMethod fastMethod) {
		this.name = MethodInvoker.name(serviceName, methodName);
		this.service = service;
		this.fastMethod = fastMethod;
		this.argTypes = fastMethod.getParameterTypes();
	}
	
	@Override
	public String name(){
		return name;
	}
	
	@Override
	public Class<?>[] argTypes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void invoke(Object[] args) {
		if (args.length != argTypes.length) {
			throw new IllegalArgumentException("ServiceMethod Mismatch args length! ServiceMethod=" + name
					+ " expect " + (argTypes.length - 1) + " but Front Pass actual " + (args.length - 1));
		}
		try {
			fastMethod.invoke(service, args);
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}

}

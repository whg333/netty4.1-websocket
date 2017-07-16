package com.whg.websocket.server.framework.method;

import com.esotericsoftware.reflectasm.MethodAccess;

public class MethodAccessInvoker implements MethodInvoker{

	private final String name;
	private final Object service;
	private final MethodAccess access;
	private final int methodIndex;
	private final Class<?>[] argTypes;

	public MethodAccessInvoker(String serviceName, String methodName, Object service, MethodAccess access) {
		this.name = MethodInvoker.name(serviceName, methodName);
		this.service = service;
		this.access = access;
		this.methodIndex = access.getIndex(methodName);
		this.argTypes = access.getParameterTypes()[methodIndex];
	}
	
	@Override
	public String name(){
		return name;
	}
	
	@Override
	public Class<?>[] argTypes() {
		return argTypes;
	}

	@Override
	public void invoke(Object[] args) {
		if (args.length != argTypes.length) {
			throw new IllegalArgumentException("ServiceMethod Mismatch args length! ServiceMethod=" + name
					+ " expect " + (argTypes.length - 1) + " but Front Pass actual " + (args.length - 1));
		}
		access.invoke(service, methodIndex, args);
	}
	
}

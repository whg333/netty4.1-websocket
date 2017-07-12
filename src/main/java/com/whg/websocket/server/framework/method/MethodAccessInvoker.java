package com.whg.websocket.server.framework.method;

import com.esotericsoftware.reflectasm.MethodAccess;

public class MethodAccessInvoker implements MethodInvoker{

	private final String name;
	private final Object service;
	private final MethodAccess access;
	private final String methodName;
	private final int methodIndex;
	private final int argsLength;

	public MethodAccessInvoker(String serviceName, String methodName, Object service, MethodAccess access) {
		this.name = MethodInvoker.name(serviceName, methodName);
		this.service = service;
		this.access = access;
		this.methodName = methodName;
		this.methodIndex = access.getIndex(methodName);
		this.argsLength = access.getParameterTypes()[methodIndex].length;
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
		access.invoke(service, methodIndex, args);
	}

	@Override
	public String toString() {
		return /*name + ":" + */service.getClass().getName() + "." + methodName;
	}
	
}

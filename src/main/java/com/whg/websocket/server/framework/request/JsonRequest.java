package com.whg.websocket.server.framework.request;

import com.whg.websocket.server.framework.Player;
import com.whg.websocket.server.framework.method.MethodInvoker;

public class JsonRequest implements Request{

	public String s;
	public String m;
	public Object[] args;
	
	@Override
	public String service() {
		return s;
	}
	@Override
	public String method() {
		return m;
	}
	@Override
	public String serviceMethod() {
		return MethodInvoker.name(s, m);
	}
	
	@Override
	public int argsCount() {
		return args.length;
	}
	
	@Override
	public Object[] methodArgs(Player player, Class<?>[] argTypes) {
		Object[] methodArgs = new Object[args.length + 1];
		System.arraycopy(args, 0, methodArgs, 1, args.length);
		methodArgs[0] = player;
		return methodArgs;
	}
	
}

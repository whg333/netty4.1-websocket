package com.whg.websocket.server.framework.request;

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
	public Object[] args() {
		return args;
	}
	
}

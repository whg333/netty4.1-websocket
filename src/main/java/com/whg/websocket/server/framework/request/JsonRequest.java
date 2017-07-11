package com.whg.websocket.server.framework.request;

import com.whg.websocket.server.framework.ServiceMethod;

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
		return ServiceMethod.name(s, m);
	}
	@Override
	public Object[] args() {
		return args;
	}
	
}

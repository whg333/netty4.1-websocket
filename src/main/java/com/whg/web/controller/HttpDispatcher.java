package com.whg.web.controller;

import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.alibaba.fastjson.JSON;
import com.whg.util.SpringContextUtil;
import com.whg.util.annotation.HttpService;
import com.whg.websocket.server.framework.Dispatcher;
import com.whg.websocket.server.framework.method.MethodInvoker;
import com.whg.websocket.server.framework.request.JsonRequest;
import com.whg.websocket.server.framework.request.Request;

@Component
public class HttpDispatcher extends Dispatcher{

	@PostConstruct
	public void init() {
		ApplicationContext ac = SpringContextUtil.getApplicationContext();
		initServiceMethod(ac, HttpService.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> dispatch(Request request) {
		Assert.isTrue(request instanceof JsonRequest);
		
		String serviceMethod = request.serviceMethod();
		MethodInvoker methodInvoker = methodInvokerMap.get(serviceMethod);
		if (methodInvoker == null) {
			throw new UnsupportedOperationException("Unsupported methodInvoker:"+serviceMethod);
		}
		
		//TODO 暂时别注掉，主要用于调试，验证前端调用的接口
		//System.err.println(JSONUtil.toJSONwithOutNullProp(wsRequest)+" --> "+serviceMethod);
		System.err.println(JSON.toJSONString(request)+" --> "+methodInvoker.name());
		
		Class<?>[] argTypes = methodInvoker.argTypes();
		if(argTypes.length != request.argsCount()){
			throw new UnsupportedOperationException("Unsupported methodInvoker:"+serviceMethod);
		}
		
		try {
			Object[] args = request.methodArgs(argTypes);
			return (Map<String, Object>) methodInvoker.invoke(args);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
}

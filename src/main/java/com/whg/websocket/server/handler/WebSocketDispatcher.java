package com.whg.websocket.server.handler;

import org.springframework.context.ApplicationContext;

import com.alibaba.fastjson.JSON;
import com.whg.util.annotation.WebSocketService;
import com.whg.websocket.server.framework.Dispatcher;
import com.whg.websocket.server.framework.Player;
import com.whg.websocket.server.framework.exception.ExceptionHandler;
import com.whg.websocket.server.framework.method.MethodInvoker;
import com.whg.websocket.server.framework.request.JsonRequest;
import com.whg.websocket.server.framework.request.ProtobufRequest;
import com.whg.websocket.server.framework.request.Request;
import com.whg.websocket.server.framework.thread.impl.GlobalThreadPool;

public class WebSocketDispatcher extends Dispatcher{
	
	private final GlobalThreadPool globalThreadPool;
	private final ExceptionHandler exceptionHandler;
	
	public WebSocketDispatcher(ApplicationContext ac) {
		globalThreadPool = (GlobalThreadPool)ac.getBean("globalThreadPool");
		exceptionHandler = (ExceptionHandler)ac.getBean("exceptionHandler");
		initServiceMethod(ac, WebSocketService.class);
	}
	
	@Override
	public void dispatch(Player player, Request request){
		globalThreadPool.execute(new Runnable(){
			@Override
			public void run() {
				doDispatch(player, request);
			}
		});
	}

	private void doDispatch(Player player, Request request) {
		String serviceMethod = request.serviceMethod();
		MethodInvoker methodInvoker = methodInvokerMap.get(serviceMethod);
		if (methodInvoker == null) {
			throw new UnsupportedOperationException("Unsupported methodInvoker:"+serviceMethod);
		}
		
		//TODO 暂时别注掉，主要用于调试，验证前端调用的接口
		if(request instanceof JsonRequest){
			//System.err.println(JSONUtil.toJSONwithOutNullProp(wsRequest)+" --> "+serviceMethod);
			System.err.println(JSON.toJSONString(request)+" --> "+methodInvoker.name());
		}else{
			System.err.println((ProtobufRequest)request+" --> "+methodInvoker.name());
		}
		
		Class<?>[] argTypes = methodInvoker.argTypes();
		if(argTypes.length-1 != request.argsCount()){
			throw new UnsupportedOperationException("Unsupported methodInvoker:"+serviceMethod);
		}
		
		try {
			Object[] args = request.methodArgs(player, argTypes);
			methodInvoker.invoke(args);
		} catch (Exception e) {
			exceptionHandler.handleException(player, e);
		}
	}
	
}

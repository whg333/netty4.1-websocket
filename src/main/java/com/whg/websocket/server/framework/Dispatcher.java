package com.whg.websocket.server.framework;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cglib.reflect.FastClass;
import org.springframework.cglib.reflect.FastMethod;
import org.springframework.context.ApplicationContext;

import com.whg.util.json.JSONUtil;
import com.whg.websocket.server.framework.exception.ExceptionHandler;
import com.whg.websocket.server.framework.request.Request;

public class Dispatcher {
	
	private static final Logger logger = LoggerFactory.getLogger(Dispatcher.class);
	
	private final Map<String, ServiceMethod> serviceMethodMap = new HashMap<String, ServiceMethod>();
	
	private final ExceptionHandler exceptionHandler;
	
	public Dispatcher(ApplicationContext ac) {
		this.exceptionHandler = (ExceptionHandler) ac.getBean("exceptionHandler");
		this.initServiceMethodMap(ac);
	}
	
	private void initServiceMethodMap(ApplicationContext ac){
		String serviceName = "userService";
		String methodName = "login";
		Object service = ac.getBean(serviceName);
		if (service == null) {
			logger.error("can't find the service: serviceName = " + serviceName + ", methodName = " + methodName);
			throw new Error("can't find the service: serviceName = " + serviceName + ", methodName = " + methodName);
		}

		Method[] methods = service.getClass().getMethods();
		boolean found = false;
		for (Method method : methods) {
			if (method.getName().equals(methodName)) {
				FastClass fastClass = FastClass.create(service.getClass());
				FastMethod fastMethod = fastClass.getMethod(methodName, method.getParameterTypes());
				String name = ServiceMethod.name(serviceName, methodName);
				ServiceMethod serviceMethod = new ServiceMethod(name, service, fastMethod);
				serviceMethodMap.put(name, serviceMethod);
				found = true;
			}
		}
		if (!found) {
			logger.error("can't find the method: serviceName = " + serviceName + ", methodName = " + methodName);
			throw new Error("can't find the method: serviceName = " + serviceName + ", methodName = " + methodName);
		}
	}

	public void dispatch(SynPlayer player, Request wsRequest) {
		Object[] srcArgs = wsRequest.args();
		Object[] desArgs = new Object[srcArgs.length + 1];
		System.arraycopy(srcArgs, 0, desArgs, 1, srcArgs.length);
		desArgs[0] = player;

		String name = wsRequest.serviceMethod();
		ServiceMethod serviceMethod = serviceMethodMap.get(name);
		if (serviceMethod == null) {
			throw new UnsupportedOperationException("Unsupported serviceMethod:"+name);
		}
		
		//TODO 暂时别注掉，主要用于调试，验证前端调用的接口
		System.err.println(JSONUtil.toJSONwithOutNullProp(wsRequest)+" --> "+serviceMethod);
		
		try {
			serviceMethod.invoke(desArgs);
		} catch (Exception e) {
			exceptionHandler.handleException(player, e);
		}
	}

}

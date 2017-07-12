package com.whg.websocket.server.framework;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cglib.reflect.FastClass;
import org.springframework.cglib.reflect.FastMethod;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.alibaba.fastjson.JSON;
import com.esotericsoftware.reflectasm.MethodAccess;
import com.whg.websocket.server.framework.exception.ExceptionHandler;
import com.whg.websocket.server.framework.method.FastMethodInvoker;
import com.whg.websocket.server.framework.method.MethodAccessInvoker;
import com.whg.websocket.server.framework.method.MethodInvoker;
import com.whg.websocket.server.framework.request.Request;

public class Dispatcher {
	
	private static final Logger logger = LoggerFactory.getLogger(Dispatcher.class);
	
	/** 是否使用ReflectASM库来执行方法调用？否的话则使用CgLib的FastMethod执行方法调用 */
	private static final boolean useReflectASM = false;
	
	private final Map<String, MethodInvoker> methodInvokerMap = new HashMap<String, MethodInvoker>();
	
	private final ExceptionHandler exceptionHandler;
	
	public Dispatcher(ApplicationContext ac) {
		this.exceptionHandler = (ExceptionHandler) ac.getBean("exceptionHandler");
		this.initServiceMethodMap(ac);
	}
	
	private void initServiceMethodMap(ApplicationContext ac){
		Map<String, Object> serviceMap = ac.getBeansWithAnnotation(Service.class);
		for(Map.Entry<String, Object> entry:serviceMap.entrySet()){
			String serviceName = entry.getKey();
			Object service = entry.getValue();
			Class<?> serviceClass = service.getClass();
			String name = serviceClass.getSimpleName();
			if(name.contains("Domain")){ //过滤掉带有Domain名称的领域Service
				continue;
			}
			
			if(useReflectASM){
				MethodAccess access = MethodAccess.get(serviceClass);
				for(String methodName:access.getMethodNames()){
					MethodInvoker methodInvoker = new MethodAccessInvoker(serviceName, methodName, service, access);
					Assert.isNull(methodInvokerMap.put(methodInvoker.name(), methodInvoker));
				}
			}else{
				Method[] methods = serviceClass.getDeclaredMethods();
				for (Method method : methods) {
					String methodName = method.getName();
					FastClass fastClass = FastClass.create(serviceClass);
					FastMethod fastMethod = fastClass.getMethod(methodName, method.getParameterTypes());
					FastMethodInvoker serviceMethod = new FastMethodInvoker(serviceName, methodName, service, fastMethod);
					Assert.isNull(methodInvokerMap.put(serviceMethod.name(), serviceMethod));
				}
			}
		}
		System.out.println(methodInvokerMap);
	}

	public void dispatch(SynPlayer player, Request wsRequest) {
		Object[] srcArgs = wsRequest.args();
		Object[] destArgs = new Object[srcArgs.length + 1];
		System.arraycopy(srcArgs, 0, destArgs, 1, srcArgs.length);
		destArgs[0] = player;

		String serviceMethod = wsRequest.serviceMethod();
		MethodInvoker methodInvoker = methodInvokerMap.get(serviceMethod);
		if (methodInvoker == null) {
			throw new UnsupportedOperationException("Unsupported methodInvoker:"+serviceMethod);
		}
		
		//TODO 暂时别注掉，主要用于调试，验证前端调用的接口
		//System.err.println(JSONUtil.toJSONwithOutNullProp(wsRequest)+" --> "+serviceMethod);
		System.err.println(JSON.toJSONString(wsRequest)+" --> "+methodInvoker);
		
		try {
			methodInvoker.invoke(destArgs);
		} catch (Exception e) {
			exceptionHandler.handleException(player, e);
		}
	}

}

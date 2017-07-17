package com.whg.websocket.server.framework;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.cglib.reflect.FastClass;
import org.springframework.cglib.reflect.FastMethod;
import org.springframework.context.ApplicationContext;
import org.springframework.util.Assert;

import com.alibaba.fastjson.JSON;
import com.esotericsoftware.reflectasm.MethodAccess;
import com.whg.util.annotation.GlobalScope;
import com.whg.websocket.server.framework.exception.ExceptionHandler;
import com.whg.websocket.server.framework.method.FastMethodInvoker;
import com.whg.websocket.server.framework.method.MethodAccessInvoker;
import com.whg.websocket.server.framework.method.MethodInvoker;
import com.whg.websocket.server.framework.request.JsonRequest;
import com.whg.websocket.server.framework.request.ProtobufRequest;
import com.whg.websocket.server.framework.request.Request;
import com.whg.websocket.server.framework.thread.impl.GlobalThreadPool;

public class Dispatcher {
	
	private final GlobalThreadPool globalThreadPool;
	private final ExceptionHandler exceptionHandler;
	
	/** 是否使用ReflectASM库来执行方法调用？否的话则使用CgLib的FastMethod执行方法调用 */
	private static final boolean useReflectASM = true;
	private final Map<String, MethodInvoker> methodInvokerMap = new HashMap<String, MethodInvoker>();
	
	public Dispatcher(ApplicationContext ac) {
		globalThreadPool = (GlobalThreadPool)ac.getBean("globalThreadPool");
		exceptionHandler = (ExceptionHandler)ac.getBean("exceptionHandler");
		initServiceMethodMap(ac);
	}
	
	private void initServiceMethodMap(ApplicationContext ac){
		Map<String, Object> serviceMap = ac.getBeansWithAnnotation(GlobalScope.class);
		for(Map.Entry<String, Object> entry:serviceMap.entrySet()){
			String serviceName = entry.getKey();
			Object service = entry.getValue();
			Class<?> serviceClass = service.getClass();
			String name = serviceClass.getSimpleName();
			if(name.contains("Domain")){ //过滤掉带有Domain名称的领域Service
				continue;
			}
			
			Class<?>[] interfaces = serviceClass.getInterfaces();
			Set<Class<?>> interfaceSet = new HashSet<Class<?>>();
			for(Class<?> interfaceClazz:interfaces){
				//因为是service有可能是Spring AOP代理，所以过滤掉Spring相关的接口
				if(!interfaceClazz.getName().contains("org.springframework")){
					interfaceSet.add(interfaceClazz);
				}
			}
			
			for(Class<?> interfaceClazz:interfaceSet){
				if(useReflectASM){
					MethodAccess access = MethodAccess.get(interfaceClazz);
					for(String methodName:access.getMethodNames()){
						MethodInvoker methodInvoker = new MethodAccessInvoker(serviceName, methodName, service, access);
						Assert.isNull(methodInvokerMap.put(methodInvoker.name(), methodInvoker), "repeated "+methodInvoker.name());
					}
				}else{
					Method[] methods = interfaceClazz.getDeclaredMethods();
					for (Method method : methods) {
						String methodName = method.getName();
						FastClass fastClass = FastClass.create(interfaceClazz);
						FastMethod fastMethod = fastClass.getMethod(methodName, method.getParameterTypes());
						FastMethodInvoker serviceMethod = new FastMethodInvoker(serviceName, methodName, service, fastMethod);
						Assert.isNull(methodInvokerMap.put(serviceMethod.name(), serviceMethod), "repeated "+serviceMethod.name());
					}
				}
			}
		}
	}
	
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

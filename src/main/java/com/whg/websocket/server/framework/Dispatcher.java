package com.whg.websocket.server.framework;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.cglib.reflect.FastClass;
import org.springframework.cglib.reflect.FastMethod;
import org.springframework.context.ApplicationContext;
import org.springframework.util.Assert;

import com.esotericsoftware.reflectasm.MethodAccess;
import com.whg.websocket.server.framework.method.FastMethodInvoker;
import com.whg.websocket.server.framework.method.MethodAccessInvoker;
import com.whg.websocket.server.framework.method.MethodInvoker;
import com.whg.websocket.server.framework.request.Request;

public abstract class Dispatcher {
	
	/** 是否使用ReflectASM库来执行方法调用？否的话则使用CgLib的FastMethod执行方法调用 */
	protected static final boolean useReflectASM = true;
	protected final Map<String, MethodInvoker> methodInvokerMap = new HashMap<String, MethodInvoker>();
	
	@SuppressWarnings("unchecked")
	public void initServiceMethod(ApplicationContext ac, Class<? extends Annotation>... annotationTypes){
		for(Class<? extends Annotation> annotationType:annotationTypes){
			initServiceMethod(ac, annotationType);
		}
	}
	
	public void initServiceMethod(ApplicationContext ac, Class<? extends Annotation> annotationType){
		Map<String, Object> serviceMap = ac.getBeansWithAnnotation(annotationType);
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
	
	public Map<String, Object> dispatch(Request request){
		throw new UnsupportedOperationException();
	}
	public void dispatch(Player player, Request request){
		throw new UnsupportedOperationException();
	}
	
}

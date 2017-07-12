package com.whg.util.exception;

import java.util.HashMap;
import java.util.Map;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.whg.util.time.DateUtil;
import com.whg.util.time.TimeUtil;
import com.whg.websocket.server.framework.SynPlayer;
import com.whg.websocket.server.framework.method.MethodInvoker;

/**
 * <p>
 * 描述：把之前基于xml配置的ExceptionHandler改为使用@Aspect注解形式的Spring AOP。<br>
 * 如此一来在@Around里面配置了只针对XxxService的public方法且返回值为Map<String, Object>的作切面拦截，并过滤掉XxxDomainService的方法
 * </p>
 * @author whg
 * @date 2016-3-18 下午02:57:32
 */
@Aspect
public class ServiceInterceptor {
	
	private static final Logger logger = LoggerFactory.getLogger(ServiceInterceptor.class);

	@SuppressWarnings("unchecked")
	@Around("execution(public java.util.Map<String, Object> com.whg.backend.service.impl.*.*(..)) and !execution(* com.whg.backend.service.impl.*DomainService*.*(..))")
	public Object around(ProceedingJoinPoint invocation) throws Throwable{
		//TODO 缓存
		Object service = invocation.getThis();
		Class<?> serviceInterface = service.getClass().getInterfaces()[0];
		String serviceName = serviceInterface.getSimpleName();
		serviceName = serviceName.substring(0, 1).toLowerCase()+serviceName.substring(1);
		
		String methodName = invocation.getSignature().getName();
		String serviceMethodName = MethodInvoker.name(serviceName, methodName);
        logger.info("ServiceInterceptor around..."+serviceMethodName);
        
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("s", serviceName);
        result.put("m", methodName);
        try {
            Map<String, Object> data = (Map<String, Object>) invocation.proceed();
            result.put("status", ErrorCode.STATUS_SUCCESS);
            result.put("data", data);
            addServerTime(result);
            return result;
        } catch (BusinessException e) {
            logger.error(StackTraceUtil.getStackTrace(e));
            result.put("status", ErrorCode.STATUS_ERROR);
            result.put("data", new BusinessExceptionJson(e));
            addServerTime(result);
            return result;
        } catch (Exception e) {
            logger.error(StackTraceUtil.getStackTrace(e));
            result.put("status", ErrorCode.STATUS_ERROR);
            BusinessException be = new BusinessException(ErrorCode.COMMON_SYSTEM_ERROR.code, e.getMessage());
            result.put("data", new BusinessExceptionJson(be));
            addServerTime(result);
            return result;
        } finally {
        	Object[] args = invocation.getArgs();
        	Object player = args[0];
        	if(player instanceof SynPlayer){
        		((SynPlayer)player).write(result);
        	}
        	
        	//OperationContext.removeOperation();
        	//HttpContext.removeHttp();
        }
	}
	
	private String serviceMethodName(ProceedingJoinPoint invocation){
    	String methodName = invocation.getSignature().getName();
        Object service = invocation.getThis();
        String serviceName = service.toString();
        serviceName = serviceName.substring(serviceName.lastIndexOf(".")+1, serviceName.indexOf("@"));
        return serviceName+"."+methodName;
    }
	
	private void addServerTime(Map<String, Object> result){
		long now = TimeUtil.currentTimeMillis();
        result.put("t", now);
        result.put("ts", DateUtil.f(now, DateUtil.DAY_SECONDS));
	}
	
}

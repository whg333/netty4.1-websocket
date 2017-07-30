package com.whg.util.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;	

/**
 * <p>代表处理长连接WebSocket的服务类</p>
 * @author wanghg
 * @date 2017年7月30日 下午5:01:09
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface WebSocketService {

	boolean value() default true;
	
}

package com.whg.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringContextUtil implements ApplicationContextAware {

	/** Spring应用上下文环境 */
	private static ApplicationContext applicationContext;

	/**
	 * 实现了ApplicationContextAware接口，必须Override此该方法；<br>
	 * 通过传递applicationContext参数初始化成员变量applicationContext
	 */
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		SpringContextUtil.applicationContext = applicationContext;
	}

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	@SuppressWarnings("unchecked")
	public static <T> T getBean(String name) throws BeansException {
		return (T)applicationContext.getBean(name);
	}
	
	public static <T> T getBean(Class<T> clazz){
		return applicationContext.getBean(clazz);
	}

}

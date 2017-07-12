package com.whg.util.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>描述：瞬时的annotation注解，标记不会持久化的字段；因为如果字段加上transient，则@JsonIgnore注解就使用不了了</p>
 * @author whg
 * @date 2016-3-11 下午01:53:04
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Transient {

	boolean value() default true;
	
}

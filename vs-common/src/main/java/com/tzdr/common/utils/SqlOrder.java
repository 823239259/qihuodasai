package com.tzdr.common.utils;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * 
 * <p></p>
 * @author QingLiu
 * @see 排序SQL排序
 * @version 2.0
 * 2015年4月7日下午5:00:18
 */
@Target({ElementType.FIELD,ElementType.METHOD})
@Retention(RUNTIME)
public @interface SqlOrder {
	
	public String NONE = "none";
	
	/**
	 * 执行SQL对应的字段名称
	 * @return String
	 */
	String value() default NONE;
	
	

}

package com.tzdr.common.utils;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * 
 * <p></p>
 * @author LiuQing
 * @see
 * @version 2.0
 * 2015年1月13日下午4:56:48
 */
@Target({ElementType.FIELD,ElementType.METHOD})
@Retention(RUNTIME)
public @interface SqlColumn {
	
	/**
	 * if true is display columnName
	 * @return boolean
	 */
	boolean display() default true;
	
	/**
	 * if name is null or "" of fieldName 
	 * @return String
	 */
	String name() default "";
	
}

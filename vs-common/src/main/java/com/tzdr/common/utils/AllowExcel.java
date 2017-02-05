package com.tzdr.common.utils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * <p>Excel 导出注解</p>
 * @author LiuQing
 * @see
 * @version 1.0
 * 2010年11月20日上午11:33:07
 */
@Retention(value=RetentionPolicy.RUNTIME)  
@Target(value=ElementType.FIELD)
public @interface AllowExcel {
	boolean value() default true;  
    
    String name();  

}

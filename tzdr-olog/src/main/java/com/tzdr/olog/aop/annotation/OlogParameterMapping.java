package com.tzdr.olog.aop.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface OlogParameterMapping {
	/** 中文名称映射 {"name":"名称",...} */
	String[] value() default "";
}

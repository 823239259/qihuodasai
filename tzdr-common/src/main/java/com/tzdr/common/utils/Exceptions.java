/**
 * Copyright (c) 2005-2012 springside.org.cn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.tzdr.common.utils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import org.slf4j.Logger;

import com.google.common.collect.Lists;

/**
 * 关于异常的工具类.
 * 
 */
public class Exceptions {

	/**
	 * 将CheckedException转换为UncheckedException.
	 */
	public static RuntimeException unchecked(Exception e) {
		if (e instanceof RuntimeException) {
			return (RuntimeException) e;
		} else {
			return new RuntimeException(e);
		}
	}

	/**
	 * 将ErrorStack转化为String.
	 */
	public static String getStackTraceAsString(Exception e) {
		StringWriter stringWriter = new StringWriter();
		e.printStackTrace(new PrintWriter(stringWriter));
		return stringWriter.toString();
	}

	/**
	 * 判断异常是否由某些底层的异常引起.
	 */
	public static boolean isCausedBy(Exception ex, Class<? extends Exception>... causeExceptionClasses) {
		Throwable cause = ex.getCause();
		while (cause != null) {
			for (Class<? extends Exception> causeClass : causeExceptionClasses) {
				if (causeClass.isInstance(cause)) {
					return true;
				}
			}
			cause = cause.getCause();
		}
		return false;
	}
	
	
	/**
	 * 异常处理
	 * 
	 * @param e
	 * @param method
	 */
	public static void HandleException(Exception e,String className,String method,Logger logger) {
		// 发送邮件
		String devEmail = ConfUtil.getContext("mail.to.dev");
		List<String> pramas = Lists.newArrayList();
		String methodName = className + "." + method;
		String exception = Exceptions.getStackTraceAsString(e);
		pramas.add(methodName);
		pramas.add(exception);
		try {
			EmailUtils.getInstance().sendMailTemp(devEmail,"exceptionemail", pramas);
		} catch (Exception ex) {
			logger.error("email:", ex.getMessage());
		}
		logger.error(methodName + ",error::{}", exception);
	}
}

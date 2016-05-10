package com.tzdr.common.exception;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import com.tzdr.common.utils.ConfUtil;
import com.tzdr.common.utils.EmailUtils;
import com.tzdr.common.utils.Exceptions;


/**
 * @Description:
 * @ClassName: EmailException.java
 * @author Lin Feng
 * @date 2015年1月30日
 */
public class EmailExceptionHandler {
	
	public static final Logger log = LoggerFactory.getLogger(EmailExceptionHandler.class);
	
	private static EmailExceptionHandler instance;
	
	private static String devEmail;
	
	public static synchronized EmailExceptionHandler getInstance(){
		if (instance == null) {
			instance = new EmailExceptionHandler();
		}
		return instance;
	}
	
	private EmailExceptionHandler(){
		devEmail = ConfUtil.getContext("mail.to.dev");
	}
	
	public  void HandleException(Exception e, String errorName,String method) {
		// 发送邮件
		List<String> pramas = Lists.newArrayList();
		String methodName=method;
		String exception=Exceptions.getStackTraceAsString(e);
		pramas.add(methodName);
		pramas.add(exception);
		try {
			EmailUtils.getInstance().sendMailTemp(devEmail,errorName, "exceptionemail",
					pramas);
		} catch (Exception ex) {
			log.error("email:", ex.getMessage());
		}
		log.error(methodName+ ",error::{}",
				exception);
	}
	
	public  void HandleException(String emailAddress,Exception e, String errorName,String method) {
		// 发送邮件
		List<String> pramas = Lists.newArrayList();
		String methodName=method;
		String exception=Exceptions.getStackTraceAsString(e);
		pramas.add(methodName);
		pramas.add(exception);
		try {
			EmailUtils.getInstance().sendMailTemp(emailAddress,errorName, "exceptionemail",
					pramas);
		} catch (Exception ex) {
			log.error("email:", ex.getMessage());
		}
		log.error(methodName+ ",error::{}",
				exception);
	}
	
	public  void HandleExceptionWithData(Exception e, String errorName,String method,String dataDetail) {
		// 发送邮件
		List<String> pramas = Lists.newArrayList();
		String methodName=method;
		String exception=Exceptions.getStackTraceAsString(e);
		pramas.add(methodName);
		pramas.add(exception);
		pramas.add(dataDetail);
		try {
			EmailUtils.getInstance().sendMailTemp(devEmail,errorName, "exceptionDetailEmail",
					pramas);
		} catch (Exception ex) {
			log.error("email:", ex.getMessage());
		}
		log.error(methodName+ ",error::{}",
				exception);
	}
	
	public  void HandleExceptionWithData(String emailAddress,Exception e, String errorName,String method,String dataDetail) {
		// 发送邮件
		List<String> pramas = Lists.newArrayList();
		String methodName=method;
		String exception=Exceptions.getStackTraceAsString(e);
		pramas.add(methodName);
		pramas.add(exception);
		pramas.add(dataDetail);
		try {
			EmailUtils.getInstance().sendMailTemp(emailAddress,errorName, "exceptionDetailEmail",
					pramas);
		} catch (Exception ex) {
			log.error("email:", ex.getMessage());
		}
		log.error(methodName+ ",error::{}",
				exception);
	}

	
	public  void HandleHintWithData(String errorName,String method,String dataDetail) {
		// 发送邮件
		List<String> pramas = Lists.newArrayList();
		String methodName=method;
		pramas.add(methodName);
		pramas.add(dataDetail);
		try {
			EmailUtils.getInstance().sendMailTemp(devEmail,errorName, "exceptionemail",
					pramas);
		} catch (Exception ex) {
			log.error("email:", ex.getMessage());
		}
		log.error(methodName+ ",error::{}",errorName);
	}
	
}

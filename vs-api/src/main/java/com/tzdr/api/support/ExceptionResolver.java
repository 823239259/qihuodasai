package com.tzdr.api.support;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jodd.util.StringUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.tzdr.api.constants.ResultStatusConstant;
import com.tzdr.api.util.RequestUtils;
import com.tzdr.common.exception.EmailExceptionHandler;
import com.tzdr.common.exception.GeneralException;
import com.tzdr.common.web.support.JsonResult;

/**
 * 
 * <B>说明: </B>异常拦截器 拦截所有异常并返回系统异常
 * @zhouchen
 * 2016年1月20日
 */
public class ExceptionResolver implements HandlerExceptionResolver {

	public static final Logger logger = LoggerFactory.getLogger(ExceptionResolver.class);
	@Override
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		
		 if (ex instanceof GeneralException)
		 {
			 GeneralException  exception  = (GeneralException) ex;
			 try {
				 String message = exception.getResourceMessage();
				 if (StringUtil.isBlank(message)){
					 message = exception.getErrorCode();
				 }
				 //发送异常邮件
				 EmailExceptionHandler.getInstance().HandleException(ex, "API-异常拦截器拦截到异常:::【"+message+"】",this.getClass().getName()+" : "+ex.getClass().getSimpleName());
			 } 
			 catch (Exception e) {
				 //发送异常邮件
				 EmailExceptionHandler.getInstance().HandleException(e, "API-异常拦截器拦截到异常", this.getClass().getName()+" : "+e.getClass().getSimpleName());
			 }
		 }else
		 {
			 //发送异常邮件
			 EmailExceptionHandler.getInstance().HandleException(ex, "API-异常拦截器拦截到异常", this.getClass().getName()+" : "+ex.getClass().getSimpleName());
		 }
		 RequestUtils.printText(JSON.toJSONString(new JsonResult(false,ResultStatusConstant.FAIL,"system.exception.")),response);
		 return new ModelAndView();
	
	
	}
}

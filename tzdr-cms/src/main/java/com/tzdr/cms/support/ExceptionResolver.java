package com.tzdr.cms.support;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jodd.util.StringUtil;

import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.tzdr.cms.constants.Constants;
import com.tzdr.cms.constants.ViewConstants;
import com.tzdr.cms.utils.WebUtil;
import com.tzdr.common.exception.EmailExceptionHandler;
import com.tzdr.common.exception.GeneralException;
import com.tzdr.common.web.support.JsonResult;


/**
 * @author zhouchen
 * @version 创建时间：2014年12月8日 下午5:41:34
 * 类说明
 */
public class ExceptionResolver implements HandlerExceptionResolver {

	public static final Logger logger = LoggerFactory.getLogger(ExceptionResolver.class);
	/**
	 * 这个异常先屏蔽
	 */
	public static final String APACHE_ERROR_NAME="org.apache.catalina.connector.ClientAbortException";
	@Override
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		
		 logger.info("异常拦截器拦截的异常名称："+ex.getClass().getSimpleName());
		 
		 Map<String, String> model = new HashMap<String, String>();  
		 if (ex instanceof GeneralException)
		 {
			 GeneralException  exception  = (GeneralException) ex;
			 model.put(Constants.ERROR_CODE, exception.getErrorCode());  
		     model.put(Constants.ERROR_MESSAGE, exception.getResourceMessage()); 
		     //发送异常邮件
			 EmailExceptionHandler.getInstance().HandleException(ex, "cms-异常拦截器拦截到异常:::"+exception.getResourceMessage(), this.getClass().getName()+" : "+ex.getClass().getSimpleName());
			
		 }
		 else if (ex instanceof UnauthorizedException 
				 || StringUtil.equals(APACHE_ERROR_NAME,ex.getClass().getName()))
		 {
			 model.put(Constants.ERROR_CODE, ex.getClass().getSimpleName());  
		     model.put(Constants.ERROR_MESSAGE, ex.getMessage()); 
		 }else
		 {
			 model.put(Constants.ERROR_CODE, ex.getClass().getSimpleName());  
		     model.put(Constants.ERROR_MESSAGE, ex.getMessage()); 
		     //发送异常邮件
			 EmailExceptionHandler.getInstance().HandleException(ex, "cms-异常拦截器拦截到异常", this.getClass().getName()+" : "+ex.getClass().getSimpleName());
			 
		 }
		 
		 //如果异常message为空  则统一设置为 系统异常，请联系管理员
		 if (StringUtil.isBlank(model.get(Constants.ERROR_MESSAGE))){
			 model.put(Constants.ERROR_MESSAGE,"系统异常，请联系管理员！"); 
		 }
		 // ajax 请求异常 直接json信息
		 if (WebUtil.isAjax(request)) {
				WebUtil.printText(JSON.toJSONString(new JsonResult(false,
						model.get(Constants.ERROR_CODE),model.get(Constants.ERROR_MESSAGE))),
						response);
				return new ModelAndView();
		 }		 
	    return new ModelAndView(ViewConstants.ERROR_VIEW, model);  
	}
	
}

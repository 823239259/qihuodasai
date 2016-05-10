package com.tzdr.api.support;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jodd.util.ObjectUtil;
import jodd.util.StringUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.tzdr.api.constants.DataConstant;
import com.tzdr.api.constants.ResultStatusConstant;
import com.tzdr.api.util.RequestUtils;
import com.tzdr.business.api.service.ApiTokenService;
import com.tzdr.common.utils.Dates;
import com.tzdr.common.web.support.JsonResult;
import com.tzdr.domain.api.entity.ApiToken;

/**
 * 
 * <B>说明: </B>系统session拦截器，页面构建完成后校验token是否有效
 * @zhouchen
 * 2016年1月20日
 */
public class SessionInterceptor implements HandlerInterceptor {
	
	@Autowired
	private ApiTokenService  apiTokenService;

	/**
	 * 页面构建完成后执行，类似try catch的finally代码块 可处理页面异常信息，将异常做统一处理。 通过URL判断session时间是否过期
	 * ，是否需要登录
	 */
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		String token = request.getHeader(DataConstant.API_TOKEN);
		if (StringUtil.isBlank(token)){
			RequestUtils.printText(JSON.toJSONString(new JsonResult(false,ResultStatusConstant.TOKEN_ERROR,"token.is.null.")), response);
			return false;
		}
		String ipAddr = RequestUtils.getIp(request);
		ApiToken appToken = apiTokenService.findByToken(token,ipAddr);
		if (ObjectUtil.equals(null,appToken)){
			RequestUtils.printText(JSON.toJSONString(new JsonResult(false,ResultStatusConstant.TOKEN_ERROR,"invalid.request.")), response);
			return false;
		}
			
		long currentTime = Dates.getCurrentLongDate();
		if(currentTime > appToken.getInvalidTime()){
			RequestUtils.printText(JSON.toJSONString(new JsonResult(false,ResultStatusConstant.TOKEN_INVALID,"token.invalid.")), response);
			return false;
		}
		request.setAttribute(DataConstant.API_TOKEN,appToken);
		return true;
		
	}

	/**
	 * 在controller执行完成后，页面构建之前执行 可执行通用操作，例如：每个页面的头部、底部内容
	 */
	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	/**
	 * 在进入controller之前执行 返回值true表示放行，false会阻止执行 此处异常获取 跟配置的异常页面跳转存在冲突 只有取消
	 * spring-servlert 的配置 这里的exception 才能有效的获取
	 * 
	 * 如果不取消 异常ex 全部为null 同步请求的 会跳转到异常页面，ajax请求的会返回error
	 * 
	 */
	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}

}

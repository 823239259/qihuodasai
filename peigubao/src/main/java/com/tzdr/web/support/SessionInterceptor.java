package com.tzdr.web.support;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jodd.util.StringUtil;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.tzdr.business.service.wuser.WUserService;
import com.tzdr.common.utils.SpringUtils;
import com.tzdr.common.utils.StringCodeUtils;
import com.tzdr.domain.web.entity.WUser;
import com.tzdr.web.constants.Constants;
import com.tzdr.web.utils.UserSessionBean;
import com.tzdr.web.utils.WebUtil;

/**
 * @author zhouchen,LiuQing
 * @version 创建时间：2014年12月5日 下午3:51:41 类说明
 */

public class SessionInterceptor implements HandlerInterceptor {
	public static final String TYPE = "type=6600";
	
	public static final String ACTIVITY_TYPE = "activity_type=6600";

	/**
	 * 页面构建完成后执行，类似try catch的finally代码块 可处理页面异常信息，将异常做统一处理。 通过URL判断session时间是否过期
	 * ，是否需要登录
	 */
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		WUserService wUserService=SpringUtils.getBean(WUserService.class);
		
		//Session 未失效
		if (!WebUtil.sessionFailure(request)) {
			UserSessionBean userSessionBean = (UserSessionBean)request.getSession().getAttribute(Constants.TZDR_USER_SESSION);  //获取用户账户信息
			WUser wUser = wUserService.getUser(userSessionBean.getId());
			
			//参加6600活动
			if(WUser.UserType.WEB_REGIST.equals(wUser.getUserType())
					&& WUser.ActivityType.ACTIVITY_6600 == wUser.getActivityType() ){
				if(TYPE.equals(request.getQueryString())){
					return true;	
				}
				else if(ACTIVITY_TYPE.equals(request.getQueryString())){
					return true;	
				}
				else{
					request.getServletContext().getRequestDispatcher("/user/account?"+ACTIVITY_TYPE).forward(request, response);
					return false;		
				}
			}
			else{
				return true;	
			} 
		} else {
			if (isAjaxRequest(request)) { // ajax
				// 处理上一个url地址
				String param = request.getQueryString();
				String returnUrl = request.getRequestURI(); // request.getRequestURL().toString();
				if (null != param) {
					returnUrl += "?" + param;
				}
				response.setHeader("sessionstatus", "timeout");// 在响应头设置session状态
				PrintWriter printWriter = response.getWriter();
				Map<String, Object> data = new HashMap<String, Object>();
				data.put("sessionstatus", "timeout");
				data.put("returnUrl", returnUrl);
				printWriter.print(JSON.toJSONString(data));
				// printWriter.print("{sessionstatus:timeout,returnUrl:"+returnUrl+"}");
				printWriter.flush();
				printWriter.close();
				return false;
			} else {
				String param = request.getQueryString();
				String returnUrl = request.getRequestURI(); // request.getRequestURL().toString();
				if (null != param) {
					returnUrl += "?" + param;
				}
				request.setAttribute("returnUrl", returnUrl);
				request.getServletContext().getRequestDispatcher("/login")
						.forward(request, response);
				request.getServletContext().getRequestDispatcher("/login").forward(request, response);
				return false;
			}
		}
	}

	private boolean isAjaxRequest(HttpServletRequest request) {
		return request.getHeader("x-requested-with") != null
				&& request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest");
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

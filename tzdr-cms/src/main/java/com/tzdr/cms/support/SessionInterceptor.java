package com.tzdr.cms.support;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author zhouchen
 * @version 创建时间：2014年12月5日 下午3:51:41 类说明
 */
public class SessionInterceptor implements HandlerInterceptor {

	/**
	 * 页面构建完成后执行，类似try catch的finally代码块 可处理页面异常信息，将异常做统一处理。 通过URL判断session时间是否过期
	 * ，是否需要登录
	 */
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {

		//TODO : CMS 的登录失效 有权限判断  不由这里操作
		return true;
		/*//session 未失效
		if (!WebUtil.sessionFailure(request)) {
			return true;
		}
		
		if (WebUtil.isAjax(request)) {
			WebUtil.printText(JSON.toJSONString(new JsonResult(false,
					ErrorCodeConstants.SESSION_FAILURE,
					"登录超时请重新登录。。")), response);
			return false;
		}
		response.sendRedirect(request.getContextPath()+"/login/toLogin");
		return false;*/
		
	}

	/**
	 * 在controller执行完成后，页面构建之前执行 可执行通用操作，例如：每个页面的头部、底部内容
	 */
	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		//System.out.println("postHandle");

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

		/*
		 * if (null != ex) { System.out.println("接收到异常了。。。。。。。。"); }
		 * 
		 * if (ObjectUtils.equals(ex, null)) { return; }
		 * 
		 * // 记录日志
		 */
	}

}

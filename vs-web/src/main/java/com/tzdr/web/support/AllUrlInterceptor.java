package com.tzdr.web.support;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jodd.util.StringUtil;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.tzdr.web.constants.Constants;
import com.tzdr.web.utils.WebUtil;


/**
 * @author wangPinQun
 * @version 创建时间：2016年01月5日 下午3:51:41 类说明
 */

public class AllUrlInterceptor implements HandlerInterceptor {
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		try {
			HttpSession session = request.getSession();
			Object firstUrlObj  = session.getAttribute(Constants.FIRSTURL_SESSION);
			String firstUrl = firstUrlObj == null ? null : firstUrlObj.toString();
			
			if (!WebUtil.sessionFailure(request)){  //判断是否已经登陆
				String from = this.getChannelFrom(true, request, response);  //删除推广地址
				if(StringUtil.isNotBlank(from)){
					request.getSession().removeAttribute(Constants.FIRSTURL_SESSION);
					return true;
				}
				if(StringUtil.isNotBlank(firstUrl)){  //判断是否已经保存首次访问的地址
					request.getSession().removeAttribute(Constants.FIRSTURL_SESSION);
					response.sendRedirect(firstUrl);
					return false;
				}
				return true;
			}
			//判断是否Ajax请求、已经保存首次访问地址
			if(isAjaxRequest(request) || StringUtil.isNotBlank(firstUrl)){  
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}
	
	private boolean isAjaxRequest(HttpServletRequest request) {
		return request.getHeader("x-requested-with") != null
				&& request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest");
	}
	

	/**
	* @Description: 获取推广渠道来源URL
	* @Title: getChannelFrom
	* @param isRemove
	* @param request
	* @param response
	* @return String    返回类型
	 */
	private String getChannelFrom(boolean isRemove,HttpServletRequest request,HttpServletResponse response){
		String channelFrom = (String) request.getSession().getAttribute(Constants.TZDR_CHANNEL_FROM);  //推广渠道
		if(StringUtil.isBlank(channelFrom)){
			Cookie[] cookies = request.getCookies();
			if(cookies != null && cookies.length > 0){
				for(Cookie cookie:cookies){
					String cookieName = cookie.getName();
					if(Constants.TZDR_CHANNEL_FROM.equals(cookieName)){
						channelFrom = cookie.getValue();
						if(isRemove){
							Cookie cookieFrom = new Cookie(Constants.TZDR_CHANNEL_FROM, null);
							cookieFrom.setMaxAge(0);
							cookieFrom.setPath("/");
							response.addCookie(cookieFrom);
						}
						break;
					}
				}
			}
		}else{
			request.getSession().removeAttribute(Constants.TZDR_CHANNEL_FROM);
			Cookie[] cookies = request.getCookies();
			if(cookies != null && cookies.length > 0){
				for(Cookie cookie:cookies){
					String cookieName = cookie.getName();
					if(Constants.TZDR_CHANNEL_FROM.equals(cookieName)){
						Cookie cookieFrom = new Cookie(Constants.TZDR_CHANNEL_FROM, null);
						if(isRemove){
							cookieFrom.setMaxAge(0);
							cookieFrom.setPath("/");
							response.addCookie(cookieFrom);
						}
						break;
					}
				}
			}
		}
		return channelFrom;
	}
}

package com.tzdr.web.support.filter;

import java.io.IOException;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jodd.util.StringUtil;

import org.apache.commons.lang3.StringUtils;

import com.tzdr.business.service.wuser.WUserService;
import com.tzdr.common.utils.SpringUtils;
import com.tzdr.common.utils.StringCodeUtils;
import com.tzdr.domain.web.entity.WUser;
import com.tzdr.web.constants.Constants;
import com.tzdr.web.utils.UserSessionBean;
import com.tzdr.web.utils.WebUtil;

public class AuthenticationFilter implements Filter {
	
	private WUserService wUserService = SpringUtils.getBean(WUserService.class);
	
	//URL排除的正则表达式
	private String excludePattern; 
	
	@Override
	public void destroy() {
	}
	
	

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain arg2) throws IOException, ServletException {
		
		HttpServletRequest request = (HttpServletRequest)req;
		if(StringUtil.isNotBlank(excludePattern) && isExclude(request.getRequestURI())){
			arg2.doFilter(req, resp);
			return;
		}
		if(request.getRequestURI().equalsIgnoreCase(request.getContextPath()+"/logout")){  //退出p2p，清除cookie
			 Cookie cookieq = new Cookie("sp2p6",null);
			 ResourceBundle prop = ResourceBundle.getBundle("webconf");
			 String domain=prop.getString("cookiedomain");
			 cookieq.setDomain(domain);
			 cookieq.setMaxAge(0);
			 cookieq.setPath("/");
			((HttpServletResponse)resp).addCookie(cookieq);
		}
		String remoteUserName = request.getRemoteUser();
		if(StringUtils.isNotEmpty(remoteUserName) && WebUtil.sessionFailure(request)) { // SSO已登录 但 当前系统未创建session
			WUser wUser = wUserService.getWUserByMobile(remoteUserName);
			if (wUser != null) {
				UserSessionBean userSessionBean = new UserSessionBean();   //session对象
				userSessionBean.setId(wUser.getId());
				userSessionBean.setEmail(wUser.getEmail());
				userSessionBean.setMobile(wUser.getMobile());
				userSessionBean.setUname(wUser.getUname());
				
				request.getSession().setAttribute(com.tzdr.web.constants.Constants.TZDR_USER_SESSION, userSessionBean); //保存都信息
				String userName = null;  //用户名称
				if(!StringUtil.isBlank(userSessionBean.getMobile())){  //手机号码加*
					String mobile = userSessionBean.getMobile();
					userName = StringCodeUtils.buildMobile(mobile);
				}
				request.getSession().setAttribute(com.tzdr.web.constants.Constants.TZDR_USERNAME_SESSION, userName);
			}
		} else if(StringUtils.isEmpty(remoteUserName)) { // 不存在登录名
			request.getSession().invalidate();
		}
		if(WebUtil.sessionFailure(request)){
			String refererPage = request.getHeader("referer");
			if (StringUtil.isNotBlank(refererPage)){
				if (!(refererPage.contains("/login") ||refererPage.contains("/signin") || refererPage.contains("/forgetpw")) ){
					request.getSession().setAttribute(Constants.FIRSTURL_SESSION, request.getHeader("referer"));				
				}
			}
		}
		arg2.doFilter(req, resp);
	}
	
	/**
	 * 判断是否排除过滤
	 * @param uri
	 * @return
	 */
	private boolean isExclude(String uri){
		Pattern pattern = null;
		pattern=Pattern.compile(excludePattern);
		if(pattern.matcher(uri).matches()){
			return true;
		}
		return false;
	}


	@Override
	public void init(FilterConfig arg0) throws ServletException {
		excludePattern=arg0.getInitParameter("excludePattern");
	}

}

package com.tzdr.olog.support;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nl.bitwalker.useragentutils.Browser;
import nl.bitwalker.useragentutils.OperatingSystem;
import nl.bitwalker.useragentutils.UserAgent;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tzdr.domain.cms.entity.user.User;
import com.tzdr.olog.OlogManager;
import com.tzdr.olog.db.domain.Olog;

/**
 * Shiro框架登录注销监听Olog实现，用于Olog自动拦截登录注销日志
 * 
 * @author Lin Feng
 * 
 */
@Component
public class OlogLoginLogoutListener implements LoginLogoutListener {
	private static final Logger logger = LoggerFactory
			.getLogger(OlogLoginLogoutListener.class);

	@Autowired
	private OlogManager ologManager;

	@Override
	public void beforeLogout(HttpServletRequest request,
			HttpServletResponse response, Subject subject) {
		logger.debug("beforeLogout : " + subject);
		Olog olog = new Olog();
		olog.setModule("system");
		olog.setModuleName("系统功能");
		olog.setAction("logout");
		olog.setActionName("注销");
		olog.setClientInformations(getClientInformations(request));
		User currentUser = (User) subject.getPrincipal();
		olog.setOperateUser(currentUser.getId() + "/"
				+ currentUser.getUsername());
		olog.setOperateUserId(String.valueOf(currentUser.getId()));
		try {
			ologManager.logger(olog);
			logger.debug("Save olog success. " + olog.toString());
		} catch (Exception e) {
			logger.debug("olog logout failure. --> " + e.getMessage());
		}
	}

	@Override
	public void afterLogin(AuthenticationToken token,
			AuthenticationException e, HttpServletRequest request,
			HttpServletResponse response) {
		logger.debug("login " + (e == null ? "success" : "falure") + " : "
				+ token);
		Olog olog = new Olog();
		olog.setModule("system");
		olog.setModuleName("系统功能");
		olog.setAction("login");
		olog.setActionName("登录");
		olog.setClientInformations(getClientInformations(request));
		if (e == null) {
			// 登录成功
			User currentUser = (User) SecurityUtils.getSubject().getPrincipal();
			if (currentUser != null) {
				olog.setOperateUser(currentUser.getId() + "/"
						+ currentUser.getUsername());
				olog.setOperateUserId(String.valueOf(currentUser.getId()));
			}
		} else {
			olog.setOperateUser(token.getPrincipal().toString());
			olog.setDescn(e.getClass().getName());
			olog.setOperateResult(Olog.OPERATE_RESULT_FAILURE);
		}

		try {
			ologManager.logger(olog);
			logger.debug("Save olog success. " + olog.toString());
		} catch (Exception ex) {
			logger.debug("olog login failure. --> " + ex.getMessage());
		}
	}

	/**
	 * 获取请求客户端信息
	 * 
	 * @param request
	 * @return
	 */
	private String getClientInformations(HttpServletRequest request) {
		String clientIP = request.getRemoteAddr();
		String requestUserAgent = request.getHeader("User-Agent");
		UserAgent userAgent = UserAgent.parseUserAgentString(requestUserAgent);
		OperatingSystem os = userAgent.getOperatingSystem();
		Browser browser = userAgent.getBrowser();
		String clientInfo = clientIP + " - " + os.getName() + " - "
				+ browser.getName() + "/" + browser.getBrowserType().getName();
		return clientInfo;
	}

}

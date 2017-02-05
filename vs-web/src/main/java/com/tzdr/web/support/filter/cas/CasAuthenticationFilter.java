package com.tzdr.web.support.filter.cas;

import javax.servlet.FilterConfig;
import javax.servlet.ServletException;

import org.jasig.cas.client.authentication.AuthenticationFilter;

import com.tzdr.common.utils.ConfUtil;

public class CasAuthenticationFilter extends AuthenticationFilter {

	@Override
	protected void initInternal(FilterConfig filterConfig)
			throws ServletException {
		// TODO Auto-generated method stub
		super.initInternal(filterConfig);
		setCasServerLoginUrl(ConfUtil.getContext("SSO.casServer.loginUrl"));
		setServerName(ConfUtil.getContext("SSO.casClientUrl"));
	}

}

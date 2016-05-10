package com.tzdr.web.support.filter.cas;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UrlConvertFilter implements Filter {
	
	private static final String SUFFIX=".sso";

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletRequest httpServletRequest=(HttpServletRequest) request;
		HttpServletResponse httpServletResponse=(HttpServletResponse) response;
		String uri=httpServletRequest.getRequestURI();
		if(uri.endsWith(SUFFIX)){
			uri=uri.substring(0, uri.length()-SUFFIX.length());
			String url=uri;
			String query=httpServletRequest.getQueryString();
			if(query!=null){
				url=uri+"?"+httpServletRequest.getQueryString();
			}
			httpServletResponse.sendRedirect(url);
			return;
		}
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

}

package com.tzdr.web.support.filter;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jodd.util.StringUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SafeFilter implements Filter{
	private String[] key = "<IFRAME,%3EIFRAME,SELECT,SELECT%20,INSERT,INSERT%20,UPDATE,UPDATE%20,DELETE,DELETE%20,|,$".split(",");
	
	public static final Logger log = LoggerFactory.getLogger(SafeFilter.class);

	
	@Override
	public void destroy() {
		
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain filter) throws IOException, ServletException {
		req.setCharacterEncoding("UTF-8");
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		boolean redirect = false;
		CheckKeyWordContains rb = new CheckKeyWordContains();
		try {
			String method = request.getMethod();
			if ("POST".equals(method)) {
				Iterator<Entry<String, String[]>> it = request.getParameterMap().entrySet().iterator();
				while(it.hasNext()) {
					Map.Entry<String, String[]> e = it.next();
					rb = containAttackChar(((String[]) e.getValue())[0], request);
					if (rb.isContains()) {
						redirect = true;
						break;
					}
				}
			}
			else {
				rb = containAttackChar(request.getQueryString(), request);
				if (rb.isContains()) {
					redirect = true;
				}
			}
		} catch (Exception e) {}
		
		if (redirect) {
			response.setIntHeader("sessionState", 3);
			response.setHeader("message", rb.getMsg().substring(rb.getMsg().lastIndexOf("：") + 1, rb.getMsg().length()));
			this.doRedirect(request, response, rb.getMsg());
		}else {
			XssHttpWrapper xssRequest = new XssHttpWrapper((HttpServletRequest) request);   
			filter.doFilter(xssRequest, response); 
			
			//filter.doFilter(request, response);
		}
	}


	private CheckKeyWordContains containAttackChar(String str, HttpServletRequest request) {
		CheckKeyWordContains rb = new CheckKeyWordContains();
		if (!StringUtil.isEmpty(str)) {
			for (int i = 0; i < key.length; i++) {
				if (StringUtil.isEmpty(key[i])) {
					continue;
				}
				if (str.toUpperCase().indexOf(key[i]) > -1) {
					String msg = "拦截到攻击性字符：" + key[i] + ",来源IP：" + request.getRemoteAddr();
					log.info(msg);
					rb.setContains(true);
					rb.setMsg("拦截到攻击性字符：" + key[i]);
					return rb;
				}
			}
		}
		return rb;
	}
	
	private void doRedirect(HttpServletRequest req, HttpServletResponse resp, String msg) throws IOException {
		resp.sendRedirect(req.getContextPath()+"/404.jsp");
	}
	
	@Override
	public void init(FilterConfig arg0) throws ServletException {
		
	}
}

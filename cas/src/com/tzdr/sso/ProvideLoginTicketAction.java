package com.tzdr.sso;

import javax.servlet.http.HttpServletRequest;
import org.jasig.cas.web.support.WebUtils;
import org.springframework.webflow.action.AbstractAction;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;

/**
 * Opens up the CAS web flow to allow external retrieval of a login ticket.
 * 
 * @author denger
 */
public class ProvideLoginTicketAction extends AbstractAction{

	@Override
	protected Event doExecute(RequestContext context) throws Exception {
		final HttpServletRequest request = WebUtils.getHttpServletRequest(context);

		if (request.getParameter("get-lt") != null && request.getParameter("get-lt").equalsIgnoreCase("true")) {
			return result("loginTicketRequested");
		}
		return result("continue");
	}
	
}
// 如果参数中包含 get-lt 参数，则返回 loginTicketRequested 执行流，并跳转至 loginTicket 生成页，否则 则跳过该flow，并按照原始login的流程来执行。

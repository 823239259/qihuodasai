package com.tzdr.cms.controller;
import javax.servlet.http.HttpServletRequest;

import jodd.util.StringUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tzdr.business.service.recharge.RechargeAuditRuleService;
import com.tzdr.cms.constants.Constants;
import com.tzdr.cms.constants.ViewConstants;
import com.tzdr.cms.support.BaseCmsController;
import com.tzdr.common.baseservice.BaseService;
import com.tzdr.domain.web.entity.RechargeAuditRule;


@Controller
@RequestMapping("/admin/rechargeAuditRule")
public class RechargeAuditRuleController  extends BaseCmsController<RechargeAuditRule>
{
	private static Logger log = LoggerFactory.getLogger(RechargeAuditRuleController.class);
	
	public RechargeAuditRuleController() {
		setResourceIdentity("sys:finance:rechargeAuditRule");
	}
	
	@Autowired
	private RechargeAuditRuleService auditRuleService;
	
	@Override
	public BaseService<RechargeAuditRule> getBaseService() {
		return auditRuleService;
	}
	
	
	@RequestMapping(value = "/list")
	public String list(HttpServletRequest request) throws Exception 
	{
		return ViewConstants.RechargeViewJsp.LIST_VIEW_RECHARGE_AUDITRULE;
	}

	
	
	@RequestMapping(value = "/edit")
	public String edit(HttpServletRequest request,@RequestParam("fromType") String fromType,
			@RequestParam(value="id",required=false) String id) throws Exception 
	{
		request.setAttribute("fromType",fromType);
		if (StringUtil.equals(fromType,Constants.ADD)){
			return ViewConstants.RechargeViewJsp.EIDT_VIEW_RECHARGE_AUDITRULE;			
		}
		
		if (StringUtil.equals(fromType,Constants.EDIT)){
			RechargeAuditRule  auditRule  = auditRuleService.get(id);
			request.setAttribute("auditRule",auditRule);
			return ViewConstants.RechargeViewJsp.EIDT_VIEW_RECHARGE_AUDITRULE;	
		}
		
		return ViewConstants.ERROR_VIEW;
	}
}

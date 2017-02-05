package com.tzdr.cms.controller;

import javax.servlet.http.HttpServletRequest;

import jodd.util.StringUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tzdr.business.service.investor.InvestorService;
import com.tzdr.cms.constants.Constants;
import com.tzdr.cms.constants.ViewConstants;
import com.tzdr.cms.support.BaseCmsController;
import com.tzdr.common.baseservice.BaseService;
import com.tzdr.domain.web.entity.Investor;

/**
 * 出资人信息 
 * @zhouchen
 * 2015年2月12日
 */
@Controller
@RequestMapping("/admin/investor")
public class InvestorController extends BaseCmsController<Investor> {
	 
	private static Logger log = LoggerFactory.getLogger(InvestorController.class);
	@Autowired
	private InvestorService investorService;

	@Override
	public BaseService<Investor> getBaseService() {
		return investorService;
	}

	public InvestorController() {
		setResourceIdentity("sys:riskmanager:investor");
	}
	
	@RequestMapping(value="list")
	public String list(HttpServletRequest  request){
		return ViewConstants.InvestorViewJsp.LIST_VIEW;
	}
	
	@RequestMapping(value = "/edit")
	public String edit(HttpServletRequest request,@RequestParam("fromType") String fromType,
			@RequestParam(value="id",required=false) String id) throws Exception 
	{
		request.setAttribute("fromType",fromType);
		if (StringUtil.equals(fromType,Constants.ADD)){
			return ViewConstants.InvestorViewJsp.EDIT_VIEW;			
		}
		
		if (StringUtil.equals(fromType,Constants.EDIT)){
			Investor  investor  = investorService.get(id);
			request.setAttribute("investor",investor);
			return ViewConstants.InvestorViewJsp.EDIT_VIEW;	
		}
		
		return ViewConstants.ERROR_VIEW;
	}
	
}

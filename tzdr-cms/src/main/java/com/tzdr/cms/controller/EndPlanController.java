package com.tzdr.cms.controller;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jodd.util.StringUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.tzdr.business.service.endplan.EndPlanService;
import com.tzdr.business.service.userTrade.UserTradeService;
import com.tzdr.cms.constants.ViewConstants;
import com.tzdr.cms.support.BaseCmsController;
import com.tzdr.cms.utils.DataGridVo;
import com.tzdr.cms.utils.WebUtil;
import com.tzdr.common.baseservice.BaseService;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.utils.ConnditionVo;
import com.tzdr.common.utils.TypeConvert;
import com.tzdr.domain.dao.userTrade.HandTradeDao;
import com.tzdr.domain.vo.EndPlanOneVo;
import com.tzdr.domain.vo.EndPlanVo;
import com.tzdr.domain.web.entity.HandTrade;
import com.tzdr.domain.web.entity.UserTrade;
import com.tzdr.domain.web.entity.WUser;


/**
 * 
 * 
 * <p>终结方案审核</p>
 * @author LiuQing
 * @see
 * @version 2.0
 * 2015年5月11日上午9:28:08
 */
@Controller
@RequestMapping("/admin/plan/end")
public class EndPlanController  extends BaseCmsController<WUser> {
	
	private static Logger log = LoggerFactory.getLogger(EndPlanController.class);
	
	@Autowired
	private  EndPlanService endPlanService;
	
	
	@RequestMapping(value = "/listReport")
	public String listReport(HttpServletRequest request,HttpServletResponse resp) throws Exception {
		return ViewConstants.EndPlanViewJsp.LIST_VIEW;
	}
	
	public EndPlanController() {
		setResourceIdentity("sys:riskmanager:plan");
	}
	
	
	@RequestMapping(value = "/listData")
	public void listData(HttpServletRequest request,HttpServletResponse resp) throws Exception {
		try {
			
			DataGridVo<EndPlanVo> grid = new DataGridVo<EndPlanVo>();
			ConnditionVo connVo = new ConnditionVo(request);
			PageInfo<EndPlanVo> page = new PageInfo<EndPlanVo>(request);
			if (connVo.isExcel()) {
				page.setCurrentPage(1);
				page.setCountOfCurrentPage(TypeConvert.EXCEL_PAGE_SIZE);
			}
			page = this.endPlanService.queryEndPlan(page, connVo);
//			grid.add(page.getPageResults());
//			grid.setTotal(page.getTotalCount());
//			WebUtil.printText(JSON.toJSONString(grid), resp);
			List<EndPlanOneVo> pageSecond=new ArrayList<EndPlanOneVo>();
			if(connVo.isExcel()){
				
				List<EndPlanVo> pageFirst=page.getPageResults();
				if(!CollectionUtils.isEmpty(pageFirst)){
				for (EndPlanVo endPlanVo : pageFirst) {
					EndPlanOneVo endPlanoneVo=new EndPlanOneVo();
					endPlanoneVo.setMobile(endPlanVo.getMobile());
					endPlanoneVo.setTname(endPlanVo.getTname());
					endPlanoneVo.setAccount(endPlanVo.getAccount());
					endPlanoneVo.setAccountName(endPlanVo.getAccountName());
					endPlanoneVo.setProgramNo(endPlanVo.getProgramNo());
					endPlanoneVo.setLeverMoney(endPlanVo.getLeverMoney());
					endPlanoneVo.setTotalLeverMoney(endPlanVo.getTotalLeverMoney());
					endPlanoneVo.setFeeType(endPlanVo.getFeeType());
					endPlanoneVo.setEndSubTimeStr(endPlanVo.getEndSubTimeStr());
					endPlanoneVo.setActivityTypeStr(endPlanVo.getActivityTypeStr());
					pageSecond.add(endPlanoneVo);
				}
			}
				connVo.isNotExcel(pageSecond, resp,"终结方案待审核列表【审核1】.xls");
				return;
			}
			
			
				grid.add(page.getPageResults());
				grid.setTotal(page.getTotalCount());
				WebUtil.printText(JSON.toJSONString(grid), resp);
			
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/listData02")
	public void listData02(HttpServletRequest request,HttpServletResponse resp) throws Exception {
		try {
			
			DataGridVo<EndPlanVo> grid = new DataGridVo<EndPlanVo>();
			ConnditionVo connVo = new ConnditionVo(request);
			PageInfo<EndPlanVo> page = new PageInfo<EndPlanVo>(request);
			if (connVo.isExcel()) {
				page.setCurrentPage(1);
				page.setCountOfCurrentPage(TypeConvert.EXCEL_PAGE_SIZE);
			}
			page = this.endPlanService.queryEndPlan02(page, connVo);
//			grid.add(page.getPageResults());
//			grid.setTotal(page.getTotalCount());
//			WebUtil.printText(JSON.toJSONString(grid), resp);
			List<EndPlanOneVo> pageSecond=new ArrayList<EndPlanOneVo>();
			if(connVo.isExcel()){
				
				List<EndPlanVo> pageFirst=page.getPageResults();
				if(!CollectionUtils.isEmpty(pageFirst)){
				for (EndPlanVo endPlanVo : pageFirst) {
					EndPlanOneVo endPlanoneVo=new EndPlanOneVo();
					endPlanoneVo.setMobile(endPlanVo.getMobile());
					endPlanoneVo.setTname(endPlanVo.getTname());
					endPlanoneVo.setAccount(endPlanVo.getAccount());
					endPlanoneVo.setAccountName(endPlanVo.getAccountName());
					endPlanoneVo.setProgramNo(endPlanVo.getProgramNo());
					endPlanoneVo.setLeverMoney(endPlanVo.getLeverMoney());
					endPlanoneVo.setTotalLeverMoney(endPlanVo.getTotalLeverMoney());
					endPlanoneVo.setFeeType(endPlanVo.getFeeType());
					endPlanoneVo.setEndSubTimeStr(endPlanVo.getEndSubTimeStr());
					endPlanoneVo.setActivityTypeStr(endPlanVo.getActivityTypeStr());
					pageSecond.add(endPlanoneVo);
				}
			}
				connVo.isNotExcel(pageSecond, resp,"终结方案待审核列表【审核2】.xls");
				return;
			}
			
				grid.add(page.getPageResults());
				grid.setTotal(page.getTotalCount());
				WebUtil.printText(JSON.toJSONString(grid), resp);
			
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/listAllData")
	public void listAllData(HttpServletRequest request,HttpServletResponse resp) throws Exception {
		try {
			
			
			DataGridVo<EndPlanVo> grid = new DataGridVo<EndPlanVo>();
			ConnditionVo connVo = new ConnditionVo(request);
			PageInfo<EndPlanVo> page = new PageInfo<EndPlanVo>(request);
			if (connVo.isExcel()) {
				page.setCurrentPage(1);
				page.setCountOfCurrentPage(TypeConvert.EXCEL_PAGE_SIZE);
			}
			page = this.endPlanService.queryAllEndPlan(page, connVo);
			
			if (connVo.isNotExcel(page.getPageResults(), resp,"终结方案审核记录.xls")) {
				grid.add(page.getPageResults());
				grid.setTotal(page.getTotalCount());
				WebUtil.printText(JSON.toJSONString(grid), resp);
			}
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("/endPlanFail")
	public void updateEndPlanFail(HttpServletRequest request,HttpServletResponse resp) {
		ConnditionVo connVo = new ConnditionVo(request);
		//校验是否已经审核
		String groupId = connVo.getValueStr("groupId");
		if (StringUtil.isBlank(groupId)){
			WebUtil.printText("数据有误，请刷新页面后重试！", resp);
			return;
		}
		
		List<UserTrade> userTrades = this.userTradeService.findByGroupId(groupId);
		if (CollectionUtils.isEmpty(userTrades)){
			WebUtil.printText("数据有误，请刷新页面后重试！", resp);
			return;
		}
		for (UserTrade ut:userTrades) {
			List<HandTrade> handTrades = this.handTradeDao.findByTradeId(ut.getId());
			if (CollectionUtils.isEmpty(handTrades)){
				WebUtil.printText("数据有误，请刷新页面后重试！", resp);
				return;
			}
			int auditEndStatus =  handTrades.get(0).getAuditEndStatus();
			if (TypeConvert.PLAN_NO_PASS == auditEndStatus
					|| TypeConvert.PLAN_PASS == auditEndStatus){
				WebUtil.printText("该方案已审核，请刷新后查看！", resp);
				return;
			}
		}
		
		this.endPlanService.updateEndPlanFail(connVo);
		WebUtil.printText("success", resp);
	}
	
	
	@RequestMapping("/endPlanPassSuccessful")
	public void updateEndPlanSuccessful(HttpServletRequest request,HttpServletResponse resp) {
		ConnditionVo connVo = new ConnditionVo(request);
		
		//校验是否已经审核
		String groupId = connVo.getValueStr("groupId");
		if (StringUtil.isBlank(groupId)){
			WebUtil.printText("数据有误，请刷新页面后重试！", resp);
			return;
		}
		
		List<UserTrade> userTrades = this.userTradeService.findByGroupId(groupId);
		if (CollectionUtils.isEmpty(userTrades)){
			WebUtil.printText("数据有误，请刷新页面后重试！", resp);
			return;
		}
		for (UserTrade ut:userTrades) {
			List<HandTrade> handTrades = this.handTradeDao.findByTradeId(ut.getId());
			if (CollectionUtils.isEmpty(handTrades)){
				WebUtil.printText("数据有误，请刷新页面后重试！", resp);
				return;
			}
			HandTrade  handTrade = handTrades.get(0);
			int auditEndStatus =  handTrade.getAuditEndStatus();
			if (TypeConvert.PLAN_NO_PASS == auditEndStatus
					|| TypeConvert.PLAN_PASS == auditEndStatus
					|| null == handTrade.getFinishedMoney()){
				WebUtil.printText("该方案已审核，请刷新后查看！", resp);
				return;
			}
		}
		
		this.endPlanService.updateEndPlanSuccessful(connVo);
		WebUtil.printText("success", resp);
	}
	
	@RequestMapping("/updateEndPlanSubmit")
	public void updateEndPlanSubmit(HttpServletRequest request,HttpServletResponse resp) {
		ConnditionVo connVo = new ConnditionVo(request);
		
		//校验是否已经审核
		String groupId = connVo.getValueStr("groupId");
		if (StringUtil.isBlank(groupId)){
			WebUtil.printText("数据有误，请刷新页面后重试！", resp);
			return;
		}
		
		List<UserTrade> userTrades = this.userTradeService.findByGroupId(groupId);
		if (CollectionUtils.isEmpty(userTrades)){
			WebUtil.printText("数据有误，请刷新页面后重试！", resp);
			return;
		}
		for (UserTrade ut:userTrades) {
			List<HandTrade> handTrades = this.handTradeDao.findByTradeId(ut.getId());
			if (CollectionUtils.isEmpty(handTrades)){
				WebUtil.printText("数据有误，请刷新页面后重试！", resp);
				return;
			}
			HandTrade  handTrade = handTrades.get(0);
			int auditEndStatus =  handTrade.getAuditEndStatus();
			if (TypeConvert.PLAN_NO_PASS == auditEndStatus
					|| TypeConvert.PLAN_PASS == auditEndStatus
					|| null != handTrade.getFinishedMoney()){
				WebUtil.printText("该方案已审核，请刷新后查看！", resp);
				return;
			}
		}
		this.endPlanService.updateEndPlanSubmit(connVo);
		WebUtil.printText("success", resp);
	}
	
	

	@Override
	public BaseService<WUser> getBaseService() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Autowired
	private UserTradeService userTradeService;
	
	@Autowired
	private HandTradeDao handTradeDao;
	
	
	
	
}

package com.tzdr.cms.controller;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.tzdr.business.service.endplan.EndPlanService;
import com.tzdr.business.service.exception.UserTradeConcurrentException;
import com.tzdr.business.service.userTrade.HandTradeService;
import com.tzdr.business.service.userTrade.UserTradeService;
import com.tzdr.cms.constants.ViewConstants;
import com.tzdr.cms.support.BaseCmsController;
import com.tzdr.cms.utils.DataGridVo;
import com.tzdr.cms.utils.WebUtil;
import com.tzdr.common.baseservice.BaseService;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.utils.ConnditionVo;
import com.tzdr.common.utils.TypeConvert;
import com.tzdr.domain.vo.ParentAccountExpireEndVo;
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
@RequestMapping("/admin/parentAccount/expire")
public class ParentAccountExpireEndController  extends BaseCmsController<WUser> {
	
	private static Logger log = LoggerFactory.getLogger(ParentAccountExpireEndController.class);
	
	@Autowired
	private  EndPlanService endPlanService;
	
	@Autowired
	private UserTradeService userTradeService;
	
	@Autowired
	private HandTradeService handTradeService;
	
	@RequestMapping(value = "/list")
	public String listReport(HttpServletRequest request,HttpServletResponse resp) throws Exception {
		return ViewConstants.ParentAccountExpireEndViewJsp.LIST_VIEW;
	}
	
	public ParentAccountExpireEndController() {
		setResourceIdentity("sys:parentAccount:expire");
	}
	
	@RequestMapping(value = "/endOfProgram")
	public void endOfProgram(HttpServletRequest request,HttpServletResponse resp) {
		ConnditionVo conn = new ConnditionVo(request);
		String groupId = conn.getValueStr("groupId");
		if (groupId == null) {
			WebUtil.printText("终结方案groupId不能为空!", resp);
			return;
		}
		List<UserTrade> userTrades = this.userTradeService.findByGroupIdOrderByAddtimeAsc(groupId);
		if (userTrades != null && userTrades.size() > 0) {
			UserTrade userTrade = userTrades.get(0);
				//涌金版
				if (userTrade.getFeeType() == 2) {
					HandTrade handTrade = this.handTradeService.findByTradeId(userTrade.getId());
					//终结待审核
					handTrade.setAuditEndStatus(0);
					handTrade.setEndSubTime(TypeConvert.dbDefaultDate());
					this.handTradeService.update(handTrade);
					WebUtil.printText("subSuccess", resp);
				    return;
				}
				else if (userTrade.getFeeType() == 1 || userTrade.getFeeType() == 0) {
					try {
						this.userTradeService.endOfProgram(groupId);
						WebUtil.printText("success", resp);
					} 
					catch (UserTradeConcurrentException e) {
						//e.printStackTrace();
						WebUtil.printText(e.getResourceMessage(), resp);
					}
					return;
				}
		}
		WebUtil.printText("不能重复执行", resp);
		
	}
	
	
	@RequestMapping(value = "/listData")
	public void listData(HttpServletRequest request,HttpServletResponse resp) throws Exception {
		try {
			
			DataGridVo<ParentAccountExpireEndVo> grid = new DataGridVo<ParentAccountExpireEndVo>();
			ConnditionVo connVo = new ConnditionVo(request);
			PageInfo<ParentAccountExpireEndVo> page = new PageInfo<ParentAccountExpireEndVo>(request);
			page = this.endPlanService.queryParentAccountExpire(page, connVo);
			grid.add(page.getPageResults());
			grid.setTotal(page.getTotalCount());
			WebUtil.printText(JSON.toJSONString(grid), resp);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public BaseService<WUser> getBaseService() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	
}

package com.tzdr.cms.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.tzdr.business.service.userTrade.UserTradeService;
import com.tzdr.cms.constants.ViewConstants;
import com.tzdr.cms.support.BaseCmsController;
import com.tzdr.cms.utils.DataGridVo;
import com.tzdr.cms.utils.WebUtil;
import com.tzdr.common.baseservice.BaseService;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.domain.vo.SettingNotWarningVo;
import com.tzdr.domain.vo.SettingWarningVo;
import com.tzdr.domain.web.entity.UserTrade;

/**
 * 
 * <B>说明: </B> 操盘方案风控线设置
 * @zhouchen
 * 2016年1月21日 上午9:30:27
 */
@Controller
@RequestMapping("/admin/setWaring")
public class SetWarningController extends BaseCmsController<UserTrade> {
	private static Logger log = LoggerFactory.getLogger(SetWarningController.class);

	@Autowired
	private UserTradeService userTradeService;
	
	@Override
	public BaseService<UserTrade> getBaseService() {
		return userTradeService;
	}

	public SetWarningController() {
		setResourceIdentity("sys:riskmanager:setWaring");
	}
	
	/**
	 * 风控线设置列表
	 * @return
	 */
	@RequestMapping(value = "/listSettingWarning")
	public String listSettingWarning() {
		return ViewConstants.WuserViewJsp.LIST_SETTING_WARNING_VIEW;
	}
	
	
	
	/**
	 * 获取已经设置过风控线的操盘方案
	 * @param request HttpServletRequest
	 * @param resp HttpServletResponse
	 * @throws Exception
	 */
	@RequestMapping(value = "/notSettingWarning")
	public void notSettingWarning(@ModelAttribute SettingNotWarningVo settingNotWarningVo,
			HttpServletRequest request,HttpServletResponse resp) throws Exception {
			try {
				DataGridVo<SettingNotWarningVo> grid = new DataGridVo<SettingNotWarningVo>();
				PageInfo<SettingNotWarningVo> dataPage = new PageInfo<SettingNotWarningVo>(request);
				PageInfo<SettingNotWarningVo> userTrades = 
						this.userTradeService.queryNotSettingWarning(dataPage,settingNotWarningVo);
				grid.add(userTrades.getPageResults());
				grid.setTotal(userTrades.getTotalCount());
				WebUtil.printText(JSON.toJSONString(grid), resp);
				
			} 
			catch (Exception e) {
				e.printStackTrace();
			}
	}
	
	/**
	 * 获取需要设置风控线的操盘方案
	 * @param request HttpServletRequest
	 * @param resp HttpServletResponse
	 * @throws Exception
	 */
	@RequestMapping(value = "/settingWarning")
	public void settingWarning(@ModelAttribute SettingWarningVo settingWarningVo,
			HttpServletRequest request,HttpServletResponse resp) throws Exception {
			try {
				DataGridVo<SettingWarningVo> grid = new DataGridVo<SettingWarningVo>();
				PageInfo<SettingWarningVo> dataPage = new PageInfo<SettingWarningVo>(request);
				PageInfo<SettingWarningVo> voes = this.userTradeService.querySettingWarning(dataPage,settingWarningVo);
				grid.add(voes.getPageResults());
				grid.setTotal(voes.getTotalCount());
				WebUtil.printText(JSON.toJSONString(grid), resp);
			} 
			catch (Exception e) {
				e.printStackTrace();
			}
	}
	
	
	/**
	 * 设置操盘方案
	 * @param request
	 * @param resp
	 */
	@RequestMapping(value = "/updateSettingState")
	public void updateSettingState(HttpServletRequest request,HttpServletResponse resp) {
		String id = request.getParameter("id");
		this.userTradeService.updateUserTradeById(id, 1);
		WebUtil.printText("success", resp);
	}
}

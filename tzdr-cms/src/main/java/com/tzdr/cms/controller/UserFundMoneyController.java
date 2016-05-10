package com.tzdr.cms.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.tzdr.business.service.userfundmoney.UserFundMoneyService;
import com.tzdr.cms.constants.ViewConstants;
import com.tzdr.cms.support.BaseCmsController;
import com.tzdr.cms.utils.DataGridVo;
import com.tzdr.cms.utils.WebUtil;
import com.tzdr.common.baseservice.BaseService;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.domain.vo.UserFundMoneyVo;
import com.tzdr.domain.web.entity.UserFund;

/**
 * 查询追加保证金
 * <P>title:@CautionMoneyController.java</p>																								
 * <P>Description：</p>
 * <p>Copyright: Copyright (c) 2015 tzdr</p>
 * <p>Company: 上海信宏</p>
 * History：
 * @author:zhangjun
 * @date 2015年3月31日
 * @version 1.0
 */

@Controller
@RequestMapping("/admin/userFundMoney")
public class UserFundMoneyController  extends BaseCmsController<UserFund> {
	@Autowired
	private UserFundMoneyService userFundMoneyService;
	/**
	 * 到查询列表
	 * @param request
	 * @return
	 * @date 2015年3月31日
	 * @author zhangjun
	 */
	@RequestMapping(value="/list")
	public String list(HttpServletRequest  request){
		return ViewConstants.UserFundMoneyViewJsp.LIST_VIEW;
	}
	@RequestMapping(value="/listProfit")
	public String listProfit(HttpServletRequest  request){
		return ViewConstants.UserFundMoneyViewJsp.LIST_PROFIT_VIEW;
	}
	

	@RequestMapping(value = "/listData")
	public void listData(@ModelAttribute UserFundMoneyVo userFundMoneyVo,String type,
			HttpServletRequest request,HttpServletResponse resp) throws Exception {
		try {
			DataGridVo<UserFundMoneyVo> grid = new DataGridVo<UserFundMoneyVo>();
			PageInfo<UserFundMoneyVo> dataPage = new PageInfo<UserFundMoneyVo>(request);
			PageInfo<UserFundMoneyVo> datas = this.userFundMoneyService.queryData(dataPage,userFundMoneyVo,type);
			grid.add(datas.getPageResults());
			grid.setTotal(datas.getTotalCount());
			WebUtil.printText(JSON.toJSONString(grid), resp);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Override
	public BaseService<UserFund> getBaseService() {
		return userFundMoneyService;
	}
	
	@Override
	public void setResourceIdentity(String resourceIdentity) {
		super.setResourceIdentity("sys:customerService:appLeverMoney");
	}
}


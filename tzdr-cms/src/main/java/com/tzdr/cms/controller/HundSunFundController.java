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
import com.hundsun.t2sdk.interfaces.T2SDKException;
import com.tzdr.business.service.stock.StockCurrentHistoryService;
import com.tzdr.cms.constants.ViewConstants;
import com.tzdr.cms.support.BaseCmsController;
import com.tzdr.cms.utils.DataGridVo;
import com.tzdr.cms.utils.WebUtil;
import com.tzdr.common.baseservice.BaseService;
import com.tzdr.common.utils.ConnditionVo;
import com.tzdr.domain.vo.HundSunFundVo;
import com.tzdr.domain.web.entity.StockCurrentHistory;

@Controller
@RequestMapping("/admin/hundSundFund")
public class HundSunFundController extends BaseCmsController<StockCurrentHistory> {
	private static Logger log = LoggerFactory
			.getLogger(HundSunFundController.class);

	@Autowired
	private StockCurrentHistoryService stockCurrentHistoryService;

	@Override
	public BaseService<StockCurrentHistory> getBaseService() {
		return stockCurrentHistoryService;
	}
	public HundSunFundController() {
		setResourceIdentity("sys:finance:hundSundFund");
	}
	
	/**
	 * 显示列表列表
	 * @param request
	 * @throws T2SDKException 
	 */
	@RequestMapping(value="list")
	public String marginRemindList(HttpServletRequest request){
		return ViewConstants.WithdrawalViewJsp.HUNDSUN_FUND_LIST_VIEW;
	}

	/**
	 * 获取数据
	 * @param request
	 * @throws T2SDKException 
	 */
	@RequestMapping(value="data")
	public void getData(HttpServletRequest request,HttpServletResponse response){
		ConnditionVo connVo = new ConnditionVo(request);
		List<HundSunFundVo> hundSunFundVos = stockCurrentHistoryService.getHundSunFunds(connVo);
		if (connVo.isNotExcel(hundSunFundVos, response,"恒生资金表.xls")) {
			DataGridVo<HundSunFundVo> grid = new DataGridVo<HundSunFundVo>();
			grid.add(hundSunFundVos);
			WebUtil.printText(JSON.toJSONString(grid), response);
		}
		
	}
}

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
import com.tzdr.common.utils.ConnditionVo;
import com.tzdr.common.utils.TypeConvert;
import com.tzdr.domain.vo.UserTradeSummaryVo;
import com.tzdr.domain.web.entity.UserTrade;

/**
 * 配资过的用户配资统计信息
 * @author 张军
 *
 */
@Controller
@RequestMapping("/admin/userTradeSummary")
public class UserTradeSummaryController extends BaseCmsController<UserTrade>{
	private static Logger log = LoggerFactory
			.getLogger(UserTradeSummaryController.class);
	@Autowired
	private UserTradeService userTradeService;

	public UserTradeSummaryController() {
		setResourceIdentity("sys:customerService:userTradeSummary");
	}
	/**
	 * 配资用户显示页面
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/list")
	public String list(HttpServletRequest  request){
		return ViewConstants.UserTradeSummaryViewJsp.LIST_VIEW;
	}
	
	@RequestMapping(value = "/listData")
	public void listData(@ModelAttribute UserTradeSummaryVo userTradeSummaryVo,String type,
			HttpServletRequest request,HttpServletResponse resp) throws Exception {
		try {
			ConnditionVo connVo = new ConnditionVo(request);
			DataGridVo<UserTradeSummaryVo> grid = new DataGridVo<UserTradeSummaryVo>();
			PageInfo<UserTradeSummaryVo> datas = new PageInfo<UserTradeSummaryVo>(request);
			datas = this.userTradeService.queryTradeSummaryData(datas,userTradeSummaryVo);
			if (connVo.isExcel()) {
				datas.setCurrentPage(1);
				datas.setCountOfCurrentPage(TypeConvert.EXCEL_PAGE_SIZE);
			}
			if (connVo.isNotExcel(datas.getPageResults(), resp, "配资用户列表.xls") ) {
				grid.add(datas.getPageResults());
				grid.setTotal(datas.getTotalCount());
				WebUtil.printText(JSON.toJSONString(grid), resp);
			}
			
		} 
		catch (Exception e) {
			log.error("查询配资用户汇总失败"+e.getMessage());
		}
	}
	@Override
	public BaseService<UserTrade> getBaseService() {
		
		return this.getBaseService();
	}
	
}

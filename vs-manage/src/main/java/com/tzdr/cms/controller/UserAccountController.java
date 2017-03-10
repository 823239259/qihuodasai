package com.tzdr.cms.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.tzdr.business.service.userTrade.FSimpleFtseUserTradeService;
import com.tzdr.business.service.wuser.WUserService;
import com.tzdr.cms.constants.ViewConstants;
import com.tzdr.cms.support.BaseCmsController;
import com.tzdr.cms.utils.DataGridVo;
import com.tzdr.cms.utils.WebUtil;
import com.tzdr.common.baseservice.BaseService;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.utils.ConnditionVo;
import com.tzdr.common.utils.TypeConvert;
import com.tzdr.domain.vo.FSimpleFtseUserTradeWebVo;
import com.tzdr.domain.vo.HandUserFundVoNew;
import com.tzdr.domain.vo.WuserListVo;
import com.tzdr.domain.vo.ftse.FSimpleFtseVo;
import com.tzdr.domain.web.entity.WUser;

/**
 * 操盘账户
 * @author gc
 *
 */
@Controller
@RequestMapping("/admin/userAccount")
public class UserAccountController extends BaseCmsController<WUser> {
	private static Logger log = LoggerFactory.getLogger(WuserController.class);
	@Autowired
	private WUserService wuserService;
	
	@Autowired
	private FSimpleFtseUserTradeService simpleFtseUserTradeService;
	
	
	@Override
	public BaseService<WUser> getBaseService() {
		return null;
	}
	public UserAccountController() {
		setResourceIdentity("sys:customerService:userAccount");
	}
	@RequestMapping(value = "/list")
	public String list(HttpServletRequest request, HttpServletResponse resp) throws Exception {
		return ViewConstants.WuserViewJsp.ACCOUNT_LIST_VIEW;
	}
	@RequestMapping(value = "/listData")
	public void listData(HttpServletRequest request, HttpServletResponse resp) throws Exception {
		try {
			DataGridVo<WuserListVo> grid = new DataGridVo<WuserListVo>();
			PageInfo<WuserListVo> dataPage = new PageInfo<WuserListVo>(request);
			ConnditionVo connVo = new ConnditionVo(request);
			if (connVo.isExcel()) {
				dataPage.setCurrentPage(1);
				dataPage.setCountOfCurrentPage(TypeConvert.EXCEL_PAGE_SIZE);
			}
			dataPage = this.wuserService.queryDataPageWuserListVo(dataPage, connVo);
			if(connVo.isNotExcel(dataPage.getPageResults(), resp,"所有用戶列表.xls")){
				if (dataPage.getPageResults() != null) {
					for (WuserListVo wu : dataPage.getPageResults()) {
						grid.add(wu);
					}
					grid.setTotal(dataPage.getTotalCount());
				}
			
			}
			WebUtil.printText(JSON.toJSONString(grid), resp);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@RequestMapping(value = "/queryUsertradeList")
	public void queryUsertradeList(HttpServletRequest request, HttpServletResponse resp) throws Exception {
		ConnditionVo conn = new ConnditionVo(request);
		DataGridVo<FSimpleFtseUserTradeWebVo> grid = new DataGridVo<FSimpleFtseUserTradeWebVo>();
		PageInfo<FSimpleFtseUserTradeWebVo> dataPage = new PageInfo<FSimpleFtseUserTradeWebVo>(request);
		PageInfo<FSimpleFtseUserTradeWebVo> voes = this.simpleFtseUserTradeService.queryUsertradeList(dataPage, conn);
		grid.add(voes.getPageResults());
		grid.setTotal(voes.getTotalCount());
		WebUtil.printText(JSON.toJSONString(grid), resp);
		
	
	}

}

package com.tzdr.cms.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.tzdr.business.service.api.yjfinance.IdentityCardHistoryService;
import com.tzdr.cms.constants.ViewConstants;
import com.tzdr.cms.support.BaseCmsController;
import com.tzdr.cms.utils.DataGridVo;
import com.tzdr.cms.utils.WebUtil;
import com.tzdr.common.baseservice.BaseService;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.utils.ConnditionVo;
import com.tzdr.common.utils.TypeConvert;
import com.tzdr.domain.web.entity.IdentityCardHistory;

/**
 * @author LinFeng
 * @version 创建时间：2015年4月14日
 * 身份证
 */
@Controller
@RequestMapping("/admin/identity")
public class IdentityCardController extends BaseCmsController<IdentityCardHistory> {
	private static Logger log = LoggerFactory.getLogger(IdentityCardController.class);

	@Autowired
	private IdentityCardHistoryService identityCardHistoryService;
	
	
	@Override
	public BaseService<IdentityCardHistory> getBaseService() {
		return identityCardHistoryService;
	}

	public IdentityCardController() {
		setResourceIdentity("sys:customerService:identity");
	}
	
	
	@RequestMapping(value = "/list")
	public String list(HttpServletRequest request,HttpServletResponse resp) throws Exception {
		return ViewConstants.WuserViewJsp.LIST_IDENTITY_VIEW;
	}
	
	/**
	 * 获取数据
	 * @param request
	 */
	@RequestMapping(value="data")
	public void getData(HttpServletRequest request,HttpServletResponse response){
		ConnditionVo connVo = new ConnditionVo(request);
		PageInfo page = new PageInfo(request);
		if (connVo.isExcel()) {
			page.setCurrentPage(1);
			page.setCountOfCurrentPage(TypeConvert.EXCEL_PAGE_SIZE);
		}
		page = identityCardHistoryService.list(connVo,page);
		if (connVo.isNotExcel(page.getPageResults(), response,"身份证认证.xls")) {
			DataGridVo<IdentityCardHistory> grid = new DataGridVo<IdentityCardHistory>();
			grid.add(page.getPageResults());
			grid.setTotal(page.getTotalCount());
			WebUtil.printText(JSON.toJSONString(grid), response);
		}
		
	}
	
}

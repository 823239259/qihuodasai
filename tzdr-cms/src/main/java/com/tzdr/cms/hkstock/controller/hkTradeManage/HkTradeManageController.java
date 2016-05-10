package com.tzdr.cms.hkstock.controller.hkTradeManage;

import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jodd.util.StringUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tzdr.business.hkstock.service.HkUserTradeService;
import com.tzdr.cms.constants.ViewConstants;
import com.tzdr.cms.hkstock.constants.HkViewConstants;
import com.tzdr.cms.support.BaseCmsController;
import com.tzdr.common.baseservice.BaseService;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.utils.ConnditionVo;
import com.tzdr.common.utils.EasyuiUtil;
import com.tzdr.common.utils.TypeConvert;
import com.tzdr.common.web.support.EasyUiPageData;
import com.tzdr.common.web.support.EasyUiPageInfo;
import com.tzdr.domain.hkstock.entity.HkUserTrade;

@Controller
@RequestMapping("/admin/hkstock/hkTradeManage")
public class HkTradeManageController extends BaseCmsController<HkUserTrade> {

	private static Logger logger = LoggerFactory.getLogger(HkTradeManageController.class);

	@Autowired
	private HkUserTradeService hkUserTradeService;

	@Override
	public BaseService<HkUserTrade> getBaseService() {
		// TODO Auto-generated method stub
		return hkUserTradeService;
	}

	@Override
	public void setResourceIdentity(String resourceIdentity) {
		// TODO Auto-generated method stub
		super.setResourceIdentity("sys:riskmanager:hkTradeManage");
	}

	/**
	 * 跳转到数据列表页
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/list")
	public String list(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return HkViewConstants.HkTradeManageJsp.LIST_VIEW;
	}

	/**
	 * 获取数据
	 * 
	 * @param easyUiPage
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getData")
	@ResponseBody
	public Object getData(EasyUiPageInfo easyUiPage, HttpServletRequest req, HttpServletResponse resp)
			throws Exception {
		// 查看权限
		if (permissionList != null) {
			this.permissionList.assertHasViewPermission();
		}
		ConnditionVo connVo = new ConnditionVo(req);
		if (connVo.isExcel()) {
			easyUiPage.setPage(1);
			easyUiPage.setRows(TypeConvert.EXCEL_PAGE_SIZE);
		}
		// 获取模糊搜索参数
		Map<String, Object> searchParams = EasyuiUtil.getParametersStartingWith(req, EasyuiUtil.SEARCH_PREFIX);
		// 排序设置
		EasyuiUtil.getMultilistSortMap(easyUiPage);

		PageInfo<Object> pageInfo = hkUserTradeService.getTradeData(easyUiPage, searchParams);
		if (connVo.isNotExcel(pageInfo.getPageResults(), resp, "港股方案列表.xls")) {
			return new EasyUiPageData<>(pageInfo);
		}
		return new EasyUiPageData<>(pageInfo);
	}

	/**
	 * 跳转子方案
	 * 
	 * @param request
	 * @param groupId
	 * @return
	 */
	@RequestMapping(value = "/tradeDetail")
	public String tradeDetail(HttpServletRequest request, @RequestParam("groupId") String groupId) {
		request.setAttribute("groupId", groupId);
		return HkViewConstants.HkTradeManageJsp.DETAIL_VIEW;
	}

	/**
	 * 查询子方案
	 * @param easyUiPage
	 * @param model
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/queryChildTrades", method = RequestMethod.POST)
	@ResponseBody
	public Object queryChildTrades(EasyUiPageInfo easyUiPage, Model model, ServletRequest request) throws Exception {

		// 查看权限
		if (permissionList != null) {
			this.permissionList.assertHasViewPermission();
		}

		// 获取模糊搜索参数
		Map<String, Object> searchParams = EasyuiUtil.getParametersStartingWith(request, EasyuiUtil.SEARCH_PREFIX);
		// 排序设置
		EasyuiUtil.getMultilistSortMap(easyUiPage);

		PageInfo<Object> pageInfo = hkUserTradeService.queryChildTrades(easyUiPage, searchParams);
		return new EasyUiPageData<>(pageInfo);
	}

}

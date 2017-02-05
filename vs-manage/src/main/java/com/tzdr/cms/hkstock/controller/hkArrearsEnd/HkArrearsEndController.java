package com.tzdr.cms.hkstock.controller.hkArrearsEnd;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tzdr.business.hkstock.service.HkUserTradeService;
import com.tzdr.cms.hkstock.constants.HkViewConstants;
import com.tzdr.cms.support.BaseCmsController;

import com.tzdr.common.baseservice.BaseService;
import com.tzdr.common.domain.PageInfo;

import com.tzdr.common.utils.EasyuiUtil;
import com.tzdr.common.web.support.EasyUiPageData;
import com.tzdr.common.web.support.EasyUiPageInfo;
import com.tzdr.common.web.support.JsonResult;
import com.tzdr.domain.hkstock.entity.HkUserTrade;

import jodd.util.StringUtil;

/**
 * 港股欠费方案
 * @Description: 
 * @author liuhaichuan
 * @date 2015年10月21日
 *
 */
@Controller
@RequestMapping("/admin/hkstock/hkArrearsEnd")
public class HkArrearsEndController extends BaseCmsController<HkUserTrade> {

	private static Logger logger = LoggerFactory.getLogger(HkArrearsEndController.class);

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
		super.setResourceIdentity("sys:riskmanager:hkArrearsEnd");
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
		return HkViewConstants.HkArrearsEndJsp.LIST_VIEW;
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
		// 获取模糊搜索参数
		Map<String, Object> searchParams = EasyuiUtil.getParametersStartingWith(req, EasyuiUtil.SEARCH_PREFIX);
		// 排序设置
		EasyuiUtil.getMultilistSortMap(easyUiPage);
		PageInfo<Object> pageInfo = hkUserTradeService.getArrearsData(easyUiPage, searchParams);
		return new EasyUiPageData<>(pageInfo);
	}

	/**
	 * 终结方案
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/endPlan", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult endPlan(@RequestParam("groupIds")String groupIds) {
		JsonResult result = new JsonResult(false);
		if(StringUtil.isNotBlank(groupIds)){
			hkUserTradeService.endPlan(groupIds.split(","));
			result.setSuccess(true);
			result.setMessage("操作成功");
		} else {
			result.setMessage("传入的数据不正确");
		}
//		try {
//			
//		} catch (Exception e) {
//			// TODO: handle exception
//			logger.debug(e.getMessage());
//			e.printStackTrace();
//			result.setMessage("操作失败");
//		}
		return result;
	}

	/**
	 * 获取欠费明细
	 * 
	 * @param easyUiPage
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getDetail")
	@ResponseBody
	public Object getDetail(EasyUiPageInfo easyUiPage, HttpServletRequest req, HttpServletResponse resp)
			throws Exception {
		// 查看权限
		if (permissionList != null) {
			this.permissionList.assertHasViewPermission();
		}
		// 获取模糊搜索参数
		Map<String, Object> searchParams = EasyuiUtil.getParametersStartingWith(req, EasyuiUtil.SEARCH_PREFIX);
		// 排序设置
		EasyuiUtil.getMultilistSortMap(easyUiPage);
		PageInfo<Object> pageInfo = hkUserTradeService.getArrearsDetail(easyUiPage, searchParams);
		return new EasyUiPageData<>(pageInfo);
	}
}

package com.tzdr.cms.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jodd.util.StringUtil;

import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tzdr.business.service.userTrade.AppendLevelMoneyFailService;
import com.tzdr.cms.constants.Constants;
import com.tzdr.cms.constants.ViewConstants;
import com.tzdr.cms.support.BaseCmsController;
import com.tzdr.common.baseservice.BaseService;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.utils.ConnditionVo;
import com.tzdr.common.utils.EasyuiUtil;
import com.tzdr.common.utils.TypeConvert;
import com.tzdr.common.web.support.EasyUiPageData;
import com.tzdr.common.web.support.EasyUiPageInfo;
import com.tzdr.common.web.support.JsonResult;
import com.tzdr.domain.web.entity.AppendLevelMoneyFail;

/**
 * 追加保证金失败处理
 * @zhouchen
 * 2015年4月11日
 */
@Controller
@RequestMapping("/admin/appendMoneyFail")
public class AppendLevelMoneyFailController extends BaseCmsController<AppendLevelMoneyFail>{

	private static Logger log = LoggerFactory.getLogger(AppendLevelMoneyFailController.class);
	
	@Autowired
	private AppendLevelMoneyFailService  appendLevelMoneyFailService;
	
	@Override
	public BaseService<AppendLevelMoneyFail> getBaseService() {
		return appendLevelMoneyFailService;
	}

	public AppendLevelMoneyFailController() {
		setResourceIdentity("sys:riskmanager:appendMoneyFail");
	}
	
	
	@RequestMapping(value="list")
	public String list(HttpServletRequest  request){
		
		return ViewConstants.MatchFundsViewJsp.APPENDMONEY_FAIL_LIST_VIEW;
	}
	
	/**
	 *  含参数的搜索  search_EQ_name
	 * @param easyUiPage
	 * @param model
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getDatas", method = RequestMethod.POST)
	@ResponseBody
	public Object easyuiPage(EasyUiPageInfo easyUiPage, Model model,
			HttpServletRequest  request,HttpServletResponse response) throws Exception{
		ConnditionVo connVo = new ConnditionVo(request);
		if (connVo.isExcel()) {
			easyUiPage.setPage(1);
			easyUiPage.setRows(TypeConvert.EXCEL_PAGE_SIZE);
		}
		  //查看权限
		if (permissionList != null) {
	            this.permissionList.assertHasViewPermission();
	    }
		//获取模糊搜索参数
		Map<String, Object> searchParams = EasyuiUtil.getParametersStartingWith(request,EasyuiUtil.SEARCH_PREFIX);
		//排序设置
		if(StringUtil.isBlank(easyUiPage.getSort())){
			easyUiPage.setSort("appendDate");
			easyUiPage.setOrder("asc");
		}
		EasyuiUtil.getMultilistSortMap(easyUiPage);
		
		PageInfo<Object> pageInfo = appendLevelMoneyFailService.queryList(easyUiPage, searchParams);
		if (connVo.isNotExcel(pageInfo.getPageResults(), response,"追加保证金.xls")) {
			return new EasyUiPageData(pageInfo);
		}
		return new EasyUiPageData(pageInfo);
	}

	/**
	 * 处理失败列表
	 * @param request
	 * @param ids
	 * @return
	 */
	@RequestMapping(value="/handleFail")
	@ResponseBody
	public JsonResult handleFail(HttpServletRequest request,
			@RequestParam("ids") String  ids){
		String [] idArray = ids.split(Constants.SEPERATOR_COMMA);
		if (ArrayUtils.isEmpty(idArray)){
			log.debug("处理追加保证金失败时，未选择数据！");
			return new JsonResult(Boolean.FALSE,"处理追加保证金失败时，未选择数据！");
		}
		return  appendLevelMoneyFailService.handleFail(idArray);
	
	}
}

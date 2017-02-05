package com.tzdr.cms.controller;

import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

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

import com.tzdr.business.service.userTrade.UserTradeService;
import com.tzdr.cms.constants.Constants;
import com.tzdr.cms.constants.ViewConstants;
import com.tzdr.cms.support.BaseCmsController;
import com.tzdr.common.baseservice.BaseService;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.utils.EasyuiUtil;
import com.tzdr.common.web.support.EasyUiPageData;
import com.tzdr.common.web.support.EasyUiPageInfo;
import com.tzdr.common.web.support.JsonResult;
import com.tzdr.domain.web.entity.UserTrade;

/**
 * @author zhouchen
 * @version 创建时间：2014年12月26日 上午11:45:59
 * 配资失败 操作 controller
 */
@Controller
@RequestMapping("/admin/tradeFail")
public class TradeFailController extends BaseCmsController<UserTrade>{

	private static Logger log = LoggerFactory.getLogger(TradeFailController.class);
	
	@Autowired
	private UserTradeService  userTradeService;
	
	@Override
	public BaseService<UserTrade> getBaseService() {
		return userTradeService;
	}

	public TradeFailController() {
		setResourceIdentity("sys:riskmanager:tradeFail");
	}
	
	@RequestMapping(value="list")
	public String list(HttpServletRequest  request){
		
		return ViewConstants.MatchFundsViewJsp.TRADE_FAIL_LIST_VIEW;
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
			ServletRequest request) throws Exception{
		
		  //查看权限
		if (permissionList != null) {
	            this.permissionList.assertHasViewPermission();
	    }
		
		//获取模糊搜索参数
		Map<String, Object> searchParams = EasyuiUtil.getParametersStartingWith(request,EasyuiUtil.SEARCH_PREFIX);
		//排序设置
		EasyuiUtil.getMultilistSortMap(easyUiPage);
		
		PageInfo<Object> pageInfo = userTradeService.queryTradeFailList(easyUiPage, searchParams);
		
		return new EasyUiPageData(pageInfo);
	}

	/**
	 * 审核
	 * @param request
	 * @param ids
	 * @return
	 */
	@RequestMapping(value="review ")
	@ResponseBody
	public JsonResult review(HttpServletRequest request,
			@RequestParam("ids") String  ids){
		String [] idArray = ids.split(Constants.SEPERATOR_COMMA);
		if (ArrayUtils.isEmpty(idArray)){
			log.debug("审核配资时，未选择数据！");
			return new JsonResult(Boolean.FALSE,"审核时，未选择数据！");
		}
		userTradeService.reviewTrade(idArray);
		return new JsonResult("操作成功");
	}
}

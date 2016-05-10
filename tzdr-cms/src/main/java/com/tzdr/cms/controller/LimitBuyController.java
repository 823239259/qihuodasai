package com.tzdr.cms.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tzdr.business.service.userTrade.UserTradeService;
import com.tzdr.cms.constants.Constants;
import com.tzdr.cms.constants.ViewConstants;
import com.tzdr.cms.support.BaseCmsController;
import com.tzdr.common.baseservice.BaseService;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.utils.EasyuiUtil;
import com.tzdr.common.utils.MessageUtils;
import com.tzdr.common.web.support.EasyUiPageData;
import com.tzdr.common.web.support.EasyUiPageInfo;
import com.tzdr.common.web.support.JsonResult;
import com.tzdr.domain.web.entity.UserTrade;

@Controller
@RequestMapping("/admin/limitBuy")
public class LimitBuyController extends BaseCmsController<UserTrade> {
	private static Logger log = LoggerFactory
			.getLogger(LimitBuyController.class);

	@Autowired
	private UserTradeService userTradeService;

	@Override
	public BaseService<UserTrade> getBaseService() {
		return userTradeService;
	}
	public LimitBuyController() {
		setResourceIdentity("sys:riskmanager:limitbuy");
	}
	
	/**
	 * 限制买入列表页面
	 * @param request
	 * @return
	 */
	@RequestMapping(value="list")
	public String limitList(HttpServletRequest request){
		return ViewConstants.MatchFundsViewJsp.LIMIT_BUY_LIST_VIEW;
	}

	
	/**
	 * 取消限制买入列表页面
	 * @param request
	 * @return
	 */
	@RequestMapping(value="cancleList")
	public String canclelist(HttpServletRequest request){
		return ViewConstants.MatchFundsViewJsp.CANCEL_LIMIT_BUY_LIST_VIEW;
	}
	
	/**
	 *限制买入列表页面数据
	 * @param request
	 */
	@RequestMapping(value="data")
	@ResponseBody
	public Object getData(HttpServletRequest request,EasyUiPageInfo easyUiPage,Short limitBuyStaus){
		
		//获取模糊搜索参数
		Map<String, Object> searchParams = EasyuiUtil.getParametersStartingWith(request,EasyuiUtil.SEARCH_PREFIX);
				
		 PageInfo<Object> pageInfo  = userTradeService.findBuyLimitBuyStatus(easyUiPage, searchParams, limitBuyStaus);
		return new EasyUiPageData(pageInfo);
	}
	
	/**
	 * 更新限制购买状态
	 * @param request
	 * @param limitBuyStatus
	 * @param groupIds
	 * @return
	 */
	@RequestMapping(value="changeLimitBuyStatus")
	@ResponseBody
	public JsonResult changeLimitBuyStatus(HttpServletRequest request,@RequestParam("limitBuyStatus") Short limitBuyStatus,
			@RequestParam("groupIds") String  groupIds){
		String [] groupIdArray = groupIds.split(Constants.SEPERATOR_COMMA);
		if (ArrayUtils.isEmpty(groupIdArray)){
			return new JsonResult(Boolean.FALSE,MessageUtils.message("user.trade.limit.buy.change.status"));
		}
		
		//userTradeService.changeLimitBuyStatus(groupIdArray, limitBuyStatus);
		return new JsonResult(MessageUtils.message("update.success"));
	}
}

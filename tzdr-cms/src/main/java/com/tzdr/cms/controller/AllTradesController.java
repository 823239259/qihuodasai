package com.tzdr.cms.controller;

import java.util.List;
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
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tzdr.business.service.pay.UserFundService;
import com.tzdr.business.service.userTrade.UserTradeService;
import com.tzdr.business.service.wuser.WUserService;
import com.tzdr.cms.constants.ViewConstants;
import com.tzdr.cms.support.BaseCmsController;
import com.tzdr.common.baseservice.BaseService;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.utils.ConnditionVo;
import com.tzdr.common.utils.EasyuiUtil;
import com.tzdr.common.utils.TypeConvert;
import com.tzdr.common.web.support.EasyUiPageData;
import com.tzdr.common.web.support.EasyUiPageInfo;
import com.tzdr.domain.vo.WuserParentVo;
import com.tzdr.domain.web.entity.UserFund;
import com.tzdr.domain.web.entity.UserTrade;

/**
 * @author zhouchen
 * @version 创建时间：2014年12月26日 上午11:45:59
 * 未注销的子账户管理controller
 */
@Controller
@RequestMapping("/admin/allTrades")
public class AllTradesController extends BaseCmsController<UserTrade>{

	private static Logger log = LoggerFactory.getLogger(AllTradesController.class);

	
	@Autowired
	private UserTradeService  userTradeService;
	
	@Autowired
	private UserFundService  userFundService;
	
	@Autowired
	private WUserService  wUserService;
	
	@Override
	public BaseService<UserTrade> getBaseService() {
		return userTradeService;
	}

	public AllTradesController() {
		setResourceIdentity("sys:riskmanager:allTrades");
	}
	
	@RequestMapping(value="list")
	public String list(HttpServletRequest  request){
		
		return ViewConstants.MatchFundsViewJsp.ALL_TRADE_LIST_VIEW;
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
			HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		  //查看权限
		if (permissionList != null) {
	            this.permissionList.assertHasViewPermission();
	    }
		
		ConnditionVo connVo = new ConnditionVo(request);
		if(connVo.getValueStr("first")==null){
			return null;
		}
		
		  if (connVo.isExcel()) {
			  easyUiPage.setPage(1);
			  easyUiPage.setRows(TypeConvert.EXCEL_PAGE_SIZE);
		  }
		  
		  
		//获取模糊搜索参数
		Map<String, Object> searchParams = EasyuiUtil.getParametersStartingWith(request,EasyuiUtil.SEARCH_PREFIX);
		//排序设置
		if (StringUtil.isBlank(easyUiPage.getOrder()) 
				&& StringUtil.isBlank(easyUiPage.getSort())){
			easyUiPage.setSort("addtime");
			easyUiPage.setOrder(EasyuiUtil.DESC);
		}
		
		PageInfo<Object> pageInfo = userTradeService.queryAllTrades(easyUiPage, searchParams);
		
		if (connVo.isNotExcel(pageInfo.getPageResults(), response,"方案列表.xls")) {
			return new EasyUiPageData(pageInfo);
		}
		return new EasyUiPageData(pageInfo);
	}
	
	
	@RequestMapping(value="tradeDetail")
	public String tradeDetail(HttpServletRequest  request,@RequestParam("groupId") String  groupId){
		request.setAttribute("groupId", groupId);
		return ViewConstants.MatchFundsViewJsp.TRADE_DETAIL_VIEW;
	}
	
	
	/**
	 *  含参数的搜索  search_EQ_name
	 * @param easyUiPage
	 * @param model
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/queryChildTrades", method = RequestMethod.POST)
	@ResponseBody
	public Object queryChildTrades(EasyUiPageInfo easyUiPage, Model model,
			ServletRequest request) throws Exception{
		
		  //查看权限
		if (permissionList != null) {
	            this.permissionList.assertHasViewPermission();
	    }
		
		//获取模糊搜索参数
		Map<String, Object> searchParams = EasyuiUtil.getParametersStartingWith(request,EasyuiUtil.SEARCH_PREFIX);
		//排序设置
		EasyuiUtil.getMultilistSortMap(easyUiPage);
		
		PageInfo<Object> pageInfo = userTradeService.queryChildTrades(easyUiPage, searchParams);
		return new EasyUiPageData(pageInfo);
	}
	
	@RequestMapping(value="getUserFund")
	public String getUserFund(HttpServletRequest  request,@RequestParam("groupId") String  groupId,
			@RequestParam("fromType") int  fromType){
		request.setAttribute("groupId", groupId);
		request.setAttribute("fromType", fromType);
		return ViewConstants.MatchFundsViewJsp.USERFUND_DETAIL_VIEW;
	}
	
	
	
	
	/**
	 *  含参数的搜索  search_EQ_name
	 * @param easyUiPage
	 * @param model
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/queryUserFunds", method = RequestMethod.POST)
	@ResponseBody
	public Object queryUserFunds(EasyUiPageInfo easyUiPage, Model model,
			@RequestParam("groupId") String  groupId,
			@RequestParam("type") int  type,
			ServletRequest request) throws Exception{
		
		  //查看权限
		if (permissionList != null) {
	            this.permissionList.assertHasViewPermission();
	    }
		
		List<UserFund> funds = userFundService.findByLidAndTypeOrderByAddtimeDesc(groupId, type);
		// 设置footer
		JSONArray array = new JSONArray();
		JSONObject jsonObject  = new JSONObject();
		jsonObject.put("money","累计："+funds.size()+"笔");
		jsonObject.put("payStatusValue","总计："+Math.abs(userFundService.sumMoneyByLidAndType(groupId, type))+"元");
		array.add(jsonObject);
		
		PageInfo<UserFund> pageInfo = new PageInfo<UserFund>();
		pageInfo.setPageResults(funds);
		pageInfo.setTotalCount(funds.size());
		return new EasyUiPageData(pageInfo,array);
	}
	
	
	/**
	 * 查看代理体系
	 * @param request
	 * @param uid
	 * @return
	 */
	@RequestMapping(value="/queryThing")
	public String queryThing(HttpServletRequest  request,@RequestParam("uid") String  uid){
		request.setAttribute("uid", uid);
		return ViewConstants.MatchFundsViewJsp.QUERY_THING_VIEW;
	}
	
	
	@RequestMapping(value = "/queryAgentInfo", method = RequestMethod.POST)
	@ResponseBody
	public Object queryAgentInfo( Model model,@RequestParam("uid") String  uid,ServletRequest request) throws Exception{
		
		
		List<WuserParentVo> list = wUserService.queryUserParents(uid);
		if (CollectionUtils.isEmpty(list) || list.size()==1){
			list.clear();
			list.add(new WuserParentVo("<span style='color:red;'>无代理上级</span>"));
			
		}
		return  new EasyUiPageData<WuserParentVo>(list.size(), list);
	}
}

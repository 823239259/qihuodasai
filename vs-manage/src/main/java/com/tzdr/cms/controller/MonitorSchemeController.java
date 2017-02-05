package com.tzdr.cms.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jodd.util.ObjectUtil;
import jodd.util.StringUtil;

import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.hundsun.t2sdk.interfaces.T2SDKException;
import com.tzdr.business.cms.service.auth.AuthService;
import com.tzdr.business.service.userTrade.HandTradeService;
import com.tzdr.business.service.userTrade.UserTradeCoverService;
import com.tzdr.business.service.userTrade.UserTradeService;
import com.tzdr.business.service.wuser.WUserService;
import com.tzdr.cms.constants.Constants;
import com.tzdr.cms.constants.ViewConstants;
import com.tzdr.cms.support.BaseCmsController;
import com.tzdr.cms.utils.DataGridVo;
import com.tzdr.cms.utils.WebUtil;
import com.tzdr.common.baseservice.BaseService;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.utils.ConnditionVo;
import com.tzdr.common.utils.Dates;
import com.tzdr.common.utils.EasyuiUtil;
import com.tzdr.common.utils.MessageUtils;
import com.tzdr.common.utils.TypeConvert;
import com.tzdr.common.web.support.EasyUiPageData;
import com.tzdr.common.web.support.EasyUiPageInfo;
import com.tzdr.common.web.support.JsonResult;
import com.tzdr.domain.vo.MonitorSchemeVo;
import com.tzdr.domain.web.entity.UserTrade;
import com.tzdr.domain.web.entity.UserTradeCover;
import com.tzdr.domain.web.entity.WUser;

/**
 * @author zhouchen
 * @version 创建时间：2015年1月14日 上午11:01:04
 * 方案监控
 */
@Controller
@RequestMapping("/admin/monitor")
public class MonitorSchemeController extends BaseCmsController<UserTrade> {
	private static Logger log = LoggerFactory.getLogger(MonitorSchemeController.class);

	@Autowired
	private UserTradeService userTradeService;

	
	@Autowired
	private HandTradeService handTradeService;
	
	@Autowired
	private UserTradeCoverService userTradeCoverService; 
	
	@Autowired
	private AuthService authService; 
	
	@Autowired
	private WUserService wUserService;
	
	@Override
	public BaseService<UserTrade> getBaseService() {
		return userTradeService;
	}
	
	@Override
	public void setResourceIdentity(String resourceIdentity) {
		super.setResourceIdentity("sys:riskmanager:monitor");
	}
	/**
	 * 方案监控列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value="list")
	public String list(HttpServletRequest request){
		return ViewConstants.MatchFundsViewJsp.MONITOR_SCHEME_LIST_VIEW;
	}
	
	/**
	 * 获取补仓提醒数据
	 * @param request
	 * @throws T2SDKException 
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value="data", method = RequestMethod.POST)
	@ResponseBody
	public Object getData(EasyUiPageInfo easyUiPage, HttpServletRequest request, HttpServletResponse response){
		

		//查看权限
		if (permissionList != null) {
	            this.permissionList.assertHasViewPermission();
	    }
		
		ConnditionVo connVo = new ConnditionVo(request);
		  if (connVo.isExcel()) {
			  easyUiPage.setPage(1);
			  easyUiPage.setRows(TypeConvert.EXCEL_PAGE_SIZE);
		  }
		
		//获取模糊搜索参数
		Map<String, Object> searchParams = EasyuiUtil.getParametersStartingWith(request,EasyuiUtil.SEARCH_PREFIX);
		//排序设置
		EasyuiUtil.getMultilistSortMap(easyUiPage);
		
		PageInfo<Object>  pageInfo  = userTradeService.queryMonitorList(easyUiPage, searchParams, connVo);
		
		if (connVo.isNotExcel(pageInfo.getPageResults(), response,"方案监控列表.xls")) {
			return new EasyUiPageData(pageInfo);
		}
		return new EasyUiPageData(pageInfo);
	}
	
	@RequestMapping(value = "/listData")
	public void listData(HttpServletRequest request,HttpServletResponse resp,short feeType,int handType) throws Exception {
		try {
			ConnditionVo connVo = new ConnditionVo(request);
			DataGridVo<MonitorSchemeVo> grid = new DataGridVo<MonitorSchemeVo>();
			PageInfo<MonitorSchemeVo> dataPage = new PageInfo<MonitorSchemeVo>(request);
			PageInfo<MonitorSchemeVo> datas = this.userTradeService.queryMonitorData(dataPage,connVo,feeType,handType);
			if (connVo.isNotExcel(datas.getPageResults(), resp, "方案监控.xls") ) {
				grid.add(datas.getPageResults());
				grid.setTotal(datas.getTotalCount());
				WebUtil.printText(JSON.toJSONString(grid), resp);
			}
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 更新操作账户状态
	 * @param request
	 * @param changeOperationStatus
	 * @param groupIds
	 * @return
	 */
	@RequestMapping(value="changeOperationStatus")
	@ResponseBody
	public JsonResult changeOperationStatus(HttpServletRequest request,
			@RequestParam("operationStatus") Integer operationStatus,
			@RequestParam("groupIds") String  groupIds){
		String [] groupIdArray = groupIds.split(Constants.SEPERATOR_COMMA);
		if (ArrayUtils.isEmpty(groupIdArray)){
			return new JsonResult(Boolean.FALSE,MessageUtils.message("user.trade.monitor.change.operation.status.no.data"));
		}
		
		if (ObjectUtil.equals(null, operationStatus)){
			return new JsonResult(Boolean.FALSE,"操作状态不能为空。");
		}
		
		//平仓
		if (operationStatus==3){
			userTradeService.openStock(groupIdArray,null);
			return new JsonResult(MessageUtils.message("update.success"));
		}
		
		// 终结方案
		if (operationStatus==4){
			for (String groupId : groupIdArray){
				userTradeService.endOfProgram(groupId);
			}
			return new JsonResult("操作成功！");
		}
		userTradeService.changeOperationStatus(groupIdArray, operationStatus,null);
		return new JsonResult(MessageUtils.message("update.success"));
	}
	
	
	@RequestMapping(value = "/saveHandTrade")
	@ResponseBody
	public JsonResult saveHandTrade(HttpServletRequest request,
			String groupId,String uid) throws Exception {
		JsonResult result=new JsonResult();
		if(StringUtil.isNotBlank(groupId)){
			handTradeService.updateTradeAuditEndStatus(groupId,uid);
			result.setSuccess(true);
			result.setMessage(MessageUtils.message("update.success"));
		}
		return result;
	}

	/**
	 * 补仓
	 * @param request
	 * @param groupId  配资组合号
	 * @param uid   配资用户编号
	 * @param type  配资类型(0:钱江版，1：涌金版)
	 * @param index 补仓类型(1:补到补仓线，2:补到平仓线)
	 * @param coverMoney  补仓金额
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveTradeCover")
	@ResponseBody
	public JsonResult saveTradeCover(HttpServletRequest request,
			String groupId,String uid,Integer type,Integer index,Double coverMoney) throws Exception {
		JsonResult result=new JsonResult(true);
		if(StringUtil.isBlank(groupId) || StringUtil.isBlank(uid) || type == null || index == null || coverMoney == null || coverMoney <= 0){
			result.setSuccess(false);
			result.setMessage(MessageUtils.message("no.cover.data"));
			return result;
		}
		//进行补仓操作
		if(type == 0){  //钱江版补仓操作
			userTradeCoverService.saveCoverUserFund(groupId, uid, coverMoney);
			result.setMessage(MessageUtils.message("submit.cover.succee"));
			return result;
		}else{  //涌金版补仓操作
			WUser wuser = wUserService.get(uid);
			UserTradeCover userTradeCover = new UserTradeCover();
			userTradeCover.setCoverMoney(coverMoney);
			userTradeCover.setCtime(Dates.getCurrentLongDate());
			userTradeCover.setCuid(new String(authService.getCurrentUser().getId().toString()));
			userTradeCover.setGroupId(groupId);
			userTradeCover.setWuser(wuser);
			userTradeCoverService.saveUserTradeCover(userTradeCover);
			result.setMessage(MessageUtils.message("submit.cover.apply"));
			return result;
		}
	}
}

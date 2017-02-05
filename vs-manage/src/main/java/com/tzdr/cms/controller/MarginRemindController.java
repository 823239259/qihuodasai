package com.tzdr.cms.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hundsun.t2sdk.interfaces.T2SDKException;
import com.tzdr.business.service.userTrade.UserTradeService;
import com.tzdr.cms.constants.Constants;
import com.tzdr.cms.constants.ViewConstants;
import com.tzdr.cms.support.BaseCmsController;
import com.tzdr.common.baseservice.BaseService;
import com.tzdr.common.utils.EasyuiUtil;
import com.tzdr.common.utils.MessageUtils;
import com.tzdr.common.web.support.EasyUiPageData;
import com.tzdr.common.web.support.EasyUiPageInfo;
import com.tzdr.common.web.support.JsonResult;
import com.tzdr.domain.vo.UserTradeCmsVo;
import com.tzdr.domain.web.entity.UserTrade;

@Controller
@RequestMapping("/admin/marginRemind")
public class MarginRemindController extends BaseCmsController<UserTrade> {
	private static Logger log = LoggerFactory
			.getLogger(MarginRemindController.class);

	@Autowired
	private UserTradeService userTradeService;

	@Override
	public BaseService<UserTrade> getBaseService() {
		return userTradeService;
	}
	public MarginRemindController() {
		setResourceIdentity("sys:customerService:marginremind");
	}
	
	/**
	 * 补仓提醒列表
	 * @param request
	 * @throws T2SDKException 
	 */
	@RequestMapping(value="list")
	public String marginRemindList(HttpServletRequest request){
		return ViewConstants.MatchFundsViewJsp.MARGINREMIND_LIST_VIEW;
	}

	/**
	 * 获取补仓提醒数据
	 * @param request
	 * @throws T2SDKException 
	 */
	@RequestMapping(value="data")
	@ResponseBody
	public Object getData(HttpServletRequest request,EasyUiPageInfo easyUiPage){
		
		//查看权限
		if (permissionList != null) {
	            this.permissionList.assertHasViewPermission();
	    }
		//获取模糊搜索参数
		Map<String, Object> searchParams = EasyuiUtil.getParametersStartingWith(request,EasyuiUtil.SEARCH_PREFIX);
		//排序设置
		EasyuiUtil.getMultilistSortMap(easyUiPage);
							
		List<UserTradeCmsVo>  userTradeVos  = userTradeService.queryMarginRemindData(easyUiPage,searchParams);
		EasyUiPageData<UserTradeCmsVo>  easyUiPageData  = new EasyUiPageData<UserTradeCmsVo>(userTradeVos.size(), userTradeVos);
		return easyUiPageData;
	}
	
	/**
	 * 
	 * @param request
	 * @param noticeStatus 改变状态值
	 * @param accountNo 账户编号
	 * @return
	 */
	@RequestMapping(value="changeNoticeStatus")
	@ResponseBody
	public JsonResult changeNoticeStatus(HttpServletRequest request,@RequestParam("noticeStatus") Short noticeStatus,
			@RequestParam("groupIds") String  groupIds){
		String [] groupIdArray = groupIds.split(Constants.SEPERATOR_COMMA);
		if (ArrayUtils.isEmpty(groupIdArray)){
			return new JsonResult(Boolean.FALSE,MessageUtils.message("user.trade.query.margin.remind.change.notice.status"));
		}
		
		userTradeService.changeNoticeStatus(groupIdArray, noticeStatus);
		return new JsonResult(MessageUtils.message("update.success"));
	}
	
	
	/**
	 * 
	 * @param request
	 * @param noticeStatus 改变状态值
	 * @param accountNo 账户编号
	 * @return
	 */
	@RequestMapping(value="sendSms")
	@ResponseBody
	public JsonResult sendSms(HttpServletRequest request,@RequestParam("groupIds") String  groupIds,
			@RequestParam("type") int type){
		String [] groupIdArray = groupIds.split(Constants.SEPERATOR_COMMA);
		if (ArrayUtils.isEmpty(groupIdArray)){
			return new JsonResult(Boolean.FALSE,"请选择对应的数据！");
		}
		
		userTradeService.smsNoticeMarginUser(groupIdArray,NumberUtils.toShort("1"),type);
		
		return new JsonResult(MessageUtils.message("update.success"));
	}
}

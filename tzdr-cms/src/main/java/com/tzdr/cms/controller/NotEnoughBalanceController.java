package com.tzdr.cms.controller;

import java.util.List;
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
import com.tzdr.business.service.wuser.WUserService;
import com.tzdr.cms.constants.Constants;
import com.tzdr.cms.constants.ViewConstants;
import com.tzdr.cms.support.BaseCmsController;
import com.tzdr.common.baseservice.BaseService;
import com.tzdr.common.utils.EasyuiUtil;
import com.tzdr.common.utils.MessageUtils;
import com.tzdr.common.web.support.EasyUiPageData;
import com.tzdr.common.web.support.EasyUiPageInfo;
import com.tzdr.common.web.support.JsonResult;
import com.tzdr.domain.vo.NotEnoughBalanceVo;
import com.tzdr.domain.web.entity.WUser;

@Controller
@RequestMapping("/admin/notEnoughBalance")
public class NotEnoughBalanceController extends BaseCmsController<WUser> {
	private static Logger log = LoggerFactory
			.getLogger(NotEnoughBalanceController.class);

	@Autowired
	private WUserService wUserService;
	
	@Autowired
	private UserTradeService userTradeService;

	@Override
	public BaseService<WUser> getBaseService() {
		return wUserService;
	}
	public NotEnoughBalanceController() {
		setResourceIdentity("sys:customerService:notEnoughBalance");
	}
	
	/**
	 * 余额 不足提醒列表
	 * @param request
	 */
	@RequestMapping(value="list")
	public String marginRemindList(HttpServletRequest request){
		return ViewConstants.MatchFundsViewJsp.NOTENOGH_BALANCE_LIST_VIEW;
	}

	/**
	 * 余额 不足提醒列表
	 * @param request
	 */
	@RequestMapping(value="data")
	@ResponseBody
	public Object getData(HttpServletRequest request,EasyUiPageInfo easyUiPage) {
		
		//查看权限
		if (permissionList != null) {
	            this.permissionList.assertHasViewPermission();
	    }
		//获取模糊搜索参数
		Map<String, Object> searchParams = EasyuiUtil.getParametersStartingWith(request,EasyuiUtil.SEARCH_PREFIX);
		//排序设置
		EasyuiUtil.getMultilistSortMap(easyUiPage);
			
				
		List<NotEnoughBalanceVo>  notEnoughBalanceVos  = userTradeService.getNotEnoughBalanceList(easyUiPage, searchParams);
		EasyUiPageData<NotEnoughBalanceVo>  easyUiPageData  = new EasyUiPageData<NotEnoughBalanceVo>(notEnoughBalanceVos.size(), notEnoughBalanceVos);
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
	public JsonResult changeNoticeStatus(HttpServletRequest request,@RequestParam("noticeStatus") Integer noticeStatus,
			@RequestParam("uids") String  uids){
		String [] uidsArray = uids.split(Constants.SEPERATOR_COMMA);
		if (ArrayUtils.isEmpty(uidsArray)){
			log.debug(MessageUtils.message("user.trade.not.enough.balance.change.notice.status"));
			return new JsonResult(Boolean.FALSE,MessageUtils.message("user.trade.not.enough.balance.change.notice.status"));
		}
		
		wUserService.changeNoticeStatus(uidsArray, noticeStatus);
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
	public JsonResult sendSms(HttpServletRequest request,@RequestParam("uids") String  uids){
		String [] uidsArray = uids.split(Constants.SEPERATOR_COMMA);
		if (ArrayUtils.isEmpty(uidsArray)){
			log.debug(MessageUtils.message("user.trade.not.enough.balance.change.notice.status"));
			return new JsonResult(Boolean.FALSE,MessageUtils.message("user.trade.not.enough.balance.change.notice.status"));
		}
		
		wUserService.smsNoticeUser(uidsArray,1);
		return new JsonResult(MessageUtils.message("update.success"));
	}
	
}

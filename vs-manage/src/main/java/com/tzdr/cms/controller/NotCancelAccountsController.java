package com.tzdr.cms.controller;

import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

import com.tzdr.business.cms.service.auth.AuthService;
import com.tzdr.business.service.account.AccountService;
import com.tzdr.business.service.userTrade.UserTradeService;
import com.tzdr.cms.constants.Constants;
import com.tzdr.cms.constants.ViewConstants;
import com.tzdr.cms.support.BaseCmsController;
import com.tzdr.common.baseservice.BaseService;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.utils.ConnditionVo;
import com.tzdr.common.utils.EasyuiUtil;
import com.tzdr.common.utils.MessageUtils;
import com.tzdr.common.utils.TypeConvert;
import com.tzdr.common.web.support.EasyUiPageData;
import com.tzdr.common.web.support.EasyUiPageInfo;
import com.tzdr.common.web.support.JsonResult;
import com.tzdr.domain.web.entity.Account;

/**
 * @author zhouchen
 * @version 创建时间：2014年12月26日 上午11:45:59
 * 未注销的子账户管理controller
 */
@Controller
@RequestMapping("/admin/notCancelAccounts")
public class NotCancelAccountsController extends BaseCmsController<Account>{

	private static Logger log = LoggerFactory.getLogger(NotCancelAccountsController.class);

	@Autowired
	private AccountService  accountService;
	
	@Autowired
	private UserTradeService  userTradeService;
	
	@Autowired
	private AuthService authService;
	
	@Override
	public BaseService<Account> getBaseService() {
		return accountService;
	}

	public NotCancelAccountsController() {
		setResourceIdentity("sys:riskmanager:notCancelAccounts");
	}
	
	@RequestMapping(value="list")
	public String list(HttpServletRequest  request){
		
		return ViewConstants.HundsunAccountViewJsp.NOT_CANCEL_LIST_VIEW;
	}
	
	/**
	 *  含参数的搜索  search_EQ_name
	 * @param easyUiPage
	 * @param model
	 * @param request
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/getDatas", method = RequestMethod.POST)
	@ResponseBody
	public Object easyuiPage(EasyUiPageInfo easyUiPage, HttpServletRequest request, HttpServletResponse response) throws Exception{
		
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
		
		PageInfo<Object> pageInfo = userTradeService.queryNoCancelledList(easyUiPage, searchParams);
		
		if (connVo.isNotExcel(pageInfo.getPageResults(), response,"终结未注销账户列表.xls")) {
			return new EasyUiPageData(pageInfo);
		}
		return new EasyUiPageData(pageInfo);
	}
	
	/**
	 * 注销账号
	 * @param request
	 * @param ids
	 * @return
	 */
	@RequestMapping(value="cancelAccount")
	@ResponseBody
	public JsonResult cancelAccount(HttpServletRequest request,
			@RequestParam("ids") String  ids){
		String [] idsArray = ids.split(Constants.SEPERATOR_COMMA);
		if (ArrayUtils.isEmpty(idsArray)){
			log.debug(MessageUtils.message("acount.cancel.not.select.data"));
			return new JsonResult(Boolean.FALSE,MessageUtils.message("acount.cancel.not.select.data"));
		}
		
		accountService.cancelAccount(idsArray);
		return new JsonResult(MessageUtils.message("acount.cancel.success"));
	}
}

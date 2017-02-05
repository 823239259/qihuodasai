package com.tzdr.cms.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tzdr.business.cms.service.auth.AuthService;
import com.tzdr.business.service.bespokeTrade.BespokeTradeService;
import com.tzdr.cms.constants.ViewConstants;
import com.tzdr.cms.support.BaseCmsController;
import com.tzdr.cms.utils.WebUtil;
import com.tzdr.common.baseservice.BaseService;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.exception.BusinessException;
import com.tzdr.common.utils.EasyuiUtil;
import com.tzdr.common.web.support.EasyUiPageData;
import com.tzdr.common.web.support.EasyUiPageInfo;
import com.tzdr.domain.web.entity.BespokeTrade;

/**
 * <B>说明: </B>定制化配资
 * @author LiuYang
 *
 * 2015年6月15日 下午7:54:26
 */
@Controller
@RequestMapping("/admin/bespokeTrade")
public class BespokeTradeController extends BaseCmsController<BespokeTrade>{

	
	@Autowired
	private BespokeTradeService bespokeTradeService;
	
	@Autowired
	private AuthService authService;
	
	@Override
	public BaseService<BespokeTrade> getBaseService() {
		return bespokeTradeService;
	}
	
	public BespokeTradeController() {
		setResourceIdentity("sys:settingParams:bespokeTrade");
	}
	
	
	/**
	 * 参数列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value="list")
	public String list(HttpServletRequest  request){
		
		return ViewConstants.BespokeTradeJsp.LIST_VIEW;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/easyuiPage", method = RequestMethod.POST)
	@ResponseBody
	@Override
	public Object easyuiPage(EasyUiPageInfo easyUiPage, Model model,
			ServletRequest request) throws Exception{
		
		  //查看权限
		  if (permissionList != null) {
	            this.permissionList.assertHasViewPermission();
	        }
		
		//获取模糊搜索参数
		Map<String, Object> searchParams = EasyuiUtil.getParametersStartingWith(request,EasyuiUtil.SEARCH_PREFIX);
		//排序设置
		Map<String, Boolean> sortMap = new LinkedHashMap<String, Boolean>();
		sortMap.put("state", false);
		sortMap.put("startTime", true);
		//查询数据
		PageInfo<BespokeTrade> pageInfo = new PageInfo<BespokeTrade>(easyUiPage.getRows(), easyUiPage.getPage());
		pageInfo = getBaseService().query(pageInfo,searchParams,sortMap);
		
		return new EasyUiPageData(pageInfo);
	}
	/**
	 * 停用方案
	 * @param id
	 * @param resp
	 */
	@RequestMapping(value="stop")
	public void stop(String id,HttpServletResponse resp){
		try {
			BespokeTrade bespokeTrade = this.bespokeTradeService.get(id);
			bespokeTrade.setState(BespokeTrade.STATE_VALUE_STOP);
			this.bespokeTradeService.update(bespokeTrade);
			WebUtil.printText("success",resp);
		} 
		catch (BusinessException e) {
			e.printStackTrace();
			WebUtil.printText("服务器忙!",resp);
		}
	}
	
}

package com.tzdr.cms.controller;

import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import jodd.util.ObjectUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tzdr.business.service.datamap.DataMapService;
import com.tzdr.cms.constants.Constants;
import com.tzdr.cms.constants.ViewConstants;
import com.tzdr.cms.support.BaseCmsController;
import com.tzdr.common.baseservice.BaseService;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.utils.EasyuiUtil;
import com.tzdr.common.web.support.EasyUiPageData;
import com.tzdr.common.web.support.EasyUiPageInfo;
import com.tzdr.common.web.support.JsonResult;
import com.tzdr.domain.cache.DataDicKeyConstants;
import com.tzdr.domain.entity.DataMap;
import com.tzdr.domain.web.entity.ParentAccount;

/**
 * @author zhouchen
 * @version 创建时间：2015年1月3日 上午10:10:05
 * 提现审核金额维护
 */
@Controller
@RequestMapping("/admin/withdrawMoney")
public class WithdrawMoneyController extends BaseCmsController<DataMap> {
	private static Logger log = LoggerFactory.getLogger(WithdrawMoneyController.class);

	@Autowired
	private DataMapService  dataMapService;
	
	@Override
	public BaseService<DataMap> getBaseService() {
		return dataMapService;
	}
	
	@Override
	public void setResourceIdentity(String resourceIdentity) {
		super.setResourceIdentity("sys:finance:withdrawMoney");
	}
	@RequestMapping(value = "/list")
	public  String  drawlist(HttpServletRequest request){
		
		return ViewConstants.WithdrawalViewJsp.MONEY_VIEW;
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
			Map<String, Boolean> sortMap = EasyuiUtil.getSortMap(easyUiPage);
			//查询数据
			PageInfo<DataMap> pageInfo = new PageInfo<DataMap>(easyUiPage.getRows(), easyUiPage.getPage());
			pageInfo = getBaseService().query(pageInfo,searchParams,sortMap);
			return new EasyUiPageData(pageInfo);
	}
	
	@RequestMapping(value = "/toEidt")
	public String toEidt(HttpServletRequest request,@RequestParam(value="id",required=false) String id) throws Exception 
	{
			Map<String,String> dataMap = dataMapService.getWithDrawAuditMoney();
			request.setAttribute("dataMap",dataMap);
			return ViewConstants.WithdrawalViewJsp.MONEY_EDIT_VIEW;	
	}
	
	
	
	@RequestMapping(value = "/setMoney", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult  setMoney(HttpServletRequest  request,@RequestParam("minMoney") String minMoney,
			@RequestParam("maxMoney") String maxMoney){
			String valueName = minMoney+DataDicKeyConstants.WITHDRAWAL_AUDIT_MONEY_SPLIT+maxMoney;
			DataMap  dataMap = dataMapService.getWithDrawMoney();
			if (ObjectUtil.equals(null, dataMap)){
				dataMap = new DataMap(DataDicKeyConstants.WITHDRAW_MONEY_VALUE_KEY,valueName,
						DataDicKeyConstants.WITHDRAW_MONEY_TYPE_KEY,DataDicKeyConstants.WITHDRAW_MONEY_TYPE_NAME);
				dataMapService.save(dataMap);
				return new JsonResult("设置成功！");
			}
			
			dataMap.setValueName(valueName);
			dataMapService.updateDataMap(dataMap);
			return new JsonResult("设置成功！");
			
	}
}

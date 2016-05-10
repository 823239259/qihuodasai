package com.tzdr.cms.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tzdr.business.service.withdrawal.WithdrawalService;
import com.tzdr.cms.constants.Constants;
import com.tzdr.cms.constants.ViewConstants;
import com.tzdr.cms.support.BaseCmsController;
import com.tzdr.cms.utils.DataGridVo;
import com.tzdr.cms.utils.WebUtil;
import com.tzdr.common.baseservice.BaseService;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.utils.ConnditionVo;
import com.tzdr.common.utils.EasyuiUtil;
import com.tzdr.common.utils.TypeConvert;
import com.tzdr.common.web.support.EasyUiPageData;
import com.tzdr.common.web.support.EasyUiPageInfo;
import com.tzdr.common.web.support.JsonResult;
import com.tzdr.domain.vo.HundSunFundVo;
import com.tzdr.domain.web.entity.DrawList;

/**
 * @author zhouchen
 * @version 创建时间：2015年1月3日 上午10:10:05
 * 提现记录 管理
 */
@Controller
@RequestMapping("/admin/withdrawal")
public class WithdrawalController extends BaseCmsController<DrawList> {
	private static Logger log = LoggerFactory.getLogger(WithdrawalController.class);

	@Autowired
	private WithdrawalService  withdrawalService;
	
	@Override
	public BaseService<DrawList> getBaseService() {
		return withdrawalService;
	}
	
	@Override
	public void setResourceIdentity(String resourceIdentity) {
		super.setResourceIdentity("sys:finance:withdrawal");
	}
	@RequestMapping(value = "/list")
	public  String  drawlist(HttpServletRequest request){
		
		return ViewConstants.WithdrawalViewJsp.LIST_VIEW;
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
		  if (connVo.isExcel()) {
			  easyUiPage.setPage(1);
			  easyUiPage.setRows(TypeConvert.EXCEL_PAGE_SIZE);
		  }
		  
		//获取模糊搜索参数
		Map<String, Object> searchParams = EasyuiUtil.getParametersStartingWith(request,EasyuiUtil.SEARCH_PREFIX);
		//排序设置
		EasyuiUtil.getMultilistSortMap(easyUiPage);
		//查询数据
		PageInfo<Object> pageInfo = withdrawalService.queryListNew(easyUiPage, searchParams);
		
		// 设置footer
		JSONArray array = new JSONArray();
		JSONObject jsonObject  = new JSONObject();
		
		jsonObject.put("card","总计：");
		jsonObject.put("money",withdrawalService.getTotalMoney(searchParams)+" 元");
		array.add(jsonObject);
				
		if (connVo.isNotExcel(pageInfo.getPageResults(), response,"提现记录表.xls")) {
			return new EasyUiPageData(pageInfo,array);
		}
		
		return new EasyUiPageData(pageInfo,array);
	}
	
	@RequestMapping(value = "/changeStatus", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult  changeStatus(HttpServletRequest  request,@RequestParam("status") Short status,
			@RequestParam("ids") String  ids){
		String [] idArray = ids.split(Constants.SEPERATOR_COMMA);
		if (ArrayUtils.isEmpty(idArray)){
			return new JsonResult(Boolean.FALSE,"修改状态时未选中数据。");
		}
		
		withdrawalService.changeStatus(idArray,status.equals(NumberUtils.toShort("31"))?"4":String.valueOf(status));
		return new JsonResult("提交成功！");
	}
}

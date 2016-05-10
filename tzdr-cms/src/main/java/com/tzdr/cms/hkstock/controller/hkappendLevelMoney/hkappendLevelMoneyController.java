package com.tzdr.cms.hkstock.controller.hkappendLevelMoney;

import java.util.Map;

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

import com.tzdr.business.hkstock.service.HkAppendLevelMoneyService;
import com.tzdr.cms.constants.Constants;
import com.tzdr.cms.controller.AppendLevelMoneyFailController;
import com.tzdr.cms.hkstock.constants.HkViewConstants;
import com.tzdr.cms.support.BaseCmsController;
import com.tzdr.common.baseservice.BaseService;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.utils.ConnditionVo;
import com.tzdr.common.utils.EasyuiUtil;
import com.tzdr.common.utils.TypeConvert;
import com.tzdr.common.web.support.EasyUiPageData;
import com.tzdr.common.web.support.EasyUiPageInfo;
import com.tzdr.common.web.support.JsonResult;
import com.tzdr.domain.hkstock.entity.HkAppendLevelMoney;
@Controller
@RequestMapping("/admin/hkappendLevelMoney")
public class hkappendLevelMoneyController extends BaseCmsController<HkAppendLevelMoney>{
	private static Logger log = LoggerFactory.getLogger(AppendLevelMoneyFailController.class);
	@Autowired
	private HkAppendLevelMoneyService hkAppendLevelMoneyService;
	
	@Override
	public BaseService<HkAppendLevelMoney> getBaseService() {
		return hkAppendLevelMoneyService;
	}
	
	@Override
	public void setResourceIdentity(String resourceIdentity) {
		super.setResourceIdentity("sys:riskmanager:hkappendLevelMoney");
	}
	
	

	@RequestMapping(value="/list")
	public String list(HttpServletRequest  request){
		
		return HkViewConstants.hkappendLevelMoney.LIST_VIEW;
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
			HttpServletRequest  request,HttpServletResponse response) throws Exception{
		ConnditionVo connVo = new ConnditionVo(request);
		if (connVo.isExcel()) {
			easyUiPage.setPage(1);
			easyUiPage.setRows(TypeConvert.EXCEL_PAGE_SIZE);
		}
		  //查看权限
		if (permissionList != null) {
	            this.permissionList.assertHasViewPermission();
	    }
		//获取模糊搜索参数
		Map<String, Object> searchParams = EasyuiUtil.getParametersStartingWith(request,EasyuiUtil.SEARCH_PREFIX);
		//排序设置
		
		easyUiPage.setSort("appendDate");
		easyUiPage.setOrder("desc");
		EasyuiUtil.getMultilistSortMap(easyUiPage);
		
		PageInfo<Object> pageInfo = hkAppendLevelMoneyService.queryList(easyUiPage, searchParams);
		if (connVo.isNotExcel(pageInfo.getPageResults(), response,"港股追加保证金.xls")) {
			return new EasyUiPageData(pageInfo);
		}
		return new EasyUiPageData(pageInfo);
	}

	
	
	/**
	 * 处理列表
	 * @param request
	 * @param ids
	 * @return
	 */
	@RequestMapping(value="/hkhandleappendMoney")
	@ResponseBody
	public JsonResult hkhandleappendMoney(HttpServletRequest request,
			@RequestParam("ids") String  ids){
		String [] idArray = ids.split(Constants.SEPERATOR_COMMA);
		if (ArrayUtils.isEmpty(idArray)){
			log.debug("处理追加保证金时，未选择数据！");
			return new JsonResult(Boolean.FALSE,"处理追加保证金时，未选择数据！");
		}
		String message=hkAppendLevelMoneyService.handleappendMoney(idArray);
		if(message!=""){
			return new JsonResult(false,message);
		}
		return new JsonResult("操作成功");
	}

}

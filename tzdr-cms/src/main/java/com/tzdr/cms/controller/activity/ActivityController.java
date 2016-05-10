package com.tzdr.cms.controller.activity;

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

import com.tzdr.business.service.activity.ActivityKudoService;
import com.tzdr.cms.constants.Constants;
import com.tzdr.cms.constants.ViewConstants;
import com.tzdr.cms.controller.AllTradesController;
import com.tzdr.cms.support.BaseCmsController;
import com.tzdr.common.baseservice.BaseService;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.utils.ConnditionVo;
import com.tzdr.common.utils.EasyuiUtil;
import com.tzdr.common.utils.TypeConvert;
import com.tzdr.common.web.support.EasyUiPageData;
import com.tzdr.common.web.support.EasyUiPageInfo;
import com.tzdr.common.web.support.JsonResult;
import com.tzdr.domain.entity.DataMap;
import com.tzdr.domain.web.entity.activity.ActivityKudo;
/**
 * 
 * 类说明     开箱豪礼
 * @author  zhaozhao
 * @date    2016年1月11日上午11:48:55
 * @version 1.0
 */
@Controller
@RequestMapping("/admin/Activity")
public class ActivityController extends BaseCmsController<ActivityKudo>{

	private static Logger log = LoggerFactory.getLogger(ActivityController.class);
	@Autowired
	ActivityKudoService activityKudoService;
	
	@Override
	public BaseService<ActivityKudo> getBaseService() {
		return activityKudoService;
	}

	@Override
	public void setResourceIdentity(String resourceIdentity) {
		super.setResourceIdentity("sys:operationalConfig:Activity");
	}
	@RequestMapping("/list")
	public String getlist(HttpServletRequest  request){
		return ViewConstants.Activity.LIST_VIEW;
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
		  //查看权限
		if (permissionList != null) {
	            this.permissionList.assertHasViewPermission();
	    }
		//获取模糊搜索参数
		Map<String, Object> searchParams = EasyuiUtil.getParametersStartingWith(request,EasyuiUtil.SEARCH_PREFIX);
		
		PageInfo<Object> pageInfo = activityKudoService.queryList(easyUiPage, searchParams);
		return new EasyUiPageData(pageInfo);
	}
	
	/**
	 * 使用列表
	 * @param request
	 * @param ids
	 * @return
	 */
	@RequestMapping(value="/usekudo")
	@ResponseBody
	public JsonResult usekudo(HttpServletRequest request,
			@RequestParam("ids") String  ids){
		String [] idArray = ids.split(Constants.SEPERATOR_COMMA);
		if (ArrayUtils.isEmpty(idArray)){
			log.debug("处理已使用奖品时，未选择数据！");
			return new JsonResult(Boolean.FALSE,"处理已使用奖品时，未选择数据！");
		}
		activityKudoService.usekudos(idArray);
		
		return new JsonResult("操作成功");
	}
	
	
	/**
	 * 使用列表
	 * @param request
	 * @param ids
	 * @return
	 */
	@RequestMapping(value="/changekudo")
	@ResponseBody
	public JsonResult changekudo(HttpServletRequest request,
			@RequestParam("ids") String  ids){
		String [] idArray = ids.split(Constants.SEPERATOR_COMMA);
		if (ArrayUtils.isEmpty(idArray)){
			log.debug("将奖品状态变更为未使用时，未选择数据！");
			return new JsonResult(Boolean.FALSE,"将奖品状态变更为未使用时，未选择数据！");
		}
		activityKudoService.chagnekudos(idArray);
		
		return new JsonResult("操作成功");
	}
	
}

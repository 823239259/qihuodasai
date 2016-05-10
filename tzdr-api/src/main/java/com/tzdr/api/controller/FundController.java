package com.tzdr.api.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jodd.util.ObjectUtil;
import jodd.util.StringUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tzdr.api.constants.DataConstant;
import com.tzdr.api.constants.ResultStatusConstant;
import com.tzdr.api.request.RequestObj;
import com.tzdr.api.support.ApiResult;
import com.tzdr.business.api.service.ApiFundService;
import com.tzdr.business.api.service.ApiUserService;
import com.tzdr.domain.api.vo.ApiFundVo;
import com.tzdr.domain.api.vo.ApiUserVo;

/**
 * 
 * <B>说明: </B>用户资金明细相关操作
 * @zhouchen
 * 2016年1月20日
 */
@Controller
@RequestMapping(value = "/fund")
public class FundController {
	
	@Autowired
	private ApiFundService  apiFundService;
	
	
	@Autowired
	private ApiUserService  apiUserService;
	
	/**
	 * 根据用户id和对应的方案组合号获取该方案产生的管理费用
	 * @param groupId
	 * @param uid
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/manage_fee",method=RequestMethod.POST)
	@ResponseBody
	public ApiResult manageFee(@RequestBody RequestObj requestObj,HttpServletRequest request,HttpServletResponse response) {
		
		String groupId=requestObj.getGroupId();
		String uid=requestObj.getUid();
		
		if (StringUtil.isBlank(uid) || StringUtil.isBlank(groupId)){
			return new ApiResult(false, ResultStatusConstant.FAIL,"query.fail;params.is.null.");
		}
		ApiUserVo apiUserVo = apiUserService.findByUid(uid);
		if (ObjectUtil.equals(null, apiUserVo)){
			return new ApiResult(false, ResultStatusConstant.ManageFee.USER_INFO_NOT_EXIST,"user.info.not.exist.");
		}
		List<ApiFundVo> apiFundVos = apiFundService.queryManegeFee(groupId, uid);
		return new ApiResult(true, ResultStatusConstant.SUCCESS,"query.success.",apiFundVos);
	}
	
	
	/**
	 * 获取用户的资金明细
	 * @param type
	 * 0：初始进入，显示当月明细
	 * 1：点击一个月前明细，获取所有明细
	 * @param uid
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/detail",method=RequestMethod.POST)
	@ResponseBody
	public ApiResult detail(@RequestBody RequestObj requestObj,HttpServletRequest request,HttpServletResponse response) {
		
		int type=requestObj.getType();
		String uid=requestObj.getUid();
		
		if (StringUtil.isBlank(uid) || type>DataConstant.FUND_DETAIL_ALL_TYPE 
				|| type<DataConstant.FUND_DETAIL_MONTH_TYPE){
			return new ApiResult(false, ResultStatusConstant.FAIL,"query.fail;params.error.");
		}
		ApiUserVo apiUserVo = apiUserService.findByUid(uid);
		if (ObjectUtil.equals(null, apiUserVo)){
			return new ApiResult(false, ResultStatusConstant.FundDetail.USER_INFO_NOT_EXIST,"user.info.not.exist.");
		}
		List<ApiFundVo> apiFundVos = apiFundService.queryFundList(type, uid);
		return new ApiResult(true, ResultStatusConstant.SUCCESS,"query.success.",apiFundVos);
	}
	
}

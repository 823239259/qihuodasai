package com.tzdr.cms.controller;

import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import jodd.util.ObjectUtil;
import jodd.util.StringUtil;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tzdr.business.service.fundconfig.FundConfigService;
import com.tzdr.business.service.securityInfo.SecurityInfoService;
import com.tzdr.business.service.userTrade.HandTradeService;
import com.tzdr.business.service.wuser.WUserService;
import com.tzdr.cms.constants.Constants;
import com.tzdr.cms.constants.ViewConstants;
import com.tzdr.cms.support.BaseCmsController;
import com.tzdr.common.baseservice.BaseService;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.utils.BigDecimalUtils;
import com.tzdr.common.utils.EasyuiUtil;
import com.tzdr.common.web.support.EasyUiPageData;
import com.tzdr.common.web.support.EasyUiPageInfo;
import com.tzdr.common.web.support.JsonResult;
import com.tzdr.domain.vo.HandTradeVo;
import com.tzdr.domain.web.entity.HandTrade;
import com.tzdr.domain.web.entity.UserVerified;
import com.tzdr.domain.web.entity.WUser;

/**
 * 手工开户
 * @author zhouchen
 * 2015年4月27日 上午11:07:23
 */
@Controller
@RequestMapping("/admin/handTrade")
public class HandTradeController extends BaseCmsController<HandTrade> {
	private static Logger log = LoggerFactory.getLogger(HandTradeController.class);
	
	@Autowired
	private HandTradeService  handTradeService;
	
	@Autowired
	private  WUserService wUserService;
	
	@Autowired
	private  SecurityInfoService securityInfoService;
	
	
	@Autowired
	private FundConfigService fundConfigService;
	
	
	@Override
	public BaseService<HandTrade> getBaseService() {
		return handTradeService;
	}
	
	@Override
	public void setResourceIdentity(String resourceIdentity) {
		super.setResourceIdentity("sys:riskmanager:handTrade");
	}
	
	/**
	 * 进入手工配资页面
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/list")
	public  String  drawlist(HttpServletRequest request){
		return ViewConstants.MatchFundsViewJsp.HAND_TRADE_LIST_VIEW;
	}
	
	
	@RequestMapping(value = "/edit")
	public String edit(HttpServletRequest request,@RequestParam("fromType") String fromType,
			@RequestParam(value="id",required=false) String id) throws Exception 
	{
		request.setAttribute("fromType",fromType);
		if (StringUtil.equals(fromType,Constants.ADD)){
			return ViewConstants.MatchFundsViewJsp.HAND_TRADE_EDIT_VIEW;			
		}
		return ViewConstants.ERROR_VIEW;
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
			ServletRequest request,@RequestParam(value="type") int type) throws Exception{
		
		  //查看权限
		  if (permissionList != null) {
	            this.permissionList.assertHasViewPermission();
	        }
		
		//获取模糊搜索参数
		Map<String, Object> searchParams = EasyuiUtil.getParametersStartingWith(request,EasyuiUtil.SEARCH_PREFIX);
	
		//排序设置
		if (type==0){
			easyUiPage.setSort("createTime");
			easyUiPage.setOrder(EasyuiUtil.ASC);
		}
		else
		{
			easyUiPage.setSort("auditTime");
			easyUiPage.setOrder(EasyuiUtil.DESC);
		}
		//查询数据
		PageInfo<Object> pageInfo =handTradeService.queryDatas(easyUiPage, searchParams);
		
		return new EasyUiPageData(pageInfo);
	}
	
	@RequestMapping(value = "/transferFailData", method = RequestMethod.POST)
	@ResponseBody
	public Object queryTransferFailList(EasyUiPageInfo easyUiPage, Model model,
			ServletRequest request) throws Exception{
		
		  //查看权限
		  if (permissionList != null) {
	            this.permissionList.assertHasViewPermission();
	        }
		
		//获取模糊搜索参数
		Map<String, Object> searchParams = EasyuiUtil.getParametersStartingWith(request,EasyuiUtil.SEARCH_PREFIX);
		
		//排序设置
		//EasyuiUtil.getMultilistSortMap(easyUiPage);
		//查询数据
		PageInfo<Object> pageInfo =handTradeService.queryTransferFailList(easyUiPage, searchParams);
		return new EasyUiPageData(pageInfo);
	}
	

	/**
	 * 审核通过
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/audit", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult  audit (HttpServletRequest  request,@RequestParam("tradeId") String tradeId,
			@RequestParam("isPass") Boolean isPass) throws Exception{
		
			handTradeService.auditTrade(tradeId, isPass);
			return new JsonResult("操作成功！");
	}
	
	
	/**
	 * 手动划账后，标记方案为可用，并发送短信
	 * @param request
	 * @param tradeId
	 * @return
	 */
	@RequestMapping(value = "/afterHandTransfer", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult  afterHandTransfer(HttpServletRequest  request,@RequestParam("tradeId") String tradeId){
			handTradeService.afterHandTransfer(tradeId);
			return new JsonResult("操作成功！");
	}
	
	
	
	/**
	 * 保存方案信息
	 * @param request
	 * @param handTradeVo
	 * @return
	 */
	@RequestMapping(value = "/saveInfo", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult  saveInfo(HttpServletRequest  request,HandTradeVo handTradeVo){
		
			if (StringUtil.isBlank(handTradeVo.getMobile())){
				return new JsonResult(false,"你输入的手机号不能为空!");
			}
			if (ObjectUtil.equals(null,handTradeVo.getLever()) 
					|| ObjectUtil.equals(null,handTradeVo.getLeverMoney()) 
					|| ObjectUtil.equals(null,handTradeVo.getNaturalDays())){
				return new JsonResult(false,"您输入的配资杠杆或配资保证金或使用期限不能为空！");
			}
			
			if (!validateAccountInfo(handTradeVo)){
				return new JsonResult(false,"请将帐号信息【恒生账户名、交易账号、交易密码、单元序号,母账户名称】填写完整，可全部为空系统自动抓取！");
			}
			
			WUser wUser=wUserService.getWUserByMobile(handTradeVo.getMobile());
			if (ObjectUtil.equals(wUser, null)) {
				return new JsonResult(false,"根据您输入的手机号未能找到对应的用户，请核对后再输入！");
			}
			
			UserVerified userVerified = securityInfoService.findByUserId(wUser.getId());
			if(ObjectUtils.equals(userVerified, null) 
					|| ObjectUtils.equals(userVerified.getIdcard(), null)){
				return new JsonResult(false,"该客户未进行实名认证，请通知客户先实名认证！");
			}
			
			//校验倍数
			Integer  lever = handTradeVo.getLever();
			double leverMoney = handTradeVo.getLeverMoney();
			double  maxAmount=fundConfigService.findAmountByTimes(lever);
			double tradeMoney = BigDecimalUtils.mulRound(leverMoney,lever);
			if(tradeMoney > maxAmount){
				return new JsonResult(false,"保证金："+leverMoney+"元，配资杠杆："+lever+"倍，超过最大配资金额："+maxAmount+"元");
			}

			handTradeService.createHandTrade(handTradeVo);
			return new JsonResult("手工开户成功！");
	}
	
	
	/**
	 * 校验页面输入的恒生账户信息
	 * 恒生账户名、交易账号、交易密码、单元序号,母账户名称 可全为空，或全不为空
	 * @param handTradeVo
	 * @return
	 */
	private boolean validateAccountInfo(HandTradeVo handTradeVo){
		if (StringUtil.isAllBlank(handTradeVo.getAccount(),handTradeVo.getAccountName(),
				handTradeVo.getAssetId(),handTradeVo.getPassword(),handTradeVo.getParentAccountId()/*,
				handTradeVo.getInsuranceNo()*/)){
			return true;
		}
		if (StringUtil.isNotBlank(handTradeVo.getAccount()) 
				&& StringUtil.isNotBlank(handTradeVo.getAccountName())
				&& StringUtil.isNotBlank(handTradeVo.getAssetId())
				&& StringUtil.isNotBlank(handTradeVo.getPassword())
				&& StringUtil.isNotBlank(handTradeVo.getParentAccountId())
				/*&& StringUtil.isNotBlank(handTradeVo.getInsuranceNo())*/){
			return true;
		}
		return false;
	}
}

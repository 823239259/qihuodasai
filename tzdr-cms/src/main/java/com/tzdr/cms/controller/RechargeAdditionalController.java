package com.tzdr.cms.controller;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import jodd.util.ObjectUtil;
import jodd.util.StringUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tzdr.business.service.pay.PayService;
import com.tzdr.business.service.recharge.RechargeAdditionalService;
import com.tzdr.cms.constants.Constants;
import com.tzdr.cms.constants.ViewConstants;
import com.tzdr.cms.support.BaseCmsController;
import com.tzdr.common.baseservice.BaseService;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.utils.EasyuiUtil;
import com.tzdr.common.utils.IpUtils;
import com.tzdr.common.web.support.EasyUiPageData;
import com.tzdr.common.web.support.EasyUiPageInfo;
import com.tzdr.common.web.support.JsonResult;
import com.tzdr.domain.constants.Constant;
import com.tzdr.domain.vo.RechargeAdditionalVo;
import com.tzdr.domain.web.entity.RechargeAdditional;
import com.tzdr.domain.web.entity.RechargeList;


@Controller
@RequestMapping("/admin/rechargeAdditional")
public class RechargeAdditionalController  extends BaseCmsController<RechargeAdditional>
{
	private static Logger log = LoggerFactory.getLogger(RechargeAdditionalController.class);
	
	public RechargeAdditionalController() {
		setResourceIdentity("sys:finance:rechargeAdditional");
	}

	@Autowired
	private RechargeAdditionalService rechargeAdditionalService;
	
	@Autowired
	private PayService payService;
	
	
	@RequestMapping(value = "/list")
	public String list(HttpServletRequest request) throws Exception 
	{
		return ViewConstants.RechargeViewJsp.LIST_VIEW_RECHARGE_ADDITIONAL;
	}

	
	
	/**
	 *  含参数的搜索  search_EQ_name
	 * @param easyUiPage
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getDatas", method = RequestMethod.POST)
	@ResponseBody
	public Object testmultilist(EasyUiPageInfo easyUiPage, Model model,
			HttpServletRequest request) throws Exception{
		//查看权限
		if (permissionList != null) {
	            this.permissionList.assertHasViewPermission();
	    }
		//获取模糊搜索参数
		Map<String, Object> searchParams = EasyuiUtil.getParametersStartingWith(request,EasyuiUtil.SEARCH_PREFIX);
		//排序设置
		EasyuiUtil.getMultilistSortMap(easyUiPage);
							
		PageInfo<Object> pageInfo = rechargeAdditionalService.queryDatas(easyUiPage,searchParams);
		return new EasyUiPageData(pageInfo);
	}
	
	
	@RequestMapping(value = "/edit")
	public String edit(HttpServletRequest request,@RequestParam("fromType") String fromType,
			@RequestParam(value="id",required=false) String id) throws Exception 
	{
		request.setAttribute("fromType",fromType);
		if (StringUtil.equals(fromType,Constants.ADD)){
			return ViewConstants.RechargeViewJsp.EIDT_VIEW_RECHARGE_ADDITIONAL;			
		}
		
		if (StringUtil.equals(fromType,Constants.EDIT)){
			RechargeAdditionalVo  additionalVo  = rechargeAdditionalService.getVoById(id);
			if (StringUtil.equals("alipay",additionalVo.getTradeAccount())){
				additionalVo.setTradeAccount(null);
			}
			request.setAttribute("additionalVo",additionalVo);
			return ViewConstants.RechargeViewJsp.EIDT_VIEW_RECHARGE_ADDITIONAL;	
		}
		
		return ViewConstants.ERROR_VIEW;
	}
	
	@Override
	public BaseService<RechargeAdditional> getBaseService() {
		return rechargeAdditionalService;
	}
	
	@RequestMapping(value = "/saveInfo", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult  saveInfo(HttpServletRequest request,RechargeAdditionalVo additionalVo){
		Integer source = additionalVo.getSource();
		if (ObjectUtil.equals(null,source) ||
				(source!=Constant.Source.TZDR && source!=Constant.Source.PGB)){
			return new JsonResult(false,"充值来源填写有误！");
		}
		if (StringUtil.equals("3",additionalVo.getType())){
			additionalVo.setTradeAccount("alipay");
			additionalVo.setBankCard("");
			additionalVo.setWechatNo("");
			if (StringUtil.isBlank(additionalVo.getAlipayNo())
					|| StringUtil.isBlank(additionalVo.getMobile())
					|| additionalVo.getMoney()<=0){
				return new JsonResult(false,"支付宝充值，数据填写有误！");
			}
		}else if(StringUtil.equals("9", additionalVo.getType())){
			additionalVo.setBankCard("");
			additionalVo.setAlipayNo(additionalVo.getWechatNo());
			additionalVo.setTradeAccount("wechat");
			if (StringUtil.isBlank(additionalVo.getWechatNo())
					|| StringUtil.isBlank(additionalVo.getMobile())
					|| additionalVo.getMoney()<=0){
				return new JsonResult(false,"支付宝充值，数据填写有误！");
			}
		}
		else
		{
			additionalVo.setAlipayNo("");
			additionalVo.setWechatNo("");
			if (StringUtil.isBlank(additionalVo.getBankCard())
					|| StringUtil.isBlank(additionalVo.getTradeAccount())
					|| StringUtil.isBlank(additionalVo.getMobile())
					|| additionalVo.getMoney()<=0){
				return new JsonResult(false,"银行转账充值，数据填写有误！");
			}
		}
		
		String ip=IpUtils.getIpAddr(request);
		additionalVo.setIp(ip);
		rechargeAdditionalService.saveData(additionalVo);
		
		return new JsonResult("操作成功！");
	}
	
	@RequestMapping(value = "/updateInfo", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult  updateInfo(HttpServletRequest request,RechargeAdditionalVo additionalVo,
			@RequestParam(value="id") String id,
			@RequestParam(value="rechargeId") String rechargeId){
		
		Integer source = additionalVo.getSource();
		if (ObjectUtil.equals(null,source) ||
				(source!=Constant.Source.TZDR && source!=Constant.Source.PGB)){
			return new JsonResult(false,"充值来源填写有误！");
		}
		
		if (StringUtil.equals("3",additionalVo.getType())){
			additionalVo.setTradeAccount("alipay");
			additionalVo.setBankCard("");
			additionalVo.setWechatNo("");
			if (StringUtil.isBlank(additionalVo.getAlipayNo())
					|| StringUtil.isBlank(additionalVo.getMobile())
					|| additionalVo.getMoney()<=0){
				return new JsonResult(false,"支付宝充值，数据填写有误！");
			}
		}else if(StringUtil.equals("9", additionalVo.getType())){
			additionalVo.setBankCard("");
			additionalVo.setAlipayNo(additionalVo.getWechatNo());
			additionalVo.setTradeAccount("wechat");
			if (StringUtil.isBlank(additionalVo.getWechatNo())
					|| StringUtil.isBlank(additionalVo.getMobile())
					|| additionalVo.getMoney()<=0){
				return new JsonResult(false,"支付宝充值，数据填写有误！");
			}
		}
		else
		{
			additionalVo.setAlipayNo("");
			additionalVo.setWechatNo("");
			if (StringUtil.isBlank(additionalVo.getBankCard())
					|| StringUtil.isBlank(additionalVo.getTradeAccount())
					|| StringUtil.isBlank(additionalVo.getMobile())
					|| additionalVo.getMoney()<=0){
				return new JsonResult(false,"银行转账充值，数据填写有误！");
			}
		}
		
		RechargeList  rechargeList = payService.get(rechargeId);
		if(rechargeList != null && rechargeList.getStatus() != 0){
			return new JsonResult(false,"该数据已处理过，无能修改！");
		}
		
		String ip=IpUtils.getIpAddr(request);
		additionalVo.setIp(ip);
		rechargeAdditionalService.updateData(additionalVo);
		
		return new JsonResult("操作成功！");
	}
}

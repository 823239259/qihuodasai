package com.tzdr.web.controller.pay;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jodd.util.ObjectUtil;
import jodd.util.StringUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Maps;
import com.tzdr.business.pgb.service.PGBBankChannelService;
import com.tzdr.business.service.pay.PayService;
import com.tzdr.business.service.wuser.UserVerifiedService;
import com.tzdr.common.api.bbpay.vo.PayParamsObject;
import com.tzdr.common.api.payease.util.PayEaseUtil;
import com.tzdr.common.api.payease.vo.PayEaseParams;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.utils.IpUtils;
import com.tzdr.common.utils.ValidatorUtil;
import com.tzdr.common.web.support.JsonResult;
import com.tzdr.domain.constants.Constant;
import com.tzdr.domain.pgb.entity.PGBPaymentSupportBank;
import com.tzdr.domain.web.entity.RechargeList;
import com.tzdr.domain.web.entity.UserVerified;
import com.tzdr.domain.web.entity.WUser;
import com.tzdr.web.constants.Constants;
import com.tzdr.web.constants.ViewConstants;
import com.tzdr.web.utils.UserSessionBean;


/**
 * 支付controller
 * <P>title:@PayController.java</p>																								
 * <P>Description：</p>
 * <p>Copyright: Copyright (c) 2014 tzdr</p>
 * <p>Company: 上海信闳</p>
 * History：
 * @author:zhangjun
 * @date 2014年12月29日
 * @version 1.0
 */
@Controller
@RequestMapping("/pay")
public class PayController {
	@Autowired
	private PayService payService;
	
	@Autowired
	private UserVerifiedService userVerifiedService;
	
	@Autowired
	private PGBBankChannelService paymentSupportBankService;
	
	public static final Logger logger = LoggerFactory.getLogger(PayController.class);

	/**
	 * 到支付页面首页
	 * @param request
	 * @return
	 * @throws Exception
	 * @date 2014年12月31日
	 * @author zhangjun
	 */
	@RequestMapping(value = "/payinfo")
	public String payInfo(HttpServletRequest request) throws Exception {
		UserSessionBean userSessionBean=(UserSessionBean) request.getSession().getAttribute(Constants.TZDR_USER_SESSION);
		WUser user = this.payService.getUser(userSessionBean.getId());
		Double avlbal=user.getAvlBal()==null?0:user.getAvlBal();
		user.setAvlBal(avlbal);//资金余额
		UserVerified userverified=this.payService.findByUserId(user.getId());
		request.setAttribute("userverified",userverified);
		request.setAttribute("user",user);
		request.setAttribute("supportBanks",paymentSupportBankService.querySupportPayBanks());
		return ViewConstants.PayViewJsp.PAY_MAIN_VIEW;
	}
	
	
	
	
	/**
	 * 添加一条充值记录
	 * @param response
	 * @param request
	 * @return
	 * @date 2014年12月25日
	 * @author zhangjun
	 */
	@RequestMapping(value = "/noquickPayment")
	@ResponseBody
	public JsonResult quickPayment(HttpServletResponse response,HttpServletRequest request){
		
		UserSessionBean userSessionBean=(UserSessionBean) request.getSession().getAttribute(Constants.TZDR_USER_SESSION);
		WUser user = this.payService.getUser(userSessionBean.getId());
		UserVerified userverified=payService.findByUserId(userSessionBean.getId());
		String bankname=request.getParameter("banktype");
		String bankCard=request.getParameter("bankCard");
		String paymoney=request.getParameter("paymoney");
		
		JsonResult jsonResult = new JsonResult(false);
		jsonResult.setSuccess(true);
		String idcard=userverified.getIdcard();
		String ip=IpUtils.getIpAddr(request);	
		
		if(StringUtil.isNotBlank(userverified.getIdcard())
				&& ValidatorUtil.verifycard(idcard)){
			
			if(Double.valueOf(paymoney)>=1 && StringUtil.isNotBlank(bankname)){
				int status=Constants.PayStatus.NO_PROCESSING;
				String paytype=Constants.PayType.QUICK;
				String url=payService.insertEntity(Constant.Source.PGB,user,bankname,status,bankCard,paymoney,ip,paytype,null);
				logger.info(url);
				jsonResult.setMessage(url);
			}else{
				jsonResult.setMessage("paymoneyless");
			}
		}else{
				jsonResult.setMessage("idCardNotExist");
		}
		return jsonResult;
	}
	
	/**
	 * 网银支付
	 * @param response
	 * @param request
	 * @return
	 * @date 2015年1月4日
	 * @author zhangjun
	 */
	@RequestMapping(value = "/netPayment")
	@ResponseBody
	public JsonResult netPayment(HttpServletResponse response,HttpServletRequest request){
		UserSessionBean userSessionBean=(UserSessionBean) request.getSession().getAttribute(Constants.TZDR_USER_SESSION);
		WUser user = this.payService.getUser(userSessionBean.getId());
		String banktype=request.getParameter("banktype");
		String paymoney=request.getParameter("money");
		JsonResult jsonResult = new JsonResult(false);
		jsonResult.setSuccess(true);
		String ip=IpUtils.getIpAddr(request);	
		if(StringUtil.isNotBlank(banktype)){
			if(Double.valueOf(paymoney)>=1){
				int status=Constants.PayStatus.NO_PROCESSING;
				String paytype=Constants.PayType.NET_BANK;
				String url=payService.insertEntity(Constant.Source.PGB,user,banktype,status,"",paymoney,ip,paytype,null);
				jsonResult.setMessage(url);
				logger.info(url);
			}else{
				jsonResult.setMessage("paymoneyless");
			}
		}else{
				jsonResult.setMessage("banktypeNotExist");
		}
		return jsonResult;
	}
	
	/**
	 * 到网银支付页面后再跳转到银行自身页面
	 * @param response
	 * @param request
	 * @return
	 * @date 2015年1月15日
	 * @author zhangjun
	 */
	@RequestMapping(value = "/netbankPayment")
	public String netbankPayment(HttpServletResponse response,HttpServletRequest request){
		UserSessionBean userSessionBean=(UserSessionBean) request.getSession().getAttribute(Constants.TZDR_USER_SESSION);
		WUser user = this.payService.getUser(userSessionBean.getId());
		String banktype=request.getParameter("banktype");
		String paymoney=request.getParameter("money");
		String url="";
		String message="";
		String ip=IpUtils.getIpAddr(request);	
		// 查看当前银行是否支持
		PGBPaymentSupportBank paymentSupportBank = paymentSupportBankService.findByAbbreviation(banktype);
		if(ObjectUtil.equals(null, paymentSupportBank)){
			message="系统暂不支持此银行充值！";
			return ViewConstants.PayViewJsp.NET_PAY_MAIN_VIEW;
		}
		
		// 支持币币和联动和易支付 
		if (paymentSupportBank.getSupportPayEase()==Constants.PAYMENT_SUPPORT 
				||(paymentSupportBank.getSupportBbpay()==Constants.PAYMENT_SUPPORT
				 && paymentSupportBank.getSupportUmpay()==Constants.PAYMENT_SUPPORT
				 && paymentSupportBank.getSupportPayEase()==Constants.PAYMENT_SUPPORT)){
			
			    return payease(new PayEaseParams(paymentSupportBank.getPayeaseCode(),paymoney, 
			    		ip, Constants.PayStatus.NO_PROCESSING, banktype), response, request);
		}
		if ((paymentSupportBank.getSupportBbpay()==Constants.PAYMENT_SUPPORT
			 && paymentSupportBank.getSupportUmpay()==Constants.PAYMENT_SUPPORT)
				|| paymentSupportBank.getSupportBbpay()==Constants.PAYMENT_SUPPORT){
			return 	toBibiPay(request,new PayParamsObject(ip,banktype,Constants.PayStatus.NO_PROCESSING, paymoney,paymentSupportBank.getBbpayCode()),user);
		}
		
		//支持联动
		if (paymentSupportBank.getSupportUmpay()==Constants.PAYMENT_SUPPORT){
			// 新增支付平台切换
			if(StringUtil.isNotBlank(banktype)){
				if(Double.valueOf(paymoney)>=1){
					int status=Constants.PayStatus.NO_PROCESSING;
					String paytype=Constants.PayType.NET_BANK;
					 url=payService.insertEntity(Constant.Source.PGB,user,banktype,status,"",paymoney,ip,paytype,null);
					
					logger.info(url);
				}else{
					message="金额不能小于1元";
				}
			}else{
				message="请选择银行";
			}
			request.setAttribute("url", url);
			request.setAttribute("message", message);
			 return ViewConstants.PayViewJsp.NET_PAY_MAIN_VIEW;
		}
		
		message="系统暂不支持此银行充值！";
		return ViewConstants.PayViewJsp.NET_PAY_MAIN_VIEW;
	}
	
	
	private static Object lock = new Object();
	
	/**
	 * 支付宝转账
	 * @param response
	 * @param request
	 * @return
	 * @date 2015年1月5日
	 * @author zhangjun
	 */
	@RequestMapping(value = "/doAlipayOrTansaccount")
	@ResponseBody
	public JsonResult doAlipayOrTansaccount(HttpServletResponse response,HttpServletRequest request){
		UserSessionBean userSessionBean=(UserSessionBean) request.getSession().getAttribute(Constants.TZDR_USER_SESSION);
		WUser user = this.payService.getUser(userSessionBean.getId());
		String alipayaccount=request.getParameter("alipayaccount");
		String bankname=request.getParameter("bankname");
		String serialnum=request.getParameter("serialnum");
		String type=request.getParameter("type");//转账类型不为空为银行转账
		String alimoney=request.getParameter("alimoney");
		JsonResult jsonResult = new JsonResult(false);
		jsonResult.setSuccess(true);
		String ip=IpUtils.getIpAddr(request);
		if(StringUtil.isBlank(type)){//支付宝转账
			if(StringUtil.isNotBlank(alipayaccount)) {
				synchronized (lock) {
					UserVerified userVerified = userVerifiedService.queryUserVerifiedByAliAccount(alipayaccount);
					if(ObjectUtil.equals(null, userVerified)) {
						UserVerified uv = userVerifiedService.queryUserVerifiedByUi(user.getId());
						//绑定支付宝账号
						uv.setAlipayAccount(alipayaccount);
						userVerifiedService.update(uv);
					} else {
						jsonResult.setMessage("支付宝账号已存在");
					}
				}
//				if(Double.valueOf(alimoney)>=1){
//					int status=Constants.PayStatus.NO_PROCESSING;
//					String paytype=Constants.PayType.ALIPAY;
//					payService.insertEntity(user,"alipay",status,alipayaccount,alimoney,ip,paytype,null);
//					
//				}else{
//					jsonResult.setMessage("金额不能小于1元");
//				}
			} else {
				jsonResult.setMessage("支付宝账号不能为空");
			}
		}else{//银行转账
			if(StringUtil.isNotBlank(serialnum)){
				if(Double.valueOf(alimoney)>=1){
					int status=Constants.PayStatus.NO_PROCESSING;
					String paytype=Constants.PayType.TRANSBANK;
					payService.insertEntity(Constant.Source.PGB,user,bankname,status,alipayaccount,alimoney,ip,paytype,serialnum);
					
				}else{
					jsonResult.setMessage("金额不能小于1元");
				}
			}else{
				jsonResult.setMessage("流水号不能为空");
			}
		}
		return jsonResult;
	}
	
	/**
	 * 查询用户支付宝账号
	 * @MethodName queryUserAliAccount
	 * @author L.Y
	 * @date 2015年7月8日
	 * @return
	 */
	@RequestMapping(value = "/queryUserAliAccount.json", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult queryUserAliAccount(HttpServletRequest request) {
		UserSessionBean userSessionBean=(UserSessionBean) request.getSession().getAttribute(Constants.TZDR_USER_SESSION);
		JsonResult jsonResult = new JsonResult(Boolean.FALSE);
		UserVerified userVerified = userVerifiedService.queryUserVerifiedByUi(userSessionBean.getId());
		if(!ObjectUtil.equals(null, userVerified)) {
			jsonResult.setSuccess(Boolean.TRUE);
			jsonResult.appendData("aliAccount", userVerified.getAlipayAccount());
		}
		return jsonResult;
	}
	
	/**
	 * 支付历史记录
	 * @param response
	 * @param request
	 * @return
	 * @date 2015年1月3日
	 * @author zhangjun
	 */
	@RequestMapping(value = "/payHistory")
	@ResponseBody
	public PageInfo<RechargeList> payHistory(HttpServletResponse response,HttpServletRequest request){
	UserSessionBean userSessionBean=(UserSessionBean) request.getSession().getAttribute(Constants.TZDR_USER_SESSION);
		String pageIndex=request.getParameter("pageIndex");
		String perPage=request.getParameter("perPage");	
		PageInfo<RechargeList> pageInfo=null;
		if(StringUtil.isNotBlank(perPage) && StringUtil.isNotBlank(pageIndex))
			 pageInfo=payService.findData(pageIndex,perPage,userSessionBean.getId());
		else
			 pageInfo=payService.findData("1","10",userSessionBean.getId());

		return pageInfo;
		
	}
		
	/**
	 * 跳转进入币币支付页面
	 * @param request
	 * @param object
	 * @return
	 */
	private String toBibiPay(HttpServletRequest request,PayParamsObject sendObject,WUser user){
		/**
		 * TODO: 币币支付测试
		 * toBibiPay(request,new PayParamsObject(ip,banktype,Constants.PayStatus.NO_PROCESSING, paymoney),user);
		 */
		
		String errorMsg = "";
		if(StringUtil.isBlank(sendObject.getAbbreviation())){
			errorMsg =  "非法请求，请先选择支付银行！";
		}
		if(Double.valueOf(sendObject.getPayMoney())<1){
			errorMsg =  "非法请求，充值金额不能低于1元！";
		}
		
		if (StringUtil.isBlank(sendObject.getPnc())){
			errorMsg =  "非法请求，系统暂不支持此银行充值！";
		}
		if (StringUtil.isNotBlank(errorMsg)){
			request.setAttribute("errorMsg",errorMsg);
			return ViewConstants.PayViewJsp.BIBI_PAY_MAIN_VIEW;
		}
		
		Map<String, Object> bibiParams = Maps.newHashMap();
		try {
			bibiParams = payService.bbPay(user, sendObject,Constant.Source.PGB);
		} catch (Exception e) {
			errorMsg =  "系统异常，请稍后再试！";
			request.setAttribute("errorMsg",errorMsg);
			return ViewConstants.PayViewJsp.BIBI_PAY_MAIN_VIEW;
		}

		request.setAttribute("mobilePayUrl",bibiParams.get("mobilePayUrl"));
		request.setAttribute("merchantaccount",bibiParams.get("merchantaccount"));
		request.setAttribute("data",bibiParams.get("data"));
		request.setAttribute("encryptkey",bibiParams.get("encryptkey"));
		
		return ViewConstants.PayViewJsp.BIBI_PAY_MAIN_VIEW;
	}
	
	/**
	 * 首信易支付
	 * @param response
	 * @param request
	 * @return
	 */
	private String payease(PayEaseParams  payEaseParams,HttpServletResponse response,HttpServletRequest request){
			String errorMsg = "";
			if(StringUtil.isBlank(payEaseParams.getAbbreviation()) 
					|| StringUtil.isBlank(payEaseParams.getVpmode())){
				errorMsg =  "非法请求，请先选择支付银行！";
			}
			if(Double.valueOf(payEaseParams.getVamount()) < 1){
				errorMsg =  "充值金额不能小于1元！";
			}
			
			if (StringUtil.isNotBlank(errorMsg)){
				request.setAttribute("errorMsg",errorMsg);
				return ViewConstants.PayViewJsp.PAY_MAIN_MAIN_VIEW;
			}
			
			UserSessionBean userSessionBean=(UserSessionBean) request.getSession().getAttribute(Constants.TZDR_USER_SESSION);
			WUser user = this.payService.getUser(userSessionBean.getId());
			PayEaseParams  easeParams = this.payService.PayEasePay(user, PayEaseUtil.pgbWebPay(payEaseParams),Constant.Source.PGB,Constant.SystemFlag.PGB_WEB);
			request.setAttribute("payEaseParams",easeParams);
			return  ViewConstants.PayViewJsp.PAY_MAIN_MAIN_VIEW;
	}
	
}

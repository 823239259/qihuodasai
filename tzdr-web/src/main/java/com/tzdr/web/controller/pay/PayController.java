package com.tzdr.web.controller.pay;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jodd.util.ObjectUtil;
import jodd.util.StringUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Maps;
import com.tzdr.business.cms.service.messagePrompt.MessagePromptService;
import com.tzdr.business.cms.service.messagePrompt.PromptTypes;
import com.tzdr.business.pay.gopay.DateUtil;
import com.tzdr.business.pay.gopay.handle.GoPayTradeData;
import com.tzdr.business.pay.gopay.model.GoPayRequestModel;
import com.tzdr.business.pay.pingpp.config.Config;
import com.tzdr.business.pay.pingpp.config.enums.Channel;
import com.tzdr.business.pay.pingpp.example.ChargeExample;
import com.tzdr.business.pay.pingpp.model.PingPPModel;
import com.tzdr.business.service.pay.PayService;
import com.tzdr.business.service.pay.PaymentSupportBankService;
import com.tzdr.business.service.wuser.UserVerifiedService;
import com.tzdr.business.service.wuser.WUserService;
import com.tzdr.common.api.bbpay.vo.PayParamsObject;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.utils.IpUtils;
import com.tzdr.common.utils.ValidatorUtil;
import com.tzdr.common.web.support.JsonResult;
import com.tzdr.domain.constants.Constant;
import com.tzdr.domain.web.entity.PaymentSupportBank;
import com.tzdr.domain.web.entity.RechargeList;
import com.tzdr.domain.web.entity.UserVerified;
import com.tzdr.domain.web.entity.WUser;
import com.tzdr.web.constants.Constants;
import com.tzdr.web.constants.ViewConstants;
import com.tzdr.web.utils.CookiesUtil;
import com.tzdr.web.utils.UserSessionBean;

/**
 * 支付controller
 * <P>
 * title:@PayController.java
 * </p>
 * <P>
 * Description：
 * </p>
 * <p>
 * Copyright: Copyright (c) 2014 tzdr
 * </p>
 * <p>
 * Company: 上海信闳
 * </p>
 * History：
 * 
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
	private WUserService wUserService;
	
	@Autowired
	private MessagePromptService messagePromptService;

	@Autowired
	private UserVerifiedService userVerifiedService;

	@Autowired
	private PaymentSupportBankService paymentSupportBankService;

	public static final Logger logger = LoggerFactory.getLogger(PayController.class);

	/**
	 * 到支付页面首页
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 * @date 2014年12月31日
	 * @author zhangjun
	 */
	@RequestMapping(value = "/payinfo")
	public String payInfo(HttpServletRequest request,HttpServletResponse response) throws Exception {
		UserSessionBean userSessionBean = (UserSessionBean) request.getSession()
				.getAttribute(Constants.TZDR_USER_SESSION);
		WUser user = this.payService.getUser(userSessionBean.getId());
		Double avlbal = user.getAvlBal() == null ? 0 : user.getAvlBal();
		user.setAvlBal(avlbal);// 资金余额
		UserVerified userverified = this.payService.findByUserId(user.getId());
		request.setAttribute("userverified", userverified);
		request.setAttribute("user", user);
		String balance = request.getParameter("balance");
		request.setAttribute("money", balance);
		String isFlag = request.getParameter("isFlag");
		//如果支付是从申请方案跳转而来就缓存方案数据
		if(isFlag != null && isFlag.equals("1")){
			request.setAttribute("isFlag", isFlag);
			CookiesUtil.addCookie(response, "bond", request.getParameter("inputTraderBond"), 600);
			CookiesUtil.addCookie(response, "lever", request.getParameter("inputTranLever"), 600);
			CookiesUtil.addCookie(response, "payurl", request.getParameter("payUrl"), 600);
			CookiesUtil.addCookie(response, "vocherid", request.getParameter("inputVocherId"), 600);
		}
		request.setAttribute("supportBanks", paymentSupportBankService.querySupportPayBanks());
		return ViewConstants.PayViewJsp.PAY_MAIN_VIEW;
	}

	/**
	 * 添加一条充值记录
	 * 
	 * @param response
	 * @param request
	 * @return
	 * @date 2014年12月25日
	 * @author zhangjun
	 */
	@RequestMapping(value = "/noquickPayment")
	@ResponseBody
	public JsonResult quickPayment(HttpServletResponse response, HttpServletRequest request) {

		UserSessionBean userSessionBean = (UserSessionBean) request.getSession()
				.getAttribute(Constants.TZDR_USER_SESSION);
		WUser user = this.payService.getUser(userSessionBean.getId());
		UserVerified userverified = payService.findByUserId(userSessionBean.getId());
		String bankname = request.getParameter("banktype");
		String bankCard = request.getParameter("bankCard");
		String paymoney = request.getParameter("paymoney");

		JsonResult jsonResult = new JsonResult(false);
		jsonResult.setSuccess(true);
		String idcard = userverified.getIdcard();
		String ip = IpUtils.getIpAddr(request);

		if (StringUtil.isNotBlank(userverified.getIdcard()) && ValidatorUtil.verifycard(idcard)) {

			if (Double.valueOf(paymoney) >= 1 && StringUtil.isNotBlank(bankname)) {
				int status = Constants.PayStatus.NO_PROCESSING;
				String paytype = Constants.PayType.QUICK;
				String url = payService.insertEntity(Constant.Source.TZDR, user, bankname, status, bankCard, paymoney,
						ip, paytype, null);
				logger.info(url);
				jsonResult.setMessage(url);
			} else {
				jsonResult.setMessage("paymoneyless");
			}
		} else {
			jsonResult.setMessage("idCardNotExist");
		}
		return jsonResult;
	}

	/**
	 * 网银支付
	 * 
	 * @param response
	 * @param request
	 * @return
	 * @date 2015年1月4日
	 * @author zhangjun
	 */
	@RequestMapping(value = "/netPayment")
	@ResponseBody
	public JsonResult netPayment(HttpServletResponse response, HttpServletRequest request) {
		UserSessionBean userSessionBean = (UserSessionBean) request.getSession()
				.getAttribute(Constants.TZDR_USER_SESSION);
		WUser user = this.payService.getUser(userSessionBean.getId());
		String banktype = request.getParameter("banktype");
		String paymoney = request.getParameter("money");
		JsonResult jsonResult = new JsonResult(false);
		jsonResult.setSuccess(true);
		String ip = IpUtils.getIpAddr(request);
		if (StringUtil.isNotBlank(banktype)) {
			if (Double.valueOf(paymoney) >= 1) {
				int status = Constants.PayStatus.NO_PROCESSING;
				String paytype = Constants.PayType.NET_BANK;
				String url = payService.insertEntity(Constant.Source.TZDR, user, banktype, status, "", paymoney, ip,
						paytype, null);
				jsonResult.setMessage(url);

				logger.info(url);
			} else {
				jsonResult.setMessage("paymoneyless");
			}
		} else {
			jsonResult.setMessage("banktypeNotExist");
		}
		return jsonResult;
	}

	/**
	 * 到网银支付页面后再跳转到银行自身页面
	 * 
	 * @param response
	 * @param request
	 * @return
	 * @date 2015年1月15日
	 * @author zhangjun
	 */
	@RequestMapping(value = "/netbankPayment")
	public String netbankPayment(HttpServletResponse response, HttpServletRequest request) {
		UserSessionBean userSessionBean = (UserSessionBean) request.getSession()
				.getAttribute(Constants.TZDR_USER_SESSION);
		WUser user = this.payService.getUser(userSessionBean.getId());
		String banktype = request.getParameter("banktype");
		String paymoney = request.getParameter("money");
		String url = "";
		String message = "";
		String ip = IpUtils.getIpAddr(request);
		// 查看当前银行是否支持
		PaymentSupportBank paymentSupportBank = paymentSupportBankService.findByAbbreviation(banktype);
		if (ObjectUtil.equals(null, paymentSupportBank)) {
			message = "系统暂不支持此银行充值！";
			return ViewConstants.PayViewJsp.NET_PAY_MAIN_VIEW;
		}
		if ((paymentSupportBank.getSupportBbpay() == Constants.PAYMENT_SUPPORT
				&& paymentSupportBank.getSupportUmpay() == Constants.PAYMENT_SUPPORT)
				|| paymentSupportBank.getSupportBbpay() == Constants.PAYMENT_SUPPORT) {
			return toBibiPay(request, new PayParamsObject(ip, banktype, Constants.PayStatus.NO_PROCESSING, paymoney,
					paymentSupportBank.getBbpayCode()), user);
		}

		// 支持联动
		if (paymentSupportBank.getSupportUmpay() == Constants.PAYMENT_SUPPORT) {
			// 新增支付平台切换
			if (StringUtil.isNotBlank(banktype)) {
				if (Double.valueOf(paymoney) >= 1) {
					int status = Constants.PayStatus.NO_PROCESSING;
					String paytype = Constants.PayType.NET_BANK;
					url = payService.insertEntity(Constant.Source.TZDR, user, banktype, status, "", paymoney, ip,
							paytype, null);

					logger.info(url);
				} else {
					message = "金额不能小于1元";
				}
			} else {
				message = "请选择银行";
			}
			request.setAttribute("url", url);
			request.setAttribute("message", message);
			return ViewConstants.PayViewJsp.NET_PAY_MAIN_VIEW;
		}
		message = "系统暂不支持此银行充值！";
		return ViewConstants.PayViewJsp.NET_PAY_MAIN_VIEW;
	}

	/**
	 * ping++支付
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/pingplusplus", method = RequestMethod.POST)
	public JsonResult pingplusplus(HttpServletRequest request) {
		if(true)return null;
		//System.out.println("执行中。。。");
		UserSessionBean userSessionBean = (UserSessionBean) request.getSession()
				.getAttribute(Constants.TZDR_USER_SESSION);
		WUser user = this.payService.getUser(userSessionBean.getId());
		JsonResult resultJson = new JsonResult(false);
		String paymoney = request.getParameter("money");
		String payWay = request.getParameter("payWay");
		if (paymoney != null && Double.parseDouble(paymoney) > 0) {
			int status = Constants.PayStatus.NO_PROCESSING;
			String paytype = Constants.PayType.ALIPAY;
			int source = Constant.Source.TZDR;
			String ip = IpUtils.getIpAddr(request);
			String orderNo = ChargeExample.randomNo();
			Channel payWayChannl = null;
			if (payWay == null) {
				payWayChannl = Channel.ALIPAY_PC_DIRECT;
			} else {
				payWayChannl = Channel.newInstanceChannel(Integer.parseInt(payWay));
				if (payWayChannl == null) {
					payWayChannl = Channel.ALIPAY_PC_DIRECT;
				}
			}
			String result = payService.doSavePingPPRecharge(payWayChannl, source, user, status, "", paymoney, ip,
					paytype, orderNo);
			if (result.equals("1")) {
				PingPPModel pingPPModel = new PingPPModel();
				pingPPModel.setAmount(Double.valueOf(paymoney));
				pingPPModel.setBody(Config.BODY);
				pingPPModel.setChannel(payWayChannl.getChannelCode());// ;
				pingPPModel.setClient_ip(ip);
				pingPPModel.setCurrency("cny");
				pingPPModel.setOrder_no(orderNo);
				pingPPModel.setSubject(Config.SUBJECT);
				resultJson.appendData("data", ChargeExample.createCharge(pingPPModel).toString());
			}
		}
		return resultJson;
	}
	
	/**
	 * 微信支付
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/wx/pingplusplus", method = RequestMethod.GET)
	public String wxPingplusplus(HttpServletRequest request) {
		System.out.println("执行中。。。");
		UserSessionBean userSessionBean = (UserSessionBean) request.getSession()
				.getAttribute(Constants.TZDR_USER_SESSION);
		WUser user = this.payService.getUser(userSessionBean.getId());
		String paymoney = request.getParameter("money");
		String payWay = request.getParameter("payWay");
		if (paymoney != null && Double.parseDouble(paymoney) > 0) {
			int status = Constants.PayStatus.NO_PROCESSING;
			String paytype = Constants.PayType.ALIPAY;
			int source = Constant.Source.TZDR;
			String ip = IpUtils.getIpAddr(request);
			String orderNo = ChargeExample.randomNo();
			Channel payWayChannl = null;
			if (payWay == null) {
				payWayChannl = Channel.ALIPAY_PC_DIRECT;
			} else {
				payWayChannl = Channel.newInstanceChannel(Integer.parseInt(payWay));
				if (payWayChannl == null) {
					payWayChannl = Channel.ALIPAY_PC_DIRECT;
				}
			}
			String result = payService.doSavePingPPRecharge(payWayChannl, source, user, status, "", paymoney, ip,
					paytype, orderNo);
			if (result.equals("1")) {
				PingPPModel pingPPModel = new PingPPModel();
				pingPPModel.setAmount(Double.valueOf(paymoney));
				pingPPModel.setBody(Config.BODY);
				pingPPModel.setChannel(payWayChannl.getChannelCode());// ;
				pingPPModel.setClient_ip(ip);
				pingPPModel.setCurrency("cny");
				pingPPModel.setOrder_no(orderNo);
				pingPPModel.setSubject(Config.SUBJECT);
				request.setAttribute("charge", ChargeExample.createCharge(pingPPModel).toString());
			}
			return "/views/pay/pingppPay";
		}
		return null;
	}

	@RequestMapping(value = "/goPayView", method = RequestMethod.GET)
	public String goPayView(ModelMap modelMap, @RequestParam("gopaymoney") Double gopaymoney,
			@RequestParam("gopayWay") String gopayWay,HttpServletRequest request) throws UnsupportedEncodingException {
		modelMap.put("money", gopaymoney);
		modelMap.put("payway", gopayWay);
		modelMap.put("isFlag", request.getParameter("isFlag"));
		return "/views/pay/gopay";
	}

	private static Object lock = new Object();

	/**
	 * 国付宝支付
	 * 
	 * @param response
	 * @param request
	 * @return
	 * @date 2016年08月10日
	 * @author hedaoqing
	 */
	@ResponseBody
	@RequestMapping(value = "/gopay", method = RequestMethod.POST)
	public JsonResult gopay(ModelMap modelMap, HttpServletResponse response, HttpServletRequest request,
			@RequestParam("paymoney") Double gopaymoney, @RequestParam("gopayWay") String gopayWay) {
		boolean flag = false;
		String message = "";
		String isFlag = request.getParameter("isFlag");
		if (gopaymoney != null && gopaymoney > 0) {
			String money = String.valueOf(gopaymoney);
			UserSessionBean userSessionBean = (UserSessionBean) request.getSession()
					.getAttribute(Constants.TZDR_USER_SESSION);
			WUser user = this.payService.getUser(userSessionBean.getId());
			String ip = IpUtils.getIpAddr(request);
			String orderNo = ChargeExample.randomNo();
			//当支付从申请方案跳转则需要缓存数据，有效时间为10分钟
			if(isFlag != null && isFlag.equals("1")){
				CookiesUtil.addCookie(response, "orderId", orderNo , 600);
			}
			//如果是充值是从申请方案跳转则需要缓存订单号
			Channel payWayChannl = null;
			if (gopayWay == null) {
				payWayChannl = Channel.ALIPAY_PC_DIRECT;
			} else {
				payWayChannl = Channel.newInstanceChannel(Integer.parseInt(gopayWay));
				if (payWayChannl == null) {
					payWayChannl = Channel.ALIPAY_PC_DIRECT;
				}
			}
			int status = Constants.PayStatus.NO_PROCESSING;
			String paytype = Constants.PayType.NET_BANK;
			int source = Constant.Source.TZDR;
			String result = payService.doSavePingPPRecharge(payWayChannl, source, user, status, "", money, ip, paytype,
					orderNo);
			if (result.equals("1")) {
				GoPayRequestModel goPayRequestModel = new GoPayRequestModel();
				goPayRequestModel.setBuyerContact("");
				goPayRequestModel.setBuyerName("");
				goPayRequestModel.setMerOrderNum(orderNo);
				goPayRequestModel.setTranAmt(money);
				goPayRequestModel.setFeeAmt("0");
				goPayRequestModel.setTranDateTime(DateUtil.getDate("yyyyMMddHHmmss"));
				goPayRequestModel.setTranIP(ip);
				goPayRequestModel.setGoodsName("维胜充值");
				goPayRequestModel.setGoodsDetail("维胜充值");
				try {
					String tradeData = GoPayTradeData.createTradeData(goPayRequestModel);
					message = tradeData;
					flag = true;
					logger.info(tradeData);
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			} else {
				message = "操作失败，请重试!";
			}
		} else {
			message = "金额错误";
		}
		JsonResult resultJson = new JsonResult();
		resultJson.setSuccess(flag);
		resultJson.appendData("data", message);
		return resultJson;
	}
	/**
	 * 支付宝转账
	 * 
	 * @param response
	 * @param request
	 * @return
	 * @date 2015年1月5日
	 * @author zhangjun
	 */
	@RequestMapping(value = "/doAlipayOrTansaccount")
	@ResponseBody
	public JsonResult doAlipayOrTansaccount(HttpServletResponse response, HttpServletRequest request) {
		UserSessionBean userSessionBean = (UserSessionBean) request.getSession()
				.getAttribute(Constants.TZDR_USER_SESSION);
		WUser user = this.payService.getUser(userSessionBean.getId());
		String alipayaccount = request.getParameter("alipayaccount");
		String bankname = request.getParameter("bankname");
		String serialnum = request.getParameter("serialnum");
		String type = request.getParameter("type");// 转账类型不为空为银行转账
		String alimoney = request.getParameter("alimoney");
		JsonResult jsonResult = new JsonResult(false);
		jsonResult.setSuccess(true);
		String ip = IpUtils.getIpAddr(request);
		if (StringUtil.isBlank(type)) {// 支付宝转账
			if (StringUtil.isNotBlank(alipayaccount)) {
				synchronized (lock) {
					UserVerified userVerified = userVerifiedService.queryUserVerifiedByAliAccount(alipayaccount);
					if (ObjectUtil.equals(null, userVerified)) {
						UserVerified uv = userVerifiedService.queryUserVerifiedByUi(user.getId());
						// 绑定支付宝账号
						uv.setAlipayAccount(alipayaccount);
						userVerifiedService.update(uv);

					} else {
						jsonResult.setMessage("支付宝账号已存在");
					}
				}
				// if(Double.valueOf(alimoney)>=1){
				// int status=Constants.PayStatus.NO_PROCESSING;
				// String paytype=Constants.PayType.ALIPAY;
				// payService.insertEntity(user,"alipay",status,alipayaccount,alimoney,ip,paytype,null);
				//
				// }else{
				// jsonResult.setMessage("金额不能小于1元");
				// }
			} else {
				jsonResult.setMessage("支付宝账号不能为空");
			}
		} else {// 银行转账
			if (StringUtil.isNotBlank(serialnum)) {
				if (Double.valueOf(alimoney) >= 1) {
					int status = Constants.PayStatus.NO_PROCESSING;
					String paytype = Constants.PayType.TRANSBANK;
					payService.insertEntity(Constant.Source.TZDR, user, bankname, status, alipayaccount, alimoney, ip,
							paytype, serialnum);
				} else {
					jsonResult.setMessage("金额不能小于1元");
				}
			} else {
				jsonResult.setMessage("流水号不能为空");
			}
		}
		WUser wuser = wUserService.get(userSessionBean.getId()); // 获取用户信息
		// TODO 充值银行审核，给工作人员发送Email
		try {
			messagePromptService.sendMessage(PromptTypes.isBankReCharge, wuser.getMobile());
		} catch (Exception e) {
			logger.info("发送邮件失败", e);
		}
		return jsonResult;
	}
	/**
	 * 绑定微信账号
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/wx/bind/account",method = RequestMethod.POST)
	public JsonResult wxBindAccount(HttpServletRequest request,@RequestParam("account")String account){
		UserSessionBean userSessionBean = (UserSessionBean) request.getSession()
				.getAttribute(Constants.TZDR_USER_SESSION);
		WUser user = this.payService.getUser(userSessionBean.getId());
		JsonResult resultJson = new JsonResult(false);
		if (StringUtil.isNotBlank(account)) {
			synchronized (lock) { 
				UserVerified userVerified = userVerifiedService.queryUserVerifiedByWechatAccount(account);
				if (ObjectUtil.equals(null, userVerified)) {
					UserVerified uv = userVerifiedService.queryUserVerifiedByUi(user.getId());
					// 绑定微信账号
					uv.setWxAccount(account);
					userVerifiedService.update(uv);
					resultJson.setSuccess(true);
				} else {
					resultJson.setMessage("微信账号已存在");
				}
			}
		} else {
			resultJson.setMessage("微信账号不能为空");
		}
		return resultJson;
	}
	
	/**
	 * 查询用户支付宝账号
	 * 
	 * @MethodName queryUserAliAccount
	 * @author L.Y
	 * @date 2015年7月8日
	 * @return
	 */
	@RequestMapping(value = "/queryUserAliAccount.json", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult queryUserAliAccount(HttpServletRequest request) {
		UserSessionBean userSessionBean = (UserSessionBean) request.getSession()
				.getAttribute(Constants.TZDR_USER_SESSION);
		JsonResult jsonResult = new JsonResult(Boolean.FALSE);
		UserVerified userVerified = userVerifiedService.queryUserVerifiedByUi(userSessionBean.getId());
		if (!ObjectUtil.equals(null, userVerified)) {
			jsonResult.setSuccess(Boolean.TRUE);
			jsonResult.appendData("aliAccount", userVerified.getAlipayAccount());
		}
		return jsonResult;
	}

	/**
	 * 支付历史记录
	 * 
	 * @param response
	 * @param request
	 * @return
	 * @date 2015年1月3日
	 * @author zhangjun
	 */
	@RequestMapping(value = "/payHistory")
	@ResponseBody
	public PageInfo<RechargeList> payHistory(HttpServletResponse response, HttpServletRequest request) {
		UserSessionBean userSessionBean = (UserSessionBean) request.getSession()
				.getAttribute(Constants.TZDR_USER_SESSION);
		String pageIndex = request.getParameter("pageIndex");
		String perPage = request.getParameter("perPage");
		PageInfo<RechargeList> pageInfo = null;
		if (StringUtil.isNotBlank(perPage) && StringUtil.isNotBlank(pageIndex))
			pageInfo = payService.findData(pageIndex, perPage, userSessionBean.getId());
		else
			pageInfo = payService.findData("1", "10", userSessionBean.getId());

		return pageInfo;

	}

	/**
	 * 跳转进入币币支付页面
	 * 
	 * @param request
	 * @param object
	 * @return
	 */
	private String toBibiPay(HttpServletRequest request, PayParamsObject sendObject, WUser user) {
		/**
		 * TODO: 币币支付测试 toBibiPay(request,new
		 * PayParamsObject(ip,banktype,Constants.PayStatus.NO_PROCESSING,
		 * paymoney),user);
		 */

		String errorMsg = "";
		if (StringUtil.isBlank(sendObject.getAbbreviation())) {
			errorMsg = "非法请求，请先选择支付银行！";
		}
		if (Double.valueOf(sendObject.getPayMoney()) < 1) {
			errorMsg = "非法请求，充值金额不能低于1元！";
		}

		if (StringUtil.isBlank(sendObject.getPnc())) {
			errorMsg = "非法请求，系统暂不支持此银行充值！";
		}
		if (StringUtil.isNotBlank(errorMsg)) {
			request.setAttribute("errorMsg", errorMsg);
			return ViewConstants.PayViewJsp.BIBI_PAY_MAIN_VIEW;
		}

		Map<String, Object> bibiParams = Maps.newHashMap();
		try {
			bibiParams = payService.bbPay(user, sendObject, Constant.Source.TZDR);
		} catch (Exception e) {
			errorMsg = "系统异常，请稍后再试！";
			request.setAttribute("errorMsg", errorMsg);
			return ViewConstants.PayViewJsp.BIBI_PAY_MAIN_VIEW;
		}

		request.setAttribute("mobilePayUrl", bibiParams.get("mobilePayUrl"));
		request.setAttribute("merchantaccount", bibiParams.get("merchantaccount"));
		request.setAttribute("data", bibiParams.get("data"));
		request.setAttribute("encryptkey", bibiParams.get("encryptkey"));

		return ViewConstants.PayViewJsp.BIBI_PAY_MAIN_VIEW;
	}
	private static Object lock_wechat_transfer = new Object();
	/**
	 * 微信转账确认充值
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/wechat_transfer",method =RequestMethod.POST)
	@ResponseBody
	public JsonResult wechatTransfer(HttpServletRequest request,@RequestParam("money")Double money,@RequestParam("transactionNo") String transactionNo){
		UserSessionBean userSessionBean = (UserSessionBean) request.getSession()
				.getAttribute(Constants.TZDR_USER_SESSION);
		JsonResult resultJson = new JsonResult(true);
		String uid = userSessionBean.getId();
		WUser user = wUserService.getUser(uid);
		if(user == null){
			resultJson.setSuccess(false);
			resultJson.setMessage("用户信息不存在");
			return resultJson;
		}
		UserVerified userVerified = userVerifiedService.queryUserVerifiedByUi(uid);
		if(userVerified != null){
				synchronized (lock_wechat_transfer) {
					RechargeList rechargeLists = payService.findByTradeNo(transactionNo);
					if(rechargeLists != null){
						resultJson.setMessage("提交失败,重复的订单号");
						resultJson.setSuccess(false);
					}
					RechargeList rechargeList  = new RechargeList();
					rechargeList.setAccount("");
					rechargeList.setAddtime(new Date().getTime());
					rechargeList.setUid(uid);
					rechargeList.setSource(Constant.RegistSource.APP_TZDR_REGIST);
					rechargeList.setActualMoney(money);
					rechargeList.setMoney(money);
					rechargeList.setTradeAccount("wechat");
					rechargeList.setType(Constants.PayType.WECHAT_TYPE);
					rechargeList.setStatus(Constants.PayStatus.NO_PROCESSING);
					rechargeList.setTradeNo(transactionNo);
					payService.autoWechat(rechargeList);
				}
		}
		return resultJson;
	}
}

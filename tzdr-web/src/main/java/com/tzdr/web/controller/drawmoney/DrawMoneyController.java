package com.tzdr.web.controller.drawmoney;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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

import com.alibaba.fastjson.JSONObject;
import com.tzdr.business.cms.service.messagePrompt.MessagePromptService;
import com.tzdr.business.cms.service.messagePrompt.PromptTypes;
import com.tzdr.business.service.datamap.DataMapService;
import com.tzdr.business.service.drawBlackList.DrawBlackListService;
import com.tzdr.business.service.drawMoney.DrawMoneyService;
import com.tzdr.business.service.drawMoney.UserBankService;
import com.tzdr.business.service.drawMoneyData.DrawMoneyDataService;
import com.tzdr.business.service.pay.PaymentSupportBankService;
import com.tzdr.business.service.securityInfo.impl.SecurityInfoServiceImpl;
import com.tzdr.business.service.thread.SMSSenderThread;
import com.tzdr.common.api.bbpay.Bibipay;
import com.tzdr.common.api.bbpay.util.BbUtil;
import com.tzdr.common.api.umpay.WithdrawPay;
import com.tzdr.common.api.umpay.data.WithdrawPayExtendInfo;
import com.tzdr.common.api.umpay.data.WithdrawPayInfo;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.exception.EmailExceptionHandler;
import com.tzdr.common.utils.BigDecimalUtils;
import com.tzdr.common.utils.DateUtils;
import com.tzdr.common.utils.IpUtils;
import com.tzdr.common.utils.StringCodeUtils;
import com.tzdr.common.web.support.JsonResult;
import com.tzdr.domain.cache.CacheManager;
import com.tzdr.domain.cache.DataDicKeyConstants;
import com.tzdr.domain.constants.Constant;
import com.tzdr.domain.entity.DataMap;
import com.tzdr.domain.web.entity.DrawBlackList;
import com.tzdr.domain.web.entity.DrawList;
import com.tzdr.domain.web.entity.DrawMoneyData;
import com.tzdr.domain.web.entity.PaymentSupportBank;
import com.tzdr.domain.web.entity.UserBank;
import com.tzdr.domain.web.entity.UserVerified;
import com.tzdr.domain.web.entity.WUser;
import com.tzdr.web.constants.Constants;
import com.tzdr.web.constants.ViewConstants;
import com.tzdr.web.utils.DrawMoneyFail;
import com.tzdr.web.utils.DrawMoneyFailBean;
import com.tzdr.web.utils.UserSessionBean;

/**
 * 取现controller
 * <P>
 * title:@DrawMoneyController.java
 * </p>
 * <P>
 * Description：
 * </p>
 * <p>
 * Copyright: Copyright (c) 2015 tzdr
 * </p>
 * <p>
 * Company: 上海信闳
 * </p>
 * History：
 * 
 * @author:zhangjun
 * @date 2015年1月7日
 * @version 1.0
 */
@Controller
@RequestMapping("/draw")
public class DrawMoneyController {
	public static final Logger logger = LoggerFactory.getLogger(DrawMoneyController.class);

	@Autowired
	private DrawMoneyService drawMoneyService;
	@Autowired
	private SecurityInfoServiceImpl securityInfoService;
	@Autowired
	private DataMapService dataMapService;
	@Autowired
	private UserBankService userBankService;
	@Autowired
	private DrawBlackListService drawBlackListService;
	@Autowired
	private DrawMoneyDataService drawMoneyDataService;

	@Autowired
	private PaymentSupportBankService paymentSupportBankService;

	/**
	 * 取现首页
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 * @date 2015年1月7日
	 * @author zhangjun
	 */
	@RequestMapping(value = "/drawmoney")
	public String drawmoney(HttpServletRequest request) throws Exception {
		UserSessionBean userSessionBean = (UserSessionBean) request.getSession()
				.getAttribute(Constants.TZDR_USER_SESSION);
		WUser user = this.drawMoneyService.getUser(userSessionBean.getId());
		Double avlbal = user.getAvlBal() == null ? 0 : user.getAvlBal();
		user.setAvlBal(avlbal);// 资金余额
		UserVerified userverified = user.getUserVerified();
		List<UserBank> banks = this.drawMoneyService.findUserBankbyUserId(user.getId());
		boolean isNeedCode = false;
		Object obj = request.getSession()
				.getAttribute(com.tzdr.web.constants.Constants.DRAW_FAIL_MAX_COUNT_SESSION_KEY);

		int drawFailCount = obj == null ? 0 : (int) obj;
		if (com.tzdr.web.constants.Constants.DRAW_FAIL_MAX_COUNT_SESSION < drawFailCount) {
			isNeedCode = true;
		}
		// 获取提现渠道设置参数
		int withdrawSetting = dataMapService.getWithDrawSetting();
		request.setAttribute("withdrawSetting", withdrawSetting);
		String handleFeeStr = CacheManager.getDataMapByKey(DataDicKeyConstants.WITHDRAW_HANDLE_FEE, "5000");
		if (withdrawSetting == Constant.PaymentChannel.BB_PAY) {
			handleFeeStr = CacheManager.getDataMapByKey(DataDicKeyConstants.WITHDRAW_HANDLE_FEE,
					DataDicKeyConstants.BB_FEE);
		}
		request.setAttribute("handleFee", handleFeeStr);
		// 获取提现支持的银行
		request.setAttribute("supportBanks", paymentSupportBankService.querySupportDrawBanks(withdrawSetting));
		request.setAttribute("isNeedCode", isNeedCode);
		request.setAttribute("userverified", userverified);
		request.setAttribute("user", user);
		request.setAttribute("banks", banks);
		return ViewConstants.DrawMoney.DRAW_MAIN_VIEW;
	}

	private static Object lock = new Object();

	/**
	 * 取现
	 * 
	 * @param response
	 * @param request
	 * @return
	 * @date
	 * @author zhangjun
	 * @throws Exception
	 */

	@RequestMapping(value = "/doDrawmoney")
	@ResponseBody
	public JsonResult doDrawmoney(HttpServletResponse response, HttpServletRequest request) {
		synchronized (lock) {
			UserSessionBean userSessionBean = (UserSessionBean) request.getSession()
					.getAttribute(Constants.TZDR_USER_SESSION);
			String bankcard = request.getParameter("bankcard");
			String money = request.getParameter("money");
			String drawpwd = request.getParameter("drawpwd");
			String bankname = request.getParameter("bankname");
			String randcode = request.getParameter("code");
			JsonResult jsonResult = new JsonResult(false);
			jsonResult.setSuccess(true);

			// 校验是否支持该银行提现
			PaymentSupportBank paymentSupportBank = paymentSupportBankService.findByAbbreviation(bankname);
			if (ObjectUtil.equals(null, paymentSupportBank)) {
				jsonResult.setMessage("网银平台暂不支持此银行提现，请重新添加银行！");
				return jsonResult;
			}
			// 获取提现渠道设置参数
			int withdrawSetting = dataMapService.getWithDrawSetting();
			if (Constant.PaymentChannel.BB_PAY != withdrawSetting
					&& Constant.PaymentChannel.UM_PAY != withdrawSetting) {
				jsonResult.setMessage("系统维护中，请稍候再试！");
				return jsonResult;
			}
			if (Constant.PaymentChannel.BB_PAY == withdrawSetting
					&& Constants.PAYMENT_SUPPORT != paymentSupportBank.getSupportBbdraw()) {
				jsonResult.setMessage("网银平台暂不支持此银行提现，请重新添加银行！");
				return jsonResult;
			}
			if (Constant.PaymentChannel.UM_PAY == withdrawSetting
					&& Constants.PAYMENT_SUPPORT != paymentSupportBank.getSupportUmdraw()) {
				jsonResult.setMessage("网银平台暂不支持此银行提现，请重新添加银行！");
				return jsonResult;
			}
			// 币币支付最多不超过50000
			if (Constant.PaymentChannel.BB_PAY == withdrawSetting && Double.valueOf(money) >= 50000) {
				jsonResult.setMessage("因网银限制，单笔提现金额要小于50000元，每天可提现5笔。");
				return jsonResult;
			}

			// 判断是否有欠费记录
			DrawBlackList drawBlackList = drawBlackListService.getDrawBlackListByUid(userSessionBean.getId());
			if (null != drawBlackList) {
				jsonResult.setMessage("existArrearage");
				jsonResult.setObj(drawBlackList.getReason());
				return jsonResult;
			}

			/*
			 * 取消这个功能，可能会引起性能问题问题 //判断暴仓没有开始 20150417 林锋 try { List<String>
			 * listGroupId=drawMoneyService.findUserTradeByUid(userSessionBean.
			 * getId()); if (!CollectionUtils.isEmpty(listGroupId)) { String
			 * listGroupIdString=StringUtils.join(listGroupId, ",");
			 * jsonResult.setMessage("方案"+listGroupIdString+
			 * "净资产小于配资金额,请追加保证金后再提现."); return jsonResult; } } catch (Exception
			 * e) { logger.error("判断暴仓没有:"+e.getMessage());
			 * jsonResult.setMessage("目前提现人数过多,请稍候再试."); return jsonResult; }
			 * //判断暴仓没有完成
			 */
			// 先判断是否要输入验证码
			Object obj = request.getServletContext()
					.getAttribute(com.tzdr.web.constants.Constants.DRAW_FAIL_MAX_COUNT_SESSION_KEY);
			ConcurrentHashMap<String, DrawMoneyFailBean> failMap = (ConcurrentHashMap<String, DrawMoneyFailBean>) (obj == null
					? null : obj);
			WUser user = drawMoneyService.getUser(userSessionBean.getId());
			String loginname = user.getMobile();
			DrawMoneyFailBean failBean = failMap == null || failMap.isEmpty() || !failMap.containsKey(loginname) ? null
					: failMap.get(loginname);
			String sessionCode = (String) request.getSession()
					.getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
			if (failBean != null
					&& com.tzdr.web.constants.Constants.DRAW_FAIL_MAX_COUNT_SESSION < failBean.getDrawFailCount()
					&& (StringUtil.isBlank(randcode) || !randcode.equalsIgnoreCase(sessionCode))) {
				failBean.setDrawFailCount(failBean.getDrawFailCount() + 1);
				Map<Object, Object> data = new HashMap<Object, Object>();
				data.put("isNeedCode", false);
				if (com.tzdr.web.constants.Constants.LOGIN_FAIL_MAX_COUNT_SESSION < failBean.getDrawFailCount()) {
					data.put("isNeedCode", true);
				}
				request.getSession().setAttribute(Constants.DRAW_FAIL_MAX_COUNT_SESSION_KEY,
						failBean.getDrawFailCount()); // 添加或修改取现失败次数
				DrawMoneyFail.addDrawFailBean(failBean, request, response); // 修改取消失败次数
				jsonResult.setData(data);
				jsonResult.setMessage("codeError");
				return jsonResult;
			}
			Double avlbal = user.getAvlBal() == null ? 0 : user.getAvlBal();
			UserVerified userverified = user.getUserVerified();
			String moneypwd = securityInfoService.getBuildPwd(drawpwd, user);
			List<DrawList> list = drawMoneyService.findDrawCount(user);
			// 提现时间控制
			List<DataMap> drawDate = dataMapService.findByTypeKey(DataDicKeyConstants.DRAW_DATE);
			if (list.size() >= 5) {
				jsonResult.setMessage("当天提现次数不能超过5次");
				return jsonResult;
			}
			// 余额大于提现金额并且配置用户大于10元
			if (Double.valueOf(money) < 10) {
				jsonResult.setMessage("每次提现金额不能小于10元");
				return jsonResult;
			}
			// 提现时间控制
			if (drawDate != null && drawDate.size() > 0) {
				DataMap datamap = drawDate.get(0);
				String keydate = datamap.getValueKey();
				Date startdate = DateUtils.stringToDate(keydate, "yyyy-MM-dd HH:mm");
				if (new Date().getTime() <= startdate.getTime()) {
					jsonResult.setMessage("截止【" + keydate + "】为系统升级时间，暂停提现功能！给您带来的不便敬请谅解！");
					return jsonResult;
				}
			}

			DrawList drawList = null; // 提现插入实体
			if (avlbal >= Double.valueOf(money)) {// 余额大于取款金额
				// 获取设置的取款金额，如果取款金额大于设置的取款金额后台需要审核
				DrawMoneyData moneydata = drawMoneyDataService.getAduitMoneyByType("1");
				Double moneyval = moneydata.getMaxmoney();
				// Map<String,String> dmap=
				// dataMapService.getWithDrawAuditMoney();
				// String moneyval=ObjectUtil.equals(null, dmap)?
				// DataDicKeyConstants.DEFAULT_WITHDRAW_MONEY_VALUE_NAME:dmap.get(DataDicKeyConstants.WITHDRAWAL_AUDIT_MIN_MONEY);
				// String maxAuditMoney=ObjectUtil.equals(null, dmap)?
				// DataDicKeyConstants.DEFAULT_WITHDRAW_MONEY_VALUE_NAME:dmap.get(DataDicKeyConstants.WITHDRAWAL_AUDIT_MAX_MONEY);
				UserBank bank = this.drawMoneyService.findUsercardbycard(bankcard, user.getId());
				String accAddress=bank.getProvinceCity();//获取此银行卡的开户地址
				if (bank != null && moneypwd.equals(userverified.getDrawMoneyPwd())) {
					String ip = IpUtils.getIpAddr(request);
					String orderId = drawMoneyService.getRandomStr(20);
					boolean flag = true;
					try {
						// drawMoneyService.insertDraw(user,money,bankcard,bankname,ip,orderId,moneyval,maxAuditMoney);
						drawList = drawMoneyService.insertDraw(Constant.Source.TZDR, user, money, bankcard,
								paymentSupportBank, ip, orderId, withdrawSetting,accAddress);
						//// 提现插入实体后，发送邮件给工作人员初审
						// messagePromptService.sendMessage(PromptTypes.isTheTrial,
						// user.getMobile());
					} catch (Exception e1) {
						flag = false;
						logger.error("插入取款数据失败" + e1.getMessage());
						String dataDetail = "userInfo:id:" + user.getId() + "|mobile:" + user.getMobile() + "orderId:"
								+ orderId + "|ip:" + ip + "|异常：" + e1.getMessage();
						EmailExceptionHandler.getInstance().HandleExceptionWithData(e1, "插入提现数据失败",
								this.getClass().getName() + ":moreSuccess", dataDetail);
						jsonResult.setMessage("提现失败，请联系客服400-852-8008");
						return jsonResult;
					}
					if (flag) {
						// 记录插入成功后不用审核开始调用取款接口
						// 提现金额
						Double dmoney = Double.valueOf(money);
						if (moneyval > dmoney) {
							if (Constant.PaymentChannel.BB_PAY == withdrawSetting) {
								return bbDrawMoney(drawList, dmoney, paymentSupportBank, user, bankcard);
							}
							if (Constant.PaymentChannel.UM_PAY == withdrawSetting) {
								// 开始调用接口
								WithdrawPayExtendInfo extendInfo = new WithdrawPayExtendInfo();
								// 提现手续费
								String handleFeeStr = CacheManager
										.getDataMapByKey(DataDicKeyConstants.WITHDRAW_HANDLE_FEE, "5000");

								Double handleFee = 0.00;

								WithdrawPayInfo withdrawPayInfo = null;

								if (!StringUtil.isBlank(handleFeeStr)) {
									handleFee = Double.valueOf(handleFeeStr);
								}

								if (BigDecimalUtils.compareTo(dmoney, 5000) < 0) {
									// 扣除手续费
									dmoney = BigDecimalUtils.subRound(dmoney, handleFee);
									withdrawPayInfo = this.setWithdrawInfo(user, dmoney, orderId, bankcard,
											user.getUserVerified());
								} else {
									withdrawPayInfo = this.setWithdrawInfo(user, dmoney, orderId, bankcard,
											user.getUserVerified());
								}

								UserVerified uvf = user.getUserVerified();
								extendInfo.setCheckFlag("1");
								extendInfo.setIdentityCode(uvf.getIdcard());
								WithdrawPay draw = WithdrawPay.getInstance();
								JSONObject json = null;
								try {
									logger.info("开始调用取款接口,金额:" + dmoney + ", orderId:" + orderId + ", 账号:"
											+ user.getMobile());
									json = draw.getWithdrawResponse(withdrawPayInfo, extendInfo);
									logger.info("结束调用取款接口.");
								} catch (Exception e) {
									logger.error("调用取款接口失败" + e.getMessage());
									String dataDetail = "userInfo:id:" + user.getId() + "|mobile:" + user.getMobile()
											+ "orderId:" + orderId + "|ip:" + ip + "|异常：" + e.getMessage();
									EmailExceptionHandler.getInstance().HandleExceptionWithData(e, "调用取款接口失败",
											this.getClass().getName() + ":moreSuccess", dataDetail);
								}
								if (json != null) {
									String code = json.getString("retCode");
									if (!Constants.Draw.SUCCESS.equals(code)) {
										jsonResult.setMessage("提现失败，请联系客服 400-852-8008");
									} else {
										// 发送短信
										Map<String, String> map = new HashMap<String, String>();
										map.put("account", user.getMobile());
										map.put("money", money);
										new SMSSenderThread(user.getMobile(), "draw.money.template", map).start();
									}
								} else {
									jsonResult.setMessage("提现失败，请联系客服 400-852-8008");
								}
							}
						}
					} else {
						jsonResult.setMessage("提现失败，请联系客服 400-852-8008");
					}
				} else {
					if (bank == null) {
						jsonResult.setMessage("绑定的银行卡不存在");
					} else if (!moneypwd.equals(userverified.getDrawMoneyPwd())) {
						// 提现密码错误加入错误次数
						if (failBean == null) {
							failBean = new DrawMoneyFailBean();
							failBean.setUserName(loginname);
							failBean.setDrawFailCount(1);
							failBean.setValidDate(new Date());
						} else {
							failBean.setDrawFailCount(failBean.getDrawFailCount() + 1);
						}
						Map<Object, Object> data = new HashMap<Object, Object>();
						data.put("isNeedCode", false);
						if (obj != null && Constants.DRAW_FAIL_MAX_COUNT_SESSION < failBean.getDrawFailCount()) {
							data.put("isNeedCode", true);
						}
						request.getSession().setAttribute(Constants.DRAW_FAIL_MAX_COUNT_SESSION_KEY,
								failBean.getDrawFailCount()); // 添加或修改session登录失败次数
						DrawMoneyFail.addDrawFailBean(failBean, request, response); // 添加或修改application失败次数
						jsonResult.setData(data);
						jsonResult.setMessage("提现密码错误");
						return jsonResult;
					}
				}
			} else {
				jsonResult.setMessage("账户余额小于提现金额");
			}
			if (failBean != null) {
				DrawMoneyFail.removeDrawFailBean(failBean, request, response); // 删除application取现失败次数(包含失效数据)
			}
			request.getSession().removeAttribute(Constants.DRAW_FAIL_MAX_COUNT_SESSION_KEY); // 清空是否需要验证码取现失败次数
			request.getServletContext().removeAttribute(Constants.DRAW_FAIL_MAX_COUNT_SESSION_KEY);
			return jsonResult;
		}
	}

	/**
	 * 提现
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/doWithdrawals", method = RequestMethod.POST)
	public JsonResult doGoWithdrawals(HttpServletRequest request, HttpServletResponse response,
			@RequestParam String bankcard, @RequestParam String money, @RequestParam String drawpwd,
			@RequestParam String bankname, @RequestParam String randcode) {
		synchronized (lock) {
			PaymentSupportBank paymentSupportBank = paymentSupportBankService.findByAbbreviation(bankname);
			String bankSupport = validationBankSupport(paymentSupportBank);
			JsonResult jsonResult = new JsonResult();
			if (bankSupport.equals("true")) {
				UserSessionBean userSessionBean = (UserSessionBean) request.getSession()
						.getAttribute(Constants.TZDR_USER_SESSION);
				// 获取提现渠道设置参数
				int withdrawSetting = dataMapService.getWithDrawSetting();
				// 判断是否有欠费记录
				DrawBlackList drawBlackList = drawBlackListService.getDrawBlackListByUid(userSessionBean.getId());
				if (null != drawBlackList) {
					jsonResult.setMessage("existArrearage");
					jsonResult.setObj(drawBlackList.getReason());
					return jsonResult;
				}
				// 先判断是否要输入验证码
				Object obj = request.getServletContext()
						.getAttribute(com.tzdr.web.constants.Constants.DRAW_FAIL_MAX_COUNT_SESSION_KEY);
				ConcurrentHashMap<String, DrawMoneyFailBean> failMap = (ConcurrentHashMap<String, DrawMoneyFailBean>) (obj == null
						? null : obj);
				WUser user = drawMoneyService.getUser(userSessionBean.getId());
				String loginname = user.getMobile();
				DrawMoneyFailBean failBean = failMap == null || failMap.isEmpty() || !failMap.containsKey(loginname)
						? null : failMap.get(loginname);
				String sessionCode = (String) request.getSession()
						.getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
				if (failBean != null
						&& com.tzdr.web.constants.Constants.DRAW_FAIL_MAX_COUNT_SESSION < failBean.getDrawFailCount()
						&& (StringUtil.isBlank(randcode) || !randcode.equalsIgnoreCase(sessionCode))) {
					failBean.setDrawFailCount(failBean.getDrawFailCount() + 1);
					Map<Object, Object> data = new HashMap<Object, Object>();
					data.put("isNeedCode", false);
					if (com.tzdr.web.constants.Constants.LOGIN_FAIL_MAX_COUNT_SESSION < failBean.getDrawFailCount()) {
						data.put("isNeedCode", true);
					}
					request.getSession().setAttribute(Constants.DRAW_FAIL_MAX_COUNT_SESSION_KEY,
							failBean.getDrawFailCount()); // 添加或修改取现失败次数
					DrawMoneyFail.addDrawFailBean(failBean, request, response); // 修改取消失败次数
					jsonResult.setData(data);
					jsonResult.setMessage("codeError");
					return jsonResult;
				}
				Double avlbal = user.getAvlBal() == null ? 0 : user.getAvlBal();
				UserVerified userverified = user.getUserVerified();
				String moneypwd = securityInfoService.getBuildPwd(drawpwd, user);
				List<DrawList> list = drawMoneyService.findDrawCount(user);
				// 提现时间控制
				List<DataMap> drawDate = dataMapService.findByTypeKey(DataDicKeyConstants.DRAW_DATE);
				if (list.size() >= 5) {
					jsonResult.setMessage("当天提现次数不能超过5次");
					return jsonResult;
				}
				// 余额大于提现金额并且配置用户大于10元
				if (Double.valueOf(money) < 10) {
					jsonResult.setMessage("每次提现金额不能小于10元");
					return jsonResult;
				}
				// 提现时间控制
				if (drawDate != null && drawDate.size() > 0) {
					DataMap datamap = drawDate.get(0);
					String keydate = datamap.getValueKey();
					Date startdate = DateUtils.stringToDate(keydate, "yyyy-MM-dd HH:mm");
					if (new Date().getTime() <= startdate.getTime()) {
						jsonResult.setMessage("截止【" + keydate + "】为系统升级时间，暂停提现功能！给您带来的不便敬请谅解！");
						return jsonResult;
					}
				}
				if (avlbal >= Double.valueOf(money)) {// 余额大于取款金额
					UserBank bank = this.drawMoneyService.findUsercardbycard(bankcard, user.getId());
					if (bank != null && moneypwd.equals(userverified.getDrawMoneyPwd())) {
						String ip = IpUtils.getIpAddr(request);
						String orderId = drawMoneyService.getRandomStr(20);
						// 提现处理
						drawMoneyService.doGoWithdrawals(Constant.Source.TZDR, user, money, bankcard,
								paymentSupportBank, ip, orderId, withdrawSetting);
					} else {
						if (bank == null) {
							jsonResult.setMessage("绑定的银行卡不存在");
						} else if (!moneypwd.equals(userverified.getDrawMoneyPwd())) {
							// 提现密码错误加入错误次数
							if (failBean == null) {
								failBean = new DrawMoneyFailBean();
								failBean.setUserName(loginname);
								failBean.setDrawFailCount(1);
								failBean.setValidDate(new Date());
							} else {
								failBean.setDrawFailCount(failBean.getDrawFailCount() + 1);
							}
							Map<Object, Object> data = new HashMap<Object, Object>();
							data.put("isNeedCode", false);
							if (obj != null && Constants.DRAW_FAIL_MAX_COUNT_SESSION < failBean.getDrawFailCount()) {
								data.put("isNeedCode", true);
							}
							request.getSession().setAttribute(Constants.DRAW_FAIL_MAX_COUNT_SESSION_KEY,
									failBean.getDrawFailCount()); // 添加或修改session登录失败次数
							DrawMoneyFail.addDrawFailBean(failBean, request, response); // 添加或修改application失败次数
							jsonResult.setData(data);
							jsonResult.setMessage("提现密码错误");
							return jsonResult;
						}
					}
				} else {
					jsonResult.setMessage("账户余额小于提现金额");
				}
				if (failBean != null) {
					DrawMoneyFail.removeDrawFailBean(failBean, request, response); // 删除application取现失败次数(包含失效数据)
				}
				request.getSession().removeAttribute(Constants.DRAW_FAIL_MAX_COUNT_SESSION_KEY); // 清空是否需要验证码取现失败次数
				request.getServletContext().removeAttribute(Constants.DRAW_FAIL_MAX_COUNT_SESSION_KEY);
			} else {
				jsonResult.setMessage(bankSupport);
			}
			return null;
		}

	}

	/**
	 * 验证银行是否支持
	 * 
	 * @param bankName
	 * @return
	 */
	public String validationBankSupport(PaymentSupportBank paymentSupportBank) {
		String result = null;
		// 校验是否支持该银行提现
		if (ObjectUtil.equals(null, paymentSupportBank)) {
			result = "网银平台暂不支持此银行提现，请重新添加银行！";
		} else {
			result = "true";
		}
		return result;
	}

	/**
	 * 根据用户和取款实体生成取款参数对象
	 * 
	 * @param user
	 *            用户对象
	 * @param money
	 *            金额
	 * @param orderId
	 *            订单号
	 * @param cardno
	 *            银行卡号
	 * @param uvf
	 *            安全信息号
	 * @return
	 * @date 2015年3月26日
	 * @author zhangjun
	 */
	private WithdrawPayInfo setWithdrawInfo(WUser user, Double money, String orderId, String cardno, UserVerified uvf) {
		WithdrawPayInfo withdrawPayInfo = new WithdrawPayInfo();
		java.text.DecimalFormat df = new DecimalFormat("#");
		UserBank ubank = this.userBankService.findUsercardbycard(cardno, user.getId());
		withdrawPayInfo.setAmount(df.format(BigDecimalUtils.mul(money, 100.0)));
		withdrawPayInfo.setMerDate(DateUtils.dateTimeToString(new Date(), "yyyyMMdd"));
		withdrawPayInfo.setOrderId(orderId);
		withdrawPayInfo.setRecvAccount(cardno);
		withdrawPayInfo.setPurpose("取现" + money);
		withdrawPayInfo.setRecvUserName(uvf.getTname());
		withdrawPayInfo.setBankBrhname("上海信闳投资");
		withdrawPayInfo.setRecvGateId(ubank.getBank().toUpperCase());
		return withdrawPayInfo;
	}

	/**
	 * 保存银行卡
	 * 
	 * @param response
	 * @param request
	 * @return
	 * @date 2015年1月12日
	 * @author zhangjun
	 */
	@RequestMapping(value = "/savecard")
	@ResponseBody
	public JsonResult savecard(HttpServletResponse response, HttpServletRequest request) {
		UserSessionBean userSessionBean = (UserSessionBean) request.getSession()
				.getAttribute(Constants.TZDR_USER_SESSION);
		String bankname = request.getParameter("bankname");
		String card = request.getParameter("card");
		String agincard = request.getParameter("agincard");
		String prov = request.getParameter("prov");// 省
		String city = request.getParameter("city");// 市
		String address = request.getParameter("address");// 具体地址
		String provinceCity=StringUtil.join(prov,city);
		JsonResult jsonResult = new JsonResult(false);
	
		if (StringUtil.isNotBlank(card)) {
			if (card.equals(agincard)) {
				UserBank ubank = drawMoneyService.findUsercardbycard(card, userSessionBean.getId());
				WUser user = this.drawMoneyService.getUser(userSessionBean.getId());
				if (ubank == null) {
					String path = this.getBankpathbybankname(bankname);

					UserBank bank = drawMoneyService.saveCard(user, bankname, card, path,address,provinceCity);
					jsonResult.setSuccess(true);
					jsonResult.setObj(bank);
					jsonResult.setMessage(StringCodeUtils.buildBank(bank.getCard()));
					return jsonResult;
				} else {
					jsonResult.setMessage("银行卡存在");
				}
			} else {
				jsonResult.setMessage("两次卡号输入不一致");
			}
		} else {
			jsonResult.setMessage("请选择银行卡");
		}
		jsonResult.setSuccess(false);
		return jsonResult;
	}

	/**
	 * 删除银行卡
	 * 
	 * @param response
	 * @param request
	 * @return
	 * @date 2015年1月12日
	 * @author zhangjun
	 */
	@RequestMapping(value = "/delCard")
	@ResponseBody
	public JsonResult delCard(HttpServletResponse response, HttpServletRequest request) {
		String cardId = request.getParameter("id");
		UserSessionBean userSessionBean = (UserSessionBean) request.getSession()
				.getAttribute(Constants.TZDR_USER_SESSION);
		JsonResult jsonResult = new JsonResult(false);
		jsonResult.setSuccess(true);
		boolean flag = drawMoneyService.checkCard(cardId, userSessionBean.getId());
		if (flag) {
			jsonResult.setMessage("该银行卡正在提现处理中，不能删除");
			return jsonResult;
		}
		this.drawMoneyService.delCard(cardId);
		jsonResult.setMessage("删除成功");
		return jsonResult;
	}

	/**
	 * 银行卡设置为默认值
	 * 
	 * @param response
	 * @param request
	 * @return
	 * @date 2015年1月12日
	 * @author zhangjun
	 */
	@RequestMapping(value = "/setDefaulcard")
	@ResponseBody
	public JsonResult setDefaulcard(HttpServletResponse response, HttpServletRequest request) {
		String cardId = request.getParameter("id");
		UserSessionBean userSessionBean = (UserSessionBean) request.getSession()
				.getAttribute(Constants.TZDR_USER_SESSION);

		this.drawMoneyService.setDefaulcard(cardId, userSessionBean.getId());
		JsonResult jsonResult = new JsonResult(false);
		jsonResult.setSuccess(true);
		jsonResult.setMessage("设置成功");
		return jsonResult;
	}

	/**
	 * 提现记录
	 * 
	 * @param response
	 * @param request
	 * @return
	 * @date 2015年1月12日
	 * @author zhangjun
	 */
	@RequestMapping(value = "/drawHistory")
	@ResponseBody
	public PageInfo<DrawList> drawHistory(HttpServletResponse response, HttpServletRequest request) {
		UserSessionBean userSessionBean = (UserSessionBean) request.getSession()
				.getAttribute(Constants.TZDR_USER_SESSION);

		String pageIndex = request.getParameter("pageIndex");
		String perPage = request.getParameter("perPage");
		PageInfo<DrawList> pageInfo = this.drawMoneyService.findData(pageIndex, perPage, userSessionBean.getId());

		return pageInfo;
	}

	/**
	 * 根据银行卡获取银行图片路径
	 * 
	 * @param bankname
	 * @return
	 * @date 2015年1月12日
	 * @author zhangjun
	 */
	private String getBankpathbybankname(String bankname) {
		String path = "";
		if ("icbc".equals(bankname)) { // 中国工商银行
			path = "/images/banks/bank_01.jpg";
		} else if ("ccb".equals(bankname)) {// 建设银行
			path = "/images/banks/bank_04.jpg";
		} else if ("abc".equals(bankname)) {// 农业银行
			path = "/images/banks/bank_03.jpg";
		} else if ("cmb".equals(bankname)) {// 招商银行
			path = "/images/banks/bank_02.jpg";
		} else if ("boc".equals(bankname)) {// 中国银行
			path = "/images/banks/bank_10.jpg";
		} else if ("cmbc".equals(bankname)) {// 中国民生银行
			path = "/images/banks/bank_05.jpg";
		} else if ("spdb".equals(bankname)) {// 浦发银行
			path = "/images/banks/bank_06.jpg";
		} else if ("gdb".equals(bankname)) {// 广发银行
			path = "/images/banks/bank_07.jpg";
		} else if ("hxb".equals(bankname)) {// 华夏银行
			path = "/images/banks/bank_08.jpg";
		} else if ("psbc".equals(bankname)) {// 中国邮政储蓄银行
			path = "/images/banks/bank_09.jpg";
		} else if ("ceb".equals(bankname)) {// 光大银行
			path = "/images/banks/bank_11.jpg";
		} else if ("bea".equals(bankname)) {// 东亚银行
			path = "/images/banks/bank_12.jpg";
		} else if ("cib".equals(bankname)) {// 兴业银行
			path = "/images/banks/bank_13.jpg";
		} else if ("comm".equals(bankname)) {// 交通银行
			path = "/images/banks/bank_14.jpg";
		} else if ("citic".equals(bankname)) {// 中信银行
			path = "/images/banks/bank_15.jpg";
		} else if ("bja".equals(bankname)) {// 北京银行
			path = "/images/banks/bank_16.jpg";
		} else if ("shrcb".equals(bankname)) {// 上海农商银行
			path = "/images/banks/bank_18.jpg";
		} else if ("wzcb".equals(bankname)) {// 温州银行
			path = "/images/banks/bank_17.jpg";
		} else if ("shb".equals(bankname)) {// 上海银行
			path = "/images/banks/shanghai.jpg";
		} else if ("spab".equals(bankname)) {// 平安银行
			path = "/images/banks/pingan.jpg";
		}
		return path;
	}

	/**
	 * 判断是否有欠费记录
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/isHaveArrearage")
	@ResponseBody
	public JsonResult isHaveArrearage(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) {
		UserSessionBean userSessionBean = (UserSessionBean) request.getSession()
				.getAttribute(Constants.TZDR_USER_SESSION);
		JsonResult jsonResult = new JsonResult(true);
		Map<Object, Object> data = new HashMap<Object, Object>();
		// 判断用户是否欠费
		DrawBlackList drawBlackList = drawBlackListService.getDrawBlackListByUid(userSessionBean.getId());
		boolean isHaveArrearage = false;
		if (null != drawBlackList) {
			isHaveArrearage = true;
			data.put("reason", drawBlackList.getReason());
		}
		data.put("isHaveArrearage", isHaveArrearage);
		jsonResult.setData(data);
		return jsonResult;
	}

	@RequestMapping(value = "/cancelDraw")
	@ResponseBody
	public JsonResult isHaveArrearage(String id, ModelMap modelMap, HttpServletRequest request,
			HttpServletResponse response) {
		synchronized (lock) {
			UserSessionBean userSessionBean = (UserSessionBean) request.getSession()
					.getAttribute(Constants.TZDR_USER_SESSION);
			JsonResult jsonResult = new JsonResult(true);
			if (StringUtil.isBlank(id)) {
				jsonResult.setMessage("idIsNull");
				return jsonResult;
			}
			DrawList drawList = this.drawMoneyService.getDrawList(id, userSessionBean.getId());
			if (drawList == null) {
				jsonResult.setMessage("idIsError");
				return jsonResult;
			}

			Map<Object, Object> data = new HashMap<Object, Object>();
			short status = drawList.getStatus(); // 数据状态
			Integer isAudit = drawList.getIsAudit(); // 数据审核状态

			if (status == 3) { // 判断是否已经取消
				jsonResult.setMessage("dataAlreadyCancel");
				data.put("status", drawList.getStatus());
				data.put("statusValue", drawList.getStatusValue());
				jsonResult.setData(data);
				return jsonResult;
			} else if (isAudit != null && (isAudit == 1 || isAudit == 2)) { // 判断是否已经审核过
				jsonResult.setMessage("dataAlreadyDispose");
				data.put("status", drawList.getStatus());
				data.put("statusValue", drawList.getStatusValue());
				jsonResult.setData(data);
				return jsonResult;
			}

			this.drawMoneyService.updatDraw(drawList, "3");

			return jsonResult;
		}
	}

	/**
	 * 币币支付提现接口调用
	 * 
	 * @param dmoney
	 * @param paymentSupportBank
	 * @param user
	 * @param bankCard
	 * @return
	 */
	private JsonResult bbDrawMoney(DrawList drawList, Double dmoney, PaymentSupportBank paymentSupportBank, WUser user,
			String bankCard) {
		logger.info("PC端提现调用币币支付接口参数：金额[" + dmoney + "],卡号[" + bankCard + "]");
		// 提现手续费
		String handleFeeStr = CacheManager.getDataMapByKey(DataDicKeyConstants.WITHDRAW_HANDLE_FEE,
				DataDicKeyConstants.BB_FEE);

		Double handleFee = 0.00;

		if (!StringUtil.isBlank(handleFeeStr)) {
			handleFee = Double.valueOf(handleFeeStr);
		}

		if (BigDecimalUtils.compareTo(dmoney, 5000) < 0) {
			// 扣除手续费
			dmoney = BigDecimalUtils.subRound(dmoney, handleFee);
		}
		// 调用提现接口
		// 格式：联行号~|~银行卡号~|~开户人~|~结算金额~|~1：私人，2：公司
		JSONObject bbResult = BbUtil.withDrawMony(dmoney, paymentSupportBank.getBbpayContactNumber(),
				drawList.getCard(), user.getUserVerified().getTname(), drawList.getNo());
		if (Bibipay.HANDLE_SUCCESS_STATUS != bbResult.getIntValue("status")) {
			logger.info("币币支付调用取款接口失败，" + bbResult);
			EmailExceptionHandler.getInstance().HandleHintWithData("币币支付调用取款接口失败", "bbDrawMoney",
					bbResult.toJSONString());
			return new JsonResult("提现失败，请联系客服 400-852-8008");
		}

		// 更新币币订单号到提现记录中
		drawMoneyService.updatDrawBybbOrderId(drawList.getId(), bbResult.getString("order_number"));
		// 发送短信
		Map<String, String> map = new HashMap<String, String>();
		map.put("account", user.getMobile());
		map.put("money", dmoney.toString());
		new SMSSenderThread(user.getMobile(), "draw.money.template", map).start();
		return new JsonResult();
	}

	public static void main(String[] args) {

	}
}

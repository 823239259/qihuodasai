package com.tzdr.business.service.drawMoney.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jodd.util.ObjectUtil;
import jodd.util.StringUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.hundsun.t2sdk.interfaces.T2SDKException;
import com.tzdr.business.exception.UserTradeException;
import com.tzdr.business.service.api.hundsun.HundsunJres;
import com.tzdr.business.service.datamap.DataMapService;
import com.tzdr.business.service.drawMoney.DrawMoneyService;
import com.tzdr.business.service.drawMoney.UserBankService;
import com.tzdr.business.service.drawMoneyData.DrawMoneyDataService;
import com.tzdr.business.service.pay.UserFundService;
import com.tzdr.business.service.securityInfo.impl.SecurityInfoServiceImpl;
import com.tzdr.business.service.thread.SMSPgbSenderThread;
import com.tzdr.business.service.thread.SMSSenderThread;
import com.tzdr.business.service.wuser.WUserService;
import com.tzdr.common.api.hundsun.data.StockCurrent;
import com.tzdr.common.api.umpay.WithdrawPay;
import com.tzdr.common.api.umpay.data.WithdrawPayExtendInfo;
import com.tzdr.common.api.umpay.data.WithdrawPayInfo;
import com.tzdr.common.baseservice.BaseServiceImpl;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.exception.EmailExceptionHandler;
import com.tzdr.common.utils.BigDecimalUtils;
import com.tzdr.common.utils.DateUtils;
import com.tzdr.common.utils.Dates;
import com.tzdr.common.utils.StringCodeUtils;
import com.tzdr.domain.cache.CacheManager;
import com.tzdr.domain.cache.DataDicKeyConstants;
import com.tzdr.domain.constants.Constant;
import com.tzdr.domain.dao.withdrawal.WithdrawalDao;
import com.tzdr.domain.pgb.entity.PGBPaymentSupportBank;
import com.tzdr.domain.vo.UserTradeCmsVo;
import com.tzdr.domain.web.entity.DrawList;
import com.tzdr.domain.web.entity.DrawMoneyData;
import com.tzdr.domain.web.entity.PaymentSupportBank;
import com.tzdr.domain.web.entity.UserBank;
import com.tzdr.domain.web.entity.UserFund;
import com.tzdr.domain.web.entity.UserVerified;
import com.tzdr.domain.web.entity.WUser;

/**
 * 提现service
 * <P>
 * title:@PayServiceImpl.java
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
 * @date 2014年12月30日
 * @version 1.0
 */
@Service("drawMoneyService")
@Transactional
public class DrawMoneyServiceImpl extends BaseServiceImpl<DrawList, WithdrawalDao> implements DrawMoneyService {
	public static final Logger logger = LoggerFactory.getLogger(DrawMoneyServiceImpl.class);

	@Autowired
	private SecurityInfoServiceImpl securityInfoService;
	@Autowired
	private WUserService wUserService;
	@Autowired
	private UserFundService userFundService;
	@Autowired
	private UserBankService userBankService;
	@Autowired
	private DataMapService dataMapService;
	@Autowired
	private DrawMoneyDataService drawMoneyDataService;

	@Override
	public UserVerified findByUserId(String userId) {
		return securityInfoService.findByUserId(userId);
	}

	@Override
	public WUser getUser(String userId) {
		return this.wUserService.getUser(userId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tzdr.business.service.pay.PayService#findData(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public PageInfo<DrawList> findData(String pageIndex, String perPage, String userId) {
		PageInfo<DrawList> pageInfo = new PageInfo<DrawList>(Integer.valueOf(perPage), Integer.valueOf(pageIndex) + 1);
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Boolean> sortMap = new HashMap<String, Boolean>();
		map.put("EQ_user.id", userId);
		sortMap.put("addtime", false);
		pageInfo = this.query(pageInfo, map, sortMap);
		return pageInfo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tzdr.business.service.DrawMoney.DrawMoneyService#getUserBankbyUserId(
	 * java.lang.String)
	 */
	@Override
	public List<UserBank> findUserBankbyUserId(String userid) {
		return userBankService.findUserBankByuserId(userid);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tzdr.business.service.DrawMoney.DrawMoneyService#findUsercardbycard(
	 * java.lang.String)
	 */
	@Override
	public UserBank findUsercardbycard(String bankcard, String userid) {
		return userBankService.findUsercardbycard(bankcard, userid);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tzdr.business.service.DrawMoney.DrawMoneyService#insertDraw(com.tzdr.
	 * domain.web.entity.WUser, java.lang.String, java.lang.String,
	 * java.lang.String)
	 */

	@Override
	public void insertDraw(WUser user, String money, String bankcard, String bankname, String ip, String orderId,
			String moneyval, String maxAuditMoney) {
		DrawList drawList = new DrawList();
		drawList.setAddip(ip);
		drawList.setUser(user);
		drawList.setAddtime(new Date().getTime() / 1000);
		drawList.setMoney(Double.valueOf(money));
		drawList.setCard(bankcard);
		drawList.setNo(orderId);
		drawList.setFee(2);// 手续费2元
		drawList.setName(user.getUserVerified().getTname());
		drawList.setStatus((short) 21);// 提现处理中
		drawList.setBank(bankname);
		if (Double.valueOf(moneyval) <= Double.valueOf(money)) {
			// auditMoneyRang 审核金额范围
			String auditMoneyRang = moneyval + DataDicKeyConstants.WITHDRAWAL_AUDIT_MONEY_SPLIT + maxAuditMoney;
			drawList.setRemark("后台开始审核提现金额，审核金额范围：" + auditMoneyRang);
			// 如果提现金额，大于提现审核金额最大值 标记为线下划账
			if (Double.valueOf(money) > Double.valueOf(maxAuditMoney)) {
				drawList.setRemark("后台开始线下划账审核提现金额，审核金额范围：" + auditMoneyRang);
				drawList.setBelowLine(1);
			}
			// 发送短信
			Map<String, String> map = new HashMap<String, String>();
			map.put("account", user.getMobile());
			map.put("money", money);
			new SMSSenderThread(user.getMobile(), "draw.money.template", map).start();
		} else {
			drawList.setUpdateTime(Dates.getCurrentLongDate());
			drawList.setUpdateUser("system");
			drawList.setIsAudit(1);
			drawList.setRemark("调用提现接口数据插入");
		}
		getEntityDao().save(drawList);
		// 更新user的冻结金额
		Double frzBal = user.getFrzBal() == null ? 0 : user.getFrzBal();// 冻结金额
		Double acctBal = user.getAcctBal() == null ? 0 : user.getAcctBal();
		Double avlBal = user.getAvlBal() == null ? 0 : user.getAvlBal();
		double newfrzbal = BigDecimalUtils.add(frzBal, Double.valueOf(money));
		acctBal = BigDecimalUtils.sub(acctBal, Double.valueOf(money));
		avlBal = BigDecimalUtils.sub(avlBal, Double.valueOf(money));
		user.setFrzBal(newfrzbal);
		user.setAvlBal(avlBal);
		this.wUserService.updateUser(user);
		// 插入充值记录表
		UserFund fund = new UserFund();
		fund.setMoney(-drawList.getMoney());
		fund.setType(2);// 取现
		fund.setNo(orderId);
		fund.setUid(user.getId());
		fund.setFreeze(newfrzbal);// 冻结金额
		fund.setAmount(avlBal);
		fund.setPayStatus((short) 1);
		fund.setRemark(
				DateUtils.dateTimeToString(new Date(), "yyyy-MM-dd HH:mm:ss") + "提现" + drawList.getMoney() + "元");
		fund.setAddtime(new Date().getTime() / 1000);
		fund.setUptime(new Date().getTime() / 1000);
		userFundService.save(fund);
	}

	@Override
	public void insertDraw(WUser user, String money, String bankcard, String bankname, String ip, String orderId) {
		// 人工审核数据
		DrawMoneyData moneydata = drawMoneyDataService.getAduitMoneyByType("2", Double.valueOf(money));
		// 线下审核数据
		DrawMoneyData linedata = drawMoneyDataService.getAduitMoneyByType("3", Double.valueOf(money));
		double moneyval = 0;
		double maxAuditMoney = 0;
		String auditMoneyRang = "";// 审核金额范围
		if (moneydata != null) {
			moneyval = moneydata.getMinmoney();
			auditMoneyRang = moneyval + "--" + moneydata.getMaxmoney();
		} else if (linedata != null) {
			maxAuditMoney = linedata.getMinmoney();
			auditMoneyRang = maxAuditMoney + "--" + linedata.getMaxmoney();
		}

		// 提现手续费
		String handleFeeStr = CacheManager.getDataMapByKey(DataDicKeyConstants.WITHDRAW_HANDLE_FEE, "5000");
		Double handleFee = 0.00;
		// 提现金额
		Double dmoney = Double.valueOf(money);
		if (!StringUtil.isBlank(handleFeeStr)) {
			handleFee = Double.valueOf(handleFeeStr);
		}

		DrawList drawList = new DrawList();
		drawList.setAddip(ip);
		drawList.setUser(user);
		drawList.setAddtime(new Date().getTime() / 1000);
		drawList.setMoney(dmoney);
		drawList.setCard(bankcard);
		drawList.setNo(orderId);
		drawList.setFee(2);// 手续费2元
		drawList.setName(user.getUserVerified().getTname());
		drawList.setStatus((short) 21);// 提现处理中
		drawList.setBank(bankname);
		if (moneydata != null) {
			drawList.setAuditId(moneydata.getId());
			drawList.setRemark("后台开始审核提现金额，审核金额范围：" + auditMoneyRang);
			// 发送短信
			Map<String, String> map = new HashMap<String, String>();
			map.put("account", user.getMobile());
			map.put("money", money);
			new SMSSenderThread(user.getMobile(), "draw.money.template", map).start();
		} else if (linedata != null) {
			drawList.setBelowLine(1);
			drawList.setRemark("后台开始线下审核提现金额，审核金额范围：" + auditMoneyRang);
			// 发送短信
			Map<String, String> map = new HashMap<String, String>();
			map.put("account", user.getMobile());
			map.put("money", money);
			new SMSSenderThread(user.getMobile(), "draw.money.template", map).start();
		} else {
			drawList.setUpdateTime(Dates.getCurrentLongDate());
			drawList.setUpdateUser("system");
			drawList.setIsAudit(1);
			drawList.setRemark("调用提现接口数据插入");
		}
		getEntityDao().save(drawList);
		// 更新user的冻结金额
		Double frzBal = user.getFrzBal() == null ? 0 : user.getFrzBal();// 冻结金额
		Double acctBal = user.getAcctBal() == null ? 0 : user.getAcctBal();
		Double avlBal = user.getAvlBal() == null ? 0 : user.getAvlBal();
		double newfrzbal = BigDecimalUtils.add(frzBal, dmoney);
		acctBal = BigDecimalUtils.sub(acctBal, dmoney);
		avlBal = BigDecimalUtils.sub(avlBal, dmoney);
		user.setFrzBal(newfrzbal);
		user.setAvlBal(avlBal);
		this.wUserService.updateUser(user);
		// 插入充值记录表
		UserFund fund = new UserFund();
		fund.setMoney(-drawList.getMoney());
		fund.setType(2);// 取现
		fund.setNo(orderId);
		fund.setUid(user.getId());
		fund.setFreeze(newfrzbal);// 冻结金额
		fund.setAmount(avlBal);
		fund.setPayStatus((short) 1);
		// 根据是否收取手续费 记录相应备注
		if (BigDecimalUtils.compareTo(dmoney, 5000) < 0) {
			fund.setRemark(DateUtils.dateTimeToString(new Date(), "yyyy-MM-dd HH:mm:ss") + "提现" + drawList.getMoney()
					+ "元；提现手续费" + handleFee + "元；" + "实际到账金额" + BigDecimalUtils.subRound(drawList.getMoney(), handleFee)
					+ "元");
		} else {
			fund.setRemark(DateUtils.dateTimeToString(new Date(), "yyyy-MM-dd HH:mm:ss") + "提现" + drawList.getMoney()
					+ "元；提现手续费0元；实际到账金额" + drawList.getMoney() + "元");
		}
		fund.setAddtime(new Date().getTime() / 1000);
		fund.setUptime(new Date().getTime() / 1000);
		userFundService.save(fund);
	}

	/*
	 * 根据取款表id后台提款(non-Javadoc)
	 * 
	 * @see
	 * com.tzdr.business.service.drawMoney.DrawMoneyService#drawMoney(java.lang.
	 * String)
	 */
	@Override
	public JSONObject drawMoney(String drawId) {
		DrawList drawList = this.get(drawId);
		JSONObject json = new JSONObject();
		if (drawList != null) {
			WUser user = drawList.getUser();
			try {
				// 开始调用接口
				WithdrawPayExtendInfo extendInfo = new WithdrawPayExtendInfo();
				WithdrawPayInfo withdrawPayInfo = this.setWithdrawInfo(user, drawList, user.getUserVerified());
				UserVerified uvf = user.getUserVerified();
				extendInfo.setCheckFlag("1");
				extendInfo.setIdentityCode(uvf.getIdcard());
				WithdrawPay draw = WithdrawPay.getInstance();
				json = draw.getWithdrawResponse(withdrawPayInfo, extendInfo);
			} catch (Exception e) {
				logger.error("后台调用取款接口失败" + e.getMessage());
				String dataDetail = "userInfo:id:" + user.getId() + "|mobile:" + user.getMobile() + "|异常："
						+ e.getMessage();
				EmailExceptionHandler.getInstance().HandleExceptionWithData(e, "后台调用取款接口失败",
						this.getClass().getName() + ":moreSuccess", dataDetail);
				e.printStackTrace();
				json.put("retMsg", "系统执行错误！");
			}
		}
		return json;
	}

	/**
	 * 根据用户和取款实体生成取款参数对象
	 * 
	 * @param user
	 * @param drawList
	 * @return
	 * @date 2015年1月12日
	 * @author zhangjun
	 */
	private WithdrawPayInfo setWithdrawInfo(WUser user, DrawList drawList, UserVerified uvf) {
		// 提现手续费
		String handleFeeStr = CacheManager.getDataMapByKey(DataDicKeyConstants.WITHDRAW_HANDLE_FEE, "5000");
		Double handleFee = 0.00;
		// 提现金额
		Double money = drawList.getMoney();
		WithdrawPayInfo withdrawPayInfo = new WithdrawPayInfo();

		if (!StringUtil.isBlank(handleFeeStr)) {
			handleFee = Double.valueOf(handleFeeStr);
		}

		// 扣除手续费
		if (BigDecimalUtils.compareTo(money, 5000) < 0) {
			money = BigDecimalUtils.subRound(money, handleFee);
		}

		java.text.DecimalFormat df = new DecimalFormat("#");
		UserBank ubank = this.userBankService.findUsercardbycard(drawList.getCard(), user.getId());
		withdrawPayInfo.setAmount(df.format(BigDecimalUtils.mul(money, 100.0)));
		withdrawPayInfo.setMerDate(DateUtils.dateTimeToString(new Date(), "yyyyMMdd"));
		withdrawPayInfo.setOrderId(drawList.getNo());
		withdrawPayInfo.setRecvAccount(drawList.getCard());
		withdrawPayInfo.setPurpose("取现" + money);
		withdrawPayInfo.setRecvUserName(uvf.getTname());
		withdrawPayInfo.setBankBrhname("上海信闳投资");
		withdrawPayInfo.setRecvGateId(ubank.getBank().toUpperCase());
		return withdrawPayInfo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tzdr.business.service.DrawMoney.DrawMoneyService#saveCard(com.tzdr.
	 * domain.web.entity.WUser, java.lang.String, java.lang.String)
	 */
	@Override
	public UserBank saveCard(WUser user, String bankname, String card, String imgpath, String address,
			String provinceCity) {
		UserBank bank = new UserBank();
		bank.setUid(user.getId());
		bank.setAddtime(new Date().getTime() / 1000);
		bank.setCard(card);
		List<UserBank> list = this.findUserBankbyUserId(user.getId());
		if (list.size() < 1) {
			bank.setIsdefault((short) 1);
		}
		bank.setBank(bankname);
		bank.setBankimgpath(imgpath);

		bank.setProvinceCity(provinceCity);
		bank.setAccountAddress(address);
		return userBankService.saveCard(bank);
	}

	/*
	 * 提款修改(non-Javadoc)
	 * 
	 * @see
	 * com.tzdr.business.service.DrawMoney.DrawMoneyService#updatDraw(java.lang.
	 * String)
	 */
	@Override
	public void updatDraw(String orderId, String aremark, String tradeNo, String tradestatus, String okdate) {
		DrawList drawList = this.getEntityDao().findByNo(orderId);
		if (drawList != null) {
			logger.info("取现表状态-----------" + drawList.getStatus());
			// 开始调用接口
			if (drawList.getStatus() != 31 && drawList.getStatus() != 4) {// 如果没有记录提现成功或者失败
				logger.info("取现返回状态-----------" + tradestatus);
				WUser wuser = drawList.getUser();
				if ("4".equals(tradestatus)) {
					drawList.setTradeNo(tradeNo);
					double frzbal = wuser.getFrzBal();
					double frz = BigDecimalUtils.sub(frzbal, drawList.getMoney());
					drawList.setRemark(DateUtils.dateTimeToString(new Date(), "yyyy-MM-dd HH:mm:ss") + "成功提现"
							+ drawList.getMoney() + "元");
					wuser.setFrzBal(frz);// 冻结金额减少
					logger.info("修改冻结金额----" + frz);
					this.wUserService.updateUser(wuser);
					drawList.setStatus((short) 31);

					Calendar cal = Calendar.getInstance();
					String hh = String.valueOf(cal.get(Calendar.HOUR_OF_DAY));
					String mm = cal.get(Calendar.MINUTE) < 10 ? "0" + String.valueOf(cal.get(Calendar.MINUTE))
							: String.valueOf(cal.get(Calendar.MINUTE));
					String ss = cal.get(Calendar.SECOND) < 10 ? "0" + String.valueOf(cal.get(Calendar.SECOND))
							: String.valueOf(cal.get(Calendar.SECOND));
					logger.info("取现成功返回时间----" + okdate + hh + mm + ss);
					Date date = new Date();// DateUtils.stringToDate(okdate+hh+mm+ss,"yyyyMMddHHmmss");
					long time = date.getTime();
					drawList.setOktime(time / 1000);
					logger.info("修改取现金额成功时间----" + time);
					this.getEntityDao().update(drawList);
					// 修改充值记录表
					UserFund fund = userFundService.findUserfundByNo(orderId, wuser.getId());
					fund.setFreeze(BigDecimalUtils.sub(fund.getFreeze(), drawList.getMoney()));
					fund.setTrxId(tradeNo);
					// fund.setUptime(new Date().getTime()/1000);
					fund.setPayStatus((short) 1);// 已支付
					userFundService.update(fund);
				} else if ("3".equals(tradestatus)) {
					drawList.setTradeNo(tradeNo);
					drawList.setRemark("提现失败,返回状态" + tradestatus);
					double frzbal = wuser.getFrzBal();
					double funds = wuser.getFund();
					double frz = BigDecimalUtils.sub(frzbal, drawList.getMoney());
					double userfunds = BigDecimalUtils.sub(funds, drawList.getMoney());
					wuser.setFund(userfunds);// 总资金加回去
					wuser.setFrzBal(frz);// 冻结金额减少
					// wuser.setAvlBal(BigDecimalUtils.add(wuser.getAvlBal(),drawList.getMoney()));//账号余额加入
					// wuser.setAcctBal(BigDecimalUtils.add(wuser.getAcctBal(),drawList.getMoney()));
					this.wUserService.updateUser(wuser);
					drawList.setStatus((short) 4);
					Calendar cal = Calendar.getInstance();
					String hh = String.valueOf(cal.get(Calendar.HOUR_OF_DAY));
					String mm = cal.get(Calendar.MINUTE) < 10 ? "0" + String.valueOf(cal.get(Calendar.MINUTE))
							: String.valueOf(cal.get(Calendar.MINUTE));
					String ss = cal.get(Calendar.SECOND) < 10 ? "0" + String.valueOf(cal.get(Calendar.SECOND))
							: String.valueOf(cal.get(Calendar.SECOND));
					Date date = new Date();// DateUtils.stringToDate(okdate+hh+mm+ss,"yyyyMMddHHmmss");
					long time = date.getTime();
					drawList.setOktime(time / 1000);
					this.getEntityDao().update(drawList);

					// 加入一条取现失败的记录表
					UserFund fund = new UserFund();
					fund.setMoney(drawList.getMoney());
					fund.setType(23);// 取现撤回
					fund.setNo(orderId);
					fund.setUid(wuser.getId());
					fund.setFreeze(frz);// 冻结金额
					fund.setAmount(wuser.getAvlBal());// 余额
					fund.setPayStatus((short) 1);
					fund.setRemark(DateUtils.dateTimeToString(new Date(), "yyyy-MM-dd HH:mm:ss") + "提现撤回"
							+ drawList.getMoney() + "元。" + aremark);
					fund.setAddtime(new Date().getTime() / 1000);
					fund.setUptime(new Date().getTime() / 1000);
					userFundService.arrearsProcess(fund);
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.tzdr.business.service.DrawMoney.DrawMoneyService#delCard(java.lang.
	 * String)
	 */
	@Override
	public void delCard(String cardId) {
		this.userBankService.delCard(cardId);

	}

	/**
	 * 生成随机字符串
	 * 
	 * @return
	 * @date 2015年1月4日
	 * @author zhangjun
	 */
	public String getRandomStr(int length) {
		String orderId = StringCodeUtils.getRandomStr(length);
		DrawList drawList = getEntityDao().findByNo(orderId);
		if (drawList != null) {
			return getRandomStr(length);
		}
		return orderId;
	}

	@Override
	public void setDefaulcard(String cardId, String userId) {
		this.userBankService.setDefaulcard(cardId, userId);

	}

	/*
	 * 查询当天取款次数(non-Javadoc)
	 * 
	 * @see
	 * com.tzdr.business.service.DrawMoney.DrawMoneyService#findDrawCount(com.
	 * tzdr.domain.web.entity.WUser)
	 */
	@Override
	public List<DrawList> findDrawCount(WUser user) {
		String userid = user.getId();
		return this.getEntityDao().findDrawCount(userid, Dates.getCurrentLongDay(), Dates.getNextLongDay());
	}

	@Override
	public boolean checkCard(String cardId, String userId) {
		String card = userBankService.findCard(cardId);
		List<DrawList> list = getEntityDao().findDrawBycard(userId, card, (short) 21);
		if (list.size() > 0) {
			return true;
		}
		return false;
	}

	@Override
	public List<String> findUserTradeByUid(String uid) {
		String sql = "select uid,parent_account_no as parentAccountNo,asset_id as assetId,combine_id as combineId,group_id as groupId,SUM(money) as totalLending,account from w_user_trade w where w.uid=? and w.status=1 GROUP BY w.group_id";
		List<Object> params = Lists.newArrayList();
		params.add(uid);
		List<UserTradeCmsVo> list = nativeQuery(sql, params, UserTradeCmsVo.class);
		List<String> listNo = new ArrayList<String>();
		try {
			HundsunJres hundsunJres = HundsunJres.getInstance();
			String userToken = hundsunJres.Login();
			if (StringUtil.isBlank(userToken)) {
				throw new UserTradeException("system.busy", null);
			}

			for (UserTradeCmsVo userTradeCmsVo : list) {
				List<StockCurrent> stockCurrentList = hundsunJres.funcAmStockCurrentQry(userToken,
						userTradeCmsVo.getParentAccountNo(), userTradeCmsVo.getCombineId());
				if (CollectionUtils.isEmpty(stockCurrentList)) {
					throw new UserTradeException("system.busy", null);
				}
				StockCurrent stockCurrent = stockCurrentList.get(0);
				double assetTotalValue = BigDecimalUtils.addRound(stockCurrent.getCurrentCash(),
						stockCurrent.getMarketValue());
				if (userTradeCmsVo.getTotalLending() > assetTotalValue) {
					listNo.add(userTradeCmsVo.getGroupId());
				}
			}

		} catch (T2SDKException e) {
			logger.error(this.getClass().getName() + e.getMessage());
			throw new UserTradeException("system.busy", null);
		}
		return listNo;
	}

	@Override
	public DrawList getDrawList(String id, String userId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("EQ_id", id);
		map.put("EQ_user.id", userId);
		List<DrawList> drawListData = this.query(map, null);
		DrawList drawList = null;
		if (drawListData != null && !drawListData.isEmpty()) {
			drawList = drawListData.get(0);
		}
		return drawList;
	}

	@Override
	public void updatDraw(DrawList drawList, String tradestatus) {
		WUser wuser = drawList.getUser(); // 获取用户信息
		if ("3".equals(tradestatus)) {
			drawList.setRemark("取消提现,返回状态" + tradestatus);
			double frzbal = wuser.getFrzBal();
			double funds = wuser.getFund();
			double frz = BigDecimalUtils.sub(frzbal, drawList.getMoney());
			double userfunds = BigDecimalUtils.sub(funds, drawList.getMoney());
			wuser.setFund(userfunds);// 总资金加回去
			wuser.setFrzBal(frz);// 冻结金额减少
			wuser.setAvlBal(BigDecimalUtils.add(wuser.getAvlBal(), drawList.getMoney()));// 账号余额加入
			this.wUserService.updateUser(wuser);
			drawList.setStatus((short) 3);
			Date date = new Date();
			long time = date.getTime();
			drawList.setOktime(time / 1000);
			this.getEntityDao().update(drawList);

			// 加入一条取现失败的记录表
			UserFund fund = new UserFund();
			fund.setMoney(drawList.getMoney());
			fund.setType(23);// 取现撤回
			fund.setUid(wuser.getId());
			fund.setFreeze(frz);// 冻结金额
			fund.setAmount(wuser.getAvlBal());// 余额
			fund.setPayStatus((short) 1);
			fund.setRemark(DateUtils.dateTimeToString(new Date(), "yyyy-MM-dd HH:mm:ss") + "提现撤回" + drawList.getMoney()
					+ "元。");
			fund.setAddtime(new Date().getTime() / 1000);
			fund.setUptime(new Date().getTime() / 1000);
			userFundService.save(fund);
		}
	}

	@Override
	public JSONObject bbDrawMoney(String drawId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DrawList insertDraw(int source, WUser user, String money, String bankcard,
			PaymentSupportBank paymentSupportBank, String ip, String orderId, int withdrawSetting) {
		// 人工审核数据
		DrawMoneyData moneydata = drawMoneyDataService.getAduitMoneyByType("2", Double.valueOf(money));
		// 线下审核数据
		DrawMoneyData linedata = drawMoneyDataService.getAduitMoneyByType("3", Double.valueOf(money));
		double moneyval = 0;
		double maxAuditMoney = 0;
		String auditMoneyRang = "";// 审核金额范围
		if (moneydata != null) {
			moneyval = moneydata.getMinmoney();
			auditMoneyRang = moneyval + "--" + moneydata.getMaxmoney();
		} else if (linedata != null) {
			maxAuditMoney = linedata.getMinmoney();
			auditMoneyRang = maxAuditMoney + "--" + linedata.getMaxmoney();
		}

		// 提现手续费
		String handleFeeStr = CacheManager.getDataMapByKey(DataDicKeyConstants.WITHDRAW_HANDLE_FEE, "5000");
		// 币币支付手续费另算
		if (withdrawSetting == Constant.PaymentChannel.BB_PAY) {
			handleFeeStr = CacheManager.getDataMapByKey(DataDicKeyConstants.WITHDRAW_HANDLE_FEE,
					DataDicKeyConstants.BB_FEE);
		}
		// 易支付支付手续费另算
		if (withdrawSetting == Constant.PaymentChannel.EASE_PAY) {
			handleFeeStr = CacheManager.getDataMapByKey(DataDicKeyConstants.WITHDRAW_HANDLE_FEE,
					DataDicKeyConstants.PAYEASE_FEE);
		}
		Double handleFee = 0.00;
		// 提现金额
		Double dmoney = Double.valueOf(money);
		if (!StringUtil.isBlank(handleFeeStr)) {
			handleFee = Double.valueOf(handleFeeStr);
		}

		DrawList drawList = new DrawList();
		// 新增支付渠道
		drawList.setSource(source);
		drawList.setPaymentChannel(withdrawSetting);
		drawList.setAddip(ip);
		drawList.setUser(user);
		drawList.setAddtime(new Date().getTime() / 1000);
		drawList.setMoney(dmoney);
		drawList.setCard(bankcard);
		drawList.setNo(orderId);
		drawList.setFee(handleFee);// 手续费2元
		drawList.setName(user.getUserVerified().getTname());
		drawList.setStatus((short) 21);// 提现处理中
		drawList.setBank(paymentSupportBank.getBankName());
		drawList.setSubbank(paymentSupportBank.getAbbreviation());
		if (moneydata != null) {
			drawList.setAuditId(moneydata.getId());
			drawList.setRemark("后台开始审核提现金额，审核金额范围：" + auditMoneyRang);
			// 发送短信
			Map<String, String> map = new HashMap<String, String>();
			map.put("account", user.getMobile());
			map.put("money", money);
			new SMSSenderThread(user.getMobile(), "draw.money.template", map).start();
		} else if (linedata != null) {
			drawList.setBelowLine(1);
			drawList.setRemark("后台开始线下审核提现金额，审核金额范围：" + auditMoneyRang);
			// 发送短信
			Map<String, String> map = new HashMap<String, String>();
			map.put("account", user.getMobile());
			map.put("money", money);
			new SMSSenderThread(user.getMobile(), "draw.money.template", map).start();
		} else {
			drawList.setUpdateTime(Dates.getCurrentLongDate());
			drawList.setUpdateUser("system");
			drawList.setIsAudit(1);
			drawList.setRemark("调用提现接口数据插入");
		}
		getEntityDao().save(drawList);
		// 更新user的冻结金额
		Double frzBal = user.getFrzBal() == null ? 0 : user.getFrzBal();// 冻结金额
		Double acctBal = user.getAcctBal() == null ? 0 : user.getAcctBal();
		Double avlBal = user.getAvlBal() == null ? 0 : user.getAvlBal();
		double newfrzbal = BigDecimalUtils.add(frzBal, dmoney);
		acctBal = BigDecimalUtils.sub(acctBal, dmoney);
		avlBal = BigDecimalUtils.sub(avlBal, dmoney);
		user.setFrzBal(newfrzbal);
		user.setAvlBal(avlBal);
		this.wUserService.updateUser(user);
		// 插入充值记录表
		UserFund fund = new UserFund();
		fund.setMoney(-drawList.getMoney());
		fund.setType(2);// 取现
		fund.setNo(orderId);
		fund.setUid(user.getId());
		fund.setFreeze(newfrzbal);// 冻结金额
		fund.setAmount(avlBal);
		fund.setPayStatus((short) 1);
		// 根据是否收取手续费 记录相应备注
		if (BigDecimalUtils.compareTo(dmoney, 5000) < 0) {
			fund.setRemark(DateUtils.dateTimeToString(new Date(), "yyyy-MM-dd HH:mm:ss") + "提现" + drawList.getMoney()
					+ "元；提现手续费" + handleFee + "元；" + "实际到账金额" + BigDecimalUtils.subRound(drawList.getMoney(), handleFee)
					+ "元");
		} else {
			fund.setRemark(DateUtils.dateTimeToString(new Date(), "yyyy-MM-dd HH:mm:ss") + "提现" + drawList.getMoney()
					+ "元；提现手续费0元；实际到账金额" + drawList.getMoney() + "元");
		}
		fund.setAddtime(new Date().getTime() / 1000);
		fund.setUptime(new Date().getTime() / 1000);
		userFundService.save(fund);

		return drawList;
	}

	@Override
	public void updatDrawBybbOrderId(String id, String bbOrderId) {
		DrawList drawList = this.getEntityDao().get(id);
		if (ObjectUtil.equals(null, drawList)) {
			return;
		}
		drawList.setTradeNo(bbOrderId);
		super.update(drawList);
	}

	@Override
	public DrawList insertDraw(int source, WUser user, String money, String bankcard,
			PGBPaymentSupportBank paymentSupportBank, String ip, String orderId, int withdrawSetting) {
		// 人工审核数据
		DrawMoneyData moneydata = drawMoneyDataService.getAduitMoneyByType("2", Double.valueOf(money));
		// 线下审核数据
		DrawMoneyData linedata = drawMoneyDataService.getAduitMoneyByType("3", Double.valueOf(money));
		double moneyval = 0;
		double maxAuditMoney = 0;
		String auditMoneyRang = "";// 审核金额范围
		if (moneydata != null) {
			moneyval = moneydata.getMinmoney();
			auditMoneyRang = moneyval + "--" + moneydata.getMaxmoney();
		} else if (linedata != null) {
			maxAuditMoney = linedata.getMinmoney();
			auditMoneyRang = maxAuditMoney + "--" + linedata.getMaxmoney();
		}

		// 提现手续费
		String handleFeeStr = CacheManager.getDataMapByKey(DataDicKeyConstants.WITHDRAW_HANDLE_FEE, "5000");
		// 币币支付手续费另算
		if (withdrawSetting == Constant.PaymentChannel.BB_PAY) {
			handleFeeStr = CacheManager.getDataMapByKey(DataDicKeyConstants.WITHDRAW_HANDLE_FEE,
					DataDicKeyConstants.BB_FEE);
		}
		// 易支付手续费另算
		if (withdrawSetting == Constant.PaymentChannel.EASE_PAY) {
			handleFeeStr = CacheManager.getDataMapByKey(DataDicKeyConstants.WITHDRAW_HANDLE_FEE,
					DataDicKeyConstants.PAYEASE_FEE);
		}
		Double handleFee = 0.00;
		// 提现金额
		Double dmoney = Double.valueOf(money);
		if (!StringUtil.isBlank(handleFeeStr)) {
			handleFee = Double.valueOf(handleFeeStr);
		}

		DrawList drawList = new DrawList();
		// 新增支付渠道
		drawList.setSource(source);
		drawList.setPaymentChannel(withdrawSetting);
		drawList.setAddip(ip);
		drawList.setUser(user);
		drawList.setAddtime(new Date().getTime() / 1000);
		drawList.setMoney(dmoney);
		drawList.setCard(bankcard);
		drawList.setNo(orderId);
		drawList.setFee(handleFee);// 手续费2元
		drawList.setName(user.getUserVerified().getTname());
		drawList.setStatus((short) 21);// 提现处理中
		drawList.setBank(paymentSupportBank.getBankName());
		drawList.setSubbank(paymentSupportBank.getAbbreviation());
		if (moneydata != null) {
			drawList.setAuditId(moneydata.getId());
			drawList.setRemark("后台开始审核提现金额，审核金额范围：" + auditMoneyRang);
			// 发送短信
			Map<String, String> map = new HashMap<String, String>();
			map.put("account", user.getMobile());
			map.put("money", money);
			new SMSPgbSenderThread(user.getMobile(), "draw.money.template", map).start();
		} else if (linedata != null) {
			drawList.setBelowLine(1);
			drawList.setRemark("后台开始线下审核提现金额，审核金额范围：" + auditMoneyRang);
			// 发送短信
			Map<String, String> map = new HashMap<String, String>();
			map.put("account", user.getMobile());
			map.put("money", money);
			new SMSPgbSenderThread(user.getMobile(), "draw.money.template", map).start();
		} else {
			drawList.setUpdateTime(Dates.getCurrentLongDate());
			drawList.setUpdateUser("system");
			drawList.setIsAudit(1);
			drawList.setRemark("调用提现接口数据插入");
		}
		getEntityDao().save(drawList);
		// 更新user的冻结金额
		Double frzBal = user.getFrzBal() == null ? 0 : user.getFrzBal();// 冻结金额
		Double acctBal = user.getAcctBal() == null ? 0 : user.getAcctBal();
		Double avlBal = user.getAvlBal() == null ? 0 : user.getAvlBal();
		double newfrzbal = BigDecimalUtils.add(frzBal, dmoney);
		acctBal = BigDecimalUtils.sub(acctBal, dmoney);
		avlBal = BigDecimalUtils.sub(avlBal, dmoney);
		user.setFrzBal(newfrzbal);
		user.setAvlBal(avlBal);
		this.wUserService.updateUser(user);
		// 插入充值记录表
		UserFund fund = new UserFund();
		fund.setMoney(-drawList.getMoney());
		fund.setType(2);// 取现
		fund.setNo(orderId);
		fund.setUid(user.getId());
		fund.setFreeze(newfrzbal);// 冻结金额
		fund.setAmount(avlBal);
		fund.setPayStatus((short) 1);
		// 根据是否收取手续费 记录相应备注
		if (BigDecimalUtils.compareTo(dmoney, 5000) < 0) {
			fund.setRemark(DateUtils.dateTimeToString(new Date(), "yyyy-MM-dd HH:mm:ss") + "提现" + drawList.getMoney()
					+ "元；提现手续费" + handleFee + "元；" + "实际到账金额" + BigDecimalUtils.subRound(drawList.getMoney(), handleFee)
					+ "元");
		} else {
			fund.setRemark(DateUtils.dateTimeToString(new Date(), "yyyy-MM-dd HH:mm:ss") + "提现" + drawList.getMoney()
					+ "元；提现手续费0元；实际到账金额" + drawList.getMoney() + "元");
		}
		fund.setAddtime(new Date().getTime() / 1000);
		fund.setUptime(new Date().getTime() / 1000);
		userFundService.save(fund);

		return drawList;
	}

	@Override
	public void updatDrawPayeaseInfo(String id, String vmid, String secret) {
		DrawList drawList = this.getEntityDao().get(id);
		if (ObjectUtil.equals(null, drawList)) {
			return;
		}
		drawList.setNBank(vmid);
		drawList.setNArea(secret);
		super.update(drawList);
	}

	@Override
	public boolean doGoWithdrawals(int source, WUser user, String money, String bankcard,
			PaymentSupportBank paymentSupportBank, String ip, String orderId, int withdrawSetting) {
		// 人工审核数据
		DrawMoneyData moneydata = drawMoneyDataService.getAduitMoneyByType("2", Double.valueOf(money));
		// 线下审核数据
		DrawMoneyData linedata = drawMoneyDataService.getAduitMoneyByType("3", Double.valueOf(money));
		double moneyval = 0;
		double maxAuditMoney = 0;
		String auditMoneyRang = "";// 审核金额范围
		if (moneydata != null) {
			moneyval = moneydata.getMinmoney();
			auditMoneyRang = moneyval + "--" + moneydata.getMaxmoney();
		} else if (linedata != null) {
			maxAuditMoney = linedata.getMinmoney();
			auditMoneyRang = maxAuditMoney + "--" + linedata.getMaxmoney();
		}
		// 提现手续费
		String handleFeeStr = CacheManager.getDataMapByKey(DataDicKeyConstants.WITHDRAW_HANDLE_FEE, "5000");
		Double handleFee = 0.00;
		// 提现金额
		Double dmoney = Double.valueOf(money);
		if (!StringUtil.isBlank(handleFeeStr)) {
			handleFee = Double.valueOf(handleFeeStr);
		}
		DrawList drawList = new DrawList();
		// 新增支付渠道
		drawList.setSource(source);
		drawList.setPaymentChannel(withdrawSetting);
		drawList.setAddip(ip);
		drawList.setUser(user);
		drawList.setAddtime(new Date().getTime() / 1000);
		drawList.setMoney(dmoney);
		drawList.setCard(bankcard);
		drawList.setNo(orderId);
		drawList.setFee(handleFee);// 手续费2元
		drawList.setName(user.getUserVerified().getTname());
		drawList.setStatus((short) 21);// 提现处理中
		drawList.setBank(paymentSupportBank.getBankName());
		drawList.setSubbank(paymentSupportBank.getAbbreviation());
		if (moneydata != null) {
			drawList.setAuditId(moneydata.getId());
			drawList.setRemark("后台开始审核提现金额，审核金额范围：" + auditMoneyRang);
			// 发送短信
			Map<String, String> map = new HashMap<String, String>();
			map.put("account", user.getMobile());
			map.put("money", money);
			new SMSSenderThread(user.getMobile(), "draw.money.template", map).start();
		} else if (linedata != null) {
			drawList.setBelowLine(1);
			drawList.setRemark("后台开始线下审核提现金额，审核金额范围：" + auditMoneyRang);
			// 发送短信
			Map<String, String> map = new HashMap<String, String>();
			map.put("account", user.getMobile());
			map.put("money", money);
			new SMSSenderThread(user.getMobile(), "draw.money.template", map).start();
		} else {
			drawList.setUpdateTime(Dates.getCurrentLongDate());
			drawList.setUpdateUser("system");
			drawList.setIsAudit(1);
			drawList.setRemark("调用提现接口数据插入");
		}
		getEntityDao().save(drawList);
		// 更新user的冻结金额
		Double frzBal = user.getFrzBal() == null ? 0 : user.getFrzBal();// 冻结金额
		Double acctBal = user.getAcctBal() == null ? 0 : user.getAcctBal();
		Double avlBal = user.getAvlBal() == null ? 0 : user.getAvlBal();
		double newfrzbal = BigDecimalUtils.add(frzBal, dmoney);
		acctBal = BigDecimalUtils.sub(acctBal, dmoney);
		avlBal = BigDecimalUtils.sub(avlBal, dmoney);
		user.setFrzBal(newfrzbal);
		user.setAvlBal(avlBal);
		this.wUserService.updateUser(user);
		// 插入充值记录表
		UserFund fund = new UserFund();
		fund.setMoney(-drawList.getMoney());
		fund.setType(2);// 取现
		fund.setNo(orderId);
		fund.setUid(user.getId());
		fund.setFreeze(newfrzbal);// 冻结金额
		fund.setAmount(avlBal);
		fund.setPayStatus((short) 1);
		// 根据是否收取手续费 记录相应备注
		if (BigDecimalUtils.compareTo(dmoney, 5000) < 0) {
			fund.setRemark(DateUtils.dateTimeToString(new Date(), "yyyy-MM-dd HH:mm:ss") + "提现" + drawList.getMoney()
					+ "元；提现手续费" + handleFee + "元；" + "实际到账金额" + BigDecimalUtils.subRound(drawList.getMoney(), handleFee)
					+ "元");
		} else {
			fund.setRemark(DateUtils.dateTimeToString(new Date(), "yyyy-MM-dd HH:mm:ss") + "提现" + drawList.getMoney()
					+ "元；提现手续费0元；实际到账金额" + drawList.getMoney() + "元");
		}
		fund.setAddtime(new Date().getTime() / 1000);
		fund.setUptime(new Date().getTime() / 1000);
		userFundService.save(fund);
		// 获取设置的取款金额，如果取款金额大于设置的取款金额后台需要审核
		DrawMoneyData drawMaxMoney = drawMoneyDataService.getAduitMoneyByType("1");
		if (drawMaxMoney != null) {
			Double maxMoney = drawMaxMoney.getMaxmoney();
			if (maxMoney > dmoney) {
				// TODO 开始提现逻辑
			}
		}
		return false;
	}
}

package com.tzdr.business.service.future.impl;

import java.math.BigDecimal;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



import com.tzdr.business.service.future.FaccountInfoService;
import com.tzdr.business.service.future.FcapitalInfoService;
import com.tzdr.business.service.future.FuserTradeService;
import com.tzdr.business.service.future.FutureAccountService;
import com.tzdr.business.service.pay.UserFundService;
import com.tzdr.business.service.wuser.WUserService;
import com.tzdr.common.utils.DateUtils;
import com.tzdr.domain.vo.future.FutureAccountVo;
import com.tzdr.domain.web.entity.UserFund;
import com.tzdr.domain.web.entity.WUser;
import com.tzdr.domain.web.entity.future.FaccountInfo;
import com.tzdr.domain.web.entity.future.FcapitalInfo;

@Service
public class FutureAccountServiceImpl implements FutureAccountService {

	@Autowired
	private FaccountInfoService accountInfoService;

	@Autowired
	private FuserTradeService tradeService;

	@Autowired
	private WUserService wUserService;

	@Autowired
	private UserFundService userFundService;

	@Autowired
	private FcapitalInfoService capitalInfoService;
	
	private static Logger logger=LoggerFactory.getLogger(FutureAccountServiceImpl.class);

	@Override
	public FutureAccountVo getFuturesAccountVo(String userID) {
		// TODO Auto-generated method stub
		FutureAccountVo futuresAccountVo = new FutureAccountVo();
		// 股指期货账户信息
		FaccountInfo accountInfo = accountInfoService.findByUid(userID);
		futuresAccountVo.setTransactions(0);
		// 股指保证金
		BigDecimal cautionMoney = new BigDecimal(0);
		//累计获得利润
		BigDecimal cumulativeTotal = new BigDecimal(0);
		//累计支出管理费
		BigDecimal cumulativeTrans = new BigDecimal(0);
		if (accountInfo != null) {
			String faid = accountInfo.getId();
			cautionMoney = getCautionMoney(faid);
			cumulativeTotal = getCumulativeTotal(faid);
			cumulativeTrans = getCumulativeTrans(faid);
			futuresAccountVo.setCumulativeProfit(getCumulativeProfit(faid));
			futuresAccountVo.setCumulativeTrans(cumulativeTrans);
			futuresAccountVo.setCumulativeTotal(cumulativeTotal);
			futuresAccountVo.setTransactions(tradeService.getRowCount(
					accountInfo.getId(), 1, 1));
		}
		// 子账户总资产
		BigDecimal totalMoney = getTotalMoney(accountInfo, cautionMoney);
		// 股指期货的账户余额
		BigDecimal balanceMoney = getBalanceMoney(accountInfo);
		// 实际盈利
		BigDecimal actualProfit = getActualProfit(cumulativeTotal,
				cumulativeTrans);
		// 盈亏率
		BigDecimal profitRate = getProfitRate(totalMoney, actualProfit);
		futuresAccountVo.setCautionMoney(cautionMoney);
		futuresAccountVo.setTotalMoney(totalMoney);
		futuresAccountVo.setBalanceMoney(balanceMoney);
		futuresAccountVo.setActualProfit(actualProfit);
		futuresAccountVo.setProfitRate(profitRate);
		return futuresAccountVo;
	}

	@Override
	public boolean isRange(WUser wUser, BigDecimal money) {
		// TODO Auto-generated method stub
		if (wUser == null) {
			return false;
		}
		BigDecimal avlBal = new BigDecimal(wUser.getAvlBal());
		int result = money.compareTo(avlBal);
		if (result == 1) {
			return false;
		}
		return true;
	}

	@Override
	public boolean isRange(FaccountInfo faccountInfo, BigDecimal money) {
		// TODO Auto-generated method stub
		if (faccountInfo == null) {
			return false;
		}
		BigDecimal balance = faccountInfo.getBalance();
		int result = money.compareTo(balance);
		if (result == 1) {
			return false;
		}
		return true;
	}

	@Override
	public void transferMoney(FaccountInfo faccountInfo, WUser wUser,
			boolean isOut, BigDecimal money) {
		// TODO Auto-generated method stub
		BigDecimal balance = faccountInfo.getBalance();
		if (balance == null) {
			balance = new BigDecimal(0);
		}
		BigDecimal avlBal = new BigDecimal(wUser.getAvlBal());
		UserFund fund = null;
		FcapitalInfo fcapitalInfo = null;
		if (isOut) {
			// 期货账户划出
			balance = balance.subtract(money);
			// 配资账户划入
			avlBal = avlBal.add(money);
			// 获取配资的资金明细
			fund = getUserFund(
					faccountInfo.getUid(),
					avlBal.doubleValue(),
					money.doubleValue(),
					30,
					DateUtils.dateTimeToString(new Date(),
							"yyyy-MM-dd HH:mm:ss") + " 从股指账户划入" + money + "元");
			// 获取期货的资金明细
			fcapitalInfo = getFcapitalInfo(faccountInfo, money, balance, -1,
					31, "划出到股票账户：" + money + "元");
		} else {
			// 期货账户划入
			balance = balance.add(money);
			// 配资账户划出
			avlBal = avlBal.subtract(money);
			// 获取配资的资金明细
			fund = getUserFund(
					faccountInfo.getUid(),
					avlBal.doubleValue(),
					-money.doubleValue(),
					31,
					DateUtils.dateTimeToString(new Date(),
							"yyyy-MM-dd HH:mm:ss") + " 划出到股指账户" + money + "元");
			// 获取期货的资金明细
			fcapitalInfo = getFcapitalInfo(faccountInfo, money, balance, 1, 30,
					"从股票账户划入" + money + "元");
		}
		faccountInfo.setBalance(balance);
		wUser.setAvlBal(avlBal.doubleValue());
		wUserService.update(wUser);
		userFundService.save(fund);
		accountInfoService.update(faccountInfo);
		capitalInfoService.save(fcapitalInfo);
	}

	@Override
	public FaccountInfo getFaccountInfo(String userID) {
		// TODO Auto-generated method stub
		return accountInfoService.findByUid(userID);
	}

	/**
	 * 获取要插入的配资资金明细
	 * 
	 * @param userID
	 * @param avlBal
	 * @param money
	 *            划出为负，划入为正
	 * @return
	 */
	private UserFund getUserFund(String userID, double avlBal, double money,
			Integer type, String remark) {
		UserFund fund = new UserFund();
		fund.setMoney(money);
		fund.setType(type);
		fund.setUid(userID);
		fund.setPayStatus((short) 1);// 已支付
		// fund.setTrxId(tradeNo);
		fund.setAmount(avlBal);
		fund.setAddtime(new Date().getTime() / 1000);
		fund.setUptime(new Date().getTime() / 1000);
		fund.setRemark(remark);
		return fund;
	}

	private FcapitalInfo getFcapitalInfo(FaccountInfo faccountInfo,
			BigDecimal money, BigDecimal balance, Integer direction,
			Integer businessType, String remark) {
		FcapitalInfo capitalInfo = new FcapitalInfo();
		capitalInfo.setFaccountInfo(faccountInfo);
		capitalInfo.setTradeMoney(money);
		capitalInfo.setBalance(balance);
		capitalInfo.setDirection(direction);
		// 内转标识
		capitalInfo.setBusinessType(businessType);
		capitalInfo.setState(1);
		capitalInfo.setRemark(remark);
		capitalInfo.setCreateTime(new Date());
		return capitalInfo;
	}

	/**
	 * 获取子账户的资产总值(股指期货的账户余额+保证金+股指期货的冻结金额)
	 * 改：股指期货的账户余额+保证金
	 * 
	 * @param userID
	 * @return
	 */
	private BigDecimal getTotalMoney(FaccountInfo accountInfo,
			BigDecimal cautionMoney) {
		if (accountInfo == null) {
			return null;
		}
		if (cautionMoney == null) {
			cautionMoney = new BigDecimal(0);
		}
		// 冻结金额
//		BigDecimal freeze = accountInfo.getFreeze();
//		if (freeze == null) {
//			freeze = new BigDecimal(0);
//		}
		// 股指期货的账户余额
		BigDecimal balance = accountInfo.getBalance();
		if (balance == null) {
			balance = new BigDecimal(0);
		}
//		return cautionMoney.add(balance.add(freeze));
		
		return cautionMoney.add(balance);
	}

	/**
	 * 获取类型为实盘、交易中的该用户的保证金
	 * 
	 * @param accountID
	 * @return
	 */
	private BigDecimal getCautionMoney(String faid) {
		return tradeService.queryCautionMoney(faid, 1, 1);
	}

	/**
	 * 获取股指期货的账户余额
	 * 
	 * @param userID
	 * @return
	 */
	private BigDecimal getBalanceMoney(FaccountInfo accountInfo) {
		return accountInfo == null ? null : accountInfo.getBalance();
	}

	/**
	 * 获取实际盈亏（累计获得利润-累计支出交易费）
	 * 
	 * @param cumulativeTotal
	 * @param cumulativeTrans
	 * @return
	 */
	private BigDecimal getActualProfit(BigDecimal cumulativeTotal,
			BigDecimal cumulativeTrans) {
		return cumulativeTotal.subtract(cumulativeTrans.abs());
	}

	/**
	 * 获取盈亏率（实际盈亏/股指期货的总资产*100）
	 * 
	 * @param totalMoney
	 * @param actualProfit
	 * @return
	 */
	private BigDecimal getProfitRate(BigDecimal totalMoney,
			BigDecimal actualProfit) {
		if (totalMoney == null || actualProfit == null) {
			return null;
		}
		if(totalMoney.intValue()==0){
			logger.debug("股指期货的总资产为零，不能被除");
			return null;
		}
		//精确到小数点后四位
		BigDecimal money=actualProfit.divide(totalMoney, 4, BigDecimal.ROUND_HALF_EVEN);
		return money.abs().multiply(new BigDecimal(100));
	}

	@Override
	public FaccountInfo findOrSave(String userId) {
		// TODO Auto-generated method stub
		return accountInfoService.findOrSave(userId);
	}

	/**
	 * 获取实盘的累计盈亏
	 * 
	 * @param faid
	 * @return
	 */
	private BigDecimal getCumulativeProfit(String faid) {
		BigDecimal money = tradeService.queryBusinessMoney(faid, -1, 1);
		return money == null ? new BigDecimal(0) : money;
	}

	/**
	 * 累计支出管理费 公式：交易费+委托费
	 * 
	 * @param faid
	 * @return
	 */
	private BigDecimal getCumulativeTrans(String faid) {
		BigDecimal money = capitalInfoService.queryCumulativeTrans(faid);
		return money == null ? new BigDecimal(0) : money;
	}

	/**
	 * 累计获得利润 公式：累计(单笔交易：交易盈亏*(1-后台设置的分润比例))
	 * 
	 * @param faid
	 * @return
	 */
	private BigDecimal getCumulativeTotal(String faid) {
		BigDecimal money = tradeService.queryGainMoney(faid, -1, 1);
		return money == null ? new BigDecimal(0) : money;
	}

}

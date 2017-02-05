package com.tzdr.business.service.feededuction;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import jodd.util.ObjectUtil;
import jodd.util.StringUtil;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.Lists;
import com.tzdr.business.service.datamap.DataMapService;
import com.tzdr.business.service.pay.UserFundService;
import com.tzdr.business.service.tradecalendar.TradeDayService;
import com.tzdr.business.service.userTrade.NoticeRecordService;
import com.tzdr.business.service.userTrade.UserTradeService;
import com.tzdr.business.service.wuser.WUserService;
import com.tzdr.common.api.ihuyi.SMSSender;
import com.tzdr.common.utils.BigDecimalUtils;
import com.tzdr.common.utils.ConfUtil;
import com.tzdr.common.utils.Dates;
import com.tzdr.common.utils.EmailUtils;
import com.tzdr.common.utils.Exceptions;
import com.tzdr.common.utils.MessageUtils;
import com.tzdr.domain.cache.CacheManager;
import com.tzdr.domain.cache.DataDicKeyConstants;
import com.tzdr.domain.vo.UserFundVo;
import com.tzdr.domain.vo.UserTradeCmsVo;
import com.tzdr.domain.web.entity.NoticeRecord;
import com.tzdr.domain.web.entity.UserFund;
import com.tzdr.domain.web.entity.UserTrade;
import com.tzdr.domain.web.entity.WUser;

/**
 * 
 * @zhouchen 2014年12月26日
 */
@Service
public class FeeDuductionServiceBak {
	@Autowired
	private DataMapService dataMapService;
	
	public static final Logger logger = LoggerFactory
			.getLogger(FeeDuductionServiceBak.class);

	@Autowired
	private UserFundService userFundService;

	@Autowired
	private WUserService wUserService;
	
	
	@Autowired
	private UserTradeService  userTradeService;
	

	@Autowired
	private TradeDayService  tradeDayService;
	
	@Autowired
	private NoticeRecordService noticeRecordService;

	/**
	 * 扣除对应的 欠费
	 * @param userFund
	 */
	@Transactional
	public synchronized void deductionUserTradeArrearage(UserFund userFund){
		String uid = userFund.getUid();
		if (StringUtil.isBlank(uid)) {
			return;
		}

		WUser wUser = wUserService.getUser(uid);
		if (ObjectUtil.equals(null, wUser)) {
			return;
		}

		try {
			Double balance = ObjectUtil.equals(wUser.getAvlBal(), null) ? NumberUtils
					.toDouble("0") : wUser.getAvlBal();
			Double deductionMoney = ObjectUtil.equals(userFund.getMoney(),
					null) ? NumberUtils.toDouble("0") : userFund.getMoney();
			if (balance < Math.abs(deductionMoney)) {
				return;
			}

			// 更新用户余额
			wUser.setAvlBal(BigDecimalUtils.add(balance, deductionMoney));
			wUser.setFund(BigDecimalUtils.add2(wUser.getAvlBal(),wUser.getFrzBal()));
			wUserService.updateUser(wUser);

			// 更新资金记录表
			userFund.setPayStatus(NumberUtils.toShort("1"));
			userFund.setUptime(Dates.getCurrentLongDate());
			userFund.setAmount(wUser.getAvlBal());
			userFundService.update(userFund);

		} catch (Exception e) {
			String detail = MessageUtils.message(
					"fee.fill.deduction.money.fail", wUser.getUname(),
					Dates.format(new Date(userFund.getAddtime() * 1000),
							Dates.CHINESE_DATE_FORMAT_LINE), CacheManager
							.getDataMapByKey(
									DataDicKeyConstants.USER_FUND_TYPE,
									String.valueOf(userFund.getType())),
					userFund.getMoney());
			logger.error(detail, e);
			HandleException(e, detail, "deductionArrearage");
		}
	}
	/**
	 * 扣除系统欠费
	 */
	public synchronized void deductionArrearage() {
		List<UserFund> userFunds = userFundService.findByPayStatus(NumberUtils
				.toShort("0"));
		if (CollectionUtils.isEmpty(userFunds)) {
			return;
		}

		for (UserFund userFund : userFunds) {
			deductionUserTradeArrearage(userFund);
		}

	}

	/**
	 * 扣去管理费或利息
	 */
	public synchronized void deductionTodayFee() {

		Long currentLongDay = Dates.getCurrentLongDay();
		List<UserTrade> userTrades = userTradeService.findByStatusAndStarttime(NumberUtils.toShort("1"), currentLongDay);
		if (CollectionUtils.isEmpty(userTrades)) {
			return;
		}

		String currentDay = Dates.format(new Date(),Dates.CHINESE_DATE_FORMAT_LONG);
		for (UserTrade userTrade : userTrades) {
			// 交易预计结束时间estimateEndtime
			Long estimateEndtime = userTrade.getEstimateEndtime();
			boolean isTradeDay = tradeDayService.isTradeDay(currentDay);
			
			/**
			 * 1 未到预计结束时间 交易日只扣管理费 非交易日不扣
			 */
			if (currentLongDay <= estimateEndtime) {
				if (!isTradeDay) {
					continue;
				}
				deductionProcess(userTrade, estimateEndtime, 1);
				continue;
			}

			/**
			 * 到达结束时间 交易日 扣管理费+利息
			 */
			if (isTradeDay) {
				deductionProcess(userTrade, estimateEndtime, 3);
				continue;
			}

			/**
			 * 到达结束时间 非交易日 只扣利息
			 */
			deductionProcess(userTrade, estimateEndtime, 2);
			continue;
		}
	}
	
	
	/**
	 * 发送当天欠费短信 ，查询资金记录表，只有当天欠费的就发送
	 */
	public  synchronized void sendCurrentArrearsSms(){
		List<UserFundVo> userFundVos = userFundService.findTodayArrears();
		if (CollectionUtils.isEmpty(userFundVos)){
			return;
		}
		
		for (UserFundVo fundVo:userFundVos){
			String uid = fundVo.getUid();
			if (StringUtil.isBlank(uid)){
				continue; 
			}
			WUser user = wUserService.getUser(uid);
			if (ObjectUtil.equals(null, user)){
				continue;
			}
			// 当天不够短信
			//SMSSender.getInstance().send(wUser.getMobile(),MessageUtils.message("fee.fill.deduction.balance.current.day.not.enough",userTrade.getId()));
			String content = MessageUtils.message("fee.fill.deduction.balance.current.day.not.enough",uid);
			SMSSender.getInstance().send(dataMapService.getSmsContentOthers(),user.getMobile(),content);
			//通知记录
			noticeRecordService.save(new NoticeRecord(uid, content, 4));
		}
	}
	
	/**
	 *  次日是否够扣费用，之前没有欠费，次日不足够扣费就要发送短信
	 */
	public  void  isCanFeeDeductionNextDay(){
		List<UserTradeCmsVo>  tradeCmsVos = userTradeService.getNoArrearsUserTrades();
		if (CollectionUtils.isEmpty(tradeCmsVos)){
			return;
		}
		
		for (UserTradeCmsVo tradeCmsVo:tradeCmsVos){
			String uid = tradeCmsVo.getUid();
			if (StringUtil.isBlank(uid)){
				continue;
			}
			
			WUser wUser = wUserService.getUser(uid);
			if (ObjectUtil.equals(null, wUser)){
				continue;
			}
			
			Double balance = wUser.getAvlBal();
			//余额为空或0 直接发短信 不够扣
			if (ObjectUtil.equals(null,balance) || balance==0){
				//SMSSender.getInstance().send(wUser.getMobile(),MessageUtils.message("fee.fill.deduction.balance.next.day.not.enough",userTrade.getId()));
				String content = MessageUtils.message("fee.fill.deduction.balance.next.day.not.enough");
				SMSSender.getInstance().send(dataMapService.getSmsContentOthers(),wUser.getMobile(),content);
				//保存通知记录
				noticeRecordService.save(new NoticeRecord(uid, content, 3));
				continue;
			}
			
			// 查看下面的 用户的下面的配资方案 ，并统计次日所应缴纳的全部费用
			String userTradeIds = tradeCmsVo.getUserTradeId();
			String [] tempTradeIds = userTradeIds.split(",");
			if (ArrayUtils.isEmpty(tempTradeIds)){
				continue;
			}
			
			// 如果余额小于 所有配资方案费用就发送短信
			double  totalDeductionMoney = getTotalDeductionMoney(tempTradeIds);
			if (balance >= totalDeductionMoney){
				continue;
			}
			//SMSSender.getInstance().send(wUser.getMobile(),MessageUtils.message("fee.fill.deduction.balance.next.day.not.enough",userTrade.getId()));
			String content = MessageUtils.message("fee.fill.deduction.balance.next.day.not.enough");
			SMSSender.getInstance().send(dataMapService.getSmsContentOthers(),wUser.getMobile(),content);
			//保存通知记录
			noticeRecordService.save(new NoticeRecord(uid, content, 3));
			continue;
		}
	}
	
	/**
	 * 根据用户配资id 获取所有配资 次日所应缴纳的费用
	 * @param tempTradeIds
	 * @return
	 */
	public double getTotalDeductionMoney(String [] tempTradeIds){
		Long  nextLongDay = Dates.getNextLongDay();
		String nextDay = Dates.format(Dates.parseLong2Date(nextLongDay), Dates.CHINESE_DATE_FORMAT_LONG);
		boolean  isTradeDay = tradeDayService.isTradeDay(nextDay);
		
		 Double  deductionMoney = NumberUtils.toDouble("0");
		 for (String tradeId : tempTradeIds){
			UserTrade  userTrade =  userTradeService.get(tradeId);
			if (ObjectUtil.equals(null, userTrade)){
				continue;
			}
			// 没有超出配资时长，交易日只收取管理费。非交易日不收费用
			Long  estimateEndtime = userTrade.getEstimateEndtime();		
			if (nextLongDay <= estimateEndtime){
				if (!isTradeDay){
					continue;
				}
				
				deductionMoney = BigDecimalUtils.add(deductionMoney,userTrade.getFeeDay());
				continue;
			}
			
			// 超出配资时长 交易日收管理和利息。非交易日只收利息
			if (isTradeDay){
				deductionMoney = BigDecimalUtils.add(deductionMoney,BigDecimalUtils.add(userTrade.getFeeMonth(),userTrade.getFeeDay()));
				continue;
			}
			
			deductionMoney = BigDecimalUtils.add(deductionMoney, userTrade.getFeeMonth());
			continue;
		 }
		 return deductionMoney;
	}
	
	
	
	/**
	 * 获取是否 该扣的费用
	 * @param deductionType
	 * @param feeDay 管理费
	 * @param feeMonth 利息
	 * @return
	 */
	private double  getDeductionMoney(int  deductionType,Double feeDay,Double feeMonth){
		double  deductionMoney = 0;
		switch (deductionType) {
		case 1:
			deductionMoney = feeDay;
			break;
		case 2:
			deductionMoney = feeMonth;
			break;
		case 3:
			deductionMoney = BigDecimalUtils.add(feeDay, feeMonth);
			break;
		default:
			break;
		}
		return deductionMoney;
	}
	
	
	/**
	 * 判断可用余额是否足以扣取费用 
	 * @param userId
	 * @param deductionMoney
	 * @return
	 */
	private  boolean  isCanFeeDeduction(String userId,Double deductionMoney){
		WUser wUser = wUserService.getUser(userId);
		if (ObjectUtil.equals(null, wUser)){
			logger.debug("根据用户id："+userId+"，找不到对应的数据.");
			return false;
		}
		Double  avlBal = wUser.getAvlBal();
		if (ObjectUtil.equals(null, avlBal) || avlBal==0){
			return false;
		}
		if (avlBal >= deductionMoney){
			return true;
		}
		
		return false;
	}
	
	/**
	 * 扣费流程公共方法
	 * @param userTrade
	 * @param estimateEndtime
	 * @param deductionType
	 */
	@Transactional
	private  synchronized void  deductionProcess(UserTrade userTrade,Long  estimateEndtime,int  deductionType){
		
		Double feeDay = ObjectUtil.equals(null,userTrade.getFeeDay())?0:userTrade.getFeeDay();
		Double feeMonth =ObjectUtil.equals(null, userTrade.getFeeMonth())?0:userTrade.getFeeMonth();
		WUser  wUser = userTrade.getWuser();
		if (ObjectUtil.equals(null, wUser)){
			logger.debug("配资方案id："+userTrade.getId()+",对应的用户不存在。");
			return;
		}
		String userId = wUser.getId();
		if (StringUtil.isBlank(userId)){
			logger.debug("配资方案id："+userTrade.getId()+",对应的用户ID不存在。");
			return;
		}
		//配资组合id
		String groupId = userTrade.getGroupId();
		
		//获取总的扣费金额 是只扣利息  还是 管理费。还是都扣
		double  deductionMoney = getDeductionMoney(deductionType, feeDay, feeMonth);
		//资金记录备注
		String feeDayRemark = MessageUtils.message("fee.deduction.management.expense.remark",Dates.format(new Date(),Dates.CHINESE_DATE_FORMAT_LINE),feeDay);
		String feeMonthRemark = MessageUtils.message("fee.deduction.interest.remark",Dates.format(new Date(),Dates.CHINESE_DATE_FORMAT_LINE),feeMonth);
		try{
			//够扣费用
			if (isCanFeeDeduction(userId, deductionMoney)){
				// 扣费
				switch (deductionType) {
				case 1:
					deductionMoney(userTrade, 12, feeDay,feeDayRemark,NumberUtils.toShort("1"));
					break;
				case 2:
					deductionMoney(userTrade, 11, feeMonth,feeMonthRemark,NumberUtils.toShort("1"));
					break;
				case 3:
					deductionMoney(userTrade, 12, feeDay,feeDayRemark,NumberUtils.toShort("1"));
					deductionMoney(userTrade, 11, feeMonth,feeMonthRemark,NumberUtils.toShort("1"));
					break;
				default:
					break;
				}
				
				//根据配资组合id 查询是否有欠费
				List<UserFund>  tempUserFunds = userFundService.queryGroupArrears(userId,groupId, NumberUtils.toShort("0"),Dates.getCurrentLongDay());
				if (CollectionUtils.isEmpty(tempUserFunds)){
					//根据组合查询 配资 并修改 限制买入状态
					List<UserTrade>  userTrades = userTradeService.findByGroupId(groupId);
					if (CollectionUtils.isEmpty(userTrades)){
						logger.debug("通过配资组合groupId:"+groupId+",没有查询到对应的配资方案。");
						return ;
					}
					for (UserTrade tempTrade:userTrades){
						/*Short limitBuyStatus = tempTrade.getLimitBuyStatus();
						if (NumberUtils.toShort("1") == limitBuyStatus || NumberUtils.toShort("2") == limitBuyStatus){
							 tempTrade.setLimitBuyStatus(NumberUtils.toShort("4"));
							 userTradeService.update(tempTrade);
						 }*/
					}
				}
				return ;
			}
			
			//不够扣费用
			switch (deductionType) {
				case 1:
					 deductionMoney(userTrade, 12, feeDay,feeDayRemark,NumberUtils.toShort("0"));
					break;
				case 2:
					deductionMoney(userTrade, 11, feeMonth,feeMonthRemark,NumberUtils.toShort("0"));
					break;
				case 3:
					// 如果 余额 不够扣除利息 和 管理费。
					//利息和管理费先扣金额大的，金额大的不够扣时，继续扣小金额。如果小金额不够扣，则利息和管理费扣费记录全部为“未支付”状态。
					deductionFeeDayAndFeeMonth(userId,userTrade, feeDay, feeMonth);
					break;
				default:
					break;
		  }
			
			// 查看组合之前欠费记录
			List<UserFund>  tempUserFunds = userFundService.queryGroupArrears(userId,groupId, NumberUtils.toShort("0"),Dates.getCurrentLongDay());
			if (CollectionUtils.isEmpty(tempUserFunds)){
				//限制账号买入将状态改为“未处理限制购买”
				//userTradeService.changeLimitBuyStatus(new String[]{groupId},NumberUtils.toShort("1"));
				return;
			}
			//如果连续两天欠费  就改变状态为待平仓
			Long firstArrearageTime = tempUserFunds.get(0).getAddtime();
			Long  arrearageDays = (Dates.getCurrentLongDay()-Dates.getDateZeroLong(firstArrearageTime))/86400L+1L;
			if (arrearageDays>=2){
				//昨天有欠费，今天也欠费 就改变状态为待平仓 
				//userTradeService.changeLimitBuyStatus(new String[]{groupId},NumberUtils.toShort("5"));
			}
		} catch (Exception e) {
			String detail = MessageUtils.message("fee.deduction.update.usertrade.limit.buy.status.fail",userTrade.getId()); 
			logger.error(detail,e);
			HandleException(e,detail,"deductionProcess");
		}
	}
	
	/**
	 * 如果 余额 不够扣除利息 和 管理费。够扣哪个扣哪个
	 * 利息和管理费先扣金额大的，金额大的不够扣时，继续扣小金额。如果小金额不够扣，则利息和管理费扣费记录全部为“未支付”状态。
	 * @param wUser
	 * @param userTrade
	 * @param feeDay
	 * @param feeMonth
	 */
	private void deductionFeeDayAndFeeMonth(String userId,UserTrade  userTrade,Double feeDay,Double feeMonth){
		// 如果 余额 不够扣除利息 和 管理费。
		//利息和管理费先扣金额大的，金额大的不够扣时，继续扣小金额。如果小金额不够扣，则利息和管理费扣费记录全部为“未支付”状态。
		WUser wUser  = wUserService.get(userId);
		Double  avlBal = ObjectUtil.equals(null,wUser.getAvlBal())?NumberUtils.toDouble("0"):wUser.getAvlBal();
		//资金记录备注
		String feeDayRemark = MessageUtils.message("fee.deduction.management.expense.remark",Dates.format(new Date(),Dates.CHINESE_DATE_FORMAT_LINE),feeDay);
		String feeMonthRemark = MessageUtils.message("fee.deduction.interest.remark",Dates.format(new Date(),Dates.CHINESE_DATE_FORMAT_LINE),feeMonth);

		if (avlBal >= feeDay){
			 deductionMoney(userTrade, 12, feeDay,feeDayRemark,NumberUtils.toShort("1"));
			 deductionMoney(userTrade, 11, feeMonth,feeMonthRemark,NumberUtils.toShort("0"));
		}
		else if (avlBal>=feeMonth){
			 deductionMoney(userTrade, 12, feeDay,feeDayRemark,NumberUtils.toShort("0"));
			 deductionMoney(userTrade, 11, feeMonth,feeMonthRemark,NumberUtils.toShort("1"));
		}
		else
		{
			 deductionMoney(userTrade, 12, feeDay,feeDayRemark,NumberUtils.toShort("0"));
			 deductionMoney(userTrade, 11, feeMonth,feeMonthRemark,NumberUtils.toShort("0"));
		}
	}
	
	/**
	 * 扣除费用
	 */
	private synchronized void deductionMoney(UserTrade userTrade,Integer type,Double deductionMoney,String remark,short payStatus){
		
		WUser  wUser = userTrade.getWuser();
		String userId = wUser.getId();
		UserFund  userFund  = new UserFund(userId, userTrade.getId(), type, -deductionMoney,Dates.getCurrentLongDate(), remark, payStatus);
		userFund.setAddtime(Dates.getCurrentLongDate());
		userFund.setLid(userTrade.getGroupId());
		try {
			userFundService.deductionMoney(userFund, userId);
		} catch (Exception e) {
			String detail = MessageUtils.message("fee.deduction.money.fail",wUser.getUname(),CacheManager.getDataMapByKey(DataDicKeyConstants.USER_FUND_TYPE, String.valueOf(type)),userFund.getMoney()); 
			logger.error(detail,e);
			HandleException(e,detail,"deductionMoney");
		}
	}
	/**
	 * 异常处理
	 * 
	 * @param e
	 * @param method
	 */
	private void HandleException(Exception e, String detailed, String method) {
		// 发送邮件
		String devEmail = ConfUtil.getContext("mail.to.dev");
		List<String> pramas = Lists.newArrayList();
		String methodName = this.getClass().getName() + "." + method;
		String exception = Exceptions.getStackTraceAsString(e);
		pramas.add(methodName);
		pramas.add(detailed);
		pramas.add(exception);
		try {
			EmailUtils.getInstance().sendMailTemp(devEmail,
					"feeDeductionExceptionemail", pramas);
		} catch (Exception ex) {
			logger.error("email:", ex.getMessage());
		}
		logger.error(methodName + ",error::{}", exception);
	}

	public static void main(String[] args) {
		//System.out.println(new Date().compareTo(new Date(1420700153*1000L)));
		System.out.println(Dates.format(new Date(Dates.getCurrentLongDay()*1000),Dates.CHINESE_DATE_FORMAT_LINE));
		Long  arrearageDays = (Dates.getCurrentLongDay()-Dates.getDateZeroLong(1420700153L))/86400L+1L;
		System.out.println("arrearageDays==="+arrearageDays);
		Calendar  calendar = Calendar.getInstance();
		calendar.setTime(new Date(1420700153*1000L));
		calendar.set(Calendar.HOUR_OF_DAY,0);
		calendar.set(Calendar.MINUTE,0);
		calendar.set(Calendar.SECOND,0);
		System.out.println(calendar.getTimeInMillis()+"==="+calendar.getTime().getTime());
		//Long  arrearageDays = (Dates.getCurrentLongDay()*1000-calendar.getTimeInMillis())/86400000;
		
		System.out.println(new Date().getTime());
		System.out.println(Dates.getNextLongDay()*1000L);
		System.out.println(arrearageDays);
	}
}

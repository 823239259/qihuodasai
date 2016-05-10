package com.tzdr.business.service.feededuction;

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

import com.tzdr.business.exception.UserTradeException;
import com.tzdr.business.service.api.hundsun.HundsunJres;
import com.tzdr.business.service.datamap.DataMapService;
import com.tzdr.business.service.pay.UserFundService;
import com.tzdr.business.service.tradecalendar.TradeDayService;
import com.tzdr.business.service.userTrade.NoticeRecordService;
import com.tzdr.business.service.userTrade.SchemeLifeCycleRecordService;
import com.tzdr.business.service.userTrade.UserTradeService;
import com.tzdr.business.service.wuser.WUserService;
import com.tzdr.common.api.ihuyi.PgbSMSSender;
import com.tzdr.common.exception.EmailExceptionHandler;
import com.tzdr.common.utils.BigDecimalUtils;
import com.tzdr.common.utils.Dates;
import com.tzdr.common.utils.Exceptions;
import com.tzdr.common.utils.MessageUtils;
import com.tzdr.domain.cache.CacheManager;
import com.tzdr.domain.cache.DataDicKeyConstants;
import com.tzdr.domain.vo.UserFundVo;
import com.tzdr.domain.vo.UserTradeCmsVo;
import com.tzdr.domain.web.entity.NoticeRecord;
import com.tzdr.domain.web.entity.SchemeLifeCycleRecord;
import com.tzdr.domain.web.entity.UserFund;
import com.tzdr.domain.web.entity.UserTrade;
import com.tzdr.domain.web.entity.WUser;

/**
 * 
 * <B>说明: </B> 系统每日扣除方案管理、利息任务
 * @zhouchen
 * 2016年1月21日 下午2:53:19
 */
@Service
public class FeeDuductionService {

	@Autowired
	private DataMapService dataMapService;
	
	public static final Logger logger = LoggerFactory
			.getLogger(FeeDuductionService.class);

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

	
	@Autowired
	private SchemeLifeCycleRecordService schemeLifeCycleRecordService;
	
	
	private HundsunJres  hundsunJres = null;

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

			logger.info("系统扣除欠费：用户手机号【"+wUser.getMobile()+"】,扣费前余额【"+wUser.getAvlBal()+"】");
			// 更新用户余额
			wUser.setAvlBal(BigDecimalUtils.addRound(balance, deductionMoney));
			//wUser.setFund(BigDecimalUtils.addRound(wUser.getAvlBal(),wUser.getFrzBal()));
			wUserService.updateUser(wUser);

			// 更新资金记录表
			userFund.setPayStatus(NumberUtils.toShort("1"));
			userFund.setUptime(Dates.getCurrentLongDate());
			userFund.setAmount(wUser.getAvlBal());
			userFundService.update(userFund);
			logger.info("系统扣除欠费：用户手机号【"+wUser.getMobile()+"】,扣除金额【"+userFund.getMoney()+"】,扣费之后余额【"+wUser.getAvlBal()+"】");
			Thread.sleep(1000);
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
			HandleException(e, detail, "deductionArrearage","扣除欠费：欠费记录-ID="+userFund.getId());
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
	 * 限制买入卖出
	 * @param groupId
	 * @param userToken
	 * @param operationStatus
	 */
	private synchronized void   changeLimitStatus(String groupId,int operationStatus,short feeType) {
		
		// 涌金版  无法调用接口限制买入卖出
		if (feeType==2){
			return ;
		}
		
		try 
		{
			hundsunJres = HundsunJres.getInstance();
		} catch (Exception e) {
			logger.error("恒生接口调用失败,会导致扣费时修改账户权限失败",Exceptions.getStackTraceAsString(e));
			HandleException(e,"恒生接口调用失败,会导致扣费时修改账户权限失败,无法执行method：：changeLimitStatus()方法","deductionTodayFee","配资-groupID="+groupId+",更新状态-operationStatus="+operationStatus);
		}
		String userToken = hundsunJres.Login();
		
		try {
				List<UserTrade> userTrades = userTradeService.findByGroupId(groupId);
				if (CollectionUtils.isEmpty(userTrades)){
					logger.error("根据组合id："+groupId+"未查询到对应的配资方案");
					throw new UserTradeException("user.trade.monitor.change.operation.status.fail", null);
				}
				
				boolean isSet = false;
				for (UserTrade userTrade:userTrades){
					if (!isSet){
						boolean result = hundsunJres.funcAmChangeOperatorInfo(userToken,NumberUtils.toLong(userTrade.getAccount()),"3", null, 180L, null,String.valueOf(operationStatus), null);
						if (!result){
							logger.error(MessageUtils.message("user.trade.monitor.change.operation.status.fail"));
							throw new UserTradeException("user.trade.monitor.change.operation.status.fail", null);
						}
						isSet = true;
					}
					
					//更新状态
					switch (operationStatus) {
					case 0:
						userTrade.setBuyStatus(Boolean.FALSE);
						userTrade.setSellStatus(Boolean.FALSE);
						break;
					case 1:
						userTrade.setBuyStatus(Boolean.TRUE);
						break;
					case 3:
						userTrade.setBuyStatus(Boolean.TRUE);
						userTrade.setSellStatus(Boolean.TRUE);
						break;
					default:
						break;
					}
					userTradeService.update(userTrade);
					
					
					// 生成 方案操作记录
					schemeLifeCycleRecordService.save(new SchemeLifeCycleRecord(userTrade,SchemeLifeCycleRecord.SYSTEM_OPERATOR,operationStatus));
			
				}
				Thread.sleep(500);
		} catch (Exception e) {
			logger.error(this.getClass().getName()+e.getMessage());
			HandleException(e,"调用恒生接口修改账户权限失败","changeLimitStatus","修改账户权限参数： 配资-groupID="+groupId+",更新状态-operationStatus="+operationStatus);
		}finally{
			//退出恒生接口登录
			if (!ObjectUtil.equals(null, hundsunJres) && StringUtil.isNotBlank(userToken)){
				hundsunJres.Logout(userToken);
			}
		}
		
	}
	
	
	/**
	 * 扣去管理费或利息
	 */
	public synchronized void deductionTodayFee() {

		Long currentLongDay = Dates.getCurrentLongDay();
		//List<UserTrade> userTrades = userTradeService.findByStatusAndStarttime(NumberUtils.toShort("1"), currentLongDay);
		List<UserTrade> userTrades = userTradeService.findSymbolDeductionUserTrade((short)1,(short)0, (short)0,currentLongDay);  
		if (CollectionUtils.isEmpty(userTrades)) {
			return;
		}
		
		String currentDay = Dates.format(new Date(),Dates.CHINESE_DATE_FORMAT_LONG);
		for (UserTrade userTrade : userTrades) {
			// 校验是否在八点左右终结方案了
			UserTrade trade  = userTradeService.get(userTrade.getId());
			if (ObjectUtil.equals(null, trade) || !(trade.getStatus() ==1 || trade.getStatus() ==0) ){
				continue;
			}
			
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
			String content = MessageUtils.message("fee.fill.deduction.balance.current.day.not.enough");
			PgbSMSSender.getInstance().send(dataMapService.getPgbSmsContentOthers(),user.getMobile(),content);
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
				String content = MessageUtils.message("fee.fill.deduction.balance.next.day.not.enough");
				PgbSMSSender.getInstance().send(dataMapService.getPgbSmsContentOthers(),wUser.getMobile(),content);
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
			PgbSMSSender.getInstance().send(dataMapService.getPgbSmsContentOthers(),wUser.getMobile(),content);
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
			deductionMoney = BigDecimalUtils.addRound(feeDay, feeMonth);
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
		
		/**
		 * 6600活动  不扣管理费和利息
		 */
		if (userTrade.getActivityType()==WUser.ActivityType.ACTIVITY_6600){
			return ;
		}
		//扣费之前 判断当天是否已经扣费，扣费之后将不再扣
		if (userFundService.isDeductionTodayFee(userTrade.getId())){
			return;
		}
		
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
		//获取总的扣费金额 是只扣利息  还是 管理费。还是都扣 .如果扣除费用为0，直接返回
		double  deductionMoney = getDeductionMoney(deductionType, feeDay, feeMonth);
		if (deductionMoney<=0){
			return;
		}
		
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
		} catch (Exception e) {
			String detail = MessageUtils.message("fee.deduction.update.usertrade.limit.buy.status.fail",userTrade.getId()); 
			logger.error(detail,e);
			HandleException(e,detail,"deductionProcess","配资id="+userTrade.getId()+",groupid="+userTrade.getGroupId());
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
		
		if (0 == deductionMoney){
			return;
		}
		
		WUser  wUser = userTrade.getWuser();
		String userId = wUser.getId();
		UserFund  userFund  = new UserFund(userId, userTrade.getId(), type, -deductionMoney,Dates.getCurrentLongDate(), remark, payStatus);
		userFund.setAddtime(Dates.getCurrentLongDate());
		userFund.setLid(userTrade.getGroupId());
		userFund.setRid(userTrade.getProgramNo());
		try {
			userFundService.deductionMoney(userFund, userId);
			
			Thread.sleep(1000);
		} catch (Exception e) {
			String detail = MessageUtils.message("fee.deduction.money.fail",wUser.getUname(),CacheManager.getDataMapByKey(DataDicKeyConstants.USER_FUND_TYPE, String.valueOf(type)),userFund.getMoney()); 
			logger.error(detail,e);
			String paramsStr = "参数：配资id="+userTrade.getId() +",资金记录类型type=："+type+",扣除金额money=："+deductionMoney
					+ ",备注remark="+remark+",支付状态payStatus="+payStatus;
			HandleException(e,detail,"deductionMoney",paramsStr);
		}
	}
	/**
	 * 异常处理
	 * 
	 * @param e
	 * @param method
	 */
	private void HandleException(Exception e, String detailed, String method,String dataDetail) {
		EmailExceptionHandler.getInstance().HandleExceptionWithData(e,detailed, method, dataDetail);
	}


	/**
	 * 校验该方案是否在补仓线以上
	 * @param userTrade
	 * @return
	 */
	public boolean isUpMarginLine(UserTrade userTrade) {
		/*if (CollectionUtils.isEmpty(this.stockCurrents)) {
			return false;
		}
		String fundAccount = userTrade.getParentAccountNo();
		String combineId = userTrade.getCombineId();
		
		for (StockCurrent stockCurrent : this.stockCurrents) {
				String sfundAccount = stockCurrent.getFundAccount();
				String scombineId = stockCurrent.getCombineId();
				if (StringUtil.equals(scombineId, combineId)
						&& StringUtil.equals(sfundAccount, fundAccount)) {
					double assetTotalValue = BigDecimalUtils.addRound(
							stockCurrent.getCurrentCash(),
							stockCurrent.getMarketValue());
					double warning = userTrade.getWarning();
					if (assetTotalValue > warning) {
						return true;
					}
				}
		}*/
		return false;
	}
	public static void main(String[] args) {
		System.out.println(Dates.getCurrentLongDay()); 
	}
}

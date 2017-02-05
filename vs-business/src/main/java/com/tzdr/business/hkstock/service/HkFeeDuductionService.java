package com.tzdr.business.hkstock.service;

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
import com.tzdr.business.service.userTrade.NoticeRecordService;
import com.tzdr.business.service.wuser.WUserService;
import com.tzdr.common.api.ihuyi.SMSSender;
import com.tzdr.common.exception.EmailExceptionHandler;
import com.tzdr.common.utils.BigDecimalUtils;
import com.tzdr.common.utils.Dates;
import com.tzdr.common.utils.MessageUtils;
import com.tzdr.domain.cache.CacheManager;
import com.tzdr.domain.cache.DataDicKeyConstants;
import com.tzdr.domain.hkstock.constants.HkConstant;
import com.tzdr.domain.hkstock.entity.HkUserTrade;
import com.tzdr.domain.hkstock.vo.HkUserTradFeeDuductionVo;
import com.tzdr.domain.web.entity.NoticeRecord;
import com.tzdr.domain.web.entity.UserFund;
import com.tzdr.domain.web.entity.WUser;

/**
 * 
 * @zhouchen 2014年12月26日
 */
@Service
public class HkFeeDuductionService {

	public static final Logger logger = LoggerFactory
			.getLogger(HkFeeDuductionService.class);

	@Autowired
	private UserFundService userFundService;

	@Autowired
	private WUserService wUserService;
	
	
	@Autowired
	private HkUserTradeService hkUserTradeService;
	

	@Autowired
	private HkTradeCalendarService  hkTradeDayService;
	
	@Autowired
	private NoticeRecordService noticeRecordService;
	
	@Autowired
	private DataMapService dataMapService;
	
	/**
	 * 港股扣去管理费或利息
	 */
	public synchronized void deductionTodayFee() {

		Long currentLongDay = Dates.getCurrentLongDay();
		List<HkUserTrade> hkUserTrades = hkUserTradeService.getDuductionTrades(currentLongDay,Lists.newArrayList(HkConstant.SHORT_ZERO,HkConstant.SHORT_ONE));  
		if (CollectionUtils.isEmpty(hkUserTrades)) {
			return;
		}
		
		String currentDay = Dates.format(new Date(),Dates.CHINESE_DATE_FORMAT_LONG);
		for (HkUserTrade hkUserTrade : hkUserTrades) {
			// 校验是否在八点左右终结方案了，或者审核不通过了则不再扣取管理费
			HkUserTrade trade  = hkUserTradeService.get(hkUserTrade.getId());
			if (ObjectUtil.equals(null, trade) || trade.getStatus()==HkConstant.INT_TWO){
				continue;
			}
			
			// 交易预计结束时间estimateEndtime
			Long estimateEndtime = hkUserTrade.getEstimateEndtime();
			boolean isTradeDay = hkTradeDayService.isTradeDay(currentDay);
			
			/**
			 * 1 未到预计结束时间 交易日只扣管理费 非交易日不扣
			 */
			if (currentLongDay <= estimateEndtime) {
				if (!isTradeDay) {
					continue;
				}
				deductionProcess(hkUserTrade, estimateEndtime, 1);
				continue;
			}

			/**
			 * 到达结束时间 交易日 扣管理费+利息
			 */
			if (isTradeDay) {
				deductionProcess(hkUserTrade, estimateEndtime, 3);
				continue;
			}

			/**
			 * 到达结束时间 非交易日 只扣利息
			 */
			deductionProcess(hkUserTrade, estimateEndtime, 2);
			continue;
		}
	}
	
	/**
	 *  次日是否够扣费用，之前没有欠费，次日不足够扣费就要发送短信
	 */
	public  void  isCanFeeDeductionNextDay(){
		List<HkUserTradFeeDuductionVo>  feeDuductionVos = hkUserTradeService.getNoArrearsHkUserTrades();
		if (CollectionUtils.isEmpty(feeDuductionVos)){
			return;
		}
		
		for (HkUserTradFeeDuductionVo duductionVo:feeDuductionVos){
			String uid = duductionVo.getUid();
			if (StringUtil.isBlank(uid)){
				continue;
			}
			
			WUser wUser = wUserService.getUser(uid);
			if (ObjectUtil.equals(null, wUser)){
				continue;
			}
			
			List<NoticeRecord> noticeRecords = noticeRecordService.getByTypeAndUid(uid,3);
			if (!CollectionUtils.isEmpty(noticeRecords)){
				continue;
			}
			Double balance = wUser.getAvlBal();
			//余额为空或0 直接发短信 不够扣
			if (ObjectUtil.equals(null,balance) || balance==HkConstant.INT_ZERO){
				String content = MessageUtils.message("fee.fill.deduction.balance.next.day.not.enough");
				SMSSender.getInstance().send(dataMapService.getSmsContentOthers(),wUser.getMobile(),content);
				//保存通知记录
				noticeRecordService.save(new NoticeRecord(uid, content, 3));
				continue;
			}
			
			// 查看下面的 用户的下面的配资方案 ，并统计次日所应缴纳的全部费用
			String userTradeIds = duductionVo.getUserTradeIds();
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
		boolean  isTradeDay = hkTradeDayService.isTradeDay(nextDay);
		
		 Double  deductionMoney = NumberUtils.toDouble("0");
		 for (String tradeId : tempTradeIds){
			HkUserTrade  hkUserTrade =  hkUserTradeService.get(tradeId);
			if (ObjectUtil.equals(null, hkUserTrade)){
				continue;
			}
			// 没有超出配资时长，交易日只收取管理费。非交易日不收费用
			Long  estimateEndtime = hkUserTrade.getEstimateEndtime();		
			if (nextLongDay <= estimateEndtime){
				if (!isTradeDay){
					continue;
				}
				
				deductionMoney = BigDecimalUtils.add(deductionMoney,hkUserTrade.getFeeDay());
				continue;
			}
			
			// 超出配资时长 交易日收管理和利息。非交易日只收利息
			if (isTradeDay){
				deductionMoney = BigDecimalUtils.add(deductionMoney,BigDecimalUtils.add(hkUserTrade.getFeeMonth(),hkUserTrade.getFeeDay()));
				continue;
			}
			
			deductionMoney = BigDecimalUtils.add(deductionMoney, hkUserTrade.getFeeMonth());
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
	 * @param hkUserTrade
	 * @param estimateEndtime
	 * @param deductionType
	 */
	@Transactional
	private  synchronized void  deductionProcess(HkUserTrade hkUserTrade,Long  estimateEndtime,int  deductionType){
		
		//扣费之前 判断当天是否已经扣费，扣费之后将不再扣
		if (userFundService.isDeductionTodayFee(hkUserTrade.getId())){
			return;
		}
		
		Double feeDay = ObjectUtil.equals(null,hkUserTrade.getFeeDay())?0:hkUserTrade.getFeeDay();
		Double feeMonth =ObjectUtil.equals(null, hkUserTrade.getFeeMonth())?0:hkUserTrade.getFeeMonth();
		WUser  wUser = hkUserTrade.getWuser();
		if (ObjectUtil.equals(null, wUser)){
			logger.debug("港股配资方案id："+hkUserTrade.getId()+",对应的用户不存在。");
			return;
		}
		String userId = wUser.getId();
		if (StringUtil.isBlank(userId)){
			logger.debug("港股配资方案id："+hkUserTrade.getId()+",对应的用户ID不存在。");
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
					deductionMoney(hkUserTrade, 12, feeDay,feeDayRemark,NumberUtils.toShort("1"));
					break;
				case 2:
					deductionMoney(hkUserTrade, 11, feeMonth,feeMonthRemark,NumberUtils.toShort("1"));
					break;
				case 3:
					deductionMoney(hkUserTrade, 12, feeDay,feeDayRemark,NumberUtils.toShort("1"));
					deductionMoney(hkUserTrade, 11, feeMonth,feeMonthRemark,NumberUtils.toShort("1"));
					break;
				default:
					break;
				}
				return ;
			}
			
			//不够扣费用
			switch (deductionType) {
				case 1:
					 deductionMoney(hkUserTrade, 12, feeDay,feeDayRemark,NumberUtils.toShort("0"));
					break;
				case 2:
					deductionMoney(hkUserTrade, 11, feeMonth,feeMonthRemark,NumberUtils.toShort("0"));
					break;
				case 3:
					// 如果 余额 不够扣除利息 和 管理费。
					//利息和管理费先扣金额大的，金额大的不够扣时，继续扣小金额。如果小金额不够扣，则利息和管理费扣费记录全部为“未支付”状态。
					deductionFeeDayAndFeeMonth(userId,hkUserTrade, feeDay, feeMonth);
					break;
				default:
					break;
		  }
		} catch (Exception e) {
			String detail = MessageUtils.message("fee.deduction.update.usertrade.limit.buy.status.fail",hkUserTrade.getId()); 
			logger.error(detail,e);
			HandleException(e,detail,"deductionProcess","配资id="+hkUserTrade.getId()+",groupid="+hkUserTrade.getGroupId());
		}
	}
	
	/**
	 * 如果 余额 不够扣除利息 和 管理费。够扣哪个扣哪个
	 * 利息和管理费先扣金额大的，金额大的不够扣时，继续扣小金额。如果小金额不够扣，则利息和管理费扣费记录全部为“未支付”状态。
	 * @param wUser
	 * @param hkUserTrade
	 * @param feeDay
	 * @param feeMonth
	 */
	private void deductionFeeDayAndFeeMonth(String userId,HkUserTrade  hkUserTrade,Double feeDay,Double feeMonth){
		// 如果 余额 不够扣除利息 和 管理费。
		//利息和管理费先扣金额大的，金额大的不够扣时，继续扣小金额。如果小金额不够扣，则利息和管理费扣费记录全部为“未支付”状态。
		WUser wUser  = wUserService.get(userId);
		Double  avlBal = ObjectUtil.equals(null,wUser.getAvlBal())?NumberUtils.toDouble("0"):wUser.getAvlBal();
		//资金记录备注
		String feeDayRemark = MessageUtils.message("fee.deduction.management.expense.remark",Dates.format(new Date(),Dates.CHINESE_DATE_FORMAT_LINE),feeDay);
		String feeMonthRemark = MessageUtils.message("fee.deduction.interest.remark",Dates.format(new Date(),Dates.CHINESE_DATE_FORMAT_LINE),feeMonth);

		if (avlBal >= feeDay){
			 deductionMoney(hkUserTrade, 12, feeDay,feeDayRemark,NumberUtils.toShort("1"));
			 deductionMoney(hkUserTrade, 11, feeMonth,feeMonthRemark,NumberUtils.toShort("0"));
		}
		else if (avlBal>=feeMonth){
			 deductionMoney(hkUserTrade, 12, feeDay,feeDayRemark,NumberUtils.toShort("0"));
			 deductionMoney(hkUserTrade, 11, feeMonth,feeMonthRemark,NumberUtils.toShort("1"));
		}
		else
		{
			 deductionMoney(hkUserTrade, 12, feeDay,feeDayRemark,NumberUtils.toShort("0"));
			 deductionMoney(hkUserTrade, 11, feeMonth,feeMonthRemark,NumberUtils.toShort("0"));
		}
	}
	
	/**
	 * 扣除费用
	 */
	private synchronized void deductionMoney(HkUserTrade hkUserTrade,Integer type,Double deductionMoney,String remark,short payStatus){
		if (0 == deductionMoney){
			return;
		}
		WUser  wUser = hkUserTrade.getWuser();
		String userId = wUser.getId();
		UserFund  userFund  = new UserFund(userId, hkUserTrade.getId(), type, -deductionMoney,Dates.getCurrentLongDate(), remark, payStatus);
		userFund.setAddtime(Dates.getCurrentLongDate());
		userFund.setLid(hkUserTrade.getGroupId());
		userFund.setRid(hkUserTrade.getProgramNo());
		try {
			userFundService.deductionMoney(userFund, userId);
			
			Thread.sleep(1000);
		} catch (Exception e) {
			String detail = MessageUtils.message("fee.deduction.money.fail",wUser.getUname(),CacheManager.getDataMapByKey(DataDicKeyConstants.USER_FUND_TYPE, String.valueOf(type)),userFund.getMoney()); 
			logger.error(detail,e);
			String paramsStr = "港股扣费失败！参数：配资id="+hkUserTrade.getId() +",资金记录类型type=："+type+",扣除金额money=："+deductionMoney
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
}

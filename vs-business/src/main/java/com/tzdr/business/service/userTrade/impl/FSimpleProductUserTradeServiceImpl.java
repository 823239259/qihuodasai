package com.tzdr.business.service.userTrade.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.tzdr.business.service.future.FSimpleCouponService;
import com.tzdr.business.service.recharge.RechargeListService;
import com.tzdr.business.service.thread.SMSSendForContentThread;
import com.tzdr.business.service.tradecalendar.TradeDayService;
import com.tzdr.business.service.userTrade.FHandleFtseUserTradeService;
import com.tzdr.business.service.userTrade.FSimpleProductAppendLevelMoneyService;
import com.tzdr.business.service.userTrade.FSimpleProductUserTradeService;
import com.tzdr.business.service.wuser.WUserService;
import com.tzdr.common.baseservice.BaseServiceImpl;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.exception.BusinessException;
import com.tzdr.common.utils.Dates;
import com.tzdr.common.utils.MessageUtils;
import com.tzdr.common.utils.TypeConvert;
import com.tzdr.common.web.support.EasyUiPageInfo;
import com.tzdr.common.web.support.MultiListParam;
import com.tzdr.domain.dao.userTrade.FSimpleFtseUserTradeDao;
import com.tzdr.domain.vo.FSimpleProductUserTradeWebVo;
import com.tzdr.domain.vo.cms.FSimpleUserTradeApplyVo;
import com.tzdr.domain.vo.cms.FSimpleUserTradeEarningVo;
import com.tzdr.domain.vo.cms.FSimpleUserTradePlanVo;
import com.tzdr.domain.web.entity.FSimpleFtseUserTrade;
import com.tzdr.domain.web.entity.FSimpleProductAppendLevelMoney;
import com.tzdr.domain.web.entity.WUser;
import com.tzdr.domain.web.entity.future.FSimpleCoupon;


/**
 *
 * <p>
 * </p>
 *
 * @author WangPinQun
 * @see FSimpleProductUserTradeServiceImpl
 * @version 2.0 2015年9月16日下午14:33:13
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class FSimpleProductUserTradeServiceImpl extends BaseServiceImpl<FSimpleFtseUserTrade, FSimpleFtseUserTradeDao>
		implements FSimpleProductUserTradeService {

	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(FSimpleProductUserTradeServiceImpl.class);

	@Autowired
	private WUserService wUserService;


	@Autowired
	private TradeDayService tradeDayService;

	@Autowired
	private RechargeListService rechargeListService;

	@Autowired
	private FSimpleProductAppendLevelMoneyService fSimpleProductAppendLevelMoneyService;

	@Autowired
	private FHandleFtseUserTradeService fHandleFtseUserTradeService;

	@Autowired
	private FSimpleCouponService fSimpleCouponService;

	@Override
	public PageInfo<Object> getEarningData(EasyUiPageInfo easyUiPage, Map<String, Object> searchParams) {
		// TODO Auto-generated method stub
		String sql = "SELECT f.id," + "f.uid," + "w.mobile," + "v.tname AS username,"
				+ "f.business_type AS businessType," + "f.tran_account AS tranAccount,"
				+ "f.app_time AS appTime," + "f.app_starttime AS appStartTime,"
				+ "f.app_end_time AS appEndTime," + "f.tran_lever AS tranLever,"
				+ "f.trader_bond AS traderBond," + "f.append_trader_bond AS appendTraderBond,"
				+ "f.tran_profit_loss AS tranProfitLoss," + "f.tran_fees_total AS tranFeesTotal,"
				+ "f.update_time AS updateTime," +"f.end_time AS endTime," + "f.state_type AS stateType ,"
				+ "f.end_actual_money AS endActualMoney," +"f.discount_money AS discountMoney," + "f.discount_actual_money AS discountActualMoney "
				+ "FROM f_simple_ftse_user_trade f,w_user w,w_user_verified v "
				+ "WHERE f.uid=w.id AND w.id=v.uid AND f.business_type in (1,2,3,4,20)"
				+ "ORDER BY appTime DESC, stateType ASC, appStartTime DESC";

		MultiListParam multilistParam = new MultiListParam(easyUiPage, searchParams, Lists.newArrayList(), sql);
		PageInfo<Object> planData = multiListPageQuery(multilistParam, FSimpleUserTradeEarningVo.class);
		List<Object> list = planData.getPageResults();
		planData.setPageResults(list);
		return planData;
	}

	@Override
	public PageInfo<Object> getApplyData(EasyUiPageInfo easyUiPage, Map<String, Object> searchParams) {
		// TODO Auto-generated method stub
		String sql = "SELECT f.id," + "f.uid," + "w.mobile," + "v.tname AS username,"
				+ "f.business_type AS businessType," + "f.trader_bond AS traderBond,"+"f.voucher_actual_money AS voucherActualMoney," + "f.line_loss AS lineLoss,"
				+ "f.trader_total AS traderTotal," + "f.tran_lever AS tranLever," + "f.tran_account AS tranAccount,"
				+ "f.tran_password AS tranPassword," + "f.app_time AS appTime," + "f.update_time AS updateTime,"
				+ "f.program_no AS programNo," + "f.state_type AS stateType "
				+ "FROM f_simple_ftse_user_trade f, w_user w, w_user_verified v "
				+ "WHERE f.uid=w.id AND w.id=v.uid AND f.business_type in (1,2,3,4,20) AND f.state_type in (1,4,5)"
				+ "ORDER BY f.state_type ASC, f.app_time DESC";

		MultiListParam multilistParam = new MultiListParam(easyUiPage, searchParams, Lists.newArrayList(), sql);

		return multiListPageQuery(multilistParam, FSimpleUserTradeApplyVo.class);
	}

	@Override
	public PageInfo<Object> getPlanData(EasyUiPageInfo easyUiPage, Map<String, Object> searchParams) {
		// TODO Auto-generated method stub

		String sql = "SELECT f.id," + "f.uid," + "w.mobile," + "v.tname AS username,"
				+ "f.business_type AS businessType," + "f.tran_account AS tranAccount,"
				+ "f.app_starttime AS appStartTime," + "f.app_end_time AS appEndTime," + "f.tran_lever AS tranLever,"
				+ "f.app_time AS appTime,"
				+ "f.trader_bond AS traderBond,"
				+ "f.voucher_actual_money AS voucherActualMoney," // 实际代金券使用金额
				+ "f.trader_total AS traderTotal,"
				+ "IF(ISNULL(f.use_tran_day),0,f.use_tran_day) AS useTranDay," + "f.append_trader_bond AS appendTraderBond,"
				+ "f.tran_profit_loss AS tranProfitLoss,"
//				+ "f.tran_actual_lever tranActualLever,"  //交易手数
				+ "f.tran_fees_total AS tranFeesTotal,"
				+ "f.discount_money as discountMoney," // 抵扣券
				+ "f.discount_actual_money as discountActualMoney," // 抵扣金额
				+ "f.end_amount_cal as endAmountCal,"  // 结算金额 
				+ "f.end_amount AS endAmount," + "f.end_time AS endTime," + "f.program_no AS programNo,"
				+ "f.state_type AS stateType, "
				+ "(select CONVERT(s.type,CHAR) from f_simple_coupon s where f.discount_id = s.id) AS type "
				+ "FROM f_simple_ftse_user_trade f,w_user w,w_user_verified v "
				+ "WHERE f.uid=w.id AND w.id=v.uid AND f.business_type in (1,2,3,4,20) AND  f.state_type in (2,3,4,6) "
				+ "ORDER BY stateType ASC, appEndTime DESC, endTime DESC";

		MultiListParam multilistParam = new MultiListParam(easyUiPage, searchParams, Lists.newArrayList(), sql);

		PageInfo<Object> planData = multiListPageQuery(multilistParam, FSimpleUserTradePlanVo.class);

		List<Object> list = planData.getPageResults();
		List<Object> newList = new ArrayList<>();
		if (list != null) {
			for (Object data : list) {
				FSimpleUserTradePlanVo planVo = (FSimpleUserTradePlanVo) data;
				if(planVo.getStateType()!=6){
					if(planVo.getAppStartTime()!=null){
						planVo.setUseTranDay(getUserTranDay(planVo.getAppStartTime().longValue(), Dates.getCurrentLongDate()));
					}
				}
				newList.add(planVo);
			}

//			Collections.sort(newList, new Comparator<Object>() {
//				@Override
//				public int compare(Object o1, Object o2) {
//					// TODO Auto-generated method stub
//					FSimpleUserTradePlanVo vo1=(FSimpleUserTradePlanVo)o1;
//					FSimpleUserTradePlanVo vo2=(FSimpleUserTradePlanVo)o2;
//					int stateTypeValue=0;
//					if(vo1.getStateType()!=null && vo2.getStateType()!=null){
//						stateTypeValue=vo1.getStateType().compareTo(vo2.getStateType());
//					}
//					int useTranDayValue=0;
//					if(vo2.getUseTranDay()!=null && vo1.getUseTranDay()!=null){
//						useTranDayValue=vo2.getUseTranDay().compareTo(vo1.getUseTranDay());	
//					}
//					int appStartTimeValue=0;
//					if(vo2.getAppStartTime()!=null && vo1.getAppStartTime()!=null){						
//						appStartTimeValue=vo2.getAppStartTime().compareTo(vo1.getAppStartTime());
//					}
//					int[] arr={stateTypeValue, useTranDayValue, appStartTimeValue};
//					for(int value:arr){
//						if(value!=0){
//							return value;
//						}
//					}
//					return 0;
//				}
//			});
		}

		planData.setPageResults(newList);
		return planData;
	}

	@Override
	public synchronized void refuseApply(String id) throws Exception {
		// TODO Auto-generated method stub
		FSimpleFtseUserTrade userTrade = get(id);
		if (userTrade == null) {
			return;
		}
		// 状态为开户中才能拒绝申请
		if (userTrade.getStateType() == 1) {
			WUser wUser = wUserService.get(userTrade.getUid());
			// 修改申请记录的状态
			userTrade.setStateType(5);
			userTrade.setUpdateTime(Dates.getCurrentLongDate());
			update(userTrade);
//			fHandleFtseUserTradeService.save(getFHandleFtseUserTrade(userTrade));
			int type = userTrade.getBusinessType().intValue();

			//添加充值记录，内含资金明细记录添加和账户余额修改
			rechargeListService.futureHandlerSaveRechargeStateWeb(userTrade.getProgramNo(),
					wUser.getMobile(), userTrade.getTraderBond().toString(), "【" + getTypeName(type) + "】开户申请不通过，保证金返还", TypeConvert.SYS_TYPE_ADJUSTMENT_OF_ACCOUNTS);
		}
	}

	@Override
	public void editAccount(String id, String tranAccount, String tranPassword, int type) throws Exception {
		// TODO Auto-generated method stub
		FSimpleFtseUserTrade userTrade = get(id);
		if (userTrade == null) {
			return;
		}
		// 状态不为“审核不通过”
		if (userTrade.getStateType() != 5) {
			WUser wUser = wUserService.get(userTrade.getUid());
			userTrade.setStateType(4);
			userTrade.setTranAccount(tranAccount);
			userTrade.setTranPassword(tranPassword);
			userTrade.setUpdateTime(Dates.getCurrentLongDate());
			if (type == 1) {
				userTrade.setAppStarttime(Dates.getCurrentLongDate());
			}
			update(userTrade);
//			fHandleFtseUserTradeService.save(getFHandleFtseUserTrade(userTrade));
			if (type == 1) {
				String content = MessageUtils.message("commodity.future.apply.audit.success",
						getTypeName(userTrade.getBusinessType().intValue()),userTrade.getTranLever());
				new SMSSendForContentThread(wUser.getMobile(), content, 2000).start();
			}
		}
	}

	@Override
	public synchronized void editResult(String id, BigDecimal tranProfitLoss, BigDecimal tranFeesTotal) {
		// TODO Auto-generated method stub
		FSimpleFtseUserTrade userTrade = get(id);
		if (userTrade == null) {
			return;
		}

		BigDecimal discountActualMoney =new BigDecimal(0);
		if (userTrade.getStateType() == 2 || userTrade.getStateType() == 3 || userTrade.getStateType()==4) {
			// 总保证金=操盘保证金+补充保证金总和
			BigDecimal bond = userTrade.getTraderBond();
			if (userTrade.getAppendTraderBond() != null) {
				bond = bond.add(userTrade.getAppendTraderBond());
			}
			userTrade.setTranProfitLoss(tranProfitLoss);
			if(null != userTrade.getDiscountId()) {
				FSimpleCoupon voucher = fSimpleCouponService.get(userTrade.getDiscountId());
				if (voucher.getType() == 3) {
					// -------------新增折扣券--------------------
					//折扣券(折)
					BigDecimal discountMoney = userTrade.getDiscountMoney();
					//抵扣券抵扣手续费
					if (discountMoney != null) {
						//未折扣的管理费
						BigDecimal unDiscountMoney = tranFeesTotal;
						//折扣=折扣券(折)/10
						discountMoney = discountMoney.divide(new BigDecimal(10));
						//折扣后交易手续费总额=交易手续费总额*折扣
						tranFeesTotal = tranFeesTotal.multiply(discountMoney, MathContext.DECIMAL32);
						//抵扣券抵扣手续费总额=交易手续费总额-折扣后交易手续费总额
						discountActualMoney = unDiscountMoney.subtract(tranFeesTotal);
						userTrade.setDiscountActualMoney(discountActualMoney);
					}
					// -------------新增折扣券--------------------
					// -------------新增抵扣卷--------------------
				} else if (voucher.getType() == 6) {
					BigDecimal discountMoney = userTrade.getDiscountMoney();
					//抵扣券抵扣手续费
					if (discountMoney != null) {
						//折扣后交易手续费总额=交易手续费总额*折扣
						if (tranFeesTotal.compareTo(discountMoney) >= 0) {
							discountActualMoney = discountMoney;
							tranFeesTotal = tranFeesTotal.subtract(discountMoney);
						} else {
							discountActualMoney = tranFeesTotal;
							tranFeesTotal = new BigDecimal(0);
						}
						//抵扣券抵扣手续费总额=交易手续费总额-折扣后交易手续费总额
						userTrade.setDiscountActualMoney(discountActualMoney);
					}
				}
			}
			userTrade.setTranFeesTotal(tranFeesTotal);

			// -------------新增抵扣券--------------------
			// 结算金额=总保证金+交易盈亏-交易手续费
			BigDecimal  endAmountCal  = bond.add(tranProfitLoss).subtract(tranFeesTotal);
			userTrade.setEndAmountCal(endAmountCal);  //设置结算金额
			//实际结算金额
			BigDecimal endAmount = endAmountCal;
			//实际抵扣金额
			BigDecimal endActualMoney = new BigDecimal("0");
			//实际代金券(元)
			BigDecimal voucherActualMoney = userTrade.getVoucherActualMoney();
			if(voucherActualMoney != null && voucherActualMoney.compareTo(new BigDecimal("0")) > 0 && endAmount.compareTo(new BigDecimal("0")) < 0){
				//结算金额+实际代金券(元)
				BigDecimal money = endAmountCal.add(voucherActualMoney);
				if(money.compareTo(new BigDecimal("0")) >= 0){
					endActualMoney = new BigDecimal("0").subtract(endAmountCal);
					endAmount = new BigDecimal("0");
				}else{
					endActualMoney = voucherActualMoney;
					endAmount = voucherActualMoney.add(endAmountCal);
				}
			}
			userTrade.setEndActualMoney(endActualMoney);  //设置实际抵扣金额
			userTrade.setEndAmount(endAmount); //设置实际结算金额
			// -------------新增折扣券--------------------

			// 修改操盘记录
			userTrade.setStateType(3);
			userTrade.setUpdateTime(Dates.getCurrentLongDate());
			update(userTrade);
		}
	}

	@Override
	public String settle(String id) throws Exception{
		// TODO Auto-generated method stub
		FSimpleFtseUserTrade userTrade = get(id);
		if (userTrade == null) {
			return null;
		}
		if(userTrade.getStateType() == 6){
			return "该方案已结算，请刷新后查看";
		}
		if (userTrade.getStateType() == 3) {
			WUser wUser = wUserService.get(userTrade.getUid());
			// 可用金额
			BigDecimal avlBal = new BigDecimal(wUser.getAvlBal());
			// 退回结算金额
			avlBal=avlBal.add(userTrade.getEndAmount());
			// 修改操盘记录
			userTrade.setStateType(6);
			userTrade.setEndTime(Dates.getCurrentLongDate());
			userTrade.setUpdateTime(Dates.getCurrentLongDate());
			userTrade.setUseTranDay(getUserTranDay(userTrade.getAppStarttime(), Dates.getCurrentLongDate()).intValue());
			update(userTrade);
//			fHandleFtseUserTradeService.save(getFHandleFtseUserTrade(userTrade));
			// 添加资金明细
			int type = userTrade.getBusinessType().intValue();
			String remark = "操盘结算";
			if (avlBal.doubleValue() < 0) {
				remark = "操盘穿仓欠费";
			}
			String sysType=TypeConvert.SYS_TYPE_ADJUSTMENT_OF_ACCOUNTS;
			//结算金额为负时，明细记为“系统冲账”
			if(userTrade.getEndAmount().doubleValue()<0){
				sysType=TypeConvert.SYS_TYPE_OFFSET_ACCOUNTS;
			}

			//添加充值记录，内含资金明细记录添加和账户余额修改
			rechargeListService.futureHandlerSaveRechargeStateWeb(userTrade.getProgramNo(),
					wUser.getMobile(), userTrade.getEndAmount().toString(), "【" + getTypeName(type) + "】" + remark, sysType);
		}
		return "";

	}

	@Override
	public FSimpleFtseUserTrade executePayable(
			FSimpleFtseUserTrade fSimpleFtseUserTrade, String mobile,
			BigDecimal payable) throws Exception {

		fSimpleFtseUserTrade.setAppTime(TypeConvert.dbDefaultDate());
		this.save(fSimpleFtseUserTrade);
		fSimpleFtseUserTrade.setProgramNo("GT" + fSimpleFtseUserTrade.getId());
		this.update(fSimpleFtseUserTrade);
		Integer type = fSimpleFtseUserTrade.getBusinessType();
		String remark="";
		if(type == 1){
			remark ="投资沪金申请(划款)。";
		}else if(type == 2){
			remark ="投资沪银申请(划款)。";
		}else if(type == 3){
			remark ="投资沪铜申请(划款)。";
		}else if(type == 4){
			remark ="投资橡胶申请(划款)。";
		}else if(type == 20){
			remark ="投资国际综合申请(划款)。";
		}
//		fHandleFtseUserTradeService.saveHandleFtseUserTrade(fSimpleFtseUserTrade); // 保存商品期货收益报表记录

		rechargeListService.futureHandlerSaveRechargeStateWeb("GT"+ fSimpleFtseUserTrade.getId(),
				mobile, payable.toString(), remark, TypeConvert.SYS_TYPE_OFFSET_ACCOUNTS);
		return fSimpleFtseUserTrade;
	}

	@Override
	public FSimpleFtseUserTrade executePayable(
			FSimpleFtseUserTrade fSimpleFtseUserTrade, FSimpleCoupon voucher, String mobile,
			BigDecimal payable) throws Exception {

		fSimpleFtseUserTrade.setAppTime(TypeConvert.dbDefaultDate());
		this.save(fSimpleFtseUserTrade);
		// 更新优惠券状态为已使用
		voucher.setStatus(new Short("3"));
		voucher.setUseTime(TypeConvert.dbDefaultDate());
		this.fSimpleCouponService.update(voucher);
		fSimpleFtseUserTrade.setProgramNo("GT" + fSimpleFtseUserTrade.getId());
		this.update(fSimpleFtseUserTrade);
		Integer type = fSimpleFtseUserTrade.getBusinessType();
		String remark="";
		if(type == 1){
			remark ="投资沪金申请(划款)。";
		}else if(type == 2){
			remark ="投资沪银申请(划款)。";
		}else if(type == 3){
			remark ="投资沪铜申请(划款)。";
		}else if(type == 4){
			remark ="投资橡胶申请(划款)。";
		}else if(type == 20){
			remark ="投资国际综合申请(划款)。";
		}
//		fHandleFtseUserTradeService.saveHandleFtseUserTrade(fSimpleFtseUserTrade); // 保存商品期货收益报表记录

		rechargeListService.futureHandlerSaveRechargeStateWeb("GT"+ fSimpleFtseUserTrade.getId(),
				mobile, payable.toString(), remark, TypeConvert.SYS_TYPE_OFFSET_ACCOUNTS);
		return fSimpleFtseUserTrade;
	}

	@SuppressWarnings("unchecked")
	@Override
	public PageInfo<FSimpleProductUserTradeWebVo> findDataList(
			String pageIndex, String perPage,  String uid) {
		PageInfo<FSimpleProductUserTradeWebVo> pageInfo= new PageInfo<FSimpleProductUserTradeWebVo>(Integer.valueOf(perPage),Integer.valueOf(pageIndex)+1);
		pageInfo = this.getEntityDao().queryPageBySql(pageInfo," SELECT * FROM f_simple_ftse_user_trade f WHERE f.business_type in (1,2,3,4,20) AND f.uid = ? ORDER BY f.app_time DESC",
				FSimpleProductUserTradeWebVo.class,null,uid);
		return pageInfo;
	}

	@Override
	public void addAppendTraderBond(FSimpleFtseUserTrade fSimpleFtseUserTrade,
									Double appendMoney, WUser wuser) throws Exception {
		Integer type = fSimpleFtseUserTrade.getBusinessType();
		String remark="";
		if(type == 1){
			remark ="商品期货(沪金)追加保证金。";
		}else if(type == 2){
			remark ="商品期货(沪银)追加保证金。";
		}else if(type == 3){
			remark ="商品期货(沪铜)追加保证金。";
		}else if(type == 4){
			remark ="商品期货(橡胶)追加保证金。";
		}else if(type == 20){
			remark = "商品期货(商品综合)追加保证金";
		}
		BigDecimal payMoney = new BigDecimal(appendMoney);  //追加保证金

		fSimpleFtseUserTrade.setAppendTraderBond(TypeConvert.scale(fSimpleFtseUserTrade.getAppendTraderBond().add(payMoney),2));

		//追加保证金划款
		rechargeListService.futureHandlerSaveRechargeStateWeb(fSimpleFtseUserTrade.getProgramNo(),
				wuser.getMobile(), appendMoney.toString(), remark, TypeConvert.SYS_TYPE_OFFSET_ACCOUNTS);

		//更新股指追加保证金
		this.update(fSimpleFtseUserTrade);

		//创建追加保证金记录
		FSimpleProductAppendLevelMoney fSimpleProductAppendLevelMoney = new FSimpleProductAppendLevelMoney(wuser.getId(),fSimpleFtseUserTrade.getProgramNo(),appendMoney,type);

		fSimpleProductAppendLevelMoneyService.save(fSimpleProductAppendLevelMoney);  //保存追加保证金记录

	}

	/**
	 * 根据类型获取类型名称
	 *
	 * @param type
	 * @return
	 */
	private String getTypeName(int type) {
		switch (type) {
			case 1:
				return "沪金期货";
			case 2:
				return "沪银期货";
			case 3:
				return "沪铜期货";
			case 4:
				return "橡胶期货";
			case 20:
				return "商品综合";
			default:
				break;
		}
		return "";
	}

	/**
	 * 获取已操盘时间
	 *
	 * @param appStartTime
	 * @param appEndTime
	 * @return
	 */
	private BigInteger getUserTranDay(Long appStartTime, Long appEndTime) {
		int day=0;
		Date startTime = Dates.parseLong2Date(appStartTime);
		Date endTime = Dates.parseLong2Date(appEndTime);
		String startTimeValue = Dates.format(startTime, "yyyyMMdd");
		String endTimeValue = Dates.format(endTime, "yyyyMMdd");
		//当天
		if(startTimeValue.equals(endTimeValue)){
			if(isBeforeTwelve(startTime)){
				day=1;
			}
		}else{
			day=tradeDayService.getTradeDays(startTimeValue, endTimeValue);
			if(!isBeforeTwelve(startTime)){
				day=day-1;
			}
		}
		return new BigInteger(String.valueOf(day));
	}

	@Override
	protected FSimpleFtseUserTradeDao getEntityDao() throws BusinessException {
		return super.getEntityDao();
	}

	/**
	 * 判断是否为14:00:00之前
	 * @param date
	 * @return
	 */
	private boolean isBeforeTwelve(Date date){
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(date);
		int hour=calendar.get(Calendar.HOUR_OF_DAY);
		int minute=calendar.get(Calendar.MINUTE);
		int second=calendar.get(Calendar.SECOND);
		if(hour<14){
			return true;
		}else if(hour==14 && minute==0 && second==0){
			return true;
		}
		return false;
	}

	@Override
	public void updateFSimpleFtseUserTradeAndFSimpleCoupon(FSimpleFtseUserTrade fSimpleFtseUserTrade, FSimpleCoupon discount) throws BusinessException {
		// 更新优惠券状态为已使用
		discount.setStatus(new Short("3"));
		discount.setUseTime(TypeConvert.dbDefaultDate());
		this.fSimpleCouponService.update(discount);

		this.update(fSimpleFtseUserTrade);
	}

//	/**
//	 * 获取商品期货收益报表记录
//	 * @param userTrade
//	 * @return
//	 */
//	private FHandleFtseUserTrade getFHandleFtseUserTrade(FSimpleFtseUserTrade userTrade){
//		FHandleFtseUserTrade fHandleFtseUserTrade=new FHandleFtseUserTrade();
//		fHandleFtseUserTrade.setUid(userTrade.getUid());
//		fHandleFtseUserTrade.setProgramNo(userTrade.getProgramNo());
//		fHandleFtseUserTrade.setTraderBond(userTrade.getTraderBond());
//		fHandleFtseUserTrade.setAppendTraderBond(userTrade.getAppendTraderBond());
//		fHandleFtseUserTrade.setTranLever(userTrade.getTranLever());
//		fHandleFtseUserTrade.setTraderTotal(userTrade.getTraderTotal());
//		fHandleFtseUserTrade.setLineLoss(userTrade.getLineLoss());
//		fHandleFtseUserTrade.setFeeManage(userTrade.getFeeManage());
//		fHandleFtseUserTrade.setTranFees(userTrade.getTranFees());
//		fHandleFtseUserTrade.setAppTime(userTrade.getAppTime());
//		fHandleFtseUserTrade.setAppStarttime(userTrade.getAppStarttime());
//		fHandleFtseUserTrade.setTranAccount(userTrade.getTranAccount());
//		fHandleFtseUserTrade.setTranPassword(userTrade.getTranPassword());
//		fHandleFtseUserTrade.setAppEndTime(userTrade.getAppEndTime());
//		fHandleFtseUserTrade.setTranActualLever(userTrade.getTranActualLever());
//		fHandleFtseUserTrade.setTranFeesTotal(userTrade.getTranFeesTotal());
//		fHandleFtseUserTrade.setTranProfitLoss(userTrade.getTranProfitLoss());
//		fHandleFtseUserTrade.setEndAmount(userTrade.getEndAmount());
//		fHandleFtseUserTrade.setEndTime(userTrade.getEndTime());
//		fHandleFtseUserTrade.setUseTranDay(userTrade.getUseTranDay());
//		fHandleFtseUserTrade.setStateType(userTrade.getStateType());
//		fHandleFtseUserTrade.setUpdateTime(userTrade.getUpdateTime());
//		fHandleFtseUserTrade.setBusinessType(userTrade.getBusinessType());
////		fHandleFtseUserTrade.setEndParities(userTrade.getEndParities());
////		fHandleFtseUserTrade.setGoldenMoney(userTrade.getGoldenMoney());
//		return fHandleFtseUserTrade;
//	}

}

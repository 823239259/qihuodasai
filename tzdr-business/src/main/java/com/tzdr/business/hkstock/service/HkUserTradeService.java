package com.tzdr.business.hkstock.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import jodd.util.ObjectUtil;
import jodd.util.StringUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.tzdr.business.exception.UserTradeException;
import com.tzdr.business.hkstock.exception.HkUserTradeException;
import com.tzdr.business.service.exception.UserTradeConcurrentException;
import com.tzdr.business.service.pay.UserFundService;
import com.tzdr.business.service.thread.SMSSenderThread;
import com.tzdr.business.service.tradecalendar.TradeDayService;
import com.tzdr.business.service.userTrade.FSimpleParitiesService;
import com.tzdr.business.service.wuser.WUserService;
import com.tzdr.common.baseservice.BaseServiceImpl;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.utils.BigDecimalUtils;
import com.tzdr.common.utils.ConnditionVo;
import com.tzdr.common.utils.Dates;
import com.tzdr.common.utils.RandomCodeUtil;
import com.tzdr.common.utils.TypeConvert;
import com.tzdr.common.web.support.EasyUiPageInfo;
import com.tzdr.common.web.support.MultiListParam;
import com.tzdr.domain.constants.Constant;
import com.tzdr.domain.hkstock.constants.HkConstant;
import com.tzdr.domain.hkstock.dao.HkUserTradeDao;
import com.tzdr.domain.hkstock.entity.HkUserTrade;
import com.tzdr.domain.hkstock.entity.HkUserTradeExtend;
import com.tzdr.domain.hkstock.vo.HKEndMoneyVo;
import com.tzdr.domain.hkstock.vo.HKUserTradeWebVo;
import com.tzdr.domain.hkstock.vo.HkArrearsEndDetailVo;
import com.tzdr.domain.hkstock.vo.HkArrearsEndVo;
import com.tzdr.domain.hkstock.vo.HkEarningsVo;
import com.tzdr.domain.hkstock.vo.HkTradeManageVo;
import com.tzdr.domain.hkstock.vo.HkUserTradFeeDuductionVo;
import com.tzdr.domain.vo.BillingFeeVo;
import com.tzdr.domain.vo.EarningsVo;
import com.tzdr.domain.vo.TotalMarginVo;
import com.tzdr.domain.vo.UserTradeCmsVo;
import com.tzdr.domain.web.entity.FSimpleParities;
import com.tzdr.domain.web.entity.ParentAccount;
import com.tzdr.domain.web.entity.UserFund;
import com.tzdr.domain.web.entity.UserTrade;
import com.tzdr.domain.web.entity.WUser;

/**
 * 
 * @author zhouchen 2015年10月16日 上午11:45:32
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class HkUserTradeService extends BaseServiceImpl<HkUserTrade, HkUserTradeDao> {
	
	@SuppressWarnings("unused")
	private static Logger log = LoggerFactory.getLogger(HkUserTradeService.class);

	@Autowired
	private HkUserTradeExtendService hkUserTradeExtendService;

	@Autowired
	private FSimpleParitiesService fSimpleParitiesService;

	@Autowired
	private WUserService wUserService;

	@Autowired
	private UserFundService userFundService;

	@Autowired
	private HkEndTradeService hkEndTradeService;

	@Autowired
	private HkTradeCalendarService hkTradeCalendarService;
	
	@Autowired
	private TradeDayService tradeDayService;

	private Lock lock = new ReentrantLock();

	/**
	 * 提供后台获取欠费待终结数据
	 * 
	 * @param easyUiPage
	 * @param searchParams
	 * @return
	 */
	public PageInfo<Object> getArrearsData(EasyUiPageInfo easyUiPage, Map<String, Object> searchParams) {
		String sql = "SELECT t.group_id as groupId," + "v.tname," + "u.mobile,p.trade_channel AS tradeChannel,"
				+ "t.account_no AS accountNo," + "t.program_no AS programNo," + "u.avl_bal AS avlBal,"
				+ "sum(f.money) AS money," + "min(f.addtime) AS addTime " + "FROM hk_user_trade t "
				+ "LEFT JOIN hk_parent_account p ON t.parent_account_id=p.id " + "LEFT JOIN w_user u ON t.uid=u.id "
				+ "LEFT JOIN w_user_verified v ON v.uid = u.id "
				+ "LEFT JOIN w_user_fund f ON f.lid=t.group_id AND f.rid=t.program_no "
				+ "WHERE f.pay_status = 0 AND f.money < 0 AND t.status = 1 " + "GROUP BY t.group_id";
		MultiListParam multilistParam = new MultiListParam(easyUiPage, searchParams, Lists.newArrayList(), sql);
		return multiListPageQuery(multilistParam, HkArrearsEndVo.class);
	}

	/**
	 * 批量申请终结方案
	 * 
	 * @param groupIds
	 * @throws Exception
	 */
	public void endPlan(String[] groupIds){
		Set<String> set = Sets.newHashSet();
		for (String groupId : groupIds) {
			set.add(groupId);
		}
		Iterator<String> iterator = set.iterator();
		while (iterator.hasNext()) {
			endPlan(iterator.next());
		}
	}

	/**
	 * 申请终结方案
	 * 
	 * @param groupId
	 * @throws Exception
	 */
	public void endPlan(String groupId){
		List<HkUserTrade> list = getEntityDao().findByGroupId(groupId);
		if (CollectionUtils.isEmpty(list)) {
			throw new HkUserTradeException("business.update.not.found.data", null);
		}
		Iterator<HkUserTrade> iterator = list.iterator();
		// 获取系统当前最新港币汇率
		FSimpleParities fSimpleParities = fSimpleParitiesService.getFSimpleParities(2);
		if (fSimpleParities == null || fSimpleParities.getParities() == null
				|| fSimpleParities.getParities().compareTo(new BigDecimal("0")) <= 0) {
			throw new UserTradeConcurrentException("com.tzdr.business.server.connection.timeout", null);
		}
		while (iterator.hasNext()) {
			HkUserTrade hkUserTrade = iterator.next();
			HkUserTradeExtend hkUserTradeExtend = hkUserTradeExtendService.findByTradeId(hkUserTrade.getId());
			if (ObjectUtil.equals(null, hkUserTradeExtend)) {
				throw new HkUserTradeException("business.update.not.found.data", null);
			}

			if (hkUserTradeExtend.getAuditEndStatus()==0 
					||hkUserTradeExtend.getAuditEndStatus()==1 ){
				throw new UserTradeException("user.trade.already.end",
						null);
			}
			
			if (hkUserTradeExtend.getAuditEndStatus() == 0) {
				throw new HkUserTradeException("apply.trade.update.processed.data",new Object[]{hkUserTrade.getGroupId()});
			}else if(hkUserTradeExtend.getAuditEndStatus()==1){
				throw new HkUserTradeException("end.trade.update.processed.data",new Object[]{hkUserTrade.getGroupId()});
			}
			
			// 修改为终结 审核状态（-1：默认； 0：终结待审核 1：方案终结审核通过 2：方案终结审核不通过；）
			hkUserTradeExtend.setAuditEndStatus(0);
			// 提交审核时间
			hkUserTradeExtend.setEndSubTime(Dates.getCurrentLongDate());
			hkUserTradeExtendService.update(hkUserTradeExtend);
			
			hkUserTrade.setEndExchangeRate(fSimpleParities.getParities().doubleValue());
			update(hkUserTrade);
		}
	}

	/**
	 * 提供后台获取欠费明细
	 * 
	 * @param easyUiPage
	 * @param searchParams
	 * @return
	 */
	public PageInfo<Object> getArrearsDetail(EasyUiPageInfo easyUiPage, Map<String, Object> searchParams) {
		String sql = "SELECT f.addtime AS addTime," + "f.money, t.group_id AS groupId " + "FROM hk_user_trade t,w_user_fund f "
				+ "WHERE f.lid=t.group_id AND f.pay_status=0 AND f.money<0 AND t.status=1 " + "ORDER BY addTime";
		MultiListParam multilistParam = new MultiListParam(easyUiPage, searchParams, Lists.newArrayList(), sql);
		return multiListPageQuery(multilistParam, HkArrearsEndDetailVo.class);
	}

	/**
	 * 提供后台获取方案管理数据
	 * 
	 * @param easyUiPage
	 * @param searchParams
	 * @return
	 */
	public PageInfo<Object> getTradeData(EasyUiPageInfo easyUiPage, Map<String, Object> searchParams) {
		String sql = "SELECT t.id AS tradeId," + "t.finished_money AS finishedMoney, t.startdays,sum(t.accrual) accrual, "
				+ "t.end_exchange_rate AS endExchangeRate," + "u.mobile," + "v.tname," + "u.activity_type userType,"
				+ "t.account_name AS accountName," + "t.account_no AS accountNo," + "t.group_id AS groupId,"
				+ "SUM(t.lever_money) AS leverMoney," + "SUM(t.append_lever_money) AS appendLeverMoney,"
				+ "SUM(t.money) AS money," + "t.total_lever_money AS totalLeverMoney," + "SUM(t.warning) AS warning,"
				+ "SUM(t.open) AS open," + "t.lever," + "t.status," + "a.trade_channel AS tradeChannel,"
				+ "MIN(t.addtime) AS addtime," + "MIN(t.starttime) AS starttime," + "t.endtime,"
				+ "MIN(t.estimate_endtime) AS estimateEndtime," + "t.type AS activityType " + "FROM hk_user_trade t "
				+ "LEFT JOIN hk_parent_account a ON a.id=t.parent_account_id " + "LEFT JOIN w_user u ON u.id=t.uid "
				+ "LEFT JOIN w_user_verified v ON v.uid=u.id " + "GROUP BY t.group_id " + "ORDER BY t.addtime DESC";
		MultiListParam multilistParam = new MultiListParam(easyUiPage, searchParams, Lists.newArrayList(), sql);
		PageInfo<Object> tradeData = multiListPageQuery(multilistParam, HkTradeManageVo.class);
		List<Object> list = tradeData.getPageResults();
		List<Object> newList = new ArrayList<>();
		if (list != null) {
			for (Object data : list) {
				HkTradeManageVo tradeManageVo = (HkTradeManageVo) data;
				tradeManageVo.setTradingDays(getTradeDays(tradeManageVo));
				newList.add(tradeManageVo);
			}
		}
		tradeData.setPageResults(newList);
		return tradeData;
	}

	/**
	 * 根据groupid查询子方案
	 * 
	 * @param easyUiPage
	 * @param searchParams
	 * @return
	 */
	public PageInfo<Object> queryChildTrades(EasyUiPageInfo easyUiPage, Map<String, Object> searchParams) {
		String sql = "SELECT id AS tradeId," + "program_no AS programNo," + "lever_money AS leverMoney," + "money,"
				+ "total_lever_money AS totalLeverMoney," + "addtime," + "fee_day AS feeDay," + "group_id AS groupId," + "apr "
				+ "FROM hk_user_trade " + "ORDER BY addtime ASC";
		MultiListParam multilistParam = new MultiListParam(easyUiPage, searchParams, Lists.newArrayList(), sql);
		return multiListPageQuery(multilistParam, HkTradeManageVo.class);
	}

	/**
	 * 获取港股已操盘时间
	 * 
	 * @param tradeManageVo
	 * @return
	 */
	public Long getTradeDays(HkTradeManageVo tradeManageVo) {
		if (tradeManageVo.getStatus() == 0) {
			return (long) 0;
		}
		String startDay = Dates.parseBigInteger2Date(tradeManageVo.getStarttime(), Dates.CHINESE_DATE_FORMAT_LONG);
		// 操盘中
		if (tradeManageVo.getStatus() == 1) {
			String today = Dates.format(Dates.CHINESE_DATE_FORMAT_LONG);
			return (long) hkTradeCalendarService.getTradeDays(startDay, today);
		}
		String endDay = Dates.parseBigInteger2Date(tradeManageVo.getEndtime(), Dates.CHINESE_DATE_FORMAT_LONG);
		return (long) hkTradeCalendarService.getTradeDays(startDay, endDay);
	}

	/**
	 * 根据组合号查找 方案
	 * 
	 * @param groupid
	 * @return
	 */
	public List<HkUserTrade> findByGroupId(String groupId) {
		return this.getEntityDao().findByGroupIdOrderByAddtimeAsc(groupId);
	}

	/**
	 * 根据组合号查找 方案
	 * @param groupid
	 * @return
	 */
	public List<HkUserTrade> findByGroupIdOrderByAddtimeAsc(String groupId) {
		return this.getEntityDao().findByGroupIdOrderByAddtimeAsc(groupId);
	}
	
	/**
	 * 根据方案组合号，获取配资基本信息
	 * 
	 * @param groupId
	 *            方案组合号
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "deprecation" })
	public HKEndMoneyVo endOfProgrameSum(String groupId) {
		String sql = " SELECT SUM(t.money) money,SUM(t.lever_money) leverMoney,"
				+ " SUM(t.append_lever_money) appendLeverMoney" + " FROM hk_user_trade t" + " WHERE  t.group_id = ?";
		List<HKEndMoneyVo> list = this.getEntityDao().queryBySql(sql, HKEndMoneyVo.class, groupId);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * 合计值
	 * 
	 * @param billFees
	 *            List<BillingFeeVo> 方案终结计费信息
	 * @param type
	 *            费用类型
	 * @return BigDecimal
	 */
	private BigDecimal getTotalFee(List<BillingFeeVo> billFees, Integer type) {
		BigDecimal zero = new BigDecimal(0);
		if (type == null) {
			return zero;
		}
		if (billFees == null || billFees.size() == 0) {
			return zero;
		}
		for (BillingFeeVo fee : billFees) {
			if (fee.getType() == type.intValue()) {
				return new BigDecimal(fee.getMoney());
			}
		}
		return zero;
	}

	/**
	 * 累计保证金
	 * 
	 * @param groupId
	 *            方案组合号
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "deprecation" })
	public BigDecimal totalLevelMoneyByGroupId(String groupId) {
		String sql = "SELECT SUM(lever_money) totalAmount FROM hk_user_trade WHERE group_id = ? ";
		List<TotalMarginVo> voes = this.getEntityDao().queryBySql(sql, TotalMarginVo.class, groupId);
		return new BigDecimal(voes.get(0).getTotalAmount());
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void endOfWellGoldProgram(String groupId, Double assetTotalValue) {
		lock.lock();
		try {
			// 校验
			if (groupId == null || "".equals(groupId)) {
				throw new UserTradeConcurrentException("com.tzdr.business.agent.error",
						new String[] { "组合ID不能为空执行失败!" });
			}
			if (assetTotalValue == null || assetTotalValue < 0) {
				throw new UserTradeConcurrentException("com.tzdr.business.agent.error",
						new String[] { "结算金额不能为空或小于0!" });
			}

			List<HkUserTrade> userTrades = this.findByGroupId(groupId);
			if (userTrades == null || userTrades.size() <= 0) {
				throw new UserTradeConcurrentException("com.tzdr.business.server.connection.timeout", null);
			}

			HkUserTrade userTrade = userTrades.get(0);

			if (userTrade.getStatus() == 2) {
				throw new UserTradeConcurrentException("com.tzdr.business.not.end.repeat", null);
			}

			if (userTrade.getEndExchangeRate() == null
					|| new BigDecimal(userTrade.getEndExchangeRate()).compareTo(new BigDecimal("0")) <= 0) {
				throw new UserTradeConcurrentException("com.tzdr.business.server.connection.timeout", null);
			}

			// 结算汇率
			double endExchangeRate = userTrade.getEndExchangeRate();

			// 1)结算金额 、结算汇率
			userTrade.setFinishedMoney(assetTotalValue);
			userTrade.setEndExchangeRate(endExchangeRate);

			// 获取groupId所属配资金额，保证金合计
			HKEndMoneyVo endMoneyVo = this.endOfProgrameSum(groupId);

			// 2)盈亏金额计算｛结算金额 - 总操盘资金｝* 结算汇率 = 盈亏
			Double accrualValue = BigDecimalUtils.sub(assetTotalValue, userTrade.getTotalLeverMoney());
			accrualValue = BigDecimalUtils.mulRound(accrualValue, endExchangeRate);

			userTrade.setAccrual(accrualValue);

			// 当：【 结算金额 - 总操盘资金 < 0】算式结果小于0 时为"亏损"的情况
			BigDecimal arrearsMoneyBig = new BigDecimal(
					BigDecimalUtils.sub(assetTotalValue, userTrade.getTotalLeverMoney()));

			WUser argWuser = userTrade.getWuser();
			WUser wuser = this.wUserService.get(argWuser.getId());

			// 用户可用余额
			// 未亏损
			// arrearsMoneyBig
			if (arrearsMoneyBig.compareTo(new BigDecimal("0")) >= 0) {
				// 3)撤回配置资金，并记入撤回流水()[配资保证金 +追加保证金+ 盈亏 = 撤回配置资金]
				Double revocationMoney = BigDecimalUtils.add(endMoneyVo.getLeverMoney(), accrualValue);
				revocationMoney = BigDecimalUtils.add(endMoneyVo.getAppendLeverMoney(), revocationMoney);

				// arrearsMoney >= 0
				UserFund revocationRecord = new UserFund();
				revocationRecord.setMoney(revocationMoney);
				revocationRecord.setType(TypeConvert.USER_FUND_C_TYPE_REVOCATION);
				revocationRecord.setPayStatus(TypeConvert.PAID);
				revocationRecord.setRemark(TypeConvert.payRemark("配资撤回", revocationRecord.getMoney()));
				// 3.2记入撤回流水
				revocationRecord.setUid(wuser.getId());
				revocationRecord.setLid(groupId);
				this.userFundService.arrearsProcess(revocationRecord);
			} else { // 亏损
				// 3)爆仓金额 = (配资保证金+追加保证金) - 亏损金额
				Double arrearsMoney = BigDecimalUtils.sub(BigDecimalUtils.add(endMoneyVo.getLeverMoney(), endMoneyVo.getAppendLeverMoney()), Math.abs(accrualValue));

				if (new BigDecimal(arrearsMoney).compareTo(new BigDecimal("0")) >= 0) { // 未爆仓
					UserFund revocationRecord = new UserFund();
					revocationRecord.setMoney(arrearsMoney);
					revocationRecord.setType(TypeConvert.USER_FUND_C_TYPE_REVOCATION);
					revocationRecord.setPayStatus(TypeConvert.PAID);
					revocationRecord.setRemark(TypeConvert.payRemark("配资撤回", revocationRecord.getMoney()));
					// 3.2记入撤回流水
					revocationRecord.setUid(wuser.getId());
					revocationRecord.setLid(groupId);
					this.userFundService.arrearsProcess(revocationRecord);
				} else { // 爆仓
							// 3.2记入撤回流水
					UserFund arrearsRecord = new UserFund();
					arrearsRecord.setMoney(arrearsMoney);
					arrearsRecord.setLid(groupId);
					arrearsRecord.setType(TypeConvert.USER_FUND_C_TYPE_ARREARS);
					arrearsRecord.setPayStatus(TypeConvert.UN_PAID);
					arrearsRecord.setRemark(TypeConvert.payRemark("配资欠费", arrearsRecord.getMoney()));
					arrearsRecord.setTypeStatus(0);
					arrearsRecord.setAddtime(Dates.getCurrentLongDate());
					this.wUserService.warehouseExplosion(wuser.getId(), arrearsRecord);
				}
			}

			// 4)方案终结合计，计费（管理费、利息费、累计配资、累计支出保证金、累计利息、累计盈亏）删除记录？？？
			this.userFundService.deleteArrearsByLid(groupId);
			List<BillingFeeVo> billFees = this.userFundService.totalMoneyByGroupId(groupId);
			// 1:充值,2:提现,3:人工充值,4:人工扣款,5:投标冻结,6:投标成功,10:配资支出,11:利息支出
			// ,12：扣取管理费（新增）

			// 配资金额合计
			Double capitalAmount = endMoneyVo.getMoney();
			capitalAmount = BigDecimalUtils.round2(capitalAmount, 2);
			wuser.setTotalLending(BigDecimalUtils.addRound(wuser.getTotalLending(), Math.abs(capitalAmount)));
			wuser.setTotalLending(Math.abs(wuser.getTotalLending()));

			// 利息支出合计
			BigDecimal lxAmount = getTotalFee(billFees, 11);
			wuser.setTotalInterestMo(
					BigDecimalUtils.addRound(wuser.getTotalInterestMo(), Math.abs(lxAmount.doubleValue())));
			wuser.setTotalInterestMo(Math.abs(wuser.getTotalInterestMo()));

			// 扣取管理费合计
			BigDecimal managerAmount = getTotalFee(billFees, 12);
			wuser.setTotalManagerMo(
					BigDecimalUtils.addRound(wuser.getTotalManagerMo(), Math.abs(managerAmount.doubleValue())));
			wuser.setTotalManagerMo(Math.abs(wuser.getTotalManagerMo()));

			// 累计保证金
			BigDecimal totalLevelMoney = totalLevelMoneyByGroupId(groupId);
			wuser.setTotalDeposit(BigDecimalUtils.addRound(wuser.getTotalDeposit(), totalLevelMoney.doubleValue()));
			wuser.setTotalDeposit(Math.abs(wuser.getTotalDeposit()));
			// 5)扣费计算(不结算)

			// 6)划账操作
			// 与恒生账户同步
			// 6.1本地账户与恒生母账户金额同步 [结算金额 = 母账户金额]

			for (HkUserTrade trade : userTrades) {
				trade.setStatus(TypeConvert.ACCOUNT_STATUS_ENDED);
				trade.setEndtime(TypeConvert.dbDefaultDate());
				// 账户冻结
				this.update(trade);
			}
			
			//发送终结方案短信
			try {
				Map<String, String> map = Maps.newHashMap();
				map.put("groupId", groupId);
				double leverMoney = BigDecimalUtils.addRound(endMoneyVo.getLeverMoney(),endMoneyVo.getAppendLeverMoney(),2);
				map.put("leverMoney", String.valueOf(leverMoney));
				map.put("accrualValue", accrualValue.toString());
				map.put("endMoney", BigDecimalUtils.round2(BigDecimalUtils.addRound(leverMoney, accrualValue), 2)+"");
				new SMSSenderThread(wuser.getMobile(),"hk.ihuyi.end.trade.ok.code.template", map).start();
			}catch(Exception e) {
				e.printStackTrace();
			}
		} finally {
			lock.unlock();
		}
	}
	
	/**
	 * 获取当天需要扣费的操盘方案
	 * @param startTime
	 * @param status
	 * @return
	 */
	public List<HkUserTrade> getDuductionTrades(Long startTime,List<Short> status){
		return this.getEntityDao().findDuductionTrades(startTime, status);
	}
	
	/**
	 * 获取今日没有的欠费的用户以及用户的操盘方案id集合
	 * @return
	 */
	public List<HkUserTradFeeDuductionVo> getNoArrearsHkUserTrades() {
		String sql = "SELECT uid, GROUP_CONCAT(id) userTradeIds FROM hk_user_trade ut WHERE ut.`status` = 1  AND NOT EXISTS ( SELECT uid FROM w_user_fund WHERE pay_status = 0 AND money < 0 AND uid = ut.uid GROUP BY uid ) GROUP BY ut.uid";
		@SuppressWarnings("unchecked")
		List<HkUserTradFeeDuductionVo> list = nativeQuery(sql, null, HkUserTradFeeDuductionVo.class);
		return list;
	}
	
	/**
	 * 保存操盘申请信息
	 * 
	 * @param hkUserTrade
	 * @param wuser
	 * @return
	 */
	public HkUserTrade saveHkTrade(HkUserTrade hkUserTrade, WUser wuser) {
		// 配资中
		hkUserTrade.setStatus((short) 0);
		String token = RandomCodeUtil.genToken(6);
		hkUserTrade.setProgramNo("HK" + RandomCodeUtil.genToken(7));
		hkUserTrade.setGroupId("HK" + token);
		// 0.首次利息计算
		double firstFee = BigDecimalUtils.round(hkUserTrade.getFeeMonth() * hkUserTrade.getNaturalDays(), 2);
		// 首次利息
		hkUserTrade.setApr(firstFee);
		// 4.w_user用户基本信息表中修改可用余额,资金总额.

		// 扣配资后
		double afterMoney = BigDecimalUtils.subRound(wuser.getAvlBal(), hkUserTrade.getLeverMoney());
		// 扣利息后
		double avlBal = BigDecimalUtils.subRound(wuser.getAvlBal(), hkUserTrade.getLeverMoney() + firstFee);

		double avlBaltemp = avlBal;

		// 扣第一天管理费后
		double manageBal = BigDecimalUtils.subRound(wuser.getAvlBal(),
				hkUserTrade.getLeverMoney() + firstFee + hkUserTrade.getFeeDay());

		double manageBalTemp = manageBal;

		String currentDay = Dates.format(new Date(), Dates.CHINESE_DATE_FORMAT_LONG);
		// 计算交易日
		String tradeDay = hkTradeCalendarService.getTradeDay();
		boolean isTradeDay = Boolean.FALSE;
		if (tradeDay.equals(currentDay)) {
			isTradeDay = Boolean.TRUE;
		}
		wuser.setUserType(WUser.UserType.TRADE);
		if (hkUserTrade.getTradeStart() == HkConstant.INT_ZERO && isTradeDay) {
			if (manageBal >= HkConstant.INT_ZERO) {
				wuser.setAvlBal(BigDecimalUtils.round(manageBal, HkConstant.INT_TWO));
				wUserService.updateUser(wuser);
			} else {
				throw new HkUserTradeException("no.cash.accout", null);
			}
		} else {
			if (avlBal >= HkConstant.INT_ZERO) {
				wuser.setAvlBal(BigDecimalUtils.round(avlBal, HkConstant.INT_TWO));
				wUserService.updateUser(wuser);
			} else {
				throw new HkUserTradeException("no.cash.accout", null);
			}
		}
		// 保存操盘信息
		save(hkUserTrade);

		// 6.生成一条w_user_fund 用户资金记录表 记录配资记录.还有一条利息记录
		// (1)配资记录
		UserFund userFund = new UserFund();
		userFund.setUid(wuser.getId());
		userFund.setNo(hkUserTrade.getId());
		userFund.setLid(hkUserTrade.getGroupId());
		userFund.setRid(hkUserTrade.getProgramNo());
		// 类型为配资支出
		userFund.setType(10);
		userFund.setMoney(-hkUserTrade.getLeverMoney());
		userFund.setAmount(BigDecimalUtils.round(afterMoney, 2));
		userFund.setPayStatus((short) 1);
		userFund.setAddtime(Dates.getCurrentLongDate());
		userFund.setUptime(Dates.getCurrentLongDate());
		userFund.setRemark("支付港股操盘本金" + hkUserTrade.getLeverMoney() + "元");
		userFundService.save(userFund);

		// (2)利息记录
		if (firstFee != HkConstant.DOUBLE_ZERO) {
			UserFund interestFund = new UserFund();
			interestFund.setUid(wuser.getId());
			interestFund.setNo(hkUserTrade.getId());
			interestFund.setLid(hkUserTrade.getGroupId());
			interestFund.setRid(hkUserTrade.getProgramNo());
			// 类型为利息费
			interestFund.setType(11);
			interestFund.setMoney(-firstFee);
			interestFund.setAmount(BigDecimalUtils.round(avlBaltemp, 2));
			interestFund.setPayStatus((short) 1);
			interestFund.setAddtime(Dates.getCurrentLongDate() + 1);
			interestFund.setUptime(Dates.getCurrentLongDate() + 1);
			interestFund.setRemark("支付港股操盘利息" + firstFee + "元");
			userFundService.save(interestFund);
		}

		// 如果立即生效必须是交易日才生效
		if (hkUserTrade.getTradeStart() == HkConstant.INT_ZERO 
				&& isTradeDay
				&& hkUserTrade.getFeeDay() != HkConstant.DOUBLE_ZERO) {
			// (3)当天管理费记录
			UserFund userFundManage = new UserFund();
			userFundManage.setUid(wuser.getId());
			userFundManage.setNo(hkUserTrade.getId());
			userFundManage.setLid(hkUserTrade.getGroupId());
			userFundManage.setRid(hkUserTrade.getProgramNo());
			// 类型为利息费
			userFundManage.setType(12);
			userFundManage.setMoney(-hkUserTrade.getFeeDay());
			userFundManage.setAmount(BigDecimalUtils.round(manageBalTemp, 2));
			userFundManage.setPayStatus((short) 1);
			userFundManage.setAddtime(Dates.getCurrentLongDate() + 2);
			userFundManage.setUptime(Dates.getCurrentLongDate() + 2);
			userFundManage.setRemark("支付港股操盘管理费" + hkUserTrade.getFeeDay() + "元");
			userFundService.save(userFundManage);
		}

		// 保存 配资申请审核信息
		HkUserTradeExtend hkUserTradeExtend = new HkUserTradeExtend(hkUserTrade.getId());
		hkUserTradeExtend.setCreateTime(Dates.getCurrentLongDate());
		hkUserTradeExtendService.save(hkUserTradeExtend);
		return hkUserTrade;
	}

	/**
	 * 提供后台获取收益数据
	 * 
	 * @param easyUiPage
	 * @param searchParams
	 * @return
	 */
	public List<HkEarningsVo> getEarningsData(ConnditionVo connVo) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("SELECT utemp.*,");
		buffer.append(
				"CAST(ifnull(freemoney, 0) + ifnull(managerMoney, 0) + ifnull(transmoney, 0) + ifnull(interestMoney, 0) - ifnull(deductMoney, 0) + ifnull(revokeManagerMoney, 0) + ifnull(revokeInterest, 0) AS DECIMAL (10, 2)) AS totalmoney ");
		buffer.append("FROM(");
		buffer.append("SELECT w.mobile,v.tname,t.account_no AS account,");
		buffer.append("p.name AS hsBelongBroker,t.group_id AS groupId,");
		buffer.append("(t.lever_money + t.append_lever_money) AS leverMoney,t.lever lever,");
		buffer.append("(TO_DAYS(NOW()) - TO_DAYS(FROM_UNIXTIME(t.starttime, '%Y%m%d')) + 1) opDay,");
		buffer.append(
				"CAST((SELECT SUM(ABS(uf.money)) FROM w_user_fund uf WHERE uf.rid = t.program_no AND uf.type = 12 AND uf.pay_status = 1 AND uf.uptime BETWEEN :beginTime AND :endTime) AS DECIMAL (10, 2)) AS managerMoney,");
		buffer.append(
				"CAST((SELECT SUM(- uf.money) FROM w_user_fund uf WHERE uf.rid = t.program_no AND uf.type = 25 AND uf.pay_status = 1 AND uf.uptime BETWEEN :beginTime AND :endTime) AS DECIMAL (10, 2)) AS revokeManagerMoney,");
		buffer.append(
				"CAST((SELECT SUM(ABS(uf.money)) FROM w_user_fund uf WHERE uf.rid = t.program_no AND uf.type = 11 AND uf.pay_status = 1 AND uf.uptime BETWEEN :beginTime AND :endTime) AS DECIMAL (10, 2)) AS interestMoney,");
		buffer.append(
				"CAST((SELECT SUM(- uf.money) FROM w_user_fund uf WHERE uf.rid = t.program_no AND uf.type = 26 AND uf.pay_status = 1 AND uf.uptime BETWEEN :beginTime AND :endTime) AS DECIMAL (10, 2)) AS revokeInterest,");
		buffer.append(
				"CAST((SELECT SUM(ABS(uf.money)) FROM w_user_fund uf WHERE uf.rid = t.program_no AND uf.type = 24 AND uf.pay_status = 1 AND uf.uptime BETWEEN :beginTime AND :endTime) AS DECIMAL (10, 2)) AS deductMoney,");
		buffer.append(
				"CAST((SELECT SUM(d.money) FROM hk_free_diff d WHERE d.type = '1' AND d.account = t.account_no AND d.addtime BETWEEN :beginTime AND :endTime) AS DECIMAL (10, 2)) AS freemoney,");
		buffer.append(
				"CAST((SELECT SUM(d.money) FROM hk_free_diff d WHERE d.type = '2' AND d.account = t.account_no AND d.addtime BETWEEN :beginTime AND :endTime) AS DECIMAL (10, 2)) AS transmoney ");
		buffer.append("FROM hk_user_trade t ");
		buffer.append("LEFT JOIN w_user w ON t.uid = w.id ");
		buffer.append("LEFT JOIN w_user_verified v ON v.uid = w.id ");
		buffer.append("LEFT JOIN hk_parent_account p ON p.id = t.parent_account_id ");
		buffer.append("WHERE ((t.status = 1 OR t.status = 0) AND t.addtime <= :endTime) ");
		buffer.append("OR (t.status = 2 AND t.endtime >= :endTime AND t.addtime <= :endTime) ");
		buffer.append("OR (t.status = 2 AND t.endtime BETWEEN :beginTime AND :endTime)" + ") utemp ");
		buffer.append("WHERE utemp.lever IS NOT NULL");

		List<String> paramNames = new ArrayList<String>();
		List<Object> params = new ArrayList<Object>();

		String starttimeStr_start = connVo.getValueStr("starttimeStr_start");
		String starttimeStr_end = connVo.getValueStr("starttimeStr_end");
		Long startTime = TypeConvert.longCriticalTimeDay(0);
		Long endTime = TypeConvert.strToZeroDate1000(TypeConvert.dbDateYmd(), 1, -1);
		if (StringUtil.isNotBlank(starttimeStr_start) && StringUtil.isNotBlank(starttimeStr_end)) {
			startTime = TypeConvert.strToZeroDate1000(starttimeStr_start, 0);
			endTime = TypeConvert.strToZeroDate1000(starttimeStr_end, 1, -1);
		}
		paramNames.add("beginTime");
		paramNames.add("endTime");
		params.add(startTime);
		params.add(endTime);

		String mobile = connVo.getValueStr("mobile");
		if (mobile != null) {
			paramNames.add("mobile");
			buffer.append(" AND utemp.mobile LIKE :mobile");
			params.add(mobile + "%");
		}

		String tname = connVo.getValueStr("tname");
		if (tname != null) {
			paramNames.add("tname");
			buffer.append(" AND utemp.tname LIKE :tname");
			params.add(tname + "%");
		}
		String account = connVo.getValueStr("account");
		if (account != null) {
			paramNames.add("account");
			buffer.append(" AND utemp.account LIKE :account");
			params.add(account + "%");
		}
		String programNo = connVo.getValueStr("programNo");
		if (programNo != null) {
			paramNames.add("programNo");
			buffer.append(" AND utemp.programNo LIKE :programNo");
			params.add(programNo + "%");
		}

		String groupId = connVo.getValueStr("groupId");
		if (groupId != null) {
			paramNames.add("groupId");
			buffer.append(" AND utemp.groupId LIKE :groupId");
			params.add(groupId + "%");
		}
		// sqlBuf.append(" ORDER BY utemp.programNo");
		return this.getEntityDao().queryDataByParamsSql(connVo.autoOrderBy(buffer.toString(), HkEarningsVo.class),
				HkEarningsVo.class, null, paramNames.toArray(new String[paramNames.size()]), params.toArray());
	}
	
	@SuppressWarnings("unchecked")
	public PageInfo<HkEarningsVo> queryPageDataListVo(PageInfo<HkEarningsVo> dataPage,ConnditionVo connVo){
		
		Map<String, Object> paramValues = new HashMap<String, Object>();
		
		StringBuffer buffer = new StringBuffer();
		buffer.append("SELECT utemp.*, ");
		buffer.append(
				"CAST(ifnull(freemoney, 0) + ifnull(managerMoney, 0) + ifnull(transmoney, 0) + ifnull(interestMoney, 0) - ifnull(deductMoney, 0) + ifnull(revokeManagerMoney, 0) + ifnull(revokeInterest, 0) AS DECIMAL (10, 2)) AS totalmoney ");
		buffer.append("FROM(");
		buffer.append("SELECT w.mobile,v.tname,t.account_no AS account,");
		buffer.append("p.name AS hsBelongBroker,t.group_id AS groupId,");
		buffer.append("(t.lever_money + t.append_lever_money) AS leverMoney,t.lever lever,");
		buffer.append("(TO_DAYS(NOW()) - TO_DAYS(FROM_UNIXTIME(t.starttime, '%Y%m%d')) + 1) opDay,");
		buffer.append(
				"CAST((SELECT SUM(ABS(uf.money)) FROM w_user_fund uf WHERE uf.rid = t.program_no AND uf.type = 12 AND uf.pay_status = 1 AND uf.uptime BETWEEN :beginTime AND :endTime) AS DECIMAL (10, 2)) AS managerMoney,");
		buffer.append(
				"CAST((SELECT SUM(- uf.money) FROM w_user_fund uf WHERE uf.rid = t.program_no AND uf.type = 25 AND uf.pay_status = 1 AND uf.uptime BETWEEN :beginTime AND :endTime) AS DECIMAL (10, 2)) AS revokeManagerMoney,");
		buffer.append(
				"CAST((SELECT SUM(ABS(uf.money)) FROM w_user_fund uf WHERE uf.rid = t.program_no AND uf.type = 11 AND uf.pay_status = 1 AND uf.uptime BETWEEN :beginTime AND :endTime) AS DECIMAL (10, 2)) AS interestMoney,");
		buffer.append(
				"CAST((SELECT SUM(- uf.money) FROM w_user_fund uf WHERE uf.rid = t.program_no AND uf.type = 26 AND uf.pay_status = 1 AND uf.uptime BETWEEN :beginTime AND :endTime) AS DECIMAL (10, 2)) AS revokeInterest,");
		buffer.append(
				"CAST((SELECT SUM(ABS(uf.money)) FROM w_user_fund uf WHERE uf.rid = t.program_no AND uf.type = 24 AND uf.pay_status = 1 AND uf.uptime BETWEEN :beginTime AND :endTime) AS DECIMAL (10, 2)) AS deductMoney,");
		buffer.append(
				"CAST((SELECT SUM(d.money) FROM hk_free_diff d WHERE d.type = '1' AND d.account = t.account_no AND d.addtime BETWEEN :beginTime AND :endTime) AS DECIMAL (10, 2)) AS freemoney,");
		buffer.append(
				"CAST((SELECT SUM(d.money) FROM hk_free_diff d WHERE d.type = '2' AND d.account = t.account_no AND d.addtime BETWEEN :beginTime AND :endTime) AS DECIMAL (10, 2)) AS transmoney ");
		buffer.append("FROM hk_user_trade t ");
		buffer.append("LEFT JOIN w_user w ON t.uid = w.id ");
		buffer.append("LEFT JOIN w_user_verified v ON v.uid = w.id ");
		buffer.append("LEFT JOIN hk_parent_account p ON p.id = t.parent_account_id ");
		buffer.append("WHERE ((t.status = 1 OR t.status = 0) AND t.addtime <= :endTime) ");
		buffer.append("OR (t.status = 2 AND t.endtime >= :endTime AND t.addtime <= :endTime) ");
		buffer.append("OR (t.status = 2 AND t.endtime BETWEEN :beginTime AND :endTime)" + ") utemp ");
		buffer.append("WHERE utemp.lever IS NOT NULL");

		String starttimeStr_start = connVo.getValueStr("starttimeStr_start");
		String starttimeStr_end = connVo.getValueStr("starttimeStr_end");
		Long startTime = TypeConvert.longCriticalTimeDay(0);
		Long endTime = TypeConvert.strToZeroDate1000(TypeConvert.dbDateYmd(), 1, -1);
		if (StringUtil.isNotBlank(starttimeStr_start) && StringUtil.isNotBlank(starttimeStr_end)) {
			startTime = TypeConvert.strToZeroDate1000(starttimeStr_start, 0);
			endTime = TypeConvert.strToZeroDate1000(starttimeStr_end, 1, -1);
		}
		
		paramValues.put("beginTime", startTime);
		paramValues.put("endTime", endTime);

		String mobile = connVo.getValueStr("mobile");
		if (mobile != null) {
			buffer.append(" AND utemp.mobile LIKE :mobile");
			paramValues.put("mobile", mobile + "%");
		}

		String tname = connVo.getValueStr("tname");
		if (tname != null) {
			buffer.append(" AND utemp.tname LIKE :tname");
			paramValues.put("tname", tname + "%");
		}
		String account = connVo.getValueStr("account");
		if (account != null) {
			buffer.append(" AND utemp.account LIKE :account");
			paramValues.put("account", account + "%");
		}
		String programNo = connVo.getValueStr("programNo");
		if (programNo != null) {
			buffer.append(" AND utemp.programNo LIKE :programNo");
			paramValues.put("programNo", programNo + "%");
		}

		String groupId = connVo.getValueStr("groupId");
		if (groupId != null) {
			buffer.append(" AND utemp.groupId LIKE :groupId");
			paramValues.put("groupId", groupId + "%");
		}
		
		PageInfo<HkEarningsVo> pageInfo= getEntityDao().queryDataByParamsSql(dataPage, buffer.toString(),HkEarningsVo.class,paramValues,null);
		
		return pageInfo;
	}
	
	@SuppressWarnings("unchecked")
	public HkEarningsVo getDataTotalVo(ConnditionVo connVo) {
		
		StringBuffer buffer = new StringBuffer();
		buffer.append("SELECT ");
		buffer.append("sum(utemp.leverMoney) AS leverMoney,sum(utemp.lever) AS lever,sum(utemp.opDay) as opDay,sum(utemp.managerMoney) as managerMoney, ");
		buffer.append("sum(utemp.interestMoney) AS interestMoney,sum(utemp.freemoney) AS freemoney,  ");
		buffer.append(
				"sum(CAST(ifnull(freemoney, 0) + ifnull(managerMoney, 0) + ifnull(transmoney, 0) + ifnull(interestMoney, 0) - ifnull(deductMoney, 0) + ifnull(revokeManagerMoney, 0) + ifnull(revokeInterest, 0) AS DECIMAL (10, 2))) AS totalmoney ");
		buffer.append("FROM(");
		buffer.append("SELECT w.mobile,v.tname,t.account_no AS account,");
		buffer.append("p.name AS hsBelongBroker,t.group_id AS groupId,");
		buffer.append("(t.lever_money + t.append_lever_money) AS leverMoney,t.lever lever,");
		buffer.append("(TO_DAYS(NOW()) - TO_DAYS(FROM_UNIXTIME(t.starttime, '%Y%m%d')) + 1) opDay,");
		buffer.append(
				"CAST((SELECT SUM(ABS(uf.money)) FROM w_user_fund uf WHERE uf.rid = t.program_no AND uf.type = 12 AND uf.pay_status = 1 AND uf.uptime BETWEEN :beginTime AND :endTime) AS DECIMAL (10, 2)) AS managerMoney,");
		buffer.append(
				"CAST((SELECT SUM(- uf.money) FROM w_user_fund uf WHERE uf.rid = t.program_no AND uf.type = 25 AND uf.pay_status = 1 AND uf.uptime BETWEEN :beginTime AND :endTime) AS DECIMAL (10, 2)) AS revokeManagerMoney,");
		buffer.append(
				"CAST((SELECT SUM(ABS(uf.money)) FROM w_user_fund uf WHERE uf.rid = t.program_no AND uf.type = 11 AND uf.pay_status = 1 AND uf.uptime BETWEEN :beginTime AND :endTime) AS DECIMAL (10, 2)) AS interestMoney,");
		buffer.append(
				"CAST((SELECT SUM(- uf.money) FROM w_user_fund uf WHERE uf.rid = t.program_no AND uf.type = 26 AND uf.pay_status = 1 AND uf.uptime BETWEEN :beginTime AND :endTime) AS DECIMAL (10, 2)) AS revokeInterest,");
		buffer.append(
				"CAST((SELECT SUM(ABS(uf.money)) FROM w_user_fund uf WHERE uf.rid = t.program_no AND uf.type = 24 AND uf.pay_status = 1 AND uf.uptime BETWEEN :beginTime AND :endTime) AS DECIMAL (10, 2)) AS deductMoney,");
		buffer.append(
				"CAST((SELECT SUM(d.money) FROM hk_free_diff d WHERE d.type = '1' AND d.account = t.account_no AND d.addtime BETWEEN :beginTime AND :endTime) AS DECIMAL (10, 2)) AS freemoney,");
		buffer.append(
				"CAST((SELECT SUM(d.money) FROM hk_free_diff d WHERE d.type = '2' AND d.account = t.account_no AND d.addtime BETWEEN :beginTime AND :endTime) AS DECIMAL (10, 2)) AS transmoney ");
		buffer.append("FROM hk_user_trade t ");
		buffer.append("LEFT JOIN w_user w ON t.uid = w.id ");
		buffer.append("LEFT JOIN w_user_verified v ON v.uid = w.id ");
		buffer.append("LEFT JOIN hk_parent_account p ON p.id = t.parent_account_id ");
		buffer.append("WHERE ((t.status = 1 OR t.status = 0) AND t.addtime <= :endTime) ");
		buffer.append("OR (t.status = 2 AND t.endtime >= :endTime AND t.addtime <= :endTime) ");
		buffer.append("OR (t.status = 2 AND t.endtime BETWEEN :beginTime AND :endTime)" + ") utemp ");
		buffer.append("WHERE utemp.lever IS NOT NULL");

		List<String> paramNames = new ArrayList<String>();
		List<Object> params = new ArrayList<Object>();

		String starttimeStr_start = connVo.getValueStr("starttimeStr_start");
		String starttimeStr_end = connVo.getValueStr("starttimeStr_end");
		Long startTime = TypeConvert.longCriticalTimeDay(0);
		Long endTime = TypeConvert.strToZeroDate1000(TypeConvert.dbDateYmd(), 1, -1);
		if (StringUtil.isNotBlank(starttimeStr_start) && StringUtil.isNotBlank(starttimeStr_end)) {
			startTime = TypeConvert.strToZeroDate1000(starttimeStr_start, 0);
			endTime = TypeConvert.strToZeroDate1000(starttimeStr_end, 1, -1);
		}
		paramNames.add("beginTime");
		paramNames.add("endTime");
		params.add(startTime);
		params.add(endTime);

		String mobile = connVo.getValueStr("mobile");
		if (mobile != null) {
			paramNames.add("mobile");
			buffer.append(" AND utemp.mobile LIKE :mobile");
			params.add(mobile + "%");
		}

		String tname = connVo.getValueStr("tname");
		if (tname != null) {
			paramNames.add("tname");
			buffer.append(" AND utemp.tname LIKE :tname");
			params.add(tname + "%");
		}
		String account = connVo.getValueStr("account");
		if (account != null) {
			paramNames.add("account");
			buffer.append(" AND utemp.account LIKE :account");
			params.add(account + "%");
		}
		String programNo = connVo.getValueStr("programNo");
		if (programNo != null) {
			paramNames.add("programNo");
			buffer.append(" AND utemp.programNo LIKE :programNo");
			params.add(programNo + "%");
		}

		String groupId = connVo.getValueStr("groupId");
		if (groupId != null) {
			paramNames.add("groupId");
			buffer.append(" AND utemp.groupId LIKE :groupId");
			params.add(groupId + "%");
		}
		 
		List<HkEarningsVo> hkEarningsVoList = this.getEntityDao().queryDataByParamsSql(connVo.autoOrderBy(buffer.toString(), HkEarningsVo.class),
				HkEarningsVo.class, null, paramNames.toArray(new String[paramNames.size()]), params.toArray());
		HkEarningsVo hkEarningsVo = null;
		if(hkEarningsVoList != null && hkEarningsVoList.size() > 0){
			hkEarningsVo = hkEarningsVoList.get(0);
		}
		return hkEarningsVo;
	}
	
	
	public PageInfo<Object> queryUserTrade(String uid, int countOfCurrentPage,
			int currentPage, Map<String, Object> searchParams) {
		PageInfo<Object> pageInfo = new PageInfo<Object>(countOfCurrentPage,
				currentPage);

		StringBuffer sql = new StringBuffer(" SELECT t.group_id AS groupId,sum(t.total_lever_money) AS totalOperateMoney, ");
		sql.append(" sum(t.lever_money) AS totalLeverMoney,sum(t.accrual) as totalAccrual, ");
		sql.append(" min(t.starttime) as starttime,MAX(t.endtime) as endtime,SUM(t.append_lever_money) as totalAppendLeverMoney, ");
		sql.append(" t.status,SUM(t.warning) as warning,SUM(t.`open`) as `open`,t.account_no AS accountNo,t.password, ");
		sql.append(" h.audit_status AS auditStatus,h.audit_end_status AS auditEndStatus ");
		sql.append(" FROM hk_user_trade t LEFT JOIN hk_parent_account p ON t.parent_account_id = p.id ,hk_user_trade_extend h ");
		sql.append(" WHERE t.uid=? AND t.id = h.trade_id ");
		sql.append(" GROUP BY t.group_id ORDER BY t.starttime DESC ");
		
		List<Object> params = Lists.newArrayList();  //查询参数 依次 存入
		params.add(uid);

		MultiListParam multilistParam = new MultiListParam(pageInfo,searchParams, sql.toString(), params);
		pageInfo = multiListPageQuery(multilistParam, HKUserTradeWebVo.class);
		return pageInfo;
	}
	
	@SuppressWarnings("unchecked")
	public HKUserTradeWebVo findByGroupIdAndUid(String groupId, String uid) {
		
		StringBuffer sql  = new StringBuffer(" select t.uid,t.lever,max(t.natural_days) as naturalDays ,t.policy_no  policyNo, ");
		sql.append(" t.parent_account_id as parentAccountId,t.startdays,sum(t.finished_money) as finishedMoney, ");
		sql.append(" sum(t.apr) as apr,MAX(t.estimate_endtime) as estimateEndtime,sum(t.fee_day) as feeDay, ");
		sql.append(" sum(t.fee_month) as feeMonth,sum(t.accrual) as totalAccrual,t.group_id as groupId,min(t.starttime) as starttime, ");
		sql.append(" MAX(t.endtime) as endtime,SUM(t.total_lever_money) as totalOperateMoney,  ");
		sql.append(" SUM(t.lever_money) as totalLeverMoney , SUM(money) as totalLending, ");
		sql.append(" SUM(t.append_lever_money) as totalAppendLeverMoney,t.status,SUM(t.warning) as warning,SUM(t.`open`) as `open`, ");
		sql.append(" t.account_no AS accountNo,t.password,h.audit_status AS auditStatus,h.audit_end_status AS auditEndStatus,t.end_exchange_rate AS endExchangeRate ");
		sql.append(" from hk_user_trade t LEFT JOIN hk_parent_account p ON t.parent_account_id = p.id ,hk_user_trade_extend h ");
		sql.append(" where t.uid=? and t.group_id=? AND t.id = h.trade_id ");
		sql.append(" GROUP BY t.group_id  ORDER BY t.addtime asc ");
		
		List<Object> params = Lists.newArrayList();
		params.add(uid);
		params.add(groupId);
		
		HKUserTradeWebVo hkUserTradeWebVo = null;
		
		List<HKUserTradeWebVo> list = this.nativeQuery(sql.toString(), params,HKUserTradeWebVo.class);
		
		if(CollectionUtils.isEmpty(list)){
			return hkUserTradeWebVo;
		}
		
		hkUserTradeWebVo = list.get(0);
		
		if (hkUserTradeWebVo.getStatus() == 1) {
			
			hkUserTradeWebVo.setAssetTotalValue(hkUserTradeWebVo.getTotalOperateMoney());

			hkUserTradeWebVo.setStarttimeString(Dates.parseBigInteger2Date(hkUserTradeWebVo.getStarttime(),Dates.CHINESE_DATE_FORMAT_LINE));
			
			hkUserTradeWebVo.setEstimateEndtimeString(Dates.parseBigInteger2Date(hkUserTradeWebVo.getEstimateEndtime(),Dates.CHINESE_DATE_FORMAT_LINE));
			
			String today = Dates.format(Dates.CHINESE_DATE_FORMAT_LONG);
			
			String startDay = Dates.parseBigInteger2Date(hkUserTradeWebVo.getStarttime(),Dates.CHINESE_DATE_FORMAT_LONG);
			
			long tradingDays = hkTradeCalendarService.getTradeDays(startDay,today);
			
			hkUserTradeWebVo.setTradingDays(tradingDays);
		} else {

			hkUserTradeWebVo.setStarttimeString(Dates.parseBigInteger2Date(hkUserTradeWebVo.getStarttime(),Dates.CHINESE_DATE_FORMAT_LINE));
			
			String endTime = Dates.parseBigInteger2Date(hkUserTradeWebVo.getEndtime(),Dates.CHINESE_DATE_FORMAT_LINE);
			
			if (hkUserTradeWebVo.getStatus() == 0) {
				endTime = Dates.parseBigInteger2Date(hkUserTradeWebVo.getEstimateEndtime(),Dates.CHINESE_DATE_FORMAT_LINE);
			}
			hkUserTradeWebVo.setEstimateEndtimeString(endTime);

			String startDay = Dates.parseBigInteger2Date(hkUserTradeWebVo.getStarttime(),Dates.CHINESE_DATE_FORMAT_LONG);
			
			String endDay = Dates.parseBigInteger2Date(hkUserTradeWebVo.getEndtime(),Dates.CHINESE_DATE_FORMAT_LONG);
			
			long tradingDays = hkTradeCalendarService.getTradeDays(startDay,endDay);
			
			hkUserTradeWebVo.setTradingDays(tradingDays);
		}
		return hkUserTradeWebVo;
	}
	
	public List<HkUserTrade> findByGroupIdAndWuser(String groupId, WUser wuser) {
		return getEntityDao().findByWuserAndGroupIdOrderByAddtimeAsc(wuser,groupId);
	}
	
	/**
	 * 判断指定的账号是否存在
	 * @param accountNo
	 * @return
	 */
	public boolean exists(String accountNo){
		int count=getEntityDao().getCount(accountNo);
		return count>0;
	}
	
	/**
	 * 获取用户当前港股融资金额
	 * @param uid  用户编号
	 * @param status 方案状态  1：操盘中
	 * @return
	 */
	public Double getSumMoneyHKUserTradesByUidAndStatus(String uid, int status) {
		
		if(StringUtil.isBlank(uid)){
			return null;
		}
		
		String sql = "SELECT SUM(t.money) FROM hk_user_trade t WHERE t.uid=? AND t.`status`=? ";
		List<Object> params = new ArrayList<Object>();
		params.add(uid);
		params.add(status);
		
		Double totalMoney = (Double) nativeQueryOne(sql, params);
		
		return totalMoney;
	}
	
	public HKUserTradeWebVo findByGroupIdAndUidLazy(String groupId, String uid) {

		StringBuffer sql = new StringBuffer(
				"select uid,activity_type as activityType,lever,max(natural_days) as naturalDays, ");
		sql.append(" parent_account_no as parentAccountNo,startdays,sum(w.finished_money) as finishedMoney, ");
		sql.append(" sum(apr) as apr,MAX(estimate_endtime) as estimateEndtime,sum(fee_day) as feeDay, ");
		sql.append(" sum(fee_month) as feeMonth,sum(accrual) as totalAccrual,w.asset_id as assetId, ");
		sql.append(" w.combine_id as combineId,group_id as groupId,min(starttime) as starttime, ");
		sql.append(" MAX(w.endtime) as endtime,SUM(total_lever_money) as totalOperateMoney, ");
		sql.append(" SUM(lever_money) as totalLeverMoney ,SUM(money) as totalLending, ");
		sql.append(" SUM(append_lever_money) as totalAppendLeverMoney,max(w.`status`) `status`, ");
		sql.append(" hs_belong_broker as hsBelongBroker,SUM(warning) as warning,SUM(`open`) as `open`, ");
		sql.append(" w.account,w.`password`,w.fee_type AS feeType,h.audit_status AS auditStatus,h.audit_end_status AS auditEndStatus, ac.insurance_no insuranceNo ");
		sql.append(" from w_user_trade w LEFT JOIN w_hand_trade h ON w.id=h.trade_id ");
		sql.append(" LEFT JOIN w_account ac ON ac.id = w.account_id ");
		sql.append(" where uid=? and w.group_id=? GROUP BY w.group_id  ORDER BY w.addtime asc ");

		List<Object> params = Lists.newArrayList();
		params.add(uid);
		params.add(groupId);
		List<HKUserTradeWebVo> list = nativeQuery(sql.toString(), params,
				HKUserTradeWebVo.class);
		if (!CollectionUtils.isEmpty(list)) {
			HKUserTradeWebVo hKUserTradeWebVo = list.get(0);
			try {
				if (hKUserTradeWebVo.getStatus() == 1) {
					hKUserTradeWebVo.setStarttimeString(Dates
							.parseBigInteger2Date(
									hKUserTradeWebVo.getStarttime(),
									Dates.CHINESE_DATE_FORMAT_LINE));
					hKUserTradeWebVo.setEstimateEndtimeString(Dates
							.parseBigInteger2Date(
									hKUserTradeWebVo.getEstimateEndtime(),
									Dates.CHINESE_DATE_FORMAT_LINE));
					String today = Dates.format(Dates.CHINESE_DATE_FORMAT_LONG);
					String startDay = Dates.parseBigInteger2Date(
							hKUserTradeWebVo.getStarttime(),
							Dates.CHINESE_DATE_FORMAT_LONG);
					long tradingDays = tradeDayService.getTradeDays(startDay,
							today);
					hKUserTradeWebVo.setTradingDays(tradingDays);
				} else {

					hKUserTradeWebVo.setStarttimeString(Dates
							.parseBigInteger2Date(
									hKUserTradeWebVo.getStarttime(),
									Dates.CHINESE_DATE_FORMAT_LINE));
					String endTime = Dates.parseBigInteger2Date(
							hKUserTradeWebVo.getEndtime(),
							Dates.CHINESE_DATE_FORMAT_LINE);
					if (hKUserTradeWebVo.getStatus() == 0) {
						endTime = Dates.parseBigInteger2Date(
								hKUserTradeWebVo.getEstimateEndtime(),
								Dates.CHINESE_DATE_FORMAT_LINE);
					}
					hKUserTradeWebVo.setEstimateEndtimeString(endTime);

					String startDay = Dates.parseBigInteger2Date(
							hKUserTradeWebVo.getStarttime(),
							Dates.CHINESE_DATE_FORMAT_LONG);
					String endDay = Dates.parseBigInteger2Date(
							hKUserTradeWebVo.getEndtime(),
							Dates.CHINESE_DATE_FORMAT_LONG);
					long tradingDays = tradeDayService.getTradeDays(startDay,
							endDay);
					hKUserTradeWebVo.setTradingDays(tradingDays);
				}
			} catch (Exception e) {
				log.error(this.getClass().getName() + e.getMessage());
				throw new UserTradeException(
						"user.trade.query.margin.remind.data.fail", null);
			}

			return hKUserTradeWebVo;
		}
		return null;
	}

	
	public synchronized boolean addBond(
				HkUserTrade userTrade, double addMoney,double hkDollar, WUser userTemp) {

			if (userTemp.getAvlBal() < addMoney) {
				throw new HkUserTradeException("no.cash.accout", null);
			}

			userTrade.setAppendLeverMoney(BigDecimalUtils.addRound(
					userTrade.getAppendLeverMoney(), addMoney));
			userTrade.setTotalLeverMoney(BigDecimalUtils.addRound(
					userTrade.getTotalLeverMoney(), hkDollar));
			

			userTemp.setAvlBal(BigDecimalUtils.subRound(userTemp.getAvlBal(),
					addMoney));
			userTemp.setFund(BigDecimalUtils.addRound(userTemp.getAvlBal(),
					userTemp.getFrzBal()));
			save(userTrade);
			wUserService.updateUser(userTemp);

			// (1)追加保证金记录
			UserFund userFund = new UserFund();
			userFund.setUid(userTemp.getId());
			userFund.setNo(userTrade.getId());
			userFund.setLid(userTrade.getGroupId());
			// 类型为追加保证金
			userFund.setType(17);
			userFund.setMoney(-addMoney);
			userFund.setAmount(userTemp.getAvlBal());
			userFund.setPayStatus((short) 1);
			userFund.setAddtime(Dates.getCurrentLongDate());
			userFund.setUptime(Dates.getCurrentLongDate());
			userFund.setRemark("支付港股追加保证金" + addMoney + "元");
			userFundService.save(userFund);
			return true;
		}

	/**
	 * 获取用户当前港股融资金额
	 * @param uid  用户编号
	 * @return
	 */
	public int getCountHKUserTradesByUid(String uid) {
		
		if(StringUtil.isBlank(uid)){
			return 0;
		}
		
		String sql = "SELECT count(0) FROM hk_user_trade t WHERE t.uid=? ";
		List<Object> params = new ArrayList<Object>();
		params.add(uid);
		
		BigInteger count = (BigInteger) nativeQueryOne(sql, params);
		return count.intValue();
	}
}

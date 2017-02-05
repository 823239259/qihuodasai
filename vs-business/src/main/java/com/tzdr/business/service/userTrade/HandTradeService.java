package com.tzdr.business.service.userTrade;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import jodd.util.ObjectUtil;
import jodd.util.StringUtil;

import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.tzdr.business.cms.service.auth.AuthService;
import com.tzdr.business.exception.UserTradeException;
import com.tzdr.business.service.account.AccountService;
import com.tzdr.business.service.account.ParentAccountService;
import com.tzdr.business.service.combine.CombineInfoService;
import com.tzdr.business.service.feededuction.FeeDuductionService;
import com.tzdr.business.service.pay.UserFundService;
import com.tzdr.business.service.thread.SMSPgbSendForContentThread;
import com.tzdr.business.service.thread.SMSPgbSenderThread;
import com.tzdr.business.service.thread.SMSSendForContentThread;
import com.tzdr.business.service.thread.SMSSenderThread;
import com.tzdr.business.service.togetherTrade.TogetherUserListService;
import com.tzdr.business.service.tradecalendar.TradeDayService;
import com.tzdr.business.service.wuser.WUserService;
import com.tzdr.common.baseservice.BaseServiceImpl;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.utils.BigDecimalUtils;
import com.tzdr.common.utils.Dates;
import com.tzdr.common.utils.MessageUtils;
import com.tzdr.common.utils.RandomCodeUtil;
import com.tzdr.common.utils.TypeConvert;
import com.tzdr.common.web.support.EasyUiPageInfo;
import com.tzdr.common.web.support.MultiListParam;
import com.tzdr.domain.cache.CacheManager;
import com.tzdr.domain.cms.entity.user.User;
import com.tzdr.domain.dao.userTrade.HandTradeDao;
import com.tzdr.domain.vo.HandTradeVo;
import com.tzdr.domain.vo.UserTradeCmsVo;
import com.tzdr.domain.vo.UserTradeVo;
import com.tzdr.domain.vo.WellGoldHandVo;
import com.tzdr.domain.web.entity.Account;
import com.tzdr.domain.web.entity.HandTrade;
import com.tzdr.domain.web.entity.NoticeRecord;
import com.tzdr.domain.web.entity.ParentAccount;
import com.tzdr.domain.web.entity.TradeConfig;
import com.tzdr.domain.web.entity.UserFund;
import com.tzdr.domain.web.entity.UserTrade;
import com.tzdr.domain.web.entity.WUser;

/**
 * 
 * @zhouchen 2015年5月4日
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class HandTradeService extends BaseServiceImpl<HandTrade, HandTradeDao> {

	@Autowired
	private AuthService authService;

	@Autowired
	private HandTradeDao handTradeDao;

	@Autowired
	private WUserService wUserService;

	@Autowired
	private AccountService accountService;

	@Autowired
	private ParentAccountService parentAccountService;

	@Autowired
	private TradeConfigService tradeConfigService;

	@Autowired
	private TradeDayService tradeDayService;

	@Autowired
	private CombineInfoService combineInfoService;

	@Autowired
	private FeeDuductionService feeDuductionService;

	@Autowired
	private UserFundService userFundService;

	@Autowired
	private UserTradeService userTradeService;

	@Autowired
	private NoticeRecordService noticeRecordService;

	@Autowired
	private TogetherUserListService togetherUserListService;

	@Autowired
	private SchemeLifeCycleRecordService schemeLifeCycleRecordService;

	public static final Logger logger = LoggerFactory
			.getLogger(HandTradeService.class);

	/**
	 * 获取列表数据
	 *
	 * @param easyUiPage
	 * @param searchParams
	 * @return
	 */
	public PageInfo<Object> queryDatas(EasyUiPageInfo easyUiPage,
									   Map<String, Object> searchParams) {
		PageInfo<Object> pageInfo = new PageInfo<Object>(easyUiPage.getRows(),
				easyUiPage.getPage());
		String sql = " SELECT handTrade.id,userTrade.id tradeId,usr.mobile, uver.tname, sum(userTrade.lever_money) leverMoney, userTrade.group_id groupId,"
				+ " userTrade.account, MAX(userTrade.natural_days) naturalDays, userTrade.lever, handTrade.audit_status auditStatus,"
				+ " handTrade.create_user createUser, handTrade.create_time createTime, handTrade.update_user auditUser,"
				+ " handTrade.update_time auditTime FROM w_hand_trade handTrade, w_user_trade userTrade, w_user usr, "
				+ " w_user_verified uver WHERE  userTrade.id = handTrade.trade_id  and userTrade.fee_type=1 AND usr.id = userTrade.uid AND usr.id = uver.uid and handTrade.`type` = 0 "
				+ "  GROUP BY userTrade.group_id ORDER BY handTrade.create_time ASC  ";
		List<Object> params = Lists.newArrayList();
		MultiListParam multilistParam = new MultiListParam(easyUiPage,
				searchParams, params, sql);

		pageInfo = multiListPageQuery(multilistParam, HandTradeVo.class);
		return pageInfo;
	}


	/**
	 * 获取审核通过划账失败列表
	 *
	 * @param easyUiPage
	 * @param searchParams
	 * @return
	 */
	public PageInfo<Object> queryTransferFailList(EasyUiPageInfo easyUiPage,
												  Map<String, Object> searchParams) {
		PageInfo<Object> pageInfo = new PageInfo<Object>(easyUiPage.getRows(),
				easyUiPage.getPage());

		String sql = " SELECT trade.account_id accountId, wuser.id uid, verified.tname uname, wuser.mobile,"
				+ " trade.combine_id combineId, trade.account, trade.group_id groupId, trade.`password`, "
				+ " trade.money totalLending, trade.lever_money totalLeverMoney, trade.append_lever_money totalAppendLeverMoney, "
				+ " trade.warning warning, trade.`open` `open`, trade.id, trade.starttime, trade.endtime, trade.`status`, "
				+ " trade.buy_status buyStatus, trade.sell_status sellStatus, trade.hs_belong_broker hsBelongBroker, "
				+ " trade.unit_number unitNumber, trade.parent_account_no parentAccountNo, trade.asset_id assetId FROM w_user_trade trade,"
				+ " w_user wuser, w_user_verified verified, w_hand_trade hat WHERE trade.`status` = 0 AND wuser.id = trade.uid "
				+ " AND wuser.id = verified.uid and trade.fee_type=1 AND  hat.trade_id = trade.id AND hat.transfer_status = 0 AND hat.audit_status = 1 and hat.`type` = 0 "
				+ " ORDER BY trade.addtime ASC  ";
		// params 查询参数 依次 存入
		List<Object> params = Lists.newArrayList();
		
		/*String code = authService.getCurrentUser().getOrganization().getCode();
		params.add(code + "%");
		sql = getPermissionSql(sql, code, params, authService.getCurrentUser()
				.getId());*/

		MultiListParam multilistParam = new MultiListParam(easyUiPage,
				searchParams, params, sql);
		pageInfo = multiListPageQuery(multilistParam, UserTradeCmsVo.class);
		return pageInfo;
	}


	private void setOperateLog(HandTrade handTrade, String operateContent,
							   String operateType) {
		User loginUser = authService.getCurrentUser();
		handTrade.setOperateContent(operateContent);
		if (StringUtil.equals(operateType, "edit")) {
			handTrade.setUpdateTime(Dates.getCurrentLongDate());
			handTrade.setUpdateUser(loginUser.getRealname());
			handTrade.setUpdateUserOrg(loginUser.getOrganization().getName());
			handTrade.setUpdateUserId(loginUser.getId());
			return;
		}

		handTrade.setCreateTime(Dates.getCurrentLongDate());
		handTrade.setCreateUser(loginUser.getRealname());
		handTrade.setCreateUserOrg(loginUser.getOrganization().getName());
		handTrade.setCreateUserId(loginUser.getId());
		return;
	}

	public boolean createHandTrade(HandTradeVo handTradeVo) {
		WUser wUser = wUserService.getWUserByMobile(handTradeVo.getMobile());

		if (ObjectUtil.equals(wUser, null)) {
			throw new UserTradeException("no.user", null);
		}

		double capitalMargin = handTradeVo.getLeverMoney();
		int lever = handTradeVo.getLever();
		long borrowPeriod = handTradeVo.getNaturalDays().longValue();

		double capitalAmount = BigDecimalUtils.mul(capitalMargin, lever);
		double totalLeverMoney = BigDecimalUtils.add(capitalMargin,
				capitalAmount);

		if (capitalAmount < 1500
				|| capitalAmount > CacheManager.getMaxCapitalAmount()) {
			throw new UserTradeException("no.total.lever.money", null);
		}

		TradeConfig config = tradeConfigService.findTradeConfig(
				(int) borrowPeriod, capitalMargin, lever);
		if (ObjectUtil.equals(config, null)) {
			throw new UserTradeException("no.interest.config", null);
		}
		// 利息（天）
		double interestFee = BigDecimalUtils.mulRound(
				config.getDailyInterest(), capitalAmount);
		// 管理费（天）
		double manageFee = BigDecimalUtils.mulRound(
				config.getDailyManagementFee(), capitalAmount);

		double shortLine = 0.0;
		double openLine = 0.0;
		if (1 <= lever && lever <= 5) {
			shortLine = BigDecimalUtils.mul(capitalAmount, 1.1);
			openLine = BigDecimalUtils.mul(capitalAmount, 1.07);
		} else if (lever == 6) {
			shortLine = BigDecimalUtils.mul(capitalAmount, 1.0867);
			openLine = BigDecimalUtils.mul(capitalAmount, 1.06);
		} else if (lever == 7) {
			shortLine = BigDecimalUtils.mul(capitalAmount, 1.0771);
			openLine = BigDecimalUtils.mul(capitalAmount, 1.0529);
		} else if (lever == 8) {
			shortLine = BigDecimalUtils.mul(capitalAmount, 1.07);
			openLine = BigDecimalUtils.mul(capitalAmount, 1.0475);
		} else if (lever == 9) {
			shortLine = BigDecimalUtils.mul(capitalAmount, 1.0644);
			openLine = BigDecimalUtils.mul(capitalAmount, 1.0433);
		} else if (lever == 10) {
			shortLine = BigDecimalUtils.mul(capitalAmount, 1.06);
			openLine = BigDecimalUtils.mul(capitalAmount, 1.04);
		} else if (lever == 11) {
			shortLine = BigDecimalUtils.mul(capitalAmount, 1.0682);
			openLine = BigDecimalUtils.mul(capitalAmount, 1.0409);
		} else if (lever == 12) {
			shortLine = BigDecimalUtils.mul(capitalAmount, 1.0625);
			openLine = BigDecimalUtils.mul(capitalAmount, 1.0375);
		} else if (lever == 13) {
			shortLine = BigDecimalUtils.mul(capitalAmount, 1.0577);
			openLine = BigDecimalUtils.mul(capitalAmount, 1.0346);
		} else if (lever == 14) {
			shortLine = BigDecimalUtils.mul(capitalAmount, 1.0536);
			openLine = BigDecimalUtils.mul(capitalAmount, 1.0321);
		} else if (lever == 15) {
			shortLine = BigDecimalUtils.mul(capitalAmount, 1.05);
			openLine = BigDecimalUtils.mul(capitalAmount, 1.03);
		}

		shortLine = BigDecimalUtils.round(shortLine, 0);
		openLine = BigDecimalUtils.round(openLine, 0);

		UserTrade userTrade = new UserTrade();
		userTrade.setWuser(wUser);
		userTrade.setType((short) 0);
		userTrade.setFeeType((short) 1);
		userTrade.setTradeStart((short) 0);
		userTrade.setMoney(capitalAmount);
		userTrade.setWarning(shortLine);
		userTrade.setOpen(openLine);
		userTrade.setLever(lever);
		userTrade.setLeverMoney(capitalMargin);
		userTrade.setAddtime(Dates.getCurrentLongDate());
		String currentDay = Dates.format(new Date(), Dates.CHINESE_DATE_FORMAT_LONG);
		Date trade = Dates.parse(currentDay, Dates.CHINESE_DATE_FORMAT_LONG);
		userTrade.setStarttime(trade.getTime() / 1000);
		String expirationDate = tradeDayService.getEndDate(currentDay, (int) borrowPeriod);
		Date estimateEnd = Dates.parse(expirationDate, Dates.CHINESE_DATE_FORMAT_LONG);
		userTrade.setEstimateEndtime(estimateEnd.getTime() / 1000);
		userTrade.setNaturalDays((long) borrowPeriod);
		// trade days
		long tradeDays = tradeDayService.getTradeDays(currentDay, expirationDate);
		userTrade.setStartdays((int) tradeDays);
		// 日(管理费)
		userTrade.setFeeDay(manageFee);
		// 月(利息)
		userTrade.setFeeMonth(interestFee);
		userTrade.setTotalLeverMoney(totalLeverMoney);

		// 0.首次利息计算
		double firstFee = BigDecimalUtils.round(userTrade.getFeeMonth() * userTrade.getNaturalDays(), 2);
		// 配资中
		userTrade.setStatus((short) 0);

		ParentAccount parentAccount = null;
		if (StringUtil.isBlank(handTradeVo.getParentAccountId())) {
			parentAccount = userTradeService.findAviParentAccount(userTrade, wUser);
		} else {
			parentAccount = parentAccountService.get(handTradeVo.getParentAccountId());
		}
		if (ObjectUtil.equals(parentAccount, null)) {
			throw new UserTradeException("no.parent.account", null);
		}

		Account account = null;
		if (StringUtil.isBlank(handTradeVo.getAccount())) {
			account = accountService.findOneByParentAccountAndStatus(parentAccount, (short) 0);
		} else {
			account = new Account();
			account.setAccountName(handTradeVo.getAccountName());
			account.setAccount(handTradeVo.getAccount());
			account.setPassword(handTradeVo.getPassword());
			account.setAssetId(handTradeVo.getAssetId());
			account.setParentAccount(parentAccount);
			account.setInsuranceNo(handTradeVo.getInsuranceNo()); //设置保险单号
			account.setStatus((short) 1);
			accountService.saveAccount(account);
		}

		if (ObjectUtil.equals(account, null)) {
			throw new UserTradeException("no.avi.accout", null);
		}


		String token = RandomCodeUtil.genToken(6);
		userTrade.setAssetId(account.getAssetId());
		userTrade.setProgramNo("T" + RandomCodeUtil.genToken(7));
		userTrade.setGroupId("T" + token);
		String childrenCombineId = combineInfoService.getHundSunCombineId(account.getAssetId());
		userTrade.setCombineId(childrenCombineId);
		userTrade.setAccountId(account.getId());

		userTrade.setAccount(account.getAccount());
		userTrade.setPassword(account.getPassword());
		// 所属券商
		userTrade.setHsBelongBroker(parentAccount.getSecuritiesBusinessValue());
		userTrade.setParentAccountNo(parentAccount.getAccountNo());
		userTrade.setUnitNumber(parentAccount.getUnitNumber());
		// 首次利息
		userTrade.setApr(firstFee);
		userTradeService.save(userTrade);

		// 4.w_user用户基本信息表中修改可用余额,资金总额.

		// 扣配资后
		double afterMoney = BigDecimalUtils.sub(wUser.getAvlBal(),
				userTrade.getLeverMoney());
		// 扣利息后
		double avlBal = BigDecimalUtils.sub(wUser.getAvlBal(),
				userTrade.getLeverMoney() + firstFee);

		// 扣第一天管理费后
		double manageBal = BigDecimalUtils.sub(wUser.getAvlBal(),
				userTrade.getLeverMoney() + firstFee + userTrade.getFeeDay());

		wUser.setUserType(WUser.UserType.TRADE);
		if (userTrade.getTradeStart() == 0) {
			if (manageBal >= 0) {
				wUser.setAvlBal(BigDecimalUtils.round(manageBal, 2));
				wUserService.updateUser(wUser);
			} else {
				throw new UserTradeException("no.cash.accout", null);
			}
		}

		// 6.生成一条w_user_fund 用户资金记录表 记录配资记录.还有一条利息记录
		// (1)配资记录
		UserFund userFund = new UserFund();
		userFund.setUid(wUser.getId());
		userFund.setNo(userTrade.getId());
		userFund.setLid(userTrade.getGroupId());
		userFund.setRid(userTrade.getProgramNo());
		// 类型为配资支出
		userFund.setType(10);
		userFund.setMoney(-userTrade.getLeverMoney());
		userFund.setAmount(BigDecimalUtils.round(afterMoney, 2));
		userFund.setPayStatus((short) 1);
		userFund.setAddtime(Dates.getCurrentLongDate());
		userFund.setUptime(Dates.getCurrentLongDate());
		userFund.setRemark("支付配资本金" + userTrade.getLeverMoney() + "元");
		userFundService.save(userFund);

		// (2)利息记录
		UserFund userFundLx = new UserFund();
		userFundLx.setUid(wUser.getId());
		userFundLx.setNo(userTrade.getId());
		userFundLx.setLid(userTrade.getGroupId());
		userFundLx.setRid(userTrade.getProgramNo());
		// 类型为利息费
		userFundLx.setType(11);
		userFundLx.setMoney(-firstFee);
		userFundLx.setAmount(BigDecimalUtils.round(avlBal, 2));
		userFundLx.setPayStatus((short) 1);
		userFundLx.setAddtime(Dates.getCurrentLongDate() + 1);
		userFundLx.setUptime(Dates.getCurrentLongDate() + 1);
		userFundLx.setRemark("支付利息" + firstFee + "元");
		userFundService.save(userFundLx);
		if (userTrade.getTradeStart() == 0) {
			// (3)当天管理费记录
			UserFund userFundManage = new UserFund();
			userFundManage.setUid(wUser.getId());
			userFundManage.setNo(userTrade.getId());
			userFundManage.setLid(userTrade.getGroupId());
			userFundManage.setRid(userTrade.getProgramNo());
			// 类型为管理费
			userFundManage.setType(12);
			userFundManage.setMoney(-userTrade.getFeeDay());
			userFundManage.setAmount(BigDecimalUtils.round(manageBal, 2));
			userFundManage.setPayStatus((short) 1);
			userFundManage.setAddtime(Dates.getCurrentLongDate() + 2);
			userFundManage.setUptime(Dates.getCurrentLongDate() + 2);
			userFundManage.setRemark("支付管理费" + userTrade.getFeeDay() + "元");
			userFundService.save(userFundManage);
		}
		// 7.更新母账户钱，标记子账户为使用
		parentAccount
				.setFundsBalance(BigDecimalUtils.sub(
						parentAccount.getFundsBalance(),
						userTrade.getTotalLeverMoney()));
		synchronized (this) {
			parentAccountService.updateParentAccount(parentAccount);
		}
		account.setStatus((short) 1);
		accountService.updateAccount(account);
		// 8.更手工开户表
		HandTrade handTrade = new HandTrade();
		handTrade.setTradeId(userTrade.getId());
		setOperateLog(handTrade, "新增手工开户", "add");
		save(handTrade);


		return true;
	}

	public HandTrade findByTradeId(String tradeId) {
		List<HandTrade> list = handTradeDao.findByTradeId(tradeId);
		if (CollectionUtils.isEmpty(list)) {
			return null;
		}
		return list.get(0);
	}

	public void auditTrade(String tradeId, boolean isPass) throws Exception {
		UserTrade userTrade = userTradeService.get(tradeId);
		WUser wuser = wUserService.getUser(userTrade.getWuser().getId());
		if (ObjectUtil.equals(userTrade, null)) {
			throw new UserTradeException("no.usertrade", null);
		}
		HandTrade handTrade = findByTradeId(tradeId);
		if (ObjectUtil.equals(handTrade, null)) {
			throw new UserTradeException("no.handtrade", null);
		}

		// 设置审核人信息
		setOperateLog(handTrade, "审核手工配资", "edit");

		if (isPass) {
			handTrade.setAuditStatus(1);
			String parentCombineId = combineInfoService
					.getHundSunCombineId(userTrade.getUnitNumber());
			// 转钱成功
			if (userTradeService.transferMoney(userTrade.getParentAccountNo(),
					parentCombineId, userTrade.getCombineId(),
					userTrade.getTotalLeverMoney(), "申请方案资金划转")) {
				userTrade.setStatus((short) 1);
				userTradeService.update(userTrade);
				handTrade.setTransferStatus(1);

				//发短信
				Map<String, String> map = Maps.newHashMap();
				map.put("group", userTrade.getGroupId());
				map.put("starttime", Dates.format(
						Dates.parseLong2Date(userTrade.getStarttime()),
						Dates.CHINESE_DATE_FORMAT_LINE));

				new SMSPgbSenderThread(wuser.getMobile(),
						"ihuyi.trade.ok.code.template", map).start();
			}
			update(handTrade);
			//配资审核通过后花钱到恒生子账号，发送配资成功短信，并校验余额是否够扣次日管理费，如不足则发送次日余额不足扣费短信。
			String nextDay = Dates.format(Dates.addDay(new Date()), Dates.CHINESE_DATE_FORMAT_LONG);
			boolean isNextTradeDay = tradeDayService.isTradeDay(nextDay);
			if (isNextTradeDay) {
				if (BigDecimalUtils.sub(wuser.getAvlBal(), userTrade.getFeeDay()) < 0) {
					String content = MessageUtils.message("fee.fill.deduction.balance.next.day.not.enough");
					new SMSPgbSendForContentThread(wuser.getMobile(), content, 2000).start();
					//保存通知记录
					noticeRecordService.save(new NoticeRecord(wuser.getId(), content, 3));
				}
			}
			//生成协议
			//new ContractsaveThread(userTrade,"").start();
		} else {
			//审核不通过
			handTrade.setAuditStatus(2);
			update(handTrade);
			//方案结束
			userTrade.setStatus((short) 2);
			userTrade.setEndtime(Dates.getCurrentLongDate());
			userTrade.setTransferState(TypeConvert.TRANSFER_STATE_SUCCESSFUL);
			userTrade.setFinishedMoney(userTrade.getTotalLeverMoney());
			userTrade.setAccrual(0.0);
			userTradeService.update(userTrade);
			//子账户结束
			Account account = accountService.get(userTrade.getAccountId());
			account.setStatus((short) 2);
			accountService.updateAccount(account);
			//配资撤回资金流水
			//配资撤回金额
			tradeRevoke(userTrade, wuser);
		}
	}

	/**
	 * 扣费流程公共方法
	 *
	 * @param userTrade
	 * @param estimateEndtime
	 * @param deductionType
	 */
	@Transactional
	private synchronized void deductionProcess(UserTrade userTrade, Long estimateEndtime, int deductionType){
		//扣费之前 判断当天是否已经扣费，扣费之后将不再扣
		if (userFundService.isDeductionTodayFee(userTrade.getId())) {
			return;
		}

		Double feeDay = ObjectUtil.equals(null, userTrade.getFeeDay()) ? 0 : userTrade.getFeeDay();
		Double feeMonth = ObjectUtil.equals(null, userTrade.getFeeMonth()) ? 0 : userTrade.getFeeMonth();
		WUser wUser = userTrade.getWuser();
		if (ObjectUtil.equals(null, wUser)) {
			logger.debug("配资方案id：" + userTrade.getId() + ",对应的用户不存在。");
			return;
		}
		String userId = wUser.getId();
		if (StringUtil.isBlank(userId)) {
			logger.debug("配资方案id：" + userTrade.getId() + ",对应的用户ID不存在。");
			return;
		}

		//获取总的扣费金额 是只扣利息  还是 管理费。还是都扣 .如果扣除费用为0，直接返回
		double deductionMoney = getDeductionMoney(deductionType, feeDay, feeMonth);
		if (deductionMoney <= 0) {
			return;
		}

		//资金记录备注
		String feeDayRemark = MessageUtils.message("fee.deduction.management.expense.remark", Dates.format(new Date(), Dates.CHINESE_DATE_FORMAT_LINE), feeDay);
		String feeMonthRemark = MessageUtils.message("fee.deduction.interest.remark", Dates.format(new Date(), Dates.CHINESE_DATE_FORMAT_LINE), feeMonth);
		//够扣费用
		if (isCanFeeDeduction(userId, deductionMoney)) {
			// 扣费
			switch (deductionType) {
				case 1:
					deductionMoney(userTrade, 12, feeDay, feeDayRemark, NumberUtils.toShort("1"));
					break;
				case 2:
					deductionMoney(userTrade, 11, feeMonth, feeMonthRemark, NumberUtils.toShort("1"));
					break;
				case 3:
					deductionMoney(userTrade, 12, feeDay, feeDayRemark, NumberUtils.toShort("1"));
					deductionMoney(userTrade, 11, feeMonth, feeMonthRemark, NumberUtils.toShort("1"));
					break;
				default:
					break;
			}
			return;
		}

		//不够扣费用
		switch (deductionType) {
			case 1:
				deductionMoney(userTrade, 12, feeDay, feeDayRemark, NumberUtils.toShort("0"));
				break;
			case 2:
				deductionMoney(userTrade, 11, feeMonth, feeMonthRemark, NumberUtils.toShort("0"));
				break;
			case 3:
				// 如果 余额 不够扣除利息 和 管理费。
				//利息和管理费先扣金额大的，金额大的不够扣时，继续扣小金额。如果小金额不够扣，则利息和管理费扣费记录全部为“未支付”状态。
				deductionFeeDayAndFeeMonth(userId, userTrade, feeDay, feeMonth);
				break;
			default:
				break;
		}
	}

	public double sumUserFundByTradeId(String tradeId) {
		String sql = "SELECT ROUND(ifnull(SUM(wuf.money),0),2) FROM w_user_fund wuf WHERE wuf.`no`=?";
		List<Object> params = new ArrayList<Object>();
		params.add(tradeId);
		Double b = (Double) nativeQueryOne(sql, params);
		return b.doubleValue();
	}

	;


	/**
	 * 划账失败，手动处理后，标记方案对应状态为可用并发送短信
	 *
	 * @param tradeId
	 */
	public void afterHandTransfer(String tradeId) {
		UserTrade userTrade = userTradeService.get(tradeId);
		if (ObjectUtil.equals(userTrade, null)) {
			throw new UserTradeException("no.usertrade", null);
		}
		WUser wuser = wUserService.getUser(userTrade.getWuser().getId());
		HandTrade handTrade = findByTradeId(tradeId);
		if (ObjectUtil.equals(handTrade, null)) {
			throw new UserTradeException("no.handtrade", null);
		}
		userTrade.setStatus((short) 1);
		userTradeService.update(userTrade);
		handTrade.setTransferStatus(1);
		super.update(handTrade);
		//发短信
		Map<String, String> map = Maps.newHashMap();
		map.put("group", userTrade.getGroupId());
		map.put("starttime", Dates.format(
				Dates.parseLong2Date(userTrade.getStarttime()),
				Dates.CHINESE_DATE_FORMAT_LINE));

		new SMSPgbSenderThread(wuser.getMobile(),
				"ihuyi.trade.ok.code.template", map).start();
	}


	/**
	 * 涌金版待审核列表
	 *
	 * @param easyUiPage
	 * @param searchParams
	 * @return
	 */
	public PageInfo<Object> queryWellGoldDatas(EasyUiPageInfo easyUiPage,
											   Map<String, Object> searchParams, int type) {
		PageInfo<Object> pageInfo = new PageInfo<Object>(easyUiPage.getRows(),
				easyUiPage.getPage());

		String sql = " SELECT userTrade.activity_type activityType, userTrade.fee_type feeType,userTrade.new_status newStatus,userTrade.trade_start tradeStart, handTrade.id, userTrade.id tradeId, usr.mobile, uver.tname, sum(userTrade.lever_money) leverMoney,"
				+ " sum(userTrade.money) money, sum( userTrade.total_lever_money ) totalLeverMoney, userTrade.warning, userTrade.`open` openline,"
				+ " userTrade.group_id groupId, handTrade.audit_status auditStatus, handTrade.create_time createTime,userTrade.voucher_actual_money voucherActualMoney FROM w_hand_trade handTrade,"
				+ " w_user_trade userTrade, w_user usr, w_user_verified uver WHERE userTrade.id = handTrade.trade_id AND usr.id = userTrade.uid "
				+ "AND usr.id = uver.uid AND handTrade.`type` in (1,2) and userTrade.fee_type in (2,3)  and handTrade.`audit_status` = 0  GROUP BY userTrade.group_id ORDER BY handTrade.create_time ASC ";

		if (type == 1) {
			sql = " SELECT temp.* FROM ("
					+ " ( SELECT userTrade.activity_type activityType, userTrade.fee_type feeType,userTrade.new_status newStatus,handTrade.id, userTrade.id tradeId, usr.mobile, uver.tname,"
					+ " sum(userTrade.lever_money) leverMoney, sum(userTrade.money) money, sum( userTrade.total_lever_money ) totalLeverMoney, userTrade.warning, userTrade.`open` openline,"
					+ " userTrade.group_id groupId, userTrade.account, acc.account_name accountName, handTrade.audit_status auditStatus, "
					+ " handTrade.create_time createTime, handTrade.update_user auditUser, handTrade.update_time auditTime,userTrade.voucher_actual_money voucherActualMoney,usr.user_type userType FROM "
					+ " w_hand_trade handTrade, w_user_trade userTrade, w_user usr, w_user_verified uver, w_account acc WHERE userTrade.id = handTrade.trade_id AND usr.id = userTrade.uid AND usr.id = uver.uid AND handTrade.`type` in (0,1,2) "
					+ " AND handTrade.`audit_status` = 1 and userTrade.fee_type in (0,1,2,3)  AND acc.id = userTrade.account_id GROUP BY userTrade.group_id ORDER BY handTrade.update_time DESC"
					+ " ) "
					+ " UNION "
					+ " ( SELECT userTrade.activity_type activityType, userTrade.fee_type feeType,userTrade.new_status newStatus,handTrade.id, userTrade.id tradeId, usr.mobile, uver.tname, "
					+ " sum(userTrade.lever_money) leverMoney, sum(userTrade.money) money, sum( userTrade.total_lever_money ) totalLeverMoney,"
					+ " userTrade.warning, userTrade.`open` openline, userTrade.group_id groupId, '' account, '' accountName, "
					+ " handTrade.audit_status auditStatus, handTrade.create_time createTime, handTrade.update_user auditUser,"
					+ " handTrade.update_time auditTime,userTrade.voucher_actual_money voucherActualMoney,usr.user_type userType FROM w_hand_trade handTrade, w_user_trade userTrade, w_user usr, w_user_verified uver "
					+ " WHERE userTrade.id = handTrade.trade_id AND usr.id = userTrade.uid AND usr.id = uver.uid AND handTrade.`type` in (0,1,2) AND "
					+ " handTrade.`audit_status` in(0,2) and userTrade.fee_type in (0,1,2,3) GROUP BY userTrade.group_id "
					+ " )"
					+ " ) temp ";
		}

		List<Object> params = Lists.newArrayList();
		MultiListParam multilistParam = new MultiListParam(easyUiPage,
				searchParams, params, sql);

		pageInfo = oldMultiListPageQuery(multilistParam, WellGoldHandVo.class);
		return pageInfo;
	}

	/**
	 * 涌金版开户审核
	 *
	 * @param tradeId
	 * @param isPass
	 */
	public void auditWellGoldTrade(String tradeId, boolean isPass, WellGoldHandVo wellGoldHandVo) throws Exception {
		UserTrade userTrade = userTradeService.get(tradeId);
		WUser wuser = wUserService.getUser(userTrade.getWuser().getId());
		if (ObjectUtil.equals(userTrade, null)) {
			throw new UserTradeException("no.usertrade", null);
		}
		HandTrade handTrade = findByTradeId(tradeId);
		if (ObjectUtil.equals(handTrade, null)) {
			throw new UserTradeException("no.handtrade", null);
		}

		// 设置审核人信息
		setOperateLog(handTrade, "审核涌金版配资方案", "edit");

		if (isPass) {
			//1.保存账户信息
			Account account = accountService.saveWellGoldAccount(wellGoldHandVo, wellGoldHandVo.getAccountGenre());

			//2.保存账户信息到 配资方案中
			int feeType = 0;
			switch (wellGoldHandVo.getAccountGenre()) {
				case 0:
					feeType = 1;
					break; //1:手工开户(钱江版)
				case 1:
					feeType = 2;
					break; //2：手工开户(涌金版)
				case 2:
					feeType = 3;
					break; //3:手工开户(同花顺)
			}
			//钱江版审核的时候，必须输入对应的单元序号
			String childrenCombineId = null;
			if (wellGoldHandVo.getAccountGenre() == 0) {
				if (StringUtil.isBlank(account.getAssetId())) {
					throw new UserTradeException("no.assetId", null);
				} else {
					childrenCombineId = combineInfoService.getHundSunCombineId(account.getAssetId());
					if (StringUtil.isBlank(childrenCombineId)) {
						throw new UserTradeException("no.combineId", new Object[]{account.getAssetId()});
					}
				}
			}
			userTrade.setAccountId(account.getId());
			userTrade.setAccount(account.getAccount());
			userTrade.setPassword(account.getPassword());
			userTrade.setFeeType((short) feeType);
			userTrade.setAssetId(account.getAssetId());
			userTrade.setCombineId(childrenCombineId);
			// 所属券商
			userTrade.setHsBelongBroker(account.getParentAccount().getSecuritiesBusinessValue());
			userTrade.setParentAccountNo(account.getParentAccount().getAccountNo());
			userTrade.setUnitNumber(account.getParentAccount().getUnitNumber());
			userTrade.setStatus((short) 1);
			Boolean inTime = isInDate();
			if (UserTrade.ActivityType.TOGETHER_TRADE == wellGoldHandVo.getActivityType() && inTime) {
				userTrade.setStarttime(new Date().getTime()/1000);
				String expirationDate = tradeDayService.getEndDate(Dates.format(new Date(),Dates.CHINESE_DATE_FORMAT_LONG),userTrade.getNaturalDays().intValue());
				Date estimateEnd = Dates.parse(expirationDate, Dates.CHINESE_DATE_FORMAT_LONG);
				userTrade.setEstimateEndtime(estimateEnd.getTime()/1000);
				deductionProcess(userTrade, null, 1);
			}
			userTradeService.update(userTrade);

			//3.保存账户信息到人工审核处理中
			handTrade.setAuditStatus(1);
			handTrade.setType(wellGoldHandVo.getAccountGenre());

			this.update(handTrade);

			//发短信
			Map<String, String> map = Maps.newHashMap();
			map.put("group", userTrade.getGroupId());
			map.put("starttime", Dates.format(Dates.parseLong2Date(userTrade.getStarttime()), Dates.CHINESE_DATE_FORMAT_LINE));
			// 股票合买短信模版不同，并且需要虚拟合买数据
			String smsTemplate = "ihuyi.trade.ok.code.template";
			if (UserTrade.ActivityType.TOGETHER_TRADE == wellGoldHandVo.getActivityType()) {
				smsTemplate = "together.ihuyi.trade.ok.code.template";
				// 虚拟股票合买数据
				this.togetherUserListService.generateTogetherUsers(userTrade.getGroupId());
				new SMSSenderThread(wuser.getMobile(), smsTemplate, map).start();

			}
			else if (UserTrade.ActivityType.MONTH_TRADE == wellGoldHandVo.getActivityType()) {
				smsTemplate = "month.trade.ok.code.template";
				new SMSPgbSenderThread(wuser.getMobile(), smsTemplate, map).start();
			}
			else {
				new SMSPgbSenderThread(wuser.getMobile(), smsTemplate, map).start();
			}

			//配资审核通过后花钱到恒生子账号，发送配资成功短信，并校验余额是否够扣次日管理费，如不足则发送次日余额不足扣费短信。
			String nextDay = Dates.format(Dates.addDay(new Date()), Dates.CHINESE_DATE_FORMAT_LONG);
			boolean isNextTradeDay = tradeDayService.isTradeDay(nextDay);

			if (isNextTradeDay) {
				if (BigDecimalUtils.sub(wuser.getAvlBal(), userTrade.getFeeDay()) < 0) {
					String notEnoughContent = MessageUtils.message("fee.fill.deduction.balance.next.day.not.enough");

					if (UserTrade.ActivityType.TOGETHER_TRADE == wellGoldHandVo.getActivityType()) {
						new SMSSendForContentThread(wuser.getMobile(), notEnoughContent, 2000).start();
						
					} else if (UserTrade.ActivityType.MONTH_TRADE != wellGoldHandVo.getActivityType()){
						new SMSPgbSendForContentThread(wuser.getMobile(), notEnoughContent, 2000).start();
					}

					//保存通知记录
					noticeRecordService.save(new NoticeRecord(wuser.getId(), notEnoughContent, 3));
				}
			}
			//生成协议
			//new ContractsaveThread(userTrade,"").start();
		} else {
			//审核不通过
			handTrade.setAuditStatus(2);
			update(handTrade);
			//方案结束
			userTrade.setStatus((short) 2);
			userTrade.setEndtime(Dates.getCurrentLongDate());
			userTrade.setTransferState(TypeConvert.TRANSFER_STATE_SUCCESSFUL);
			userTrade.setFinishedMoney(userTrade.getTotalLeverMoney());
			userTrade.setAccrual(0.0);
			userTradeService.update(userTrade);

			//配资撤回资金流水
			//配资撤回金额
			tradeRevoke(userTrade, wuser);
		}
	}

	/**
	 * 涌金版终结方案  改变状态
	 */
	public void endWellGoldTrade(String id) {
		HandTrade handTrade = get(id);
		if (ObjectUtil.equals(handTrade, null)) {
			throw new UserTradeException("no.handtrade", null);
		}

		handTrade.setEndSubTime(Dates.getCurrentLongDate());
		handTrade.setAuditEndStatus(0);
		super.update(handTrade);
	}


	/**
	 * 根据用户id和配资组合号修改配资关联表状态
	 *
	 * @param groupId
	 * @param uid
	 */
	public void updateTradeAuditEndStatus(String groupId, String uid) {
		String sql = " select id from w_user_trade w where uid=? and w.group_id=? GROUP BY w.group_id  ORDER BY starttime DESC";
		List<Object> params = Lists.newArrayList();
		params.add(uid);
		params.add(groupId);
		List<UserTradeVo> list = nativeQuery(sql, params,
				UserTradeVo.class);
		if (!CollectionUtils.isEmpty(list)) {
			UserTradeVo tradevo = list.get(0);
			String tradeId = tradevo.getId();
			HandTrade trade = this.getHandTradeByTradeId(tradeId);
			if (trade != null) {
				trade.setAuditEndStatus(0);
				trade.setEndSubTime(new Date().getTime() / 1000);
				this.update(trade);
			}
		}
	}

	/**
	 * 根据配资id获取 手工配资关联表
	 *
	 * @return
	 */
	private HandTrade getHandTradeByTradeId(String tradeId) {
		List<HandTrade> list = this.getEntityDao().findByTradeId(tradeId);
		return list.get(0);
	}


	/**
	 * 根据tradeId 和 type 求和userfund
	 *
	 * @param tradeId
	 * @param type
	 * @return
	 */
	public double findByTradeIdAndType(String tradeId, int type) {
		String sql = "SELECT ROUND(ifnull(SUM(wuf.money),0),2) FROM w_user_fund wuf WHERE wuf.`no`=? and type=?";
		List<Object> params = new ArrayList<Object>();
		params.add(tradeId);
		params.add(type);
		Double b = (Double) nativeQueryOne(sql, params);
		return b.doubleValue();
	}

	;

	/**
	 * 查询抵扣券收入和利息的和
	 *
	 * @param tradeId
	 * @return
	 */
	public double queryInterest(String tradeId) {
		String sql = "SELECT ROUND(ifnull(SUM(wuf.money),0),2) FROM w_user_fund wuf WHERE wuf.`no`=? and type in (11,24)";
		List<Object> params = new ArrayList<Object>();
		params.add(tradeId);
		Double b = (Double) nativeQueryOne(sql, params);
		return b.doubleValue();
	}

	;


	public void tradeRevoke(UserTrade userTrade, WUser wuser) {
		//配资撤回资金流水
		//配资撤回金额
		double returnMoney = findByTradeIdAndType(userTrade.getId(), 10);
		revokeUserFund(userTrade, wuser, returnMoney, 15, "配资撤回", 1);

		//配资管理费撤回
		double returnFeeMoney = findByTradeIdAndType(userTrade.getId(), 12);
		if (returnFeeMoney < 0) {
			revokeUserFund(userTrade, wuser, returnFeeMoney, 25, "配资管理费撤回", 2);
		}

		//配资利息撤回
		double returnInterestMoney = queryInterest(userTrade.getId());
		if (returnInterestMoney < 0) {
			revokeUserFund(userTrade, wuser, returnInterestMoney, 26, "配资利息撤回", 3);
		}

	}


	private void revokeUserFund(UserTrade userTrade, WUser wuser, double revokeMoney,
								int revokeType, String remark, long time) {
		double balance = BigDecimalUtils.addRound(wuser.getAvlBal(), -revokeMoney);
		UserFund userfund = new UserFund();
		userfund.setMoney(-revokeMoney);
		userfund.setType(revokeType);
		userfund.setPayStatus(TypeConvert.PAID);
		userfund.setRemark(TypeConvert.payRemark(remark,
				userfund.getMoney()));
		userfund.setUid(wuser.getId());
		userfund.setNo(userTrade.getId());
		userfund.setLid(userTrade.getGroupId());
		userfund.setRid(userTrade.getProgramNo());
		userfund.setAmount(balance);
		userfund.setAddtime(Dates.getCurrentLongDate() + time);
		userfund.setUptime(Dates.getCurrentLongDate() + time);

		userFundService.arrearsProcess(userfund);

//		userFundService.save(userfund);
//		wuser.setAvlBal(balance);
//		wUserService.update(wuser);
	}


	public static boolean isInDate() throws Exception {
		Calendar calendar1  = Calendar.getInstance();
		calendar1.set(Calendar.HOUR_OF_DAY,8);
		calendar1.set(Calendar.MINUTE,0);
		calendar1.set(Calendar.SECOND, 0);
		Long begin =calendar1.getTime().getTime();
		Calendar calendar2  = Calendar.getInstance();
		calendar2.set(Calendar.HOUR_OF_DAY,14);
		calendar2.set(Calendar.MINUTE,45);
		calendar2.set(Calendar.SECOND, 0);
		Long end = calendar2.getTime().getTime();
		Boolean isInDate = false;
		Long now = new Date().getTime();
		if (begin < now && now < end) {
			isInDate = true;
		}
		return isInDate;
	}

	/**
	 * 判断可用余额是否足以扣取费用
	 *
	 * @param userId
	 * @param deductionMoney
	 * @return
	 */
	private boolean isCanFeeDeduction(String userId, Double deductionMoney) {
		WUser wUser = wUserService.getUser(userId);
		if (ObjectUtil.equals(null, wUser)) {
			logger.debug("根据用户id：" + userId + "，找不到对应的数据.");
			return false;
		}
		Double avlBal = wUser.getAvlBal();
		if (ObjectUtil.equals(null, avlBal) || avlBal == 0) {
			return false;
		}
		if (avlBal >= deductionMoney) {
			return true;
		}

		return false;
	}

	/**
	 * 获取是否 该扣的费用
	 *
	 * @param deductionType
	 * @param feeDay        管理费
	 * @param feeMonth      利息
	 * @return
	 */
	private double getDeductionMoney(int deductionType, Double feeDay, Double feeMonth) {
		double deductionMoney = 0;
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
	 * 如果 余额 不够扣除利息 和 管理费。够扣哪个扣哪个
	 * 利息和管理费先扣金额大的，金额大的不够扣时，继续扣小金额。如果小金额不够扣，则利息和管理费扣费记录全部为“未支付”状态。
	 *
	 * @param
	 * @param userTrade
	 * @param feeDay
	 * @param feeMonth
	 */
	private void deductionFeeDayAndFeeMonth(String userId, UserTrade userTrade, Double feeDay, Double feeMonth) {
		// 如果 余额 不够扣除利息 和 管理费。
		//利息和管理费先扣金额大的，金额大的不够扣时，继续扣小金额。如果小金额不够扣，则利息和管理费扣费记录全部为“未支付”状态。
		WUser wUser = wUserService.get(userId);
		Double avlBal = ObjectUtil.equals(null, wUser.getAvlBal()) ? NumberUtils.toDouble("0") : wUser.getAvlBal();
		//资金记录备注
		String feeDayRemark = MessageUtils.message("fee.deduction.management.expense.remark", Dates.format(new Date(), Dates.CHINESE_DATE_FORMAT_LINE), feeDay);
		String feeMonthRemark = MessageUtils.message("fee.deduction.interest.remark", Dates.format(new Date(), Dates.CHINESE_DATE_FORMAT_LINE), feeMonth);

		if (avlBal >= feeDay) {
			deductionMoney(userTrade, 12, feeDay, feeDayRemark, NumberUtils.toShort("1"));
			deductionMoney(userTrade, 11, feeMonth, feeMonthRemark, NumberUtils.toShort("0"));
		} else if (avlBal >= feeMonth) {
			deductionMoney(userTrade, 12, feeDay, feeDayRemark, NumberUtils.toShort("0"));
			deductionMoney(userTrade, 11, feeMonth, feeMonthRemark, NumberUtils.toShort("1"));
		} else {
			deductionMoney(userTrade, 12, feeDay, feeDayRemark, NumberUtils.toShort("0"));
			deductionMoney(userTrade, 11, feeMonth, feeMonthRemark, NumberUtils.toShort("0"));
		}
	}

	/**
	 * 扣除费用
	 */
	private synchronized void deductionMoney(UserTrade userTrade, Integer type, Double deductionMoney, String remark, short payStatus) {

		if (0 == deductionMoney) {
			return;
		}

		WUser wUser = userTrade.getWuser();
		String userId = wUser.getId();
		UserFund userFund = new UserFund(userId, userTrade.getId(), type, -deductionMoney, Dates.getCurrentLongDate(), remark, payStatus);
		userFund.setAddtime(Dates.getCurrentLongDate());
		userFund.setLid(userTrade.getGroupId());
		userFund.setRid(userTrade.getProgramNo());
		userFundService.deductionMoney(userFund, userId);
	}
}

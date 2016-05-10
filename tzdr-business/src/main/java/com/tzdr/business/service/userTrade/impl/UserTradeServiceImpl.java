package com.tzdr.business.service.userTrade.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import jodd.util.ObjectUtil;
import jodd.util.StringUtil;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.dozer.DozerBeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.hundsun.t2sdk.interfaces.T2SDKException;
import com.tzdr.business.cms.service.auth.AuthService;
import com.tzdr.business.exception.DataMapException;
import com.tzdr.business.exception.UserTradeException;
import com.tzdr.business.service.account.AccountService;
import com.tzdr.business.service.account.ParentAccountService;
import com.tzdr.business.service.api.hundsun.HundsunJres;
import com.tzdr.business.service.combine.CombineInfoService;
import com.tzdr.business.service.datamap.DataMapService;
import com.tzdr.business.service.exception.UserTradeConcurrentException;
import com.tzdr.business.service.failinfo.FreezeFailInfoService;
import com.tzdr.business.service.feededuction.FeeDuductionService;
import com.tzdr.business.service.pay.UserFundService;
import com.tzdr.business.service.thread.SMSPgbSendForContentThread;
import com.tzdr.business.service.thread.SMSPgbSenderThread;
import com.tzdr.business.service.thread.SMSSenderThread;
import com.tzdr.business.service.togetherTrade.TogetherTradeService;
import com.tzdr.business.service.togetherTrade.TogetherUserListService;
import com.tzdr.business.service.tradecalendar.TradeDayService;
import com.tzdr.business.service.userTrade.HandTradeService;
import com.tzdr.business.service.userTrade.NoticeRecordService;
import com.tzdr.business.service.userTrade.SchemeLifeCycleRecordService;
import com.tzdr.business.service.userTrade.UserTradeService;
import com.tzdr.business.service.volume.VolumeDetailService;
import com.tzdr.business.service.wuser.WUserService;
import com.tzdr.common.api.hundsun.data.Combostock;
import com.tzdr.common.api.hundsun.data.Entrust;
import com.tzdr.common.api.hundsun.data.EntrustResult;
import com.tzdr.common.api.hundsun.data.StockCurrent;
import com.tzdr.common.api.ihuyi.PgbSMSSender;
import com.tzdr.common.baseservice.BaseServiceImpl;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.exception.EmailExceptionHandler;
import com.tzdr.common.utils.BigDecimalUtils;
import com.tzdr.common.utils.ConfUtil;
import com.tzdr.common.utils.ConnditionVo;
import com.tzdr.common.utils.Dates;
import com.tzdr.common.utils.EmailUtils;
import com.tzdr.common.utils.Exceptions;
import com.tzdr.common.utils.MessageUtils;
import com.tzdr.common.utils.RandomCodeUtil;
import com.tzdr.common.utils.TypeConvert;
import com.tzdr.common.web.support.EasyUiPageInfo;
import com.tzdr.common.web.support.MultiListParam;
import com.tzdr.domain.cache.CacheManager;
import com.tzdr.domain.cache.DataDicKeyConstants;
import com.tzdr.domain.cms.entity.organization.Organization;
import com.tzdr.domain.cms.entity.user.User;
import com.tzdr.domain.constants.Constant;
import com.tzdr.domain.dao.userTrade.HandTradeDao;
import com.tzdr.domain.dao.userTrade.UserTradeDao;
import com.tzdr.domain.entity.DataMap;
import com.tzdr.domain.vo.AccountVo;
import com.tzdr.domain.vo.BillingFeeVo;
import com.tzdr.domain.vo.EndMoneyVo;
import com.tzdr.domain.vo.EndProgramVo;
import com.tzdr.domain.vo.MonitorSchemeVo;
import com.tzdr.domain.vo.NotEnoughBalanceVo;
import com.tzdr.domain.vo.ParentAccountVo;
import com.tzdr.domain.vo.PositionDetailsVo;
import com.tzdr.domain.vo.SettingNotWarningVo;
import com.tzdr.domain.vo.SettingWarningVo;
import com.tzdr.domain.vo.TotalMarginVo;
import com.tzdr.domain.vo.TransferVo;
import com.tzdr.domain.vo.UserTradeCmsVo;
import com.tzdr.domain.vo.UserTradeMonitorVo;
import com.tzdr.domain.vo.UserTradeSummaryCmsVo;
import com.tzdr.domain.vo.UserTradeSummaryVo;
import com.tzdr.domain.vo.UserTradeTransfer;
import com.tzdr.domain.vo.UserTradeVo;
import com.tzdr.domain.web.entity.Account;
import com.tzdr.domain.web.entity.FreezeFailInfo;
import com.tzdr.domain.web.entity.HandTrade;
import com.tzdr.domain.web.entity.NoticeRecord;
import com.tzdr.domain.web.entity.ParentAccount;
import com.tzdr.domain.web.entity.SchemeLifeCycleRecord;
import com.tzdr.domain.web.entity.UserFund;
import com.tzdr.domain.web.entity.UserTrade;
import com.tzdr.domain.web.entity.VolumeDetail;
import com.tzdr.domain.web.entity.WUser;

/**
 * @Description: (用户配资业务信息管理业务实现层)
 * @ClassName: UserTradeServiceImpl
 * @author wangpinqun
 * @author QingLiu
 * @date 2015年1月4日 下午7:48:17
 */
@Service("userTradeService")
@Transactional(propagation = Propagation.REQUIRED)
public class UserTradeServiceImpl extends
		BaseServiceImpl<UserTrade, UserTradeDao> implements UserTradeService {

	private static Logger log = LoggerFactory
			.getLogger(UserTradeServiceImpl.class);

	@Autowired
	private WUserService wUserService;

	@Autowired
	private ParentAccountService parentAccountService;

	@Autowired
	private AccountService accountService;

	@Autowired
	private UserFundService userFundService;

	@Autowired
	private AuthService authService;

	@Autowired
	private UserTradeDao userTradeDao;

	@Autowired
	private FeeDuductionService FeeDuductionService;

	@Autowired
	private NoticeRecordService noticeRecordService;

	@Autowired
	private TradeDayService tradeDayService;

	@Autowired
	private FreezeFailInfoService freezeFailInfoService;

	@Autowired
	private SchemeLifeCycleRecordService schemeLifeCycleRecordService;

	@Autowired
	private CombineInfoService combineInfoService;

	@Autowired
	private DataMapService dataMapService;

	@Autowired
	private VolumeDetailService volumeDetailService;

	@Autowired
	private HandTradeService handTradeService;
	
	@Autowired
	private TogetherTradeService togetherTradeService;
	
	@Autowired
	private TogetherUserListService togetherUserListService;

	@Override
	public boolean isActity_6600(WUser wuser) {
		return WUser.UserType.WEB_REGIST.equals(wuser.getUserType())
				&& WUser.ActivityType.ACTIVITY_6600 == wuser.getActivityType();
	}

	@Override
	public List<UserTrade> findByWuserAndStatusOrderByAddtimeDesc(String uid,
			short status) {
		WUser wuser = wUserService.getUser(uid);
		return getEntityDao().findByWuserAndStatusOrderByAddtimeDesc(wuser,
				status);
	}

	@Override
	public Double getSumAccrualByWuserAndStatus(String uid, short status) {
		WUser wuser = wUserService.getUser(uid);
		return getEntityDao().getSumAccrualByWuserAndStatus(wuser, status) == null ? 0
				: getEntityDao().getSumAccrualByWuserAndStatus(wuser, status);
	}

	/**
	 * 查询配资金额
	 */
	public List<UserTradeVo> queryUserTotalLending(String uid, short status,
			short status2) {
		WUser wuser = wUserService.getUser(uid);
		/*
		 * List<UserTradeVo> result = getEntityDao()
		 * .queryUserTradeVoByWuserAndStatus(wuser, status, status2);
		 */
		List<UserTradeVo> result = queryUserTradeVoByUidAndStatus(uid, status,
				status2);
		return result;
	}

	@Deprecated
	@Override
	public List<UserTradeVo> queryUserTradeVoByWuserAndStatus(String uid,
			short status, short status2) {
		//WUser wuser = wUserService.getUser(uid);
		/*
		 * List<UserTradeVo> result = getEntityDao()
		 * .queryUserTradeVoByWuserAndStatus(wuser, status, status2);
		 */
		List<UserTradeVo> result = queryUserTradeVoByUidAndStatus(uid, status,
				status2);
	/*	try {
			if (result == null || result.isEmpty()) {
				return result;
			}
			HundsunJres hundsunJres = HundsunJres.getInstance();
			String userToken = hundsunJres.Login();
			// 方案盈亏
			for (UserTradeVo userTradeVo : result) {
				String combineId = combineInfoService
						.getHundSunCombineId(userTradeVo.getAssetId());
				if (StringUtil.isBlank(combineId)) {
					continue;
				}
				List<StockCurrent> stockCurrentList = hundsunJres
						.funcAmStockCurrentQry(userToken,
								userTradeVo.getParentAccountNo(), combineId);
				if (CollectionUtils.isEmpty(stockCurrentList)) {
					continue;
				}

				if (!CollectionUtils.isEmpty(stockCurrentList)) {
					StockCurrent stockCurrent = stockCurrentList.get(0);
					double assetTotalValue = BigDecimalUtils.addRound(
							stockCurrent.getCurrentCash(),
							stockCurrent.getMarketValue());
					userTradeVo
							.setTotalAccrual(BigDecimalUtils.sub(
									assetTotalValue,
									userTradeVo.getTotalOperateMoney()));
				}
			}
			hundsunJres.Logout(userToken);
		} catch (T2SDKException e) {
			log.error(this.getClass().getName() + e.getMessage());
			throw new UserTradeException("user.trade.query.fail", null);
		}*/
		return result;
	}

	@Override
	public List<UserTradeCmsVo> queryMarginRemindData(
			EasyUiPageInfo easyUiPage, Map<String, Object> searchParams) {
		String sql = " SELECT trade.account_id accountId, wuser.id uid, verified.tname uname, wuser.mobile, trade.combine_id combineId, trade.account, trade.group_id groupId, trade.`password`, sum(trade.money) totalLending, sum(trade.lever_money) totalLeverMoney, sum(trade.append_lever_money) totalAppendLeverMoney, sum(trade.warning) warning, sum(trade.`open`) open, trade.starttime, trade.endtime, trade.`status`, trade.notice_status noticeStatus, trade.hs_belong_broker hsBelongBroker, trade.unit_number unitNumber, trade.parent_account_no parentAccountNo, trade.asset_id assetId,(SELECT MAX(notice_time) from w_notice_record wnr where wnr.type=2 and wnr.`status`=1 and wnr.uid=wuser.id and trade.group_id=wnr.group_id) lastNoticeTime FROM w_user_trade trade, w_user wuser, w_user_verified verified WHERE trade.`status` = 1 and trade.fee_type in(0,1)   AND wuser.id = trade.uid AND wuser.id = verified.uid GROUP BY trade.group_id ORDER BY verified.tname DESC ";
		// sql = getPermissionSql(sql);

		MultiListParam multilistParam = new MultiListParam(easyUiPage,
				searchParams, null, sql);

		List<UserTradeCmsVo> list = nativeQuery(multilistParam,
				UserTradeCmsVo.class);
		List<UserTradeCmsVo> tempTradeVos = Lists.newArrayList();

		if (CollectionUtils.isEmpty(list)) {
			return tempTradeVos;
		}

		// 获取组合资产信息
		List<StockCurrent> stockCurrents = getAllStockCurrents();
		if (CollectionUtils.isEmpty(stockCurrents)) {
			return tempTradeVos;
		}

		for (StockCurrent stockCurrent : stockCurrents) {
			String scombineId = stockCurrent.getCombineId();
			String sfundAccount = stockCurrent.getFundAccount();
			for (UserTradeCmsVo tradeVo : list) {
				String combineId = tradeVo.getCombineId();
				String parentAccountNo = tradeVo.getParentAccountNo();
				if (StringUtil.isBlank(combineId)
						|| StringUtil.isBlank(parentAccountNo)) {
					continue;
				}

				if (StringUtil.equals(scombineId, combineId)
						&& StringUtil.equals(sfundAccount, parentAccountNo)) {
					double assetTotalValue = BigDecimalUtils.addRound(
							stockCurrent.getCurrentCash(),
							stockCurrent.getMarketValue());
					double warning = tradeVo.getWarning();
					if (assetTotalValue > warning) {
						continue;
					}
					tradeVo.setAssetTotalValue(assetTotalValue);
					tradeVo.setMinimumDeposit(BigDecimalUtils.sub(warning,
							assetTotalValue));
					tempTradeVos.add(tradeVo);
				}
			}
		}
		return tempTradeVos;
	}

	@Override
	public List<UserTradeCmsVo> queryNeedAdd(String uid) {
		String sql = " select uid,sum(accrual) as totalAccrual,w.asset_id as assetId,"
				+ "w.combine_id as combineId,group_id as groupId,min(starttime) as starttime,MAX(w.endtime) as endtime,"
				+ "SUM(total_lever_money) as totalOperateMoney,SUM(lever_money) as totalLending ,"
				+ "SUM(money) as totalLeverMoney,SUM(append_lever_money) as totalAppendLeverMoney,w.status,"
				+ "hs_belong_broker as hsBelongBroker,SUM(warning) as warning,SUM(`open`) as `open`,w.account,w.password,fee_type as feeType, ac.insurance_no insuranceNo "
				+ "from w_user_trade w LEFT JOIN w_account ac ON ac.id = w.account_id "
				+ "where w.status=1 and uid=? "
				+ "GROUP BY w.group_id  ORDER BY starttime DESC";

		List<Object> params = Lists.newArrayList();
		params.add(uid);
		List<UserTradeCmsVo> list = nativeQuery(sql.toString(), params,
				UserTradeCmsVo.class);
		List<UserTradeCmsVo> tempTradeVos = Lists.newArrayList();

		if (CollectionUtils.isEmpty(list)) {
			return tempTradeVos;
		}

		try {
			HundsunJres hundsunJres = HundsunJres.getInstance();
			String userToken = hundsunJres.Login();

			for (UserTradeCmsVo tradeVo : list) {
				if (tradeVo.getFeeType() == 2) {
					continue;
				}
				String combineId = combineInfoService
						.getHundSunCombineId(tradeVo.getAssetId());
				if (StringUtil.isBlank(combineId)) {
					continue;
				}
				List<StockCurrent> stockCurrentList = hundsunJres
						.funcAmStockCurrentQry(userToken,
								tradeVo.getParentAccountNo(), combineId);
				if (CollectionUtils.isEmpty(stockCurrentList)) {
					continue;
				}

				StockCurrent stockCurrent = stockCurrentList.get(0);
				double assetTotalValue = BigDecimalUtils.addRound(
						stockCurrent.getCurrentCash(),
						stockCurrent.getMarketValue());
				double warning = tradeVo.getWarning();
				if (assetTotalValue > warning) {
					continue;
				}
				tradeVo.setAssetTotalValue(assetTotalValue);
				tradeVo.setMinimumDeposit(BigDecimalUtils.sub(warning,
						assetTotalValue));
				tradeVo.setTotalAccrual(BigDecimalUtils.round(
						BigDecimalUtils.sub(assetTotalValue,
								tradeVo.getTotalOperateMoney()), 2));
				tempTradeVos.add(tradeVo);

			}
			hundsunJres.Logout(userToken);
		} catch (T2SDKException e) {
			log.error(this.getClass().getName() + e.getMessage());
			throw new UserTradeException("user.trade.query.add.fail", null);
		}
		return tempTradeVos;
	}

	@Override
	public List<UserTrade> findByAccount(String account) {
		return getEntityDao().findByAccount(account);
	}

	@Override
	public void changeNoticeStatus(String[] groupIds, Short noticeStatus) {
		for (String groupId : groupIds) {
			List<UserTrade> userTrades = getEntityDao().findByGroupId(groupId);
			if (CollectionUtils.isEmpty(userTrades)) {
				throw new UserTradeException(
						"user.trade.query.margin.remind.change.notice.fail",
						null);
			}

			WUser wUser = null;
			for (UserTrade userTrade : userTrades) {
				userTrade.setNoticeStatus(noticeStatus);
				update(userTrade);
				if (ObjectUtil.equals(null, wUser)) {
					wUser = userTrade.getWuser();
				}
			}

			// 保存通知记录
			noticeRecordService.save(new NoticeRecord(wUser.getId(), groupId,
					"补仓提醒电话通知用户："
							+ wUser.getUserVerified().getTname()
							+ " "
							+ CacheManager.getDataMapByKey(
									DataDicKeyConstants.CALL_NOTICE_STATUS,
									String.valueOf(noticeStatus)), 2,
					NumberUtils.toInt(String.valueOf(noticeStatus))));
		}
	}

	@Override
	public UserTradeTransfer addUserTradeAndOpenHundsun(UserTrade userTrade,
			WUser wuser, String volumeDetailId) {

		// 配资中
		userTrade.setStatus((short) 0);

		String token = RandomCodeUtil.genToken(6);
		userTrade.setProgramNo("T" + RandomCodeUtil.genToken(7));
		userTrade.setGroupId("T" + token);

		// 1.按规则查询可用母账户和管理单元号
		// 2.用母账户按规则查询可用子账户(合并1和2)
		ParentAccount parentAccount = null;
		// 参加8800活动
		if (isActity_6600(wuser)) {
			//parentAccount = findParentAccout(CacheManager.getParentAccount());
			// 配资中
			return 	saveHandleTrade(userTrade, wuser, volumeDetailId,Constant.FeeType.HAND_OPEN_WELL_GOLD,Constant.HandTradeType.WELL_GOLD_TYPE);
		} 
		else {
			parentAccount = findAviParentAccount(userTrade, wuser);
		}
		// 如果账户不存不存在，直接走涌金版
		if (ObjectUtil.equals(null, parentAccount)) {
			String isStart = dataMapService.getStartTierce();
			if (StringUtil.equals(DataDicKeyConstants.START_VALUE, isStart)) {
				return 	saveHandleTrade(userTrade, wuser, volumeDetailId,Constant.FeeType.HAND_OPEN_TIERCE,Constant.HandTradeType.TIERCE_TYPE);
			}
			return 	saveHandleTrade(userTrade, wuser, volumeDetailId,Constant.FeeType.HAND_OPEN_WELL_GOLD,Constant.HandTradeType.WELL_GOLD_TYPE);
		}

		Account account = accountService.findOneByParentAccountAndStatus(
				parentAccount, (short) 0);

		if (ObjectUtil.equals(account, null)) {
			String isStart = dataMapService.getStartTierce();
			if (StringUtil.equals(DataDicKeyConstants.START_VALUE, isStart)) {
				return 	saveHandleTrade(userTrade, wuser, volumeDetailId,Constant.FeeType.HAND_OPEN_TIERCE,Constant.HandTradeType.TIERCE_TYPE);
			}
			isStart = dataMapService.getStartWellGold();
			if (StringUtil.equals(DataDicKeyConstants.START_VALUE, isStart)) {
				return 	saveHandleTrade(userTrade, wuser, volumeDetailId,Constant.FeeType.HAND_OPEN_WELL_GOLD,Constant.HandTradeType.WELL_GOLD_TYPE);
			}
			throw new UserTradeException("no.avi.accout", null);
		}
		// 3.拿到子账户combineId
		String parentCombineId = combineInfoService
				.getHundSunCombineId(parentAccount.getUnitNumber());
		String childrenCombineId = combineInfoService
				.getHundSunCombineId(account.getAssetId());

		userTrade.setAssetId(account.getAssetId());
		userTrade.setCombineId(childrenCombineId);
		userTrade.setAccountId(account.getId());

		userTrade.setAccount(account.getAccount());
		userTrade.setPassword(account.getPassword());
		// 所属券商
		userTrade.setHsBelongBroker(parentAccount.getSecuritiesBusinessValue());
		userTrade.setParentAccountNo(parentAccount.getAccountNo());
		userTrade.setUnitNumber(parentAccount.getUnitNumber());

		// 保存配资信息信
		saveTradeInfo(userTrade, wuser, volumeDetailId);

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

		// 下一个交易日的账号要冻结
		if (userTrade.getTradeStart() == 1) {
			/*
			 * a)交易日15：30前配资时冻结恒生账号的逻辑不变。b)非交易日或交易日15：30及以后配资不冻结恒生账号。
			 */
			if (tradeDayService.isTradeFreezeTime()) {
				// 子账户状态需要修改
				HundsunJres hundsunJres = null;
				try {
					hundsunJres = HundsunJres.getInstance();
				} catch (T2SDKException e) {
					log.error(e.getMessage());
				}

				try {
					String userToken = hundsunJres.Login();
					// 走线下[账号、异常类型、异常信息]
					Boolean flag = hundsunJres.funcAmChangeOperatorInfo(
							userToken, new Long(userTrade.getAccount()),
							TypeConvert.USER_FREEZE_STATE, null, 180L, null,
							null, null);
					hundsunJres.Logout(userToken);
					if (!flag) {
						FreezeFailInfo freezeFailInfo = new FreezeFailInfo();
						freezeFailInfo.setAccount(userTrade.getAccount());
						freezeFailInfo.setTypeInfo("冻结失败");
						freezeFailInfo.setMessageText("调用恒生接口失败");
						freezeFailInfoService.save(freezeFailInfo);
					}
				} catch (Exception e) {
					e.printStackTrace();
					FreezeFailInfo freezeFailInfo = new FreezeFailInfo();
					freezeFailInfo.setAccount(userTrade.getAccount());
					freezeFailInfo.setTypeInfo("冻结失败");
					freezeFailInfo.setMessageText("调用恒生接口通讯异常");
					freezeFailInfoService.save(freezeFailInfo);
				}
			}
		}

		UserTradeTransfer userTradeTransfer = new UserTradeTransfer();
		userTradeTransfer.setUserTrade(userTrade);
		userTradeTransfer.setAccountNo(parentAccount.getAccountNo());
		userTradeTransfer.setParentCombineId(parentCombineId);
		userTradeTransfer.setChildrenCombineId(childrenCombineId);
		// 方案最长延期时间
		String endtime=TypeConvert.long1000ToDateStr((parentAccount.getAllocationDate()-5*Dates.DAY_LONG));
		userTradeTransfer.setParentEndTime(endtime);
		return userTradeTransfer;
	}

	// 失败应该有处理，发邮件给后台。
	public boolean transferMoney(String fundAccount, String parentCombineId,
			String childrenCombineId, double money, String remark) {
		return transferMoneyUserToken(fundAccount, parentCombineId,
				childrenCombineId, money, remark, null);
	}

	// 失败应该有处理，发邮件给后台。
	public boolean transferMoneyUserToken(String fundAccount,
			String parentCombineId, String childrenCombineId, double money,
			String remark, String userToken) {
		boolean flag = Boolean.FALSE;
		try {
			HundsunJres hundsunJres = HundsunJres.getInstance();
			if (userToken == null) {
				userToken = hundsunJres.Login();
			}
			// money最多两位小数
			flag = hundsunJres.funcAmAssetMove(userToken, fundAccount,
					parentCombineId, childrenCombineId,
					BigDecimalUtils.round(money, 2), HundsunJres.CASH, remark);
			// 失败应该有处理.发邮件给后台
			hundsunJres.Logout(userToken);
		} catch (T2SDKException e) {
			log.error(MessageUtils.message("transfer.money.error",
					e.getMessage()));
			List<String> pramas = Lists.newArrayList();
			String methodName = this.getClass().getName() + "."
					+ "TransferMoney";
			String exception = e.getMessage();
			pramas.add(methodName);
			pramas.add("参数 fundAccount:" + fundAccount + "-parentCombineId:"
					+ parentCombineId + "-childrenCombineId:"
					+ childrenCombineId + "-money" + money + exception);
			try {
				EmailUtils.getInstance().sendMailTemp(
						ConfUtil.getContext("mail.to.dev"), "exceptionemail",
						pramas);
				throw new UserTradeException(
						MessageUtils.message("transfer.money.error"), null);
			} catch (Exception e1) {
				log.error(this.getClass().getName() + e.getMessage());
			}
		}
		return flag;
	}

	/**
	 * @param userTrade
	 * @param user
	 * @return 根据规则找母账户
	 */
	@Override
	public ParentAccount findAviParentAccount(UserTrade userTrade, WUser user) {
		int lever =userTrade.getLever();
		double totalLeverMoney = userTrade.getTotalLeverMoney();
		//结束时间=开始操盘时间+配资操盘天数+5
		long needEndTime=userTrade.getStarttime()+(userTrade.getNaturalDays()+5)*Dates.DAY_LONG;
		
		boolean isNew=WUser.UserType.WEB_REGIST.equals(user.getUserType());
		
		List<ParentAccountVo> parentList= getParentAccountList(lever,totalLeverMoney,needEndTime,isNew);
		if(parentList==null){
			//判断是不是涌金版
			return isCanUseWellGold(null);
		}
		for(ParentAccountVo parentAccountVo:parentList){
			ParentAccount parentAccount= parentAccountService.get(parentAccountVo.getId());
			int size = accountService.findByParentAccountAndStatus(
					parentAccount, (short) 0).size();
			// 母账户要可用的子账户
			if (size > 0) {
				return parentAccount;
			}			
		}
		//判断是不是涌金版
		return isCanUseWellGold(null);
	}
	/*@Override
	public ParentAccount findAviParentAccount(UserTrade userTrade, WUser user) {
		double firstMoney = 1000000;
		int firstDay = 180;

		double secondMoney = 500000;
		int secondDay = 90;

		double totalLeverMoney = userTrade.getTotalLeverMoney();
		int day = userTrade.getStartdays();
		int lever = userTrade.getLever();

		double bigMoney = Double.MAX_VALUE;
		if (!StringUtil.isBlank(dataMapService.getBigAccountMoney())) {
			bigMoney = Double.parseDouble(dataMapService.getBigAccountMoney());
		}
		// 大客户用户专用
		if (totalLeverMoney >= bigMoney) {
			ParentAccount bigParentAccount = findBigParentAccout(totalLeverMoney);
			if (bigParentAccount != null) {
				return bigParentAccount;
			}
		}
		// 新用户专用
		if (WUser.UserType.WEB_REGIST.equals(user.getUserType())) {
			ParentAccount newParentAccount = findNewParentAccout(totalLeverMoney);
			if (newParentAccount != null) {
				return newParentAccount;
			}
		}

		if (totalLeverMoney <= firstMoney && day <= firstDay) {
			ParentAccount jParentAccount = findParentAccout(totalLeverMoney,
					"X");
			if (ObjectUtil.equals(jParentAccount, null)) {
				if (totalLeverMoney <= secondMoney && day < secondDay) {
					ParentAccount mParentAccount = findParentAccout(
							totalLeverMoney, "M");
					if (ObjectUtil.equals(mParentAccount, null)) {
						ParentAccount rParentAccount = findParentAccoutR(
								totalLeverMoney, "R", lever);
						if (ObjectUtil.equals(rParentAccount, null)) {
							ParentAccount oParentAccount = findParentAccout(
									totalLeverMoney, "O");
							return isCanUseWellGold(oParentAccount);
							/*
							 * if (ObjectUtil.equals(oParentAccount, null)) { //
							 * 可能要发邮件 throw new
							 * UserTradeException("no.avi.accout", null); } else
							 * { return oParentAccount; }
							 
						} else {
							return rParentAccount;
						}
					} else {
						return mParentAccount;
					}
				} else {
					ParentAccount rParentAccount = findParentAccoutR(
							totalLeverMoney, "R", lever);
					if (ObjectUtil.equals(rParentAccount, null)) {
						ParentAccount mParentAccount = findParentAccout(
								totalLeverMoney, "M");
						if (ObjectUtil.equals(mParentAccount, null)) {
							ParentAccount oParentAccount = findParentAccout(
									totalLeverMoney, "O");
							return isCanUseWellGold(oParentAccount);
							/*
							 * if (ObjectUtil.equals(oParentAccount, null)) { //
							 * 可能要发邮件 throw new
							 * UserTradeException("no.avi.accout", null); } else
							 * { return oParentAccount; }
							 
						} else {
							return mParentAccount;
						}
					} else {
						return rParentAccount;
					}
				}
			} else {
				return jParentAccount;
			}
		} else {
			if (totalLeverMoney > firstMoney && day <= firstDay) {
				ParentAccount rParentAccount = findParentAccoutR(
						totalLeverMoney, "R", lever);
				if (ObjectUtil.equals(rParentAccount, null)) {
					ParentAccount oParentAccount = findParentAccout(
							totalLeverMoney, "O");
					return isCanUseWellGold(oParentAccount);
					/*
					 * if (ObjectUtil.equals(oParentAccount, null)) { // 可能要发邮件
					 * throw new UserTradeException("no.avi.accout", null); }
					 * else { return oParentAccount; }
					 
				} else {
					return rParentAccount;
				}
			} else {
				ParentAccount oParentAccount = findParentAccout(
						totalLeverMoney, "O");
				return isCanUseWellGold(oParentAccount);
				/*
				 * if (ObjectUtil.equals(oParentAccount, null)) { // 可能要发邮件
				 * throw new UserTradeException("no.avi.accout", null); } else {
				 * return oParentAccount; }
				 
			}
		}
	}
	*/

	/**
	 * @param totalLeverMoney
	 *            总操盘资金
	 * @param accountType
	 *            母账户类型
	 * @return 可用母账户
	 */
	public ParentAccount findParentAccout(double totalLeverMoney,
			String accountType) {
		List<ParentAccount> parentAccountList = parentAccountService
				.findByAccountTypeOrderByPriorityNo(accountType);
		for (ParentAccount parentAccount : parentAccountList) {
			int size = accountService.findByParentAccountAndStatus(
					parentAccount, (short) 0).size();
			// 母账户要钱够和有可用的子账户
			if (BigDecimalUtils.sub(parentAccount.getFundsBalance(),
					parentAccount.getSubFunds()) >= totalLeverMoney && size > 0) {
				return parentAccount;
			}
		}
		return null;
	}

	/**
	 * @param totalLeverMoney
	 *            总操盘资金
	 * @param accountType
	 *            母账户类型
	 * @return 可用母账户
	 */
	public ParentAccount findParentAccoutR(double totalLeverMoney,
			String accountType, int lever) {
		List<ParentAccount> parentAccountList = parentAccountService
				.findByAccountTypeOrderByPriorityNo(accountType);
		for (ParentAccount parentAccount : parentAccountList) {
			int size = accountService.findByParentAccountAndStatus(
					parentAccount, (short) 0).size();
			// 母账户要钱够和有可用的子账户
			if (lever >= parentAccount.getMultipleLimit()
					&& BigDecimalUtils.sub(parentAccount.getFundsBalance(),
							parentAccount.getSubFunds()) >= totalLeverMoney
					&& size > 0) {
				return parentAccount;
			}
		}
		return null;
	}

	/**
	 * @param totalLeverMoney
	 *            总操盘资金
	 * @return 可用母账户
	 */
	public ParentAccount findNewParentAccout(double totalLeverMoney) {
		ParentAccount parentAccount = findParentAccout(CacheManager
				.getParentAccount());
		if (parentAccount != null && parentAccount.getStatus()) {
			int size = accountService.findByParentAccountAndStatus(
					parentAccount, (short) 0).size();
			// 母账户要钱够和有可用的子账户
			if (BigDecimalUtils.sub(parentAccount.getFundsBalance(),
					parentAccount.getSubFunds()) >= totalLeverMoney && size > 0) {
				return parentAccount;
			}
		}

		return null;
	}

	/**
	 * @param totalLeverMoney
	 *            总操盘资金
	 * @return 大账户母账户
	 */
	public ParentAccount findBigParentAccout(double totalLeverMoney) {
		String bigParent = dataMapService.getBigAccountType();
		if (StringUtils.isBlank(bigParent)) {
			return null;
		}
		ParentAccount parentAccount = findParentAccout(bigParent);
		if (parentAccount != null) {
			int size = accountService.findByParentAccountAndStatus(
					parentAccount, (short) 0).size();
			// 母账户要钱够和有可用的子账户
			if (BigDecimalUtils.sub(parentAccount.getFundsBalance(),
					parentAccount.getSubFunds()) >= totalLeverMoney && size > 0) {
				return parentAccount;
			}
		}

		return null;
	}

	@Override
	public List<UserTrade> findByStatusAndStarttime(short status, Long Starttime) {
		return getEntityDao().findUserTrades(status, Starttime);
	}

	@SuppressWarnings("unchecked")
	public List<UserTrade> findOpeningAccountUserTrade(short status,short auditStatus, Long Starttime){
		String sql = " SELECT t.id FROM w_user_trade t,w_hand_trade h WHERE t.`status`=? AND t.starttime<=? AND t.id=h.trade_id AND h.audit_status=? ";
		List<Object> params = Lists.newArrayList();
		params.add(status);
		params.add(Starttime);
		params.add(auditStatus);
		List<UserTrade> list = nativeQuery(sql, params, UserTrade.class);
		List<String> ids = new ArrayList<String>();
		if(list != null && !list.isEmpty()){
			for (UserTrade userTrade : list) {
				ids.add(userTrade.getId());
			}
		}
		List<UserTrade> userTradeList = this.getEntityDao().findAll(ids);
		
		return userTradeList;
	}
	
	@Override
	public List<UserTrade> findSymbolDeductionUserTrade(short statusOne,
			short statusTwo, short auditStatus, Long Starttime) {
		
		List<UserTrade> list = this.findByStatusAndStarttime(statusOne, Starttime);
		
		List<UserTrade> openingAccountUserTrade = this.findOpeningAccountUserTrade(statusTwo, auditStatus, Starttime);
		
		List<UserTrade> userTradeList =  new ArrayList<UserTrade>();
		
		if(!CollectionUtils.isEmpty(list)){ //判断是否有操盘中的方案
			for (UserTrade userTrade : list) {  //遍历操盘中的方案，并且存到新的集合里
				userTradeList.add(userTrade);
			}
		}
		
		if(!CollectionUtils.isEmpty(openingAccountUserTrade)){  //判断是否有开户中的方案
			for (UserTrade userTrade : openingAccountUserTrade) {  //遍历开户中的方案，并且存到新的集合里
				userTradeList.add(userTrade);
			}
		}
		
		return userTradeList;
	}
	
	@Override
	public List<UserTradeCmsVo> getNoArrearsUserTrades() {

		String sql = "SELECT uid, GROUP_CONCAT(id) userTradeId FROM w_user_trade ut WHERE ut.`status` = 1 AND ut.activity_type = 0 AND NOT EXISTS ( SELECT uid FROM w_user_fund WHERE pay_status = 0 AND money < 0 AND uid = ut.uid GROUP BY uid ) GROUP BY ut.uid";
		@SuppressWarnings("unchecked")
		List<UserTradeCmsVo> list = nativeQuery(sql, null, UserTradeCmsVo.class);
		return list;
	}

	/*
	 * @see
	 * com.tzdr.business.service.userTrade.UserTradeService#queryUserTradeVoByWuser
	 * (java.lang.String)
	 */

	@Override
	public PageInfo<Object> queryUserTrade(String uid, int countOfCurrentPage,
			int currentPage, Map<String, Object> searchParams) {
		PageInfo<Object> pageInfo = new PageInfo<Object>(countOfCurrentPage,
				currentPage);
		// params 查询参数 依次 存入
		List<Object> params = Lists.newArrayList();
		params.add(uid);
		// String sql =
		// "select uid,parent_account_no as parentAccountNo,sum(accrual) as totalAccrual,asset_id as assetId,combine_id as combineId,group_id as groupId,min(starttime) as starttime,MAX(endtime) as endtime,SUM(total_lever_money) as totalOperateMoney,SUM(lever_money) as totalLeverMoney ,SUM(money) as totalLending,SUM(append_lever_money) as totalAppendLeverMoney,STATUS as status,hs_belong_broker as hsBelongBroker,SUM(warning) as warning,SUM(`open`) as `open`,account,password from w_user_trade w GROUP BY w.group_id HAVING uid=? ORDER BY starttime DESC";

		StringBuffer sql = new StringBuffer("select uid,parent_account_no as parentAccountNo,sum(accrual) as totalAccrual,w.asset_id as assetId, ");
		sql.append(" w.combine_id as combineId,group_id as groupId,min(starttime) as starttime,MAX(w.endtime) as endtime, ");
		sql.append(" SUM(total_lever_money) as totalOperateMoney,SUM(lever_money) as totalLeverMoney ,SUM(money) as totalLending, ");
		sql.append(" SUM(append_lever_money) as totalAppendLeverMoney,w.status,hs_belong_broker as hsBelongBroker, ");
		sql.append(" SUM(warning) as warning,SUM(`open`) as `open`,w.account,w.password ,");
		sql.append(" w.fee_type AS feeType,h.audit_status AS auditStatus,h.audit_end_status AS auditEndStatus, ac.insurance_no insuranceNo ");
		sql.append(" from w_user_trade w LEFT JOIN w_hand_trade h ON w.id=h.trade_id ");
		sql.append(" LEFT JOIN w_account ac ON ac.id = w.account_id ");
		sql.append(" WHERE uid=? and w.activity_type in (0,1,2,3)");
		sql.append(" GROUP BY w.group_id ORDER BY starttime DESC ");

		MultiListParam multilistParam = new MultiListParam(pageInfo,
				searchParams, sql.toString(), params);
		pageInfo = multiListPageQuery(multilistParam, UserTradeCmsVo.class);
		return pageInfo;
	}
	@Override
	public PageInfo<Object> queryUserTogetherTrade(String uid, int countOfCurrentPage,
			int currentPage, Map<String, Object> searchParams) {
		PageInfo<Object> pageInfo = new PageInfo<Object>(countOfCurrentPage,
				currentPage);
		// params 查询参数 依次 存入
		List<Object> params = Lists.newArrayList();
		params.add(uid);
		// String sql =
		// "select uid,parent_account_no as parentAccountNo,sum(accrual) as totalAccrual,asset_id as assetId,combine_id as combineId,group_id as groupId,min(starttime) as starttime,MAX(endtime) as endtime,SUM(total_lever_money) as totalOperateMoney,SUM(lever_money) as totalLeverMoney ,SUM(money) as totalLending,SUM(append_lever_money) as totalAppendLeverMoney,STATUS as status,hs_belong_broker as hsBelongBroker,SUM(warning) as warning,SUM(`open`) as `open`,account,password from w_user_trade w GROUP BY w.group_id HAVING uid=? ORDER BY starttime DESC";

		StringBuffer sql = new StringBuffer("select uid,parent_account_no as parentAccountNo,sum(accrual) as totalAccrual,w.asset_id as assetId, ");
		sql.append(" w.combine_id as combineId,group_id as groupId,min(starttime) as starttime,MAX(w.endtime) as endtime, ");
		sql.append(" SUM(total_lever_money) as totalOperateMoney,SUM(lever_money) as totalLeverMoney ,SUM(money) as totalLending, ");
		sql.append(" SUM(append_lever_money) as totalAppendLeverMoney,w.status,hs_belong_broker as hsBelongBroker, ");
		sql.append(" SUM(warning) as warning,SUM(`open`) as `open`,w.account,w.password ,");
		sql.append(" w.fee_type AS feeType,h.audit_status AS auditStatus,h.audit_end_status AS auditEndStatus, ac.insurance_no insuranceNo ");
		sql.append(" from w_user_trade w LEFT JOIN w_hand_trade h ON w.id=h.trade_id ");
		sql.append(" LEFT JOIN w_account ac ON ac.id = w.account_id ");
		sql.append(" WHERE uid=? and w.activity_type = 4");
		sql.append(" GROUP BY w.group_id ORDER BY starttime DESC ");

		MultiListParam multilistParam = new MultiListParam(pageInfo,
				searchParams, sql.toString(), params);
		pageInfo = multiListPageQuery(multilistParam, UserTradeCmsVo.class);
		return pageInfo;
	}

	@Override
	public PageInfo<UserTrade> queryDataPageByConndition(
			final PageInfo<UserTrade> page, final Map<String, Object> equals,
			final Map<String, String> isLike,
			final Map<String, String> groupNames,
			final Map<String, String> polys, final Map<String, Boolean> orders) {

		Page<UserTrade> pageData = this.userTradeDao.findAll(
				new Specification<UserTrade>() {
					@Override
					public Predicate toPredicate(Root<UserTrade> root,
							CriteriaQuery<?> query, CriteriaBuilder cb) {

						List<Predicate> pres = new ArrayList<Predicate>();
						if (equals != null && equals.size() > 0) {
							for (Map.Entry<String, Object> equalObj : equals
									.entrySet()) {
								String[] names = StringUtil.split(
										equalObj.getKey(), ".");
								if (names.length > 1) {
									Path expression = root.get(names[0]);
									expression = expression.get(names[1]);
									Predicate pre = cb.equal(expression,
											equalObj.getValue());
									pres.add(pre);
								} else {
									Path<Object> path = root.get(equalObj
											.getKey());
									Predicate pre = cb.equal(path,
											equalObj.getValue());
									pres.add(pre);
								}
							}
						}
						if (isLike != null && isLike.size() > 0) {
							for (Map.Entry<String, String> isLikeObj : isLike
									.entrySet()) {
								String[] names = StringUtil.split(
										isLikeObj.getKey(), ".");
								if (names.length > 1) {
									Path expression = root.get(names[0]);
									expression = expression.get(names[1]);
									Predicate pre = cb.like(expression, "%"
											+ isLikeObj.getValue() + "%");
									pres.add(pre);
								} else {
									Path<String> path = root.get(isLikeObj
											.getKey());
									Predicate pre = cb.like(path, "%"
											+ isLikeObj.getValue() + "%");
									pres.add(pre);
								}
							}
						}

						if (groupNames != null) {
							List<Path> groups = new ArrayList<Path>();
							for (Map.Entry<String, String> groupName : groupNames
									.entrySet()) {
								String[] names = StringUtil.split(
										groupName.getKey(), ".");
								if (names.length > 1) {
									Path expression = root.get(names[0]);
									expression = expression.get(names[1]);
									groups.add(expression);
									// query.groupBy(expression);
								} else {
									Path<String> expression = root
											.get(groupName.getKey());
									groups.add(expression);
									// query.groupBy(expression);
								}
							}
							if (groups != null && groups.size() > 0) {
								query.groupBy(groups.toArray(new Path[groups
										.size()]));
								query.getGroupRestriction();
							}
						}

						List<Order> queryOrders = new ArrayList<Order>();
						if (orders != null && orders.size() > 0) {
							for (Map.Entry<String, Boolean> order : orders
									.entrySet()) {
								if (order.getValue()) {
									queryOrders.add(cb.asc(root.get(order
											.getKey())));
								} else {
									queryOrders.add(cb.desc(root.get(order
											.getKey())));
								}
							}
						}
						if (queryOrders.size() > 0) {
							query.orderBy(queryOrders);
						}
						CriteriaQuery q = cb.createQuery();

						if (polys != null && polys.size() > 0) {
							List<Expression> polies = new ArrayList<Expression>();
							for (Map.Entry<String, String> polymerization : polys
									.entrySet()) {
								String[] names = StringUtil.split(
										polymerization.getKey(), ".");
								if (names.length > 1) {
									Path expression = root.get(names[0]);
									expression = expression.get(names[1]);
									Expression exp = TypeConvert
											.settingExpression(cb, expression,
													polymerization.getValue());
									polies.add(exp);
								} else {
									Path expression = root.get(polymerization
											.getKey());
									Expression exp = TypeConvert
											.settingExpression(cb, expression,
													polymerization.getValue());
									polies.add(exp);
								}
							}
							if (polies != null) {
								for (Expression exp : polies) {
									q.select(exp);
								}
								// query.multiselect(polies.toArray(new
								// Expression[polies.size()]));
							}
						}

						return cb.and(pres.toArray(new Predicate[pres.size()]));

					}
				},
				new PageRequest(page.getCurrentPage(), page
						.getCountOfCurrentPage()));
		page.setTotalCount((int) pageData.getTotalElements());
		page.setPageResults(pageData.getContent());
		return page;
	}

	@Override
	public PageInfo<SettingNotWarningVo> queryNotSettingWarning(
			PageInfo<SettingNotWarningVo> page, SettingNotWarningVo vo) {

		List<Object> params = new ArrayList<Object>();
		String sql = " SELECT u.id,u.account_id accountId, u.account, u.group_id groupId, "
				+ " u.money money, u.lever_money leverMoney, "
				+ " ( SELECT SUM(`open`) FROM w_user_trade WHERE group_id = u.group_id ) OPEN, "
				+ " ( SELECT SUM(total_lever_money) FROM w_user_trade WHERE group_id = u.group_id ) totalLeverMoney, "
				+ " ( SELECT SUM(warning) FROM w_user_trade WHERE group_id = u.group_id ) warning, "
				+ " u.addtime,u.starttime "
				+ " FROM w_user_trade u "
				+ " WHERE 1 = 1 AND u.warning_value_tag = 0 AND u.`status` = 1 "
				+ " AND u.starttime < " + TypeConvert.longCriticalTimeDay(1);
		if("2".equals(vo.getType())){
			sql+=" and u.fee_type=2 ";
		}else{
			sql+=" and u.fee_type!=2 ";
		}
		StringBuffer sqlBuf = new StringBuffer(sql);
		if (vo != null) {
			if (vo.getAccount() != null && !"".equals(vo.getAccount())) {
				sqlBuf.append(" AND u.account like ? ");
				params.add(vo.getAccount());
			}
		}
		sqlBuf.append(" ORDER BY u.addtime DESC");
		String code = authService.getCurrentUser().getOrganization().getCode();
		params.add(code + "%");
		return this.userTradeDao.queryDataPageBySql(
				page,
				getPermissionSql(sqlBuf.toString(), code, params, authService
						.getCurrentUser().getId()), SettingNotWarningVo.class,
				params.toArray());
	}

	@Override
	public PageInfo<SettingWarningVo> querySettingWarning(
			PageInfo<SettingWarningVo> page, SettingWarningVo vo) {
		List<Object> params = new ArrayList<Object>();

		String sql = " SELECT u.id,u.account_id accountId, u.account, u.group_id groupId,"
				+ "  u.money money, u.lever_money leverMoney, "
				+ " ( SELECT SUM(`open`) FROM w_user_trade WHERE group_id = u.group_id ) open, "
				+ " ( SELECT SUM(total_lever_money) FROM w_user_trade WHERE group_id = u.group_id ) totalLeverMoney, "
				+ " ( SELECT SUM(warning) FROM w_user_trade WHERE group_id = u.group_id ) warning, "
				+ " u.warning_process_time warningProcessTime, u.addtime "
				+ " FROM w_user_trade u WHERE 1 = 1 AND u.warning_value_tag = 1 AND u.status = 1"
				+ " AND u.starttime < " + TypeConvert.longCriticalTimeDay(1);

		StringBuffer sqlBuf = new StringBuffer(sql);
		if (vo != null) {
			if (vo.getAccount() != null && !"".equals(vo.getAccount())) {
				sqlBuf.append(" AND u.account like ? ");
				params.add(vo.getAccount());
			}
		}
		sqlBuf.append(" ORDER BY u.addtime DESC");
		String code = authService.getCurrentUser().getOrganization().getCode();
		params.add(code + "%");
		return this.userTradeDao.queryDataPageBySql(
				page,
				getPermissionSql(sqlBuf.toString(), code, params, authService
						.getCurrentUser().getId()), SettingWarningVo.class,
				params.toArray());

	}

	@Override
	public List<NotEnoughBalanceVo> getNotEnoughBalanceList(
			EasyUiPageInfo easyUiPage, Map<String, Object> searchParams) {
		String sql = "SELECT temp.*, us.notice_status noticeStatus, us.mobile mobile, us.avl_bal balance, verified.tname uname FROM ( SELECT uid, SUM(fee_month) interest, GROUP_CONCAT(id) userTradeIds, SUM(fee_day) fee FROM w_user_trade WHERE STATUS =? GROUP BY uid ) temp, w_user us, w_user_verified verified WHERE us.id = temp.uid AND verified.uid = us.id AND us.notice_status <> ?";
		List<Object> params = Lists.newArrayList();
		params.add(1);
		params.add(1);
		MultiListParam multilistParam = new MultiListParam(easyUiPage,
				searchParams, params, sql);
		List<NotEnoughBalanceVo> list = nativeQuery(multilistParam,
				NotEnoughBalanceVo.class);
		if (CollectionUtils.isEmpty(list)) {
			return list;
		}
		List<NotEnoughBalanceVo> tempList = Lists.newArrayList();
		for (NotEnoughBalanceVo enoughBalanceVo : list) {
			String uid = enoughBalanceVo.getUid();
			if (StringUtil.isBlank(uid)) {
				continue;
			}
			WUser wUser = wUserService.getUser(uid);
			if (ObjectUtil.equals(null, wUser)) {
				continue;
			}

			Double balance = wUser.getAvlBal();
			// 余额为空或0
			if (ObjectUtil.equals(null, balance) || balance == 0) {
				tempList.add(enoughBalanceVo);
				continue;
			}

			// 查看下面的 用户的下面的配资方案 ，并统计次日所应缴纳的全部费用
			String userTradeIds = enoughBalanceVo.getUserTradeIds();
			String[] tempTradeIds = userTradeIds.split(",");
			if (ArrayUtils.isEmpty(tempTradeIds)) {
				continue;
			}

			// 如果余额小于 所有配资方案费用
			double totalDeductionMoney = FeeDuductionService
					.getTotalDeductionMoney(tempTradeIds);
			if (balance < totalDeductionMoney) {
				tempList.add(enoughBalanceVo);
				continue;
			}
		}
		return tempList;
	}

	@Override
	public PageInfo<Object> findBuyLimitBuyStatus(EasyUiPageInfo easyUiPage,
			Map<String, Object> searchParams, Short limitBuyStaus) {

		String sql = " SELECT group_id groupId,uid, account account,GROUP_CONCAT(id) userTradeId from w_user_trade where limit_buy_status=? GROUP BY group_id ";
		List<Object> params = Lists.newArrayList();
		params.add(limitBuyStaus);

		MultiListParam multilistParam = new MultiListParam(easyUiPage,
				searchParams, params, sql);

		return multiListPageQuery(multilistParam, UserTradeCmsVo.class);
	}

	@Override
	public void changeArrearageStatus(String[] groupIds, boolean isArrearage) {
		for (String groupId : groupIds) {
			List<UserTrade> userTrades = getEntityDao().findByGroupId(groupId);
			if (CollectionUtils.isEmpty(userTrades)) {
				log.debug("根据配资组合id无法查询到相应配资");
				return;
			}

			for (UserTrade userTrade : userTrades) {
				// userTrade.setIsArrearage(isArrearage);
				update(userTrade);
			}
		}
	}

	@Override
	public List<UserTrade> findByGroupId(String groupId) {
		return getEntityDao().findByGroupIdOrderByAddtimeAsc(groupId);
	}

	@Override
	public PageInfo<Object> findTrade(PageInfo<Object> pageInfo) {
		List<Object> tempTradeVos = Lists.newArrayList();
		try {
			HundsunJres hundsunJres = HundsunJres.getInstance();
			String userToken = hundsunJres.Login();
			if (CollectionUtils.isEmpty(pageInfo.getPageResults())) {
				return pageInfo;
			}
			for (Object obj : pageInfo.getPageResults()) {
				UserTradeCmsVo userTradeCmsVo = (UserTradeCmsVo) obj;
				if (userTradeCmsVo.getStatus() == 1) {
					if (userTradeCmsVo.getFeeType() == 2) {
						tempTradeVos.add(userTradeCmsVo);
						continue;
					}
					String combineId = userTradeCmsVo.getCombineId();
					if (StringUtil.isBlank(combineId)) {
						continue;
					}
					if (StringUtil.isBlank(userTradeCmsVo.getParentAccountNo())) {
						continue;
					}
					List<StockCurrent> stockCurrentList = hundsunJres
							.funcAmStockCurrentQry(userToken,
									userTradeCmsVo.getParentAccountNo(),
									combineId);
					if (CollectionUtils.isEmpty(stockCurrentList)) {
						continue;
					}

					StockCurrent stockCurrent = stockCurrentList.get(0);
					double assetTotalValue = BigDecimalUtils.addRound(
							stockCurrent.getCurrentCash(),
							stockCurrent.getMarketValue());
					userTradeCmsVo.setTotalAccrual(BigDecimalUtils.round(
							BigDecimalUtils.sub(assetTotalValue,
									userTradeCmsVo.getTotalOperateMoney()), 2));
				}
				tempTradeVos.add(userTradeCmsVo);
			}
			hundsunJres.Logout(userToken);
		} catch (T2SDKException e) {
			log.error(this.getClass().getName() + e.getMessage());
			throw new UserTradeException("user.trade.query.page.fail", null);
		}
		pageInfo.setPageResults(tempTradeVos);
		return pageInfo;
	}

	@Override
	public void updateUserTradeByGroupId(String groupId, Integer stateValue) {
		List<UserTrade> userTrades = this.findByGroupId(groupId);
		for (UserTrade userTrade : userTrades) {
			userTrade.setWarningValueTag(stateValue);
			userTrade.setWarningProcessTime(TypeConvert.dbDefaultDate());
			this.update(userTrade);
		}
	}

	@Override
	public void updateUserTradeById(String id, Integer stateValue) {
		UserTrade userTrade = this.get(id);
		if (userTrade != null) {
			userTrade.setWarningValueTag(stateValue);
			userTrade.setWarningProcessTime(TypeConvert.dbDefaultDate());
			this.update(userTrade);
		}

	}

	/**
	 * 获取系统中 所有母账户下的 组合资产值
	 * 
	 * @return
	 */
	@Override
	public List<StockCurrent> getAllStockCurrents() {
		List<ParentAccount> parentAccounts = parentAccountService
				.findByAccountGenre(0);
		if (CollectionUtils.isEmpty(parentAccounts)) {
			return null;
		}

		List<StockCurrent> list = getAllStockCurrents(parentAccounts);
		// 如果获取的组合资产信息为空 ，进行容错2次 拉取
		if (CollectionUtils.isEmpty(list)) {
			return getAllStockCurrents(parentAccounts);
		}
		return list;
	}

	/**
	 * 获取所有单元资产
	 * 
	 * @param parentAccounts
	 * @return
	 */
	private List<StockCurrent> getAllStockCurrents(
			List<ParentAccount> parentAccounts) {
		List<StockCurrent> list = Lists.newArrayList();
		try {
			HundsunJres hundsunJres = HundsunJres.getInstance();
			String userToken = hundsunJres.Login();
			for (ParentAccount parentAccount : parentAccounts) {
				List<StockCurrent> stockCurrentList = hundsunJres
						.funcAmStockCurrentQry(userToken,
								parentAccount.getAccountNo(), null);
				if (CollectionUtils.isEmpty(stockCurrentList)
						|| StringUtil.isBlank(stockCurrentList.get(0)
								.getFundAccount())) {
					continue;
				}

				list.addAll(stockCurrentList);
			}
			Thread.sleep(1000);
			hundsunJres.Logout(userToken);
		} catch (Exception e) {
			log.error(this.getClass().getName() + e.getMessage());
			// 发送异常邮件
			EmailExceptionHandler.getInstance()
					.HandleException(
							e,
							"获取系统组合资产信息失败.此方法主要用于查询结果数据，主要用于方案监控，补仓提醒等cms功能。",
							this.getClass().getName()
									+ " : method-getAllStockCurrents");
		}
		return list;
	}

	@Override
	public PageInfo<Object> queryMonitorList(EasyUiPageInfo easyUiPage,
			Map<String, Object> searchParams, ConnditionVo connVo) {
		String sql = "SELECT trade.account_id accountId,trade.activity_type activityType, wuser.id uid, acc.account_name accountName, verified.tname uname, wuser.mobile, trade.combine_id combineId, trade.account, trade.group_id groupId, trade.`password`, sum(trade.money) totalLending, sum(trade.lever_money) totalLeverMoney, sum(trade.append_lever_money) totalAppendLeverMoney, sum(trade.warning) warning, sum(trade.`open`) `open`, trade.starttime, trade.endtime, trade.`status`, trade.buy_status buyStatus, trade.sell_status sellStatus, trade.hs_belong_broker hsBelongBroker, trade.unit_number unitNumber, trade.parent_account_no parentAccountNo, trade.asset_id assetId FROM w_user_trade trade, w_user wuser, w_user_verified verified, w_account acc WHERE trade.`status` = 1 AND wuser.id = trade.uid AND wuser.id = verified.uid AND trade.account_id = acc.id and trade.fee_type<2  GROUP BY trade.group_id ORDER BY trade.addtime ASC ";
		String code = authService.getCurrentUser().getOrganization().getCode();
		// params 查询参数 依次 存入
		List<Object> params = Lists.newArrayList();
		params.add(code + "%");
		sql = getPermissionSql(sql, code, params, authService.getCurrentUser()
				.getId());
		
		MultiListParam multilistParam = new MultiListParam(easyUiPage,
				searchParams, params, sql);

		PageInfo<Object> pageInfo = new PageInfo<Object>(easyUiPage.getRows(),
				easyUiPage.getPage());

		pageInfo = multiListPageQuery(multilistParam, UserTradeMonitorVo.class);

		List<Object> list = pageInfo.getPageResults();
		List<Object> tempTradeVos = Lists.newArrayList();

		if (CollectionUtils.isEmpty(list)) {
			return pageInfo;
		}
		
		// 获取组合资产信息
		List<StockCurrent> stockCurrents = getAllStockCurrents();
		if (CollectionUtils.isEmpty(stockCurrents)) {
			return pageInfo;
		}

		for (StockCurrent stockCurrent : stockCurrents) {
			String scombineId = stockCurrent.getCombineId();
			String sfundAccount = stockCurrent.getFundAccount();
			for (Object objVo : list) {
				UserTradeMonitorVo tradeVo = (UserTradeMonitorVo) objVo;
				String combineId = tradeVo.getCombineId();
				String parentAccountNo = tradeVo.getParentAccountNo();
				if (StringUtil.isBlank(combineId)
						|| StringUtil.isBlank(parentAccountNo)) {
					continue;
				}

				if (StringUtil.equals(scombineId, combineId)
						&& StringUtil.equals(sfundAccount, parentAccountNo)) {
					double setStockAssets=stockCurrent.getMarketValue();
					double assetTotalValue = BigDecimalUtils.addRound(
							stockCurrent.getCurrentCash(),
							setStockAssets);
					double stockAssetsProportion=0;
					if(assetTotalValue!=0){
						stockAssetsProportion=BigDecimalUtils.mulRound(BigDecimalUtils.divRound(setStockAssets, assetTotalValue), 100.0);
					}
					tradeVo.setAssetTotalValue(assetTotalValue);
					tradeVo.setStockAssets(setStockAssets);
					tradeVo.setStockAssetsProportion(stockAssetsProportion);
					
//					String isBreakStock = connVo.getValueStr("isBreakStock");
					//未穿仓
					if(/*!StringUtil.isBlank(isBreakStock) && "0".equals(isBreakStock) && */
							BigDecimalUtils.compareTo(tradeVo.getAssetTotalValue(), tradeVo.getTotalLending()) >= 0) {
//						tempTradeVos.add(tradeVo);
						tradeVo.setIsBreakStore("否");
					//已穿仓
					} else if(/*!StringUtil.isBlank(isBreakStock) && "1".equals(isBreakStock) && */
							BigDecimalUtils.compareTo(tradeVo.getAssetTotalValue(), tradeVo.getTotalLending()) < 0) {
//						tempTradeVos.add(tradeVo);
						tradeVo.setIsBreakStore("是");
					//其他
					} else /*if(StringUtil.isBlank(isBreakStock)) */{
//						tempTradeVos.add(tradeVo);
					}
					tempTradeVos.add(tradeVo);
				}
			}
		}
		pageInfo.setPageResults(tempTradeVos);
		return pageInfo;
	}

	@Resource(name = "entityManagerFactory")
	private EntityManagerFactory entityManagerFactory;

	@SuppressWarnings("unchecked")
	@Override
	public PageInfo<EndProgramVo> queryEnd(PageInfo<EndProgramVo> page,
			EndProgramVo vo) {
		StringBuffer sqlBuffer = new StringBuffer(
				" SELECT t.account_id accountId,t.account,w.mobile,t.group_id groupId,sum(f.money) money"
						+ " ,t.buy_status buyStatus"
						+ " ,t.sell_status sellStatus "
						+ " ,(SELECT min(w.account_name) FROM w_account w WHERE w.id = t.account_id) accountName "
						+ " ,v.tname,t.program_no programNo,min(f.addtime) addtime"
						+ " ,w.avl_bal avlBal"
						+ " FROM w_user_trade t LEFT JOIN w_user w "
						+ " ON t.uid = w.id LEFT JOIN w_user_fund f "
						+ " ON f.lid = t.group_id AND f.rid=t.program_no  LEFT JOIN w_user_verified v "
						+ " ON v.uid = w.id LEFT JOIN w_hand_trade h ON h.trade_id = t.id AND IFNULL(h.deleted,0)=0"
						+ " WHERE f.pay_status = 0 AND f.money < 0 AND t.status = 1 ");
		List<Object> params = new ArrayList<Object>();
		if (vo != null) {
			if (vo.getAccount() != null && !"".equals(vo.getAccount())) {
				sqlBuffer.append(" AND t.account LIKE ?");
				params.add("%" + vo.getAccount() + "%");
			}
			if (vo.getMobile() != null && !"".equals(vo.getMobile())) {
				sqlBuffer.append(" AND w.mobile LIKE ?");
				params.add("%" + vo.getMobile() + "%");
			}
			if(vo.getBuyStatus() !=null){
				sqlBuffer.append(" AND t.buy_status = ?");
				if(vo.getBuyStatus()){
					params.add(1);
				}else{
					params.add(0);
				}
			}
			// 判断是钱江版1 | 涌金版2
			Integer type = vo.getType();
			if (!ObjectUtil.equals(null, type)) {
				if (1 == type) {
					sqlBuffer.append(" AND t.fee_type IN (0, 1)");
				} else {
					// 涌金版两次审核 一次审核提交确认
					// 提交审核确认后 一次审核列表不显示 提交后的
					// 二次审核未通过2 则重新回到一次审核列表中
					sqlBuffer
							.append(" AND t.fee_type=2 AND h.audit_end_status IN (-1, 2)");
				}
			}
		}

		sqlBuffer.append(" GROUP BY t.group_id");
		String code = authService.getCurrentUser().getOrganization().getCode();
		params.add(code + "%");
		String sql = getPermissionSql(sqlBuffer.toString(), code, params,
				authService.getCurrentUser().getId());
		return this.getEntityDao().queryPageBySql(page, sql, EndProgramVo.class, null, params.toArray());
	}

@Override
	public PageInfo<EndProgramVo> queryEnd(PageInfo<EndProgramVo> page,
			ConnditionVo conn) {
		// TODO Auto-generated method stub
		StringBuffer sqlBuffer = new StringBuffer(
				" SELECT t.activity_type activityType, t.account_id accountId,t.account,w.mobile,t.group_id groupId,sum(f.money) money"
						+ " ,t.buy_status buyStatus"
						+ " ,t.sell_status sellStatus "
						+ " ,(SELECT min(w.account_name) FROM w_account w WHERE w.id = t.account_id) accountName "
						+ " ,v.tname,t.program_no programNo,min(f.addtime) addtime"
						+ " ,w.avl_bal avlBal"
						+ " FROM w_user_trade t LEFT JOIN w_user w "
						+ " ON t.uid = w.id LEFT JOIN w_user_fund f "
						+ " ON f.lid = t.group_id AND f.rid=t.program_no  LEFT JOIN w_user_verified v "
						+ " ON v.uid = w.id LEFT JOIN w_hand_trade h ON h.trade_id = t.id AND IFNULL(h.deleted,0)=0"
						+ " WHERE f.pay_status = 0 AND f.money < 0 AND t.status = 1 ");
		List<Object> params = new ArrayList<Object>();
		
		if (conn != null) {
			if(conn.getValueStr("mobile") !=null){				
				sqlBuffer.append(" AND w.mobile LIKE ?");
				params.add("%" + conn.getValueStr("mobile") + "%");
			}
			if (conn.getValueStr("account") !=null){
				sqlBuffer.append(" AND t.account LIKE ?");
				params.add("%" + conn.getValueStr("account") + "%");
			}
			if(conn.getValueStr("buyStatus") !=null){
				boolean buyStatus=Boolean.valueOf(conn.getValueStr("buyStatus"));
				sqlBuffer.append(" AND t.buy_status = ?");
				if(buyStatus){
					params.add(1);
				}else{
					params.add(0);
				}
			}
			// 判断方案类型
			if (null != conn.getValueStr("search_EQ_activityType")){
				sqlBuffer.append(" AND t.activity_type = ?");
				params.add(conn.getValueStr("search_EQ_activityType"));
			}
			// 判断是钱江版1 | 涌金版2| 同花顺手动版3
			Integer type = Integer.valueOf(conn.getValueStr("type"));
			if (!ObjectUtil.equals(null, type)) {
				if (1 == type) {
					sqlBuffer.append(" AND t.fee_type IN (0, 1)");
				} else if(2 == type) {
					// 涌金版两次审核 一次审核提交确认
					// 提交审核确认后 一次审核列表不显示 提交后的
					// 二次审核未通过2 则重新回到一次审核列表中
					sqlBuffer
							.append(" AND t.fee_type=2 AND h.audit_end_status IN (-1, 2)");
				}
				else
				{
					// 涌金版两次审核 一次审核提交确认
					// 提交审核确认后 一次审核列表不显示 提交后的
					// 二次审核未通过2 则重新回到一次审核列表中
					sqlBuffer
							.append(" AND t.fee_type=3 AND h.audit_end_status IN (-1, 2)");
				}
			}
		}
		sqlBuffer.append(" GROUP BY t.group_id");
		String code = authService.getCurrentUser().getOrganization().getCode();
		params.add(code + "%");
		String sql = getPermissionSql(sqlBuffer.toString(), code, params,
				authService.getCurrentUser().getId());
		return this.getEntityDao().queryPageBySql(page, sql, EndProgramVo.class, conn, params.toArray());
	}
	
	@Override
	public UserTradeCmsVo publicFindByGroupId(String groupId) {
		// String sql =
		// " select uid,activity_type as activityType,lever,max(natural_days) as naturalDays,parent_account_no as parentAccountNo,startdays,sum(finished_money) as finishedMoney,sum(apr) as apr,MAX(estimate_endtime) as estimateEndtime,sum(fee_day) as feeDay,sum(fee_month) as feeMonth,sum(accrual) as totalAccrual,asset_id as assetId,combine_id as combineId,group_id as groupId,min(starttime) as starttime,MAX(endtime) as endtime,SUM(total_lever_money) as totalOperateMoney,SUM(lever_money) as totalLeverMoney ,SUM(money) as totalLending,SUM(append_lever_money) as totalAppendLeverMoney,STATUS as status,hs_belong_broker as hsBelongBroker,SUM(warning) as warning,SUM(`open`) as `open`,account,password from w_user_trade w where uid=? and w.group_id=? GROUP BY w.group_id  ORDER BY starttime DESC";

		StringBuffer sql = new StringBuffer(
				"select uid,activity_type as activityType,lever,max(natural_days) as naturalDays, ");
		sql.append(" parent_account_no as parentAccountNo,startdays,sum(w.finished_money) as finishedMoney, ");
		sql.append(" sum(apr) as apr,MAX(estimate_endtime) as estimateEndtime,sum(fee_day) as feeDay, ");
		sql.append(" sum(fee_month) as feeMonth,sum(accrual) as totalAccrual,w.asset_id as assetId, ");
		sql.append(" w.combine_id as combineId,group_id as groupId,min(starttime) as starttime,min(addtime) as addtime, ");
		sql.append(" MAX(w.endtime) as endtime,SUM(total_lever_money) as totalOperateMoney, ");
		sql.append(" SUM(lever_money) as totalLeverMoney ,SUM(money) as totalLending, ");
		sql.append(" SUM(append_lever_money) as totalAppendLeverMoney,w.status, ");
		sql.append(" hs_belong_broker as hsBelongBroker,SUM(warning) as warning,SUM(`open`) as `open`, ");
		sql.append(" w.account,w.password,w.fee_type AS feeType,h.audit_status AS auditStatus,h.audit_end_status AS auditEndStatus, ac.insurance_no insuranceNo ,h.update_time AS auditPassTime");
		sql.append(" from w_user_trade w LEFT JOIN w_hand_trade h ON w.id=h.trade_id ");
		sql.append(" LEFT JOIN w_account ac ON ac.id = w.account_id ");
		sql.append(" where w.group_id=? GROUP BY w.group_id  ORDER BY w.addtime asc ");

		List<Object> params = Lists.newArrayList();
		params.add(groupId);
		List<UserTradeCmsVo> list = nativeQuery(sql.toString(), params,
				UserTradeCmsVo.class);
		if (!CollectionUtils.isEmpty(list)) {
			UserTradeCmsVo userTradeCmsVo = list.get(0);
			try {
				if (userTradeCmsVo.getStatus() == 1) {
					// 涌金版
					if (userTradeCmsVo.getFeeType() == Constant.FeeType.HAND_OPEN_TIERCE 
							|| userTradeCmsVo.getFeeType() == Constant.FeeType.HAND_OPEN_WELL_GOLD ) {
						userTradeCmsVo.setAssetTotalValue(userTradeCmsVo
								.getTotalOperateMoney());
					} else { // feetype = 0 ,feetype=1 表示钱江版 可调用恒生接口
						HundsunJres hundsunJres = HundsunJres.getInstance();
						String userToken = hundsunJres.Login();

						String combineId = userTradeCmsVo.getCombineId();
						if (StringUtil.isBlank(combineId)) {
							return userTradeCmsVo;
						}
						List<StockCurrent> stockCurrentList = hundsunJres
								.funcAmStockCurrentQry(userToken,
										userTradeCmsVo.getParentAccountNo(),
										combineId);
						if (CollectionUtils.isEmpty(stockCurrentList)) {
							return userTradeCmsVo;
						}

						StockCurrent stockCurrent = stockCurrentList.get(0);
						double assetTotalValue = BigDecimalUtils.addRound(
								stockCurrent.getCurrentCash(),
								stockCurrent.getMarketValue());
						userTradeCmsVo.setAssetTotalValue(assetTotalValue);
						double total = BigDecimalUtils.addRound(
								userTradeCmsVo.getTotalLending(),
								userTradeCmsVo.getTotalLeverMoney());
						userTradeCmsVo.setTotalAccrual(BigDecimalUtils
								.subRound(assetTotalValue,
										userTradeCmsVo.getTotalOperateMoney()));
						userTradeCmsVo.setExtractableProfit(BigDecimalUtils
								.subRound(assetTotalValue, total*1.10));
						userTradeCmsVo.setStockAssets(stockCurrent
								.getMarketValue());
						userTradeCmsVo.setCashBalance(stockCurrent
								.getCurrentCash());
						if (userTradeCmsVo.getExtractableProfit() <= 0) {
							userTradeCmsVo.setExtractableProfit(0);
						} else {
							if (userTradeCmsVo.getExtractableProfit() > userTradeCmsVo
									.getCashBalance()) {
								userTradeCmsVo
										.setExtractableProfit(userTradeCmsVo
												.getCashBalance());
							}
						}
						hundsunJres.Logout(userToken);
					}

					userTradeCmsVo.setStarttimeString(Dates
							.parseBigInteger2Date(
									userTradeCmsVo.getStarttime(),
									Dates.CHINESE_DATE_FORMAT_LINE));
					userTradeCmsVo.setEstimateEndtimeString(Dates
							.parseBigInteger2Date(
									userTradeCmsVo.getEstimateEndtime(),
									Dates.CHINESE_DATE_FORMAT_LINE));
					String today = Dates.format(Dates.CHINESE_DATE_FORMAT_LONG);
					String startDay = Dates.parseBigInteger2Date(
							userTradeCmsVo.getStarttime(),
							Dates.CHINESE_DATE_FORMAT_LONG);
					long tradingDays = tradeDayService.getTradeDays(startDay,
							today);
					userTradeCmsVo.setTradingDays(tradingDays);
				} else {

					userTradeCmsVo.setStarttimeString(Dates
							.parseBigInteger2Date(
									userTradeCmsVo.getStarttime(),
									Dates.CHINESE_DATE_FORMAT_LINE));
					String endTime = Dates.parseBigInteger2Date(
							userTradeCmsVo.getEndtime(),
							Dates.CHINESE_DATE_FORMAT_LINE);
					if (userTradeCmsVo.getStatus() == 0) {
						endTime = Dates.parseBigInteger2Date(
								userTradeCmsVo.getEstimateEndtime(),
								Dates.CHINESE_DATE_FORMAT_LINE);
					}
					userTradeCmsVo.setEstimateEndtimeString(endTime);

					String startDay = Dates.parseBigInteger2Date(
							userTradeCmsVo.getStarttime(),
							Dates.CHINESE_DATE_FORMAT_LONG);
					String endDay = Dates.parseBigInteger2Date(
							userTradeCmsVo.getEndtime(),
							Dates.CHINESE_DATE_FORMAT_LONG);
					long tradingDays = tradeDayService.getTradeDays(startDay,
							endDay);
					userTradeCmsVo.setTradingDays(tradingDays);
				}
			} catch (T2SDKException e) {
				log.error(this.getClass().getName() + e.getMessage());
				throw new UserTradeException(
						"user.trade.query.margin.remind.data.fail", null);
			}

			return userTradeCmsVo;
		}
		return null;
	}
	
	@Override
	public UserTradeCmsVo findByGroupIdAndUid(String groupId, String uid) {
		// String sql =
		// " select uid,activity_type as activityType,lever,max(natural_days) as naturalDays,parent_account_no as parentAccountNo,startdays,sum(finished_money) as finishedMoney,sum(apr) as apr,MAX(estimate_endtime) as estimateEndtime,sum(fee_day) as feeDay,sum(fee_month) as feeMonth,sum(accrual) as totalAccrual,asset_id as assetId,combine_id as combineId,group_id as groupId,min(starttime) as starttime,MAX(endtime) as endtime,SUM(total_lever_money) as totalOperateMoney,SUM(lever_money) as totalLeverMoney ,SUM(money) as totalLending,SUM(append_lever_money) as totalAppendLeverMoney,STATUS as status,hs_belong_broker as hsBelongBroker,SUM(warning) as warning,SUM(`open`) as `open`,account,password from w_user_trade w where uid=? and w.group_id=? GROUP BY w.group_id  ORDER BY starttime DESC";

		StringBuffer sql = new StringBuffer(
				"select uid,activity_type as activityType,lever,max(natural_days) as naturalDays, ");
		sql.append(" parent_account_no as parentAccountNo,startdays,sum(w.finished_money) as finishedMoney, ");
		sql.append(" sum(apr) as apr,MAX(estimate_endtime) as estimateEndtime,sum(fee_day) as feeDay, ");
		sql.append(" sum(fee_month) as feeMonth,sum(accrual) as totalAccrual,w.asset_id as assetId, ");
		sql.append(" w.combine_id as combineId,group_id as groupId,min(starttime) as starttime, ");
		sql.append(" MAX(w.endtime) as endtime,SUM(total_lever_money) as totalOperateMoney, ");
		sql.append(" SUM(lever_money) as totalLeverMoney ,SUM(money) as totalLending, ");
		sql.append(" SUM(append_lever_money) as totalAppendLeverMoney,w.status, ");
		sql.append(" hs_belong_broker as hsBelongBroker,SUM(warning) as warning,SUM(`open`) as `open`, ");
		sql.append(" w.account,w.password,w.fee_type AS feeType,h.audit_status AS auditStatus,h.audit_end_status AS auditEndStatus, ac.insurance_no insuranceNo ");
		sql.append(" from w_user_trade w LEFT JOIN w_hand_trade h ON w.id=h.trade_id ");
		sql.append(" LEFT JOIN w_account ac ON ac.id = w.account_id ");
		sql.append(" where uid=? and w.group_id=? GROUP BY w.group_id  ORDER BY w.addtime asc ");

		List<Object> params = Lists.newArrayList();
		params.add(uid);
		params.add(groupId);
		List<UserTradeCmsVo> list = nativeQuery(sql.toString(), params,
				UserTradeCmsVo.class);
		if (!CollectionUtils.isEmpty(list)) {
			UserTradeCmsVo userTradeCmsVo = list.get(0);
			try {
				if (userTradeCmsVo.getStatus() == 1) {
					// 涌金版
					if (userTradeCmsVo.getFeeType() == Constant.FeeType.HAND_OPEN_TIERCE 
							|| userTradeCmsVo.getFeeType() == Constant.FeeType.HAND_OPEN_WELL_GOLD ) {
						userTradeCmsVo.setAssetTotalValue(userTradeCmsVo
								.getTotalOperateMoney());
					} else { // feetype = 0 ,feetype=1 表示钱江版 可调用恒生接口
						HundsunJres hundsunJres = HundsunJres.getInstance();
						String userToken = hundsunJres.Login();

						String combineId = userTradeCmsVo.getCombineId();
						if (StringUtil.isBlank(combineId)) {
							return userTradeCmsVo;
						}
						List<StockCurrent> stockCurrentList = hundsunJres
								.funcAmStockCurrentQry(userToken,
										userTradeCmsVo.getParentAccountNo(),
										combineId);
						if (CollectionUtils.isEmpty(stockCurrentList)) {
							return userTradeCmsVo;
						}

						StockCurrent stockCurrent = stockCurrentList.get(0);
						double assetTotalValue = BigDecimalUtils.addRound(
								stockCurrent.getCurrentCash(),
								stockCurrent.getMarketValue());
						userTradeCmsVo.setAssetTotalValue(assetTotalValue);
						double total = BigDecimalUtils.addRound(
								userTradeCmsVo.getTotalLending(),
								userTradeCmsVo.getTotalLeverMoney());
						userTradeCmsVo.setTotalAccrual(BigDecimalUtils
								.subRound(assetTotalValue,
										userTradeCmsVo.getTotalOperateMoney()));
						userTradeCmsVo.setExtractableProfit(BigDecimalUtils
								.subRound(assetTotalValue, total*1.10));
						userTradeCmsVo.setStockAssets(stockCurrent
								.getMarketValue());
						userTradeCmsVo.setCashBalance(stockCurrent
								.getCurrentCash());
						if (userTradeCmsVo.getExtractableProfit() <= 0) {
							userTradeCmsVo.setExtractableProfit(0);
						} else {
							if (userTradeCmsVo.getExtractableProfit() > userTradeCmsVo
									.getCashBalance()) {
								userTradeCmsVo
										.setExtractableProfit(userTradeCmsVo
												.getCashBalance());
							}
						}
						hundsunJres.Logout(userToken);
					}

					userTradeCmsVo.setStarttimeString(Dates
							.parseBigInteger2Date(
									userTradeCmsVo.getStarttime(),
									Dates.CHINESE_DATE_FORMAT_LINE));
					userTradeCmsVo.setEstimateEndtimeString(Dates
							.parseBigInteger2Date(
									userTradeCmsVo.getEstimateEndtime(),
									Dates.CHINESE_DATE_FORMAT_LINE));
					String today = Dates.format(Dates.CHINESE_DATE_FORMAT_LONG);
					String startDay = Dates.parseBigInteger2Date(
							userTradeCmsVo.getStarttime(),
							Dates.CHINESE_DATE_FORMAT_LONG);
					long tradingDays = tradeDayService.getTradeDays(startDay,
							today);
					userTradeCmsVo.setTradingDays(tradingDays);
				} else {

					userTradeCmsVo.setStarttimeString(Dates
							.parseBigInteger2Date(
									userTradeCmsVo.getStarttime(),
									Dates.CHINESE_DATE_FORMAT_LINE));
					String endTime = Dates.parseBigInteger2Date(
							userTradeCmsVo.getEndtime(),
							Dates.CHINESE_DATE_FORMAT_LINE);
					if (userTradeCmsVo.getStatus() == 0) {
						endTime = Dates.parseBigInteger2Date(
								userTradeCmsVo.getEstimateEndtime(),
								Dates.CHINESE_DATE_FORMAT_LINE);
					}
					userTradeCmsVo.setEstimateEndtimeString(endTime);

					String startDay = Dates.parseBigInteger2Date(
							userTradeCmsVo.getStarttime(),
							Dates.CHINESE_DATE_FORMAT_LONG);
					String endDay = Dates.parseBigInteger2Date(
							userTradeCmsVo.getEndtime(),
							Dates.CHINESE_DATE_FORMAT_LONG);
					long tradingDays = tradeDayService.getTradeDays(startDay,
							endDay);
					userTradeCmsVo.setTradingDays(tradingDays);
				}
			} catch (T2SDKException e) {
				log.error(this.getClass().getName() + e.getMessage());
				throw new UserTradeException(
						"user.trade.query.margin.remind.data.fail", null);
			}

			return userTradeCmsVo;
		}
		return null;
	}
	
	
	@Override
	public UserTradeCmsVo findByGroupIdAndUidLazy(String groupId, String uid) {
		// String sql =
		// " select uid,activity_type as activityType,lever,max(natural_days) as naturalDays,parent_account_no as parentAccountNo,startdays,sum(finished_money) as finishedMoney,sum(apr) as apr,MAX(estimate_endtime) as estimateEndtime,sum(fee_day) as feeDay,sum(fee_month) as feeMonth,sum(accrual) as totalAccrual,asset_id as assetId,combine_id as combineId,group_id as groupId,min(starttime) as starttime,MAX(endtime) as endtime,SUM(total_lever_money) as totalOperateMoney,SUM(lever_money) as totalLeverMoney ,SUM(money) as totalLending,SUM(append_lever_money) as totalAppendLeverMoney,STATUS as status,hs_belong_broker as hsBelongBroker,SUM(warning) as warning,SUM(`open`) as `open`,account,password from w_user_trade w where uid=? and w.group_id=? GROUP BY w.group_id  ORDER BY starttime DESC";

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
		List<UserTradeCmsVo> list = nativeQuery(sql.toString(), params,
				UserTradeCmsVo.class);
		if (!CollectionUtils.isEmpty(list)) {
			UserTradeCmsVo userTradeCmsVo = list.get(0);
			try {
				if (userTradeCmsVo.getStatus() == 1) {
					// 涌金版
					if (userTradeCmsVo.getFeeType() == Constant.FeeType.HAND_OPEN_TIERCE 
							|| userTradeCmsVo.getFeeType() == Constant.FeeType.HAND_OPEN_WELL_GOLD ) {
						userTradeCmsVo.setAssetTotalValue(userTradeCmsVo
								.getTotalOperateMoney());
					} else { // feetype = 0 ,feetype=1 表示钱江版 可调用恒生接口
//						HundsunJres hundsunJres = HundsunJres.getInstance();
//						String userToken = hundsunJres.Login();
//
//						String combineId = userTradeCmsVo.getCombineId();
//						if (StringUtil.isBlank(combineId)) {
//							return userTradeCmsVo;
//						}
//						List<StockCurrent> stockCurrentList = hundsunJres
//								.funcAmStockCurrentQry(userToken,
//										userTradeCmsVo.getParentAccountNo(),
//										combineId);
//						if (CollectionUtils.isEmpty(stockCurrentList)) {
//							return userTradeCmsVo;
//						}
//
//						StockCurrent stockCurrent = stockCurrentList.get(0);
//						double assetTotalValue = BigDecimalUtils.addRound(
//								stockCurrent.getCurrentCash(),
//								stockCurrent.getMarketValue());
//						userTradeCmsVo.setAssetTotalValue(assetTotalValue);
//						double total = BigDecimalUtils.addRound(
//								userTradeCmsVo.getTotalLending(),
//								userTradeCmsVo.getTotalLeverMoney());
//						userTradeCmsVo.setTotalAccrual(BigDecimalUtils
//								.subRound(assetTotalValue,
//										userTradeCmsVo.getTotalOperateMoney()));
//						userTradeCmsVo.setExtractableProfit(BigDecimalUtils
//								.subRound(assetTotalValue, total));
//						userTradeCmsVo.setStockAssets(stockCurrent
//								.getMarketValue());
//						userTradeCmsVo.setCashBalance(stockCurrent
//								.getCurrentCash());
//						if (userTradeCmsVo.getExtractableProfit() <= 0) {
//							userTradeCmsVo.setExtractableProfit(0);
//						} else {
//							if (userTradeCmsVo.getExtractableProfit() > userTradeCmsVo
//									.getCashBalance()) {
//								userTradeCmsVo
//										.setExtractableProfit(userTradeCmsVo
//												.getCashBalance());
//							}
//						}
//						hundsunJres.Logout(userToken);
					}

					userTradeCmsVo.setStarttimeString(Dates
							.parseBigInteger2Date(
									userTradeCmsVo.getStarttime(),
									Dates.CHINESE_DATE_FORMAT_LINE));
					userTradeCmsVo.setEstimateEndtimeString(Dates
							.parseBigInteger2Date(
									userTradeCmsVo.getEstimateEndtime(),
									Dates.CHINESE_DATE_FORMAT_LINE));
					String today = Dates.format(Dates.CHINESE_DATE_FORMAT_LONG);
					String startDay = Dates.parseBigInteger2Date(
							userTradeCmsVo.getStarttime(),
							Dates.CHINESE_DATE_FORMAT_LONG);
					long tradingDays = tradeDayService.getTradeDays(startDay,
							today);
					userTradeCmsVo.setTradingDays(tradingDays);
				} else {

					userTradeCmsVo.setStarttimeString(Dates
							.parseBigInteger2Date(
									userTradeCmsVo.getStarttime(),
									Dates.CHINESE_DATE_FORMAT_LINE));
					String endTime = Dates.parseBigInteger2Date(
							userTradeCmsVo.getEndtime(),
							Dates.CHINESE_DATE_FORMAT_LINE);
					if (userTradeCmsVo.getStatus() == 0) {
						endTime = Dates.parseBigInteger2Date(
								userTradeCmsVo.getEstimateEndtime(),
								Dates.CHINESE_DATE_FORMAT_LINE);
					}
					userTradeCmsVo.setEstimateEndtimeString(endTime);

					String startDay = Dates.parseBigInteger2Date(
							userTradeCmsVo.getStarttime(),
							Dates.CHINESE_DATE_FORMAT_LONG);
					String endDay = Dates.parseBigInteger2Date(
							userTradeCmsVo.getEndtime(),
							Dates.CHINESE_DATE_FORMAT_LONG);
					long tradingDays = tradeDayService.getTradeDays(startDay,
							endDay);
					userTradeCmsVo.setTradingDays(tradingDays);
				}
			} catch (Exception e) {
				log.error(this.getClass().getName() + e.getMessage());
				throw new UserTradeException(
						"user.trade.query.margin.remind.data.fail", null);
			}

			return userTradeCmsVo;
		}
		return null;
	}
	
	/**
	 * 查询操盘中用户 信息
	 * @MethodName findTradingUserInfo
	 * @author L.Y
	 * @date 2015年6月30日
	 * @param groupId
	 * @param uid
	 * @return
	 */
	public UserTradeCmsVo findTradingUserInfo(String groupId, String uid) {
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
		sql.append(" where uid=? and w.group_id=? GROUP BY w.group_id  ORDER BY starttime DESC ");

		List<Object> params = Lists.newArrayList();
		params.add(uid);
		params.add(groupId);
		List<UserTradeCmsVo> list = nativeQuery(sql.toString(), params,
				UserTradeCmsVo.class);
		if (!CollectionUtils.isEmpty(list)) {
			UserTradeCmsVo userTradeCmsVo = list.get(0);
			try {
				if (userTradeCmsVo.getStatus() == 1) {
					// 钱江版
					if (userTradeCmsVo.getFeeType() == Constant.FeeType.AUTO_OPEN_CASH_RIVER 
							|| userTradeCmsVo.getFeeType() == Constant.FeeType.HAND_OPEN_CASH_RIVER ) {
						userTradeCmsVo.setAssetTotalValue(userTradeCmsVo
								.getTotalOperateMoney());
						
						HundsunJres hundsunJres = HundsunJres.getInstance();
						String userToken = hundsunJres.Login();
						if(StringUtil.isBlank(userToken)) {
							throw new UserTradeException(
									"user.trade.query.margin.hundsunJres.token.isnull", null);
						}

						String combineId = userTradeCmsVo.getCombineId();
						if (StringUtil.isBlank(combineId)) {
							return userTradeCmsVo;
						}
						List<StockCurrent> stockCurrentList = hundsunJres
								.funcAmStockCurrentQry(userToken,
										userTradeCmsVo.getParentAccountNo(),
										combineId);
						if (CollectionUtils.isEmpty(stockCurrentList)) {
							return userTradeCmsVo;
						}

						StockCurrent stockCurrent = stockCurrentList.get(0);
						double assetTotalValue = BigDecimalUtils.addRound(
								stockCurrent.getCurrentCash(),
								stockCurrent.getMarketValue());
						userTradeCmsVo.setAssetTotalValue(assetTotalValue);
						double total = BigDecimalUtils.addRound(
								userTradeCmsVo.getTotalLending(),
								userTradeCmsVo.getTotalLeverMoney());
						userTradeCmsVo.setTotalAccrual(BigDecimalUtils
								.subRound(assetTotalValue,
										userTradeCmsVo.getTotalOperateMoney()));
						userTradeCmsVo.setExtractableProfit(BigDecimalUtils
								.subRound(assetTotalValue, total*1.10));
						userTradeCmsVo.setStockAssets(stockCurrent
								.getMarketValue());
						userTradeCmsVo.setCashBalance(stockCurrent
								.getCurrentCash());
						if (userTradeCmsVo.getExtractableProfit() <= 0) {
							userTradeCmsVo.setExtractableProfit(0);
						} else {
							if (userTradeCmsVo.getExtractableProfit() > userTradeCmsVo
									.getCashBalance()) {
								userTradeCmsVo
										.setExtractableProfit(userTradeCmsVo
												.getCashBalance());
							}
						}
						hundsunJres.Logout(userToken);
						
						return userTradeCmsVo;
					}
				}
			} catch (T2SDKException e) {
				log.error(this.getClass().getName() + e.getMessage());
				throw new UserTradeException(
						"user.trade.query.margin.remind.data.fail", null);
			}
		}
		return null;
	}

	@Override
	public List<UserTrade> findByGroupIdAndWuser(String groupId, WUser wuser) {
		return getEntityDao().findByWuserAndGroupIdOrderByAddtimeAsc(wuser,groupId);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void endOfProgram(String groupId) {
		// 子账户状态需要修改
		HundsunJres hundsunJres = null;
		try {
			hundsunJres = HundsunJres.getInstance();
		} catch (T2SDKException e) {
			log.error(e.getMessage());
			throw new UserTradeConcurrentException(
					"com.tzdr.business.server.connection.timeout", null);
		}
		String userToken = hundsunJres.Login();
		// 1)结算金额
		double assetTotalValue = 0D;
		List<UserTrade> userTrades = this
				.findByGroupIdOrderByAddtimeAsc(groupId);
		if (userTrades == null || userTrades.size() <= 0) {
			throw new UserTradeConcurrentException(
					"com.tzdr.business.server.connection.timeout", null);
		}
		UserTrade userTrade = userTrades.get(0);
		if (userTrade.getStatus() == 2 || userTrade.getStatus() == 3) { // 操盘中、终结中
			throw new UserTradeConcurrentException(
					"com.tzdr.business.not.end.repeat", null);
		}

		// 获取母账户编号和组合编号
		String parentAccount = userTrade.getParentAccountNo();
		String combineId = userTrade.getCombineId();

		// 1.1 【记入为终结中状态】 将记录记入首条数据
		// userTrade.setStatus((short)3);
		/*
		 * for (UserTrade trade:userTrades) { trade.setStatus((short)3); }
		 */

		// 1.2 获取总资产(组合资产查询)
		List<StockCurrent> stockCurrentList = hundsunJres
				.funcAmStockCurrentQry(userToken, parentAccount, combineId);

		if (!CollectionUtils.isEmpty(stockCurrentList)) {
			StockCurrent stockCurrent = stockCurrentList.get(0);
			// 持仓查询
			// 如果有股票资产者为持仓
			if (stockCurrent == null
					|| stockCurrent.getMarketValue() > 0
					|| stockCurrent.getCurrentCash() <= 0
					|| TypeConvert.cleanSpaceCharacter(stockCurrent
							.getFundAccount()) == null) {
				// 持有股票不能终结方案
				throw new UserTradeConcurrentException(
						"com.tzdr.business.end.holding", null);
			}else{
				//是否有交易委托
				List<Entrust>  entrustlist=hundsunJres.funcAmEntrustQry(userToken, parentAccount, combineId, null, null, null, 0, 0, 0, 0);
				//从数据字典取出交易委托数据
				List<DataMap> datamaps=dataMapService.findByTypeKey(DataDicKeyConstants.AMENTSTATUS);
				DataMap data=datamaps.get(0);
				String key=data.getValueKey();
				if(entrustlist!=null && entrustlist.size()>0){
					for(Entrust entrust:entrustlist){
							String status=entrust.getAmentrustStatus();
							log.info("userToken--"+userToken+",parentAccount----"+parentAccount+"combineId----"+combineId+"委托存在状态-----"+status);
							if(key.contains(status)){
								// 持有股票有交易委托
								throw new UserTradeConcurrentException(
										"com.tzdr.business.end.holding", null);
							}
							//							if("1".equals(status)||"4".equals(status)||"C".equals(status)||
//								"B".equals(status)||"a".equals(status)||"A".equals(status)){
//								// 持有股票有交易委托
//								throw new UserTradeConcurrentException(
//										"com.tzdr.business.end.holding", null);
//						}
					}
				}
			}
			// 结算总资产
			assetTotalValue = BigDecimalUtils.addRound(
					stockCurrent.getCurrentCash(),
					stockCurrent.getMarketValue());

		} else {
			throw new UserTradeConcurrentException(
					"com.tzdr.business.server.connection.timeout", null);
		}
		userTrade.setFinishedMoney(assetTotalValue);

		// 获取groupId所属配资金额，保证金合计
		EndMoneyVo endMoneyVo = this.endOfProgrameSum(groupId);

		// 2)盈亏金额计算｛结算金额 - （配资金额+保证金+追加保证金）｝ = 盈亏

		double withCapitalLevel = BigDecimalUtils.add2(endMoneyVo.getMoney(),
				endMoneyVo.getLeverMoney());
		Double capitalAppend = BigDecimalUtils.add2(withCapitalLevel,
				endMoneyVo.getAppendLeverMoney());
		Double accrualValue = BigDecimalUtils.sub(assetTotalValue,
				capitalAppend);
		userTrade.setAccrual(accrualValue);

		// 当：【 结算金额-配资金额 < 0】算式结果小于0 时为"爆仓"的情况
		Double arrearsMoney = BigDecimalUtils.sub(assetTotalValue,
				endMoneyVo.getMoney());
		BigDecimal arrearsMoneyBig = new BigDecimal(arrearsMoney.toString());

		// 8800活动【 结算金额-配资金额-保证金 < 0】算式结果小于0 时为"爆仓"的情况
		if (userTrade.getActivityType() == UserTrade.ActivityType.ACTIVITY_6600) {
			arrearsMoney = BigDecimalUtils.sub(arrearsMoney,
					endMoneyVo.getLeverMoney());
			arrearsMoneyBig = new BigDecimal(arrearsMoney.toString());
		}

		// 3)撤回配置资金，并记入撤回流水()[结算金额 - （配资金额 ） = 撤回配置资金]
		Double revocationMoney = BigDecimalUtils.sub(assetTotalValue,
				endMoneyVo.getMoney());

		WUser argWuser = userTrade.getWuser();
		WUser wuser = this.wUserService.get(argWuser.getId());

		// 8800活动[结算金额 - （配资金额 ）8000-（保证金）800 = 撤回配置资金]
		if (userTrade.getActivityType() == UserTrade.ActivityType.ACTIVITY_6600) {
			revocationMoney = BigDecimalUtils.sub(revocationMoney,
					endMoneyVo.getLeverMoney());
		}

		// 用户可用余额
		// 未爆仓
		// arrearsMoneyBig
		if (arrearsMoneyBig.compareTo(new BigDecimal("0")) >= 0) {
			// arrearsMoney >= 0
			UserFund revocationRecord = new UserFund();
			revocationRecord.setMoney(revocationMoney);
			revocationRecord.setType(TypeConvert.USER_FUND_C_TYPE_REVOCATION);
			revocationRecord.setPayStatus(TypeConvert.PAID);
			revocationRecord.setRemark(TypeConvert.payRemark("配资撤回",
					revocationRecord.getMoney()));
			// 3.2记入撤回流水
			revocationRecord.setUid(wuser.getId());
			revocationRecord.setLid(groupId);
			this.userFundService.arrearsProcess(revocationRecord);
			// this.userFundService.rechargeOperation(revocationRecord,
			// TypeConvert.TAKE_DEPOSIT_TYPE_INSTORE);
		} else {

			// 6600活动亏损
			if (userTrade.getActivityType() == UserTrade.ActivityType.ACTIVITY_6600) {

				UserFund arrearsRecord = new UserFund();
				arrearsRecord.setMoney(arrearsMoney);
				arrearsRecord.setType(TypeConvert.USER_FUND_C_TYPE_ARREARS);
				arrearsRecord.setPayStatus(TypeConvert.PAID);
				arrearsRecord.setLid(groupId);
				arrearsRecord.setAmount(BigDecimalUtils.addRound(
						wuser.getAvlBal(), arrearsMoney));
				arrearsRecord.setAddtime(Dates.getCurrentLongDate());
				arrearsRecord.setUptime(Dates.getCurrentLongDate());
				arrearsRecord.setRemark(TypeConvert.payRemark("6600 活动 方案【" + userTrade.getProgramNo() 
						+ "】配资欠费",
						arrearsRecord.getMoney()));
				arrearsRecord.setUid(userTrade.getWuser().getId());
				userFundService.save(arrearsRecord);

				UserFund record = new UserFund();
				record.setMoney(-arrearsMoney);
				record.setType(21);
				record.setPayStatus(TypeConvert.PAID);
				record.setLid(groupId);
				record.setAmount(wuser.getAvlBal());
				record.setAddtime(Dates.getCurrentLongDate() + 1);
				record.setUptime(Dates.getCurrentLongDate() + 1);
				record.setRemark(TypeConvert.payRemark("6600 活动 方案【" + userTrade.getProgramNo() 
						+ "】补偿收入",
						record.getMoney()));
				record.setUid(userTrade.getWuser().getId());
				userFundService.save(record);
				wUserService.updateUser(wuser);
			} else {
				// 爆仓
				UserFund arrearsRecord = new UserFund();
				arrearsRecord.setMoney(arrearsMoney);
				arrearsRecord.setType(TypeConvert.USER_FUND_C_TYPE_ARREARS);
				arrearsRecord.setPayStatus(TypeConvert.UN_PAID);
				arrearsRecord.setLid(groupId);
				arrearsRecord.setAddtime(Dates.getCurrentLongDate());
				arrearsRecord.setRemark(TypeConvert.payRemark("配资欠费",
						arrearsRecord.getMoney()));
				arrearsRecord.setTypeStatus(0);
				this.wUserService.warehouseExplosion(wuser.getId(),
						arrearsRecord);
			}
		}

		// 4)方案终结合计，计费（管理费、利息费、累计配资、累计支出保证金、累计利息、累计盈亏）删除记录？？？
		this.userFundService.deleteArrearsByLid(groupId);
		List<BillingFeeVo> billFees = this.userFundService
				.totalMoneyByGroupId(groupId);
		// 1:充值,2:提现,3:人工充值,4:人工扣款,5:投标冻结,6:投标成功,10:配资支出,11:利息支出 ,12：扣取管理费（新增）

		// 配资金额合计
		Double capitalAmount = endMoneyVo.getMoney();
		capitalAmount = BigDecimalUtils.round2(capitalAmount, 2);
		wuser.setTotalLending(BigDecimalUtils.addRound(wuser.getTotalLending(),
				Math.abs(capitalAmount)));
		wuser.setTotalLending(Math.abs(wuser.getTotalLending()));

		// 利息支出合计
		BigDecimal lxAmount = getTotalFee(billFees, 11);
		wuser.setTotalInterestMo(BigDecimalUtils.addRound(
				wuser.getTotalInterestMo(), Math.abs(lxAmount.doubleValue())));
		wuser.setTotalInterestMo(Math.abs(wuser.getTotalInterestMo()));
		// 扣取管理费合计
		BigDecimal managerAmount = getTotalFee(billFees, 12);
		wuser.setTotalManagerMo(BigDecimalUtils.addRound(
				wuser.getTotalManagerMo(),
				Math.abs(managerAmount.doubleValue())));
		wuser.setTotalManagerMo(Math.abs(wuser.getTotalManagerMo()));

		// 累计保证金
		BigDecimal totalLevelMoney = totalLevelMoneyByGroupId(groupId);
		wuser.setTotalDeposit(BigDecimalUtils.addRound(wuser.getTotalDeposit(),
				totalLevelMoney.doubleValue()));
		wuser.setTotalDeposit(Math.abs(wuser.getTotalDeposit()));
		// 5)扣费计算(不结算)

		// 6)划账操作
		// 与恒生账户同步
		// 6.1本地账户与恒生母账户金额同步 [结算金额 = 母账户金额]
		/*
		 * ParentAccount parentAccountObj =
		 * parentAccountService.findByParentAccountId(parentAccount); if
		 * (parentAccountObj != null) { parentAccountObj.setFundsBalance(
		 * BigDecimalUtils
		 * .add2(parentAccountObj.getFundsBalance(),assetTotalValue));
		 * this.parentAccountService.updateParentAccount(parentAccountObj); }
		 */
		/*
		 * //6.2恒生账户资金同步 改为T+1 if (transferMoney(parentAccount, combineId,
		 * CacheManager.getHundSunCombineId(parentAccountObj.getUnitNumber()),
		 * assetTotalValue,"终结方案划转资金")) { for (UserTrade trade:userTrades) {
		 * trade.setStatus((short)2); } }
		 */
		for (UserTrade trade : userTrades) {
			trade.setStatus((short) 2);
			trade.setEndtime(TypeConvert.dbDefaultDate());
			// 账户冻结
			try {
				// 走线下[账号、异常类型、异常信息]
				Boolean flag = hundsunJres.funcAmChangeOperatorInfo(userToken,
						new Long(userTrade.getAccount()),
						TypeConvert.USER_FREEZE_STATE, null, 180L, null, null,
						null);
				if (!flag) {
					FreezeFailInfo freezeFailInfo = new FreezeFailInfo();
					freezeFailInfo.setAccount(userTrade.getAccount());
					freezeFailInfo.setTypeInfo("冻结失败");
					freezeFailInfo.setMessageText("调用恒生接口失败");
					freezeFailInfoService.save(freezeFailInfo);
				}
			} catch (Exception e) {
				e.printStackTrace();
				FreezeFailInfo freezeFailInfo = new FreezeFailInfo();
				freezeFailInfo.setAccount(userTrade.getAccount());
				freezeFailInfo.setTypeInfo("冻结失败");
				freezeFailInfo.setMessageText("调用恒生接口通讯异常");
				freezeFailInfoService.save(freezeFailInfo);
			}
			this.update(trade);

			// 生成 方案操作记录
			// schemeLifeCycleRecordService.save(new
			// SchemeLifeCycleRecord(userTrade,authService.getCurrentUser().getId(),5));
		}
		// 接口失败【正在终结】
		// 更新用户数据
		// funcAmChangeOperatorInfo;

		// hundsunJres.funcAmChangeOperatorInfo(String userToken, long
		// operatorNo,
		// String businOpflag, String operatorName, long expireDay,
		// String forceChgpassword, String stockOpright, String futureOpright)
		hundsunJres.Logout(userToken);
		// userTrade.setEndtime(TypeConvert.dbDefaultDate());

	}

	/**
	 * T+1 9:00 after 佣金差划款
	 */
	public void endOfCommission() {

		HundsunJres hundsunJres = null;
		try {
			hundsunJres = HundsunJres.getInstance();
		} catch (T2SDKException e) {
			e.printStackTrace();
			throw new UserTradeConcurrentException(
					"com.tzdr.business.server.connection.timeout", null);
		}
		String userToken = hundsunJres.Login();

		// 获取T+1的数据内容
		List<TransferVo> transferVoes = this.wUserService
				.queryUserTradeTodayAndOneBySuccessful();
		if (transferVoes != null) {
			for (TransferVo vo : transferVoes) {
				try {
					try {
						Thread.sleep(1000);
					} catch (Exception e) {
						e.printStackTrace();
					}
					String parentAccountNo = vo.getParentAccountNo();
					String combineId = vo.getCombineId();

					// 1.2 获取总资产(组合资产查询)
					List<StockCurrent> stockCurrentList = hundsunJres
							.funcAmStockCurrentQry(userToken, parentAccountNo,
									combineId);
					// 1)结算金额
					double assetTotalValue = 0D;
					if (!CollectionUtils.isEmpty(stockCurrentList)) {
						StockCurrent stockCurrent = stockCurrentList.get(0);
						// 结算总资产
						assetTotalValue = BigDecimalUtils.addRound(
								stockCurrent.getCurrentCash(),
								stockCurrent.getMarketValue());
					}
					if (new BigDecimal(assetTotalValue + "")
							.compareTo(new BigDecimal(0)) == 0) {
						continue;
					}
					// 6)划账操作
					// 与恒生账户同步
					// 6.1本地账户与恒生母账户金额同步 [结算金额 = 母账户金额]
					ParentAccount parentAccountObj = parentAccountService
							.findByParentAccountId(parentAccountNo);
					if (parentAccountObj != null) {

						/*
						 * parentAccountObj.setFundsBalance(BigDecimalUtils.add2(
						 * parentAccountObj.getFundsBalance(),
						 * assetTotalValue)); this.parentAccountService
						 * .updateParentAccount(parentAccountObj);
						 */
						boolean flag = transferMoneyUserToken(parentAccountNo,
								combineId,
								combineInfoService
										.getHundSunCombineId(parentAccountObj
												.getUnitNumber()),
								assetTotalValue, "终结方案划转资金", userToken);
						UserTrade userTrade = this.userTradeDao.get(vo.getId());
						if (flag) {
							userTrade
									.setCommissionState(TypeConvert.COMMISSION_STATE_SUCCESSFUL);
							this.userTradeDao.update(userTrade);
						} else {
							userTrade
									.setCommissionState(TypeConvert.COMMISSION_STATE_FAIL);
							this.userTradeDao.update(userTrade);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					UserTrade userTrade = this.userTradeDao.get(vo.getId());
					userTrade
							.setCommissionState(TypeConvert.COMMISSION_STATE_FAIL);
					this.userTradeDao.update(userTrade);
				}
			}
		}

	}

	/**
	 * T+1 终结划账
	 */
	public void endOfTransferMoney() {

		HundsunJres hundsunJres = null;
		try {
			hundsunJres = HundsunJres.getInstance();
		} catch (T2SDKException e) {
			e.printStackTrace();
			throw new UserTradeConcurrentException(
					"com.tzdr.business.server.connection.timeout", null);
		}
		String userToken = hundsunJres.Login();

		// 获取T+1的数据内容
		List<TransferVo> transferVoes = this.wUserService
				.queryUserTradeTodayAndOneBy();
		if (transferVoes != null) {
			for (TransferVo vo : transferVoes) {
				try {
					try {
						Thread.sleep(2000);
					} catch (Exception e) {
						e.printStackTrace();
					}
					String parentAccountNo = vo.getParentAccountNo();
					String combineId = vo.getCombineId();

					// 1.2 获取总资产(组合资产查询)
					/*
					 * List<StockCurrent> stockCurrentList = hundsunJres
					 * .funcAmStockCurrentQry(userToken, parentAccountNo,
					 * combineId);
					 */
					// 1)结算金额
					double assetTotalValue = 0D;
					/*
					 * if (!CollectionUtils.isEmpty(stockCurrentList)) {
					 * StockCurrent stockCurrent = stockCurrentList.get(0); //
					 * 结算总资产 assetTotalValue = BigDecimalUtils.addRound(
					 * stockCurrent.getCurrentCash(),
					 * stockCurrent.getMarketValue()); }
					 */
					assetTotalValue = vo.getFinishedMoney();
					// 6)划账操作
					// 与恒生账户同步
					// 6.1本地账户与恒生母账户金额同步 [结算金额 = 母账户金额]
					ParentAccount parentAccountObj = parentAccountService
							.findByParentAccountId(parentAccountNo);
					if (parentAccountObj != null) {
						/**
						 * parentAccountObj.setFundsBalance(BigDecimalUtils.add2
						 * ( parentAccountObj.getFundsBalance(),
						 * assetTotalValue)); this.parentAccountService
						 * .updateParentAccount(parentAccountObj);
						 **/
						/*boolean flag = transferMoneyUserToken(parentAccountNo,
								combineId,
								combineInfoService
										.getHundSunCombineId(parentAccountObj
												.getUnitNumber()),
								assetTotalValue, "终结方案划转资金", userToken);*/
						boolean flag = true;
						UserTrade userTrade = this.userTradeDao.get(vo.getId());
						if (flag) {
							userTrade
									.setTransferState(TypeConvert.TRANSFER_STATE_SUCCESSFUL);
							// userTrade.ge
							this.userTradeDao.update(userTrade);
						} else {
							userTrade
									.setTransferState(TypeConvert.TRANSFER_STATE_FAIL);
							this.userTradeDao.update(userTrade);
						}

						Account account = accountService.get(userTrade
								.getAccountId());
						account.setStatus(TypeConvert.ACCOUNT_STATUS_ENDED);
						account.setEndtime(Dates.getCurrentLongDate());
						accountService.updateAccount(account);
					}
				} catch (Exception e) {
					e.printStackTrace();
					UserTrade userTrade = this.userTradeDao.get(vo.getId());
					userTrade.setTransferState(TypeConvert.TRANSFER_STATE_FAIL);
					this.userTradeDao.update(userTrade);
				}
			}
		}
	}

	/**
	 * 合计值
	 * 
	 * @param billFees
	 *            List<BillingFeeVo>
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
	 * （operationStatus: -0：取消限制 -1：限制买入 2:限制卖出 3：限制买入卖出
	 */
	@Override
	public void changeOperationStatus(String[] groupIds,
			Integer operationStatus, String sysId) {

		try {

			HundsunJres hundsunJres = HundsunJres.getInstance();
			String userToken = hundsunJres.Login();
			for (String groupId : groupIds) {
				List<UserTrade> userTrades = findByGroupId(groupId);
				if (CollectionUtils.isEmpty(userTrades)) {
					log.error("根据组合id：" + groupId + "未查询到对应的配资方案");
					throw new UserTradeException(
							"user.trade.monitor.change.operation.status.fail",
							null);
				}

				boolean isSet = false;
				List<Integer> types = Arrays.asList(0, 1); //钱江版手动 与自动 0，1 
				for (UserTrade userTrade : userTrades) {
					if (!isSet && types.contains(userTrade.getFeeType())) {
						boolean result = hundsunJres.funcAmChangeOperatorInfo(
								userToken,
								NumberUtils.toLong(userTrade.getAccount()),
								"3", null, 180L, null,
								String.valueOf(operationStatus), null);
						if (!result) {
							hundsunJres.Logout(userToken);
							log.error(MessageUtils
									.message("user.trade.monitor.change.operation.status.fail"));
							throw new UserTradeException(
									"user.trade.monitor.change.operation.status.fail",
									null);
						}
						isSet = true;
					}

					// 更新状态
					switch (operationStatus) {
					case 0:
						userTrade.setBuyStatus(Boolean.FALSE);
						userTrade.setSellStatus(Boolean.FALSE);
						break;
					case 1:
						userTrade.setBuyStatus(Boolean.TRUE);
						break;
					case 2:
						userTrade.setSellStatus(Boolean.TRUE);
						break;
					case 3:
						userTrade.setBuyStatus(Boolean.TRUE);
						userTrade.setSellStatus(Boolean.TRUE);
						break;
					default:
						break;
					}
					update(userTrade);

					// 生成 方案操作记录
					if (StringUtil.isNotBlank(sysId)) {
						schemeLifeCycleRecordService
								.save(new SchemeLifeCycleRecord(userTrade,
										sysId, operationStatus));
					} else {
						User sysuser = authService.getCurrentUser();
						schemeLifeCycleRecordService
								.save(new SchemeLifeCycleRecord(userTrade,
										sysuser.getId(), operationStatus));
					}
				}
			}
			hundsunJres.Logout(userToken);
		} catch (Exception e) {
			log.error(this.getClass().getName() + e.getMessage());
			throw new UserTradeException(
					"user.trade.monitor.change.operation.status.fail", null);
		}

	}

	/**
	 * 平仓
	 * 
	 * @param userTrade
	 */
	@Override
	public void openStock(String[] groupIds, String sysId) {
		try {
			HundsunJres hundsunJres = HundsunJres.getInstance();
			String userToken = hundsunJres.Login();

			for (String groupId : groupIds) {
				List<UserTrade> userTrades = findByGroupId(groupId);
				if (CollectionUtils.isEmpty(userTrades)) {
					log.error("根据配资组合id：" + groupId + "未查询到对应的配资方案");
					throw new UserTradeException(
							"user.trade.monitor.change.operation.status.fail",
							null);
				}

				boolean isSet = false;
				for (UserTrade userTrade : userTrades) {
					if (!isSet) {
						// 获取母账户编号和组合编号
						String parentAccount = userTrade.getParentAccountNo();
						String combineId = userTrade.getCombineId();
						if (StringUtil.isBlank(combineId)
								|| StringUtil.isBlank(parentAccount)) {
							log.error(MessageUtils
									.message("user.trade.monitor.change.operation.status.open.stock.fail"));
							throw new UserTradeException(
									"user.trade.monitor.change.operation.status.open.stock.fail",
									null);
						}
						// 持仓查询
						List<Combostock> combostocks = hundsunJres
								.funcAmCombostockQry(userToken, parentAccount,
										combineId, null, null);
						// 根据持仓 依次 委卖
						if (!CollectionUtils.isEmpty(combostocks)) {
							for (Combostock combostock : combostocks) {
								// 委卖 可用数量是否大于0
								double enableAmount = combostock
										.getEnableAmount();
								if (enableAmount <= 0) {
									continue;
								}
								// 有可用数量 直接委卖可用数量
								String exchangeType = combostock
										.getExchangeType();
								String ampriceType = StringUtil.equals(
										exchangeType, "1") ? "a" : "A";
								// 调用普通委托接口
								EntrustResult result = hundsunJres
										.funcAmEntrust(userToken,
												combostock.getFundAccount(),
												combostock.getCombineId(),
												null, null,
												combostock.getExchangeType(),
												combostock.getStockCode(), "2",
												ampriceType, enableAmount,
												1000, null, null);

								if (!result.isSuccess()) {
									log.debug("委卖配资方案ID：" + userTrade.getId()
											+ "所持股票失败，股票代码："
											+ combostock.getStockCode());
									throw new UserTradeException(
											"user.trade.monitor.open.stock.fail",
											null);
								}
							}
						}

						isSet = true;
					}

					// 生成 方案操作记录
					if (StringUtil.isNotBlank(sysId)) {
						schemeLifeCycleRecordService
								.save(new SchemeLifeCycleRecord(userTrade,
										sysId, 4));
					} else {
						User sysuser = authService.getCurrentUser();
						schemeLifeCycleRecordService
								.save(new SchemeLifeCycleRecord(userTrade,
										sysuser.getId(), 4));
					}

				}
			}
		} catch (Exception e) {
			log.error(this.getClass().getName() + e.getMessage());
			throw new UserTradeException("user.trade.monitor.open.stock.fail",
					null);
		}
	}

	public UserTrade findUserTradeByGroupId(String groupId) {
		List<UserTrade> list = getEntityDao().findByGroupIdOrderByAddtimeAsc(groupId);
		return CollectionUtils.isEmpty(list) ? null : list.get(0);
	}

	/*
	 * @see
	 * com.tzdr.business.service.userTrade.UserTradeService#findPositionDetail()
	 */
	@Override
	public List<PositionDetailsVo> findPositionDetail(String groupId) {
		List<PositionDetailsVo> positionDetailsVoList = Lists.newArrayList();
		try {
			UserTrade userTrade = this.findUserTradeByGroupId(groupId);
			HundsunJres hundsunJres = HundsunJres.getInstance();
			String userToken = hundsunJres.Login();
			List<Combostock> combostocksList = hundsunJres.funcAmCombostockQry(
					userToken, userTrade.getParentAccountNo(),
					userTrade.getCombineId(), null, null);
			for (Combostock combostock : combostocksList) {
				if (combostock.getCurrentAmount() != 0) {
					PositionDetailsVo positionDetailsVo = new PositionDetailsVo();
					positionDetailsVo.setStockCode(combostock.getStockCode());
					positionDetailsVo.setCurrentAmount(combostock
							.getCurrentAmount());
					positionDetailsVo.setCostBalance(combostock
							.getCostBalance());
					positionDetailsVo.setMarketValue(combostock
							.getMarketValue());
					positionDetailsVoList.add(positionDetailsVo);
				}
			}
			hundsunJres.Logout(userToken);
		} catch (Exception e) {
			log.error(this.getClass().getName() + e.getMessage());
			throw new UserTradeException(
					"user.trade.monitor.change.operation.status.fail", null);
		}

		return positionDetailsVoList;
	}

	/* 
	 * 	查询第一个配资方案
	 * 
	 * @see com.tzdr.business.service.userTrade.UserTradeService#findByGroupIdOrderByAddtimeAsc(java.lang.String)
	 */
	@Override
	public List<UserTrade> findByGroupIdOrderByAddtimeAsc(String groupId) {
		return this.getEntityDao().findByGroupIdOrderByAddtimeAsc(groupId);
	}

	@Override
	public UserTrade findOneByGroupIdOrderByAddtimeAsc(String groupId) {
		List<UserTrade> list = this.getEntityDao()
				.findByGroupIdOrderByAddtimeAsc(groupId);
		return CollectionUtils.isEmpty(list) ? null : list.get(0);
	}

	@Override
	public BigDecimal totalLevelMoneyByGroupId(String groupId) {
		String sql = "SELECT SUM(lever_money) totalAmount FROM w_user_trade WHERE group_id = ? ";
		List<TotalMarginVo> voes = this.getEntityDao().queryBySql(sql,
				TotalMarginVo.class, groupId);
		return new BigDecimal(voes.get(0).getTotalAmount());
	}

	@Override
	public synchronized boolean addBond(ParentAccount parentAccount,
			UserTrade userTrade, double addMoney, WUser userTemp) {

		if (userTemp.getAvlBal() < addMoney) {
			throw new UserTradeException("no.cash.accout", null);
		}

		userTrade.setAppendLeverMoney(BigDecimalUtils.addRound(
				userTrade.getAppendLeverMoney(), addMoney));
		userTrade.setTotalLeverMoney(BigDecimalUtils.addRound(
				userTrade.getTotalLeverMoney(), addMoney));
		parentAccount.setFundsBalance(BigDecimalUtils.subRound(
				parentAccount.getFundsBalance(), addMoney));

		userTemp.setAvlBal(BigDecimalUtils.subRound(userTemp.getAvlBal(),
				addMoney));
		userTemp.setFund(BigDecimalUtils.addRound(userTemp.getAvlBal(),
				userTemp.getFrzBal()));
		save(userTrade);
		parentAccountService.updateParentAccount(parentAccount);
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
		userFund.setRemark("支付追加保证金" + addMoney + "元");
		userFundService.save(userFund);

		return true;
	}

	@Override
	public boolean isUpMarginLine(String groupId) {
		String sql = "SELECT trade.uid, trade.account, trade.group_id groupId, sum(trade.warning) warning, trade.combine_id combineId, trade.parent_account_no parentAccountNo FROM w_user_trade trade WHERE trade.`status` = 1 AND trade.group_id = ?";
		List<Object> params = Lists.newArrayList();
		params.add(groupId);
		List<UserTradeCmsVo> list = nativeQuery(sql, params,
				UserTradeCmsVo.class);
		if (CollectionUtils.isEmpty(list)) {
			return false;
		}
		UserTradeCmsVo userTradeCmsVo = list.get(0);
		String combineId = userTradeCmsVo.getCombineId();
		if (StringUtil.isBlank(combineId)) {
			return false;
		}
		List<StockCurrent> stockCurrentList;
		try {
			HundsunJres hundsunJres = HundsunJres.getInstance();
			String userToken = hundsunJres.Login();
			stockCurrentList = hundsunJres.funcAmStockCurrentQry(userToken,
					userTradeCmsVo.getParentAccountNo(), combineId);
			hundsunJres.Logout(userToken);

			if (CollectionUtils.isEmpty(stockCurrentList)) {
				return false;
			}

			StockCurrent stockCurrent = stockCurrentList.get(0);
			double assetTotalValue = BigDecimalUtils.addRound(
					stockCurrent.getCurrentCash(),
					stockCurrent.getMarketValue());
			double warning = userTradeCmsVo.getWarning();
			if (assetTotalValue > warning) {
				return true;
			}
		} catch (Exception e) {
			log.error("调用恒生接口股票资产查询失败:" + Exceptions.getStackTraceAsString(e));
			EmailExceptionHandler.getInstance().HandleExceptionWithData(e,
					"扣费时校验方案是否在补仓线以上，" + "调用恒生接口股票资产查询失败", "isUpMarginLine",
					"方案组合号：groupID=" + groupId);
			return false;
		}
		return false;
	}

	@Override
	public UserTradeCmsVo findTradeByGroupIdAndUid(String groupId, String uid) {
		String sql = " select uid,activity_type as activityType,fee_type as feeType,account_id as accountId,unit_number as unitNumber,lever,parent_account_no as parentAccountNo,startdays,sum(finished_money) as finishedMoney,sum(apr) as apr,MAX(estimate_endtime) as estimateEndtime,sum(fee_day) as feeDay,sum(fee_month) as feeMonth,sum(accrual) as totalAccrual,asset_id as assetId,combine_id as combineId,group_id as groupId,min(starttime) as starttime,MAX(endtime) as endtime,SUM(total_lever_money) as totalOperateMoney,SUM(lever_money) as totalLeverMoney ,SUM(money) as totalLending,SUM(append_lever_money) as totalAppendLeverMoney,STATUS as status,hs_belong_broker as hsBelongBroker,SUM(warning) as warning,SUM(`open`) as `open`,account,password from w_user_trade w where uid=? and w.group_id=? GROUP BY w.group_id  ORDER BY starttime DESC";
		List<Object> params = Lists.newArrayList();
		params.add(uid);
		params.add(groupId);
		List<UserTradeCmsVo> list = nativeQuery(sql, params,
				UserTradeCmsVo.class);
		if (!CollectionUtils.isEmpty(list)) {
			UserTradeCmsVo userTradeCmsVo = list.get(0);
			return userTradeCmsVo;
		}
		return null;
	}

	@Override
	public synchronized UserTradeTransfer addMoreUserTradeAndOpenHundsun(
			UserTrade userTrade, WUser wuser, ParentAccount parentAccount,
			UserTradeCmsVo userTradeCmsVo, String volumeDetailId) {
		// 0.首次利息计算
		double firstFee = BigDecimalUtils.round(userTrade.getFeeMonth()
				* userTrade.getNaturalDays(), 2);

		String parentCombineId = combineInfoService
				.getHundSunCombineId(userTradeCmsVo.getUnitNumber());

		// 配资中
		userTrade.setStatus((short) 0);

		userTrade.setAssetId(userTradeCmsVo.getAssetId());
		userTrade.setProgramNo("T" + RandomCodeUtil.genToken(7));
		userTrade.setCombineId(userTradeCmsVo.getCombineId());
		userTrade.setAccount(userTradeCmsVo.getAccount());
		userTrade.setAccountId(userTradeCmsVo.getAccountId());
		userTrade.setPassword(userTradeCmsVo.getPassword());
		userTrade.setHsBelongBroker(userTradeCmsVo.getHsBelongBroker());
		userTrade.setParentAccountNo(userTradeCmsVo.getParentAccountNo());
		userTrade.setUnitNumber(userTradeCmsVo.getUnitNumber());
		// 首次利息
		userTrade.setApr(firstFee);

		// 1.w_user用户基本信息表中修改可用余额,资金总额.
		// WUser wuser = wUserService.getUser(user.getId());
		// 扣配资后
		double afterMoney = BigDecimalUtils.sub(wuser.getAvlBal(),
				userTrade.getLeverMoney());
		// 扣利息后
		double avlBal = BigDecimalUtils.sub(wuser.getAvlBal(),
				userTrade.getLeverMoney() + firstFee);

		double avlBaltemp = avlBal;
		// 扣第一天管理费后
		double manageBal = BigDecimalUtils.sub(wuser.getAvlBal(),
				userTrade.getLeverMoney() + firstFee + userTrade.getFeeDay());
		double manageBalTemp = manageBal;

		double volumeFee = 0.0;
		// 有抵扣券
		if (!StringUtils.isBlank(volumeDetailId)) {
			VolumeDetail volumeDetail = volumeDetailService.get(volumeDetailId);
			// 没有要抛异常
			if (ObjectUtil.equals(volumeDetail, null)) {
				throw new UserTradeException("no.avi.volumeDetail", null);
			}
			double cardMoney = volumeDetail.getVolumeDeductible().getMoney();
			if (BigDecimalUtils.sub(cardMoney, firstFee) >= 0) {
				volumeFee = firstFee;
			} else {
				volumeFee = cardMoney;
			}
			volumeDetail.setRealPayAmount(volumeFee);
			volumeDetail.setUseState(VolumeDetail.USE_STATE_USED);
			volumeDetail.setUseDateValue(Dates.getCurrentLongDate());
			volumeDetail.setRemark("方案编号：" + userTrade.getProgramNo());
			volumeDetailService.update(volumeDetail);
			avlBal = BigDecimalUtils.addRound(avlBal, volumeFee);
			manageBal = BigDecimalUtils.addRound(manageBal, volumeFee);
		}

		if (userTrade.getTradeStart() == 0) {
			if (manageBal >= 0) {
				wuser.setAvlBal(manageBal);
				wuser.setFund(BigDecimalUtils.add(manageBal, wuser.getFrzBal()));
				wUserService.updateUser(wuser);
			} else {
				throw new UserTradeException("no.cash.accout", null);
			}
		} else {
			if (avlBal >= 0) {
				wuser.setAvlBal(avlBal);
				wuser.setFund(BigDecimalUtils.add(avlBal, wuser.getFrzBal()));
				wUserService.updateUser(wuser);
			} else {
				throw new UserTradeException("no.cash.accout", null);
			}
		}

		save(userTrade);

		// 3.生成一条w_user_fund 用户资金记录表 记录配资记录.还有一条利息记录
		// (1)配资记录
		UserFund userFund = new UserFund();
		userFund.setUid(wuser.getId());
		userFund.setNo(userTrade.getId());
		userFund.setLid(userTrade.getGroupId());
		userFund.setRid(userTrade.getProgramNo());
		// 类型为配资支出
		userFund.setType(10);
		userFund.setMoney(-userTrade.getLeverMoney());
		userFund.setAmount(afterMoney);
		userFund.setPayStatus((short) 1);
		userFund.setAddtime(Dates.getCurrentLongDate());
		userFund.setUptime(Dates.getCurrentLongDate());
		userFund.setRemark("支付配资本金" + userTrade.getLeverMoney() + "元");
		userFundService.save(userFund);
		// (2)利息记录
		UserFund userFundLx = new UserFund();
		userFundLx.setUid(wuser.getId());
		userFundLx.setNo(userTrade.getId());
		userFundLx.setLid(userTrade.getGroupId());
		userFundLx.setRid(userTrade.getProgramNo());
		// 类型为利息费
		userFundLx.setType(11);
		userFundLx.setMoney(-firstFee);
		userFundLx.setAmount(avlBaltemp);
		userFundLx.setPayStatus((short) 1);
		userFundLx.setAddtime(Dates.getCurrentLongDate() + 1);
		userFundLx.setUptime(Dates.getCurrentLongDate() + 1);
		userFundLx.setRemark("支付利息" + firstFee + "元");
		userFundService.save(userFundLx);
		if (userTrade.getTradeStart() == 0) {
			// (3)当天管理费记录
			UserFund userFundManage = new UserFund();
			userFundManage.setUid(wuser.getId());
			userFundManage.setNo(userTrade.getId());
			userFundManage.setLid(userTrade.getGroupId());
			userFundManage.setRid(userTrade.getProgramNo());
			// 类型为利息费
			userFundManage.setType(12);
			userFundManage.setMoney(-userTrade.getFeeDay());
			userFundManage.setAmount(manageBalTemp);
			userFundManage.setPayStatus((short) 1);
			userFundManage.setAddtime(Dates.getCurrentLongDate() + 2);
			userFundManage.setUptime(Dates.getCurrentLongDate() + 2);
			userFundManage.setRemark("支付管理费" + userTrade.getFeeDay() + "元");
			userFundService.save(userFundManage);
		}
		// 4.更新母账户钱

		if (!StringUtils.isBlank(volumeDetailId)) {
			UserFund userFundVolume = new UserFund();
			userFundVolume.setUid(wuser.getId());
			userFundVolume.setNo(userTrade.getId());
			userFundVolume.setLid(userTrade.getGroupId());
			userFundVolume.setRid(userTrade.getProgramNo());
			// 24 抵扣劵收入
			userFundVolume.setType(24);
			userFundVolume.setMoney(volumeFee);
			userFundVolume.setAmount(BigDecimalUtils.round(avlBal, 2));
			if (userTrade.getTradeStart() == 0) {
				userFundVolume.setAmount(BigDecimalUtils.round(manageBal, 2));
			}
			userFundVolume.setPayStatus((short) 1);
			userFundVolume.setAddtime(Dates.getCurrentLongDate() + 3);
			userFundVolume.setUptime(Dates.getCurrentLongDate() + 3);
			userFundVolume.setRemark("抵扣劵收入：" + volumeFee + "元，用于方案："
					+ userTrade.getProgramNo() + "抵扣利息。");
			userFundService.save(userFundVolume);
		}

		parentAccount
				.setFundsBalance(BigDecimalUtils.sub(
						parentAccount.getFundsBalance(),
						userTrade.getTotalLeverMoney()));
		synchronized (this) {
			parentAccountService.updateParentAccount(parentAccount);
		}

		UserTradeTransfer userTradeTransfer = new UserTradeTransfer();
		userTradeTransfer.setUserTrade(userTrade);
		userTradeTransfer.setAccountNo(parentAccount.getAccountNo());
		userTradeTransfer.setParentCombineId(parentCombineId);
		userTradeTransfer.setChildrenCombineId(userTradeCmsVo.getCombineId());
		String endtime=TypeConvert.long1000ToDateStr((parentAccount.getAllocationDate()-5*Dates.DAY_LONG));
		userTradeTransfer.setParentEndTime(endtime);
		return userTradeTransfer;
	}

	/*
	 * 
	 * 提取利润
	 * 
	 * @see
	 * com.tzdr.business.service.userTrade.UserTradeService#extractableProfit
	 * (com.tzdr.domain.web.entity.UserTrade, com.tzdr.domain.web.entity.WUser,
	 * com.tzdr.domain.web.entity.ParentAccount)
	 */

	@Override
	public synchronized boolean extractableProfit(UserTrade userTrade,
			WUser user, ParentAccount parentAccount,
			double extractableProfitMoney) {

		String parentCombineId = combineInfoService
				.getHundSunCombineId(userTrade.getUnitNumber());

		/*
		 * (1)增加balance不要了
		 * parentAccount.setFundsBalance(BigDecimalUtils.addRound(
		 * parentAccount.getFundsBalance(), extractableProfitMoney));
		 * parentAccountService.updateParentAccount(parentAccount);
		 */
		// (2)生成提取利润记录
		UserFund userFund = new UserFund();
		userFund.setUid(user.getId());
		userFund.setNo(userTrade.getId());
		userFund.setLid(userTrade.getGroupId());
		// 类型为提取利润
		userFund.setType(16);
		userFund.setMoney(extractableProfitMoney);
		// 余额
		double banlance = BigDecimalUtils.addRound(user.getAvlBal(),
				extractableProfitMoney);
		userFund.setAmount(banlance);
		userFund.setPayStatus((short) 1);
		userFund.setAddtime(Dates.getCurrentLongDate());
		userFund.setUptime(Dates.getCurrentLongDate());
		userFund.setRemark("提取利润" + extractableProfitMoney + "元");
		// userFundService.save(userFund);

		// (3)增加balance
		// user.setAvlBal(banlance);
		// user.setFund(BigDecimalUtils.addRound(banlance, user.getFrzBal()));
		// wUserService.updateUser(user);

		userFundService.arrearsProcess(userFund);

		if (transferMoney(userTrade.getParentAccountNo(),
				userTrade.getCombineId(), parentCombineId,
				extractableProfitMoney, "提取利润资金划转")) {
			return true;
		} else {
			log.error("提取利润资金划转失败Uid:" + userTrade.getWuser().getId()
					+ "ParentAccountNo:" + userTrade.getParentAccountNo()
					+ "childrenId:" + userTrade.getCombineId()
					+ "parentCombineId:" + parentCombineId
					+ "extractableProfitMoney:" + extractableProfitMoney);
			throw new UserTradeException("system.time.error", null);
		}
	}

	@Override
	public EndMoneyVo endOfProgrameSum(String groupId) {
		String sql = " SELECT SUM(t.money) money,SUM(t.lever_money) leverMoney,"
				+ " SUM(t.append_lever_money) appendLeverMoney"
				+ " FROM w_user_trade t" + " WHERE  t.group_id = ?";
		List<EndMoneyVo> list = this.getEntityDao().queryBySql(sql,
				EndMoneyVo.class, groupId);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public PageInfo<Object> queryNoCancelledList(EasyUiPageInfo easyUiPage,
			Map<String, Object> searchParams) {
		PageInfo<Object> pageInfo = new PageInfo<Object>(easyUiPage.getRows(),
				easyUiPage.getPage());

		String sql = " SELECT temp.id, temp.mobile, temp.endtime, temp.account, temp.assetId, temp.`status`, temp.feeType "
				+ " FROM ( SELECT ac.id, us.mobile, trade.endtime, ac.account, ac.asset_id as assetId, ac.`status`, ac.create_user_id, trade.fee_type feeType FROM"
				+ " w_user_trade trade, w_account ac, w_user us WHERE trade.`status` = 2  and ac.`status`= 2 AND ac.id = trade.account_id AND us.id = trade.uid GROUP BY trade.group_id ) temp,"
				+ " sys_user su, sys_organization org WHERE temp.create_user_id = su.id AND su.organization_id = org.id AND org.`code` LIKE ?";

		User loginUser = authService.getCurrentUser();
		String code = loginUser.getOrganization().getCode();
		// params 查询参数 依次 存入
		List<Object> params = Lists.newArrayList();
		params.add(code + "%");
		if (code.length() >= Organization.DEFAULT_CODE_LENGTH) {
			sql = sql + " and  temp.create_user_id=? ";
			params.add(loginUser.getId());
		}

		MultiListParam multilistParam = new MultiListParam(easyUiPage,
				searchParams, params, sql);

		pageInfo = multiListPageQuery(multilistParam, AccountVo.class);
		return pageInfo;
	}

	/**
	 * @param 6600 母账户类型
	 * @return 可用母账户
	 */
	public ParentAccount findParentAccout(String priorityNo) {
		return parentAccountService.findByPriorityNoAndDeletedFalse(priorityNo);
	}

	@Override
	public PageInfo<Object> queryTradeFailList(EasyUiPageInfo easyUiPage,
			Map<String, Object> searchParams) {
		PageInfo<Object> pageInfo = new PageInfo<Object>(easyUiPage.getRows(),
				easyUiPage.getPage());

		String sql = " SELECT trade.account_id accountId, wuser.id uid, verified.tname uname, wuser.mobile, trade.combine_id combineId, trade.account, trade.group_id groupId, trade.`password`, trade.money totalLending, trade.lever_money totalLeverMoney, trade.append_lever_money totalAppendLeverMoney, trade.warning warning, trade.`open` `open`, trade.id, trade.starttime, trade.endtime, trade.`status`, trade.buy_status buyStatus, trade.sell_status sellStatus, trade.hs_belong_broker hsBelongBroker, trade.unit_number unitNumber, trade.parent_account_no parentAccountNo, trade.asset_id assetId FROM w_user_trade trade, w_user wuser, w_user_verified verified WHERE trade.`status` = 0 AND wuser.id = trade.uid AND wuser.id = verified.uid"
				+ " and trade.fee_type=0 " + " ORDER BY trade.addtime ASC ";
		// params 查询参数 依次 存入
		String code = authService.getCurrentUser().getOrganization().getCode();
		List<Object> params = Lists.newArrayList();
		params.add(code + "%");
		sql = getPermissionSql(sql, code, params, authService.getCurrentUser()
				.getId());
		MultiListParam multilistParam = new MultiListParam(easyUiPage,
				searchParams, params, sql);
		pageInfo = multiListPageQuery(multilistParam, UserTradeCmsVo.class);
		return pageInfo;
	}

	@Override
	public void thawUserTrade() {
		List<UserTrade> list = findUserTradeStartToday();

		// 子账户状态需要修改
		HundsunJres hundsunJres = null;
		try {
			hundsunJres = HundsunJres.getInstance();
		} catch (T2SDKException e) {
			log.error(e.getMessage());
			throw new UserTradeConcurrentException(
					"com.tzdr.business.server.connection.timeout", null);
		}

		String userToken = hundsunJres.Login();
		for (UserTrade userTrade : list) {
			try {
				// 走线下[账号、异常类型、异常信息]
				Boolean flag = hundsunJres.funcAmChangeOperatorInfo(userToken,
						new Long(userTrade.getAccount()),
						TypeConvert.USER_THAW_STATE, null, 180L, null, null,
						null);
				if (!flag) {
					FreezeFailInfo freezeFailInfo = new FreezeFailInfo();
					freezeFailInfo.setAccount(userTrade.getAccount());
					freezeFailInfo.setTypeInfo("解冻失败");
					freezeFailInfo.setMessageText("调用恒生接口失败");
					freezeFailInfoService.save(freezeFailInfo);
				}
				// 暂停
				Thread.sleep(2000);
			} catch (Exception e) {
				e.printStackTrace();
				FreezeFailInfo freezeFailInfo = new FreezeFailInfo();
				freezeFailInfo.setAccount(userTrade.getAccount());
				freezeFailInfo.setTypeInfo("解冻失败");
				freezeFailInfo.setMessageText("调用恒生接口通讯异常");
				freezeFailInfoService.save(freezeFailInfo);
			}

		}
		hundsunJres.Logout(userToken);

	}

	@Override
	public List<UserTrade> findUserTradeStartToday() {
		String now = Dates.format(new Date(), Dates.CHINESE_DATE_FORMAT_LONG);
		String sql = " select account as account  from w_user_trade w where from_unixtime(w.starttime,'%Y%m%d')=? and w.trade_start=1 and w.status!=2 and w.fee_type < 2";
		List<Object> params = Lists.newArrayList();
		params.add(now);
		List<UserTrade> list = nativeQuery(sql, params, UserTrade.class);
		return list;
	}

	@Override
	public void reviewTrade(String[] ids) {
		for (String id : ids) {
			UserTrade userTrade = getEntityDao().get(id);
			userTrade.setStatus(NumberUtils.toShort("1"));
			userTrade.setCombineId(combineInfoService
					.getHundSunCombineId(userTrade.getAssetId()));
			super.update(userTrade);
			// 发送短信
			Map<String, String> map = Maps.newHashMap();
			map.put("group", userTrade.getGroupId());
			map.put("starttime", Dates.format(
					Dates.parseLong2Date(userTrade.getStarttime()),
					Dates.CHINESE_DATE_FORMAT_LINE));
			new SMSPgbSenderThread(userTrade.getWuser().getMobile(),
					"ihuyi.trade.ok.code.template", map).start();
		}
	}

	@Override
	public void sendMarginRemindMsg() {
		String sql = " SELECT trade.account_id accountId, "
						+ "wuser.id uid, verified.tname uname, wuser.mobile, "
						+ "trade.combine_id combineId, trade.account, trade.group_id groupId, "
						+ "trade.`password`, sum(trade.money) totalLending, "
						+ "sum(trade.lever_money) totalLeverMoney, "
						+ "sum(trade.append_lever_money) totalAppendLeverMoney, "
						+ "sum(trade.warning) warning, sum(trade.`open`) `open`, "
						+ "trade.starttime, trade.endtime, trade.`status`, "
						+ "trade.notice_status noticeStatus, trade.hs_belong_broker hsBelongBroker, "
						+ "trade.unit_number unitNumber, trade.parent_account_no parentAccountNo, "
						+ "trade.asset_id assetId "
					+ "FROM w_user_trade trade, w_user wuser, w_user_verified verified "
					+ "WHERE trade.`status` = 1 "
						+ "and trade.fee_type < 2  "
						+ "AND wuser.id = trade.uid "
						+ "AND wuser.id = verified.uid "
						+ "AND NOT EXISTS ( "
									+ "SELECT "
										+ "wnr.group_id "
									+ "FROM "
										+ "w_notice_record wnr "
									+ "WHERE "
										+ "wnr.type = 5 "
										+ "and wnr.group_id=trade.group_id "
								+ ") "
						+ "GROUP BY trade.group_id "
						+ "ORDER BY verified.tname DESC ";
		List<UserTradeCmsVo> list = nativeQuery(sql, null, UserTradeCmsVo.class);
		if (CollectionUtils.isEmpty(list)) {
			return;
		}

		// 获取组合资产信息
		List<StockCurrent> stockCurrents = getAllStockCurrents();
		if (CollectionUtils.isEmpty(stockCurrents)) {
			return;
		}
		for (UserTradeCmsVo tradeVo : list) {
			String fundAccount = tradeVo.getParentAccountNo();
			String combineId = tradeVo.getCombineId();
			if (StringUtil.isBlank(combineId)
					|| StringUtil.isBlank(fundAccount)) {
				continue;
			}

			for (StockCurrent stockCurrent : stockCurrents) {
				String sfundAccount = stockCurrent.getFundAccount();
				String scombineId = stockCurrent.getCombineId();

				if (StringUtil.equals(scombineId, combineId)
						&& StringUtil.equals(sfundAccount, fundAccount)) {
					log.info("定时扫描补仓提醒短信--获取【" + tradeVo.getAccount()
							+ "】账户的恒生数据：fundAccount="
							+ stockCurrent.getFundAccount() + ",combineId="
							+ stockCurrent.getCombineId() + ",currentCash="
							+ stockCurrent.getCurrentCash() + ",marketValue="
							+ stockCurrent.getMarketValue());

					double assetTotalValue = BigDecimalUtils.addRound(
							stockCurrent.getCurrentCash(),
							stockCurrent.getMarketValue());
					double warning = tradeVo.getWarning();
					if (assetTotalValue > warning) {
						continue;
					}

					String groupId = tradeVo.getGroupId();
					List<UserTrade> userTrades = findByGroupId(groupId);
					if (CollectionUtils.isEmpty(userTrades)) {
						continue;
					}
					// 限制买入
					// changeOperationStatus(new
					// String[]{groupId},1,SchemeLifeCycleRecord.SYSTEM_OPERATOR);

					// 补偿提醒 短信发送
					String content = MessageUtils.message(
							"user.trade.margin.remind.message", groupId);
					List<DataMap> startdata = CacheManager
							.getDataMapListByTypeKey(DataDicKeyConstants.ACTI_START);
					List<DataMap> enddata = CacheManager
							.getDataMapListByTypeKey(DataDicKeyConstants.ACTI_END);
					String starttime = startdata.get(0).getValueKey();
					String endtime = enddata.get(0).getValueKey();
					Date startdate = Dates.parse(starttime,
							Dates.CHINESE_DATETIME_FORMAT_LINE);
					Date enddate = Dates.parse(endtime,
							Dates.CHINESE_DATETIME_FORMAT_LINE);
					Date nowDate = new Date();
					if (nowDate.before(enddate) && nowDate.after(startdate)) {
						content = MessageUtils.message(
								"user.trade.margin.remind.activity.message",
								groupId);
					}
					PgbSMSSender.getInstance().send(dataMapService.getPgbSmsContentOthers(), tradeVo.getMobile(), content);
					// 通知记录
					noticeRecordService.save(new NoticeRecord(tradeVo.getUid(),
							groupId, content, 5));
				}
			}

		}
	}

	@Override
	public PageInfo<Object> queryAllTrades(EasyUiPageInfo easyUiPage,
			Map<String, Object> searchParams) {
		PageInfo<Object> pageInfo = new PageInfo<Object>(easyUiPage.getRows(),
				easyUiPage.getPage());
		String sql = "SELECT MIN(trade.estimate_endtime) estimateEndtime ,trade.fee_type feeType,trade.activity_type activityType,"
				// + "get_user_profits (wuser.id, trade.group_id) allExtractableProfit, "
				+ " trade.account_id accountId,trade.new_status newStatus,trade.shortest_duration shortestDuration, MIN(trade.addtime) addtime, wuser.id uid, "
				+ " verified.tname uname, wuser.mobile, trade.combine_id combineId, trade.account, trade.group_id groupId, trade.`password`,"
				+ " sum(trade.money) totalLending, sum(trade.lever_money) totalLeverMoney, sum(trade.append_lever_money) totalAppendLeverMoney, "
				+ " sum(trade.warning) warning, sum(trade.`open`) `open`, MAX(trade.natural_days) naturalDays, MIN(trade.starttime) starttime,"
				+ " trade.endtime, trade.`status`, trade.buy_status buyStatus, trade.sell_status sellStatus, trade.hs_belong_broker hsBelongBroker, "
				+ " trade.unit_number unitNumber, trade.parent_account_no parentAccountNo, trade.asset_id assetId, acc.account_name accountName, "
				+ " wuser.activity_type userType FROM w_user_trade trade, w_user wuser, w_user_verified verified, w_account acc WHERE wuser.id = trade.uid "
				+ " AND wuser.id = verified.uid AND acc.id = trade.account_id GROUP BY trade.group_id ORDER BY trade.addtime ASC ";
		// params 查询参数 依次 存入
		String code = authService.getCurrentUser().getOrganization().getCode();
		List<Object> params = Lists.newArrayList();
		params.add(code + "%");
		sql = getPermissionSql(sql, code, params, authService.getCurrentUser()
				.getId());
		MultiListParam multilistParam = new MultiListParam(easyUiPage,
				searchParams, params, sql);
		pageInfo = multiListPageQuery(multilistParam, UserTradeCmsVo.class);

		List<Object> list = pageInfo.getPageResults();
		List<Object> tempList = Lists.newArrayList();
		if (!CollectionUtils.isEmpty(list)) {
			for (Object tradeCmsVo : list) {
				UserTradeCmsVo cmsVo = (UserTradeCmsVo) tradeCmsVo;
				cmsVo.setTradingDays(tradeDayService.getTradeDays(cmsVo));
				/*BigInteger day=cmsVo.getNaturalDays();
				BigInteger addtime=cmsVo.getAddtime();
				Date adddate=Dates.parseLong2Date(addtime.longValue());
				String estimatedTime=Dates.dateAddDay(adddate, day.intValue());
				cmsVo.setEstimatedTime(estimatedTime);*/
				
				tempList.add(cmsVo);
			}
		}
		pageInfo.setPageResults(tempList);
		return pageInfo;
	}

	public PageInfo<Object> queryEndFailTrades(EasyUiPageInfo easyUiPage,
			Map<String, Object> searchParams) {

		PageInfo<Object> pageInfo = new PageInfo<Object>(easyUiPage.getRows(),
				easyUiPage.getPage());
		String sql = "SELECT trade.account_id accountId, wuser.id uid, verified.tname uname, wuser.mobile, trade.account, trade.group_id groupId, trade.endtime, trade.`status`, trade.parent_account_no parentAccountNo FROM w_user_trade trade, w_user wuser, w_user_verified verified WHERE wuser.id = trade.uid AND trade.`status` = 2 AND trade.transfer_state = 0 AND wuser.id = verified.uid GROUP BY trade.group_id ORDER BY trade.addtime ASC ";

		// params 查询参数 依次 存入
		String code = authService.getCurrentUser().getOrganization().getCode();
		List<Object> params = Lists.newArrayList();
		params.add(code + "%");
		sql = getPermissionSql(sql, code, params, authService.getCurrentUser()
				.getId());
		MultiListParam multilistParam = new MultiListParam(easyUiPage,
				searchParams, params, sql);
		pageInfo = multiListPageQuery(multilistParam, UserTradeCmsVo.class);
		return pageInfo;
	}

	@Override
	public void endSuccessTrade(String[] groupIdArray) {
		for (String groupId : groupIdArray) {
			List<UserTrade> userTrades = getEntityDao().findByGroupId(groupId);
			boolean flag = false;

			for (UserTrade userTrade : userTrades) {
				if (!flag) {
					String accountId = userTrade.getAccountId();
					Account account = accountService.get(accountId);
					account.setStatus(TypeConvert.ACCOUNT_STATUS_ENDED);
					account.setEndtime(Dates.getCurrentLongDate());
					accountService.updateAccount(account);
					flag = true;
				}

				//
				userTrade
						.setTransferState(TypeConvert.TRANSFER_STATE_SUCCESSFUL);
				// userTrade.ge
				this.userTradeDao.update(userTrade);
			}

		}
	}

	@Override
	public PageInfo<Object> queryChildTrades(EasyUiPageInfo easyUiPage,
			Map<String, Object> searchParams) {
		String sql = "SELECT trade.id,trade.program_no programNo,trade.apr apr,trade.combine_id combineId, trade.account, trade.group_id groupId, trade.money totalLending, trade.lever_money totalLeverMoney, trade.append_lever_money totalAppendLeverMoney, trade.warning warning, trade.`open` `open`, trade.starttime, trade.endtime, trade.addtime, trade.`status`, trade.fee_day feeDay, trade.fee_month feeMonth FROM w_user_trade trade ORDER BY addtime ASC ";
		// params 查询参数 依次 存入
		List<Object> params = Lists.newArrayList();

		MultiListParam multilistParam = new MultiListParam(easyUiPage,
				searchParams, params, sql);

		PageInfo<Object> pageInfo = new PageInfo<Object>(easyUiPage.getRows(),
				easyUiPage.getPage());

		pageInfo = multiListPageQuery(multilistParam, UserTradeCmsVo.class);
		return pageInfo;
	}

	@Override
	public double findActivityByGroupId(String groupId) {
		String sql = "SELECT COUNT(*) FROM w_user_trade wut WHERE wut.activity_type=1 AND wut.accrual<=(SELECT wut.accrual FROM w_user_trade wut WHERE wut.activity_type=1 AND wut.group_id=?)";
		List<Object> params = new ArrayList<Object>();
		params.add(groupId);
		BigInteger count = (BigInteger) nativeQueryOne(sql, params);
		String sqlAll = "SELECT COUNT(*) FROM w_user_trade wut WHERE wut.activity_type=1";
		BigInteger allCount = (BigInteger) nativeQueryOne(sqlAll, null);

		BigDecimal countb = new BigDecimal(String.valueOf(count));
		BigDecimal countAllb = new BigDecimal(String.valueOf(allCount));

		double activity = BigDecimalUtils.round(countb.divide(countAllb, 2)
				.doubleValue(), 2);

		return activity;
	}

	@Override
	public void adjustmentTransfer() {

		List<UserTrade> userTrades = this.getEntityDao()
				.findByTypeAndStatusAndFeeType((short) 0, (short) 0, (short) 0);
		// 子账户状态需要修改
		HundsunJres hundsunJres = null;
		try {
			hundsunJres = HundsunJres.getInstance();
		} catch (T2SDKException e) {
			log.error(e.getMessage());
			throw new UserTradeConcurrentException(
					"com.tzdr.business.server.connection.timeout", null);
		}
		String userToken = hundsunJres.Login();

		if (userTrades == null || userTrades.size() <= 0) {
			return;
		}
		for (UserTrade userTrade : userTrades) {
			// 1)结算金额
			double assetTotalValue = 0D;
			if (userTrade.getStatus() != 0) {
				throw new UserTradeConcurrentException(
						"com.tzdr.business.not.end.repeat", null);
			}

			// 获取母账户编号和组合编号
			String parentAccountNo = userTrade.getParentAccountNo();
			String combineId = userTrade.getCombineId();

			// 1.2 获取总资产(组合资产查询)
			List<StockCurrent> stockCurrentList = hundsunJres
					.funcAmStockCurrentQry(userToken, parentAccountNo,
							combineId);

			if (!CollectionUtils.isEmpty(stockCurrentList)) {
				StockCurrent stockCurrent = stockCurrentList.get(0);
				// 持仓查询
				// 如果有股票资产者为持仓
				if (stockCurrent != null) {
					// 结算总资产
					assetTotalValue = BigDecimalUtils.addRound(
							stockCurrent.getCurrentCash(),
							stockCurrent.getMarketValue());
					if (assetTotalValue == 0) {
						boolean flag = transferMoneyUserToken(parentAccountNo,
								combineInfoService
										.getHundSunCombineId(userTrade
												.getUnitNumber()), combineId,
								userTrade.getTotalLeverMoney(), "配资中资金划转",
								userToken);
						if (flag) {
							userTrade
									.setStatus(TypeConvert.ADJUST_MENT_SUCCESSFUL);
							this.userTradeDao.update(userTrade);
							sendAdjustmentUserTrade(userTrade);

						}
					}
				}

			}

		}

	}

	/**
	 * 配资中划账短信
	 * 
	 * @param userTrade
	 *            UserTrade
	 */
	private void sendAdjustmentUserTrade(UserTrade userTrade) {
		try {
			Thread.sleep(1000);
			WUser wuser = userTrade.getWuser();
			if (wuser != null && wuser.getMobile() != null
					&& !"".equals(wuser.getMobile())) {
				// 发送短信
				Map<String, String> map = Maps.newHashMap();
				map.put("group", userTrade.getGroupId());
				map.put("starttime", Dates.format(
						Dates.parseLong2Date(userTrade.getStarttime()),
						Dates.CHINESE_DATE_FORMAT_LINE));
				new SMSPgbSenderThread(userTrade.getWuser().getMobile(),
						"ihuyi.trade.ok.code.template", map).start();
			}

		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}

	@Override
	public int findUserTradeByUidId(String uid) {
		String sql = "SELECT COUNT(*) FROM w_user_trade wut WHERE (wut.`status`=1 or wut.`status`=0) AND wut.type=0 AND wut.uid=?";
		List<Object> params = new ArrayList<Object>();
		params.add(uid);
		BigInteger count = (BigInteger) nativeQueryOne(sql, params);
		return count.intValue();
	};

	@Override
	public void thawNextUserTrade() {
		List<UserTrade> list = findUserTradeStartNextToday();

		// 子账户状态需要修改
		HundsunJres hundsunJres = null;
		try {
			hundsunJres = HundsunJres.getInstance();
		} catch (T2SDKException e) {
			log.error(e.getMessage());
			throw new UserTradeConcurrentException(
					"com.tzdr.business.server.connection.timeout", null);
		}

		String userToken = hundsunJres.Login();
		for (UserTrade userTrade : list) {
			try {
				// 走线下[账号、异常类型、异常信息]
				Boolean flag = hundsunJres.funcAmChangeOperatorInfo(userToken,
						new Long(userTrade.getAccount()),
						TypeConvert.USER_THAW_STATE, null, 180L, null, null,
						null);
				if (!flag) {
					FreezeFailInfo freezeFailInfo = new FreezeFailInfo();
					freezeFailInfo.setAccount(userTrade.getAccount());
					freezeFailInfo.setTypeInfo("解冻失败");
					freezeFailInfo.setMessageText("调用恒生接口失败");
					freezeFailInfoService.save(freezeFailInfo);
				}
				// 暂停
				Thread.sleep(2000);
			} catch (Exception e) {
				e.printStackTrace();
				FreezeFailInfo freezeFailInfo = new FreezeFailInfo();
				freezeFailInfo.setAccount(userTrade.getAccount());
				freezeFailInfo.setTypeInfo("解冻失败");
				freezeFailInfo.setMessageText("调用恒生接口通讯异常");
				freezeFailInfoService.save(freezeFailInfo);
			}

		}
		hundsunJres.Logout(userToken);

	}

	@Override
	public Double getTradeMoneyByUserId(String userId, String date) {

		WUser user = wUserService.get(userId);
		List<Object> params = new ArrayList<Object>();
		long dateLong = Dates.getAssignDateLong(date,
				Dates.CHINESE_DATE_FORMAT_LINE, 24 * 60 * 60); // 获取前一天
		if (user != null) {
			params.add(dateLong);
			params.add(user.getId());
			this.getAgencyUserId(user, params);
		}
		Double totalMoney = 0.00;
		if (params != null && !params.isEmpty()) {
			StringBuffer sql = new StringBuffer(
					"SELECT  sum(t.money) totalMoney FROM w_user_trade t,w_user_fund f ");
			sql.append(" WHERE f.type=12 AND ?=UNIX_TIMESTAMP(DATE_FORMAT(FROM_UNIXTIME(f.uptime, '%Y-%m-%d'),'%Y-%m-%d')) ");
			sql.append(" AND t.uid IN " + getQueryInSql(params.size() - 1)
					+ " AND t.group_id=f.lid ");
			totalMoney = (Double) nativeQueryOne(sql.toString(), params);
		}
		return totalMoney;
	}

	/**
	 * 递归获取代理用户编号
	 * 
	 * @param user
	 * @param userIds
	 */
	private void getAgencyUserId(WUser user, List<Object> userIds) {
		if (user != null) {
			List<WUser> childs = user.getChilds();
			if (childs != null && childs.size() > 0) {
				for (WUser childUser : childs) {
					userIds.add(childUser.getId());
					if (childUser.getChilds() != null
							&& childUser.getChilds().size() > 0) {
						this.getAgencyUserId(childUser, userIds);
					}
				}
			}
		}
	}

	/**
	 * 获取系统中 所有母账户下的 组合资产值
	 * 
	 * @return
	 */
	@Override
	public List<Combostock> getAllCombostocks() {
		List<ParentAccount> parentAccounts = parentAccountService
				.findByAccountGenre(0);
		if (CollectionUtils.isEmpty(parentAccounts)) {
			return null;
		}

		List<Combostock> list = getAllCombostocks(parentAccounts);
		// 如果获取的组合资产信息为空 ，进行容错2次 拉取
		if (CollectionUtils.isEmpty(list)) {
			return getAllCombostocks(parentAccounts);
		}
		return list;
	}

	/**
	 * 获取所有单元资产
	 * 
	 * @param parentAccounts
	 * @return
	 */
	private List<Combostock> getAllCombostocks(
			List<ParentAccount> parentAccounts) {
		List<Combostock> list = Lists.newArrayList();
		try {
			HundsunJres hundsunJres = HundsunJres.getInstance();
			String userToken = hundsunJres.Login();
			for (ParentAccount parentAccount : parentAccounts) {
				List<Combostock> combostocks = hundsunJres.funcAmCombostockQry(
						userToken, parentAccount.getAccountNo(), null, null,
						null);
				if (CollectionUtils.isEmpty(combostocks)
						|| StringUtil.isBlank(combostocks.get(0)
								.getFundAccount())) {
					continue;
				}

				list.addAll(combostocks);
				Thread.sleep(1000);
			}
			hundsunJres.Logout(userToken);
		} catch (Exception e) {
			log.error(this.getClass().getName() + e.getMessage());
			// 发送异常邮件
			EmailExceptionHandler.getInstance().HandleException(e,
					"获取系统当天持仓信息失败。",
					this.getClass().getName() + " : method-getAllCombostocks");
		}
		return list;
	}

	@Override
	public List<UserTrade> findUserTradeStartNextToday() {
		// 下一个交易日
		String nextDay = tradeDayService.getNextTradeDay();
		String sql = " select account as account  from w_user_trade w where from_unixtime(w.starttime,'%Y%m%d')=? and w.trade_start=1 and w.status!=2 and  w.fee_type < 2";
		List<Object> params = Lists.newArrayList();
		params.add(nextDay);
		List<UserTrade> list = nativeQuery(sql, params, UserTrade.class);
		return list;
	}

	/**
	 * web 申请配资保存相应信息 包括：方案信息、扣费信息、抵扣券使用情况等
	 * 
	 * @param userTrade
	 * @param wuser
	 * @param volumeDetailId
	 */
	private void saveTradeInfo(UserTrade userTrade, WUser wuser,
			String volumeDetailId) {
		// 0.首次利息计算
		double firstFee = BigDecimalUtils.round(userTrade.getFeeMonth()
				* userTrade.getNaturalDays(), 2);
		// 首次利息
		userTrade.setApr(firstFee);

		// 4.w_user用户基本信息表中修改可用余额,资金总额.

		// 扣配资后
		double afterMoney = BigDecimalUtils.sub(wuser.getAvlBal(),
				userTrade.getLeverMoney());
		// 扣利息后
		double avlBal = BigDecimalUtils.sub(wuser.getAvlBal(),
				userTrade.getLeverMoney() + firstFee);

		double avlBaltemp = avlBal;

		// 扣第一天管理费后
		double manageBal = BigDecimalUtils.sub(wuser.getAvlBal(),
				userTrade.getLeverMoney() + firstFee + userTrade.getFeeDay());

		double manageBalTemp = manageBal;

		double volumeFee = 0.0;
		// 有抵扣券
		if (!StringUtils.isBlank(volumeDetailId)) {
			VolumeDetail volumeDetail = volumeDetailService.get(volumeDetailId);
			// 没有要抛异常
			if (ObjectUtil.equals(volumeDetail, null)) {
				throw new UserTradeException("no.avi.volumeDetail", null);
			}
			double cardMoney = volumeDetail.getVolumeDeductible().getMoney();
			if (BigDecimalUtils.sub(cardMoney, firstFee) >= 0) {
				volumeFee = firstFee;
			} else {
				volumeFee = cardMoney;
			}
			volumeDetail.setRealPayAmount(volumeFee);
			volumeDetail.setUseState(VolumeDetail.USE_STATE_USED);
			volumeDetail.setUseDateValue(Dates.getCurrentLongDate());
			volumeDetail.setRemark("方案编号：" + userTrade.getProgramNo());
			volumeDetailService.update(volumeDetail);
			avlBal = BigDecimalUtils.addRound(avlBal, volumeFee);
			manageBal = BigDecimalUtils.addRound(manageBal, volumeFee);
		}
		String currentDay = Dates.format(new Date(),
				Dates.CHINESE_DATE_FORMAT_LONG);
		//计算交易日
		String tradeDay = tradeDayService.getTradeDay();
		boolean isTradeDay = Boolean.FALSE;
		if (tradeDay.equals(currentDay)) {
			isTradeDay = Boolean.TRUE;
		}
		// 参加8800活动
		//userTrade.setActivityType(UserTrade.ActivityType.NO);
		if (isActity_6600(wuser)) {
			userTrade.setActivityType(UserTrade.ActivityType.ACTIVITY_6600);
		}
		// 先扣费
		// 参加8800活动
		if (isActity_6600(wuser)) {
			wuser.setUserType(WUser.UserType.TRADE);
			if (afterMoney >= 0) {
				wuser.setAvlBal(BigDecimalUtils.round(afterMoney, 2));
				wUserService.updateUser(wuser);
			} else {
				throw new UserTradeException("no.cash.accout", null);
			}
		} else {
			wuser.setUserType(WUser.UserType.TRADE);
			if (userTrade.getTradeStart() == 0  && isTradeDay) {
				if (manageBal >= 0) {
					wuser.setAvlBal(BigDecimalUtils.round(manageBal, 2));
					wUserService.updateUser(wuser);
				} else {
					throw new UserTradeException("no.cash.accout", null);
				}
			} else {
				if (avlBal >= 0) {
					wuser.setAvlBal(BigDecimalUtils.round(avlBal, 2));
					wUserService.updateUser(wuser);
				} else {
					throw new UserTradeException("no.cash.accout", null);
				}
			}
		}

		save(userTrade);

		// 6.生成一条w_user_fund 用户资金记录表 记录配资记录.还有一条利息记录
		// (1)配资记录
		UserFund userFund = new UserFund();
		userFund.setUid(wuser.getId());
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
		// 不参加6600活动
		if (userTrade.getActivityType() != UserTrade.ActivityType.ACTIVITY_6600) {
			// (2)利息记录
			UserFund userFundLx = new UserFund();
			userFundLx.setUid(wuser.getId());
			userFundLx.setNo(userTrade.getId());
			userFundLx.setLid(userTrade.getGroupId());
			userFundLx.setRid(userTrade.getProgramNo());
			// 类型为利息费
			userFundLx.setType(11);
			userFundLx.setMoney(-firstFee);
			userFundLx.setAmount(BigDecimalUtils.round(avlBaltemp, 2));
			userFundLx.setPayStatus((short) 1);
			userFundLx.setAddtime(Dates.getCurrentLongDate() + 1);
			userFundLx.setUptime(Dates.getCurrentLongDate() + 1);
			userFundLx.setRemark("支付利息" + firstFee + "元");
			if(userFundLx.getMoney()!=0){
				userFundService.save(userFundLx);
			}
			
			
			//如果立即生效必须是交易日才生效
			if (userTrade.getTradeStart() == 0 && isTradeDay) {
				// (3)当天管理费记录
				UserFund userFundManage = new UserFund();
				userFundManage.setUid(wuser.getId());
				userFundManage.setNo(userTrade.getId());
				userFundManage.setLid(userTrade.getGroupId());
				userFundManage.setRid(userTrade.getProgramNo());
				// 类型为利息费
				userFundManage.setType(12);
				userFundManage.setMoney(-userTrade.getFeeDay());
				userFundManage.setAmount(BigDecimalUtils
						.round(manageBalTemp, 2));
				userFundManage.setPayStatus((short) 1);
				userFundManage.setAddtime(Dates.getCurrentLongDate() + 2);
				userFundManage.setUptime(Dates.getCurrentLongDate() + 2);
				userFundManage.setRemark("支付管理费" + userTrade.getFeeDay() + "元");
				if(userFundManage.getMoney()!=0)
					userFundService.save(userFundManage);
			}
		}

		if (!StringUtils.isBlank(volumeDetailId)) { // 抵扣券处理
			UserFund userFundVolume = new UserFund();
			userFundVolume.setUid(wuser.getId());
			userFundVolume.setNo(userTrade.getId());
			userFundVolume.setLid(userTrade.getGroupId());
			userFundVolume.setRid(userTrade.getProgramNo());
			// 24 抵扣劵收入
			userFundVolume.setType(24);
			userFundVolume.setMoney(volumeFee);
			userFundVolume.setAmount(BigDecimalUtils.round(avlBal, 2));
			if (userTrade.getTradeStart() == 0) {
				userFundVolume.setAmount(BigDecimalUtils.round(manageBal, 2));
			}
			userFundVolume.setPayStatus((short) 1);
			userFundVolume.setAddtime(Dates.getCurrentLongDate() + 3);
			userFundVolume.setUptime(Dates.getCurrentLongDate() + 3);
			userFundVolume.setRemark("抵扣劵收入：" + volumeFee + "元，用于方案："
					+ userTrade.getProgramNo() + "抵扣利息。");
			userFundService.save(userFundVolume);
		}
	}

	/**
	 * 是否开启使用涌金版或同花顺 手工操作
	 * 
	 * @return
	 */
	private ParentAccount isCanUseWellGold(ParentAccount parentAccount) {
		if (ObjectUtil.equals(parentAccount, null)) {
			String isStart = dataMapService.getStartTierce();
			if (StringUtil.equals(DataDicKeyConstants.START_VALUE, isStart)) {
				return null;
			}
			
			isStart = dataMapService.getStartWellGold();
			if (StringUtil.equals(DataDicKeyConstants.START_VALUE, isStart)) {
				return null;
			}
			// 可能要发邮件
			throw new UserTradeException("no.avi.accout", null);
		} else {
			return parentAccount;
		}
	}

	@Override
	public List<UserTradeVo> queryUserTradeVoByUidAndStatus(String uid,
			short status, short status2) {
		StringBuffer sql = new StringBuffer(
				"SELECT t.uid,t.group_id AS groupId,t.account AS account,t.`password` AS password, ");
		sql.append(" t.hs_belong_broker AS hsBelongBroker,sum(t.total_lever_money) AS totalAccountLeverMoney, ");
		sql.append(" sum(t.money) AS totalMoney,sum(t.warning) AS totalWarning,sum(t.`open`) AS totalOpen, ");
		sql.append(" sum(t.lever_money) AS totalLeverMoney,sum(t.append_lever_money) AS totalAppendLeverMoney, ");
		sql.append(" min(t.starttime) AS starttime,t.`status` AS status,t.parent_account_no AS parentAccountNo, ");
		sql.append(" t.asset_id AS assetId,t.fee_type AS feeType,h.audit_status AS auditStatus, ");
		sql.append(" h.audit_end_status AS auditEndStatus,sum(t.accrual) as totalAccrual, ac.insurance_no insuranceNo ");
		sql.append(" FROM w_user_trade t LEFT JOIN w_hand_trade h ON t.id=h.trade_id ");
		sql.append(" LEFT JOIN w_account ac ON ac.id = t.account_id ");
		sql.append(" WHERE t.uid=? AND (t.`status`=? OR t.`status`=?) GROUP BY t.group_id ORDER BY t.starttime DESC");

		List<Object> params = Lists.newArrayList();
		params.add(uid);
		params.add(status);
		params.add(status2);

		return this.getEntityDao().queryBySql(sql.toString(),
				UserTradeVo.class, null, params.toArray());

	}

	@Override
	public UserTradeVo getUserTradeVoByGroupId(String uid, String groupId) {

		StringBuffer sql = new StringBuffer(
				"SELECT w.fee_type AS feeType,w.`status` AS `status`,h.audit_status AS auditStatus,h.audit_end_status AS auditEndStatus ");
		sql.append(" FROM w_user_trade w LEFT JOIN w_hand_trade h ON w.id=h.trade_id ");
		sql.append(" where w.uid=? and w.group_id=? GROUP BY w.group_id  ORDER BY starttime DESC ");

		List<Object> params = Lists.newArrayList();
		params.add(uid);
		params.add(groupId);

		List<UserTradeVo> userTradeVoList = this.getEntityDao().queryBySql(
				sql.toString(), UserTradeVo.class, null, params.toArray());
		UserTradeVo UserTradeVo = null;
		if (userTradeVoList != null && !userTradeVoList.isEmpty()) {
			UserTradeVo = userTradeVoList.get(0);
		}

		return UserTradeVo;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void endOfWellGoldProgram(String groupId, Double assetTotalValue) {
		lock.lock();
		try {
			// 校验
			if (groupId == null || "".equals(groupId)) {
				throw new UserTradeConcurrentException(
						"com.tzdr.business.agent.error",
						new String[] { "组合ID不能为空执行失败!" });
			}
			if (assetTotalValue == null || assetTotalValue < 0) {
				throw new UserTradeConcurrentException(
						"com.tzdr.business.agent.error",
						new String[] { "结算金额不能为空或小于0!" });
			}

			List<UserTrade> userTrades = this
					.findByGroupIdOrderByAddtimeAsc(groupId);
			if (userTrades == null || userTrades.size() <= 0) {
				throw new UserTradeConcurrentException(
						"com.tzdr.business.server.connection.timeout", null);
			}

			UserTrade userTrade = userTrades.get(0);

			if (userTrade.getStatus() == 2) {
				throw new UserTradeConcurrentException(
						"com.tzdr.business.not.end.repeat", null);
			}

			// 1)结算金额
			userTrade.setFinishedMoney(assetTotalValue);
			// 获取groupId所属配资金额，保证金合计
			EndMoneyVo endMoneyVo = this.endOfProgrameSum(groupId);
			// 2)盈亏金额计算｛结算金额 - （配资金额+保证金+追加保证金）｝ = 盈亏

			double withCapitalLevel = BigDecimalUtils.add2(
					endMoneyVo.getMoney(), endMoneyVo.getLeverMoney());
			Double capitalAppend = BigDecimalUtils.add2(withCapitalLevel,
					endMoneyVo.getAppendLeverMoney());
			Double accrualValue = BigDecimalUtils.sub(assetTotalValue,
					capitalAppend);
			userTrade.setAccrual(accrualValue);

			// 当：【 结算金额-配资金额 < 0】算式结果小于0 时为"爆仓"的情况
			Double arrearsMoney = BigDecimalUtils.sub(assetTotalValue,
					endMoneyVo.getMoney());
			BigDecimal arrearsMoneyBig = new BigDecimal(arrearsMoney.toString());

			// 8800活动【 结算金额-配资金额-保证金 < 0】算式结果小于0 时为"爆仓"的情况
			if (userTrade.getActivityType() == UserTrade.ActivityType.ACTIVITY_6600) {
				arrearsMoney = BigDecimalUtils.sub(arrearsMoney,
						endMoneyVo.getLeverMoney());
				arrearsMoneyBig = new BigDecimal(arrearsMoney.toString());
			}

			// 3)撤回配置资金，并记入撤回流水()[结算金额 - （配资金额 ） = 撤回配置资金]
			Double revocationMoney = BigDecimalUtils.sub(assetTotalValue,
					endMoneyVo.getMoney());

			WUser argWuser = userTrade.getWuser();
			WUser wuser = this.wUserService.get(argWuser.getId());

			// 8800活动[结算金额 - （配资金额 ）8000-（保证金）800 = 撤回配置资金]
			if (userTrade.getActivityType() == UserTrade.ActivityType.ACTIVITY_6600) {
				revocationMoney = BigDecimalUtils.sub(revocationMoney,
						endMoneyVo.getLeverMoney());
			}
			
			// 股票合买【结算金额 - 合买者出资 - （结算金额 - 总操盘资金）* 合买者分成系数  = 撤回配置资金】
			if(userTrade.getActivityType() == UserTrade.ActivityType.TOGETHER_TRADE && accrualValue > 0) {
				Map<String, Object> togetherTrade = this.togetherTradeService.getByGroupId(groupId);
				Object profitRation = togetherTrade.get("profit_ratio");
				// 合买者盈利=（结算金额 - 总操盘资金）* 合买者分成系数
				Double togetherAccrualValue = BigDecimalUtils.mulRound(accrualValue, Double.parseDouble(profitRation.toString()),2);
				// 当方案盈利时，计入合买者盈利，重新计算撤回配置资金
				if(togetherAccrualValue > 0) {
					this.togetherTradeService.updateByGroupId(groupId, togetherAccrualValue);
					
					revocationMoney = BigDecimalUtils.truncation(BigDecimalUtils.sub(revocationMoney, togetherAccrualValue), 2);
				}
			}

			// 用户可用余额
			// 未爆仓
			// arrearsMoneyBig
			if (arrearsMoneyBig.compareTo(new BigDecimal("0")) >= 0) {
				// arrearsMoney >= 0
				UserFund revocationRecord = new UserFund();
				revocationRecord.setMoney(revocationMoney);
				revocationRecord
						.setType(TypeConvert.USER_FUND_C_TYPE_REVOCATION);
				revocationRecord.setPayStatus(TypeConvert.PAID);
				revocationRecord.setRemark(TypeConvert.payRemark("配资撤回",
						revocationRecord.getMoney()));
				// 3.2记入撤回流水
				revocationRecord.setUid(wuser.getId());
				revocationRecord.setLid(groupId);
				this.userFundService.arrearsProcess(revocationRecord);
				// this.userFundService.rechargeOperation(revocationRecord,
				// TypeConvert.TAKE_DEPOSIT_TYPE_INSTORE);
			} 
			else {

				// 6600活动亏损
				if (userTrade.getActivityType() == UserTrade.ActivityType.ACTIVITY_6600) {

					UserFund arrearsRecord = new UserFund();
					arrearsRecord.setMoney(arrearsMoney);
					arrearsRecord.setType(TypeConvert.USER_FUND_C_TYPE_ARREARS);
					arrearsRecord.setPayStatus(TypeConvert.PAID);
					arrearsRecord.setLid(groupId);
					arrearsRecord.setAmount(BigDecimalUtils.addRound(
							wuser.getAvlBal(), arrearsMoney));
					arrearsRecord.setAddtime(Dates.getCurrentLongDate());
					arrearsRecord.setUptime(Dates.getCurrentLongDate());
					arrearsRecord.setRemark(TypeConvert.payRemark("配资欠费",
							arrearsRecord.getMoney()));
					arrearsRecord.setUid(userTrade.getWuser().getId());
					userFundService.save(arrearsRecord);

					UserFund record = new UserFund();
					record.setMoney(-arrearsMoney);
					record.setType(21);
					record.setPayStatus(TypeConvert.PAID);
					record.setLid(groupId);
					record.setAmount(wuser.getAvlBal());
					record.setAddtime(Dates.getCurrentLongDate() + 1);
					record.setUptime(Dates.getCurrentLongDate() + 1);
					record.setRemark(TypeConvert.payRemark("6600 活动方案【" + userTrade.getProgramNo() + 
							"】补偿收入",
							record.getMoney()));
					record.setUid(userTrade.getWuser().getId());
					userFundService.save(record);
					wUserService.updateUser(wuser);
				} 
				else {
					// 爆仓
					UserFund arrearsRecord = new UserFund();
					arrearsRecord.setMoney(arrearsMoney);
					arrearsRecord.setType(TypeConvert.USER_FUND_C_TYPE_ARREARS);
					arrearsRecord.setPayStatus(TypeConvert.UN_PAID);
					arrearsRecord.setLid(groupId);
					arrearsRecord.setAddtime(Dates.getCurrentLongDate());
					arrearsRecord.setRemark(TypeConvert.payRemark("配资欠费",
							arrearsRecord.getMoney()));
					arrearsRecord.setTypeStatus(0);
					this.wUserService.warehouseExplosion(wuser.getId(),
							arrearsRecord);
				}
			}

			// 4)方案终结合计，计费（管理费、利息费、累计配资、累计支出保证金、累计利息、累计盈亏）删除记录？？？
			this.userFundService.deleteArrearsByLid(groupId);
			List<BillingFeeVo> billFees = this.userFundService
					.totalMoneyByGroupId(groupId);
			// 1:充值,2:提现,3:人工充值,4:人工扣款,5:投标冻结,6:投标成功,10:配资支出,11:利息支出
			// ,12：扣取管理费（新增）

			// 配资金额合计
			Double capitalAmount = endMoneyVo.getMoney();
			capitalAmount = BigDecimalUtils.round2(capitalAmount, 2);
			wuser.setTotalLending(BigDecimalUtils.addRound(
					wuser.getTotalLending(), Math.abs(capitalAmount)));
			wuser.setTotalLending(Math.abs(wuser.getTotalLending()));

			// 利息支出合计
			BigDecimal lxAmount = getTotalFee(billFees, 11);
			wuser.setTotalInterestMo(BigDecimalUtils.addRound(
					wuser.getTotalInterestMo(),
					Math.abs(lxAmount.doubleValue())));
			wuser.setTotalInterestMo(Math.abs(wuser.getTotalInterestMo()));
			// 扣取管理费合计
			BigDecimal managerAmount = getTotalFee(billFees, 12);
			wuser.setTotalManagerMo(BigDecimalUtils.addRound(
					wuser.getTotalManagerMo(),
					Math.abs(managerAmount.doubleValue())));
			wuser.setTotalManagerMo(Math.abs(wuser.getTotalManagerMo()));

			// 累计保证金
			BigDecimal totalLevelMoney = totalLevelMoneyByGroupId(groupId);
			wuser.setTotalDeposit(BigDecimalUtils.addRound(
					wuser.getTotalDeposit(), totalLevelMoney.doubleValue()));
			wuser.setTotalDeposit(Math.abs(wuser.getTotalDeposit()));
			// 5)扣费计算(不结算)

			// 6)划账操作
			// 与恒生账户同步
			// 6.1本地账户与恒生母账户金额同步 [结算金额 = 母账户金额]

			for (UserTrade trade : userTrades) {
				trade.setStatus(TypeConvert.ACCOUNT_STATUS_ENDED);
				trade.setEndtime(TypeConvert.dbDefaultDate());
				trade.setTransferState(TypeConvert.TRANSFER_STATE_SUCCESSFUL);
				// 账户冻结
				this.update(trade);
				Account account = accountService.get(trade.getAccountId());
				if (account != null) {
					account.setStatus(TypeConvert.ACCOUNT_STATUS_ENDED);
					account.setEndtime(Dates.getCurrentLongDate());
					accountService.updateAccount(account);
				}
				List<HandTrade> handTrades = this.handTradeDao
						.findByTradeId(trade.getId());
				if (handTrades != null) {
					for (HandTrade ht : handTrades) {
						ht.setAuditEndStatus(TypeConvert.PLAN_PASS);
						User currentUser = authService.getCurrentUser();
						ht.setEndAuditUserId(currentUser.getId());
						ht.setEndAuditUserName(currentUser.getRealname());
						ht.setEndAuditTime(TypeConvert.dbDefaultDate());
						this.handTradeDao.update(ht);
					}
				}

			}
			// 审核成功，股票合买下发短信
			if(userTrade.getActivityType() == UserTrade.ActivityType.TOGETHER_TRADE) {
				// 区分股票合买短信模版
				Map<String, String> map = new HashMap<String, String>();
				map.put("group", groupId);
				map.put("totalInvest", BigDecimalUtils.truncation(BigDecimalUtils.add2(endMoneyVo.getLeverMoney(), endMoneyVo.getAppendLeverMoney()), 2) + ""); // 操盘者总出资=保证金+累计最佳保证金
				map.put("accrualValue", BigDecimalUtils.truncation(accrualValue, 2) + ""); // 投资盈亏
				map.put("revocationMoney", BigDecimalUtils.truncation(revocationMoney, 2) + ""); // 撤回配置资金
				new SMSSenderThread(wuser.getMobile(),"together.ihuyi.end.trade.ok.code.template", map).start();
				
				// 更新合买记录时间
				this.togetherUserListService.updateByGroupId(groupId);
			} else {
				// A股审核发送短信
				Map<String, String> map = new HashMap<String, String>();
				map.put("group", groupId);
				map.put("totalInvest", BigDecimalUtils.truncation(BigDecimalUtils.add2(endMoneyVo.getLeverMoney(), endMoneyVo.getAppendLeverMoney()), 2) + ""); // 操盘者总出资=保证金+累计最佳保证金
				map.put("accrualValue", BigDecimalUtils.truncation(accrualValue, 2) + ""); // 投资盈亏
				map.put("revocationMoney", BigDecimalUtils.truncation(revocationMoney, 2) + ""); // 撤回配置资金
				new SMSPgbSenderThread(wuser.getMobile(),"ag.ihuyi.end.trade.ok.code.template", map).start();
			}
		} finally {
			lock.unlock();
		}
	}

	@Autowired
	private HandTradeDao handTradeDao;

	private Lock lock = new ReentrantLock();

	@Override
	public PageInfo<MonitorSchemeVo> queryMonitorData(
			PageInfo<MonitorSchemeVo> dataPage, ConnditionVo connVo,
			short feeType,int handType) {
		List<Object> params = Lists.newArrayList();
		String sql = "SELECT trade.activity_type activityType, trade.account_id accountId, wuser.id uid, acc.account_name accountName,\r"
				+ " verified.tname uname, wuser.mobile, trade.combine_id combineId, trade.account, \r"
				+ "trade.group_id groupId, trade.`password`, sum(trade.money) totalLending,SUM(total_lever_money) as totalOperateMoney,\r"
				+ " sum(trade.lever_money) totalLeverMoney, sum(trade.append_lever_money) totalAppendLeverMoney,\r"
				+ " sum(trade.warning) warning, sum(trade.`open`) `open`, trade.starttime, \r"
				+ "trade.endtime, trade.`status`, trade.buy_status buyStatus, trade.sell_status sellStatus, \r"
				+ "trade.hs_belong_broker hsBelongBroker, trade.unit_number unitNumber, \r"
				+ "trade.parent_account_no parentAccountNo, trade.asset_id assetId, "
				+ "IF(DATEDIFF(CURRENT_DATE(),FROM_UNIXTIME(trade.starttime)) < 0, "
				+				"0, DATEDIFF(CURRENT_DATE()+1, FROM_UNIXTIME(trade.starttime))) useDays  "
				+ "FROM w_user_trade trade,w_hand_trade ht,\r"
				+ " w_user wuser, w_user_verified verified, w_account acc WHERE trade.`status` = 1 AND ht.trade_id= trade.id and ht.type = ?\r"
				+ " and wuser.id = trade.uid AND wuser.id = verified.uid AND trade.account_id = acc.id and trade.fee_type=? \r";

		params.add(handType);
		params.add(feeType);
		if(connVo!=null){
			Object accountObj=connVo.getValue("account");
			if(TypeConvert.objToStrIsNotNull(accountObj)!=null){
				sql += " and trade.account LIKE ?";
				params.add("%"+String.valueOf(accountObj)+"%");
			}
			Object accountNameObj=connVo.getValue("accountName");
			if(TypeConvert.objToStrIsNotNull(accountNameObj)!=null){
				sql += " and acc.account_name LIKE ?";
				params.add("%"+String.valueOf(accountNameObj)+"%");
			}
			Object parentAccountNoObj=connVo.getValue("parentAccountNo");
			if(TypeConvert.objToStrIsNotNull(parentAccountNoObj)!=null){
				sql += " and trade.parent_account_no LIKE ?";
				params.add("%"+String.valueOf(parentAccountNoObj)+"%");
			}
			
			/**
			 * 增加活动用户过滤条件
			 */
			Object userType = connVo.getValue("userType");
			if(null != TypeConvert.objToStrIsNotNull(userType)){
				sql += " AND wuser.activity_type=?";
				params.add(userType);
			}
			/**
			 * 增加买入状态过滤条件
			 */
			Object buyStatus = connVo.getValue("buyStatus");
			if(null != TypeConvert.objToStrIsNotNull(buyStatus)){
				sql += " AND trade.buy_status=?";
				params.add(buyStatus);
			}
			// 判断方案类型
			Object activityType=connVo.getValue("search_EQ_activityType");
			if(TypeConvert.objToStrIsNotNull(activityType)!=null){
				sql += " and trade.activity_type = ?";
				params.add(String.valueOf(activityType));
			}
			Object mobile = connVo.getValue("mobile");
			if(TypeConvert.objToStrIsNotNull(mobile) != null){
				sql += " and wuser.mobile LIKE ?";
				params.add("%" + String.valueOf(mobile) + "%");
			}
		}
		sql += " GROUP BY  trade.group_id  ORDER BY trade.addtime ASC ";
		String code = authService.getCurrentUser().getOrganization().getCode();
		params.add(code + "%");
		sql = getPermissionSql(sql, code, params, authService.getCurrentUser()
				.getId());
		/**
		 * 使用天数
		 */
		Object useDays = connVo.getValue("useDays");
		if(null != TypeConvert.objToStrIsNotNull(useDays)){
			sql += " AND temp.useDays=?";
			params.add(useDays);
		}

		PageInfo<MonitorSchemeVo> page = getEntityDao().queryPageBySql(
				dataPage, sql, MonitorSchemeVo.class, null, params.toArray());
		return page;
	}

	@Override
	@Transactional
	public void endSolationReview(EndProgramVo vo) throws Exception {
		// 结算金额
		// Double fm = null; //finishedMoney
		String groupId = null;

		if (ObjectUtil.equals(null, vo)
		// || ObjectUtil.equals(null, fm = vo.getFinishedMoney())
				|| ObjectUtil.equals(null, groupId = vo.getGroupId())) {
			throw new DataMapException("business.update.not.found.data", null);
		}

		List<UserTrade> findByGroupId = getEntityDao().findByGroupId(groupId);

		if (CollectionUtils.isEmpty(findByGroupId)) {
			throw new DataMapException("business.update.not.found.data", null);
		}

		for (Iterator<UserTrade> iterator = findByGroupId.iterator(); iterator
				.hasNext();) {
			UserTrade userTrade = iterator.next();
			HandTrade ht = handTradeService.findByTradeId(userTrade.getId());

			if (ObjectUtil.equals(null, ht)) {
				throw new DataMapException("business.update.not.found.data",
						null);
			}
			if (ht.getAuditEndStatus()==0 ||ht.getAuditEndStatus()==1 ){
				throw new UserTradeException("user.trade.already.end",
						null);
			}
			// 结算金额
			// ht.setFinishedMoney(BigDecimalUtils.truncation(fm, 2));
			// 修改为终结 审核状态（-1：默认； 0： 提交审核； 1：审核通过；2：审核不通过；）
			ht.setAuditEndStatus(0);
			// 提交审核时间
			ht.setEndSubTime(Dates.getCurrentLongDate());
			handTradeService.update(ht);
		}
	}

	@Override
	public UserTrade findByProgramNoAndWuser(String programNo, WUser wuser) {
		return this.getEntityDao().findByProgramNoAndWuser(programNo, wuser);
	}

	@Override
	public void cancelAstrictBuy(UserTrade userTrade) {
		try {
			if(userTrade == null || userTrade.getWuser() == null){
				return ;
			}
			
			List<UserFund> userFundList = userFundService.queryUserFunds(userTrade.getWuser().getId(), userTrade.getProgramNo(), new Integer[]{11,12});
			
			//判断是不是(非操盘中、涌金版、欠费【管理费+利息】)，满足条件的直接返回
			if(1 != userTrade.getStatus() || 2 == userTrade.getFeeType() || (userFundList != null && !userFundList.isEmpty())){    
				return;
			}
			
			//判断账户是否在补仓线以上,没有在补仓线上  直接返回
			if (!this.isUpMarginLine(userTrade.getGroupId())){
				return;
			}
			//取消限制买入
			this.changeOperationStatus(new String[]{userTrade.getGroupId()}, 0, "system"); 
		} catch (Exception e) {
			log.error(this.getClass().getName() + e.getMessage());
			// 发送异常邮件
			EmailExceptionHandler.getInstance().HandleException(e,"追加保证金时，取消限制买入失败。",this.getClass().getName()+ " : method-cancelAstrictBuy");
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.tzdr.business.service.userTrade.UserTradeService#queryTradeSummaryData(com.tzdr.common.domain.PageInfo, com.tzdr.domain.vo.UserTradeSummaryVo)
	 */
	@Override
	public PageInfo<UserTradeSummaryVo> queryTradeSummaryData(
			PageInfo<UserTradeSummaryVo> dataPage,
			UserTradeSummaryVo userTradeSummaryVo) {
		    List<Object> params = Lists.newArrayList();
		    String sql="select IFNULL(ts.sday,0)+IFNULL(ts.sdays,0) usedays,IFNULL(ts.totalmoney,0)+IFNULL(ts.leverMoney,0) totalamount,"
		    		+ " DATE_FORMAT(lastaddtime,'%Y-%m-%d %H:%i:%s') as lasttime, ts.* from ( \r";
				sql+="select u.mobile,tra.uid,tra.totalmoney,v.tname,tra.startdays,tra.leverMoney,tra.appendLeverMoney,tra.countProNo, \r";
				sql+="(";
				sql+=" select  FROM_UNIXTIME(t1.addtime) addtime from w_user_trade t1 \r";
				sql+="  where not exists (select 1 from w_user_trade t2 where t2.uid=t1.uid and t2.addtime>t1.addtime) \r";
				sql+=" and t1.uid=tra.uid \r";
				sql+=" ) as lastaddtime , \r";
				sql+="(";
				sql+="SELECT SUM(s.money)  from w_user_fund s where  s.uid=tra.uid and s.type=16 group by s.uid \r";
				sql+=") as profit , \r";
				sql+="(";
				sql+="select sum(TO_DAYS(FROM_UNIXTIME(t.endtime, '%Y%m%d')) - TO_DAYS(FROM_UNIXTIME(t.starttime, '%Y%m%d'))) \r";
				sql+=" days from w_user_trade t where status=2 and t.uid=tra.uid group by t.uid \r";
				sql+=") as sday, \r";
				sql+="(";
				sql+="select sum(TO_DAYS(NOW()) - TO_DAYS(FROM_UNIXTIME(t.starttime, '%Y%m%d'))) sdays \r";
				sql+="from w_user_trade t where status=1 and t.uid=tra.uid group by t.uid \r";
				sql+=") as sdays,\r";
				sql+="(select count(t.group_id) as useProNo from w_user_trade t where status=1 and t.uid=tra.uid group by t.uid ) as useProNo \r";
				sql+="from (select  sum(t.money) totalmoney,SUM(t.lever_money) leverMoney,count(t.program_no) countProNo,t.uid,sum(natural_days) startdays, \r";
				sql+="sum(append_lever_money) appendLeverMoney from w_user_trade t where t.unit_number is not null group by t.uid) \r";
				sql+="tra, w_user u,w_user_verified v where u.id=tra.uid and v.uid=tra.uid and v.uid=u.id \r";
				
				if(StringUtils.isNotBlank(userTradeSummaryVo.getMobile())){
					sql+=" and u.mobile=? ";
					params.add(userTradeSummaryVo.getMobile());
				}
				if(StringUtils.isNotBlank(userTradeSummaryVo.getTname())){
					sql+=" and v.tname=? ";
					params.add(userTradeSummaryVo.getTname());
				}
				sql+=") ts ";
				PageInfo<UserTradeSummaryVo> page= getEntityDao().queryPageBySql(dataPage, sql,
				UserTradeSummaryVo.class,null, params.toArray());
				if(page!=null){
					for(UserTradeSummaryVo vo:page.getPageResults()){
						String uid=vo.getUid();
						long days=0;
						List<UserTradeSummaryCmsVo> list=getTradesByUidAndGroupId(uid);
						for(UserTradeSummaryCmsVo cmsvo:list){
							UserTradeCmsVo cvo = new DozerBeanMapper().map(cmsvo, UserTradeCmsVo.class);
							 
							days+=tradeDayService.getTradeDays(cvo);
						}
						vo.setUsedays(days);
					}
				}
				
				
			    return page;	
	}
	
	private List<UserTradeSummaryCmsVo> getTradesByUidAndGroupId(String uid){
		String sql=" select min(t.starttime) starttime,t.endtime,t.uid,t.`status` from w_user_trade t"
				+ "	 where t.unit_number is not null and t.uid=? group by t.group_id ";
		List<Object> params = Lists.newArrayList();
		params.add(uid);
		List<UserTradeSummaryCmsVo> list = this.getEntityDao().queryBySql(
				sql.toString(), UserTradeSummaryCmsVo.class, null, params.toArray());
		return list;
	}
	
	
	public List<ParentAccountVo> getParentAccountList(int lever,double totalLeverMoney,long needEndTime,boolean isNew) {
		int type = isNew ? 1 : 2;

		StringBuffer sql = new StringBuffer("SELECT wpa.* FROM w_parent_account wpa WHERE "
				+ "wpa.deleted=0 AND wpa.account_genre=0 AND wpa.multiple_start <= ? AND wpa.multiple_end >= ? "
				+ "AND wpa.amount_start <= ? AND wpa.amount_end >= ? "
				+ "AND wpa.allocation_date >= ? AND ( wpa.funds_balance - wpa.sub_funds ) >= ? "
				+ "AND wpa.new_and_old_state IN (?, 3) ORDER BY wpa.account_type,wpa.priority_no");
		List<Object> params = Lists.newArrayList();
		params.add(lever);
		params.add(lever);
		params.add(totalLeverMoney);
		params.add(totalLeverMoney);
		params.add(needEndTime);
		params.add(totalLeverMoney);
		params.add(type);
		@SuppressWarnings("unchecked")
		List<ParentAccountVo> list = this.getEntityDao().queryBySql(
				sql.toString(), ParentAccountVo.class, null, params.toArray());
		return list;
	}

	@Override
	public List<UserTradeVo> queryNewUserTradeVoByWuserAndStatus(String uid,
			short status, short status2) {
		List<UserTradeVo> result = queryUserTradeVoByUidAndStatus(uid, status,
				status2);
		return result;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Double getTradeAccrual(String uid,String groupId) {
		
		if(StringUtil.isBlank(uid) || StringUtil.isBlank(groupId)){
			return null;
		}
		
		StringBuffer sql = new StringBuffer("SELECT t.uid,t.group_id AS groupId,t.account AS account,t.`password` AS password, ");
		sql.append(" t.hs_belong_broker AS hsBelongBroker,sum(t.total_lever_money) AS totalAccountLeverMoney, ");
		sql.append(" sum(t.money) AS totalMoney,sum(t.warning) AS totalWarning,sum(t.`open`) AS totalOpen, ");
		sql.append(" sum(t.lever_money) AS totalLeverMoney,sum(t.append_lever_money) AS totalAppendLeverMoney, ");
		sql.append(" min(t.starttime) AS starttime,t.`status` AS status,t.parent_account_no AS parentAccountNo,");
		sql.append(" t.asset_id AS assetId,t.fee_type AS feeType,sum(t.accrual) as totalAccrual ");
		sql.append(" FROM w_user_trade t ");
		sql.append("  WHERE t.uid=? AND t.group_id=? GROUP BY t.group_id ORDER BY t.starttime DESC");
		
		List<Object> params = Lists.newArrayList();
		params.add(uid);
		params.add(groupId);

		List<UserTradeVo> userTradeVoList = this.getEntityDao().queryBySql(
				sql.toString(), UserTradeVo.class, null, params.toArray());
		UserTradeVo userTradeVo = null;
		if (userTradeVoList != null && !userTradeVoList.isEmpty()) {
			userTradeVo = userTradeVoList.get(0);
		}
		
		//方案盈亏
		Double totalAccrual =  null;
		
		try {
			if (userTradeVo == null) {
				return null;
			}
			if(0 == userTradeVo.getStatus()){
				return 0.00;
			}
			HundsunJres hundsunJres = HundsunJres.getInstance();
			String userToken = hundsunJres.Login();
			// 方案盈亏
			String combineId = combineInfoService
					.getHundSunCombineId(userTradeVo.getAssetId());
			if (StringUtil.isBlank(combineId)) {
				return null;
			}
			List<StockCurrent> stockCurrentList = hundsunJres
					.funcAmStockCurrentQry(userToken,
							userTradeVo.getParentAccountNo(), combineId);
			if (CollectionUtils.isEmpty(stockCurrentList)) {
				return null;
			}

			if (!CollectionUtils.isEmpty(stockCurrentList)) {
				StockCurrent stockCurrent = stockCurrentList.get(0);
				double assetTotalValue = BigDecimalUtils.addRound(
						stockCurrent.getCurrentCash(),
						stockCurrent.getMarketValue());
				userTradeVo.setTotalAccrual(BigDecimalUtils.sub(
								assetTotalValue,
								userTradeVo.getTotalOperateMoney()));
				totalAccrual = BigDecimalUtils.sub(assetTotalValue,userTradeVo.getTotalOperateMoney());
			}
			hundsunJres.Logout(userToken);
		} catch (T2SDKException e) {
			log.error(this.getClass().getName() + e.getMessage());
			throw new UserTradeException("user.trade.query.fail", null);
		}
		
		return totalAccrual;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserTradeVo> queryUserTradeVos(String uid, String date) {

		StringBuffer sql = new StringBuffer("SELECT t.uid,t.group_id AS groupId,t.account AS account,t.`password` AS password, ");
		sql.append(" t.hs_belong_broker AS hsBelongBroker,sum(t.total_lever_money) AS totalAccountLeverMoney, ");
		sql.append(" sum(t.money) AS totalMoney,sum(t.warning) AS totalWarning,sum(t.`open`) AS totalOpen, ");
		sql.append(" sum(t.lever_money) AS totalLeverMoney,sum(t.append_lever_money) AS totalAppendLeverMoney, ");
		sql.append(" min(t.starttime) AS starttime,t.`status` AS status,t.parent_account_no AS parentAccountNo,");
		sql.append(" t.asset_id AS assetId,t.fee_type AS feeType,sum(t.accrual) as totalAccrual ");
		sql.append(" FROM w_user_trade t ");
		sql.append("  WHERE t.uid=? AND ?=from_unixtime(t.addtime,'%Y-%m-%d') GROUP BY t.group_id ORDER BY t.starttime DESC");
		
		List<Object> params = Lists.newArrayList();
		params.add(uid);
		params.add(date);

		List<UserTradeVo> userTradeVoList = this.getEntityDao().queryBySql(
				sql.toString(), UserTradeVo.class, null, params.toArray());
		
		return userTradeVoList;
	}
	
	/**
	 * type 1:补仓短信  2：平仓短信
	 */
	@Override
	public void smsNoticeMarginUser(String[] groupIds,Short noticeStatus,int type) {
		for (String groupId : groupIds) {
			List<UserTrade> userTrades = getEntityDao().findByGroupId(groupId);
			if (CollectionUtils.isEmpty(userTrades)) {
				throw new UserTradeException(
						"user.trade.query.margin.remind.change.notice.fail",
						null);
			}

			WUser wUser = null;
			for (UserTrade userTrade : userTrades) {
				userTrade.setNoticeStatus(noticeStatus);
				update(userTrade);
				if (ObjectUtil.equals(null, wUser)) {
					wUser = userTrade.getWuser();
				}
			}
			
			// 发送通知
			String content = MessageUtils.message("user.trade.below.warning.line.message", groupId);
			if (type==2){
				content=MessageUtils.message("user.trade.below.open.line.message", groupId);
			}
			new SMSPgbSendForContentThread(wUser.getMobile(), content,1000).start();
						
			// 保存通知记录
			noticeRecordService.save(new NoticeRecord(wUser.getId(), groupId,
					"补仓提醒手动触发短信通知用户："
							+ wUser.getUserVerified().getTname()
							+ " "
							+ CacheManager.getDataMapByKey(
									DataDicKeyConstants.CALL_NOTICE_STATUS,
									String.valueOf(noticeStatus)), 2,
					NumberUtils.toInt(String.valueOf(noticeStatus))));
			
		}
	}
	
	
	
	/**
	 * 获取用某天的配资，去除涌金，手工开户失败配资
	 * @param uid
	 * @param date
	 * @return
	 */
	@Override
	public List<UserTradeVo> queryUserDayTrades(String uid, String date) {
		StringBuffer sql = new StringBuffer("SELECT trade.uid, trade.group_id AS groupId, trade.account AS account, trade.`password`, trade.hs_belong_broker AS hsBelongBroker, "
				+ " sum(trade.total_lever_money) AS totalAccountLeverMoney, sum(trade.money) AS totalMoney, "
				+ " sum(trade.warning) AS totalWarning, sum(trade.`open`) AS totalOpen,"
				+ " sum(trade.lever_money) AS totalLeverMoney, sum(trade.append_lever_money) AS totalAppendLeverMoney,"
				+ " min(trade.starttime) AS starttime, trade.`status` AS STATUS, trade.parent_account_no AS parentAccountNo,"
				+ " trade.asset_id AS assetId, trade.fee_type AS feeType, sum(trade.accrual) AS totalAccrual "
				+ " FROM w_user_trade trade WHERE uid = ? AND from_unixtime(trade.addtime, '%Y-%m-%d') = ? and trade.activity_type in(0,1,2) and trade.type=0 "
				+ " AND NOT EXISTS ( SELECT id FROM w_hand_trade hand WHERE hand.trade_id = trade.id AND hand.audit_status = 2 ) "
				+ " GROUP BY trade.group_id ");
		List<Object> params = Lists.newArrayList();
		params.add(uid);
		params.add(date);

		List<UserTradeVo> userTradeVoList = this.getEntityDao().queryBySql(
				sql.toString(), UserTradeVo.class, null, params.toArray());
		return userTradeVoList;
	}
	
	
		
	/**
	 * 涌金版 、同花顺手动 保存配资信息
	 * @param userTrade
	 * @param wuser
	 * @param volumeDetailId
	 * @param feeType
	 * @return
	 */
	private UserTradeTransfer saveHandleTrade(UserTrade userTrade,WUser wuser,String volumeDetailId,
			short feeType,int type){
		// 保存配资信息
		userTrade.setFeeType(feeType);
		saveTradeInfo(userTrade, wuser, volumeDetailId);
		// 保存 配资申请审核信息
		HandTrade handTrade = new HandTrade(userTrade.getId(),type);
		handTrade.setCreateTime(Dates.getCurrentLongDate());
		handTradeService.save(handTrade);
		// 返回配资信息
		UserTradeTransfer userTradeTransfer = new UserTradeTransfer();
		userTradeTransfer.setUserTrade(userTrade);
		return userTradeTransfer;
	}

	@Override
	public int findUserTradesNotBespoke(String uid) {
		String sql = " SELECT COUNT(*) FROM w_user_trade wut WHERE (wut.`status`=1 or wut.`status`=0) AND wut.type=0 AND wut.activity_type in (0,1,2) and  wut.uid=? ";
		List<Object> params = new ArrayList<Object>();
		params.add(uid);
		BigInteger count = (BigInteger) nativeQueryOne(sql, params);
		return count.intValue();
	}

	@Override
	public Double getSumMoneyUserTradesByUidAndStatus(String uid, int status) {
		
		if(StringUtil.isBlank(uid)){
			return null;
		}
		
		String sql = "SELECT SUM(t.money) FROM w_user_trade t WHERE t.uid=? AND t.`status`=? ";
		List<Object> params = new ArrayList<Object>();
		params.add(uid);
		params.add(status);
		
		Double totalMoney = (Double) nativeQueryOne(sql, params);
		
		return totalMoney;
	}

	@Override
	public int getCountUserTradesByUid(String uid) {
		if(StringUtil.isBlank(uid)){
			return 0;
		}
		
		String sql = "SELECT count(0) FROM w_user_trade t WHERE t.uid=? AND t.activity_type in(0,1,2,3) ";
		List<Object> params = new ArrayList<Object>();
		params.add(uid);
		
		BigInteger count = (BigInteger) nativeQueryOne(sql, params);
		return count.intValue();
	}

	@Override
	public UserTrade buildUserTrade(UserTrade userTrade, WUser wuser,
			String volumeDetailId) {
			// 配资中
			userTrade.setStatus((short) 0);
			if (UserTrade.ActivityType.TOGETHER_TRADE==userTrade.getActivityType()){
				String token = RandomCodeUtil.genToken(7);
				userTrade.setProgramNo("HM" + RandomCodeUtil.genToken(7));
				userTrade.setGroupId("HM" + token);
			}else
			{	
				String token = RandomCodeUtil.genToken(6);
				userTrade.setProgramNo("T" + RandomCodeUtil.genToken(7));
				userTrade.setGroupId("T" + token);
			}

			// 保存配资信息
			userTrade.setFeeType(Constant.FeeType.HAND_OPEN_WELL_GOLD);
			saveTradeInfo(userTrade, wuser, volumeDetailId);
			// 保存 配资申请审核信息
			HandTrade handTrade = new HandTrade(userTrade.getId(),Constant.HandTradeType.WELL_GOLD_TYPE);
			handTrade.setCreateTime(Dates.getCurrentLongDate());
			handTradeService.save(handTrade);
			return userTrade;
	}

	@Override
	public boolean isHaveNoTrade(WUser user) {
		List<Integer> activityTypeList = new ArrayList<Integer>();
		activityTypeList.add(0);
		activityTypeList.add(1);
		activityTypeList.add(2);
		activityTypeList.add(3);
		List<UserTrade> list = userTradeDao.findByWuserAndActivityTypeIn(user,activityTypeList);
		if (CollectionUtils.isEmpty(list)) {
			return true;
		}else{
			return false;
		}
	}
	
}

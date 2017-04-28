package com.tzdr.business.service.userTrade.impl;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import com.google.common.collect.Lists;
import com.hundsun.t2sdk.common.util.CollectionUtils;
import com.tzdr.business.cms.service.auth.AuthService;
import com.tzdr.business.service.account.AccountService;
import com.tzdr.business.service.crudeActive.CrudeActiveService;
import com.tzdr.business.service.datamap.DataMapService;
import com.tzdr.business.service.extension.ActivityRewardService;
import com.tzdr.business.service.ftseActive.FtseActiveService;
import com.tzdr.business.service.future.FSimpleCouponService;
import com.tzdr.business.service.futureMatchAccount.FutureMatchAccountService;
import com.tzdr.business.service.pay.UserFundService;
import com.tzdr.business.service.recharge.RechargeListService;
import com.tzdr.business.service.thread.SMSSendForContentThread;
import com.tzdr.business.service.tradeDetail.TradeDetailService;
import com.tzdr.business.service.tradecalendar.TradeDayService;
import com.tzdr.business.service.userTrade.FHandleFtseUserTradeService;
import com.tzdr.business.service.userTrade.FSimpleFtseUserTradeService;
import com.tzdr.business.service.userTrade.FSimpleParitiesService;
import com.tzdr.business.service.userTrade.FinternationFutureAppendLevelMoneyService;
import com.tzdr.business.service.userTrade.HandTradeService;
import com.tzdr.business.service.wuser.WUserService;
import com.tzdr.common.api.ihuyi.SMSSender;
import com.tzdr.common.baseservice.BaseServiceImpl;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.exception.BusinessException;
import com.tzdr.common.utils.ConnditionVo;
import com.tzdr.common.utils.Dates;
import com.tzdr.common.utils.MessageUtils;
import com.tzdr.common.utils.TypeConvert;
import com.tzdr.common.web.support.EasyUiPageInfo;
import com.tzdr.common.web.support.JsonResult;
import com.tzdr.common.web.support.MultiListParam;
import com.tzdr.domain.cms.entity.user.User;
import com.tzdr.domain.constants.Constant;
import com.tzdr.domain.constants.ExtensionConstants;
import com.tzdr.domain.dao.userTrade.FSimpleFtseUserTradeDao;
import com.tzdr.domain.vo.FSimpleFtseUserTradeWebVo;
import com.tzdr.domain.vo.HandUserFundVoNew;
import com.tzdr.domain.vo.ftse.FHandleFtseUserTradeVo;
import com.tzdr.domain.vo.ftse.FHandleFtseUserTradeVo2;
import com.tzdr.domain.vo.ftse.FSimpleFtseManageVo;
import com.tzdr.domain.vo.ftse.FSimpleFtseVo;
import com.tzdr.domain.web.entity.ActivityReward;
import com.tzdr.domain.web.entity.FSimpleFtseUserTrade;
import com.tzdr.domain.web.entity.FSimpleParities;
import com.tzdr.domain.web.entity.FinternationFutureAppendLevelMoney;
import com.tzdr.domain.web.entity.FutureMatchAccount;
import com.tzdr.domain.web.entity.WUser;
import com.tzdr.domain.web.entity.future.FSimpleCoupon;
import com.tzdr.common.api.contact.BusinessTypeEnum;
import jodd.util.StringUtil;

/**
 * <p>
 * </p>
 *
 * @author WangPinQun
 * @version 2.0 2015年9月16日下午14:33:13
 * @see FSimpleFtseUserTradeServiceImpl
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class FSimpleFtseUserTradeServiceImpl extends BaseServiceImpl<FSimpleFtseUserTrade, FSimpleFtseUserTradeDao>
		implements FSimpleFtseUserTradeService {

	private static Logger log = LoggerFactory.getLogger(FSimpleFtseUserTradeServiceImpl.class);

	@Autowired
	private FutureMatchAccountService futureMatchAccountService;

	@Autowired
	private FSimpleFtseUserTradeDao simpleFtseUserTradeDao;
	@Autowired
	private WUserService wUserService;
	@Autowired
	private RechargeListService rechargeListService;
	@Autowired
	private AuthService authService;
	@Autowired
	private TradeDayService tradeDayService;
	@Autowired
	private FSimpleParitiesService simpleParitiesService;
	@Autowired
	private FtseActiveService ftseActiveService;
	@Autowired
	private CrudeActiveService crudeActiveService;
	@Autowired
	private FinternationFutureAppendLevelMoneyService finternationFutureAppendLevelMoneyService;
	@Autowired
	private FSimpleCouponService fSimpleCouponService;
	@Autowired
	private ActivityRewardService activityRewardService;
	@Autowired
	private DataMapService dataMapService;
	@Autowired
	private TradeDetailService tradeDetailService;

	@Override
	public FSimpleFtseUserTrade executePayable(FSimpleFtseUserTrade fSimpleFtseUserTrade, String mobile,
			BigDecimal payable) throws Exception {

		fSimpleFtseUserTrade.setAppTime(TypeConvert.dbDefaultDate());
		this.save(fSimpleFtseUserTrade);
		fSimpleFtseUserTrade.setProgramNo("GT" + fSimpleFtseUserTrade.getId());
		this.update(fSimpleFtseUserTrade);
		// handleFtseUserTradeService.saveHandleFtseUserTrade(fSimpleFtseUserTrade);
		// // 保存富时A50收益报表记录
		rechargeListService.futureHandlerSaveRechargeStateWeb("GT" + fSimpleFtseUserTrade.getId(), mobile,
				payable.toString(), "投资新华富时A50申请（划款）。", TypeConvert.SYS_TYPE_OFFSET_ACCOUNTS, 1);
		return fSimpleFtseUserTrade;
	}

	@Override
	public FSimpleFtseUserTrade executePayable(FSimpleFtseUserTrade fSimpleFtseUserTrade, String mobile,
			BigDecimal payable, String remark, int businessType) throws Exception {

		fSimpleFtseUserTrade.setAppTime(TypeConvert.dbDefaultDate());
		this.save(fSimpleFtseUserTrade);
		fSimpleFtseUserTrade.setProgramNo("GT" + fSimpleFtseUserTrade.getId());
		this.update(fSimpleFtseUserTrade);
		// handleFtseUserTradeService.saveHandleFtseUserTrade(fSimpleFtseUserTrade);
		// // 保存富时A50收益报表记录
		rechargeListService.futureHandlerSaveRechargeStateWeb("GT" + fSimpleFtseUserTrade.getId(), mobile,
				payable.toString(), remark, TypeConvert.SYS_TYPE_OFFSET_ACCOUNTS, businessType);
		// 匹配账号类型
		int type = 0;
		switch (fSimpleFtseUserTrade.getBusinessType()) {
		case 8: // 国际综合
			type = 2;
			break;
		case 20: // 商品综合
			type = 1;
			break;
		case 0: // A50
			type = 3;
			break;
		case 6: // 原油
			type = 4;
			break;
		case 7: // 恒生
			type = 5;
			break;
		case 9:// 小恒指
			type = 6;
			break;
		default:
			break;
		}
		int lever = 0;
		if (type != 1 && type != 2) {
			lever = fSimpleFtseUserTrade.getTranLever();
		}

		String business=BusinessTypeEnum.getBusinessTypeToBusiness(fSimpleFtseUserTrade.getBusinessType());

		FutureMatchAccount futureMatchAccount = futureMatchAccountService.getOne(type, lever,
				fSimpleFtseUserTrade.getTraderTotal().doubleValue());
		/*
		 * if (type == 5 && 2 == fSimpleFtseUserTrade.getSource()){
		 * futureMatchAccount = null; }
		 */
		if (null != futureMatchAccount) {
			// 匹配成功
			fSimpleFtseUserTrade.setTranAccount(futureMatchAccount.getAccount());
			fSimpleFtseUserTrade.setTranPassword(futureMatchAccount.getPassword());
			fSimpleFtseUserTrade.setStateType(4);
			fSimpleFtseUserTrade.setAppStarttime(Dates.getCurrentLongDate());
			fSimpleFtseUserTrade.setUpdateTime(new Date().getTime() / 1000);
			futureMatchAccount.setIsUse(1);
			futureMatchAccount.setTid(fSimpleFtseUserTrade.getId());
			futureMatchAccount.setAssignTime(new Date().getTime() / 1000);
			this.futureMatchAccountService.update(futureMatchAccount);
			WUser wUser = wUserService.get(fSimpleFtseUserTrade.getUid());
			if (type == 1) {
				String content = MessageUtils.message("commodity.future.apply.audit.success",lever, business);
				new SMSSendForContentThread(wUser.getMobile(), content, 2000).start();

			} else {
				String content = MessageUtils.message("commodity.crude.apply.audit.success",lever, business);
				new SMSSendForContentThread(wUser.getMobile(), content, 2000).start();
			}
		} else {
			// 审核中
			fSimpleFtseUserTrade.setStateType(1);
		}
		this.update(fSimpleFtseUserTrade);
		return fSimpleFtseUserTrade;
	}

	@Override
	public FSimpleFtseUserTrade executePayable(FSimpleFtseUserTrade fSimpleFtseUserTrade, FSimpleCoupon voucher,
			String mobile, BigDecimal payable, String remark, int businessType) throws Exception {

		fSimpleFtseUserTrade.setAppTime(TypeConvert.dbDefaultDate());
		this.save(fSimpleFtseUserTrade);
		// 更新优惠券状态为已使用
		voucher.setStatus(new Short("3"));
		voucher.setUseTime(TypeConvert.dbDefaultDate());
		this.fSimpleCouponService.update(voucher);
		fSimpleFtseUserTrade.setProgramNo("GT" + fSimpleFtseUserTrade.getId());
		this.update(fSimpleFtseUserTrade);
		// handleFtseUserTradeService.saveHandleFtseUserTrade(fSimpleFtseUserTrade);
		// // 保存富时A50收益报表记录
		rechargeListService.futureHandlerSaveRechargeStateWeb("GT" + fSimpleFtseUserTrade.getId(), mobile,
				payable.toString(), remark, TypeConvert.SYS_TYPE_OFFSET_ACCOUNTS, businessType);
		int type = 0;
		switch (fSimpleFtseUserTrade.getBusinessType()) {  //业务类型【0.富时A50  1.沪金     2.沪银   3.沪铜   4.橡胶  6.原油    7. 恒指   8.国际综合  9.小恒指     20.商品综合】
		case 8:
			type = 2;
			break;
		case 20:
			type = 1;
			break;
		case 0:
			type = 3;
			break;
		case 6:
			type = 4;
			break;
		case 7:
			type = 5;
			break;
		case 9:
			type = 6;
			break;
		default:
			break;
		}
		int lever = 0;
		if (type != 1 && type != 2) {
			lever = fSimpleFtseUserTrade.getTranLever();
		}
		FutureMatchAccount futureMatchAccount = futureMatchAccountService.getOne(type, lever,
				fSimpleFtseUserTrade.getTraderTotal().doubleValue());
		/*
		 * if (type == 5 && 2 == fSimpleFtseUserTrade.getSource()){
		 * futureMatchAccount = null; }
		 */
		if (futureMatchAccount != null) {
			fSimpleFtseUserTrade.setTranAccount(futureMatchAccount.getAccount());
			fSimpleFtseUserTrade.setTranPassword(futureMatchAccount.getPassword());
			fSimpleFtseUserTrade.setAppStarttime(Dates.getCurrentLongDate());
			fSimpleFtseUserTrade.setUpdateTime(new Date().getTime() / 1000);
			fSimpleFtseUserTrade.setStateType(4);
			futureMatchAccount.setIsUse(1);
			futureMatchAccount.setTid(fSimpleFtseUserTrade.getId());
			futureMatchAccount.setAssignTime(new Date().getTime() / 1000);
			this.futureMatchAccountService.update(futureMatchAccount);
		} else {
			// 审核中
			fSimpleFtseUserTrade.setStateType(1);
		}
		this.update(fSimpleFtseUserTrade);
		return fSimpleFtseUserTrade;
	}

	@SuppressWarnings("unchecked")
	@Override
	public PageInfo<FSimpleFtseUserTradeWebVo> findDataList(String pageIndex, String perPage, String type, String uid) {
		PageInfo<FSimpleFtseUserTradeWebVo> pageInfo = new PageInfo<FSimpleFtseUserTradeWebVo>(Integer.valueOf(perPage),
				Integer.valueOf(pageIndex) + 1);
		pageInfo = this.getEntityDao().queryPageBySql(pageInfo,
				" SELECT * FROM f_simple_ftse_user_trade f WHERE f.business_type=? AND f.uid = ? ORDER BY f.app_time DESC",
				FSimpleFtseUserTradeWebVo.class, null, new Integer(type), uid);
		return pageInfo;
	}

	@SuppressWarnings("unchecked")
	@Override
	public PageInfo<FSimpleFtseUserTradeWebVo> findDataList(String pageIndex, String perPage, String uid) {
		PageInfo<FSimpleFtseUserTradeWebVo> pageInfo = new PageInfo<FSimpleFtseUserTradeWebVo>(Integer.valueOf(perPage),
				Integer.valueOf(pageIndex) + 1);
		pageInfo = this.getEntityDao().queryPageBySql(pageInfo,
				" SELECT * FROM f_simple_ftse_user_trade f WHERE f.business_type in(0,6,7,8,9) AND f.uid = ? ORDER BY f.app_time DESC",
				FSimpleFtseUserTradeWebVo.class, null, uid);
		return pageInfo;
	}

	@SuppressWarnings("unchecked")
	@Override
	public PageInfo<FSimpleFtseUserTradeWebVo> findFristTradeDataByUid(String pageIndex, String perPage, String uid) {
		PageInfo<FSimpleFtseUserTradeWebVo> pageInfo = new PageInfo<FSimpleFtseUserTradeWebVo>(Integer.valueOf(perPage),
				Integer.valueOf(pageIndex) + 1);
		pageInfo = this.getEntityDao().queryPageBySql(pageInfo,
				" SELECT * FROM f_simple_ftse_user_trade f WHERE f.business_type in(0,6,7,8,9) AND f.uid = ? ORDER BY f.end_time asc",
				FSimpleFtseUserTradeWebVo.class, null, uid);
		return pageInfo;
	}

	public List<FSimpleFtseUserTrade> findLossPlan(Long beginTime, Long endTime) {
		return this.getEntityDao().findLossPlan(beginTime, endTime);
	}

	@Override
	public PageInfo<Object> queryWellGoldDatas(EasyUiPageInfo easyUiPage, Map<String, Object> searchParams, int type,
			int businessType) throws Exception {

		PageInfo<Object> pageInfo = new PageInfo<Object>(easyUiPage.getRows(), easyUiPage.getPage());
		StringBuffer sql = new StringBuffer();
		if (type == 0) {
			sql.append("SELECT usr.mobile, \n");
			sql.append("	uver.tname,\n");
			sql.append("	userTrade.id,\n");
			sql.append("	userTrade.trader_bond as traderBond,\n");
			sql.append("	userTrade.golden_money as bondDollar,\n");
			sql.append("	(ifnull(userTrade.trader_total,0)-ifnull(userTrade.golden_money,0)) as lossMoney,\n");
			sql.append("	userTrade.line_loss as lineLoss,\n");
			sql.append("	userTrade.trader_total as traderTotal,\n");
			sql.append("	userTrade.tran_lever as tranLever,\n");
			sql.append("	userTrade.tran_account as tranAccount,\n");
			sql.append("	userTrade.tran_password as tranPassword,\n");
			sql.append("	userTrade.app_time as appTime,\n");
			sql.append("	userTrade.update_time as updateTime,\n");
			sql.append("	userTrade.program_no as programNo,\n");
			sql.append("	userTrade.state_type as stateType\n");
			sql.append("FROM f_simple_ftse_user_trade userTrade, w_user usr, w_user_verified uver \n");
			sql.append("WHERE usr.id = userTrade.uid \n");
			sql.append("	AND usr.id = uver.uid  \n");
			sql.append("	AND userTrade.business_type = " + businessType + " AND userTrade.state_type in(1,4,5)\n");
			sql.append("ORDER BY field(userTrade.state_type,5,4,1) desc,userTrade.app_time desc");
			List<Object> params = Lists.newArrayList();
			MultiListParam multilistParam = new MultiListParam(easyUiPage, searchParams, params, sql.toString());
			pageInfo = multiListPageQuery(multilistParam, FSimpleFtseVo.class);
		} else if (type == 1) {
			sql.append("SELECT usr.mobile, \n");
			sql.append("	uver.tname,\n");
			sql.append("	userTrade.id,\n");
			// sql.append(" userTrade.deleted,\n");
			// sql.append(" userTrade.version,\n");
			// sql.append(" userTrade.app_end_time as appEndTime,\n");
			sql.append("	userTrade.tran_account as tranAccount,\n");
			sql.append("	userTrade.app_starttime as appStarttime,\n");
			sql.append("	userTrade.tran_lever as tranLever,\n");
			sql.append("	userTrade.use_tran_day as useTranDay,\n");
			sql.append("	userTrade.trader_bond as traderBond,\n");
			sql.append("	userTrade.trader_total as traderTotal,\n");
			sql.append("	userTrade.tran_profit_loss as tranProfitLoss,\n");
			sql.append("	userTrade.tran_actual_lever as tranActualLever,\n");
			sql.append("	userTrade.tran_fees as tranFees,\n");
			sql.append("	userTrade.tran_fees_total as tranFeesTotal,\n");
			sql.append("	userTrade.end_parities as endParities,\n");
			sql.append("	userTrade.end_amount as endAmount,\n");
			// sql.append(" userTrade.app_time as appTime,\n");
			sql.append("	userTrade.app_end_time as appEndTime,\n");
			sql.append("	userTrade.end_time as endTime,\n");
			// sql.append(" userTrade.fee_manage as feeManage,\n");
			// sql.append(" userTrade.line_loss as lineLoss,\n");
			sql.append("	userTrade.program_no as programNo,\n");
			sql.append("	userTrade.state_type as stateType\n");
			// sql.append(" userTrade.tran_password as tranPassword,\n");
			// sql.append(" userTrade.uid,\n");
			// sql.append(" userTrade.business_type as businessType,\n");
			// sql.append(" userTrade.update_time as updateTime\n");
			sql.append("FROM f_simple_ftse_user_trade userTrade, w_user usr, w_user_verified uver \n");
			sql.append("WHERE usr.id = userTrade.uid \n");
			sql.append("	AND usr.id = uver.uid  \n");
			sql.append("	AND userTrade.business_type = " + businessType + " AND userTrade.state_type in(6,4,3,2)\n");
			sql.append("ORDER BY field(userTrade.state_type,6,4,3,2) desc,userTrade.app_starttime");
			List<Object> params = Lists.newArrayList();
			MultiListParam multilistParam = new MultiListParam(easyUiPage, searchParams, params, sql.toString());
			pageInfo = multiListPageQuery(multilistParam, FSimpleFtseManageVo.class);
			// 计算已操盘时间
			List<Object> list = pageInfo.getPageResults();
			for (Object obj : list) {
				FSimpleFtseManageVo simpleFtseVo = (FSimpleFtseManageVo) obj;
				if (!"已结算".equals(simpleFtseVo.getStateType())) {
					simpleFtseVo.setUseTranDay(
							tradeDayService.getTradeDayNum(simpleFtseVo.getAppStarttimeLong().longValue(), 14));
				}
			}
		}

		return pageInfo;
	}

	@Override
	public FSimpleFtseUserTrade findById(String id) throws Exception {
		List<FSimpleFtseUserTrade> list = simpleFtseUserTradeDao.findById(id);
		if (CollectionUtils.isEmpty(list)) {
			return null;
		}
		return list.get(0);
	}

	@Override
	public JsonResult auditNotPass(String id) throws Exception {
		FSimpleFtseUserTrade simpleFtseUserTrade = this.get(id);
		String business = "";
		if (simpleFtseUserTrade.getBusinessType() == 0) {
			business = "富时A50";
		} else if (simpleFtseUserTrade.getBusinessType() == 6) {
			business = "国际原油";
		} else if (simpleFtseUserTrade.getBusinessType() == 7) {
			business = "恒生指数";
		} else if (simpleFtseUserTrade.getBusinessType() == 8) {
			business = "国际综合";
		} else if (simpleFtseUserTrade.getBusinessType() == 9) {
			business = "小恒指";
		}
		User currentUser = this.authService.getCurrentUser();
		log.info("用户：" + currentUser.getUsername() + "，于：" + Dates.format(new Date()) + "。" + business + "申请审核拒绝。");

		if (simpleFtseUserTrade.getStateType() == 5) {
			return new JsonResult(false, "该申请刚被另一操作员拒绝，请刷新后查看!");
		}
		if (simpleFtseUserTrade.getStateType() != 1) {
			return new JsonResult(false, "该申请已经被处理过，请刷新后查看!");
		}

		simpleFtseUserTrade.setStateType(5);
		simpleFtseUserTrade.setUseTranDay(0); // 帐号使用时间为0
		simpleFtseUserTrade.setUpdateTime(Dates.getCurrentLongDate());
		simpleFtseUserTrade.setOperator(currentUser.getRealname());
		this.update(simpleFtseUserTrade);
		if (simpleFtseUserTrade.getTraderBond().compareTo(BigDecimal.ZERO) == 0
				&& simpleFtseUserTrade.getBusinessType() == 0) {
			// handleFtseUserTradeService.saveHandleFtseUserTrade(simpleFtseUserTrade);
			// // 保存收益报表记录
		} else {
			BigDecimal tranBond = simpleFtseUserTrade.getTraderBond();
			BigDecimal teeManage = simpleFtseUserTrade.getFeeManage();
			BigDecimal actualMoney = tranBond.add(teeManage);
			String actualMoneyStr = actualMoney.toString();
			String tradeNo = simpleFtseUserTrade.getProgramNo();
			WUser wuser = wUserService.getUser(simpleFtseUserTrade.getUid());
			String remark = business + "开户申请不通过，保证金返还";
			String sysType = TypeConvert.SYS_TYPE_ADJUSTMENT_OF_ACCOUNTS;
			this.rechargeListService.futureHandlerSaveRechargeState(tradeNo, wuser.getMobile(), actualMoneyStr, remark,
					sysType, 2);
			// handleFtseUserTradeService.saveHandleFtseUserTrade(simpleFtseUserTrade);
			// // 保存收益报表记录
		}
		return new JsonResult(true, "审核拒绝成功！");
	}

	@Override
	public JsonResult auditPass(String id, FSimpleFtseUserTrade simpleFtseUserTrade) throws Exception {

		User currentUser = this.authService.getCurrentUser();
		FSimpleFtseUserTrade rsUserTrade = this.get(id);
		WUser wUser = wUserService.get(rsUserTrade.getUid());
		String business =BusinessTypeEnum.getBusinessTypeToBusiness(rsUserTrade.getBusinessType());
		
		Long dateLong = Dates.getCurrentLongDate();
		String msg = "分配帐号";
		Integer stateType = rsUserTrade.getStateType();
		if (stateType != 1) {
			msg = "修改帐号";
		}
		log.info("用户：" + currentUser.getUsername() + "，于：" + Dates.format(new Date()) + "。" + business + "," + msg);

		if (!(stateType == 1 || stateType == 4)) {
			return new JsonResult(false, "该申请方案已不可审核或修改，请刷新后查看!");
		}

		if (stateType == 1) {// 分配账号
			rsUserTrade.setStateType(4);
			rsUserTrade.setAppStarttime(dateLong);
			rsUserTrade.setOperator(currentUser.getRealname());
		}

		rsUserTrade.setTranAccount(simpleFtseUserTrade.getTranAccount());
		rsUserTrade.setTranPassword(simpleFtseUserTrade.getTranPassword());
		rsUserTrade.setOperator(currentUser.getRealname());
		rsUserTrade.setUpdateTime(dateLong);
		this.update(rsUserTrade);
		if (stateType == 1) {
			// handleFtseUserTradeService.saveHandleFtseUserTrade(rsUserTrade);
			// // 保存收益报表记录
			String content = MessageUtils.message("commodity.crude.apply.audit.success",rsUserTrade.getTranLever(), business);
			new SMSSendForContentThread(wUser.getMobile(), content, 2000).start();
		}
		return new JsonResult(true, msg + "成功!");
	}

	@Override
	public JsonResult inputResult(FSimpleFtseVo wellGoldA50) throws Exception {

		FSimpleFtseUserTrade simpleFtseUserTrade = this.get(wellGoldA50.getId());
		simpleFtseUserTrade.setTranProfitLoss(wellGoldA50.getTranProfitLoss()); // 交易盈亏
		simpleFtseUserTrade.setTranActualLever(wellGoldA50.getTranActualLever()); // 交易手数
																					// ，国际综合时为A50
																					// 交易手数
		/**
		 * 国际综合校验 输入的交易手数
		 */
		if (8 == simpleFtseUserTrade.getBusinessType()) {
			if (null == wellGoldA50.getCrudeTranActualLever() || null == wellGoldA50.getHsiTranActualLever()
					|| null == wellGoldA50.getMdtranActualLever() || null == wellGoldA50.getMntranActualLever()
					|| null == wellGoldA50.getMbtranActualLever() || null == wellGoldA50.getNikkeiTranActualLever()
					|| null == wellGoldA50.getDaxtranActualLever() || null == wellGoldA50.getLhsiTranActualLever()
					|| null == wellGoldA50.getAgTranActualLever()||null==wellGoldA50.getHeStockMarketLever()||null==wellGoldA50.getXhStockMarketLever()
					||null==wellGoldA50.getAmeCopperMarketLever()||null==wellGoldA50.getAmeSilverMarketLever()||null==wellGoldA50.getSmallCrudeOilMarketLever()
					|| null == wellGoldA50.getDaxtranMinActualLever() || null == wellGoldA50.getNaturalGasActualLever()) {
				return new JsonResult(false, "交易手数输入有误，请核对后重新输入！");
			}
			simpleFtseUserTrade.setCrudeTranActualLever(wellGoldA50.getCrudeTranActualLever()); // 原油交易手数
			simpleFtseUserTrade.setHsiTranActualLever(wellGoldA50.getHsiTranActualLever()); // 恒指交易手数

			simpleFtseUserTrade.setMdtranActualLever(wellGoldA50.getMdtranActualLever()); // 迷你道指交易手数
			simpleFtseUserTrade.setMntranActualLever(wellGoldA50.getMntranActualLever()); // 迷你纳指交易手数
			simpleFtseUserTrade.setMbtranActualLever(wellGoldA50.getMbtranActualLever()); // 迷你标普交易手数
			simpleFtseUserTrade.setNikkeiTranActualLever(wellGoldA50.getNikkeiTranActualLever()); // 日经255交易手数
			simpleFtseUserTrade.setDaxtranActualLever(wellGoldA50.getDaxtranActualLever()); // 德国dax交易手数
			simpleFtseUserTrade.setLhsiTranActualLever(wellGoldA50.getLhsiTranActualLever()); // 小恒指交易手数
			simpleFtseUserTrade.setAgTranActualLever(wellGoldA50.getAgTranActualLever()); // 美黄金交易手数
			simpleFtseUserTrade.sethStockMarketLever(wellGoldA50.getHeStockMarketLever());//H股指
			simpleFtseUserTrade.setxHStockMarketLever(wellGoldA50.getXhStockMarketLever());//小H股指
			simpleFtseUserTrade.setAmeCopperMarketLever(wellGoldA50.getAmeCopperMarketLever());//美铜
			simpleFtseUserTrade.setAmeSilverMarketLever(wellGoldA50.getAmeSilverMarketLever());//美白银
			simpleFtseUserTrade.setSmallCrudeOilMarketLever(wellGoldA50.getSmallCrudeOilMarketLever());//小原油
			simpleFtseUserTrade.setDaxtranMinActualLever(wellGoldA50.getDaxtranMinActualLever());//迷你德国DAX指数
			simpleFtseUserTrade.setNaturalGasActualLever(wellGoldA50.getNaturalGasActualLever());//天然气指数
		}
		
		simpleFtseUserTrade.setEndType(wellGoldA50.getEndType());	//设置结算方式
		/**
		 * 获取汇率
		 */
		BigDecimal endParities = simpleFtseUserTrade.getEndParities();
		if (endParities == null) {
			FSimpleParities fSimpleParities = this.simpleParitiesService.getFSimpleParities(1);
			if (fSimpleParities != null) {
				endParities = fSimpleParities.getParities();
				simpleFtseUserTrade.setEndParities(endParities);
			} else {
				return new JsonResult(false, "没有获取到最新汇率信息，请先维护汇率信息！");
			}
		}

		/* 已操盘时间 */
		int useTranDay = tradeDayService.getTradeDayNum(simpleFtseUserTrade.getAppStarttime(), 14);
		simpleFtseUserTrade.setUseTranDay(useTranDay);

		// 交易手续费总额=交易手数*交易手续费
		BigDecimal tranFees = new BigDecimal(simpleFtseUserTrade.getTranActualLever())
				.multiply(simpleFtseUserTrade.getTranFees(), MathContext.DECIMAL32);
		/**
		 * 国际综合校验计算交易手续费总额 依次相加
		 */
		if (8 == simpleFtseUserTrade.getBusinessType()) {
			// 上面tranFees 默认为A50 交易手续费
			// 计算国际原油交易手续费
			tranFees = tranFees.add(new BigDecimal(simpleFtseUserTrade.getCrudeTranActualLever())
					.multiply(simpleFtseUserTrade.getCrudeTranFees(), MathContext.DECIMAL32));
			// 计算恒生指数交易手续费
			tranFees = tranFees.add(new BigDecimal(simpleFtseUserTrade.getHsiTranActualLever())
					.multiply(simpleFtseUserTrade.getHsiTranFees(), MathContext.DECIMAL32));

			// 计算迷你道指交易手续费
			tranFees = tranFees.add(new BigDecimal(simpleFtseUserTrade.getMdtranActualLever())
					.multiply(simpleFtseUserTrade.getMdTranFees(), MathContext.DECIMAL32));
			// 计算迷你纳指交易手续费
			tranFees = tranFees.add(new BigDecimal(simpleFtseUserTrade.getMntranActualLever())
					.multiply(simpleFtseUserTrade.getMnTranFees(), MathContext.DECIMAL32));
			// 计算迷你标普交易手续费
			tranFees = tranFees.add(new BigDecimal(simpleFtseUserTrade.getMbtranActualLever())
					.multiply(simpleFtseUserTrade.getMbTranFees(), MathContext.DECIMAL32));
			// 计算日经255交易手续费
			tranFees = tranFees.add(new BigDecimal(simpleFtseUserTrade.getNikkeiTranActualLever())
					.multiply(simpleFtseUserTrade.getNikkeiTranFees(), MathContext.DECIMAL32));
			// 计算德国dax交易手续费
			tranFees = tranFees.add(new BigDecimal(simpleFtseUserTrade.getDaxtranActualLever())
					.multiply(simpleFtseUserTrade.getDaxTranFees(), MathContext.DECIMAL32));
			// 计算小恒指交易手续费
			BigDecimal lhsiTranFees = simpleFtseUserTrade.getLhsiTranFees() == null ? BigDecimal.ZERO
					: simpleFtseUserTrade.getLhsiTranFees();
			tranFees = tranFees.add(new BigDecimal(simpleFtseUserTrade.getLhsiTranActualLever()).multiply(lhsiTranFees,
					MathContext.DECIMAL32));
			// 计算美黄金交易手续费
			BigDecimal agTranFees = simpleFtseUserTrade.getAgTranFees() == null ? BigDecimal.ZERO
					: simpleFtseUserTrade.getAgTranFees();
			tranFees = tranFees.add(new BigDecimal(simpleFtseUserTrade.getAgTranActualLever()).multiply(agTranFees,
					MathContext.DECIMAL32));
            //计算H股指交易手续费
			tranFees = tranFees.add(new BigDecimal(simpleFtseUserTrade.gethStockMarketLever())
					.multiply(simpleFtseUserTrade.gethSTranFees(), MathContext.DECIMAL32));
			//计算小H股指交易手续费
			tranFees = tranFees.add(new BigDecimal(simpleFtseUserTrade.getxHStockMarketLever())
					.multiply(simpleFtseUserTrade.getxHSTranFees(), MathContext.DECIMAL32));
			//计算美铜交易手续费
			tranFees = tranFees.add(new BigDecimal(simpleFtseUserTrade.getAmeCopperMarketLever())
					.multiply(simpleFtseUserTrade.getAmeCTranFees(), MathContext.DECIMAL32));
			//计算美白银交易手续费
			tranFees = tranFees.add(new BigDecimal(simpleFtseUserTrade.getAmeSilverMarketLever())
					.multiply(simpleFtseUserTrade.getAmeSTranFees(), MathContext.DECIMAL32));
			//计算小原油交易手续费
			tranFees = tranFees.add(new BigDecimal(simpleFtseUserTrade.getSmallCrudeOilMarketLever())
					.multiply(simpleFtseUserTrade.getSmallCTranFees(), MathContext.DECIMAL32));
			//计算迷你德国DAX指数交易手续费
			BigDecimal daxmintranfess = simpleFtseUserTrade.getDaxMinTranFees();
			tranFees = tranFees.add(new BigDecimal(simpleFtseUserTrade.getDaxtranMinActualLever())
					.multiply((daxmintranfess == null ? new BigDecimal(0) :daxmintranfess), MathContext.DECIMAL32));
			//天然气指数交易手续费
			BigDecimal naturalGasFess = simpleFtseUserTrade.getNaturalGasFess();
			tranFees = tranFees.add(new BigDecimal(simpleFtseUserTrade.getNaturalGasActualLever())
					.multiply((naturalGasFess == null ? new BigDecimal(0) :naturalGasFess), MathContext.DECIMAL32));
		}

		// 抵扣券抵扣手续费
		BigDecimal discountActualMoney;

		if (null != simpleFtseUserTrade.getDiscountId()) {
			FSimpleCoupon voucher = fSimpleCouponService.get(simpleFtseUserTrade.getDiscountId());
			if (voucher.getType() == 3) {
				// -------------新增折扣券--------------------
				// 折扣券(折)
				BigDecimal discountMoney = simpleFtseUserTrade.getDiscountMoney();
				// 抵扣券抵扣手续费
				if (discountMoney != null) {
					// 未折扣的管理费
					BigDecimal unDiscountMoney = tranFees;
					// 折扣=折扣券(折)/10
					discountMoney = discountMoney.divide(new BigDecimal(10));
					// 折扣后交易手续费总额=交易手续费总额*折扣
					tranFees = tranFees.multiply(discountMoney, MathContext.DECIMAL32);
					// 抵扣券抵扣手续费总额=交易手续费总额-折扣后交易手续费总额
					discountActualMoney = unDiscountMoney.subtract(tranFees);
					simpleFtseUserTrade.setDiscountActualMoney(discountActualMoney);
				}
				// -------------新增折扣券--------------------
				// -------------新增抵扣卷--------------------
			} else if (voucher.getType() == 6) {
				BigDecimal discountMoney = simpleFtseUserTrade.getDiscountMoney();
				// 抵扣券抵扣手续费
				if (discountMoney != null) {
					// 折扣后交易手续费总额=交易手续费总额*折扣
					if (tranFees.compareTo(discountMoney) >= 0) {
						discountActualMoney = discountMoney;
						tranFees = tranFees.subtract(discountMoney);
					} else {
						discountActualMoney = tranFees;
						tranFees = new BigDecimal(0);
					}
					// 抵扣券抵扣手续费总额=交易手续费总额-折扣后交易手续费总额
					simpleFtseUserTrade.setDiscountActualMoney(discountActualMoney);
				}
			}
		}

		// if (discountMoney != null) {
		// //未折扣的管理费
		// BigDecimal unDiscountMoney = tranFees;
		// //折扣=折扣券(折)/10
		// discountMoney = discountMoney.divide(new BigDecimal(10), 4,
		// BigDecimal.ROUND_HALF_EVEN);
		// //折扣后交易手续费总额=交易手续费总额*折扣
		// tranFees = tranFees.multiply(discountMoney, MathContext.DECIMAL32);
		// //抵扣券抵扣手续费总额=交易手续费总额-折扣后交易手续费总额
		// discountActualMoney = unDiscountMoney.subtract(tranFees);
		// simpleFtseUserTrade.setDiscountActualMoney(discountActualMoney);
		// }
		simpleFtseUserTrade.setTranFeesTotal(tranFees);

		// 结算金额=保证金+追加保证金+交易盈亏*汇率-交易手续费
		BigDecimal afterParities = simpleFtseUserTrade.getTranProfitLoss()
				.multiply(simpleFtseUserTrade.getEndParities(), MathContext.DECIMAL32);
		BigDecimal endAmountCal = simpleFtseUserTrade.getTraderBond().add(simpleFtseUserTrade.getAppendTraderBond())
				.add(afterParities).subtract(tranFees);

		simpleFtseUserTrade.setEndAmountCal(endAmountCal); // 设置结算金额

		// 实际结算金额
		BigDecimal endAmount = endAmountCal;
		// 实际抵扣金额
		BigDecimal endActualMoney = new BigDecimal("0");
		// 实际代金券(元)
		BigDecimal voucherActualMoney = simpleFtseUserTrade.getVoucherActualMoney();

		if (voucherActualMoney != null && voucherActualMoney.compareTo(new BigDecimal("0")) > 0
				&& endAmount.compareTo(new BigDecimal("0")) < 0) {
			// 结算金额+实际代金券(元)
			BigDecimal money = endAmountCal.add(voucherActualMoney);
			if (money.compareTo(new BigDecimal("0")) >= 0) {
				endActualMoney = new BigDecimal("0").subtract(endAmountCal);
				endAmount = new BigDecimal("0");
			} else {
				endActualMoney = voucherActualMoney;
				endAmount = voucherActualMoney.add(endAmountCal);
			}
		}

		simpleFtseUserTrade.setEndActualMoney(endActualMoney); // 设置实际抵扣金额

		simpleFtseUserTrade.setEndAmount(endAmount); // 设置实际结算金额
		
		
		simpleFtseUserTrade.setStateType(3); // 待结算
		simpleFtseUserTrade.setOperator(authService.getCurrentUser().getRealname());
		this.update(simpleFtseUserTrade);
		return new JsonResult(true, "方案结算信息录入成功！");
	}

	@Override
	public JsonResult settlementA50(FSimpleFtseVo wellGoldA50) throws Exception {
		FSimpleFtseUserTrade simpleFtseUserTrade = this.get(wellGoldA50.getId());
		String business = BusinessTypeEnum.getBusinessTypeToBusiness(simpleFtseUserTrade.getBusinessType());
		if (simpleFtseUserTrade.getStateType() == 6) {
			return new JsonResult(false, "该方案已结算，请刷新后查看!");
		}
		if (simpleFtseUserTrade.getStateType() != 3) {
			return new JsonResult(false, "只有待结算方案可点击[结算]操作。请先录入结算信息，或刷新后查看!");
		} else {
			String tradeNo = simpleFtseUserTrade.getProgramNo();
			WUser wuser = wUserService.getUser(simpleFtseUserTrade.getUid());
			String remark = business + "结算";
			String sysType = TypeConvert.SYS_TYPE_ADJUSTMENT_OF_ACCOUNTS;
			BigDecimal endAmount = simpleFtseUserTrade.getEndAmount();// 结算金额
			if (simpleFtseUserTrade.getTraderBond().compareTo(BigDecimal.ZERO) == 0
					&& simpleFtseUserTrade.getBusinessType() == 0) {
				if (endAmount.intValue() < 0) {
					sysType = TypeConvert.SYS_TYPE_OFFSET_ACCOUNTS;
					remark = "A50活动扣除手续费后，亏损" + endAmount.toString() + "元，实际扣除0元";
					this.rechargeListService.futureHandlerSaveRechargeState(tradeNo, wuser.getMobile(), "0", remark,
							sysType, 3);
				} else {
					remark = "A50活动扣除手续费后结算";
					this.rechargeListService.futureHandlerSaveRechargeState(tradeNo, wuser.getMobile(),
							endAmount.toString(), remark, sysType, 3);
				}
				simpleFtseUserTrade.setStateType(6); // 6.已结算
				simpleFtseUserTrade.setEndTime(Dates.getCurrentLongDate());
				simpleFtseUserTrade.setOperator(authService.getCurrentUser().getRealname());
				this.update(simpleFtseUserTrade);
				// handleFtseUserTradeService.saveHandleFtseUserTrade(simpleFtseUserTrade);
				// // 保存收益报表记录
			} else {
				// 结算金额为负，自动扣取用户余额，用户余额不够扣取为负
				if (endAmount.intValue() < 0) {
					if (simpleFtseUserTrade.getTraderBond().compareTo(BigDecimal.ZERO) == 0
							&& simpleFtseUserTrade.getBusinessType() == 6) {
						sysType = TypeConvert.SYS_TYPE_OFFSET_ACCOUNTS;
						remark = "国际原油活动扣除手续费后，亏损" + endAmount.toString() + "元，实际扣除0元";
						endAmount = new BigDecimal("0");
					} else {
						sysType = TypeConvert.SYS_TYPE_OFFSET_ACCOUNTS;
						remark = business + "穿仓欠费";
					}
				} else {
					if (simpleFtseUserTrade.getTraderBond().compareTo(BigDecimal.ZERO) == 0
							&& simpleFtseUserTrade.getBusinessType() == 6) {
						remark = "国际原油活动扣除手续费后结算";
					}
				}
				this.rechargeListService.futureHandlerSaveRechargeState(tradeNo, wuser.getMobile(),
						endAmount.toString(), remark, sysType, 3);

				simpleFtseUserTrade.setStateType(6); // 6.已结算
				simpleFtseUserTrade.setEndTime(Dates.getCurrentLongDate());
				simpleFtseUserTrade.setOperator(authService.getCurrentUser().getRealname());
				this.update(simpleFtseUserTrade);
				// handleFtseUserTradeService.saveHandleFtseUserTrade(simpleFtseUserTrade);
				// // 保存收益报表记录
			}
			if (dataMapService.activityExpired("activityOnlineEndTime")) {
				this.validationIsTradeSubsidy(simpleFtseUserTrade.getUid(), wuser.getMobile(), wellGoldA50.getId());
			}
			Double tranProfitLoss = simpleFtseUserTrade.getTranProfitLoss().doubleValue() * simpleFtseUserTrade.getEndParities().doubleValue();
			Double absTranProfitLoss = Math.abs(tranProfitLoss);
			Double bond = simpleFtseUserTrade.getTraderBond().doubleValue();
			BigDecimal appendBondBig = simpleFtseUserTrade.getAppendTraderBond();
			Double appendBond = appendBondBig == null ? 0.00:appendBondBig.doubleValue() ;
			Double endAmlount = simpleFtseUserTrade.getEndAmount().doubleValue();
			Double tradeFeeTotalDouble = 0.00;
			BigDecimal tradeFeeTotal = simpleFtseUserTrade.getTranFeesTotal();
			if(tradeFeeTotal != null){
				tradeFeeTotalDouble = tradeFeeTotal.doubleValue();
				if(tradeFeeTotalDouble > 0){
					if(tranProfitLoss < 0){
						if(endAmlount < 0){
							endAmlount = absTranProfitLoss;
						}else{
							endAmlount = (appendBond + bond - tradeFeeTotalDouble);
						}
					}
					Double countOperateMoney = wuser.getCountOperateMoney();
					if(countOperateMoney == null){
						countOperateMoney = 0.00;
					}
					Double new_CountOperateMoney = Math.abs(countOperateMoney+endAmlount);
					BigDecimal bd = new BigDecimal(new_CountOperateMoney); 
					wuser.setCountOperateMoney(bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
				}
			}
			wUserService.update(wuser);
			//短信
			Map<String, String> map = new HashMap<String, String>();
			int lever = 0;
			if(business.equals("国际综合")){
				lever = (simpleFtseUserTrade.getTranActualLever()==null ? 0 : simpleFtseUserTrade.getTranActualLever() )+
						(simpleFtseUserTrade.getCrudeTranActualLever()==null ? 0 : simpleFtseUserTrade.getCrudeTranActualLever() )+
						(simpleFtseUserTrade.getHsiTranActualLever()==null ? 0 : simpleFtseUserTrade.getHsiTranActualLever() )+
						(simpleFtseUserTrade.getMntranActualLever()==null ? 0 : simpleFtseUserTrade.getMntranActualLever() )+
						(simpleFtseUserTrade.getMbtranActualLever()==null ? 0 : simpleFtseUserTrade.getMbtranActualLever() )+
						(simpleFtseUserTrade.getDaxtranActualLever()==null ? 0 : simpleFtseUserTrade.getDaxtranActualLever() )+
						(simpleFtseUserTrade.getNikkeiTranActualLever()==null ? 0 : simpleFtseUserTrade.getNikkeiTranActualLever() )+
						(simpleFtseUserTrade.getMdtranActualLever()==null ? 0 : simpleFtseUserTrade.getMdtranActualLever() )+
						(simpleFtseUserTrade.getLhsiTranActualLever()==null ? 0 : simpleFtseUserTrade.getLhsiTranActualLever() )+
						(simpleFtseUserTrade.getAgTranActualLever()==null ? 0 : simpleFtseUserTrade.getAgTranActualLever() )+
						(simpleFtseUserTrade.gethStockMarketLever()==null ? 0 : simpleFtseUserTrade.gethStockMarketLever() )+
						(simpleFtseUserTrade.getxHStockMarketLever()==null ? 0 : simpleFtseUserTrade.getxHStockMarketLever() )+
						(simpleFtseUserTrade.getAmeCopperMarketLever()==null ? 0 : simpleFtseUserTrade.getAmeCopperMarketLever() )+
						(simpleFtseUserTrade.getAmeSilverMarketLever()==null ? 0 : simpleFtseUserTrade.getAmeSilverMarketLever() )+
						(simpleFtseUserTrade.getSmallCrudeOilMarketLever()==null ? 0 : simpleFtseUserTrade.getSmallCrudeOilMarketLever() )+
						(simpleFtseUserTrade.getDaxtranMinActualLever()==null ? 0 : simpleFtseUserTrade.getDaxtranMinActualLever() )+
						(simpleFtseUserTrade.getNaturalGasActualLever()==null ? 0 : simpleFtseUserTrade.getNaturalGasActualLever() );
			}else{
				lever = (simpleFtseUserTrade.getTranActualLever()==null ? 0 : simpleFtseUserTrade.getTranActualLever());
			}
			map.put("lever", String.valueOf(lever));
			map.put("business", business);
			SMSSender.getInstance().sendByTemplate(1, wuser.getMobile(), "ihuyi.settlement.success.template", map);
			
			return new JsonResult(true, "方案结算成功！");
		}
	}
	public void validationIsTradeSubsidy(String uid, String mobile, String id) {
		List<ActivityReward> activityRewards = activityRewardService.doGetByUid(uid, "001");
		if (activityRewards != null && activityRewards.size() > 0) {
			return;
		}
		List<FSimpleFtseUserTrade> fstvos = getEntityDao().findByUidFristLoss(uid);
		int size = fstvos.size();
		// 如果是第一次交易
		if (size > 0) {
			if (size == 1) {
				FSimpleFtseUserTrade fstvo = fstvos.get(0);
				// 如果第一次交易亏损可参与抽奖
				if (fstvo.getTranProfitLoss().intValue() < 0) {
					ActivityReward activityReward = new ActivityReward();
					activityReward.setIstip(false);
					activityReward.setIsvalid(false);
					activityReward.setReward_type(ExtensionConstants.REWARD_TYPE_LUCK_DRAW);
					activityReward.setUid(uid);
					activityReward.setType(ExtensionConstants.SubsidyType.LUCK_DRAW);
					activityReward.setMoney(0.00);
					activityReward.setActivity(ExtensionConstants.ACTIVITY_TYPE);
					activityReward.setCreateTime(new Date().getTime() / 1000);
					activityReward.setFtseTradeId(id);
					activityRewardService.doSave(activityReward);
					try {
						// 获取短信通道
						Integer smsChannel = dataMapService.getSmsContentOthers();
						SMSSender.getInstance().sendByTemplate(smsChannel, mobile, "activity.luck.ihuyi.code.template",
								null);
					} catch (Exception e) {
						e.printStackTrace();
						log.info("通知抽奖的短信发送异常");
					}
				}
			}
		}
	}

	@Override
	public boolean isHave(String uid, int type) {
		List<FSimpleFtseUserTrade> list = simpleFtseUserTradeDao.findByUidAndBusinessType(uid, type);
		if (CollectionUtils.isEmpty(list)) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public FSimpleFtseUserTrade activePayable(FSimpleFtseUserTrade fSimpleFtseUserTrade, String mobile,
			BigDecimal payable, String uid) throws Exception {
		fSimpleFtseUserTrade.setAppTime(TypeConvert.dbDefaultDate());
		this.save(fSimpleFtseUserTrade);
		fSimpleFtseUserTrade.setProgramNo("GT" + fSimpleFtseUserTrade.getId());
		this.update(fSimpleFtseUserTrade);
		ftseActiveService.use(uid);
		// handleFtseUserTradeService.saveHandleFtseUserTrade(fSimpleFtseUserTrade);
		// // 保存富时A50收益报表记录
		rechargeListService.futureHandlerSaveRechargeStateWeb("GT" + fSimpleFtseUserTrade.getId(), mobile,
				payable.toString(), "投资新华富时A50申请（活动划款）。", TypeConvert.SYS_TYPE_OFFSET_ACCOUNTS, 1);
		return fSimpleFtseUserTrade;
	}

	@Override
	public FSimpleFtseUserTrade crudeActivePayable(FSimpleFtseUserTrade fSimpleFtseUserTrade, String mobile,
			BigDecimal payable, String uid) throws Exception {
		fSimpleFtseUserTrade.setAppTime(TypeConvert.dbDefaultDate());
		this.save(fSimpleFtseUserTrade);
		fSimpleFtseUserTrade.setProgramNo("GT" + fSimpleFtseUserTrade.getId());
		this.update(fSimpleFtseUserTrade);
		crudeActiveService.use(uid);
		// handleFtseUserTradeService.saveHandleFtseUserTrade(fSimpleFtseUserTrade);
		// // 保存富时A50收益报表记录
		rechargeListService.futureHandlerSaveRechargeStateWeb("GT" + fSimpleFtseUserTrade.getId(), mobile,
				payable.toString(), "投资国际原油申请（活动划款）。", TypeConvert.SYS_TYPE_OFFSET_ACCOUNTS, 1);
		return fSimpleFtseUserTrade;
	}

	@Override
	public void addAppendTraderBond(FSimpleFtseUserTrade fSimpleFtseUserTrade, Double appendMoney, String rate,
			Double dollar, WUser wuser) throws Exception {
		Integer type = fSimpleFtseUserTrade.getBusinessType();
		String remark = "";
		if (type == 0) {
			remark = "国际期货(富时A50)追加保证金。";
		} else if (type == 6) {
			remark = "国际期货(国际原油)追加保证金。";
		} else if (type == 7) {
			remark = "国际期货(恒生指数)追加保证金。";
		} else if (type == 8) {
			remark = "国际期货(国际综合)追加保证金。";
		} else if (type == 9) {
			remark = "国际期货(迷你恒指)追加保证金。";
		}
		BigDecimal payMoney = new BigDecimal(appendMoney); // 追加保证金

		fSimpleFtseUserTrade
				.setAppendTraderBond(TypeConvert.scale(fSimpleFtseUserTrade.getAppendTraderBond().add(payMoney), 2));

		// 追加保证金划款
		rechargeListService.futureHandlerSaveRechargeStateWeb(fSimpleFtseUserTrade.getProgramNo(), wuser.getMobile(),
				appendMoney.toString(), remark, TypeConvert.SYS_TYPE_OFFSET_ACCOUNTS);

		// 更新股指追加保证金
		this.update(fSimpleFtseUserTrade);

		// 创建追加保证金记录
		FinternationFutureAppendLevelMoney appendLevelMoney = new FinternationFutureAppendLevelMoney(wuser.getId(),
				fSimpleFtseUserTrade.getProgramNo(), appendMoney, Double.parseDouble(rate), dollar, type);

		finternationFutureAppendLevelMoneyService.save(appendLevelMoney); // 保存追加保证金记录

	}

	@Override
	public PageInfo<Object> queryInternationFutureDatas(EasyUiPageInfo easyUiPage, Map<String, Object> searchParams,
			int queryType) throws Exception {

		PageInfo<Object> pageInfo = new PageInfo<Object>(easyUiPage.getRows(), easyUiPage.getPage());
		StringBuffer sql = new StringBuffer();
		if (queryType == 0) {
			sql.append("SELECT usr.mobile, \n");
			sql.append("	uver.tname,\n");
			sql.append("	userTrade.id,\n");
			sql.append("	userTrade.tran_fees as tranFees,\n");
			sql.append("    userTrade.business_type as businessType,\n");
			sql.append("	userTrade.trader_bond as traderBond,\n");
			sql.append("	userTrade.voucher_actual_money as voucherActualMoney,\n");
			sql.append("	userTrade.golden_money as bondDollar,\n");
			sql.append("	(ifnull(userTrade.trader_total,0)-ifnull(userTrade.golden_money,0)) as lossMoney,\n");
			sql.append("	userTrade.line_loss as lineLoss,\n");
			sql.append("	userTrade.trader_total as traderTotal,\n");
			sql.append("	userTrade.tran_lever as tranLever,\n");
			sql.append("	userTrade.tran_account as tranAccount,\n");
			sql.append("	userTrade.tran_password as tranPassword,\n");
			sql.append("	userTrade.app_time as appTime,\n");
			sql.append("	userTrade.update_time as updateTime,\n");
			sql.append("	userTrade.program_no as programNo,\n");
			sql.append("	userTrade.state_type as stateType,\n");
			sql.append("	userTrade.source as source,\n");
			sql.append("	userTrade.operator as operator\n");
			sql.append("FROM f_simple_ftse_user_trade userTrade, w_user usr, w_user_verified uver \n");
			sql.append("WHERE usr.id = userTrade.uid \n");
			sql.append("	AND usr.id = uver.uid  \n");
			sql.append("	AND userTrade.business_type in(0,6,7,8,9) AND userTrade.state_type in(1,4,5)\n");
			sql.append(" ORDER BY field(userTrade.state_type,5,4,1) desc,userTrade.app_time desc  ");
			List<Object> params = Lists.newArrayList();
			MultiListParam multilistParam = new MultiListParam(easyUiPage, searchParams, params, sql.toString());
			pageInfo = multiListPageQuery(multilistParam, FSimpleFtseVo.class);
		} else if (queryType == 1) {
			sql.append("SELECT usr.mobile, \n");
			sql.append("	uver.tname,\n");
			sql.append("	userTrade.id,\n");
			sql.append("    userTrade.business_type as businessType,\n");
			// sql.append(" userTrade.deleted,\n");
			// sql.append(" userTrade.version,\n");
			// sql.append(" userTrade.app_end_time as appEndTime,\n");
			sql.append("	userTrade.tran_account as tranAccount,\n");
			sql.append("	userTrade.app_starttime as appStarttime,\n");
			sql.append("	userTrade.app_time as appTime,\n");
			sql.append("	userTrade.tran_lever as tranLever,\n");
			sql.append("	userTrade.use_tran_day as useTranDay,\n");
			sql.append("	userTrade.trader_bond as traderBond,\n");
			sql.append("	userTrade.voucher_actual_money as voucherActualMoney,\n");
			sql.append("	userTrade.append_trader_bond as appendTraderBond,\n");
			sql.append("	userTrade.trader_total as traderTotal,\n");
			sql.append("	userTrade.tran_profit_loss as tranProfitLoss,\n");
			sql.append("	userTrade.tran_actual_lever as tranActualLever,\n");
			sql.append("	userTrade.crude_tran_actual_lever as crudeTranActualLever,\n");
			sql.append("	userTrade.hsi_tran_actual_lever as hsiTranActualLever,\n");

			sql.append("	userTrade.mdtran_actual_lever as mdtranActualLever,\n");
			sql.append("	userTrade.mntran_actual_lever as mntranActualLever,\n");
			sql.append("	userTrade.mbtran_actual_lever as mbtranActualLever,\n");
			sql.append("	userTrade.daxtran_actual_lever as daxtranActualLever,\n");
			sql.append("	userTrade.nikkei_tran_actual_lever as nikkeiTranActualLever,\n");
			sql.append("	userTrade.lhsi_tran_actual_lever as lhsiTranActualLever,\n");
			sql.append("	userTrade.ag_tran_actual_lever as agTranActualLever,\n");
			sql.append("	userTrade.h_stock_market_lever as heStockMarketLever,\n");
			sql.append("	userTrade.xhstock_market_lever as xhStockMarketLever,\n");
			sql.append("	userTrade.ame_copper_market_lever as ameCopperMarketLever,\n");
			sql.append("	userTrade.ame_silver_market_lever as ameSilverMarketLever,\n");
			sql.append("	userTrade.small_crude_oil_market_lever as smallCrudeOilMarketLever,\n");
			sql.append("	userTrade.daxtran_min_actual_lever as daxtranMinActualLever,\n");
			sql.append("	userTrade.natural_gas_actual_lever as naturalGasActualLever,\n");
			sql.append("	userTrade.tran_fees as tranFees,\n");
			sql.append("	userTrade.tran_fees_total as tranFeesTotal,\n");
			sql.append("	userTrade.discount_money as discountMoney,\n");
			sql.append("	userTrade.discount_actual_money as discountActualMoney,\n");
			sql.append("	userTrade.end_parities as endParities,\n");
			sql.append("	userTrade.end_amount as endAmount,\n");
			sql.append("	userTrade.end_amount_cal as endAmountCal,\n");
			// sql.append(" userTrade.app_time as appTime,\n");
			sql.append("	userTrade.app_end_time as appEndTime,\n");
			sql.append("	userTrade.end_time as endTime,\n");
			sql.append("	userTrade.end_type as endType,\n");
			// sql.append(" userTrade.fee_manage as feeManage,\n");
			// sql.append(" userTrade.line_loss as lineLoss,\n");
			sql.append("	userTrade.program_no as programNo,\n");
			sql.append("	userTrade.state_type as stateType,\n");
			sql.append("	userTrade.source as source,\n");
			sql.append("	userTrade.operator as operator,\n");
			sql.append("(select CONVERT(s.type,CHAR) from f_simple_coupon s where userTrade.discount_id = s.id) AS type \n");
			// sql.append(" userTrade.tran_password as tranPassword,\n");
			// sql.append(" userTrade.uid,\n");
			// sql.append(" userTrade.business_type as businessType,\n");
			// sql.append(" userTrade.update_time as updateTime\n");
			sql.append("FROM f_simple_ftse_user_trade userTrade, w_user usr, w_user_verified uver \n");
			sql.append("WHERE usr.id = userTrade.uid \n");
			sql.append("	AND usr.id = uver.uid  \n");
			sql.append("	AND userTrade.business_type in (0,6,7,8,9) AND userTrade.state_type in(6,4,3,2)\n");
			sql.append(
					"ORDER BY field(userTrade.state_type,6,4,3,2) desc, appEndTime desc, userTrade.app_starttime desc ");
			List<Object> params = Lists.newArrayList();
			MultiListParam multilistParam = new MultiListParam(easyUiPage, searchParams, params, sql.toString());
			pageInfo = multiListPageQuery(multilistParam, FSimpleFtseManageVo.class);
			// 计算已操盘时间
			List<Object> list = pageInfo.getPageResults();
			for (Object obj : list) {
				FSimpleFtseManageVo simpleFtseVo = (FSimpleFtseManageVo) obj;
				if (!"已结算".equals(simpleFtseVo.getStateType())) {
					simpleFtseVo.setUseTranDay(
							tradeDayService.getTradeDayNum(simpleFtseVo.getAppStarttimeLong().longValue(), 14));
				}
			}
		}

		return pageInfo;
	}

	@Override
	public PageInfo<Object> queryPageDatas(EasyUiPageInfo easyUiPage, Map<String, Object> searchParams,
			int businessType) throws Exception {

		PageInfo<Object> pageInfo = new PageInfo<Object>(easyUiPage.getRows(), easyUiPage.getPage());
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT usr.mobile,\n");
		sql.append("		uver.tname,\n");
		sql.append("		hfut.app_end_time as appEndTime,\n");
		sql.append("		hfut.app_starttime as appStarttime,\n");
		sql.append("		hfut.app_time as appTime,\n");
		sql.append("		hfut.business_type as businessType,\n");
		sql.append("		hfut.end_amount as endAmount,\n");
		sql.append("		hfut.end_parities as endParities,\n");
		sql.append("		hfut.end_time as endTime,\n");
		sql.append("		hfut.fee_manage as feeManage,\n");
		sql.append("		hfut.line_loss as lineLoss,\n");
		sql.append("		hfut.program_no as programNo,\n");
		sql.append("		hfut.state_type as stateType,\n");
		sql.append("		hfut.trader_bond as traderBond,\n");
		sql.append("		hfut.trader_total as traderTotal,\n");
		sql.append("		hfut.append_trader_bond as appendTraderBond,\n");
		sql.append("		hfut.tran_account as tranAccount,\n");
		sql.append("		hfut.tran_actual_lever as tranActualLever,\n");
		sql.append("		hfut.tran_fees as tranFees,\n");
		sql.append("		hfut.tran_fees_total as tranFeesTotal,\n");
		sql.append("		hfut.tran_lever as tranLever,\n");
		sql.append("		hfut.tran_password as tranPassword,\n");
		sql.append("		hfut.tran_profit_loss as tranProfitLoss,\n");
		sql.append("		hfut.uid,\n");
		sql.append("		hfut.update_time as updateTime,\n");
		sql.append("		hfut.use_tran_day as useTranDay,\n");
		sql.append("		hfut.end_actual_money as endActualMoney,\n");
		sql.append("		hfut.discount_money as discountMoney,\n");
		sql.append("		hfut.discount_actual_money as discountActualMoney,\n");
		sql.append("		hfut.source as source\n");
		sql.append("FROM f_simple_ftse_user_trade hfut,w_user usr, w_user_verified uver\n");
		sql.append("WHERE usr.id = hfut.uid\n");
		sql.append("	AND usr.id = uver.uid\n");
		sql.append("	AND hfut.business_type = " + businessType + "\n");
		sql.append("ORDER BY hfut.app_time DESC");
		List<Object> params = Lists.newArrayList();
		MultiListParam multilistParam = new MultiListParam(easyUiPage, searchParams, params, sql.toString());

		if (8 == businessType) {
			pageInfo = multiListPageQuery(multilistParam, FHandleFtseUserTradeVo2.class);
			// 计算已操盘时间
			List<Object> list = pageInfo.getPageResults();
			for (Object obj : list) {
				FHandleFtseUserTradeVo2 simpleFtseVo = (FHandleFtseUserTradeVo2) obj;
				// 状态【1.开户中、2.申请结算、3.待结算、4.操盘中 5.审核不通过 、6.已结算】
				if (simpleFtseVo.getState() == 2 || simpleFtseVo.getState() == 3 || simpleFtseVo.getState() == 4) {
					simpleFtseVo.setUseTranDay(
							tradeDayService.getTradeDayNum(simpleFtseVo.getAppStarttimeLong().longValue(), 14));
				}
			}
		} else {
			pageInfo = multiListPageQuery(multilistParam, FHandleFtseUserTradeVo.class);
			// 计算已操盘时间
			List<Object> list = pageInfo.getPageResults();
			for (Object obj : list) {
				FHandleFtseUserTradeVo simpleFtseVo = (FHandleFtseUserTradeVo) obj;
				// 状态【1.开户中、2.申请结算、3.待结算、4.操盘中 5.审核不通过 、6.已结算】
				if (simpleFtseVo.getState() == 2 || simpleFtseVo.getState() == 3 || simpleFtseVo.getState() == 4) {
					simpleFtseVo.setUseTranDay(
							tradeDayService.getTradeDayNum(simpleFtseVo.getAppStarttimeLong().longValue(), 14));
				}
			}
		}

		return pageInfo;
	}

	@Override
	public void updateFSimpleFtseUserTradeAndFSimpleCoupon(FSimpleFtseUserTrade fSimpleFtseUserTrade,
			FSimpleCoupon discount) throws BusinessException {
		// 更新优惠券状态为已使用
		discount.setStatus(new Short("3"));
		discount.setUseTime(TypeConvert.dbDefaultDate());
		this.fSimpleCouponService.update(discount);

		this.update(fSimpleFtseUserTrade);
	}

	@Override
	public String passSaveAccount(String id) {
		if (StringUtils.isEmpty(id)) {
			return "";
		}
		FSimpleFtseUserTrade rsUserTrade = this.get(id);
		if (rsUserTrade == null) {
			return "";
		}
		Integer stateType = rsUserTrade.getStateType();
		if (stateType == 4) {
			return "账号已分配，请刷新后查看！";
		}
		return "";
	}

	@SuppressWarnings("deprecation")
	@Override
	public List<FSimpleFtseUserTrade> findByUidAndStateType(String uid) {
		if(!StringUtils.isEmpty(uid)){
			return	this.getEntityDao().findByUidAndStateType(uid);
		}
		return null;
	}
	@Override
	public void clearFsimpleFtseUserTradeById(String id,Integer stateType) {
		FSimpleFtseUserTrade fSimpleFtseUserTrade =  getEntityDao().get(id);
		fSimpleFtseUserTrade.setStateType(stateType);
		fSimpleFtseUserTrade.setTranProfitLoss(null);
		fSimpleFtseUserTrade.setEndParities(null);
		fSimpleFtseUserTrade.setTranFeesTotal(null);
		fSimpleFtseUserTrade.setDaxtranMinActualLever(null);
		fSimpleFtseUserTrade.setNaturalGasActualLever(null);;
		fSimpleFtseUserTrade.setSmallCrudeOilMarketLever(null);
		fSimpleFtseUserTrade.setAmeSilverMarketLever(null);
		fSimpleFtseUserTrade.setAmeCopperMarketLever(null);
		fSimpleFtseUserTrade.setxHStockMarketLever(null);
		fSimpleFtseUserTrade.sethStockMarketLever(null);
		fSimpleFtseUserTrade.setAgTranActualLever(null);
		fSimpleFtseUserTrade.setLhsiTranActualLever(null);
		fSimpleFtseUserTrade.setMdtranActualLever(null);
		fSimpleFtseUserTrade.setNikkeiTranActualLever(null);
		fSimpleFtseUserTrade.setDaxtranActualLever(null);
		fSimpleFtseUserTrade.setMbtranActualLever(null);
		fSimpleFtseUserTrade.setMntranActualLever(null);
		fSimpleFtseUserTrade.setHsiTranActualLever(null);
		fSimpleFtseUserTrade.setCrudeTranActualLever(null);
		fSimpleFtseUserTrade.setTranActualLever(null);
		fSimpleFtseUserTrade.setAppEndTime(null);
		fSimpleFtseUserTrade.setEndTime(null);
		fSimpleFtseUserTrade.setEndAmountCal(null);
		fSimpleFtseUserTrade.setEndAmount(null);
		getEntityDao().update(fSimpleFtseUserTrade);
		tradeDetailService.deleteByFastId(id);
	}

	@Override
	public FSimpleFtseUserTrade findByUserNo(String userNo) {
		
		if(!StringUtils.isEmpty(userNo)){
			List<FSimpleFtseUserTrade> findByTranAccount = this.getEntityDao().findByTranAccount(userNo);
			if(findByTranAccount!=null){
				return findByTranAccount.get(0);
			}
			return null;
		}
		return null;
	}

	@Override
	public PageInfo<FSimpleFtseUserTradeWebVo> queryUsertradeList(PageInfo<FSimpleFtseUserTradeWebVo> page,
			ConnditionVo conn) {
		List<Object> params = new ArrayList<Object>();
		StringBuffer sqlBuf = new StringBuffer(
				  "SELECT uid, business_type,trader_bond,append_trader_bond,tran_fees,"
				+"tran_lever,tran_account,app_time,state_type,end_time "
			    +"FROM f_simple_ftse_user_trade WHERE  1=1 "); 
		if (conn != null) {
			String uid = TypeConvert.objToStrIsNotNull(conn.getValue("uid"));
			if (uid != null) {
				sqlBuf.append(" AND uid = ?");
				params.add(uid);
			}
		}
		
		sqlBuf.append(" ORDER BY state_type ASC,end_time DESC  ");
		return this.getEntityDao().queryPageBySql(page, sqlBuf.toString(), 
				FSimpleFtseUserTradeWebVo.class, conn,params.toArray());
	}
	
}

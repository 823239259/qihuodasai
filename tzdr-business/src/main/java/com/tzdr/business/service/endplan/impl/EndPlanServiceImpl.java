package com.tzdr.business.service.endplan.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jodd.util.StringUtil;

import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tzdr.business.service.account.AccountService;
import com.tzdr.business.service.account.ParentAccountService;
import com.tzdr.business.service.datamap.DataMapService;
import com.tzdr.business.service.endplan.EndPlanService;
import com.tzdr.business.service.exception.UserTradeConcurrentException;
import com.tzdr.business.service.userTrade.UserTradeService;
import com.tzdr.common.api.ihuyi.PgbSMSSender;
import com.tzdr.common.api.ihuyi.SMSSender;
import com.tzdr.common.baseservice.BaseServiceImpl;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.utils.ConnditionVo;
import com.tzdr.common.utils.TypeConvert;
import com.tzdr.domain.dao.userTrade.HandTradeDao;
import com.tzdr.domain.dao.wuser.WUserDao;
import com.tzdr.domain.vo.EndPlanVo;
import com.tzdr.domain.vo.ParentAccountExpireEndVo;
import com.tzdr.domain.web.entity.HandTrade;
import com.tzdr.domain.web.entity.UserTrade;
import com.tzdr.domain.web.entity.WUser;

/**
 * 
 * 
 * <p>结算</p>
 * @author LiuQing
 * @see
 * @version 2.0
 * 2015年5月11日上午10:37:17
 */
@Transactional(propagation=Propagation.REQUIRED)
@Service
public class EndPlanServiceImpl extends BaseServiceImpl<WUser, WUserDao> implements EndPlanService {
	@Autowired
	private DataMapService dataMapService;
	
	public static final Logger logger = LoggerFactory
			.getLogger(EndPlanService.class);
	
	@Override
	public PageInfo<EndPlanVo> queryEndPlan(PageInfo<EndPlanVo> page,
			ConnditionVo connVo) {
		StringBuffer sql = new StringBuffer(
				  " SELECT ut.* FROM "
				+ " ( SELECT t.activity_type activityType, w.mobile,(SELECT v.tname FROM w_user_verified v WHERE v.uid = w.id) tname,"
				+ " (SELECT min(w.account) FROM w_account w WHERE w.id = t.account_id) account,"
				+ " (SELECT min(w.account_name) FROM w_account w WHERE w.id = t.account_id) accountName,"
				+ " t.program_no,(IFNULL(t.lever_money,0) - IFNULL(t.voucher_actual_money,0)) as lever_money,t.voucher_actual_money,t.total_lever_money,h.end_sub_time,t.group_id ,t.fee_type feeType"
				+ " FROM w_user_trade t LEFT JOIN w_hand_trade h ON h.trade_id = t.id "
				+ " LEFT JOIN w_user w ON t.uid = w.id "
				+ " WHERE 1=1 AND t.fee_type in (2,3) AND h.audit_end_status = 0 AND h.finished_money IS NULL");
		sql.append(" GROUP BY t.program_no ");
		sql.append(" ) ut WHERE 1=1 ");
		List<Object> params = new ArrayList<Object>();
		
		// 判断方案类型
		String activityType = connVo.getValueStr("search_EQ_activityType");
		if (StringUtil.isNotBlank(activityType)) {
			sql.append(" AND ut.activityType = ?");
			params.add(NumberUtils.toShort(activityType));
		}
		
		String feeType = connVo.getValueStr("feeType");
		if (StringUtil.isNotBlank(feeType)) {
			sql.append(" AND ut.feeType = ?");
			params.add(NumberUtils.toShort(feeType));
		}
		
		String tname = connVo.getValueStr("tname");
		if (tname != null) {
			sql.append(" AND ut.tname LIKE ?");
			params.add(tname + "%");
		}
		String mobile = connVo.getValueStr("mobile");
		if (mobile != null) {
			sql.append(" AND ut.mobile LIKE ?");
			params.add(mobile + "%");
		}
		String endSubTimeStrStart = connVo.getValueStr("endSubTimeStr_start");
		String endSubTimeStrEnd = connVo.getValueStr("endSubTimeStr_end");
		if (endSubTimeStrStart != null && endSubTimeStrEnd != null) {
			sql.append(" AND (ut.end_sub_time >= ? AND ut.end_sub_time <= ?)");
			params.add(TypeConvert.strToDatetime1000Long(endSubTimeStrStart));
			params.add(TypeConvert.strToDatetime1000Long(endSubTimeStrEnd));
		}
		
		return this.getEntityDao().queryPageBySql(page,sql.toString(),
				EndPlanVo.class,connVo,params.toArray());
	}
	
	
	@Override
	public PageInfo<EndPlanVo> queryEndPlan02(PageInfo<EndPlanVo> page,
			ConnditionVo connVo) {
		StringBuffer sql = new StringBuffer(
				  " SELECT ut.* FROM "
				+ " ( SELECT t.activity_type activityType, w.mobile,(SELECT v.tname FROM w_user_verified v WHERE v.uid = w.id) tname,"
				+ " (SELECT min(w.account) FROM w_account w WHERE w.id = t.account_id) account,"
				+ " (SELECT min(w.account_name) FROM w_account w WHERE w.id = t.account_id) accountName,"
				+ " t.program_no,(IFNULL(t.lever_money,0) - IFNULL(t.voucher_actual_money,0)) as lever_money,t.voucher_actual_money,t.total_lever_money,h.end_sub_time,t.group_id,t.fee_type feeType"
				+ " FROM w_user_trade t LEFT JOIN w_hand_trade h ON h.trade_id = t.id "
				+ " LEFT JOIN w_user w ON t.uid = w.id "
				+ " WHERE 1=1 AND t.fee_type in (2,3) AND h.audit_end_status = 0 "
				+ " AND h.finished_money IS NOT NULL");
		sql.append(" GROUP BY t.group_id");
		sql.append(" ) ut WHERE 1=1 ");
		List<Object> params = new ArrayList<Object>();
		
		// 判断方案类型
		String activityType = connVo.getValueStr("search_EQ_activityType");
		if (StringUtil.isNotBlank(activityType)) {
			sql.append(" AND ut.activityType = ?");
			params.add(NumberUtils.toShort(activityType));
		}
		
		String feeType = connVo.getValueStr("feeType");
		if (StringUtil.isNotBlank(feeType)) {
			sql.append(" AND ut.feeType = ?");
			params.add(NumberUtils.toShort(feeType));
		}
		
		String tname = connVo.getValueStr("tname");
		if (tname != null) {
			sql.append(" AND ut.tname LIKE ?");
			params.add(tname + "%");
		}
		String mobile = connVo.getValueStr("mobile");
		if (mobile != null) {
			sql.append(" AND ut.mobile LIKE ?");
			params.add(mobile + "%");
		}
		String endSubTimeStrStart = connVo.getValueStr("endSubTimeStr_start");
		String endSubTimeStrEnd = connVo.getValueStr("endSubTimeStr_end");
		if (endSubTimeStrStart != null && endSubTimeStrEnd != null) {
			sql.append(" AND (ut.end_sub_time >= ? AND ut.end_sub_time <= ?)");
			params.add(TypeConvert.strToDatetime1000Long(endSubTimeStrStart));
			params.add(TypeConvert.strToDatetime1000Long(endSubTimeStrEnd));
		}
		
		return this.getEntityDao().queryPageBySql(page,sql.toString(),
				EndPlanVo.class,connVo,params.toArray());
	}
	
	
	
	@Override
	public PageInfo<EndPlanVo> queryAllEndPlan(PageInfo<EndPlanVo> page,
			ConnditionVo connVo) {
		StringBuffer sql = new StringBuffer(
				  " SELECT ut.* FROM "
				+ " ( SELECT t.activity_type activityType, w.mobile,(SELECT v.tname FROM w_user_verified v WHERE v.uid = w.id) tname,"
				+ " (SELECT min(w.account) FROM w_account w WHERE w.id = t.account_id) account,"
				+ " (SELECT min(w.account_name) FROM w_account w WHERE w.id = t.account_id) accountName,"
				+ " t.program_no,(IFNULL(t.lever_money,0) - IFNULL(t.voucher_actual_money,0)) as lever_money,t.voucher_actual_money,t.total_lever_money,h.end_sub_time,h.end_audit_time,"
				+ " h.audit_end_status, t.finished_money, t.fee_type feeType,"
				+ " (SELECT s.realname FROM sys_user s WHERE s.id = h.end_audit_user_id) endAuditUserIdStr "
				+ " FROM w_user_trade t LEFT JOIN w_hand_trade h ON h.trade_id = t.id "
				+ " LEFT JOIN w_user w ON t.uid = w.id "
				+ " WHERE 1=1 AND t.fee_type in (2,3) AND h.audit_end_status != -1");
		sql.append(" GROUP BY t.group_id");
		sql.append(" ) ut WHERE 1=1 ");
		List<Object> params = new ArrayList<Object>();
		
		// 判断方案类型
		String activityType = connVo.getValueStr("search_EQ_activityType");
		if (StringUtil.isNotBlank(activityType)) {
			sql.append(" AND ut.activityType = ?");
			params.add(NumberUtils.toShort(activityType));
		}
		
		String feeType = connVo.getValueStr("feeType");
		if (StringUtil.isNotBlank(feeType)) {
			sql.append(" AND ut.feeType = ?");
			params.add(NumberUtils.toShort(feeType));
		}
		
		String auditEndStatus = connVo.getValueStr("auditEndStatus");
		if (auditEndStatus != null && !"-1".equals(auditEndStatus)) {
			sql.append(" AND ut.audit_end_status = ?");
			params.add(Integer.parseInt(auditEndStatus));
		}
		
		String tname = connVo.getValueStr("tname");
		if (tname != null) {
			sql.append(" AND ut.tname LIKE ?");
			params.add(tname + "%");
		}
		String mobile = connVo.getValueStr("mobile");
		if (mobile != null) {
			sql.append(" AND ut.mobile LIKE ?");
			params.add(mobile + "%");
		}
		String endSubTimeStrStart = connVo.getValueStr("endSubTimeStr_start");
		String endSubTimeStrEnd = connVo.getValueStr("endSubTimeStr_end");
		if (endSubTimeStrStart != null && endSubTimeStrEnd != null) {
			sql.append(" AND (ut.end_sub_time >= ? AND ut.end_sub_time <= ?)");
			params.add(TypeConvert.strToDatetime1000Long(endSubTimeStrStart));
			params.add(TypeConvert.strToDatetime1000Long(endSubTimeStrEnd));
		}
		String endAuditTimeStrStart = connVo.getValueStr("endAuditTimeStr_start");
		String endAuditTimeStrEnd = connVo.getValueStr("endAuditTimeStr_end");
		if (endAuditTimeStrStart != null && endAuditTimeStrEnd != null) {
			sql.append(" AND (ut.end_audit_time >= ? AND ut.end_audit_time <= ?)");
			params.add(TypeConvert.strToDatetime1000Long(endAuditTimeStrStart));
			params.add(TypeConvert.strToDatetime1000Long(endAuditTimeStrEnd));
		}
		
		return this.getEntityDao().queryPageBySql(page,sql.toString(),
				EndPlanVo.class,connVo,params.toArray());
	}

	@Override
	public void updateEndPlanFail(ConnditionVo connVo) {
		String groupId = connVo.getValueStr("groupId");
		String failCause = connVo.getValueStr("failCause");
		if (groupId == null || "".equals(groupId)) {
			throw new UserTradeConcurrentException(
					"com.tzdr.business.agent.error", new String[]{"组合ID不能为空执行失败!"});
		}
		if (failCause == null || "".equals(failCause)) {
			throw new UserTradeConcurrentException(
					"com.tzdr.business.agent.error", new String[]{"失败原因不能为空执行失败!"});
		}
		
		List<UserTrade> userTrades = this.userTradeService.findByGroupId(groupId);
		for (UserTrade ut:userTrades) {
			List<HandTrade> handTrades = this.handTradeDao.findByTradeId(ut.getId());
			for (HandTrade ht:handTrades) {
				ht.setAuditEndStatus(TypeConvert.PLAN_NO_PASS);
				ht.setFailCause(failCause);
				ht.setFinishedMoney(null);
				this.handTradeDao.update(ht);
			}
		}
		/**
		 * 发送失败原因短信
		 */
		if (userTrades.size() > 0) {
			try {
				// 区分股票合买短信
				String activityType = connVo.getValueStr("activityType");
				UserTrade varUserTrade = userTrades.get(0);
				String mobile = varUserTrade.getWuser().getMobile();
				boolean flag = (UserTrade.ActivityType.TOGETHER_TRADE+"").equals(activityType);
				if (flag){
					SMSSender.getInstance().sendByTemplateClasspath(dataMapService.getSmsContentOthers(), "togetherPlanEnd01", mobile,varUserTrade.getGroupId(),failCause);
				}else{
					if (StringUtil.equals(activityType,String.valueOf(UserTrade.ActivityType.MONTH_TRADE))){
						PgbSMSSender.getInstance().sendByTemplateClasspath(dataMapService.getSmsContentOthers(), "monthEndFail", mobile,varUserTrade.getProgramNo(),failCause);
					}else
					{
						PgbSMSSender.getInstance().sendByTemplateClasspath(dataMapService.getSmsContentOthers(), "planEnd01", mobile,varUserTrade.getProgramNo(),failCause);
					}
				}
			}
			catch(Exception e) {e.printStackTrace();}
		}
		
		
		
	}
	
	@Override
	public void updateEndPlanSuccessful(ConnditionVo connVo) {
		String groupId = connVo.getValueStr("groupId");
		String amount = connVo.getValueStr("amount");
		BigDecimal amountValue = new BigDecimal(amount);
		amountValue = TypeConvert.scale(amountValue, TypeConvert.SCALE_VALUE);
		List<UserTrade> userTrades = this.userTradeService.findByGroupIdOrderByAddtimeAsc(groupId);
		UserTrade userTrade = userTrades.get(0);
		List<HandTrade> handTradeValidations = this.handTradeDao.findByTradeId(userTrade.getId());
		if (handTradeValidations != null) {
			for (HandTrade ht:handTradeValidations) {
				Double finishedMoney = ht.getFinishedMoney();
				if (finishedMoney != null) {
					BigDecimal finMoney = new BigDecimal(finishedMoney.toString());
					if (finMoney.compareTo(amountValue) != 0) {
						ht.setFinishedMoney(null);
						handTradeDao.update(ht);
						return;
					}
				}
				else {
					return;
				}
			}
			this.userTradeService.endOfWellGoldProgram(groupId,amountValue.doubleValue());
		}
	}
	
	@Override
	public void updateEndPlanSubmit(ConnditionVo connVo) {
		String groupId = connVo.getValueStr("groupId");
		String amount = connVo.getValueStr("amount");
		BigDecimal amountValue = new BigDecimal(amount);
		
		//校验
		if (groupId == null  || "".equals(groupId)) {
			throw new UserTradeConcurrentException(
					"com.tzdr.business.agent.error", new String[]{"组合ID不能为空执行失败!"});
		}
		if (amount == null || "".equals(amount) || amountValue == null) {
			throw new UserTradeConcurrentException(
					"com.tzdr.business.agent.error", new String[]{"结算金额不能为空或小于0!"});
		}
		
		List<UserTrade> userTrades = this.userTradeService.findByGroupId(groupId);
		if (userTrades != null) {
			for (UserTrade ut:userTrades) {
				List<HandTrade> handTrades = 
						this.handTradeDao.findByTradeId(ut.getId());
				if (handTrades != null) {
					for (HandTrade ht:handTrades) {
						if (amountValue != null) {
							ht.setFinishedMoney(amountValue.doubleValue());
						}
						else {
							ht.setFinishedMoney(null);
						}
						this.handTradeDao.update(ht);
					}
				}
			}
		}
	}
	
	@Override
	public PageInfo<ParentAccountExpireEndVo> queryParentAccountExpire(
			PageInfo<ParentAccountExpireEndVo> page, ConnditionVo connVo) {
		StringBuffer sql = new StringBuffer(
				  " SELECT ut.*  FROM ("
				+ " SELECT(SELECT MAX(w.mobile) FROM w_user w WHERE w.id = t.uid) mobile,"
				+ " (SELECT MAX(v.tname) FROM w_user_verified v WHERE v.uid = t.uid) tname,"
				+ " t.starttime,t.program_no,t.account,p.account_name parentAccountName,"
				+ " p.allocation_date,t.natural_days naturalDays,t.group_id "
				+ " FROM w_user_trade t,w_parent_account p,w_hand_trade h"
				+ " WHERE t.`status` = 1 AND t.parent_account_no = p.account_no  "
				+ " AND p.allocation_date <= ? "
				+ " AND "
				+ " ( (t.fee_type = 2  AND t.id = h.trade_id AND h.audit_end_status != 0 AND h.audit_end_status != 1) "
				+ "   OR (t.fee_type != 2)"
				+ " )");
		sql.append(" GROUP BY t.program_no");
		sql.append(" ) ut WHERE 1=1 ");
		List<Object> params = new ArrayList<Object>();
		params.add(TypeConvert.strToZeroDate1000(TypeConvert.dateToDateStr(new Date()), 5));
		String tname = connVo.getValueStr("tname");
		if (tname != null) {
			sql.append(" AND ut.tname LIKE ?");
			params.add(tname + "%");
		}
		String mobile = connVo.getValueStr("mobile");
		if (mobile != null) {
			sql.append(" AND ut.mobile LIKE ?");
			params.add(mobile + "%");
		}
		String account = connVo.getValueStr("account");
		if (account != null) {
			sql.append(" AND ut.account LIKE ?");
			params.add(account + "%");
		}
		
		return this.getEntityDao().queryPageBySql(page,sql.toString(),
				ParentAccountExpireEndVo.class,connVo,params.toArray());
	}
	
	@Override
	public void autoLimitParentAccount() {
		StringBuffer sql = new StringBuffer(
				  " SELECT ut.*  FROM ("
				+ " SELECT(SELECT MAX(w.mobile) FROM w_user w WHERE w.id = t.uid) mobile,"
				+ " (SELECT MAX(v.tname) FROM w_user_verified v WHERE v.uid = t.uid) tname,"
				+ " t.starttime,t.program_no,t.account,p.account_name parentAccountName,"
				+ " p.id parentAccountId,"
				+ " p.allocation_date,t.natural_days naturalDays,t.group_id "
				+ " FROM w_user_trade t,w_parent_account p"
				+ " WHERE t.`status` = 1 AND t.parent_account_no = p.account_no "
				+ " AND p.allocation_date <= ? "
				+ " AND "
				+ " (t.fee_type = 1 OR t.fee_type = 0)");
		sql.append(" GROUP BY t.group_id");
		sql.append(" ) ut WHERE 1=1 ");
		List<Object> params = new ArrayList<Object>();
		params.add(TypeConvert.strToZeroDate1000(TypeConvert.dateToDateStr(new Date()), 5));
		List<ParentAccountExpireEndVo> parentListVoes = this.getEntityDao().queryBySql(sql.toString(),
				ParentAccountExpireEndVo.class, null, params.toArray());
		if (parentListVoes != null && parentListVoes.size() > 0) {
			for (ParentAccountExpireEndVo parentAccountVo:parentListVoes) {
				if (parentAccountVo.getGroupId() != null) {
					userTradeService.changeOperationStatus(new String[]{parentAccountVo.getGroupId()}, 1,"system");
				}
			}
		}
		
	}
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private ParentAccountService parentAccountService;
	
	@Autowired
	private UserTradeService userTradeService;
	
	@Autowired
	private HandTradeDao handTradeDao;

	@Override
	public void programmeExtensionSendMessage() {
		
		StringBuffer sql = new StringBuffer(
				   " SELECT ut.*  FROM ("
				 + " SELECT(SELECT MAX(w.mobile) FROM w_user w WHERE w.id = t.uid) mobile,"
				 + " (SELECT MAX(v.tname) FROM w_user_verified v WHERE v.uid = t.uid) tname,"
				 + " t.starttime,t.program_no,t.account,p.account_name parentAccountName,"
				 + " p.id parentAccountId,"
				 + " p.allocation_date,t.natural_days naturalDays,t.group_id "
				 + " FROM w_user_trade t,w_parent_account p"
				 + " WHERE t.`status` = 1 AND t.parent_account_no = p.account_no  "
				 + " AND p.allocation_date <= ? AND p.allocation_date >= ?");
		sql.append(" GROUP BY t.group_id");
		sql.append(" ) ut WHERE 1=1 ");
		List<Object> params = new ArrayList<Object>();
		params.add(TypeConvert.strToZeroDate1000(TypeConvert.dateToDateStr(new Date()), 7));
		params.add(TypeConvert.strToZeroDate1000(TypeConvert.dateToDateStr(new Date()), 5));
		sql.append(" GROUP BY ut.mobile");
		List<ParentAccountExpireEndVo> parentListVoes = this.getEntityDao().queryBySql(sql.toString(),
				ParentAccountExpireEndVo.class, null, params.toArray());
		if (parentListVoes != null && parentListVoes.size() > 0) {
			logger.info("母账户到期日志：发送队列大小[" + parentListVoes.size() + "]");
			for (ParentAccountExpireEndVo parentAccountVo:parentListVoes) {
				if (parentAccountVo.getMobile() != null) {
					logger.info("方案号:" + parentAccountVo.getProgramNo());
					logger.info("母账户到期时间:" + parentAccountVo.getAllocationDateStr());
					logger.info("发送手机号：" + parentAccountVo.getMobile());
					//您的股票配资方案【变量】目前仅能延期至【变量】，请您在【变量】前平仓并终结方案
					long fiveDay = TypeConvert.strToZeroDate1000(
							TypeConvert.long1000ToDateStr(parentAccountVo.getAllocationDate()), -5);
					String content = TypeConvert.getActivityFileContent("prgrammeExtension",
							parentAccountVo.getProgramNo(),TypeConvert.long1000ToDateStr(fiveDay),
							TypeConvert.long1000ToDateStr(fiveDay));
					logger.info("发送内容：" + content);
					if (content != null) {
						PgbSMSSender sms = PgbSMSSender.getInstance();
						try {
							Thread.sleep(500);
						} 
						catch (InterruptedException e) {
							e.printStackTrace();
						}
						boolean stateValue = sms.send(dataMapService.getPgbSmsContentOthers(),parentAccountVo.getMobile(), content);
						if (stateValue) {
							logger.info("发送状态：" + "成功[successful...]");
						}
						else {
							logger.info("发送状态：" + "失败[fail...]");
						}
					}
				}
			}
			logger.info("短信提醒结束。。。。");
		}
		
	}

}
 
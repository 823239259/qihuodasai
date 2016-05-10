package com.tzdr.business.service.reports.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.tzdr.business.service.reports.EarningsReportService;
import com.tzdr.common.baseservice.BaseServiceImpl;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.utils.ConnditionVo;
import com.tzdr.common.utils.TypeConvert;
import com.tzdr.domain.dao.wuser.WUserDao;
import com.tzdr.domain.vo.EarningsVo;
import com.tzdr.domain.web.entity.WUser;

/**
 * 
 * 需要添加的
 * <p></p>
 * @author LiuQing
 * @see
 * @version 2.0
 * 2015年3月20日 下午1:12:31
 */
@Service
public class EarningsReportServiceImpl extends BaseServiceImpl<WUser, WUserDao> implements EarningsReportService {

	@Override
	public List<EarningsVo> queryDataListVo(ConnditionVo connVo) {
		List<String> paramNames = new ArrayList<String>();
		List<Object> params = new ArrayList<Object>();
		//5人工调账、银行 4、支付宝3
		StringBuffer sqlBuf = new StringBuffer(
			" SELECT utemp.*,CAST("
			+ " ifnull(freemoney,0)+ifnull(managerMoney,0)+ifnull(transmoney,0)+ifnull(interest,0) - ifnull(deductMoney,0)+ifnull(revokeManagerMoney,0)+ifnull(revokeInterest,0)+(case when profitMoney<0 then 0 else profitMoney end) AS DECIMAL(10, 2) "
			+ " ) as totalmoney FROM ("
				+ " SELECT t.activity_type activityType, ROUND(ifnull(wtt.profit_money, 0),2) profitMoney, w.mobile ,v.tname, (SELECT min(w.account_name) FROM w_account w WHERE w.id = t.account_id) account "
				+ " ,(SELECT min(p.account_name) FROM w_parent_account p WHERE p.account_no = t.parent_account_no) hsBelongBroker,t.program_no programNo"
				+ " ,(t.lever_money + t.append_lever_money) leverMoney"
				+ " ,t.lever lever "
				+ " ,(TO_DAYS(NOW()) - TO_DAYS(FROM_UNIXTIME(t.starttime, '%Y%m%d')) + 1) opDay"
				//+ " ,t.natural_days opDay"
				+ " ,CAST( (    "
				+ "       SELECT SUM(ABS(uf.money)) FROM w_user_fund uf "
				+ "       WHERE uf.rid = t.program_no AND uf.type = 12 AND uf.pay_status = 1 "
				+ "       AND uf.uptime	BETWEEN :beginTime AND :endTime) AS DECIMAL(10,2)"
				+ "  ) managerMoney"
				+ " ,CAST( (    "
				+ "       SELECT SUM(-uf.money) FROM w_user_fund uf "
				+ "       WHERE uf.rid = t.program_no AND uf.type = 25 AND uf.pay_status = 1 "
				+ "       AND uf.uptime	BETWEEN :beginTime AND :endTime) AS DECIMAL(10,2)"
				+ "  ) revokeManagerMoney"
				+ " ,CAST( (    "
				+ "       SELECT SUM(ABS(uf.money)) FROM w_user_fund uf "
				+ "       WHERE uf.rid = t.program_no AND uf.type = 11 AND uf.pay_status = 1    "
				+ "       AND  uf.uptime BETWEEN :beginTime AND :endTime) AS DECIMAL(10,2)"
				+ "     ) interest"
				+ " ,CAST( (    "
				+ "       SELECT SUM(-uf.money) FROM w_user_fund uf "
				+ "       WHERE uf.rid = t.program_no AND uf.type = 26 AND uf.pay_status = 1    "
				+ "       AND  uf.uptime BETWEEN :beginTime AND :endTime) AS DECIMAL(10,2)"
				+ "     ) revokeInterest"
				+ " ,CAST( (    "
				+ "       SELECT SUM(ABS(uf.money)) FROM w_user_fund uf "
				+ "       WHERE uf.rid = t.program_no AND uf.type = 24 AND uf.pay_status = 1    "
				+ "       AND  uf.uptime BETWEEN :beginTime AND :endTime) AS DECIMAL(10,2)"
				+ "     ) deductMoney"
				
				+ " ,CAST( (    "
				+ "       SELECT SUM(ABS(uf.money)) FROM w_user_fund uf "
				+ "       WHERE uf.rid = t.program_no AND uf.type = 13"
				+ "       AND uf.pay_status = 1    AND     uf.uptime BETWEEN :beginTime AND :endTime) AS DECIMAL(10,2)"
				+ " ) backMoney ,"
				+ "  CAST( (    "
				+ "    select SUM(d.money)  from w_free_diff d,w_account acc where d.type='1' "
				+ "       and d.account=acc.account_name and t.account_id=acc.id  "
				+ "  AND d.addtime BETWEEN :beginTime AND :endTime )"
				+ " AS DECIMAL(10, 2) ) freemoney ,t.group_id groupId ,"
				+ "  CAST( (    "
				+ "    select SUM(d.money)  from w_free_diff d,w_account acc where d.type='2' "
				+ "       and d.account=acc.account_name and t.account_id=acc.id  "
				+ "  AND d.addtime BETWEEN :beginTime AND :endTime )"
				+ " AS DECIMAL(10, 2) ) transmoney "
				+ " FROM w_user_trade t LEFT JOIN w_user w ON t.uid = w.id LEFT JOIN w_user_verified v ON v.uid = w.id "
				+ " LEFT JOIN w_together_trade wtt ON wtt.gid = t.group_id "
				// + " WHERE t.status = 1 OR (t.status = 2)AND t.starttime	"
				+ " WHERE ((t.`status`=1 OR t.`status`=0) and t.addtime <= :endTime)  "
				+ " OR (t.`status`=2 AND t.endtime >= :endTime AND t.addtime<= :endTime) "
				+ " OR (t.`status`=2 AND t.endtime >= :beginTime AND t.endtime<= :endTime)"
				// + " BETWEEN :beginTime AND :endTime "
		        + " ) utemp WHERE 1 = 1 AND utemp.lever IS NOT NULL");
		
		    paramNames.add("beginTime");
		    paramNames.add("endTime");
			String starttimeStr_start = connVo.getValueStr("starttimeStr_start");
			String starttimeStr_end = connVo.getValueStr("starttimeStr_end");
			if (starttimeStr_start != null && starttimeStr_end != null) {
				params.add(TypeConvert.strToZeroDate1000(starttimeStr_start,0));
				params.add(TypeConvert.strToZeroDate1000(starttimeStr_end,1,-1));
			}
			else {
				params.add(TypeConvert.longCriticalTimeDay(0));
				params.add(TypeConvert.strToZeroDate1000(TypeConvert.dbDateYmd(),1,-1));
			}
			
			String mobile = connVo.getValueStr("mobile");
			if (mobile != null) {
				paramNames.add("mobile");
				sqlBuf.append(" AND utemp.mobile LIKE :mobile");
				params.add(mobile + "%");
			}
			
			String tname = connVo.getValueStr("tname");
			if (tname != null) {
				paramNames.add("tname");
				sqlBuf.append(" AND utemp.tname LIKE :tname");
				params.add(tname + "%");
			}
			String account = connVo.getValueStr("account");
			if (account != null) {
				paramNames.add("account");
				sqlBuf.append(" AND utemp.account LIKE :account");
				params.add(account + "%");
			}
			String programNo = connVo.getValueStr("programNo");
			if (programNo != null) {
				paramNames.add("programNo");
				sqlBuf.append(" AND utemp.programNo LIKE :programNo");
				params.add(programNo + "%");
			}
			
			String groupId = connVo.getValueStr("groupId");
			if (groupId != null) {
				paramNames.add("groupId");
				sqlBuf.append(" AND utemp.groupId LIKE :groupId");
				params.add(groupId + "%");
			}
			// 方案类型
			String activityType = connVo.getValueStr("search_EQ_activityType");
			if (activityType != null) {
				paramNames.add("activityType");
				sqlBuf.append(" AND utemp.activityType = :activityType");
				params.add(activityType);
			}
			//sqlBuf.append(" ORDER BY utemp.programNo");
		return this.getEntityDao().queryDataByParamsSql(connVo.autoOrderBy(sqlBuf.toString(),EarningsVo.class),
				EarningsVo.class,null,paramNames.toArray(new String[paramNames.size()]), params.toArray());
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public PageInfo<EarningsVo> queryPageDataListVo(PageInfo<EarningsVo> dataPage,ConnditionVo connVo) {
		
		Map<String, Object> paramValues = new HashMap<String, Object>();
		
		//5人工调账、银行 4、支付宝3
		StringBuffer sqlBuf = new StringBuffer(
			" SELECT utemp.*,CAST("
			+ " ifnull(freemoney,0)+ifnull(managerMoney,0)+ifnull(transmoney,0)+ifnull(interest,0) - ifnull(deductMoney,0)+ifnull(revokeManagerMoney,0)+ifnull(revokeInterest,0)+(case when profitMoney<0 then 0 else profitMoney end) AS DECIMAL(10, 2) "
			+ " ) as totalmoney FROM ("
				+ " SELECT t.activity_type activityType, ROUND(ifnull(wtt.profit_money, 0),2) profitMoney, w.mobile ,v.tname, (SELECT min(w.account_name) FROM w_account w WHERE w.id = t.account_id) account "
				+ " ,(SELECT min(p.account_name) FROM w_parent_account p WHERE p.account_no = t.parent_account_no) hsBelongBroker,t.program_no programNo"
				+ " ,(t.lever_money + t.append_lever_money) leverMoney"
				+ " ,t.lever lever "
				+ " ,(TO_DAYS(NOW()) - TO_DAYS(FROM_UNIXTIME(t.starttime, '%Y%m%d')) + 1) opDay"
				+ " ,CAST( (    "
				+ "       SELECT SUM(ABS(uf.money)) FROM w_user_fund uf "
				+ "       WHERE uf.rid = t.program_no AND uf.type = 12 AND uf.pay_status = 1 "
				+ "       AND uf.uptime	BETWEEN :beginTime AND :endTime) AS DECIMAL(10,2)"
				+ "  ) managerMoney"
				+ " ,CAST( (    "
				+ "       SELECT SUM(-uf.money) FROM w_user_fund uf "
				+ "       WHERE uf.rid = t.program_no AND uf.type = 25 AND uf.pay_status = 1 "
				+ "       AND uf.uptime	BETWEEN :beginTime AND :endTime) AS DECIMAL(10,2)"
				+ "  ) revokeManagerMoney"
				+ " ,CAST( (    "
				+ "       SELECT SUM(ABS(uf.money)) FROM w_user_fund uf "
				+ "       WHERE uf.rid = t.program_no AND uf.type = 11 AND uf.pay_status = 1    "
				+ "       AND  uf.uptime BETWEEN :beginTime AND :endTime) AS DECIMAL(10,2)"
				+ "     ) interest"
				+ " ,CAST( (    "
				+ "       SELECT SUM(-uf.money) FROM w_user_fund uf "
				+ "       WHERE uf.rid = t.program_no AND uf.type = 26 AND uf.pay_status = 1    "
				+ "       AND  uf.uptime BETWEEN :beginTime AND :endTime) AS DECIMAL(10,2)"
				+ "     ) revokeInterest"
				+ " ,CAST( (    "
				+ "       SELECT SUM(ABS(uf.money)) FROM w_user_fund uf "
				+ "       WHERE uf.rid = t.program_no AND uf.type = 24 AND uf.pay_status = 1    "
				+ "       AND  uf.uptime BETWEEN :beginTime AND :endTime) AS DECIMAL(10,2)"
				+ "     ) deductMoney"
				
				+ " ,CAST( (    "
				+ "       SELECT SUM(ABS(uf.money)) FROM w_user_fund uf "
				+ "       WHERE uf.rid = t.program_no AND uf.type = 13"
				+ "       AND uf.pay_status = 1    AND     uf.uptime BETWEEN :beginTime AND :endTime) AS DECIMAL(10,2)"
				+ " ) backMoney ,"
				+ "  CAST( (    "
				+ "    select SUM(d.money)  from w_free_diff d,w_account acc where d.type='1' "
				+ "       and d.account=acc.account_name and t.account_id=acc.id  "
				+ "  AND d.addtime BETWEEN :beginTime AND :endTime )"
				+ " AS DECIMAL(10, 2) ) freemoney ,t.group_id groupId ,"
				+ "  CAST( (    "
				+ "    select SUM(d.money)  from w_free_diff d,w_account acc where d.type='2' "
				+ "       and d.account=acc.account_name and t.account_id=acc.id  "
				+ "  AND d.addtime BETWEEN :beginTime AND :endTime )"
				+ " AS DECIMAL(10, 2) ) transmoney "
				+ " FROM w_user_trade t LEFT JOIN w_user w ON t.uid = w.id LEFT JOIN w_user_verified v ON v.uid = w.id "
				+ " LEFT JOIN w_together_trade wtt ON wtt.gid = t.group_id "
				+ " WHERE ((t.`status`=1 OR t.`status`=0) and t.addtime <= :endTime)  "
				+ " OR (t.`status`=2 AND t.endtime >= :endTime AND t.addtime<= :endTime) "
				+ " OR (t.`status`=2 AND t.endtime >= :beginTime AND t.endtime<= :endTime)"
		        + " ) utemp WHERE 1 = 1 AND utemp.lever IS NOT NULL");
		
			String starttimeStr_start = connVo.getValueStr("starttimeStr_start");
			String starttimeStr_end = connVo.getValueStr("starttimeStr_end");
			if (starttimeStr_start != null && starttimeStr_end != null) {
				paramValues.put("beginTime", TypeConvert.strToZeroDate1000(starttimeStr_start,0));
				paramValues.put("endTime", TypeConvert.strToZeroDate1000(starttimeStr_end,1,-1));
			}
			else {
				paramValues.put("beginTime", TypeConvert.longCriticalTimeDay(0));
				paramValues.put("endTime", TypeConvert.strToZeroDate1000(TypeConvert.dbDateYmd(),1,-1));
			}
			
			String mobile = connVo.getValueStr("mobile");
			if (mobile != null) {
				sqlBuf.append(" AND utemp.mobile LIKE :mobile");
				paramValues.put("mobile", mobile + "%");
			}
			
			String tname = connVo.getValueStr("tname");
			if (tname != null) {
				sqlBuf.append(" AND utemp.tname LIKE :tname");
				paramValues.put("tname", tname + "%");
			}
			String account = connVo.getValueStr("account");
			if (account != null) {
				sqlBuf.append(" AND utemp.account LIKE :account");
				paramValues.put("account", account + "%");
			}
			String programNo = connVo.getValueStr("programNo");
			if (programNo != null) {
				sqlBuf.append(" AND utemp.programNo LIKE :programNo");
				paramValues.put("programNo", programNo + "%");
			}
			
			String groupId = connVo.getValueStr("groupId");
			if (groupId != null) {
				sqlBuf.append(" AND utemp.groupId LIKE :groupId");
				paramValues.put("groupId", groupId + "%");
			}
			// 方案类型
			String activityType = connVo.getValueStr("search_EQ_activityType");
			if (activityType != null) {
				sqlBuf.append(" AND utemp.activityType = :activityType");
				paramValues.put("activityType", activityType);
			}
			
		PageInfo<EarningsVo> pageInfo= getEntityDao().queryDataByParamsSql(dataPage, sqlBuf.toString(),EarningsVo.class,paramValues,null);
	
		return pageInfo;
	}

	@SuppressWarnings("unchecked")
	@Override
	public EarningsVo getDataTotalVo(ConnditionVo connVo) {
		
		List<String> paramNames = new ArrayList<String>();
		List<Object> params = new ArrayList<Object>();
		//5人工调账、银行 4、支付宝3
		StringBuffer sqlBuf = new StringBuffer(
			" SELECT sum(utemp.revokeManagerMoney) AS revokeManagerMoney,sum(utemp.revokeInterest) AS revokeInterest,sum(utemp.leverMoney) AS leverMoney,sum(utemp.lever) AS lever,sum(utemp.opDay) as opDay,sum(utemp.managerMoney) as managerMoney,"
			    + " sum(utemp.backMoney) AS backMoney,sum(utemp.profitMoney) AS profitMoney,sum(utemp.interest) AS interest,sum(utemp.freemoney) AS freemoney, "
			    + " sum(CAST("
			    + " ifnull(freemoney,0)+ifnull(managerMoney,0)+ifnull(transmoney,0)+ifnull(interest,0) - ifnull(deductMoney,0)+ifnull(revokeManagerMoney,0)+ifnull(revokeInterest,0)+(case when profitMoney<0 then 0 else profitMoney end) AS DECIMAL(10, 2) "
			    + " )) as totalmoney FROM ("
				+ " SELECT t.activity_type activityType, ROUND(ifnull(wtt.profit_money, 0),2) profitMoney, w.mobile ,v.tname, (SELECT min(w.account_name) FROM w_account w WHERE w.id = t.account_id) account "
				+ " ,(SELECT min(p.account_name) FROM w_parent_account p WHERE p.account_no = t.parent_account_no) hsBelongBroker,t.program_no programNo"
				+ " ,(t.lever_money + t.append_lever_money) leverMoney"
				+ " ,t.lever lever "
				+ " ,(TO_DAYS(NOW()) - TO_DAYS(FROM_UNIXTIME(t.starttime, '%Y%m%d')) + 1) opDay"
				+ " ,CAST( (    "
				+ "       SELECT SUM(ABS(uf.money)) FROM w_user_fund uf "
				+ "       WHERE uf.rid = t.program_no AND uf.type = 12 AND uf.pay_status = 1 "
				+ "       AND uf.uptime	BETWEEN :beginTime AND :endTime) AS DECIMAL(10,2)"
				+ "  ) managerMoney"
				+ " ,CAST( (    "
				+ "       SELECT SUM(-uf.money) FROM w_user_fund uf "
				+ "       WHERE uf.rid = t.program_no AND uf.type = 25 AND uf.pay_status = 1 "
				+ "       AND uf.uptime	BETWEEN :beginTime AND :endTime) AS DECIMAL(10,2)"
				+ "  ) revokeManagerMoney"
				+ " ,CAST( (    "
				+ "       SELECT SUM(ABS(uf.money)) FROM w_user_fund uf "
				+ "       WHERE uf.rid = t.program_no AND uf.type = 11 AND uf.pay_status = 1    "
				+ "       AND  uf.uptime BETWEEN :beginTime AND :endTime) AS DECIMAL(10,2)"
				+ "     ) interest"
				+ " ,CAST( (    "
				+ "       SELECT SUM(-uf.money) FROM w_user_fund uf "
				+ "       WHERE uf.rid = t.program_no AND uf.type = 26 AND uf.pay_status = 1    "
				+ "       AND  uf.uptime BETWEEN :beginTime AND :endTime) AS DECIMAL(10,2)"
				+ "     ) revokeInterest"
				+ " ,CAST( (    "
				+ "       SELECT SUM(ABS(uf.money)) FROM w_user_fund uf "
				+ "       WHERE uf.rid = t.program_no AND uf.type = 24 AND uf.pay_status = 1    "
				+ "       AND  uf.uptime BETWEEN :beginTime AND :endTime) AS DECIMAL(10,2)"
				+ "     ) deductMoney"
				
				+ " ,CAST( (    "
				+ "       SELECT SUM(ABS(uf.money)) FROM w_user_fund uf "
				+ "       WHERE uf.rid = t.program_no AND uf.type = 13"
				+ "       AND uf.pay_status = 1    AND     uf.uptime BETWEEN :beginTime AND :endTime) AS DECIMAL(10,2)"
				+ " ) backMoney ,"
				+ "  CAST( (    "
				+ "    select SUM(d.money)  from w_free_diff d,w_account acc where d.type='1' "
				+ "       and d.account=acc.account_name and t.account_id=acc.id  "
				+ "  AND d.addtime BETWEEN :beginTime AND :endTime )"
				+ " AS DECIMAL(10, 2) ) freemoney ,t.group_id groupId ,"
				+ "  CAST( (    "
				+ "    select SUM(d.money)  from w_free_diff d,w_account acc where d.type='2' "
				+ "       and d.account=acc.account_name and t.account_id=acc.id  "
				+ "  AND d.addtime BETWEEN :beginTime AND :endTime )"
				+ " AS DECIMAL(10, 2) ) transmoney "
				+ " FROM w_user_trade t LEFT JOIN w_user w ON t.uid = w.id LEFT JOIN w_user_verified v ON v.uid = w.id "
				+ " LEFT JOIN w_together_trade wtt ON wtt.gid = t.group_id "
				+ " WHERE ((t.`status`=1 OR t.`status`=0) and t.addtime <= :endTime)  "
				+ " OR (t.`status`=2 AND t.endtime >= :endTime AND t.addtime<= :endTime) "
				+ " OR (t.`status`=2 AND t.endtime >= :beginTime AND t.endtime<= :endTime)"
		        + " ) utemp WHERE 1 = 1 AND utemp.lever IS NOT NULL");
		
		    paramNames.add("beginTime");
		    paramNames.add("endTime");
			String starttimeStr_start = connVo.getValueStr("starttimeStr_start");
			String starttimeStr_end = connVo.getValueStr("starttimeStr_end");
			if (starttimeStr_start != null && starttimeStr_end != null) {
				params.add(TypeConvert.strToZeroDate1000(starttimeStr_start,0));
				params.add(TypeConvert.strToZeroDate1000(starttimeStr_end,1,-1));
			}
			else {
				params.add(TypeConvert.longCriticalTimeDay(0));
				params.add(TypeConvert.strToZeroDate1000(TypeConvert.dbDateYmd(),1,-1));
			}
			
			String mobile = connVo.getValueStr("mobile");
			if (mobile != null) {
				paramNames.add("mobile");
				sqlBuf.append(" AND utemp.mobile LIKE :mobile");
				params.add(mobile + "%");
			}
			
			String tname = connVo.getValueStr("tname");
			if (tname != null) {
				paramNames.add("tname");
				sqlBuf.append(" AND utemp.tname LIKE :tname");
				params.add(tname + "%");
			}
			String account = connVo.getValueStr("account");
			if (account != null) {
				paramNames.add("account");
				sqlBuf.append(" AND utemp.account LIKE :account");
				params.add(account + "%");
			}
			String programNo = connVo.getValueStr("programNo");
			if (programNo != null) {
				paramNames.add("programNo");
				sqlBuf.append(" AND utemp.programNo LIKE :programNo");
				params.add(programNo + "%");
			}
			
			String groupId = connVo.getValueStr("groupId");
			if (groupId != null) {
				paramNames.add("groupId");
				sqlBuf.append(" AND utemp.groupId LIKE :groupId");
				params.add(groupId + "%");
			}
			// 方案类型
			String activityType = connVo.getValueStr("search_EQ_activityType");
			if (activityType != null) {
				paramNames.add("activityType");
				sqlBuf.append(" AND utemp.activityType = :activityType");
				params.add(activityType);
			}
			
		List<EarningsVo> earningsVoList = this.getEntityDao().queryDataByParamsSql(connVo.autoOrderBy(sqlBuf.toString(),EarningsVo.class),
				EarningsVo.class,null,paramNames.toArray(new String[paramNames.size()]), params.toArray());
		
		EarningsVo earningsVo = null;
		
		if(earningsVoList != null && earningsVoList.size() > 0){
			earningsVo = earningsVoList.get(0);
		}
		
		return earningsVo;
	}

}

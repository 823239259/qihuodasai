package com.tzdr.business.service.generalize.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tzdr.business.service.generalize.AgentReturnInfoService;
import com.tzdr.business.service.generalize.GeneralizeService;
import com.tzdr.business.service.wuser.WUserService;
import com.tzdr.common.baseservice.BaseServiceImpl;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.utils.ConnditionVo;
import com.tzdr.common.utils.TypeConvert;
import com.tzdr.domain.dao.generalize.AgentReturnInfoDao;
import com.tzdr.domain.vo.AgentChildVo;
import com.tzdr.domain.vo.AgentReturnInfoCmsVo;
import com.tzdr.domain.vo.AgentReturnInfoVo;
import com.tzdr.domain.vo.ProgramAgentCmsVo;
import com.tzdr.domain.vo.ProgramTotalVo;
import com.tzdr.domain.web.entity.AgentReturnInfo;
import com.tzdr.domain.web.entity.WUser;

/**
 * 
 * 
 * <p></p>
 * @author LiuQing
 * @see 
 * @version 2.0
 * 2015年4月18日上午9:45:45
 */
@Service
public class AgentReturnInfoServiceImpl extends BaseServiceImpl<AgentReturnInfo, AgentReturnInfoDao> 
                                        implements AgentReturnInfoService {

	@Override
	public PageInfo<AgentReturnInfoVo> queryAgentReturnInfoVoByConn(
			PageInfo<AgentReturnInfoVo> page, ConnditionVo connVo) {
		page.setCurrentPage(page.getCurrentPage() + 1);
		if (page.getCurrentPage() <= 0) {
			page.setCurrentPage(1);
		}
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer(" SELECT a.* FROM w_agent_return_info a WHERE 1=1 AND a.uid = ?");
		
		if (connVo != null) {
			String uid = connVo.getValueStr("tzdrUser");
			if (uid != null) {
				params.add(uid);
			}
			else {
				page.setPageResults(null);
				return page;
			}
			String start = connVo.getValueStr("addtime_start");
			String end = connVo.getValueStr("addtime_end");
			if (start != null && end != null) {
				params.add(TypeConvert.strToZeroDate1000(start,0));
				params.add(TypeConvert.strToZeroDate1000(end,1,-1));
				sql.append(" AND a.add_date >= ? AND a.add_date <= ?");
			}
		}
		sql.append(" ORDER BY a.add_date desc ");
		return this.getEntityDao().queryPageBySql(page, sql.toString(),
				AgentReturnInfoVo.class, connVo, params.toArray());
	}
	
	
	@Override
	public PageInfo<AgentReturnInfoCmsVo>  queryAgentReturnInfoCmsVoByConn(
			PageInfo<AgentReturnInfoCmsVo> page, ConnditionVo connVo) {
		page.setCurrentPage(page.getCurrentPage() + 1);
		if (page.getCurrentPage() <= 0) {
			page.setCurrentPage(1);
		}
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer(
				  " SELECT a.uid,(SELECT w.mobile FROM w_user w WHERE w.id=a.uid)mobile,"
				+ " (SELECT v.tname FROM w_user_verified v WHERE v.uid=a.uid)tname,"
				+ " a.id,a.all_child_number,a.child_number,a.return_amount,a.total_amount,a.add_date,a.scheme_number"
				+ " FROM w_agent_return_info a "
				+ " WHERE 1=1 AND a.uid IN ("
				+ "    SELECT c.id FROM w_user c WHERE 1=1");
		
		if (connVo != null) {
			String mobile = connVo.getValueStr("mobile");
			if (mobile != null) {
				sql.append(" AND (c.parent_id IN((SELECT w.id FROM w_user w WHERE w.mobile LIKE ?)) or c.mobile LIKE ? )");
				params.add(mobile + "%");
				params.add(mobile + "%");
			}
			
			String superMobile = connVo.getValueStr("superMobile");
			if (superMobile != null) {
				sql.append(" AND c.id IN((SELECT w.parent_id FROM w_user w WHERE w.mobile LIKE ?))");
				params.add(superMobile + "%");
			}
			
			
			
			String tname = connVo.getValueStr("tname");
			if (tname != null) {
				sql.append(" AND c.parent_id IN((SELECT v.uid FROM w_user_verified v WHERE v.tname LIKE ?))");
				params.add(tname + "%");
			}
			String start = connVo.getValueStr("addtime_start");
			String end = connVo.getValueStr("addtime_end");
			if (start != null && end != null) {
				params.add(TypeConvert.strToZeroDate1000(start,0));
				params.add(TypeConvert.strToZeroDate1000(end,1,-1));
				sql.append(" AND a.add_date >= ? AND a.add_date <= ?");
			}
		}
		sql.append(")");
		return this.getEntityDao().queryPageBySql(page, sql.toString(),
				AgentReturnInfoCmsVo.class, connVo, params.toArray());
	}


	@Override
	public PageInfo<ProgramAgentCmsVo> queryProgramAgentCmsVoByConn(
			PageInfo<ProgramAgentCmsVo> page, ConnditionVo connVo) {
		
		
		if (page.getCurrentPage() <= 0) {
			page.setCurrentPage(1);
		}
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer(
				  " SELECT w.rid programId,(SELECT t.lever_money FROM w_user_trade t WHERE t.program_no=w.rid)leverMoney,"
				+ " (SELECT t.lever FROM w_user_trade t WHERE t.program_no=w.rid)lever,"
				+ " (SELECT t.starttime FROM w_user_trade t WHERE t.program_no=w.rid)starttime,"
				+ " (SELECT t.endtime FROM w_user_trade t WHERE t.program_no=w.rid)endtime "
				+ " FROM w_user_fund w WHERE w.type=12 AND w.pay_status = 1 AND (w.addtime >= ? AND w.addtime <= ?) "
				+ " AND w.rid IS NOT NULL ");
				//+ " AND w.uid IN(SELECT m.id FROM w_user m WHERE m.parent_id = ?) ");
		
		if (connVo != null) {
			String agentId = connVo.getValueStr("agentId");
			if (agentId != null) {
				AgentReturnInfo agentInfoVo = this.get(agentId);
				Integer addDate = agentInfoVo.getAddDate();
				String dateStr = TypeConvert.long1000ToDateStr(addDate.longValue());
				Long addtimeStart = TypeConvert.strToZeroDate1000(dateStr,0);
				Long addtimeEnd = TypeConvert.strToZeroDate1000(dateStr, 1, -1);
				params.add(addtimeStart);
				params.add(addtimeEnd);
				
				Map<String,String> uids = generalizeService.allChildUids(agentInfoVo.getUid());
				uids.put(agentInfoVo.getUid(), agentInfoVo.getUid());
				StringBuffer uidSql = new StringBuffer();
				for(Map.Entry<String, String> enValue:uids.entrySet()) {
					uidSql.append("'" + enValue.getKey() + "'").append(",");
				}
				if(uidSql.length() > 0) {
					sql.append(" AND w.uid IN(" + uidSql.substring(0, uidSql.length() - 1) + ")");
				}
				//params.add(agentInfoVo.getUid());
			}
			else {
				page.setPageResults(null);
				return page;
			}
		}
		sql.append(" GROUP BY w.rid");
		return this.getEntityDao().queryPageBySql(page, sql.toString(),
				ProgramAgentCmsVo.class, connVo, params.toArray());
	}
public static void main(String[] args) {
	String currentDateStr = TypeConvert.dateToDateStr(new Date());
	//昨天
	Date yesterday = TypeConvert.strToZeroDate(currentDateStr,-1);
	long yesterdayStartLong = TypeConvert.strToZeroDate1000(TypeConvert.dateToDateStr(yesterday), 0);
	long yesterdayEndLong = TypeConvert.strToZeroDate1000(TypeConvert.dateToDateStr(yesterday), 1,-1);
	System.out.println(yesterdayStartLong+","+yesterdayEndLong);
}

	@Override
	public void agentDayReturnIncomeExecute() {
		String currentDateStr = TypeConvert.dateToDateStr(new Date());
		//昨天
		Date yesterday = TypeConvert.strToZeroDate(currentDateStr, -1);
		long yesterdayStartLong = TypeConvert.strToZeroDate1000(TypeConvert.dateToDateStr(yesterday), 0);
		
		long yesterdayEndLong = TypeConvert.strToZeroDate1000(TypeConvert.dateToDateStr(yesterday), 1,-1);
		List<WUser> wUsers = this.wUserService.queryNotSystemWuser();
		
		Date today = TypeConvert.strToZeroDate(currentDateStr, 0);
		long todayStartLong = TypeConvert.strToZeroDate1000(TypeConvert.dateToDateStr(today), 0);
		deleteByAddtime(todayStartLong);
		
		long todayEndLong = TypeConvert.strToZeroDate1000(TypeConvert.dateToDateStr(today), 1,-1);
		
		for (WUser wuser:wUsers) {
			List<AgentChildVo> voes = 
					this.generalizeService.queryChildsCountByParentId(wuser.getId());
			int currentSize = 0;
			if (voes != null) {
			    currentSize = voes.size();
			}
			int allSize = this.generalizeService.queryChildsSize(wuser.getId());
			AgentReturnInfo agentReturnInfo = new AgentReturnInfo();
			agentReturnInfo.setUid(wuser.getId());
			//所有下级数量
			agentReturnInfo.setAllChildNumber(allSize);
			//直属下级数量
			agentReturnInfo.setChildNumber(currentSize);
			
			//
			agentReturnInfo.setAddDate((int)todayStartLong);
			List<ProgramTotalVo> totalVoes = 
					this.totalAmountByParentId(wuser.getId(), yesterdayStartLong, yesterdayEndLong);
			List<ProgramTotalVo> commissionIncomes = 
					this.commissionIncomeByUid(wuser.getId(), todayStartLong, todayEndLong);
			
		    Double money = 0D;
		    Integer programNum = 0;
		    Double returnAmount = 0D;
			if (totalVoes != null && totalVoes.size() > 0) {
				ProgramTotalVo totalVo = totalVoes.get(0);
				money = totalVo.getMoney();
				programNum = totalVo.getProgramNum();
				//方案数
				agentReturnInfo.setSchemeNumber(programNum);
			}
			
			if (commissionIncomes != null && commissionIncomes.size() > 0) {
				ProgramTotalVo totalVo = commissionIncomes.get(0);
				returnAmount = totalVo.getMoney();
				if (returnAmount != null) {
					agentReturnInfo.setReturnAmount(new BigDecimal(returnAmount));
				}
			}
			
			if (money != null) {
				//总金额
				agentReturnInfo.setTotalAmount(new BigDecimal(money));
			}
			this.save(agentReturnInfo);
			
		}
	}
	

	
	/**
	 * 计算出总金额、方案数合计
	 * @param parentId String
	 * @param addtimeStart Long
	 * @param addtimeEnd Long
	 * @return List<ProgramTotalVo>
	 */
	private List<ProgramTotalVo> totalAmountByParentId(String parentId,Long addtimeStart,Long addtimeEnd) {
		Map<String,String> uids = generalizeService.allChildUids(parentId);
		StringBuffer uidSql = new StringBuffer();
		for(Map.Entry<String, String> enValue:uids.entrySet()) {
			uidSql.append("'" + enValue.getKey() + "'").append(",");
		}
		StringBuffer sql = new StringBuffer(
				  " SELECT count(temp.rid)programNum,temp.pay_status,"
				+ " SUM((SELECT t.money FROM w_user_trade t WHERE t.program_no=temp.rid))money,"
				+ " temp.uid "
				+ " FROM (SELECT rid,w.pay_status,w.uid FROM w_user_fund w WHERE w.type=12 "
				+ " AND w.addtime >= ? AND w.addtime <= ? AND rid IS NOT NULL AND w.pay_status=1 "
				+ " GROUP BY rid)temp WHERE 1 = 1 ");
		
		if(uidSql.length() > 0) {
			sql.append(" AND temp.uid IN(" + uidSql.substring(0, uidSql.length() - 1) + ")");
		}
		return this.getEntityDao().queryBySql(sql.toString(), 
				ProgramTotalVo.class, null, addtimeStart,addtimeEnd);
	}
	
	/**
	 * 删除重复数据 
	 * @param addtimeStart Long
	 */
	private synchronized void deleteByAddtime(Long addtimeStart) {
		Map<String,Object> equals = new HashMap<String,Object>();
		equals.put("addDate", addtimeStart);
		List<AgentReturnInfo> returnInfoes = this.getEntityDao().queryBySimple(equals, null, null);
		for (AgentReturnInfo info:returnInfoes) {
			this.getEntityDao().delete(info);
		}
	}
	
	
	
	/**
	 * 计算出收入佣金
	 * @param uid String
	 * @param start Integer
	 * @param end Integer
	 * @return List<ProgramTotalVo>
	 */
	private List<ProgramTotalVo> commissionIncomeByUid(String uid,Long start,Long end) {
		StringBuffer sql = new StringBuffer(
				  " SELECT sum(w.money) money "
				+ " FROM w_user_fund w WHERE w.type = 13 AND w.pay_status = 1"
				+ " AND w.uid = ? AND (w.addtime >= ? AND w.addtime <= ?) " );
		return this.getEntityDao().queryBySql(sql.toString(), ProgramTotalVo.class, null, uid,start,end);
	}
	
	@Autowired
	private WUserService wUserService;
	
	@Autowired
	private GeneralizeService generalizeService;
	
	
	

	
}

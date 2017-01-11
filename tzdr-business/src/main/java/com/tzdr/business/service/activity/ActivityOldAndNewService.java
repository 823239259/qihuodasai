package com.tzdr.business.service.activity;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.tzdr.common.baseservice.BaseServiceImpl;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.utils.DateUtils;
import com.tzdr.domain.dao.activity.OldAndNewActivityDao;
import com.tzdr.domain.vo.activity.OldAndNewVo;
import com.tzdr.domain.web.entity.activity.OldAndNewStatistics;

import jodd.util.StringUtil;

@Service("activityOldAndNewService")
public class ActivityOldAndNewService extends BaseServiceImpl<OldAndNewStatistics, OldAndNewActivityDao> {
	public static final Logger logger = LoggerFactory.getLogger(ActivityOldAndNewService.class);
	
	
	@SuppressWarnings("unchecked")
	public PageInfo<OldAndNewVo> getOldAndNewVoList(int pageIndex,int perPage, String parentId,String mobile,String starttime,String endtime){
		PageInfo<Object> pageInfo = new PageInfo<Object>(perPage,pageIndex+1);
		
		StringBuffer sql = new StringBuffer();
		sql.append("select s.mobile,s.ctime,s.tname,s.end_time,s.sumLever from ");
		sql.append("(SELECT w.mobile mobile,w.ctime ctime,v.tname tname,min(f.end_time) end_time, ");
		sql.append("sum(IFNULL(f.tran_actual_lever,0)+IFNULL(f.crude_tran_actual_lever,0)+IFNULL(f.hsi_tran_actual_lever,0)+IFnull(f.mdtran_actual_lever,0)+ ");
		sql.append("IFNULL(f.mntran_actual_lever,0)+IFNULL(f.mbtran_actual_lever,0)+IFNULL(f.daxtran_actual_lever,0)+IFNULL(f.nikkei_tran_actual_lever,0)+ ");
		sql.append("IFNULL(f.ag_tran_actual_lever,0)+IFNULL(f.lhsi_tran_actual_lever,0)+IFNULL(f.ame_copper_market_lever,0)+IFNULL(f.ame_silver_market_lever,0)+ ");
		sql.append("IFNULL(f.h_stock_market_lever,0)+IFNULL(f.small_crude_oil_market_lever,0)+IFNULL(f.xhstock_market_lever,0)+IFNULL(f.daxtran_min_actual_lever,0)) sumLever ");
		sql.append("FROM w_user w inner join w_user_verified v on w.id = v.uid inner join f_simple_ftse_user_trade f on f.uid=w.id where w.parent_id = '"+parentId+"' ");
		
		if(StringUtil.isNotBlank(mobile)){
			sql.append(" and SUBSTRING(w.mobile,-4) = '"+mobile+"' ");
		}
		if(StringUtil.isNotBlank(starttime)){
			Date startdate=DateUtils.stringToDate(starttime, "yyyy-MM-dd");
			long sdate=startdate.getTime()/1000;
			sql.append(" and w.ctime >= '"+sdate+"' ");
		}
		if(StringUtil.isNotBlank(endtime)){
			endtime+=" 23:59:59";
			Date enddate=DateUtils.stringToDate(endtime, "yyyy-MM-dd HH:mm:ss");
			long edate=enddate.getTime()/1000;
			sql.append(" and w.ctime <= '"+edate+"'");
		}
		sql.append("  GROUP BY  f.uid )s where s.tname <> '' and s.sumLever != 0 and s.end_time is not null GROUP BY s.mobile");
		sql.append(" order by s.ctime desc");
		PageInfo<OldAndNewVo> oldAndNewVos = this.getEntityDao().queryPageBySql(pageInfo, sql.toString(), OldAndNewVo.class,null,null);
		//计算总条数和总页数
		List<OldAndNewVo> oldAndNewVoList = this.getEntityDao().queryListBySql(sql.toString(),OldAndNewVo.class,null,null);
		if(oldAndNewVoList==null){
			pageInfo.setTotalCount(0);
			pageInfo.setTotalPage((long)((0 + perPage) - 1) / perPage);
		}else{
			pageInfo.setTotalCount(oldAndNewVoList.size());
			pageInfo.setTotalPage((long)((oldAndNewVoList.size() + perPage) - 1) / perPage);
		}

		return  oldAndNewVos;
	}
	
	
	/**
	 * 某一位用户 的 老带新活动统计
	 * @param parentId 
	 */
	public Map<String,Object> getActivityStatistics(String parentId){
		Map<String,Object> data = new HashMap<String,Object>();
		//查询推广 注册的用户个数
		String sql1 = " select count(id) from w_user where parent_id = '"+parentId+"' ";
		Object registNum = this.nativeQueryOne(sql1,null);
		data.put("registNum", registNum);
		//查询推广 申请过方案的用户个数
		String sql2 = " select w.id from w_user w left join f_simple_ftse_user_trade f on w.id = f.uid where parent_id = '"+parentId+"' and f.state_type = 6 group by w.id ";
		List ftradeNum = this.nativeQuery(sql2,null);
		data.put("ftradeNum", ftradeNum.size());
		//统计用户已实名认证并且方案状态为已结算的方案手数 按照申请方案时间排序
		String sql3 = "SELECT w.id, f.app_time,sum(IFNULL(f.tran_actual_lever, 0) + IFNULL(f.crude_tran_actual_lever,0) + IFNULL(f.hsi_tran_actual_lever, 0) + IFnull(f.mdtran_actual_lever, 0) "
				+ "+ IFNULL(f.mntran_actual_lever, 0) + IFNULL(f.mbtran_actual_lever, 0) + IFNULL(f.daxtran_actual_lever, 0) + IFNULL(f.nikkei_tran_actual_lever,0) + IFNULL(f.ag_tran_actual_lever, 0) "
				+ "+ IFNULL(f.lhsi_tran_actual_lever, 0) + IFNULL(f.ame_copper_market_lever,0) + IFNULL(f.ame_silver_market_lever,0) + IFNULL(f.h_stock_market_lever, 0) + IFNULL(f.small_crude_oil_market_lever,0) "
				+ "+ IFNULL(f.xhstock_market_lever, 0) + IFNULL(f.daxtran_min_actual_lever,0)) lever "
				+ "FROM w_user w Left JOIN w_user_verified v on w.id = v.uid LEFT JOIN f_simple_ftse_user_trade f ON w.id = f.uid "
				+ "WHERE w.parent_id = '"+parentId+"' and v.tname <>  '' and f.state_type = 6 GROUP BY w.id  ORDER BY f.app_time;";
		List<Map<String, Object>>  awards = this.nativeQuery(sql3,null);
		Integer sum  = 0;
		for (int i = 0; i < awards.size(); i++) {
			Map<String, Object>  map = awards.get(i);
			Integer lever = Integer.parseInt(String.valueOf(map.get("lever")));
			if(lever >= 5){
				if(i == 0 || i == 1){
					sum = sum + 1;
				}
				else
					sum = sum + 2;
			}
		}
		data.put("awardSum",sum );
		return data;
	}
	
	public void getActivityStatistics(){
		String sql = "SELECT v.tname, g2.mobile, g2.id id, g2.lever lever, g2.parent_id FROM "
				+ "(SELECT w.id, g1.lever, w.parent_id, w.mobile FROM "
				+ "( SELECT f.uid, sum( IFNULL(f.tran_actual_lever, 0) + IFNULL( f.crude_tran_actual_lever, 0 ) + IFNULL(f.hsi_tran_actual_lever, 0) "
				+ "+ IFnull(f.mdtran_actual_lever, 0) + IFNULL(f.mntran_actual_lever, 0) + IFNULL(f.mbtran_actual_lever, 0) + IFNULL(f.daxtran_actual_lever, 0) "
				+ "+ IFNULL( f.nikkei_tran_actual_lever, 0 ) + IFNULL(f.ag_tran_actual_lever, 0) + IFNULL(f.lhsi_tran_actual_lever, 0) "
				+ "+ IFNULL( f.ame_copper_market_lever, 0 ) + IFNULL( f.ame_silver_market_lever, 0 ) + IFNULL(f.h_stock_market_lever, 0) "
				+ "+ IFNULL( f.small_crude_oil_market_lever, 0 ) + IFNULL(f.xhstock_market_lever, 0) + IFNULL( f.daxtran_min_actual_lever, 0 )) lever "
				+ "FROM f_simple_ftse_user_trade f WHERE f.state_type = 6 GROUP BY f.uid ) g1 "
				+ "INNER JOIN w_user w ON w.id = g1.uid ) g2 INNER JOIN w_user_verified v ON v.uid = g2.id WHERE v.tname <> '';";
		List<Map<String, Object>>  lists = this.nativeQuery(sql,null);
		
		for (int i = 0; i < lists.size(); i++) {
			int registNum = 0;
			int ftradeGt5 = 0;
			int ftradeLt5 = 0;
			Map<String, Object> map = lists.get(i);
			String realName = String.valueOf(map.get("tname"));
			String mobile = String.valueOf(map.get("mobile"));
			String id = String.valueOf(map.get("id"));
			
			OldAndNewStatistics statistics = new OldAndNewStatistics();
			statistics.setRealName(realName);
			statistics.setMobile(mobile);
			
			for (int j = 0; j < lists.size(); j++) {
				 Map<String, Object> map2 = lists.get(j);
				 String parentId = String.valueOf(map2.get("parent_id"));
				 if(id.equals(parentId)){
					 registNum++ ;
					 int lever = Integer.parseInt(String.valueOf(map2.get("lever")));
					 if(lever >= 5)
						 ftradeGt5 ++;
					 else 
					     ftradeLt5 ++;
				 }
			}
			if(registNum != 0){
				statistics.setRegistNum(registNum);
				statistics.setFtradeGt5(ftradeGt5);
				statistics.setFtradeLt5(ftradeLt5);
				statistics.setFtradeNum(ftradeGt5 + ftradeLt5);
				statistics.setAwardSum(ftradeGt5 / 3 > 0 ? ftradeGt5 * 2 - 2 : ftradeGt5);
				this.getEntityDao().save(statistics);
			}
		}
	}
	
}

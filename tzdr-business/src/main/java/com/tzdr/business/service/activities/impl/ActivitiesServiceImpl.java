package com.tzdr.business.service.activities.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.engine.spi.SessionImplementor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.tzdr.business.service.activities.ActivitiesService;
import com.tzdr.common.baseservice.BaseServiceImpl;
import com.tzdr.domain.dao.activity.ActivityProfitRecordDao;
import com.tzdr.domain.vo.ActivityProfitRecordVo;
import com.tzdr.domain.web.entity.ActivityProfitRecord;


/**
 * 活动实现类
 * <P>title:@ActivitiesServiceImpl.java</p>																								
 * <P>Description：</p>
 * <p>Copyright: Copyright (c) 2015 tzdr</p>
 * <p>Company: 上海信闳</p>
 * History：
 * @author:zhangjun
 * @date 2015年2月7日
 * @version 1.0
 */
@Service("activitiesService")
@Transactional
public class ActivitiesServiceImpl extends BaseServiceImpl<ActivityProfitRecord,ActivityProfitRecordDao> implements ActivitiesService{
	public static final Logger logger = LoggerFactory.getLogger(ActivitiesServiceImpl.class);

	@Autowired
	private ActivityProfitRecordDao activityProfitRecordDao;
	@PersistenceContext
    private EntityManager em;
	/*
	 * (non-Javadoc)
	 * @see com.tzdr.business.service.activities.ActivitiesService#getActivities(java.lang.Long)
	 */
	@Override
	public List<ActivityProfitRecordVo> getActivities(Long date) {
		String sql=" select concat(substring(pe.mobile,1,3),'****',right(pe.mobile,4)) as mobile, pe.profit, pe.profit_rate from w_activity_profit_record "; 
				 sql+="pe where profit_date=? order by pe.profit desc limit 10  ";
		List<Object> params = Lists.newArrayList();
		params.add(date);
		List<ActivityProfitRecordVo> records = nativeQuery(sql, params, ActivityProfitRecordVo.class);
		return records;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.tzdr.business.service.activities.ActivitiesService#getGrowActivities(java.lang.Long, java.lang.Long)
	 */
	@Override
	public 	List<Map<String,String>> getGrowActivities(Long yesterday,
			Long beforeyesterday) {
		String  sql="select concat(substring(s.mobile,1,3),'****',right(s.mobile,4)) as mobile,\r"
				+ " (t.rownum-s.rownum) as grownum,s.profit_rate  from ( \r";
				sql+="select @rownum:=@rownum+1 as rownum, w_activity_profit_record.* \r";
				sql+="FROM (select @rownum:=0) r, w_activity_profit_record where \r";
				sql+="profit_date=? ORDER BY profit desc ) t,( \r";
				sql+="select @rownum1:=@rownum1+1 as rownum, w_activity_profit_record.* \r";
				sql+="FROM (select @rownum1:=0) r, w_activity_profit_record where \r";
				sql+="profit_date=? order  by profit desc) s where \r";
				sql+="t.mobile=s.mobile order  by (t.rownum-s.rownum) desc limit 10 \r";
				List<Object> params = Lists.newArrayList();
				params.add(beforeyesterday);
				params.add(yesterday);
				List<Map<String,String>> datas=this.getSqlData(sql, params);
				return datas;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.tzdr.business.service.activities.ActivitiesService#getStockData()
	 */
	@Override
	public List<ActivityProfitRecordVo> getStockData() {
		String sql="select stock_code,count(*) as countnum "
				+ "from w_realdeal  where stock_code!='' "
				+ " and stock_code is not null group by stock_code order by count(*) desc   limit 6";
		 List<Object> params = Lists.newArrayList();
		 List<ActivityProfitRecordVo> records = nativeQuery(sql, params, ActivityProfitRecordVo.class);
		
		 return records;
		
	}
	
	/**
	 * jdbc方式查询
	 * @param sql
	 * @param param
	 * @return
	 * @date 2015年2月10日
	 * @author zhangjun
	 */
	private List<Map<String,String>> getSqlData(String sql,List<Object> param){
		   List<Map<String,String>> list=new ArrayList<Map<String,String>>();
		   SessionImplementor session =em.unwrap(SessionImplementor.class);
	   	   Connection conn =   session.connection();
	   	   ResultSet rs=null;
	   	   PreparedStatement pstmt=null;
		   try {
				pstmt = conn.prepareStatement(sql);
				int k=1;
				for(Object val:param){
					if(val instanceof String)
						pstmt.setString(k, (String)val); 
					else if(val instanceof Long)
						pstmt.setLong(k, (Long)val); 
					else if(val instanceof Double)
						pstmt.setDouble(k, (Double)val);
					else if(val instanceof Float)
						pstmt.setFloat(k, (Float)val); 
					else if(val instanceof Integer)
						pstmt.setInt(k, (Integer)val); 
					k++;
				}
	            rs=pstmt.executeQuery(); 
				ResultSetMetaData meta = rs.getMetaData();
				int columnCount = meta.getColumnCount();
				while(rs.next()) {
					Map<String, String> map = new LinkedHashMap<String, String>();
					for(int i=1; i<=columnCount; i++) {
						String colName = meta.getColumnLabel(i).toLowerCase();
						String colValue = rs.getString(i);
						map.put(colName, colValue);
					}
					list.add(map);
				}
			
		} catch (SQLException e) {
			logger.error("查询异常sql"+sql+",error"+e.getMessage());
		}finally{
			free(conn, pstmt, rs);
		}
	   return list;
	}

	
	
	/**  
	 * 关闭连接资源  
	 * @param conn  
	 */
	private static void free(Connection conn, Statement st, ResultSet rs) {
		if(rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(st != null) {
			try {
				st.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	
}

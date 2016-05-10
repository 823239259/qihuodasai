package com.tzdr.domain.dao.future;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tzdr.common.dao.BaseJpaDao;
import com.tzdr.domain.web.entity.future.FuserTrade;


/**
 * 
 * 
 * <p></p>
 * @author LiuQing
 * @see
 * @version 2.0
 * 2015年7月23日下午4:14:15
 */
public interface FuserTradeDao extends BaseJpaDao<FuserTrade, String> {
	
	@Query(value="SELECT SUM(caution_money) AS cautionMoney FROM f_user_trade WHERE f_aid=:faid AND actual_type=:actualType AND state=:state",nativeQuery=true)
	public BigDecimal queryCautionMoney(@Param("faid")String faid, @Param("state")Integer state, @Param("actualType")Integer actualType);
	
	@Query(value="SELECT count(*) FROM f_user_trade WHERE f_aid=:faid AND actual_type=:actualType AND state=:state", nativeQuery=true)
	public int getRowCount(@Param("faid")String faid, @Param("state")Integer state, @Param("actualType")Integer actualType);
	
	@Query(value="SELECT SUM(IF(profit_state=1,business_money,0)-IF(profit_state=-1,business_money,0)) AS businessMoney FROM f_user_trade WHERE f_aid=:faid AND actual_type=:actualType AND state=:state",nativeQuery=true)
	public BigDecimal queryBusinessMoney(@Param("faid")String faid, @Param("state")Integer state, @Param("actualType")Integer actualType);
	
	@Query(value="SELECT SUM(IF(profit_state=1,gain_money,0)-IF(profit_state=-1,gain_money,0)) AS gainMoney FROM f_user_trade WHERE f_aid=:faid AND actual_type=:actualType AND state=:state", nativeQuery=true)
	public BigDecimal queryGainMoney(@Param("faid")String faid, @Param("state")Integer state, @Param("actualType")Integer actualType);
}

package com.tzdr.domain.dao.future;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tzdr.common.dao.BaseJpaDao;
import com.tzdr.domain.web.entity.future.FcapitalInfo;


/**
 * 
 * 
 * <p></p>
 * @author LiuQing
 * @see
 * @version 2.0
 * 2015年7月23日下午4:14:15
 */
public interface FcapitalInfoDao extends BaseJpaDao<FcapitalInfo, String> {

	@Query(value="SELECT SUM((IF(fci.business_type=1,fci.trade_money,0)-IF(fci.business_type=2,fci.trade_money,0)+IF(fci.business_type=41,fci.trade_money,0))) as money FROM f_capital_info fci WHERE fci.f_aid=:faid AND fci.state=1", nativeQuery=true)
	public BigDecimal queryCumulativeTrans(@Param("faid")String faid);
	
}

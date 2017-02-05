package com.tzdr.domain.dao.userTrade;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.tzdr.common.dao.BaseJpaDao;
import com.tzdr.domain.web.entity.FSimpleParities;

/**
 * 
 * 
 * <p></p>
 * @author WangPinQun
 * @see 富时A50抢险版汇率信息
 * FSimpleParitiesDao
 * @version 2.0
 * 2015年9月16日下午14:33:13
 */
public interface FSimpleParitiesDao extends BaseJpaDao<FSimpleParities, String> {
	
	@Query("from FSimpleParities F where F.type=?1 order By F.addTime desc")
	public List<FSimpleParities> findFSimpleParitiess(Integer type); 
}

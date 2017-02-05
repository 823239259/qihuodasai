package com.tzdr.domain.dao.userTrade;


import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.tzdr.common.dao.BaseJpaDao;
import com.tzdr.domain.web.entity.FSimpleConfig;

/**
 * 
 * 
 * <p></p>
 * @author WangPinQun
 * @see 富时A50抢险版方案配置信息
 * FSimpleConfigDao
 * @version 2.0
 * 2015年9月16日下午14:33:13
 */
public interface FSimpleConfigDao extends BaseJpaDao<FSimpleConfig, String> {
	
	public List<FSimpleConfig>  findByType(Integer type);
	
	@Query("from FSimpleConfig F where F.type=?1 order By convert(F.tranLever,unsigned) asc ")
	public List<FSimpleConfig> findFSimpleConfigsByType(Integer type); 
	
	@Query("from FSimpleConfig F where F.type=?1 and F.tranLever=?2 ")
	public FSimpleConfig getFSimpleConfig(Integer type,String tranLever);
}

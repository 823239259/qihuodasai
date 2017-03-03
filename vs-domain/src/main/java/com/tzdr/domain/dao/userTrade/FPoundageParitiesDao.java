package com.tzdr.domain.dao.userTrade;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import com.tzdr.common.dao.BaseJpaDao;
import com.tzdr.domain.web.entity.FPoundageParities;

public interface FPoundageParitiesDao extends BaseJpaDao<FPoundageParities, String> {
	@Query("from FPoundageParities F where F.type=?1 order By F.addTime desc")
	public List<FPoundageParities> findFPoundageParitiess(Integer type); 
	
 }

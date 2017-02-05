package com.tzdr.domain.dao.comprehensiveCommodity;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.tzdr.common.dao.BaseJpaDao;
import com.tzdr.domain.web.entity.ComprehensiveCommodityParameter;


/**
 * @description
 * @Author huangkai
 * @Date 2016-03-29
 */
public interface ComprehensiveCommodityParameterDao extends BaseJpaDao<ComprehensiveCommodityParameter, String> {
	
	/**
	 * @Author Guo Xingyou
	 * @return
	 */
	@Query("from ComprehensiveCommodityParameter order by traderBond")
	List<ComprehensiveCommodityParameter> findAllOrderByTraderBond();
	List<ComprehensiveCommodityParameter> findByTraderBond(BigDecimal traderBond);
}

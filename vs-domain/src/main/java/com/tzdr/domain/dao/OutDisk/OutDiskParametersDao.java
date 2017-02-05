package com.tzdr.domain.dao.OutDisk;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.tzdr.common.dao.BaseJpaDao;
import com.tzdr.domain.web.entity.OutDiskParameters;
/**
 * 国际综合参数设置
 * 类说明
 * @author  zhaozhao
 * @date    2016年2月23日上午8:45:07
 * @version 1.0
 */
public interface OutDiskParametersDao extends BaseJpaDao<OutDiskParameters, String>{

	
	@Query("from OutDiskParameters order by traderBond")
	List<OutDiskParameters> findAllOrderByTraderBond();

	List<OutDiskParameters> findByTraderBond(BigDecimal traderBond);

}

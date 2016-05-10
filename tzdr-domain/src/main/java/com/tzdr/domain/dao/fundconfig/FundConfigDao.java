package com.tzdr.domain.dao.fundconfig;

import java.util.List;

import com.tzdr.common.dao.BaseJpaDao;
import com.tzdr.domain.web.entity.FundConfig;

/**
 * 配资倍数和金额持久层
 * @ClassName FundConfigDao
 * @author L.Y
 * @email meiluohai@163.com
 * @date 2015年4月27日
 */
public interface FundConfigDao extends BaseJpaDao<FundConfig, String> {
	
	public FundConfig findByTimes(int times);
	
	public List<FundConfig> findByIdIsNotNullOrderByTimesAsc();

}
package com.tzdr.domain.dao.realdeal;


import java.util.List;

import com.tzdr.common.dao.BaseJpaDao;
import com.tzdr.domain.web.entity.RealdealEntity;

/**
 * 
 * @zhouchen
 * 2015年1月17日
 */
public interface RealdealDao extends BaseJpaDao<RealdealEntity,String> {

	/**
	 * 查询具体某一天的 成交记录
	 * @param initDate
	 * @return
	 */
	public List<RealdealEntity> findByInitDate(long initDate);
	
	/**
	 * 查询具体一个区间的 成交记录
	 * @param initDate
	 * @return
	 */
	public List<RealdealEntity> findByFundAccountAndCombineIdAndInitDateBetween(String fundAccount,String combineId,long start,long end);
}

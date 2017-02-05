package com.tzdr.domain.dao.stock;


import java.util.List;

import com.tzdr.common.dao.BaseJpaDao;
import com.tzdr.domain.web.entity.UserCombostock;

/**
 * 用户持仓查询
 * @zhouchen
 * 2014年12月26日
 */
public interface UserCombostockDao extends BaseJpaDao<UserCombostock, String> {
	/**
	 * 查询具体某一天的 持仓
	 * @param initDate
	 * @return
	 */
	public List<UserCombostock> findByInitDate(long initDate);
}
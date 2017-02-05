package com.tzdr.domain.dao.stock;


import java.util.List;

import com.tzdr.common.dao.BaseJpaDao;
import com.tzdr.domain.web.entity.Stock;

/**
 * 
 * @zhouchen
 * 2014年12月26日
 */
public interface StockDao extends BaseJpaDao<Stock, String> {
	
	public List<Stock>  findByCodeAndDeletedFalseAndType(String code,Integer type);
	
	public List<Stock>  findByDeletedFalseAndType(Integer type);
}
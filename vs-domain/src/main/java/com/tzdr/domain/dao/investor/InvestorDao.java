package com.tzdr.domain.dao.investor;


import java.util.List;

import com.tzdr.common.dao.BaseJpaDao;
import com.tzdr.domain.web.entity.Investor;

/**
 * 
 * @zhouchen
 * 2014年12月26日
 */
public interface InvestorDao extends BaseJpaDao<Investor, String> {
	
	public List<Investor>  findByIdCardAndDeletedFalse(String idCard);
	
}
package com.tzdr.domain.dao.pay;


import java.util.List;

import com.tzdr.common.dao.BaseJpaDao;
import com.tzdr.domain.web.entity.PaymentSupportBank;


/**
 * 支付 支持银行 dao
 * @zhouchen
 * 2015年11月30日
 */
public interface PaymentSupportBankDao extends BaseJpaDao<PaymentSupportBank, String> {

	/**
	 * 根据银行简称获取银行信息
	 * @param abbreviation
	 * @return
	 */
	public List<PaymentSupportBank> findByAbbreviation(String abbreviation);
	
}

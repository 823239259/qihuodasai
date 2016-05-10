package com.tzdr.domain.pgb.dao;

import java.util.List;

import com.tzdr.common.dao.BaseJpaDao;
import com.tzdr.domain.pgb.entity.PGBPaymentSupportBank;
/**
 * 配股宝支付 支持银行 dao
 * @author zhaozhao
 *
 */
public interface PGBPaymentSupportBankDao extends BaseJpaDao<PGBPaymentSupportBank, String>{
		/**
		 * 
		 * @param abbreviation
		 * @return
		 */
		public List<PGBPaymentSupportBank> findByAbbreviation(String abbreviation);
}

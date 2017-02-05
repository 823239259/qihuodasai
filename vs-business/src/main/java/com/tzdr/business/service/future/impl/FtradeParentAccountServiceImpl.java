package com.tzdr.business.service.future.impl;


import java.util.List;

import jodd.util.StringUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.tzdr.business.service.future.FtradeParentAccountService;
import com.tzdr.common.baseservice.BaseServiceImpl;
import com.tzdr.domain.dao.future.FtradeParentAccountDao;
import com.tzdr.domain.web.entity.future.FtradeParentAccount;


/**
 * @author LiuQing
 * @see
 * @version 2.0 2015年7月23日下午4:46:30
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class FtradeParentAccountServiceImpl extends
		BaseServiceImpl<FtradeParentAccount, FtradeParentAccountDao> implements
		FtradeParentAccountService {
	
	private static Logger logger = LoggerFactory
			.getLogger(FtradeParentAccountServiceImpl.class);


	@Override
	public FtradeParentAccount queryAvailableAccount(Integer limitNo) {
		try {
			String sql = "SELECT acc.id FROM f_trade_parent_account acc LEFT JOIN "
					+ " f_account_info info ON acc.id = info.f_tid WHERE acc.deleted = 0 "
					+ " AND acc.state = 1 GROUP BY acc.id HAVING COUNT(info.f_tid) < ? "
					+ " ORDER BY acc.priority ASC LIMIT 1 ";
			List<Object> params = Lists.newArrayList();
			params.add(limitNo);
			String accountId = String.valueOf(nativeQueryOne(sql, params));
			if (StringUtil.isBlank(accountId)){
				return null;
			}
			return this.get(accountId);
		} catch (Exception e) {
			logger.error("not.fund.available.parent.account.",e);
			return null;
		}
	}

}
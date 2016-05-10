package com.tzdr.domain.hkstock.dao;


import java.util.List;

import com.tzdr.common.dao.BaseJpaDao;
import com.tzdr.domain.hkstock.entity.HkParentAccount;

/**
 * 港股母账户
 * @author zhouchen
 * 2015年10月16日 上午11:18:57
 */
public interface HkParentAccountDao extends BaseJpaDao<HkParentAccount, String> {

	/**
	 *  查询可用的 未被删除的 母账户
	 * @return
	 */
	List<HkParentAccount> findByDeletedFalse();
}

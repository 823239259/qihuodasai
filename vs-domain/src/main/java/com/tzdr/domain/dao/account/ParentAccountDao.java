package com.tzdr.domain.dao.account;


import java.util.List;

import com.tzdr.common.dao.BaseJpaDao;
import com.tzdr.domain.web.entity.ParentAccount;

/**
 * 
 * @zhouchen
 * 2014年12月26日
 */
public interface ParentAccountDao extends BaseJpaDao<ParentAccount, String> {

	/**
	 *  查询可用的 未被删除的 母账户
	 * @return
	 */
	List<ParentAccount> findByDeletedFalse();
	
	/**
	 *  查询AccountType
	 * @return
	 */
	List<ParentAccount> findByAccountTypeAndDeletedFalseOrderByPriorityNoAsc(String accountType);
	
	/**
	 *  查询ParentAccount
	 * @return
	 */
	ParentAccount findByAccountNo(String accountNo);
	
	
	/**
	 *  查询ParentAccount BY accountNo
	 * @return
	 */
	ParentAccount findByAccountNoAndDeletedFalse(String accountNo);
	
	
	/**
	 *  查询ParentAccount BY unitNumber
	 * @return
	 */
	List<ParentAccount> findByUnitNumberAndDeletedFalse(String unitNumber);
	
	/**
	 *  查询ParentAccount BY priorityNo
	 * @return
	 */
	List<ParentAccount> findByPriorityNoAndDeletedFalse(String priorityNo);
	
	
	/**
	 *  查询可用的 未被删除的 和 账户类型  佣金 和钱江 母账户
	 * @return
	 */
	List<ParentAccount> findByDeletedFalseAndAccountGenre(int type);

}

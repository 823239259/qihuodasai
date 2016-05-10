package com.tzdr.domain.dao.future;

import com.tzdr.common.dao.BaseJpaDao;
import com.tzdr.domain.web.entity.future.FaccountInfo;


/**
 * 
 * 
 * <p></p>
 * @author LiuQing
 * @see
 * @version 2.0
 * 2015年7月23日下午4:14:15
 */
public interface FaccountInfoDao extends BaseJpaDao<FaccountInfo, String> {
	
	/**
	 * 根据用户ID查询账户信息
	 * @param uid
	 * @return
	 */
	public FaccountInfo findByUid(String uid);
}

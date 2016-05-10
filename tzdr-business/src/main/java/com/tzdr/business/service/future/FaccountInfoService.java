package com.tzdr.business.service.future;

import com.tzdr.common.baseservice.BaseService;
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
public interface FaccountInfoService extends BaseService<FaccountInfo> {

	/**
	 * 根据用户ID查询账户信息
	 * @param uid
	 * @return
	 */
	public FaccountInfo findByUid(String uid);
	
	/**
	 * 判断指定的股指期货账户是否存在，不存在则新增一个默认的账户
	 * @param userId
	 */
	public FaccountInfo findOrSave(String userId);

}

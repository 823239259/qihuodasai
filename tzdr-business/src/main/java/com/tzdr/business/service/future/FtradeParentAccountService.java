package com.tzdr.business.service.future;


import com.tzdr.common.baseservice.BaseService;
import com.tzdr.domain.web.entity.future.FtradeParentAccount;


/**
 * 
 * 
 * <p>
 * </p>
 * 
 * @author LiuQing
 * @see
 * @version 2.0 2015年7月23日下午4:14:15
 */
public interface FtradeParentAccountService extends
		BaseService<FtradeParentAccount> {
	
	/**
	 * 实盘申请 获取可用的唯一的母账户
	 * @param limitNo 母账户限制分配用户数，
	 * 		     即一个母账户可被几个用户使用
	 * @return
	 */
	FtradeParentAccount queryAvailableAccount(Integer limitNo);


}

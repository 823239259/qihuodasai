package com.tzdr.domain.dao.customer;

import com.tzdr.common.dao.BaseJpaDao;
import com.tzdr.domain.web.entity.Customer;

/**
* @Description: 销售客户业务信息管理持久层
* @ClassName: CustomerDao
* @author wangpinqun
* @date 2015年07月07日 下午5:40:28
 */
public interface CustomerDao extends BaseJpaDao<Customer, String>{

	/**
	* @Description: 根据手机号码获取客户信息)
	* @Title: findByMobile
	* @param mobile   手机号码
	* @return Customer    返回类型
	 */
	public Customer findByMobile(String mobile);
}

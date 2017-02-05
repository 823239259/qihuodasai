package com.tzdr.business.service.customer;

import java.util.List;

import com.tzdr.common.baseservice.BaseService;
import com.tzdr.domain.web.entity.CustomerDetails;

/**
* @Description: 客户业务信息管理业务接口层
* @ClassName: CustomerDetailsService
* @author wangpinqun
* @date 2015年07月07日 下午5:45:27
 */
public interface CustomerDetailsService extends BaseService<CustomerDetails>{

	/**
	* @Description: 保存客户联系信息
	* @Title: saveCustomerDetails
	* @param customerDetails   客户联系信息
	* @return void    返回类型
	 */
	public void saveCustomerDetails(CustomerDetails customerDetails);
	
	/**
	* @Description: 更新客户联系信息
	* @Title: updateCustomerDetails
	* @param customerDetails   客户联系信息
	* @return void    返回类型
	 */
	public void updateCustomerDetails(CustomerDetails customerDetails);
	
	public List<CustomerDetails> findCustomerDetailsByCid(String cid);
}

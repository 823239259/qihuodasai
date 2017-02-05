package com.tzdr.business.service.customer;

import com.tzdr.common.baseservice.BaseService;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.domain.vo.CustomerVo;
import com.tzdr.domain.web.entity.Customer;

/**
* @Description: 客户业务信息管理业务接口层
* @ClassName: CustomerService
* @author wangpinqun
* @date 2015年07月07日 下午5:45:27
 */
public interface CustomerService extends BaseService<Customer>{

	/**
	* @Description: 保存客户信息
	* @Title: saveCustomer
	* @param customer   客户信息
	* @return void    返回类型
	 */
	public void saveCustomer(Customer customer);
	
	/**
	 * @Description: 根据手机号码获取客户信息
	 * @Title: getByMobile
	 * @param mobile  手机号码
	 * @return  Customer
	 */
	public Customer getByMobile(String mobile);
	
	/**
	 * @Description: 根据条件获取客户信息
	 * @param dataPage
	 * @param vo  条件
	 * @return
	 */
	public PageInfo<CustomerVo> findCustomerList(
			PageInfo<CustomerVo> dataPage, CustomerVo vo);
	
	/**
	 * 分配客户
	 * @param belongMarket 新所属人
	 * @param belongMarket 新所属人的所属组织code
	 * @param idArray  被分配客户编号集
	 */
	public void updateCustomer(Long belongMarket,String organizationCode,String [] idArray);
}

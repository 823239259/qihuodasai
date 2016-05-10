package com.tzdr.business.service.customer.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.tzdr.business.service.customer.CustomerDetailsService;
import com.tzdr.common.baseservice.BaseServiceImpl;
import com.tzdr.domain.dao.customer.CustomerDetailsDao;
import com.tzdr.domain.web.entity.CustomerDetails;

/**
* @Description: 客户联系业务信息管理业务接口层
* @ClassName: CustomerDetailsServiceImpl
* @author wangpinqun
* @date 2015年07月07日 下午5:45:27
 */
@Service("customerDetailsService")
public class CustomerDetailsServiceImpl extends BaseServiceImpl<CustomerDetails, CustomerDetailsDao> implements CustomerDetailsService{

	@Override
	public void saveCustomerDetails(CustomerDetails customerDetails) {
		this.getEntityDao().save(customerDetails);
	}

	@Override
	public void updateCustomerDetails(CustomerDetails customerDetails) {
		this.getEntityDao().update(customerDetails);
	}

	@Override
	public List<CustomerDetails> findCustomerDetailsByCid(String cid) {
		Map<String,Object> map=new HashMap<String,Object>();
		Map<String,Boolean> sortmap=new HashMap<String,Boolean>();
		map.put("EQ_customer.id", cid);
		sortmap.put("contactTime", Boolean.valueOf(false));
		List<CustomerDetails> customerDetailsList = this.query(map, sortmap);
		return customerDetailsList;
	}

}

package com.tzdr.business.service.customer;

import java.util.Date;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.tzdr.domain.web.entity.Customer;
import com.tzdr.domain.web.entity.CustomerDetails;

@ContextConfiguration(locations = { "/applicationContext-test.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class CustomerServiceTest {

	@Autowired
	private CustomerService customerService;
//	@Ignore
	@Test
	public void saveCustomer() {
		Customer customer = new Customer();
		customer.setMobile("15215050049");
		customer.setName("wangpinqun");
		customer.setAssignTime(new Date().getTime()/1000);
		customer.setCreateUserId((long)1);
		customer.setBelongMarket((long)1);
		CustomerDetails customerDetails = new CustomerDetails();
		customerDetails.setContactTime(new Date().getTime()/1000);
		customerDetails.setRemark("测试");
		customer.add(customerDetails);
		customerService.save(customer);
	}

	@Ignore
//	@Test
	public void updateCustomer() {
		Customer customer = customerService.get("40288afa4e687644014e68769c0a0000");
		customer.setName("王聘群");
		customerService.update(customer);
	}
}

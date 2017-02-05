package com.tzdr.business.service.customer;


import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.tzdr.domain.web.entity.Customer;
import com.tzdr.domain.web.entity.CustomerDetails;

@ContextConfiguration(locations = { "/applicationContext-test.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class CustomerDetailsServiceTest {

	@Autowired
	private CustomerDetailsService customerDetailsService;
	
	@Autowired
	private CustomerService customerService;
	
	@Test
	public void saveCustomerDetails() {
		Customer customer = customerService.get("40288afa4e687644014e68769c0a0000");
		CustomerDetails customerDetails = new CustomerDetails();
		customerDetails.setContactTime(new Date().getTime()/1000);
		customerDetails.setRemark("测试测试测试");
		customerDetails.setCustomer(customer);
		customerDetailsService.save(customerDetails);
	}

}

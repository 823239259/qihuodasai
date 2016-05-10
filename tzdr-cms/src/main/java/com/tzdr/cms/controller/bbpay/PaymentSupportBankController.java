package com.tzdr.cms.controller.bbpay;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tzdr.business.service.pay.PaymentSupportBankService;
import com.tzdr.cms.constants.ViewConstants;
import com.tzdr.cms.support.BaseCmsController;
import com.tzdr.common.baseservice.BaseService;
import com.tzdr.domain.web.entity.PaymentSupportBank;

@Controller
@RequestMapping("/admin/paySupportBank")
public class PaymentSupportBankController extends BaseCmsController<PaymentSupportBank> {
	@Autowired
	private PaymentSupportBankService paymentSupportBankService;

	@Override
	public BaseService<PaymentSupportBank> getBaseService() {
		return paymentSupportBankService;
	}
	
	@RequestMapping("/list")
	public String index(){
		return ViewConstants.paymentSupportBank.LIST_VIEW;
	}
	
	
	@Override
	public void setResourceIdentity(String resourceIdentity) {
		super.setResourceIdentity("sys:operationalConfig:paySupportBank");
	}
	
}

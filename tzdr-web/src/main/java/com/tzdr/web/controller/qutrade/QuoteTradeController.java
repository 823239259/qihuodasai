package com.tzdr.web.controller.qutrade;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.tzdr.web.constants.ViewConstants;

@Controller
@RequestMapping(value = "/qutrade")
public class QuoteTradeController {
	
	@RequestMapping(value="/view",method=RequestMethod.GET)
	private String quoteTradeView(){
		return ViewConstants.QuoteTradeJsp.View;
	}
}

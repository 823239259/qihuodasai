package com.tzdr.web.controller.stock;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tzdr.business.service.stock.StockService;
import com.tzdr.domain.web.entity.Stock;

@Controller
@RequestMapping("/limitstock")
/**
 * 限制股查询
 * @author Administrator
 *
 */
public class LimitStockController {

	@Autowired
	private StockService stockService;
	
	@RequestMapping(value = "/stocks")
	@ResponseBody
	public List<Stock> secInfo(HttpServletRequest request) throws Exception {
		return stockService.findData();
	}
	
	
}

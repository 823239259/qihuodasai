package com.tzdr.web.controller.userTrade;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tzdr.business.service.bespokeTrade.BespokeTradeService;
import com.tzdr.domain.vo.BespokeTradeVo;
import com.tzdr.web.constants.ViewConstants;

/**
 * @Description:免息操盘
 * @ClassName: FreeTradeController.java
 * @author WangPinQun
 * @date 2015年9月9日
 */
@Controller
@RequestMapping("/")
public class FreeTradeController {
	
	public static final Logger logger = LoggerFactory.getLogger(FreeTradeController.class);
	
	@Autowired
	private BespokeTradeService  bespokeTradeService;

	@RequestMapping(value = "/freetrade")
	public String freetrade(ModelMap modelMap,HttpServletRequest request,HttpServletResponse response){
		//查询定制配资
	    List<BespokeTradeVo> trades=bespokeTradeService.findAllBespokeTrade();
	    modelMap.put("trades", trades);
		return ViewConstants.FreeTradeJsp.FREE_TRADE;
	}
}

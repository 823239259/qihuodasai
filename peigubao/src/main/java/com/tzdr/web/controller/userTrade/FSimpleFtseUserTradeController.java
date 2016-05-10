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
import org.springframework.web.bind.annotation.ResponseBody;

import com.tzdr.business.service.contract.ContractParitiesService;
import com.tzdr.business.service.tradecalendar.TradeDayService;
import com.tzdr.business.service.userTrade.FSimpleConfigService;
import com.tzdr.business.service.userTrade.FSimpleFtseUserTradeService;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.domain.vo.FSimpleFtseUserTradeWebVo;
import com.tzdr.domain.web.entity.ContractParities;
import com.tzdr.domain.web.entity.FSimpleConfig;
import com.tzdr.web.constants.Constants;
import com.tzdr.web.constants.ViewConstants;
import com.tzdr.web.utils.UserSessionBean;

/**
 * 
 * 
 * <p></p>
 * @author WangPinQun
 * @see
 * FSimpleFtseUserTradeController
 * @version 2.0
 * 2015年9月16日下午16:33:13
 */
@Controller
@RequestMapping("/ftse")
public class FSimpleFtseUserTradeController {

	private static Logger log = LoggerFactory.getLogger(FSimpleFtseUserTradeController.class);
	
	@Autowired
	private FSimpleConfigService fSimpleConfigService;
	
	@Autowired
	private FSimpleFtseUserTradeService fSimpleFtseUserTradeService;
	
	@Autowired
	private  TradeDayService tradeDayService;
	
	@Autowired
	private ContractParitiesService contractParitiesService;
	
	/**
	 * 方案配置类型
	 */
	private final static int CONFIGTYPE = 5;
	
	@RequestMapping(value = "/index")
	public String index(ModelMap modelMap,HttpServletRequest request,HttpServletResponse response){
		
		List<FSimpleConfig> fSimpleConfigList = fSimpleConfigService.findFSimpleConfigsByType(CONFIGTYPE);
		ContractParities newConfig = contractParitiesService.get("00001");
		modelMap.put("contract", newConfig.getContract());
		modelMap.put("fSimpleConfigList", fSimpleConfigList);
		return ViewConstants.FSimpleFtseUserTradeJsp.FTSE_INDEX;
	}
	
	
}

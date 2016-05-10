package com.tzdr.web.controller.userTrade;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jodd.util.StringUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tzdr.business.service.contract.ContractParitiesService;
import com.tzdr.business.service.tradecalendar.TradeDayService;
import com.tzdr.business.service.userTrade.FSimpleProductConfigService;
import com.tzdr.business.service.userTrade.FSimpleProductUserTradeService;
import com.tzdr.domain.web.entity.ContractParities;
import com.tzdr.domain.web.entity.FSimpleConfig;
import com.tzdr.web.constants.ViewConstants;


/**
 * @author LiuYang
 *商品期货
 * 2015年9月17日 下午6:40:08
 */
@Controller
@RequestMapping("/product")
public class FSimpleProductUserTradeController {
private static Logger log = LoggerFactory.getLogger(FSimpleProductUserTradeController.class);
	
	@Autowired
	private FSimpleProductConfigService fSimpleProductConfigService;
	
	@Autowired
	private FSimpleProductUserTradeService fSimpleProductUserTradeService;
	
	
	@Autowired
	private  TradeDayService tradeDayService;
	
	@Autowired
	private ContractParitiesService contractParitiesService;
	
	/**
	 * 沪金配资页
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/gold_index")
	public String goldIndex(ModelMap modelMap,HttpServletRequest request,HttpServletResponse response){
		FSimpleConfig fSimpleProductConfig = fSimpleProductConfigService.getFSimpleConfigByType(1);
		List<Integer> tranLevers = new ArrayList<Integer>();
		if(fSimpleProductConfig != null && StringUtil.isNotBlank(fSimpleProductConfig.getTranLever())){
			String[] tranLeverStrs = fSimpleProductConfig.getTranLever().split(",");
			if(tranLeverStrs != null && tranLeverStrs.length > 0){
				for (String tranLever : tranLeverStrs) {
					tranLevers.add(new Integer(tranLever));
				}
			}
		}
		
		List<Integer> tranFeesArray = new ArrayList<Integer>();
		if(fSimpleProductConfig != null && StringUtil.isNotBlank(fSimpleProductConfig.getTranFeesArray())){
			String[] tranFeesArrayStr = fSimpleProductConfig.getTranFeesArray().split(",");
			if(tranFeesArray != null && tranFeesArrayStr.length > 0){
				for (String tranFees : tranFeesArrayStr) {
					tranFeesArray.add(new Integer(tranFees));
				}
			}
		}
		ContractParities newConfig = contractParitiesService.get("00002");
		modelMap.put("contract", newConfig.getContract());
		modelMap.put("tranFeesArray", tranFeesArray);
		modelMap.put("fSimpleProductConfig", fSimpleProductConfig);
		modelMap.put("tranLevers", tranLevers);
		
		
		return ViewConstants.FSimpleProductUserTradeViewJsp.GOLD_INDEX;
	}
	
	/**
	 * 沪银配资页
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/sliver_index")
	public String sliverIndex(ModelMap modelMap,HttpServletRequest request,HttpServletResponse response){
		FSimpleConfig fSimpleProductConfig = fSimpleProductConfigService.getFSimpleConfigByType(2);
		List<Integer> tranLevers = new ArrayList<Integer>();
		if(fSimpleProductConfig != null && StringUtil.isNotBlank(fSimpleProductConfig.getTranLever())){
			String[] tranLeverStrs = fSimpleProductConfig.getTranLever().split(",");
			if(tranLeverStrs != null && tranLeverStrs.length > 0){
				for (String tranLever : tranLeverStrs) {
					tranLevers.add(new Integer(tranLever));
				}
			}
		}
		List<Integer> tranFeesArray = new ArrayList<Integer>();
		if(fSimpleProductConfig != null && StringUtil.isNotBlank(fSimpleProductConfig.getTranFeesArray())){
			String[] tranFeesArrayStr = fSimpleProductConfig.getTranFeesArray().split(",");
			if(tranFeesArray != null && tranFeesArrayStr.length > 0){
				for (String tranFees : tranFeesArrayStr) {
					tranFeesArray.add(new Integer(tranFees));
				}
			}
		}
		ContractParities newConfig = contractParitiesService.get("00003");
		modelMap.put("contract", newConfig.getContract());
		modelMap.put("tranFeesArray", tranFeesArray);
		modelMap.put("fSimpleProductConfig", fSimpleProductConfig);
		modelMap.put("tranLevers", tranLevers);
		
		
		return ViewConstants.FSimpleProductUserTradeViewJsp.SLIVER_INDEX;
	}
	/**
	 * 沪铜配资页
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/copper_index")
	public String copperIndex(ModelMap modelMap,HttpServletRequest request,HttpServletResponse response){
		FSimpleConfig fSimpleProductConfig = fSimpleProductConfigService.getFSimpleConfigByType(3);
		List<Integer> tranLevers = new ArrayList<Integer>();
		if(fSimpleProductConfig != null && StringUtil.isNotBlank(fSimpleProductConfig.getTranLever())){
			String[] tranLeverStrs = fSimpleProductConfig.getTranLever().split(",");
			if(tranLeverStrs != null && tranLeverStrs.length > 0){
				for (String tranLever : tranLeverStrs) {
					tranLevers.add(new Integer(tranLever));
				}
			}
		}
		List<Integer> tranFeesArray = new ArrayList<Integer>();
		if(fSimpleProductConfig != null && StringUtil.isNotBlank(fSimpleProductConfig.getTranFeesArray())){
			String[] tranFeesArrayStr = fSimpleProductConfig.getTranFeesArray().split(",");
			if(tranFeesArray != null && tranFeesArrayStr.length > 0){
				for (String tranFees : tranFeesArrayStr) {
					tranFeesArray.add(new Integer(tranFees));
				}
			}
		}
		
		ContractParities newConfig = contractParitiesService.get("00004");
		modelMap.put("contract", newConfig.getContract());
		modelMap.put("tranFeesArray", tranFeesArray);
		modelMap.put("fSimpleProductConfig", fSimpleProductConfig);
		modelMap.put("tranLevers", tranLevers);
		
		
		return ViewConstants.FSimpleProductUserTradeViewJsp.COPPER_INDEX;
	}
	/**
	 * 橡胶配资页
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/rubber_index")
	public String rubberIndex(ModelMap modelMap,HttpServletRequest request,HttpServletResponse response){
		FSimpleConfig fSimpleProductConfig = fSimpleProductConfigService.getFSimpleConfigByType(4);
		List<Integer> tranLevers = new ArrayList<Integer>();
		if(fSimpleProductConfig != null && StringUtil.isNotBlank(fSimpleProductConfig.getTranLever())){
			String[] tranLeverStrs = fSimpleProductConfig.getTranLever().split(",");
			if(tranLeverStrs != null && tranLeverStrs.length > 0){
				for (String tranLever : tranLeverStrs) {
					tranLevers.add(new Integer(tranLever));
				}
			}
		}
		List<Integer> tranFeesArray = new ArrayList<Integer>();
		if(fSimpleProductConfig != null && StringUtil.isNotBlank(fSimpleProductConfig.getTranFeesArray())){
			String[] tranFeesArrayStr = fSimpleProductConfig.getTranFeesArray().split(",");
			if(tranFeesArray != null && tranFeesArrayStr.length > 0){
				for (String tranFees : tranFeesArrayStr) {
					tranFeesArray.add(new Integer(tranFees));
				}
			}
		}
		ContractParities newConfig = contractParitiesService.get("00005");
		modelMap.put("contract", newConfig.getContract());
		modelMap.put("tranFeesArray", tranFeesArray);
		modelMap.put("fSimpleProductConfig", fSimpleProductConfig);
		modelMap.put("tranLevers", tranLevers);
		
		
		return ViewConstants.FSimpleProductUserTradeViewJsp.RUBBER_INDEX;
	}
	
	
	
	
}

package com.tzdr.web.controller.userTrade;

import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.hundsun.t2sdk.common.util.CollectionUtils;

import com.tzdr.business.service.comprehensiveCommodity.ComprehensiveCommodityParameterService;
import com.tzdr.business.service.comprehensiveCommodity.ComprehensiveCommodityPriceService;
import com.tzdr.common.web.support.JsonResult;
import com.tzdr.domain.web.entity.ComprehensiveCommodityParameter;
import com.tzdr.domain.web.entity.ComprehensiveCommodityPrice;

import com.tzdr.web.constants.ViewConstants;

/**
 * 
 * <B>说明: 商品综合</B>
 * @author GuoXingyou
 * 2016年2月23日
 */
@Controller
@RequestMapping("/commodity")
public class ComprehensiveCommodityController {
	
	@Autowired
	private ComprehensiveCommodityParameterService comprehensiveCommodityParameterService;
	@Autowired
	private ComprehensiveCommodityPriceService comprehensiveCommodityPriceService;
	@SuppressWarnings("unused")
	private static Logger log = LoggerFactory.getLogger(ComprehensiveCommodityController.class);
	
	/**
	 * 配资页面
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/index")
	public String index(ModelMap modelMap,HttpServletRequest request,HttpServletResponse response){
		
		List<ComprehensiveCommodityParameter> comprehensiveCommodityParameter = comprehensiveCommodityParameterService.findAllComprehensiveCommodityParameter();
		List<ComprehensiveCommodityPrice> comprehensiveCommodityPrice = comprehensiveCommodityPriceService.findAllComprehensiveCommodityPrice();
		
		modelMap.put("comprehensiveCommodityParameter", comprehensiveCommodityParameter);
		modelMap.put("comprehensiveCommodityPrice", comprehensiveCommodityPrice);
		return ViewConstants.FSimpleProductUserTradeViewJsp.INDEX;
	}
	
	/**
	 * 获取配资数据
	 * @param traderBond
	 * @return
	 */
	@RequestMapping(value = "/data.json" , method = RequestMethod.POST)
	@ResponseBody
	public JsonResult  showData(@RequestParam BigDecimal traderBond){
		List<ComprehensiveCommodityParameter> comprehensiveCommodityParameterList = comprehensiveCommodityParameterService.findByTraderBond(traderBond);
		JSONObject  resultData = new JSONObject();
		JsonResult  result = null;
		if(CollectionUtils.isEmpty(comprehensiveCommodityParameterList)){
			 result = new JsonResult(false, "保证金有误！");
		}else{
			ComprehensiveCommodityParameter comprehensiveCommodityParameter= comprehensiveCommodityParameterList.get(0);
			resultData.put("traderBond", comprehensiveCommodityParameter.getTraderBond());
			resultData.put("traderTotal", comprehensiveCommodityParameter.getTraderTotal());
			resultData.put("lineLoss", comprehensiveCommodityParameter.getLineLoss());
			
			resultData.put("silverLever", comprehensiveCommodityParameter.getSilverLever());
			resultData.put("aluminumLever", comprehensiveCommodityParameter.getAluminumLever());
			resultData.put("goldLever", comprehensiveCommodityParameter.getGoldLever());
			resultData.put("asphaltLever", comprehensiveCommodityParameter.getAsphaltLever());
			resultData.put("copperLever", comprehensiveCommodityParameter.getCopperLever());
			resultData.put("coilLever", comprehensiveCommodityParameter.getCoilLever());
			resultData.put("nickelLever", comprehensiveCommodityParameter.getNickelLever());
			resultData.put("rebarLever", comprehensiveCommodityParameter.getRebarLever());		
			resultData.put("zincLever", comprehensiveCommodityParameter.getZincLever());
			resultData.put("rubberLever", comprehensiveCommodityParameter.getRubberLever());
			resultData.put("beanLever", comprehensiveCommodityParameter.getBeanLever());
			resultData.put("cornLever", comprehensiveCommodityParameter.getCornLever());
			resultData.put("cornStarchLever", comprehensiveCommodityParameter.getCornStarchLever());
			resultData.put("ironOreLever", comprehensiveCommodityParameter.getIronOreLever());
			resultData.put("cokeLever", comprehensiveCommodityParameter.getCokeLever());
			resultData.put("eggLever", comprehensiveCommodityParameter.getEggLever());			
			resultData.put("cokingCoalLever", comprehensiveCommodityParameter.getCokingCoalLever());
			resultData.put("plasticLever", comprehensiveCommodityParameter.getPlasticLever());
			resultData.put("soybeanMealLever", comprehensiveCommodityParameter.getSoybeanMealLever());
			resultData.put("palmOilLever", comprehensiveCommodityParameter.getPalmOilLever());
			resultData.put("polypropyleneLever", comprehensiveCommodityParameter.getPolypropyleneLever());
			resultData.put("soybeanOilLever", comprehensiveCommodityParameter.getSoybeanOilLever());
			resultData.put("cottonLever", comprehensiveCommodityParameter.getCottonLever());
			resultData.put("glassLever", comprehensiveCommodityParameter.getGlassLever());
			resultData.put("methanolLever", comprehensiveCommodityParameter.getMethanolLever());
			resultData.put("rapeOilLever", comprehensiveCommodityParameter.getRapeOilLever());
			resultData.put("rapeseedMealLever", comprehensiveCommodityParameter.getRapeseedMealLever());
			resultData.put("sugarLever", comprehensiveCommodityParameter.getSugarLever());
			resultData.put("pTALever", comprehensiveCommodityParameter.getpTALever());
			resultData.put("powerCoalLever", comprehensiveCommodityParameter.getPowerCoalLever());
			resultData.put("fiveNationalDebtLever", comprehensiveCommodityParameter.getFiveNationalDebtLever());
			resultData.put("tenNationalDebtLever", comprehensiveCommodityParameter.getTenNationalDebtLever());
			
			result = new JsonResult(true, "处理成功！");
			result.setObj(resultData);
		}
		return  result;
	}
}

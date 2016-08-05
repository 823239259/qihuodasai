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
import com.tzdr.business.service.OutDisk.OutDiskParametersService;
import com.tzdr.business.service.OutDisk.OutDiskPriceService;
import com.tzdr.common.web.support.JsonResult;
import com.tzdr.domain.web.entity.OutDiskParameters;
import com.tzdr.domain.web.entity.OutDiskPrice;
import com.tzdr.web.constants.ViewConstants;
/**
 * 
 * <B>说明: 国际综合</B>
 * @author Liu Yang
 * 2016年2月23日
 */
@Controller
@RequestMapping("/outDisk")
public class OutDiskController {
	@Autowired
	private OutDiskParametersService outDiskParametersService;
	@Autowired
	private OutDiskPriceService outDiskPriceService;
	
	@SuppressWarnings("unused")
	private static Logger log = LoggerFactory.getLogger(OutDiskController.class);
	/**
	 * 配资页面
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/index")
	public String index(ModelMap modelMap,HttpServletRequest request,HttpServletResponse response){
		List<OutDiskParameters> outDiskParameters = outDiskParametersService.findAllOutDiskParameters();
		List<OutDiskPrice> outDiskPrice = outDiskPriceService.findAllOutDiskPrice();
		
		modelMap.put("outDiskParameters", outDiskParameters);
		modelMap.put("outDiskPrice", outDiskPrice);
		return ViewConstants.OutDiskJsp.INDEX;
	}
	/**
	 * 获取配资数据
	 * @param traderBond
	 * @return
	 */
	@RequestMapping(value = "/data.json" , method = RequestMethod.POST)
	@ResponseBody
	public JsonResult  showData(@RequestParam BigDecimal traderBond){
		List<OutDiskParameters> outDiskParametersList = outDiskParametersService.findByTraderBond(traderBond);
		JSONObject  resultData = new JSONObject();
		JsonResult  result = null;
		if(CollectionUtils.isEmpty(outDiskParametersList)){
			 result = new JsonResult(false, "保证金有误！");
		}else{	
			OutDiskParameters outDiskParameters= outDiskParametersList.get(0);
			resultData.put("traderBond", outDiskParameters.getTraderBond());
			resultData.put("traderTotal", outDiskParameters.getTraderTotal());
			resultData.put("lineLoss", outDiskParameters.getLineLoss());
			resultData.put("ATranActualLever", outDiskParameters.getAtranActualLever());
			resultData.put("HTranActualLever", outDiskParameters.getHtranActualLever());
			resultData.put("YTranActualLever", outDiskParameters.getYtranActualLever());
			
			resultData.put("mbtranActualLever", outDiskParameters.getMbtranActualLever());
			resultData.put("mntranActualLever", outDiskParameters.getMntranActualLever());
			resultData.put("mdtranActualLever", outDiskParameters.getMdtranActualLever());
			resultData.put("nikkeiTranActualLever", outDiskParameters.getNikkeiTranActualLever());
			resultData.put("daxtranActualLever", outDiskParameters.getDaxtranActualLever());
			resultData.put("hstranActualLever", outDiskParameters.getHstranActualLever());
			resultData.put("agtranActualLever", outDiskParameters.getAgtranActualLever());
			result = new JsonResult(true, "处理成功！");
			result.setObj(resultData);
		}
		return  result;
	}
	
	
	
	
	
	
}

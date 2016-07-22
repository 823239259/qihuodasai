package com.tzdr.api.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tzdr.api.constants.BusinessTypeEnum;
import com.tzdr.api.constants.ResultStatusConstant;
import com.tzdr.api.support.ApiResult;
import com.tzdr.business.app.service.FTradeService;
import com.tzdr.business.service.togetherFuture.FTogetherTradeService;
import com.tzdr.common.utils.MessageUtils;
import com.tzdr.domain.app.vo.FTradeParamsVo;
import com.tzdr.domain.app.vo.FTradeVo;

/**  
 * @Title: FTradeController.java     
 * @Description: 期货产品管理信息    
 * @author： WangPinQun 
 * @E-mail：wangpinqun@tzdr.com
 * @company： 上海信闳投资管理有限公司重庆分公司
 * @address：重庆市渝北区黄山大道中段70号两江新界3栋13楼
 * @date： 2016年3月22日 上午9:32:13    
 * @version： V1.0  
 */
@Controller
@RequestMapping("/ftrade")
public class FTradeController {
	
	public static final Logger logger = LoggerFactory.getLogger(FTradeController.class);

	@Autowired
	private FTradeService fTradeService;
	
	@Autowired
	private FTogetherTradeService fTogetherTradeService;
	
	/**
	* @Title: goods    
	* @Description: 获取期货产品信息 
	* @param modelMap
	* @param request
	* @param response
	* @return
	* @throws Exception
	 */
	@RequestMapping(value = "/goods" , method = RequestMethod.POST)
	@ResponseBody
	public ApiResult goods(ModelMap modelMap,HttpServletRequest request,HttpServletResponse response) throws Exception{
	
		 List<FTradeVo> dataList = fTradeService.findGoods();
		 // 如果是恒指获取配置的固定手续费
		 String hsiTranFees = MessageUtils.message("tzdr.app.hsi.tran.fees");
		 if (StringUtils.isNotBlank(hsiTranFees)){
			 for (FTradeVo fTradeVo:dataList){
				 if (BusinessTypeEnum.HSI.getValue() == fTradeVo.getType()){
					 fTradeVo.setTradeFees(new BigDecimal(hsiTranFees));	
				 }
			 }
		 }
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("tradeList", dataList);
		
		// 操盘页面  获取下期货合买活动图片显示
		dataMap.put("isFtogetherActivityTime",fTogetherTradeService.checkActivityTime());
		return new ApiResult(true,ResultStatusConstant.SUCCESS,null,dataMap);
	}
	
	
	/**
	 * 获取操盘参数
	 * @param businessType 业务类型 0.富时A50  6.原油 7.恒指 8.国际综合 9.小恒指
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/params" , method = RequestMethod.POST)
	@ResponseBody
	public ApiResult params(int businessType,HttpServletRequest request,HttpServletResponse response) throws Exception{
	
		if (!BusinessTypeEnum.isBusinessType(businessType)){
			return new ApiResult(false,ResultStatusConstant.FAIL,"params.is.error.");
		}
		Map<String, Object> dataMap = new HashMap<String, Object>();
		// 获取A50、恒指、原油、小恒指的操盘参数
		if (BusinessTypeEnum.A50.getValue()==businessType 
				|| BusinessTypeEnum.CRUDE.getValue()==businessType 
				|| BusinessTypeEnum.HSI.getValue()==businessType 
				|| BusinessTypeEnum.LHSI.getValue()==businessType){
			 int cfgBusinessType = (BusinessTypeEnum.A50.getValue()==businessType)?BusinessTypeEnum.A50_CONFIG.getValue():businessType;
			 List<FTradeParamsVo> fTradeParamsVos  = fTradeService.queryFtradeParams(cfgBusinessType);
			 // 如果是恒指获取配置的固定手续费
			 if (BusinessTypeEnum.HSI.getValue()==businessType){
				 String hsiTranFees = MessageUtils.message("tzdr.app.hsi.tran.fees");
				 if (StringUtils.isNotBlank(hsiTranFees)){
					 for (FTradeParamsVo fTradeParamsVo:fTradeParamsVos){
						 fTradeParamsVo.setTranFees(new BigDecimal(hsiTranFees));	
					 }
				 }
			 }
			 dataMap.put("paramList",fTradeParamsVos);
			 dataMap.put("contractList", fTradeService.findOutDiskVos(businessType));
		}
		
		// 获取国际综合操盘参数
		if (BusinessTypeEnum.MULTIPLE.getValue()==businessType){
			 List<FTradeParamsVo> fTradeParamsVos  = fTradeService.queryFtradeParams();
			 dataMap.put("paramList",fTradeParamsVos);
			 dataMap.put("contractList", fTradeService.findOutDiskVos());
		}
		
		return new ApiResult(true,ResultStatusConstant.SUCCESS,"query.success",dataMap);
	}
}

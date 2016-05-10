package com.tzdr.web.hkstock.controller;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jodd.util.ObjectUtil;
import jodd.util.StringUtil;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.tzdr.business.exception.UserTradeException;
import com.tzdr.business.hkstock.exception.HkUserTradeException;
import com.tzdr.business.hkstock.service.HkStockParamsService;
import com.tzdr.business.hkstock.service.HkTradeCalendarService;
import com.tzdr.business.hkstock.service.HkUserTradeService;
import com.tzdr.business.service.securityInfo.SecurityInfoService;
import com.tzdr.business.service.userTrade.FSimpleParitiesService;
import com.tzdr.business.service.userTrade.NoticeRecordService;
import com.tzdr.business.service.userTrade.TradeConfigService;
import com.tzdr.business.service.wuser.WUserService;
import com.tzdr.common.utils.BigDecimalUtils;
import com.tzdr.common.utils.Dates;
import com.tzdr.common.web.support.JsonResult;
import com.tzdr.domain.hkstock.constants.HkConstant;
import com.tzdr.domain.hkstock.entity.HkStockParams;
import com.tzdr.domain.web.entity.FSimpleParities;
import com.tzdr.domain.web.entity.TradeConfig;
import com.tzdr.domain.web.entity.UserVerified;
import com.tzdr.domain.web.entity.WUser;
import com.tzdr.web.constants.Constants;
import com.tzdr.web.constants.ViewConstants;
import com.tzdr.web.hkstock.constants.HkViewConstants;
import com.tzdr.web.hkstock.utils.HkStockUtil;
import com.tzdr.web.utils.UserSessionBean;

@Controller
@RequestMapping("/hkstock")
public class HkHomeController {

	private static Logger log = LoggerFactory.getLogger(HkHomeController.class);

	@Autowired
	private HkTradeCalendarService hkTradeCalendarService;
	
	@Autowired
	private  HkUserTradeService  hkUserTradeService;
	
	@Autowired
	private  NoticeRecordService noticeRecordService;
	
	@Autowired
	private TradeConfigService tradeConfigService;
	
	
	@Autowired
	private  HkStockParamsService  hkStockParamsService;
	
	@Autowired
	private FSimpleParitiesService fSimpleParitiesService;
	
	@Autowired
	private WUserService  wUserService;
	
	@Autowired
	private SecurityInfoService securityInfoService;
	
	/**
	 * 校验当前时间是否可以选择立即交易
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/isTrading.json" , method = RequestMethod.POST)
	@ResponseBody
	public JsonResult tradeDay(HttpServletRequest request,
			HttpServletResponse response) {
		log.info("校验当前时间是否可以选择立即交易");
		JsonResult jsonResult = new JsonResult(false);
		String currentDay = Dates.format(new Date(),
				Dates.CHINESE_DATE_FORMAT_LONG);
		String tradeDay = hkTradeCalendarService.getTradeDay();
		boolean isTradeDay = Boolean.FALSE;
		if (tradeDay.equals(currentDay)) {
			isTradeDay = Boolean.TRUE;
		}
		Map<Object, Object> data = Maps.newHashMap();
		data.put("isTradeDay", isTradeDay);
		data.put("tradeDay", tradeDay);
		jsonResult.setData(data);
		jsonResult.setSuccess(true);
		return jsonResult;
	}
	
	
	/**
	 * 获取操盘方案预计结束日期
	 * @param borrowPeriod
	 * @param tradeStart
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/endDay.json" , method = RequestMethod.POST)
	@ResponseBody
	public JsonResult getEndDay( @RequestParam int borrowPeriod, @RequestParam int tradeStart,
			HttpServletRequest request) {
		JsonResult jsonResult = new JsonResult(false);
		String endDay = HkStockUtil.getEndDay(borrowPeriod, tradeStart,hkTradeCalendarService);	
		Map<Object, Object> map = Maps.newHashMap();
		map.put("endDay", endDay);
		jsonResult.setData(map);
		jsonResult.setSuccess(true);
		return jsonResult;
	}
	
	
	@RequestMapping(value="/index")
	public  String  index(ModelMap modelMap,HttpServletRequest request,HttpServletResponse response){
		//获取港股操盘参数
		HkStockParams  hkStockParams = hkStockParamsService.getParams();
		request.setAttribute("hkStockParams", hkStockParams);
		if (ObjectUtil.equals(null, hkStockParams)){
			log.debug("港股操盘参数配置信息不存在。");
			return ViewConstants.ERROR_VIEW;
		}
		
		String recommendHoldMoney = hkStockParams.getRecommendHoldMoney();
		String recommendHoldDays = hkStockParams.getRecommendHoldDays();
		String leverConfig  =  hkStockParams.getLeverConfig();
		
		if (StringUtil.isNotBlank(recommendHoldMoney)){
			request.setAttribute("recommendHoldMoney",recommendHoldMoney.split(Constants.SEPERATOR_SEMICOLON));
		}
		
		if (StringUtil.isNotBlank(recommendHoldDays)){
			request.setAttribute("recommendHoldDays",recommendHoldDays.split(Constants.SEPERATOR_SEMICOLON));
		}
		
		if (StringUtil.isNotBlank(leverConfig)){
			request.setAttribute("leverConfig",leverConfig.split(Constants.SEPERATOR_SEMICOLON));
		}
		
		/***************返回修改**********************/	
		modelMap.put("lever", request.getParameter("lever"));
		modelMap.put("bailMoney",request.getParameter("bailMoney"));
		modelMap.put("borrowPeriod", request.getParameter("borrowPeriod"));
		modelMap.put("totalMoney", request.getParameter("totalMoney"));
		modelMap.put("tradeStart", request.getParameter("tradeStart"));
		/***************返回修改**********************/
		log.info("进入港股操盘页面...");
		return HkViewConstants.HkUserTradeViews.HK_TRADE_INDEX;
	}
	
	/**
	 * 根据总操盘金额和倍数获取需要显示的内容，如：平仓线、补仓线、操盘保证金、利息、管理费等。
	 * @param totalMoney
	 * @param lever
	 * @return
	 */
	@RequestMapping(value = "/baildata.json" , method = RequestMethod.POST)
	@ResponseBody
	public JsonResult  showData(@RequestParam Double totalMoney,@RequestParam Double lever){
		//获取港股操盘参数
		HkStockParams  hkStockParams = hkStockParamsService.getParams();
		if (ObjectUtil.equals(null, hkStockParams)){
			return new JsonResult(false, "港股参数配置信息有误！");
		}

		// 校验总操盘资金是否合法
		if (totalMoney < hkStockParams.getMinTotalMoney() || totalMoney > hkStockParams.getMaxTotalMoney()){
			return new JsonResult(false, "总操盘资金有误！");
		}
		//校验倍数是否合法
		String [] levers = hkStockParams.getLeverConfig().split(Constants.SEPERATOR_SEMICOLON);
		if (ArrayUtils.isEmpty(levers)){
			return new JsonResult(false, "倍数配置信息有误！");
		}
		
		//根据倍数获取对应的 操盘保证金=总操盘资金 x 保证金计算系数/(倍数+1)
		JSONObject  resultData = new JSONObject();
		resultData.put("bailArray",HkStockUtil.parseLeverConfig(hkStockParams, totalMoney));
		
		/**
		 * 计算平仓线、补仓线、配资金额
		 * 配资金额=总操盘资金 x 配资倍数/(倍数+1)  
		 * 亏损警戒线=配资金额 x 警戒线风控系数
		 * 亏损平仓线=配资金额 x 平仓线风控系数
		 */
		Double  withCapitalMoney = BigDecimalUtils.div(BigDecimalUtils.mulRound(totalMoney,lever),(lever+1),0);
		Double waringMoney = BigDecimalUtils.mulRound(withCapitalMoney,HkStockUtil.parseWarningCoefficient(hkStockParams,lever.intValue()), 0);
		Double openMoney = BigDecimalUtils.mulRound(withCapitalMoney,HkStockUtil.parseOpenCoefficient(hkStockParams,lever.intValue()), 0);
		resultData.put("waringMoney", waringMoney);
		resultData.put("openMoney", openMoney);
		resultData.put("operatorsInfo",HkStockUtil.operatorsInfo(withCapitalMoney));
		
		JsonResult  result = new JsonResult(true, "处理成功！");
		result.setObj(resultData);
		return  result;
	}
	
	
	/**
	 * 根据使用天数、总操盘资金、倍数算出应扣取的利息和管理费
	 * @param borrowPeriod 借用天数
	 * @param lever 倍数
	 * @param totalMoney 总操盘资金
	 * @param bailMoney 保证金
	 * @param tradeStart 是否立即交易；1：下个交易日 、0：立即交易
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/deduct.data.json" , method = RequestMethod.POST)
	@ResponseBody
	public JsonResult deductdata(
			@RequestParam int borrowPeriod,@RequestParam int lever, 
			@RequestParam double totalMoney,@RequestParam double bailMoney,
			@RequestParam int tradeStart,HttpServletRequest request) {
		//获取港股操盘参数
		HkStockParams  hkStockParams = hkStockParamsService.getParams();
		request.setAttribute("hkStockParams", hkStockParams);
		if (ObjectUtil.equals(null, hkStockParams)){
			log.debug("港股操盘参数配置信息不存在。");
			return new JsonResult(false,"港股操盘参数配置信息不存在！");
		}
		//校验倍数与操盘保证金是否对应
		JSONArray  bailArray = HkStockUtil.parseLeverConfig(hkStockParams, totalMoney);
		if (CollectionUtils.isEmpty(bailArray)){
			return new JsonResult(false,"港股操盘参数配置信息有误！");
		}
		//校验倍数和保证金是否匹配
		if (!HkStockUtil.isRightLeverAndBailMoney(bailArray, bailMoney, lever)){
			return new JsonResult(false,"非法请求，提交倍数与保证金不匹配！");
		}
		
		// 查下利息管理费配置
		TradeConfig config = tradeConfigService.findTradeConfig(borrowPeriod,bailMoney, lever);
		if (ObjectUtil.equals(config, null)) {
			String detail="borrwPeriod:"+borrowPeriod+"lever:"+lever+"bailMoney:"+bailMoney;
			log.error("利息配置有误"+detail);
			throw new HkUserTradeException("no.interest.config",null);
		}
		//查下汇率配置
		FSimpleParities  fSimpleParities = fSimpleParitiesService.getFSimpleParities(HkConstant.HK_PARITIES_TYPE);
		if (ObjectUtil.equals(null, fSimpleParities)){
			return new JsonResult(false,"汇率配置有误！");
		}
		Double  bailCoefficient = hkStockParams.getBailCoefficient();
		//配资金额
		Double  withCapitalMoney = BigDecimalUtils.div(BigDecimalUtils.mulRound(totalMoney,lever),(lever+HkConstant.DOUBLE_ONE),HkConstant.INT_ZERO);
		double interestFee = BigDecimalUtils.mulRound(BigDecimalUtils.mul(config.getDailyInterest(),BigDecimalUtils.mul(withCapitalMoney,bailCoefficient)),hkStockParams.getInterestCoefficient());
		double manageFee = BigDecimalUtils.mulRound(BigDecimalUtils.mul(config.getDailyManagementFee(),BigDecimalUtils.mul(withCapitalMoney,bailCoefficient)),hkStockParams.getManageFeeCoefficient());
		Double totalInterestFee = BigDecimalUtils.mulRound(interestFee,borrowPeriod);
		//预计结束日期
		String endDay = HkStockUtil.getEndDay(borrowPeriod, tradeStart,hkTradeCalendarService);
		Map<Object, Object> map = Maps.newHashMap();
		map.put("endDay", endDay);
		map.put("interestFee", interestFee);
		map.put("totalInterestFee", totalInterestFee);
		map.put("manageFee", manageFee);
		JsonResult  result  = new JsonResult(true,"处理成功！");
		result.setData(map);
		return result;
	}
	
	/**
	 * 操盘协议
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/contract")
	public String tradeContract(ModelMap modelMap,HttpServletRequest request,HttpServletResponse response){
		return HkViewConstants.HkUserTradeViews.TRADECONTRACT;
	}
	

	
	/**
	 * 确认操盘时先 校验数据是否正常
	 * @param modelMap
	 * @param tradeStart 是否立即交易；1：下个交易日 、0：立即交易
	 * @param lever 倍数
	 * @param bailMoney 倍数对应的保证金
	 * @param borrowPeriod  借用天数
	 * @param totalMoney  总操盘资金
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/confirm.check")
	@ResponseBody
	public JsonResult comfirmCheck(int tradeStart, int lever, double bailMoney, int borrowPeriod, 
			double totalMoney,HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UserSessionBean userSessionBean = (UserSessionBean) request.getSession().getAttribute(Constants.TZDR_USER_SESSION);
		if (ObjectUtil.equals(null, userSessionBean)) {
			log.debug("未获取到登录信息");
			return new JsonResult(false,"非法请求，请先登录！");
		}
		//获取港股操盘参数
		HkStockParams  hkStockParams = hkStockParamsService.getParams();
		request.setAttribute("hkStockParams", hkStockParams);
		if (ObjectUtil.equals(null, hkStockParams)){
			log.debug("港股操盘参数配置信息不存在。");
			return new JsonResult(false,"港股操盘参数配置有误！");
		}
		//校验倍数与操盘保证金是否对应
		JSONArray  bailArray = HkStockUtil.parseLeverConfig(hkStockParams, totalMoney);
		if (CollectionUtils.isEmpty(bailArray)){
			return new JsonResult(false,"港股操盘参数配置有误！");
		}
		//校验倍数和保证金是否匹配
		if (!HkStockUtil.isRightLeverAndBailMoney(bailArray, bailMoney, lever)){
			return new JsonResult(false,"港股操盘参数配置有误！");
		}
		
		// 查下利息管理费配置
		TradeConfig config = tradeConfigService.findTradeConfig(borrowPeriod,bailMoney, lever);
		if (ObjectUtil.equals(config, null)) {
			String detail="borrwPeriod:"+borrowPeriod+"lever:"+lever+"bailMoney:"+bailMoney;
			log.error("利息配置有误"+detail);
			return new JsonResult(false,"利息配置有误！");
		}
		Double  bailCoefficient = hkStockParams.getBailCoefficient();
		//配资金额
		Double  withCapitalMoney = BigDecimalUtils.div(BigDecimalUtils.mulRound(totalMoney,lever),(lever+HkConstant.DOUBLE_ONE),HkConstant.INT_ZERO);
		//利息-天
		double interestFee = BigDecimalUtils.mulRound(BigDecimalUtils.mul(config.getDailyInterest(),BigDecimalUtils.mul(withCapitalMoney,bailCoefficient)),hkStockParams.getInterestCoefficient());
		//管理费-天
		double manageFee = BigDecimalUtils.mulRound(BigDecimalUtils.mul(config.getDailyManagementFee(),BigDecimalUtils.mul(withCapitalMoney,bailCoefficient)),hkStockParams.getManageFeeCoefficient());
		//当前应扣除的所有利息
		Double totalInterestFee = BigDecimalUtils.mulRound(interestFee,borrowPeriod);
		
		// 计算交易日
		String currentDay = Dates.format(new Date(),Dates.CHINESE_DATE_FORMAT_LONG);
		String tradeDay = hkTradeCalendarService.getTradeDay();
		// 当前时间是否交易日
		boolean isTradeDay = Boolean.FALSE;
		if (tradeDay.equals(currentDay)) {
			isTradeDay = Boolean.TRUE;
		}
		// 若当前是非交易日，但是tradeStart=0 立即交易冲突
		if(!isTradeDay && tradeStart==HkConstant.INT_ZERO){
				throw new UserTradeException("no.trade.start", null);				
		}

		//校验用户余额
		WUser user = wUserService.getUser(userSessionBean.getId());
		UserVerified userVerified = securityInfoService.findByUserId(user.getId());
		int isVerified=HkConstant.INT_ZERO; //是否实名认证
		if(!ObjectUtils.equals(userVerified, null) && StringUtil.isNotBlank(userVerified.getIdcard())){
			isVerified=HkConstant.INT_ONE;
		}
		double needPay=BigDecimalUtils.addRound(bailMoney, totalInterestFee); //当前应付款
		//立即交易扣取今日管理费
		if(tradeStart==HkConstant.INT_ZERO){
			needPay=BigDecimalUtils.addRound(needPay,manageFee);
		}
		//明天是否交易日，是交易日算上管理费
		String nextDayDate = Dates.format(new Date(),Dates.CHINESE_DATE_FORMAT_LONG);
		double needNextdayPay=HkConstant.DOUBLE_ZERO;//算上明日扣取费用应付款
		if (hkTradeCalendarService.isTradeDay(nextDayDate)){
			needNextdayPay = BigDecimalUtils.addRound(needPay, manageFee); 	
		}
		Map<Object, Object> modelMap = Maps.newHashMap();
		// 校验是否够扣
		double payEnough = BigDecimalUtils.sub(user.getAvlBal(), needPay);
		double payNextdayEnough = BigDecimalUtils.sub(user.getAvlBal(), needNextdayPay);
		if (payNextdayEnough<HkConstant.INT_ZERO) {
			modelMap.put("needNext", "on"); // 开启下个交易日不够扣提示
		}
		else {
			modelMap.put("needNext", "off");
		}
		// 今日是否够扣金额
		if (payEnough < HkConstant.INT_ZERO) {
			modelMap.put("need", "on");  //开启本次还欠费提示
			modelMap.put("avlBal", user.getAvlBal());
			modelMap.put("payEnough", -payEnough); 
		} 
		else {
			modelMap.put("need", "off");
		}
		modelMap.put("needPay", needPay);
		modelMap.put("isVerified", isVerified);
		JsonResult result = new JsonResult(true, "处理成功！");
		result.setData(modelMap);
		return result;
	}
	
}
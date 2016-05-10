package com.tzdr.web.hkstock.controller;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jodd.util.ObjectUtil;
import jodd.util.StringUtil;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.google.common.collect.Maps;
import com.tzdr.business.exception.UserTradeException;
import com.tzdr.business.hkstock.exception.HkUserTradeException;
import com.tzdr.business.hkstock.service.HkStockParamsService;
import com.tzdr.business.hkstock.service.HkTradeCalendarService;
import com.tzdr.business.hkstock.service.HkUserTradeService;
import com.tzdr.business.service.securityInfo.SecurityInfoService;
import com.tzdr.business.service.thread.SMSSendForContentThread;
import com.tzdr.business.service.userTrade.FSimpleParitiesService;
import com.tzdr.business.service.userTrade.NoticeRecordService;
import com.tzdr.business.service.userTrade.TradeConfigService;
import com.tzdr.business.service.wuser.WUserService;
import com.tzdr.business.thread.HkStockContractThread;
import com.tzdr.common.exception.EmailExceptionHandler;
import com.tzdr.common.utils.BigDecimalUtils;
import com.tzdr.common.utils.Dates;
import com.tzdr.common.utils.MessageUtils;
import com.tzdr.common.web.support.JsonResult;
import com.tzdr.domain.hkstock.constants.HkConstant;
import com.tzdr.domain.hkstock.entity.HkStockParams;
import com.tzdr.domain.hkstock.entity.HkUserTrade;
import com.tzdr.domain.web.entity.NoticeRecord;
import com.tzdr.domain.web.entity.TradeConfig;
import com.tzdr.domain.web.entity.UserVerified;
import com.tzdr.domain.web.entity.WUser;
import com.tzdr.web.constants.Constants;
import com.tzdr.web.hkstock.constants.HkViewConstants;
import com.tzdr.web.hkstock.utils.HkStockUtil;
import com.tzdr.web.utils.UserSessionBean;

@Controller
@RequestMapping("/userhkstock")
public class HkUserTradeController {

	private static Logger log = LoggerFactory.getLogger(HkUserTradeController.class);

	@Autowired
	private  HkUserTradeService  hkUserTradeService;
	
	@Autowired
	private  NoticeRecordService noticeRecordService;
	 
	@Autowired
	private HkTradeCalendarService hkTradeCalendarService;
	
	
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
	 * 确认操盘方案
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
	@RequestMapping(value = "/confirm")
	public String comfirm(ModelMap modelMap, int tradeStart, int lever, double bailMoney, 
			int borrowPeriod, double totalMoney,HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UserSessionBean userSessionBean = (UserSessionBean) request.getSession().getAttribute(Constants.TZDR_USER_SESSION);
		if (ObjectUtil.equals(null, userSessionBean)) {
			log.debug("未获取到登录信息");
			throw new HkUserTradeException("hkstock.request.invalid",null);
		}
		//获取港股操盘参数
		HkStockParams  hkStockParams = hkStockParamsService.getParams();
		request.setAttribute("hkStockParams", hkStockParams);
		if (ObjectUtil.equals(null, hkStockParams)){
			log.debug("港股操盘参数配置信息不存在。");
			throw new HkUserTradeException("hkstock.params.config.error",null);
		}
		//校验倍数与操盘保证金是否对应
		JSONArray  bailArray = HkStockUtil.parseLeverConfig(hkStockParams, totalMoney);
		if (CollectionUtils.isEmpty(bailArray)){
			throw new HkUserTradeException("hkstock.params.config.error",null);
		}
		//校验倍数和保证金是否匹配
		if (!HkStockUtil.isRightLeverAndBailMoney(bailArray, bailMoney, lever)){
			throw new HkUserTradeException("hkstock.request.invalid",null);
		}
		
		// 查下利息管理费配置
		TradeConfig config = tradeConfigService.findTradeConfig(borrowPeriod,bailMoney, lever);
		if (ObjectUtil.equals(config, null)) {
			String detail="borrwPeriod:"+borrowPeriod+"lever:"+lever+"bailMoney:"+bailMoney;
			log.error("利息配置有误"+detail);
			throw new HkUserTradeException("no.interest.config",null);
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
		// 补仓线
		Double waringMoney = BigDecimalUtils.mulRound(withCapitalMoney,HkStockUtil.parseWarningCoefficient(hkStockParams,lever), 0);
		// 平仓线
		Double openMoney = BigDecimalUtils.mulRound(withCapitalMoney,HkStockUtil.parseOpenCoefficient(hkStockParams,lever), 0);
	
		
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
		//获取操盘风险规则
		String operatorsInfo = HkStockUtil.operatorsInfo(withCapitalMoney);

		modelMap.put("lever", lever);
		modelMap.put("tradeStart", tradeStart);
		modelMap.put("bailMoney", bailMoney);
		modelMap.put("capitalAmount", withCapitalMoney);
		modelMap.put("totalMoney", totalMoney);
		modelMap.put("borrowPeriod", borrowPeriod);
		modelMap.put("totalInterestFee", totalInterestFee);
		modelMap.put("manageFee", manageFee);
		modelMap.put("waringMoney",waringMoney);
		modelMap.put("openMoney", openMoney);
		modelMap.put("operatorsInfo", operatorsInfo);
		
		//校验用户余额
		WUser user = wUserService.getUser(userSessionBean.getId());
		UserVerified userVerified = securityInfoService.findByUserId(user.getId());
		int isVerified=HkConstant.INT_ZERO; //是否实名认证
		if(!ObjectUtils.equals(userVerified, null)&& StringUtil.isNotBlank(userVerified.getIdcard())){
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
		
		return HkViewConstants.HkUserTradeViews.HK_TRADE_CONFIRM;
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
	
	
	/**
	 * 操盘申请提交处理 -锁
	 */
	private static Object lock = new Object();

	/**
	 * 提交申请操盘处理
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
	@RequestMapping(value = "/handle")
	public String handle(ModelMap modelMap, int tradeStart, int lever, double bailMoney, 
			int borrowPeriod, double totalMoney,HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UserSessionBean userSessionBean = (UserSessionBean) request.getSession().getAttribute(Constants.TZDR_USER_SESSION);
		if (ObjectUtil.equals(null, userSessionBean)) {
			log.debug("未获取到登录信息");
			throw new HkUserTradeException("hkstock.request.invalid",null);
		}
		//查询实名认证
		UserVerified isUserVerified = securityInfoService.findByUserId(userSessionBean.getId());
		if (ObjectUtils.equals(isUserVerified, null) || StringUtil.isBlank(isUserVerified.getIdcard())) {
			throw new HkUserTradeException("no.user.verified", null);
		}
		//获取港股操盘参数
		HkStockParams  hkStockParams = hkStockParamsService.getParams();
		request.setAttribute("hkStockParams", hkStockParams);
		if (ObjectUtil.equals(null, hkStockParams)){
			log.debug("港股操盘参数配置信息不存在。");
			throw new HkUserTradeException("hkstock.params.config.error",null);
		}
		//校验总操盘资金最大、最小限制
		if (totalMoney < hkStockParams.getMinTotalMoney() || totalMoney > hkStockParams.getMaxTotalMoney()){
			throw new HkUserTradeException("hkstock.handle.total.money.error",null);
		}
		
		//校验总操盘资金最大、最小限制
		if (borrowPeriod < hkStockParams.getMinHoldDays() || borrowPeriod > hkStockParams.getMaxHoldDays()){
			throw new HkUserTradeException("hkstock.handle.use.day.error",null);
		}
		//校验倍数与操盘保证金是否对应
		JSONArray  bailArray = HkStockUtil.parseLeverConfig(hkStockParams, totalMoney);
		if (CollectionUtils.isEmpty(bailArray)){
			throw new HkUserTradeException("hkstock.params.config.error",null);
		}
		//校验倍数和保证金是否匹配
		if (!HkStockUtil.isRightLeverAndBailMoney(bailArray, bailMoney, lever)){
			throw new HkUserTradeException("hkstock.request.invalid",null);
		}
		
		// 查下利息管理费配置
		TradeConfig config = tradeConfigService.findTradeConfig(borrowPeriod,bailMoney, lever);
		if (ObjectUtil.equals(config, null)) {
			String detail="borrwPeriod:"+borrowPeriod+"lever:"+lever+"bailMoney:"+bailMoney;
			log.error("利息配置有误"+detail);
			throw new HkUserTradeException("no.interest.config",null);
		}
		
		Double  bailCoefficient = hkStockParams.getBailCoefficient();;
		//配资金额
		Double  withCapitalMoney = BigDecimalUtils.div(BigDecimalUtils.mulRound(totalMoney,lever),(lever+HkConstant.DOUBLE_ONE),HkConstant.INT_ZERO);
		//利息-天
		double interestFee = BigDecimalUtils.mulRound(BigDecimalUtils.mul(config.getDailyInterest(),BigDecimalUtils.mul(withCapitalMoney,bailCoefficient)),hkStockParams.getInterestCoefficient());
		//管理费-天
		double manageFee = BigDecimalUtils.mulRound(BigDecimalUtils.mul(config.getDailyManagementFee(),BigDecimalUtils.mul(withCapitalMoney,bailCoefficient)),hkStockParams.getManageFeeCoefficient());
		//当前应扣除的所有利息
		Double totalInterestFee = BigDecimalUtils.mulRound(interestFee,borrowPeriod);
		// 补仓线
		Double waringMoney = BigDecimalUtils.mulRound(withCapitalMoney,HkStockUtil.parseWarningCoefficient(hkStockParams,lever), 0);
		// 平仓线
		Double openMoney = BigDecimalUtils.mulRound(withCapitalMoney,HkStockUtil.parseOpenCoefficient(hkStockParams,lever), 0);
	
		
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
				throw new HkUserTradeException("no.trade.start", null);				
		}
		if(isTradeDay&&tradeStart==HkConstant.INT_ONE){
			tradeDay=hkTradeCalendarService.getNextTradeDay();
		}
		
		//校验用户余额
		WUser wuser = wUserService.getUser(userSessionBean.getId());		
		double needPay=BigDecimalUtils.addRound(bailMoney, totalInterestFee); //当前应付款
		//立即交易扣取今日管理费
		if(tradeStart==HkConstant.INT_ZERO){
			needPay=BigDecimalUtils.addRound(needPay,manageFee);
		}
		
		//新建操盘方案
		HkUserTrade  hkUserTrade  = new HkUserTrade();
		hkUserTrade.setWuser(wuser);
		hkUserTrade.setTradeStart((short) tradeStart);
		hkUserTrade.setMoney(withCapitalMoney); // 配资金额-港元
		hkUserTrade.setWarning(waringMoney);
		hkUserTrade.setOpen(openMoney);
		hkUserTrade.setLever(lever);
		hkUserTrade.setLeverMoney(bailMoney);
		hkUserTrade.setAddtime(Dates.getCurrentLongDate());
		// 设置交易开始时间、预计结束日期、交易自然天数
		Date trade = Dates.parse(tradeDay, Dates.CHINESE_DATE_FORMAT_LONG);
		hkUserTrade.setStarttime(trade.getTime() / 1000);
		String expirationDate = hkTradeCalendarService.getEndDate(tradeDay, borrowPeriod);
		Date estimateEnd = Dates.parse(expirationDate,Dates.CHINESE_DATE_FORMAT_LONG);
		hkUserTrade.setEstimateEndtime(estimateEnd.getTime() / 1000);
		hkUserTrade.setNaturalDays((long)borrowPeriod);
		
		//中间的交易日天数
		long tradeDays=hkTradeCalendarService.getTradeDays(tradeDay, expirationDate);
		hkUserTrade.setStartdays((int)tradeDays);
		// 日(管理费)
		hkUserTrade.setFeeDay(manageFee);
		// 月(利息)
		hkUserTrade.setFeeMonth(interestFee);
		hkUserTrade.setTotalLeverMoney(totalMoney);
		//操盘申请港币汇率
		hkUserTrade.setApplyExchangeRate(bailCoefficient);
		
		/**
		 * 处理并保存操盘申请信息
		 */
		try {
			synchronized(lock) {
				HkUserTrade  returnHkUserTrade = hkUserTradeService.saveHkTrade(hkUserTrade, wuser);
				//配资时管理费新校验,下个交易日是否够扣
				String nextDay = Dates.format(Dates.addDay(new Date()),Dates.CHINESE_DATE_FORMAT_LONG);
				boolean isNextTradeDay = hkTradeCalendarService.isTradeDay(nextDay);
				if(isNextTradeDay){
					if(BigDecimalUtils.sub(wuser.getAvlBal(), manageFee)<0){
						String content = MessageUtils.message("fee.fill.deduction.balance.next.day.not.enough");
						new SMSSendForContentThread(wuser.getMobile(),content,2000).start();
						//保存通知记录
						noticeRecordService.save(new NoticeRecord(wuser.getId(), content, 3));
					}			
				}
				/**
				* @Description:生成数据保存中心数据 (处理配资合同动态参数)
				*/
				UserVerified userVerified=wuser.getUserVerified();
				String programNo=hkUserTrade.getProgramNo(); // 方案编号
				String loanTrueName=userVerified.getTname(); // 真实姓名
				String cardNo=userVerified.getIdcard(); // 身份证号
				String email=userVerified.getValidateemail();// 认证邮箱
				String nickName=wuser.getMobile();  // 客户手机号
				String startTime=Dates.format(Dates.parseLong2Date(hkUserTrade.getStarttime()), Dates.CHINESE_DATE_FORMAT_LINE); // 方案开始时间
				String endTime=Dates.format(Dates.parseLong2Date(hkUserTrade.getEstimateEndtime()), Dates.CHINESE_DATE_FORMAT_LINE); // 方案预计结束时间
				String bathPath=	request.getSession().getServletContext().getRealPath("/");
				new HkStockContractThread(programNo, loanTrueName, cardNo, nickName,withCapitalMoney, interestFee,bailMoney, manageFee,borrowPeriod, startTime, endTime, waringMoney, openMoney, bathPath,totalMoney,wuser.getId(),email).start();
				//申请成功返回值
				modelMap.put("mobile", wuser.getMobile());
				modelMap.put("groupId",returnHkUserTrade.getGroupId());
			}
		} catch (Exception e) {
			String dataDetail="userInfo:id:"+wuser.getId()+"|mobile:"+wuser.getMobile()+"|异常：";
			if ( e instanceof HkUserTradeException){
				HkUserTradeException tempException = (HkUserTradeException) e;
				dataDetail = dataDetail + tempException.getResourceMessage();
			}
			log.error(dataDetail);			
			EmailExceptionHandler.getInstance().HandleExceptionWithData(e, "港股操盘申请失败异常", this.getClass().getName()+":tradeOk", dataDetail);
			return "redirect:/userhkstock/handleFail";
		}
		return "redirect:/userhkstock/handleSuccess";
	}
	


	@RequestMapping(value = "/handleSuccess")
	public String handleSuccess(ModelMap modelMap,HttpServletRequest request,
			@RequestParam String groupId,@RequestParam(required=false) String mobile) throws Exception {
		modelMap.put("groupId", groupId);
		modelMap.put("mobile", mobile);
		return HkViewConstants.HkUserTradeViews.TRADE_OK;
	}
	
	@RequestMapping(value = "/handleFail")
	public String handleFail(ModelMap modelMap,HttpServletRequest request) throws Exception {		
		return HkViewConstants.HkUserTradeViews.TRADE_FAIL;
	}
}

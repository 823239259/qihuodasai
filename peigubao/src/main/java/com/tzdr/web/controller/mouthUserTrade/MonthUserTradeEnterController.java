package com.tzdr.web.controller.mouthUserTrade;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jodd.util.ObjectUtil;

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

import com.google.common.collect.Maps;
import com.tzdr.business.exception.UserTradeException;
import com.tzdr.business.service.account.ParentAccountService;
import com.tzdr.business.service.bespokeTrade.BespokeTradeService;
import com.tzdr.business.service.datamap.DataMapService;
import com.tzdr.business.service.fundconfig.FundConfigService;
import com.tzdr.business.service.future.FSimpleCouponService;
import com.tzdr.business.service.monthUserTradeParams.MonthUserTradeParamsService;
import com.tzdr.business.service.pay.UserFundService;
import com.tzdr.business.service.realdeal.RealDealService;
import com.tzdr.business.service.securityInfo.SecurityInfoService;
import com.tzdr.business.service.tradecalendar.TradeDayService;
import com.tzdr.business.service.userTrade.TradeConfigService;
import com.tzdr.business.service.userTrade.UserTradeService;
import com.tzdr.business.service.volume.VolumeDetailService;
import com.tzdr.business.service.wuser.WUserService;
import com.tzdr.common.utils.BigDecimalUtils;
import com.tzdr.common.utils.Dates;
import com.tzdr.common.web.support.JsonResult;
import com.tzdr.domain.constants.Constant;
import com.tzdr.domain.vo.UserTradeVo;
import com.tzdr.domain.web.entity.FundConfig;
import com.tzdr.domain.web.entity.MonthUserTradeParams;
import com.tzdr.domain.web.entity.TradeConfig;
import com.tzdr.domain.web.entity.UserVerified;
import com.tzdr.domain.web.entity.WUser;
import com.tzdr.web.constants.Constants;
import com.tzdr.web.constants.ViewConstants;
import com.tzdr.web.utils.UserSessionBean;
import com.tzdr.web.utils.WebUtil;


@Controller
@RequestMapping("/monthTrade")
public class MonthUserTradeEnterController {

	private static Logger log = LoggerFactory
			.getLogger(MonthUserTradeEnterController.class);

	@Autowired
	private UserTradeService userTradeService;
	@Autowired
	private VolumeDetailService volumeDetailService;
	@Autowired
	private TradeDayService tradeDayService;

	@Autowired
	private TradeConfigService tradeConfigService;

	@Autowired
	private WUserService wUserService;
	
	@Autowired
	private UserFundService userFundService;
	
	@Autowired
	private ParentAccountService parentAccountService;
	
	
	@Autowired
	private SecurityInfoService securityInfoService;

	@Autowired
	private FSimpleCouponService fSimpleCouponService;

	@Autowired
	private RealDealService realDealService;
	
	@Autowired
	private FundConfigService fundConfigService;
	
	@Autowired
	private DataMapService  dataMapService;
	@Autowired
	private BespokeTradeService bespokeTradeService;
	
	@Autowired
	private MonthUserTradeParamsService monthUserTradeParamsService;
	/**
	* @Description: (配资合同)
	* @Title: websiteAgreement
	* @param modelMap
	* @param request
	* @param response
	* @return String    返回类型
	 */
	@RequestMapping(value = "/tradeContract")
	public String tradeContract(ModelMap modelMap,HttpServletRequest request,HttpServletResponse response){
		return ViewConstants.UserTradeViewJsp.MONTH_TRADECONTRACT;
	}

	@RequestMapping(value = "/index")
	public String perByDay(ModelMap modelMap,HttpServletRequest request) throws Exception {
		String lever=request.getParameter("lever")!=null?request.getParameter("lever"):"1"; //倍数
		String capitalMargin=request.getParameter("capitalMargin")!=null?request.getParameter("capitalMargin"):"0";
		String borrowPeriod=request.getParameter("borrowPeriod")!=null?request.getParameter("borrowPeriod"):"2"; //天数
		String tradeStart=request.getParameter("tradeStart")!=null?request.getParameter("tradeStart"):"1";
		modelMap.put("lever", lever);
		modelMap.put("capitalMargin", (long)Double.parseDouble(capitalMargin));
		modelMap.put("borrowPeriod", borrowPeriod);
		modelMap.put("tradeStart", tradeStart);
		modelMap.put("maxLeverMoney", Double.valueOf(dataMapService.maxLeverMoney()));
		modelMap.put("isOpen", dataMapService.isOpenMaxLeverMoney());
		List<FundConfig> findConfigList=fundConfigService.findOrderByTimesAsc();
		modelMap.put("findConfigList", findConfigList);
		UserSessionBean userSessionBean = (UserSessionBean)request.getSession().getAttribute(Constants.TZDR_USER_SESSION);
		if(userSessionBean!=null){
			modelMap.put("loginStatus", 1);
		}else{
			modelMap.put("loginStatus", 0);
		}
		//限制当天当个用户配资数
		String limitTradeNum = dataMapService.isLimitTodayTradeNum();
		modelMap.put("limitTradeNum", limitTradeNum);
		//数据库取页面参数
		MonthUserTradeParams monthUserTradeParams = monthUserTradeParamsService.getParams();	
		if (ObjectUtil.equals(null, monthUserTradeParams)){
			log.debug("月月配操盘参数配置信息不存在。");
			return ViewConstants.ERROR_VIEW;
		}
		
		String[] moneys = monthUserTradeParams.getRecommendHoldMoney().split(Constants.SEPERATOR_SEMICOLON);
		String[] months = monthUserTradeParams.getRecommendHoldMonths().split(Constants.SEPERATOR_SEMICOLON);
		String[] levers = monthUserTradeParams.getLeverConfig().split(Constants.SEPERATOR_SEMICOLON);
		modelMap.addAttribute("maxMoney",monthUserTradeParams.getMaxTotalMoney());
		modelMap.addAttribute("minMoney",monthUserTradeParams.getMinTotalMoney());
		modelMap.addAttribute("maxLever",levers[levers.length-1]);
		modelMap.addAttribute("minLever",levers[0]);
		modelMap.addAttribute("maxMonth",months[months.length-1]);
		modelMap.addAttribute("minMonth",months[0]);
		modelMap.addAttribute("money",moneys);
		modelMap.addAttribute("months",months);
		modelMap.addAttribute("levers",levers);
		return ViewConstants.UserTradeViewJsp.MONTH_PER_BY_DAY;
	}
		
	@RequestMapping(value = "/isTrading.json" , method = RequestMethod.POST)
	@ResponseBody
	public JsonResult tradeDay(HttpServletRequest request,
			HttpServletResponse response) {
		JsonResult jsonResult = new JsonResult(false);
		String currentDay = Dates.format(new Date(),
				Dates.CHINESE_DATE_FORMAT_LONG);
		String tradeDay = tradeDayService.getTradeDay();
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
	
	
	@RequestMapping(value = "/endDay.json" , method = RequestMethod.POST)
	@ResponseBody
	public JsonResult getEndDay( @RequestParam int borrowPeriod, @RequestParam int tradeStart,
			HttpServletRequest request) {
		JsonResult jsonResult = new JsonResult(false);
		Integer borrowPeriodDays=borrowPeriod*Constant.MONTH_TRADE_MONTH_DAYS;
		String endDay = getEndDay(borrowPeriodDays, tradeStart);
	
		Map<Object, Object> map = Maps.newHashMap();

		map.put("endDay", endDay);
		jsonResult.setData(map);
		jsonResult.setSuccess(true);
		return jsonResult;
	}
	
	
	
	
	


	@RequestMapping(value = "/data.json" , method = RequestMethod.POST)
	@ResponseBody
	public JsonResult data(@RequestParam int borrowPeriod,
			@RequestParam int lever, @RequestParam double capitalMargin,@RequestParam int tradeStart,
			HttpServletRequest request) {
		JsonResult jsonResult = new JsonResult(false);
		List<MonthUserTradeParams> monthUserTradeParams = monthUserTradeParamsService.getAll();
		if(CollectionUtils.isEmpty(monthUserTradeParams)){
			jsonResult.setMessage("参数异常！请重试");
			return jsonResult;
		}
		MonthUserTradeParams monthTrade = monthUserTradeParams.get(0);
		Double interest = monthTrade.getInterestCoefficient();
		Double expenese = monthTrade.getManageFeeCoefficient();
		String endDay = getEndDay(borrowPeriod, tradeStart);
		double capitalAmount = BigDecimalUtils.mul(capitalMargin, lever);
		Map<Object, Object> map = Maps.newHashMap();
		
		TradeConfig config = tradeConfigService.findTradeConfig(borrowPeriod,
				capitalMargin, lever);
		// 利息（天）
		double interestFee=0;
		// 管理费（天）
		double manageFee =0;
		if(lever>0){
			if (ObjectUtil.equals(config, null)) {
				String detail="borrwPeriod:"+borrowPeriod+"lever:"+lever+"capitalMargin:"+capitalMargin;
				log.error("利息配置有误"+detail);
				throw new UserTradeException("no.interest.config",null);
			}
			interestFee = BigDecimalUtils.mulRound(config.getDailyInterest(),capitalAmount);
			manageFee = BigDecimalUtils.mulRound(config.getDailyManagementFee(), capitalAmount);
		}
		if(null != interest){
			interestFee=BigDecimalUtils.mulRound(interestFee,interest);
		}
		if(null != expenese ){
			manageFee=BigDecimalUtils.mulRound(manageFee,expenese);
		}
		String endTime = Dates.format(
				Dates.parse(endDay,
						Dates.CHINESE_DATE_FORMAT_LINE),
				Dates.CHINESE_DATE_FORMAT_LONG);
		Integer tradeDays=tradeDayService.getTradeDays(this.getStartTime(tradeStart), endTime);
		double totalFee=BigDecimalUtils.addRound(BigDecimalUtils.mulRound(interestFee,borrowPeriod),BigDecimalUtils.mulRound(manageFee,tradeDays));
		map.put("endDay", endDay);
		map.put("totalFee", totalFee);
		jsonResult.setData(map);
		jsonResult.setSuccess(true);
		return jsonResult;
	}

	/**
	 * 获取方案开始时间
	 * @param tradeStart
	 * @return
	 */
	private String getStartTime(int tradeStart){
		String currentDay = Dates.format(new Date(),
				Dates.CHINESE_DATE_FORMAT_LONG);
		String tradeDay = tradeDayService.getTradeDay();
		boolean isTradeDay = Boolean.FALSE;
		if (tradeDay.equals(currentDay)) {
			isTradeDay = Boolean.TRUE;
		}
		if(!isTradeDay){
			if(tradeStart==0){
				throw new UserTradeException("no.trade.start", null);	
			}				
		}
		
		if(isTradeDay && tradeStart==1){
			tradeDay=tradeDayService.getNextTradeDay();
		}
		return tradeDay;
	}
	private String getEndDay(int borrowPeriod, int tradeStart) {
		String currentDay = Dates.format(new Date(),
				Dates.CHINESE_DATE_FORMAT_LONG);
		String tradeDay = tradeDayService.getTradeDay();
		boolean isTradeDay = Boolean.FALSE;
		if (tradeDay.equals(currentDay)) {
			isTradeDay = Boolean.TRUE;
		}
		
		if(isTradeDay&&tradeStart==1){
			tradeDay=tradeDayService.getNextTradeDay();
		}

		String expirationDate = tradeDayService.getEndDate(tradeDay,
				borrowPeriod);
		String endDay = Dates.format(
				Dates.parse(String.valueOf(expirationDate),
						Dates.CHINESE_DATE_FORMAT_LONG),
				Dates.CHINESE_DATE_FORMAT_LINE);
		return endDay;
	}

	@RequestMapping(value = "/check.json" , method = RequestMethod.POST)
	@ResponseBody
	public JsonResult checkData(@RequestParam int borrowPeriod,
			@RequestParam int lever, @RequestParam double capitalMargin,@RequestParam int tradeStart,
			HttpServletRequest request) {
		JsonResult jsonResult = new JsonResult(false);
		List<MonthUserTradeParams> monthUserTradeParams = monthUserTradeParamsService.getAll();
		if(CollectionUtils.isEmpty(monthUserTradeParams)){
			jsonResult.setMessage("参数异常！请重试");
			return jsonResult;
		}
		MonthUserTradeParams monthTrade = monthUserTradeParams.get(0);
		Double interest = monthTrade.getInterestCoefficient();
		Double expenese = monthTrade.getManageFeeCoefficient();
//		String voucherId=request.getParameter("voucherId");

		
		UserSessionBean userSessionBean = (UserSessionBean) request.getSession().getAttribute(
				Constants.TZDR_USER_SESSION);


		//配资金额
		double capitalAmount = BigDecimalUtils.mul(capitalMargin, lever);
		Integer borrowPeriodDays = borrowPeriod*Constant.MONTH_TRADE_MONTH_DAYS;
		TradeConfig config = tradeConfigService.findTradeConfig(borrowPeriodDays,
				capitalMargin, lever);
		//配资结算信息
		if (ObjectUtil.equals(config, null)) {
			String detail="borrwPeriod:"+borrowPeriod+"lever:"+lever+"capitalMargin:"+capitalMargin;
			log.error("利息配置有误"+detail);
			throw new UserTradeException("no.interest.config",null);
		}
		//获取当前时间yyyyMMdd
		String currentDay = Dates.format(new Date(),
				Dates.CHINESE_DATE_FORMAT_LONG);
		String tradeDay = tradeDayService.getTradeDay();
		//是否为“立即交易”
		boolean isTradeDay = Boolean.FALSE;
		if (tradeDay.equals(currentDay)) {
			isTradeDay = Boolean.TRUE;
		}
		
		if(!isTradeDay){
			if(tradeStart==0){
				throw new UserTradeException("no.trade.start", null);	
			}				
		}
		
		// 利息（天）
		double interestFee = 
				BigDecimalUtils.mulRound(config.getDailyInterest(), capitalAmount);
		// 管理费（天）
		double manageFee = 
				BigDecimalUtils.mulRound(config.getDailyManagementFee(), capitalAmount);
		if(null != interest){
			interestFee=BigDecimalUtils.mulRound(interestFee,interest);

		}
		if(null != expenese){
			manageFee=BigDecimalUtils.mulRound(manageFee,expenese);

		}


		
		//需要支付的总金额 = 配资保证金  + 总利息
		
		String endDay = getEndDay(borrowPeriodDays, tradeStart);
		String endTime = Dates.format(
				Dates.parse(endDay,
						Dates.CHINESE_DATE_FORMAT_LINE),
				Dates.CHINESE_DATE_FORMAT_LONG);
		Integer tradeDays=tradeDayService.getTradeDays(this.getStartTime(tradeStart), endTime);

		double totalFee=BigDecimalUtils.add(BigDecimalUtils.mul(BigDecimalUtils.mul(interestFee,borrowPeriod),Constant.MONTH_TRADE_MONTH_DAYS),BigDecimalUtils.mul(manageFee,tradeDays));
		double needPay = BigDecimalUtils.addRound(totalFee, capitalMargin);
		
		WUser wuser = wUserService.getUser(userSessionBean.getId());
		UserVerified userVerified = securityInfoService.findByUserId(userSessionBean.getId());
		int isVerified=0;
		if(!ObjectUtils.equals(userVerified, null)&&!ObjectUtils.equals(userVerified.getIdcard(), null)){
			isVerified=1;
		}
		
		Map<Object, Object> map = Maps.newHashMap();




	    //“立即交易类型时”需要支付的金额
		double payEnough= BigDecimalUtils.sub(wuser.getAvlBal(), needPay);
		//“下一个交易类型时”需要支付的金额
		double payNextdayEnough= BigDecimalUtils.sub(wuser.getAvlBal(), needPay);
		int proCount=userTradeService.findUserTradesNotBespoke(userSessionBean.getId());
		// 判断是否超过最大操盘方案数  默认最多3个
		int holdMaxNum = dataMapService.getHoldMaxTradeNum();
		map.put("holdMaxNum", holdMaxNum);
		//获取用户当天配资数
		List<UserTradeVo> userTradeVoList = userTradeService.queryUserDayTrades(userSessionBean.getId(),Dates.format(Dates.CHINESE_DATE_FORMAT_LINE));
		if (userTradeVoList != null && !userTradeVoList.isEmpty()) {
			map.put("userTodayTradeNum", userTradeVoList.size());
		}
		else {
			map.put("userTodayTradeNum", 0);
		}
		
		//限制当天当个用户配资数
		String limitTradeNum = dataMapService.isLimitTodayTradeNum();
		map.put("limitTradeNum", limitTradeNum);




		if (payNextdayEnough < 0) {
			map.put("needNext", "on");
		}
		else {
			map.put("needNext", "off");
		}
		map.put("avlBal", wuser.getAvlBal());
		if (payEnough < 0) {
			map.put("need", "on");
			map.put("payEnough", -payEnough);
			map.put("needPay", needPay);
			map.put("isVerified", isVerified);
			map.put("proCount", proCount);
		} 
		else {
			map.put("proCount", proCount);
			map.put("need", "off");
			map.put("needPay", needPay);
			map.put("isVerified", isVerified);
		}
		jsonResult.setData(map);
		jsonResult.setSuccess(true);
		return jsonResult;
	}
	
	
	@RequestMapping(value = "/totrade")
	public String toTrade(ModelMap modelMap, int tradeStart, int lever,
			int type, double capitalMargin, int borrowPeriod, String backtype,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		// 申请操盘时校验 倍数是否合法
		if (!WebUtil.validateTradeLever(lever, capitalMargin)){
			throw new UserTradeException("user.trade.select.lever.not.valid",null);
		}
		
		UserSessionBean userSessionBean = (UserSessionBean) request.getSession().getAttribute(
				Constants.TZDR_USER_SESSION);
		double capitalAmount = BigDecimalUtils.mul(capitalMargin, lever);
		double totalTrader = BigDecimalUtils.addRound(capitalMargin, capitalAmount);

		Integer borrowPeriodDays =  borrowPeriod*Constant.MONTH_TRADE_MONTH_DAYS;
		TradeConfig config = tradeConfigService.findTradeConfig(borrowPeriodDays,
				capitalMargin, lever);
		if (ObjectUtil.equals(config, null)) {
			String detail="borrwPeriod:"+borrowPeriodDays+"lever:"+lever+"capitalMargin:"+capitalMargin;
			log.error("利息配置有误"+detail);
			throw new UserTradeException("no.interest.config",null);
		}
		
		List<MonthUserTradeParams> monthUserTradeParams = monthUserTradeParamsService.getAll();
		if(CollectionUtils.isEmpty(monthUserTradeParams)){
			log.error("参数配置异常");
			throw new UserTradeException("paramsworng",null);
		}
		
		String tradeDay = this.getStartTime(tradeStart);		
		MonthUserTradeParams monthTrade = monthUserTradeParams.get(0);		
	
		// 利息（天）
		double interestFee = 
				BigDecimalUtils.mulRound(config.getDailyInterest(), capitalAmount);
		interestFee = BigDecimalUtils.mulRound(monthTrade.getInterestCoefficient(),interestFee);
		// 管理费（天）
		double manageFee = 
				BigDecimalUtils.mulRound(config.getDailyManagementFee(), capitalAmount);
		manageFee = BigDecimalUtils.mulRound(monthTrade.getManageFeeCoefficient(), manageFee);
		
		String endDay = getEndDay(borrowPeriodDays, tradeStart);
		String endTime = Dates.format(
				Dates.parse(endDay,
						Dates.CHINESE_DATE_FORMAT_LINE),
				Dates.CHINESE_DATE_FORMAT_LONG);
		Integer tradeDays=tradeDayService.getTradeDays(this.getStartTime(tradeStart), endTime);
		double totalFee=BigDecimalUtils.addRound(BigDecimalUtils.mul(BigDecimalUtils.mul(interestFee,borrowPeriod),Constant.MONTH_TRADE_MONTH_DAYS),BigDecimalUtils.mul(manageFee,tradeDays));
		modelMap.addAttribute("totalFee",totalFee);
		double shortLine = 0.0;
		double openLine = 0.0;

		if (1 <= lever && lever <= 5) {
			shortLine = BigDecimalUtils.mul(capitalAmount, 1.1);
			openLine = BigDecimalUtils.mul(capitalAmount, 1.07);
		} else if (lever==6) {
			shortLine = BigDecimalUtils.mul(capitalAmount, 1.0867);
			openLine = BigDecimalUtils.mul(capitalAmount, 1.06);
		} else if (lever==7) {
			/*shortLine = BigDecimalUtils.mul(capitalAmount, 1.0771);
			openLine = BigDecimalUtils.mul(capitalAmount, 1.0529);*/
			shortLine = BigDecimalUtils.mul(capitalAmount, 1.0929);
			openLine = BigDecimalUtils.mul(capitalAmount, 1.0571);
		} else if (lever==8) {
			/*shortLine = BigDecimalUtils.mul(capitalAmount, 1.07);
			openLine = BigDecimalUtils.mul(capitalAmount, 1.0475);*/
			shortLine = BigDecimalUtils.mul(capitalAmount, 1.0875);
			openLine = BigDecimalUtils.mul(capitalAmount, 1.0563);
		} else if (lever==9) {
			shortLine = BigDecimalUtils.mul(capitalAmount, 1.0644);
			openLine = BigDecimalUtils.mul(capitalAmount, 1.0433);
		} else if (lever==10) {
			shortLine = BigDecimalUtils.mul(capitalAmount, 1.06);
			openLine = BigDecimalUtils.mul(capitalAmount, 1.04);
		} else if (lever==11) {
			shortLine = BigDecimalUtils.mul(capitalAmount, 1.0682);
			openLine = BigDecimalUtils.mul(capitalAmount, 1.0409);
		} else if (lever==12) {
			shortLine = BigDecimalUtils.mul(capitalAmount, 1.0625);
			openLine = BigDecimalUtils.mul(capitalAmount, 1.0375);
		} else if (lever==13) {
			shortLine = BigDecimalUtils.mul(capitalAmount, 1.0577);
			openLine = BigDecimalUtils.mul(capitalAmount, 1.0346);
		} else if (lever==14) {
			shortLine = BigDecimalUtils.mul(capitalAmount, 1.0536);
			openLine = BigDecimalUtils.mul(capitalAmount, 1.0321);
		} else if (lever==15) {
			shortLine = BigDecimalUtils.mul(capitalAmount, 1.05);
			openLine = BigDecimalUtils.mul(capitalAmount, 1.03);
		}
		shortLine = BigDecimalUtils.round(shortLine,0);
		openLine = BigDecimalUtils.round(openLine,0);
		String operatorsInfo = operatorsInfo(capitalAmount);
//		List<Map<String, Object>> methods = new ArrayList<>();
//		List<Map<String, Object>> fscs = fSimpleCouponService.getFSC(userSessionBean.getId(), 11);
//		if (null != fscs && !fscs.isEmpty()) {
//			for (Map fsc : fscs) {
//				Map<String, Object> map = new HashMap<>();
//				map.put("id", fsc.get("id"));
//				String money =fsc.get("money").toString();
//				Integer types =Integer.valueOf(fsc.get("type").toString());
//				String voucher = "";
//				switch (types) {
//					case 2:
//						money=money.split("\\.")[0];
//						voucher = money + "元代金券";
//						break;
//					case 3:
//						money=money.substring(0,money.length()-1);
//						voucher = money + "折折扣券";
//						break;
//					case 6:
//						money=money.split("\\.")[0];
//						voucher = money +"元抵扣券";
//						break;
//					default:break;
//				}
//				map.put("voucher",voucher );
//				methods.add(map);
//			}
//		}
//
//		modelMap.put("voucher",methods);
		modelMap.put("lever", lever);
		modelMap.put("type", type);
		modelMap.put("tradeStart", tradeStart);
		modelMap.put("capitalMargin", capitalMargin);
		modelMap.put("capitalAmount", capitalAmount); 
		modelMap.put("totalTrader", totalTrader);
		modelMap.put("borrowPeriod", borrowPeriod);
//		modelMap.put("manageFee", manageFee);
//		modelMap.put("interestFee", interestFee);
		modelMap.put("shortLine", BigDecimalUtils.round(shortLine,0));
		modelMap.put("openLine",  BigDecimalUtils.round(openLine,0));
		modelMap.put("operatorsInfo", operatorsInfo);
		modelMap.put("backtype", backtype);
		modelMap.put("maxLeverMoney", Double.valueOf(dataMapService.maxLeverMoney()));
		modelMap.put("isOpen", dataMapService.isOpenMaxLeverMoney());
		modelMap.put("tradeTime", Dates.format(Dates.parse(tradeDay),Dates.CHINESE_DATE_FORMAT_LINE));
		if (userSessionBean != null) {
			WUser user = wUserService.getUser(userSessionBean.getId());
			
			
			UserVerified userVerified = securityInfoService.findByUserId(user.getId());
			int isVerified=0;
			if(!ObjectUtils.equals(userVerified, null)&&!ObjectUtils.equals(userVerified.getIdcard(), null)){
				isVerified=1;
			}
			double needPay = BigDecimalUtils.addRound(capitalMargin, totalFee);

			//获取用户当天配资数
			List<UserTradeVo> userTradeVoList = userTradeService.queryUserDayTrades(userSessionBean.getId(),Dates.format(Dates.CHINESE_DATE_FORMAT_LINE));
			if (userTradeVoList != null && !userTradeVoList.isEmpty()) {
				modelMap.put("userTodayTradeNum", userTradeVoList.size());
			}
			else {
				modelMap.put("userTodayTradeNum", 0);
			}
			
			//限制当天当个用户配资数
			String limitTradeNum = dataMapService.isLimitTodayTradeNum();
			modelMap.put("limitTradeNum", limitTradeNum);
			
			modelMap.put("user", "on");
			WUser wuser = wUserService.getUser(user.getId());
			
			double payEnough = BigDecimalUtils.sub(wuser.getAvlBal(), needPay);
			int proCount=userTradeService.findUserTradesNotBespoke(userSessionBean.getId());
			
			// 判断是否超过最大操盘方案数  默认最多3个
			int holdMaxNum = dataMapService.getHoldMaxTradeNum();
			modelMap.put("holdMaxNum", holdMaxNum);
						
			if (payEnough < 0) {
				modelMap.put("need", "on");
				modelMap.put("avlBal", wuser.getAvlBal());
				modelMap.put("payEnough", -payEnough);
				modelMap.put("needPay", needPay);
				modelMap.put("isVerified", isVerified);
				modelMap.put("proCount",proCount);
			} 
			else {
				modelMap.put("proCount",proCount);
				modelMap.put("need", "off");
				modelMap.put("needPay", needPay);
				modelMap.put("isVerified", isVerified);
			}
		} 
		else {
			modelMap.put("user", "off");
		}
		return ViewConstants.UserTradeViewJsp.MONTH_TRADE;
	}
	
	public String operatorsInfo(double capitalAmount) {
		String operatorsInfo = "";
		if (capitalAmount <= 100000) {
			operatorsInfo = "投资沪深A股，仓位不限制。";
		} else if (100000 < capitalAmount && capitalAmount <= 500000) {
			operatorsInfo = "投资沪深A股，仓位有限制，单股不超总操盘资金的70%。";
		} else if (500000 < capitalAmount && capitalAmount <= 1000000) {
			operatorsInfo = "投资沪深A股，仓位有限制.单股不超总操盘资金的50%。";
		} else if (1000000 < capitalAmount) {
			operatorsInfo = "投资沪深A股，仓位有限制.单股不超总操盘资金的50%(创业板33%)。";
		}
		return operatorsInfo;
	}

	@RequestMapping(value = "/maxLever.json" , method = RequestMethod.POST)
	@ResponseBody
	public JsonResult maxLever(HttpServletRequest request,
			HttpServletResponse response,@RequestParam double capitalMargin) {
		JsonResult jsonResult = new JsonResult(false);
		int lever=fundConfigService.findMaxLever(capitalMargin);
		jsonResult.setObj(lever);
		jsonResult.setSuccess(true);
		return jsonResult;
	}
	

	
	
	
	@RequestMapping(value = "/maxAmount.json" , method = RequestMethod.POST)
	@ResponseBody
	public JsonResult maxAmount(HttpServletRequest request,
			HttpServletResponse response,@RequestParam int lever) {
		JsonResult jsonResult = new JsonResult(false);
		UserSessionBean userSessionBean = (UserSessionBean) request.getSession().getAttribute(Constants.TZDR_USER_SESSION);
		double  maxAmount=fundConfigService.findAmountByTimes(lever);
		double maxLeverMoney=Double.valueOf(dataMapService.maxLeverMoney());
		String isOpen=dataMapService.isOpenMaxLeverMoney();
		Map<Object, Object> map= new HashMap<Object, Object>();
		map.put("maxLeverMoney",maxLeverMoney);
		map.put("isOpen",isOpen);
		int userTodayTradeNum = 0;
		//限制当天当个用户配资数
		int limitTradeNum = Integer.valueOf(dataMapService.isLimitTodayTradeNum().trim());
		if(userSessionBean != null){
			//获取用户当天配资数
			List<UserTradeVo> userTradeVoList = userTradeService.queryUserDayTrades(userSessionBean.getId(),Dates.format(Dates.CHINESE_DATE_FORMAT_LINE));
			if(userTradeVoList != null && !userTradeVoList.isEmpty()){
				userTodayTradeNum = userTradeVoList.size();
			}
			map.put("loginStatus", 1);
		}else{
			map.put("loginStatus", 0);
		}
		map.put("userTodayTradeNum", userTodayTradeNum);
		map.put("limitTradeNum", limitTradeNum);
		jsonResult.setObj(maxAmount);
		jsonResult.setData(map);
		jsonResult.setSuccess(true);
		return jsonResult;
	}
}

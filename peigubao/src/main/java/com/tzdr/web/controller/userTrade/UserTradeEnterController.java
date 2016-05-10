package com.tzdr.web.controller.userTrade;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
import org.springframework.web.bind.annotation.PathVariable;
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
import com.tzdr.common.utils.TypeConvert;
import com.tzdr.common.web.support.JsonResult;
import com.tzdr.domain.cache.CacheManager;
import com.tzdr.domain.vo.UserTradeVo;
import com.tzdr.domain.vo.UserVolumeDetailVo;
import com.tzdr.domain.web.entity.BespokeTrade;
import com.tzdr.domain.web.entity.FundConfig;
import com.tzdr.domain.web.entity.TradeConfig;
import com.tzdr.domain.web.entity.UserVerified;
import com.tzdr.domain.web.entity.WUser;
import com.tzdr.web.constants.Constants;
import com.tzdr.web.constants.ViewConstants;
import com.tzdr.web.utils.UserSessionBean;
import com.tzdr.web.utils.WebUtil;

/**
 * @Description:不会让拦截器拦住
 * @ClassName: UserTradeEnterController.java
 * @author Lin Feng
 * @date 2015年1月5日
 */
@Controller
@RequestMapping("/")
public class UserTradeEnterController {

	private static Logger log = LoggerFactory
			.getLogger(UserTradeEnterController.class);

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
	private RealDealService realDealService;
	
	@Autowired
	private FundConfigService fundConfigService;
	
	@Autowired
	private DataMapService  dataMapService;
	@Autowired
	private BespokeTradeService bespokeTradeService;
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
		return ViewConstants.UserTradeViewJsp.TRADECONTRACT;
	}

	@RequestMapping(value = "/day")
	public String perByDay(ModelMap modelMap,HttpServletRequest request) throws Exception {
		String lever=request.getParameter("lever")!=null?request.getParameter("lever"):"1";
		String capitalMargin=request.getParameter("capitalMargin")!=null?request.getParameter("capitalMargin"):"0";
		String borrowPeriod=request.getParameter("borrowPeriod")!=null?request.getParameter("borrowPeriod"):"2";
		String enter=request.getParameter("enter")!=null?request.getParameter("enter"):"0";
		String tradeStart=request.getParameter("tradeStart")!=null?request.getParameter("tradeStart"):"1";
		
		modelMap.put("lever", lever);
		modelMap.put("capitalMargin", (long)Double.parseDouble(capitalMargin));
		modelMap.put("borrowPeriod", borrowPeriod);
		modelMap.put("max", CacheManager.getMaxCapitalAmount());
		modelMap.put("enter", enter);
		modelMap.put("tradeStart", tradeStart);
		modelMap.put("maxLeverMoney", Double.valueOf(dataMapService.maxLeverMoney()));
		modelMap.put("isOpen", dataMapService.isOpenMaxLeverMoney());
		List<FundConfig> findConfigList=fundConfigService.findOrderByTimesAsc();
		modelMap.put("findConfigList", findConfigList);
		//判断是不是6600活动
		UserSessionBean userSessionBean = (UserSessionBean)request.getSession().getAttribute(Constants.TZDR_USER_SESSION);
		if(userSessionBean!=null){
			WUser wuser = wUserService.getUser(userSessionBean.getId());
			if(WUser.UserType.WEB_REGIST.equals(wuser.getUserType())&&WUser.ActivityType.ACTIVITY_6600==wuser.getActivityType()){
				modelMap.put("activity_6600", true); 
				//List<DataMap>  enddata=CacheManager.getDataMapListByTypeKey(DataDicKeyConstants.ACTI_END);
				//String endtime=enddata.get(0).getValueKey();
				//Calendar  calendar  = Calendar.getInstance();
				Date lastDate = TypeConvert.strToZeroDate(TypeConvert.dbDateYmd(), 8, -1);
				long max=(lastDate.getTime() - new Date().getTime()) / 86400000L;
				modelMap.put("maxActivityDay", max); 
				//改8800活动页面
				return ViewConstants.UserTradeViewJsp.TRADE_ACTIVITY;
			}
			modelMap.put("loginStatus", 1);
		}else{
			modelMap.put("loginStatus", 0);
		}
		//限制当天当个用户配资数
		String limitTradeNum = dataMapService.isLimitTodayTradeNum();
		modelMap.put("limitTradeNum", limitTradeNum);
		return ViewConstants.UserTradeViewJsp.PER_BY_DAY;
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
	
	
	@RequestMapping(value = "/isTradingMore.json" , method = RequestMethod.POST)
	@ResponseBody
	public JsonResult tradeMoreDay(HttpServletRequest request,
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
		
		String endDay = getEndDay(borrowPeriod, tradeStart);
	
		Map<Object, Object> map = Maps.newHashMap();

		map.put("endDay", endDay);
		jsonResult.setData(map);
		jsonResult.setSuccess(true);
		return jsonResult;
	}
	
	
	@RequestMapping(value = "/getMoreBorrowPeriod.json" , method = RequestMethod.POST)
	@ResponseBody
	public JsonResult getMoreBorrowPeriod( @RequestParam String endDayString, @RequestParam int tradeStart,
			HttpServletRequest request) {
		JsonResult jsonResult = new JsonResult(false);
		
		String end=Dates.format(Dates.parse(endDayString, Dates.CHINESE_DATE_FORMAT_LINE), Dates.CHINESE_DATE_FORMAT_LONG);
		
		int borrowPeriod = (int)getMoreBorrowPeriodDay(end, tradeStart);
	
		Map<Object, Object> map = Maps.newHashMap();

		map.put("borrowPeriod", borrowPeriod);
		jsonResult.setData(map);
		jsonResult.setSuccess(true);
		return jsonResult;
	}
	
	
	private long getMoreBorrowPeriodDay(String endDayString, int tradeStart) {
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

		long borrowPeriod = tradeDayService.getNaturalDays(tradeDay,
				endDayString);

		return borrowPeriod;
	}

	@RequestMapping(value = "/data.json" , method = RequestMethod.POST)
	@ResponseBody
	public JsonResult data(@RequestParam int borrowPeriod,
			@RequestParam int lever, @RequestParam double capitalMargin,@RequestParam int tradeStart,
			HttpServletRequest request) {
		JsonResult jsonResult = new JsonResult(false);
		String interest=request.getParameter("interest");
		String expenese=request.getParameter("expenese");
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
		if(StringUtil.isNotBlank(interest)){
			interestFee=BigDecimalUtils.mul(interestFee,Double.valueOf(interest));
			interestFee=BigDecimalUtils.divRound(interestFee, 100);
		}
		if(StringUtil.isNotBlank(expenese)){
			manageFee=BigDecimalUtils.mul(manageFee,Double.valueOf(expenese));
			manageFee=BigDecimalUtils.divRound(manageFee, 100);
		}
		
		map.put("endDay", endDay);
		map.put("interestFee", interestFee);
		map.put("manageFee", manageFee);
		jsonResult.setData(map);
		jsonResult.setSuccess(true);
		return jsonResult;
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
		String expenese=request.getParameter("expenese");
		String interest=request.getParameter("interest");
		
		JsonResult jsonResult = new JsonResult(false);
		UserSessionBean userSessionBean = (UserSessionBean) request.getSession().getAttribute(
				Constants.TZDR_USER_SESSION);

		//配资金额
		double capitalAmount = BigDecimalUtils.mul(capitalMargin, lever);

		TradeConfig config = tradeConfigService.findTradeConfig(borrowPeriod,
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
		if(StringUtil.isNotBlank(interest)){
			interestFee=BigDecimalUtils.mul(interestFee,Double.valueOf(interest));
			interestFee=BigDecimalUtils.divRound(interestFee, 100);
		}
		if(StringUtil.isNotBlank(expenese)){
			manageFee=BigDecimalUtils.mul(manageFee,Double.valueOf(expenese));
			manageFee=BigDecimalUtils.divRound(manageFee, 100);
		}
		
		//总利息  = 利息  x 借款期限
		double totalInterestFee = BigDecimalUtils.mul(interestFee,borrowPeriod);
		//需要支付的总金额 = 配资保证金  + 总利息
		double needPay = BigDecimalUtils.addRound(capitalMargin, totalInterestFee);
		double needNextdayPay=0;
		WUser wuser = wUserService.getUser(userSessionBean.getId());
		UserVerified userVerified = securityInfoService.findByUserId(userSessionBean.getId());
		int isVerified=0;
		if(!ObjectUtils.equals(userVerified, null)&&!ObjectUtils.equals(userVerified.getIdcard(), null)){
			isVerified=1;
		}
		
		Map<Object, Object> map = Maps.newHashMap();
		
		needPay = BigDecimalUtils.addRound(capitalMargin, totalInterestFee);
		needNextdayPay=BigDecimalUtils.addRound(needPay, manageFee);
	    if (tradeStart==0) {
			needPay=BigDecimalUtils.addRound(needPay,manageFee);
			needNextdayPay=BigDecimalUtils.addRound(needNextdayPay, manageFee);
		}
	
	    //“立即交易类型时”需要支付的金额
		double payEnough= BigDecimalUtils.sub(wuser.getAvlBal(), needPay);
		//“下一个交易类型时”需要支付的金额
		double payNextdayEnough= BigDecimalUtils.sub(wuser.getAvlBal(), needNextdayPay);
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
	
	
	/**
	 * 0倍配置数据校验
	 * @param borrowPeriod
	 * @param lever
	 * @param capitalMargin
	 * @param tradeStart
	 * @param request
	 * @return
	 */
	private JsonResult checkZeroData(int borrowPeriod,int lever, 
			double capitalMargin, int tradeStart,HttpServletRequest request) {
		JsonResult jsonResult = new JsonResult(false);
		UserSessionBean userSessionBean = (UserSessionBean) request.getSession().getAttribute(
				Constants.TZDR_USER_SESSION);
	
		double needPay = capitalMargin;
		double needNextdayPay=0;
		WUser wuser = wUserService.getUser(userSessionBean.getId());
		UserVerified userVerified = securityInfoService.findByUserId(userSessionBean.getId());
		int isVerified=0;
		if(!ObjectUtils.equals(userVerified, null)&&!ObjectUtils.equals(userVerified.getIdcard(), null)){
			isVerified=1;
		}
		Map<Object, Object> map = Maps.newHashMap();
		double payEnough= BigDecimalUtils.sub(wuser.getAvlBal(), needPay);
		double payNextdayEnough= BigDecimalUtils.sub(wuser.getAvlBal(), needNextdayPay);
		int proCount=userTradeService.findUserTradesNotBespoke(userSessionBean.getId());
		
		// 判断是否超过最大操盘方案数  默认最多3个
		int holdMaxNum = dataMapService.getHoldMaxTradeNum();
		map.put("holdMaxNum", holdMaxNum);
				
		//获取用户当天配资数
		List<UserTradeVo> userTradeVoList = userTradeService.queryUserDayTrades(userSessionBean.getId(),Dates.format(Dates.CHINESE_DATE_FORMAT_LINE));
		if(userTradeVoList != null && !userTradeVoList.isEmpty()){
			map.put("userTodayTradeNum", userTradeVoList.size());
		}else{
			map.put("userTodayTradeNum", 0);
		}
		
		//限制当天当个用户配资数
		String limitTradeNum = dataMapService.isLimitTodayTradeNum();
		map.put("limitTradeNum", limitTradeNum);
		
		if(payNextdayEnough<0){
			map.put("needNext", "on");
		}else{
			map.put("needNext", "off");
		}
		if (payEnough < 0) {
			map.put("need", "on");
			map.put("avlBal", wuser.getAvlBal());
			map.put("payEnough", -payEnough);
			map.put("needPay", needPay);
			map.put("isVerified", isVerified);
			map.put("proCount", proCount);
		} else {
			map.put("proCount", proCount);
			map.put("need", "off");
			map.put("needPay", needPay);
			map.put("isVerified", isVerified);
		}
		jsonResult.setData(map);
		jsonResult.setSuccess(true);
		return jsonResult;
		
	}

	/**
	 * 到定制配资显示页面
	 * @param modelMap
	 * @param tradeConfigId 定制配资id显示
	 * @param tradeStart 是否立即生效
	 * @param lever 配资杠杆
	 * @param type 
	 * @param capitalMargin 保证金
	 * @param borrowPeriod 配资天数
	 * @param backtype
	 * @param enter
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/toBespokeTrade")
	public String toBespokeTrade(ModelMap modelMap,String tradeConfigId, int tradeStart, int lever,
			 double capitalMargin, int borrowPeriod,String expenese,String interest,HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		BespokeTrade bespokeTrade=bespokeTradeService.get(tradeConfigId);
		double minbond=bespokeTrade.getMinBond();
		double maxbond=bespokeTrade.getMaxBond();
		double  maxAmount=fundConfigService.findAmountByTimes(lever);
		double totaltrademoney=capitalMargin*lever;
		boolean maxmoneyflag=true;
		if(lever>0){
			if(totaltrademoney>maxAmount){
				maxmoneyflag=false;
			}
		}
		if(!maxmoneyflag|| capitalMargin>maxbond ||capitalMargin<minbond){
			String detail="tradeConfigId"+tradeConfigId+"borrwPeriod:"+borrowPeriod+"lever:"+lever+"capitalMargin:"+capitalMargin;
			log.error("输入保证金有误"+detail);
			throw new UserTradeException("no.BespokeTrade.config",null);
		}

		
		UserSessionBean userSessionBean = (UserSessionBean) request.getSession().getAttribute(
				Constants.TZDR_USER_SESSION);
		if(lever==0){//0倍配资
			return zeroTrade(userSessionBean,modelMap, tradeStart, lever, 0, capitalMargin, borrowPeriod, null, null,tradeConfigId,"bespokeTrade");
		}
		
		
		double capitalAmount = BigDecimalUtils.mul(capitalMargin, lever);
		double totalTrader = BigDecimalUtils.addRound(capitalMargin, capitalAmount);

		TradeConfig config = tradeConfigService.findTradeConfig(borrowPeriod,
				capitalMargin, lever);
		if (ObjectUtil.equals(config, null)) {
			String detail="borrwPeriod:"+borrowPeriod+"lever:"+lever+"capitalMargin:"+capitalMargin;
			log.error("利息配置有误"+detail);
			throw new UserTradeException("no.interest.config",null);
		}
		
	
		// 利息（天）
		double interestFee = 
				BigDecimalUtils.mulRound(config.getDailyInterest(), capitalAmount);
		// 管理费（天）
		double manageFee = 
				BigDecimalUtils.mulRound(config.getDailyManagementFee(), capitalAmount);
		if(StringUtil.isNotBlank(interest)){
			interestFee=BigDecimalUtils.mul(interestFee,Double.valueOf(interest));
			interestFee=BigDecimalUtils.divRound(interestFee, 100);
		}
		if(StringUtil.isNotBlank(expenese)){
			manageFee=BigDecimalUtils.mul(manageFee,Double.valueOf(expenese));
			manageFee=BigDecimalUtils.divRound(manageFee, 100);
		}
		double totalInterestFee = BigDecimalUtils.mulRound(interestFee,borrowPeriod);
		
		double shortLine = 0.0;
		double openLine = 0.0;

		if (1 <= lever && lever <= 5) {
			shortLine = BigDecimalUtils.mul(capitalAmount, 1.1);
			openLine = BigDecimalUtils.mul(capitalAmount, 1.07);
		} else if (lever==6) {
			shortLine = BigDecimalUtils.mul(capitalAmount, 1.0867);
			openLine = BigDecimalUtils.mul(capitalAmount, 1.06);
		} else if (lever==7) {
			shortLine = BigDecimalUtils.mul(capitalAmount, 1.0771);
			openLine = BigDecimalUtils.mul(capitalAmount, 1.0529);
		} else if (lever==8) {
			shortLine = BigDecimalUtils.mul(capitalAmount, 1.07);
			openLine = BigDecimalUtils.mul(capitalAmount, 1.0475);
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

		modelMap.put("tradeConfigId", tradeConfigId);
		modelMap.put("interest", interest);
		modelMap.put("expenese", expenese);
		modelMap.put("lever", lever);
		modelMap.put("tradeStart", tradeStart);
		modelMap.put("capitalMargin", capitalMargin);
		modelMap.put("capitalAmount", capitalAmount);
		modelMap.put("totalTrader", totalTrader);
		modelMap.put("borrowPeriod", borrowPeriod);
		modelMap.put("totalInterestFee", totalInterestFee);
		modelMap.put("manageFee", manageFee);
		modelMap.put("shortLine", BigDecimalUtils.round(shortLine,0));
		modelMap.put("openLine",  BigDecimalUtils.round(openLine,0));
		modelMap.put("operatorsInfo", operatorsInfo);
		modelMap.put("tradetype", "bespokeTrade");
		modelMap.put("maxLeverMoney", Double.valueOf(dataMapService.maxLeverMoney()));
		//modelMap.put("isOpen", dataMapService.isOpenMaxLeverMoney());
		
		if (userSessionBean != null) {
			WUser user = wUserService.getUser(userSessionBean.getId());
			//定制配资不能用抵扣劵
			//List<UserVolumeDetailVo> userVolumeDetailVoList = volumeDetailService.findVolume(userSessionBean.getId(), Constants.VolumeType.NOUSE);
			//modelMap.put("userVolumeDetailVoList", userVolumeDetailVoList);
			
			double needPay=0;
			double needNextdayPay=0;
			UserVerified userVerified = securityInfoService.findByUserId(user.getId());
			int isVerified=0;
			if(!ObjectUtils.equals(userVerified, null)&&!ObjectUtils.equals(userVerified.getIdcard(), null)){
				isVerified=1;
			}
			//计算交易日
			String currentDay = Dates.format(new Date(),
					Dates.CHINESE_DATE_FORMAT_LONG);
			String tradeDay = tradeDayService.getTradeDay();
			boolean isTradeDay = Boolean.FALSE;
			if (tradeDay.equals(currentDay)||lever==0) {//0倍配资也立即生效
				isTradeDay = Boolean.TRUE;
			}
			if(userTradeService.isActity_6600(user)){
				//参加8800活动
				needPay=capitalMargin;
				modelMap.put("isFree", "on");
				isVerified=1;
			}else{
				needPay = BigDecimalUtils.addRound(capitalMargin, totalInterestFee);
				needNextdayPay=BigDecimalUtils.addRound(needPay, manageFee);
				if(tradeStart==0 && isTradeDay){
					needPay=BigDecimalUtils.addRound(needPay,manageFee);
					needNextdayPay=BigDecimalUtils.addRound(needNextdayPay, manageFee);
				}
			}
			
			//获取用户当天配资数
			List<UserTradeVo> userTradeVoList = userTradeService.queryUserDayTrades(userSessionBean.getId(),Dates.format(Dates.CHINESE_DATE_FORMAT_LINE));
			if(userTradeVoList != null && !userTradeVoList.isEmpty()){
				modelMap.put("userTodayTradeNum", userTradeVoList.size());
			}else{
				modelMap.put("userTodayTradeNum", 0);
			}
			
			//限制当天当个用户配资数定制配资不用限制
			//String limitTradeNum = dataMapService.isLimitTodayTradeNum();
			//modelMap.put("limitTradeNum", limitTradeNum);
			
			modelMap.put("user", "on");
			WUser wuser = wUserService.getUser(user.getId());
			
			double payEnough = BigDecimalUtils.sub(wuser.getAvlBal(), needPay);
			double payNextdayEnough = BigDecimalUtils.sub(wuser.getAvlBal(), needNextdayPay);
			int proCount=userTradeService.findUserTradesNotBespoke(userSessionBean.getId());
			
			// 判断是否超过最大操盘方案数  默认最多3个
			//int holdMaxNum = dataMapService.getHoldMaxTradeNum();
			modelMap.put("holdMaxNum", 0);
			
			
			if(payNextdayEnough<0){
				modelMap.put("needNext", "on");
			}else{
				modelMap.put("needNext", "off");
			}
			if (payEnough < 0) {
				modelMap.put("need", "on");
				modelMap.put("avlBal", wuser.getAvlBal());
				modelMap.put("payEnough", -payEnough);
				modelMap.put("needPay", needPay);
				modelMap.put("isVerified", isVerified);
				modelMap.put("proCount",proCount);
			} else {
				modelMap.put("proCount",proCount);
				modelMap.put("need", "off");
				modelMap.put("needPay", needPay);
				modelMap.put("isVerified", isVerified);
			}
		} else {
			modelMap.put("user", "off");
		}
		
		return ViewConstants.UserTradeViewJsp.BESPOKETRADE;
	}
	
	@RequestMapping(value = "/totrade")
	public String toTrade(ModelMap modelMap, int tradeStart, int lever,
			int type, double capitalMargin, int borrowPeriod, String backtype, String enter,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		// 申请操盘时校验 倍数是否合法
		if (!WebUtil.validateTradeLever(lever, capitalMargin)){
			throw new UserTradeException("user.trade.select.lever.not.valid",null);
		}
		
		UserSessionBean userSessionBean = (UserSessionBean) request.getSession().getAttribute(
				Constants.TZDR_USER_SESSION);
		if(lever==0){//0倍配资
			return zeroTrade(userSessionBean,modelMap, tradeStart, lever, type, capitalMargin, borrowPeriod, backtype, enter,null,null);
		}
		double capitalAmount = BigDecimalUtils.mul(capitalMargin, lever);
		double totalTrader = BigDecimalUtils.addRound(capitalMargin, capitalAmount);

		TradeConfig config = tradeConfigService.findTradeConfig(borrowPeriod,
				capitalMargin, lever);
		if (ObjectUtil.equals(config, null)) {
			String detail="borrwPeriod:"+borrowPeriod+"lever:"+lever+"capitalMargin:"+capitalMargin;
			log.error("利息配置有误"+detail);
			throw new UserTradeException("no.interest.config",null);
		}
		
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
		
		// 利息（天）
		double interestFee = 
				BigDecimalUtils.mulRound(config.getDailyInterest(), capitalAmount);
		// 管理费（天）
		double manageFee = 
				BigDecimalUtils.mulRound(config.getDailyManagementFee(), capitalAmount);

		double totalInterestFee = BigDecimalUtils.mulRound(interestFee,borrowPeriod);
		
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

		modelMap.put("lever", lever);
		modelMap.put("type", type);
		modelMap.put("tradeStart", tradeStart);
		modelMap.put("capitalMargin", capitalMargin);
		modelMap.put("capitalAmount", capitalAmount);
		modelMap.put("totalTrader", totalTrader);
		modelMap.put("borrowPeriod", borrowPeriod);
		modelMap.put("totalInterestFee", totalInterestFee);
		modelMap.put("manageFee", manageFee);
		modelMap.put("interestFee", interestFee);
		modelMap.put("shortLine", BigDecimalUtils.round(shortLine,0));
		modelMap.put("openLine",  BigDecimalUtils.round(openLine,0));
		modelMap.put("operatorsInfo", operatorsInfo);
		modelMap.put("backtype", backtype);
		modelMap.put("enter", enter);
		modelMap.put("maxLeverMoney", Double.valueOf(dataMapService.maxLeverMoney()));
		modelMap.put("isOpen", dataMapService.isOpenMaxLeverMoney());
		modelMap.put("tradeTime", Dates.format(Dates.parse(tradeDay),Dates.CHINESE_DATE_FORMAT_LINE));
		if (tradeStart==0){
			modelMap.put("payManageFee", manageFee);
		}
		if (userSessionBean != null) {
			WUser user = wUserService.getUser(userSessionBean.getId());
			List<UserVolumeDetailVo> userVolumeDetailVoList = volumeDetailService.findVolume(userSessionBean.getId(), Constants.VolumeType.NOUSE);
			modelMap.put("userVolumeDetailVoList", userVolumeDetailVoList);
			
			double needPay=0;
			double needNextdayPay=0;
			UserVerified userVerified = securityInfoService.findByUserId(user.getId());
			int isVerified=0;
			if(!ObjectUtils.equals(userVerified, null)&&!ObjectUtils.equals(userVerified.getIdcard(), null)){
				isVerified=1;
			}
			
			if(userTradeService.isActity_6600(user)){
				//参加6600活动
				needPay=capitalMargin;
				modelMap.put("isFree", "on");
				isVerified=1;
			}
			else {
				needPay = BigDecimalUtils.addRound(capitalMargin, totalInterestFee);
				needNextdayPay=BigDecimalUtils.addRound(needPay, manageFee);
				if(tradeStart==0){
					needPay=BigDecimalUtils.addRound(needPay,manageFee);
					needNextdayPay=BigDecimalUtils.addRound(needNextdayPay, manageFee);
				}
			}
			
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
			double payNextdayEnough = BigDecimalUtils.sub(wuser.getAvlBal(), needNextdayPay);
			int proCount=userTradeService.findUserTradesNotBespoke(userSessionBean.getId());
			
			// 判断是否超过最大操盘方案数  默认最多3个
			int holdMaxNum = dataMapService.getHoldMaxTradeNum();
			modelMap.put("holdMaxNum", holdMaxNum);
						
						
			if (payNextdayEnough<0) {
				modelMap.put("needNext", "on");
			}
			else {
				modelMap.put("needNext", "off");
			}
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
		
		return ViewConstants.UserTradeViewJsp.TRADE;
	}
	
	/**
	 * 0倍配资
	 * @param tradeStart 开始交易0：立即交易，1下一个交易日
	 * @param lever 配资杠杠
	 * @param type
	 * @param capitalMargin 保证金
	 * @param borrowPeriod 使用天数
	 * @param backtype
	 * @param enter
	 * @return
	 */
	private String zeroTrade(UserSessionBean userSessionBean,ModelMap modelMap,int tradeStart, int lever,
			int type, double capitalMargin, int borrowPeriod, String backtype, String enter,String configId,String tradetype){
		String operatorsInfo = operatorsInfo(capitalMargin);//操盘须知
		modelMap.put("lever", lever);
		modelMap.put("type", type);
		modelMap.put("tradeStart", tradeStart);
		modelMap.put("capitalMargin", capitalMargin);
		modelMap.put("capitalAmount", 0);
		modelMap.put("totalTrader", capitalMargin);
		modelMap.put("borrowPeriod", borrowPeriod);
		modelMap.put("totalInterestFee", 0);
		modelMap.put("manageFee", 0);
		modelMap.put("shortLine", BigDecimalUtils.round(0,0));
		modelMap.put("openLine",  BigDecimalUtils.round(0,0));
		modelMap.put("operatorsInfo", operatorsInfo);
		modelMap.put("backtype", backtype);
		modelMap.put("enter", enter);
		modelMap.put("tradetype", tradetype);
		modelMap.put("tradeConfigId", configId);
		modelMap.put("maxLeverMoney", Double.valueOf(dataMapService.maxLeverMoney()));
		//modelMap.put("isOpen", dataMapService.isOpenMaxLeverMoney());
	
		if (userSessionBean != null) {
			WUser wuser = wUserService.getUser(userSessionBean.getId());
			UserVerified userVerified = securityInfoService.findByUserId(wuser.getId());
			int isVerified=0;
			if(!ObjectUtils.equals(userVerified, null)&&!ObjectUtils.equals(userVerified.getIdcard(), null)){
				isVerified=1;
			}
			double payEnough = BigDecimalUtils.sub(wuser.getAvlBal(), capitalMargin);
			double payNextdayEnough = BigDecimalUtils.sub(wuser.getAvlBal(), capitalMargin);
			int proCount=userTradeService.findUserTradesNotBespoke(userSessionBean.getId());
			
			// 判断是否超过最大操盘方案数  默认最多3个
			int holdMaxNum = dataMapService.getHoldMaxTradeNum();
			modelMap.put("holdMaxNum", holdMaxNum);
						
			if(payNextdayEnough<0){
				modelMap.put("needNext", "on");
			}else{
				modelMap.put("needNext", "off");
			}
			
			if (payEnough < 0) {
				modelMap.put("need", "on");
				modelMap.put("avlBal", wuser.getAvlBal());
				modelMap.put("payEnough", -payEnough);
				modelMap.put("needPay", capitalMargin);
				modelMap.put("isVerified", isVerified);
				modelMap.put("proCount",proCount);
			} else {
				modelMap.put("proCount",proCount);
				modelMap.put("need", "off");
				modelMap.put("needPay", capitalMargin);
				modelMap.put("isVerified", isVerified);
			}
		}else {
			modelMap.put("user", "off");
		}
		
		return ViewConstants.UserTradeViewJsp.BESPOKETRADE;
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
	
	@RequestMapping(value = "/share/{groupId:T[A-Z0-9]{6}}")
	public String share(@PathVariable("groupId") String groupId,ModelMap modelMap,HttpServletRequest request,HttpServletResponse response){
		double rank=userTradeService.findActivityByGroupId(groupId);
		int rankint=(int)rank*100;
		modelMap.put("rankint", rankint);
		if(rankint>=50){
			return ViewConstants.UserTradeViewJsp.SHARE_HIGH;
		}else{
			return ViewConstants.UserTradeViewJsp.SHARE_LOW;	
		}
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

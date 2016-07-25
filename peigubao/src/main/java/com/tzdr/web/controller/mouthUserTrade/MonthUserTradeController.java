package com.tzdr.web.controller.mouthUserTrade;

import java.util.Date;
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
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tzdr.business.exception.UserTradeException;
import com.tzdr.business.service.account.ParentAccountService;
import com.tzdr.business.service.bespokeTrade.BespokeTradeService;
import com.tzdr.business.service.combine.CombineInfoService;
import com.tzdr.business.service.contractsave.ContractsaveService;
import com.tzdr.business.service.datamap.DataMapService;
import com.tzdr.business.service.fundconfig.FundConfigService;
import com.tzdr.business.service.future.FSimpleCouponService;
import com.tzdr.business.service.monthUserTradeParams.MonthUserTradeParamsService;
import com.tzdr.business.service.pay.UserFundService;
import com.tzdr.business.service.realdeal.RealDealService;
import com.tzdr.business.service.securityInfo.SecurityInfoService;
import com.tzdr.business.service.tradecalendar.TradeDayService;
import com.tzdr.business.service.userTrade.AppendLevelMoneyFailService;
import com.tzdr.business.service.userTrade.HandTradeService;
import com.tzdr.business.service.userTrade.NoticeRecordService;
import com.tzdr.business.service.userTrade.TradeConfigService;
import com.tzdr.business.service.userTrade.UserTradeService;
import com.tzdr.business.service.volume.VolumeDetailService;
import com.tzdr.business.service.wuser.WUserService;
import com.tzdr.business.thread.ContractThread;
import com.tzdr.common.api.ihuyi.PgbSMSSender;
import com.tzdr.common.exception.EmailExceptionHandler;
import com.tzdr.common.utils.BigDecimalUtils;
import com.tzdr.common.utils.Dates;
import com.tzdr.common.utils.MessageUtils;
import com.tzdr.common.utils.StringCodeUtils;
import com.tzdr.common.utils.TypeConvert;
import com.tzdr.common.web.support.JsonResult;
import com.tzdr.domain.cache.CacheManager;
import com.tzdr.domain.cache.DataDicKeyConstants;
import com.tzdr.domain.constants.Constant;
import com.tzdr.domain.entity.DataMap;
import com.tzdr.domain.vo.UserTradeCmsVo;
import com.tzdr.domain.vo.UserTradeVo;
import com.tzdr.domain.web.entity.MonthUserTradeParams;
import com.tzdr.domain.web.entity.ParentAccount;
import com.tzdr.domain.web.entity.TradeConfig;
import com.tzdr.domain.web.entity.UserFund;
import com.tzdr.domain.web.entity.UserTrade;
import com.tzdr.domain.web.entity.UserVerified;
import com.tzdr.domain.web.entity.WUser;
import com.tzdr.web.constants.Constants;
import com.tzdr.web.constants.ViewConstants;
import com.tzdr.web.utils.UserSessionBean;
import com.tzdr.web.utils.WebUtil;

/**
 * @Description:
 * @ClassName: UserTradeController.java
 * @author LinFeng
 * @date 2015年1月5日
 */
@Controller
@RequestMapping("/user/monthTrade")
public class MonthUserTradeController {

	private static Logger log = LoggerFactory
			.getLogger(MonthUserTradeController.class);

	@Autowired
	private UserTradeService userTradeService;
	@Autowired
	private ContractsaveService contractsaveService;

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
	private VolumeDetailService volumeDetailService;
	@Autowired
	private SecurityInfoService securityInfoService;

	@Autowired
	private FSimpleCouponService fSimpleCouponService;

	@Autowired
	private RealDealService realDealService;
	
	@Autowired
	private CombineInfoService combineInfoService;
	
	@Autowired
	private DataMapService  dataMapService;
	
	@Autowired
	private AppendLevelMoneyFailService appendLevelMoneyFailService;
	
	@Autowired
	private NoticeRecordService noticeRecordService;
	@Autowired
	private BespokeTradeService bespokeTradeService;
	@Autowired
	private FundConfigService fundConfigService;

	@Autowired
	private HandTradeService handTradeService;
	@Autowired
	private MonthUserTradeParamsService monthUserTradeParamsService;

	private static Object lock = new Object();
	
	/**
	 * @param modelMap
	 * @param lever		配资倍数
	 * @param tradeStart		是否下一交易日
	 * @param type		配资类型：新配资、追加配资
	 * @param capitalMargin		保证金
	 * @param borrowPeriod		配资期限
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/success")
	public String tradeOk(ModelMap modelMap, int lever, int tradeStart,
			Short type, double capitalMargin, int borrowPeriod,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		// 申请操盘时校验 倍数是否合法
		if (!WebUtil.validateTradeLever(lever, capitalMargin)){
			throw new UserTradeException("user.trade.select.lever.not.valid",null);
		}
				
		UserSessionBean userSessionBean = (UserSessionBean) request.getSession().getAttribute(
				Constants.TZDR_USER_SESSION);
		WUser user = wUserService.getUser(userSessionBean.getId());	
		//获取用户当天配资数
		List<UserTradeVo> userTradeVoList = userTradeService.queryUserDayTrades(userSessionBean.getId(),Dates.format(Dates.CHINESE_DATE_FORMAT_LINE));
		if (ObjectUtil.equals(userSessionBean, null)) {
			throw new UserTradeException("no.user", null);
		}
		try {
				List<MonthUserTradeParams> list = monthUserTradeParamsService.getAll();
				if(CollectionUtils.isEmpty(list)){
					throw new UserTradeException("user.trade.select.lever.not.valid",null);
				}
				MonthUserTradeParams params = list.get(0);
				//查询实名认证
				UserVerified isUserVerified = securityInfoService.findByUserId(userSessionBean.getId());
				if (ObjectUtils.equals(isUserVerified, null) || ObjectUtils.equals(isUserVerified.getIdcard(), null)) {
					throw new UserTradeException("no.user.verified", null);
				}
				String[] levers = params.getLeverConfig().split(";");
				if (lever>Integer.parseInt(levers[levers.length-1]) || lever<Integer.parseInt(levers[0])) {//0倍配资修改
					throw new UserTradeException("no.lever", null);
				}
				//如果是非定制配资验证金额
				if (Integer.valueOf(dataMapService.isOpenMaxLeverMoney()) == 1 
						&& BigDecimalUtils.mulRound(lever, capitalMargin) > Double.valueOf(dataMapService.maxLeverMoney())) {
					throw new UserTradeException("over.max.levermoney", null);
				}
				
				String limitTradeNum = dataMapService.isLimitTodayTradeNum();
				if(Integer.valueOf(dataMapService.isOpenMaxLeverMoney()) == 1 && userTradeVoList != null && !userTradeVoList.isEmpty() && userTradeVoList.size() >= Integer.valueOf(limitTradeNum)){
					throw new UserTradeException("over.max.limittradenum", null);
				}
				
				if (capitalMargin>CacheManager.getMaxCapitalAmount()||capitalMargin<300) {
					throw new UserTradeException("no.capital.margin", null);
				}
				
				
				String[] monthString=params.getRecommendHoldMonths().split(";");
				if (borrowPeriod<Integer.parseInt(monthString[0]) || borrowPeriod>Integer.parseInt(monthString[monthString.length-1])) {
					throw new UserTradeException("no.borrow.period", null);
				}

				double capitalAmount = BigDecimalUtils.mul(capitalMargin, lever);
				double totalLeverMoney = BigDecimalUtils.add(capitalMargin,
						capitalAmount);
				//检验倍数，最大赔资额问题
				double  maxAmount=params.getMaxTotalMoney();			
				if(capitalAmount>maxAmount){
					throw new UserTradeException("no.more.money", null);
				}
				// 利息（天）
				double interestFee=0;
				double manageFee=0;
				if (capitalAmount>CacheManager.getMaxCapitalAmount()) {
					throw new UserTradeException("no.total.lever.money", null);
				}
				//查询管理费的费率和利息的费率
				Integer borrowPeriodDays =borrowPeriod*Constant.MONTH_TRADE_MONTH_DAYS;
				TradeConfig config = tradeConfigService.findTradeConfig(borrowPeriodDays,
						capitalMargin, lever);
				if (ObjectUtil.equals(config, null)) {
					throw new UserTradeException("no.interest.config", null);
				}
				interestFee= BigDecimalUtils.mulRound(config.getDailyInterest(), capitalAmount);
				interestFee= BigDecimalUtils.mulRound(interestFee, params.getInterestCoefficient());
				
				// 管理费（天）
				manageFee= BigDecimalUtils.mulRound(config.getDailyManagementFee(), capitalAmount);
				manageFee= BigDecimalUtils.mulRound(manageFee, params.getManageFeeCoefficient());
		
			//亏损补仓钱，亏损平仓钱的计算
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
			UserTrade userTrade = new UserTrade();
			userTrade.setActivityType(UserTrade.ActivityType.MONTH_TRADE);
			userTrade.setWuser(user);
			userTrade.setType((short) type);
			userTrade.setTradeStart((short) tradeStart);
			userTrade.setMoney(capitalAmount);
			userTrade.setWarning(shortLine);
			userTrade.setOpen(openLine);
			userTrade.setLever(lever);
			userTrade.setLeverMoney(capitalMargin);
			userTrade.setAddtime(Dates.getCurrentLongDate());
			
			String currentDay = Dates.format(new Date(),
					Dates.CHINESE_DATE_FORMAT_LONG);
			//计算交易日
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
			
			Date trade = Dates.parse(tradeDay, Dates.CHINESE_DATE_FORMAT_LONG);
			
			userTrade.setStarttime(trade.getTime() / 1000);
			
			String expirationDate = tradeDayService.getEndDate(tradeDay, borrowPeriodDays);
			Date estimateEnd = Dates.parse(expirationDate,
					Dates.CHINESE_DATE_FORMAT_LONG);
			userTrade.setEstimateEndtime(estimateEnd.getTime() / 1000);

			userTrade.setNaturalDays((long)borrowPeriodDays);
			
			//trade days
			long tradeDays=tradeDayService.getTradeDays(tradeDay, expirationDate);
			userTrade.setStartdays((int)tradeDays);
			// 日(管理费)
			userTrade.setFeeDay(manageFee);
			// 月(利息)
			userTrade.setFeeMonth(interestFee);
			userTrade.setTotalLeverMoney(totalLeverMoney);
			
			
			WUser wuser = wUserService.getUser(user.getId());
			if(userTradeService.isHaveNoTrade(wuser)){
				userTrade.setNewStatus(1);
			}
						
			synchronized(lock) {
				userTrade=userTradeService.buildUserMonthTrade(userTrade, wuser,borrowPeriod);
				
				/**
				* @Description: (处理配资合同动态参数)
				* @Title: createContract
				* @param contractNo    协议编号
				* @param loanTrueName  真实姓名
				* @param cardNo        身份证号
				* @param nickName      投资用户名
				* @param loanAmounts   借款金额
				* @param loanInterest  借款利息 元/日
				* @param margin        保证金金额
				* @param managementFee 账户管理费 元/交易日
				* @param days          借款期限（天数）自然日
				* @param startTime     借款期限（开始时间  格式如：2015-01-21）
				* @param endTime       借款期限（结束时间  格式如：2015-01-25）
				* @param warning       警戒线
				* @param open          平仓线
				* @return List<String>    返回类型
				 */
				//动态参数
				UserVerified userVerified=wuser.getUserVerified();
				String programNo=userTrade.getProgramNo();
				String loanTrueName=userVerified.getTname();
				String cardNo=userVerified.getIdcard();
				String email=userVerified.getValidateemail();
				String nickName=wuser.getMobile();
				long loanAmounts=(long)capitalAmount;
				double loanInterest=interestFee;
				long margin=(long)capitalMargin;
				double managementFee=manageFee;
				int days=borrowPeriodDays;
				String startTime=Dates.format(Dates.parseLong2Date(userTrade.getStarttime()), Dates.CHINESE_DATE_FORMAT_LINE);
				String endTime=Dates.format(Dates.parseLong2Date(userTrade.getEstimateEndtime()), Dates.CHINESE_DATE_FORMAT_LINE);
				long warning=(long)shortLine;
				long open=(long)openLine;
				//加到协议母账户结束日期
				String parentEndDate="";//userTradeTransfer.getParentEndTime();
				String bathPath=	request.getSession().getServletContext().getRealPath("/");
				if(!userTradeService.isActity_6600(user)){
					double totalmoney=userTrade.getTotalLeverMoney();
					String uid=wuser.getId();
					if(userTrade.getStatus()!=0){
						new ContractThread(programNo, loanTrueName, cardNo, nickName, loanAmounts, loanInterest, margin, managementFee, days, startTime, endTime, warning, open, bathPath,totalmoney,uid,email,parentEndDate).start();
						
					}
				}			
			}	
			
			// 新增返回feetype
			modelMap.put("feeType", userTrade.getFeeType());
			// 新增返回feetype
			
			modelMap.put("groupId", userTrade.getGroupId());
			modelMap.put("account", userTrade.getAccount());
			modelMap.put("password", userTrade.getPassword());
			modelMap.put("mobile", StringCodeUtils.buildMobile(user.getMobile()));
			modelMap.put("isMore", false);
			
			if (userTrade.getStatus()==1) {
			modelMap.put("isActive", true);
			}
			else {
			modelMap.put("isActive", false);	
			}
			if (userTrade.getActivityType()==1) {
				modelMap.put("activityType", true);
			}
			else{
				modelMap.put("activityType", false);	
			}
			//修改为已经配置
			user.setUserType("1");
			wUserService.update(user);
		} 
		catch (UserTradeException e) { 
			String dataDetail="userInfo:id:"+user.getId()+"|mobile:"+user.getMobile()+"|异常："+e.getResourceMessage();
			log.error(dataDetail);			
			EmailExceptionHandler.getInstance().HandleExceptionWithData(e, "申请配资失败异常", this.getClass().getName()+":tradeOk", dataDetail);
			return "redirect:/user/monthTrade/tradeFail";
		}
		return "redirect:/user/monthTrade/tradeSuccess";
	}
	
	@RequestMapping(value = "/tradeSuccess")
	public String tradeSuccess(ModelMap modelMap,HttpServletRequest request,@RequestParam String groupId,@RequestParam(required=false) String account,@RequestParam(required=false) String password,@RequestParam(required=false) String mobile,@RequestParam boolean isMore ,@RequestParam boolean isActive,@RequestParam(required=false) boolean activityType,
			@RequestParam String feeType, @RequestParam(required=false) String parentEndDate) throws Exception {
		modelMap.put("feeType", feeType);
		modelMap.put("groupId", groupId);
		modelMap.put("account", account);
		modelMap.put("password", password);
		modelMap.put("mobile", mobile);
		modelMap.put("isMore", isMore);
		modelMap.put("isActive", isActive);		
		modelMap.put("activityType", activityType);
		modelMap.put("parentEndDate", parentEndDate);
		return ViewConstants.UserTradeViewJsp.MONTH_TRADE_OK;
	}
	
	@RequestMapping(value = "/tradeFail")
	public String tradeFail(ModelMap modelMap,HttpServletRequest request) throws Exception {		
		return ViewConstants.UserTradeViewJsp.MONTH_TRADE_FAIL;
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
	
	

	
	@RequestMapping(value = "/detail/{groupId:MM[A-Z0-9]{6}}")
	public String detail(@PathVariable("groupId") String groupId,ModelMap modelMap,HttpServletRequest request) throws Exception {
		UserSessionBean userSessionBean = (UserSessionBean) request.getSession().getAttribute(
				Constants.TZDR_USER_SESSION);
		WUser userTemp=wUserService.getUser(userSessionBean.getId());
		
//		UserTradeCmsVo userTradeCmsVo =userTradeService.findByGroupIdAndUid(groupId, userSessionBean.getId());	
		UserTradeCmsVo userTradeCmsVo =userTradeService.findByGroupIdAndUidLazy(groupId, userSessionBean.getId());	
		if (userTradeCmsVo.getActivityType() == UserTrade.ActivityType.ACTIVITY_6600) {
		    modelMap.put("activityType6600", 2);	
		}
		List<Map<String, Object>> moneys = userTradeService.getSumMoney(userTemp, userTradeCmsVo.getGroupId());
		if(!CollectionUtils.isEmpty(moneys)){
			String money = moneys.get(0).get("money").toString();
			modelMap.addAttribute("money",money);
		}
		MonthUserTradeParams params =monthUserTradeParamsService.getParams();
		String[] months = params.getRecommendHoldMonths().split(";");
		modelMap.addAttribute("months",months);
		//double totalLending=userTradeCmsVo.getTotalLending();
		List<UserFund> ticketfunds=userFundService.getVolumeDetail(groupId,userSessionBean.getId(),Constants.UserFundStatus.TICKET);
		if(ticketfunds!=null && ticketfunds.size()>0){
			modelMap.put("isshowticket", true);
		}
		int isExtractableProfit=0;
		if(!tradeDayService.isTradeTime()/*&&userTradeCmsVo.getExtractableProfit()>0*/){
			isExtractableProfit=1;
		}
		
		/*String startDate=Dates.format(new Date(), Dates.CHINESE_DATE_FORMAT_LONG);
		String endDate=Dates.parseBigInteger2Date(userTradeCmsVo.getEstimateEndtime(), Dates.CHINESE_DATE_FORMAT_LONG);
		
		long days=tradeDayService.getNaturalDays(startDate, endDate);
		
		int isAddProgram=0;
		if(days>=7 && userTradeCmsVo.getActivityType()==UserTrade.ActivityType.NO){
			isAddProgram=1;
		}
		// 配资金额大于 最大配资金额600万
		if(totalLending>=CacheManager.getMaxCapitalAmount()){
			isAddProgram=0;
		}
		// 总配资金额 大于等于 倍数最大配资金额
		if(totalLending>=fundConfigService.findAmountByTimes(userTradeCmsVo.getLever())){
			isAddProgram=0;
		}*/
		
		//方案最长可延期至
		String parentAccountNo=userTradeCmsVo.getParentAccountNo();
		String parentEndDate= null;
		if(!StringUtil.isBlank(parentAccountNo)) {
			ParentAccount parentAccount=parentAccountService.findByAccountNo(parentAccountNo);
			if(!ObjectUtil.equals(null, parentAccount) && !ObjectUtil.equals(null, parentAccount.getAllocationDate())) {
				parentEndDate=TypeConvert.long1000ToDateStr((parentAccount.getAllocationDate()-5*Dates.DAY_LONG));
			}
		}
		
		// 获取已使用自然日
		String todayStr = Dates.format(Dates.CHINESE_DATE_FORMAT_LONG);
        String startDay = Dates.parseBigInteger2Date(
                userTradeCmsVo.getStarttime(), 
                Dates.CHINESE_DATE_FORMAT_LONG);
        long useDays = tradeDayService.getNaturalDays(startDay,todayStr);
        modelMap.put("useDays", useDays);
		        
		modelMap.put("parentEndDate",parentEndDate);		
		modelMap.put("balance", userTemp.getAvlBal());
		modelMap.put("trade", userTradeCmsVo);
		modelMap.put("tradeMonth",userTradeCmsVo.getNaturalDays().longValue()/30);
		modelMap.put("activityEnd", false);
		if(userTradeCmsVo.getActivityType()==1&&userTradeCmsVo.getStatus()==2){
			List<DataMap>  enddata=CacheManager.getDataMapListByTypeKey(DataDicKeyConstants.ACTI_END);
			String endtime=enddata.get(0).getValueKey();
			Date today=new Date();
			Date enddate=Dates.parse(endtime, "yyyy-MM-dd HH:mm:ss");
			long max=(today.getTime() - enddate.getTime());
			if(max>=0){
				double rank=userTradeService.findActivityByGroupId(groupId);
				int rankint=(int)rank*100;
				modelMap.put("rankint", rankint);
				modelMap.put("activityEnd", true);
			}
		}
		modelMap.put("isExtractableProfit", isExtractableProfit);
		modelMap.put("isAddProgram",0);
		String operatorsInfo = operatorsInfo(userTradeCmsVo.getTotalLending());
		modelMap.put("operatorsInfo", operatorsInfo);
		/*//最低保证金为总操盘资金1%
		modelMap.put("minAddMoney", BigDecimalUtils.mulRound(userTradeCmsVo.getTotalOperateMoney(),0.01));*/
		modelMap.put("minAddMoney", Constants.UserTrade.TRADE_A_MIN_MARGIN);
		modelMap.put("maxAddMoney", Constants.UserTrade.TRADE_A_MAX_MARGIN);
		modelMap.put("totalManageFee", BigDecimalUtils.mulRound(userTradeCmsVo.getFeeDay(),userTradeCmsVo.getTradingDays()));
		modelMap.put("insuranceNo", userTradeCmsVo.getInsuranceNo());
		return ViewConstants.UserTradeViewJsp.MONTH_DETAIL;
	}
	
	
	
	
	
	@RequestMapping(value = "/detail.json" , method = RequestMethod.POST)
	@ResponseBody
	public JsonResult type(@RequestParam int type,@RequestParam String groupId,ModelMap modelMap,HttpServletRequest request) throws Exception {
		UserSessionBean userSessionBean = (UserSessionBean) request.getSession().getAttribute(
				Constants.TZDR_USER_SESSION);
		WUser user = wUserService.getUser(userSessionBean.getId());
		//String basePath=request.getSession().getServletContext().getRealPath("/");
		JsonResult jsonResult = new JsonResult(false);
		if (type==3){
			List<UserTrade> userTrade = userTradeService.findByGroupIdAndWuser(groupId, user);
			if(CollectionUtils.isEmpty(userTrade)){
				jsonResult.setMessage("找不到该参数");
				jsonResult.setSuccess(false);
				return jsonResult;
			}
			UserTrade trade=userTrade.get(0);
			List<Map<String, Object>> value = userTradeService.getdetailList(user, trade);
			jsonResult.setObj(value);
		}
		jsonResult.setSuccess(true);
		
		return jsonResult;
	}
	@ResponseBody
	@RequestMapping(value="/countFee",method = RequestMethod.POST)
	public JsonResult countFee(int month,String groupId,HttpServletRequest request){
		JsonResult jsonResult = new JsonResult(false);
		UserSessionBean userSessionBean = (UserSessionBean) request.getSession().getAttribute(
				Constants.TZDR_USER_SESSION);
		WUser user = wUserService.getUser(userSessionBean.getId());
		
		List<UserTrade> userTrade = userTradeService.findByGroupIdAndWuser(groupId, user);
		if(CollectionUtils.isEmpty(userTrade)){
			jsonResult.setMessage("找不到该参数");
			return jsonResult;
		}
		
		if (month <1 || month > 12){
			return new JsonResult(false,"延期月数有误！");
		}		
		//变更延期对应参数值
		// 新增的自然日天数
		UserTrade trade=userTrade.get(0);
		Integer  addNaturalDays = month*Constant.MONTH_TRADE_MONTH_DAYS;
		// 重新设置预计结束时间
		String oldEstimateEndtime =  Dates.format(Dates.addDay(Dates.parseLong2Date(trade.getEstimateEndtime()),1)
				,Dates.CHINESE_DATE_FORMAT_LONG);
		String expirationDate = tradeDayService.getEndDate(oldEstimateEndtime,addNaturalDays);
		// 新增的交易日天数
		Integer addTtradeDays=tradeDayService.getTradeDays(oldEstimateEndtime, expirationDate);
		// 计算本次延期需要支付的管理费和利息费用
		//利息计算
        double interestFee = BigDecimalUtils.round(trade.getFeeMonth()*addNaturalDays, 2);
        //管理费计算
        double  manageFee = BigDecimalUtils.round(trade.getFeeDay()*addTtradeDays, 2);
		
        double totalFee = BigDecimalUtils.addRound(interestFee,manageFee);
        jsonResult.setObj(totalFee);
        jsonResult.setSuccess(true);
        return jsonResult;
	}

	@ResponseBody
	@RequestMapping(value="/addMonth",method=RequestMethod.POST)
	public JsonResult addMonth(@RequestParam String groupId,@RequestParam int addMonth,ModelMap modelMap,HttpServletRequest request){
		UserSessionBean userSessionBean = (UserSessionBean) request.getSession().getAttribute(
				Constants.TZDR_USER_SESSION);
 		JsonResult jsonResult = new JsonResult(false);
		WUser userTemp=wUserService.getUser(userSessionBean.getId());
		List<UserTrade> userTrade = userTradeService.findByGroupIdAndWuser(groupId, userTemp);
		if(CollectionUtils.isEmpty(userTrade)){
			jsonResult.setMessage("参数异常请重试");
			jsonResult.setSuccess(false);
			return jsonResult;
		}
		UserTrade trade=userTrade.get(0);
		JsonResult result=userTradeService.delayMonthTrade(trade,addMonth,false);
		if(result.isSuccess()){
			PgbSMSSender.getInstance().send(dataMapService.getPgbSmsContentOthers(),userTemp.getMobile(), MessageUtils.message("monthTrade.addMonth.success",groupId,addMonth,result.getObj()));
		}
		return result;
	}
}

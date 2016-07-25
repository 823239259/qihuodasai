package com.tzdr.web.controller.userTrade;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tzdr.business.service.future.FSimpleCouponService;
import com.tzdr.domain.web.entity.future.FSimpleCoupon;

import jodd.util.ObjectUtil;
import jodd.util.StringUtil;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.math.NumberUtils;
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

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.tzdr.business.exception.UserTradeException;
import com.tzdr.business.service.account.ParentAccountService;
import com.tzdr.business.service.bespokeTrade.BespokeTradeService;
import com.tzdr.business.service.combine.CombineInfoService;
import com.tzdr.business.service.contractsave.ContractsaveService;
import com.tzdr.business.service.datamap.DataMapService;
import com.tzdr.business.service.fundconfig.FundConfigService;
import com.tzdr.business.service.pay.UserFundService;
import com.tzdr.business.service.realdeal.RealDealService;
import com.tzdr.business.service.securityInfo.SecurityInfoService;
import com.tzdr.business.service.thread.SMSPgbSendForContentThread;
import com.tzdr.business.service.thread.SMSPgbSenderThread;
import com.tzdr.business.service.tradecalendar.TradeDayService;
import com.tzdr.business.service.userTrade.AppendLevelMoneyFailService;
import com.tzdr.business.service.userTrade.HandTradeService;
import com.tzdr.business.service.userTrade.NoticeRecordService;
import com.tzdr.business.service.userTrade.TradeConfigService;
import com.tzdr.business.service.userTrade.UserTradeService;
import com.tzdr.business.service.volume.VolumeDetailService;
import com.tzdr.business.service.wuser.WUserService;
import com.tzdr.business.thread.ContractThread;
import com.tzdr.common.domain.PageInfo;
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
import com.tzdr.domain.vo.ContractTradeSafeVo;
import com.tzdr.domain.vo.PositionDetailsVo;
import com.tzdr.domain.vo.UserTradeCmsVo;
import com.tzdr.domain.vo.UserTradeTransfer;
import com.tzdr.domain.vo.UserTradeVo;
import com.tzdr.domain.vo.UserVolumeDetailVo;
import com.tzdr.domain.web.entity.AppendLevelMoneyFail;
import com.tzdr.domain.web.entity.HandTrade;
import com.tzdr.domain.web.entity.NoticeRecord;
import com.tzdr.domain.web.entity.ParentAccount;
import com.tzdr.domain.web.entity.RealdealEntity;
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
@RequestMapping("/trade")
public class UserTradeController {

	private static Logger log = LoggerFactory
			.getLogger(UserTradeController.class);

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

	
	@RequestMapping(value = "/list")
	public String list(ModelMap modelMap,HttpServletRequest request) throws Exception {
		UserSessionBean userSessionBean = (UserSessionBean)request.getSession().getAttribute(Constants.TZDR_USER_SESSION);  //获取用户账户信息
		WUser wUser = wUserService.getUser(userSessionBean.getId());
		Double totalAccrual = userTradeService.getSumAccrualByWuserAndStatus(wUser.getId(), (short)2);  //累计盈亏
		double totalAccrualRate=0;
		if(wUser.getTotalDeposit()+wUser.getTotalInterestMo()+wUser.getTotalManagerMo()!=0){
		totalAccrualRate=BigDecimalUtils.mul(BigDecimalUtils.div(totalAccrual, wUser.getTotalDeposit()+wUser.getTotalInterestMo()+wUser.getTotalManagerMo(), 4),100);
		}
		
		modelMap.put("totalLending",wUser.getTotalLending());//累计配资金额 
		modelMap.put("totalDeposit",wUser.getTotalDeposit());//累计支出保证金
		modelMap.put("totalInterestMo", wUser.getTotalInterestMo());//累计利息
		modelMap.put("totalManagerMo", wUser.getTotalManagerMo());//累计管理费
		modelMap.put("totalAccrual",totalAccrual);//累计盈亏
		modelMap.put("totalAccrualRate",totalAccrualRate);//盈亏率

		return ViewConstants.UserTradeViewJsp.LIST;
	}

	private static Object lock = new Object();
	
	/**
	 * @param modelMap
	 * @param lever		配资倍数
	 * @param tradeStart		是否下一交易日
	 * @param type		配资类型：新配资、追加配资
	 * @param capitalMargin		保证金
	 * @param borrowPeriod		配资期限
	 * @param volumeDetailId	抵扣券ID（不能存在为null）
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/success")
	public String tradeOk(ModelMap modelMap, int lever, int tradeStart,
			Short type, double capitalMargin, int borrowPeriod,String voucherId,
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
				//查询实名认证
				UserVerified isUserVerified = securityInfoService.findByUserId(userSessionBean.getId());
				if (ObjectUtils.equals(isUserVerified, null) || ObjectUtils.equals(isUserVerified.getIdcard(), null)) {
					throw new UserTradeException("no.user.verified", null);
				}
				if (lever>5 || lever<0) {//0倍配资修改
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

				if (borrowPeriod>180||capitalMargin<2) {
					throw new UserTradeException("no.borrow.period", null);
				}

				double capitalAmount = BigDecimalUtils.mul(capitalMargin, lever);
				double totalLeverMoney = BigDecimalUtils.add(capitalMargin,
						capitalAmount);
				//检验倍数，最大赔资额问题
				double  maxAmount=fundConfigService.findAmountByTimes(lever);			
				if(capitalAmount>maxAmount){
					throw new UserTradeException("no.more.money", null);
				}
				// 利息（天）
				double interestFee=0;
				double manageFee=0;
				if (capitalAmount<1500||capitalAmount>CacheManager.getMaxCapitalAmount()) {
					throw new UserTradeException("no.total.lever.money", null);
				}
				//查询管理费的费率和利息的费率
				TradeConfig config = tradeConfigService.findTradeConfig(borrowPeriod,
						capitalMargin, lever);
				if (ObjectUtil.equals(config, null)) {
					throw new UserTradeException("no.interest.config", null);
				}
				interestFee= BigDecimalUtils.mulRound(config.getDailyInterest(), capitalAmount);
				// 管理费（天）
				manageFee= BigDecimalUtils.mulRound(config.getDailyManagementFee(), capitalAmount);
				
		
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
			userTrade.setActivityType(UserTrade.ActivityType.NO);
			userTrade.setWuser(user);
			userTrade.setType((short) type);
			userTrade.setFeeType((short) 0);
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
			
			String expirationDate = tradeDayService.getEndDate(tradeDay, borrowPeriod);
			Date estimateEnd = Dates.parse(expirationDate,
					Dates.CHINESE_DATE_FORMAT_LONG);
			userTrade.setEstimateEndtime(estimateEnd.getTime() / 1000);

			userTrade.setNaturalDays((long)borrowPeriod);
			
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
				userTrade=userTradeService.buildUserTrade(userTrade, wuser, voucherId ,"11");
				
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
				int days=borrowPeriod;
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
			return "redirect:/trade/tradeFail";
		}
		return "redirect:/trade/tradeSuccess";
	}
	
	/**
	 * @author LiuQing
	 * @param user WUser
	 * @param userTrade UserTrade
	 */
	private void setActivityValue(WUser user,UserTrade userTrade,ModelMap modelMap){
		if (userTradeService.isActity_6600(user)) {
			userTrade.setActivityType(UserTrade.ActivityType.ACTIVITY_6600);
			modelMap.put("activityType6600", "true");
		}
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
		return ViewConstants.UserTradeViewJsp.TRADE_OK;
	}
	
	@RequestMapping(value = "/tradeFail")
	public String tradeFail(ModelMap modelMap,HttpServletRequest request) throws Exception {		
		return ViewConstants.UserTradeViewJsp.TRADE_FAIL;
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
	
	@RequestMapping(value = "/list.json" , method = RequestMethod.POST)
	@ResponseBody
	public JsonResult listJson(@RequestParam String type,@RequestParam int countOfCurrentPage,@RequestParam int currentPage,HttpServletRequest request) throws Exception {
		UserSessionBean userSessionBean = (UserSessionBean)request.getSession().getAttribute(Constants.TZDR_USER_SESSION); 
		JsonResult jsonResult = new JsonResult(false);
		PageInfo<Object> pageinfo= new PageInfo<Object>();
		if("all".equals(type)){
			pageinfo=userTradeService.queryUserTrade(userSessionBean.getId(), countOfCurrentPage, currentPage, null);
//			pageinfo=userTradeService.findTrade(pageinfo);
		}else if("opt".equals(type)){
			Map<String, Object> searchParams =Maps.newHashMap();
			searchParams.put("EQ_status", 1);
			pageinfo=userTradeService.queryUserTrade(userSessionBean.getId(), countOfCurrentPage, currentPage, searchParams);
//			pageinfo=userTradeService.findTrade(pageinfo);
		}else if("closed".equals(type)){
			Map<String, Object> searchParams =Maps.newHashMap();
			searchParams.put("EQ_status", 2);
			pageinfo=userTradeService.queryUserTrade(userSessionBean.getId(), countOfCurrentPage, currentPage, searchParams);
		}
		jsonResult.setObj(pageinfo);	
		jsonResult.setSuccess(true);
		return jsonResult;
	}
	
	@RequestMapping(value = "/needlist.json" , method = RequestMethod.POST)
	@ResponseBody
	public JsonResult needlistJson(HttpServletRequest request) throws Exception {
		UserSessionBean userSessionBean = (UserSessionBean)request.getSession().getAttribute(Constants.TZDR_USER_SESSION); 
		JsonResult jsonResult = new JsonResult(false);
		List<UserTradeCmsVo> userTradeCmsVoList=userTradeService.queryNeedAdd(userSessionBean.getId());
		jsonResult.setObj(userTradeCmsVoList);	
		jsonResult.setSuccess(true);
		return jsonResult;
	}
	
	@RequestMapping(value = "/detail/{groupId:T[A-Z0-9]{6}}")
	public String detail(@PathVariable("groupId") String groupId,ModelMap modelMap,HttpServletRequest request) throws Exception {
		UserSessionBean userSessionBean = (UserSessionBean) request.getSession().getAttribute(
				Constants.TZDR_USER_SESSION);
		WUser userTemp=wUserService.getUser(userSessionBean.getId());
//		UserTradeCmsVo userTradeCmsVo =userTradeService.findByGroupIdAndUid(groupId, userSessionBean.getId());	
		UserTradeCmsVo userTradeCmsVo =userTradeService.findByGroupIdAndUidLazy(groupId, userSessionBean.getId());	
		if (userTradeCmsVo.getActivityType() == UserTrade.ActivityType.ACTIVITY_6600) {
		    modelMap.put("activityType6600", 2);	
		}
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
		modelMap.put("parentEndDate",parentEndDate);		
		modelMap.put("balance", userTemp.getAvlBal());
		modelMap.put("trade", userTradeCmsVo);
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
		// 获取已使用自然日
		String today = Dates.format(Dates.CHINESE_DATE_FORMAT_LONG);
        String startDay = Dates.parseBigInteger2Date(
                userTradeCmsVo.getStarttime(),
                Dates.CHINESE_DATE_FORMAT_LONG);
        long useDays = tradeDayService.getNaturalDays(startDay,today);
        modelMap.put("useDays", useDays);
        
		modelMap.put("isExtractableProfit", isExtractableProfit);
		modelMap.put("isAddProgram",isAddProgrom(userTradeCmsVo)?1:0);
		String operatorsInfo = operatorsInfo(userTradeCmsVo.getTotalLending());
		modelMap.put("operatorsInfo", operatorsInfo);
		/*//最低保证金为总操盘资金1%
		modelMap.put("minAddMoney", BigDecimalUtils.mulRound(userTradeCmsVo.getTotalOperateMoney(),0.01));*/
		modelMap.put("minAddMoney", Constants.UserTrade.TRADE_A_MIN_MARGIN);
		modelMap.put("maxAddMoney", Constants.UserTrade.TRADE_A_MAX_MARGIN);
		modelMap.put("totalManageFee", BigDecimalUtils.mulRound(userTradeCmsVo.getFeeDay(),userTradeCmsVo.getTradingDays()));
		modelMap.put("insuranceNo", userTradeCmsVo.getInsuranceNo());
		return ViewConstants.UserTradeViewJsp.DETAIL;
	}
	
	/**
	 * 获取添加保证金信息
	 * @MethodName getRemarginInfo
	 * @author L.Y
	 * @date 2015年7月1日
	 * @param groupId
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getRemarginInfo", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult getRemarginInfo(@RequestParam String gid, HttpServletRequest request) {
		JsonResult jsonResult = new JsonResult(false);
		try {
			UserSessionBean userSessionBean = (UserSessionBean) request
					.getSession().getAttribute(Constants.TZDR_USER_SESSION);
			WUser userTemp = wUserService.getUser(userSessionBean.getId());
			UserTradeCmsVo userTradeCmsVo = userTradeService
					.findByGroupIdAndUidLazy(gid, userSessionBean.getId());
			jsonResult.appendData("balance", userTemp.getAvlBal());
			jsonResult.appendData("trade", userTradeCmsVo);
			jsonResult.appendData("minAddMoney", Constants.UserTrade.TRADE_A_MIN_MARGIN);
			jsonResult.appendData("maxAddMoney", Constants.UserTrade.TRADE_A_MAX_MARGIN);
			/*jsonResult.appendData(
					"minAddMoney",
					BigDecimalUtils.mulRound(
							userTradeCmsVo.getTotalOperateMoney(), 0.01));*/
			jsonResult.setSuccess(Boolean.TRUE);
		} catch (Exception e) {
			log.error("UserTradeController.getRemargin()：" + e.getMessage());
		}
		return jsonResult;
	}
	
	/**
	 * 获取操盘用户信息
	 * @MethodName getTradingUserInfo
	 * @author L.Y
	 * @date 2015年6月30日
	 * @param groupId
	 * @param modelMap
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/tradingUserInfo", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult getTradingUserInfo(@RequestParam String groupId, ModelMap modelMap, HttpServletRequest request) {
		JsonResult jsonResult = new JsonResult(Boolean.FALSE);
		UserSessionBean userSessionBean = (UserSessionBean) request.getSession().getAttribute(
				Constants.TZDR_USER_SESSION);
		try {
			UserTradeCmsVo userTradeCmsVo = userTradeService
					.findTradingUserInfo(groupId, userSessionBean.getId());
			jsonResult.setObj(userTradeCmsVo);
			jsonResult.setSuccess(Boolean.TRUE);
		} catch (Exception e) {
			log.error("UserTradeController.getTradingUserInfo()" + e.getMessage());
		}
		return jsonResult;
	}
	
	@RequestMapping(value = "/detail.json" , method = RequestMethod.POST)
	@ResponseBody
	public JsonResult type(@RequestParam int type,@RequestParam String groupId,ModelMap modelMap,HttpServletRequest request) throws Exception {
		UserSessionBean userSessionBean = (UserSessionBean) request.getSession().getAttribute(
				Constants.TZDR_USER_SESSION);
		WUser user = wUserService.getUser(userSessionBean.getId());
		String basePath=	request.getSession().getServletContext().getRealPath("/");
		JsonResult jsonResult = new JsonResult(false);
		if(type==1){
			List<UserTrade> userTradeList =userTradeService.findByGroupIdAndWuser(groupId, user);
			List<ContractTradeSafeVo> vos=new ArrayList<ContractTradeSafeVo>();
			try {
				vos = contractsaveService.createTradeVo(userTradeList,basePath);
				jsonResult.setObj(vos);
			} catch (Exception e) {
				jsonResult.setObj(userTradeList);
			}
		}else if (type==2){
			List<PositionDetailsVo> positionDetailsVoList =userTradeService.findPositionDetail(groupId);
			jsonResult.setObj(positionDetailsVoList);
		}else if (type==3){
			List<Integer> typeList= Lists.newArrayList();
			typeList.add(11);
			typeList.add(12);
			List<UserFund> userFundList=userFundService.findByUidAndLidAndTypeIn(user.getId(), groupId, typeList);
			List<Integer> typeManage= Lists.newArrayList();
			typeManage.add(12);
			double sumManage =userFundService.sumMoneyByUidAndLidAndTypeIn(user.getId(), groupId, typeManage);
			List<Integer> typeInterest= Lists.newArrayList();
			typeInterest.add(11);
			double sumInterest =userFundService.sumMoneyByUidAndLidAndTypeIn(user.getId(), groupId, typeInterest);
			Map<Object,Object> data= Maps.newHashMap();
			data.put("sumManage", sumManage);
			data.put("sumInterest", sumInterest);
			jsonResult.setData(data);
			jsonResult.setObj(userFundList);
		}else if (type==5){
			List<Integer> typeList= Lists.newArrayList();
			typeList.add(16);
			List<UserFund> userFundList=userFundService.findByUidAndLidAndTypeIn(user.getId(), groupId, typeList);
			double sum =userFundService.sumMoneyByUidAndLidAndTypeIn(user.getId(), groupId, typeList);
			Map<Object,Object> data= Maps.newHashMap();
			data.put("total", userFundList.size());
			data.put("sum", sum);
			jsonResult.setData(data);
			jsonResult.setObj(userFundList);
		}else if (type==6){
			List<Integer> typeList= Lists.newArrayList();
			typeList.add(17);
			List<UserFund> userFundList=userFundService.findByUidAndLidAndTypeIn(user.getId(), groupId, typeList);
			double sum =userFundService.sumMoneyByUidAndLidAndTypeIn(user.getId(), groupId, typeList);
			Map<Object,Object> data= Maps.newHashMap();
			data.put("total", userFundList.size());
			data.put("sum", sum);
			jsonResult.setData(data);
			jsonResult.setObj(userFundList);
		}else if (type==7){
			UserTradeCmsVo userTradeCmsVo =userTradeService.findTradeByGroupIdAndUid(groupId, user.getId());	
			String fundAccount=userTradeCmsVo.getParentAccountNo();
			String combineId=userTradeCmsVo.getCombineId();
			String startTime=Dates.parseBigInteger2Date(userTradeCmsVo.getStarttime(), Dates.CHINESE_DATE_FORMAT_LONG);
			String endTime=Dates.parseBigInteger2Date(userTradeCmsVo.getEndtime(), Dates.CHINESE_DATE_FORMAT_LONG);
            long start=NumberUtils.toLong(startTime);
            long end=NumberUtils.toLong(endTime);
			List<RealdealEntity> realdealEntityList=realDealService.findByFundAccountAndCombineIdAndInitDateBetween(fundAccount, combineId,start,end);
			jsonResult.setObj(realdealEntityList);
		}
		jsonResult.setSuccess(true);
		
		return jsonResult;
	}
	
	
	private static Object lockObj = new Object();
	
	@RequestMapping(value = "/addBond.json" , method = RequestMethod.POST)
	@ResponseBody
	public JsonResult addBond(@RequestParam String groupId,@RequestParam double addMoney,ModelMap modelMap,HttpServletRequest request) throws Exception {
		UserSessionBean userSessionBean = (UserSessionBean) request.getSession().getAttribute(
				Constants.TZDR_USER_SESSION);
		JsonResult jsonResult = new JsonResult(false);
		WUser userTemp=wUserService.getUser(userSessionBean.getId());
		if(dataMapService.isMaintenanceTime()){
			throw new UserTradeException("system.time", null);
		}
		synchronized(lockObj){
		UserTradeCmsVo userTradeCmsVo =userTradeService.findByGroupIdAndUid(groupId, userSessionBean.getId());	
		// double minAdd=BigDecimalUtils.mulRound(userTradeCmsVo.getTotalOperateMoney(),0.01);
		if(addMoney<Constants.UserTrade.TRADE_A_MIN_MARGIN){
			throw new UserTradeException("min.addbond", null);
		}
		if(addMoney>Constants.UserTrade.TRADE_A_MAX_MARGIN){
			throw new UserTradeException("max.addbond", null);
		}
		
		UserTrade userTrade=userTradeService.findOneByGroupIdOrderByAddtimeAsc(groupId);
		Double max=BigDecimalUtils.mulRound(CacheManager.getMaxCapitalAmount(),2.0);
		
		//追加保证金+当前方案总操盘资金<单个恒生帐号的最大值
		if (BigDecimalUtils.add(addMoney, userTradeCmsVo.getAssetTotalValue()) > max) {
			Double maxAdd = BigDecimalUtils.subRound(max, userTradeCmsVo.getAssetTotalValue());
			DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");// 格式化设置
			jsonResult.setMessage("追加保证金最大值为" + decimalFormat.format(maxAdd) + "元");
			return jsonResult;
		}
		String parentAccountNo=userTradeCmsVo.getParentAccountNo();
		ParentAccount parentAccount=parentAccountService.findByAccountNo(parentAccountNo);
		//判断母账户的资金是否足
		if(parentAccount.getFundsBalance()<addMoney){
			throw new UserTradeException("no.account.money", null);
		}
		//追加保证金
		boolean flag=userTradeService.addBond(parentAccount, userTrade, addMoney,userTemp);
		
		
		boolean transFlag = false;
		if (userTrade.getFeeType() < 2){
			String parentCombineId = combineInfoService
					.getHundSunCombineId(userTrade.getUnitNumber());
		    transFlag=userTradeService.transferMoney(parentAccount.getAccountNo(), parentCombineId,
					userTrade.getCombineId(), addMoney, "追加保证金资金划转");			
			if(!transFlag){
				log.error("追加保证金资金划转失败：parentAccount-"+parentAccount.getAccountNo()+"parentCombineId-"+parentCombineId+"targetCombineId-"+userTrade.getCombineId()+"money-"+addMoney);
			}
			if(transFlag){  
				//取消限制买入
				userTradeService.cancelAstrictBuy(userTrade);
			}
		}
		// 涌金版自动进入失败列表不调用接口,transFlag=true 则钱江版划账成功
		if(!transFlag){
			AppendLevelMoneyFail appendLevelMoneyFail =new AppendLevelMoneyFail(userTrade.getWuser().getId(),userTrade.getGroupId(),addMoney,userTrade.getAccountId());
			appendLevelMoneyFailService.save(appendLevelMoneyFail);
		}
		
		
		if(!flag){
			jsonResult.setMessage("追加保证金不成功！");
		}
		jsonResult.setSuccess(flag);
		}
		return jsonResult;
	}
	
	
	/**
	 * 	跳转到追加配资页面
	 * 
	 * @param groupId
	 * @param capitalMargin
	 * @param borrowPeriod
	 * @param tradeStart
	 * @param modelMap
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/more/{groupId:T[A-Z0-9]{6}}")
	public String more(@PathVariable("groupId") String groupId,@RequestParam(required=false) Double capitalMargin ,@RequestParam(required=false) Long borrowPeriod,@RequestParam(required=false) Integer tradeStart,ModelMap modelMap,HttpServletRequest request) throws Exception {
		UserSessionBean userSessionBean = (UserSessionBean)request.getSession().getAttribute(Constants.TZDR_USER_SESSION);  //获取用户账户信息
		WUser wUser = wUserService.getUser(userSessionBean.getId());
		UserTradeCmsVo userTradeCmsVo =userTradeService.findTradeByGroupIdAndUid(groupId, wUser.getId());
		double moreMaxMoney=BigDecimalUtils.convertsToInt(BigDecimalUtils.sub(CacheManager.getMaxCapitalAmount(), userTradeCmsVo.getTotalLending()));
		int lever=userTradeCmsVo.getLever();
		
		double max=BigDecimalUtils.convertsToInt(BigDecimalUtils.div(moreMaxMoney, userTradeCmsVo.getLever()));
				
		String startDay = tradeDayService.getTradeDay();
		String endDay=Dates.parseBigInteger2Date(userTradeCmsVo.getEstimateEndtime(), Dates.CHINESE_DATE_FORMAT_LONG);
		if(borrowPeriod==null){
		 borrowPeriod=tradeDayService.getNaturalDays(startDay,endDay);
		}
		String endDayString=Dates.parseBigInteger2Date(userTradeCmsVo.getEstimateEndtime(), Dates.CHINESE_DATE_FORMAT_LINE);
		modelMap.put("lever", lever);
		modelMap.put("borrowPeriod", borrowPeriod);
		modelMap.put("moreMaxMoney", max);
		modelMap.put("endDayString", endDayString);
		modelMap.put("groupId", groupId);
		if(tradeStart!=null){
		modelMap.put("backTradeStart", tradeStart);
		}
		modelMap.put("capitalMargin", capitalMargin==null?0:capitalMargin);
		return ViewConstants.UserTradeViewJsp.MORE;
	}
	
	
	/**
	 * 	跳转到追加配资支付页面
	 * 	
	 * @param groupId
	 * @param modelMap
	 * @param tradeStart
	 * @param lever
	 * @param type
	 * @param capitalMargin
	 * @param borrowPeriod
	 * @param backtype
	 * @param moreMaxMoney
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/moretrade/{groupId:T[A-Z0-9]{6}}")
	public String moreTrade(@PathVariable("groupId") String groupId,ModelMap modelMap,int tradeStart,int lever,
			 int type,double capitalMargin,int borrowPeriod,String backtype,double moreMaxMoney,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		UserSessionBean userSessionBean = (UserSessionBean) request.getSession().getAttribute(
				Constants.TZDR_USER_SESSION);

		double capitalAmount = BigDecimalUtils.mul(capitalMargin, lever);
		double totalTrader = BigDecimalUtils.add(capitalMargin, capitalAmount);

		TradeConfig config = tradeConfigService.findTradeConfig(borrowPeriod,
				capitalMargin, lever);
		if (ObjectUtil.equals(config, null)) {
			throw new UserTradeException("no.interest.config", null);
		}
		// 利息（天）
		double interestFee = 
				BigDecimalUtils.mulRound(config.getDailyInterest(), capitalAmount);
		// 管理费（天）
		double manageFee = 
				BigDecimalUtils.mulRound(config.getDailyManagementFee(), capitalAmount);

		double totalInterestFee = BigDecimalUtils.mulRound(interestFee,borrowPeriod);
		

		double needPay = BigDecimalUtils.add(capitalMargin, totalInterestFee);

		double shortLine = 0.0;
		double openLine = 0.0;
		if (userSessionBean != null) {
		List<UserVolumeDetailVo> userVolumeDetailVoList = volumeDetailService.findVolume(userSessionBean.getId(), Constants.VolumeType.NOUSE);
		modelMap.put("userVolumeDetailVoList", userVolumeDetailVoList);
		}
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

		modelMap.put("lever", lever);
		modelMap.put("type", type);
		modelMap.put("tradeStart", tradeStart);
		modelMap.put("capitalMargin", capitalMargin);
		modelMap.put("capitalAmount", capitalAmount);
		modelMap.put("totalTrader", totalTrader);
		modelMap.put("borrowPeriod", borrowPeriod);
		modelMap.put("totalInterestFee", totalInterestFee);
		modelMap.put("manageFee", manageFee);
		modelMap.put("shortLine", shortLine);
		modelMap.put("openLine", openLine);
		modelMap.put("operatorsInfo", operatorsInfo);
		modelMap.put("backtype", backtype);
		modelMap.put("moreMaxMoney", moreMaxMoney);
		modelMap.put("groupId", groupId);
		
		if (userSessionBean != null) {
			modelMap.put("user", "on");
			WUser wuser = wUserService.getUser(userSessionBean.getId());
			double payEnough = BigDecimalUtils.sub(wuser.getAvlBal(), needPay);
			double needNextdayPay=BigDecimalUtils.addRound(needPay, manageFee);
			if(tradeStart==0){
				payEnough=BigDecimalUtils.sub(payEnough,manageFee);
				needPay=BigDecimalUtils.addRound(needPay,manageFee);
				needNextdayPay=BigDecimalUtils.addRound(needNextdayPay,manageFee);
			}
			double payNextdayEnough = BigDecimalUtils.sub(wuser.getAvlBal(), needNextdayPay);
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
			} else {
				modelMap.put("need", "off");
				modelMap.put("needPay", needPay);
			}
		} else {
			modelMap.put("user", "off");
		}

		return ViewConstants.UserTradeViewJsp.MORETRADE;
	}
	
	
	@RequestMapping(value = "/addProgramCheck.json" , method = RequestMethod.POST)
	@ResponseBody
	public JsonResult addProgramCheck(@RequestParam double capitalMargin,@RequestParam String groupId,@RequestParam double totalTrader,ModelMap modelMap,HttpServletRequest request) throws Exception {
		UserSessionBean userSessionBean = (UserSessionBean) request.getSession().getAttribute(
				Constants.TZDR_USER_SESSION);
		JsonResult jsonResult = new JsonResult(false);
		UserTradeCmsVo userTradeCmsVo =userTradeService.findTradeByGroupIdAndUid(groupId, userSessionBean.getId());
		Double max=BigDecimalUtils.sub(BigDecimalUtils.mulRound(CacheManager.getMaxCapitalAmount(),2.0), userTradeCmsVo.getTotalOperateMoney());
		//追加方案总操盘资金+当前方案总操盘资金<单个恒生帐号的最大值
		if(BigDecimalUtils.add(totalTrader, userTradeCmsVo.getTotalOperateMoney())>BigDecimalUtils.mulRound(CacheManager.getMaxCapitalAmount(),2.0)){
			throw new UserTradeException("add.max.money", new Object[]{max});
		}
		
		
		//检验倍数问题
		//所有方案配资金额总和不能超过 倍数对应的最大配资金额
		double totalLending = userTradeCmsVo.getTotalLending();
		int lever =  userTradeCmsVo.getLever();
		double  maxAmount=fundConfigService.findAmountByTimes(lever);	
		double capitalAmount = BigDecimalUtils.mul(capitalMargin, lever);
		if(BigDecimalUtils.addRound(capitalAmount,totalLending)>maxAmount){
			double maxCapital=BigDecimalUtils.convertsToInt(BigDecimalUtils.div(BigDecimalUtils.subRound(maxAmount,totalLending),lever));
			throw new UserTradeException("max.input.capital.margin",new Object[]{maxCapital});
		}
		
		
		String parentAccountNo=userTradeCmsVo.getParentAccountNo();
		ParentAccount parentAccount=parentAccountService.findByAccountNo(parentAccountNo);
		//判断母账户的资金是否足
		if(BigDecimalUtils.subRound(parentAccount.getFundsBalance(), parentAccount.getSubFunds())<totalTrader){
			throw new UserTradeException("no.account.money", null);
		}
	
		jsonResult.setSuccess(true);		
		return jsonResult;
	}

	/**
	 * 	提交追加配资申请
	 * 
	 * @param modelMap
	 * @param tradeStart
	 * @param type
	 * @param capitalMargin
	 * @param groupId
	 * @param volumeDetailId
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/moresuccess")
	public String moreSuccess(ModelMap modelMap, int tradeStart,
			int type, double capitalMargin,String groupId,String volumeDetailId,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UserSessionBean userSessionBean = (UserSessionBean) request.getSession().getAttribute(
				Constants.TZDR_USER_SESSION);
		if (ObjectUtil.equals(userSessionBean, null)) {
			throw new UserTradeException("no.user", null);
		}
				
		try {
			UserTradeCmsVo userTradeCmsVo =userTradeService.findTradeByGroupIdAndUid(groupId, userSessionBean.getId());
			// 是否支持追加方案
			if (!isAddProgrom(userTradeCmsVo)){
				throw new UserTradeException("no.account.money", null);
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
			
			if(isTradeDay&&tradeStart==1){
				tradeDay=tradeDayService.getNextTradeDay();
			}						
			
			String endDay=Dates.parseBigInteger2Date(userTradeCmsVo.getEstimateEndtime(), Dates.CHINESE_DATE_FORMAT_LONG);
			long borrowPeriod=tradeDayService.getNaturalDays(tradeDay,endDay);
			
			double moreMaxMoney=BigDecimalUtils.convertsToInt(BigDecimalUtils.sub(CacheManager.getMaxCapitalAmount(),  userTradeCmsVo.getTotalLending()));
			int lever=userTradeCmsVo.getLever();
			
			double max=BigDecimalUtils.convertsToInt(BigDecimalUtils.div(moreMaxMoney, userTradeCmsVo.getLever()));
			
			if (capitalMargin>max||capitalMargin<100) {
				throw new UserTradeException("no.capital.margin", null);
			}
			
			

			double capitalAmount = BigDecimalUtils.mul(capitalMargin, lever);
			double totalLeverMoney = BigDecimalUtils.add(capitalMargin,
					capitalAmount);
			
			if (totalLeverMoney<=0||capitalAmount>CacheManager.getMaxCapitalAmount()) {
				throw new UserTradeException("no.total.lever.money", null);
			}
			
			//检验倍数问题
			//所有方案配资金额总和不能超过 倍数对应的最大配资金额
			double totalLending = userTradeCmsVo.getTotalLending();
			double  maxAmount=fundConfigService.findAmountByTimes(lever);			
			if(BigDecimalUtils.addRound(capitalAmount,totalLending)>maxAmount){
				throw new UserTradeException("no.more.money", null);
			}
			
			//追加方案总操盘资金+当前方案总操盘资金<单个恒生帐号的最大值
			if(BigDecimalUtils.add(totalLeverMoney, userTradeCmsVo.getTotalOperateMoney())>=BigDecimalUtils.mulRound(CacheManager.getMaxCapitalAmount(),2.0)){
					throw new UserTradeException("add.max.money", null);
			}
			String parentAccountNo=userTradeCmsVo.getParentAccountNo();
			ParentAccount parentAccount=parentAccountService.findByAccountNo(parentAccountNo);
			//判断母账户的资金是否足
			if(BigDecimalUtils.subRound(parentAccount.getFundsBalance(), parentAccount.getSubFunds())<totalLeverMoney){
				throw new UserTradeException("no.account.money", null);
			}

			TradeConfig config = tradeConfigService.findTradeConfig((int)borrowPeriod,
					capitalMargin, lever);
			if (ObjectUtil.equals(config, null)) {
				throw new UserTradeException("no.interest.config", null);
			}
			// 利息（天）
			double interestFee = BigDecimalUtils.mulRound(config.getDailyInterest(), capitalAmount);
			// 管理费（天）
			double manageFee = BigDecimalUtils.mulRound(config.getDailyManagementFee(), capitalAmount);

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

			UserTrade userTrade = new UserTrade();
			
			WUser user = wUserService.getUser(userSessionBean.getId());
			
			userTrade.setGroupId(groupId);
			userTrade.setWuser(user);
			userTrade.setType((short) type);
			userTrade.setFeeType((short)userTradeCmsVo.getFeeType());
			userTrade.setTradeStart((short) tradeStart);
			userTrade.setMoney(capitalAmount);
			userTrade.setWarning(shortLine);
			userTrade.setOpen(openLine);
			userTrade.setLever(lever);
			userTrade.setLeverMoney(capitalMargin);
			userTrade.setAddtime(Dates.getCurrentLongDate());
			
			Date trade = Dates.parse(tradeDay, Dates.CHINESE_DATE_FORMAT_LONG);
			
			userTrade.setStarttime(trade.getTime() / 1000);
		
			userTrade.setEstimateEndtime(userTradeCmsVo.getEstimateEndtime().longValue());

			userTrade.setNaturalDays((long)borrowPeriod);
			
			//trade days
			long tradeDays=tradeDayService.getTradeDays(tradeDay, endDay);
			userTrade.setStartdays((int)tradeDays);
			// 日(管理费)
			userTrade.setFeeDay(manageFee);
			// 月(利息)
			userTrade.setFeeMonth(interestFee);
			userTrade.setTotalLeverMoney(totalLeverMoney);
			
			WUser wuser = wUserService.getUser(userSessionBean.getId());

			UserTradeTransfer userTradeTransfer= userTradeService.addMoreUserTradeAndOpenHundsun(userTrade, wuser, parentAccount, userTradeCmsVo,volumeDetailId);
			userTrade=userTradeTransfer.getUserTrade();
			
			// 2.调恒生接口转钱,生成一条配资记录
			// 转钱成功
			if (userTradeService.transferMoney(userTradeTransfer.getAccountNo(), userTradeTransfer.getParentCombineId(),
					userTradeTransfer.getChildrenCombineId(), userTrade.getTotalLeverMoney(),
							"追加方案资金划转")) {
					userTrade.setStatus((short) 1);
				userTradeService.update(userTrade);
			}
			
			// CR0000343 2015.2.27 LinFeng
			if (userTrade.getStatus() != 0) {
				// 5.发短信通知客户
				Map<String, String> map = Maps.newHashMap();
				map.put("group", userTrade.getGroupId());
				map.put("starttime", Dates.format(
						Dates.parseLong2Date(userTrade.getStarttime()),
						Dates.CHINESE_DATE_FORMAT_LINE));
				new SMSPgbSenderThread(wuser.getMobile(),
						"ihuyi.trade.ok.code.template", map).start();
			}
			
			//配资时管理费新校验
			String nextDay = Dates.format(Dates.addDay(new Date()),Dates.CHINESE_DATE_FORMAT_LONG);
			boolean isNextTradeDay = tradeDayService.isTradeDay(nextDay);
			if(isNextTradeDay){
				if(BigDecimalUtils.sub(wuser.getAvlBal(), manageFee)<0){
					String content = MessageUtils.message("fee.fill.deduction.balance.next.day.not.enough");
					new SMSPgbSendForContentThread(wuser.getMobile(),content,2000).start();
					//保存通知记录
					noticeRecordService.save(new NoticeRecord(wuser.getId(), content, 3));
				}			
			}
			
			
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
			String nickName=wuser.getMobile();
			long loanAmounts=(long)capitalAmount;
			double loanInterest=interestFee;
			long margin=(long)capitalMargin;
			double managementFee=manageFee;
			int days=(int)borrowPeriod;
			String startTime=Dates.format(Dates.parseLong2Date(userTrade.getStarttime()), Dates.CHINESE_DATE_FORMAT_LINE);
			String endTime=Dates.format(Dates.parseLong2Date(userTrade.getEstimateEndtime()), Dates.CHINESE_DATE_FORMAT_LINE);
			long warning=(long)shortLine;
			long open=(long)openLine;
			String bathPath = request.getSession().getServletContext().getRealPath("/");
			double totalmoney=userTrade.getTotalLeverMoney();
			String uid=wuser.getId();
			String email=userVerified.getValidateemail();
			//加到协议母账户结束日期
			String parentEndDate=userTradeTransfer.getParentEndTime();
			if(userTrade.getStatus()!=0){
				new ContractThread(programNo, loanTrueName, cardNo, nickName, loanAmounts, loanInterest, margin, managementFee, days, startTime, endTime, warning, open, bathPath,totalmoney,uid,email,parentEndDate).start();
			}
			// 新增返回feetype
			modelMap.put("feeType", userTrade.getFeeType());
			// 新增返回feetype
			modelMap.put("parentEndDate", userTradeTransfer.getParentEndTime());		
			modelMap.put("groupId", userTrade.getGroupId());
			modelMap.put("isMore", true);
			if(userTrade.getStatus()==1){
				modelMap.put("isActive", true);
			}else{
				modelMap.put("isActive", false);	
			}
		} catch (UserTradeException e) { 
			log.error(e.getResourceMessage());
			String dataDetail="userInfo:id:"+userSessionBean.getId()+"|mobile:"+userSessionBean.getMobile()+"|异常："+e.getResourceMessage();
			EmailExceptionHandler.getInstance().HandleExceptionWithData(e, "追加配资失败异常", this.getClass().getName()+":moreSuccess", dataDetail);
			return "redirect:/trade/tradeFail";
		}
		return "redirect:/trade/tradeSuccess";
	}

	
	
	@RequestMapping(value = "/extractableProfit.json" , method = RequestMethod.POST)
	@ResponseBody
	public  JsonResult extractableProfit(@RequestParam String groupId,@RequestParam double extractableProfitMoney,ModelMap modelMap,HttpServletRequest request) throws Exception {
		UserSessionBean userSessionBean = (UserSessionBean) request.getSession().getAttribute(
				Constants.TZDR_USER_SESSION);
		JsonResult jsonResult = new JsonResult(false);
		WUser userTemp=wUserService.getUser(userSessionBean.getId());
		//有调恒生接口计算
		UserTradeCmsVo userTradeCmsVo =userTradeService.findByGroupIdAndUid(groupId, userTemp.getId());	
		
		//涌金版 不能提取利润
		if (userTradeCmsVo.getFeeType()==2){
			jsonResult.setMessage("涌金版配资方案，暂未开通提取利润功！");
			return jsonResult;
		}
		
		if(dataMapService.isMaintenanceTime()){
			throw new UserTradeException("system.time", null);
		}
		
		if(!tradeDayService.isTradeTime()&&BigDecimalUtils.sub(userTradeCmsVo.getExtractableProfit(), extractableProfitMoney)<0){
			throw new UserTradeException("extractable.profit.money", null);
		}
		
		if(extractableProfitMoney<=0){
			throw new UserTradeException("profit.money", null);
		}
		
		UserTrade userTrade=userTradeService.findOneByGroupIdOrderByAddtimeAsc(groupId);
		
		String parentAccountNo=userTradeCmsVo.getParentAccountNo();
		ParentAccount parentAccount=parentAccountService.findByAccountNo(parentAccountNo);
		boolean flag =userTradeService.extractableProfit(userTrade, userTemp, parentAccount,extractableProfitMoney);
		if(!flag){
			jsonResult.setMessage("提取利润不成功！");
		}
		jsonResult.setSuccess(flag);		
		return jsonResult;
	}
	
	
	/**
	 * 	终止方案
	 * 	
	 * @param groupId
	 * @param modelMap
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/endOfProgram.json" , method = RequestMethod.POST)
	@ResponseBody
	public JsonResult endOfProgram(@RequestParam String groupId,ModelMap modelMap,HttpServletRequest request) throws Exception {
		JsonResult jsonResult = new JsonResult(false);
		
		List<UserTrade> userTrades = userTradeService.findByGroupIdOrderByAddtimeAsc(groupId);
		if (CollectionUtils.isEmpty(userTrades)){
			jsonResult.setMessage("系统异常，未找到对应的配资方案！");
		}
		
		// 涌金版 终结方案 修改handtrade状态
		UserTrade endTrade = userTrades.get(0);
		
		if (endTrade.getFeeType()==Constant.FeeType.AUTO_OPEN_CASH_RIVER 
				|| endTrade.getFeeType()==Constant.FeeType.HAND_OPEN_CASH_RIVER){
			jsonResult.setMessage("系统升级中，该功能暂停使用。");
			return jsonResult;
		}
		
		//定制配资不能提前终结
		if(endTrade.getActivityType()==UserTrade.ActivityType.BESPOKETRADE_BESPOKE){
			Long starttime=endTrade.getStarttime();
			Date stime=Dates.parseLong2Date(starttime);
			String stimestr=Dates.format(stime, "yyyyMMdd");
			String etimestr=Dates.format(new Date(), "yyyyMMdd");		
			Integer shortestDuration=endTrade.getShortestDuration();
			int tradeday=tradeDayService.getTradeDays(stimestr, etimestr);
			if(shortestDuration>tradeday){
				jsonResult.setMessage("定制配资的方案不能提前终结！");
				return jsonResult;
			}
		}
		
		String tradeId = endTrade.getId();
		if (endTrade.getFeeType()==Constant.FeeType.HAND_OPEN_WELL_GOLD 
				|| endTrade.getFeeType()==Constant.FeeType.HAND_OPEN_TIERCE){
			HandTrade  handTrade = handTradeService.findByTradeId(tradeId);
				
			if (ObjectUtil.equals(null, handTrade)){
				jsonResult.setMessage("系统异常，未找到对应的配资方案！");
				return jsonResult;
			}
			
			// 页面未刷新，后台已审核用户再次点终结 直接返回
			if (handTrade.getAuditEndStatus()==1){
				jsonResult.setSuccess(true);		
				return jsonResult;
			}
			
			// 终结审核状态等于0，表示已经提交审核不能重复提交！
			if (handTrade.getAuditEndStatus()==0)
			{
				jsonResult.setMessage("您已提交过终结方案申请不能再次提交！");
				return jsonResult;
			}
			
			handTradeService.endWellGoldTrade(handTrade.getId());
		}
		else
		{  // 钱江版终结方案
			if(dataMapService.isMaintenanceTime()){
				throw new UserTradeException("system.time", null);
			}
			userTradeService.endOfProgram(groupId);
		}
		
		jsonResult.setSuccess(true);		
		return jsonResult;
	}
	
	/**
	 * 方案终结提示
	 * @param groupId
	 * @param modelMap
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/checkEndOfProgram.json" , method = RequestMethod.POST)
	@ResponseBody
	public JsonResult checkEndOfProgram(@RequestParam String groupId, @RequestParam int type, ModelMap modelMap, HttpServletRequest request){
		JsonResult jsonResult = new JsonResult(false);
		UserSessionBean userSessionBean = (UserSessionBean) request.getSession().getAttribute(Constants.TZDR_USER_SESSION);
		Map<Object, Object> data = new HashMap<Object, Object>();
		String uid = userSessionBean.getId();
		//交易天数
		Integer tradeDays = null;
		
		data.put("tradeDays", 0);
		try {
			//查询交易开始 时间与结束时间
			UserTradeCmsVo userTradeCmsVo = userTradeService
					.findByGroupIdAndUid(groupId, uid);
			
			if(!ObjectUtil.equals(null, userTradeCmsVo)) {
				//查询交易天数
				tradeDays = tradeDayService.getTradeDays(userTradeCmsVo.getStarttime(), userTradeCmsVo.getEstimateEndtime());
				if(!ObjectUtil.equals(null, tradeDays) && 0 < tradeDays) {
					data.put("tradeDays", tradeDays);
				}
			}
		} catch (Exception e) {
			log.warn("UserTradeController.checkEndOfProgram()" + e.getMessage());
		}
		
		//涌金版是否可以终结方案
		if(2 == type) {
			UserTradeVo UserTradeVo = userTradeService.getUserTradeVoByGroupId(uid, groupId);
			if(UserTradeVo.getFeeType() == 2){
				if(UserTradeVo.getStatus() == 2){   //已终结
					data.put("tradeStatus", "finalized");   
				}else{
					if(UserTradeVo.getAuditEndStatus() != null && UserTradeVo.getAuditEndStatus() == 0){ //终结中
						data.put("tradeStatus", "auditing"); 
					}else{
						//操盘中
						data.put("tradeStatus", "tradering"); 
					}
				}
			}
		}
		jsonResult.setSuccess(true);
		jsonResult.setData(data);
		return jsonResult;
	}
	
	
	/**
	 * 是否能追加方案
	 * @param userTradeCmsVo
	 * @return
	 */
	private boolean isAddProgrom (UserTradeCmsVo userTradeCmsVo){
		String startDate=Dates.format(new Date(), Dates.CHINESE_DATE_FORMAT_LONG);
		String endDate=Dates.parseBigInteger2Date(userTradeCmsVo.getEstimateEndtime(), Dates.CHINESE_DATE_FORMAT_LONG);
		
		long days=tradeDayService.getNaturalDays(startDate, endDate);
		double totalLending=userTradeCmsVo.getTotalLending();

		// 配资金额大于 最大配资金额600万
		if(totalLending>=CacheManager.getMaxCapitalAmount()){
			return false;
		}
		// 总配资金额 大于等于 倍数最大配资金额
		if(totalLending>=fundConfigService.findAmountByTimes(userTradeCmsVo.getLever())){
			return false;
		}
		
		if(days< 7 || userTradeCmsVo.getActivityType()!=UserTrade.ActivityType.NO){
			return false;
		}
		
		//同花顺、涌金版 不能追加方案 
		int feeType = userTradeCmsVo.getFeeType();
		if (feeType==Constant.FeeType.HAND_OPEN_TIERCE 
				  || feeType==Constant.FeeType.HAND_OPEN_WELL_GOLD){
			return false;
		}
		return true;
	}
}

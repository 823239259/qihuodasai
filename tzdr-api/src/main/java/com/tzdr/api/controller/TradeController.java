package com.tzdr.api.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jodd.util.ObjectUtil;
import jodd.util.StringUtil;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.tzdr.api.constants.DataConstant;
import com.tzdr.api.constants.ResultStatusConstant;
import com.tzdr.api.request.RequestObj;
import com.tzdr.api.support.ApiResult;
import com.tzdr.business.api.service.ApiDataMapService;
import com.tzdr.business.api.service.ApiTradeService;
import com.tzdr.business.api.service.ApiUserService;
import com.tzdr.business.service.account.ParentAccountService;
import com.tzdr.business.service.combine.CombineInfoService;
import com.tzdr.business.service.datamap.DataMapService;
import com.tzdr.business.service.fundconfig.FundConfigService;
import com.tzdr.business.service.securityInfo.SecurityInfoService;
import com.tzdr.business.service.stock.StockService;
import com.tzdr.business.service.thread.SMSPgbSendForContentThread;
import com.tzdr.business.service.thread.SMSPgbSenderThread;
import com.tzdr.business.service.tradecalendar.TradeDayService;
import com.tzdr.business.service.userTrade.AppendLevelMoneyFailService;
import com.tzdr.business.service.userTrade.HandTradeService;
import com.tzdr.business.service.userTrade.NoticeRecordService;
import com.tzdr.business.service.userTrade.TradeConfigService;
import com.tzdr.business.service.userTrade.UserTradeService;
import com.tzdr.business.service.wuser.WUserService;
import com.tzdr.business.thread.ContractThread;
import com.tzdr.common.utils.BigDecimalUtils;
import com.tzdr.common.utils.Dates;
import com.tzdr.common.utils.MessageUtils;
import com.tzdr.domain.api.constants.Constant;
import com.tzdr.domain.api.vo.ApiTradeVo;
import com.tzdr.domain.api.vo.TradeDetail;
import com.tzdr.domain.api.vo.UserInfoVo;
import com.tzdr.domain.cache.CacheManager;
import com.tzdr.domain.vo.UserTradeCmsVo;
import com.tzdr.domain.vo.UserTradeTransfer;
import com.tzdr.domain.vo.UserTradeVo;
import com.tzdr.domain.web.entity.AppendLevelMoneyFail;
import com.tzdr.domain.web.entity.HandTrade;
import com.tzdr.domain.web.entity.NoticeRecord;
import com.tzdr.domain.web.entity.ParentAccount;
import com.tzdr.domain.web.entity.Stock;
import com.tzdr.domain.web.entity.TradeConfig;
import com.tzdr.domain.web.entity.UserTrade;
import com.tzdr.domain.web.entity.UserVerified;
import com.tzdr.domain.web.entity.WUser;

/**
 * <B>说明: </B>用户申请方案、获取方案详情相关接口
 * @zhouchen
 * 2016年1月20日
 */
@Controller 
@RequestMapping(value = "/trade")
public class TradeController {

	private static Logger log = LoggerFactory.getLogger(TradeController.class);
	
	@Autowired
	private ApiUserService apiUserService;

	@Autowired
	private WUserService wUserService;

	@Autowired
	private UserTradeService userTradeService;

	@Autowired
	private ApiTradeService apiTradeService;

	@Autowired
	private TradeConfigService tradeConfigService;

	@Autowired
	private TradeDayService tradeDayService;

	@Autowired
	private SecurityInfoService securityInfoService;

	@Autowired
	private FundConfigService fundConfigService;

	@Autowired
	private NoticeRecordService noticeRecordService;
	
	@Autowired
	private HandTradeService  handTradeService;
	
	@Autowired
	private StockService  stockService;
	
	@Autowired
	private DataMapService dataMapService;
	
	@Autowired
	private ParentAccountService parentAccountService;
	@Autowired
	private CombineInfoService combineInfoService;
	
	@Autowired
	private AppendLevelMoneyFailService appendLevelMoneyFailService;
	
	@Autowired
	private ApiDataMapService apiDataMapService;

	private static Object lock = new Object();

	/**
	 * 获取用户方案列表
	 * @param uid
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/list",method = RequestMethod.POST)
	@ResponseBody
	public ApiResult list(@RequestBody RequestObj requestObj, HttpServletRequest request,
			HttpServletResponse response) {

		String uid = requestObj.getUid();
		if (StringUtil.isBlank(uid)) {
			return new ApiResult(false, ResultStatusConstant.FAIL,
					"uid.is.null.");
		}
		
		UserInfoVo infoVo = apiUserService.queryUserInfo(uid);
		if (ObjectUtil.equals(null, infoVo)){
			return new ApiResult(false,ResultStatusConstant.TradeList.USER_INFO_NOT_EXIST,"user.info.not.exist.");
		}
		
		List<ApiTradeVo> apiTradeVos = apiTradeService.findUserTrades(uid);
		return new ApiResult(true, ResultStatusConstant.SUCCESS,
				"query.success.", apiTradeVos);
	}

	/**
	 * 获取方案详情
	 * 
	 * @param groupId
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/detail",method = RequestMethod.POST)
	@ResponseBody
	public ApiResult detail(@RequestBody RequestObj requestObj, HttpServletRequest request,
			HttpServletResponse response) {

		String groupId = requestObj.getGroupId();
		String uid = requestObj.getUid();
		if (StringUtil.isBlank(groupId) || StringUtil.isBlank(uid)) {
			return new ApiResult(false, ResultStatusConstant.FAIL,
					"params.is.null.");
		}
		TradeDetail tradeDetail = apiTradeService.findUserTradeDetail(groupId,uid);
		if (ObjectUtil.equals(null,tradeDetail)) {
			return new ApiResult(false, ResultStatusConstant.FAIL,"not.find.this.detail.");
		}
		
		return new ApiResult(true, ResultStatusConstant.SUCCESS,
				"query.success.", tradeDetail);
	}

	/**
	 * 确认配资
	 * 
	 * @param jsonObject
	 * @param tradeStart
	 * @param lever
	 * @param type
	 * @param capitalMargin
	 * @param borrowPeriod
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/confirm",method = RequestMethod.POST)
	@ResponseBody
	public ApiResult confirm(@RequestBody RequestObj requestObj, 
			HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		int tradeStart=requestObj.getTradeStart();
		int lever=requestObj.getLever();
		double capitalMargin=requestObj.getCapitalMargin();
		int borrowPeriod=requestObj.getBorrowPeriod();
		String uid=requestObj.getUid();
		
		/*ApiToken  apiToken = (ApiToken) request.getAttribute(DataConstant.API_TOKEN);
		double minCapitalMargin = DataConstant.CAPITALMARGIN_MIN;
		if (!ObjectUtil.equals(null,apiToken) 
				&& StringUtil.equals(DataConstant.SOURCE_WAP,apiToken.getSource())){
			minCapitalMargin = DataConstant.PLATFORM_CAPITALMARGIN_MIN;
		}
		int maxLever = DataConstant.LEVER_MAX;
		if (!ObjectUtil.equals(null,apiToken) 
				&& StringUtil.equals(DataConstant.SOURCE_PEIGUBAO,apiToken.getSource())){
			maxLever = DataConstant.LEVER_MAX_SEX;
		}*/
		
		if (StringUtil.isBlank(uid) || lever < DataConstant.LEVER_MIN
				|| lever > DataConstant.LEVER_MAX
				|| capitalMargin < DataConstant.CAPITALMARGIN_MIN
				|| borrowPeriod < DataConstant.BORROWPERIOD_MIN
				|| borrowPeriod > DataConstant.BORROWPERIOD_MAX) {
			return new ApiResult(false, ResultStatusConstant.FAIL,
					"params.error.");
		}

		// 判断是否超过最大操盘方案数  默认最多3个
		int holdMaxNum = apiDataMapService.getHoldMaxTradeNum();
		int holdNum = apiTradeService.findUserOperateTrades(uid);
		if (holdNum >= holdMaxNum){
			return new ApiResult(false, ResultStatusConstant.ConfirmTrade.OVER_MAX_HOLD_TRADE_NUM,
					"over.max.hold.trade.num.");
		}		
				
		//校验 当前时段 最大配资金额 和 最多配资个数
		ApiResult apiResult = validateMaxLeverMony(lever, capitalMargin,DataConstant.TYPE_CONFIRM,uid);
		if (!apiResult.isSuccess()){
			return apiResult;
		}
				
		double capitalAmount = BigDecimalUtils.mul(capitalMargin, lever);
		double totalTrader = BigDecimalUtils.addRound(capitalMargin,
				capitalAmount);

		// 检验倍数，最大赔资额问题
		double maxAmount = fundConfigService.findAmountByTimes(lever);
		if (capitalAmount > maxAmount) {
			return new ApiResult(false,
					ResultStatusConstant.ConfirmTrade.OVER_MAX_TRADE_MONEY,
					"over.max.trade.money");
		}
		
		TradeConfig config = tradeConfigService.findTradeConfig(borrowPeriod,
				capitalMargin, lever);
		if (ObjectUtil.equals(config, null)) {
			return new ApiResult(false,
					ResultStatusConstant.ConfirmTrade.NO_INTEREST_CONFIG,
					"no.interest.config.");
		}

		String currentDay = Dates.format(new Date(),
				Dates.CHINESE_DATE_FORMAT_LONG);
		String tradeDay = tradeDayService.getTradeDay();
		boolean isTradeDay = Boolean.FALSE;
		if (tradeDay.equals(currentDay)) {
			isTradeDay = Boolean.TRUE;
		}

		if (!isTradeDay) {
			if (tradeStart == 0) {
				return new ApiResult(false,
						ResultStatusConstant.ConfirmTrade.NO_TRADE_START,
						"no.trade.start.");
			}
		}
		// 利息（天）
		double interestFee = BigDecimalUtils.mulRound(
				config.getDailyInterest(), capitalAmount);
		// 管理费（天）
		double manageFee = BigDecimalUtils.mulRound(
				config.getDailyManagementFee(), capitalAmount);

		double totalInterestFee = BigDecimalUtils.mulRound(interestFee,
				borrowPeriod);

		// 计算平仓线 和补仓线
		double shortLine = 0.0;
		double openLine = 0.0;
		List<Double> lines = handOpenAndShortLine(shortLine, openLine, capitalAmount, lever);
		shortLine = BigDecimalUtils.round(lines.get(0), 0);
		openLine = BigDecimalUtils.round(lines.get(1), 0);

		// 设置返回值
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("lever", lever);
		jsonObject.put("tradeStart", tradeStart);
		jsonObject.put("capitalMargin", capitalMargin);
		jsonObject.put("totalTrader", totalTrader);
		jsonObject.put("borrowPeriod", borrowPeriod);
		jsonObject.put("totalInterestFee", totalInterestFee);
		jsonObject.put("manageFee", manageFee);
		jsonObject.put("shortLine", BigDecimalUtils.round(shortLine, 0));
		jsonObject.put("openLine", BigDecimalUtils.round(openLine, 0));
		jsonObject.put("startTime", tradeDay);

		WUser user = wUserService.getUser(uid);
		if (ObjectUtil.equals(null, user)) {
			return new ApiResult(false,
					ResultStatusConstant.ConfirmTrade.USER_INFO_NOT_EXIST,
					"user.info.not.exist.");
		}
		
						
		UserVerified userVerified = securityInfoService.findByUserId(user
				.getId());
		if (!ObjectUtils.equals(userVerified, null)
				&& StringUtil.isNotBlank(userVerified.getIdcard())) {
			jsonObject.put("isVerified", 1);
		} else {
			jsonObject.put("isVerified", 0);
		}
		// 总支付金额
		double needPay = BigDecimalUtils.addRound(capitalMargin,
				totalInterestFee);
		if (tradeStart == 0) {
			needPay = BigDecimalUtils.addRound(needPay, manageFee);
		}
		jsonObject.put("needPay", needPay);
		jsonObject.put("balance", user.getAvlBal());
		return new ApiResult(true, ResultStatusConstant.SUCCESS,
				"confirm.success.", jsonObject);

	}

	/**
	 * 完成配资操作接口
	 * @param modelMap
	 * @param requestObj
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/handle",method = RequestMethod.POST)
	@ResponseBody
	public ApiResult handle(ModelMap modelMap,@RequestBody RequestObj requestObj,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		int tradeStart=requestObj.getTradeStart();
		int lever=requestObj.getLever();
		double capitalMargin=requestObj.getCapitalMargin();
		int borrowPeriod=requestObj.getBorrowPeriod();
		String uid=requestObj.getUid();
		
		if (StringUtil.isBlank(uid)) {
			return new ApiResult(false, ResultStatusConstant.FAIL,
					"uid.is.null.");
		}

		// 判断是否超过最大操盘方案数  默认最多3个
		int holdMaxNum = apiDataMapService.getHoldMaxTradeNum();
		int holdNum = apiTradeService.findUserOperateTrades(uid);
		if (holdNum >= holdMaxNum){
			return new ApiResult(false, ResultStatusConstant.HandleTrade.OVER_MAX_HOLD_TRADE_NUM,
					"over.max.hold.trade.num.");
		}
				
		WUser user = wUserService.getUser(uid);
		if (ObjectUtil.equals(user, null)) {
			return new ApiResult(false,
					ResultStatusConstant.HandleTrade.USER_INFO_NOT_EXIST,
					"user.info.not.exist.");
		}
		// 查询实名认证
		UserVerified isUserVerified = securityInfoService.findByUserId(uid);
		if (ObjectUtils.equals(isUserVerified, null)
				|| StringUtil.isBlank(isUserVerified.getIdcard())) {
			return new ApiResult(false,
					ResultStatusConstant.HandleTrade.NOT_AUTH_REALNAME,
					"not.auth.realname.");
		}

		/**
		 *  wap 最大4倍  peigubao 最大6倍
		 */
	/*	ApiToken  apiToken = (ApiToken) request.getAttribute(DataConstant.API_TOKEN);
		int maxLever = DataConstant.LEVER_MAX;
		if (!ObjectUtil.equals(null,apiToken) 
				&& StringUtil.equals(DataConstant.SOURCE_PEIGUBAO,apiToken.getSource())){
			maxLever = DataConstant.LEVER_MAX_SEX;
		}*/
		
		if (lever > DataConstant.LEVER_MAX || lever < DataConstant.LEVER_MIN) {
			return new ApiResult(false,
					ResultStatusConstant.HandleTrade.LEVER_ERROR,
					"lever.error.");
		}

		/** 
		 * 根据来源区分最低保证金  
		 * wap 50000
		 * peigubao 100
		 */
		/*double minCapitalMargin = DataConstant.CAPITALMARGIN_MIN;
		if (!ObjectUtil.equals(null,apiToken) 
				&& StringUtil.equals(DataConstant.SOURCE_WAP,apiToken.getSource())){
			minCapitalMargin = DataConstant.PLATFORM_CAPITALMARGIN_MIN;
		}*/
		
		if (capitalMargin > CacheManager.getMaxCapitalAmount()
				|| capitalMargin < DataConstant.CAPITALMARGIN_MIN) {
			return new ApiResult(false,
					ResultStatusConstant.HandleTrade.CASH_FUND_ERROR,
					"cash.fund.error.");
		}

		if (borrowPeriod > DataConstant.BORROWPERIOD_MAX || capitalMargin < DataConstant.BORROWPERIOD_MIN) {
			return new ApiResult(false,
					ResultStatusConstant.HandleTrade.BORROW_PERIOD_ERROR,
					"borrow.period.error");
		}

		double capitalAmount = BigDecimalUtils.mul(capitalMargin, lever);
		double totalLeverMoney = BigDecimalUtils.add(capitalMargin,
				capitalAmount);
		// 检验倍数，最大赔资额问题
		double maxAmount = fundConfigService.findAmountByTimes(lever);
		if (capitalAmount > maxAmount) {
			return new ApiResult(false,
					ResultStatusConstant.HandleTrade.OVER_MAX_TRADE_MONEY,
					"over.max.trade.money");
		}

		if (capitalAmount < 1500
				|| capitalAmount > CacheManager.getMaxCapitalAmount()) {
			return new ApiResult(false,
					ResultStatusConstant.HandleTrade.MAX_TRADE_MONEY_600,
					"max.trade.money.600");
		}
		// 查询管理费的费率和利息的费率
		TradeConfig config = tradeConfigService.findTradeConfig(borrowPeriod,
				capitalMargin, lever);
		if (ObjectUtil.equals(config, null)) {
			return new ApiResult(false,
					ResultStatusConstant.HandleTrade.NO_INTEREST_CONFIG,
					"no.interest.config.");
		}

		//校验 当前时段 最大配资金额 和 最多配资个数
		ApiResult apiResult = validateMaxLeverMony(lever, capitalMargin,DataConstant.TYPE_HANDLE,uid);
		if (!apiResult.isSuccess()){
			return apiResult;
		}
		
		// 利息（天）
		double interestFee = BigDecimalUtils.mulRound(
				config.getDailyInterest(), capitalAmount);
		// 管理费（天）
		double manageFee = BigDecimalUtils.mulRound(
				config.getDailyManagementFee(), capitalAmount);
		
		// 亏损补仓钱，亏损平仓钱的计算
		double shortLine = 0.0;
		double openLine = 0.0;
		List<Double> lines = handOpenAndShortLine(shortLine, openLine, capitalAmount, lever);
		shortLine = BigDecimalUtils.round(lines.get(0), 0);
		openLine = BigDecimalUtils.round(lines.get(1), 0);

		UserTrade userTrade = new UserTrade();

		userTrade.setWuser(user);
		userTrade.setType((short) 0);
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
		// 算交易日
		String tradeDay = tradeDayService.getTradeDay();
		boolean isTradeDay = Boolean.FALSE;
		if (tradeDay.equals(currentDay)) {
			isTradeDay = Boolean.TRUE;
		}

		if (!isTradeDay) {
			if (tradeStart == 0) {
				return new ApiResult(false,
						ResultStatusConstant.HandleTrade.NO_TRADE_START,
						"no.trade.start.");
			}
		}

		if (isTradeDay && tradeStart == 1) {
			tradeDay = tradeDayService.getNextTradeDay();
		}

		Date trade = Dates.parse(tradeDay, Dates.CHINESE_DATE_FORMAT_LONG);

		userTrade.setStarttime(trade.getTime() / 1000);

		String expirationDate = tradeDayService.getEndDate(tradeDay,
				borrowPeriod);
		Date estimateEnd = Dates.parse(expirationDate,
				Dates.CHINESE_DATE_FORMAT_LONG);
		userTrade.setEstimateEndtime(estimateEnd.getTime() / 1000);

		userTrade.setNaturalDays((long) borrowPeriod);

		// trade days
		long tradeDays = tradeDayService.getTradeDays(tradeDay, expirationDate);
		userTrade.setStartdays((int) tradeDays);
		// 日(管理费)
		userTrade.setFeeDay(manageFee);
		// 月(利息)
		userTrade.setFeeMonth(interestFee);
		userTrade.setTotalLeverMoney(totalLeverMoney);
		WUser wuser = wUserService.getUser(user.getId());
		if(userTradeService.isHaveNoTrade(wuser)){
			userTrade.setNewStatus(1);
		}
		synchronized (lock) {
			UserTradeTransfer userTradeTransfer = new UserTradeTransfer();
			userTradeTransfer = userTradeService.addUserTradeAndOpenHundsun(
					userTrade, wuser, null);
			userTrade = userTradeTransfer.getUserTrade();

			// 钱江版 划账
			if (userTrade.getFeeType() == 0 || userTrade.getFeeType() == 1) {
				// 5.调恒生接口转钱,生成一条配资记录
				// 转钱成功
				if (userTradeService.transferMoney(
						userTradeTransfer.getAccountNo(),
						userTradeTransfer.getParentCombineId(),
						userTradeTransfer.getChildrenCombineId(),
						userTrade.getTotalLeverMoney(), "申请方案资金划转")) {
					userTrade.setStatus((short) 1);
					userTradeService.update(userTrade);
				}
				// 8.发短信通知客户
				if (userTrade.getStatus() != 0) {
					Map<String, String> map = Maps.newHashMap();
					map.put("group", userTrade.getGroupId());
					map.put("starttime", Dates.format(
							Dates.parseLong2Date(userTrade.getStarttime()),
							Dates.CHINESE_DATE_FORMAT_LINE));

					new SMSPgbSenderThread(wuser.getMobile(),
							"ihuyi.trade.ok.code.template", map).start();
				}

				// 配资时管理费新校验
				String nextDay = Dates.format(Dates.addDay(new Date()),
						Dates.CHINESE_DATE_FORMAT_LONG);
				boolean isNextTradeDay = tradeDayService.isTradeDay(nextDay);
				if (isNextTradeDay) {
					if (BigDecimalUtils.sub(wuser.getAvlBal(), manageFee) < 0) {
						String content = MessageUtils
								.message("fee.fill.deduction.balance.next.day.not.enough");
						new SMSPgbSendForContentThread(wuser.getMobile(), content,
								2000).start();
						// 保存通知记录
						noticeRecordService.save(new NoticeRecord(
								wuser.getId(), content, 3));
					}
				}

				/**
				 * @Description: (处理配资合同动态参数)
				 * @Title: createContract
				 * @param contractNo
				 *            协议编号
				 * @param loanTrueName
				 *            真实姓名
				 * @param cardNo
				 *            身份证号
				 * @param nickName
				 *            投资用户名
				 * @param loanAmounts
				 *            借款金额
				 * @param loanInterest
				 *            借款利息 元/日
				 * @param margin
				 *            保证金金额
				 * @param managementFee
				 *            账户管理费 元/交易日
				 * @param days
				 *            借款期限（天数）自然日
				 * @param startTime
				 *            借款期限（开始时间 格式如：2015-01-21）
				 * @param endTime
				 *            借款期限（结束时间 格式如：2015-01-25）
				 * @param warning
				 *            警戒线
				 * @param open
				 *            平仓线
				 * @return List<String> 返回类型
				 */
				// 动态参数
				UserVerified userVerified = wuser.getUserVerified();
				String programNo = userTrade.getProgramNo();
				String loanTrueName = userVerified.getTname();
				String cardNo = userVerified.getIdcard();
				String email = userVerified.getValidateemail();
				String nickName = wuser.getMobile();
				long loanAmounts = (long) capitalAmount;
				double loanInterest = interestFee;
				long margin = (long) capitalMargin;
				double managementFee = manageFee;
				int days = borrowPeriod;
				String startTime = Dates.format(
						Dates.parseLong2Date(userTrade.getStarttime()),
						Dates.CHINESE_DATE_FORMAT_LINE);
				String endTime = Dates.format(
						Dates.parseLong2Date(userTrade.getEstimateEndtime()),
						Dates.CHINESE_DATE_FORMAT_LINE);
				long warning = (long) shortLine;
				long open = (long) openLine;
				String bathPath = request.getSession().getServletContext()
						.getRealPath("/");
				//加到协议母账户结束日期
				String parentEndDate=userTradeTransfer.getParentEndTime();
				if(!userTradeService.isActity_6600(user)){
					double totalmoney=userTrade.getTotalLeverMoney();
					if(userTrade.getStatus()!=0){
						new ContractThread(programNo, loanTrueName, cardNo, nickName, loanAmounts, loanInterest, margin, managementFee, days, startTime, endTime, warning, open, bathPath,totalmoney,uid,email,parentEndDate).start();
						
					}
				}
			}
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("feeType", userTrade.getFeeType());
		jsonObject.put("groupId", userTrade.getGroupId());
		jsonObject.put("account", userTrade.getAccount());
		jsonObject.put("password", userTrade.getPassword());
		jsonObject.put("status", userTrade.getStatus());
		// 设置方案状态
		if (Constant.FEE_TYPE_WELLGOLD == userTrade.getFeeType()){
			jsonObject.put("status",Constant.OPENING_ACCOUNT);
		}
		return new ApiResult(true, ResultStatusConstant.SUCCESS,
				"trade.success.", jsonObject);
	}

	/**
	 * 获取交易日接口
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/trade_day")
	@ResponseBody
	public ApiResult tradeDay(HttpServletRequest request,HttpServletResponse response) {
		String  tradeDay = tradeDayService.getTradeDay();
		if (StringUtil.isBlank(tradeDay)){
			return new ApiResult(false, ResultStatusConstant.FAIL,"query.fail.");
		}
		JSONObject  jsonObject = new JSONObject();
		jsonObject.put("tradeDay", tradeDay);
		return new ApiResult(true, ResultStatusConstant.SUCCESS,"query.success.",jsonObject);
	}
	
	
	/**
	 * 获取下个交易日接口
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/trade_next_day")
	@ResponseBody
	public ApiResult tradeNextDay(HttpServletRequest request,HttpServletResponse response) {
		String  tradeDay = tradeDayService.getNextTradeDay();
		if (StringUtil.isBlank(tradeDay)){
			return new ApiResult(false, ResultStatusConstant.FAIL,"query.fail.");
		}
		JSONObject  jsonObject = new JSONObject();
		jsonObject.put("nextTradeDay", tradeDay);
		return new ApiResult(true, ResultStatusConstant.SUCCESS,"query.success.",jsonObject);
	}
	
	
	/**
	 * 获取两个时间段的交易日天数
	 * @param startTime
	 * @param endTime
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/interval_trade_days")
	@ResponseBody
	public ApiResult intervalTradeDays(@RequestBody RequestObj requestObj,HttpServletRequest request,
			HttpServletResponse response) {
		String startTime = requestObj.getStartTime();
		String endTime = requestObj.getEndTime();
		
		if (StringUtil.isBlank(endTime) 
				|| StringUtil.isBlank(startTime) 
				|| !NumberUtils.isNumber(startTime) 
				|| !NumberUtils.isNumber(endTime)
				|| NumberUtils.toInt(startTime)>=NumberUtils.toInt(endTime)
				|| startTime.length()!=DataConstant.CHINESE_DATE_FORMAT_LONG_LENGTH
				|| endTime.length()!=DataConstant.CHINESE_DATE_FORMAT_LONG_LENGTH
				|| !Dates.isValidDate(startTime,Dates.CHINESE_DATE_FORMAT_LONG)
				|| !Dates.isValidDate(endTime,Dates.CHINESE_DATE_FORMAT_LONG))
		{
			return new ApiResult(false, ResultStatusConstant.FAIL,"params.is.null or params.is.not.date.pattern.");
		}
		Integer  tradeDays = tradeDayService.getTradeDays(startTime,endTime);
		JSONObject  jsonObject = new JSONObject();
		jsonObject.put("tradeDays", tradeDays);
		return new ApiResult(true, ResultStatusConstant.SUCCESS,"query.success.",jsonObject);
	}
	
	
	/**
	 * 获取限制股接口
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/limit_stocks")
	@ResponseBody
	public ApiResult limitStocks(HttpServletRequest request,HttpServletResponse response) {
		List<Stock>  stocks = stockService.findData();
		if (CollectionUtils.isEmpty(stocks)){
			return new ApiResult(true, ResultStatusConstant.SUCCESS,"query.success;data.is.null.");
		}
		JSONArray  jsonArray = new JSONArray();
		for (Stock stock : stocks){
			JSONObject  jsonObject = new JSONObject();
			jsonObject.put("stockCode", stock.getCode());
			jsonObject.put("stockName", stock.getName());
			jsonArray.add(jsonObject);
		}
		return new ApiResult(true, ResultStatusConstant.SUCCESS,"query.success.",jsonArray);
	}
	
	
	private static Object lockObj = new Object();
	/**
	 * 追加保证金接口
	 * @param requestObj
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/add_bond",method = RequestMethod.POST)
	@ResponseBody
	public ApiResult addBond(@RequestBody RequestObj requestObj,HttpServletRequest request) throws Exception {
		
		String uid = requestObj.getUid();
		String groupId=requestObj.getGroupId();
		double money=requestObj.getMoney();
		
		if (StringUtil.isBlank(groupId) || StringUtil.isBlank(uid)){
			return new ApiResult(false, ResultStatusConstant.FAIL,"add.fail;params.is.null.");
		}
		
		
		if(dataMapService.isMaintenanceTime()){
			return new ApiResult(false, ResultStatusConstant.AddBond.SYSTEM_MAINTENANCE_TIME,"system.maintenance.time.");
		}
		
		WUser userTemp=wUserService.getUser(uid);
		if (ObjectUtil.equals(null,userTemp)){
			return new ApiResult(false, ResultStatusConstant.AddBond.USER_INFO_NOT_EXIST,"user.info.not.exist.");
		}
		
		boolean flag = false;
		synchronized(lockObj) {
			UserTradeCmsVo userTradeCmsVo =userTradeService.findByGroupIdAndUid(groupId,uid);	
			if (ObjectUtil.equals(null, userTradeCmsVo)){
				return new ApiResult(false, ResultStatusConstant.AddBond.PROGRAM_NOT_EXIST,"program.not.exist.");
			}
			
			double minAdd=BigDecimalUtils.mulRound(userTradeCmsVo.getTotalOperateMoney(),0.01);
			if(money<minAdd){
				return new ApiResult(false, ResultStatusConstant.AddBond.ERROR_ADD_BOND_MONEY,"error.add.bond.money.");
			}
			
			if (DataConstant.TRADE_STATUS_HANDLING != userTradeCmsVo.getStatus()){
				return new ApiResult(false, ResultStatusConstant.AddBond.ONLY_HANDLING_TRADE_CAN_ADDBOND,"only.handling.trade.can.addbond.");

			}
			UserTrade userTrade=userTradeService.findOneByGroupIdOrderByAddtimeAsc(groupId);
			Double max=BigDecimalUtils.mulRound(CacheManager.getMaxCapitalAmount(),2.0);
			
			//追加保证金+当前方案总操盘资金<单个恒生帐号的最大值
			if(BigDecimalUtils.add(money, userTradeCmsVo.getAssetTotalValue())>max){
				return new ApiResult(false, ResultStatusConstant.AddBond.OVER_ACCOUNT_MAX_MONEY,"over.account.max.money.");
			}
			String parentAccountNo=userTradeCmsVo.getParentAccountNo();
			ParentAccount parentAccount=parentAccountService.findByAccountNo(parentAccountNo);
			//判断母账户的资金是否足
			if(parentAccount.getFundsBalance()<money){
				return new ApiResult(false, ResultStatusConstant.AddBond.UNABLE_ADD_BOND,"unable.add.bond.");
			}
			//追加保证金
			flag=userTradeService.addBond(parentAccount, userTrade, money,userTemp);
			
			boolean transFlag = false;
			if (userTrade.getFeeType() < Constant.FEE_TYPE_WELLGOLD){
				String parentCombineId = combineInfoService
						.getHundSunCombineId(userTrade.getUnitNumber());
			    transFlag=userTradeService.transferMoney(parentAccount.getAccountNo(), parentCombineId,
						userTrade.getCombineId(), money, "追加保证金资金划转");			
				if(!transFlag){
					log.error("追加保证金资金划转失败：parentAccount-"+parentAccount.getAccountNo()+"parentCombineId-"+parentCombineId+"targetCombineId-"+userTrade.getCombineId()+"money-"+money);
				}
			}
			// 涌金版自动进入失败列表不调用接口,transFlag=true 则钱江版划账成功
			if(!transFlag){
				AppendLevelMoneyFail appendLevelMoneyFail =new AppendLevelMoneyFail(userTrade.getWuser().getId(),userTrade.getGroupId(),money,userTrade.getAccountId());
				appendLevelMoneyFailService.save(appendLevelMoneyFail);
			}
		}

		if(flag){
			return new ApiResult(true, ResultStatusConstant.SUCCESS,"add.bond.success.");
		}
		return new ApiResult(false, ResultStatusConstant.FAIL,"add.bond.fail.");
	}
	
	

	/**
	 * 终结方案
	 * @param groupId
	 * @param uid
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/end_program",method = RequestMethod.POST)
	@ResponseBody
	public ApiResult endOfProgram(@RequestBody RequestObj requestObj,HttpServletRequest request) throws Exception {
		
		 String groupId=requestObj.getGroupId();
		 String uid=requestObj.getUid();
		
		if (StringUtil.isBlank(groupId) || StringUtil.isBlank(uid)){
			return new ApiResult(false, ResultStatusConstant.FAIL,"end.fail;params.is.null.");
		}
		
		WUser wuser=wUserService.get(uid);
		if (ObjectUtil.equals(null,wuser)){
			return new ApiResult(false, ResultStatusConstant.EndProgram.USER_INFO_NOT_EXIST,"user.info.not.exist.");
		}
		
		List<UserTrade> userTrades = apiTradeService.findByWuserAndGroupId(uid,groupId);
		if (CollectionUtils.isEmpty(userTrades)){
			return new ApiResult(false, ResultStatusConstant.EndProgram.PROGRAM_NOT_EXIST,"program.not.exist.");
		}
		// 涌金版 终结方案 修改handtrade状态
		UserTrade endTrade = userTrades.get(0);
		if (DataConstant.TRADE_STATUS_HANDLING != endTrade.getStatus()){
			return new ApiResult(false, ResultStatusConstant.EndProgram.ONLY_HANDLING_TRADE_CAN_END,"only.handling.trade.can.end.");

		}
		
		String tradeId = endTrade.getId();
		if (endTrade.getFeeType()==2){
			HandTrade  handTrade = handTradeService.findByTradeId(tradeId);
				
			if (ObjectUtil.equals(null, handTrade)){
				return new ApiResult(false, ResultStatusConstant.EndProgram.PROGRAM_NOT_EXIST,"program.not.exist.");

			}
			
			// 页面未刷新，后台已审核用户再次点终结 直接返回
			if (handTrade.getAuditEndStatus()==1){
				return new ApiResult(true, ResultStatusConstant.SUCCESS,"end.success.");
			}
			
			// 终结审核状态等于0，表示已经提交审核不能重复提交！
			if (handTrade.getAuditEndStatus()==0)
			{
				return new ApiResult(false, ResultStatusConstant.EndProgram.REPEAT_SUBMIT,"repeat.submit.");
			}
			
			handTradeService.endWellGoldTrade(handTrade.getId());
		}
		else
		{  // 钱江版终结方案
			if(dataMapService.isMaintenanceTime()){
				return new ApiResult(false, ResultStatusConstant.EndProgram.SYSTEM_MAINTENANCE_TIME,"system.maintenance.time.");
			}
			userTradeService.endOfProgram(groupId);
		}
		return new ApiResult(true, ResultStatusConstant.SUCCESS,"end.success.");
	}
	
	/**
	 * 校验 当前时段 最大配资金额 和 最多配资个数
	 * @param lever
	 * @param capitalMargin
	 * @param type
	 * @return
	 */
	private ApiResult  validateMaxLeverMony(int lever,double capitalMargin,int type,String uid){
		//是否超过当前时段最大配资金额
		String isOpenMaxLeverMoney = dataMapService.isOpenMaxLeverMoney();
		String maxLeverMoney = dataMapService.maxLeverMoney();
		if(StringUtil.equals(DataConstant.IS_OPEN_MAXLEVERMONEY, isOpenMaxLeverMoney) 
				&& BigDecimalUtils.mulRound(lever, capitalMargin) > NumberUtils.toDouble(maxLeverMoney)){
			if (DataConstant.TYPE_HANDLE == type){
				return new ApiResult(false,
						ResultStatusConstant.HandleTrade.OVER_MAX_LEVERMONEY,
						"over.max.levermoney.");
			}
			
			return new ApiResult(false,
					ResultStatusConstant.ConfirmTrade.OVER_MAX_LEVERMONEY,
					"over.max.levermoney.");
		}
		//限制当天当个用户配资数
		String limitTradeNum = dataMapService.isLimitTodayTradeNum();
		//获取用户当天配资数
		List<UserTradeVo> userTradeVoList = userTradeService.queryUserDayTrades(uid,Dates.format(Dates.CHINESE_DATE_FORMAT_LINE));
		int holdTrades = 0 ;
		if (!CollectionUtils.isEmpty(userTradeVoList)){
			holdTrades = userTradeVoList.size();
		}
		if(StringUtil.equals(DataConstant.IS_OPEN_MAXLEVERMONEY, isOpenMaxLeverMoney) 
				&& holdTrades >= NumberUtils.toInt(limitTradeNum)){
			
			if (DataConstant.TYPE_HANDLE == type){
				return new ApiResult(false,
					ResultStatusConstant.HandleTrade.OVER_MAX_LIMIT_TRADENUM,
					"over.max.limit.tradenum.");
			}
			
			return new ApiResult(false,
					ResultStatusConstant.ConfirmTrade.OVER_MAX_LIMIT_TRADENUM,
					"over.max.limit.tradenum.");
		}
		
		return new ApiResult("validate.success.");
	}
	
	/**
	 * 计算 补仓线 和  平仓线
	 * @param shortLine
	 * @param openLine
	 * @param capitalAmount
	 * @param lever
	 */
	private List<Double> handOpenAndShortLine(double shortLine,double openLine,
			double capitalAmount,int lever){
		if (1 <= lever && lever <= 5) {
			shortLine = BigDecimalUtils.mul(capitalAmount, 1.1);
			openLine = BigDecimalUtils.mul(capitalAmount, 1.07);
		} else if (lever == 6) {
			shortLine = BigDecimalUtils.mul(capitalAmount, 1.0867);
			openLine = BigDecimalUtils.mul(capitalAmount, 1.06);
		} else if (lever == 7) {
			/*shortLine = BigDecimalUtils.mul(capitalAmount, 1.0771);
			openLine = BigDecimalUtils.mul(capitalAmount, 1.0529);*/
			shortLine = BigDecimalUtils.mul(capitalAmount, 1.0929);
			openLine = BigDecimalUtils.mul(capitalAmount, 1.0571);
		} else if (lever == 8) {
			/*shortLine = BigDecimalUtils.mul(capitalAmount, 1.07);
			openLine = BigDecimalUtils.mul(capitalAmount, 1.0475);*/
			shortLine = BigDecimalUtils.mul(capitalAmount, 1.0875);
			openLine = BigDecimalUtils.mul(capitalAmount, 1.0563);
		} else if (lever == 9) {
			shortLine = BigDecimalUtils.mul(capitalAmount, 1.0644);
			openLine = BigDecimalUtils.mul(capitalAmount, 1.0433);
		} else if (lever == 10) {
			shortLine = BigDecimalUtils.mul(capitalAmount, 1.06);
			openLine = BigDecimalUtils.mul(capitalAmount, 1.04);
		} else if (lever == 11) {
			shortLine = BigDecimalUtils.mul(capitalAmount, 1.0682);
			openLine = BigDecimalUtils.mul(capitalAmount, 1.0409);
		} else if (lever == 12) {
			shortLine = BigDecimalUtils.mul(capitalAmount, 1.0625);
			openLine = BigDecimalUtils.mul(capitalAmount, 1.0375);
		} else if (lever == 13) {
			shortLine = BigDecimalUtils.mul(capitalAmount, 1.0577);
			openLine = BigDecimalUtils.mul(capitalAmount, 1.0346);
		} else if (lever == 14) {
			shortLine = BigDecimalUtils.mul(capitalAmount, 1.0536);
			openLine = BigDecimalUtils.mul(capitalAmount, 1.0321);
		} else if (lever == 15) {
			shortLine = BigDecimalUtils.mul(capitalAmount, 1.05);
			openLine = BigDecimalUtils.mul(capitalAmount, 1.03);
		}
		return Lists.newArrayList(shortLine,openLine);
	}
}

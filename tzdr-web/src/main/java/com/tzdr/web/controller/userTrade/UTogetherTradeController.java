package com.tzdr.web.controller.userTrade;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;
import com.tzdr.business.exception.UserTradeException;
import com.tzdr.business.service.account.ParentAccountService;
import com.tzdr.business.service.combine.CombineInfoService;
import com.tzdr.business.service.contractsave.ContractsaveService;
import com.tzdr.business.service.datamap.DataMapService;
import com.tzdr.business.service.pay.UserFundService;
import com.tzdr.business.service.realdeal.RealDealService;
import com.tzdr.business.service.securityInfo.SecurityInfoService;
import com.tzdr.business.service.togetherTrade.TogetherConfigService;
import com.tzdr.business.service.togetherTrade.TogetherTradeService;
import com.tzdr.business.service.tradecalendar.TradeDayService;
import com.tzdr.business.service.userTrade.AppendLevelMoneyFailService;
import com.tzdr.business.service.userTrade.HandTradeService;
import com.tzdr.business.service.userTrade.TradeConfigService;
import com.tzdr.business.service.userTrade.UserTradeService;
import com.tzdr.business.service.wuser.WUserService;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.exception.EmailExceptionHandler;
import com.tzdr.common.utils.BigDecimalUtils;
import com.tzdr.common.utils.Dates;
import com.tzdr.common.utils.TypeConvert;
import com.tzdr.common.web.support.JsonResult;
import com.tzdr.domain.cache.CacheManager;
import com.tzdr.domain.cache.DataDicKeyConstants;
import com.tzdr.domain.entity.DataMap;
import com.tzdr.domain.vo.UserTradeCmsVo;
import com.tzdr.domain.vo.UserTradeVo;
import com.tzdr.domain.web.entity.AppendLevelMoneyFail;
import com.tzdr.domain.web.entity.HandTrade;
import com.tzdr.domain.web.entity.ParentAccount;
import com.tzdr.domain.web.entity.TogetherConfig;
import com.tzdr.domain.web.entity.TogetherTrade;
import com.tzdr.domain.web.entity.TradeConfig;
import com.tzdr.domain.web.entity.UserFund;
import com.tzdr.domain.web.entity.UserTrade;
import com.tzdr.domain.web.entity.UserVerified;
import com.tzdr.domain.web.entity.WUser;
import com.tzdr.web.constants.Constants;
import com.tzdr.web.constants.ViewConstants;
import com.tzdr.web.utils.UserSessionBean;
/**
 * <B>说明: </B>股票合买需要登录
 * @author LiuYang
 *
 * 2016年1月20日 上午10:00:01
 */
@Controller
@RequestMapping("/usertogether")
public class UTogetherTradeController {
	private static Logger log = LoggerFactory.getLogger(UTogetherTradeController.class);
	
	@Autowired
	private SecurityInfoService securityInfoService;
	@Autowired
	private TradeConfigService tradeConfigService;
	@Autowired
	private UserTradeService userTradeService;
	@Autowired
	private TogetherConfigService togetherConfigService;
	@Autowired
	private UserFundService userFundService;
	@Autowired
	private TradeDayService tradeDayService;
	@Autowired
	private WUserService wUserService;
	@Autowired
	private DataMapService  dataMapService;
	@Autowired
	private CombineInfoService combineInfoService;
	@Autowired
	private ContractsaveService contractsaveService;
	@Autowired
	private AppendLevelMoneyFailService appendLevelMoneyFailService;
	@Autowired
	private RealDealService realDealService;
	@Autowired
	private ParentAccountService parentAccountService;
	@Autowired
	private HandTradeService handTradeService;
	@Autowired
	private TogetherTradeService togetherTradeService;
	
	/**
	 * 跳转到方案支付页
	 * @param modelMap
	 * @param recommendMoney
	 * @param lever
	 * @param days
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/totrade")
	public String toTrade(ModelMap modelMap,   Double recommendMoney,  Integer lever,  Integer days, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		UserSessionBean userSessionBean = (UserSessionBean) request.getSession().getAttribute(Constants.TZDR_USER_SESSION);
		if (userSessionBean != null) {
			if(recommendMoney == null){
				recommendMoney = 0.0;
			}
			if(lever ==null){
				lever = 0;
			}
			if(days ==null){
				days = 0;
			}
			
			WUser wuser = wUserService.getUser(userSessionBean.getId());
			//查询是否实名认证
			UserVerified userVerified = securityInfoService.findByUserId(userSessionBean.getId());
			int isVerified=0;
			if(!ObjectUtils.equals(userVerified, null)&&!ObjectUtils.equals(userVerified.getIdcard(), null)){
				isVerified=1;
			}
			TogetherConfig togetherConfig = togetherConfigService.getParams();
			if (ObjectUtil.equals(null, togetherConfig)) {
				return this.toIndexpage(modelMap);
			}

			// 校验总操盘资金是否合法
			if (recommendMoney < togetherConfig.getMinMoney() || recommendMoney > togetherConfig.getMaxMoney()) {
				
				return this.toIndexpage(modelMap);
			}
			// 校验倍数是否合法
			String leverStr = lever + "";

			String[] levers = togetherConfig.getMoneyRatio().split(Constants.SEPERATOR_SEMICOLON);

			if (ArrayUtils.isEmpty(levers)) {
				this.toIndexpage(modelMap);
			} else {
				boolean flag = false;
				for (String configLever : levers) {
					if (leverStr.equals(configLever)) {
						flag = true;
					}
				}
				if (!flag) {
					return this.toIndexpage(modelMap);
				}
			}

			// 校验天数是否合法
			String daysStr = days + "";

			String[] recommendDay = togetherConfig.getRecommendDay().split(Constants.SEPERATOR_SEMICOLON);

			if (ArrayUtils.isEmpty(recommendDay)) {
				return this.toIndexpage(modelMap);
			} else {
				boolean flag = false;
				for (String daysLever : recommendDay) {
					if (daysStr.equals(daysLever)) {
						flag = true;
					}
				}
				if (!flag) {
					return this.toIndexpage(modelMap);
				}
			}

			//计算保证金 = 总操盘金/（倍数+1）
			double cautionMoney = BigDecimalUtils.div(recommendMoney, lever+1.0, 0);
			//计算配资金额 = 总操盘金 - 保证金
			double capitalAmount = BigDecimalUtils.sub(recommendMoney, cautionMoney);
			
			double shortLine = 0.0; // 补仓
			double openLine = 0.0; // 平仓
			if (1 <= lever && lever <= 5) {
				shortLine = BigDecimalUtils.mul(capitalAmount, 1.1);
				openLine = BigDecimalUtils.mul(capitalAmount, 1.07);
			} else if (lever == 6) {
				shortLine = BigDecimalUtils.mul(capitalAmount, 1.0867);
				openLine = BigDecimalUtils.mul(capitalAmount, 1.06);
			} else if (lever == 7) {
				shortLine = BigDecimalUtils.mul(capitalAmount, 1.0929);
				openLine = BigDecimalUtils.mul(capitalAmount, 1.0571);
			} else if (lever == 8) {
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
			shortLine = BigDecimalUtils.round(shortLine, 0);
			openLine = BigDecimalUtils.round(openLine, 0);
			TradeConfig config = tradeConfigService.findTradeConfig(days, cautionMoney, lever);
			if (ObjectUtil.equals(config, null)) {
				String detail = "borrwPeriod:" + days + "lever:" + lever + "capitalMargin:" + cautionMoney;
				log.error("利息配置有误" + detail);
				throw new UserTradeException("no.interest.config", null);
			}

			// 合买利息系数
			double foenusRatio = togetherConfig.getFoenusRatio();
			// 合买管理费系数
			double manageRatio = togetherConfig.getManageRatio();
			// 利息（天）
			double interestFee = BigDecimalUtils.mulRound(BigDecimalUtils.mulRound(config.getDailyInterest(), capitalAmount), foenusRatio);
			// 管理费（天）
			double manageFee = BigDecimalUtils.mulRound(BigDecimalUtils.mulRound(config.getDailyManagementFee(), capitalAmount), manageRatio);
			// 总利息
			double totalInterestFee = BigDecimalUtils.mulRound(interestFee, days);
			// 需要支付的总金额 = 配资保证金 + 总利息
			double needPay = BigDecimalUtils.addRound(cautionMoney, totalInterestFee);
			// 加上下个交易日的管理费
			double needNextdayPay = BigDecimalUtils.addRound(needPay, manageFee);
			// 是否够利息和配资金额
			double payEnough = BigDecimalUtils.sub(wuser.getAvlBal(), needPay);
			// 是否够利息和配资金额和下个交易日管理费
			double payNextdayEnough = BigDecimalUtils.sub(wuser.getAvlBal(), needNextdayPay);
			String operatorsInfo = operatorsInfo(capitalAmount);
			if (payNextdayEnough < 0) {
				modelMap.put("needNext", "on");
			} else {
				modelMap.put("needNext", "off");
			}
			if (payEnough < 0) {
				modelMap.put("need", "on");
			} else {
				modelMap.put("need", "off");
			}
			modelMap.put("isVerified", isVerified);
			modelMap.put("avlBal", wuser.getAvlBal());
			modelMap.put("payEnough", -payEnough);
			modelMap.put("needPay", needPay);
			modelMap.put("recommendMoney", cautionMoney);
			modelMap.put("totalMoney", recommendMoney);
			modelMap.put("days", days);
			modelMap.put("lever", lever);
			modelMap.put("shortLine", shortLine);
			modelMap.put("openLine", openLine);
			modelMap.put("interestFee", interestFee);
			modelMap.put("manageFee", manageFee);
			modelMap.put("totalInterestFee", totalInterestFee);
			modelMap.put("operatorsInfo", operatorsInfo);
		} else {
			modelMap.put("user", "off");
		}
		return ViewConstants.TogetherTradeJsp.PAY;
	}

	private static Object lock = new Object();
	/**
	 * 配资成功页面
	 * @param modelMap
	 * @param recommendMoney
	 * @param lever
	 * @param days
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/success")
	public String tradeOk(ModelMap modelMap,  Double totalMoney,Double recommendMoney,  Integer lever,  Integer days, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UserSessionBean userSessionBean = (UserSessionBean) request.getSession().getAttribute(Constants.TZDR_USER_SESSION);

		if (ObjectUtil.equals(userSessionBean, null)) {
			throw new UserTradeException("no.user", null);
		}

		WUser wuser = wUserService.getUser(userSessionBean.getId());
		try {
			TogetherConfig togetherConfig = togetherConfigService.getParams();
			if (ObjectUtil.equals(null, togetherConfig)) {
				return this.toIndexpage(modelMap);
			}
			UserVerified userVerified = securityInfoService.findByUserId(userSessionBean.getId());
			if(ObjectUtils.equals(userVerified, null)||ObjectUtils.equals(userVerified.getIdcard(), null)){
				return this.toIndexpage(modelMap);
			}
			// 总操盘资金是否合法
			if (totalMoney < togetherConfig.getMinMoney() || totalMoney > togetherConfig.getMaxMoney()) {
				return this.toIndexpage(modelMap);
			}
			// 校验倍数是否合法
			String leverStr = lever + "";

			String[] levers = togetherConfig.getMoneyRatio().split(Constants.SEPERATOR_SEMICOLON);

			if (ArrayUtils.isEmpty(levers)) {
				return this.toIndexpage(modelMap);
			} else {
				boolean flag = false;
				for (String configLever : levers) {
					if (leverStr.equals(configLever)) {
						flag = true;
					}
				}
				if (!flag) {
					return this.toIndexpage(modelMap);
				}
			}
			// 校验天数是否合法
			String daysStr = days + "";

			String[] recommendDay = togetherConfig.getRecommendDay().split(Constants.SEPERATOR_SEMICOLON);

			if (ArrayUtils.isEmpty(recommendDay)) {
				return this.toIndexpage(modelMap);
			} else {
				boolean flag = false;
				for (String daysLever : recommendDay) {
					if (daysStr.equals(daysLever)) {
						flag = true;
					}
				}
				if (!flag) {
					return this.toIndexpage(modelMap);
				}
			}
			//计算配资金额 = 总操盘金 - 保证金
			double capitalAmount = BigDecimalUtils.sub(totalMoney, recommendMoney);	

			double shortLine = 0.0; // 补仓
			double openLine = 0.0; // 平仓
			if (1 <= lever && lever <= 5) {
				shortLine = BigDecimalUtils.mul(capitalAmount, 1.1);
				openLine = BigDecimalUtils.mul(capitalAmount, 1.07);
			} else if (lever == 6) {
				shortLine = BigDecimalUtils.mul(capitalAmount, 1.0867);
				openLine = BigDecimalUtils.mul(capitalAmount, 1.06);
			} else if (lever == 7) {
				shortLine = BigDecimalUtils.mul(capitalAmount, 1.0929);
				openLine = BigDecimalUtils.mul(capitalAmount, 1.0571);
			} else if (lever == 8) {
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
			shortLine = BigDecimalUtils.round(shortLine, 0);
			openLine = BigDecimalUtils.round(openLine, 0);
			TradeConfig config = tradeConfigService.findTradeConfig(days, recommendMoney, lever);
			if (ObjectUtil.equals(config, null)) {
				String detail = "borrwPeriod:" + days + "lever:" + lever + "capitalMargin:" + recommendMoney;
				log.error("利息配置有误" + detail);
				throw new UserTradeException("no.interest.config", null);
			}

			// 合买利息系数
			double foenusRatio = togetherConfig.getFoenusRatio();
			// 合买管理费系数
			double manageRatio = togetherConfig.getManageRatio();
			// 利息（天）
			double interestFee = BigDecimalUtils.mulRound(BigDecimalUtils.mulRound(config.getDailyInterest(), capitalAmount), foenusRatio);
			// 管理费（天）
			double manageFee = BigDecimalUtils.mulRound(BigDecimalUtils.mulRound(config.getDailyManagementFee(), capitalAmount), manageRatio);
			// 总利息
			double totalInterestFee = BigDecimalUtils.mulRound(interestFee, days);
			// 需要支付的总金额 = 配资保证金 + 总利息
			double needPay = BigDecimalUtils.addRound(recommendMoney, totalInterestFee);
			// 加上下个交易日的管理费
			double needNextdayPay = BigDecimalUtils.addRound(needPay, manageFee);
			// 是否够利息和配资金额
			double payEnough = BigDecimalUtils.sub(wuser.getAvlBal(), needPay);
			// 是否够利息和配资金额和下个交易日管理费
			double payNextdayEnough = BigDecimalUtils.sub(wuser.getAvlBal(), needNextdayPay);
			if (payNextdayEnough < 0) {
				return this.toIndexpage(modelMap);
			}
			if (payEnough < 0) {
				return this.toIndexpage(modelMap);
			}
			UserTrade userTrade = new UserTrade();
			// 属于合买配资
			userTrade.setActivityType(UserTrade.ActivityType.TOGETHER_TRADE);
			userTrade.setWuser(wuser);
			userTrade.setType((short) 0);
			userTrade.setFeeType((short) 2);
			userTrade.setTradeStart((short) 1);// 下个交易日生效
			userTrade.setMoney(capitalAmount);
			userTrade.setWarning(shortLine);
			userTrade.setOpen(openLine);
			userTrade.setLever(lever);
			userTrade.setLeverMoney(recommendMoney);
			userTrade.setAddtime(Dates.getCurrentLongDate());
			// 计算交易日(合买配资默认下个交易日生效)
			String tradeDay = tradeDayService.getNextTradeDay();
			Date trade = Dates.parse(tradeDay, Dates.CHINESE_DATE_FORMAT_LONG);

			userTrade.setStarttime(trade.getTime() / 1000);
			String expirationDate = tradeDayService.getEndDate(tradeDay, days);
			Date estimateEnd = Dates.parse(expirationDate, Dates.CHINESE_DATE_FORMAT_LONG);
			userTrade.setEstimateEndtime(estimateEnd.getTime() / 1000);
			userTrade.setNaturalDays((long) days);
			long tradeDays = tradeDayService.getTradeDays(tradeDay, expirationDate);
			userTrade.setStartdays((int) tradeDays);
			// 日(管理费)
			userTrade.setFeeDay(manageFee);
			// 月(利息)
			userTrade.setFeeMonth(interestFee);
			userTrade.setTotalLeverMoney(totalMoney);
			synchronized (lock) {
				userTrade = userTradeService.buildUserTrade(userTrade, wuser, "");
				// 生成合买trade记录 
				TogetherTrade tTrade = new TogetherTrade();
				tTrade.setTid(userTrade.getId());
				tTrade.setGid(userTrade.getGroupId());
				tTrade.setType(1);
				tTrade.setProfitRatio(togetherConfig.getProfitRatio());
				togetherTradeService.save(tTrade);
			}
			wuser.setUserType("1");
			wUserService.update(wuser);
			modelMap.put("groupId", userTrade.getGroupId());
		} catch (UserTradeException e) {
			String dataDetail = "userInfo:id:" + wuser.getId() + "|mobile:" + wuser.getMobile() + "|异常：" + e.getResourceMessage();
			log.error(dataDetail);
			EmailExceptionHandler.getInstance().HandleExceptionWithData(e, "申请配资失败异常", this.getClass().getName() + ":tradeOk", dataDetail);
			return this.toIndexpage(modelMap);
		}
		return "redirect:/usertogether/tradeSuccess";
		
	}
	@RequestMapping(value = "/tradeSuccess")
	public String tradeSuccess(ModelMap modelMap,HttpServletRequest request,String groupId) throws Exception {
		modelMap.put("groupId", groupId);
		return ViewConstants.TogetherTradeJsp.PAY_SUCCESSFUL;
	}
	
	/**
	 * 方案列表页面
	 * @param modelMap
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/list")
	public String list(ModelMap modelMap, HttpServletRequest request) throws Exception {
		UserSessionBean userSessionBean = (UserSessionBean) request.getSession().getAttribute(Constants.TZDR_USER_SESSION); // 获取用户账户信息
		WUser wUser = wUserService.getUser(userSessionBean.getId());
		Double totalAccrual = userTradeService.getSumAccrualByWuserAndStatus(wUser.getId(), (short) 2); // 累计盈亏
		double totalAccrualRate = 0;
		if (wUser.getTotalDeposit() + wUser.getTotalInterestMo() + wUser.getTotalManagerMo() != 0) {
			totalAccrualRate = BigDecimalUtils.mul(BigDecimalUtils.div(totalAccrual, wUser.getTotalDeposit() + wUser.getTotalInterestMo() + wUser.getTotalManagerMo(), 4), 100);
		}

		modelMap.put("totalLending", wUser.getTotalLending());// 累计配资金额
		modelMap.put("totalDeposit", wUser.getTotalDeposit());// 累计支出保证金
		modelMap.put("totalInterestMo", wUser.getTotalInterestMo());// 累计利息
		modelMap.put("totalManagerMo", wUser.getTotalManagerMo());// 累计管理费
		modelMap.put("totalAccrual", totalAccrual);// 累计盈亏
		modelMap.put("totalAccrualRate", totalAccrualRate);// 盈亏率
		return ViewConstants.TogetherTradeJsp.LIST;
	}
	/**
	 * 所有方案列表数据
	 * @param type
	 * @param countOfCurrentPage
	 * @param currentPage
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/list.json", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult listJson(@RequestParam String type, @RequestParam int countOfCurrentPage, @RequestParam int currentPage, HttpServletRequest request) throws Exception {
		UserSessionBean userSessionBean = (UserSessionBean) request.getSession().getAttribute(Constants.TZDR_USER_SESSION);
		JsonResult jsonResult = new JsonResult(false);
		PageInfo<Object> pageinfo= new PageInfo<Object>();
		pageinfo=userTradeService.queryUserTogetherTrade(userSessionBean.getId(), countOfCurrentPage, currentPage, null);
		jsonResult.setObj(pageinfo);
		jsonResult.setSuccess(true);
		return jsonResult;
	}
	
	/**
	 * 跳转到方案详情页面
	 * @param groupId
	 * @param modelMap
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/detail/{groupId:HM[A-Z0-9]{7}}")
	public String detail(@PathVariable("groupId") String groupId, ModelMap modelMap, HttpServletRequest request) throws Exception {
		UserSessionBean userSessionBean = (UserSessionBean) request.getSession().getAttribute(Constants.TZDR_USER_SESSION);
		WUser userTemp = wUserService.getUser(userSessionBean.getId());
		UserTradeCmsVo userTradeCmsVo = userTradeService.findByGroupIdAndUidLazy(groupId, userSessionBean.getId());
		List<UserFund> ticketfunds = userFundService.getVolumeDetail(groupId, userSessionBean.getId(), Constants.UserFundStatus.TICKET);
		if (ticketfunds != null && ticketfunds.size() > 0) {
			modelMap.put("isshowticket", true);
		}
		int isExtractableProfit = 0;
		if (!tradeDayService.isTradeTime()) {
			isExtractableProfit = 1;
		}

		// 方案最长可延期至
		String parentAccountNo = userTradeCmsVo.getParentAccountNo();
		String parentEndDate = null;
		if (!StringUtil.isBlank(parentAccountNo)) {
			ParentAccount parentAccount = parentAccountService.findByAccountNo(parentAccountNo);
			if (!ObjectUtil.equals(null, parentAccount) && !ObjectUtil.equals(null, parentAccount.getAllocationDate())) {
				parentEndDate = TypeConvert.long1000ToDateStr((parentAccount.getAllocationDate() - 5 * Dates.DAY_LONG));
			}
		}
		modelMap.put("parentEndDate", parentEndDate);
		modelMap.put("balance", userTemp.getAvlBal());
		modelMap.put("trade", userTradeCmsVo);
		modelMap.put("activityEnd", false);
		if (userTradeCmsVo.getActivityType() == 1 && userTradeCmsVo.getStatus() == 2) {
			List<DataMap> enddata = CacheManager.getDataMapListByTypeKey(DataDicKeyConstants.ACTI_END);
			String endtime = enddata.get(0).getValueKey();
			Date today = new Date();
			Date enddate = Dates.parse(endtime, "yyyy-MM-dd HH:mm:ss");
			long max = (today.getTime() - enddate.getTime());
			if (max >= 0) {
				double rank = userTradeService.findActivityByGroupId(groupId);
				int rankint = (int) rank * 100;
				modelMap.put("rankint", rankint);
				modelMap.put("activityEnd", true);
			}
		}
		modelMap.put("isExtractableProfit", isExtractableProfit);
		modelMap.put("isAddProgram", isAddProgrom(userTradeCmsVo) ? 1 : 0);
		String operatorsInfo = operatorsInfo(userTradeCmsVo.getTotalLending());
		modelMap.put("operatorsInfo", operatorsInfo);
		modelMap.put("minAddMoney", Constants.UserTrade.TRADE_A_MIN_MARGIN);
		modelMap.put("maxAddMoney", Constants.UserTrade.TRADE_A_MAX_MARGIN);
		modelMap.put("totalManageFee", BigDecimalUtils.mulRound(userTradeCmsVo.getFeeDay(), userTradeCmsVo.getTradingDays()));
		modelMap.put("insuranceNo", userTradeCmsVo.getInsuranceNo());
		return ViewConstants.TogetherTradeJsp.DETAIL;
	}
	/**
	 * 方案详情信息
	 * @param groupId
	 * @param modelMap
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/detail.json" , method = RequestMethod.POST)
	@ResponseBody
	public JsonResult type(@RequestParam String groupId,ModelMap modelMap,HttpServletRequest request) throws Exception {
		UserSessionBean userSessionBean = (UserSessionBean) request.getSession().getAttribute(
				Constants.TZDR_USER_SESSION);
		WUser user = wUserService.getUser(userSessionBean.getId());
		JsonResult jsonResult = new JsonResult(false);
	
			List<Integer> typeList= Lists.newArrayList();
			typeList.add(11);
			typeList.add(12);
			List<UserFund> userFundList=userFundService.findByUidAndLidAndTypeIn(user.getId(), groupId, typeList);
			jsonResult.setObj(userFundList);
		
		jsonResult.setSuccess(true);
		
		return jsonResult;
	}
	
	private static Object lockObj = new Object();
	/**
	 * 获取追加保证金信息
	 * @param groupId
	 * @param addMoney
	 * @param modelMap
	 * @param request
	 * @return
	 * @throws Exception
	 */
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
		
		
		String tradeId = endTrade.getId();

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
		jsonResult.setSuccess(true);		
		return jsonResult;
	}
	
	
	
	/**
	 * 是否能追加方案
	 * 
	 * @param userTradeCmsVo
	 * @return
	 */
	private boolean isAddProgrom(UserTradeCmsVo userTradeCmsVo) {
		return false;
	}
	/**
	 * 计算提示信息
	 * @param capitalAmount
	 * @return
	 */
	public String operatorsInfo(double capitalAmount) {
		String operatorsInfo = "";
		if (capitalAmount <= 100000) {
			operatorsInfo = "投资沪深A股，仓位不限制。";
		} else if (100000 < capitalAmount && capitalAmount <= 500000) {
			operatorsInfo = "投资沪深A股，仓位有限制，单只股票不超过总操盘资金的70%。";
		} else if (500000 < capitalAmount && capitalAmount <= 1000000) {
			operatorsInfo = "投资沪深A股，仓位有限制，只股票不超过总操盘资金的50%。";
		} else if (1000000 < capitalAmount) {
			operatorsInfo = "投资沪深A股，仓位有限制，只股票不超过总操盘资金的50%(创业板33%)。";
		}
		return operatorsInfo;
	}

	/**
	 * 信息错误返回 初始页面
	 * @param modelMap
	 * @return
	 */
	public String toIndexpage(ModelMap modelMap){
		TogetherConfig togetherConfig = togetherConfigService.getParams();
		modelMap.put("togetherConfig", togetherConfig);
		String recommendMoney = togetherConfig.getRecommendMoney();
		String recommendDay = togetherConfig.getRecommendDay();
		String moneyRatio  =  togetherConfig.getMoneyRatio();
				
		if (StringUtil.isNotBlank(recommendMoney)){
			modelMap.put("recommendMoney",recommendMoney.split(Constants.SEPERATOR_SEMICOLON));
		}
		
		if (StringUtil.isNotBlank(recommendDay)){
			modelMap.put("recommendDay",recommendDay.split(Constants.SEPERATOR_SEMICOLON));
		}
		
		if (StringUtil.isNotBlank(moneyRatio)){
			modelMap.put("moneyRatio",moneyRatio.split(Constants.SEPERATOR_SEMICOLON));
		}
		return ViewConstants.TogetherTradeJsp.INDEX;
	}
}

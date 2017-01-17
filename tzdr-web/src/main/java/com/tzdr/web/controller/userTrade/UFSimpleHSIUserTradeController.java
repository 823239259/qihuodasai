package com.tzdr.web.controller.userTrade;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jodd.util.StringUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.hundsun.t2sdk.common.util.UUID;
import com.tzdr.business.cms.service.messagePrompt.MessagePromptService;
import com.tzdr.business.cms.service.messagePrompt.PromptTypes;
import com.tzdr.business.service.contract.ContractParitiesService;
import com.tzdr.business.service.datamap.DataMapService;
import com.tzdr.business.service.future.FSimpleCouponService;
import com.tzdr.business.service.tradecalendar.TradeDayService;
import com.tzdr.business.service.userTrade.FSimpleConfigService;
import com.tzdr.business.service.userTrade.FSimpleFtseUserTradeService;
import com.tzdr.business.service.userTrade.FSimpleParitiesService;
import com.tzdr.business.service.wuser.WUserService;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.utils.Dates;
import com.tzdr.common.utils.TypeConvert;
import com.tzdr.common.web.support.JsonResult;
import com.tzdr.domain.vo.FSimpleFtseUserTradeWebVo;
import com.tzdr.domain.web.entity.ContractParities;
import com.tzdr.domain.web.entity.FSimpleConfig;
import com.tzdr.domain.web.entity.FSimpleFtseUserTrade;
import com.tzdr.domain.web.entity.FSimpleParities;
import com.tzdr.domain.web.entity.WUser;
import com.tzdr.domain.web.entity.future.FSimpleCoupon;
import com.tzdr.web.constants.Constants;
import com.tzdr.web.constants.ViewConstants;
import com.tzdr.web.utils.CookiesUtil;
import com.tzdr.web.utils.UserSessionBean;

/**
 * 
 * 
 * <p>
 * </p>
 * 
 * @author WangPinQun
 * @see UFSimpleHSIUserTradeController
 * @version 2.0 2015年10月12日下午09:33:13
 */
@Controller
@RequestMapping("/userhsi")
public class UFSimpleHSIUserTradeController {

	private static Logger log = LoggerFactory.getLogger(UFSimpleHSIUserTradeController.class);

	@Autowired
	private FSimpleConfigService fSimpleConfigService;

	@Autowired
	private FSimpleFtseUserTradeService fSimpleFtseUserTradeService;

	@Autowired
	private FSimpleParitiesService fSimpleParitiesService;

	@Autowired
	private WUserService wUserService;

	@Autowired
	private TradeDayService tradeDayService;
	@Autowired
	private DataMapService dataMapService;

	@Autowired
	private FSimpleCouponService fSimpleCouponService;

	@Autowired
	private ContractParitiesService contractParitiesService;

	@Autowired
	private MessagePromptService messagePromptService;
	/**
	 * 汇率类型
	 */
	private final static int PARITIESTYPE = 1;

	/**
	 * 方案配置类型
	 */
	private final static int CONFIGTYPE = 7;

	/**
	 * 资金明细业务类型
	 */
	private final static int MONEYDETAILTYPE = 5;

	/**
	 * 
	 * @param big
	 *            BigDecimal
	 * @return String
	 */
	public String moneyToStrObject(BigDecimal big) {
		if (big == null) {
			return "";
		}
		NumberFormat numberFormat = NumberFormat.getNumberInstance();
		return numberFormat.format(big);
	}

	/**
	 * 
	 * @param big
	 *            BigDecimal
	 * @return String
	 */
	public String moneyToStrObject(Integer big) {
		if (big == null) {
			return "";
		}
		NumberFormat numberFormat = NumberFormat.getNumberInstance();
		return numberFormat.format(big);
	}

	@RequestMapping(value = "/pay")
	public String pay(HttpServletResponse response,ModelMap modelMap, BigDecimal inputTraderBond, Integer inputTranLever, HttpServletRequest request,
			RedirectAttributes attr) {

		Object object = request.getSession().getAttribute(com.tzdr.web.constants.Constants.TZDR_USER_SESSION);
		UserSessionBean userSessionBean = (UserSessionBean) object;

		if (inputTraderBond == null) { // 判断保证金
			inputTraderBond = new BigDecimal("0");
		}

		if (inputTranLever == null) { // 判断手数
			inputTranLever = 0;
		}

		inputTraderBond = inputTraderBond.abs(); // 绝对值

		inputTranLever = Math.abs(inputTranLever); // 绝对值

		FSimpleConfig fSimpleConfig = fSimpleConfigService.getFSimpleConfig(CONFIGTYPE, String.valueOf(inputTranLever)); // 获取配置方案信息

		// 系统保证金金额
		BigDecimal traderBond = fSimpleConfig != null ? fSimpleConfig.getTraderBond() : new BigDecimal("0");

		// 系统手数
		Integer tranLever = fSimpleConfig != null ? Integer.valueOf(fSimpleConfig.getTranLever()) : Integer.valueOf(0);

		// 系统操盘金额
		BigDecimal traderMoney = fSimpleConfig != null ? fSimpleConfig.getTraderMoney() : new BigDecimal("0");

		// 系统亏损警告线
		BigDecimal LineLoss = fSimpleConfig != null ? fSimpleConfig.getLineLoss() : new BigDecimal("0");

		// 系统管理费
		BigDecimal feeManage = fSimpleConfig != null ? fSimpleConfig.getFeeManage() : new BigDecimal("0");

		// 系统交易手续费
		BigDecimal tranFees = fSimpleConfig != null ? fSimpleConfig.getTranFees() : new BigDecimal("0");

		// 总操盘保证金=操盘保证金
		BigDecimal inputTotalTraderBond = new BigDecimal("0").add(traderBond);

		// 总操盘保证金=保证金金额
		BigDecimal traderTotal = new BigDecimal("0").add(traderMoney);

		// 亏损警告线
		BigDecimal lossLine = new BigDecimal("0").add(LineLoss);

		// 单管理费
		BigDecimal manageAmount = new BigDecimal("0").add(feeManage);

		// 总管理费
		BigDecimal totalManageAmount = new BigDecimal("0").add(manageAmount);

		// 应付金额
		BigDecimal payable = new BigDecimal("0").add(inputTotalTraderBond).abs().add(totalManageAmount);

		// 开仓手数
		modelMap.addAttribute("inputTranLever", tranLever);

		// 总操盘金
		modelMap.addAttribute("traderTotal", this.moneyToStrObject(traderTotal));

		// 操盘保证金
		modelMap.addAttribute("inputTraderBond", traderBond);

		// 总操盘保证金
		modelMap.addAttribute("traderBond", this.moneyToStrObject(inputTotalTraderBond));

		// 亏损平仓线
		modelMap.addAttribute("lossLine", this.moneyToStrObject(lossLine));

		// 管理费
		modelMap.addAttribute("inputManageAmount", this.moneyToStrObject(manageAmount));

		// 总管理费
		modelMap.addAttribute("totalManageAmount", this.moneyToStrObject(totalManageAmount));

		// 交易费
		modelMap.addAttribute("inputTranFees", this.moneyToStrObject(tranFees));

		// 应付金额
		modelMap.addAttribute("payable", payable);

		modelMap.addAttribute("showAvl", 0);

		String userUid = userSessionBean.getId();
		WUser wuser = wUserService.get(userUid);

		// 用户余额
		Double avlBal = wuser.getAvlBal();
		modelMap.addAttribute("avlBal", avlBal);

		// 代金券
		List<Map<String, Object>> voucher = this.fSimpleCouponService.queryCouponByUserId(userUid, 2, 7);
		modelMap.put("voucher", voucher);

		// 用户余额+最大代金券是否充足
		BigDecimal voucherMoney = new BigDecimal(0);
		if (null != voucher && !voucher.isEmpty()) {
			voucherMoney = voucherMoney.add(new BigDecimal(voucher.get(0).get("money").toString()));
		}
		if (payable.compareTo(new BigDecimal(avlBal).add(voucherMoney)) > 0) {
			modelMap.addAttribute("avlBal_user", this.moneyToStrObject(new BigDecimal(avlBal)));
			modelMap.addAttribute("payable_avlBal_user",
					this.moneyToStrObject(TypeConvert.scale(payable.subtract(new BigDecimal(avlBal)), 2)));
			modelMap.addAttribute("showAvl", 1);
		}
		ContractParities newConfig = contractParitiesService.get("00007");
		modelMap.put("contract", newConfig.getContract());

		Object uuid = UUID.randomUUID();
		request.getSession(false).setAttribute("tokenTzdr", uuid);
		CookiesUtil.addCookie(response, "tokenTzdr", String.valueOf(uuid), 600);
		return ViewConstants.FSimpleHSIUserTradeJsp.HSI_PAY;
	}

	@RequestMapping(value = "/paySuccessful")
	public String paySuccessful(ModelMap modelMap, BigDecimal inputTraderBond, Integer inputTranLever, String tokenTzdr,
			String voucherId, HttpServletRequest request, HttpServletResponse response, RedirectAttributes attr)
			throws Exception {

		if (inputTraderBond == null || inputTranLever == null) {
			this.pay(response,modelMap, inputTraderBond, inputTranLever, request, attr);
			return ViewConstants.FSimpleHSIUserTradeJsp.HSI_PAY;
		}

		FSimpleConfig fSimpleConfig = fSimpleConfigService.getFSimpleConfig(CONFIGTYPE, String.valueOf(inputTranLever)); // 获取配置方案信息

		if (fSimpleConfig == null) {
			this.pay(response,modelMap, inputTraderBond, inputTranLever, request, attr);
			return ViewConstants.FSimpleHSIUserTradeJsp.HSI_PAY;
		}

		if (fSimpleConfig.getTraderBond().compareTo(inputTraderBond) != 0) { // 判断当前配置方案单手保证金是正确
			this.pay(response,modelMap, inputTraderBond, inputTranLever, request, attr);
			return ViewConstants.FSimpleHSIUserTradeJsp.HSI_PAY;
		}

		inputTraderBond = inputTraderBond.abs(); // 绝对值

		inputTranLever = Math.abs(inputTranLever); // 绝对值

		Object object = request.getSession().getAttribute(com.tzdr.web.constants.Constants.TZDR_USER_SESSION);
		String uid = "";
		Object tokenTzdrObj = request.getSession(false).getAttribute("tokenTzdr");
		if (object != null && tokenTzdrObj != null && String.valueOf(tokenTzdrObj).equals(tokenTzdr)) {
			UserSessionBean userSessionBean = (UserSessionBean) object;
			uid = userSessionBean.getId();
		} else {
			this.pay(response,modelMap, inputTraderBond, inputTranLever, request, attr);
			return ViewConstants.FSimpleHSIUserTradeJsp.HSI_PAY;
		}

		if (uid == null) {
			this.pay(response,modelMap, inputTraderBond, inputTranLever, request, attr);
			return ViewConstants.FSimpleHSIUserTradeJsp.HSI_PAY;
		}

		// 获取用户信息
		WUser wuser = wUserService.get(uid);

		// 总操盘保证金=操盘保证金
		BigDecimal inputTotalTraderBond = new BigDecimal("0").add(inputTraderBond);

		// 总操盘保证金=保证金金额
		BigDecimal traderTotal = new BigDecimal("0").add(fSimpleConfig.getTraderMoney());

		// 亏损警告线
		BigDecimal lossLine = new BigDecimal("0").add(fSimpleConfig.getLineLoss());

		// 单管理费
		BigDecimal manageAmount = new BigDecimal("0").add(fSimpleConfig.getFeeManage());

		// 总管理费
		BigDecimal totalManageAmount = new BigDecimal("0").add(manageAmount);

		// 应付金额
		BigDecimal payable = new BigDecimal("0").add(inputTotalTraderBond).abs().add(totalManageAmount);

		// 交易费
		BigDecimal totalTranFees = new BigDecimal("0").add(fSimpleConfig.getTranFees());

		if (wuser != null && wuser.getMobile() != null) {
			BigDecimal avlBal = new BigDecimal(wuser.getAvlBal().toString());
			// 验证代金券
			FSimpleCoupon voucher = this.fSimpleCouponService.get(voucherId);
			BigDecimal voucherActualMoney = null; // 代金券使用金额
			if (this.fSimpleCouponService.isCouponValid(voucher, 2, 7)) {
				voucherActualMoney = new BigDecimal(voucher.getMoney() + "");
				payable = payable.subtract(voucherActualMoney);
				if (payable.compareTo(BigDecimal.ZERO) < 0) {
					voucherActualMoney = voucherActualMoney.add(payable);
					payable = BigDecimal.ZERO;
				}
				inputTotalTraderBond = inputTotalTraderBond.subtract(voucherActualMoney);
				if (inputTotalTraderBond.compareTo(BigDecimal.ZERO) < 0) {
					inputTotalTraderBond = BigDecimal.ZERO;
				}
			}
			if (avlBal.compareTo(payable) >= 0) {
				FSimpleFtseUserTrade st = new FSimpleFtseUserTrade();
				st.setUid(uid);
				st.setTraderTotal(traderTotal);
				st.setTraderBond(inputTotalTraderBond);
				st.setTranLever(inputTranLever);
				st.setLineLoss(lossLine);
				st.setFeeManage(totalManageAmount);
				st.setTranFees(totalTranFees);
				// 审核中
				st.setStateType(1);
				st.setBusinessType(7); // 恒生指数
				// 入金金额(美元)
				BigDecimal goldenMoney = new BigDecimal("0").add(fSimpleConfig.getGoldenMoney());
				st.setGoldenMoney(goldenMoney);
				// 设置代金券相关信息
				FSimpleFtseUserTrade fSimpleFtseUserTrade = null; 
				if (this.fSimpleCouponService.isCouponValid(voucher, 2, 7)) {
					st.setVoucherId(voucher.getId());
					st.setVoucherMoney(voucher.getMoney());
					st.setVoucherActualMoney(voucherActualMoney);
					fSimpleFtseUserTrade = this.fSimpleFtseUserTradeService.executePayable(st, voucher, wuser.getMobile(), payable,
							"投资香港恒生指数期货申请（划款）。", MONEYDETAILTYPE);
				} else {
					fSimpleFtseUserTrade = this.fSimpleFtseUserTradeService.executePayable(st, wuser.getMobile(), payable, "投资香港恒生指数期货申请（划款）。",
							MONEYDETAILTYPE);
					
				}
				request.getSession(false).removeAttribute("tokenTzdr");
				// TODO 申请操盘，支付成功给工作人员发送Email
				try {

					if (wuser != null) {
						messagePromptService.sendMessage(PromptTypes.isFutures, wuser.getMobile());
					}

				} catch (Exception e) {
					log.info("发送邮件失败", e);
				}
				if(fSimpleFtseUserTrade != null){
					modelMap.addAttribute("stateType", fSimpleFtseUserTrade.getStateType());
				}
				return ViewConstants.FSimpleHSIUserTradeJsp.HSI_PAY_SUCCESSFUL;
			} else {
				if (payable.compareTo(avlBal) > 0) {
					modelMap.addAttribute("avlBal_user", this.moneyToStrObject(avlBal));
					modelMap.addAttribute("payable_avlBal_user",
							this.moneyToStrObject(TypeConvert.scale(payable.subtract(avlBal), 2)));
					modelMap.addAttribute("showAvl", 1);
				}
			}
		}
		this.pay(response,modelMap, inputTraderBond, inputTranLever, request, attr);
		return ViewConstants.FSimpleHSIUserTradeJsp.HSI_PAY;
	}

	/**
	 * 获取当前汇率
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/getparities")
	@ResponseBody
	public JsonResult getParities(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) {
		JsonResult jsonResult = new JsonResult(true);
		Map<Object, Object> data = new HashMap<Object, Object>();
		FSimpleParities fSimpleParities = fSimpleParitiesService.getFSimpleParities(PARITIESTYPE);
		data.put("parities", fSimpleParities.getParities());
		jsonResult.setData(data);
		return jsonResult;
	}

	/**
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/apply_end_trade", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult applyEndTrade(ModelMap modelMap, String id, HttpServletRequest request,
			HttpServletResponse response) {

		JsonResult jsonResult = new JsonResult(true);

		if (StringUtil.isBlank(id)) {
			jsonResult.setMessage("notFindData");
			return jsonResult;
		}

		UserSessionBean userSessionBean = (UserSessionBean) request.getSession()
				.getAttribute(Constants.TZDR_USER_SESSION);

		FSimpleFtseUserTrade fSimpleFtseUserTrade = fSimpleFtseUserTradeService.get(id);

		if (fSimpleFtseUserTrade == null || !userSessionBean.getId().equals(fSimpleFtseUserTrade.getUid())) {
			jsonResult.setMessage("notFindData");
			return jsonResult;
		}

		if (fSimpleFtseUserTrade.getStateType() == 2) { // 不能重复申请
			jsonResult.setMessage("notRepetitionApply");
			return jsonResult;
		}

		if (fSimpleFtseUserTrade.getStateType() != 4) { // 判断是不是操盘中
			jsonResult.setMessage("applyEndTradeFail");
			return jsonResult;
		}

		FSimpleParities fSimpleParities = fSimpleParitiesService.getFSimpleParities(PARITIESTYPE);

		fSimpleFtseUserTrade.setEndParities(fSimpleParities.getParities()); // 申请终结当前汇率
		fSimpleFtseUserTrade.setStateType(2); // 申请终结方案
		fSimpleFtseUserTrade.setAppEndTime(Dates.getCurrentLongDate()); // 申请终结时间

		fSimpleFtseUserTradeService.update(fSimpleFtseUserTrade);
		WUser wuser = wUserService.get(userSessionBean.getId()); // 获取用户信息
		// TODO 终结方案，给工作人员发送Email
		try {
			messagePromptService.sendMessage(PromptTypes.isEndScheme, wuser.getMobile());
		} catch (Exception e) {
			log.info("发送邮件失败", e);
		}
		return jsonResult;
	}

	@RequestMapping(value = "/trade_list")
	public String tradeList(ModelMap modelMap, String index, HttpServletRequest request, HttpServletResponse response) {
		modelMap.addAttribute("index", index);
		return ViewConstants.FSimpleHSIUserTradeJsp.HSI_TRADE_LIST;
	}

	/**
	 * 
	 * @param response
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/findData")
	@ResponseBody
	public PageInfo<FSimpleFtseUserTradeWebVo> findData(HttpServletResponse response, HttpServletRequest request) {
		UserSessionBean userSessionBean = (UserSessionBean) request.getSession()
				.getAttribute(Constants.TZDR_USER_SESSION);
		if (userSessionBean == null) {
			return null;
		}
		String pageIndex = request.getParameter("pageIndex");
		String perPage = request.getParameter("perPage");
		String type = request.getParameter("type");
		PageInfo<FSimpleFtseUserTradeWebVo> pageInfo = this.fSimpleFtseUserTradeService.findDataList(pageIndex, perPage,
				type, userSessionBean.getId());
		List<FSimpleFtseUserTradeWebVo> pageResults = pageInfo == null ? null : pageInfo.getPageResults();
		if (pageInfo != null && pageInfo.getPageResults() != null && !pageInfo.getPageResults().isEmpty()) {
			for (FSimpleFtseUserTradeWebVo fSimpleFtseUserTradeWebVo : pageResults) {
				if (fSimpleFtseUserTradeWebVo.getStateType() == 1 || fSimpleFtseUserTradeWebVo.getStateType() == 5) {
					fSimpleFtseUserTradeWebVo.setUseTradeDay(0);
				} else {
					fSimpleFtseUserTradeWebVo.setUseTradeDay(
							fSimpleFtseUserTradeWebVo.getStateType() == 6 ? fSimpleFtseUserTradeWebVo.getUseTradeDay()
									: tradeDayService.getTradeDayNum(fSimpleFtseUserTradeWebVo.getAppStarttime(), 14));
				}
			}
			pageInfo.setPageResults(pageResults);
		}
		return pageInfo;
	}

	/**
	 * 获取需要追加保证金信息
	 * 
	 * @param id
	 *            方案编号
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getAppendMoneyInfo", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult getAppendMoneyInfo(String id, ModelMap modelMap, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		JsonResult jsonResult = new JsonResult(true);

		UserSessionBean userSessionBean = (UserSessionBean) request.getSession()
				.getAttribute(Constants.TZDR_USER_SESSION);

		WUser wuser = wUserService.get(userSessionBean.getId()); // 获取用户信息

		FSimpleFtseUserTrade fSimpleUserTrade = fSimpleFtseUserTradeService.get(id);

		if (fSimpleUserTrade == null) { // 未找到该方案
			jsonResult.setMessage("notFindData");
			return jsonResult;
		}

		Map<Object, Object> data = new HashMap<Object, Object>();
		data.put("avlBal", wuser.getAvlBal()); // 当前余额
		data.put("traderTotal", fSimpleUserTrade.getTraderTotal()); // 总操盘金额
		String rate = dataMapService.findByTypeKey("exchangeRate").get(0).getValueKey();
		data.put("exchangeRate", rate); // 当前固定汇率
		jsonResult.setData(data);
		return jsonResult;
	}

	/**
	 * 追加保证金
	 * 
	 * @param id
	 *            方案号TG+ID号
	 * @param appendMoney
	 *            追加保证金额
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/appendMoney", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult addAppendMoney(String id, Double appendMoney, String rate, Double dollar, ModelMap modelMap,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		JsonResult jsonResult = new JsonResult(true);

		BigDecimal payMoney = new BigDecimal(appendMoney); // 追加保证金

		BigDecimal defaultMinAppendMoney = new BigDecimal(2000.00); // 默认最小追加保证金2000

		if (payMoney.compareTo(defaultMinAppendMoney) < 0) { // 追加金额是否低于默认最小追加保证金
			jsonResult.setMessage("underDefaultMinAppendMoney");
			return jsonResult;
		}

		UserSessionBean userSessionBean = (UserSessionBean) request.getSession()
				.getAttribute(Constants.TZDR_USER_SESSION);

		WUser wuser = wUserService.get(userSessionBean.getId()); // 获取用户信息

		BigDecimal avlBal = new BigDecimal(wuser.getAvlBal().toString()); // 获取用户余额

		if (avlBal.compareTo(payMoney) < 0) { // 判断追加保证金是否大于用户余额
			jsonResult.setMessage("insufficientBalance");
			return jsonResult;
		}

		FSimpleFtseUserTrade fSimpleFtseUserTrade = fSimpleFtseUserTradeService.get(id);

		if (fSimpleFtseUserTrade == null) { // 未找到该方案
			jsonResult.setMessage("notFindData");
			return jsonResult;
		} else if (fSimpleFtseUserTrade.getStateType() == 6) { // 已完结
			jsonResult.setMessage("isOver");
			return jsonResult;
		}

		// 追加保证金
		fSimpleFtseUserTradeService.addAppendTraderBond(fSimpleFtseUserTrade, appendMoney, rate, dollar, wuser);
		// TODO 追加保证金，给工作人员发送Email
		try {
			messagePromptService.sendMessage(PromptTypes.isAddBond, wuser.getMobile());
		} catch (Exception e) {
			log.info("发送邮件失败", e);
		}
		return jsonResult;
	}
}

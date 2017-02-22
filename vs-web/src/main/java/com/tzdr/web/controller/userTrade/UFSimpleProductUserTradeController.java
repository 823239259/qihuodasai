package com.tzdr.web.controller.userTrade;

import java.math.BigDecimal;
import java.math.MathContext;
import java.text.NumberFormat;
import java.util.ArrayList;
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

import com.hundsun.t2sdk.common.util.UUID;
import com.tzdr.business.cms.service.messagePrompt.MessagePromptService;
import com.tzdr.business.cms.service.messagePrompt.PromptTypes;
import com.tzdr.business.service.contract.ContractParitiesService;
import com.tzdr.business.service.future.FSimpleCouponService;
import com.tzdr.business.service.tradecalendar.TradeDayService;
import com.tzdr.business.service.userTrade.FSimpleProductConfigService;
import com.tzdr.business.service.userTrade.FSimpleProductUserTradeService;
import com.tzdr.business.service.wuser.WUserService;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.utils.Dates;
import com.tzdr.common.utils.TypeConvert;
import com.tzdr.common.web.support.JsonResult;
import com.tzdr.domain.vo.FSimpleProductUserTradeWebVo;
import com.tzdr.domain.web.entity.ContractParities;
import com.tzdr.domain.web.entity.FSimpleConfig;
import com.tzdr.domain.web.entity.FSimpleFtseUserTrade;
import com.tzdr.domain.web.entity.WUser;
import com.tzdr.domain.web.entity.future.FSimpleCoupon;
import com.tzdr.web.constants.Constants;
import com.tzdr.web.constants.ViewConstants;
import com.tzdr.web.utils.UserSessionBean;

/**
 * <B>璇存槑: </B>鍟嗗搧鏈熻揣
 * 
 * @author LiuYang
 *
 *         2015骞�鏈�7鏃�涓嬪崍6:40:08
 */
@Controller
@RequestMapping("/userproduct")
public class UFSimpleProductUserTradeController {
	private static Logger log = LoggerFactory.getLogger(UFSimpleProductUserTradeController.class);

	@Autowired
	private FSimpleProductConfigService fSimpleProductConfigService;

	@Autowired
	private FSimpleProductUserTradeService fSimpleProductUserTradeService;

	@Autowired
	private WUserService wUserService;
	@Autowired
	private TradeDayService tradeDayService;

	@Autowired
	private FSimpleCouponService fSimpleCouponService;

	@Autowired
	private ContractParitiesService contractParitiesService;

	@Autowired
	private MessagePromptService messagePromptService;

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

	/**
	 * 鏀粯椤�
	 * 
	 * @param modelMap
	 * @param inputTraderBond
	 * @param inputTranLever
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/pay")
	public String pay(ModelMap modelMap, BigDecimal inputTraderBond, Integer inputTranLever, BigDecimal inputTranFees,
			int type, HttpServletRequest request) {
		Map<Integer, String> payView = new HashMap<Integer, String>();
		payView.put(1, ViewConstants.FSimpleProductUserTradeViewJsp.GOLD_PAY);
		payView.put(2, ViewConstants.FSimpleProductUserTradeViewJsp.SLIVER_PAY);
		payView.put(3, ViewConstants.FSimpleProductUserTradeViewJsp.COPPER_PAY);
		payView.put(4, ViewConstants.FSimpleProductUserTradeViewJsp.RUBBER_PAY);
		Object object = request.getSession().getAttribute(com.tzdr.web.constants.Constants.TZDR_USER_SESSION);
		UserSessionBean userSessionBean = (UserSessionBean) object;

		ContractParities newConfig = new ContractParities();
		switch (type) {
		case 1:
			newConfig = contractParitiesService.get("00002");
			break;
		case 2:
			newConfig = contractParitiesService.get("00003");
			break;
		case 3:
			newConfig = contractParitiesService.get("00004");
			break;
		case 4:
			newConfig = contractParitiesService.get("00005");
			break;
		default:
			break;
		}
		modelMap.put("contract", newConfig.getContract());

		if (inputTraderBond == null) { // 鍒ゆ柇淇濊瘉閲�
			inputTraderBond = new BigDecimal("0");
		}

		if (inputTranLever == null) { // 鍒ゆ柇鎵嬫暟
			inputTranLever = 0;
		}

		if (inputTranFees == null) { // 鍒ゆ柇鎵嬫暟
			inputTranFees = new BigDecimal("0");
		}

		inputTranFees = inputTranFees.abs(); // 缁濆鍊�

		inputTraderBond = inputTraderBond.abs(); // 缁濆鍊�

		inputTranLever = Math.abs(inputTranLever); // 缁濆鍊�

		FSimpleConfig fSimpleConfig = fSimpleProductConfigService.getFSimpleConfigByType(type); // 鑾峰彇閰嶇疆鏂规淇℃伅

		// 绯荤粺鍗曟墜鎿嶇洏閲戦
		BigDecimal traderMoney = fSimpleConfig != null ? fSimpleConfig.getTraderMoney() : new BigDecimal("0");

		// 绯荤粺鍗曟墜浜忔崯璀﹀憡绾�
		BigDecimal LineLoss = fSimpleConfig != null ? fSimpleConfig.getLineLoss() : new BigDecimal("0");

		// 绯荤粺鍗曟墜绠＄悊璐�
		BigDecimal feeManage = fSimpleConfig != null ? fSimpleConfig.getFeeManage() : new BigDecimal("0");

		// 鎬绘搷鐩樹繚璇侀噾=鎿嶇洏淇濊瘉閲�鎵嬫暟
		BigDecimal inputTotalTraderBond = new BigDecimal("0").add(inputTraderBond)
				.multiply(new BigDecimal(inputTranLever), MathContext.DECIMAL32);

		// 鎬绘搷鐩樹繚璇侀噾=鍗曟墜淇濊瘉閲戦噾棰�鎵嬫暟
		BigDecimal traderTotal = new BigDecimal("0").add(traderMoney).multiply(new BigDecimal(inputTranLever),
				MathContext.DECIMAL32);

		// 浜忔崯璀﹀憡绾�
		BigDecimal lossLine = new BigDecimal("0").add(traderTotal.subtract(inputTotalTraderBond)
				.add(LineLoss.multiply(new BigDecimal(inputTranLever), MathContext.DECIMAL32)));
		// 鍗曠鐞嗚垂
		BigDecimal manageAmount = new BigDecimal("0").add(feeManage);

		// 鎬荤鐞嗚垂
		BigDecimal totalManageAmount = new BigDecimal("0").add(manageAmount);

		// 搴斾粯閲戦
		BigDecimal payable = new BigDecimal("0").add(inputTotalTraderBond).abs().add(totalManageAmount);

		// 寮�粨鎵嬫暟
		modelMap.addAttribute("inputTranLever", inputTranLever);

		// 鎬绘搷鐩橀噾
		modelMap.addAttribute("traderTotal", this.moneyToStrObject(traderTotal));

		// 鎿嶇洏淇濊瘉閲�
		modelMap.addAttribute("inputTraderBond", inputTraderBond);

		// 鎬绘搷鐩樹繚璇侀噾
		modelMap.addAttribute("traderBond", this.moneyToStrObject(inputTotalTraderBond));

		// 浜忔崯骞充粨绾�
		modelMap.addAttribute("lossLine", this.moneyToStrObject(lossLine));

		// 绠＄悊璐�
		modelMap.addAttribute("inputManageAmount", this.moneyToStrObject(manageAmount));

		// 鎬荤鐞嗚垂
		modelMap.addAttribute("totalManageAmount", this.moneyToStrObject(totalManageAmount));

		// 浜ゆ槗璐�
		modelMap.addAttribute("inputTranFees", this.moneyToStrObject(inputTranFees));

		// 搴斾粯閲戦
		modelMap.addAttribute("payable", payable);

		modelMap.addAttribute("showAvl", 0);

		String userUid = userSessionBean.getId();
		WUser wuser = wUserService.get(userUid);

		// 鐢ㄦ埛浣欓
		Double avlBal = wuser.getAvlBal();
		modelMap.addAttribute("avlBal", avlBal);

		// 浠ｉ噾鍒�
		List<Map<String, Object>> voucher = this.fSimpleCouponService.queryCouponByUserId(userUid, 2, 5);
		modelMap.put("voucher", voucher);

		// 鐢ㄦ埛浣欓+鏈�ぇ浠ｉ噾鍒告槸鍚﹀厖瓒�
		BigDecimal voucherMoney = new BigDecimal(0);
		if (null != voucher && !voucher.isEmpty()) {
			voucherMoney = voucherMoney.add(new BigDecimal(voucher.get(0).get("money").toString()));
		}

		// 鐢ㄦ埛浣欓
		if (payable.compareTo(new BigDecimal(avlBal).add(voucherMoney)) > 0) {
			modelMap.addAttribute("avlBal_user", this.moneyToStrObject(new BigDecimal(avlBal)));
			modelMap.addAttribute("payable_avlBal_user",
					this.moneyToStrObject(TypeConvert.scale(payable.subtract(new BigDecimal(avlBal)), 2)));
			modelMap.addAttribute("showAvl", 1);
		}
		request.getSession(false).setAttribute("tokenTzdr", UUID.randomUUID());
		return payView.get(type);
	}

	/**
	 * 鏀粯鎴愬姛椤�
	 * 
	 * @param modelMap
	 * @param inputTraderBond
	 * @param inputTranLever
	 * @param inputTranFees
	 * @param type
	 * @param tokenTzdr
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/paySuccessful")
	public String paySuccessful(ModelMap modelMap, BigDecimal inputTraderBond, Integer inputTranLever,
			BigDecimal inputTranFees, int type, String tokenTzdr, String voucherId, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<Integer, String> payView = new HashMap<Integer, String>();
		payView.put(1, ViewConstants.FSimpleProductUserTradeViewJsp.GOLD_PAY);
		payView.put(2, ViewConstants.FSimpleProductUserTradeViewJsp.SLIVER_PAY);
		payView.put(3, ViewConstants.FSimpleProductUserTradeViewJsp.COPPER_PAY);
		payView.put(4, ViewConstants.FSimpleProductUserTradeViewJsp.RUBBER_PAY);
		if (inputTraderBond == null || inputTranLever == null) {
			this.pay(modelMap, inputTraderBond, inputTranLever, inputTranFees, type, request);
			return payView.get(type);
		}

		FSimpleConfig fSimpleConfig = fSimpleProductConfigService.getFSimpleConfigByType(type); // 鑾峰彇閰嶇疆鏂规淇℃伅

		if (fSimpleConfig == null) {
			this.pay(modelMap, inputTraderBond, inputTranLever, inputTranFees, type, request);
			return payView.get(type);
		}

		if (fSimpleConfig.getTraderBond().compareTo(inputTraderBond) != 0) { // 鍒ゆ柇褰撳墠閰嶇疆鏂规鍗曟墜淇濊瘉閲戞槸姝ｇ‘
			this.pay(modelMap, inputTraderBond, inputTranLever, inputTranFees, type, request);
			return payView.get(type);
		}

		String[] tranLeverStrs = null; // 鐜版湁寮�粨鎵嬫暟

		if (fSimpleConfig != null && StringUtil.isNotBlank(fSimpleConfig.getTranLever())) {
			tranLeverStrs = fSimpleConfig.getTranLever().split(",");
		}

		boolean isTranLeverTrue = false; // 褰撳墠寮�粨鎵嬫暟鏄惁姝ｇ‘

		if (tranLeverStrs != null && tranLeverStrs.length > 0) {
			for (String tranLever : tranLeverStrs) {
				if (Integer.parseInt(tranLever) == inputTranLever) { // 褰撳墠寮�粨鎵嬫暟鏄惁瀛樺湪
					isTranLeverTrue = true;
					break;
				}
			}
		}

		if (!isTranLeverTrue) { // 鍒ゆ柇褰撳墠寮�粨鎵嬫暟鏄惁姝ｇ‘
			this.pay(modelMap, inputTraderBond, inputTranLever, inputTranFees, type, request);
			return payView.get(type);
		}

		String[] tranFeesStrs = null; // 鐜版湁鎵嬬画璐归厤缃�

		if (fSimpleConfig != null && StringUtil.isNotBlank(fSimpleConfig.getTranLever())) {
			tranFeesStrs = fSimpleConfig.getTranFeesArray().split(",");
		}

		boolean isTranFeesTrue = false; // 褰撳墠鎵嬬画璐规槸鍚︽纭�

		if (tranFeesStrs != null && tranFeesStrs.length > 0) {
			for (String tranFees : tranFeesStrs) {
				if (new BigDecimal(tranFees).equals(inputTranFees)) { // 褰撳墠寮�粨鎵嬫暟鏄惁瀛樺湪
					isTranFeesTrue = true;
					break;
				}
			}
		}

		if (!isTranFeesTrue) { // 鍒ゆ柇褰撳墠鎵嬬画璐规槸鍚︽纭�
			this.pay(modelMap, inputTraderBond, inputTranLever, inputTranFees, type, request);
			return payView.get(type);
		}

		inputTranFees = inputTranFees.abs(); // 缁濆鍊�

		inputTraderBond = inputTraderBond.abs(); // 缁濆鍊�

		inputTranLever = Math.abs(inputTranLever); // 缁濆鍊�

		Object object = request.getSession().getAttribute(com.tzdr.web.constants.Constants.TZDR_USER_SESSION);
		String uid = "";
		Object tokenTzdrObj = request.getSession(false).getAttribute("tokenTzdr");
		if (object != null && tokenTzdrObj != null && String.valueOf(tokenTzdrObj).equals(tokenTzdr)) {
			UserSessionBean userSessionBean = (UserSessionBean) object;
			uid = userSessionBean.getId();
		} else {
			this.pay(modelMap, inputTraderBond, inputTranLever, inputTranFees, type, request);
			return payView.get(type);
		}

		if (uid == null) {
			this.pay(modelMap, inputTraderBond, inputTranLever, inputTranFees, type, request);
			return payView.get(type);
		}

		// 鑾峰彇鐢ㄦ埛淇℃伅
		WUser wuser = wUserService.get(uid);

		// 鎬绘搷鐩樹繚璇侀噾=鎿嶇洏淇濊瘉閲�鎵嬫暟
		BigDecimal inputTotalTraderBond = new BigDecimal("0").add(inputTraderBond)
				.multiply(new BigDecimal(inputTranLever), MathContext.DECIMAL32);

		// 鎬绘搷鐩樹繚璇侀噾=鍗曟墜淇濊瘉閲戦噾棰�鎵嬫暟
		BigDecimal traderTotal = new BigDecimal("0").add(fSimpleConfig.getTraderMoney())
				.multiply(new BigDecimal(inputTranLever), MathContext.DECIMAL32);

		// 浜忔崯璀﹀憡绾�
		BigDecimal lossLine = new BigDecimal("0").add(traderTotal.subtract(inputTotalTraderBond)
				.add(fSimpleConfig.getLineLoss().multiply(new BigDecimal(inputTranLever), MathContext.DECIMAL32)));
		// 鍗曠鐞嗚垂
		BigDecimal manageAmount = new BigDecimal("0").add(fSimpleConfig.getFeeManage());

		// 鎬荤鐞嗚垂
		BigDecimal totalManageAmount = new BigDecimal("0").add(manageAmount);

		// 搴斾粯閲戦
		BigDecimal payable = new BigDecimal("0").add(inputTotalTraderBond).abs().add(totalManageAmount);

		// 浜ゆ槗璐�
		BigDecimal totalTranFees = new BigDecimal("0").add(inputTranFees);

		if (wuser != null && wuser.getMobile() != null) {
			BigDecimal avlBal = new BigDecimal(wuser.getAvlBal().toString());
			// 楠岃瘉浠ｉ噾鍒�
			FSimpleCoupon voucher = this.fSimpleCouponService.get(voucherId);
			BigDecimal voucherActualMoney = null; // 浠ｉ噾鍒镐娇鐢ㄩ噾棰�
			if (this.fSimpleCouponService.isCouponValid(voucher, 2, 5)) {
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
				// 瀹℃牳涓�
				st.setStateType(1);
				st.setBusinessType(type);
				// 璁剧疆浠ｉ噾鍒哥浉鍏充俊鎭�
				if (this.fSimpleCouponService.isCouponValid(voucher, 2, 5)) {
					st.setVoucherId(voucher.getId());
					st.setVoucherMoney(voucher.getMoney());
					st.setVoucherActualMoney(voucherActualMoney);
					this.fSimpleProductUserTradeService.executePayable(st, voucher, wuser.getMobile(), payable);
				} else {
					this.fSimpleProductUserTradeService.executePayable(st, wuser.getMobile(), payable);
				}
				request.getSession(false).removeAttribute("tokenTzdr");
				// TODO 鐢宠鎿嶇洏锛屾敮浠樻垚鍔熺粰宸ヤ綔浜哄憳鍙戦�Email
				try {

					if (wuser != null) {
						messagePromptService.sendMessage(PromptTypes.isFutures, wuser.getMobile());
					}

				} catch (Exception e) {
					log.info("鍙戦�閭欢澶辫触", e);
				}
				return ViewConstants.FSimpleProductUserTradeViewJsp.PAY_SUCCESSFUL;
			} else {
				if (payable.compareTo(avlBal) > 0) {
					modelMap.addAttribute("avlBal_user", this.moneyToStrObject(avlBal));
					modelMap.addAttribute("payable_avlBal_user",
							this.moneyToStrObject(TypeConvert.scale(payable.subtract(avlBal), 2)));
					modelMap.addAttribute("showAvl", 1);
				}
			}
		}
		this.pay(modelMap, inputTraderBond, inputTranLever, inputTranFees, type, request);
		return payView.get(type);
	}

	/**
	 * 璺宠浆鏂规鍒楄〃椤甸潰
	 * 
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/trade_list")
	public String tradeList(ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) {
		return ViewConstants.FSimpleProductUserTradeViewJsp.TRADE_LIST;
	}

	/**
	 * 鑾峰彇鎶樻墸鍒�
	 * 
	 * @param modelMap
	 * @param businessType
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/getCoupon")
	@ResponseBody
	public JsonResult getCoupon(ModelMap modelMap, Integer businessType, HttpServletRequest request,
			HttpServletResponse response) {
		JsonResult jsonResult = new JsonResult(true);
		Map<Object, Object> data = new HashMap<Object, Object>();
		// 鎶樻墸鍒�
		List<Map<String, Object>> discount = new ArrayList<>();
		Object userSession = request.getSession().getAttribute(Constants.TZDR_USER_SESSION);
		if (null != userSession) {
			UserSessionBean userSessionBean = (UserSessionBean) userSession;
			discount = this.fSimpleCouponService.queryCouponByUserId(userSessionBean.getId(), 3, businessType);
		}
		data.put("discount", discount);
		jsonResult.setData(data);
		return jsonResult;
	}

	/**
	 * 鏌ヨ鏂规鏁版嵁
	 * 
	 * @param response
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/findData")
	@ResponseBody
	public PageInfo<FSimpleProductUserTradeWebVo> findData(HttpServletResponse response, HttpServletRequest request) {
		UserSessionBean userSessionBean = (UserSessionBean) request.getSession()
				.getAttribute(Constants.TZDR_USER_SESSION);
		if (userSessionBean == null) {
			return null;
		}
		String pageIndex = request.getParameter("pageIndex");
		String perPage = request.getParameter("perPage");
		PageInfo<FSimpleProductUserTradeWebVo> pageInfo = this.fSimpleProductUserTradeService.findDataList(pageIndex,
				perPage, userSessionBean.getId());
		List<FSimpleProductUserTradeWebVo> pageResults = pageInfo == null ? null : pageInfo.getPageResults();
		if (pageInfo != null && pageInfo.getPageResults() != null && !pageInfo.getPageResults().isEmpty()) {
			for (FSimpleProductUserTradeWebVo fSimpleProductUserTradeWebVo : pageResults) {
				if (fSimpleProductUserTradeWebVo.getStateType() == 1
						|| fSimpleProductUserTradeWebVo.getStateType() == 5) {
					fSimpleProductUserTradeWebVo.setUseTradeDay(0);
				} else {
					fSimpleProductUserTradeWebVo.setUseTradeDay(fSimpleProductUserTradeWebVo.getStateType() == 6
							? fSimpleProductUserTradeWebVo.getUseTradeDay()
							: tradeDayService.getTradeDayNum(fSimpleProductUserTradeWebVo.getAppStarttime(), 14));
				}
			}
			pageInfo.setPageResults(pageResults);
		}
		return pageInfo;
	}

	/**
	 * 鐢宠缁堢粨
	 * 
	 * @param modelMap
	 * @param id
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/apply_end_trade", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult applyEndTrade(ModelMap modelMap, String id, Integer businessType, String discountId,
			HttpServletRequest request, HttpServletResponse response) {

		JsonResult jsonResult = new JsonResult(true);

		if (StringUtil.isBlank(id)) {
			jsonResult.setMessage("notFindData");
			return jsonResult;
		}

		UserSessionBean userSessionBean = (UserSessionBean) request.getSession()
				.getAttribute(Constants.TZDR_USER_SESSION);

		FSimpleFtseUserTrade fSimpleFtseUserTrade = fSimpleProductUserTradeService.get(id);

		if (fSimpleFtseUserTrade == null || !userSessionBean.getId().equals(fSimpleFtseUserTrade.getUid())) {
			jsonResult.setMessage("notFindData");
			return jsonResult;
		}

		if (fSimpleFtseUserTrade.getStateType() == 2) { // 涓嶈兘閲嶅鐢宠
			jsonResult.setMessage("notRepetitionApply");
			return jsonResult;
		}

		if (fSimpleFtseUserTrade.getStateType() != 4) { // 鍒ゆ柇鏄笉鏄搷鐩樹腑
			jsonResult.setMessage("applyEndTradeFail");
			return jsonResult;
		}

		fSimpleFtseUserTrade.setStateType(2); // 鐢宠缁堢粨鏂规
		fSimpleFtseUserTrade.setAppEndTime(Dates.getCurrentLongDate()); // 鐢宠缁堢粨鏃堕棿

		// 楠岃瘉鎶樻墸鍒�
		FSimpleCoupon discount = this.fSimpleCouponService.get(discountId);
		if (this.fSimpleCouponService.isCouponValid(discount, 3, businessType)) {
			fSimpleFtseUserTrade.setDiscountId(discount.getId());
			fSimpleFtseUserTrade.setDiscountMoney(discount.getMoney());
			fSimpleProductUserTradeService.updateFSimpleFtseUserTradeAndFSimpleCoupon(fSimpleFtseUserTrade, discount);
		} else {
			fSimpleProductUserTradeService.update(fSimpleFtseUserTrade);
		}
		WUser wuser = wUserService.get(userSessionBean.getId()); // 鑾峰彇鐢ㄦ埛淇℃伅
		// TODO 缁堢粨鏂规锛岀粰宸ヤ綔浜哄憳鍙戦�Email
		try {
			messagePromptService.sendMessage(PromptTypes.isEndScheme, wuser.getMobile());
		} catch (Exception e) {
			log.info("鍙戦�閭欢澶辫触", e);
		}
		return jsonResult;
	}

	/**
	 * 鑾峰彇闇�杩藉姞淇濊瘉閲戜俊鎭�
	 * 
	 * @param id
	 *            鏂规缂栧彿
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

		WUser wuser = wUserService.get(userSessionBean.getId()); // 鑾峰彇鐢ㄦ埛淇℃伅

		FSimpleFtseUserTrade fSimpleUserTrade = fSimpleProductUserTradeService.get(id);

		if (fSimpleUserTrade == null) { // 鏈壘鍒拌鏂规
			jsonResult.setMessage("notFindData");
			return jsonResult;
		}

		Map<Object, Object> data = new HashMap<Object, Object>();
		data.put("avlBal", wuser.getAvlBal()); // 褰撳墠浣欓
		data.put("traderTotal", fSimpleUserTrade.getTraderTotal()); // 鎬绘搷鐩橀噾棰�

		jsonResult.setData(data);
		return jsonResult;
	}

	/**
	 * 杩藉姞淇濊瘉閲�
	 * 
	 * @param id
	 *            鏂规鍙稵G+ID鍙�
	 * @param appendMoney
	 *            杩藉姞淇濊瘉閲戦
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/appendMoney", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult addAppendMoney(String id, Double appendMoney, ModelMap modelMap, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		JsonResult jsonResult = new JsonResult(true);

		BigDecimal payMoney = new BigDecimal(appendMoney); // 杩藉姞淇濊瘉閲�

		BigDecimal defaultMinAppendMoney = new BigDecimal(500.00); // 榛樿鏈�皬杩藉姞淇濊瘉閲�000

		if (payMoney.compareTo(defaultMinAppendMoney) < 0) { // 杩藉姞閲戦鏄惁浣庝簬榛樿鏈�皬杩藉姞淇濊瘉閲�
			jsonResult.setMessage("underDefaultMinAppendMoney");
			return jsonResult;
		}

		UserSessionBean userSessionBean = (UserSessionBean) request.getSession()
				.getAttribute(Constants.TZDR_USER_SESSION);

		WUser wuser = wUserService.get(userSessionBean.getId()); // 鑾峰彇鐢ㄦ埛淇℃伅

		BigDecimal avlBal = new BigDecimal(wuser.getAvlBal().toString()); // 鑾峰彇鐢ㄦ埛浣欓

		if (avlBal.compareTo(payMoney) < 0) { // 鍒ゆ柇杩藉姞淇濊瘉閲戞槸鍚﹀ぇ浜庣敤鎴蜂綑棰�
			jsonResult.setMessage("insufficientBalance");
			return jsonResult;
		}

		FSimpleFtseUserTrade fSimpleFtseUserTrade = fSimpleProductUserTradeService.get(id);

		if (fSimpleFtseUserTrade == null) { // 鏈壘鍒拌鏂规
			jsonResult.setMessage("notFindData");
			return jsonResult;
		} else if (fSimpleFtseUserTrade.getStateType() == 6) { // 宸插畬缁�
			jsonResult.setMessage("isOver");
			return jsonResult;
		}

		// 杩藉姞淇濊瘉閲�
		fSimpleProductUserTradeService.addAppendTraderBond(fSimpleFtseUserTrade, appendMoney, wuser);

		// TODO 杩藉姞淇濊瘉閲戯紝缁欏伐浣滀汉鍛樺彂閫丒mail
		try {
			messagePromptService.sendMessage(PromptTypes.isAddBond, wuser.getMobile());
		} catch (Exception e) {
			log.info("鍙戦�閭欢澶辫触", e);
		}
		return jsonResult;
	}

}

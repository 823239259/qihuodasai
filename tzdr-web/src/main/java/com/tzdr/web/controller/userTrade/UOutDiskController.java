package com.tzdr.web.controller.userTrade;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hundsun.t2sdk.common.util.CollectionUtils;
import com.hundsun.t2sdk.common.util.UUID;
import com.tzdr.business.cms.service.messagePrompt.MessagePromptService;
import com.tzdr.business.cms.service.messagePrompt.PromptTypes;
import com.tzdr.business.pay.pingpp.config.enums.TradeStatus;
import com.tzdr.business.service.OutDisk.OutDiskParametersService;
import com.tzdr.business.service.OutDisk.OutDiskPriceService;
import com.tzdr.business.service.future.FSimpleCouponService;
import com.tzdr.business.service.pay.PayService;
import com.tzdr.business.service.userTrade.FSimpleFtseUserTradeService;
import com.tzdr.business.service.wuser.WUserService;
import com.tzdr.common.utils.TypeConvert;
import com.tzdr.common.web.support.JsonResult;
import com.tzdr.domain.web.entity.FSimpleFtseUserTrade;
import com.tzdr.domain.web.entity.OutDiskParameters;
import com.tzdr.domain.web.entity.OutDiskPrice;
import com.tzdr.domain.web.entity.RechargeList;
import com.tzdr.domain.web.entity.WUser;
import com.tzdr.domain.web.entity.future.FSimpleCoupon;
import com.tzdr.web.constants.ViewConstants;
import com.tzdr.web.utils.CookiesUtil;
import com.tzdr.web.utils.UserSessionBean;

/**
 * 
 * <B>说明: </B>
 * 
 * @author Liu Yang 2016年3月4日
 */
@Controller
@RequestMapping("/userOutDisk")
public class UOutDiskController {
	@Autowired
	private MessagePromptService messagePromptService;
	@Autowired
	private OutDiskParametersService outDiskParametersService;
	@Autowired
	private OutDiskPriceService outDiskPriceService;
	@Autowired
	private FSimpleFtseUserTradeService fSimpleFtseUserTradeService;
	@Autowired
	private WUserService wUserService;
	@Autowired
	private FSimpleCouponService fSimpleCouponService;
	@Autowired
	private PayService payService;
	/**
	 * 资金明细业务类型
	 */
	private final static int MONEYDETAILTYPE = 8;
	@SuppressWarnings("unused")
	private static Logger log = LoggerFactory.getLogger(UOutDiskController.class);

	/**
	 * 支付页面
	 * 
	 * @param modelMap
	 * @param traderBondAttr
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/pay")
	public String pay(HttpServletResponse response,ModelMap modelMap, BigDecimal traderBondAttr, HttpServletRequest request) {
		Object object = request.getSession().getAttribute(com.tzdr.web.constants.Constants.TZDR_USER_SESSION);
		UserSessionBean userSessionBean = (UserSessionBean) object;
		List<OutDiskPrice> outDiskPrice = outDiskPriceService.findAllOutDiskPrice();
		List<OutDiskParameters> outDiskParametersList = outDiskParametersService.findByTraderBond(traderBondAttr);
		if (CollectionUtils.isEmpty(outDiskParametersList)) {
			return ViewConstants.OutDiskJsp.INDEX;
		} else {
			OutDiskParameters outDiskParameters = outDiskParametersList.get(0);

			String userUid = userSessionBean.getId();
			WUser wuser = wUserService.get(userUid);
			// 应付金额
			BigDecimal payable = new BigDecimal("0").add(traderBondAttr).abs();
			// 用户余额
			Double avlBal = wuser.getAvlBal();
			// 代金券
			List<Map<String, Object>> voucher = this.fSimpleCouponService.queryCouponByUserId(userUid, 2, 8);

			// 用户余额+最大代金券是否充足
			BigDecimal voucherMoney = new BigDecimal(0);
			if (null != voucher && !voucher.isEmpty()) {
				voucherMoney = voucherMoney.add(new BigDecimal(voucher.get(0).get("money").toString()));
			}
			if (payable.compareTo(new BigDecimal(avlBal).add(voucherMoney)) > 0) {
				modelMap.put("avlBal_user", this.moneyToStrObject(new BigDecimal(avlBal)));
				modelMap.put("payable_avlBal_user",
						this.moneyToStrObject(TypeConvert.scale(payable.subtract(new BigDecimal(avlBal)), 2)));
				modelMap.put("showAvl", 1);
			}

			modelMap.put("traderBond", outDiskParameters.getTraderBond());
			modelMap.put("traderTotal", outDiskParameters.getTraderTotal());
			modelMap.put("lineLoss", outDiskParameters.getLineLoss());
			modelMap.put("ATranActualLever", outDiskParameters.getAtranActualLever());
			modelMap.put("HTranActualLever", outDiskParameters.getHtranActualLever());
			modelMap.put("YTranActualLever", outDiskParameters.getYtranActualLever());
			modelMap.put("outDiskPrice", outDiskPrice);
			modelMap.put("avlBal", avlBal);
			modelMap.put("payable", payable);
			// 代金券
			modelMap.put("voucher", voucher);
			Object uuid = UUID.randomUUID();
			request.getSession(false).setAttribute("tokenTzdr", uuid);
			CookiesUtil.addCookie(response, "tokenTzdr", String.valueOf(uuid), 600);
			return ViewConstants.OutDiskJsp.PAY;
		}
	}
	/**
	 * 国付宝支付前台地址回调
	 * @param request
	 * @return
	 */
	@RequestMapping("/callback/frontmer")
	public String doGoPayCallBackFrontMer(HttpServletRequest request){
		request.setAttribute("orderId", request.getParameter("merOrderNum"));
		return "/views/pay/paysuc";
	}
	/**
	 * 验证订单是否支付成功
	 * @param orderId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/validation/order/pay")
	public JsonResult validationOrderPay(HttpServletRequest request,HttpServletResponse response){
		//缓存订单是否在有效期内
		Cookie cookie = CookiesUtil.getCookieByName(request, "orderId");
		String orderId = null;
		boolean flag = false;
		JsonResult resultJson = new JsonResult();
		if(cookie != null){
			orderId = cookie.getValue();
			log.info("支付完成验证订单:"+orderId);
			RechargeList rechageList = payService.findByNo(orderId);
			if(rechageList != null){
				if(rechageList.getStatus() == TradeStatus.SUCCESS.getCode()){
					flag = true;
				}
			}
		}
		log.info("验证结果:"+flag);
		resultJson.setSuccess(flag);
		resultJson.setMessage(String.valueOf(request.getSession(false).getAttribute("tokenTzdr")));
		return resultJson;
	} 
	/**
	 * 清除支付cookie
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/clearPayCookie",method = RequestMethod.POST)
	public void clearPayCookieData(HttpServletRequest request,HttpServletResponse response){
		CookiesUtil.delCookies("orderId", response);
		CookiesUtil.delCookies("bond", response);
		CookiesUtil.delCookies("lever", response);
		CookiesUtil.delCookies("payurl", response);
		CookiesUtil.delCookies("vocherid", response);
		CookiesUtil.delCookies("tokenTzdr", response);
		log.info("清除支付cookie数据成功");
	}
	/**
	 * 支付成功页面
	 * 
	 * @param modelMap
	 * @param traderBondAttr
	 * @param tokenTzdr
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/paySuccessful")
	public String paySuccessful(ModelMap modelMap, BigDecimal inputTraderBond, String inputTranLever, String tokenTzdr, String voucherId,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		if (inputTraderBond == null) {
			this.pay(response,modelMap, inputTraderBond, request);
			return ViewConstants.OutDiskJsp.PAY;
		}
		List<OutDiskPrice> outDiskPrice = outDiskPriceService.findAllOutDiskPrice();
		List<OutDiskParameters> outDiskParametersList = outDiskParametersService.findByTraderBond(inputTraderBond);
		if (CollectionUtils.isEmpty(outDiskParametersList)) {
			this.pay(response,modelMap, inputTraderBond, request);
			return ViewConstants.OutDiskJsp.PAY;
		} else {
			OutDiskParameters outDiskParameters = outDiskParametersList.get(0);
			Object object = request.getSession().getAttribute(com.tzdr.web.constants.Constants.TZDR_USER_SESSION);
			String uid = "";
			Object tokenTzdrObj = request.getSession(false).getAttribute("tokenTzdr");
			if (object != null && tokenTzdrObj != null && String.valueOf(tokenTzdrObj).equals(tokenTzdr)) {
				UserSessionBean userSessionBean = (UserSessionBean) object;
				uid = userSessionBean.getId();
			} else {
				this.pay(response,modelMap, inputTraderBond, request);
				return ViewConstants.OutDiskJsp.PAY;
			}

			if (uid == null) {
				this.pay(response,modelMap, inputTraderBond, request);
				return ViewConstants.OutDiskJsp.PAY;
			}

			// 获取用户信息
			WUser wuser = wUserService.get(uid);
			// 应付金额
			BigDecimal payable = new BigDecimal("0").add(inputTraderBond).abs();

			if (wuser != null && wuser.getMobile() != null) {
				BigDecimal avlBal = new BigDecimal(wuser.getAvlBal().toString());
				// 验证代金券
				FSimpleCoupon voucher = this.fSimpleCouponService.get(voucherId);
				BigDecimal voucherActualMoney = null; // 代金券使用金额
				if (this.fSimpleCouponService.isCouponValid(voucher, 2, 8)) {
					voucherActualMoney = new BigDecimal(voucher.getMoney() + "");
					payable = payable.subtract(voucherActualMoney);
					if (payable.compareTo(BigDecimal.ZERO) < 0) {
						voucherActualMoney = voucherActualMoney.add(payable);
						payable = BigDecimal.ZERO;
					}
					inputTraderBond = inputTraderBond.subtract(voucherActualMoney);
					if (inputTraderBond.compareTo(BigDecimal.ZERO) < 0) {
						inputTraderBond = BigDecimal.ZERO;
					}
				}

				if (avlBal.compareTo(payable) >= 0) {
					FSimpleFtseUserTrade st = new FSimpleFtseUserTrade();
					st.setUid(uid);
					st.setTraderTotal(outDiskParameters.getTraderTotal());
					st.setTraderBond(inputTraderBond);
					st.setLineLoss(outDiskParameters.getLineLoss());
					st.setFeeManage(new BigDecimal(0));
					st.setTranFees(outDiskPrice.get(0).getPrice());
					st.setCrudeTranFees(outDiskPrice.get(1).getPrice());
					st.setHsiTranFees(outDiskPrice.get(2).getPrice());

					// 设置新增品种价格
					st.setMdTranFees(outDiskPrice.get(3).getPrice());
					st.setMnTranFees(outDiskPrice.get(4).getPrice());
					st.setMbTranFees(outDiskPrice.get(5).getPrice());
					st.setDaxTranFees(outDiskPrice.get(6).getPrice());
					st.setNikkeiTranFees(outDiskPrice.get(7).getPrice());
					st.setLhsiTranFees(outDiskPrice.get(8).getPrice());
					st.setAgTranFees(outDiskPrice.get(9).getPrice());
					st.sethSTranFees(outDiskPrice.get(10).getPrice());//H股指
					st.setxHSTranFees(outDiskPrice.get(11).getPrice());//小H股指
					st.setAmeCTranFees(outDiskPrice.get(12).getPrice());//美铜
					st.setAmeSTranFees(outDiskPrice.get(13).getPrice());//美白银
					st.setSmallCTranFees(outDiskPrice.get(14).getPrice());//小原油
					
					// 审核中
					st.setStateType(1);
					st.setBusinessType(8);
					st.setGoldenMoney(outDiskParameters.getGoldenMoney());
					// 设置代金券相关信息
					if (this.fSimpleCouponService.isCouponValid(voucher, 2, 8)) {
						st.setVoucherId(voucher.getId());
						st.setVoucherMoney(voucher.getMoney());
						st.setVoucherActualMoney(voucherActualMoney);
						this.fSimpleFtseUserTradeService.executePayable(st, voucher, wuser.getMobile(), payable,
								"投资国际综合方案申请(划款)。", MONEYDETAILTYPE);
					} else {
						this.fSimpleFtseUserTradeService.executePayable(st, wuser.getMobile(), payable,
								"投资国际综合方案申请(划款)。", MONEYDETAILTYPE);
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
					return ViewConstants.OutDiskJsp.PAY_SUCCESSFUL;
				} else {
					if (payable.compareTo(avlBal) > 0) {
						modelMap.addAttribute("avlBal_user", this.moneyToStrObject(avlBal));
						modelMap.addAttribute("payable_avlBal_user",
								this.moneyToStrObject(TypeConvert.scale(payable.subtract(avlBal), 2)));
						modelMap.addAttribute("showAvl", 1);
					}
				}
			}
			this.pay(response,modelMap, inputTraderBond, request);
			return ViewConstants.OutDiskJsp.PAY;
		}
	}

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
}

package com.tzdr.web.controller.userTrade;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hundsun.t2sdk.common.util.CollectionUtils;
import com.hundsun.t2sdk.common.util.UUID;
import com.tzdr.business.cms.service.messagePrompt.MessagePromptService;
import com.tzdr.business.cms.service.messagePrompt.PromptTypes;
import com.tzdr.business.service.OutDisk.OutDiskParametersService;
import com.tzdr.business.service.OutDisk.OutDiskPriceService;
import com.tzdr.business.service.future.FSimpleCouponService;
import com.tzdr.business.service.userTrade.FSimpleFtseUserTradeService;
import com.tzdr.business.service.wuser.WUserService;
import com.tzdr.common.utils.TypeConvert;
import com.tzdr.domain.web.entity.FSimpleFtseUserTrade;
import com.tzdr.domain.web.entity.OutDiskParameters;
import com.tzdr.domain.web.entity.OutDiskPrice;
import com.tzdr.domain.web.entity.WUser;
import com.tzdr.domain.web.entity.future.FSimpleCoupon;
import com.tzdr.web.constants.ViewConstants;
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
	public String pay(ModelMap modelMap, BigDecimal traderBondAttr, HttpServletRequest request) {
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
			request.getSession(false).setAttribute("tokenTzdr", UUID.randomUUID());
			return ViewConstants.OutDiskJsp.PAY;
		}
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
	public String paySuccessful(ModelMap modelMap, BigDecimal traderBondAttr, String tokenTzdr, String voucherId,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		if (traderBondAttr == null) {
			this.pay(modelMap, traderBondAttr, request);
			return ViewConstants.OutDiskJsp.PAY;
		}
		List<OutDiskPrice> outDiskPrice = outDiskPriceService.findAllOutDiskPrice();
		List<OutDiskParameters> outDiskParametersList = outDiskParametersService.findByTraderBond(traderBondAttr);
		if (CollectionUtils.isEmpty(outDiskParametersList)) {
			this.pay(modelMap, traderBondAttr, request);
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
				this.pay(modelMap, traderBondAttr, request);
				return ViewConstants.OutDiskJsp.PAY;
			}

			if (uid == null) {
				this.pay(modelMap, traderBondAttr, request);
				return ViewConstants.OutDiskJsp.PAY;
			}

			// 获取用户信息
			WUser wuser = wUserService.get(uid);
			// 应付金额
			BigDecimal payable = new BigDecimal("0").add(traderBondAttr).abs();

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
					traderBondAttr = traderBondAttr.subtract(voucherActualMoney);
					if (traderBondAttr.compareTo(BigDecimal.ZERO) < 0) {
						traderBondAttr = BigDecimal.ZERO;
					}
				}

				if (avlBal.compareTo(payable) >= 0) {
					FSimpleFtseUserTrade st = new FSimpleFtseUserTrade();
					st.setUid(uid);
					st.setTraderTotal(outDiskParameters.getTraderTotal());
					st.setTraderBond(traderBondAttr);
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
			this.pay(modelMap, traderBondAttr, request);
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

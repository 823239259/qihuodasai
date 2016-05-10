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
import com.tzdr.business.service.comprehensiveCommodity.ComprehensiveCommodityFeeService;
import com.tzdr.business.service.comprehensiveCommodity.ComprehensiveCommodityParameterService;
import com.tzdr.business.service.comprehensiveCommodity.ComprehensiveCommodityPriceService;
import com.tzdr.business.service.future.FSimpleCouponService;
import com.tzdr.business.service.userTrade.FSimpleFtseUserTradeService;

import com.tzdr.business.service.wuser.WUserService;
import com.tzdr.common.utils.TypeConvert;
import com.tzdr.domain.web.entity.ComprehensiveCommodityFee;
import com.tzdr.domain.web.entity.ComprehensiveCommodityParameter;
import com.tzdr.domain.web.entity.ComprehensiveCommodityPrice;
import com.tzdr.domain.web.entity.FSimpleFtseUserTrade;

import com.tzdr.domain.web.entity.WUser;
import com.tzdr.domain.web.entity.future.FSimpleCoupon;
import com.tzdr.web.constants.ViewConstants;
import com.tzdr.web.utils.UserSessionBean;

/**
 * 
 * <B>说明: </B>
 * @author Guo Xingyou
 * 2016年3月30日
 */
@Controller
@RequestMapping("/userCommodity")
public class UComprehensiveCommodityController {
	
	@Autowired
	private ComprehensiveCommodityParameterService comprehensiveCommodityParameterService;
	@Autowired
	private ComprehensiveCommodityPriceService comprehensiveCommodityPriceService;
	@Autowired
	private FSimpleFtseUserTradeService fSimpleFtseUserTradeService;
	@Autowired
	private WUserService wUserService;
	@Autowired
	private FSimpleCouponService fSimpleCouponService;
	@Autowired
	private ComprehensiveCommodityFeeService companyCommissionBalanceService;
	
	/**
	 * 资金明细业务类型
	 */
	private final static int MONEYDETAILTYPE = 8;
	@SuppressWarnings("unused")
	private static Logger log = LoggerFactory.getLogger(UComprehensiveCommodityController.class);
	
	/**
	 * 支付页面
	 * @param modelMap
	 * @param traderBondAttr
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/pay")
	public String pay(ModelMap modelMap,BigDecimal traderBondAttr,HttpServletRequest request){
		Object object = request.getSession().getAttribute(com.tzdr.web.constants.Constants.TZDR_USER_SESSION);
		UserSessionBean userSessionBean = (UserSessionBean) object;
		List<ComprehensiveCommodityPrice> comprehensiveCommodityPrice = comprehensiveCommodityPriceService.findAllComprehensiveCommodityPrice();
		List<ComprehensiveCommodityParameter> comprehensiveCommodityParameterList = comprehensiveCommodityParameterService.findByTraderBond(traderBondAttr);
		if(CollectionUtils.isEmpty(comprehensiveCommodityParameterList)){
			return ViewConstants.FSimpleProductUserTradeViewJsp.INDEX;
		}else{
			ComprehensiveCommodityParameter comprehensiveCommodityParameter= comprehensiveCommodityParameterList.get(0);
			
			String userUid = userSessionBean.getId();
			WUser wuser = wUserService.get(userUid);
			//应付金额
			BigDecimal payable =  new BigDecimal("0").add(traderBondAttr).abs();
			//用户余额
			Double avlBal = wuser.getAvlBal();
			// 代金券
			List<Map<String, Object>> voucher = this.fSimpleCouponService.queryCouponByUserId(userUid, 2, 20);
			
			//用户余额+最大代金券是否充足
			BigDecimal voucherMoney = new BigDecimal(0);
			if(null != voucher && !voucher.isEmpty()) {
				voucherMoney = voucherMoney.add(new BigDecimal(voucher.get(0).get("money").toString()));
			}
			if (payable.compareTo(new BigDecimal(avlBal).add(voucherMoney)) > 0) {
				modelMap.put("avlBal_user",this.moneyToStrObject(new BigDecimal(avlBal)));
				modelMap.put("payable_avlBal_user",this.moneyToStrObject(TypeConvert.scale(
						payable.subtract(new BigDecimal(avlBal)),2) ));
				modelMap.put("showAvl",1);
			}
			
			modelMap.put("traderBond", comprehensiveCommodityParameter.getTraderBond());
			modelMap.put("traderTotal", comprehensiveCommodityParameter.getTraderTotal());
			modelMap.put("lineLoss", comprehensiveCommodityParameter.getLineLoss());
			
			modelMap.put("silverLever", comprehensiveCommodityParameter.getSilverLever());
			modelMap.put("aluminumLever", comprehensiveCommodityParameter.getAluminumLever());
			modelMap.put("goldLever", comprehensiveCommodityParameter.getGoldLever());
			modelMap.put("asphaltLever", comprehensiveCommodityParameter.getAsphaltLever());
			modelMap.put("copperLever", comprehensiveCommodityParameter.getCopperLever());
			modelMap.put("coilLever", comprehensiveCommodityParameter.getCoilLever());
			modelMap.put("nickelLever", comprehensiveCommodityParameter.getNickelLever());
			modelMap.put("rebarLever", comprehensiveCommodityParameter.getRebarLever());		
			modelMap.put("zincLever", comprehensiveCommodityParameter.getZincLever());
			modelMap.put("rubberLever", comprehensiveCommodityParameter.getRubberLever());
			modelMap.put("beanLever", comprehensiveCommodityParameter.getBeanLever());
			modelMap.put("cornLever", comprehensiveCommodityParameter.getCornLever());
			modelMap.put("cornStarchLever", comprehensiveCommodityParameter.getCornStarchLever());
			modelMap.put("ironOreLever", comprehensiveCommodityParameter.getIronOreLever());
			modelMap.put("cokeLever", comprehensiveCommodityParameter.getCokeLever());
			modelMap.put("eggLever", comprehensiveCommodityParameter.getEggLever());			
			modelMap.put("cokingCoalLever", comprehensiveCommodityParameter.getCokingCoalLever());
			modelMap.put("plasticLever", comprehensiveCommodityParameter.getPlasticLever());
			modelMap.put("soybeanMealLever", comprehensiveCommodityParameter.getSoybeanMealLever());
			modelMap.put("palmOilLever", comprehensiveCommodityParameter.getPalmOilLever());
			modelMap.put("polypropyleneLever", comprehensiveCommodityParameter.getPolypropyleneLever());
			modelMap.put("soybeanOilLever", comprehensiveCommodityParameter.getSoybeanOilLever());
			modelMap.put("cottonLever", comprehensiveCommodityParameter.getCottonLever());
			modelMap.put("glassLever", comprehensiveCommodityParameter.getGlassLever());
			modelMap.put("methanolLever", comprehensiveCommodityParameter.getMethanolLever());
			modelMap.put("rapeOilLever", comprehensiveCommodityParameter.getRapeOilLever());
			modelMap.put("rapeseedMealLever", comprehensiveCommodityParameter.getRapeseedMealLever());
			modelMap.put("sugarLever", comprehensiveCommodityParameter.getSugarLever());
			modelMap.put("pTALever", comprehensiveCommodityParameter.getpTALever());
			modelMap.put("powerCoalLever", comprehensiveCommodityParameter.getPowerCoalLever());
			modelMap.put("fiveNationalDebtLever", comprehensiveCommodityParameter.getFiveNationalDebtLever());
			modelMap.put("tenNationalDebtLever", comprehensiveCommodityParameter.getTenNationalDebtLever());
			
			modelMap.put("comprehensiveCommodityPrice", comprehensiveCommodityPrice);
			modelMap.put("avlBal", avlBal);
			modelMap.put("payable", payable);
			// 代金券
			modelMap.put("voucher", voucher);
			request.getSession(false).setAttribute("tokenTzdr", UUID.randomUUID());
			return ViewConstants.FSimpleProductUserTradeViewJsp.PAY;
		}
	}
	
	
	/**
	 * 支付成功页面
	 * @param modelMap
	 * @param traderBondAttr
	 * @param tokenTzdr
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/paySuccessful")
	public String paySuccessful(ModelMap modelMap,BigDecimal traderBondAttr, String tokenTzdr, String voucherId,
			HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		if (traderBondAttr == null ) {
		 	this.pay(modelMap, traderBondAttr,request);
		 	return ViewConstants.FSimpleProductUserTradeViewJsp.PAY;
		}
		List<ComprehensiveCommodityPrice> comprehensiveCommodityPrice = comprehensiveCommodityPriceService.findAllComprehensiveCommodityPrice();
		List<ComprehensiveCommodityParameter> comprehensiveCommodityParameterList = comprehensiveCommodityParameterService.findByTraderBond(traderBondAttr);
		
		if(CollectionUtils.isEmpty(comprehensiveCommodityParameterList)){
			this.pay(modelMap, traderBondAttr,request);
		 	return ViewConstants.FSimpleProductUserTradeViewJsp.PAY;
		}else{
			ComprehensiveCommodityParameter comprehensiveCommodityParameter= comprehensiveCommodityParameterList.get(0);
			Object object = request.getSession().getAttribute(com.tzdr.web.constants.Constants.TZDR_USER_SESSION);
			String uid = "";
			Object tokenTzdrObj = request.getSession(false).getAttribute("tokenTzdr");
			if (object != null && tokenTzdrObj != null && String.valueOf(tokenTzdrObj).equals(tokenTzdr)) {
				UserSessionBean userSessionBean = (UserSessionBean) object;
				uid = userSessionBean.getId();
			}else {
				this.pay(modelMap, traderBondAttr, request);
				return ViewConstants.FSimpleProductUserTradeViewJsp.PAY;
			}
			
			if (uid == null) {
				this.pay(modelMap, traderBondAttr, request);
				return  ViewConstants.FSimpleProductUserTradeViewJsp.PAY;
			}
			
			//获取用户信息
			WUser wuser = wUserService.get(uid);
			//应付金额
			BigDecimal payable =  new BigDecimal("0").add(traderBondAttr).abs();
			
			if (wuser != null && wuser.getMobile() != null) {
				BigDecimal avlBal = new BigDecimal(wuser.getAvlBal().toString());
				// 验证代金券
				FSimpleCoupon voucher = this.fSimpleCouponService.get(voucherId);
				BigDecimal voucherActualMoney = null; // 代金券使用金额
				if(this.fSimpleCouponService.isCouponValid(voucher, 2, 8)) {
					voucherActualMoney = new BigDecimal(voucher.getMoney()+"");
					payable = payable.subtract(voucherActualMoney);
					if(payable.compareTo(BigDecimal.ZERO) < 0) {
						voucherActualMoney = voucherActualMoney.add(payable);
						payable = BigDecimal.ZERO;
					}
					traderBondAttr = traderBondAttr.subtract(voucherActualMoney);
					if(traderBondAttr.compareTo(BigDecimal.ZERO) < 0) {
						traderBondAttr = BigDecimal.ZERO;
					}
				}
				
				if (avlBal.compareTo(payable) >= 0 ) {
					FSimpleFtseUserTrade st = new FSimpleFtseUserTrade();
					
					st.setUid(uid);
					st.setTraderTotal(comprehensiveCommodityParameter.getTraderTotal());
					st.setTraderBond(traderBondAttr);
					st.setLineLoss(comprehensiveCommodityParameter.getLineLoss());
					st.setFeeManage(new BigDecimal(0));
					
					
					
					/*st.setTranFees(outDiskPrice.get(0).getPrice());
					st.setCrudeTranFees(outDiskPrice.get(1).getPrice());
					st.setHsiTranFees(outDiskPrice.get(2).getPrice());
					
					//设置新增品种价格
					st.setMdTranFees(outDiskPrice.get(3).getPrice());
					st.setMnTranFees(outDiskPrice.get(4).getPrice());
					st.setMbTranFees(outDiskPrice.get(5).getPrice());
					st.setDaxTranFees(outDiskPrice.get(6).getPrice());
					st.setNikkeiTranFees(outDiskPrice.get(7).getPrice());*/
					
					
					//审核中
					st.setStateType(1);
					// 8：国际综合  20：商品综合
					st.setBusinessType(20); 
					st.setGoldenMoney(comprehensiveCommodityParameter.getGoldenMoney());
					// 设置代金券相关信息   8：国际综合  20：商品综合
					if(this.fSimpleCouponService.isCouponValid(voucher, 2, 20)) {
						st.setVoucherId(voucher.getId());
						st.setVoucherMoney(voucher.getMoney());
						st.setVoucherActualMoney(voucherActualMoney);
						st=this.fSimpleFtseUserTradeService.executePayable(st, voucher, wuser.getMobile(), payable,"投资商品综合方案申请(划款)。",MONEYDETAILTYPE);
						
					} else {
						st=this.fSimpleFtseUserTradeService.executePayable(st, wuser.getMobile(), payable,"投资商品综合方案申请(划款)。",MONEYDETAILTYPE);
					}
					
					// 保存记录
					for(ComprehensiveCommodityPrice c:comprehensiveCommodityPrice){
						ComprehensiveCommodityFee ccb=new ComprehensiveCommodityFee();
						ccb.setTradeId(st.getId());
						ccb.setPoundage(c.getPrice());
						ccb.setType(c.getTradeType()+"");
						companyCommissionBalanceService.create(ccb);
					}
					
					request.getSession(false).removeAttribute("tokenTzdr");
					request.setAttribute("businessType", 20);
					return ViewConstants.FSimpleProductUserTradeViewJsp.PAY_SUCCESSFUL;
				}
				else {
					if (payable.compareTo(avlBal) > 0) {
						modelMap.addAttribute("avlBal_user",this.moneyToStrObject(avlBal));
						modelMap.addAttribute("payable_avlBal_user",this.moneyToStrObject(TypeConvert.scale(payable.subtract(avlBal),2) ));
						modelMap.addAttribute("showAvl",1);
					}
				}
			}
			this.pay(modelMap, traderBondAttr,request);
			return ViewConstants.FSimpleProductUserTradeViewJsp.PAY;
		}
	}
	
	

	
	/**
	 * 
	 * @param big BigDecimal
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
	 * @param big BigDecimal
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

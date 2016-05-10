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
 * <B>说明: </B>商品期货
 * @author LiuYang
 *
 * 2015年9月17日 下午6:40:08
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
	private  TradeDayService tradeDayService;
	
	@Autowired
	private FSimpleCouponService fSimpleCouponService;
	
	@Autowired
	private ContractParitiesService contractParitiesService;
	
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
	
	/**
	 * 支付页
	 * @param modelMap
	 * @param inputTraderBond
	 * @param inputTranLever
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/pay")
	public String pay(ModelMap modelMap,BigDecimal inputTraderBond, Integer inputTranLever,BigDecimal inputTranFees,int type,HttpServletRequest request){
		Map<Integer,String> payView = new HashMap<Integer,String>();
		payView.put(1, ViewConstants.FSimpleProductUserTradeViewJsp.GOLD_PAY);
		payView.put(2, ViewConstants.FSimpleProductUserTradeViewJsp.SLIVER_PAY);
		payView.put(3, ViewConstants.FSimpleProductUserTradeViewJsp.COPPER_PAY);
		payView.put(4, ViewConstants.FSimpleProductUserTradeViewJsp.RUBBER_PAY);
		Object object = request.getSession().getAttribute(com.tzdr.web.constants.Constants.TZDR_USER_SESSION);
		UserSessionBean userSessionBean = (UserSessionBean) object;
		
		ContractParities  newConfig = new ContractParities();
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
		
		if(inputTraderBond == null){   //判断保证金
			inputTraderBond =  new BigDecimal("0");
		}

		if(inputTranLever == null){  //判断手数
			inputTranLever = 0;
		}
		
		if(inputTranFees == null){  //判断手数
			inputTranFees = new BigDecimal("0");
		}

		inputTranFees = inputTranFees.abs();   //绝对值
		
		inputTraderBond = inputTraderBond.abs();   //绝对值

		inputTranLever = Math.abs(inputTranLever); //绝对值

		FSimpleConfig fSimpleConfig = fSimpleProductConfigService.getFSimpleConfigByType(type);  //获取配置方案信息

		//系统单手操盘金额
		BigDecimal traderMoney = fSimpleConfig != null ? fSimpleConfig.getTraderMoney() : new BigDecimal("0");
		
		//系统单手亏损警告线
		BigDecimal LineLoss = fSimpleConfig != null ? fSimpleConfig.getLineLoss() : new BigDecimal("0");
		
		//系统单手管理费
		BigDecimal feeManage = fSimpleConfig != null ? fSimpleConfig.getFeeManage() : new BigDecimal("0");

		//总操盘保证金=操盘保证金*手数
		BigDecimal inputTotalTraderBond = new BigDecimal("0").add(inputTraderBond).multiply(new BigDecimal(inputTranLever), MathContext.DECIMAL32);

		//总操盘保证金=单手保证金金额*手数
		BigDecimal traderTotal = new BigDecimal("0").add(traderMoney).multiply(new BigDecimal(inputTranLever), MathContext.DECIMAL32);

		//亏损警告线
		BigDecimal lossLine = new BigDecimal("0").add(traderTotal.subtract(inputTotalTraderBond).add(LineLoss.multiply(new BigDecimal(inputTranLever), MathContext.DECIMAL32)));
		//单管理费
		BigDecimal manageAmount = new BigDecimal("0").add(feeManage);

		//总管理费
		BigDecimal totalManageAmount = new BigDecimal("0").add(manageAmount);
		
		//应付金额
		BigDecimal payable =  new BigDecimal("0").add(inputTotalTraderBond).abs().add(totalManageAmount);

		//开仓手数
		modelMap.addAttribute("inputTranLever",inputTranLever);
		
		//总操盘金
		modelMap.addAttribute("traderTotal",this.moneyToStrObject(traderTotal));
		
		//操盘保证金
		modelMap.addAttribute("inputTraderBond",inputTraderBond);
		
		//总操盘保证金
		modelMap.addAttribute("traderBond",this.moneyToStrObject(inputTotalTraderBond));
		
		//亏损平仓线
		modelMap.addAttribute("lossLine",this.moneyToStrObject(lossLine));
		
		//管理费
		modelMap.addAttribute("inputManageAmount",this.moneyToStrObject(manageAmount));
		
		//总管理费
		modelMap.addAttribute("totalManageAmount",this.moneyToStrObject(totalManageAmount));
		
		//交易费
		modelMap.addAttribute("inputTranFees",this.moneyToStrObject(inputTranFees));
		
		//应付金额
		modelMap.addAttribute("payable",payable);

		modelMap.addAttribute("showAvl",0);

		String userUid = userSessionBean.getId();
		WUser wuser = wUserService.get(userUid);
		
		//用户余额
		Double avlBal = wuser.getAvlBal();
		modelMap.addAttribute("avlBal",avlBal);
		
		// 代金券
		List<Map<String, Object>> voucher = this.fSimpleCouponService.queryCouponByUserId(userUid, 2, 5);
		modelMap.put("voucher", voucher);
		
		//用户余额+最大代金券是否充足
		BigDecimal voucherMoney = new BigDecimal(0);
		if(null != voucher && !voucher.isEmpty()) {
			voucherMoney = voucherMoney.add(new BigDecimal(voucher.get(0).get("money").toString()));
		}
		
		//用户余额
		if (payable.compareTo(new BigDecimal(avlBal).add(voucherMoney)) > 0) {
			modelMap.addAttribute("avlBal_user",this.moneyToStrObject(new BigDecimal(avlBal)));
			modelMap.addAttribute("payable_avlBal_user",this.moneyToStrObject(TypeConvert.scale(
					payable.subtract(new BigDecimal(avlBal)),2) ));
			modelMap.addAttribute("showAvl",1);
		}
		request.getSession(false).setAttribute("tokenTzdr", UUID.randomUUID());
		return payView.get(type);
	}
	/**
	 * 支付成功页
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
	public String paySuccessful(ModelMap modelMap,BigDecimal inputTraderBond, Integer inputTranLever,BigDecimal inputTranFees,int type,String tokenTzdr,String voucherId,
			HttpServletRequest request,HttpServletResponse response) throws Exception{
		Map<Integer,String> payView = new HashMap<Integer,String>();
		payView.put(1, ViewConstants.FSimpleProductUserTradeViewJsp.GOLD_PAY);
		payView.put(2, ViewConstants.FSimpleProductUserTradeViewJsp.SLIVER_PAY);
		payView.put(3, ViewConstants.FSimpleProductUserTradeViewJsp.COPPER_PAY);
		payView.put(4, ViewConstants.FSimpleProductUserTradeViewJsp.RUBBER_PAY);
		if (inputTraderBond == null || inputTranLever == null) {
		 	this.pay(modelMap, inputTraderBond, inputTranLever,inputTranFees,type,request);
		 	return payView.get(type);
		}
		
		FSimpleConfig fSimpleConfig = fSimpleProductConfigService.getFSimpleConfigByType(type);  //获取配置方案信息
		
		if(fSimpleConfig == null){ 
			this.pay(modelMap, inputTraderBond, inputTranLever,inputTranFees,type,request);
			return payView.get(type);
		}
		
		
		if(fSimpleConfig.getTraderBond().compareTo(inputTraderBond) != 0 ){   //判断当前配置方案单手保证金是正确
			this.pay(modelMap, inputTraderBond, inputTranLever,inputTranFees,type,request);
			return payView.get(type);
		}
		
		String[] tranLeverStrs =  null;  //现有开仓手数
		
		if(fSimpleConfig != null && StringUtil.isNotBlank(fSimpleConfig.getTranLever())){
			tranLeverStrs = fSimpleConfig.getTranLever().split(",");
		}
		
		boolean isTranLeverTrue = false;  //当前开仓手数是否正确
		
		if(tranLeverStrs != null && tranLeverStrs.length > 0){
			for (String tranLever : tranLeverStrs) {
				if(Integer.parseInt(tranLever) == inputTranLever){  //当前开仓手数是否存在
					isTranLeverTrue = true;
					break;
				}
			}
		}

		if(!isTranLeverTrue){  //判断当前开仓手数是否正确
			this.pay(modelMap, inputTraderBond, inputTranLever,inputTranFees,type,request);
			return payView.get(type);
		}
		
		String[] tranFeesStrs =  null;  //现有手续费配置
		
		if(fSimpleConfig != null && StringUtil.isNotBlank(fSimpleConfig.getTranLever())){
			tranFeesStrs = fSimpleConfig.getTranFeesArray().split(",");
		}
		
		boolean isTranFeesTrue = false;  //当前手续费是否正确
		
		if(tranFeesStrs != null && tranFeesStrs.length > 0){
			for (String tranFees : tranFeesStrs) {
				if(new BigDecimal(tranFees).equals(inputTranFees)){  //当前开仓手数是否存在
					isTranFeesTrue = true;
					break;
				}
			}
		}

		if(!isTranFeesTrue){  //判断当前手续费是否正确
			this.pay(modelMap, inputTraderBond, inputTranLever,inputTranFees,type,request);
			return payView.get(type);
		}
		
		inputTranFees = inputTranFees.abs();   //绝对值
		
		inputTraderBond = inputTraderBond.abs();   //绝对值
		
		inputTranLever = Math.abs(inputTranLever); //绝对值
		
		Object object = request.getSession().getAttribute(com.tzdr.web.constants.Constants.TZDR_USER_SESSION);
		String uid = "";
		Object tokenTzdrObj = request.getSession(false).getAttribute("tokenTzdr");
		if (object != null && tokenTzdrObj != null && String.valueOf(tokenTzdrObj).equals(tokenTzdr)) {
			UserSessionBean userSessionBean = (UserSessionBean) object;
			uid = userSessionBean.getId();
		}else {
			this.pay(modelMap, inputTraderBond, inputTranLever,inputTranFees,type,request);
			return payView.get(type);
		}
		
		if (uid == null) {
			this.pay(modelMap, inputTraderBond, inputTranLever,inputTranFees,type,request);
			return payView.get(type);
		}
		
		//获取用户信息
		WUser wuser = wUserService.get(uid);
		
		//总操盘保证金=操盘保证金*手数
		BigDecimal inputTotalTraderBond = new BigDecimal("0").add(inputTraderBond).multiply(new BigDecimal(inputTranLever), MathContext.DECIMAL32);
		
		//总操盘保证金=单手保证金金额*手数
		BigDecimal traderTotal = new BigDecimal("0").add(fSimpleConfig.getTraderMoney()).multiply(new BigDecimal(inputTranLever), MathContext.DECIMAL32);
		
		//亏损警告线
		BigDecimal lossLine = new BigDecimal("0").add(traderTotal.subtract(inputTotalTraderBond).add(fSimpleConfig.getLineLoss().multiply(new BigDecimal(inputTranLever), MathContext.DECIMAL32)));
		//单管理费
		BigDecimal manageAmount = new BigDecimal("0").add(fSimpleConfig.getFeeManage());

		//总管理费
		BigDecimal totalManageAmount = new BigDecimal("0").add(manageAmount);

		//应付金额
		BigDecimal payable =  new BigDecimal("0").add(inputTotalTraderBond).abs().add(totalManageAmount);
		
		//交易费
		BigDecimal totalTranFees = new BigDecimal("0").add(inputTranFees);
		
		if (wuser != null && wuser.getMobile() != null) {
			BigDecimal avlBal = new BigDecimal(wuser.getAvlBal().toString());
			// 验证代金券
			FSimpleCoupon voucher = this.fSimpleCouponService.get(voucherId);
			BigDecimal voucherActualMoney = null; // 代金券使用金额
			if(this.fSimpleCouponService.isCouponValid(voucher, 2, 5)) {
				voucherActualMoney = new BigDecimal(voucher.getMoney()+"");
				payable = payable.subtract(voucherActualMoney);
				if(payable.compareTo(BigDecimal.ZERO) < 0) {
					voucherActualMoney = voucherActualMoney.add(payable);
					payable = BigDecimal.ZERO;
				}
				inputTotalTraderBond = inputTotalTraderBond.subtract(voucherActualMoney);
				if(inputTotalTraderBond.compareTo(BigDecimal.ZERO) < 0) {
					inputTotalTraderBond = BigDecimal.ZERO;
				}
			}
			if (avlBal.compareTo(payable) >= 0 ) {
				FSimpleFtseUserTrade st = new FSimpleFtseUserTrade();
				st.setUid(uid);
				st.setTraderTotal(traderTotal);
				st.setTraderBond(inputTotalTraderBond);
				st.setTranLever(inputTranLever);
				st.setLineLoss(lossLine);
				st.setFeeManage(totalManageAmount);
				st.setTranFees(totalTranFees);
				//审核中
				st.setStateType(1);
				st.setBusinessType(type);  
				// 设置代金券相关信息
				if(this.fSimpleCouponService.isCouponValid(voucher, 2, 5)) {
					st.setVoucherId(voucher.getId());
					st.setVoucherMoney(voucher.getMoney());
					st.setVoucherActualMoney(voucherActualMoney);
					this.fSimpleProductUserTradeService.executePayable(st, voucher, wuser.getMobile(), payable);
				} else {
					this.fSimpleProductUserTradeService.executePayable(st, wuser.getMobile(), payable);
				}
				request.getSession(false).removeAttribute("tokenTzdr");
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
		this.pay(modelMap, inputTraderBond, inputTranLever,inputTranFees,type,request);
		return payView.get(type);
	}
	/**
	 * 跳转方案列表页面
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/trade_list")
	public String tradeList(ModelMap modelMap,  HttpServletRequest request,HttpServletResponse response){
		return ViewConstants.FSimpleProductUserTradeViewJsp.TRADE_LIST;
	}
	
	/**
	 * 获取折扣券
	 * @param modelMap
	 * @param businessType
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/getCoupon")
	@ResponseBody
	public JsonResult  getCoupon(ModelMap modelMap,Integer businessType, HttpServletRequest request,HttpServletResponse response){
		JsonResult jsonResult = new JsonResult(true);
		Map<Object, Object> data = new HashMap<Object, Object>();
		// 折扣券
		List<Map<String, Object>> discount = new ArrayList<>();
		Object userSession = request.getSession().getAttribute(Constants.TZDR_USER_SESSION);
		if(null != userSession) {
			UserSessionBean userSessionBean = (UserSessionBean) userSession;
			discount = this.fSimpleCouponService.queryCouponByUserId(userSessionBean.getId(), 3, businessType);
		}
		data.put("discount", discount);
		jsonResult.setData(data);
		return jsonResult;
	}
	
	/**
	 * 查询方案数据
	 * @param response
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/findData")
	@ResponseBody
	public PageInfo<FSimpleProductUserTradeWebVo> findData(HttpServletResponse response,HttpServletRequest request){
		UserSessionBean userSessionBean=(UserSessionBean) request.getSession().getAttribute(Constants.TZDR_USER_SESSION);
		if (userSessionBean == null) {
			return null;
		}
		String pageIndex = request.getParameter("pageIndex");
		String perPage = request.getParameter("perPage");
		PageInfo<FSimpleProductUserTradeWebVo> pageInfo = this.fSimpleProductUserTradeService.findDataList(pageIndex, perPage,userSessionBean.getId());
		List<FSimpleProductUserTradeWebVo> pageResults = pageInfo == null ? null : pageInfo.getPageResults();
		if(pageInfo != null && pageInfo.getPageResults() != null && !pageInfo.getPageResults().isEmpty()){
			for (FSimpleProductUserTradeWebVo fSimpleProductUserTradeWebVo : pageResults) {
				if(fSimpleProductUserTradeWebVo.getStateType() == 1 || fSimpleProductUserTradeWebVo.getStateType() == 5){
					fSimpleProductUserTradeWebVo.setUseTradeDay(0);
				}else{
					fSimpleProductUserTradeWebVo.setUseTradeDay(fSimpleProductUserTradeWebVo.getStateType() == 6 ? fSimpleProductUserTradeWebVo.getUseTradeDay() : tradeDayService.getTradeDayNum(fSimpleProductUserTradeWebVo.getAppStarttime(), 14));
					}
			}
			pageInfo.setPageResults(pageResults);
		}
		return pageInfo;
	}
	/**
	 * 申请终结
	 * @param modelMap
	 * @param id
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/apply_end_trade" , method = RequestMethod.POST)
	@ResponseBody
	public JsonResult applyEndTrade(ModelMap modelMap,String id,Integer businessType, String discountId, HttpServletRequest request,HttpServletResponse response){
		
		JsonResult  jsonResult = new JsonResult(true);
		
		if(StringUtil.isBlank(id)){
			jsonResult.setMessage("notFindData");
			return jsonResult;
		}
		
		UserSessionBean userSessionBean=(UserSessionBean) request.getSession().getAttribute(Constants.TZDR_USER_SESSION);
		
		FSimpleFtseUserTrade fSimpleFtseUserTrade = fSimpleProductUserTradeService.get(id);
		
		if(fSimpleFtseUserTrade == null || !userSessionBean.getId().equals(fSimpleFtseUserTrade.getUid())){
			jsonResult.setMessage("notFindData");
			return jsonResult;
		}
		
		if(fSimpleFtseUserTrade.getStateType() == 2){   //不能重复申请
			jsonResult.setMessage("notRepetitionApply");
			return jsonResult;
		}
		
		if(fSimpleFtseUserTrade.getStateType() != 4){  //判断是不是操盘中 
			jsonResult.setMessage("applyEndTradeFail");
			return jsonResult;
		}
		
		fSimpleFtseUserTrade.setStateType(2);  //申请终结方案
		fSimpleFtseUserTrade.setAppEndTime(Dates.getCurrentLongDate());  //申请终结时间
		
		// 验证折扣券
		FSimpleCoupon discount = this.fSimpleCouponService.get(discountId);
		if(this.fSimpleCouponService.isCouponValid(discount, 3, businessType)) {
			fSimpleFtseUserTrade.setDiscountId(discount.getId());
			fSimpleFtseUserTrade.setDiscountMoney(discount.getMoney());
			fSimpleProductUserTradeService.updateFSimpleFtseUserTradeAndFSimpleCoupon(fSimpleFtseUserTrade, discount);
		} else {
			fSimpleProductUserTradeService.update(fSimpleFtseUserTrade);
		}
		
		return jsonResult;
	}
	
	

	/**
	 * 获取需要追加保证金信息
	 * @param id  方案编号
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getAppendMoneyInfo" , method = RequestMethod.POST)
	@ResponseBody
	public JsonResult  getAppendMoneyInfo(String id,ModelMap modelMap,HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		JsonResult jsonResult = new JsonResult(true);
		
		UserSessionBean userSessionBean=(UserSessionBean) request.getSession().getAttribute(Constants.TZDR_USER_SESSION);
		
		WUser wuser = wUserService.get(userSessionBean.getId());   //获取用户信息
		
		FSimpleFtseUserTrade fSimpleUserTrade = fSimpleProductUserTradeService.get(id);
		
		if(fSimpleUserTrade == null){   //未找到该方案
			jsonResult.setMessage("notFindData");
			return jsonResult;
		}
		
		Map<Object, Object> data = new HashMap<Object, Object>();
		data.put("avlBal", wuser.getAvlBal());   //当前余额
		data.put("traderTotal", fSimpleUserTrade.getTraderTotal()); //总操盘金额
		
		jsonResult.setData(data);
		return jsonResult;
	}
	
	
	/**
	 * 追加保证金
	 * @param id    方案号TG+ID号
	 * @param appendMoney  追加保证金额
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/appendMoney" , method = RequestMethod.POST)
	@ResponseBody
	public JsonResult  addAppendMoney(String id,Double appendMoney,ModelMap modelMap,HttpServletRequest request,HttpServletResponse response) throws Exception{
		JsonResult jsonResult = new JsonResult(true);

		BigDecimal payMoney = new BigDecimal(appendMoney);  //追加保证金
		
		BigDecimal defaultMinAppendMoney = new BigDecimal(2000.00);  //默认最小追加保证金2000

		if(payMoney.compareTo(defaultMinAppendMoney) < 0){   //追加金额是否低于默认最小追加保证金
			jsonResult.setMessage("underDefaultMinAppendMoney");
			return jsonResult;
		}
		
		UserSessionBean userSessionBean=(UserSessionBean) request.getSession().getAttribute(Constants.TZDR_USER_SESSION);
		
		WUser wuser = wUserService.get(userSessionBean.getId());   //获取用户信息
		
		BigDecimal avlBal = new BigDecimal(wuser.getAvlBal().toString());  //获取用户余额
		
		if(avlBal.compareTo(payMoney) < 0 ) {   //判断追加保证金是否大于用户余额
			jsonResult.setMessage("insufficientBalance");
			return jsonResult;
		}
		
		FSimpleFtseUserTrade fSimpleFtseUserTrade = fSimpleProductUserTradeService.get(id);
		
		if(fSimpleFtseUserTrade == null){   //未找到该方案
			jsonResult.setMessage("notFindData");
			return jsonResult;
		}else if(fSimpleFtseUserTrade.getStateType() == 6){   //已完结
			jsonResult.setMessage("isOver");
			return jsonResult;
		}

		//追加保证金
		fSimpleProductUserTradeService.addAppendTraderBond(fSimpleFtseUserTrade, appendMoney, wuser);
		
		return jsonResult;
	}
	
}

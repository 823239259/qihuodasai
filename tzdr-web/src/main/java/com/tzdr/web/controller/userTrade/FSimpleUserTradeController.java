package com.tzdr.web.controller.userTrade;

import java.math.BigDecimal;
import java.math.MathContext;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hundsun.t2sdk.common.util.UUID;
import com.tzdr.business.cms.service.auth.AuthService;
import com.tzdr.business.service.datamap.DataMapService;
import com.tzdr.business.service.recharge.RechargeListService;
import com.tzdr.business.service.tradecalendar.TradeDayService;
import com.tzdr.business.service.userTrade.FSimpleUserTradeService;
import com.tzdr.business.service.wuser.WUserService;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.utils.TypeConvert;
import com.tzdr.domain.cache.CacheManager;
import com.tzdr.domain.entity.DataMap;
import com.tzdr.domain.vo.FSimpleUserTradeWebVo;
import com.tzdr.domain.web.entity.FSimpleUserTrade;
import com.tzdr.domain.web.entity.WUser;
import com.tzdr.web.constants.Constants;
import com.tzdr.web.constants.ViewConstants;
import com.tzdr.web.utils.UserSessionBean;


/**
 * 
 * 
 * <p></p>
 * @author QingLiu
 * @see
 * FSimpleUserTradeController
 * @version 2.0
 * 2015年2月5日下午7:33:13
 */
@Controller
@RequestMapping("/future")
public class FSimpleUserTradeController {
	
	@Autowired
	private AuthService authService;
	
	@Autowired
	private DataMapService  dataMapService;
	
	@Autowired
	private  TradeDayService tradeDayService;
	
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
	
	@SuppressWarnings("unused")
	private static Logger log = LoggerFactory
			.getLogger(UserTradeEnterController.class);
	
	@RequestMapping(value = "/index")
	public String index(ModelMap modelMap,HttpServletRequest request,HttpServletResponse response){
		return ViewConstants.FSimpleUserTradeViewJsp.FUTRUE_INDEX;
	}
	
	@RequestMapping(value = "/day_index")
	public String indexDay(ModelMap modelMap,HttpServletRequest request,HttpServletResponse response){
		return ViewConstants.FSimpleUserTradeViewJsp.FUTRUE_DAY_INDEX;
	}
	
	@RequestMapping(value = "/pay")
	public String pay(ModelMap modelMap,BigDecimal inputTraderBond, Integer inputTranLever,Integer inputTranDay,HttpServletRequest request){
		
		if(inputTraderBond == null){   //判断保证金
			inputTraderBond =  new BigDecimal("0");
		}
		
		if(inputTranLever == null){  //判断手数
			inputTranLever = 0;
		}
		
		if(inputTranDay == null){  //交易日
			inputTranDay = 0;
		}
		
		inputTraderBond = inputTraderBond.abs();   //绝对值
		
		inputTranLever = Math.abs(inputTranLever); //绝对值
		
		inputTranDay = Math.abs(inputTranDay); //绝对值
		
		Object object = request.getSession().getAttribute(com.tzdr.web.constants.Constants.TZDR_USER_SESSION);
		UserSessionBean userSessionBean = (UserSessionBean) object;
		
		List<DataMap> futureLoanMoneyDatas =  dataMapService.findByTypeKey("futureLoanMoney");  //获取借款金额基数
		String futureLoanMoney = null;
		if (futureLoanMoneyDatas.size() > 0) {
			DataMap mapObj = futureLoanMoneyDatas.get(0);
			futureLoanMoney = mapObj.getValueKey();
		}
		
		//总操盘保证金=操盘保证金*手数
		BigDecimal inputTotalTraderBond = new BigDecimal("0").add(inputTraderBond).multiply(new BigDecimal(inputTranLever), MathContext.DECIMAL32);
		
		//总操盘保证金=17万*手数
		BigDecimal traderTotal = new BigDecimal(futureLoanMoney).multiply(new BigDecimal(inputTranLever), MathContext.DECIMAL32);
		
		//控制点数=基数*手数
		BigDecimal lossAddPoint = new BigDecimal("3000").multiply(new BigDecimal(inputTranLever), MathContext.DECIMAL32);
		
		//亏损警告线
		BigDecimal lossLine = new BigDecimal("0").add(traderTotal).subtract(inputTotalTraderBond).add(lossAddPoint); 
		
		//单管理费
		BigDecimal manageAmount = new BigDecimal("0");
		
		if(inputTranDay == 1){
			manageAmount = new BigDecimal("0");
		}
		else{
			if(inputTranDay == 2 || inputTranDay == 3){
				manageAmount = new BigDecimal("200");
			}else if(inputTranDay == 4 || inputTranDay == 5){
				manageAmount = new BigDecimal("150");
			}else if(inputTranDay == 6 || inputTranDay == 7){
				manageAmount = new BigDecimal("120");
			}else if(inputTranDay == 8 || inputTranDay == 9){
				manageAmount = new BigDecimal("100");
			}else if(inputTranDay == 10){
				manageAmount = new BigDecimal("80");
			}
		}
		
		//总管理费
		BigDecimal totalManageAmount = new BigDecimal("0").add(manageAmount).multiply(new BigDecimal(inputTranDay), MathContext.DECIMAL32);
		
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
		//交易日
		modelMap.addAttribute("inputTranDay",inputTranDay);
		//管理费
		modelMap.addAttribute("inputManageAmount",this.moneyToStrObject(manageAmount));
		//总管理费
		modelMap.addAttribute("totalManageAmount",this.moneyToStrObject(totalManageAmount));
		//应付金额
		modelMap.addAttribute("payable",this.moneyToStrObject(payable));
		
		modelMap.addAttribute("showAvl",0);
		
		if (userSessionBean != null) {
			String userUid = userSessionBean.getId();
			if (userUid != null) {
				WUser wuser = wUserService.get(userUid);
				//用户余额
				Double avlBal = wuser.getAvlBal();
				//用户余额
				if (payable.compareTo(new BigDecimal(avlBal)) > 0) {
					modelMap.addAttribute("avlBal_user",this.moneyToStrObject(new BigDecimal(avlBal)));
					modelMap.addAttribute("payable_avlBal_user",this.moneyToStrObject(TypeConvert.scale(
							payable.subtract(new BigDecimal(avlBal)),2) ));
					modelMap.addAttribute("showAvl",1);
				}
			}
		}
		
		request.getSession(false).setAttribute("tokenTzdr", UUID.randomUUID());
		return ViewConstants.FSimpleUserTradeViewJsp.FUTRUE_PAY;
	}
	
	@RequestMapping(value = "/day_pay")
	public String payDay(ModelMap modelMap,BigDecimal inputTraderBond, Integer inputTranLever,HttpServletRequest request){
		
		if(inputTraderBond == null){   //判断保证金
			inputTraderBond =  new BigDecimal("0");
		}else if(inputTranLever == null){  //判断手数
			inputTranLever = 0;
		}
		
		inputTraderBond = inputTraderBond.abs();   //绝对值
		
		inputTranLever = Math.abs(inputTranLever); //绝对值
		
		Object object = request.getSession().getAttribute(com.tzdr.web.constants.Constants.TZDR_USER_SESSION);
		UserSessionBean userSessionBean = (UserSessionBean) object;
		
		List<DataMap> futureLoanMoneyDatas =  dataMapService.findByTypeKey("futureLoanMoney");  //获取借款金额基数
		String futureLoanMoney = null;
		if (futureLoanMoneyDatas.size() > 0) {
			DataMap mapObj = futureLoanMoneyDatas.get(0);
			futureLoanMoney = mapObj.getValueKey();
		}
		
		//总操盘保证金=操盘保证金
		BigDecimal inputTotalTraderBond = new BigDecimal("0").add(inputTraderBond);
		
		//总操盘资金=17万*手数
		BigDecimal traderTotal = new BigDecimal(futureLoanMoney).multiply(new BigDecimal(inputTranLever), MathContext.DECIMAL32);
		
		//控制点数=基数*手数
		BigDecimal lossAddPoint = new BigDecimal("3000").multiply(new BigDecimal(inputTranLever), MathContext.DECIMAL32);
		
		//亏损警告线
		BigDecimal lossLine = new BigDecimal("0").add(traderTotal).subtract(inputTotalTraderBond).add(lossAddPoint); 
		
		//应付金额
	    BigDecimal payable =  new BigDecimal("0").add(inputTotalTraderBond).abs();
		
	    //开仓手数
	    modelMap.addAttribute("inputTranLever",inputTranLever);
		//总操盘金
		modelMap.addAttribute("traderTotal",this.moneyToStrObject(traderTotal));
		//操盘保证金
		modelMap.addAttribute("inputTraderBond",inputTraderBond);
		modelMap.addAttribute("traderBond",this.moneyToStrObject(inputTotalTraderBond));
		//亏损平仓线
		modelMap.addAttribute("lossLine",this.moneyToStrObject(lossLine));
		//应付金额
		modelMap.addAttribute("payable",this.moneyToStrObject(payable));
		modelMap.addAttribute("showAvl",0);
		
		if (userSessionBean != null) {
			String userUid = userSessionBean.getId();
			if (userUid != null) {
				WUser wuser = wUserService.get(userUid);
				//用户余额
				Double avlBal = wuser.getAvlBal();
				//用户余额
				if (payable.compareTo(new BigDecimal(avlBal)) > 0) {
					modelMap.addAttribute("avlBal_user",this.moneyToStrObject(new BigDecimal(avlBal)));
					modelMap.addAttribute("payable_avlBal_user",this.moneyToStrObject(TypeConvert.scale(
							payable.subtract(new BigDecimal(avlBal)),2) ));
					modelMap.addAttribute("showAvl",1);
				}
			}
		}
		
		request.getSession(false).setAttribute("tokenTzdr", UUID.randomUUID());
		return ViewConstants.FSimpleUserTradeViewJsp.FUTRUE_DAY_PAY;
	}
	
	@RequestMapping(value = "/paySuccessful")
	public String paySuccessful(ModelMap modelMap,BigDecimal inputTraderBond, Integer inputTranLever,Integer inputTranDay,String tokenTzdr,
			HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		if (inputTraderBond == null || inputTranLever == null || inputTranDay == null) {
		 	this.pay(modelMap, inputTraderBond, inputTranLever,inputTranDay,request);
		 	return ViewConstants.FSimpleUserTradeViewJsp.FUTRUE_PAY;
		}else if(inputTranLever != null && 
				!(inputTranLever == 1 || inputTranLever == 2 || inputTranLever ==3 
				|| inputTranLever == 4 || inputTranLever == 5)){
			this.pay(modelMap, inputTraderBond, inputTranLever,inputTranDay,request);
		 	return ViewConstants.FSimpleUserTradeViewJsp.FUTRUE_PAY;
		}else if(inputTraderBond != null && 
				!(inputTraderBond.compareTo(new BigDecimal("6000")) == 0 
				|| inputTraderBond.compareTo(new BigDecimal("9000")) == 0 
				|| inputTraderBond.compareTo(new BigDecimal("12000")) == 0 
				|| inputTraderBond.compareTo(new BigDecimal("15000")) == 0 )){
			this.pay(modelMap, inputTraderBond, inputTranLever,inputTranDay,request);
		 	return ViewConstants.FSimpleUserTradeViewJsp.FUTRUE_PAY;
		}else if(inputTranDay != null && !(inputTranLever == 1 || inputTranLever == 2 || inputTranLever == 3 
				|| inputTranLever == 4 || inputTranLever == 5 || inputTranLever == 6 || inputTranLever == 7 
				|| inputTranLever == 8 || inputTranLever == 9 || inputTranLever == 10)){
			this.pay(modelMap, inputTraderBond, inputTranLever,inputTranDay,request);
		 	return ViewConstants.FSimpleUserTradeViewJsp.FUTRUE_PAY;
		}
		
		inputTraderBond = inputTraderBond.abs();   //绝对值
		
		inputTranLever = Math.abs(inputTranLever); //绝对值
		
		inputTranDay = Math.abs(inputTranDay); //绝对值
		
		Object object = request.getSession().getAttribute(com.tzdr.web.constants.Constants.TZDR_USER_SESSION);
		String uid = "";
		Object tokenTzdrObj = request.getSession(false).getAttribute("tokenTzdr");
		if (object != null && tokenTzdrObj != null && String.valueOf(tokenTzdrObj).equals(tokenTzdr)) {
			UserSessionBean userSessionBean = (UserSessionBean) object;
			uid = userSessionBean.getId();
		}else {
			this.pay(modelMap, inputTraderBond, inputTranLever,inputTranDay,request);
			return ViewConstants.FSimpleUserTradeViewJsp.FUTRUE_PAY;
		}
		
		if (uid == null) {
			this.pay(modelMap, inputTraderBond, inputTranLever,inputTranDay,request);
			return ViewConstants.FSimpleUserTradeViewJsp.FUTRUE_PAY;
		}
		WUser wuser = wUserService.get(uid);
		
		List<DataMap> futureLoanMoneyDatas =  dataMapService.findByTypeKey("futureLoanMoney");  //获取借款金额基数
		String futureLoanMoney = null;
		if (futureLoanMoneyDatas.size() > 0) {
			DataMap mapObj = futureLoanMoneyDatas.get(0);
			futureLoanMoney = mapObj.getValueKey();
		}
		
		//总操盘保证金=操盘保证金*手数
		BigDecimal inputTotalTraderBond = new BigDecimal("0").add(inputTraderBond).multiply(new BigDecimal(inputTranLever), MathContext.DECIMAL32);
		
		//总操盘保证金=17万*手数
		BigDecimal traderTotal = new BigDecimal(futureLoanMoney).multiply(new BigDecimal(inputTranLever), MathContext.DECIMAL32);
		
		//控制点数=基数*手数
		BigDecimal lossAddPoint = new BigDecimal("3000").multiply(new BigDecimal(inputTranLever), MathContext.DECIMAL32);
		
		//亏损警告线
		BigDecimal lossLine = new BigDecimal("0").add(traderTotal).subtract(inputTotalTraderBond).add(lossAddPoint); 
		
	    //单管理费
		BigDecimal manageAmount = new BigDecimal("0");
		
		//交易费
		BigDecimal totalTranFees = new BigDecimal("0");
		
		if(inputTranDay == 1){
			String  tranDefaultFees = null;
			List<DataMap> defDatas =  dataMapService.findByTypeKey("futureDefTranFees");
			if (defDatas.size() > 0) {
				DataMap mapObj = defDatas.get(0);
				tranDefaultFees = mapObj.getValueKey();
			}
			manageAmount = new BigDecimal("0");
			totalTranFees = new BigDecimal(tranDefaultFees);
		}
		else{
			List<DataMap> futureTranFeesDate =  dataMapService.findByTypeKey("futureTranFees");
			String tranFeesStr = null;
			if (futureTranFeesDate.size() > 0) {
				DataMap mapObj = futureTranFeesDate.get(0);
				tranFeesStr = mapObj.getValueKey();
			}
			totalTranFees = new BigDecimal(tranFeesStr);
			if(inputTranDay == 2 || inputTranDay == 3){
				manageAmount = new BigDecimal("200");
			}else if(inputTranDay == 4 || inputTranDay == 5){
				manageAmount = new BigDecimal("150");
			}else if(inputTranDay == 6 || inputTranDay == 7){
				manageAmount = new BigDecimal("120");
			}else if(inputTranDay == 8 || inputTranDay == 9){
				manageAmount = new BigDecimal("100");
			}else if(inputTranDay == 10){
				manageAmount = new BigDecimal("80");
			}
		}
		
		//总管理费
		BigDecimal totalManageAmount = new BigDecimal("0").add(manageAmount).multiply(new BigDecimal(inputTranDay), MathContext.DECIMAL32);
		
		//应付金额
	    BigDecimal payable =  new BigDecimal("0").add(inputTotalTraderBond).abs().add(totalManageAmount);
		
		if (wuser != null && wuser.getMobile() != null) {
			BigDecimal avlBal = new BigDecimal(wuser.getAvlBal().toString());
			if (avlBal.compareTo(payable) >= 0 ) {
				//总操盘金
				modelMap.addAttribute("traderTotal",this.moneyToStrObject(traderTotal));
				//操盘保证金
				modelMap.addAttribute("traderBond",this.moneyToStrObject(inputTraderBond));
				//亏损平仓线
				modelMap.addAttribute("lossLine",this.moneyToStrObject(lossLine));
				//应付金额
				modelMap.addAttribute("payable",this.moneyToStrObject(payable));
				
				FSimpleUserTrade st = new FSimpleUserTrade();
				st.setUid(uid);
				st.setTraderTotal(traderTotal);
				st.setTraderBond(inputTraderBond);
				st.setTranLever(inputTranLever);
				st.setLineLoss(lossLine);
				st.setFeeManage(totalManageAmount);
				st.setTranFees(totalTranFees);
				st.setTranDay(inputTranDay);
				//审核中
				st.setStateType(1);
				st.setBusinessType(1);  //随心乐
				this.fSimpleUserTradeService.executePayable(st, wuser.getMobile(), payable);
				request.getSession(false).removeAttribute("tokenTzdr");
				return ViewConstants.FSimpleUserTradeViewJsp.FUTRUE_PAY_SUCCESSFUL;
			}
			else {
				if (payable.compareTo(avlBal) > 0) {
					modelMap.addAttribute("avlBal_user",this.moneyToStrObject(avlBal));
					modelMap.addAttribute("payable_avlBal_user",this.moneyToStrObject(TypeConvert.scale(
							payable.subtract(avlBal),2) ));
					modelMap.addAttribute("showAvl",1);
				}
			}
		}
		this.pay(modelMap, inputTraderBond, inputTranLever,inputTranDay,request);
		return ViewConstants.FSimpleUserTradeViewJsp.FUTRUE_PAY;
		
	}
	
	@RequestMapping(value = "/day_paySuccessful")
	public String paySuccessfulDay(ModelMap modelMap,BigDecimal inputTraderBond, Integer inputTranLever,String tokenTzdr,
			HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		if (inputTraderBond == null || inputTranLever == null ) {
		 	this.payDay(modelMap, inputTraderBond, inputTranLever,request);
		 	return ViewConstants.FSimpleUserTradeViewJsp.FUTRUE_DAY_PAY;
		}else if(inputTranLever != null && 
				!(inputTranLever == 3 || inputTranLever == 5 ||  inputTranLever == 8 || inputTranLever == 10)){
			this.payDay(modelMap, inputTraderBond, inputTranLever,request);
		 	return ViewConstants.FSimpleUserTradeViewJsp.FUTRUE_DAY_PAY;
		}else if(inputTraderBond != null && 
				!(inputTraderBond.compareTo(new BigDecimal("20000")) == 0 
				|| inputTraderBond.compareTo(new BigDecimal("40000")) == 0 
				|| inputTraderBond.compareTo(new BigDecimal("70000")) == 0 
				|| inputTraderBond.compareTo(new BigDecimal("100000")) == 0 )){
			this.payDay(modelMap, inputTraderBond, inputTranLever,request);
		 	return ViewConstants.FSimpleUserTradeViewJsp.FUTRUE_DAY_PAY;
		}
		
		inputTraderBond = inputTraderBond.abs();   //绝对值
		
		inputTranLever = Math.abs(inputTranLever); //绝对值
		
		Object object = request.getSession().getAttribute(com.tzdr.web.constants.Constants.TZDR_USER_SESSION);
		String uid = "";
		Object tokenTzdrObj = request.getSession(false).getAttribute("tokenTzdr");
		if (object != null && tokenTzdrObj != null && String.valueOf(tokenTzdrObj).equals(tokenTzdr)) {
			UserSessionBean userSessionBean = (UserSessionBean) object;
			uid = userSessionBean.getId();
		}else {
			this.payDay(modelMap, inputTraderBond, inputTranLever,request);
			return ViewConstants.FSimpleUserTradeViewJsp.FUTRUE_DAY_PAY;
		}
		
		if (uid == null) {
			this.payDay(modelMap, inputTraderBond, inputTranLever,request);
			return ViewConstants.FSimpleUserTradeViewJsp.FUTRUE_DAY_PAY;
		}
		WUser wuser = wUserService.get(uid);
		
		List<DataMap> futureLoanMoneyDatas =  dataMapService.findByTypeKey("futureLoanMoney");  //获取借款金额基数
		String futureLoanMoney = null;
		if (futureLoanMoneyDatas.size() > 0) {
			DataMap mapObj = futureLoanMoneyDatas.get(0);
			futureLoanMoney = mapObj.getValueKey();
		}
		
		//总操盘保证金=操盘保证金*手数
		BigDecimal inputTotalTraderBond = new BigDecimal("0").add(inputTraderBond);
		
		//总操盘资金=17万*手数
		BigDecimal traderTotal = new BigDecimal(futureLoanMoney).multiply(new BigDecimal(inputTranLever), MathContext.DECIMAL32);
		
		//控制点数=基数*手数
		BigDecimal lossAddPoint = new BigDecimal("3000").multiply(new BigDecimal(inputTranLever), MathContext.DECIMAL32);
		
		//亏损警告线
		BigDecimal lossLine = new BigDecimal("0").add(traderTotal).subtract(inputTotalTraderBond).add(lossAddPoint); 
		
		//应付金额
	    BigDecimal payable =  new BigDecimal("0").add(inputTotalTraderBond).abs();
		
		//总管理费
		BigDecimal totalManageAmount = new BigDecimal("0");
		
		//交易费  n/手
		
		List<DataMap>  futureDayTranFees = CacheManager.getDataMapListByTypeKey("futureDayTranFees");
		BigDecimal tranFees = new BigDecimal(futureDayTranFees.get(0).getValueKey());
		if (wuser != null && wuser.getMobile() != null) {
			BigDecimal avlBal = new BigDecimal(wuser.getAvlBal().toString());
			if (avlBal.compareTo(payable) >= 0 ) {
				//总操盘金
				modelMap.addAttribute("traderTotal",this.moneyToStrObject(traderTotal));
				//操盘保证金
				modelMap.addAttribute("traderBond",this.moneyToStrObject(inputTraderBond));
				//亏损平仓线
				modelMap.addAttribute("lossLine",this.moneyToStrObject(lossLine));
				//应付金额
				modelMap.addAttribute("payable",this.moneyToStrObject(payable));
				
				FSimpleUserTrade st = new FSimpleUserTrade();
				st.setUid(uid);
				st.setTraderTotal(traderTotal);
				st.setTraderBond(inputTraderBond);
				st.setTranLever(inputTranLever);
				st.setTranFees(tranFees);
				st.setLineLoss(lossLine);
				st.setFeeManage(totalManageAmount);
				st.setBusinessType(2);
				
				//审核中
				st.setStateType(1);
				this.fSimpleUserTradeService.executePayable(st, wuser.getMobile(), payable);
				request.getSession(false).removeAttribute("tokenTzdr");
				return ViewConstants.FSimpleUserTradeViewJsp.FUTRUE_PAY_SUCCESSFUL;
			}
			else {
				if (payable.compareTo(avlBal) > 0) {
					modelMap.addAttribute("avlBal_user",this.moneyToStrObject(avlBal));
					modelMap.addAttribute("payable_avlBal_user",this.moneyToStrObject(TypeConvert.scale(
							payable.subtract(avlBal),2) ));
					modelMap.addAttribute("showAvl",1);
				}
			}
		}
		this.payDay(modelMap, inputTraderBond, inputTranLever,request);
		return ViewConstants.FSimpleUserTradeViewJsp.FUTRUE_PAY;
		
	}
	
	@RequestMapping(value = "/trade_list")
	public String tradeList(ModelMap modelMap, String index, HttpServletRequest request,HttpServletResponse response){
		modelMap.addAttribute("index", index);
		return ViewConstants.FSimpleUserTradeViewJsp.FUTRUE_TRADE_LIST;
	}

	/**
	 * 
	 * @param response
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/findData")
	@ResponseBody
	public PageInfo<FSimpleUserTradeWebVo> findData(HttpServletResponse response,HttpServletRequest request){
		UserSessionBean userSessionBean=(UserSessionBean) request.getSession().getAttribute(Constants.TZDR_USER_SESSION);
		if (userSessionBean == null) {
			return null;
		}
		String pageIndex = request.getParameter("pageIndex");
		String perPage = request.getParameter("perPage");	
		String type = request.getParameter("type");
		PageInfo<FSimpleUserTradeWebVo> pageInfo = this.fSimpleUserTradeService.findDataList(pageIndex, perPage,type,userSessionBean.getId());
		List<FSimpleUserTradeWebVo> pageResults = pageInfo == null ? null : pageInfo.getPageResults();
		if(pageInfo != null && pageInfo.getPageResults() != null && !pageInfo.getPageResults().isEmpty()){
			for (FSimpleUserTradeWebVo fSimpleUserTradeWebVo : pageResults) {
				if(fSimpleUserTradeWebVo.getStateType() == 1 || fSimpleUserTradeWebVo.getStateType() == -1){
					fSimpleUserTradeWebVo.setUseTradeDay(0);
				}else{
					String startDate =  new SimpleDateFormat("yyyyMMdd").format(new Date(fSimpleUserTradeWebVo.getAppStarttime()*1000));
					String endDate =  new SimpleDateFormat("yyyyMMdd").format(new Date());
					fSimpleUserTradeWebVo.setUseTradeDay(fSimpleUserTradeWebVo.getStateType() == 3 ? fSimpleUserTradeWebVo.getUseTradeDay() : tradeDayService.getTradeDays(startDate, endDate));
				}
			}
			pageInfo.setPageResults(pageResults);
		}
		return pageInfo;
	}
	
	@Autowired
	private RechargeListService rechargeListService;
	
	@Autowired
	private FSimpleUserTradeService fSimpleUserTradeService;
	
	@Autowired
	private WUserService wUserService;

}

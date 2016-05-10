package com.tzdr.web.controller.crudeFreetrial;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.hundsun.t2sdk.common.util.UUID;
import com.tzdr.business.service.contract.ContractParitiesService;
import com.tzdr.business.service.crudeActive.CrudeActiveService;
import com.tzdr.business.service.datamap.DataMapService;
import com.tzdr.business.service.tradecalendar.TradeDayService;
import com.tzdr.business.service.userTrade.FSimpleFtseUserTradeService;
import com.tzdr.business.service.userTrade.FSimpleParitiesService;
import com.tzdr.business.service.wuser.WUserService;
import com.tzdr.common.utils.Dates;
import com.tzdr.common.utils.TypeConvert;
import com.tzdr.common.web.support.JsonResult;
import com.tzdr.domain.web.entity.ContractParities;
import com.tzdr.domain.web.entity.CrudeActive;
import com.tzdr.domain.web.entity.FSimpleFtseUserTrade;
import com.tzdr.domain.web.entity.WUser;
import com.tzdr.web.constants.ViewConstants;
import com.tzdr.web.controller.spread.FteSpreadController;
import com.tzdr.web.utils.UserSessionBean;

@Controller
@RequestMapping("/crudeActive")
public class CrudeActiveController {
	@Autowired
	private DataMapService dataMapService;
	@Autowired
	private WUserService wUserService;
	@Autowired
	private TradeDayService tradeDayService;
	@Autowired
	private  CrudeActiveService crudeActiveService;
	@Autowired
	private  FSimpleFtseUserTradeService fSimpleFtseUserTradeService;
	@Autowired
	private ContractParitiesService contractParitiesService;
	@Autowired
	private FSimpleParitiesService fSimpleParitiesService;
	/**
	 * 资金明细业务类型
	 */
	private final static int MONEYDETAILTYPE = 4;
	private static Logger log = LoggerFactory.getLogger(FteSpreadController.class);
	@RequestMapping(value = "/apply")
	@ResponseBody
	public JsonResult apply(HttpServletRequest request,HttpServletResponse response){
		
		
		Object object = request.getSession().getAttribute(com.tzdr.web.constants.Constants.TZDR_USER_SESSION);
		UserSessionBean userSessionBean = (UserSessionBean) object;
		String uid = userSessionBean.getId();
		JsonResult  jsonResult = new JsonResult(true);
		int num = Integer.parseInt(dataMapService.findByTypeKey("crudeActive").get(0).getValueKey());
		
		if(num<=0){
			jsonResult.setMessage("over");
			return jsonResult;
		}else if(isAagain(uid)){
			jsonResult.setMessage("again");
			return jsonResult;
		}else if(isOld(uid)){
			jsonResult.setMessage("old");
			return jsonResult;
		}else if(!isNoDate()){
			jsonResult.setMessage("wrong");
			return jsonResult;
		}else if(isFtseActive(uid)){
			jsonResult.setMessage("ftse");
			return jsonResult;
		}else{
			jsonResult.setMessage("success");
			CrudeActive crudeActive = new CrudeActive();
			crudeActive.setCreateTime(Dates.getCurrentLongDate());
			crudeActive.setUid(uid);
			crudeActive.setType(1);
			crudeActiveService.apply(crudeActive);
			
			return jsonResult;
		}
	}
	
	/**
	 * 判断是否为老用户
	 */
	private boolean isOld(String uid){
		
		return fSimpleFtseUserTradeService.isHave(uid,6);
	}
	/**
	 * 判断是否是申请时间（11月 20-27 交易日的 17-24）
	 */
	private boolean isNoDate(){
		boolean flag = false;
		String currentDay = Dates.format(new Date(),
				Dates.CHINESE_DATE_FORMAT_LONG);
		boolean isTrade = tradeDayService.isTradeDay(currentDay);
		Calendar now = Calendar.getInstance();
		int hour = now.get(Calendar.HOUR_OF_DAY);
		Calendar from = Calendar.getInstance();
		from.set(2015,11-1,20,17,0,0);
		Calendar to = Calendar.getInstance();
		to.set(2015,11-1,26,0,0,0);
		
		if(isTrade && now.after(from) && now.before(to) && 17<=hour && hour<24){
	    	flag = true;
	    }
		
		
		return flag;
	}
	/**
	 * 判断是否名额未使用
	 */
	
	private boolean isAagain(String uid){
		
		return crudeActiveService.isAgain(uid);
	}
	/**
	 * 判断是否申请过A50活动
	 */
	
	private boolean isFtseActive(String uid){
		
		return crudeActiveService.isFtseActive(uid);
	}
	/**
	 * 判断名额是否过期
	 */
	@RequestMapping(value = "/isOut")
	@ResponseBody
	private JsonResult isOut(HttpServletRequest request,HttpServletResponse response){
		Object object = request.getSession().getAttribute(com.tzdr.web.constants.Constants.TZDR_USER_SESSION);
		UserSessionBean userSessionBean = (UserSessionBean) object;
		String uid = userSessionBean.getId();
		JsonResult  jsonResult = new JsonResult(true);
		if(crudeActiveService.isOut(uid)){
			jsonResult.setMessage("out");
			return jsonResult;
		}else{
			jsonResult.setMessage("success");
			return jsonResult;
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
	 * 方案页面
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/index")
	public String index(ModelMap modelMap,HttpServletRequest request,HttpServletResponse response){
		return ViewConstants.NOT_FOUND_VIEW;
		/*
		 * ContractParities newConfig = contractParitiesService.get("00006");
		 * modelMap.put("contract", newConfig.getContract()); return
		 * ViewConstants.CrudeActiveJsp.INDEX;
		 */
	}
	
	@RequestMapping(value = "/pay")
	public String pay(ModelMap modelMap,BigDecimal inputTraderBond, Integer inputTranLever,HttpServletRequest request,RedirectAttributes attr){

		Object object = request.getSession().getAttribute(com.tzdr.web.constants.Constants.TZDR_USER_SESSION);
		UserSessionBean userSessionBean = (UserSessionBean) object;
		
		//应付金额
		BigDecimal payable =  new BigDecimal("1");

		//开仓手数
		modelMap.addAttribute("inputTranLever",1);
		
		//总操盘金
		modelMap.addAttribute("traderTotal",this.moneyToStrObject(new BigDecimal("6500")));
		
		//操盘保证金
		modelMap.addAttribute("inputTraderBond",new BigDecimal("2150"));
		
		//总操盘保证金
		modelMap.addAttribute("traderBond",this.moneyToStrObject(new BigDecimal("2150")));
		
		//亏损平仓线
		modelMap.addAttribute("lossLine",this.moneyToStrObject(new BigDecimal("6440")));
		
		//管理费
		modelMap.addAttribute("inputManageAmount",this.moneyToStrObject(new BigDecimal("0")));
		
		//总管理费
		modelMap.addAttribute("totalManageAmount",this.moneyToStrObject(new BigDecimal("0")));
		
		//交易费
		modelMap.addAttribute("inputTranFees",this.moneyToStrObject(new BigDecimal("125")));
		
		//应付金额
		modelMap.addAttribute("payable",this.moneyToStrObject(payable));

		modelMap.addAttribute("showAvl",0);

		String userUid = userSessionBean.getId();
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
		request.getSession(false).setAttribute("tokenTzdr", UUID.randomUUID());
		return ViewConstants.CrudeActiveJsp.PAY;
	}
	
	@RequestMapping(value = "/paySuccessful")
	public String paySuccessful(ModelMap modelMap,BigDecimal inputTraderBond, Integer inputTranLever,String tokenTzdr,
			HttpServletRequest request,HttpServletResponse response,RedirectAttributes attr) throws Exception{
		
		Object object = request.getSession().getAttribute(com.tzdr.web.constants.Constants.TZDR_USER_SESSION);
		String uid = "";
		Object tokenTzdrObj = request.getSession(false).getAttribute("tokenTzdr");
		if (object != null && tokenTzdrObj != null && String.valueOf(tokenTzdrObj).equals(tokenTzdr)) {
			UserSessionBean userSessionBean = (UserSessionBean) object;
			uid = userSessionBean.getId();
		}else {
			this.pay(modelMap, inputTraderBond, inputTranLever,request,attr);
			return ViewConstants.CrudeActiveJsp.PAY;
		}
		
		if (uid == null) {
			this.pay(modelMap, inputTraderBond, inputTranLever,request,attr);
			return ViewConstants.CrudeActiveJsp.PAY;
		}
		
		//获取用户信息
		WUser wuser = wUserService.get(uid);
		
		//总操盘保证金=操盘保证金
		BigDecimal inputTotalTraderBond = new BigDecimal("2150");
		
		//总操盘保证金=保证金金额
		BigDecimal traderTotal = new BigDecimal("0").add(new BigDecimal("6500"));
		
		//亏损警告线
		BigDecimal lossLine = new BigDecimal("0").add(new BigDecimal("6440"));
		
		//总管理费
		BigDecimal totalManageAmount = new BigDecimal("0");

		//应付金额
		BigDecimal payable =  new BigDecimal("1");
		
		//交易费
		BigDecimal totalTranFees = new BigDecimal("125");
		
		if (wuser != null && wuser.getMobile() != null) {
			BigDecimal avlBal = new BigDecimal(wuser.getAvlBal().toString());
			if (avlBal.compareTo(payable) >= 0 ) {
				FSimpleFtseUserTrade st = new FSimpleFtseUserTrade();
				st.setUid(uid);
				st.setTraderTotal(traderTotal);
				st.setTraderBond(new BigDecimal("0"));
				st.setTranLever(inputTranLever);
				st.setLineLoss(lossLine);
				st.setFeeManage(totalManageAmount);
				st.setTranFees(totalTranFees);
				//审核中
				st.setStateType(1);
				st.setBusinessType(6);  //国际原油
				//入金金额(美元)
				BigDecimal goldenMoney = new BigDecimal("0");
				st.setGoldenMoney(goldenMoney);
				this.fSimpleFtseUserTradeService.crudeActivePayable(st, wuser.getMobile(), payable,uid);
				request.getSession(false).removeAttribute("tokenTzdr");
				return ViewConstants.CrudeActiveJsp.PAY_SUCCESSFUL;
			}
			else {
				if (payable.compareTo(avlBal) > 0) {
					modelMap.addAttribute("avlBal_user",this.moneyToStrObject(avlBal));
					modelMap.addAttribute("payable_avlBal_user",this.moneyToStrObject(TypeConvert.scale(payable.subtract(avlBal),2) ));
					modelMap.addAttribute("showAvl",1);
				}
			}
		}
		this.pay(modelMap, inputTraderBond, inputTranLever,request,attr);
		return ViewConstants.CrudeActiveJsp.PAY;
	}
}

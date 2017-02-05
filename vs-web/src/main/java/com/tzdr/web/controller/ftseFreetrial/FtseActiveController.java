package com.tzdr.web.controller.ftseFreetrial;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Calendar;

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
import com.tzdr.business.service.datamap.DataMapService;
import com.tzdr.business.service.ftseActive.FtseActiveService;
import com.tzdr.business.service.tradecalendar.TradeDayService;
import com.tzdr.business.service.userTrade.FSimpleFtseUserTradeService;
import com.tzdr.business.service.userTrade.FSimpleParitiesService;
import com.tzdr.business.service.wuser.WUserService;
import com.tzdr.common.utils.Dates;
import com.tzdr.common.utils.TypeConvert;
import com.tzdr.common.web.support.JsonResult;
import com.tzdr.domain.web.entity.FSimpleFtseUserTrade;
import com.tzdr.domain.web.entity.FtseActive;
import com.tzdr.domain.web.entity.WUser;
import com.tzdr.web.constants.ViewConstants;
import com.tzdr.web.controller.spread.FteSpreadController;
import com.tzdr.web.utils.UserSessionBean;
/**
 * <B>说明: </B>A50 推广活动
 * @author LiuYang
 *
 * 2016年1月20日 上午10:32:33
 */
@Controller
@RequestMapping("/ftseActive")
public class FtseActiveController {
	@Autowired
	private DataMapService dataMapService;
	@Autowired
	private WUserService wUserService;
	@Autowired
	private  TradeDayService tradeDayService;
	@Autowired
	private  FtseActiveService ftseActiveService;
	@Autowired
	private  FSimpleFtseUserTradeService fSimpleFtseUserTradeService;
	@Autowired
	private ContractParitiesService contractParitiesService;
	@Autowired
	private FSimpleParitiesService fSimpleParitiesService;
	
	private static Logger log = LoggerFactory.getLogger(FteSpreadController.class);
	/**
	 * 申请页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/apply")
	@ResponseBody
	public JsonResult apply(HttpServletRequest request,HttpServletResponse response){
		
		
		Object object = request.getSession().getAttribute(com.tzdr.web.constants.Constants.TZDR_USER_SESSION);
		UserSessionBean userSessionBean = (UserSessionBean) object;
		String uid = userSessionBean.getId();
		JsonResult  jsonResult = new JsonResult(true);
		int num = Integer.parseInt(dataMapService.findByTypeKey("ftseActive").get(0).getValueKey());
		
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
		}else{
			jsonResult.setMessage("success");
			FtseActive ftseActive = new FtseActive();
			ftseActive.setCreateTime(Dates.getCurrentLongDate());
			ftseActive.setUid(uid);
			ftseActive.setType(1);
			ftseActiveService.apply(ftseActive);
			
			return jsonResult;
		}
	}
	
	/**
	 * 判断是否为老用户
	 */
	private boolean isOld(String uid){
		
		return fSimpleFtseUserTradeService.isHave(uid,0);
	}
	/**
	 * 判断是否是申请时间（11月 16-20 交易日的 9-15）
	 */
	private boolean isNoDate(){
		boolean flag = false;
		boolean isTrade = tradeDayService.isTradeTime();
		Calendar now = Calendar.getInstance();
		Calendar from = Calendar.getInstance();
		from.set(2015,11-1,15);
		Calendar to = Calendar.getInstance();
		to.set(2015,11-1,21);
	    if(now.after(from) && now.before(to) && isTrade){
	    	flag = true;
	    }
		
		
		return flag;
	}
	
	/**
	 * 判断是否名额未使用
	 */
	
	private boolean isAagain(String uid){
		
		return ftseActiveService.isAgain(uid);
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
		if(ftseActiveService.isOut(uid)){
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
		 * ContractParities newConfig = contractParitiesService.get("00001");
		 * modelMap.put("contract", newConfig.getContract()); return
		 * ViewConstants.FtseActiveJsp.INDEX;
		 */
	}
	/**
	 * 支付页面
	 * @param modelMap
	 * @param inputTraderBond
	 * @param inputTranLever
	 * @param request
	 * @param attr
	 * @return
	 */
	@RequestMapping(value = "/pay")
	public String pay(ModelMap modelMap,BigDecimal inputTraderBond, Integer inputTranLever,HttpServletRequest request,RedirectAttributes attr){

		Object object = request.getSession().getAttribute(com.tzdr.web.constants.Constants.TZDR_USER_SESSION);
		UserSessionBean userSessionBean = (UserSessionBean) object;
		//开仓手数
		modelMap.addAttribute("inputTranLever",1);
		
		//总操盘金
		modelMap.addAttribute("traderTotal",2687.5);
		
		//操盘保证金
		modelMap.addAttribute("inputTraderBond",687.5);
		
		//总操盘保证金
		modelMap.addAttribute("traderBond",687.5);
		
		//亏损平仓线
		modelMap.addAttribute("lossLine",2631);
		
		//管理费
		modelMap.addAttribute("inputManageAmount",0);
		
		//总管理费
		modelMap.addAttribute("totalManageAmount",0);
		
		//交易费
		modelMap.addAttribute("inputTranFees",58);
		
		//应付金额
		modelMap.addAttribute("payable",1);

		modelMap.addAttribute("showAvl",0);

		String userUid = userSessionBean.getId();
		WUser wuser = wUserService.get(userUid);
		
		//用户余额
		Double avlBal = wuser.getAvlBal();
		
		//用户余额
		if (new BigDecimal("1").compareTo(new BigDecimal(avlBal)) > 0) {
			modelMap.addAttribute("avlBal_user",this.moneyToStrObject(new BigDecimal(avlBal)));
			modelMap.addAttribute("payable_avlBal_user",this.moneyToStrObject(TypeConvert.scale(
					new BigDecimal("1").subtract(new BigDecimal(avlBal)),2) ));
			modelMap.addAttribute("showAvl",1);
		}
		request.getSession(false).setAttribute("tokenTzdr", UUID.randomUUID());
		return ViewConstants.FtseActiveJsp.PAY;
	}
	/**
	 * 支付成功页面
	 * @param modelMap
	 * @param inputTraderBond
	 * @param inputTranLever
	 * @param tokenTzdr
	 * @param request
	 * @param response
	 * @param attr
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/paySuccessful")
	public String paySuccessful(ModelMap modelMap,BigDecimal inputTraderBond, Integer inputTranLever,String tokenTzdr,
			HttpServletRequest request,HttpServletResponse response,RedirectAttributes attr) throws Exception{
		
		
		inputTraderBond = inputTraderBond.abs();   //绝对值
		
		inputTranLever = Math.abs(inputTranLever); //绝对值
		
		Object object = request.getSession().getAttribute(com.tzdr.web.constants.Constants.TZDR_USER_SESSION);
		String uid = "";
		Object tokenTzdrObj = request.getSession(false).getAttribute("tokenTzdr");

		if (object != null && tokenTzdrObj != null && String.valueOf(tokenTzdrObj).equals(tokenTzdr)) {
			UserSessionBean userSessionBean = (UserSessionBean) object;
			uid = userSessionBean.getId();
		}else {
			this.pay(modelMap, inputTraderBond, inputTranLever,request,attr);
			return ViewConstants.FtseActiveJsp.PAY;
		}
		//获取用户信息
		WUser wuser = wUserService.get(uid);
		
		//操盘保证金
		BigDecimal inputTotalTraderBond = new BigDecimal("0");
		
		//总操盘金
		BigDecimal traderTotal = new BigDecimal("2687.5");
		
		//亏损警告线
		BigDecimal lossLine = new BigDecimal("2631");
		
		//管理费
		BigDecimal manageAmount = new BigDecimal("0");

		//应付金额
		BigDecimal payable =  new BigDecimal("1");
		
		//交易费
		BigDecimal totalTranFees = new BigDecimal("58");
		
		if (wuser != null && wuser.getMobile() != null) {
			BigDecimal avlBal = new BigDecimal(wuser.getAvlBal().toString());
			if (avlBal.compareTo(payable) >= 0 ) {
				FSimpleFtseUserTrade st = new FSimpleFtseUserTrade();
				st.setUid(uid);
				st.setTraderTotal(traderTotal);
				st.setTraderBond(inputTotalTraderBond);
				st.setTranLever(inputTranLever);
				st.setLineLoss(lossLine);
				st.setFeeManage(manageAmount);
				st.setTranFees(totalTranFees);
				//审核中
				st.setStateType(1);
				st.setBusinessType(0);  //富时A50
				//入金金额(美元)
				BigDecimal goldenMoney = new BigDecimal("0");
				st.setGoldenMoney(goldenMoney);
				this.fSimpleFtseUserTradeService.activePayable(st, wuser.getMobile(), payable,uid);
				request.getSession(false).removeAttribute("tokenTzdr");
				return ViewConstants.FtseActiveJsp.PAY_SUCCESSFUL;
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
		return ViewConstants.FtseActiveJsp.PAY;
	}
}

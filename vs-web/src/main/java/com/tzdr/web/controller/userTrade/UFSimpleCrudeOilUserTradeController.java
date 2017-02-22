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
 * <p></p>
 * @author WangPinQun
 * @see
 * UFSimpleCrudeOilUserTradeController
 * @version 2.0
 * 2015骞�0鏈�2鏃ヤ笅鍗�9:33:13
 */
@Controller
@RequestMapping("/usercrudeoil")
public class UFSimpleCrudeOilUserTradeController {

	private static Logger log = LoggerFactory.getLogger(UFSimpleCrudeOilUserTradeController.class);

	@Autowired
	private FSimpleConfigService fSimpleConfigService;
	
	@Autowired
	private FSimpleFtseUserTradeService fSimpleFtseUserTradeService;
	
	@Autowired
	private FSimpleParitiesService fSimpleParitiesService;
	
	@Autowired
	private WUserService wUserService;
	
	@Autowired
	private  TradeDayService tradeDayService;
	@Autowired
	private DataMapService dataMapService;
	
	@Autowired
	private FSimpleCouponService fSimpleCouponService;

	
	@Autowired
	private ContractParitiesService contractParitiesService;
	
	@Autowired
	private MessagePromptService messagePromptService;
	/**
	 * 姹囩巼绫诲瀷
	 */
	private final static int PARITIESTYPE = 1;
	
	/**
	 * 鏂规閰嶇疆绫诲瀷
	 */
	private final static int CONFIGTYPE = 6;
	
	/**
	 * 璧勯噾鏄庣粏涓氬姟绫诲瀷
	 */
	private final static int MONEYDETAILTYPE = 4;
	
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
	
	@RequestMapping(value = "/pay")
	public String pay(HttpServletResponse response,ModelMap modelMap,BigDecimal inputTraderBond, Integer inputTranLever,HttpServletRequest request,RedirectAttributes attr){

		Object object = request.getSession().getAttribute(com.tzdr.web.constants.Constants.TZDR_USER_SESSION);
		UserSessionBean userSessionBean = (UserSessionBean) object;
		
		if(inputTraderBond == null){   //鍒ゆ柇淇濊瘉閲�
			inputTraderBond =  new BigDecimal("0");
		}

		if(inputTranLever == null){  //鍒ゆ柇鎵嬫暟
			inputTranLever = 0;
		}

		inputTraderBond = inputTraderBond.abs();   //缁濆鍊�

		inputTranLever = Math.abs(inputTranLever); //缁濆鍊�

		FSimpleConfig fSimpleConfig = fSimpleConfigService.getFSimpleConfig(CONFIGTYPE,String.valueOf(inputTranLever));  //鑾峰彇閰嶇疆鏂规淇℃伅

		//绯荤粺淇濊瘉閲戦噾棰�
		BigDecimal traderBond = fSimpleConfig != null ? fSimpleConfig.getTraderBond() : new BigDecimal("0");
		
		//绯荤粺鎵嬫暟
		Integer tranLever = fSimpleConfig != null ? Integer.valueOf(fSimpleConfig.getTranLever()) : Integer.valueOf(0);
		
		//绯荤粺鎿嶇洏閲戦
		BigDecimal traderMoney = fSimpleConfig != null ? fSimpleConfig.getTraderMoney() : new BigDecimal("0");
		
		//绯荤粺浜忔崯璀﹀憡绾�
		BigDecimal LineLoss = fSimpleConfig != null ? fSimpleConfig.getLineLoss() : new BigDecimal("0");
		
		//绯荤粺绠＄悊璐�
		BigDecimal feeManage = fSimpleConfig != null ? fSimpleConfig.getFeeManage() : new BigDecimal("0");
		
		//绯荤粺浜ゆ槗鎵嬬画璐�
		BigDecimal tranFees = fSimpleConfig != null ? fSimpleConfig.getTranFees() : new BigDecimal("0");

		//鎬绘搷鐩樹繚璇侀噾=鎿嶇洏淇濊瘉閲�
		BigDecimal inputTotalTraderBond = new BigDecimal("0").add(traderBond);

		//鎬绘搷鐩樹繚璇侀噾=淇濊瘉閲戦噾棰�
		BigDecimal traderTotal = new BigDecimal("0").add(traderMoney);

		//浜忔崯璀﹀憡绾�
		BigDecimal lossLine = new BigDecimal("0").add(LineLoss);

		//鍗曠鐞嗚垂
		BigDecimal manageAmount = new BigDecimal("0").add(feeManage);

		//鎬荤鐞嗚垂
		BigDecimal totalManageAmount = new BigDecimal("0").add(manageAmount);
		
		//搴斾粯閲戦
		BigDecimal payable =  new BigDecimal("0").add(inputTotalTraderBond).abs().add(totalManageAmount);

		//寮�粨鎵嬫暟
		modelMap.addAttribute("inputTranLever",tranLever);
		
		//鎬绘搷鐩橀噾
		modelMap.addAttribute("traderTotal",this.moneyToStrObject(traderTotal));
		
		//鎿嶇洏淇濊瘉閲�
		modelMap.addAttribute("inputTraderBond",traderBond);
		
		//鎬绘搷鐩樹繚璇侀噾
		modelMap.addAttribute("traderBond",this.moneyToStrObject(inputTotalTraderBond));
		
		//浜忔崯骞充粨绾�
		modelMap.addAttribute("lossLine",this.moneyToStrObject(lossLine));
		
		//绠＄悊璐�
		modelMap.addAttribute("inputManageAmount",this.moneyToStrObject(manageAmount));
		
		//鎬荤鐞嗚垂
		modelMap.addAttribute("totalManageAmount",this.moneyToStrObject(totalManageAmount));
		
		//浜ゆ槗璐�
		modelMap.addAttribute("inputTranFees",this.moneyToStrObject(tranFees));
		
		//搴斾粯閲戦
		modelMap.addAttribute("payable",payable);

		modelMap.addAttribute("showAvl",0);

		String userUid = userSessionBean.getId();
		WUser wuser = wUserService.get(userUid);
		
		//鐢ㄦ埛浣欓
		Double avlBal = wuser.getAvlBal();
		modelMap.addAttribute("avlBal",avlBal);
		
		// 浠ｉ噾鍒�
		List<Map<String, Object>> voucher = this.fSimpleCouponService.queryCouponByUserId(userUid, 2, 6);
		modelMap.put("voucher", voucher);
		
		//鐢ㄦ埛浣欓+鏈�ぇ浠ｉ噾鍒告槸鍚﹀厖瓒�
		BigDecimal voucherMoney = new BigDecimal(0);
		if(null != voucher && !voucher.isEmpty()) {
			voucherMoney = voucherMoney.add(new BigDecimal(voucher.get(0).get("money").toString()));
		}
		if (payable.compareTo(new BigDecimal(avlBal).add(voucherMoney)) > 0) {
			modelMap.addAttribute("avlBal_user",this.moneyToStrObject(new BigDecimal(avlBal)));
			modelMap.addAttribute("payable_avlBal_user",this.moneyToStrObject(TypeConvert.scale(
					payable.subtract(new BigDecimal(avlBal)),2) ));
			modelMap.addAttribute("showAvl",1);
		}
		Object uuid = UUID.randomUUID();
		request.getSession(false).setAttribute("tokenTzdr", uuid);
		CookiesUtil.addCookie(response, "tokenTzdr", String.valueOf(uuid), 600);
		ContractParities newConfig = contractParitiesService.get("00006");
		modelMap.put("contract", newConfig.getContract());
		return ViewConstants.FSimpleCrudeOilUserTradeJsp.CRUDE_OIL_PAY;
	}
	
	@RequestMapping(value = "/paySuccessful")
	public String paySuccessful(ModelMap modelMap,BigDecimal inputTraderBond, Integer inputTranLever,String tokenTzdr,String voucherId,
			HttpServletRequest request,HttpServletResponse response,RedirectAttributes attr) throws Exception{
		
		if (inputTraderBond == null || inputTranLever == null) {
		 	this.pay(response,modelMap, inputTraderBond, inputTranLever,request,attr);
		 	return ViewConstants.FSimpleCrudeOilUserTradeJsp.CRUDE_OIL_PAY;
		}
		
		FSimpleConfig fSimpleConfig = fSimpleConfigService.getFSimpleConfig(CONFIGTYPE,String.valueOf(inputTranLever));  //鑾峰彇閰嶇疆鏂规淇℃伅
		
		if(fSimpleConfig == null){ 
			this.pay(response,modelMap, inputTraderBond, inputTranLever,request,attr);
		 	return ViewConstants.FSimpleCrudeOilUserTradeJsp.CRUDE_OIL_PAY;
		}
		
		if(fSimpleConfig.getTraderBond().compareTo(inputTraderBond) != 0 ){   //鍒ゆ柇褰撳墠閰嶇疆鏂规鍗曟墜淇濊瘉閲戞槸姝ｇ‘
			this.pay(response,modelMap, inputTraderBond, inputTranLever,request,attr);
		 	return ViewConstants.FSimpleCrudeOilUserTradeJsp.CRUDE_OIL_PAY;
		}
		
		inputTraderBond = inputTraderBond.abs();   //缁濆鍊�
		
		inputTranLever = Math.abs(inputTranLever); //缁濆鍊�
		
		Object object = request.getSession().getAttribute(com.tzdr.web.constants.Constants.TZDR_USER_SESSION);
		String uid = "";
		Object tokenTzdrObj = request.getSession(false).getAttribute("tokenTzdr");
		if (object != null && tokenTzdrObj != null && String.valueOf(tokenTzdrObj).equals(tokenTzdr)) {
			UserSessionBean userSessionBean = (UserSessionBean) object;
			uid = userSessionBean.getId();
		}else {
			this.pay(response,modelMap, inputTraderBond, inputTranLever,request,attr);
			return ViewConstants.FSimpleCrudeOilUserTradeJsp.CRUDE_OIL_PAY;
		}
		
		if (uid == null) {
			this.pay(response,modelMap, inputTraderBond, inputTranLever,request,attr);
			return ViewConstants.FSimpleCrudeOilUserTradeJsp.CRUDE_OIL_PAY;
		}
		
		//鑾峰彇鐢ㄦ埛淇℃伅
		WUser wuser = wUserService.get(uid);
		
		//鎬绘搷鐩樹繚璇侀噾=鎿嶇洏淇濊瘉閲�
		BigDecimal inputTotalTraderBond = new BigDecimal("0").add(inputTraderBond);
		
		//鎬绘搷鐩樹繚璇侀噾=淇濊瘉閲戦噾棰�
		BigDecimal traderTotal = new BigDecimal("0").add(fSimpleConfig.getTraderMoney());
		
		//浜忔崯璀﹀憡绾�
		BigDecimal lossLine = new BigDecimal("0").add(fSimpleConfig.getLineLoss());
		
		//鍗曠鐞嗚垂
		BigDecimal manageAmount = new BigDecimal("0").add(fSimpleConfig.getFeeManage());

		//鎬荤鐞嗚垂
		BigDecimal totalManageAmount = new BigDecimal("0").add(manageAmount);

		//搴斾粯閲戦
		BigDecimal payable =  new BigDecimal("0").add(inputTotalTraderBond).abs().add(totalManageAmount);
		
		//浜ゆ槗璐�
		BigDecimal totalTranFees = new BigDecimal("0").add(fSimpleConfig.getTranFees());
		
		if (wuser != null && wuser.getMobile() != null) {
			BigDecimal avlBal = new BigDecimal(wuser.getAvlBal().toString());
			// 楠岃瘉浠ｉ噾鍒�
			FSimpleCoupon voucher = this.fSimpleCouponService.get(voucherId);
			BigDecimal voucherActualMoney = null; // 浠ｉ噾鍒镐娇鐢ㄩ噾棰�
			if(this.fSimpleCouponService.isCouponValid(voucher, 2, 6)) {
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
				//瀹℃牳涓�
				st.setStateType(1);
				st.setBusinessType(6);  //鍥介檯鍘熸补
				//鍏ラ噾閲戦(缇庡厓)
				BigDecimal goldenMoney = new BigDecimal("0").add(fSimpleConfig.getGoldenMoney());
				st.setGoldenMoney(goldenMoney);
				FSimpleFtseUserTrade fSimpleFtseUserTrade = null;
				// 璁剧疆浠ｉ噾鍒哥浉鍏充俊鎭�
				if(this.fSimpleCouponService.isCouponValid(voucher, 2, 6)) {
					st.setVoucherId(voucher.getId());
					st.setVoucherMoney(voucher.getMoney());
					st.setVoucherActualMoney(voucherActualMoney);
					fSimpleFtseUserTrade = this.fSimpleFtseUserTradeService.executePayable(st, voucher, wuser.getMobile(), payable,"鎶曡祫鍥介檯鍘熸补鏈熻揣鐢宠锛堝垝娆撅級銆�,MONEYDETAILTYPE);
				} else {
					fSimpleFtseUserTrade = this.fSimpleFtseUserTradeService.executePayable(st, wuser.getMobile(), payable,"鎶曡祫鍥介檯鍘熸补鏈熻揣鐢宠锛堝垝娆撅級銆�,MONEYDETAILTYPE);
				}
				request.getSession(false).removeAttribute("tokenTzdr");
				//TODO 鐢宠鎿嶇洏锛屾敮浠樻垚鍔熺粰宸ヤ綔浜哄憳鍙戦�Email
				try {
					
						if(wuser != null){
							messagePromptService.sendMessage(PromptTypes.isFutures, wuser.getMobile());
						}
					
				} catch (Exception e) {
					log.info("鍙戦�閭欢澶辫触",e);
				}
				if(fSimpleFtseUserTrade != null){
					modelMap.addAttribute("stateType", fSimpleFtseUserTrade.getStateType());
				}
				return ViewConstants.FSimpleCrudeOilUserTradeJsp.CRUDE_OIL_PAY_SUCCESSFUL;
			}
			else {
				if (payable.compareTo(avlBal) > 0) {
					modelMap.addAttribute("avlBal_user",this.moneyToStrObject(avlBal));
					modelMap.addAttribute("payable_avlBal_user",this.moneyToStrObject(TypeConvert.scale(payable.subtract(avlBal),2) ));
					modelMap.addAttribute("showAvl",1);
				}
			}
		}
		this.pay(response,modelMap, inputTraderBond, inputTranLever,request,attr);
	
		
		return ViewConstants.FSimpleCrudeOilUserTradeJsp.CRUDE_OIL_PAY;
	}
	
	/**
	 * 鑾峰彇褰撳墠姹囩巼
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/getparities")
	@ResponseBody
	public JsonResult  getParities(ModelMap modelMap,HttpServletRequest request,HttpServletResponse response){
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
	@RequestMapping(value = "/apply_end_trade" , method = RequestMethod.POST)
	@ResponseBody
	public JsonResult applyEndTrade(ModelMap modelMap,String id, HttpServletRequest request,HttpServletResponse response){
		
		JsonResult  jsonResult = new JsonResult(true);
		
		if(StringUtil.isBlank(id)){
			jsonResult.setMessage("notFindData");
			return jsonResult;
		}
		
		UserSessionBean userSessionBean=(UserSessionBean) request.getSession().getAttribute(Constants.TZDR_USER_SESSION);
		
		FSimpleFtseUserTrade fSimpleFtseUserTrade = fSimpleFtseUserTradeService.get(id);
		
		if(fSimpleFtseUserTrade == null || !userSessionBean.getId().equals(fSimpleFtseUserTrade.getUid())){
			jsonResult.setMessage("notFindData");
			return jsonResult;
		}
		
		if(fSimpleFtseUserTrade.getStateType() == 2){   //涓嶈兘閲嶅鐢宠
			jsonResult.setMessage("notRepetitionApply");
			return jsonResult;
		}
		
		if(fSimpleFtseUserTrade.getStateType() != 4){  //鍒ゆ柇鏄笉鏄搷鐩樹腑 
			jsonResult.setMessage("applyEndTradeFail");
			return jsonResult;
		}
		
		FSimpleParities fSimpleParities = fSimpleParitiesService.getFSimpleParities(PARITIESTYPE);
		
		fSimpleFtseUserTrade.setEndParities(fSimpleParities.getParities());  //鐢宠缁堢粨褰撳墠姹囩巼
		fSimpleFtseUserTrade.setStateType(2);  //鐢宠缁堢粨鏂规
		fSimpleFtseUserTrade.setAppEndTime(Dates.getCurrentLongDate());  //鐢宠缁堢粨鏃堕棿
		
		fSimpleFtseUserTradeService.update(fSimpleFtseUserTrade);
		WUser wuser = wUserService.get(userSessionBean.getId());   //鑾峰彇鐢ㄦ埛淇℃伅
		//TODO 缁堢粨鏂规锛岀粰宸ヤ綔浜哄憳鍙戦�Email
		try {
			messagePromptService.sendMessage(PromptTypes.isEndScheme, wuser.getMobile());
		} catch (Exception e) {
			log.info("鍙戦�閭欢澶辫触",e);
		}
		
		return jsonResult;
	}
	
	
	@RequestMapping(value = "/trade_list")
	public String tradeList(ModelMap modelMap, String index, HttpServletRequest request,HttpServletResponse response){
		modelMap.addAttribute("index", index);
		return ViewConstants.FSimpleCrudeOilUserTradeJsp.CRUDE_OIL_TRADE_LIST;
	}
	
	/**
	 * 
	 * @param response
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/findData")
	@ResponseBody
	public PageInfo<FSimpleFtseUserTradeWebVo> findData(HttpServletResponse response,HttpServletRequest request){
		UserSessionBean userSessionBean=(UserSessionBean) request.getSession().getAttribute(Constants.TZDR_USER_SESSION);
		if (userSessionBean == null) {
			return null;
		}
		String pageIndex = request.getParameter("pageIndex");
		String perPage = request.getParameter("perPage");	
		String type = request.getParameter("type");
		PageInfo<FSimpleFtseUserTradeWebVo> pageInfo = this.fSimpleFtseUserTradeService.findDataList(pageIndex, perPage,type,userSessionBean.getId());
		List<FSimpleFtseUserTradeWebVo> pageResults = pageInfo == null ? null : pageInfo.getPageResults();
		if(pageInfo != null && pageInfo.getPageResults() != null && !pageInfo.getPageResults().isEmpty()){
			for (FSimpleFtseUserTradeWebVo fSimpleFtseUserTradeWebVo : pageResults) {
				if(fSimpleFtseUserTradeWebVo.getStateType() == 1 || fSimpleFtseUserTradeWebVo.getStateType() == 5){
					fSimpleFtseUserTradeWebVo.setUseTradeDay(0);
				}else{
					fSimpleFtseUserTradeWebVo.setUseTradeDay(fSimpleFtseUserTradeWebVo.getStateType() == 6 ? fSimpleFtseUserTradeWebVo.getUseTradeDay() : tradeDayService.getTradeDayNum(fSimpleFtseUserTradeWebVo.getAppStarttime(), 14));
				}
			}
			pageInfo.setPageResults(pageResults);
		}
		return pageInfo;
	}
	
	/**
	 * 鑾峰彇闇�杩藉姞淇濊瘉閲戜俊鎭�
	 * @param id  鏂规缂栧彿
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
		
		WUser wuser = wUserService.get(userSessionBean.getId());   //鑾峰彇鐢ㄦ埛淇℃伅
		
		FSimpleFtseUserTrade fSimpleUserTrade = fSimpleFtseUserTradeService.get(id);
		
		if(fSimpleUserTrade == null){   //鏈壘鍒拌鏂规
			jsonResult.setMessage("notFindData");
			return jsonResult;
		}
		
		Map<Object, Object> data = new HashMap<Object, Object>();
		data.put("avlBal", wuser.getAvlBal());   //褰撳墠浣欓
		data.put("traderTotal", fSimpleUserTrade.getTraderTotal()); //鎬绘搷鐩橀噾棰�
		String rate = dataMapService.findByTypeKey("exchangeRate").get(0).getValueKey();
		data.put("exchangeRate", rate); //褰撳墠鍥哄畾姹囩巼
		jsonResult.setData(data);
		return jsonResult;
	}
	
	/**
	 * 杩藉姞淇濊瘉閲�
	 * @param id    鏂规鍙稵G+ID鍙�
	 * @param appendMoney  杩藉姞淇濊瘉閲戦
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/appendMoney" , method = RequestMethod.POST)
	@ResponseBody
	public JsonResult  addAppendMoney(String id,Double appendMoney,String rate,Double dollar,ModelMap modelMap,HttpServletRequest request,HttpServletResponse response) throws Exception{
		JsonResult jsonResult = new JsonResult(true);

		BigDecimal payMoney = new BigDecimal(appendMoney);  //杩藉姞淇濊瘉閲�
		
		BigDecimal defaultMinAppendMoney = new BigDecimal(500.00);  //榛樿鏈�皬杩藉姞淇濊瘉閲�000

		if(payMoney.compareTo(defaultMinAppendMoney) < 0){   //杩藉姞閲戦鏄惁浣庝簬榛樿鏈�皬杩藉姞淇濊瘉閲�
			jsonResult.setMessage("underDefaultMinAppendMoney");
			return jsonResult;
		}
		
		UserSessionBean userSessionBean=(UserSessionBean) request.getSession().getAttribute(Constants.TZDR_USER_SESSION);
		
		WUser wuser = wUserService.get(userSessionBean.getId());   //鑾峰彇鐢ㄦ埛淇℃伅
		
		BigDecimal avlBal = new BigDecimal(wuser.getAvlBal().toString());  //鑾峰彇鐢ㄦ埛浣欓
		
		if(avlBal.compareTo(payMoney) < 0 ) {   //鍒ゆ柇杩藉姞淇濊瘉閲戞槸鍚﹀ぇ浜庣敤鎴蜂綑棰�
			jsonResult.setMessage("insufficientBalance");
			return jsonResult;
		}
		
		FSimpleFtseUserTrade fSimpleFtseUserTrade = fSimpleFtseUserTradeService.get(id);
		
		if(fSimpleFtseUserTrade == null){   //鏈壘鍒拌鏂规
			jsonResult.setMessage("notFindData");
			return jsonResult;
		}else if(fSimpleFtseUserTrade.getStateType() == 6){   //宸插畬缁�
			jsonResult.setMessage("isOver");
			return jsonResult;
		}

		//杩藉姞淇濊瘉閲�
		fSimpleFtseUserTradeService.addAppendTraderBond(fSimpleFtseUserTrade, appendMoney,rate,dollar, wuser);
		//TODO 杩藉姞淇濊瘉閲戯紝缁欏伐浣滀汉鍛樺彂閫丒mail
		try {
			messagePromptService.sendMessage(PromptTypes.isAddBond, wuser.getMobile());
		} catch (Exception e) {
			log.info("鍙戦�閭欢澶辫触",e);
		}
	
		return jsonResult;
	}
}

package com.tzdr.web.hkstock.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jodd.util.ObjectUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.tzdr.business.hkstock.exception.HkUserTradeException;
import com.tzdr.business.hkstock.service.HkAppendLevelMoneyService;
import com.tzdr.business.hkstock.service.HkTradeCalendarService;
import com.tzdr.business.hkstock.service.HkUserTradeExtendService;
import com.tzdr.business.hkstock.service.HkUserTradeService;
import com.tzdr.business.service.contractsave.ContractsaveService;
import com.tzdr.business.service.datamap.DataMapService;
import com.tzdr.business.service.exception.UserTradeConcurrentException;
import com.tzdr.business.service.pay.UserFundService;
import com.tzdr.business.service.userTrade.FSimpleParitiesService;
import com.tzdr.business.service.wuser.WUserService;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.utils.BigDecimalUtils;
import com.tzdr.common.web.support.JsonResult;
import com.tzdr.domain.hkstock.entity.HkAppendLevelMoney;
import com.tzdr.domain.hkstock.entity.HkUserTrade;
import com.tzdr.domain.hkstock.entity.HkUserTradeExtend;
import com.tzdr.domain.hkstock.vo.ContractHkTradeSafeVo;
import com.tzdr.domain.hkstock.vo.HKUserTradeWebVo;
import com.tzdr.domain.web.entity.FSimpleParities;
import com.tzdr.domain.web.entity.UserFund;
import com.tzdr.domain.web.entity.WUser;
import com.tzdr.web.constants.Constants;
import com.tzdr.web.hkstock.constants.HkViewConstants;
import com.tzdr.web.hkstock.utils.HkStockUtil;
import com.tzdr.web.utils.UserSessionBean;

@Controller
@RequestMapping("/uhkstock")
public class HKUserTradeListController {
	private double minAdd=1000;
	private double maxAdd=1000000;

	@SuppressWarnings("unused")
	private static Logger log = LoggerFactory.getLogger(HKUserTradeListController.class);
	
	@Autowired
	private HkUserTradeService hkUserTradeService;
	
	@Autowired
	private HkTradeCalendarService hkTradeCalendarService;
	
	@Autowired
	private FSimpleParitiesService fSimpleParitiesService;
	
	@Autowired
	private HkUserTradeExtendService hkUserTradeExtendService;
	
	@Autowired
	private WUserService wUserService;
	
	@Autowired
	private UserFundService userFundService;
	
	@Autowired
	private ContractsaveService  contractsaveService;
	
	@Autowired
	private DataMapService  dataMapService;
	
	@Autowired
	private HkAppendLevelMoneyService  hkAppendLevelMoneyService;
	
	/**
	 * 访问方案列表
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/list")
	public  String  list(ModelMap modelMap,HttpServletRequest request,HttpServletResponse response){
		
		return HkViewConstants.HkUserTradeViews.TRADE_LIST;
	}
	
	/**
	 * 获取方案列表数据
	 * @param type
	 * @param countOfCurrentPage
	 * @param currentPage
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/list.json" , method = RequestMethod.POST)
	@ResponseBody
	public JsonResult listJson(@RequestParam String type,@RequestParam int countOfCurrentPage,@RequestParam int currentPage,HttpServletRequest request) throws Exception {
		UserSessionBean userSessionBean = (UserSessionBean)request.getSession().getAttribute(Constants.TZDR_USER_SESSION); 
		JsonResult jsonResult = new JsonResult(false);
		PageInfo<Object> pageinfo= new PageInfo<Object>();
		pageinfo= hkUserTradeService.queryUserTrade(userSessionBean.getId(), countOfCurrentPage, currentPage, null);
		jsonResult.setObj(pageinfo);	
		jsonResult.setSuccess(true);
		return jsonResult;
	}
	
	/**
	 * 方案终结提示
	 * @param groupId
	 * @param modelMap
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/checkEndOfProgram.json" , method = RequestMethod.POST)
	@ResponseBody
	public JsonResult checkEndOfProgram(@RequestParam String groupId,ModelMap modelMap, HttpServletRequest request){

		UserSessionBean userSessionBean = (UserSessionBean) request.getSession().getAttribute(Constants.TZDR_USER_SESSION);
		Map<Object, Object> data = new HashMap<Object, Object>();
		String uid = userSessionBean.getId();
		
		//获取方案信息
		HKUserTradeWebVo hkUserTradeWebVo = hkUserTradeService.findByGroupIdAndUid(groupId, uid);
		
		if(ObjectUtil.equals(null, hkUserTradeWebVo)) {
			return new JsonResult("nofinddata");
		}
		
		//交易天数
		Integer tradeDays = null;
		
		if(hkUserTradeWebVo.getStatus() == 2){   //已终结
			return new JsonResult("finalized");
		}else{
			if(hkUserTradeWebVo.getAuditEndStatus() != null && hkUserTradeWebVo.getAuditEndStatus() == 0){ //终结中
				return new JsonResult("auditing");
			}
		}
		
		//查询交易天数
		tradeDays = hkTradeCalendarService.getTradeDays(hkUserTradeWebVo.getStarttime(), hkUserTradeWebVo.getEstimateEndtime());
		data.put("tradeDays", tradeDays);
		
		//获取系统当前最新港币汇率
		FSimpleParities fSimpleParities = fSimpleParitiesService.getFSimpleParities(2);
		data.put("parities", fSimpleParities.getParities());
		
		JsonResult jsonResult = new JsonResult(); 
		jsonResult.setSuccess(true);
		jsonResult.setData(data);
		return jsonResult;
	}
	
	/**
	 * 	终止方案
	 * 	
	 * @param groupId
	 * @param modelMap
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/endOfProgram.json" , method = RequestMethod.POST)
	@ResponseBody
	public JsonResult endOfProgram(@RequestParam String groupId,ModelMap modelMap,HttpServletRequest request) throws Exception {
		
		List<HkUserTrade> userTrades = hkUserTradeService.findByGroupIdOrderByAddtimeAsc(groupId);
		
		if (CollectionUtils.isEmpty(userTrades)){
			return new JsonResult("notFindData");
		}
		
		//终结方案 修改handtrade状态
		HkUserTrade endTrade = userTrades.get(0);
		
		//终结审核状态等于1，表示已经终结！
		if(endTrade.getStatus() == 2){
			return new JsonResult("finalized");
		}
		
		String tradeId = endTrade.getId();  //获取方案ID
		
		HkUserTradeExtend  handTrade = hkUserTradeExtendService.findByTradeId(tradeId);  //获取方案审核信息
		
		if (ObjectUtil.equals(null, handTrade)){
			return new JsonResult("notFindData");
		}
		
		// 终结审核状态等于0，表示已经提交审核不能重复提交！
		if (handTrade.getAuditEndStatus() == 0){
			return new JsonResult("auditing");
		}
		
		//申请终结方案
		hkUserTradeExtendService.endWellGoldTrade(handTrade.getId());
		
		return new JsonResult();
	}
	
	/**
	 * 获取方案详情信息
	 * @param groupId
	 * @param modelMap
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/detail/{groupId:HK[A-Z0-9]{6}}")
	public String detail(@PathVariable("groupId") String groupId,ModelMap modelMap,HttpServletRequest request) throws Exception {
		
		UserSessionBean userSessionBean = (UserSessionBean) request.getSession().getAttribute(Constants.TZDR_USER_SESSION);
		
		WUser userTemp = wUserService.getUser(userSessionBean.getId());
		
		HKUserTradeWebVo hkUserTradeVo = hkUserTradeService.findByGroupIdAndUid(groupId, userSessionBean.getId());	
		
		modelMap.put("balance", userTemp.getAvlBal());
		modelMap.put("trade", hkUserTradeVo);

		String operatorsInfo = HkStockUtil.operatorsInfo(hkUserTradeVo.getTotalLending());
		modelMap.put("operatorsInfo", operatorsInfo);
		modelMap.put("totalManageFee", BigDecimalUtils.mulRound(hkUserTradeVo.getFeeDay(),hkUserTradeVo.getTradingDays()));
		modelMap.put("insuranceNo", hkUserTradeVo.getPolicyNo());
		
		return HkViewConstants.HkUserTradeViews.DETAIL;
	}
	
	/**
	 * 获取方案详情【子方案列表、管理费列表】
	 * @param type
	 * @param groupId
	 * @param modelMap
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/detail.json" , method = RequestMethod.POST)
	@ResponseBody
	public JsonResult type(@RequestParam int type,@RequestParam String groupId,ModelMap modelMap,HttpServletRequest request) throws Exception {
		UserSessionBean userSessionBean = (UserSessionBean) request.getSession().getAttribute(
				Constants.TZDR_USER_SESSION);
		WUser user = wUserService.getUser(userSessionBean.getId());
		String basePath=	request.getSession().getServletContext().getRealPath("/");
		JsonResult jsonResult = new JsonResult(false);
		if(type==1){
			List<HkUserTrade> userTradeList = hkUserTradeService.findByGroupIdAndWuser(groupId, user);
			List<ContractHkTradeSafeVo> vos=new ArrayList<ContractHkTradeSafeVo>();
			try {
				vos = contractsaveService.createHkTradeVo(userTradeList,basePath);
				jsonResult.setObj(vos);
			} catch (Exception e) {
				jsonResult.setObj(userTradeList);
			}
		}else if (type==3){
			List<Integer> typeList= Lists.newArrayList();
			typeList.add(11);
			typeList.add(12);
			List<UserFund> userFundList=userFundService.findByUidAndLidAndTypeIn(user.getId(), groupId, typeList);
			List<Integer> typeManage= Lists.newArrayList();
			typeManage.add(12);
			double sumManage =userFundService.sumMoneyByUidAndLidAndTypeIn(user.getId(), groupId, typeManage);
			List<Integer> typeInterest= Lists.newArrayList();
			typeInterest.add(11);
			double sumInterest =userFundService.sumMoneyByUidAndLidAndTypeIn(user.getId(), groupId, typeInterest);
			Map<Object,Object> data= Maps.newHashMap();
			data.put("sumManage", sumManage);
			data.put("sumInterest", sumInterest);
			jsonResult.setData(data);
			jsonResult.setObj(userFundList);
		}
		jsonResult.setSuccess(true);
		
		return jsonResult;
	}
	
	/**
	 * 获取添加保证金信息
	 * @MethodName getRemarginInfo
	 * @author L.Y
	 * @date 2015年7月1日
	 * @param groupId
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getRemarginInfo", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult getRemarginInfo(@RequestParam String gid, HttpServletRequest request) {
		JsonResult jsonResult = new JsonResult(false);
		List<HkUserTrade> userTrade=hkUserTradeService.findByGroupIdOrderByAddtimeAsc(gid);
		if(userTrade.size()>0){
			HkUserTrade trade = userTrade.get(0);
			
			int state = hkUserTradeExtendService.findByTradeId(trade.getId()).getAuditEndStatus();
			if(state == 1|| state == 0){
				jsonResult.setMessage("申请终结中的方案不能追加保证金");
				return jsonResult;
			}
		}
		try {
			UserSessionBean userSessionBean = (UserSessionBean) request
					.getSession().getAttribute(Constants.TZDR_USER_SESSION);
			WUser userTemp = wUserService.getUser(userSessionBean.getId());
			jsonResult.appendData("balance", userTemp.getAvlBal());
			jsonResult.appendData("minAddMoney",minAdd);
			jsonResult.appendData("maxAddMoney",maxAdd);
			
			// 获取系统当前最新港币汇率
			FSimpleParities fSimpleParities = fSimpleParitiesService.getFSimpleParities(2);

			if (fSimpleParities == null || fSimpleParities.getParities() == null
					|| fSimpleParities.getParities().compareTo(new BigDecimal("0")) <= 0) {
				throw new UserTradeConcurrentException("com.tzdr.business.server.connection.timeout", null);
			}
			jsonResult.appendData("rate", BigDecimalUtils.div(1, fSimpleParities.getParities().doubleValue())); //当前固定汇率
			jsonResult.setSuccess(Boolean.TRUE);
		} catch (Exception e) {
			log.error("HKUserTradeListController.getRemargin()：" + e.getMessage());
		}
		return jsonResult;
	}
	private static Object lockObj = new Object();
	@RequestMapping(value = "/addBond.json" , method = RequestMethod.POST)
	@ResponseBody
	public JsonResult addBond(@RequestParam String groupId,@RequestParam double addMoney,ModelMap modelMap,HttpServletRequest request) throws Exception {
		UserSessionBean userSessionBean = (UserSessionBean) request.getSession().getAttribute(
				Constants.TZDR_USER_SESSION);
		JsonResult jsonResult = new JsonResult(false);
		WUser userTemp=wUserService.getUser(userSessionBean.getId());
		synchronized(lockObj) {
		if(addMoney<minAdd||addMoney>maxAdd){
			throw new HkUserTradeException("hkstock.addBond", null);
		}
		
		List<HkUserTrade> userTrade=hkUserTradeService.findByGroupIdOrderByAddtimeAsc(groupId);
		if(userTrade.size()>0){
			HkUserTrade trade = userTrade.get(0);
			
			int state = hkUserTradeExtendService.findByTradeId(trade.getId()).getAuditEndStatus();
			if(state == 1|| state == 0){
				jsonResult.setMessage("申请终结中的方案不能追加保证金");
				return jsonResult;
			}
			
			// 获取系统当前最新港币汇率
			FSimpleParities fSimpleParities = fSimpleParitiesService.getFSimpleParities(2);

			if (fSimpleParities == null || fSimpleParities.getParities() == null
					|| fSimpleParities.getParities().compareTo(new BigDecimal("0")) <= 0) {
				throw new UserTradeConcurrentException("com.tzdr.business.server.connection.timeout", null);
			}
			double rate = BigDecimalUtils.div(1, fSimpleParities.getParities().doubleValue());
			double rateDouble = new Double(rate*10000).intValue()/10000.0;
			
			double dollerMoney = addMoney*rateDouble;
			double hkDollarMoney = new Double(dollerMoney*100).intValue()/100.0;
			//追加保证金
			boolean flag=hkUserTradeService.addBond( trade,addMoney, hkDollarMoney,userTemp);
		
			if(!flag){
				jsonResult.setMessage("追加保证金不成功！");
			}else{
				HkAppendLevelMoney  hkAppendLevelMoney =new HkAppendLevelMoney();
				hkAppendLevelMoney.setGroupId(groupId);
				hkAppendLevelMoney.setUid(userSessionBean.getId());
				hkAppendLevelMoney.setAppendMoney(addMoney);
				hkAppendLevelMoney.setExchangeRate(rateDouble);
				hkAppendLevelMoney.setHkDollarMoney(hkDollarMoney);
				hkAppendLevelMoneyService.save(hkAppendLevelMoney);
			}
			jsonResult.setSuccess(flag);
		}
		}
		return jsonResult;
	}
	
}

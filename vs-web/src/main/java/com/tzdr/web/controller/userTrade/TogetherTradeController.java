package com.tzdr.web.controller.userTrade;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jodd.util.ObjectUtil;
import jodd.util.StringUtil;

import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.hundsun.t2sdk.common.util.CollectionUtils;
import com.tzdr.business.exception.UserTradeException;
import com.tzdr.business.service.datamap.DataMapService;
import com.tzdr.business.service.togetherTrade.TogetherConfigService;
import com.tzdr.business.service.togetherTrade.TogetherTradeService;
import com.tzdr.business.service.togetherTrade.TogetherUserListService;
import com.tzdr.business.service.userTrade.TradeConfigService;
import com.tzdr.business.service.userTrade.UserTradeService;
import com.tzdr.business.service.wuser.WUserService;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.utils.BigDecimalUtils;
import com.tzdr.common.web.support.JsonResult;
import com.tzdr.domain.vo.UserTradeCmsVo;
import com.tzdr.domain.web.entity.TogetherConfig;
import com.tzdr.domain.web.entity.TogetherUserList;
import com.tzdr.domain.web.entity.TradeConfig;
import com.tzdr.web.constants.Constants;
import com.tzdr.web.constants.ViewConstants;


 /**
  * 
  * <B>说明: </B> 股票合买 不需要登录
  * @author LiuYang
  * 2016年1月20日
  */
@Controller
@RequestMapping("/together")
public class TogetherTradeController {
	private static Logger log = LoggerFactory.getLogger(TogetherTradeController.class);
	
	@Autowired
	private TradeConfigService tradeConfigService;
	@Autowired
	private UserTradeService userTradeService;
	
	@Autowired
	private  TogetherTradeService  togetherTradeService;
	@Autowired
	private DataMapService dataMapService;
	@Autowired
	private  TogetherConfigService  togetherConfigService;
	@Autowired
	private TogetherUserListService togetherUserListService;
	
	@Autowired
	private WUserService  wUserService;
	/**
	 * 配资页面
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/index")
	public String index(ModelMap modelMap,HttpServletRequest request,HttpServletResponse response){
		
		//获取合买操盘参数
		TogetherConfig  togetherConfig = togetherConfigService.getParams();
				request.setAttribute("togetherConfig", togetherConfig);
				if (ObjectUtil.equals(null, togetherConfig)){
					log.debug("合买操盘参数配置信息不存在。");
					return ViewConstants.ERROR_VIEW;
				}
				
				String recommendMoney = togetherConfig.getRecommendMoney();
				String recommendDay = togetherConfig.getRecommendDay();
				String moneyRatio  =  togetherConfig.getMoneyRatio();
						
				if (StringUtil.isNotBlank(recommendMoney)){
					request.setAttribute("recommendMoney",recommendMoney.split(Constants.SEPERATOR_SEMICOLON));
				}
				
				if (StringUtil.isNotBlank(recommendDay)){
					request.setAttribute("recommendDay",recommendDay.split(Constants.SEPERATOR_SEMICOLON));
				}
				
				if (StringUtil.isNotBlank(moneyRatio)){
					request.setAttribute("moneyRatio",moneyRatio.split(Constants.SEPERATOR_SEMICOLON));
				}
				
//				/***************返回修改**********************/	
//				modelMap.put("lever", request.getParameter("lever"));
//				modelMap.put("bailMoney",request.getParameter("bailMoney"));
//				modelMap.put("borrowPeriod", request.getParameter("borrowPeriod"));
//				modelMap.put("totalMoney", request.getParameter("totalMoney"));
//				modelMap.put("tradeStart", request.getParameter("tradeStart"));
//				/***************返回修改**********************/
				log.info("进入合买操盘页面...");
				return ViewConstants.TogetherTradeJsp.INDEX;
	}
	
	/**
	 * 根据操盘者金额和倍数 天数获取需要显示的内容，如：平仓线、补仓线、操盘保证金、利息、管理费等。
	 * @param totalMoney
	 * @param lever
	 * @return
	 */
	@RequestMapping(value = "/deduct.data.json" , method = RequestMethod.POST)
	@ResponseBody
	public JsonResult  showData(@RequestParam Double recommendMoney,@RequestParam int lever,@RequestParam int days){
		TogetherConfig  togetherConfig = togetherConfigService.getParams();
		if (ObjectUtil.equals(null, togetherConfig)){
			return new JsonResult(false, "合买参数配置信息有误！");
		}

		// 校验保证金是否合法
		if (recommendMoney < togetherConfig.getMinMoney() || recommendMoney > togetherConfig.getMaxMoney()){
			return new JsonResult(false, "总操盘资金有误！");
		}
		//校验倍数是否合法
		String leverStr = lever+"";
		
		String [] levers = togetherConfig.getMoneyRatio().split(Constants.SEPERATOR_SEMICOLON);

		if (ArrayUtils.isEmpty(levers)){
			return new JsonResult(false, "合买者金额信息有误！");
		}else{
			boolean flag = false;
			for(String configLever:levers){
				if(leverStr.equals(configLever)){
					flag = true;
				}
			}
			if(!flag){
				return new JsonResult(false, "合买者金额信息有误！");
			}
		}
		//校验天数是否合法
		String daysStr = days+"";
		
		String [] recommendDay = togetherConfig.getRecommendDay().split(Constants.SEPERATOR_SEMICOLON);

		if (ArrayUtils.isEmpty(recommendDay)){
			return new JsonResult(false, "操盘天数信息有误！");
		}else{
			boolean flag = false;
			for(String daysLever:recommendDay){
				if(daysStr.equals(daysLever)){
					flag = true;
				}
			}
			if(!flag){
				return new JsonResult(false, "操盘天数信息有误！");
			}
		}
		//计算保证金 = 总操盘金/（倍数+1）
		double cautionMoney = BigDecimalUtils.div(recommendMoney, lever+1.0, 0);
		//计算配资金额 = 总操盘金 - 保证金
		double capitalAmount = BigDecimalUtils.sub(recommendMoney, cautionMoney);
		
		double shortLine = 0.0; //补仓
		double openLine = 0.0;  //平仓
		if (1 <= lever && lever <= 5) {
			shortLine = BigDecimalUtils.mul(capitalAmount, 1.1);
			openLine = BigDecimalUtils.mul(capitalAmount, 1.07);
		} else if (lever==6) {
			shortLine = BigDecimalUtils.mul(capitalAmount, 1.0867);
			openLine = BigDecimalUtils.mul(capitalAmount, 1.06);
		} else if (lever==7) {
			shortLine = BigDecimalUtils.mul(capitalAmount, 1.0929);
			openLine = BigDecimalUtils.mul(capitalAmount, 1.0571);
		} else if (lever==8) {
			shortLine = BigDecimalUtils.mul(capitalAmount, 1.0875);
			openLine = BigDecimalUtils.mul(capitalAmount, 1.0563);
		} else if (lever==9) {
			shortLine = BigDecimalUtils.mul(capitalAmount, 1.0644);
			openLine = BigDecimalUtils.mul(capitalAmount, 1.0433);
		} else if (lever==10) {
			shortLine = BigDecimalUtils.mul(capitalAmount, 1.06);
			openLine = BigDecimalUtils.mul(capitalAmount, 1.04);
		} else if (lever==11) {
			shortLine = BigDecimalUtils.mul(capitalAmount, 1.0682);
			openLine = BigDecimalUtils.mul(capitalAmount, 1.0409);
		} else if (lever==12) {
			shortLine = BigDecimalUtils.mul(capitalAmount, 1.0625);
			openLine = BigDecimalUtils.mul(capitalAmount, 1.0375);
		} else if (lever==13) {
			shortLine = BigDecimalUtils.mul(capitalAmount, 1.0577);
			openLine = BigDecimalUtils.mul(capitalAmount, 1.0346);
		} else if (lever==14) {
			shortLine = BigDecimalUtils.mul(capitalAmount, 1.0536);
			openLine = BigDecimalUtils.mul(capitalAmount, 1.0321);
		} else if (lever==15) {
			shortLine = BigDecimalUtils.mul(capitalAmount, 1.05);
			openLine = BigDecimalUtils.mul(capitalAmount, 1.03);
		}
		shortLine = BigDecimalUtils.round(shortLine,0);
		openLine = BigDecimalUtils.round(openLine,0);
		TradeConfig config = tradeConfigService.findTradeConfig(days,
				cautionMoney, lever);
		if (ObjectUtil.equals(config, null)) {
			String detail="borrwPeriod:"+days+"lever:"+lever+"capitalMargin:"+cautionMoney;
			log.error("利息配置有误"+detail);
			throw new UserTradeException("no.interest.config",null);
		}
		
		//合买利息系数
		double foenusRatio  =togetherConfig.getFoenusRatio();
		//合买管理费系数
		double manageRatio  =togetherConfig.getManageRatio();
//		double a = config.getDailyInterest();
//		double b = config.getDailyManagementFee();
		// 利息（天）
		double interestFee = 
				BigDecimalUtils.mulRound(BigDecimalUtils.mulRound(config.getDailyInterest(), capitalAmount),foenusRatio);
		// 管理费（天）
		double manageFee = 
				BigDecimalUtils.mulRound(BigDecimalUtils.mulRound(config.getDailyManagementFee(), capitalAmount),manageRatio);
		//总利息
		double totalInterestFee = BigDecimalUtils.mulRound(interestFee,days);
		String operatorsInfo = operatorsInfo(capitalAmount);
		JSONObject  resultData = new JSONObject();
		
		resultData.put("totalMoney", recommendMoney);
		resultData.put("cautionMoney", cautionMoney);
		resultData.put("capitalAmount", capitalAmount);
		resultData.put("shortLine", shortLine);
		resultData.put("openLine", openLine);
		resultData.put("interestFee", interestFee);
		resultData.put("manageFee", manageFee);
		resultData.put("totalInterestFee", totalInterestFee);
		resultData.put("operatorsInfo", operatorsInfo);
		JsonResult  result = new JsonResult(true, "处理成功！");
		result.setObj(resultData);
		return  result;
	}
	/**
	 * 参与合买 页面列表
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/list")
	public String publicList(ModelMap modelMap,HttpServletRequest request,HttpServletResponse response){
		String togetherTradeNo = dataMapService.findByTypeKey("togetherTradeNo").get(0).getValueKey();
		String togetherTradeUserNo = dataMapService.findByTypeKey("togetherTradeUserNo").get(0).getValueKey();
		String togetherTradeMoney = dataMapService.findByTypeKey("togetherTradeMoney").get(0).getValueKey();
		modelMap.put("togetherTradeNo", togetherTradeNo);
		modelMap.put("togetherTradeUserNo", togetherTradeUserNo);
		modelMap.put("togetherTradeMoney", togetherTradeMoney);
		return ViewConstants.TogetherTradeJsp.PUBLICLIST;
	}
	/**
	 * 合买信息列表详情
	 * @param countOfCurrentPage
	 * @param currentPage
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/list.json", method = RequestMethod.POST)
	@ResponseBody
	public JsonResult listJson(@RequestParam int countOfCurrentPage, @RequestParam int currentPage, HttpServletRequest request) throws Exception {
		JsonResult jsonResult = new JsonResult(false);
		PageInfo<Object> pageinfo = new PageInfo<Object>();
		pageinfo = togetherTradeService.queryTogetherTradeList(countOfCurrentPage, currentPage, null);
		jsonResult.setObj(pageinfo);
		jsonResult.setSuccess(true);
		return jsonResult;
	}
	/**
	 * 每个方案的详情页面
	 * @param groupId
	 * @param modelMap
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/detail/{groupId:HM[A-Z0-9]{7}}")
	public String detail(@PathVariable("groupId") String groupId, ModelMap modelMap, HttpServletRequest request) throws Exception {
		//groupId = request.getParameter("groupId");
//		UserSessionBean userSessionBean = (UserSessionBean) request.getSession().getAttribute(Constants.TZDR_USER_SESSION);
		UserTradeCmsVo userTradeCmsVo = userTradeService.publicFindByGroupId(groupId);
		Map<String, Object> togetherTrade = this.togetherTradeService.getByGroupId(groupId);
		List<TogetherUserList> list = togetherUserListService.findByGid(groupId);
		if (CollectionUtils.isEmpty(list)) {
			modelMap.put("number", 0);
		}else{
			modelMap.put("number", list.size());
			modelMap.put("userList", list);
		}
		modelMap.put("trade", userTradeCmsVo);
		modelMap.put("togetherTrade", togetherTrade);
		String operatorsInfo = operatorsInfo(userTradeCmsVo.getTotalLending());
		modelMap.put("operatorsInfo", operatorsInfo);
		return ViewConstants.TogetherTradeJsp.PUBLICDETAIL;
	}
	
	
	/**
	 * 计算提示信息
	 * @param capitalAmount
	 * @return
	 */
	
	public String operatorsInfo(double capitalAmount) {
		String operatorsInfo = "";
		if (capitalAmount <= 100000) {
			operatorsInfo = "仓位不限制，盈利全归您";
		} else if (100000 < capitalAmount && capitalAmount <= 500000) {
			operatorsInfo = "单只股票不超过总操盘资金的70%";
		} else if (500000 < capitalAmount && capitalAmount <= 1000000) {
			operatorsInfo = "单只股票不超过总操盘资金的50%";
		} else if (1000000 < capitalAmount) {
			operatorsInfo = "单股不超总操盘资金的50%(创业板33%)";
		}
		return operatorsInfo;
	}
	
}

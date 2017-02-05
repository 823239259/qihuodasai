package com.tzdr.web.controller.userTrade;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jodd.util.StringUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tzdr.business.service.tradecalendar.TradeDayService;
import com.tzdr.business.service.userTrade.FSimpleUserTradeService;
import com.tzdr.business.service.wuser.WUserService;
import com.tzdr.common.web.support.JsonResult;
import com.tzdr.domain.web.entity.FSimpleUserTrade;
import com.tzdr.domain.web.entity.WUser;
import com.tzdr.web.constants.Constants;
import com.tzdr.web.utils.UserSessionBean;

/**
 * @author WangPinQun
 * @see
 * UFSimpleUserTradeController
 * @version 2.0
 * 2015年07月29日下午11:33:13
 */
@Controller
@RequestMapping("/userfuture")
public class UFSimpleUserTradeController {
	
	@Autowired
	private FSimpleUserTradeService fSimpleUserTradeService;

	@Autowired
	private WUserService wUserService;
	
	@Autowired
	private  TradeDayService tradeDayService;

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
		
		FSimpleUserTrade fSimpleUserTrade = fSimpleUserTradeService.get(id);
		
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
	 * 股指追加保证金
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
		
		BigDecimal defaultMinAppendMoney = new BigDecimal(3000.00);  //默认最小追加保证金

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
		
		FSimpleUserTrade fSimpleUserTrade = fSimpleUserTradeService.get(id);
		
		if(fSimpleUserTrade == null){   //未找到该方案
			jsonResult.setMessage("notFindData");
			return jsonResult;
		}else if(fSimpleUserTrade.getStateType() == 3){   //已完结
			jsonResult.setMessage("isOver");
			return jsonResult;
		}

		//追加保证金
		fSimpleUserTradeService.addAppendTraderBond(fSimpleUserTrade, appendMoney, wuser);
		
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
		
		FSimpleUserTrade fSimpleUserTrade = fSimpleUserTradeService.get(id);
		
		if(fSimpleUserTrade == null || !userSessionBean.getId().equals(fSimpleUserTrade.getUid())){
			jsonResult.setMessage("notFindData");
			return jsonResult;
		}
		
		if(fSimpleUserTrade.getStateType() == 4){   //不能重复申请
			jsonResult.setMessage("notRepetitionApply");
			return jsonResult;
		}
		
		if(fSimpleUserTrade.getStateType() != 2){  //判断是不是操盘中 
			jsonResult.setMessage("applyEndTradeFail");
			return jsonResult;
		}
		
		String startDate =  new SimpleDateFormat("yyyyMMdd").format(new Date(fSimpleUserTrade.getAppStarttime()*1000));
		String endDate =  new SimpleDateFormat("yyyyMMdd").format(new Date());
		int usertradeDays = tradeDayService.getTradeDays(startDate, endDate);
		if(usertradeDays < 2){   //申请终结操盘时间T+1
			jsonResult.setMessage("notUpApplyEndTradeTime");
			return jsonResult;
		}
		
		fSimpleUserTrade.setStateType(4);  //申请终结方案
		
		fSimpleUserTradeService.update(fSimpleUserTrade);
		
		return jsonResult;
	}
}

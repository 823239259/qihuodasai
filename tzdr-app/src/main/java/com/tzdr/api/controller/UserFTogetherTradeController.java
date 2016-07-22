package com.tzdr.api.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jodd.util.ObjectUtil;
import jodd.util.StringUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tzdr.api.constants.ResultStatusConstant;
import com.tzdr.api.support.ApiResult;
import com.tzdr.api.util.AuthUtils;
import com.tzdr.business.service.togetherFuture.FTogetherRecordService;
import com.tzdr.business.service.togetherFuture.FTogetherTradeService;
import com.tzdr.business.service.wuser.WUserService;
import com.tzdr.common.utils.Dates;
import com.tzdr.domain.app.vo.UserFTogetherRecordVo;
import com.tzdr.domain.app.vo.UserFTogetherTradeVo;
import com.tzdr.domain.constants.Constant;
import com.tzdr.domain.web.entity.FTogetherRecord;
import com.tzdr.domain.web.entity.FTogetherTrade;
import com.tzdr.domain.web.entity.WUser;

/**
 * 期货合买登录后可操作接口
 * @author zhouchen
 * 2016年5月23日 上午9:34:13
 */
@Controller
@RequestMapping("/user/ftogether_trade")
public class UserFTogetherTradeController {
	
	public static final Logger logger = LoggerFactory.getLogger(UserFTogetherTradeController.class);
	
	@Autowired
	private WUserService wUserService;
	
	
	@Autowired
	private FTogetherTradeService fTogetherTradeService;
	
	
	@Autowired
	private FTogetherRecordService fTogetherRecordService;
	
	/**
	 * 确认支付并参与游戏
	 * @param tradeId 方案id
	 * @param copies  参与份数
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/pay" , method = RequestMethod.POST)
	@ResponseBody
	public ApiResult pay(Integer direction,String tradeId,Integer copies,HttpServletRequest request,HttpServletResponse response){
		if (ObjectUtil.equals(null,direction)
				|| ObjectUtil.equals(null, copies)
				|| StringUtil.isBlank(tradeId)
				|| (Constant.FtogetherGame.CALL_DIRECTION !=direction 
					&& Constant.FtogetherGame.PUT_DIRECTION !=direction)
				|| copies < 0 ){
			return new ApiResult(false,ResultStatusConstant.FAIL,"params.is.error.");
		}
		//获取方案信息
		FTogetherTrade  fTogetherTrade = fTogetherTradeService.get(tradeId);
		if (ObjectUtil.equals(null, fTogetherTrade)){
			return new ApiResult(false,ResultStatusConstant.FAIL,"tradeId.is.not.find.in.system.");
		}
		
		//校验是否可以参与合买 即当前时间+5分钟大于开仓时间，将不能再合买
		if (Dates.getCurrentLongDate()+Constant.FtogetherGame.FIVE_MINITE_SEC >= fTogetherTrade.getOpenTime()){
			return new ApiResult(false,ResultStatusConstant.FtogetherTradePay.GAME_IS_ALREADY_STARTED,"game.is.already.started.");
		}
		
		//方案已经结算
		if (Constant.FtogetherGame.END_STATUS == fTogetherTrade.getStatus()){
			return new ApiResult(false,ResultStatusConstant.FtogetherTradePay.TRADE_ALREADY_END,"trade.already.end.");
		}
		
		//获取用户信息
		String uid = AuthUtils.getCacheUser(request).getUid();  
		//校验相反方向是否已经参与
		// 已经看跌不能看张
		if (Constant.FtogetherGame.CALL_DIRECTION == direction){
			FTogetherRecord tempfTogetherRecord = fTogetherRecordService.findByTradeIdAndDirectionAndUid(tradeId, Constant.FtogetherGame.PUT_DIRECTION, uid);			
			if (!ObjectUtil.equals(null,tempfTogetherRecord)){
				return new ApiResult(false,ResultStatusConstant.FtogetherTradePay.ALREADY_PUT_CANOT_CALL,"already_put_canot_call");
			}
		}
		//已经看张不能看跌
		if (Constant.FtogetherGame.PUT_DIRECTION == direction){
			FTogetherRecord tempfTogetherRecord = fTogetherRecordService.findByTradeIdAndDirectionAndUid(tradeId, Constant.FtogetherGame.CALL_DIRECTION, uid);			
			if (!ObjectUtil.equals(null,tempfTogetherRecord)){
				return new ApiResult(false,ResultStatusConstant.FtogetherTradePay.ALREADY_CALL_CANOT_PUT,"already_put_canot_call");
			}
		}
		//校验余额是否充足
		WUser  wuser = wUserService.get(uid);
		BigDecimal  payMoney  = fTogetherTrade.getPrice().multiply(new BigDecimal(copies));
		//校验活动期间 免除90元费用
		boolean isCanFreeMoney = fTogetherTradeService.checkActivityTime() && fTogetherTradeService.checkIsNewUser(uid);
		if (isCanFreeMoney){
			payMoney = payMoney.subtract(Constant.FtogetherGame.ACTIVITY_FREE_MONEY);
		}
		if (wuser.getAvlBal() < payMoney.doubleValue()){
			return new ApiResult(false,ResultStatusConstant.FtogetherTradePay.NOT_SUFFICIENT_FUNDS,"not_sufficient_funds");
		}
		//如果已经存在就自动作为追加处理
		FTogetherRecord fTogetherRecord = fTogetherRecordService.findByTradeIdAndDirectionAndUid(tradeId,direction, uid);			
		if (ObjectUtil.equals(null,fTogetherRecord)){
			fTogetherRecord = new FTogetherRecord(uid, tradeId, direction);
			//活动期间标记为活动类型
			if (isCanFreeMoney){
				fTogetherRecord.setActivityType(Constant.FtogetherGame.ACTIVITY_TYPE_ONE);
			}
		}
		Integer  tempCopies  = ObjectUtil.equals(null,fTogetherRecord.getCopies())?Constant.FtogetherGame.ZERO:fTogetherRecord.getCopies();
		// 超过最大份数限制
		if ((tempCopies+copies) > Constant.FtogetherGame.MAX_COPIES){
			return new ApiResult(false,ResultStatusConstant.FtogetherTradePay.OVER_MAX_COPIES,"over.max.copies.");
		}
				
		fTogetherRecordService.createTogetherRecord(fTogetherRecord, payMoney, copies, wuser);
		
		return new ApiResult(true,ResultStatusConstant.SUCCESS,"pay.success",null);
	}
	
	
	/**
	 * 获取用户的所有合买方案
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/list" , method = RequestMethod.POST)
	@ResponseBody
	public ApiResult list(HttpServletRequest request,HttpServletResponse response) throws Exception{
		//获取用户信息
		String uid = AuthUtils.getCacheUser(request).getUid();  
		List<UserFTogetherTradeVo>  userFTogetherTradeVos = fTogetherTradeService.queryUserTogetherTrades(uid);
		Map<String,Object>  resultData = new HashMap<String, Object>();
		resultData.put("trades", userFTogetherTradeVos);
		
		Map<String, Object>  map =  fTogetherTradeService.queryUserProfitRank(uid);
		resultData.put("userRank",map);
		return new ApiResult(true,ResultStatusConstant.SUCCESS,"query.success",resultData);
	}
	
	@RequestMapping(value = "/detail" , method = RequestMethod.POST)
	@ResponseBody
	public ApiResult recordDetail(String recordId,HttpServletRequest request,HttpServletResponse response) throws Exception{
		//获取用户信息
		String uid = AuthUtils.getCacheUser(request).getUid(); 
		UserFTogetherRecordVo  userFTogetherRecordVo = fTogetherRecordService.queryUserTogetherRecord(uid, recordId);
		return new ApiResult(true,ResultStatusConstant.SUCCESS,null,userFTogetherRecordVo);
	}
}

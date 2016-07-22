package com.tzdr.api.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jodd.util.Base64;
import jodd.util.ObjectUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tzdr.api.constants.DataConstant;
import com.tzdr.api.constants.ResultStatusConstant;
import com.tzdr.api.support.ApiResult;
import com.tzdr.business.service.togetherFuture.FTogetherRecordDetailService;
import com.tzdr.business.service.togetherFuture.FTogetherRecordService;
import com.tzdr.business.service.togetherFuture.FTogetherTradeService;
import com.tzdr.domain.app.vo.FTogetherTradeWebVo;
import com.tzdr.domain.app.vo.FtogetherLineDataVo;
import com.tzdr.domain.constants.Constant;
import com.tzdr.domain.web.entity.FTogetherRecord;

/**
 * 期货合买免登陆接口
 * @author zhouchen
 * 2016年5月23日 上午9:34:32
 */
@Controller
@RequestMapping(value = "/ftogether_trade")
public class FTogetherTradeController {

	@Autowired
	private FTogetherTradeService fTogetherTradeService;
	
	@Autowired
	private FTogetherRecordService fTogetherRecordService;
	
	@Autowired
	private FTogetherRecordDetailService fTogetherRecordDetailService;
	
	
	/**
	 * 获取合买方案
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/list" , method = RequestMethod.POST)
	@ResponseBody
	public ApiResult list(@RequestParam(value = "pageNo", required = false) Integer  pageNo ,
			HttpServletRequest request,HttpServletResponse response) throws Exception{
		if (ObjectUtil.equals(null,pageNo)){
			pageNo = Constant.FtogetherGame.ZERO;
		}
		List<FTogetherTradeWebVo>  fTogetherTradeWebVos = fTogetherTradeService.queryTogetherTrades(pageNo);
		return new ApiResult(true,ResultStatusConstant.SUCCESS,null,fTogetherTradeWebVos);
	}
	
	@RequestMapping(value = "/detail" , method = RequestMethod.POST)
	@ResponseBody
	public ApiResult fdetail(String tradeID,HttpServletRequest request,HttpServletResponse response) throws Exception{
		FTogetherTradeWebVo  fTogetherTradeWebVo = fTogetherTradeService.findTradeById(tradeID);
		//获取用户信息
		String token = request.getHeader(DataConstant.APP_TOKEN);
		String uid = Base64.decodeToString(token);
		List<FTogetherRecord> userRecords  = fTogetherRecordService.findByTradeIdAndUid(tradeID, uid);
		if (!CollectionUtils.isEmpty(userRecords)){
			fTogetherTradeWebVo.setOwn(true);
			fTogetherTradeWebVo.setRecordId(userRecords.get(0).getId());
		}
		return new ApiResult(true,ResultStatusConstant.SUCCESS,null,fTogetherTradeWebVo);
	}
	
	@RequestMapping(value = "/line_data" , method = RequestMethod.POST)
	@ResponseBody
	public ApiResult lineData(String tradeID,HttpServletRequest request,HttpServletResponse response) throws Exception{
		FtogetherLineDataVo  ftogetherLineDataVo  = fTogetherTradeService.queryLineData(tradeID);
		return new ApiResult(true,ResultStatusConstant.SUCCESS,null,ftogetherLineDataVo);
	}
	
	@RequestMapping(value = "/win_profit_rank" , method = RequestMethod.POST)
	@ResponseBody
	public ApiResult queryProfitRank(HttpServletRequest request,HttpServletResponse response) throws Exception{
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("win", fTogetherTradeService.queryWinRank());
		dataMap.put("profit", fTogetherTradeService.queryProfitRank());
		// 操盘页面  获取下期货合买活动图片显示
		dataMap.put("isFtogetherActivityTime",fTogetherTradeService.checkActivityTime());
		return new ApiResult(true,ResultStatusConstant.SUCCESS,null,dataMap);
	}
	
	

	/**
	 * 查看对应方案的看涨、看跌合买记录
	 * @param tradeID
	 * @param direction
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/trade_record_detail" , method = RequestMethod.POST)
	@ResponseBody
	public ApiResult queryTradeRecordDetails(String tradeID,Integer direction,
			HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("statistics", fTogetherRecordDetailService.queryCopies(tradeID, direction));
		dataMap.put("records", fTogetherRecordDetailService.queryRecords(tradeID, direction));
		dataMap.put("trade", fTogetherTradeService.findTradeById(tradeID));
		return new ApiResult(true,ResultStatusConstant.SUCCESS,null,dataMap);
	}
}

package com.tzdr.web.hkstock.utils;

import java.util.Date;

import jodd.util.StringUtil;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang3.ArrayUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tzdr.business.hkstock.service.HkTradeCalendarService;
import com.tzdr.common.utils.BigDecimalUtils;
import com.tzdr.common.utils.Dates;
import com.tzdr.common.utils.MessageUtils;
import com.tzdr.domain.hkstock.entity.HkStockParams;
import com.tzdr.web.constants.Constants;

/**
 * 港股相关 工具方案
 * @author zhouchen
 * 2015年10月19日 下午1:54:14
 */
public class HkStockUtil {

	
	public static   Double  parseWarningCoefficient(HkStockParams hkStockParams,int lever){
		String waringCoefficient = hkStockParams.getWarningCoefficient();
		if (StringUtil.isBlank(waringCoefficient)){
			return 0.0;
		}
		
		String [] coefficients = waringCoefficient.split(Constants.SEPERATOR_SEMICOLON);
		if (lever>coefficients.length){
			return 0.0;
		}
		
		String result = coefficients[lever-1];
		return NumberUtils.toDouble(result);
	}
	
	public static Double  parseOpenCoefficient(HkStockParams hkStockParams,int lever){
		String openCoefficient = hkStockParams.getOpenCoefficient();
		if (StringUtil.isBlank(openCoefficient)){
			return 0.0;
		}
		
		String [] coefficients = openCoefficient.split(Constants.SEPERATOR_SEMICOLON);
		if (lever>coefficients.length){
			return 0.0;
		}
		
		String result = coefficients[lever-1];
		return NumberUtils.toDouble(result);
	}
	
	/**
	 * 根据配置参数解析出对应的保证金
	 * @param hkStockParams
	 * @return
	 */
	public static JSONArray  parseLeverConfig(HkStockParams hkStockParams,Double totalMoney){
		String [] levers = hkStockParams.getLeverConfig().split(Constants.SEPERATOR_SEMICOLON);
		if (ArrayUtils.isEmpty(levers)){
			return null;
		}
		
		JSONArray bailArray = new JSONArray();
		int lever=1;
		for (String tmpLever : levers){
			JSONObject  jsonObject = new JSONObject();
			jsonObject.put("lever", lever);
			jsonObject.put("bailMoney",BigDecimalUtils.div(BigDecimalUtils.mul(totalMoney,hkStockParams.getBailCoefficient()),(NumberUtils.toDouble(tmpLever)+1),0));
			bailArray.add(jsonObject);
			lever++;
		}
		return bailArray;
	}
	
	/**
	 * 校验倍数和保证金是否对应
	 * @param bailArray
	 * @param bailMoney
	 * @param lever
	 * @return
	 */
	public static boolean isRightLeverAndBailMoney(JSONArray bailArray,double bailMoney,int lever){
		//校验倍数和保证金是否匹配
		for (Object jsonObject : bailArray){
			 JSONObject  bail = (JSONObject) jsonObject;
			 if (bail.getInteger("lever")==lever && bailMoney==bail.getDoubleValue("bailMoney")){
				 return true;
			 }
		}
		
		return false;
	}
	
	
	/**
	 * 操盘风险规则
	 * @param capitalAmount
	 * @return
	 */
	public static String operatorsInfo(double capitalAmount) {
		String operatorsInfo = "";
		if (capitalAmount <= 100000) {
			operatorsInfo = MessageUtils.message("hkstock.operatorsInfo.one");
		} else if (100000 < capitalAmount && capitalAmount <= 500000) {
			operatorsInfo = MessageUtils.message("hkstock.operatorsInfo.two");
		} else if (500000 < capitalAmount && capitalAmount <= 1000000) {
			operatorsInfo = MessageUtils.message("hkstock.operatorsInfo.three");
		} else if (1000000 < capitalAmount) {
			operatorsInfo = MessageUtils.message("hkstock.operatorsInfo.four");
		}
		return operatorsInfo;
	}
	
	
	
	/**
	 * 计算获取预计结束日期
	 * @param borrowPeriod
	 * @param tradeStart
	 * @return
	 */
	public static String getEndDay(int borrowPeriod, int tradeStart,
				HkTradeCalendarService hkTradeCalendarService) {
		String currentDay = Dates.format(new Date(),
				Dates.CHINESE_DATE_FORMAT_LONG);
		String tradeDay = hkTradeCalendarService.getTradeDay();
		boolean isTradeDay = Boolean.FALSE;
		if (tradeDay.equals(currentDay)) {
			isTradeDay = Boolean.TRUE;
		}
		
		if(isTradeDay&&tradeStart==1){
			tradeDay=hkTradeCalendarService.getNextTradeDay();
		}

		String expirationDate = hkTradeCalendarService.getEndDate(tradeDay,
				borrowPeriod);
		String endDay = Dates.format(
				Dates.parse(String.valueOf(expirationDate),
						Dates.CHINESE_DATE_FORMAT_LONG),
				Dates.CHINESE_DATE_FORMAT_LINE);
		return endDay;
	}
	
}

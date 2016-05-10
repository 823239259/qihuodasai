package com.tzdr.domain.cache;

import java.util.concurrent.ConcurrentHashMap;

/**
 * WEB开箱壕礼缓存数据
 * @author WangPinQun
 * 2016年01月08日
 */
public class CacheSIFActivitesManager {

	/**
	 * 存放交易日缓存
	 */
	public static ConcurrentHashMap<String, Object> tradeDayCacheMap = new ConcurrentHashMap<String, Object>();
	
	/**
	 * 存放黑名单
	 */
	public static ConcurrentHashMap<String, Object> blackListCacheMap = new ConcurrentHashMap<String, Object>();
	
	/**
	 * 存放获取红包
	 */
	public static ConcurrentHashMap<String, Object> redPacketCacheMap = new ConcurrentHashMap<String, Object>();
	
	
	/**
	 * 添加交易日
	 * @param today  时间     如：20160112
	 * @param isTradeDay  是否是交易日，true:是；false：否;
	 */
	public static  void addTradeDayCacheMap(String today,boolean isTradeDay){
		if(!tradeDayCacheMap.containsKey(today)){
			tradeDayCacheMap.put(today, isTradeDay);
		}
	}
	
	/**
	 * 获取该时间的交易日信息
	 * @param today 时间      如：20160112
	 * @return
	 */
	public static  Object getTradeDayCacheMap(String today){
		if(tradeDayCacheMap.containsKey(today)){
			return tradeDayCacheMap.get(today);
		}
		return null;
	}
	
	/**
	 * 添加黑名单
	 * @param today  时间     如：20160112
	 * @param isTradeDay  是否是交易日，true:是；false：否;
	 */
	public static  void addBlackListCacheMap(String today,String blackList){
		if(!blackListCacheMap.containsKey(today)){
			blackListCacheMap.put(today, blackList);
		}
	}
	
	/**
	 * 获取该时间的黑名单信息
	 * @param today 时间      如：20160112
	 * @return
	 */
	public static  Object getblackListCacheMap(String today){
		if(blackListCacheMap.containsKey(today)){
			return blackListCacheMap.get(today);
		}
		return null;
	}
	
	
	/**
	 * 添加红包
	 * @param todayUid 时间+手机号码      如：20160112+用户编号(描述：20160112为时间)
	 */
	public static  void addRedPacketCacheMap(String todayUid){
		if(!redPacketCacheMap.containsKey(todayUid)){
			redPacketCacheMap.put(todayUid, true);
		}
	}
	
	/**
	 * 获取该时间的红包信息
	 * @param todayUid 时间+手机号码      如：20160112+用户编号(描述：20160112为时间)
	 * @return
	 */
	public static  Object getRedPacketCacheMap(String todayUid){
		if(redPacketCacheMap.containsKey(todayUid)){
			return redPacketCacheMap.get(todayUid);
		}
		return null;
	}
}

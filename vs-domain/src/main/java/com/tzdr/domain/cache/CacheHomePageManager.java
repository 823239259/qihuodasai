package com.tzdr.domain.cache;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import jodd.util.ObjectUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.tzdr.domain.dao.HomePageCinfigDao;
import com.tzdr.domain.dao.operational.OperationalConfigDao;
import com.tzdr.domain.entity.HomePageCinfig;
import com.tzdr.domain.web.entity.OperationalConfig;

/**
 * 缓存信息 WangPinQun  2015.12.20
 * 
 */
public class CacheHomePageManager {
	
	/**
	 * banner
	 */
	public static final int  BANNER_STATUS = 4;

	private static HomePageCinfigDao homePageCinfigDao;
	
	private static OperationalConfigDao operationalConfigDao;

	public static final Logger log = LoggerFactory.getLogger(CacheHomePageManager.class);

	/**
	 * 选项缓存信息Map<String, List<Option>>
	 */
	public static Map<String, Map<String, HomePageCinfig>> dataMapCache = Maps.newHashMap();
	
	/**
	 * 缓存banners
	 */
	public static List<OperationalConfig> bannersCache = Lists.newArrayList();
	
	public CacheHomePageManager(HomePageCinfigDao dataMapDao,OperationalConfigDao operationalConfigDao) {
		synchronized (this) {
			CacheHomePageManager.homePageCinfigDao = dataMapDao;
			CacheHomePageManager.operationalConfigDao = operationalConfigDao;
			CacheHomePageManager.init();
		}
	}

	/**
	 * 
	 * 初始化系统缓存信息
	 */
	public static void init() {
		log.info("==============================CacheManager Init Begin==============================");
		dataMapCache.clear();
		bannersCache.clear();
		initDataMap();
		log.info("==============================CacheManager Init End==============================");
		
	}

	/**
	 * 
	 * 刷新所有缓存信息
	 * 
	 * @auther <a href="mailto:hubo@feinno.com">hubo</a>
	 * @since 2011-9-15 上午11:53:37
	 */
	public static void refreshCache() {
		log.info("==============================CacheManager clear Begin==============================");
		dataMapCache.clear();
		bannersCache.clear();
		log.info("==============================CacheManager clear End==============================");
		init();
	}
	
	/**
	 * 刷新首页缓存信息
	 */
	public static void refreshBannersCache(){
		log.info("==============================CacheManager BannersCache clear Begin==============================");
		bannersCache.clear();
		log.info("==============================CacheManager BannersCache clear End==============================");
		try {  //首页banner
			List<OperationalConfig> newBanners = operationalConfigDao.findBanner(CacheHomePageManager.BANNER_STATUS);
			bannersCache = newBanners;
		} catch (Exception e) {
			log.error("com.tzdr.business.service.CacheManager.initDataMap,error::{}",e.getMessage());
		}
	}

	/**
	 * 
	 * 初始首页数据
	 */
	public static void initDataMap() {
		
		try {  //首页banner
			List<OperationalConfig> newBanners = operationalConfigDao.findBanner(CacheHomePageManager.BANNER_STATUS);
			bannersCache = newBanners;
		} catch (Exception e) {
			log.error("com.tzdr.business.service.CacheManager.initDataMap,error::{}",e.getMessage());
		}
		
		try {
			List<HomePageCinfig> dataMapList = homePageCinfigDao.findByDeletedFalse();
			for (HomePageCinfig dataMap : dataMapList) {
				String typeKey = dataMap.getTypeKey();
				String key = dataMap.getValueKey();
				Map<String, HomePageCinfig> temps = dataMapCache.get(typeKey);
				// 当前配置项不存在，新增一个配置项
				if (null == temps) {
					temps = Maps.newTreeMap();
					dataMapCache.put(typeKey, temps);
				}
				temps.put(key, dataMap);
			}
		} catch (Exception e) {
			log.error("com.tzdr.business.service.CacheManager.initDataMap,error::{}",e.getMessage());
		}
	}

	public static Map<String, String> getDataMapByTypeKey(String typeKey) {
		Map<String, HomePageCinfig> temps = dataMapCache.get(typeKey);
		if (CollectionUtils.isEmpty(temps)){
			return null;
		}
		List<Map.Entry<String, HomePageCinfig>> list = sortDataMap(temps);
		Map<String, String> returnMap = Maps.newHashMap();
		for (Map.Entry<String, HomePageCinfig> mapEntry : list) {
			returnMap.put(mapEntry.getKey(), mapEntry.getValue().getValueData());
		}
		return returnMap;
	}
	
	public static List<Map.Entry<String, HomePageCinfig>> getDataMapSortListByTypeKey(String typeKey) {
		Map<String, HomePageCinfig> temps = dataMapCache.get(typeKey);
		if (CollectionUtils.isEmpty(temps)){
			return null;
		}
		List<Map.Entry<String, HomePageCinfig>> list = sortDataMap(temps);
		return list;
	}
	
	public static List<HomePageCinfig> getDataMapListByTypeKey(String typeKey) {
		Map<String, HomePageCinfig> temps = dataMapCache.get(typeKey);
		if (CollectionUtils.isEmpty(temps)){
			return null;
		}
		List<HomePageCinfig> dataMapList = Lists.newArrayList();
		List<Map.Entry<String, HomePageCinfig>> list = sortDataMap(temps);
		for (Map.Entry<String, HomePageCinfig> entry : list){
			dataMapList.add(entry.getValue());
		}
		return dataMapList;
	}
	
	public static String getDataMapByKey(String typeKey, String key) {
		Map<String, HomePageCinfig> map = dataMapCache.get(typeKey);
		if (CollectionUtils.isEmpty(map)){
			return null;
		}
		HomePageCinfig  dataMap = map.get(key);
		if (ObjectUtil.equals(null, dataMap)){
			return null;
		}
		return dataMap.getValueData();
	}

	private static List<Map.Entry<String, HomePageCinfig>> sortDataMap(
			Map<String, HomePageCinfig> temps) {
		List<Map.Entry<String, HomePageCinfig>> list = new ArrayList<Map.Entry<String, HomePageCinfig>>(
				temps.entrySet());
		if (CollectionUtils.isEmpty(list)) {
			return null;
		}
		// 然后通过比较器来实现排序
		Collections.sort(list, new Comparator<Map.Entry<String, HomePageCinfig>>() {
			@Override
			public int compare(Entry<String, HomePageCinfig> entry1,
					Entry<String, HomePageCinfig> entry2) {
				Integer weight1 = entry1.getValue().getWeight();
				Integer weight2 = entry2.getValue().getWeight();

				if (ObjectUtil.equals(null, weight1)) {
					weight1 = 0;
				}

				if (ObjectUtil.equals(null, weight2)) {
					weight2 = 0;
				}
				return weight1 - weight2;
			}
		});
		
		return list;
	}
}

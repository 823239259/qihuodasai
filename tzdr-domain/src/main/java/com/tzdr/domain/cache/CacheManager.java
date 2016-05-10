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
import com.tzdr.domain.dao.DataMapDao;
import com.tzdr.domain.entity.DataMap;

/**
 * 缓存信息 Lin Feng 2014.12.26
 * 
 */
public class CacheManager {

	private static DataMapDao dataMapDao;
	

	public static final Logger log = LoggerFactory
			.getLogger(CacheManager.class);

	/**
	 * 选项缓存信息Map<String, List<Option>>
	 */
	public static Map<String, Map<String, DataMap>> dataMapCache = Maps
			.newHashMap();

	

	/**
	 *  恒生 系统  combine_id 和asset_id 缓存
	 */
	
	
	public CacheManager(DataMapDao dataMapDao) {
		synchronized (this) {
			CacheManager.dataMapDao = dataMapDao;
			CacheManager.init();
		}
	}

	/**
	 * 
	 * 初始化系统缓存信息
	 */
	public static void init() {
		log.info("==============================CacheManager Init Begin==============================");
		dataMapCache.clear();
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
		log.info("==============================CacheManager clear End==============================");
		init();
	}

	/**
	 * 
	 * 初始数据字典
	 */
	public static void initDataMap() {
		try {
			List<DataMap> dataMapList = dataMapDao.findByDeletedFalse();
			for (DataMap dataMap : dataMapList) {
				String typeKey = dataMap.getTypeKey();
				String key = dataMap.getValueKey();
				// String value = dataMap.getValueName();
				Map<String, DataMap> temps = dataMapCache.get(typeKey);
				// 当前配置项不存在，新增一个配置项
				if (null == temps) {
					temps = Maps.newTreeMap();
					dataMapCache.put(typeKey, temps);
				}
				temps.put(key, dataMap);
			}
		} catch (Exception e) {
			log.error(
					"com.tzdr.business.service.CacheManager.initDataMap,error::{}",
					e.getMessage());
		}
	}

	public static Map<String, String> getDataMapByTypeKey(String typeKey) {
		Map<String, DataMap> temps = dataMapCache.get(typeKey);
		if (CollectionUtils.isEmpty(temps)){
			return null;
		}
		List<Map.Entry<String, DataMap>> list = sortDataMap(temps);
		Map<String, String> returnMap = Maps.newHashMap();
		for (Map.Entry<String, DataMap> mapEntry : list) {

			returnMap.put(mapEntry.getKey(), mapEntry.getValue().getValueName());
		}
		return returnMap;

	}

	
	public static List<Map.Entry<String, DataMap>> getDataMapSortListByTypeKey(String typeKey) {
		Map<String, DataMap> temps = dataMapCache.get(typeKey);
		if (CollectionUtils.isEmpty(temps)){
			return null;
		}
		List<Map.Entry<String, DataMap>> list = sortDataMap(temps);
		return list;
	}
	
	public static List<DataMap> getDataMapListByTypeKey(String typeKey) {
		Map<String, DataMap> temps = dataMapCache.get(typeKey);
		if (CollectionUtils.isEmpty(temps)){
			return null;
		}
		List<DataMap> dataMapList = Lists.newArrayList();
		List<Map.Entry<String, DataMap>> list = sortDataMap(temps);
		for (Map.Entry<String, DataMap> entry : list){
			dataMapList.add(entry.getValue());
		}
		return dataMapList;
	}
	
	
	public static String getDataMapByKey(String typeKey, String key) {
		Map<String, DataMap> map = dataMapCache.get(typeKey);
		if (CollectionUtils.isEmpty(map)){
			return null;
		}
		
		DataMap  dataMap = map.get(key);
		if (ObjectUtil.equals(null, dataMap)){
			return null;
		}
		return dataMap.getValueName();
	}

	private static List<Map.Entry<String, DataMap>> sortDataMap(
			Map<String, DataMap> temps) {
		List<Map.Entry<String, DataMap>> list = new ArrayList<Map.Entry<String, DataMap>>(
				temps.entrySet());
		if (CollectionUtils.isEmpty(list)) {
			return null;
		}

		// 然后通过比较器来实现排序
		Collections.sort(list, new Comparator<Map.Entry<String, DataMap>>() {
			@Override
			public int compare(Entry<String, DataMap> entry1,
					Entry<String, DataMap> entry2) {
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
	
	
	
	

	
	public static double getMaxCapitalAmount(){
		return Double.parseDouble(getDataMapByKey(DataDicKeyConstants.TRADE_MAX,DataDicKeyConstants.MAX));
	}
	
	
	/**
	 * 母账户类型8800
	 */
	public static String getParentAccount(){
		return getDataMapByKey(DataDicKeyConstants.PARENT_8800_ACCOUNT_TYPE,DataDicKeyConstants.PARENT_8800);
	}
}

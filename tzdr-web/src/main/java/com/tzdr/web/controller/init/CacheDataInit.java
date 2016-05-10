package com.tzdr.web.controller.init;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hundsun.t2sdk.interfaces.T2SDKException;
import com.tzdr.business.service.api.hundsun.HundsunJres;
import com.tzdr.common.web.support.JsonResult;
import com.tzdr.domain.cache.CacheHomePageManager;
import com.tzdr.domain.cache.CacheManager;
import com.tzdr.domain.dao.DataMapDao;
import com.tzdr.domain.dao.HomePageCinfigDao;
import com.tzdr.domain.dao.operational.OperationalConfigDao;

/**
 * @author zhouchen
 * @version 创建时间：2014年12月27日 上午9:59:01
 * 初始化数据字典数据 保存在缓存中
 * @PostConstruct 详情了解此注解：http://comeonbabye.iteye.com/blog/1399472
 */
@Controller
@RequestMapping("/")
public class CacheDataInit {
	public static final Logger log = LoggerFactory
			.getLogger(CacheDataInit.class);
	
	@Autowired
	private  DataMapDao dataMapDao;
	
	@Autowired
	private  HomePageCinfigDao homePageCinfigDao;
	
	@Autowired
	private  OperationalConfigDao operationalConfigDao;
	
	@PostConstruct
	private void init() {
		new CacheManager(dataMapDao);
		log.info("==============================CacheHomePageManager homepage data Init Begin==============================");
		new CacheHomePageManager(homePageCinfigDao,operationalConfigDao);
		log.info("==============================CacheHomePageManager homepage data Init End==============================");
		
		log.info("==============================CacheManager hundsun data Init Begin==============================");
		//initHundsunData();
		log.info("==============================CacheManager hundsun data Init End==============================");
	}
	/**
	 * 
	 * 初始恒生 combine_id 数据
	 * @throws T2SDKException 
	 */
	public static void initHundsunData() {			
		try {
			HundsunJres.getInstance();	
		} catch (T2SDKException e) {
			log.error("恒生接口初始化失败。", e);
		}
		
	}
	
	
	@RequestMapping("/refreshCache")
	@ResponseBody
	private JsonResult initdata() {
		new CacheManager(dataMapDao);
		log.info("==============================refreshCache homepage data Init Begin==============================");
		new CacheHomePageManager(homePageCinfigDao,operationalConfigDao);
		log.info("==============================refreshCache homepage data Init End==============================");
		return  new JsonResult("刷新成功！");
	}
	
	/**
	 * 刷新首页缓存数据
	 */
	@RequestMapping("/refreshHomePageCache")
	@ResponseBody
	private JsonResult initHomePageData() {
		log.info("==============================refreshHomePageCache data Init Begin==============================");
		new CacheHomePageManager(homePageCinfigDao,operationalConfigDao);
		log.info("==============================refreshHomePageCache data Init End==============================");
		return  new JsonResult("刷新成功！");
	}
	
	
	/**
	 * 刷新首页banner缓存数据
	 */
	@RequestMapping("/refreshBannersCache")
	@ResponseBody
	private JsonResult initBannersData() {
		log.info("==============================refreshBannersCache data Init Begin==============================");
		CacheHomePageManager.refreshBannersCache();
		log.info("==============================refreshBannersCache data Init End==============================");
		return  new JsonResult("刷新成功！");
	}
	
}

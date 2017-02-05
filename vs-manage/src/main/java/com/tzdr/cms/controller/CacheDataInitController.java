package com.tzdr.cms.controller;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hundsun.t2sdk.interfaces.T2SDKException;
import com.tzdr.business.service.api.hundsun.HundsunJres;
import com.tzdr.business.service.combine.CombineInfoService;
import com.tzdr.common.web.support.JsonResult;
import com.tzdr.domain.cache.CacheManager;
import com.tzdr.domain.dao.DataMapDao;

/**
 * @author zhouchen
 * @version 创建时间：2014年12月27日 上午9:59:01
 * 初始化数据字典数据 保存在缓存中
 * @PostConstruct 详情了解此注解：http://comeonbabye.iteye.com/blog/1399472
 */
@Controller
@RequestMapping("/admin/cache")
public class CacheDataInitController {
	
	public static final Logger log = LoggerFactory.getLogger(CacheDataInitController.class);

	
	@Autowired
	private  DataMapDao dataMapDao;
	
	@Autowired
	private  CombineInfoService combineInfoService;
	
	@PostConstruct
	private void init() {
		new CacheManager(dataMapDao);
		
		log.info("==============================CacheManager hundsun data Init Begin==============================");
		//initHundsunData();
		log.info("==============================CacheManager hundsun data Init End==============================");
		
		log.info("==============================combine info Init Begin==============================");
		//combineInfoService.refreshCombineInfos();
		log.info("==============================combine info Init Begin==============================");
	
	}
	
	@RequestMapping("/init")
	@ResponseBody
	private JsonResult initdata() {
		new CacheManager(dataMapDao);
		return  new JsonResult("刷新成功！");
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
}

package com.tzdr.api.controller;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tzdr.common.web.support.JsonResult;
import com.tzdr.domain.cache.CacheManager;
import com.tzdr.domain.dao.DataMapDao;

/**
 * <B>说明: </B>初始化数据字典数据 保存在缓存中
 * @zhouchen
 * 2016年1月20日
 * @PostConstruct 详情了解此注解：http://comeonbabye.iteye.com/blog/1399472
 */
@Controller
@RequestMapping("/")
public class CacheDataInit {
	public static final Logger log = LoggerFactory
			.getLogger(CacheDataInit.class);
	
	@Autowired
	private  DataMapDao dataMapDao;
	
	@PostConstruct
	private void init() {
		new CacheManager(dataMapDao);
		log.info("==============================CacheManager hundsun data Init Begin==============================");
		//initHundsunData();
		log.info("==============================CacheManager hundsun data Init End==============================");
	}	
	
	@RequestMapping("/refreshCache")
	@ResponseBody
	private JsonResult initdata() {
		new CacheManager(dataMapDao);
		return  new JsonResult("刷新成功！");
	}
}

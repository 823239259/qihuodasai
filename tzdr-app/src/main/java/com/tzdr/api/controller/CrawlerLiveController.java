package com.tzdr.api.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tzdr.api.support.ApiResult;
import com.tzdr.business.service.crawler.CrawlerWallstreetnLiveService;
import com.tzdr.domain.web.entity.CrawlerWallstreetnLive;

@Controller
@RequestMapping(value = "/crawler/live")
public class CrawlerLiveController {
	private Logger logger = LoggerFactory.getLogger(CrawlerLiveController.class);
	@Autowired
	private CrawlerWallstreetnLiveService crawlerWallstreetnLiveService;
	/**
	 * 获取数据
	 * @param crawlerWallstreetnLive
	 * @return
	 */
//	@ResponseBody
//	@RequestMapping(value = "/getCrawler",method = RequestMethod.GET)
//	public ApiResult getCrawler(CrawlerWallstreetnLive crawlerWallstreetnLive){
//		
//	}
}

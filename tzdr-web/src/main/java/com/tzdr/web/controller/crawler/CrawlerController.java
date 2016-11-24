package com.tzdr.web.controller.crawler;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tzdr.business.service.crawler.CrawlerCalendarService;
import com.tzdr.business.service.crawler.CrawlerWallstreetnLiveContentService;
import com.tzdr.business.service.crawler.CrawlerWallstreetnLiveService;
import com.tzdr.common.utils.Page;
import com.tzdr.common.web.support.JsonResult;
import com.tzdr.domain.web.entity.CrawlerWallstreetnLive;

@Controller
@RequestMapping(value = "/crawler")
public class CrawlerController {
	private Logger logger = LoggerFactory.getLogger(CrawlerController.class);
	@Autowired
	private CrawlerWallstreetnLiveService crawlerWallstreetnLiveService;
	@Autowired
	private CrawlerCalendarService crawlerCalendarService;
	@Autowired
	private CrawlerWallstreetnLiveContentService crawlerWallstreetnLiveContentService;
	/**
	 * 获取实时新闻数据
	 * @param crawlerWallstreetnLive
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getCrawler",method = RequestMethod.GET)
	public JsonResult getCrawler(CrawlerWallstreetnLive crawlerWallstreetnLive,HttpServletRequest request){
		JsonResult result = new JsonResult(true);
		result.appendData("data", crawlerWallstreetnLiveService.getCrawler(new Page(request)));
		return result;
	}
	/**
	 * 获取日历
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getCrawlerCalendar",method = RequestMethod.GET)
	public JsonResult getCrawlerCalendar(HttpServletRequest request){
		JsonResult result = new JsonResult();
		result.setSuccess(true);
		result.appendData("data",crawlerCalendarService.doGetCrwlerCalendar(new Page(request)));
		return result;
	}
	/**
	 * 实时数据内容
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getCrawlerLiveContent",method = RequestMethod.GET)
	public JsonResult getCrawlerLiveContent(HttpServletRequest request,@RequestParam("liveId")String liveId){
		JsonResult result = new JsonResult(true);
		result.appendData("data",crawlerWallstreetnLiveContentService.doGetCrawlerLiveContent(liveId));
		return result;
	}
}

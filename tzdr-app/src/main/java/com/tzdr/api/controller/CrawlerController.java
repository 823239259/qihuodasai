package com.tzdr.api.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tzdr.api.constants.ResultStatusConstant;
import com.tzdr.api.support.ApiResult;
import com.tzdr.business.service.crawler.CrawlerCalendarService;
import com.tzdr.business.service.crawler.CrawlerWallstreetnLiveContentService;
import com.tzdr.business.service.crawler.CrawlerWallstreetnLiveService;
import com.tzdr.common.utils.Page;
import com.tzdr.domain.web.entity.CrawlerCalendar;
import com.tzdr.domain.web.entity.CrawlerWallstreetnLive;
import com.tzdr.domain.web.entity.CrawlerWallstreetnLiveContent;

import jodd.util.StringUtil;

/**
 * 7*24小时controller
 *
 */
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
	@RequestMapping(value = "/getCrawler",method = RequestMethod.POST)
	public ApiResult getCrawler(HttpServletRequest request){
		
		Map<String,List<CrawlerWallstreetnLive>> result = new HashMap<String,List<CrawlerWallstreetnLive>>();
 		try{
			result.put ("crawlers", crawlerWallstreetnLiveService.getCrawler(new Page(request)));
		}catch(Exception e){
			return new ApiResult(false,ResultStatusConstant.FAIL,"获取数据失败");
		}
		
 		return new ApiResult(true,ResultStatusConstant.SUCCESS,"success",result);
	}
	/**
	 * 获取实时新闻数据
	 * @param crawlerWallstreetnLive
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getCrawlerByChannel",method = RequestMethod.POST)
	public ApiResult getCrawlerByChannel(HttpServletRequest request,@RequestParam(value = "channelset",required = false)String channelset){
		if(StringUtil.isBlank(channelset)){
			return new ApiResult(false,ResultStatusConstant.AUTH_PARAMS_ERROR," no passing parame channelset ");
		}
		Map<String,List<CrawlerWallstreetnLive>> result = new HashMap<String,List<CrawlerWallstreetnLive>>();
 		try{
 			result.put("crawlers", crawlerWallstreetnLiveService.getCrawler(new Page(request),channelset));
		}catch(Exception e){
			return new ApiResult(false,ResultStatusConstant.FAIL,"获取数据失败");
		}
		
 		return new ApiResult(true,ResultStatusConstant.SUCCESS,"success",result);
	}
	/**
	 * 实时数据列表和数据内容
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getCrawlerByChannelLiveContent",method = RequestMethod.POST)
	public ApiResult getCrawlerByChannelLiveContent(HttpServletRequest request,@RequestParam(value = "channelset",required = false)String channelset){
		if(StringUtil.isBlank(channelset)){
			return new ApiResult(false,ResultStatusConstant.AUTH_PARAMS_ERROR," no passing parame channelset ");
		}
		Map<String,List<Map<String, Object>>> result = new HashMap<String,List<Map<String, Object>> >();
		try {
			result.put("crawlers", crawlerWallstreetnLiveService.getCrawlerLiveContent(new Page(request),channelset));
		} catch (Exception e) {
			return new ApiResult(false,ResultStatusConstant.FAIL,"获取数据失败");
		}
		return new ApiResult(true,ResultStatusConstant.SUCCESS,"success",result);
	}
	/**
	 * 获取财经日历
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getCrawlerCalendar",method = RequestMethod.POST)
	public ApiResult getCrawlerCalendar(HttpServletRequest request,@RequestParam("type")String type,@RequestParam("startTime")String startTime,@RequestParam("endTime")String endTime){

		Map<String,List<CrawlerCalendar>> result = new HashMap<String,List<CrawlerCalendar>>() ;
		result.put("calendars", crawlerCalendarService.doGetCrwlerCalendar(new Page(request),type,startTime,endTime));
		return new ApiResult(true,ResultStatusConstant.SUCCESS,"success",result);
		
	}
	/**
	 * 获取日历数据
	 * @param request
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getCrawlerCalendarByTime",method = RequestMethod.POST)
	public ApiResult getCrawlerCalendarByTime(HttpServletRequest request,@RequestParam("startTime") String startTime,@RequestParam("endTime") String endTime){
		
		Map<String,List<CrawlerCalendar>> result = new HashMap<String,List<CrawlerCalendar>>() ;
		result.put("calendars",crawlerCalendarService.doGetCrwlerCalendarByTime(startTime, endTime));
		return new ApiResult(true,ResultStatusConstant.SUCCESS,"success",result);
	}
	/**
	 * 实时数据内容
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getCrawlerLiveContent",method = RequestMethod.POST)
	public ApiResult getCrawlerLiveContent(HttpServletRequest request,@RequestParam("liveId")String liveId){
		Map<String,List<CrawlerWallstreetnLiveContent>> result = new HashMap<String,List<CrawlerWallstreetnLiveContent>>();
		result.put("crawlers",crawlerWallstreetnLiveContentService.doGetCrawlerLiveContent(liveId));
		return new ApiResult(true,ResultStatusConstant.SUCCESS,"success",result);
	}
}

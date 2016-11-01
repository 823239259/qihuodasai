package com.tzdr.cms.timer.wallstreetcn;




import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.tzdr.business.service.crawler.CrawlerCalendarService;
import com.tzdr.business.service.crawler.CrawlerUrlService;
import com.tzdr.business.service.crawler.CrawlerWallstreetnLiveService;
import com.tzdr.cms.utils.HttpUrl;
import com.tzdr.domain.web.entity.CrawlerUrl;

public abstract class BaseWallstreetcnHandle {
	private static final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static CrawlerUrlService crawlerUrlService;
	private static CrawlerCalendarService crawlerCalendarService;
	private static CrawlerWallstreetnLiveService crawlerWallstreetnLiveService;
	/**
	 * 当前请求的url的对象
	 */
	private  CrawlerUrl crawlerUrl;
	public CrawlerUrlService getCrawlerUrlService() {
		return crawlerUrlService;
	}
	public static void setCrawlerUrlService(CrawlerUrlService crawlerUrlService) {
		if(BaseWallstreetcnHandle.crawlerUrlService == null){
			BaseWallstreetcnHandle.crawlerUrlService = crawlerUrlService;
		}
	}
	public CrawlerUrl getCrawlerUrl() {
		return crawlerUrl;
	}
	public void setCrawlerUrl(CrawlerUrl crawlerUrl) {
		this.crawlerUrl = crawlerUrl;
	}
	public static CrawlerCalendarService getCrawlerCalendarService() {
		return crawlerCalendarService;
	}
	public static void setCrawlerCalendarService(CrawlerCalendarService crawlerCalendarService) {
		if(BaseWallstreetcnHandle.crawlerCalendarService == null){
			BaseWallstreetcnHandle.crawlerCalendarService = crawlerCalendarService;
		}
	}
	public static CrawlerWallstreetnLiveService getCrawlerWallstreetnLiveService() {
		return crawlerWallstreetnLiveService;
	}
	public static void setCrawlerWallstreetnLiveService(CrawlerWallstreetnLiveService crawlerWallstreetnLiveService) {
		if(BaseWallstreetcnHandle.crawlerWallstreetnLiveService == null){
			BaseWallstreetcnHandle.crawlerWallstreetnLiveService = crawlerWallstreetnLiveService;
		}
	}
	public static SimpleDateFormat getDf() {
		return df;
	}
	public static String doSend(String url ,String method ,String param){
		String result = "";
		if(method != null && method.length() > 0){
			if(method.equals("GET")){
				result = HttpUrl.httpGet(url,param);
			}else if(method.equals("POST")){
				result = HttpUrl.httpPost(url,param);
			}
			return result;
		}else{
			throw new RuntimeException("请求方式错误");
		}
	}
	public static Long todayTime(){
		Long dateTime = new Date().getTime();
		String date = df.format(dateTime);
		try {
			return df.parse(date).getTime();
		} catch (ParseException e) {
			return dateTime;
		}
	}
	public static Long stringToLongTime(String str){
		 try {
			return df.parse(str).getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		 return null;
	}
}

package com.tzdr.cms.timer.wallstreetcn;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import com.tzdr.business.service.crawler.CrawlerUrlService;
import com.tzdr.cms.utils.HttpUrl;
import com.tzdr.common.utils.SpringUtils;
import com.tzdr.domain.web.entity.CrawlerUrl;
import com.tzdr.domain.web.entity.CrawlerUrlParam;

public abstract class BaseWallstreetcnHandle {
	private static final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static final SimpleDateFormat yyyy_MM_dd = new SimpleDateFormat("yyyy-MM-dd");
	private static CrawlerUrlService crawlerUrlService = SpringUtils.getBean(CrawlerUrlService.class);
	
	/**
	 * 当前请求的url的对象
	 */
	private  CrawlerUrl crawlerUrl;
	private  List<CrawlerUrlParam> crawlerUrlParams;
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
	
	public List<CrawlerUrlParam> getCrawlerUrlParams() {
		return crawlerUrlParams;
	}
	public void setCrawlerUrlParams(List<CrawlerUrlParam> crawlerUrlParams) {
		this.crawlerUrlParams = crawlerUrlParams;
	}
	public static SimpleDateFormat getDf() {
		return df;
	}
	
	public static SimpleDateFormat getYyyyMmDd() {
		return yyyy_MM_dd;
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

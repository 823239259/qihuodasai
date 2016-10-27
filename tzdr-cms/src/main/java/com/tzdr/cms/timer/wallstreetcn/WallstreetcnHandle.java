package com.tzdr.cms.timer.wallstreetcn;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tzdr.business.service.crawler.CrawlerWallstreetnLiveService;
import com.tzdr.cms.utils.HttpUrl;
import com.tzdr.domain.web.entity.CrawlerWallstreetnLive;
import com.tzdr.domain.web.entity.CrawlerWallstreetnLiveContent;

public class WallstreetcnHandle {
	private static Logger logger = LoggerFactory.getLogger(WallstreetcnHandle.class);
	private CrawlerWallstreetnLiveService crawlerWallstreetnLiveService;
	private static final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	/**
	 * 保存上一次拉取的数据第一条的id
	 */
	private static String lastWalklstreetId = "0";
	/**
	 * Unicode 转成中文
	 * @param utfString
	 * @return
	 */
	public static String convert(String utfString){  
	    StringBuilder sb = new StringBuilder();  
	    int i = -1;  
	    int pos = 0;  
	    while((i=utfString.indexOf("\\u", pos)) != -1){  
	        sb.append(utfString.substring(pos, i));  
	        if(i+5 < utfString.length()){  
	            pos = i+6;  
	            sb.append((char)Integer.parseInt(utfString.substring(i+2, i+6), 16));  
	        }  
	    }  
	    return sb.toString();  
	} 
	/**
	 * 获取实时行情数据
	 * @return
	 */
	public  void getWallstreetcn(Wallstreetn wallstreetn){
		String method = wallstreetn.getMethod();
		String result = "";
		if(method != null && method.length() > 0){
			if(method.equals("GET")){
				result = WallstreetcnHandle.httpGetWalls(wallstreetn);
			}else if(method.equals("POST")){
				result = WallstreetcnHandle.httpPostWalls(wallstreetn);
			}
			String stringJson = convert(result);
			handleData(stringJson);
		}else{
			throw new RuntimeException("请求方式错误");
		}
	}
	/**
	 * 处理请求返回数据
	 * @param stringJson
	 */
	public  void handleData(String stringJson){
		List<CrawlerWallstreetnLive> wallstreetnLives = new ArrayList<>();
		List<CrawlerWallstreetnLiveContent> contents = new ArrayList<>();
		try {
			JSONObject resultData = JSONObject.parseObject(stringJson);
			JSONArray resultArray = resultData.getJSONArray("result");
			int size = resultArray.size();
			Long time = todayTime() / 1000;
			Long dateTime = new Date().getTime();
			for (int i = 0; i < size; i++) {
				JSONObject jsonObject = resultArray.getJSONObject(i);
				Long wallstreeTime = jsonObject.getLong("createdAt");
				if(wallstreeTime < time){
					break;
				}
				CrawlerWallstreetnLive live = new CrawlerWallstreetnLive();
				String wallId = jsonObject.getString("id");
				live.setLiveWallstreetnId(wallId);
				if(wallId.equals(lastWalklstreetId)){
					break;
				}
				if(i == 0){
					setLastWalklstreetId(wallId);
				}
				live.setLiveTitle(jsonObject.getString("title"));
				live.setLiveCreatetime(dateTime);
				live.setLiveUpdatetime(dateTime);
				CrawlerWallstreetnLiveContent content = new CrawlerWallstreetnLiveContent();
				content.setLiveContentCreatetime(dateTime);
				content.setLiveContentUpdatetime(dateTime);
				content.setLiveContentHtml(jsonObject.getString("contentHtml"));
				content.setLiveContentText(jsonObject.getString("contentText"));
				content.setLiveContentImage(jsonObject.getJSONObject("text").getString("contentExtra"));
				wallstreetnLives.add(live);
				contents.add(content);
			}
			crawlerWallstreetnLiveService.doSavesBatch(wallstreetnLives, contents);
		} catch (Exception e) {
			logger.info("请求数据异常");
		}
	}
	
	public CrawlerWallstreetnLiveService getCrawlerWallstreetnLiveService() {
		return crawlerWallstreetnLiveService;
	}
	public void setCrawlerWallstreetnLiveService(CrawlerWallstreetnLiveService crawlerWallstreetnLiveService) {
		this.crawlerWallstreetnLiveService = crawlerWallstreetnLiveService;
	}
	public static String getLastWalklstreetId() {
		return lastWalklstreetId;
	}
	public static void setLastWalklstreetId(String lastWalklstreetId) {
		if(WallstreetcnHandle.lastWalklstreetId.equals(lastWalklstreetId)){
			return;
		}
		WallstreetcnHandle.lastWalklstreetId = lastWalklstreetId;
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
	public static String httpGetWalls(Wallstreetn wallstreetn){
		return HttpUrl.httpGet(wallstreetn.getUrl(), wallstreetn.getParam());
	}
	public static String httpPostWalls(Wallstreetn wallstreetn){
		return HttpUrl.httpPost(wallstreetn.getUrl(), wallstreetn.getParam());
	}
}

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
import com.tzdr.business.service.crawler.CrawlerUrlService;
import com.tzdr.business.service.crawler.CrawlerWallstreetnLiveService;
import com.tzdr.cms.utils.HttpUrl;
import com.tzdr.domain.web.entity.CrawlerUrl;
import com.tzdr.domain.web.entity.CrawlerWallstreetnLive;
import com.tzdr.domain.web.entity.CrawlerWallstreetnLiveContent;

public class WallstreetcnHandle {
	private static Logger logger = LoggerFactory.getLogger(WallstreetcnHandle.class);
	private CrawlerWallstreetnLiveService crawlerWallstreetnLiveService;
	private CrawlerUrlService crawlerUrlService;
	private static final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	/**
	 * 当前请求的url的对象
	 */
	private   CrawlerUrl crawlerUrl;
	/**
	 * Unicode 转成中文
	 * @param utfString
	 * @return
	 */
	public static String convert(String utfString){  
		 char aChar;      
		   
	     int len = utfString.length();      
	   
	    StringBuffer outBuffer = new StringBuffer(len);      
	   
	    for (int x = 0; x < len;) {      
	   
	     aChar = utfString.charAt(x++);      
	   
	     if (aChar == '\\') {      
	   
	      aChar = utfString.charAt(x++);      
	   
	      if (aChar == 'u') {      
	   
	       // Read the xxxx      
	   
	       int value = 0;      
	   
	       for (int i = 0; i < 4; i++) {      
	   
	        aChar = utfString.charAt(x++);      
	   
	        switch (aChar) {      
	   
	        case '0':      
	   
	        case '1':      
	   
	        case '2':      
	   
	        case '3':      
	   
	       case '4':      
	   
	        case '5':      
	   
	         case '6':      
	          case '7':      
	          case '8':      
	          case '9':      
	           value = (value << 4) + aChar - '0';      
	           break;      
	          case 'a':      
	          case 'b':      
	          case 'c':      
	          case 'd':      
	          case 'e':      
	          case 'f':      
	           value = (value << 4) + 10 + aChar - 'a';      
	          break;      
	          case 'A':      
	          case 'B':      
	          case 'C':      
	          case 'D':      
	          case 'E':      
	          case 'F':      
	           value = (value << 4) + 10 + aChar - 'A';      
	           break;      
	          default:      
	           throw new IllegalArgumentException(      
	             "Malformed   \\uxxxx   encoding.");      
	          }      
	   
	        }      
	         outBuffer.append((char) value);      
	        } else {      
	         if (aChar == 't')      
	          aChar = '\t';      
	         else if (aChar == 'r')      
	          aChar = '\r';      
	   
	         else if (aChar == 'n')      
	   
	          aChar = '\n';      
	   
	         else if (aChar == 'f')      
	   
	          aChar = '\f';      
	   
	         outBuffer.append(aChar);      
	        }      
	       } else     
	   
	       outBuffer.append(aChar);      
	   
	      }      
	      return outBuffer.toString();      
	} 
	/**
	 * 获取实时行情数据
	 * @return
	 */
	public  void getWallstreetcn(Wallstreetn wallstreetn){
		String method = wallstreetn.getMethod();
		String result = "";
		String lastWallstreetnTime = crawlerUrl.getLastWallstreetnTime();
		if(lastWallstreetnTime == null){
			lastWallstreetnTime = String.valueOf(todayTime() / 1000);
		}
		wallstreetn.setParam(wallstreetn.getParam()+"&"+Long.parseLong(lastWallstreetnTime));
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
	public synchronized void handleData(String param){
		String stringJson = param.replace("\\", "");
		List<CrawlerWallstreetnLive> wallstreetnLives = new ArrayList<>();
		List<CrawlerWallstreetnLiveContent> contents = new ArrayList<>();
		logger.info("请求成功:"+stringJson);
		try {
			JSONObject resultData = JSONObject.parseObject(stringJson);
			JSONArray resultArray = resultData.getJSONArray("results");
			int size = resultArray.size();
			Long dateTime = new Date().getTime();
			for (int i = 0; i < size; i++) {
				JSONObject jsonObject = resultArray.getJSONObject(i);
				CrawlerWallstreetnLive live = setCrawlerWallstreetnLive(jsonObject);
				CrawlerWallstreetnLiveContent content = setCrawlerWllStreetnLiveContent(jsonObject);
				live.setLiveCreatetime(dateTime);
				live.setLiveUpdatetime(dateTime);
				content.setLiveContentCreatetime(dateTime);
				content.setLiveContentUpdatetime(dateTime);
				content.setText(stringJson);
				wallstreetnLives.add(live);
				contents.add(content);
			}
			if(size > 0){
				JSONObject jsonObject = resultArray.getJSONObject(0);
				String wallId = jsonObject.getString("id");
				//更新最新数据第一条数据的id
				crawlerUrl.setLastWallstreetnId(wallId);
				crawlerUrl.setLastWallstreetnTime(jsonObject.getString("createAt"));
				crawlerUrlService.update(crawlerUrl);
			}
			logger.info("共获取:"+size);
			crawlerWallstreetnLiveService.doSavesBatch(wallstreetnLives, contents);
		} catch (Exception e) {
			logger.info("请求数据异常" + e);
		}
	}
	/**
	 * 设置保存的列表信息
	 * @return
	 */
	public CrawlerWallstreetnLive setCrawlerWallstreetnLive(JSONObject jsonObject){
		CrawlerWallstreetnLive live = new CrawlerWallstreetnLive();
		live.setLiveWallstreetnId(jsonObject.getString("id"));
		live.setLiveTitle(jsonObject.getString("title"));
		live.setLiveType("");
		live.setChannelSet(jsonObject.getString("channelSet"));
		live.setCodeType(jsonObject.getString("codeType"));
		live.setCommentStatus(jsonObject.getString("commentStatus"));
		live.setCreatedAt(jsonObject.getLong("createAt"));
		live.setUpdatedAt(jsonObject.getLong("updateAt"));
		live.setImportance(jsonObject.getString("importance"));
		live.setPublished("0");
		live.setStar(jsonObject.getString("start"));
		live.setType(jsonObject.getString("type"));
		return live;
	}
	/**
	 * 设置保存的内容信息
	 * @return
	 */
	public CrawlerWallstreetnLiveContent setCrawlerWllStreetnLiveContent(JSONObject jsonObject){
		CrawlerWallstreetnLiveContent content = new CrawlerWallstreetnLiveContent();
		content.setLiveContentHtml(jsonObject.getString("contentHtml"));
		content.setLiveContentText(jsonObject.getString("contentText"));
		JSONObject textJson = jsonObject.getJSONObject("text");
		content.setContentExtra(textJson.getString("contentExtra"));
		content.setContentFollowup(textJson.getString("contentFollowup"));
		content.setContentAnalysis(textJson.getString("contentAnalysis"));
		content.setMoreText(jsonObject.getString("moreText"));
		content.setMoreImgs(jsonObject.getString("moreImgs"));
		content.setImageUrls(jsonObject.getString("imageUrls"));
		content.setNodeColor(jsonObject.getString("node_color"));
		content.setNodeFormat(jsonObject.getString("node_format"));
		content.setCommentCount(jsonObject.getString("commentCount"));
		content.setContentAnalysis(jsonObject.getString("contentAnalysis"));
		content.setContentExtra(jsonObject.getString("contentExtra"));
		content.setContentFollowup(jsonObject.getString("contentFollowup"));
		content.setData(jsonObject.getString("data"));
		content.setDetailPost(jsonObject.getString("detailPost"));
		content.setHasMore(jsonObject.getString("hasMore"));
		content.setImage(jsonObject.getString("image"));
		content.setImageCount(jsonObject.getString("imageCount"));
		content.setShareCount(jsonObject.getString("shareCount"));
		content.setSourceName(jsonObject.getString("sourceName"));
		content.setSourceUrl(jsonObject.getString("sourceUrl"));
		content.setUserId(jsonObject.getString("userId"));
		content.setVideo(jsonObject.getString("video"));
		content.setVideoCount(jsonObject.getString("videoCount"));
		content.setCategorySet(jsonObject.getString("categorySet"));
		return content;
	}
	public CrawlerWallstreetnLiveService getCrawlerWallstreetnLiveService() {
		return crawlerWallstreetnLiveService;
	}
	public void setCrawlerWallstreetnLiveService(CrawlerWallstreetnLiveService crawlerWallstreetnLiveService) {
		this.crawlerWallstreetnLiveService = crawlerWallstreetnLiveService;
	}
	
	public CrawlerUrlService getCrawlerUrlService() {
		return crawlerUrlService;
	}
	public void setCrawlerUrlService(CrawlerUrlService crawlerUrlService) {
		this.crawlerUrlService = crawlerUrlService;
	}
	
	
	public  CrawlerUrl getCrawlerUrl() {
		return crawlerUrl;
	}
	public  void setCrawlerUrl(CrawlerUrl crawlerUrl) {
		this.crawlerUrl = crawlerUrl;
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

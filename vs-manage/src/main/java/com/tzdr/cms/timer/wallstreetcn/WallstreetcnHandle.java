package com.tzdr.cms.timer.wallstreetcn;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tzdr.business.service.crawler.CrawlerWallstreetnLiveService;
import com.tzdr.common.utils.SpringUtils;
import com.tzdr.domain.web.entity.CrawlerUrl;
import com.tzdr.domain.web.entity.CrawlerWallstreetnLive;
import com.tzdr.domain.web.entity.CrawlerWallstreetnLiveContent;

public class WallstreetcnHandle extends BaseWallstreetcnHandle{
	private static Logger logger = LoggerFactory.getLogger(WallstreetcnHandle.class);
	private static CrawlerWallstreetnLiveService crawlerWallstreetnLiveService = SpringUtils.getBean(CrawlerWallstreetnLiveService.class);
	/**
	 * 获取实时行情数据
	 * @return
	 */
	public  void getWallstreetcn(Wallstreetn wallstreetn){
		try {
			String url = wallstreetn.getUrl();
			String method = wallstreetn.getMethod();
			String lastWallstreetnTime = getCrawlerUrl().getLastWallstreetnTime();
			if(lastWallstreetnTime == null){
				lastWallstreetnTime = String.valueOf(todayTime() / 1000);
			}
			String param = wallstreetn.getParam() + "&min_update="+Long.parseLong(lastWallstreetnTime);
			String result =  doSend(url, method, param);
			handleData(result);
		} catch (Exception e) {
			logger.info("行情任务执行异常:"+e.getMessage());
		}
	}
	/**
	 * 处理请求返回数据
	 * @param stringJson
	 */
	public synchronized void handleData(String param){
		String stringJson = param;
		List<CrawlerWallstreetnLive> wallstreetnLives = new ArrayList<>();
		List<CrawlerWallstreetnLiveContent> contents = new ArrayList<>();
		try {
			JSONObject resultData = JSONObject.parseObject(stringJson);
			JSONArray resultArray = resultData.getJSONArray("results");
			int size = resultArray.size();
			Long dateTime = new Date().getTime();
			for (int i = 0; i < size; i++) {
				JSONObject jsonObject = resultArray.getJSONObject(i);
				String[] channelSets = jsonObject.getString("channelSet").split(",");
				for(int j = 0 ; j < channelSets.length ; j++){
					CrawlerWallstreetnLive live = setCrawlerWallstreetnLive(jsonObject,channelSets[j]);
					CrawlerWallstreetnLiveContent content = setCrawlerWllStreetnLiveContent(jsonObject);
					live.setLiveCreatetime(dateTime);
					live.setLiveUpdatetime(dateTime);
					content.setLiveContentCreatetime(dateTime);
					content.setLiveContentUpdatetime(dateTime);
					content.setText(stringJson);
					wallstreetnLives.add(live);
					contents.add(content);
				}
			}
			logger.info("共获取:"+size);
			getCrawlerWallstreetnLiveService().doSavesBatch(wallstreetnLives, contents);
			if(size > 0){
				JSONObject jsonObject = resultArray.getJSONObject(0);
				String wallId = jsonObject.getString("id");
				//更新最新数据第一条数据的id
				CrawlerUrl crawlerUrl = getCrawlerUrl();
				crawlerUrl = getCrawlerUrlService().get(crawlerUrl.getId());
				crawlerUrl.setLastWallstreetnId(wallId);
				crawlerUrl.setLastWallstreetnTime(jsonObject.getString("createdAt"));
				getCrawlerUrlService().update(crawlerUrl);
				setCrawlerUrl(crawlerUrl);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("数据处理失败:" + e.getMessage());
		}
	}
	/**
	 * 设置保存的列表信息
	 * @return
	 */
	public CrawlerWallstreetnLive setCrawlerWallstreetnLive(JSONObject jsonObject,String channelSet){
		CrawlerWallstreetnLive live = new CrawlerWallstreetnLive();
		live.setLiveWallstreetnId(jsonObject.getString("id"));
		live.setLiveTitle(jsonObject.getString("title"));
		live.setLiveType("");
		live.setChannelSet(channelSet);
		live.setCodeType(jsonObject.getString("codeType"));
		live.setCommentStatus(jsonObject.getString("commentStatus"));
		live.setCreatedAt(jsonObject.getLong("createdAt"));
		live.setUpdatedAt(jsonObject.getLong("updatedAt"));
		live.setImportance(jsonObject.getString("importance"));
		live.setPublished("1");
		live.setStar(jsonObject.getString("star"));
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
		content.setLiveId(jsonObject.getString("id"));
		return content;
	}
	public static CrawlerWallstreetnLiveService getCrawlerWallstreetnLiveService() {
		return crawlerWallstreetnLiveService;
	}
	public static void setCrawlerWallstreetnLiveService(CrawlerWallstreetnLiveService crawlerWallstreetnLiveService) {
		if(WallstreetcnHandle.crawlerWallstreetnLiveService == null){
			WallstreetcnHandle.crawlerWallstreetnLiveService = crawlerWallstreetnLiveService;
		}
	}
}

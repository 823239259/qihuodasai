package com.tzdr.business.service.crawler;

import java.util.List;
import java.util.Map;

import com.tzdr.common.baseservice.BaseService;
import com.tzdr.common.utils.Page;
import com.tzdr.domain.web.entity.CrawlerWallstreetnLive;
import com.tzdr.domain.web.entity.CrawlerWallstreetnLiveContent;

public interface CrawlerWallstreetnLiveService extends BaseService<CrawlerWallstreetnLive>{
	public void doSavesBatch(List<CrawlerWallstreetnLive> crawlerWallstreetnLives,List<CrawlerWallstreetnLiveContent> contents);
	/**
	 * 获取行文
	 * @param page
	 * @return
	 */
	public  List<CrawlerWallstreetnLive> getCrawler(Page page);
	/**
	 * 获取实时新新闻
	 * @param page
	 * @param channelset
	 * @return
	 */
	public  List<CrawlerWallstreetnLive> getCrawler(Page page,String channelset);
	/**
	 * 获取实时新闻列表（包含内容）
	 * @param page
	 * @param channelset
	 * @return
	 */
	public List<Map<String, Object>> getCrawlerLiveContent(Page page,String channelset);
}

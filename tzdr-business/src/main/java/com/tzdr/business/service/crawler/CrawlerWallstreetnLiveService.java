package com.tzdr.business.service.crawler;

import java.util.List;

import com.tzdr.common.baseservice.BaseService;
import com.tzdr.domain.web.entity.CrawlerWallstreetnLive;
import com.tzdr.domain.web.entity.CrawlerWallstreetnLiveContent;

public interface CrawlerWallstreetnLiveService extends BaseService<CrawlerWallstreetnLive>{
	public void doSavesBatch(List<CrawlerWallstreetnLive> crawlerWallstreetnLives,List<CrawlerWallstreetnLiveContent> contents);
	public  List<CrawlerWallstreetnLive> getCrawler();
}

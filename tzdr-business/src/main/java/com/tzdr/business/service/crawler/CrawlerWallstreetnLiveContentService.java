package com.tzdr.business.service.crawler;

import java.util.List;

import com.tzdr.common.baseservice.BaseService;
import com.tzdr.domain.web.entity.CrawlerWallstreetnLiveContent;

public interface CrawlerWallstreetnLiveContentService extends BaseService<CrawlerWallstreetnLiveContent>{
	/**
	 * 获取实时数据
	 * @param liveId
	 * @return
	 */
	public List<CrawlerWallstreetnLiveContent> doGetCrawlerLiveContent(String liveId);
}

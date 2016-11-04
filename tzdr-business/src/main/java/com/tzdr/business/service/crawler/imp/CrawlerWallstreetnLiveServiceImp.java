package com.tzdr.business.service.crawler.imp;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tzdr.business.service.crawler.CrawlerWallstreetnLiveContentService;
import com.tzdr.business.service.crawler.CrawlerWallstreetnLiveService;
import com.tzdr.common.baseservice.BaseServiceImpl;
import com.tzdr.domain.dao.crawler.CrawlerWallstreetnLiveDao;
import com.tzdr.domain.web.entity.CrawlerWallstreetnLive;
import com.tzdr.domain.web.entity.CrawlerWallstreetnLiveContent;

@Service("crawlerWallstreetnLiveService")
@Transactional
public class CrawlerWallstreetnLiveServiceImp extends BaseServiceImpl<CrawlerWallstreetnLive, CrawlerWallstreetnLiveDao> 
		implements CrawlerWallstreetnLiveService{
	private static Logger logger = LoggerFactory.getLogger(CrawlerWallstreetnLiveServiceImp.class);
	@Autowired
	private CrawlerWallstreetnLiveContentService crawlerWallstreetnLiveContentService;
	public void doSavesBatch(List<CrawlerWallstreetnLive> crawlerWallstreetnLives,
			List<CrawlerWallstreetnLiveContent> contents) {
		int size = crawlerWallstreetnLives.size();
		int saveSize = 0;
		for (int i = 0; i < size; i++) {
			CrawlerWallstreetnLive crawlerWallstreetnLive = crawlerWallstreetnLives.get(i);
			List<CrawlerWallstreetnLive> crawlerWallstreetnLives2 = getEntityDao().findByCrawlerId(crawlerWallstreetnLive.getLiveWallstreetnId());
			if(crawlerWallstreetnLives2.size() > 0){
				continue;
			}
			save(crawlerWallstreetnLive);
			crawlerWallstreetnLiveContentService.save(contents.get(i));
			saveSize++;
		}
	   logger.info("新增"+saveSize+"条");
	}
	@Override
	public void getCrawler() {
		
	}
}

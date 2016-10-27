package com.tzdr.business.service.crawler.imp;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tzdr.business.service.crawler.CrawlerWallstreetnLiveContentService;
import com.tzdr.common.baseservice.BaseServiceImpl;
import com.tzdr.domain.dao.crawler.CrawlerWallstreetnLiveContentDao;
import com.tzdr.domain.web.entity.CrawlerWallstreetnLiveContent;

@Service("crawlerWallstreetnLiveContentService")
@Transactional
public class CrawlerWallstreetnLiveContentServiceImp extends BaseServiceImpl<CrawlerWallstreetnLiveContent, CrawlerWallstreetnLiveContentDao>
				implements CrawlerWallstreetnLiveContentService{

}

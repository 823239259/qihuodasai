package com.tzdr.business.service.crawler.imp;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tzdr.business.service.crawler.CrawlerWallstreetnLiveService;
import com.tzdr.common.baseservice.BaseServiceImpl;
import com.tzdr.domain.dao.crawler.CrawlerWallstreetnLiveDao;
import com.tzdr.domain.web.entity.CrawlerWallstreetnLive;

@Service("crawlerWallstreetnLiveService")
@Transactional
public class CrawlerWallstreetnLiveServiceImp extends BaseServiceImpl<CrawlerWallstreetnLive, CrawlerWallstreetnLiveDao> 
		implements CrawlerWallstreetnLiveService{

}

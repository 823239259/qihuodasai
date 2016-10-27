package com.tzdr.domain.dao.crawler;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.tzdr.common.dao.BaseJpaDao;
import com.tzdr.domain.web.entity.CrawlerWallstreetnLive;
/**
 * 实时数据列表
 * @author username
 *
 */
public interface CrawlerWallstreetnLiveDao extends BaseJpaDao<CrawlerWallstreetnLive, String>{

	@Query(value = "from CrawlerWallstreetnLive where liveWallstreetnId = ?1")
	public List<CrawlerWallstreetnLive> findByCrawlerId(String id);
}

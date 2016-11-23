package com.tzdr.domain.dao.crawler;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.tzdr.common.dao.BaseJpaDao;
import com.tzdr.domain.web.entity.CrawlerWallstreetnLiveContent;
/**
 * 实时数据内容
 * @author username
 *
 */
public interface CrawlerWallstreetnLiveContentDao extends BaseJpaDao<CrawlerWallstreetnLiveContent, String>{
	@Query(value = "from CrawlerWallstreetnLiveContent where liveId = ?1")
	public List<CrawlerWallstreetnLiveContent> findByCrawlerContentByLiveId(String liveId);
}

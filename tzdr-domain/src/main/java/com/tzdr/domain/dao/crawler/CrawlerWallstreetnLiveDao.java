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
	/**
	 * 新闻数据
	 * @param startIndex
	 * @param size
	 * @return
	 */
	@Query(value = "select *  from crawler_wallstreetn_live  where published = 1  LIMIT ?1 , ?2", nativeQuery = true)
	public List<CrawlerWallstreetnLive> findByCrawlerPage(int startIndex , int size);
	/**
	 * 新闻数据,根据类型获取
	 * @param startIndex
	 * @param size
	 * @return
	 */
	@Query(value = "select *  from crawler_wallstreetn_live  where published = 1 and channel_set = ?3  LIMIT ?1 , ?2", nativeQuery = true)
	public List<CrawlerWallstreetnLive> findByCrawlerPageByChannel(int startIndex , int size,String channelset);
}

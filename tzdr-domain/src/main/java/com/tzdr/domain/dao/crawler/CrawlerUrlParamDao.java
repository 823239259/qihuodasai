package com.tzdr.domain.dao.crawler;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.tzdr.common.dao.BaseJpaDao;
import com.tzdr.domain.web.entity.CrawlerUrlParam;

/**
 * 请求参数配置
 * @author username
 *
 */
public interface CrawlerUrlParamDao extends BaseJpaDao<CrawlerUrlParam, String> {
	/**
	 * 根据url id获取请求参数
	 * @param urlId
	 * @return
	 */
	@Query("from CrawlerUrlParam where urlParamUrlId = ?1")
	public List<CrawlerUrlParam> doGetawlerUrlParamByUrlId(String urlId);
	
}

package com.tzdr.domain.dao.crawler;

import org.springframework.data.jpa.repository.Query;

import com.tzdr.common.dao.BaseJpaDao;
import com.tzdr.domain.web.entity.CrawlerUrl;
/**
 * url请求路劲配置（第三方数据获取）
 * @author username
 *
 */
public interface CrawlerUrlDao extends BaseJpaDao<CrawlerUrl, String>{
	@Query("from CrawlerUrl where id = ?1")
	public CrawlerUrl doGetDataById(String id);
}

package com.tzdr.business.service.crawler;

import java.util.List;

import com.tzdr.common.baseservice.BaseService;
import com.tzdr.domain.web.entity.CrawlerUrlParam;

public interface CrawlerUrlParamService extends BaseService<CrawlerUrlParam>{
	/**
	 * 根据url id获取请求参数
	 * @param urlId
	 * @return
	 */
	public List<CrawlerUrlParam> doGetawlerUrlParamByUrlId(String urlId);
	/**
	 * 根据URL id删除数据
	 * @param urlId
	 */
	public void doDeleteByUrlId(String urlId);
}

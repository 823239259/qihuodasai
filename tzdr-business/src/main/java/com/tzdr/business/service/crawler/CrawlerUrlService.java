package com.tzdr.business.service.crawler;

import java.util.Map;

import com.tzdr.common.baseservice.BaseService;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.web.support.EasyUiPageInfo;
import com.tzdr.domain.web.entity.CrawlerUrl;

public interface CrawlerUrlService extends BaseService<CrawlerUrl>{
	/**
	 * 获取列表
	 * @param easyUiPage
	 * @param map
	 * @return
	 */
	public PageInfo<Object> doGetCrawlerUrlList(EasyUiPageInfo easyUiPage,Map<String, Object> map);
}

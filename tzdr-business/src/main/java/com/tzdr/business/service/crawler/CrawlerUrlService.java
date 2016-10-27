package com.tzdr.business.service.crawler;

import java.util.List;
import java.util.Map;

import com.tzdr.common.baseservice.BaseService;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.web.support.EasyUiPageInfo;
import com.tzdr.domain.web.entity.CrawlerUrl;
import com.tzdr.domain.web.entity.CrawlerUrlParam;

public interface CrawlerUrlService extends BaseService<CrawlerUrl>{
	/**
	 * 获取列表
	 * @param easyUiPage
	 * @param map
	 * @return
	 */
	public PageInfo<Object> doGetCrawlerUrlList(EasyUiPageInfo easyUiPage,Map<String, Object> map);
	/**
	 * 根据id获取数据
	 * @param id
	 * @return
	 */
	public CrawlerUrl doGetDataById(String id);
	/**
	 * 新增数据
	 * @param crawlerUrl
	 * @param crawlerUrlParam
	 */
	public void doSave(CrawlerUrl crawlerUrl , List<CrawlerUrlParam> crawlerUrlParam);
	/**
	 * 删除url数据
	 * @param urlId
	 */
	public void doDeleteByUrlId(String urlId);
}

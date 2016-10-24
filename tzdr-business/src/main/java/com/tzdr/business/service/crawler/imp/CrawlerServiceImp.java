package com.tzdr.business.service.crawler.imp;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.tzdr.business.service.crawler.CrawlerUrlService;
import com.tzdr.common.baseservice.BaseServiceImpl;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.web.support.EasyUiPageInfo;
import com.tzdr.common.web.support.MultiListParam;
import com.tzdr.domain.dao.crawler.CrawlerUrlDao;
import com.tzdr.domain.vo.CrawlerUrlVo;
import com.tzdr.domain.web.entity.CrawlerUrl;

@Service("crawlerService")
@Transactional
public class CrawlerServiceImp extends BaseServiceImpl<CrawlerUrl, CrawlerUrlDao> implements CrawlerUrlService{
	@Override
	public PageInfo<Object> doGetCrawlerUrlList(EasyUiPageInfo easyUiPage, Map<String, Object> map) {
		PageInfo<Object> pageInfo = new PageInfo<Object>(easyUiPage.getRows(),easyUiPage.getPage());
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT c.id,\n");
		sql.append("		c.url_title as urlTitle,\n");
		sql.append("		c.url_url as urlUrl,\n");
		sql.append("		c.status as status,\n");
		sql.append("		c.url_remarks as urlRemarks,\n");
		sql.append("		c.url_method as urlMethod,\n");
		sql.append("		c.exec_rule as execRule\n");
		/*sql.append("		c.last_opentime as lastOpentime, \n");
		sql.append("		c.url_createtime as urlCreatetime,\n");
		sql.append("		c.url_updatetime as urlUpdatetime\n");*/
		sql.append("  FROM crawler_url c\n");
		
		List<Object> params = Lists.newArrayList();
		MultiListParam multilistParam = new MultiListParam(easyUiPage,map, params, sql.toString());
		pageInfo = multiListPageQuery(multilistParam, CrawlerUrlVo.class);
		
		return pageInfo;
	}
}

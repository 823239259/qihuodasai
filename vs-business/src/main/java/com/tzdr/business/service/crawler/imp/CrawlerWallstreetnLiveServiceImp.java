package com.tzdr.business.service.crawler.imp;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tzdr.business.service.crawler.CrawlerWallstreetnLiveContentService;
import com.tzdr.business.service.crawler.CrawlerWallstreetnLiveService;
import com.tzdr.common.baseservice.BaseServiceImpl;
import com.tzdr.common.utils.Dates;
import com.tzdr.common.utils.Page;
import com.tzdr.domain.dao.crawler.CrawlerWallstreetnLiveDao;
import com.tzdr.domain.web.entity.CrawlerWallstreetnLive;
import com.tzdr.domain.web.entity.CrawlerWallstreetnLiveContent;

@Service("crawlerWallstreetnLiveService")
@Transactional
public class CrawlerWallstreetnLiveServiceImp extends BaseServiceImpl<CrawlerWallstreetnLive, CrawlerWallstreetnLiveDao> 
		implements CrawlerWallstreetnLiveService{
	private static Logger logger = LoggerFactory.getLogger(CrawlerWallstreetnLiveServiceImp.class);
	@Autowired
	private CrawlerWallstreetnLiveContentService crawlerWallstreetnLiveContentService;
	public void doSavesBatch(List<CrawlerWallstreetnLive> crawlerWallstreetnLives,
			List<CrawlerWallstreetnLiveContent> contents) {
		int size = crawlerWallstreetnLives.size();
		int saveSize = 0;
		for (int i = 0; i < size; i++) {
			CrawlerWallstreetnLive crawlerWallstreetnLive = crawlerWallstreetnLives.get(i);
			List<CrawlerWallstreetnLive> crawlerWallstreetnLives2 = getEntityDao().findByCrawlerId(crawlerWallstreetnLive.getLiveWallstreetnId());
			if(crawlerWallstreetnLives2.size() > 0){
				continue;
			}
			save(crawlerWallstreetnLive);
			crawlerWallstreetnLiveContentService.save(contents.get(i));
			saveSize++;
		}
	   logger.info("新增"+saveSize+"条");
	}
	@Override
	public List<CrawlerWallstreetnLive> getCrawler(Page page) {
		return  getEntityDao().findByCrawlerPage(page.getStartIndex(), page.getSize());
	}
	@Override
	public List<CrawlerWallstreetnLive> getCrawler(Page page, String channelset) {
		return  getEntityDao().findByCrawlerPageByChannel(page.getStartIndex(), page.getSize(),channelset);
	}
	@Override
	public List<Map<String, Object>> getCrawlerLiveContent(Page page, String channelset) {
		StringBuffer buffer = new StringBuffer();
		/*buffer.append("select l.id,l.published,l.live_title as liveTitle,"
				+ "l.live_wallstreetn_id as liveWallstreetnId,l.live_type as liveType,"
				+ "l.live_createtime as liveCreatetime,l.live_updatetime as liveUpdatetime,"
				+ "l.channel_set as channelSet,l.type,l.code_type as codeType,"
				+ "l.importance,l.created_at as createdAt,l.updated_at updatedAt,"
				+ "l.comment_status as commentStatus,l.star,"
				+ "c.live_content_html as liveContent,c.detail_post as detailPost,c.more_imgs as moreImages  "
				+ " from crawler_wallstreetn_live l ,crawler_wallstreetn_live_content c "
				+ " where "
				+ " l.published = 1 "
				+ " and l.channel_set = '"+channelset+"' "
				+ " and c.live_id=l.live_wallstreetn_id "
				+ " GROUP BY l.channel_set,l.live_wallstreetn_id "
				+ " order by created_at desc  LIMIT "+page.getStartIndex()+" , "+page.getSize()+"");*/
		Long time =  Dates.addDay(new Date(), -7).getTime();
		buffer.append("select l.id,l.live_title as liveTitle,"
				+ "l.live_createtime as liveCreatetime,"
				+ "l.created_at as createdAt,"
				+ "c.live_content_html as liveContent "
				+ " from crawler_wallstreetn_live l ,crawler_wallstreetn_live_content c "
				+ " where "
				+ " l.published = 1 "
				+ " and l.live_createtime > "+time+" "
				+ " and l.channel_set = '"+channelset+"' "
				+ " and c.live_id=l.live_wallstreetn_id "
				+ " GROUP BY l.channel_set,l.live_wallstreetn_id "
				+ " order by created_at desc  LIMIT "+page.getStartIndex()+" , "+page.getSize()+"");
		return getEntityDao().queryMapBySql(buffer.toString(), null);
	}
}

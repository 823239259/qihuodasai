package com.tzdr.cms.timer.wallstreetcn;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import com.tzdr.business.service.crawler.CrawlerUrlParamService;
import com.tzdr.business.service.crawler.CrawlerUrlService;
import com.tzdr.common.utils.SpringUtils;
import com.tzdr.domain.web.entity.CrawlerUrl;
import com.tzdr.domain.web.entity.CrawlerUrlParam;
/**
 * 采集第三方数据（华尔街见闻）服务器启动监听
 * @author username
 *
 */
@Service
public class WallstreetcnStartupListener implements ApplicationListener<ContextRefreshedEvent>{
	private Logger logger = LoggerFactory.getLogger(WallstreetcnStartupListener.class);
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if (event.getApplicationContext().getParent() == null) {
				/*CrawlerUrlService crawlerUrlService = SpringUtils.getBean(CrawlerUrlService.class);
				CrawlerUrlParamService crawlerUrlParamService = SpringUtils.getBean(CrawlerUrlParamService.class);
				List<CrawlerUrl> crawlerUrls = crawlerUrlService.getAll();
				for (CrawlerUrl crawlerUrl : crawlerUrls) {
					//1-需要在程序启动时加入的定时任务
					if(crawlerUrl.getStatus().equals("1")){
						List<CrawlerUrlParam> crawlerUrlParams = crawlerUrlParamService.doGetawlerUrlParamByUrlId(crawlerUrl.getId());
						WallstreetcnEntry entry = new WallstreetcnEntry();
						try {
							entry.exected(crawlerUrl, crawlerUrlParams);
							logger.info("任务启动成功:{}",crawlerUrl.getUrlUrl());
						} catch (Exception e) {
							crawlerUrl.setStatus("0");
							crawlerUrlService.update(crawlerUrl);
							logger.info("任务启动失败:{}",crawlerUrl.getUrlUrl());
						}
						
					}
				}*/
			
        }
	}
}

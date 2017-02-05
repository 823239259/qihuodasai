package com.tzdr.cms.controller.crawler;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tzdr.common.baseservice.BaseService;
import com.tzdr.common.web.BaseController;
import com.tzdr.domain.web.entity.CrawlerWallstreetnLiveContent;

@Controller
@RequestMapping(value = "/admin/crawler/walls/content")
public class CrawlerWallstreetnLiveContentController extends BaseController<CrawlerWallstreetnLiveContent>{

	@Override
	public BaseService<CrawlerWallstreetnLiveContent> getBaseService() {
		// TODO Auto-generated method stub
		return null;
	}

}

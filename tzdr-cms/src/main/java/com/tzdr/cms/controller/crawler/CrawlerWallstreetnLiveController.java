package com.tzdr.cms.controller.crawler;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tzdr.common.baseservice.BaseService;
import com.tzdr.common.web.BaseController;
import com.tzdr.domain.web.entity.CrawlerWallstreetnLive;

@Controller
@RequestMapping(value = "/admin/crawler/live")
public class CrawlerWallstreetnLiveController extends BaseController<CrawlerWallstreetnLive>{

	@Override
	public BaseService<CrawlerWallstreetnLive> getBaseService() {
		// TODO Auto-generated method stub
		return null;
	}

}

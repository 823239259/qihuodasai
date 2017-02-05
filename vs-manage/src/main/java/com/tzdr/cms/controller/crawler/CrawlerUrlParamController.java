package com.tzdr.cms.controller.crawler;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tzdr.common.baseservice.BaseService;
import com.tzdr.common.web.BaseController;
import com.tzdr.domain.web.entity.CrawlerUrlParam;

@Controller
@RequestMapping(value = "/admin/crawler/url/param")
public class CrawlerUrlParamController extends BaseController<CrawlerUrlParam>{

	@Override
	public BaseService<CrawlerUrlParam> getBaseService() {
		// TODO Auto-generated method stub
		return null;
	}

}

package com.tzdr.cms.controller.crawler;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tzdr.common.baseservice.BaseService;
import com.tzdr.common.web.BaseController;
import com.tzdr.domain.web.entity.CrawlerCalendarHistory;

@Controller
@RequestMapping(value = "/crawler/backup")
public class CrawlerBackupHistoryController extends BaseController<CrawlerCalendarHistory>{
	
	@Override
	public BaseService<CrawlerCalendarHistory> getBaseService() {
		// TODO Auto-generated method stub
		return null;
	}

}

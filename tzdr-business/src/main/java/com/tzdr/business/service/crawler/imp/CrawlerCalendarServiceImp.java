package com.tzdr.business.service.crawler.imp;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tzdr.business.service.crawler.CrawlerCalendarService;
import com.tzdr.common.baseservice.BaseServiceImpl;
import com.tzdr.domain.dao.crawler.CrawlerCalendarDao;
import com.tzdr.domain.web.entity.CrawlerCalendar;

@Transactional
@Service("crawlerCalendarService")
public class CrawlerCalendarServiceImp  extends BaseServiceImpl<CrawlerCalendar, CrawlerCalendarDao> implements CrawlerCalendarService{
	private Logger logger = LoggerFactory.getLogger(CrawlerCalendarServiceImp.class);
	@Override
	public CrawlerCalendar doGetCrawlerCalendarByCalendarId(String calendarId) {
		List<CrawlerCalendar> calendars = getEntityDao().findByCalendar(calendarId);
		if(calendars.size() > 0){
			return calendars.get(0);
		}else{
			return null;
		}
	}
	@Override
	public void doSaveCrawlerCalendarList(List<CrawlerCalendar> calendars) {
		int size = calendars.size();
		int saveSize = 0;
		int updateSize = 0;
		for(int i = 0 ; i < size ; i++){
			CrawlerCalendar calendar = calendars.get(i);
			CrawlerCalendar crawlerCalendar = this.doGetCrawlerCalendarByCalendarId(calendar.getCalendarId());
			if(crawlerCalendar == null){
				getEntityDao().save(calendar);
				saveSize++;
			}else{
				crawlerCalendar.setAccurateFlag(calendar.getAccurateFlag());
				crawlerCalendar.setActual(calendar.getActual());
				crawlerCalendar.setActualw(calendar.getActualw());
				crawlerCalendar.setCalendarId(calendar.getCalendarId());
				crawlerCalendar.setCalendarType(calendar.getCalendarType());
				crawlerCalendar.setCategoryId(calendar.getCategoryId());
				crawlerCalendar.setCountry(calendar.getCountry());
				crawlerCalendar.setCurrency(calendar.getCurrency());
				crawlerCalendar.setDescription(calendar.getDescription());
				crawlerCalendar.setEventRowId(calendar.getEventRowId());
				crawlerCalendar.setFlagUrl(calendar.getFlagUrl());
				crawlerCalendar.setForecast(calendar.getForecast());
				crawlerCalendar.setForecastw(calendar.getForecastw());
				crawlerCalendar.setImportance(calendar.getImportance());
				crawlerCalendar.setLevel(calendar.getLevel());
				crawlerCalendar.setLocalDateTime(calendar.getLocalDateTime());
				crawlerCalendar.setMark(calendar.getMark());
				crawlerCalendar.setPrevious(calendar.getPrevious());
				crawlerCalendar.setPreviousw(calendar.getPreviousw());
				crawlerCalendar.setPushStatus(calendar.getPushStatus());
				crawlerCalendar.setRelatedAssets(calendar.getRelatedAssets());
				crawlerCalendar.setRemark(calendar.getRemark());
				crawlerCalendar.setRevised(calendar.getRevised());
				crawlerCalendar.setStars(calendar.getStars());
				crawlerCalendar.setTicker(calendar.getTicker());
				crawlerCalendar.setTimestamp(calendar.getTimestamp());
				crawlerCalendar.setTitle(calendar.getTitle());
				crawlerCalendar.setUnderline(calendar.getUnderline());
				getEntityDao().update(crawlerCalendar);
				updateSize++;
			}
		}
		logger.info("增加成功:{}条,更新成功:{}条",saveSize,updateSize);
	}
}

package com.tzdr.business.service.crawler.imp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tzdr.business.service.crawler.CrawlerBackUpHistoryService;
import com.tzdr.business.service.crawler.CrawlerCalendarService;
import com.tzdr.common.baseservice.BaseServiceImpl;
import com.tzdr.domain.dao.crawler.CrawlerCalenDarBackUpHistoryDao;
import com.tzdr.domain.web.entity.CrawlerCalendar;
import com.tzdr.domain.web.entity.CrawlerCalendarHistory;

@Transactional
@Service("crawlerBackUpHistoryService")
public class CrawlerBackUpHistoryServiceImp extends BaseServiceImpl<CrawlerCalendarHistory, CrawlerCalenDarBackUpHistoryDao> implements CrawlerBackUpHistoryService{
	private Logger logger = LoggerFactory.getLogger(CrawlerBackUpHistoryServiceImp.class);
	@Autowired 
	private CrawlerCalendarService calendarService;
	public void backUpHistory(Date start,Date end){
		List<CrawlerCalendar> calendars = calendarService.findByCreateTimeBetween(start.getTime()/1000, end.getTime()/1000);
		int size = calendars.size();
		if(size == 0){
			return;
		}
		List<CrawlerCalendarHistory> calendarHistories = new ArrayList<>();
		Long time = new Date().getTime();
		for(int i = 0 ; i < size ; i++){
			CrawlerCalendar calendar = calendars.get(i);
			CrawlerCalendarHistory calendarHistory =  new CrawlerCalendarHistory();
			calendarHistory.setAccurateFlag(calendar.getAccurateFlag());
			calendarHistory.setActual(calendar.getActual());
			calendarHistory.setActualw(calendar.getActualw());
			calendarHistory.setCalendarId(calendar.getCalendarId());
			calendarHistory.setCalendarType(calendar.getCalendarType());
			calendarHistory.setCategoryId(calendar.getCategoryId());
			calendarHistory.setCountry(calendar.getCountry());
			calendarHistory.setCurrency(calendar.getCurrency());
			calendarHistory.setDescription(calendar.getDescription());
			calendarHistory.setEventRowId(calendar.getEventRowId());
			calendarHistory.setFlagUrl(calendar.getFlagUrl());
			calendarHistory.setForecast(calendar.getForecast());
			calendarHistory.setForecastw(calendar.getForecastw());
			calendarHistory.setImportance(calendar.getImportance());
			calendarHistory.setLevel(calendar.getLevel());
			calendarHistory.setLocalDateTime(calendar.getLocalDateTime());
			calendarHistory.setMark(calendar.getMark());
			calendarHistory.setPrevious(calendar.getPrevious());
			calendarHistory.setPreviousw(calendar.getPreviousw());
			calendarHistory.setPushStatus(calendar.getPushStatus());
			calendarHistory.setRelatedAssets(calendar.getRelatedAssets());
			calendarHistory.setRemark(calendar.getRemark());
			calendarHistory.setRevised(calendar.getRevised());
			calendarHistory.setStars(calendar.getStars());
			calendarHistory.setTicker(calendar.getTicker());
			calendarHistory.setTimestamp(calendar.getTimestamp());
			calendarHistory.setTitle(calendar.getTitle());
			calendarHistory.setUnderline(calendar.getUnderline());
			calendarHistory.setCreateTime(time);
			calendarHistory.setUpdateTime(time);
			calendarHistories.add(calendarHistory);
		}
		getEntityDao().saves(calendarHistories);
		calendarService.deleteBatch(calendars);
		logger.info(new Date()+"备份成功:新增{}条,备份时间段为：{}-{}",size,start,end);
	}
}

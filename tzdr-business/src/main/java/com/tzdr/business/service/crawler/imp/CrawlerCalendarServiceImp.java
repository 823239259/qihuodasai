package com.tzdr.business.service.crawler.imp;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tzdr.business.service.crawler.CrawlerCalendarService;
import com.tzdr.common.baseservice.BaseServiceImpl;
import com.tzdr.common.utils.Page;
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
	public List<CrawlerCalendar> findByCreateTimeBetween(Long startTime,Long endTime){
		return getEntityDao().findByCalendarCreateBetween(startTime, endTime);
	}
	@Override
	public void deleteBatch(List<CrawlerCalendar> entities) {
		getEntityDao().deleteInBatch(entities);
	}
	@Override
	public List<CrawlerCalendar> doGetCrwlerCalendar(Page page,String type,String startTime,String endTime) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date startDate;
		try {
			startDate = df.parse(startTime);
			Date endDate = df.parse(endTime);
			return getEntityDao().findByCalerdarPage(page.getPageIndex(), page.getSize(),type,startDate.getTime()/1000,endDate.getTime()/1000);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
		return null;
	}
	@Override
	public List<CrawlerCalendar> doGetCrwlerCalendarByTime( String startTime, String endTime) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date startDate;
		try {
			startDate = df.parse(startTime);
			Date endDate = df.parse(endTime);
			return getEntityDao().findByCalerdarPageByTime( startDate.getTime()/1000, endDate.getTime()/1000);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
}

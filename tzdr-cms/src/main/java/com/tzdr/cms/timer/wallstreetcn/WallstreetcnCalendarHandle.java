package com.tzdr.cms.timer.wallstreetcn;


import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tzdr.domain.web.entity.CrawlerCalendar;
import com.tzdr.domain.web.entity.CrawlerUrl;

public class WallstreetcnCalendarHandle extends BaseWallstreetcnHandle{
	private Logger logger = LoggerFactory.getLogger(WallstreetcnCalendarHandle.class);
	
	public void getWallstreetcnCalendar(Wallstreetn wallstreetn){
		String url = wallstreetn.getUrl();
		String method = wallstreetn.getMethod();
		String param = wallstreetn.getParam();
		String result = doSend(url, method, param);
		handLeData(result);
	}
	public synchronized void handLeData(String param){
		String stringJson = param;
		JSONArray array = JSONObject.parseObject(stringJson).getJSONArray("results");
		List<CrawlerCalendar> calendars = setCrawlerCalendarList(array);
		if(calendars.size() > 0){
			JSONObject object = array.getJSONObject(0);
			CrawlerUrl crawlerUrl = getCrawlerUrlService().get(getCrawlerUrl().getId());
			crawlerUrl.setLastWallstreetnId(object.getString("id"));
			crawlerUrl.setLastWallstreetnTime(object.getString("timestamp"));
			getCrawlerUrlService().update(crawlerUrl);
		}
		logger.info("获取数据{}",calendars.size());
		getCrawlerCalendarService().doSaveCrawlerCalendarList(calendars);
	}
	/**
	 * 设置结果数据到实体对象
	 * @param jsonObject
	 * @return
	 */
	public List<CrawlerCalendar> setCrawlerCalendarList(JSONArray array){
		List<CrawlerCalendar> calendars = new ArrayList<>();
		int size = array.size();
		for(int i = 0 ; i < size ; i++){
			JSONObject object = array.getJSONObject(i);
			String calendarId = object.getString("id");
			CrawlerCalendar calendar = new CrawlerCalendar();;
			calendar.setCalendarId(calendarId);
			calendar.setEventRowId(object.getString("eventRowId"));
			calendar.setTimestamp(object.getLong("timestamp"));
			calendar.setLocalDateTime(stringToLongTime(object.getString("localDateTime")));
			calendar.setImportance(object.getString("importance"));
			calendar.setStars(object.getString("stars"));
			calendar.setTitle(object.getString("title"));
			calendar.setTicker(object.getString("ticker"));
			calendar.setForecast(object.getString("forecast"));
			calendar.setActual(object.getString("actual"));
			calendar.setPrevious(object.getString("previous"));
			calendar.setRevised(object.getString("revised"));
			calendar.setCategoryId(object.getString("category_id"));
			calendar.setRelatedAssets(object.getString("relatedAssets"));
			calendar.setRemark(object.getString("remark"));
			calendar.setMark(object.getString("mark"));
			calendar.setUnderline(object.getString("underline"));
			calendar.setAccurateFlag(object.getString("accurateFlag"));
			calendar.setLevel(object.getString("level"));
			calendar.setPushStatus(object.getString("pushStatus"));
			calendar.setCountry(object.getString("country"));
			calendar.setCurrency(object.getString("currency"));
			calendar.setCalendarType(object.getString("calendarType"));
			calendar.setDescription(object.getString("description"));
			calendar.setFlagUrl(object.getString("flagUrl"));
			calendar.setForecastw(object.getString("forecast_w"));
			calendar.setActualw(object.getString("actual_w"));
			calendar.setPreviousw(object.getString("previous_w"));
			calendars.add(calendar);
		}
		return calendars;
	}
}

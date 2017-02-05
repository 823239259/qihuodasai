package com.tzdr.cms.timer.wallstreetcn;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tzdr.business.service.crawler.CrawlerCalendarService;
import com.tzdr.common.utils.DateUtils;
import com.tzdr.common.utils.SpringUtils;
import com.tzdr.domain.web.entity.CrawlerCalendar;
import com.tzdr.domain.web.entity.CrawlerUrl;
import com.tzdr.domain.web.entity.CrawlerUrlParam;

public class WallstreetcnCalendarHandle extends BaseWallstreetcnHandle{
	private Logger logger = LoggerFactory.getLogger(WallstreetcnCalendarHandle.class);
	private static CrawlerCalendarService crawlerCalendarService = SpringUtils.getBean(CrawlerCalendarService.class);
	private static String startKey  = "start";
	private static String endKey = "end";
	public void getWallstreetcnCalendar(Wallstreetn wallstreetn){
		try {
			String url = wallstreetn.getUrl();
			String method = wallstreetn.getMethod();
			String param = wallstreetn.getParam();
			int size  = getCrawlerUrlParams().size();
			Date date  = new Date();
			String start = getYyyyMmDd().format(DateUtils.addDates(date, 0));
			String end = getYyyyMmDd().format(DateUtils.addDates(date, 15));
			String key = "";
			String value = "";
			for (int i = 0; i < size; i++) {
			   CrawlerUrlParam crawlerUrlParam = getCrawlerUrlParams().get(i);
			   key = crawlerUrlParam.getUrlParamKey();
			   value = crawlerUrlParam.getUrlParamValue();
			   if(key.equals(startKey)){
				   start = value;
			   }
			   if(key.equals(endKey)){
				   end = value;
			   }
			}
			if(param != null && param.length() > 0){
				param += "&";
			}
			param = startKey+"="+start+"&"+endKey+"="+end;
			String result = doSend(url, method, param);
			handLeData(result);
		} catch (Exception e) {
			logger.info("日历任务执行异常:"+e.getMessage());
		}
		
	}
	public synchronized void handLeData(String param){
		String stringJson = param;
		JSONArray array = JSONObject.parseObject(stringJson).getJSONArray("results");
		List<CrawlerCalendar> calendars = setCrawlerCalendarList(array);
		/*logger.info("获取数据{}",calendars.size());*/
		getCrawlerCalendarService().doSaveCrawlerCalendarList(calendars);
		if(calendars.size() > 0){
			JSONObject object = array.getJSONObject(0);
			CrawlerUrl crawlerUrl = getCrawlerUrlService().get(getCrawlerUrl().getId());
			crawlerUrl.setLastWallstreetnId(object.getString("id"));
			crawlerUrl.setLastWallstreetnTime(object.getString("timestamp"));
			getCrawlerUrlService().update(crawlerUrl);
		}
	}
	/**
	 * 设置结果数据到实体对象
	 * @param jsonObject
	 * @return
	 */
	public List<CrawlerCalendar> setCrawlerCalendarList(JSONArray array){
		List<CrawlerCalendar> calendars = new ArrayList<>();
		int size = array.size();
		Long date = todayTime();
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
			calendar.setCreateTime(date);
			calendar.setUpdateTime(date);
			calendars.add(calendar);
		}
		return calendars;
	}
	public static CrawlerCalendarService getCrawlerCalendarService() {
		return crawlerCalendarService;
	}
	public static void setCrawlerCalendarService(CrawlerCalendarService crawlerCalendarService) {
		if(WallstreetcnCalendarHandle.crawlerCalendarService == null){
			WallstreetcnCalendarHandle.crawlerCalendarService = crawlerCalendarService;
		}
	}
}

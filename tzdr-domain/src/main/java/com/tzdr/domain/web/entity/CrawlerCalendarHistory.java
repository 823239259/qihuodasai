package com.tzdr.domain.web.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.tzdr.common.domain.BaseEntity;

/**
 * 财经日历实体历史数据实体
 * @author username
 *
 */
@Entity
@Table(name = "crawler_calendar_history")
public class CrawlerCalendarHistory extends BaseEntity{

	private static final long serialVersionUID = -29398450382820226L;
	private String calendarId;
	private String eventRowId;
	private Long timestamp;
	private Long localDateTime;
	private String importance;
	private String stars;
	private String title;
	private String ticker;
	private String forecast;
	private String actual;
	private String previous;
	private String revised;
	private String categoryId;
	private String relatedAssets;
	private String remark;
	private String mark;
	private String underline;
	private String accurateFlag;
	private String level;
	private String pushStatus;
	private String country;
	private String currency;
	private String calendarType;
	private String description;
	private String flagUrl;
	private String forecastw;
	private String actualw;
	private String previousw;
	private Long createTime;
	private Long updateTime;
	
	
	public Long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}
	public Long getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Long updateTime) {
		this.updateTime = updateTime;
	}
	public String getCalendarId() {
		return calendarId;
	}
	public void setCalendarId(String calendarId) {
		this.calendarId = calendarId;
	}
	public String getEventRowId() {
		return eventRowId;
	}
	public void setEventRowId(String eventRowId) {
		this.eventRowId = eventRowId;
	}
	
	public Long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}
	public Long getLocalDateTime() {
		return localDateTime;
	}
	public void setLocalDateTime(Long localDateTime) {
		this.localDateTime = localDateTime;
	}
	public String getImportance() {
		return importance;
	}
	public void setImportance(String importance) {
		this.importance = importance;
	}
	public String getStars() {
		return stars;
	}
	public void setStars(String stars) {
		this.stars = stars;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getTicker() {
		return ticker;
	}
	public void setTicker(String ticker) {
		this.ticker = ticker;
	}
	public String getForecast() {
		return forecast;
	}
	public void setForecast(String forecast) {
		this.forecast = forecast;
	}
	public String getActual() {
		return actual;
	}
	public void setActual(String actual) {
		this.actual = actual;
	}
	public String getPrevious() {
		return previous;
	}
	public void setPrevious(String previous) {
		this.previous = previous;
	}
	public String getRevised() {
		return revised;
	}
	public void setRevised(String revised) {
		this.revised = revised;
	}
	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	public String getRelatedAssets() {
		return relatedAssets;
	}
	public void setRelatedAssets(String relatedAssets) {
		this.relatedAssets = relatedAssets;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getMark() {
		return mark;
	}
	public void setMark(String mark) {
		this.mark = mark;
	}
	public String getUnderline() {
		return underline;
	}
	public void setUnderline(String underline) {
		this.underline = underline;
	}
	public String getAccurateFlag() {
		return accurateFlag;
	}
	public void setAccurateFlag(String accurateFlag) {
		this.accurateFlag = accurateFlag;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getPushStatus() {
		return pushStatus;
	}
	public void setPushStatus(String pushStatus) {
		this.pushStatus = pushStatus;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getCalendarType() {
		return calendarType;
	}
	public void setCalendarType(String calendarType) {
		this.calendarType = calendarType;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getFlagUrl() {
		return flagUrl;
	}
	public void setFlagUrl(String flagUrl) {
		this.flagUrl = flagUrl;
	}
	public String getForecastw() {
		return forecastw;
	}
	public void setForecastw(String forecastw) {
		this.forecastw = forecastw;
	}
	public String getActualw() {
		return actualw;
	}
	public void setActualw(String actualw) {
		this.actualw = actualw;
	}
	public String getPreviousw() {
		return previousw;
	}
	public void setPreviousw(String previousw) {
		this.previousw = previousw;
	}
	
}

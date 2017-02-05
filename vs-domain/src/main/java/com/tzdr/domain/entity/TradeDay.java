package com.tzdr.domain.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import jodd.util.ObjectUtil;

import com.tzdr.common.domain.BaseCrudEntity;
import com.tzdr.common.utils.Dates;

/**
 * 
 * @zhouchen
 * 2015年1月4日
 */
@Entity
@Table(name = "trade_calendar")
public class TradeDay extends BaseCrudEntity {

	private static final long serialVersionUID = 838028999390597368L;

	 /**
     * 是否交易日
     */
    private Boolean isTrade = Boolean.TRUE;
    
    /**
     * 日期值 20150102
     */
    private Long date;
    

    /**
     * 时间精确到秒值
     */
    private Long dateTime;
    @Transient
    private String dateValue;
    
	public Boolean getIsTrade() {
		return isTrade;
	}

	public void setIsTrade(Boolean isTrade) {
		this.isTrade = isTrade;
	}

	public Long getDate() {
		return date;
	}

	public void setDate(Long date) {
		this.date = date;
	}

	public TradeDay(Long date) {
		super();
		this.date = date;
	}
    
    public TradeDay() {
		
	}

	public String getDateValue() {
		if (ObjectUtil.equals(null, this.date)){
			return  null;
		}
		return Dates.format(Dates.parse(String.valueOf(this.date),Dates.CHINESE_DATE_FORMAT_LONG),Dates.CHINESE_DATE_FORMAT_LINE);
	}

	public void setDateValue(String dateValue) {
		this.dateValue = dateValue;
	}

	public Long getDateTime() {
		return dateTime;
	}

	public void setDateTime(Long dateTime) {
		this.dateTime = dateTime;
	}
	
	
}
package com.tzdr.domain.vo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Transient;

import com.tzdr.common.utils.SqlColumn;
import com.tzdr.common.utils.TypeConvert;
import com.tzdr.domain.cache.CacheManager;
import com.tzdr.domain.cache.DataDicKeyConstants;
/**
 * 
 * @author LiuYang
 *
 * 2015年6月11日 下午7:44:25
 */

public class BespokeTradeVo implements Serializable{
	@SqlColumn
	private String id;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setMinMultiple(Integer minMultiple) {
		this.minMultiple = minMultiple;
	}
	public void setMaxMultiple(Integer maxMultiple) {
		this.maxMultiple = maxMultiple;
	}
	public void setMinDuration(Integer minDuration) {
		this.minDuration = minDuration;
	}
	public void setMaxDuration(Integer maxDuration) {
		this.maxDuration = maxDuration;
	}
	public void setInterest(Double interest) {
		this.interest = interest;
	}
	public void setExpenese(Double expenese) {
		this.expenese = expenese;
	}
	public void setStartTime(Long startTime) {
		this.startTime = startTime;
	}
	
	private String minmoneystr;
	
	public String getMinmoneystr() {
		return minmoneystr;
	}
	public void setMinmoneystr(String minmoneystr) {
		this.minmoneystr = minmoneystr;
	}
	public String getMaxmoneystr() {
		return maxmoneystr;
	}
	public void setMaxmoneystr(String maxmoneystr) {
		this.maxmoneystr = maxmoneystr;
	}

	private String maxmoneystr;
	/**
	 * 方案名称
	 */
	@Column
	@SqlColumn
	private String name;
	/**
	 * 最低保证金
	 */
	@Column
	private double minBond;
	/**
	 * 最高保证金
	 */
	@Column
	@SqlColumn
	private double maxBond;
	
	/**
	 * 最低倍数
	 */
	@Column
	@SqlColumn
	private Integer minMultiple;
	/**
	 * 最高倍数
	 */
	@Column
	@SqlColumn
	private Integer maxMultiple;
	/**
	 * 倍数区间
	 */
	@Transient
	private String multipleValue;
	/**
	 * 最短配资时长
	 */
	@Column
	@SqlColumn
	private Integer minDuration;
	/**
	 * 最高配资时长
	 */
	@Column
	@SqlColumn
	private Integer maxDuration;
	/**
	 * 时长区间
	 */
	@Transient
	private String durationValue;
	/**
	 * 最短保留时长
	 */
	@Column
	@SqlColumn
	private int shortestDuration;
	
	/**
	 * 利息百分比
	 */
	@Column
	@SqlColumn
	private Double interest;
	
	@Transient
	private String interestValue;
	/**
	 * 管理费百分比
	 */
	@Column
	@SqlColumn
	private Double expenese;
	
	@Transient
	private String expeneseValue;
	/**
	 * 方案开始时间
	 */
	@Column
	@SqlColumn
	private Long startTime;
	@Transient
	private String startTimeValue;

	/**
	 * 方案状态 1：启用、-1：停用
	 */
	@Column
	@SqlColumn
	private int state;
	@Transient
	private String stateValue;
	/**
	 * 启用
	 */
	public final static int STATE_VALUE_STARTUP = 1;
	
	/**
	 * 停用
	 */
	public final static int STATE_VALUE_STOP = -1;
	
	/**
	 * 创建时间
	 */
	@Transient
	private String createTimeValue;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getMinBond() {
		return minBond;
	}
	public void setMinBond(double minBond) {
		this.minBond = minBond;
	}
	public double getMaxBond() {
		return maxBond;
	}
	public void setMaxBond(double maxBond) {
		this.maxBond = maxBond;
	}
	
	public int getMinMultiple() {
		return minMultiple;
	}
	public void setMinMultiple(int minMultiple) {
		this.minMultiple = minMultiple;
	}
	public int getMaxMultiple() {
		return maxMultiple;
	}
	public void setMaxMultiple(int maxMultiple) {
		this.maxMultiple = maxMultiple;
	}
	public int getMinDuration() {
		return minDuration;
	}
	public void setMinDuration(int minDuration) {
		this.minDuration = minDuration;
	}
	public int getMaxDuration() {
		return maxDuration;
	}
	public void setMaxDuration(int maxDuration) {
		this.maxDuration = maxDuration;
	}
	public double getInterest() {
		return interest;
	}
	public void setInterest(double interest) {
		this.interest = interest;
	}
	public double getExpenese() {
		return expenese;
	}
	public void setExpenese(double expenese) {
		this.expenese = expenese;
	}
	public long getStartTime() {
		return startTime;
	}
	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public int getShortestDuration() {
		return shortestDuration;
	}
	public void setShortestDuration(int shortestDuration) {
		this.shortestDuration = shortestDuration;
	}
	
	public String getMultipleValue() {
		
		if(this.minMultiple != null && this.maxMultiple != null){
			this.multipleValue =  this.minMultiple + "-" + this.maxMultiple + "倍";
		}
		return multipleValue;
	}
	public void setMultipleValue(String multipleValue) {
		this.multipleValue = multipleValue;
	}
	public String getDurationValue() {
		if(this.minDuration!=null &&this.maxDuration!=null){
			this.durationValue = this.minDuration + "-" + this.maxDuration +"天";
		}
		return durationValue;
	}
	public void setDurationValue(String durationValue) {
		this.durationValue = durationValue;
	}
	
	public String getStartTimeValue() {
		if(this.startTime != null){
			this.startTimeValue = TypeConvert.long1000ToDatetimeStr(this.startTime);
		}
		return startTimeValue;
	}
	public void setStartTimeValue(String startTimeValue) {
		this.startTimeValue = startTimeValue;
	}
	
	public void setCreateTimeValue(String createTimeValue) {
		this.createTimeValue = createTimeValue;
	}
	public String getStateValue() {
		return CacheManager.getDataMapByKey(DataDicKeyConstants.BESPOKETRADESTATE,String.valueOf(this.state));
	}
	public void setStateValue(String stateValue) {
		this.stateValue = stateValue;
	}
	public String getInterestValue() {
		if(interest!=null){
			interestValue = interest.toString()+"%";
		}
		return interestValue;
	}
	public void setInterestValue(String interestValue) {
		this.interestValue = interestValue;
	}
	public String getExpeneseValue() {
		if(expenese!=null){
			expeneseValue = expenese.toString()+"%";
		}
		return expeneseValue;
	}
	public void setExpeneseValue(String expeneseValue) {
		this.expeneseValue = expeneseValue;
	}
	
	
}

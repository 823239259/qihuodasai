package com.tzdr.domain.web.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.tzdr.common.domain.BaseCrudEntity;

/**
 * 
 * 
 * <p></p>
 * @author LiuQing
 * @see
 * @version 2.0
 * 2015年4月27日下午4:30:25
 */
@Entity
@Table(name = "w_volume_deductible")
public class VolumeDeductible extends BaseCrudEntity {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 卷名称
	 */
	@Column
	private String name;

	/**
	 * 类型
	 */
	@Column
	private String type;
	
	/**
	 * 类型编号
	 */
	@Column
	private String typeCode;
	
	/**
	 * 使用方式
	 */
	@Column
	private String useType;
	
	/**
	 * 金额
	 */
	@Column
	private double money;
	
	/**
	 * 发行张数
	 */
	@Column
	private int releaseNum;
	
	/**
	 * 使用张数
	 */
	@Column
	private int useNum;
	
	/**
	 * 启用日期
	 */
	@Column
	private Long startupDateValue;
	
	/**
	 * 发放结束日期
	 */
	@Column
	private Long dealEndDateValue;
	
	/**
	 * 截止日期
	 */
	@Column
	private Long endDateValue;
	
	/**
	 * 发放开始日期
	 */
	@Column
	private Long dealStartDateValue;
	
	/**
	 * 截止天数
	 */
	@Column
	private Integer endDayValue;
	
	/**
	 * 截至期限类型：0：截止日期、1：使用周期
	 */
	@Column
	private int deadlineType;
	
	/**
	 * 状态 1：启用、-1：停用
	 */
	@Column
	private int stateValue;
	
	/**
	 * 启用
	 */
	public final static int STATE_VALUE_STARTUP = 1;
	
	/**
	 * 停用
	 */
	public final static int STATE_VALUE_STOP = -1;
	
	
	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY,mappedBy="volumeDeductible")
	private List<VolumeDetail> volumeDetails;
	
	public void add(VolumeDetail detail) {
		if (this.volumeDetails == null) {
			this.volumeDetails = new ArrayList<VolumeDetail>();
		}
		detail.setVolumeDeductible(this);
		volumeDetails.add(detail);
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	public String getUseType() {
		return useType;
	}

	public void setUseType(String useType) {
		this.useType = useType;
	}

	public double getMoney() {
		return money;
	}

	public void setMoney(double money) {
		this.money = money;
	}

	public int getReleaseNum() {
		return releaseNum;
	}

	public void setReleaseNum(int releaseNum) {
		this.releaseNum = releaseNum;
	}

	public int getUseNum() {
		return useNum;
	}

	public void setUseNum(int useNum) {
		this.useNum = useNum;
	}

	public Long getStartupDateValue() {
		return startupDateValue;
	}



	public void setStartupDateValue(Long startupDateValue) {
		this.startupDateValue = startupDateValue;
	}



	public Long getEndDateValue() {
		return endDateValue;
	}



	public void setEndDateValue(Long endDateValue) {
		this.endDateValue = endDateValue;
	}



	public List<VolumeDetail> getVolumeDetails() {
		return volumeDetails;
	}



	public void setVolumeDetails(List<VolumeDetail> volumeDetails) {
		this.volumeDetails = volumeDetails;
	}



	public Integer getEndDayValue() {
		return endDayValue;
	}



	public void setEndDayValue(Integer endDayValue) {
		this.endDayValue = endDayValue;
	}

	public int getDeadlineType() {
		return deadlineType;
	}

	public void setDeadlineType(int deadlineType) {
		this.deadlineType = deadlineType;
	}

	public int getStateValue() {
		return stateValue;
	}

	public void setStateValue(int stateValue) {
		this.stateValue = stateValue;
	}

	public Long getDealStartDateValue() {
		return dealStartDateValue;
	}

	public void setDealStartDateValue(Long dealStartDateValue) {
		this.dealStartDateValue = dealStartDateValue;
	}

	public Long getDealEndDateValue() {
		return dealEndDateValue;
	}

	public void setDealEndDateValue(Long dealEndDateValue) {
		this.dealEndDateValue = dealEndDateValue;
	}

	
}
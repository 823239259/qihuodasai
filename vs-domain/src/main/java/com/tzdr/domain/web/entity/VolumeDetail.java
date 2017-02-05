package com.tzdr.domain.web.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "w_volume_detail")
public class VolumeDetail extends BaseCrudEntity {

	private static final long serialVersionUID = 1L;
	
	@ManyToOne(cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	@JoinColumn(name="v_id")
	private VolumeDeductible volumeDeductible;

	/**
	 * 编码
	 */
	@Column
	private String code;
	
	/**
	 * 使用时间
	 */
	@Column
	private Long useDateValue;
	
	/**
	 * 获取抵扣卷的时间
	 */
	@Column
	private Long timeValueOfGetVolume;
	
	/**
	 * 使用人
	 */
	@Column
	private String uid;
	
	/**
	 * 实付金额
	 */
	@Column
	private Double realPayAmount;
	
	/**
	 * 1:已使用、-1未使用
	 */
	@Column
	private Integer useState;
	
	/**
	 * 1:已使用
	 */
	public static final int USE_STATE_USED = 1;
	
	/**
	 * -1:未使用
	 */
	public static final int USE_STATE_USE = -1;
	
	/**
	 * 备注
	 */
	@Column
	private String remark;

	public VolumeDeductible getVolumeDeductible() {
		return volumeDeductible;
	}

	public void setVolumeDeductible(VolumeDeductible volumeDeductible) {
		this.volumeDeductible = volumeDeductible;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Double getRealPayAmount() {
		return realPayAmount;
	}

	public void setRealPayAmount(Double realPayAmount) {
		this.realPayAmount = realPayAmount;
	}

	public Integer getUseState() {
		return useState;
	}

	public void setUseState(Integer useState) {
		this.useState = useState;
	}

	public Long getUseDateValue() {
		return useDateValue;
	}

	public void setUseDateValue(Long useDateValue) {
		this.useDateValue = useDateValue;
	}

	public Long getTimeValueOfGetVolume() {
		return timeValueOfGetVolume;
	}

	public void setTimeValueOfGetVolume(Long timeValueOfGetVolume) {
		this.timeValueOfGetVolume = timeValueOfGetVolume;
	}
	
	
}
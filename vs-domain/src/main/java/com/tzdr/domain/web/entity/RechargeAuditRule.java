package com.tzdr.domain.web.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import jodd.util.ObjectUtil;

import com.tzdr.common.domain.BaseCrudEntity;
import com.tzdr.common.utils.Dates;

/**
 * 充值 审核规则   每个人审核金额 限制
 * @author zhouchen
 */
@Entity
@Table(name = "w_recharge_auditrule")
public class RechargeAuditRule extends BaseCrudEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 审核人id
	 */
	private String  auditorId;
	
	/**
	 * 审核人姓名
	 */
	private String  auditorName;
	
	
	/**
	 * 审核金额 begin
	 */
	private double beginMoney=0.0;
	
	/**
	 * 审核金额 end
	 */
	private double endMoney=0.0;

	/**
	 * 审核金额列表显示  如：beginMoney-endMoney
	 */
	@Transient
	private String viewMoney;
	
	/**
	 * 创建时间显示
	 */
	@Transient
	private String viewCreateTime;
	
	/**
	 * 更新时间显示
	 */
	@Transient
	private String viewUpdateTime;

	public double getBeginMoney() {
		return beginMoney;
	}

	public void setBeginMoney(double beginMoney) {
		this.beginMoney = beginMoney;
	}

	public double getEndMoney() {
		return endMoney;
	}

	public void setEndMoney(double endMoney) {
		this.endMoney = endMoney;
	}

	public String getViewMoney() {
		this.viewMoney=String.valueOf(beginMoney)+" - "+String.valueOf(endMoney);
		return viewMoney;
	}

	public void setViewMoney(String viewMoney) {
		this.viewMoney = viewMoney;
	}

	public String getViewCreateTime() {
		if (ObjectUtil.equals(null,super.getCreateTime())){
			return "";
		}
		return Dates.format(Dates.parseLong2Date(super.getCreateTime()),Dates.CHINESE_DATETIME_FORMAT_LINE);
	}

	public void setViewCreateTime(String viewCreateTime) {
		this.viewCreateTime = viewCreateTime;
	}

	public String getViewUpdateTime() {
		if (ObjectUtil.equals(null,super.getUpdateTime())){
			return "";
		}
		return Dates.format(Dates.parseLong2Date(super.getUpdateTime()),Dates.CHINESE_DATETIME_FORMAT_LINE);

	}

	public void setViewUpdateTime(String viewUpdateTime) {
		this.viewUpdateTime = viewUpdateTime;
	}

	public String getAuditorId() {
		return auditorId;
	}

	public void setAuditorId(String auditorId) {
		this.auditorId = auditorId;
	}

	public String getAuditorName() {
		return auditorName;
	}

	public void setAuditorName(String auditorName) {
		this.auditorName = auditorName;
	}
	
	
	
}
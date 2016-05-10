package com.tzdr.domain.web.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.tzdr.common.domain.BaseCrudEntity;

/**
 * 提现设置表
 * @author Administrator
 *
 */

@Entity
@Table(name="w_draw_money_data")
public class DrawMoneyData extends BaseCrudEntity{

	private Long firstAuditId;//初审人
	private Long reAuditId;//复审人
	private Long finalAuditId;
	private Long finalDate;//时间
	private Double minmoney; //审核金额最小值
	private Double maxmoney;//审核金额最大值
	private String type;//1自动打开,2人工审核3 线下打款
	public Long getFirstAuditId() {
		return firstAuditId;
	}
	
	
	public Long getFinalAuditId() {
		return finalAuditId;
	}


	public void setFinalAuditId(Long finalAuditId) {
		this.finalAuditId = finalAuditId;
	}


	public Long getFinalDate() {
		return finalDate;
	}


	public void setFinalDate(Long finalDate) {
		this.finalDate = finalDate;
	}


	public void setFirstAuditId(Long firstAuditId) {
		this.firstAuditId = firstAuditId;
	}
	public Long getReAuditId() {
		return reAuditId;
	}
	public void setReAuditId(Long reAuditId) {
		this.reAuditId = reAuditId;
	}
	
	public Double getMinmoney() {
		return minmoney;
	}
	public void setMinmoney(Double minmoney) {
		this.minmoney = minmoney;
	}
	public Double getMaxmoney() {
		return maxmoney;
	}
	public void setMaxmoney(Double maxmoney) {
		this.maxmoney = maxmoney;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
}

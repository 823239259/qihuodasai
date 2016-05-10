package com.tzdr.domain.web.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.tzdr.common.domain.BaseCrudEntity;
/**
 * 合买方案信息扩展表
 * @author LiuYang
 *
 * 2015年12月25日 上午10:41:33
 */
@Entity
@Table(name = "w_together_trade")
public class TogetherTrade extends BaseCrudEntity{

	private static final long serialVersionUID = 144067409945461370L;
	
	/**
	 * 合买方案ID
	 */
	private String tid;
	/**
	 * 合买方案groupID
	 */
	private String gid;
	
	/**
	 * 合买者分成比例
	 */
	private double profitRatio; 
	
	/**
	 * 合买者分成金额
	 */
	private double profitMoney;
	/**
	 * 方案分利类型  1.合买者少分利  2.和买者多分利  默认为1
	 */
	private int type = 1;
	@Column(name="tid")
	public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}
	
	@Column(name="gid")
	public String getGid() {
		return gid;
	}

	public void setGid(String gid) {
		this.gid = gid;
	}

	@Column(name="profit_ratio")
	public double getProfitRatio() {
		return profitRatio;
	}
	
	public void setProfitRatio(double profitRatio) {
		this.profitRatio = profitRatio;
	}
	@Column(name="profit_money")
	public double getProfitMoney() {
		return profitMoney;
	}

	public void setProfitMoney(double profitMoney) {
		this.profitMoney = profitMoney;
	}
	@Column(name="type")
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	} 
	
}

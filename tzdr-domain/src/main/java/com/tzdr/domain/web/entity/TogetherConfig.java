package com.tzdr.domain.web.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.tzdr.common.domain.BaseCrudEntity;
/**
 * 合买配资表 
 * @author LiuYang
 *
 * 2015年12月25日 上午10:41:03
 */
@Entity
@Table(name = "w_together_config")
public class TogetherConfig extends BaseCrudEntity{

	private static final long serialVersionUID = -4466691347209187254L;
	
	/**
	 * 合买利息系数
	 */
	private double foenusRatio;
	/**
	 * 管理费系数
	 */
	private double manageRatio;
	
	/**
	 * 操盘者最低出资
	 */
	private double minMoney;
	/**
	 * 操盘者最高出资
	 */
	private double maxMoney;
	/**
	 * 操盘者推荐出资
	 */
	private String recommendMoney;
	/**
	 * 合买者出资倍数
	 */
	private String moneyRatio;
	/**
	 * 推荐天数
	 */
	private String recommendDay;
	/**
	 * 合买者分成比例
	 */
	private double profitRatio;

	
	@Column(name="foenus_ratio")
	public double getFoenusRatio() {
		return foenusRatio;
	}
	public void setFoenusRatio(double foenusRatio) {
		this.foenusRatio = foenusRatio;
	}
	@Column(name="manage_ratio")
	public double getManageRatio() {
		return manageRatio;
	}
	public void setManageRatio(double manageRatio) {
		this.manageRatio = manageRatio;
	}
	@Column(name="min_money")
	public double getMinMoney() {
		return minMoney;
	}
	public void setMinMoney(double minMoney) {
		this.minMoney = minMoney;
	}
	@Column(name="max_money")
	public double getMaxMoney() {
		return maxMoney;
	}
	public void setMaxMoney(double maxMoney) {
		this.maxMoney = maxMoney;
	}
	@Column(name="recommend_money")
	public String getRecommendMoney() {
		return recommendMoney;
	}
	public void setRecommendMoney(String recommendMoney) {
		this.recommendMoney = recommendMoney;
	}
	@Column(name="money_ratio")
	public String getMoneyRatio() {
		return moneyRatio;
	}
	public void setMoneyRatio(String moneyRatio) {
		this.moneyRatio = moneyRatio;
	}
	@Column(name="recommend_day")
	public String getRecommendDay() {
		return recommendDay;
	}
	public void setRecommendDay(String recommendDay) {
		this.recommendDay = recommendDay;
	}
	@Column(name="profit_ratio")
	public double getProfitRatio() {
		return profitRatio;
	}
	public void setProfitRatio(double profitRatio) {
		this.profitRatio = profitRatio;
	}
	
}

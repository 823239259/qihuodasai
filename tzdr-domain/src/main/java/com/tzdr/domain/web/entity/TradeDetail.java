package com.tzdr.domain.web.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.tzdr.common.domain.BaseEntity;

@Entity
@Table(name = "w_trade_detail")
public class TradeDetail extends BaseEntity{

	private static final long serialVersionUID = 2953235263723340197L;
	/**
	 * 品种
	 */
	private String commodityNo;
	/**
	 * 合约
	 */
	private String contractNo;
	/**
	 * 方向
	 */
	private String drection;
	/**
	 * 成交价格
	 */
	private String tradePrice;
	/**
	 * 成交数量
	 */
	private String tradeNum;
	/**
	 * 订单价格
	 */
	private String orderPrice;
	/**
	 * 手续费
	 */
	private String free;
	/**
	 * 订单类型
	 */
	private String orderType;
	/**
	 * 订单日期
	 */
	private String marketDate;
	/**
	 * 下单人
	 */
	private String orderUser;
	/**
	 * 平盈
	 */
	private String flat;
	/**
	 * 方案ID
	 */
	private String fastId;
	/**
	 * 创建时间
	 */
	private Long createTime;
	/**
	 * 更新时间
	 */
	private Long updateTime;
	
	public String getFastId() {
		return fastId;
	}
	public void setFastId(String fastId) {
		this.fastId = fastId;
	}
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
	public String getCommodityNo() {
		return commodityNo;
	}
	public void setCommodityNo(String commodityNo) {
		this.commodityNo = commodityNo;
	}
	public String getContractNo() {
		return contractNo;
	}
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	public String getDrection() {
		return drection;
	}
	public void setDrection(String drection) {
		this.drection = drection;
	}
	public String getTradePrice() {
		return tradePrice;
	}
	public void setTradePrice(String tradePrice) {
		this.tradePrice = tradePrice;
	}
	public String getTradeNum() {
		return tradeNum;
	}
	public void setTradeNum(String tradeNum) {
		this.tradeNum = tradeNum;
	}
	public String getOrderPrice() {
		return orderPrice;
	}
	public void setOrderPrice(String orderPrice) {
		this.orderPrice = orderPrice;
	}
	public String getFree() {
		return free;
	}
	public void setFree(String free) {
		this.free = free;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public String getMarketDate() {
		return marketDate;
	}
	public void setMarketDate(String marketDate) {
		this.marketDate = marketDate;
	}
	public String getOrderUser() {
		return orderUser;
	}
	public void setOrderUser(String orderUser) {
		this.orderUser = orderUser;
	}
	public String getFlat() {
		return flat;
	}
	public void setFlat(String flat) {
		this.flat = flat;
	}
	
}

package com.tzdr.domain.web.entity;

import com.tzdr.common.domain.BaseCrudEntity;
import com.tzdr.domain.cache.CacheManager;
import com.tzdr.domain.cache.DataDicKeyConstants;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import java.math.BigDecimal;

/**
 * Created by Administrator on 2016/5/17.
 */
@Entity
@Table(name = "f_together_config")
public class TogetherFuture extends BaseCrudEntity {

    /**
     * 期货种类 1富士A50 2恒指期货 3国际原油 4股指期货
     */
    private Integer scope;
    /**
     * 操盘合约
     */
    private String tradingContract;

    /**
     * 单价
     */
    private BigDecimal unitPrice;
    /**
     * 满单份数
     */
    private Integer fullNum;
    /**
     * 止盈点
     */
    private BigDecimal stopProfitPoint;
    /**
     * 止亏点
     */
    private BigDecimal stopLossPoint;
    /**
     * 盈利手续费
     */
    private BigDecimal profitFee;
    /**
     * 盈利点数价格
     */
    private BigDecimal profitPointPrice;
    /**
     * 亏损手续费
     */
    private BigDecimal lossFee;
    /**
     * 亏损点数价格
     */
    private BigDecimal lossPointPrice;
    /**
     * 跳动点数
     */
    private BigDecimal beatPoint;
    
    /**
     * 对应类型名称
     */
    @Transient
    private String typeName;

    public Integer getScope() {
        return scope;
    }

    public void setScope(Integer scope) {
        this.scope = scope;
    }

    public String getTradingContract() {
        return tradingContract;
    }

    public void setTradingContract(String tradingContract) {
        this.tradingContract = tradingContract;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Integer getFullNum() {
        return fullNum;
    }

    public void setFullNum(Integer fullNum) {
        this.fullNum = fullNum;
    }

    public BigDecimal getStopProfitPoint() {
        return stopProfitPoint;
    }

    public void setStopProfitPoint(BigDecimal stopProfitPoint) {
        this.stopProfitPoint = stopProfitPoint;
    }

    public BigDecimal getStopLossPoint() {
        return stopLossPoint;
    }

    public void setStopLossPoint(BigDecimal stopLossPoint) {
        this.stopLossPoint = stopLossPoint;
    }

    public BigDecimal getProfitFee() {
        return profitFee;
    }

    public void setProfitFee(BigDecimal profitFee) {
        this.profitFee = profitFee;
    }

    public BigDecimal getProfitPointPrice() {
        return profitPointPrice;
    }

    public void setProfitPointPrice(BigDecimal profitPointPrice) {
        this.profitPointPrice = profitPointPrice;
    }

    public BigDecimal getLossFee() {
        return lossFee;
    }

    public void setLossFee(BigDecimal lossFee) {
        this.lossFee = lossFee;
    }

    public BigDecimal getLossPointPrice() {
        return lossPointPrice;
    }

    public void setLossPointPrice(BigDecimal lossPointPrice) {
        this.lossPointPrice = lossPointPrice;
    }

    public BigDecimal getBeatPoint() {
        return beatPoint;
    }

    public void setBeatPoint(BigDecimal beatPoint) {
        this.beatPoint = beatPoint;
    }

	public String getTypeName() {
		return CacheManager.getDataMapByKey(DataDicKeyConstants.FTOGETHER_TRADE_TYPE,String.valueOf(this.scope));
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
    
    
}

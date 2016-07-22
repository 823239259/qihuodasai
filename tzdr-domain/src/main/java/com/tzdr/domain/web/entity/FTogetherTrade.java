package com.tzdr.domain.web.entity;

import com.tzdr.common.domain.BaseCrudEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import java.math.BigDecimal;

/**
 * 期货合买方案表
 * Created by huangkai on 2016/5/20.
 */
@Entity
@Table(name = "f_together_trade")
public class  FTogetherTrade extends BaseCrudEntity {
    /**
     * 方案号
     */
    @Column(length = 10)
    private String tradeNo;

    /**
     * 方案名称
     */
    @Column(length = 100)
    private String name;

    /**
     *操盘合约
     */
    @Column(length = 50)
    private String contract;

    /**
     * 开仓时间
     */
    private Long openTime;

    /**
     * 操盘时间（分钟）
     */
    private Integer operateTime;
    /**
     * 看涨开仓点数
     */
    private BigDecimal callOpenPoint;

    /**
     * 看跌开仓点数
     */
    private BigDecimal putOpenPoint;

    /**
     * 看涨平仓点数
     */
    private BigDecimal callClosePoint;

    /**
     * 看跌平仓点数
     */
    private BigDecimal putClosePoint;

    /**
     * 合买价格
     */
    private BigDecimal price;

    /**
     * 满单份数
     */
    private Integer fullCopies;

    /**
     * 止盈点数
     */
    private BigDecimal targetProfitPoint;

    /**
     * 止损点数
     */
    private BigDecimal stopPoint;

    /**
     * 盈利扣取手续费
     */
    private BigDecimal profitFee;

    /**
     * 盈利份数价格
     */
    private BigDecimal profitCopiesPrice;

    /**
     * 亏损扣取手续费
     */
    private BigDecimal lossFee;

    /**
     * 亏损份数价格
     */
    private BigDecimal lossCopiesPrice;

    /**
     * 每次跳动点数
     */
    private BigDecimal floatMoint;

    /**
     * 合买品种（与参数配置中品种一致，冗余）
     */
    private Integer species;

    /**
     * 配置参数id（冗余，备用）
     */
    private String configId;

    /**
     * 合买状态（1：合买中，2：操盘中，3：已结算）
     */
    private Integer status;

    
    /**
     * 结算时间
     */
    private Long settlementTime;
    
    
    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContract() {
        return contract;
    }

    public void setContract(String contract) {
        this.contract = contract;
    }

    public Long getOpenTime() {
        return openTime;
    }

    public void setOpenTime(Long openTime) {
        this.openTime = openTime;
    }

    public BigDecimal getCallOpenPoint() {
        return callOpenPoint;
    }

    public void setCallOpenPoint(BigDecimal callOpenPoint) {
        this.callOpenPoint = callOpenPoint;
    }

    public BigDecimal getPutOpenPoint() {
        return putOpenPoint;
    }

    public void setPutOpenPoint(BigDecimal putOpenPoint) {
        this.putOpenPoint = putOpenPoint;
    }

    public BigDecimal getCallClosePoint() {
        return callClosePoint;
    }

    public void setCallClosePoint(BigDecimal callClosePoint) {
        this.callClosePoint = callClosePoint;
    }

    public BigDecimal getPutClosePoint() {
        return putClosePoint;
    }

    public void setPutClosePoint(BigDecimal putClosePoint) {
        this.putClosePoint = putClosePoint;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getFullCopies() {
        return fullCopies;
    }

    public void setFullCopies(Integer fullCopies) {
        this.fullCopies = fullCopies;
    }

    public BigDecimal getTargetProfitPoint() {
        return targetProfitPoint;
    }

    public void setTargetProfitPoint(BigDecimal targetProfitPoint) {
        this.targetProfitPoint = targetProfitPoint;
    }

    public BigDecimal getStopPoint() {
        return stopPoint;
    }

    public void setStopPoint(BigDecimal stopPoint) {
        this.stopPoint = stopPoint;
    }

    public BigDecimal getProfitFee() {
        return profitFee;
    }

    public void setProfitFee(BigDecimal profitFee) {
        this.profitFee = profitFee;
    }

    public BigDecimal getProfitCopiesPrice() {
        return profitCopiesPrice;
    }

    public void setProfitCopiesPrice(BigDecimal profitCopiesPrice) {
        this.profitCopiesPrice = profitCopiesPrice;
    }

    public BigDecimal getLossFee() {
        return lossFee;
    }

    public void setLossFee(BigDecimal lossFee) {
        this.lossFee = lossFee;
    }

    public BigDecimal getLossCopiesPrice() {
        return lossCopiesPrice;
    }

    public void setLossCopiesPrice(BigDecimal lossCopiesPrice) {
        this.lossCopiesPrice = lossCopiesPrice;
    }

    public BigDecimal getFloatMoint() {
        return floatMoint;
    }

    public void setFloatMoint(BigDecimal floatMoint) {
        this.floatMoint = floatMoint;
    }

    public Integer getSpecies() {
        return species;
    }

    public void setSpecies(Integer species) {
        this.species = species;
    }

    public String getConfigId() {
        return configId;
    }

    public void setConfigId(String configId) {
        this.configId = configId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(Integer operateTime) {
        this.operateTime = operateTime;
    }

	public Long getSettlementTime() {
		return settlementTime;
	}

	public void setSettlementTime(Long settlementTime) {
		this.settlementTime = settlementTime;
	}
    
    
}

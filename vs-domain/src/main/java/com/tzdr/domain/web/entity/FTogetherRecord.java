package com.tzdr.domain.web.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.tzdr.common.domain.BaseCrudEntity;

/**
 * 合买记录
 * Created by huangkai on 2016/5/20.
 */
@Entity
@Table(name = "f_together_record")
public class FTogetherRecord extends BaseCrudEntity  {

    /**
     * 用户id
     */
    @Column(length = 32)
    private String uid;

    /**
     * 方案id
     */
    @Column(length = 32)
    private String tradeId;
    /**
     * 购买份数
     */
    private Integer copies;
    
    /**
     * 退回份数
     */
    private Integer backCopies=0;

    /**
     * 实际支付金额
     */
    private BigDecimal payMoney;
    
    
    /**
     * 退回金额金额
     */
    private BigDecimal backMoney= new BigDecimal(0);
    /**
     * 手续费
     */
    private BigDecimal poundage;
    /**
     * 盈亏点数
     */
    private BigDecimal profitLossPoint;

    /**
     * 实现盈亏
     */
    private BigDecimal achieveProfitLoss;

    /**
     * 预计结算金额
     */
    private BigDecimal expectSettlementMoney;

    /**
     * 实际结算金额
     */
    private BigDecimal actualSettlementMoney;

    /**
     * 结算时间
     */
    private Long settlementTime;

    /**
     * 涨跌方向（1：涨 2：跌）
     */
    private Integer direction;
    
    /**
     * 期货合买活动类型：0：未参加活动 1：新用户首次操盘免90元活动
     */
    private Integer activityType=0;
    
    
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getTradeId() {
        return tradeId;
    }

    public void setTradeId(String tradeId) {
        this.tradeId = tradeId;
    }

    public Integer getCopies() {
        return copies;
    }

    public void setCopies(Integer copies) {
        this.copies = copies;
    }

    public BigDecimal getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(BigDecimal payMoney) {
        this.payMoney = payMoney;
    }


    public BigDecimal getPoundage() {
        return poundage;
    }

    public void setPoundage(BigDecimal poundage) {
        this.poundage = poundage;
    }

    public BigDecimal getProfitLossPoint() {
        return profitLossPoint;
    }

    public void setProfitLossPoint(BigDecimal profitLossPoint) {
        this.profitLossPoint = profitLossPoint;
    }

    public BigDecimal getAchieveProfitLoss() {
        return achieveProfitLoss;
    }

    public void setAchieveProfitLoss(BigDecimal achieveProfitLoss) {
        this.achieveProfitLoss = achieveProfitLoss;
    }

    public BigDecimal getExpectSettlementMoney() {
        return expectSettlementMoney;
    }

    public void setExpectSettlementMoney(BigDecimal expectSettlementMoney) {
        this.expectSettlementMoney = expectSettlementMoney;
    }

    public BigDecimal getActualSettlementMoney() {
        return actualSettlementMoney;
    }

    public void setActualSettlementMoney(BigDecimal actualSettlementMoney) {
        this.actualSettlementMoney = actualSettlementMoney;
    }

    public Long getSettlementTime() {
        return settlementTime;
    }

    public void setSettlementTime(Long settlementTime) {
        this.settlementTime = settlementTime;
    }


	public Integer getBackCopies() {
		return backCopies;
	}

	public void setBackCopies(Integer backCopies) {
		this.backCopies = backCopies;
	}

	public BigDecimal getBackMoney() {
		return backMoney;
	}

	public void setBackMoney(BigDecimal backMoney) {
		this.backMoney = backMoney;
	}

	public Integer getDirection() {
		return direction;
	}

	public void setDirection(Integer direction) {
		this.direction = direction;
	}
    
    public FTogetherRecord() {
		// TODO Auto-generated constructor stub
	}

	public FTogetherRecord(String uid, String tradeId, Integer direction) { 
		super();
		this.uid = uid;
		this.tradeId = tradeId;
		this.direction = direction;
	}

	public Integer getActivityType() {
		return activityType;
	}

	public void setActivityType(Integer activityType) {
		this.activityType = activityType;
	}
    
    
    
}

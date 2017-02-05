package com.tzdr.domain.web.entity.future;


// Generated 2015-7-23 14:20:39 by Hibernate Tools 4.3.1


import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.tzdr.common.domain.FbaseCrudEntity;

/**
 * 
 * 
 * <p></p>
 * @author LiuQing
 * @see
 * @version 2.0
 * 2015年8月1日下午6:10:50
 */
@Entity
@Table(name="f_user_trade")
public class FuserTrade extends FbaseCrudEntity  implements java.io.Serializable {


     //账户id
    @ManyToOne
    @JoinColumn(name="f_aid")
	private FaccountInfo  faccountInfo;
    
    @ManyToOne
    @JoinColumn(name="f_tid")
	private FtradeParentAccount ftradeParentAccount;
    //交易单号
     private String tradeNo;
     //交易数量(手)
     private Integer amount;
     //期货产品ID
     @ManyToOne
     @JoinColumn(name="f_pid")
     private FproductInfo fproductInfo;
     //触发止盈点
     private BigDecimal gainPoint;
     //触发止损点
     private BigDecimal lossPoint;
     //交易费(money)
     private BigDecimal transactionMoney;
     //管理费(money)
     private BigDecimal feeManage;
     //交易天数
     private Integer tranDay;
     //履约止损金（Money）
     private BigDecimal lossMoney;
     //保证金(money)
     private BigDecimal cautionMoney;
     //支付金额(money)
     private BigDecimal paymentMoney;
     //开仓类型  1:及时买入  2：指数触发
     private Integer buyType;
     //开仓指数触发方式设置的指数
     private Double buyPoint;
     //开仓类型  1：买入开仓  2：卖出开仓
     private Integer buyDirection;
     //开仓（平仓点数）
     private BigDecimal buyPrice;
     //开仓成交时间
     private Date buyTime;
     //触发委托单id（开仓）
     private String buyFeid;
     //平仓类型  1:即时卖出   2:指数触发 3：强制平仓
     private Integer sellType;
     //平仓指数触发方式设置的指数
     private Double sellPoint;
     //平仓(平仓点数)
     private BigDecimal sellPrice;
     //平仓成交时间
     private Date sellTime;
     //触发委托单id（平仓）
     private String sellFeid;
     //盈利分配金额
     private BigDecimal gainMoney;
     //交易盈亏金额
     private BigDecimal businessMoney;
     //结算金额
     private BigDecimal endMoney;
     //盈亏类型  1： 盈利       -1： 亏损
     private Integer profitState;
     //平仓类型  1:正常平仓   -1:强制平仓
     private Integer emptyType;
     //强制平仓人id
     private String emptyUid;
     //强制平仓时间
     private Date emptyTime;
     //状态 1: 操盘中 ；-1:已结算 ；-2：结算中；2：操盘申请(开仓委托)；3：申请异常；4：申请超时  5：已下单平仓中
     private Integer state;
     //操盘类型  1：实盘  -1虚拟盘
     private Integer actualType;
     //止盈点
     private BigDecimal profit;
     //止损点
     private BigDecimal loss;
     //交易所代码
     private String exchangeId;
     
     

	public String getExchangeId() {
		return exchangeId;
	}


	public void setExchangeId(String exchangeId) {
		this.exchangeId = exchangeId;
	}


	public BigDecimal getProfit() {
		return profit;
	}


	public void setProfit(BigDecimal profit) {
		this.profit = profit;
	}


	public BigDecimal getLoss() {
		return loss;
	}


	public void setLoss(BigDecimal loss) {
		this.loss = loss;
	}


	@Column(name="order_ref")
     private String orderRef;
	 /**
	  * 交易成功的交易系统单号
	  */
	 private String orderSysID;
    public String getOrderSysID() {
		return orderSysID;
	}


	public void setOrderSysID(String orderSysID) {
		this.orderSysID = orderSysID;
	}


	public FuserTrade() {
    }
    

    public FaccountInfo getFaccountInfo() {
		return faccountInfo;
	}

	public void setFaccountInfo(FaccountInfo faccountInfo) {
		this.faccountInfo = faccountInfo;
	}
    
    @Column(name="amount")
    public Integer getAmount() {
        return this.amount;
    }
    
    public void setAmount(Integer amount) {
        this.amount = amount;
    }


    public FproductInfo getFproductInfo() {
		return fproductInfo;
	}


	public void setFproductInfo(FproductInfo fproductInfo) {
		this.fproductInfo = fproductInfo;
	}


	@Column(name="gain_point")
    public BigDecimal getGainPoint() {
        return this.gainPoint;
    }
    
    public void setGainPoint(BigDecimal gainPoint) {
        this.gainPoint = gainPoint;
    }

    
    @Column(name="loss_point")
    public BigDecimal getLossPoint() {
        return this.lossPoint;
    }
    
    public void setLossPoint(BigDecimal lossPoint) {
        this.lossPoint = lossPoint;
    }

    @Column(name="loss_money")
    public BigDecimal getLossMoney() {
        return this.lossMoney;
    }
    
    public void setLossMoney(BigDecimal lossMoney) {
        this.lossMoney = lossMoney;
    }

    
    @Column(name="caution_money")
    public BigDecimal getCautionMoney() {
        return this.cautionMoney;
    }
    
    public void setCautionMoney(BigDecimal cautionMoney) {
        this.cautionMoney = cautionMoney;
    }

    
    @Column(name="buy_type")
    public Integer getBuyType() {
        return this.buyType;
    }
    
    public void setBuyType(Integer buyType) {
        this.buyType = buyType;
    }

    
    @Column(name="buy_point", precision=22, scale=0)
    public Double getBuyPoint() {
        return this.buyPoint;
    }
    
    public void setBuyPoint(Double buyPoint) {
        this.buyPoint = buyPoint;
    }

    
    @Column(name="buy_direction")
    public Integer getBuyDirection() {
        return this.buyDirection;
    }
    
    public void setBuyDirection(Integer buyDirection) {
        this.buyDirection = buyDirection;
    }

    
    @Column(name="buy_price")
    public BigDecimal getBuyPrice() {
        return this.buyPrice;
    }
    
    public void setBuyPrice(BigDecimal buyPrice) {
        this.buyPrice = buyPrice;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="buy_time", length=19)
    public Date getBuyTime() {
        return this.buyTime;
    }
    
    public void setBuyTime(Date buyTime) {
        this.buyTime = buyTime;
    }

    
    @Column(name="buy_feid", length=32)
    public String getBuyFeid() {
        return this.buyFeid;
    }
    
    public void setBuyFeid(String buyFeid) {
        this.buyFeid = buyFeid;
    }

    
    @Column(name="sell_type")
    public Integer getSellType() {
        return this.sellType;
    }
    
    public void setSellType(Integer sellType) {
        this.sellType = sellType;
    }

    
    @Column(name="sell_point", precision=22, scale=0)
    public Double getSellPoint() {
        return this.sellPoint;
    }
    
    public void setSellPoint(Double sellPoint) {
        this.sellPoint = sellPoint;
    }

    
    @Column(name="sell_price")
    public BigDecimal getSellPrice() {
        return this.sellPrice;
    }
    
    public void setSellPrice(BigDecimal sellPrice) {
        this.sellPrice = sellPrice;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="sell_time", length=19)
    public Date getSellTime() {
        return this.sellTime;
    }
    
    public void setSellTime(Date sellTime) {
        this.sellTime = sellTime;
    }

    
    @Column(name="sell_feid", length=32)
    public String getSellFeid() {
        return this.sellFeid;
    }
    
    public void setSellFeid(String sellFeid) {
        this.sellFeid = sellFeid;
    }

    
    @Column(name="gain_money")
    public BigDecimal getGainMoney() {
        return this.gainMoney;
    }
    
    public void setGainMoney(BigDecimal gainMoney) {
        this.gainMoney = gainMoney;
    }

    
    @Column(name="business_money")
    public BigDecimal getBusinessMoney() {
        return this.businessMoney;
    }
    
    public void setBusinessMoney(BigDecimal businessMoney) {
        this.businessMoney = businessMoney;
    }

    
    @Column(name="end_money")
    public BigDecimal getEndMoney() {
        return this.endMoney;
    }
    
    public void setEndMoney(BigDecimal endMoney) {
        this.endMoney = endMoney;
    }

    
    @Column(name="profit_state")
    public Integer getProfitState() {
        return this.profitState;
    }
    
    public void setProfitState(Integer profitState) {
        this.profitState = profitState;
    }

    
    @Column(name="empty_type")
    public Integer getEmptyType() {
        return this.emptyType;
    }
    
    public void setEmptyType(Integer emptyType) {
        this.emptyType = emptyType;
    }

    
    @Column(name="empty_uid", length=32)
    public String getEmptyUid() {
        return this.emptyUid;
    }
    
    public void setEmptyUid(String emptyUid) {
        this.emptyUid = emptyUid;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="empty_time", length=19)
    public Date getEmptyTime() {
        return this.emptyTime;
    }
    
    public void setEmptyTime(Date emptyTime) {
        this.emptyTime = emptyTime;
    }

    
    @Column(name="state")
    public Integer getState() {
        return this.state;
    }
    
    public void setState(Integer state) {
        this.state = state;
    }

    
    @Column(name="actual_type")
    public Integer getActualType() {
        return this.actualType;
    }
    
    public void setActualType(Integer actualType) {
        this.actualType = actualType;
    }


	public FtradeParentAccount getFtradeParentAccount() {
		return ftradeParentAccount;
	}


	public void setFtradeParentAccount(FtradeParentAccount ftradeParentAccount) {
		this.ftradeParentAccount = ftradeParentAccount;
	}

	@Column(name="trade_no", length=32)
	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	@Column(name="transaction_money")
	public BigDecimal getTransactionMoney() {
		return transactionMoney;
	}

	public void setTransactionMoney(BigDecimal transactionMoney) {
		this.transactionMoney = transactionMoney;
	}

	@Column(name="payment_money")
	public BigDecimal getPaymentMoney() {
		return paymentMoney;
	}

	public void setPaymentMoney(BigDecimal paymentMoney) {
		this.paymentMoney = paymentMoney;
	}
	
	@Column(name="fee_manage")
	public BigDecimal getFeeManage() {
		return feeManage;
	}

	public void setFeeManage(BigDecimal feeManage) {
		this.feeManage = feeManage;
	}

	@Column(name="tran_day")
	public Integer getTranDay() {
		return tranDay;
	}

	public void setTranDay(Integer tranDay) {
		this.tranDay = tranDay;
	}


	public String getOrderRef() {
		return orderRef;
	}


	public void setOrderRef(String orderRef) {
		this.orderRef = orderRef;
	}
}



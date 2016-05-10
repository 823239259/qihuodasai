package com.tzdr.domain.web.entity.future;



import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.tzdr.common.domain.FbaseCrudEntity;
import com.tzdr.common.utils.Dates;

/**
 * 
 * 
 * <p></p>
 * @author LiuQing
 * @see
 * @version 2.0
 * 2015年7月24日下午3:37:44
 */
@Entity
@Table(name="f_product_info")
public class FproductInfo extends FbaseCrudEntity  implements java.io.Serializable {


	private static final long serialVersionUID = -5688588134406006876L;
	//交易品种【1.IF、2.IH、3.IC】
     private String type;
     //交易合约（IF0000）
     private String tradeDateNo;
     //单次开仓限制手数
     private Integer restrictNum;
     //保证金
     private BigDecimal cautionMoney;
     //交易费
     private BigDecimal transaction;
     //管理费
     private BigDecimal feeManage;
     //分润比例
     private Double proportion;
     @Transient
     private String proportionValue;
     //止盈值
     private String gainPrice;
     //止损值【多个】
     private String lossPrices;
     //状态【1.启用、0.禁用】
     private Integer useState;
     @Transient
     private String useStateValue;
     //限制【1.正常、0.限制今日所有交易】
     private Integer restrictState;
     @Transient
     private String restrictStateValue;
     //更改限制时间
     private Date restrictTime;
     @Transient
     private String  restrictTimeValue;
     
     
     //更改限制人id
     private Long restrictUid;
     
     //创建时间
     @Transient
     private String  createTimeValue;
     
     //更新时间
     @Transient
     private String  updateTimeValue;

    public FproductInfo() {
    }
   
    @Column(name="type", length=2)
    public String getType() {
        return this.type;
    }
    
    public void setType(String type) {
        this.type = type;
    }

    
    @Column(name="trade_date_no", length=50)
    public String getTradeDateNo() {
        return this.tradeDateNo;
    }
    
    public void setTradeDateNo(String tradeDateNo) {
        this.tradeDateNo = tradeDateNo;
    }
    
    @Column(name="caution_money")
    public BigDecimal getCautionMoney() {
        return this.cautionMoney;
    }

    @Column
    public Integer getRestrictNum() {
		return restrictNum;
	}

	public void setRestrictNum(Integer restrictNum) {
		this.restrictNum = restrictNum;
	}

	public void setCautionMoney(BigDecimal cautionMoney) {
        this.cautionMoney = cautionMoney;
    }

    
    @Column(name="transaction")
    public BigDecimal getTransaction() {
        return this.transaction;
    }
    
    public void setTransaction(BigDecimal transaction) {
        this.transaction = transaction;
    }

    
    @Column(name="fee_manage")
    public BigDecimal getFeeManage() {
        return this.feeManage;
    }
    
    public void setFeeManage(BigDecimal feeManage) {
        this.feeManage = feeManage;
    }

    
    @Column(name="proportion", precision=22, scale=0)
    public Double getProportion() {
        return this.proportion;
    }
    
    public void setProportion(Double proportion) {
        this.proportion = proportion;
    }

    
    @Column(name="gain_price", length=350)
    public String getGainPrice() {
        return this.gainPrice;
    }
    
    public void setGainPrice(String gainPrice) {
        this.gainPrice = gainPrice;
    }

    
    @Column(name="loss_prices", length=350)
    public String getLossPrices() {
        return this.lossPrices;
    }
    
    public void setLossPrices(String lossPrices) {
        this.lossPrices = lossPrices;
    }

    
    @Column(name="use_state")
    public Integer getUseState() {
        return this.useState;
    }
    
    public void setUseState(Integer useState) {
        this.useState = useState;
    }

    
    @Column(name="restrict_state")
    public Integer getRestrictState() {
        return this.restrictState;
    }
    
    public void setRestrictState(Integer restrictState) {
        this.restrictState = restrictState;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="restrict_time")
    public Date getRestrictTime() {
        return this.restrictTime;
    }
    
    public void setRestrictTime(Date restrictTime) {
        this.restrictTime = restrictTime;
    }

    
    @Column(name="restrict_uid")
    public Long getRestrictUid() {
        return this.restrictUid;
    }
    
    public void setRestrictUid(Long restrictUid) {
        this.restrictUid = restrictUid;
    }

	public String getRestrictTimeValue() {
		if(restrictTime!=null){
			this.restrictTimeValue = Dates.format(restrictTime);
		}
		return restrictTimeValue;
	}

	public void setRestrictTimeValue(String restrictTimeValue) {
		this.restrictTimeValue = restrictTimeValue;
	}

	public String getCreateTimeValue() {
		if(this.getCreateTime()!=null){
			this.createTimeValue = Dates.format(this.getCreateTime());
		}
		return createTimeValue;
	}

	public void setCreateTimeValue(String createTimeValue) {
		this.createTimeValue = createTimeValue;
	}

	public String getUpdateTimeValue() {
		if(this.getUpdateTime()!=null){
			
			this.updateTimeValue = Dates.format(this.getUpdateTime());
		}
		return updateTimeValue;
	}

	public void setUpdateTimeValue(String updateTimeValue) {
		this.updateTimeValue = updateTimeValue;
	}

	public String getProportionValue() {
		if(this.proportion!=null){
			proportionValue = String.valueOf(proportion) + "%";
		}
		return proportionValue;
	}

	public void setProportionValue(String proportionValue) {
		this.proportionValue = proportionValue;
	}

	public String getUseStateValue() {
		if(this.useState!=null){
			if(this.useState == 1){
				this.useStateValue="启用";
			}else if(this.useState == 0){
				this.useStateValue="停用";
			}else{
				this.useStateValue="状态异常";
			}
		}
		return useStateValue;
	}

	public void setUseStateValue(String useStateValue) {
		this.useStateValue = useStateValue;
	}

	public String getRestrictStateValue() {
		if(this.restrictState!=null){
			
			if(this.restrictState == 1){
				this.restrictStateValue="正常";
			}else if(this.restrictState == 0){
				this.restrictStateValue="限制";
			}else{
				this.restrictStateValue="状态异常";
			}
		}
		return restrictStateValue;
	}

	public void setRestrictStateValue(String restrictStateValue) {
		this.restrictStateValue = restrictStateValue;
	}

    


}



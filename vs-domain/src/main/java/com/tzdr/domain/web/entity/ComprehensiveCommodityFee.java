package com.tzdr.domain.web.entity;

import com.tzdr.common.domain.BaseCrudEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * 商品综合 申请方案 32品种手续费对照表
 * @description
 * @Author huangkai
 * @Date 2016-03-28
 */
@Entity
@Table(name = "w_comprehensive_commodity_fee")
public class ComprehensiveCommodityFee extends BaseCrudEntity {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * 方案id
     */
    @Column(nullable = false)
    private String tradeId;
    /**
     * 期货种类
     */
    private String type;
    /**
     * 该种类应付手续费
     */
    private BigDecimal poundage;

    
    
    
    public String getTradeId() {
        return tradeId;
    }

    public void setTradeId(String tradeId) {
        this.tradeId = tradeId;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigDecimal getPoundage() {
        return poundage;
    }

    public void setPoundage(BigDecimal poundage) {
        this.poundage = poundage;
    }
}

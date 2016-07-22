package com.tzdr.domain.web.entity;

import com.tzdr.common.domain.BaseCrudEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * 行情点位
 * Created by huangkai on 2016/5/20.
 */
@Entity
@Table(name = "f_together_conjuncture")
public class FTogetherConjuncture extends BaseCrudEntity {

    /**
     * 方案id
     */
    @Column(length = 32)
    private String tradeId;

    /**
     * 分钟数
     */
    private Integer minutes;

    /**
     * 行情点位
     */
    private BigDecimal point;

    public String getTradeId() {
        return tradeId;
    }

    public void setTradeId(String tradeId) {
        this.tradeId = tradeId;
    }

    public Integer getMinutes() {
        return minutes;
    }

    public void setMinutes(Integer minutes) {
        this.minutes = minutes;
    }

    public BigDecimal getPoint() {
        return point;
    }

    public void setPoint(BigDecimal point) {
        this.point = point;
    }
}

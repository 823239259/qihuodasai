package com.tzdr.domain.web.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.tzdr.common.domain.BaseCrudEntity;

/**
 * Created by huangkai on 2016/5/20.
 */
@Entity
@Table(name = "f_together_record_detail")
public class FTogetherRecordDetail extends BaseCrudEntity{

    /**
     * 购买时间
     */
    private Long buyTime=new Date().getTime();

    /**
     * 合买记录Id
     */
    @Column(length = 32)
    private String recordId;

    /**
     * 涨跌方向（1：涨 2：跌）
     */
    private Integer direction;

    /**
     * 用户id
     */
    @Column(length = 32)
    private String uid;

    /**
     * 合买方案ID
     */
    @Column(length = 32)
    private String tradeId;

    /**
     * 是否退回 0正常 1已退
     */
    private Integer isBack=0;

    public Long getBuyTime() {
        return buyTime;
    }

    public void setBuyTime(Long buyTime) {
        this.buyTime = buyTime;
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public Integer getDirection() {
        return direction;
    }

    public void setDirection(Integer direction) {
        this.direction = direction;
    }

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

    public Integer getIsBack() {
        return isBack;
    }

    public void setIsBack(Integer isBack) {
        this.isBack = isBack;
    }
    
    public FTogetherRecordDetail() {
		// TODO Auto-generated constructor stub
	}

	public FTogetherRecordDetail(FTogetherRecord fTogetherRecord) {
		super();
		this.recordId = fTogetherRecord.getId();
		this.direction = fTogetherRecord.getDirection();
		this.uid = fTogetherRecord.getUid();
		this.tradeId = fTogetherRecord.getTradeId();
	}
    
    
}

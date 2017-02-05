package com.tzdr.domain.app.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

import jodd.util.ObjectUtil;

import com.tzdr.common.utils.Dates;
import com.tzdr.domain.constants.Constant;

public class UserFTogetherTradeVo implements Serializable {

	/**
	 * id
	 */
    private String id;

    /**
     * 方案名称
     */
    private String name;

    /**
     * 开仓时间
     */
    private BigInteger openTime;


    /**
     * 合买状态（1：合买中，2：操盘中，3：已结算）
     */
    private Integer status;
    
    /**
     * 购买份数
     */
    private Integer copies;
    
    
    /**
     * 涨跌方向（1：涨 2：跌）
     */
    private Integer direction;
    
    /**
     *操盘合约
     */
    private String contract;
    
    
    /**
     * 退回份数
     */
    private Integer backCopies;
    
    /**
     * 实现盈亏
     */
    private BigDecimal achieveProfitLoss;
    
    /**
     * 当前方案同方向总共操盘份数
     */
    private BigInteger sameDireCopies;
    
    /**
     * 开仓时间字符串格式显示
     */
    private String openTimeStr;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigInteger getOpenTime() {
		return openTime;
	}

	public void setOpenTime(BigInteger openTime) {
		this.openTime = openTime;
	}
	public Integer getStatus() {
		if (this.status==Constant.FtogetherGame.JOINT_PURCHASE_STATUS 
				&& !ObjectUtil.equals(this.openTime,null)
				&& Dates.getCurrentLongDate()+Constant.FtogetherGame.FIVE_MINITE_SEC>this.openTime.longValue()){
			return Constant.FtogetherGame.OPERATE_STATUS;
		}
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}


	public String getOpenTimeStr() {
		if (ObjectUtil.equals(this.openTime,null)){
			return openTimeStr;
		}
		return Dates.getContainsWeekStr(openTime.longValue(),"MM.dd周 HH:mm");
	}

	public void setOpenTimeStr(String openTimeStr) {
		this.openTimeStr = openTimeStr;
	}

	public String getContract() {
		return contract;
	}

	public void setContract(String contract) {
		this.contract = contract;
	}

	public Integer getCopies() {
		return copies;
	}

	public void setCopies(Integer copies) {
		this.copies = copies;
	}

	public Integer getDirection() {
		return direction;
	}

	public void setDirection(Integer direction) {
		this.direction = direction;
	}

	public Integer getBackCopies() {
		return backCopies;
	}

	public void setBackCopies(Integer backCopies) {
		this.backCopies = backCopies;
	}

	public BigDecimal getAchieveProfitLoss() {
		return achieveProfitLoss;
	}

	public void setAchieveProfitLoss(BigDecimal achieveProfitLoss) {
		this.achieveProfitLoss = achieveProfitLoss;
	}

	public BigInteger getSameDireCopies() {
		return sameDireCopies;
	}

	public void setSameDireCopies(BigInteger sameDireCopies) {
		this.sameDireCopies = sameDireCopies;
	}

	
    
}

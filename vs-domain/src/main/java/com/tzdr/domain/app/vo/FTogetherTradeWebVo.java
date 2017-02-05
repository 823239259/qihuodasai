package com.tzdr.domain.app.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

import jodd.util.ObjectUtil;

import com.tzdr.common.utils.Dates;
import com.tzdr.domain.cache.CacheManager;
import com.tzdr.domain.cache.DataDicKeyConstants;
import com.tzdr.domain.constants.Constant;

public class FTogetherTradeWebVo implements Serializable {

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
     * 满单份数
     */
    private Integer fullCopies;

    /**
     * 合买状态（1：合买中，2：操盘中，3：已结算）
     */
    private Integer status;
    
    /**
     * 看涨份数
     */
    private BigInteger riseCopies;
    
    /**
     * 看跌份数
     */
    private BigInteger dropCopies;
    
    /**
     * 排除退回的看涨份数
     */
    private BigInteger realRiseCopies;
    
    /**
     * 排除退回的看跌份数
     */
    private BigInteger realDropCopies;
    
    
    /**
     * 操盘时间（分钟）
     */
    private Integer operateTime;
    
    
    /**
     * 合买价格
     */
    private BigDecimal price;
    
    
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
     *操盘合约
     */
    private String contract;
    
    /**
     * 开仓时间字符串格式显示
     */
    private String openTimeStr;
    
    /**
     * 操盘时间段显示
     * 例：4.8周四   12:00—12:20
     */
    private String operateTimeStr;

    /**
     * 校验用户是否拥有方案
     */
    private boolean  isOwn=false;
    
    /**
     * 用户拥有的方案对应的合买记录id
     */
    private String recordId;
    
    /**
     * 合买品种（与参数配置中品种一致，冗余）
     */
    private Integer species;
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

	public Integer getFullCopies() {
		return fullCopies;
	}

	public void setFullCopies(Integer fullCopies) {
		this.fullCopies = fullCopies;
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

	public BigInteger getRiseCopies() {
		return riseCopies;
	}

	public void setRiseCopies(BigInteger riseCopies) {
		this.riseCopies = riseCopies;
	}

	public BigInteger getDropCopies() {
		return dropCopies;
	}

	public void setDropCopies(BigInteger dropCopies) {
		this.dropCopies = dropCopies;
	}

	public BigInteger getRealRiseCopies() {
		return realRiseCopies;
	}

	public void setRealRiseCopies(BigInteger realRiseCopies) {
		this.realRiseCopies = realRiseCopies;
	}

	public BigInteger getRealDropCopies() {
		return realDropCopies;
	}

	public void setRealDropCopies(BigInteger realDropCopies) {
		this.realDropCopies = realDropCopies;
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

	public Integer getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(Integer operateTime) {
		this.operateTime = operateTime;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
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

	public String getContract() {
		return contract;
	}

	public void setContract(String contract) {
		this.contract = contract;
	}

	public String getOperateTimeStr() {
		if (ObjectUtil.equals(this.openTime,null) || ObjectUtil.equals(null, this.operateTime)){
			return operateTimeStr;
		}
		return Dates.getContainsWeekStr(openTime.longValue(),"MM.dd周 HH:mm")+"-"+
		Dates.format(Dates.parseLong2Date(openTime.longValue()+this.operateTime*60),"HH:mm");
	}

	public void setOperateTimeStr(String operateTimeStr) {
		this.operateTimeStr = operateTimeStr;
	}

	public boolean isOwn() {
		return isOwn;
	}

	public void setOwn(boolean isOwn) {
		this.isOwn = isOwn;
	}

	public String getRecordId() {
		return recordId;
	}

	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}
    
	public String getSpecies() {
		return CacheManager.getDataMapByKey(DataDicKeyConstants.FTOGETHER_TRADE_TYPE,String.valueOf(this.species));
	}

	public void setSpecies(Integer species) {
		this.species = species;
	}
    
}

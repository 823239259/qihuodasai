package com.tzdr.domain.vo;

import java.math.BigDecimal;

import jodd.util.ObjectUtil;

import org.apache.commons.lang.math.NumberUtils;

import com.tzdr.common.utils.Dates;
import com.tzdr.domain.cache.CacheManager;
import com.tzdr.domain.cache.DataDicKeyConstants;
import com.tzdr.domain.constants.Constant;

/**
 * Created by huangkai on 2016/5/23.
 */
public class FTogetherTradeVo {

    private String id;
    /**
     * 方案号
     */
    private String tradeNo;

    /**
     * 参数id
     */
    private String configId;
    /**
     * 满单份数
     */
    private Integer fullCopies;
    /**
     * 方案名称
     */
    private String name;

    /**
     *操盘合约
     */
    private String contract;

    /**
     * 开仓时间
     */
    private String openTime;

    
    
    private String openTimeValue;
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
     * 合买状态（1：合买中，2：操盘中，3：已结算）
     */
    private Integer status;
    /**
     * 看涨单数
     */
    private String callNo;
    /**
     * 看跌单数
     */
    private String putNo;

    /**
     * 看涨满单量
     */
    private Integer callFullNo;

    /**
     * 看跌满单量
     */
    private Integer putFullNo;

    /**
     * 发布时间
     */
    private String addTime;
    /**
     * 期货种类
     */
    private Integer species;

    
    /**
     * 合买品种类型名称
     */
    private String typeName;
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

    public String getOpenTime() {
        return openTime;
    }

    public void setOpenTime(String openTime) {
        this.openTime = openTime;
    }

    public Integer getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(Integer operateTime) {
        this.operateTime = operateTime;
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

    public Integer getStatus() {
    	if (this.status==Constant.FtogetherGame.JOINT_PURCHASE_STATUS 
				&& !ObjectUtil.equals(this.openTime,null)
				&& Dates.getCurrentLongDate()+Constant.FtogetherGame.FIVE_MINITE_SEC 
				> Double.valueOf(this.openTime)){
			return Constant.FtogetherGame.OPERATE_STATUS;
		}
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCallNo() {
        return callNo;
    }

    public void setCallNo(String callNo) {
        this.callNo = callNo;
    }

    public String getPutNo() {
        return putNo;
    }

    public void setPutNo(String putNo) {
        this.putNo = putNo;
    }

    public Integer getCallFullNo() {
        if(null != callNo){
            int cNo =Integer.parseInt(callNo);
            if(cNo>=fullCopies) {
                callFullNo = cNo / fullCopies;
            }else {
                callFullNo = 0;
            }
        }
        return callFullNo;
    }

    public void setCallFullNo(Integer callFullNo) {
        this.callFullNo = callFullNo;
    }

    public Integer getPutFullNo() {
        if(null != putNo){
            int pNo =Integer.parseInt(putNo);
            if(pNo >=fullCopies){
                putFullNo=pNo/fullCopies;
            }else {
                putFullNo = 0;
            }
        }
        return putFullNo;
    }

    public void setPutFullNo(Integer putFullNo) {


        this.putFullNo = putFullNo;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getFullCopies() {
        return fullCopies;
    }

    public void setFullCopies(Integer fullCopies) {
        this.fullCopies = fullCopies;
    }

    public String getConfigId() {
        return configId;
    }

    public void setConfigId(String configId) {
        this.configId = configId;
    }



	public String getOpenTimeValue() {
		 if (null != this.openTime){
	          openTimeValue =  Dates.format(Dates.parseLong2Date(NumberUtils.toLong(this.openTime)),"YYYY-MM-dd HH:mm:ss");	
	        }
		return openTimeValue;
	}

	public void setOpenTimeValue(String openTimeValue) {
		this.openTimeValue = openTimeValue;
	}

    public Integer getSpecies() {
        return species;
    }

    public void setSpecies(Integer species) {
        this.species = species;
    }

	public String getTypeName() {
		return CacheManager.getDataMapByKey(DataDicKeyConstants.FTOGETHER_TRADE_TYPE,String.valueOf(this.species));
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
    
    
}

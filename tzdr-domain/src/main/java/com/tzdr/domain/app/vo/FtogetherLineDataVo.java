package com.tzdr.domain.app.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;


/**
 * 期货合买行情数据vo
 * @author zhouchen
 * 2016年6月3日 上午9:43:42
 */
public class FtogetherLineDataVo implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<Object> riseData;
	
	private List<Object> dropData;
	
	private BigDecimal  maxPoint;
	
	private BigDecimal  minPoint;
	
	private BigDecimal  riseFloatPoint;
	
	private BigDecimal  dropFloatPoint;
	
	private List<Object>  xAxisData;

	 /**
     * 看涨开仓点数
     */
    private BigDecimal callOpenPoint;

    /**
     * 看跌开仓点数
     */
    private BigDecimal putOpenPoint;
    
    /**
     * 止损点位
     */
    private BigDecimal  stopPoint;
    
    
	public List<Object> getRiseData() {
		return riseData;
	}

	public void setRiseData(List<Object> riseData) {
		this.riseData = riseData;
	}

	public List<Object> getDropData() {
		return dropData;
	}

	public void setDropData(List<Object> dropData) {
		this.dropData = dropData;
	}

	public BigDecimal getMaxPoint() {
		return maxPoint;
	}

	public void setMaxPoint(BigDecimal maxPoint) {
		this.maxPoint = maxPoint;
	}

	public BigDecimal getMinPoint() {
		return minPoint;
	}

	public void setMinPoint(BigDecimal minPoint) {
		this.minPoint = minPoint;
	}

	public List<Object> getxAxisData() {
		return xAxisData;
	}

	public void setxAxisData(List<Object> xAxisData) {
		this.xAxisData = xAxisData;
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

	public BigDecimal getStopPoint() {
		return stopPoint;
	}

	public void setStopPoint(BigDecimal stopPoint) {
		this.stopPoint = stopPoint;
	}

	public BigDecimal getRiseFloatPoint() {
		return riseFloatPoint;
	}

	public void setRiseFloatPoint(BigDecimal riseFloatPoint) {
		this.riseFloatPoint = riseFloatPoint;
	}

	public BigDecimal getDropFloatPoint() {
		return dropFloatPoint;
	}

	public void setDropFloatPoint(BigDecimal dropFloatPoint) {
		this.dropFloatPoint = dropFloatPoint;
	}

	
	
	
}

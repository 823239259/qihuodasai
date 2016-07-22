package com.tzdr.domain.vo;

import com.tzdr.domain.web.entity.FTogetherConjuncture;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class TogetherConjunctureVo {

	 private String tradeId;
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
    
    private List<Map<String,Object>> markes;

	private FConjunctureVo [] conjuns;

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

	public List<Map<String,Object>> getMarkes() {
		return markes;
	}

	public void setMarkes(List<Map<String,Object>> markes) {
		this.markes = markes;
	}

	public FConjunctureVo[] getConjuns() {
		return conjuns;
	}

	public void setConjuns(FConjunctureVo[] conjuns) {
		this.conjuns = conjuns;
	}

	public String getTradeId() {
		return tradeId;
	}

	public void setTradeId(String tradeId) {
		this.tradeId = tradeId;
	}
}

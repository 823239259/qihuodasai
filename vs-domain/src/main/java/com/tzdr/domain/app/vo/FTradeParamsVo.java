package com.tzdr.domain.app.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import com.tzdr.common.utils.SqlColumn;
import com.tzdr.common.utils.SqlOrder;


/**
 * 国际期货 操盘参数vo
 * @author zhouchen
 * 2016年3月24日 上午10:29:53
 */
public class FTradeParamsVo implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 手续费
	 */
	@SqlColumn(name="tran_fees")
	private BigDecimal tranFees;

	
	/**
	 * 管理费
	 */
	@SqlColumn(name="fee_manage")
	private BigDecimal manageFee;
	
	
	
	/**
	 * 保证金(人民币)
	 */
	@SqlOrder
	@SqlColumn(name="trader_bond")
	private BigDecimal traderBond;
	
	/**
	 * 总操盘金额，
	 */
	@SqlColumn(name="trader_total")
	private BigDecimal traderTotal;
	
	/**
	 * 亏损平仓线，
	 */
	@SqlColumn(name="line_loss")
	private BigDecimal lineLoss;

	/**
	 * 推荐开仓手数 国际综合 表示A50 推荐开仓手数
	 */
	@SqlColumn(name="tran_lever")
	private Integer tranLever;
	

	/**
	 * 恒指交易手数
	 */
	@SqlColumn(name="htran_actual_lever")
	private Integer hsiTranLever;
	
	/**
	 * 原油交易手数
	 */
	@SqlColumn(name="ytran_actual_lever")
	private Integer crudeTranLever;
	
	/**
	 * 迷你纳指交易手数
	 */
	@SqlColumn(name="mntran_actual_lever")
	private Integer mntranLever;
	
	/**
	 * 迷你标普交易手数
	 */
	@SqlColumn(name="mbtran_actual_lever")
	private Integer mbtranLever;
	
	/**
	 * 德国DAX交易手数
	 */
	@SqlColumn(name="daxtran_actual_lever")
	private Integer daxtranLever;
	
	
	
	/**
	 * 日经225交易手数
	 */
	@SqlColumn(name="nikkei_tran_actual_lever")
	private Integer nikkeiTranLever;
	
	/**
	 *迷你道指交易手数
	 */
	@SqlColumn(name="mdtran_actual_lever")
	private Integer mdtranLever;

	/**
	 * 小恒指
	 */
	@SqlColumn(name="hstran_actual_lever")
	private Integer lhsiTranActualLever;
	
	/**
	 * 美黄金
	 * @return
	 */
	@SqlColumn(name="agtran_actual_lever")
	private Integer agTranActualLever;
	
	/**
	 * H股指
	 * @return
	 */
	@SqlColumn(name="h_index_actual_lever")
	private Integer hIndexActualLever;
	
	/**
	 * 小H股指
	 * @return
	 */
	@SqlColumn(name="xh_index_actual_lever")
	private Integer xhIndexActualLever;
	
	/**
	 * 美铜
	 * @return
	 */
	@SqlColumn(name="a_copper_actual_lever")
	private Integer aCopperActualLever;
	
	/**
	 * 美白银
	 * @return
	 */
	@SqlColumn(name="a_silver_actual_lever")
	private Integer aSilverActualLever;
	
	/**
	 * 小原油
	 * @return
	 */
	@SqlColumn(name="sma_actual_lever")
	private Integer smaActualLever;
	
	/**
	 * 迷你德国DAX指数
	 * @return
	 */
	@SqlColumn(name="daxtran_min_actual_lever")
	private Integer daxtranMinActualLever;
	
	/**
	 * 天然气交易指数
	 * @return
	 */
	@SqlColumn(name="natural_gas_actual_lever")
	private Integer naturalGasActualLever;
	
	public BigDecimal getTranFees() {
		return tranFees;
	}

	public void setTranFees(BigDecimal tranFees) {
		this.tranFees = tranFees;
	}

	public BigDecimal getManageFee() {
		return manageFee;
	}

	public void setManageFee(BigDecimal manageFee) {
		this.manageFee = manageFee;
	}

	public BigDecimal getTraderBond() {
		return traderBond;
	}

	public void setTraderBond(BigDecimal traderBond) {
		this.traderBond = traderBond;
	}

	public BigDecimal getTraderTotal() {
		return traderTotal;
	}

	public void setTraderTotal(BigDecimal traderTotal) {
		this.traderTotal = traderTotal;
	}

	public BigDecimal getLineLoss() {
		return lineLoss;
	}

	public void setLineLoss(BigDecimal lineLoss) {
		this.lineLoss = lineLoss;
	}

	public Integer getTranLever() {
		return tranLever;
	}

	public void setTranLever(Integer tranLever) {
		this.tranLever = tranLever;
	}

	public Integer getHsiTranLever() {
		return hsiTranLever;
	}

	public void setHsiTranLever(Integer hsiTranLever) {
		this.hsiTranLever = hsiTranLever;
	}

	public Integer getCrudeTranLever() {
		return crudeTranLever;
	}

	public void setCrudeTranLever(Integer crudeTranLever) {
		this.crudeTranLever = crudeTranLever;
	}

	public Integer getMntranLever() {
		return mntranLever;
	}

	public void setMntranLever(Integer mntranLever) {
		this.mntranLever = mntranLever;
	}

	public Integer getMbtranLever() {
		return mbtranLever;
	}

	public void setMbtranLever(Integer mbtranLever) {
		this.mbtranLever = mbtranLever;
	}

	public Integer getDaxtranLever() {
		return daxtranLever;
	}

	public void setDaxtranLever(Integer daxtranLever) {
		this.daxtranLever = daxtranLever;
	}

	public Integer getNikkeiTranLever() {
		return nikkeiTranLever;
	}

	public void setNikkeiTranLever(Integer nikkeiTranLever) {
		this.nikkeiTranLever = nikkeiTranLever;
	}

	public Integer getMdtranLever() {
		return mdtranLever;
	}

	public void setMdtranLever(Integer mdtranLever) {
		this.mdtranLever = mdtranLever;
	}

	public Integer getLhsiTranActualLever() {
		return lhsiTranActualLever;
	}

	public void setLhsiTranActualLever(Integer lhsiTranActualLever) {
		this.lhsiTranActualLever = lhsiTranActualLever;
	}

	public Integer getAgTranActualLever() {
		return agTranActualLever;
	}

	public void setAgTranActualLever(Integer agTranActualLever) {
		this.agTranActualLever = agTranActualLever;
	}

	public Integer gethIndexActualLever() {
		return hIndexActualLever;
	}

	public void sethIndexActualLever(Integer hIndexActualLever) {
		this.hIndexActualLever = hIndexActualLever;
	}

	public Integer getXhIndexActualLever() {
		return xhIndexActualLever;
	}

	public void setXhIndexActualLever(Integer xhIndexActualLever) {
		this.xhIndexActualLever = xhIndexActualLever;
	}

	public Integer getaCopperActualLever() {
		return aCopperActualLever;
	}

	public void setaCopperActualLever(Integer aCopperActualLever) {
		this.aCopperActualLever = aCopperActualLever;
	}

	public Integer getaSilverActualLever() {
		return aSilverActualLever;
	}

	public void setaSilverActualLever(Integer aSilverActualLever) {
		this.aSilverActualLever = aSilverActualLever;
	}

	public Integer getSmaActualLever() {
		return smaActualLever;
	}

	public void setSmaActualLever(Integer smaActualLever) {
		this.smaActualLever = smaActualLever;
	}

	public Integer getDaxtranMinActualLever() {
		return daxtranMinActualLever;
	}

	public void setDaxtranMinActualLever(Integer daxtranMinActualLever) {
		this.daxtranMinActualLever = daxtranMinActualLever;
	}

	public Integer getNaturalGasActualLever() {
		return naturalGasActualLever;
	}

	public void setNaturalGasActualLever(Integer naturalGasActualLever) {
		this.naturalGasActualLever = naturalGasActualLever;
	}
	
		
}

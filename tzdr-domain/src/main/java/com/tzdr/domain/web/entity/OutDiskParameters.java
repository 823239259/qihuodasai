package com.tzdr.domain.web.entity;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.tzdr.common.domain.BaseCrudEntity;
/**
 * 
 * 类说明   国际综合参数设置
 * @author  zhaozhao
 * @date    2016年2月22日下午5:28:18
 * @version 1.0
 */
@Entity
@Table(name="w_out_disk_parameters")
public class OutDiskParameters extends BaseCrudEntity{

	private static final long serialVersionUID = 7704705259957276476L;

	/**
	 * 保证金额(￥)
	 */
	private BigDecimal traderBond;
	
	/**
	 * 总操盘资金($)
	 */
	private BigDecimal traderTotal;

	/**
	 * 亏损平仓线($)
	 */
	private BigDecimal lineLoss;

	/**
	 * 入金金额($)
	 */
	private BigDecimal goldenMoney;

	/**
	 * A50交易手数
	 */
	private Integer atranActualLever;
	
	/**
	 * 恒指交易手数
	 */
	private Integer htranActualLever;
	
	/**
	 *原油交易手数
	 */
	private Integer ytranActualLever;
	
	
	/**
	 * 迷你纳指交易手数
	 */
	private Integer mntranActualLever;
	
	/**
	 * 迷你标普交易手数
	 */
	private Integer mbtranActualLever;
	
	/**
	 * 德国DAX交易手数
	 */
	private Integer daxtranActualLever;
	
	
	
	/**
	 * 日经225交易手数
	 */
	private Integer nikkeiTranActualLever;
	
	/**
	 *迷你道指交易手数
	 */
	private Integer mdtranActualLever;
	
	

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

	public BigDecimal getGoldenMoney() {
		return goldenMoney;
	}

	public void setGoldenMoney(BigDecimal goldenMoney) {
		this.goldenMoney = goldenMoney;
	}

	public Integer getAtranActualLever() {
		return atranActualLever;
	}

	public void setAtranActualLever(Integer atranActualLever) {
		this.atranActualLever = atranActualLever;
	}

	public Integer getHtranActualLever() {
		return htranActualLever;
	}

	public void setHtranActualLever(Integer htranActualLever) {
		this.htranActualLever = htranActualLever;
	}

	public Integer getYtranActualLever() {
		return ytranActualLever;
	}

	public void setYtranActualLever(Integer ytranActualLever) {
		this.ytranActualLever = ytranActualLever;
	}

	public Integer getMntranActualLever() {
		return mntranActualLever;
	}

	public void setMntranActualLever(Integer mntranActualLever) {
		this.mntranActualLever = mntranActualLever;
	}

	public Integer getMbtranActualLever() {
		return mbtranActualLever;
	}

	public void setMbtranActualLever(Integer mbtranActualLever) {
		this.mbtranActualLever = mbtranActualLever;
	}

	public Integer getDaxtranActualLever() {
		return daxtranActualLever;
	}

	public void setDaxtranActualLever(Integer daxtranActualLever) {
		this.daxtranActualLever = daxtranActualLever;
	}

	public Integer getNikkeiTranActualLever() {
		return nikkeiTranActualLever;
	}

	public void setNikkeiTranActualLever(Integer nikkeiTranActualLever) {
		this.nikkeiTranActualLever = nikkeiTranActualLever;
	}

	public Integer getMdtranActualLever() {
		return mdtranActualLever;
	}

	public void setMdtranActualLever(Integer mdtranActualLever) {
		this.mdtranActualLever = mdtranActualLever;
	}

	
	
	

}

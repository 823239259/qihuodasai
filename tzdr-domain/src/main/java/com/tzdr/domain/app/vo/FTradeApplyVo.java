package com.tzdr.domain.app.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import org.apache.commons.lang3.math.NumberUtils;

import com.tzdr.domain.web.entity.FSimpleConfig;
import com.tzdr.domain.web.entity.OutDiskParameters;


/**
 * 国际期货 申请操盘时返回的确认信息
 * @author zhouchen
 * 2016年3月24日 上午10:29:53
 */
public class FTradeApplyVo implements Serializable {

		/**
		 * 手续费
		 */
		private BigDecimal payable;
		
		
		/**
		 * 单手保证金(人民币)
		 */
		private Double balance;
		
		/**
		 * 总操盘金额，
		 */
		private BigDecimal traderTotal;
		
		/**
		 * 亏损平仓线，
		 */
		private BigDecimal lineLoss;

		/**
		 * 推荐开仓手数 国际综合 表示A50 推荐开仓手数
		 */
		private Integer tranLever;
		

		/**
		 * 恒指交易手数
		 */
		private Integer hsiTranLever;
		
		/**
		 *原油交易手数
		 */
		private Integer crudeTranLever;
		
		/**
		 * 迷你纳指交易手数
		 */
		private Integer mntranLever;
		
		/**
		 * 迷你标普交易手数
		 */
		private Integer mbtranLever;
		
		/**
		 * 德国DAX交易手数
		 */
		private Integer daxtranLever;
		
		
		
		/**
		 * 日经225交易手数
		 */
		private Integer nikkeiTranLever;
		
		/**
		 *迷你道指交易手数
		 */
		private Integer mdtranLever;


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

		public BigDecimal getPayable() {
			return payable;
		}

		public void setPayable(BigDecimal payable) {
			this.payable = payable;
		}

		public Double getBalance() {
			return balance;
		}

		public void setBalance(Double balance) {
			this.balance = balance;
		}
		
		public FTradeApplyVo() {
		}
		
		
		public FTradeApplyVo(FSimpleConfig fSimpleConfig,BigDecimal payable,Double balance) {
			this.tranLever = NumberUtils.toInt(fSimpleConfig.getTranLever());
			this.traderTotal=fSimpleConfig.getTraderMoney();
			this.lineLoss=fSimpleConfig.getLineLoss();
			this.payable=payable;
			this.balance=balance;
		}
		
		public FTradeApplyVo(OutDiskParameters outDiskParameters,BigDecimal payable,Double balance) {
			this.tranLever = outDiskParameters.getAtranActualLever();
			this.hsiTranLever = outDiskParameters.getHtranActualLever();
			this.crudeTranLever = outDiskParameters.getYtranActualLever();
			this.mntranLever = outDiskParameters.getMntranActualLever();
			this.mdtranLever = outDiskParameters.getMdtranActualLever();
			this.mbtranLever = outDiskParameters.getMbtranActualLever();
			this.daxtranLever = outDiskParameters.getDaxtranActualLever();
			this.nikkeiTranLever = outDiskParameters.getNikkeiTranActualLever();
			
			this.traderTotal=outDiskParameters.getTraderTotal();
			this.lineLoss=outDiskParameters.getLineLoss();
			this.payable=payable;
			this.balance=balance;
		}
		
		
		
}

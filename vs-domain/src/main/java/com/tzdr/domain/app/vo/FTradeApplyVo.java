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
		
		/**
		 *小恒指交易手数
		 */
		private Integer hstranLever;
		
		/**
		 *美黄金交易手数
		 */
		private Integer agtranLever;
		/**
		 * H股指交易手数

		 */
        private Integer hStockMarketLever;
        /**
         * 小H股指交易手数
         */
        private Integer xHStockMarketLever;
        /**
         * 美铜交易手数
         */
        private Integer ameCopperMarketLever;
        /**
         * 美白银交易手数
         */
        private Integer ameSilverMarketLever;
        /**
         * 小原油交易手数
         */
        private Integer smallCrudeOilMarketLever;
        /**
         * 迷你德国DAX指数交易手数
         */
        private Integer daxtranMinActualLever;
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
		
		
		
		public Integer getHstranLever() {
			return hstranLever;
		}

		public void setHstranLever(Integer hstranLever) {
			this.hstranLever = hstranLever;
		}

		public Integer getAgtranLever() {
			return agtranLever;
		}

		public void setAgtranLever(Integer agtranLever) {
			this.agtranLever = agtranLever;
		}

		public Integer gethStockMarketLever() {
			return hStockMarketLever;
		}

		public void sethStockMarketLever(Integer hStockMarketLever) {
			this.hStockMarketLever = hStockMarketLever;
		}

		public Integer getxHStockMarketLever() {
			return xHStockMarketLever;
		}

		public void setxHStockMarketLever(Integer xHStockMarketLever) {
			this.xHStockMarketLever = xHStockMarketLever;
		}

		public Integer getAmeCopperMarketLever() {
			return ameCopperMarketLever;
		}

		public void setAmeCopperMarketLever(Integer ameCopperMarketLever) {
			this.ameCopperMarketLever = ameCopperMarketLever;
		}

		public Integer getAmeSilverMarketLever() {
			return ameSilverMarketLever;
		}

		public void setAmeSilverMarketLever(Integer ameSilverMarketLever) {
			this.ameSilverMarketLever = ameSilverMarketLever;
		}

		public Integer getSmallCrudeOilMarketLever() {
			return smallCrudeOilMarketLever;
		}

		public void setSmallCrudeOilMarketLever(Integer smallCrudeOilMarketLever) {
			this.smallCrudeOilMarketLever = smallCrudeOilMarketLever;
		}

		public Integer getDaxtranMinActualLever() {
			return daxtranMinActualLever;
		}

		public void setDaxtranMinActualLever(Integer daxtranMinActualLever) {
			this.daxtranMinActualLever = daxtranMinActualLever;
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
			this.hstranLever = outDiskParameters.getHstranActualLever();
			this.agtranLever = outDiskParameters.getAgtranActualLever();
			this.hStockMarketLever = outDiskParameters.gethIndexActualLever();
			this.xHStockMarketLever = outDiskParameters.getxhIndexActualLever();
			this.ameCopperMarketLever = outDiskParameters.getaCopperActualLever();
			this.ameSilverMarketLever = outDiskParameters.getaSilverActualLever();
			this.smallCrudeOilMarketLever = outDiskParameters.getSmaActualLever();
			this.daxtranMinActualLever = outDiskParameters.getDaxtranMinActualLever();
			this.traderTotal=outDiskParameters.getTraderTotal();
			this.lineLoss=outDiskParameters.getLineLoss();
			this.payable=payable;
			this.balance=balance;
		}
		
		
		
}

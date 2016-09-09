package com.tzdr.domain.vo.ftse;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

import jodd.util.ObjectUtil;

import com.tzdr.common.utils.AllowExcel;
import com.tzdr.common.utils.Dates;

/**
 * 富时A50开户查询 VO
 * 
 * @author wucholiang 2015年9月17日 上午11:23:50
 */
public class FSimpleFtseManageVo implements Serializable {

	private static final long serialVersionUID = -6469817179539585309L;

	/**
	 * 申请ID
	 */
	private String id;

	/**
	 * 用户编号
	 */
	private String uid;

	/**
	 * 用户手机号
	 */
	@AllowExcel(name = "手机号码")
	private String mobile;

	/**
	 * 客户姓名
	 */
	@AllowExcel(name = "客户姓名")
	private String tname;

	/**
	 * 业务状态【0.富时A50 6.原油 7. 恒指 9 .小恒指】
	 */
	@AllowExcel(name = "交易品种")
	private Integer businessType;

	/**
	 * 交易账号
	 */
	@AllowExcel(name = "操盘账户")
	private String tranAccount;

	/**
	 * 申请时间
	 */
	@AllowExcel(name = "申请时间")
	private BigInteger appTime;

	/**
	 * 开始时间（使用日期）
	 */
	@AllowExcel(name = "账户启用日期")
	private BigInteger appStarttime;
	private BigInteger appStarttimeLong;

	/**
	 * 开仓手数
	 */
	@AllowExcel(name = "可开仓手数")
	private Integer tranLever;

	/**
	 * 实际操盘时间
	 */
	@AllowExcel(name = "已操盘时间")
	private Integer useTranDay;

	/**
	 * 总保证金额
	 */
	@AllowExcel(name = "操盘保证金(元)")
	private BigDecimal traderBond;

	/**
	 * 代金券使用金额
	 */
	@AllowExcel(name = "实际代金券(元)")
	private BigDecimal voucherActualMoney;

	/**
	 * 补充保证金金额
	 */
	@AllowExcel(name = "补充保证金(元)")
	private BigDecimal appendTraderBond;
	/**
	 * 总操盘金额
	 */
	@AllowExcel(name = "总操盘资金(美元)")
	private BigDecimal traderTotal;

	/**
	 * 交易盈亏
	 */
	@AllowExcel(name = "交易盈亏(美元)")
	private BigDecimal tranProfitLoss;

	/**
	 * 交易手数 备注：当businessType=8 国际综合时，此字段表示A50交易手数，否则为当前类型交易手数
	 */
	@AllowExcel(name = "A50交易手数")
	private Integer tranActualLever;

	/**
	 * 恒生指数交易手数
	 */
	@AllowExcel(name = "恒指交易手数")
	private Integer hsiTranActualLever;

	/**
	 * 国际原油交易手数
	 */
	@AllowExcel(name = "原油交易手数")
	private Integer crudeTranActualLever;

	/**
	 * 迷你道指交易手数
	 */
	@AllowExcel(name = "迷你道指交易手数")
	private Integer mdtranActualLever;

	/**
	 * 迷你纳指交易手数
	 */
	@AllowExcel(name = "迷你纳指交易手数")
	private Integer mntranActualLever;

	/**
	 * 迷你标普交易手数
	 */
	@AllowExcel(name = "迷你标普交易手数")
	private Integer mbtranActualLever;

	/**
	 * 德国DAX交易手数
	 */
	@AllowExcel(name = "德国DAX交易手数")
	private Integer daxtranActualLever;

	/**
	 * 日经225交易手数
	 */
	@AllowExcel(name = "日经225交易手数")
	private Integer nikkeiTranActualLever;

	/**
	 * 小恒指交易手数
	 */
	@AllowExcel(name = "小恒指交易手数")
	private Integer lhsiTranActualLever;

	/**
	 * 美黄金交易手数
	 */
	@AllowExcel(name = "美黄金交易手数")
	private Integer agTranActualLever;

	/**
	 * H股指交易手数
	 */
	@AllowExcel(name = "H股指交易手数")
	private Integer heStockMarketLever;
	/**
	 * 小H股指交易手数
	 */
	@AllowExcel(name = "小H股指交易手数")
	private Integer xhStockMarketLever;
	/**
	 * 美铜交易手数
	 */
	@AllowExcel(name = "美铜交易手数")
	private Integer ameCopperMarketLever;
	/**
	 * 美白银交易手数
	 */
	@AllowExcel(name = "美白银交易手数")
	private Integer ameSilverMarketLever;
	/**
	 * 小原油交易手数
	 */
	@AllowExcel(name = "小原油交易手数")
	private Integer smallCrudeOilMarketLever;
	
	/**
	 * 交易手续费总额
	 */
	@AllowExcel(name = "交易手续费")
	private BigDecimal tranFeesTotal;

	/**
	 * 折扣券面值
	 */

	private BigDecimal discountMoney;
	@AllowExcel(name = "优惠券(折)")
	private String discountMoneyStr;
	/**
	 * 抵扣券抵扣手续费
	 */
	@AllowExcel(name = "抵扣手续费(元)")
	private BigDecimal discountActualMoney;

	/**
	 * 汇率【美元($)-人民币(¥)】
	 */
	@AllowExcel(name = "汇率")
	private BigDecimal endParities;

	/**
	 * 结算金额
	 */
	@AllowExcel(name = "结算金额(元)")
	private BigDecimal endAmountCal;

	/**
	 * 实际结算金额
	 */
	@AllowExcel(name = "实际结算金额(元)")
	private BigDecimal endAmount;

	/**
	 * 亏损平仓线
	 */
	private BigDecimal lineLoss;

	/**
	 * 账户管理费
	 */
	private BigDecimal feeManage;

	/**
	 * 交易手续费
	 */
	@AllowExcel(name = "申请手续费")
	private BigDecimal tranFees;

	/**
	 * 账号密码
	 */
	private String tranPassword;

	/**
	 * 申请终结时间
	 */
	@AllowExcel(name = "申请结算时间")
	private BigInteger appEndTime;

	/**
	 * 结算时间
	 */
	@AllowExcel(name = "结算时间")
	private BigInteger endTime;

	/**
	 * 平台来源 1:网站平台 2:APP平台 默认1
	 */
	private Integer source;
	@AllowExcel(name = "平台来源")
	private String sourceStr;

	/**
	 * 方案号TG+ID号
	 */
	@AllowExcel(name = "方案编号")
	private String programNo;

	/**
	 * 状态【1.开户中、2.申请结算、3.待结算、4.操盘中 5.审核不通过 、6.已结算】
	 */
	@AllowExcel(name = "结算状态")
	private Integer stateType;

	/**
	 * 更改时间
	 */
	private BigInteger updateTime;

	/**
	 * 逻辑删除flag
	 */
	private Boolean deleted;

	private BigInteger version;

	/**
	 * 优惠券类型
	 */
	private String type;
	
	/**
	 * 操作员
	 */
    @AllowExcel(name = "操作员")
	private String operator;

	public Integer getSource() {
		return source;
	}

	public void setSource(Integer source) {
		this.source = source;
	}

	public String getSourceStr() {
		String str = "";
		if (this.source != null && this.source == 2) {
			str = "APP平台";
		} else {
			str = "网站平台";
		}
		return str;
	}

	public void setSourceStr(String sourceStr) {
		this.sourceStr = sourceStr;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getProgramNo() {
		return programNo;
	}

	public void setProgramNo(String programNo) {
		this.programNo = programNo;
	}

	public BigDecimal getTraderBond() {
		return traderBond;
	}

	public void setTraderBond(BigDecimal traderBond) {
		this.traderBond = traderBond;
	}

	public Integer getTranLever() {
		return tranLever;
	}

	public void setTranLever(Integer tranLever) {
		this.tranLever = tranLever;
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

	public BigDecimal getFeeManage() {
		return feeManage;
	}

	public void setFeeManage(BigDecimal feeManage) {
		this.feeManage = feeManage;
	}

	public BigDecimal getTranFees() {
		return tranFees;
	}

	public void setTranFees(BigDecimal tranFees) {
		this.tranFees = tranFees;
	}

	public String getAppTime() {
		if (ObjectUtil.equals(null, this.appTime)) {
			return "";
		}
		return Dates.parseBigInteger2Date(this.appTime, Dates.CHINESE_DATETIME_FORMAT_LINE);
	}

	public void setAppTime(BigInteger appTime) {
		this.appTime = appTime;
	}

	public String getAppStarttime() {
		if (ObjectUtil.equals(null, this.appStarttime)) {
			return "";
		}
		return Dates.parseBigInteger2Date(this.appStarttime, Dates.CHINESE_DATETIME_FORMAT_LINE);
	}

	public void setAppStarttime(BigInteger appStarttime) {
		this.setAppStarttimeLong(appStarttime);
		this.appStarttime = appStarttime;
	}

	public BigInteger getAppStarttimeLong() {
		return appStarttimeLong;
	}

	public void setAppStarttimeLong(BigInteger appStarttimeLong) {
		this.appStarttimeLong = appStarttimeLong;
	}

	public String getTranAccount() {
		return tranAccount;
	}

	public void setTranAccount(String tranAccount) {
		this.tranAccount = tranAccount;
	}

	public String getTranPassword() {
		return tranPassword;
	}

	public void setTranPassword(String tranPassword) {
		this.tranPassword = tranPassword;
	}

	public String getAppEndTime() {
		if (ObjectUtil.equals(null, this.appEndTime)) {
			return "";
		}
		return Dates.parseBigInteger2Date(this.appEndTime, Dates.CHINESE_DATETIME_FORMAT_LINE);
	}

	public void setAppEndTime(BigInteger appEndTime) {
		this.appEndTime = appEndTime;
	}

	public Integer getTranActualLever() {
		if (this.businessType == 6 || this.businessType == 7 || this.businessType == 9) {
			if (this.businessType == 6) {
				this.crudeTranActualLever = this.tranActualLever;
			}
			if (this.businessType == 7) {
				this.hsiTranActualLever = this.tranActualLever;
			}

			if (this.businessType == 9) {
				this.lhsiTranActualLever = this.tranActualLever;
			}
			this.tranActualLever = null;
		}
		return tranActualLever;
	}

	public void setTranActualLever(Integer tranActualLever) {
		this.tranActualLever = tranActualLever;
	}

	public BigDecimal getTranFeesTotal() {
		return tranFeesTotal;
	}

	public void setTranFeesTotal(BigDecimal tranFeesTotal) {
		this.tranFeesTotal = tranFeesTotal;
	}

	public BigDecimal getTranProfitLoss() {
		return tranProfitLoss;
	}

	public void setTranProfitLoss(BigDecimal tranProfitLoss) {
		this.tranProfitLoss = tranProfitLoss;
	}

	public BigDecimal getEndAmount() {
		return endAmount;
	}

	public void setEndAmount(BigDecimal endAmount) {
		this.endAmount = endAmount;
	}

	public BigDecimal getEndAmountCal() {
		return endAmountCal;
	}

	public void setEndAmountCal(BigDecimal endAmountCal) {
		this.endAmountCal = endAmountCal;
	}

	public BigDecimal getEndParities() {
		return endParities;
	}

	public void setEndParities(BigDecimal endParities) {
		this.endParities = endParities;
	}

	public String getEndTime() {
		if (ObjectUtil.equals(null, this.endTime)) {
			return "";
		}
		return Dates.parseBigInteger2Date(this.endTime, Dates.CHINESE_DATETIME_FORMAT_LINE);
	}

	public void setEndTime(BigInteger endTime) {
		this.endTime = endTime;
	}

	public Integer getUseTranDay() {
		return useTranDay;
	}

	public void setUseTranDay(Integer useTranDay) {
		this.useTranDay = useTranDay;
	}

	/**
	 * 状态【1.开户中、2.申请结算、3.待结算、4.操盘中 5.审核不通过 、6.已结算】
	 */
	public String getStateType() {
		switch (stateType.intValue()) {
		case 1:
			return "开户中";
		case 2:
			return "申请结算";
		case 3:
			return "待结算";
		case 4:
			return "操盘中";
		case 5:
			return "申请已拒绝";
		case 6:
			return "已结算";
		default:
			return "";
		}
	}

	public void setStateType(Integer stateType) {
		this.stateType = stateType;
	}

	public String getUpdateTime() {
		if (ObjectUtil.equals(null, this.updateTime)) {
			return "";
		}
		return Dates.parseBigInteger2Date(this.updateTime, Dates.CHINESE_DATETIME_FORMAT_LINE);
	}

	public void setUpdateTime(BigInteger updateTime) {
		this.updateTime = updateTime;
	}

	public String getBusinessType() {
		switch (businessType.intValue()) {
		case 0:
			return "富时A50";
		case 6:
			return "国际原油";
		case 7:
			return "恒生指数";
		case 8:
			return "国际综合";
		case 9:
			return "小恒指";
		default:
			return "";
		}

	}

	public void setBusinessType(Integer businessType) {
		this.businessType = businessType;
	}

	public String getTname() {
		return tname;
	}

	public void setTname(String tname) {
		this.tname = tname;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	public BigInteger getVersion() {
		return version;
	}

	public void setVersion(BigInteger version) {
		this.version = version;
	}

	public BigDecimal getAppendTraderBond() {
		return appendTraderBond;
	}

	public void setAppendTraderBond(BigDecimal appendTraderBond) {
		this.appendTraderBond = appendTraderBond;
	}

	public Integer getHsiTranActualLever() {
		return hsiTranActualLever;
	}

	public void setHsiTranActualLever(Integer hsiTranActualLever) {
		this.hsiTranActualLever = hsiTranActualLever;
	}

	public Integer getCrudeTranActualLever() {
		return crudeTranActualLever;
	}

	public void setCrudeTranActualLever(Integer crudeTranActualLever) {
		this.crudeTranActualLever = crudeTranActualLever;
	}

	public Integer getMdtranActualLever() {
		return mdtranActualLever;
	}

	public void setMdtranActualLever(Integer mdtranActualLever) {
		this.mdtranActualLever = mdtranActualLever;
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

	public BigDecimal getVoucherActualMoney() {
		return voucherActualMoney;
	}

	public void setVoucherActualMoney(BigDecimal voucherActualMoney) {
		this.voucherActualMoney = voucherActualMoney;
	}

	public BigDecimal getDiscountMoney() {
		return discountMoney;
	}

	public void setDiscountMoney(BigDecimal discountMoney) {
		this.discountMoney = discountMoney;
	}

	public BigDecimal getDiscountActualMoney() {
		return discountActualMoney;
	}

	public void setDiscountActualMoney(BigDecimal discountActualMoney) {
		this.discountActualMoney = discountActualMoney;
	}

	public String getDiscountMoneyStr() {

		if (null != discountMoney && null != type) {
			if ("3".equals(type)) {
				discountMoneyStr = discountMoney.doubleValue() + "折";
			} else if ("6".equals(type)) {
				discountMoneyStr = discountMoney.toString() + "元";
			}
		}

		return discountMoneyStr;
	}

	public void setDiscountMoneyStr(String discountMoneyStr) {
		this.discountMoneyStr = discountMoneyStr;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}




	public Integer getHeStockMarketLever() {
		return heStockMarketLever;
	}

	public void setHeStockMarketLever(Integer heStockMarketLever) {
		this.heStockMarketLever = heStockMarketLever;
	}

	public Integer getXhStockMarketLever() {
		return xhStockMarketLever;
	}

	public void setXhStockMarketLever(Integer xhStockMarketLever) {
		this.xhStockMarketLever = xhStockMarketLever;
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

}
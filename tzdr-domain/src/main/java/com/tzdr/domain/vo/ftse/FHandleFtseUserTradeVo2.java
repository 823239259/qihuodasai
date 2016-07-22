package com.tzdr.domain.vo.ftse;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

import javax.persistence.Column;

import jodd.util.ObjectUtil;

import com.tzdr.common.utils.AllowExcel;
import com.tzdr.common.utils.Dates;

/**
 * 富时A50开户查询  VO
 * @author wucholiang
 * 2015年9月25日 上午11:23:50
 */
public class FHandleFtseUserTradeVo2 implements Serializable {
	
	private static final long serialVersionUID = -9124403652338753651L;

	/**
	 * 申请ID
	 */
	private String id;
	
	/**
	 * 用户ID
	 */
	private String uid;

	/**
	 * 用户手机号
	 */
	@AllowExcel(name="手机号码")
	private String mobile;
	
	/**
	 * 客户姓名
	 */
	@AllowExcel(name="客户姓名")
	private String tname;
	
	/**  
	 * 业务状态【0.富时A50 6.原油    7. 恒指  8.国际综合】
	 */
	private Integer businessType;
	
	//@AllowExcel(name="交易品种")
	private String businessTypeStr;

	/**
	 * 交易账号
	 */
	@AllowExcel(name="操盘账户")
	private String tranAccount;

	/**
	 * 申请时间
	 */
	@AllowExcel(name="申请时间")
	private BigInteger appTime;

	/**
	 * 总保证金额
	 */
	@AllowExcel(name="保证金(元)")
	private BigDecimal traderBond;

	/**
	 * 结算时实际抵扣金额
	 */
	@AllowExcel(name="抵扣保证金(元)")
	private BigDecimal endActualMoney;
	
	/**
	 * 开仓手数
	 */
	//@AllowExcel(name="可开仓手数")
	private Integer tranLever;

	/**
	 * 处理时间
	 */
	@AllowExcel(name="开户处理时间")
	private BigInteger updateTime;
	@AllowExcel(name="补充保证金(元)")
	private BigDecimal appendTraderBond;
	/**
	 * 实际操盘时间
	 */
	private Integer useTranDay;
	/**
	 * 申请终结时间
	 */
	@AllowExcel(name="申请结算时间")
	private BigInteger appEndTime;

	/**
	 * 结算时间
	 */
	@AllowExcel(name="结算时间")
	private BigInteger endTime;

	/**
	 * 交易手续费总额
	 */
	@AllowExcel(name="交易手续费(元)")
	private BigDecimal tranFeesTotal;
	
	
	/**
	 * 折扣券面值
	 */
	@AllowExcel(name="折扣券（折）")
	private BigDecimal discountMoney;
	
	/**
	 * 抵扣券抵扣手续费
	 */
	@AllowExcel(name="抵扣手续费(元)")
	private BigDecimal discountActualMoney;
	
	/**
	 * 实际盈亏(元)
	 */
	@AllowExcel(name="实际盈亏(元)")
	private BigDecimal actualProfitLoss;

	
	/**
	 * 平台来源 1:网站平台   2:APP平台   默认1
	 */
	private Integer source;
	@AllowExcel(name="平台来源")
	private String sourceStr;
	
	
	/**  
	 * 状态【1.开户中、2.申请结算、3.待结算、4.操盘中  5.审核不通过 、6.已结算】
	 */
	@AllowExcel(name="结算状态")
	private Integer stateType;
	
	/**
	 * 开始时间（使用日期）
	 */
	private BigInteger appStarttime;
	private BigInteger appStarttimeLong;
	
	/**
	 * 总操盘金额
	 */
	private BigDecimal traderTotal;

	/**
	 * 交易盈亏
	 */
	private BigDecimal tranProfitLoss;
	
	/**
	 * 交易手数
	 */
	private Integer tranActualLever;

	/**
	 * 汇率【美元($)-人民币(¥)】
	 */
	private BigDecimal endParities;

	/**
	 * 结算金额
	 */
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
	private BigDecimal tranFees;
	
	/**
	 * 账号密码
	 */
	private String tranPassword;
	
	

	
	/**
	 * 方案号TG+ID号
	 */
	private String programNo;
	
	/**  
	 * 状态【1.开户中、2.申请结算、3.待结算、4.操盘中  5.审核不通过 、6.已结算】
	 */
	private Integer state;

	/**
	 * 追加保证金额
	 */
	
	public Integer getSource() {
		return source;
	}

	public void setSource(Integer source) {
		this.source = source;
	}

	public String getSourceStr() {
		String str="";
		if(this.source!=null&&this.source==2){
			str="APP平台";
		}else{
			str="网站平台";
		}
		return str;
	}

	public void setSourceStr(String sourceStr) {
		this.sourceStr = sourceStr;
	}
	
	
	public BigDecimal getAppendTraderBond() {
		return appendTraderBond;
	}

	public void setAppendTraderBond(BigDecimal appendTraderBond) {
		this.appendTraderBond = appendTraderBond;
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
		if (ObjectUtil.equals(null,this.appTime)){
			return "";
		}
		return Dates.parseBigInteger2Date(this.appTime, Dates.CHINESE_DATETIME_FORMAT_LINE);
	}

	public void setAppTime(BigInteger appTime) {
		this.appTime = appTime;
	}

	public String getAppStarttime() {
		if (ObjectUtil.equals(null,this.appStarttime)){
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
		if (ObjectUtil.equals(null,this.appEndTime)){
			return "";
		}
		return Dates.parseBigInteger2Date(this.appEndTime, Dates.CHINESE_DATETIME_FORMAT_LINE);
	}

	public void setAppEndTime(BigInteger appEndTime) {
		this.appEndTime = appEndTime;
	}

	public Integer getTranActualLever() {
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

	public BigDecimal getEndParities() {
		return endParities;
	}

	public void setEndParities(BigDecimal endParities) {
		this.endParities = endParities;
	}

	public String getEndTime() {
		if (ObjectUtil.equals(null,this.endTime)){
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
	 * 状态【1.开户中、2.申请结算、3.待结算、4.操盘中  5.审核不通过 、6.已结算】
	 */
	public String getStateType() {
		this.state = this.stateType.intValue();
		switch (stateType.intValue()) {
			case 1:return "开户中";
			case 2:return "申请结算";
			case 3:return "待结算";
			case 4:return "操盘中";
			case 5:return "申请已拒绝";
			case 6:return "已结算";
			default:return "";
		}
	}

	public void setStateType(Integer stateType) {
		this.stateType = stateType;
	}

	public String getUpdateTime() {
		if (ObjectUtil.equals(null,this.updateTime)){
			return "";
		}
		return Dates.parseBigInteger2Date(this.updateTime, Dates.CHINESE_DATETIME_FORMAT_LINE);
	}

	public void setUpdateTime(BigInteger updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getBusinessType() {
		return businessType;
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

	public BigDecimal getActualProfitLoss() {
		if(endAmount!=null && traderBond!=null){
			if(appendTraderBond==null){
				actualProfitLoss=endAmount.subtract(traderBond);
			}else{
				actualProfitLoss=endAmount.subtract(traderBond).subtract(appendTraderBond);
			}
		}
		return actualProfitLoss;
	}

	public void setActualProfitLoss(BigDecimal actualProfitLoss) {
		this.actualProfitLoss = actualProfitLoss;
	}

	public Integer getState() {
		this.getStateType();
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getBusinessTypeStr() {
		switch (businessType.intValue()) {
		case 0:
			setBusinessTypeStr("富时A50");
			break;
		case 6:
			setBusinessTypeStr("国际原油");
			break;
		case 7:
			setBusinessTypeStr("恒生指数");
			break;
		case 8:
			setBusinessTypeStr("国际综合");
			break;
		default:
			setBusinessTypeStr("");
			break;
		}
		return businessTypeStr;
	}

	public void setBusinessTypeStr(String businessTypeStr) {
		this.businessTypeStr = businessTypeStr;
	}

	public BigDecimal getEndActualMoney() {
		return endActualMoney;
	}

	public void setEndActualMoney(BigDecimal endActualMoney) {
		this.endActualMoney = endActualMoney;
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
}
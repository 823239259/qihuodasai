package com.tzdr.domain.hkstock.vo;

import java.io.Serializable;
import java.math.BigInteger;

import jodd.util.ObjectUtil;

import com.tzdr.common.utils.AllowExcel;
import com.tzdr.common.utils.BigDecimalUtils;
import com.tzdr.common.utils.Dates;

/**
 * 港股方案管理VO
 * 
 * @Description:
 * @author liuhaichuan
 * @date 2015年10月21日
 *
 */
public class HkTradeManageVo implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 配资方案id
	 */
	private String tradeId;

	/**
	 * 结算金额=终结方案后总金额
	 */
	private Double finishedMoney = 0.0;

	/**
	 * 港元汇率（方案终结时计算的汇率）
	 */
	private Double endExchangeRate = 0.0;

	/**
	 * 手机号
	 */
	@AllowExcel(name = "手机号码")
	private String mobile;

	/**
	 * 客户姓名
	 */
	@AllowExcel(name = "真实姓名")
	private String tname;

	/**
	 * 账户类型
	 */
	private Integer userType=0;
	@AllowExcel(name = "用户类型")
	private String userTypeValue;

	/**
	 * 交易账户名
	 */
	@AllowExcel(name = "交易账户名")
	private String accountName;

	/**
	 * 交易账号
	 */
	@AllowExcel(name = "交易账号")
	private String accountNo;

	/**
	 * 方案编号
	 */
	@AllowExcel(name = "方案编号")
	private String groupId;

	/**
	 * 保证金（元）
	 */
	@AllowExcel(name = "保证金（元）")
	private Double leverMoney = 0.0;

	/**
	 * 追加保证金（元）
	 */
	@AllowExcel(name = "累计追加保证金（元）")
	private Double appendLeverMoney = 0.0;

	/**
	 * 配资金额（港元）
	 */
	@AllowExcel(name = "配资金额（港元）")
	private Double money = 0.0;

	/**
	 * 总操盘资金（港元）
	 */
	@AllowExcel(name = "总操盘资金（港元）")
	private Double totalLeverMoney = 0.0;

	/**
	 * 亏损警戒线（港元）
	 */
	@AllowExcel(name = "亏损警戒线（港元）")
	private Double warning = 0.0;

	/**
	 * 亏损平仓线（港元）
	 */
	@AllowExcel(name = "亏损平仓线（港元）")
	private Double open = 0.0;

	/**
	 * 配资倍数
	 */
	@AllowExcel(name = "配资倍数")
	private Integer lever;

	/**
	 * 配资交易天数（交易日）
	 */
	@AllowExcel(name = "配资天数")
	private Integer startdays;

	/**
	 * 已操盘天数
	 */
	@AllowExcel(name = "已操盘天数")
	private Long tradingDays = 0l;

	/**
	 * 操盘盈亏（港元） 操盘盈亏 =结算金额-总操盘资金
	 */
	@AllowExcel(name = "操盘盈亏（港元）")
	private Double accrual = 0.0;

	/**
	 * 投资盈利（元） 投资盈利 = （结算金额-总操盘资金）x 结算汇率
	 */
	@AllowExcel(name = "投资盈利（元）")
	private Double investAccrual = 0.0;

	/**
	 * 0：验资中，1操盘中，2方案结束（终结）
	 */
	private Short status = 0;
	@AllowExcel(name = "方案状态")
	private String statusValue;

	/**
	 * 账户交易通道 0:TTS
	 */
	private Integer tradeChannel = 0;
	@AllowExcel(name = "交易账户类型")
	private String tradeChannelValue;

	/**
	 * 方案创建时间
	 */
	private BigInteger addtime;
	@AllowExcel(name = "方案创建时间")
	private String addTimeValue;

	/**
	 * 方案开始日期
	 */
	private BigInteger starttime;
	@AllowExcel(name = "方案开始日期")
	private String startTimeValue;

	/**
	 * 方案终止时间
	 */
	private BigInteger endtime;
	@AllowExcel(name = "方案终止时间")
	private String endTimeValue;

	/**
	 * 预计结束时间
	 */
	private BigInteger estimateEndtime;
	@AllowExcel(name = "预计结束日期")
	private String estimateEndTimeValue;
	
	/**
	 * 子方案ID
	 */
	private String programNo;
	
	/**
	 * 管理费
	 */
	private Double feeDay=0.0;
	
	/**
	 * 首次总利息
	*/
	private Double apr=0.0;
	
	/**
	 * 方案类型
	 */
	private Integer activityType=0;

	public String getTradeId() {
		return tradeId;
	}

	public void setTradeId(String tradeId) {
		this.tradeId = tradeId;
	}

	public Double getFinishedMoney() {
		return finishedMoney;
	}

	public void setFinishedMoney(Double finishedMoney) {
		if(finishedMoney!=null){			
			this.finishedMoney = finishedMoney;
		}
	}

	public Double getEndExchangeRate() {
		return endExchangeRate;
	}

	public void setEndExchangeRate(Double endExchangeRate) {
		if(endExchangeRate!=null){			
			this.endExchangeRate = endExchangeRate;
		}
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getTname() {
		return tname;
	}

	public void setTname(String tname) {
		this.tname = tname;
	}

	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		if(userType!=null){			
			this.userType = userType;
		}
	}

	public String getUserTypeValue() {
		switch (userType) {
		case 1:
			userTypeValue = "8800用户";
			break;
		case 2:
			userTypeValue = "6600用户";
			break;
		default:
			userTypeValue = "普通用户";
			break;
		}
		return userTypeValue;
	}

	public void setUserTypeValue(String userTypeValue) {
		this.userTypeValue = userTypeValue;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public Double getLeverMoney() {
		return leverMoney;
	}

	public void setLeverMoney(Double leverMoney) {
		if(leverMoney!=null){			
			this.leverMoney = leverMoney;
		}
	}

	public Double getAppendLeverMoney() {
		return appendLeverMoney;
	}

	public void setAppendLeverMoney(Double appendLeverMoney) {
		if(appendLeverMoney!=null){
			this.appendLeverMoney = appendLeverMoney;			
		}
	}

	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		if(money!=null){			
			this.money = money;
		}
	}

	public Double getTotalLeverMoney() {
		return totalLeverMoney;
	}

	public void setTotalLeverMoney(Double totalLeverMoney) {
		if(totalLeverMoney!=null){			
			this.totalLeverMoney = totalLeverMoney;
		}
	}

	public Double getWarning() {
		return warning;
	}

	public void setWarning(Double warning) {
		if(warning!=null){			
			this.warning = warning;
		}
	}

	public Double getOpen() {
		return open;
	}

	public void setOpen(Double open) {
		if(open!=null){			
			this.open = open;
		}
	}

	public Integer getLever() {
		return lever;
	}

	public void setLever(Integer lever) {
		if(lever!=null){			
			this.lever = lever;
		}
	}

	public Integer getStartdays() {
		return startdays;
	}

	public void setStartdays(Integer startdays) {
		if(startdays!=null){			
			this.startdays = startdays;
		}
	}

	public Long getTradingDays() {
		return tradingDays;
	}

	public void setTradingDays(Long tradingDays) {
		if(tradingDays!=null){			
			this.tradingDays = tradingDays;
		}
	}

	public Double getAccrual() {
		return accrual;
	}

	public void setAccrual(Double accrual) {
			this.accrual = accrual;
	}

	public Double getInvestAccrual() {
		if (!ObjectUtil.equals(null,this.endExchangeRate) 
				&& this.endExchangeRate !=0 ){
			investAccrual = BigDecimalUtils.divRound(this.accrual, endExchangeRate);			
		}
		return investAccrual;
	}

	public void setInvestAccrual(Double investAccrual) {
		if(investAccrual!=null){			
			this.investAccrual = investAccrual;
		}
	}

	public Short getStatus() {
		return status;
	}

	public void setStatus(Short status) {
		if(status!=null){			
			this.status = status;
		}
	}

	public String getStatusValue() {
		switch (status) {
		case 0:
			statusValue = "验资中";
			break;
		case 1:
			statusValue = "操盘中";
			break;
		case 2:
			statusValue = "方案终结";
			break;
		default:
			break;
		}
		return statusValue;
	}

	public void setStatusValue(String statusValue) {
		this.statusValue = statusValue;
	}

	public Integer getTradeChannel() {
		return tradeChannel;
	}

	public void setTradeChannel(Integer tradeChannel) {
		if(tradeChannel!=null){			
			this.tradeChannel = tradeChannel;
		}
	}

	public String getTradeChannelValue() {
		switch (tradeChannel) {
		case 0:
			tradeChannelValue = "TTS";
			break;
		default:
			break;
		}
		return tradeChannelValue;
	}

	public void setTradeChannelValue(String tradeChannelValue) {
		this.tradeChannelValue = tradeChannelValue;
	}

	public BigInteger getAddtime() {
		return addtime;
	}

	public void setAddtime(BigInteger addtime) {
		this.addtime = addtime;
	}

	public String getAddTimeValue() {
		if (addtime != null) {
			addTimeValue = Dates.format(Dates.parseLong2Date(addtime.longValue()));
		}
		return addTimeValue;
	}

	public void setAddTimeValue(String addTimeValue) {
		this.addTimeValue = addTimeValue;
	}

	public BigInteger getStarttime() {
		return starttime;
	}

	public void setStarttime(BigInteger starttime) {
		this.starttime = starttime;
	}

	public String getStartTimeValue() {
		if (starttime != null) {
			startTimeValue = Dates.format(Dates.parseLong2Date(starttime.longValue()), Dates.CHINESE_DATE_FORMAT_LINE);
		}
		return startTimeValue;
	}

	public void setStartTimeValue(String startTimeValue) {
		this.startTimeValue = startTimeValue;
	}

	public BigInteger getEndtime() {
		return endtime;
	}

	public void setEndtime(BigInteger endtime) {
		this.endtime = endtime;
	}

	public String getEndTimeValue() {
		if (endtime != null) {
			endTimeValue = Dates.format(Dates.parseLong2Date(endtime.longValue()));
		}
		return endTimeValue;
	}

	public void setEndTimeValue(String endTimeValue) {
		this.endTimeValue = endTimeValue;
	}

	public BigInteger getEstimateEndtime() {
		return estimateEndtime;
	}

	public void setEstimateEndtime(BigInteger estimateEndtime) {
		this.estimateEndtime = estimateEndtime;
	}

	public String getEstimateEndTimeValue() {
		if (estimateEndtime != null) {
			estimateEndTimeValue = Dates.format(Dates.parseLong2Date(estimateEndtime.longValue()), Dates.CHINESE_DATE_FORMAT_LINE);
		}
		return estimateEndTimeValue;
	}

	public void setEstimateEndTimeValue(String estimateEndTimeValue) {
		this.estimateEndTimeValue = estimateEndTimeValue;
	}

	public String getProgramNo() {
		return programNo;
	}

	public void setProgramNo(String programNo) {
		this.programNo = programNo;
	}

	public Double getFeeDay() {
		return feeDay;
	}

	public void setFeeDay(Double feeDay) {
		if(feeDay!=null){			
			this.feeDay = feeDay;
		}
	}

	public Double getApr() {
		return apr;
	}

	public void setApr(Double apr) {
		if(apr!=null){		
			this.apr = apr;
		}
	}

	public Integer getActivityType() {
		return activityType;
	}

	public void setActivityType(Integer activityType) {
		this.activityType = activityType;
	}
	

}

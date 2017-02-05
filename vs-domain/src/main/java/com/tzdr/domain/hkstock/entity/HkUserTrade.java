package com.tzdr.domain.hkstock.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.tzdr.common.domain.BaseEntity;
import com.tzdr.domain.web.entity.WUser;


/**
 * 港股配资方案
 * @author zhouchen
 * 2015年10月16日 上午11:00:37
 */
@Entity
@Table(name="hk_user_trade")
public class HkUserTrade  extends BaseEntity {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 *  方案 类型预留
	 */ 
	private int type=0;
	
	/**
	*帐户管理费收取方式：1下个交易日，0本交易日（立刻生效）
	*/	
	private Short tradeStart;
	/**
	*(配资金额)公司出的钱
	*/
	private Double money=0.0;
	/**
	 * 首次总利息
	*/
	private Double apr=0.0;
	/**
	 * 结算金额=终结方案后总金额
	*/
	private Double finishedMoney=0.0;
	/**
	 * 赢亏多少(+-)（在配资过程中是0,终结方案后会记录本次的赢亏）
	*/
	private Double accrual=0.0;
	/**
	 * 亏损警告线,亏损补仓线
	*/
	private Double warning=0.0;
	/**
	 * 亏损平仓线
	*/
	private Double open=0.0;

	/**
	 *  放大倍数，杠杆
	*/
	private Integer lever;
	/**
	 * 风险保证金(配资保证金)用户出的钱
	*/
	private Double leverMoney=0.0;
	/**
	 * 配资创建时间（和开始交易时间不同）
	*/
	private Long addtime;
	/**
	 * 配资终结时间
	*/
	private Long endtime;
	/**
	 * 配资开始交易时间
	*/
	private Long starttime;
	/**
	 * 配资交易天数（交易日）
	*/
	private Integer startdays;
	/**
	 * 审核通过时间
	*/
	private Long auditime;
	
	/**
	 * 预计交易结束时间
	 */
	private Long estimateEndtime;
	
	/**
	 * 预计自然结束天数(算利息)
	 */
	private Long  naturalDays;
	

	/**
	 * 天息（管理费）
	 */
	private Double feeDay=0.0;

	/**
	 * 月息（利息）一天的利息
	 */
	private Double feeMonth=0.0;
	
	/**
	 * 交易账户名	
	 */
	private String accountName;
	/**
	 *账户密码
	 */
	private String password;
	
	/**
	 * 交易帐号
	 */
	private String accountNo;
	
	/**
	 * 0：验资中，1操盘中，2方案结束（终结）
	 */
	private Short status=0;
	
	

	/**
	 * 追加保证金
	 */
	private Double appendLeverMoney=0.0;
	
	/**
	 * 母账户的id
	 */
	private String parentAccountId;
	
	
	/**
	 * 总操盘资金
	 */
	private Double totalLeverMoney=0.0;
	
	/**
	 * 配资组合号
	 */
	private String groupId;
	
	/**
	 * 方案号随机8位
	 */
	private String programNo;
	

	/**
	 * 保险单号
	 */
	private String policyNo;
	
	/**
	 * 港元汇率（申请方案时计算的汇率
	 */
	private Double applyExchangeRate=0.0;
	
	/**
	 * 港元汇率（方案终结时计算的汇率）
	 */
	private Double endExchangeRate=0.0;
	
	@ManyToOne(optional = true, fetch = FetchType.EAGER)
	@Fetch(FetchMode.SELECT)               
	@JoinColumn(name="uid", nullable=true)
	private WUser wuser;

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Short getTradeStart() {
		return tradeStart;
	}

	public void setTradeStart(Short tradeStart) {
		this.tradeStart = tradeStart;
	}

	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	public Double getApr() {
		return apr;
	}

	public void setApr(Double apr) {
		this.apr = apr;
	}

	public Double getFinishedMoney() {
		return finishedMoney;
	}

	public void setFinishedMoney(Double finishedMoney) {
		this.finishedMoney = finishedMoney;
	}

	public Double getAccrual() {
		return accrual;
	}

	public void setAccrual(Double accrual) {
		this.accrual = accrual;
	}

	public Double getWarning() {
		return warning;
	}

	public void setWarning(Double warning) {
		this.warning = warning;
	}

	public Double getOpen() {
		return open;
	}

	public void setOpen(Double open) {
		this.open = open;
	}

	public Integer getLever() {
		return lever;
	}

	public void setLever(Integer lever) {
		this.lever = lever;
	}

	public Double getLeverMoney() {
		return leverMoney;
	}

	public void setLeverMoney(Double leverMoney) {
		this.leverMoney = leverMoney;
	}

	public Long getAddtime() {
		return addtime;
	}

	public void setAddtime(Long addtime) {
		this.addtime = addtime;
	}

	public Long getEndtime() {
		return endtime;
	}

	public void setEndtime(Long endtime) {
		this.endtime = endtime;
	}

	public Long getStarttime() {
		return starttime;
	}

	public void setStarttime(Long starttime) {
		this.starttime = starttime;
	}

	public Integer getStartdays() {
		return startdays;
	}

	public void setStartdays(Integer startdays) {
		this.startdays = startdays;
	}

	public Long getAuditime() {
		return auditime;
	}

	public void setAuditime(Long auditime) {
		this.auditime = auditime;
	}

	public Long getEstimateEndtime() {
		return estimateEndtime;
	}

	public void setEstimateEndtime(Long estimateEndtime) {
		this.estimateEndtime = estimateEndtime;
	}

	public Long getNaturalDays() {
		return naturalDays;
	}

	public void setNaturalDays(Long naturalDays) {
		this.naturalDays = naturalDays;
	}

	public Double getFeeDay() {
		return feeDay;
	}

	public void setFeeDay(Double feeDay) {
		this.feeDay = feeDay;
	}

	public Double getFeeMonth() {
		return feeMonth;
	}

	public void setFeeMonth(Double feeMonth) {
		this.feeMonth = feeMonth;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public Short getStatus() {
		return status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	public Double getAppendLeverMoney() {
		return appendLeverMoney;
	}

	public void setAppendLeverMoney(Double appendLeverMoney) {
		this.appendLeverMoney = appendLeverMoney;
	}

	public String getParentAccountId() {
		return parentAccountId;
	}

	public void setParentAccountId(String parentAccountId) {
		this.parentAccountId = parentAccountId;
	}

	public Double getTotalLeverMoney() {
		return totalLeverMoney;
	}

	public void setTotalLeverMoney(Double totalLeverMoney) {
		this.totalLeverMoney = totalLeverMoney;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getProgramNo() {
		return programNo;
	}

	public void setProgramNo(String programNo) {
		this.programNo = programNo;
	}

	public String getPolicyNo() {
		return policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	public Double getApplyExchangeRate() {
		return applyExchangeRate;
	}

	public void setApplyExchangeRate(Double applyExchangeRate) {
		this.applyExchangeRate = applyExchangeRate;
	}

	public Double getEndExchangeRate() {
		return endExchangeRate;
	}

	public void setEndExchangeRate(Double endExchangeRate) {
		this.endExchangeRate = endExchangeRate;
	}

	public WUser getWuser() {
		return wuser;
	}

	public void setWuser(WUser wuser) {
		this.wuser = wuser;
	}
	
	
}
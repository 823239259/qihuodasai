package com.tzdr.domain.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.MathContext;

import com.tzdr.common.utils.AllowExcel;
import com.tzdr.common.utils.SqlColumn;
import com.tzdr.common.utils.SqlOrder;
import com.tzdr.common.utils.TypeConvert;
import com.tzdr.domain.cache.CacheManager;
import com.tzdr.domain.cache.DataDicKeyConstants;

/**
 * 
 * <p></p>
 * @author LiuQing
 * @see
 * @version 2.0
 * 2015年3月20日 下午2:26:38
 */
public class EarningsVo implements Serializable {
	
	private static final long serialVersionUID = -8591246450899980591L;
	//达人用户名
	@SqlColumn
	@AllowExcel(name="用户名")
	private String mobile;
	//w.mobile '达人用户名',
	//v.tname '实名',
	@SqlColumn
	@SqlOrder
	@AllowExcel(name="实名")
	private String tname;
	//t.account '恒生账号',
	@SqlColumn
	@AllowExcel(name="恒生账户")
	private String account;
	//t.hs_belong_broker '所属券商户'
	@SqlColumn
	@AllowExcel(name="所属券商户")
	private String hsBelongBroker;
	//,t.program_no '方案号',
	@SqlColumn
	@AllowExcel(name="方案号")
	private String groupId;
	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	private String programNo;
	//(t.lever_money + t.append_lever_money) '保证金',
	@SqlColumn
	@AllowExcel(name="保证金")
	private Double leverMoney;
	
	//t.lever '倍数'
	@SqlColumn
	@AllowExcel(name="倍数")
	private Integer lever;
	//#,FROM_UNIXTIME(t.starttime, '%Y%m%d') '开始时间'
	//@SqlColumn
	//,date_format(NOW(),'%Y%m%d') '当前时间'
	//,(TO_DAYS(NOW()) - TO_DAYS(FROM_UNIXTIME(t.starttime, '%Y%m%d')) + 1) '操盘天数',
	@SqlColumn
	@AllowExcel(name="天数")
	private Integer opDay;
	//'管理费',
	@SqlColumn
	@AllowExcel(name="管理费")
	private BigDecimal managerMoney;
	
	//'配资管理费撤回',
	@SqlColumn
	@AllowExcel(name="配资管理费撤回")
	private BigDecimal revokeManagerMoney;
		
		
	
	@SqlColumn(name="interest")
	@AllowExcel(name="应收利息")
	private BigDecimal interestMoney;
	
	@SqlColumn
	@AllowExcel(name="配资利息撤回")
	private BigDecimal revokeInterest;
	
	
	@AllowExcel(name="抵扣金额")
	@SqlColumn
	private BigDecimal deductMoney;
	
	@AllowExcel(name="实收利息")
	private BigDecimal realIncomeMoney;
	
	//) AS DECIMAL(10,2)) '代收利息',
	@SqlColumn
	//@AllowExcel(name="返利")
	private BigDecimal backMoney;
	
	@SqlColumn
	@AllowExcel(name="佣金差")
	private BigDecimal freemoney;
	
	@SqlColumn
	@AllowExcel(name="过户费差")
	private BigDecimal transmoney;
	
	@SqlColumn
	@AllowExcel(name="盈利分成")
	private double profitMoney;
	
	public double getProfitMoney() {
		return profitMoney;
	}

	public void setProfitMoney(double profitMoney) {
		this.profitMoney = profitMoney;
	}
	
	@SqlColumn
	@AllowExcel(name="收益小计")
	private BigDecimal totalmoney;
	
	/**
	 * 活动类型 0：没有活动，1：8800活动
	 */
	@SqlColumn
	private int activityType;
	
	public int getActivityType() {
		return activityType;
	}

	public void setActivityType(int activityType) {
		this.activityType = activityType;
	}

	/**
	 * 方案类型
	 */
	@AllowExcel(name="方案类型")
	private String activityTypeStr;

	public String getActivityTypeStr() {
		return CacheManager.getDataMapByKey(DataDicKeyConstants.ACTIVITY_TYPE,String.valueOf(this.activityType));
	}

	public void setActivityTypeStr(String activityTypeStr) {
		this.activityTypeStr = activityTypeStr;
	}
	
	public BigDecimal getTransmoney() {
		this.transmoney=TypeConvert.scale(this.transmoney,TypeConvert.SCALE_VALUE);
		return transmoney;
	}

	public void setTransmoney(BigDecimal transmoney) {
		this.transmoney = transmoney;
	}

	public BigDecimal getTotalmoney() {
		this.totalmoney=TypeConvert.scale(this.totalmoney,TypeConvert.SCALE_VALUE);
		return totalmoney;
	}

	public void setTotalmoney(BigDecimal totalmoney) {
		this.totalmoney = totalmoney;
	}

	public BigDecimal getFreemoney() {
		this.freemoney=TypeConvert.scale(this.freemoney,TypeConvert.SCALE_VALUE);
		return freemoney;
	}

	public BigDecimal getDeductMoney() {
		return deductMoney;
	}

	public void setDeductMoney(BigDecimal deductMoney) {
		this.deductMoney = deductMoney;
	}

	public void setFreemoney(BigDecimal freemoney) {
		this.freemoney = freemoney;
	}

	public BigDecimal getRealIncomeMoney() {
		if (this.interestMoney != null && this.deductMoney != null) {
			this.realIncomeMoney = this.interestMoney.subtract(this.deductMoney, MathContext.DECIMAL32);
			this.realIncomeMoney = TypeConvert.scale(this.realIncomeMoney, TypeConvert.SCALE_VALUE);
		}
		return realIncomeMoney;
	}

	public void setRealIncomeMoney(BigDecimal realIncomeMoney) {
		this.realIncomeMoney = realIncomeMoney;
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

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getHsBelongBroker() {
		return hsBelongBroker;
	}

	public void setHsBelongBroker(String hsBelongBroker) {
		this.hsBelongBroker = hsBelongBroker;
	}

	public String getProgramNo() {
		return programNo;
	}

	public void setProgramNo(String programNo) {
		this.programNo = programNo;
	}

	public Double getLeverMoney() {
		if (this.leverMoney != null) {
			BigDecimal leverMoneyBig = 
					TypeConvert.scale(new BigDecimal(this.leverMoney), TypeConvert.SCALE_VALUE);
			if (leverMoneyBig != null) {
				this.leverMoney = leverMoneyBig.doubleValue();
			}
		}
		return leverMoney;
	}

	public void setLeverMoney(Double leverMoney) {
		this.leverMoney = leverMoney;
	}

	public Integer getLever() {
		return lever;
	}

	public void setLever(Integer lever) {
		this.lever = lever;
	}

	public Integer getOpDay() {
		return opDay;
	}

	public void setOpDay(Integer opDay) {
		this.opDay = opDay;
	}

	public BigDecimal getManagerMoney() {
		this.managerMoney = TypeConvert.scale(this.managerMoney, TypeConvert.SCALE_VALUE);
		return managerMoney;
	}

	public void setManagerMoney(BigDecimal managerMoney) {
		this.managerMoney = managerMoney;
	}

	public BigDecimal getInterestMoney() {
		this.interestMoney=TypeConvert.scale(this.interestMoney, TypeConvert.SCALE_VALUE);
		return interestMoney;
	}

	public void setInterestMoney(BigDecimal interestMoney) {
		this.interestMoney = interestMoney;
	}

	public BigDecimal getBackMoney() {
		this.backMoney=TypeConvert.scale(this.backMoney,TypeConvert.SCALE_VALUE);
		return backMoney;
	}

	public void setBackMoney(BigDecimal backMoney) {
		this.backMoney = backMoney;
	}

	public BigDecimal getRevokeManagerMoney() {
		return revokeManagerMoney;
	}

	public void setRevokeManagerMoney(BigDecimal revokeManagerMoney) {
		this.revokeManagerMoney = revokeManagerMoney;
	}

	public BigDecimal getRevokeInterest() {
		return revokeInterest;
	}

	public void setRevokeInterest(BigDecimal revokeInterest) {
		this.revokeInterest = revokeInterest;
	}
	
	

}

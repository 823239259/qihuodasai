package com.tzdr.domain.api.vo;

import java.io.Serializable;
import java.math.BigInteger;

/**
 * user fund vo
 * @author zhouchen
 * 2015年5月26日 上午9:57:05
 */
public class ApiFundVo implements Serializable  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	/**
	 * 支付时间
	 */
	private BigInteger payTime;
	
	
	/**
	 * 支付时间
	 */
	private BigInteger subTime;
	
	
	/**
	 * 类型：1:充值,2:提现,,3:系统调账,4:系统冲账,5:投标冻结,6:投标成功,10:配资支出,11:利息支出 ,
	 * 12：扣取管理费（新增），13佣金收入，15配资撤回,16利润提取17 追加保证金、18配资欠费、19活动赠送、20活动收回、21补偿收入
	 * ,23提现撤回,24 抵扣劵收入
	 */
	private Integer type;
	
	/**
	 * 金额
	 */
	private Double money;
	
	
	/**
	 * 0:未支付 1:已支付
	 */
	private int payStatus;


	public BigInteger getPayTime() {
		return payTime;
	}


	public void setPayTime(BigInteger payTime) {
		this.payTime = payTime;
	}


	public BigInteger getSubTime() {
		return subTime;
	}


	public void setSubTime(BigInteger subTime) {
		this.subTime = subTime;
	}


	public Integer getType() {
		return type;
	}


	public void setType(Integer type) {
		this.type = type;
	}


	public Double getMoney() {
		return money;
	}


	public void setMoney(Double money) {
		this.money = money;
	}


	public int getPayStatus() {
		return payStatus;
	}


	public void setPayStatus(int payStatus) {
		this.payStatus = payStatus;
	}
	
	
	
	
}

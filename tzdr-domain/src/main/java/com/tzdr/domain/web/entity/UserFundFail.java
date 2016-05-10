package com.tzdr.domain.web.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.tzdr.common.domain.BaseEntity;

@Entity
@Table(name="w_user_fund_fail")
public class UserFundFail  extends BaseEntity {


	/**
	 * 
	 */ 
	private static final long serialVersionUID = 1L;

	private String uid;

	/**
	 * 类型：1:充值,2:提现,,3:系统调账,4:系统冲账,5:投标冻结,6:投标成功,10:配资支出,11:利息支出 ,
	 * 12：扣取管理费（新增），13佣金收入，15配资撤回,16利润提取17 追加保证金、18配资欠费、19活动赠送、20活动收回、21补偿收入
	 * ,23提现撤回,24 抵扣劵收入 25:配资管理费撤回  26:配资利息撤回 27:补仓欠费
	 */
	private Integer type;
	
	/**
	 * 交易金额 
	 */
	private double money;
	
	private Long addtime;

	/**
	 *支付时间
	 */
	private Long uptime;
	
	
	private String remark;
	
	/**
	 * 0:未支付 1:已支付
	 */
	private short payStatus;
	
	/**
	 * type:27【补仓欠费】时，此字段表示补仓明细拆分 【 0：否 ， 1：是】  type:13【佣金收入】时，彼字段表示佣金明细异常【空：正常，1：手工执行】
	 */
	private Integer typeStatus;
	
	/**
	 * 处理状态，0：未处理  1：已处理
	 */
	private short runStatus = (short)0;
	

	@Column(name="uid", length=32)
	public String getUid() {
		return this.uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}
	
	@Column(name="type", nullable=false)
	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Column(name="money", nullable=false)
	public double getMoney() {
		return this.money;
	}

	public void setMoney(double money) {
		this.money = money;
	}


	@Column(name="addtime", nullable=false)
	public Long getAddtime() {
		return this.addtime;
	}

	public void setAddtime(Long addtime) {
		this.addtime = addtime;
	}

	@Column(name="uptime", nullable=false)
	public Long getUptime() {
		return this.uptime;
	}

	public void setUptime(Long uptime) {
		this.uptime = uptime;
	}

	@Column(name="remark", nullable=false)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public short getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(short payStatus) {
		this.payStatus = payStatus;
	}

	public Integer getTypeStatus() {
		return typeStatus;
	}

	public void setTypeStatus(Integer typeStatus) {
		this.typeStatus = typeStatus;
	}

	public short getRunStatus() {
		return runStatus;
	}

	public void setRunStatus(short runStatus) {
		this.runStatus = runStatus;
	}
}
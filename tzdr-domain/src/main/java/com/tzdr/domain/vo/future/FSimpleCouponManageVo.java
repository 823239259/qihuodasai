package com.tzdr.domain.vo.future;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * 期货优惠券管理Vo
 * 类说明
 * @author  zhaozhao
 * @date    2016年3月1日下午3:59:19
 * @version 1.0
 */
public class FSimpleCouponManageVo implements Serializable{
  
	private static final long serialVersionUID = 3250514979991211700L;
	
	
	private String id;
	/**
	 * 优惠券名称
	 */
	
	private String name;

	/**
	 * 优惠券类型：1-现金红包，2-代金券，3-折扣券，4-实物，5-优惠券
	 */
	
	private Short type;

	/**
	 * 使用范围：存储国际期货的业务ID，多个使用逗号分隔，如：0,1,2
	 */
	
	private String scope;

	/**
	 * 优惠券面值：类型为现金红包和代金券时，面值单位为元，类型为折扣券时，面值单位为折，并且数值不能大于10；
	 */
	
	private BigDecimal money;

	/**
	 * 使用周期：单位天
	 */
	
	private Integer cycle;

	/**
	 * 截止日期
	 */
	
	private BigInteger deadline;

	/**
	 * 个数
	 */
	
	private BigInteger numToHave;
	
	/**
	 * 已发放个数
	 */
	
	private BigInteger numToLost;
	
	/**
	 * 创建人
	 */
	
	private String createUser;
	
	/**
	 * 创建时间
	 */
	
	  private  BigInteger  createTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Short getType() {
		return type;
	}

	public void setType(Short type) {
		this.type = type;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public BigDecimal getMoney() {
		return money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}

	public Integer getCycle() {
		return cycle;
	}

	public void setCycle(Integer cycle) {
		this.cycle = cycle;
	}

	public BigInteger getDeadline() {
		return deadline;
	}

	public void setDeadline(BigInteger deadline) {
		this.deadline =deadline;
	}

	public BigInteger getNumToHave() {
		return numToHave;
	}

	public void setNumToHave(BigInteger numToHave) {
		this.numToHave = numToHave;
	}

	public BigInteger getNumToLost() {
		return numToLost;
	}

	public void setNumToLost(BigInteger numToLost) {
		this.numToLost = numToLost;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public BigInteger getCreateTime() {
		return createTime;
	}

	public void setCreateTime(BigInteger createTime) {
		this.createTime = createTime;
	}
	
}

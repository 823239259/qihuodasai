package com.tzdr.domain.vo.future;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;


public class FSimpleCouponUseVo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3248165428953921490L;

	private String id;
	/**
	 * 优惠券名称
	 */
	private String name;

	/**
	 * 优惠券类型：1-现金红包，2-代金券，3-折扣券，4-实物，5-优惠券
	 */
	private int type;

	/**
	 * 使用范围：存储国际期货的业务ID 【0.富时A50  （1.沪金     2.沪银   3.沪铜   4.橡胶）=5.商品期货  6.原油    7. 恒指  8.国际综合】
	 * 多个使用逗号分隔，如：0,5,6,7,8
	 */
	private String scope;

	/**
	 * 优惠券面值：类型为现金红包和代金券时，面值单位为元，类型为折扣券时，面值单位为折，并且数值不能大于10；
	 */
	private BigDecimal money;

	/**
	 * 创建时间
	 */
	private BigInteger createTime;
	/**
	 * 发放和使用状态：1-未发放，2-已发放，3-已使用；4.已过期  默认为1未发放
	 */
	private String status;

	/**
	 * 发放时间
	 */
	private BigInteger grantTime;

	/**
	 * 使用时间
	 */
	private BigInteger useTime;

	/**
	 * 使用人ID
	 */
	private String userId;

	/**
	 * 使用人姓名
	 */
	private String userName;

	/**
	 * 使用人电话号码
	 */
	private String userPhone;

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

	public int getType() {
		return type;
	}

	public void setType(int type) {
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public BigInteger getGrantTime() {
		return grantTime;
	}

	public void setGrantTime(BigInteger grantTime) {
		this.grantTime = grantTime;
	}

	public BigInteger getUseTime() {
		return useTime;
	}

	public void setUseTime(BigInteger useTime) {
		this.useTime = useTime;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	public BigInteger getCreateTime() {
		return createTime;
	}

	public void setCreateTime(BigInteger createTime) {
		this.createTime = createTime;
	}
	
}

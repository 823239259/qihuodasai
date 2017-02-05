/**
 * 
 */
package com.tzdr.domain.web.entity.future;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.tzdr.common.domain.BaseCrudEntity;

/**
 * <B>说明: 期货优惠券 </B>TODO
 * 
 * @chen.ding 2016年3月1日
 */
@Entity
@Table(name = "f_simple_coupon")
public class FSimpleCoupon extends BaseCrudEntity {

	private static final long serialVersionUID = 3358002908136317892L;

	/**
	 * 优惠券名称
	 */
	@Column(name = "name", nullable = false, length = 512)
	private String name;

	/**
	 * 优惠券类型：1-现金红包，2-代金券，3-折扣券，4-实物 ，5- ，6-抵扣卷
	 */
	@Column
	private Short type;

	/**
	 * 使用范围：存储国际期货的业务ID 【0.富时A50  （1.沪金     2.沪银   3.沪铜   4.橡胶）=5.商品期货  6.原油    7. 恒指  8.国际综合，5，商品综合，10港股，11 A股 12 股票合买 13 小恒指】
	 * 多个使用逗号分隔，如：0,5,6,7,8
	 */
	@Column
	private String scope;

	/**
	 * 优惠券面值：类型为现金红包和代金券时，面值单位为元，类型为折扣券时，面值单位为折，并且数值不能大于10；
	 */
	@Column
	private BigDecimal money;

	/**
	 * 使用周期：单位天
	 */
	@Column
	private Integer cycle;

	/**
	 * 截止日期
	 */
	@Column
	private Long deadline;

	/**
	 * 发放和使用状态：1-未发放，2-已发放，3-已使用；默认为1未发放
	 */
	@Column
	private Short status = 1;

	/**
	 * 发放时间
	 */
	@Column
	private Long grantTime;

	/**
	 * 使用时间
	 */
	@Column
	private Long useTime;

	/**
	 * 使用人ID
	 */
	@Column
	private String userId;

	/**
	 * 使用平台
	 */
	private String platform;
	/**
	 * 使用人姓名
	 */
	@Column
	private String userName;

	/**
	 * 使用人电话号码
	 */
	@Column
	private String userPhone;

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

	public Long getDeadline() {
		return deadline;
	}

	public void setDeadline(Long deadline) {
		this.deadline = deadline;
	}

	public Short getStatus() {
		return status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	public Long getGrantTime() {
		return grantTime;
	}

	public void setGrantTime(Long grantTime) {
		this.grantTime = grantTime;
	}

	public Long getUseTime() {
		return useTime;
	}

	public void setUseTime(Long useTime) {
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

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}
}

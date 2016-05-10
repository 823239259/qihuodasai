package com.tzdr.domain.app.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import com.tzdr.common.utils.Dates;
import com.tzdr.common.utils.SqlColumn;

/**
* @Title: FSimpleCouponWebVo.java     
* @Description: web我的优惠劵VO   
* @author： WangPinQun 
* @E-mail：wangpinqun@tzdr.com
* @company： 上海信闳投资管理有限公司重庆分公司
* @address：重庆市渝北区黄山大道中段70号两江新界3栋13楼
* @date： 2016年3月7日 上午11:43:38    
* @version： V1.0
 */
public class UserCouponVo implements Serializable{
  
	private static final long serialVersionUID = 3250514979991211700L;
	
	/**
	 * id
	 */
	@SqlColumn(name="id")
	private String id;
	
	/**
	 * 优惠券类型：1-现金红包，2-代金券，3-折扣券
	 */
	@SqlColumn(name="type")
	private Short type;

	/**
	 * 优惠券面值：类型为现金红包和代金券时，面值单位为元，类型为折扣券时，面值单位为折，并且数值不能大于10；
	 */
	@SqlColumn(name="money")
	private BigDecimal money;

	/**
	 * 截止日期
	 */
	@SqlColumn(name="deadline")
	private Long deadline;

	/**
	 * 发放时间
	 */
	@SqlColumn(name="grant_time")
	private  Long grantTime;
	
	/**
	 * 优惠券名称
	 */
	@SqlColumn(name="name")
	private String name;
	
	/**
	 * 发放和使用状态：2-已发放，3-已使用,4已过期
	 */
	@SqlColumn(name="status")
	private Short status;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Short getType() {
		return type;
	}

	public void setType(Short type) {
		this.type = type;
	}

	public BigDecimal getMoney() {
		return money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}

	public Long getDeadline() {
		return deadline;
	}

	public void setDeadline(Long deadline) {
		this.deadline = deadline;
	}

	public Long getGrantTime() {
		return grantTime;
	}

	public void setGrantTime(Long grantTime) {
		this.grantTime = grantTime;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Short getStatus() {
		
		if(this.status == 2 ){  //2-已发放，3-已使用,4已过期
			this.status = Dates.getCurrentLongDate() > this.deadline ? 4 :this.status;
		}
		return status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}
}

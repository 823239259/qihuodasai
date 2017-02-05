package com.tzdr.domain.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import com.tzdr.common.utils.SqlColumn;

/**
 * 
 * <p></p>
 * @author QingLiu
 * @see 方案终结计费VO
 * @version 2.0
 * 2015年1月16日下午1:40:52
 */
public class BillingFeeVo implements Serializable {

	private static final long serialVersionUID = -6141311311163326085L;
	
	//1:充值,2:提现,,3:人工充值,4:人工扣款,5:投标冻结,6:投标成功,10:配资支出,11:利息支出 ,12：扣取管理费（新增）
	@SqlColumn
	private String groupId;
	//类型
	@SqlColumn
	private Integer type;
	@SqlColumn
	private Double money;//合计多少

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
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
	

}

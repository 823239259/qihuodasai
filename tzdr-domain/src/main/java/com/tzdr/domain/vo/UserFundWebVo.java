package com.tzdr.domain.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import com.tzdr.common.domain.BaseEntity;
import com.tzdr.common.utils.BigDecimalUtils;
import com.tzdr.common.utils.SqlColumn;
import com.tzdr.common.utils.TypeConvert;
import com.tzdr.domain.cache.CacheManager;

/**
 * 
 * 
 * <p></p>
 * @author LiuQing
 * @see 
 * @version 2.0
 * 2015年4月17日下午5:37:11
 */
public class UserFundWebVo  implements Serializable {


	private static final long serialVersionUID = -7287347991436320559L;
	
	@SqlColumn
	private String uid;

	@SqlColumn
	private String mobile;
	
	@SqlColumn
	private String tname;
	
	@SqlColumn
	private Integer addtime;
	
	private String addtimeStr;
	
	@SqlColumn
	private Integer type;
	
	private String typeStr;
	
	@SqlColumn
	private Double money;
	
	@SqlColumn
	private String remark;
	
	private String moneyStr;
	
	private String totalAmount;
	
	

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getTotalAmount() {
		if (totalAmount != null && !"".equals(totalAmount) ) {
			totalAmount = BigDecimalUtils.round2(Double.valueOf(totalAmount), 2)+"";
		}
		return totalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getAddtime() {
		return addtime;
	}

	public void setAddtime(Integer addtime) {
		this.addtime = addtime;
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
	
	public String getMobile() {
		if (mobile == null) {
			this.mobile = "";
		}
		else {
			this.mobile = TypeConvert.showPasswordText(this.mobile, 3,7, '*');
		}
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getTname() {
		if (this.tname == null) {
			this.tname = "";
		}
		else {
			this.tname = TypeConvert.showPasswordText(tname);
		}
		return tname;
	}

	public void setTname(String tname) {
		this.tname = tname;
	}

	public String getAddtimeStr() {
		if (this.addtime != null) {
			this.addtimeStr = TypeConvert.long1000ToDatetimeStr(this.addtime.longValue());
		}
		return addtimeStr;
	}

	public void setAddtimeStr(String addtimeStr) {
		this.addtimeStr = addtimeStr;
	}

	public String getTypeStr() {
		if (this.type != null) {
			this.typeStr = CacheManager.getDataMapByKey("userfundType", this.type + "");
		}
		return typeStr;
	}

	public void setTypeStr(String typeStr) {
		this.typeStr = typeStr;
	}

	public String getMoneyStr() {
		if (this.money != null) {
			Double value = BigDecimalUtils.round2(this.money, 2);
			if (value != null) {
				this.moneyStr = value.toString();
			}
		}
		return moneyStr;
	}

	public void setMoneyStr(String moneyStr) {
		this.moneyStr = moneyStr;
	}
	

}
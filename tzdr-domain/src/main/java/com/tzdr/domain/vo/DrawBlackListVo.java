package com.tzdr.domain.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import com.tzdr.common.utils.AllowExcel;
import com.tzdr.common.utils.SqlColumn;
import com.tzdr.common.utils.TypeConvert;

/**
 * 提现黑名单vo
 * <P>title:@DrawBlackListVo.java</p>																								
 * <P>Description：</p>
 * <p>Copyright: Copyright (c) 2015 tzdr</p>
 * <p>Company: 上海信宏</p>
 * History：
 * @author:zhangjun
 * @date 2015年5月22日
 * @version 1.0
 */
public class DrawBlackListVo implements Serializable {
	@SqlColumn
	private String id;
	
	@AllowExcel(name="用户姓名")
	@SqlColumn
	private String tname;
	
	@AllowExcel(name="手机号")
	@SqlColumn
	private String mobile;
	
	@AllowExcel(name="配资金额")
	@SqlColumn
	private Double money;//配资金额
	
	@AllowExcel(name="账户余额")
	@SqlColumn
	private Double avlBal;//余额
	
	@AllowExcel(name="冻结金额")
	@SqlColumn
	private Double frzBal;//冻结金额
	
	@AllowExcel(name="欠款金额")
	@SqlColumn
	private Double debts;//欠款金额
	
	@AllowExcel(name="创建时间")
	@SqlColumn
	private String createtime;//创建时间
	
	@AllowExcel(name="创建人")
	@SqlColumn
	private String createUser;
	
	@AllowExcel(name="原因")
	@SqlColumn
	private String reason;
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTname() {
		return tname;
	}
	public void setTname(String tname) {
		this.tname = tname;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public Double getMoney() {
		if (this.money != null) {
			BigDecimal leverMoneyBig = 
					TypeConvert.scale(new BigDecimal(this.money), TypeConvert.SCALE_VALUE);
			if (leverMoneyBig != null) {
				this.money = leverMoneyBig.doubleValue();
			}
		}
		return money;
		
	}
	public void setMoney(Double money) {
		this.money = money;
	}
	public Double getAvlBal() {
		if (this.avlBal != null) {
			BigDecimal leverMoneyBig = 
					TypeConvert.scale(new BigDecimal(this.avlBal), TypeConvert.SCALE_VALUE);
			if (leverMoneyBig != null) {
				this.avlBal = leverMoneyBig.doubleValue();
			}
		}
		return avlBal;
	}
	public void setAvlBal(Double avlBal) {
		this.avlBal = avlBal;
	}
	public Double getFrzBal() {
		if (this.frzBal != null) {
			BigDecimal leverMoneyBig = 
					TypeConvert.scale(new BigDecimal(this.frzBal), TypeConvert.SCALE_VALUE);
			if (leverMoneyBig != null) {
				this.frzBal = leverMoneyBig.doubleValue();
			}
		}
		return frzBal;
	}
	public void setFrzBal(Double frzBal) {
		this.frzBal = frzBal;
	}
	public Double getDebts() {
		if (this.debts != null) {
			BigDecimal leverMoneyBig = 
					TypeConvert.scale(new BigDecimal(this.debts), TypeConvert.SCALE_VALUE);
			if (leverMoneyBig != null) {
				this.debts = leverMoneyBig.doubleValue();
			}
		}
		return debts;
	}
	public void setDebts(Double debts) {
		this.debts = debts;
	}
	public String getCreatetime() {
		return createtime;
	}
	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}
	
}


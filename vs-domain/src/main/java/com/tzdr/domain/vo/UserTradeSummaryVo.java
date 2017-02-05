package com.tzdr.domain.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import com.tzdr.common.utils.AllowExcel;
import com.tzdr.common.utils.SqlColumn;
import com.tzdr.common.utils.TypeConvert;

/**
 * 配资用户统计
 * @author 张军
 *
 */
public class UserTradeSummaryVo  implements Serializable {

	
	@SqlColumn
	@AllowExcel(name="手机号")
	private String mobile;//手机号
	@SqlColumn
	@AllowExcel(name="姓名")
	private String tname;//姓名
	@SqlColumn
	@AllowExcel(name="累计配资方案数")
	private Integer countProNo;//方案个数
	@SqlColumn
	@AllowExcel(name="操盘中配资方案数")
	private Integer useProNo;//正使用的方案个数
	@SqlColumn
	@AllowExcel(name="累计配资金额 ")
	private Double totalmoney;//配资金额
	@SqlColumn
	@AllowExcel(name="累计配资保证金")
	private Double leverMoney;//配资保证金总和
	@SqlColumn
	@AllowExcel(name="累计追加保证金")
	private Double appendLeverMoney;//追加保证金
	@SqlColumn
	@AllowExcel(name="累计提取利润")
	private Double profit;//利润
	@SqlColumn
	@AllowExcel(name="累计配资天数")
	private Integer startdays;//配资天数
	@SqlColumn
	@AllowExcel(name="累计使用天数")
	private Long usedays;//使用天数
	@SqlColumn
	@AllowExcel(name="最后一次配资时间")
	private String lasttime;//最后一次配资时间
	@SqlColumn
	private Double totalamount;//总配资金额
	@SqlColumn
	private String uid;
	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public Integer getUseProNo() {
		return useProNo;
	}

	public String getLasttime() {
		return lasttime;
	}

	public void setLasttime(String lasttime) {
		this.lasttime = lasttime;
	}

	public void setUseProNo(Integer useProNo) {
		this.useProNo = useProNo;
	}
	public Integer getCountProNo() {
		return countProNo;
	}
	public void setCountProNo(Integer countProNo) {
		this.countProNo = countProNo;
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
	
	public Double getTotalmoney() {
		if (this.totalmoney != null) {
			BigDecimal leverMoneyBig = 
					TypeConvert.scale(new BigDecimal(this.totalmoney), TypeConvert.SCALE_VALUE);
			if (leverMoneyBig != null) {
				this.totalmoney = leverMoneyBig.doubleValue();
			}
		}
		return totalmoney;
	}

	public void setTotalmoney(Double totalmoney) {
		this.totalmoney = totalmoney;
	}

	public Double getAppendLeverMoney() {
		if (this.appendLeverMoney != null) {
			BigDecimal leverMoneyBig = 
					TypeConvert.scale(new BigDecimal(this.appendLeverMoney), TypeConvert.SCALE_VALUE);
			if (leverMoneyBig != null) {
				this.appendLeverMoney = leverMoneyBig.doubleValue();
			}
		}
		return appendLeverMoney;
		
	}
	public void setAppendLeverMoney(Double appendLeverMoney) {
		this.appendLeverMoney = appendLeverMoney;
	}
	public Double getProfit() {
		if (this.profit != null) {
			BigDecimal leverMoneyBig = 
					TypeConvert.scale(new BigDecimal(this.profit), TypeConvert.SCALE_VALUE);
			if (leverMoneyBig != null) {
				this.profit = leverMoneyBig.doubleValue();
			}
		}
		return profit;
		
	}
	public void setProfit(Double profit) {
		this.profit = profit;
	}
	

	public Integer getStartdays() {
		return startdays;
	}

	public void setStartdays(Integer startdays) {
		this.startdays = startdays;
	}

	public Long getUsedays() {
		return usedays;
	}
	public void setUsedays(Long usedays) {
		this.usedays = usedays;
	}
	public Double getTotalamount() {
		if (this.totalamount != null) {
			BigDecimal leverMoneyBig = 
					TypeConvert.scale(new BigDecimal(this.totalamount), TypeConvert.SCALE_VALUE);
			if (leverMoneyBig != null) {
				this.totalamount = leverMoneyBig.doubleValue();
			}
		}
		return totalamount;
	}
	public void setTotalamount(Double totalamount) {
		this.totalamount = totalamount;
	}
	
	
	
}

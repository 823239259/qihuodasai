package com.tzdr.domain.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

import com.tzdr.common.utils.AllowExcel;
import com.tzdr.common.utils.SqlColumn;
import com.tzdr.common.utils.TypeConvert;
import com.tzdr.domain.cache.CacheManager;
import com.tzdr.domain.cache.DataDicKeyConstants;

/**
 * 过户费差vo
 * @author 张军
 *
 */
public class FreeDiffVo implements Serializable {
	private static final long serialVersionUID = 1L;
	@SqlColumn 
	private String id;
	@SqlColumn
	@AllowExcel(name="恒生母账号")
	private String parentaccount;//母账号
	@SqlColumn
	@AllowExcel(name="恒生账户")
	private String account;//
	@SqlColumn
	@AllowExcel(name="方案号")
	private String groupid;//方案号
	@SqlColumn
	@AllowExcel(name="类型")
	private String type;//类型
	@SqlColumn
	@AllowExcel(name="金额")
	private Double money;//金额
	@SqlColumn
	@AllowExcel(name="创建时间")
	private String createdate;//创建时间
	@SqlColumn
	@AllowExcel(name="录入者")
	private String lrr;//录入人
	
	private String minmoney;
	private String maxmoney;
	private Double totalMoney;
	private BigInteger countnum;
	public Double getTotalMoney() {
		if (this.totalMoney != null) {
			BigDecimal leverMoneyBig = 
					TypeConvert.scale(new BigDecimal(this.totalMoney), TypeConvert.SCALE_VALUE);
			if (leverMoneyBig != null) {
				this.totalMoney = leverMoneyBig.doubleValue();
			}
		}
		return totalMoney;
	}

	public void setTotalMoney(Double totalMoney) {
		this.totalMoney = totalMoney;
	}

	public BigInteger getCountnum() {
		return countnum;
	}

	public void setCountnum(BigInteger countnum) {
		this.countnum = countnum;
	}

	private String qtype;//查询类型属性
	public String getQtype() {
		return qtype;
	}

	public void setQtype(String qtype) {
		this.qtype = qtype;
	}

	public String getMinmoney() {
		return minmoney;
	}

	public void setMinmoney(String minmoney) {
		this.minmoney = minmoney;
	}

	public String getMaxmoney() {
		return maxmoney;
	}

	public void setMaxmoney(String maxmoney) {
		this.maxmoney = maxmoney;
	}

	public String getGroupid() {
		return groupid;
	}

	public void setGroupid(String groupid) {
		this.groupid = groupid;
	}

	public String getParentaccount() {
		return parentaccount;
	}

	public void setParentaccount(String parentaccount) {
		this.parentaccount = parentaccount;
	}

	private String starttime;
	private String endtime;
	public String getStarttime() {
		return starttime;
	}

	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}

	public String getEndtime() {
		return endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getType() {
		 return CacheManager.getDataMapByKey(DataDicKeyConstants.FREETYPE, String.valueOf(this.type));
	}

	public void setType(String type) {
		this.type = type;
	}


	

	public String getCreatedate() {
		return createdate;
	}

	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}

	public String getLrr() {
		return lrr;
	}

	public void setLrr(String lrr) {
		this.lrr = lrr;
	}


}

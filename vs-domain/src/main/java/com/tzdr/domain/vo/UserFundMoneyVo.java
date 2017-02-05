package com.tzdr.domain.vo;

import java.io.Serializable;
import java.sql.Timestamp;

import com.tzdr.common.utils.SqlColumn;

/**
 * 
 * <P>title:@UserFundMoneyVo.java</p>																								
 * <P>Description：</p>
 * <p>Copyright: Copyright (c) 2015 tzdr</p>
 * <p>Company: 上海信宏</p>
 * History：
 * @author:zhangjun
 * @date 2015年4月1日
 * @version 1.0
 */
public class UserFundMoneyVo  implements Serializable {
	@SqlColumn
	private String aname;//恒生客户姓名
	@SqlColumn
	private Double money;//提取利润金额
	@SqlColumn
	private String adtime;//追加时间
	@SqlColumn
	private String lid;//方案编号

	@SqlColumn
	private String tname;

	@SqlColumn
	private String account;//恒生账号
	@SqlColumn
	private String mobile;
	
	private String starttime;
	private String endtime;
	public Double getMoney() {
		return money;
	}
	public void setMoney(Double money) {
		this.money = money;
	}
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

	public String getAdtime() {
		return adtime;
	}
	public void setAdtime(String adtime) {
		this.adtime = adtime;
	}
	public String getLid() {
		return lid;
	}
	public void setLid(String lid) {
		this.lid = lid;
	}
	
	
	public String getAname() {
		return aname;
	}
	public void setAname(String aname) {
		this.aname = aname;
	}
	public String getTname() {
		return tname;
	}
	public void setTname(String tname) {
		this.tname = tname;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
}


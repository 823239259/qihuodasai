package com.tzdr.domain.vo;

import java.io.Serializable;
import java.math.BigInteger;

import jodd.util.ObjectUtil;

import com.tzdr.common.utils.Dates;

/**
 * 用户的拥有的停牌股
 * @author zhouchen
 *
 */

public class UserRestrictStockVo implements Serializable {
	
	private static final long serialVersionUID = -6469817179539585307L;
	
	/**
	 * id
	 */
	private String  id;

	
	/**
	 * 手机号
	 */
	private String  mobile;
	
	/**
	 * 用户姓名
	 */
	private String  username;
	
	/**
	 * 恒生帐号
	 */
	private String  account;
	
	/**
	 * 方案组合号
	 */
	private String groupId;
	
	/**
	 * 股票代码
	 */
	private String code;
	
	/**
	 * 股票名称
	 */
	private String stockName;
	/**
	 * 停牌日期
	 */
	private String effectiveDate;

	
	/**
	 * 持股日期
	 */
	private BigInteger  positionTime;
	
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(String effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStockName() {
		return stockName;
	}

	public void setStockName(String stockName) {
		this.stockName = stockName;
	}

	public String getPositionTime() {
		
		if (ObjectUtil.equals(null,this.positionTime)){
			return "";
		}
		return Dates.parseBigInteger2Date(this.positionTime,Dates.CHINESE_DATE_FORMAT_LINE);

	}

	public void setPositionTime(BigInteger positionTime) {
		this.positionTime = positionTime;
	}

	
}

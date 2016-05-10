package com.tzdr.domain.vo.cms;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

import com.ibm.wsdl.BindingInputImpl;
import com.tzdr.common.utils.AllowExcel;
import com.tzdr.common.utils.Dates;

public class FSimpleAppendLevelMoneyVo implements Serializable {

	private static final long serialVersionUID = -5466393557404329044L;

	private String id;

	/**
	 * 用户id
	 */
	private String uid;

	/**
	 * 手机号码
	 */
	@AllowExcel(name = "手机号码")
	private String mobile;

	/**
	 * 客户姓名
	 */
	@AllowExcel(name = "客户姓名")
	private String username;

	/**
	 * 商品类型【 1.沪金 2.沪银 3.沪铜 4.橡胶】
	 */
	private Integer type;
	@AllowExcel(name = "交易品种")
	private String typeValue;

	/**
	 * 交易账号
	 */
	@AllowExcel(name = "操盘账号")
	private String tranAccount;

	/**
	 * 方案号TG+ID号
	 */
	private String programNo;

	/**
	 * 追加金额
	 */
	@AllowExcel(name = "补充保证金（元）")
	private Double appendMoney;

	/**
	 * 追加时间
	 */
	private BigInteger appendDate;
	@AllowExcel(name = "申请追加时间")
	private String appendDateValue;

	/**
	 * 更新时间
	 */
	private BigInteger updateTime;
	@AllowExcel(name = "处理时间")
	private String updateTimeValue;

	/**
	 * 处理状态 默认0:未处理 1：已处理
	 */
	private Integer status;
	@AllowExcel(name = "状态")
	private String statusValue;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

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

	public int getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getTypeValue() {
		if (type != null) {
			switch (type.intValue()) {
			case 0:
				typeValue = "富时A50";
				break;
			case 1:
				typeValue = "沪金";
				break;
			case 2:
				typeValue = "沪银";
				break;
			case 3:
				typeValue = "沪铜";
				break;
			case 4:
				typeValue = "橡胶";
				break;
			case 20:
				typeValue = "商品综合";
				break;
			default:
				break;
			}
		}
		return typeValue;
	}

	public void setTypeValue(String typeValue) {
		this.typeValue = typeValue;
	}

	public String getTranAccount() {
		return tranAccount;
	}

	public void setTranAccount(String tranAccount) {
		this.tranAccount = tranAccount;
	}

	public String getProgramNo() {
		return programNo;
	}

	public void setProgramNo(String programNo) {
		this.programNo = programNo;
	}

	public Double getAppendMoney() {
		return appendMoney;
	}

	public void setAppendMoney(Double appendMoney) {
		this.appendMoney = appendMoney;
	}

	public BigInteger getAppendDate() {
		return appendDate;
	}

	public void setAppendDate(BigInteger appendDate) {
		this.appendDate = appendDate;
	}

	public String getAppendDateValue() {
		if(appendDate!=null){
			appendDateValue=Dates.format(Dates.parseLong2Date(appendDate.longValue()));
		}
		return appendDateValue;
	}

	public void setAppendDateValue(String appendDateValue) {
		this.appendDateValue = appendDateValue;
	}

	public BigInteger getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(BigInteger updateTime) {
		this.updateTime = updateTime;
	}

	public String getUpdateTimeValue() {
		if(updateTime!=null){
			updateTimeValue=Dates.format(Dates.parseLong2Date(updateTime.longValue()));
		}
		return updateTimeValue;
	}

	public void setUpdateTimeValue(String updateTimeValue) {
		this.updateTimeValue = updateTimeValue;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getStatusValue() {
		if (status != null) {
			switch (status.intValue()) {
			case 0:
				statusValue = "未处理";
				break;
			case 1:
				statusValue = "已处理";
				break;
			default:
				break;
			}
		}
		return statusValue;
	}

	public void setStatusValue(String statusValue) {
		this.statusValue = statusValue;
	}

}

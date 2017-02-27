package com.tzdr.domain.vo.cms;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

import com.tzdr.common.utils.AllowExcel;
import com.tzdr.common.utils.Dates;

/**
 * @author gc
 *
 */
public class FinternationFutureMoneyVo implements Serializable {

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
	 * 商品类型【 0.A50   6.国际原油   7.恒生指数 8.国际综合 9.小恒指】
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
	@AllowExcel(name = "补充保证金（￥）")
	private Double appendMoney;

	
	/**
	 * 追加金额美元金额
	 */
	@AllowExcel(name = "补充保证金（$）")
	private Double dollarMoney;
	
	/**
	 * 亏损平仓线
	 */
	@AllowExcel(name = "亏损平仓线（$）")
	private BigDecimal lineLoss;


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
	
	
	/**
	 * 平台来源 1:网站平台   2:APP平台   默认1
	 */
	private Integer source;
	@AllowExcel(name="平台来源")
	private String sourceStr;
	
	@AllowExcel(name="操作员")
	private String operator;//操作员

	public Integer getSource() {
		return source;
	}

	public void setSource(Integer source) {
		this.source = source;
	}

	public String getSourceStr() {
		String str="";
		if(this.source!=null&&this.source==2){
			str="APP平台";
		}else{
			str="网站平台";
		}
		return str;
	}

	public void setSourceStr(String sourceStr) {
		this.sourceStr = sourceStr;
	}
	
	
	
	public BigDecimal getLineLoss() {
		return lineLoss;
	}

	public void setLineLoss(BigDecimal lineLoss) {
		this.lineLoss = lineLoss;
	}

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
			case 6:
				typeValue = "国际原油";
				break;
			case 7:
				typeValue = "恒生指数";
				break;
			case 8:
				typeValue = "国际综合";
				break;
			case 9:
				typeValue = "小恒指";
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

	public Double getDollarMoney() {
		return dollarMoney;
	}

	public void setDollarMoney(Double dollarMoney) {
		this.dollarMoney = dollarMoney;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

}

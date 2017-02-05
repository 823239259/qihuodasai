package com.tzdr.domain.vo;

import java.io.Serializable;
import java.math.BigDecimal;



import java.math.MathContext;
import java.util.Date;

import com.tzdr.common.utils.AllowExcel;
import com.tzdr.common.utils.SqlColumn;
import com.tzdr.common.utils.TypeConvert;

/**
 * @author WangPinQun
 * @version 创建时间：2015年07月29日 上午14:38:47
 * 类说明
 */
public class FSimpleAppendLevelMoneyVo  implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	/**
	 * 编号
	 */
	@SqlColumn(name="id")
	private String id;
	
	/**
	 * 手机号
	 */
	@AllowExcel(name="手机号码")
	@SqlColumn(name="mobile")
	private String mobile;
	
	/**
	 *真实姓名
	 */
	@SqlColumn(name="realName")
	@AllowExcel(name="客户姓名")
	private String realName;
	
	/**
	 * 操盘保证金
	 */
	@SqlColumn(name="traderBond")
	@AllowExcel(name="操盘保证金")
	private BigDecimal traderBond= new BigDecimal(0.00);
	
	/**
	 * 手数
	 */
	@SqlColumn(name="tranLever")
	@AllowExcel(name="可开仓手数")
	private Integer tranLever;
	
	/**
	 *股指期货账户
	 */
	@SqlColumn(name="tranAccount")
	@AllowExcel(name="股指期货帐号")
	private String tranAccount;
	
	/**
	 *股指期货密码
	 */
	@SqlColumn(name="tranPassword")
	@AllowExcel(name="股指期货密码")
	private String tranPassword;

	/**
	 * 追加保证金
	 */
	@SqlColumn(name="appendMoney")
	@AllowExcel(name="补充保证金")
	private Double appendMoney=0.00;

	/**
	 *  追加时间
	 */
	@SqlColumn(name="appendDate")
	private Long appendDate;
	@AllowExcel(name="申请追加时间")
	private String appendDateStr;

	/**
	 * 处理时间
	 */
	@SqlColumn(name="handleDate")
	private Long handleDate;
	@AllowExcel(name="处理时间")
	private String handleDateStr;

	
	/**
	 * 处理状态  默认0:未处理      1：已处理
	 */
	@SqlColumn(name="status")
	private int status=0;
	
	/**
	 * 状态（字符串）
	 */
	@AllowExcel(name="状态")
	private String statusName;
	
	/**
	 * 业务类型
	 */
	@SqlColumn(name="business_type")
	private Integer businessType;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Double getAppendMoney() {
		return appendMoney;
	}

	public void setAppendMoney(Double appendMoney) {
		this.appendMoney = appendMoney;
	}

	public int getStatus() {
		return status;
	}

	public Long getAppendDate() {
		return appendDate;
	}

	public void setAppendDate(Long appendDate) {
		this.appendDate = appendDate;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public BigDecimal getTraderBond() {
		if(this.businessType != null && this.businessType == 1){
			traderBond = this.tranLever == null || this.tranLever <= 0 ? traderBond : this.traderBond.multiply(new BigDecimal(this.tranLever), MathContext.DECIMAL32);
		}
		return traderBond;
	}

	public void setTraderBond(BigDecimal traderBond) {
		this.traderBond = traderBond;
	}

	public String getTranAccount() {
		return tranAccount;
	}

	public void setTranAccount(String tranAccount) {
		this.tranAccount = tranAccount;
	}

	public String getTranPassword() {
		return tranPassword;
	}

	public void setTranPassword(String tranPassword) {
		this.tranPassword = tranPassword;
	}

	public String getStatusName() {
		if(0 == this.getStatus()){
			return "未处理";
		}
		return "已处理";
	}

	public Long getHandleDate() {
		return handleDate;
	}

	public void setHandleDate(Long handleDate) {
		this.handleDate = handleDate;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public Integer getTranLever() {
		return tranLever;
	}

	public void setTranLever(Integer tranLever) {
		this.tranLever = tranLever;
	}

	public String getAppendDateStr() {
		if(this.appendDate != null){
			appendDateStr = TypeConvert.dateToDatetimeStr(new Date(this.appendDate*1000));
		}else{
			appendDateStr = "";
		}
		return appendDateStr;
	}

	public void setAppendDateStr(String appendDateStr) {
		this.appendDateStr = appendDateStr;
	}

	public String getHandleDateStr() {
		if(this.handleDate != null){
			handleDateStr = TypeConvert.dateToDatetimeStr(new Date(this.handleDate*1000));
		}else{
			handleDateStr = "";
		}
		return handleDateStr;
	}

	public void setHandleDateStr(String handleDateStr) {
		this.handleDateStr = handleDateStr;
	}

	public Integer getBusinessType() {
		return businessType;
	}

	public void setBusinessType(Integer businessType) {
		this.businessType = businessType;
	}
}

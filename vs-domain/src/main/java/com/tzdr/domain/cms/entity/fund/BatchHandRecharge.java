package com.tzdr.domain.cms.entity.fund;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.tzdr.common.domain.BaseEntity;
import com.tzdr.domain.cache.CacheManager;
import com.tzdr.domain.cache.DataDicKeyConstants;

/**
 * 
 * <B>说明: 手工批量现金充值</B>
 * @zhouchen
 * 2016年2月17日 上午10:34:25
 */
@Entity
@Table(name="w_batch_hand_recharge")
public class BatchHandRecharge  extends BaseEntity {

	/**
	 * 
	 */ 
	private static final long serialVersionUID = 1L;

	/**
	 * 用户手机号
	 */
	private String mobile;
	
	/**
	 * 内部流水号
	 */
	private String serialNumber;
	
	/**
	 * 类型：3:系统调账,4:系统冲账	 
	 * */
	private Integer type;
	
	/**
	 * 充值金额
	 */
	private double money;
	
	/**
	 * 充值原因
	 */
	private String reason;
	
	
	/**
	 * 处理结果
	 */
	private String handleResult;
	
	/**
	 * 导入时间
	 */
	private Long  importTime;
	
	@Transient 
	private String typevalue;
	public String getTypevalue() {
		return CacheManager.getDataMapByKey(DataDicKeyConstants.USER_FUND_TYPE, String.valueOf(this.type));
		
	}

	public void setTypevalue(String typevalue) {
		this.typevalue = typevalue;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public double getMoney() {
		return money;
	}

	public void setMoney(double money) {
		this.money = money;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	

	public String getHandleResult() {
		return handleResult;
	}

	public void setHandleResult(String handleResult) {
		this.handleResult = handleResult;
	}

	public Long getImportTime() {
		return importTime;
	}

	public void setImportTime(Long importTime) {
		this.importTime = importTime;
	}
	
	
	
}
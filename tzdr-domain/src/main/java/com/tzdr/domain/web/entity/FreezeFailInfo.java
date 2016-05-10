package com.tzdr.domain.web.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.tzdr.common.domain.BaseEntity;
import com.tzdr.common.utils.TypeConvert;

/**
 * 
 * <p></p>
 * @author QingLiu
 * @see
 * @version 2.0
 * 2015年2月5日下午7:33:13
 */
@Entity
@Table(name="fail_freeze_info")
public class FreezeFailInfo extends BaseEntity {
	
	//extends BaseEntity走线下[账号、异常类型、异常信息]
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7755836659768756059L;
	//账号
	@Column
	private String account;
	//异常类型 1：冻结失败 2：通讯异常
	@Column 
	private String typeInfo;
	//异常信息
	@Column
	private String messageText;
	
	@Column
	private Long crDatetime = TypeConvert.dbDefaultDate();
	
	@Transient
	private String crDatetimeStr;
	
	public String getAccount() {
		return account;
	}
	
	public void setAccount(String account) {
		this.account = account;
	}
	
	public String getTypeInfo() {
		return typeInfo;
	}
	
	public void setTypeInfo(String typeInfo) {
		this.typeInfo = typeInfo;
	}
	
	public String getMessageText() {
		return messageText;
	}
	
	public void setMessageText(String messageText) {
		this.messageText = messageText;
	}

	public Long getCrDatetime() {
		return crDatetime;
	}

	public void setCrDatetime(Long crDatetime) {
		this.crDatetime = crDatetime;
	}
	
	public String getCrDatetimeStr() {
		if (this.crDatetime != null) {
			this.crDatetimeStr = TypeConvert.long1000ToDatetimeStr(this.crDatetime);
		}
		return crDatetimeStr;
	}

	public void setCrDatetimeStr(String crDatetimeStr) {
		this.crDatetimeStr = crDatetimeStr;
	}
	
	
}

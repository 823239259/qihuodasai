package com.tzdr.olog.db.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.tzdr.common.domain.BaseEntity;

/**
 * 操作日志 Entity
 * 
 * Date: 2013-02-28 22:59:16
 * 
 * @author Lin Feng
 */
@Entity
@Table(name = "SYS_OLOG")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Olog extends BaseEntity{
	/** uid */
	private static final long serialVersionUID = -1740401537348774052L;

	public static final int OPERATE_RESULT_SUCCESS = 1;
	public static final int OPERATE_RESULT_FAILURE = 2;

	

	/** 模块名称 */
	private String moduleName;

	/** 功能模块 */
	private String module;

	/** 操作名称 */
	private String actionName;

	/** 操作 */
	private String action;

	/** 操作时间 */
	private Date operateTime = new Date();

	/** 操作员 */
	private String operateUser;

	/** 操作员ID */
	private String operateUserId;

	/** 操作结果 */
	private int operateResult = OPERATE_RESULT_SUCCESS;

	private String operateMessage;

	/** 备注 */
	private String descn;

	/** 请求参数 */
	private String requestParameters;

	/** 客户端信息 */
	private String clientInformations;

	/** 执行时间 */
	private long executeMilliseconds = 0;

	public String getModuleName() {
		return this.moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getModule() {
		return this.module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getActionName() {
		return this.actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	public String getAction() {
		return this.action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public Date getOperateTime() {
		return this.operateTime;
	}

	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}

	public String getOperateUser() {
		return this.operateUser;
	}

	public void setOperateUser(String operateUser) {
		this.operateUser = operateUser;
	}


	/**
	 * @return the operateUserId
	 */
	public String getOperateUserId() {
		return operateUserId;
	}

	/**
	 * @param operateUserId the operateUserId to set
	 */
	public void setOperateUserId(String operateUserId) {
		this.operateUserId = operateUserId;
	}

	public int getOperateResult() {
		return this.operateResult;
	}

	public void setOperateResult(int operateResult) {
		this.operateResult = operateResult;
	}

	public String getDescn() {
		return this.descn;
	}

	public void setDescn(String descn) {
		this.descn = descn;
	}

	public String getRequestParameters() {
		return this.requestParameters;
	}

	public void setRequestParameters(String requestParameters) {
		this.requestParameters = requestParameters;
	}

	public String getClientInformations() {
		return this.clientInformations;
	}

	public void setClientInformations(String clientInformations) {
		this.clientInformations = clientInformations;
	}

	public long getExecuteMilliseconds() {
		return this.executeMilliseconds;
	}

	public void setExecuteMilliseconds(long executeMilliseconds) {
		this.executeMilliseconds = executeMilliseconds;
	}

	public String getOperateMessage() {
		return operateMessage;
	}

	public void setOperateMessage(String operateMessage) {
		this.operateMessage = operateMessage;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
	}

}

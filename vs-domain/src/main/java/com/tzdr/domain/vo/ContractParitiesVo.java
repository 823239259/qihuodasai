package com.tzdr.domain.vo;

import java.io.Serializable;
import java.math.BigInteger;

import jodd.util.ObjectUtil;

import com.tzdr.common.utils.Dates;

public class ContractParitiesVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4998693324054148108L;
	
	private String id;
	
	/**
	 * 合约名称
	 */
	private String typeName;
	
	/**  
	 * 业务类型【0.富时A50  1.沪金     2.沪银   3.沪铜   4.橡胶  6.原油    7. 恒指】
	 */
	private Integer businessType;
	
	/**
	 * 合约内容
	 */
	private String contract;
	/**
	    * 创建人id
	    */
	   private BigInteger  createUserId;
		 /**
	     * 创建人username
	     */
	    private  String createUser;
	    
	    /**
	     * 创建时间
	     */
	    private  BigInteger  createTime;
	    
	    /**
	     * 更新人（包括删除） id
	     */
	    private  BigInteger updateUserId;
	    /**
	     * 更新人username（包括删除）
	     */
	    private  String updateUser;
	    
	    /**
	     * 更新时间
	     */
	    private  BigInteger  updateTime;

	    /**
	     * 创建人所属组织
	     */
	    private  String createUserOrg;
	    
	    /**
	     * 更新人所属组织
	     */
	    private  String updateUserOrg;
	 
	    /**
	     * 最后一次的操作内容
	     */
	    private  String operateContent;
	    
	    
	    
	    

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getTypeName() {
			return typeName;
		}

		public void setTypeName(String typeName) {
			this.typeName = typeName;
		}

		public Integer getBusinessType() {
			return businessType;
		}

		public void setBusinessType(Integer businessType) {
			this.businessType = businessType;
		}

		public String getContract() {
			return contract;
		}

		public void setContract(String contract) {
			this.contract = contract;
		}

		public BigInteger getCreateUserId() {
			return createUserId;
		}

		public void setCreateUserId(BigInteger createUserId) {
			this.createUserId = createUserId;
		}

		public String getCreateUser() {
			return createUser;
		}

		public void setCreateUser(String createUser) {
			this.createUser = createUser;
		}

		public String getCreateTime() {
			if (ObjectUtil.equals(null,this.createTime)){
				return "";
			}
			return Dates.parseBigInteger2Date(this.createTime, Dates.CHINESE_DATETIME_FORMAT_LINE);
		}

		public void setCreateTime(BigInteger createTime) {
			this.createTime = createTime;
		}

		public BigInteger getUpdateUserId() {
			return updateUserId;
		}

		public void setUpdateUserId(BigInteger updateUserId) {
			this.updateUserId = updateUserId;
		}

		public String getUpdateUser() {
			return updateUser;
		}

		public void setUpdateUser(String updateUser) {
			this.updateUser = updateUser;
		}

		public String getUpdateTime() {
			if (ObjectUtil.equals(null,this.updateTime)){
				return "";
			}
			return Dates.parseBigInteger2Date(this.updateTime, Dates.CHINESE_DATETIME_FORMAT_LINE);
		}

		public void setUpdateTime(BigInteger updateTime) {
			this.updateTime = updateTime;
		}

		public String getCreateUserOrg() {
			return createUserOrg;
		}

		public void setCreateUserOrg(String createUserOrg) {
			this.createUserOrg = createUserOrg;
		}

		public String getUpdateUserOrg() {
			return updateUserOrg;
		}

		public void setUpdateUserOrg(String updateUserOrg) {
			this.updateUserOrg = updateUserOrg;
		}

		public String getOperateContent() {
			return operateContent;
		}

		public void setOperateContent(String operateContent) {
			this.operateContent = operateContent;
		}
	    
	    
	    

}

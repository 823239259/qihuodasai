package com.tzdr.domain.vo.ftse;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

import jodd.util.ObjectUtil;

import com.tzdr.common.utils.Dates;


public class FSimpleParitiesVo implements Serializable {
	
	private static final long serialVersionUID = -6469817171539585309L;
	/**
	 * 汇率记录ID
	 */
	protected String id;
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

	 /**
     * 逻辑删除flag
     */
    private Boolean deleted;
    /**
     * 版本号
     */
    private BigInteger version;
    
	/**
	 * 汇率兑换类型【1：美元；2：港元】
	 */
	private Integer type;
	
	/**
	 * 汇率兑换类型名称
	 */
	private String typeName;
	
	/**
	 * 汇率
	 */
	private BigDecimal parities;
	
	/**
	 * 添加时间
	 */
	private BigInteger  addTime;
	
	/**
	 * 币种
	 */
	private String currencyNo;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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
		String dateStr = "";
		if (!ObjectUtil.equals(null,this.createTime)){
			dateStr = Dates.parseBigInteger2Date(this.createTime, Dates.CHINESE_DATETIME_FORMAT_LINE);
		}
		return dateStr;
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
		String dateStr = "";
		if (!ObjectUtil.equals(null,this.updateTime)){
			dateStr = Dates.parseBigInteger2Date(this.updateTime, Dates.CHINESE_DATETIME_FORMAT_LINE);
		}
		return dateStr;
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

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		switch (type) {
		case 1: this.typeName = "美元";this.currencyNo = "USD";break;
		case 2: this.typeName = "港元";this.currencyNo = "HKD-HKFE";break;
		case 3: this.typeName = "欧元";this.currencyNo = "EUR";break;
		case 4: this.typeName = "日元";this.currencyNo = "JPY";break;
		case 5: this.typeName = "人民币";this.currencyNo = "CNY";break;
		default:break;
		}
		this.type = type;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public BigDecimal getParities() {
		return parities;
	}

	public void setParities(BigDecimal parities) {
		this.parities = parities;
	}

	public String getAddTime() {
		String dateStr = "";
		if (!ObjectUtil.equals(null,this.addTime)){
			dateStr = Dates.parseBigInteger2Date(this.addTime, Dates.CHINESE_DATETIME_FORMAT_LINE);
		}
		return dateStr;
	}

	public void setAddTime(BigInteger addTime) {
		this.addTime = addTime;
	}

	public BigInteger getVersion() {
		return version;
	}

	public void setVersion(BigInteger version) {
		this.version = version;
	}

	public String getCurrencyNo() {
		return currencyNo;
	}

	public void setCurrencyNo(String currencyNo) {
		this.currencyNo = currencyNo;
	}
	
}
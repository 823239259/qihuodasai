package com.tzdr.domain.web.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import jodd.util.StringUtil;

import org.hibernate.validator.constraints.NotBlank;

import com.tzdr.common.domain.BaseCrudEntity;
import com.tzdr.domain.cache.CacheManager;
import com.tzdr.domain.cache.DataDicKeyConstants;


/**
 * 母账户 
 * @zhouchen
 * 2014年12月26日
 */
@Entity
@Table(name="w_parent_account")
public class ParentAccount  extends BaseCrudEntity  {



	private static final long serialVersionUID = 1L;

	/**
	 * 账户编号
	 */
	@NotBlank(message="{parent.account.no.not.null}")
	private String accountNo;
	
	/**
	 * 账户名称
	 */
	@NotBlank(message="{parent.account.name.not.null}")
	private String accountName;
	
	/**
	 * 账户类别
	 * 0：钱江版；1：涌金版
	 */
	private int accountGenre;
    
	/**
	 * 账户优先级编号
	 */
	@NotBlank(message="{parent.account.priorityno.not.null}")
	private String priorityNo;
	
	
	/**
	 * 管理单元序号
	 */
	@NotBlank(message="{parent.account.unitNumber.not.null}")
	private String unitNumber;
	
	
	/**
	 * 账户类型
	 */
	@NotBlank(message="{parent.account.type.not.null}")
	private String accountType;
	
	/**
	 * 所属券商
	 */
	@NotBlank(message="{parent.account.securities.business.not.null}")
	private String securitiesBusiness;
	
	/**
	 * 资金总额
	 */
	@NotNull(message="{parent.account.total.funds.not.null}")
	private Double  totalFunds;
	
	/**
	 * 资金余额
	 */
	private Double  fundsBalance;
	
	/**
	 * 暂停抓取子账户金额
	 */
	@NotNull(message="{parent.account.subfunds.not.null}")
	private Double  subFunds;
	
	 /**
     * 未配资专用 状态  默认为FALSE：关闭 ，true：开启
     */
    private Boolean status = Boolean.FALSE;
    
    /**
     * 融资融券 倍数限制
     */
    private int  multipleLimit=0;
    
    /**
     * 配资倍数 开始
     */
    @Column
    private Integer multipleStart;
    
    /**
     * 配资倍数 结束
     */
    @Column
    private Integer multipleEnd;
    
    /**
     * 账户金额范围 开始
     */
    @Column
    private Double amountStart;
    
    /**
     * 账户金额范围 结束
     */
    @Column
    private Double amountEnd;
    
    /**
     * 配资截止日期
     */
    @Column
    private Long allocationDate;
    
    /**
     * 1:新用户、2:老用户、3:新老组合选择
     */
    @Column
    private  Integer newAndOldState;
    
    
    /**
     * @Transient
     * 非数据库字段  只供页面显示
     */
    
    @Transient
    private String securitiesBusinessValue;
    
    @Transient
    private String accountTypeValue;
    
    @Transient
    private String accountGenreValue;
    
	public String getAccountNo() {
		return accountNo;
	}


	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}


	public String getAccountName() {
		return accountName;
	}


	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}


	public String getPriorityNo() {
		return priorityNo;
	}


	public void setPriorityNo(String priorityNo) {
		this.priorityNo = priorityNo;
	}


	public String getAccountType() {
		return accountType;
	}


	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}


	public String getSecuritiesBusiness() {
		return securitiesBusiness;
	}


	public void setSecuritiesBusiness(String securitiesBusiness) {
		this.securitiesBusiness = securitiesBusiness;
	}


	

	public Double getTotalFunds() {
		return totalFunds;
	}


	public void setTotalFunds(Double totalFunds) {
		this.totalFunds = totalFunds;
	}


	public Double getFundsBalance() {
		return fundsBalance;
	}


	public void setFundsBalance(Double fundsBalance) {
		this.fundsBalance = fundsBalance;
	}


	public Double getSubFunds() {
		return subFunds;
	}


	public void setSubFunds(Double subFunds) {
		this.subFunds = subFunds;
	}


	public String getUnitNumber() {
		return unitNumber;
	}


	public void setUnitNumber(String unitNumber) {
		this.unitNumber = unitNumber;
	}
    
	public String getSecuritiesBusinessValue() {
		if (StringUtil.isBlank(this.securitiesBusiness)){
			return null;
		}
		//return null;
		return CacheManager.getDataMapByKey(DataDicKeyConstants.SECURITIES_BUSINESS,this.securitiesBusiness);
	}


	public void setSecuritiesBusinessValue(String securitiesBusinessValue) {
		this.securitiesBusinessValue = securitiesBusinessValue;
	}


	public String getAccountTypeValue() {
		if (StringUtil.isBlank(this.accountType)){
			return null;
		}
		//return null;
		return CacheManager.getDataMapByKey(DataDicKeyConstants.PARENT_ACCOUNT_TYPE,this.accountType);
	}


	public void setAccountTypeValue(String accountTypeValue) {
		this.accountTypeValue = accountTypeValue;
	}


	public Boolean getStatus() {
		return status;
	}


	public void setStatus(Boolean status) {
		this.status = status;
	}


	public int getMultipleLimit() {
		return multipleLimit;
	}


	public void setMultipleLimit(int multipleLimit) {
		this.multipleLimit = multipleLimit;
	}


	public int getAccountGenre() {
		return accountGenre;
	}


	public void setAccountGenre(int accountGenre) {
		this.accountGenre = accountGenre;
	}


	public String getAccountGenreValue() {
		return CacheManager.getDataMapByKey(DataDicKeyConstants.ACCOUNT_GENRE,String.valueOf(this.accountGenre));
	}


	public void setAccountGenreValue(String accountGenreValue) {
		this.accountGenreValue = accountGenreValue;
	}


	public Integer getMultipleStart() {
		return multipleStart;
	}


	public void setMultipleStart(Integer multipleStart) {
		this.multipleStart = multipleStart;
	}


	public Integer getMultipleEnd() {
		return multipleEnd;
	}


	public void setMultipleEnd(Integer multipleEnd) {
		this.multipleEnd = multipleEnd;
	}


	public Double getAmountStart() {
		return amountStart;
	}


	public void setAmountStart(Double amountStart) {
		this.amountStart = amountStart;
	}


	public Double getAmountEnd() {
		return amountEnd;
	}


	public void setAmountEnd(Double amountEnd) {
		this.amountEnd = amountEnd;
	}


	public Long getAllocationDate() {
		return allocationDate;
	}


	public void setAllocationDate(Long allocationDate) {
		this.allocationDate = allocationDate;
	}


	public Integer getNewAndOldState() {
		return newAndOldState;
	}


	public void setNewAndOldState(Integer newAndOldState) {
		this.newAndOldState = newAndOldState;
	}
    
	
}
package com.tzdr.domain.vo;

import java.io.Serializable;

import javax.persistence.Column;

import com.tzdr.common.utils.SqlColumn;
import com.tzdr.common.utils.SqlOrder;
import com.tzdr.common.utils.TypeConvert;
import com.tzdr.domain.cache.CacheManager;

/**
 * 
 * <p>母账户对象</p>
 * @author LiuQing
 * @see 手机号
 * @version 2.0
 * 2015年1月5日下午7:58:20
 */
public class ParentAccountVo implements Serializable {

	private static final long serialVersionUID = -8330215229679446303L;
	
	@SqlColumn
	private String id;
	
	/**
	 * 账户编号
	 */
	@SqlColumn(name="account_no")
	private String accountNo;
	
	/**
	 * 账户名称
	 */
	@SqlColumn(name="account_name")
	private String accountName;
	
	/**
	 * 账户类别
	 * 0：钱江版；1：涌金版
	 */
	@SqlColumn(name="account_genre")
	private int accountGenre;
	
	private String accountGenreStr;
    
	/**
	 * 账户优先级编号
	 */
	@SqlColumn(name="priority_no")
	private String priorityNo;
	
	
	/**
	 * 管理单元序号
	 */
	@SqlColumn(name="unit_number")
	private String unitNumber;
	
	
	/**
	 * 账户类型
	 */
	@SqlColumn(name="account_type")
	@SqlOrder("account_type")
	private String accountType;
	
	/**
	 * 所属券商
	 */
	@SqlColumn(name="securities_business")
	private String securitiesBusiness;
	
	/**
	 * 资金总额
	 */
	@SqlColumn(name="total_funds")
	private Double  totalFunds;
	
	/**
	 * 资金余额
	 */
	@SqlColumn(name="funds_balance")
	private Double  fundsBalance;
	
	/**
	 * 暂停抓取子账户金额
	 */
	@SqlColumn(name="sub_funds")
	private Double  subFunds;
	
	 /**
     * 未配资专用 状态  默认为FALSE：关闭 ，true：开启
     */
    private Boolean status = Boolean.FALSE;
    
    /**
     * 配资倍数 开始
     */
    @SqlColumn(name="multiple_start")
    private Integer multipleStart;
    
    /**
     * 配资倍数 结束
     */
    @SqlColumn(name="multiple_end")
    private Integer multipleEnd;
    
    private String multipleStr;
    
    /**
     * 账户金额范围 开始
     */
    @SqlColumn(name="amount_start")
    private Double amountStart;
    
    /**
     * 账户金额范围 结束
     */
    @SqlColumn(name="amount_end")
    private Double amountEnd;
    
    private String amountStr;
    
    /**
     * 配资截止日期
     */
    @SqlColumn(name="allocation_date")
    private Long allocationDate;
    
    /**
     * 
     */
    private String allocationDateStr;
    
    /**
     * 1:新用户、2:老用户、3:新老组合选择
     */
    @SqlColumn(name="new_and_old_state")
    private Integer newAndOldState;
    
    private String newAndOldStateStr;
    
    @SqlColumn(name="create_time")
    private Long createTime;
    
    private String createTimeStr;
    
    @SqlColumn(name="user")
    private String createUser;

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getAccountName() {
		return accountName;
	}

	public String getCreateTimeStr() {
		if (this.createTime != null) {
			this.createTimeStr = TypeConvert.long1000ToDatetimeStr(this.createTime);
		}
		return createTimeStr;
	}

	public void setCreateTimeStr(String createTimeStr) {
		this.createTimeStr = createTimeStr;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public int getAccountGenre() {
		return accountGenre;
	}

	public void setAccountGenre(int accountGenre) {
		this.accountGenre = accountGenre;
	}

	public String getAccountGenreStr() {
		this.accountGenreStr = CacheManager.getDataMapByKey("accountGenre",this.accountGenre + "");
		return accountGenreStr;
	}

	public void setAccountGenreStr(String accountGenreStr) {
		this.accountGenreStr = accountGenreStr;
	}

	public String getPriorityNo() {
		return priorityNo;
	}

	public void setPriorityNo(String priorityNo) {
		this.priorityNo = priorityNo;
	}

	public String getUnitNumber() {
		return unitNumber;
	}

	public void setUnitNumber(String unitNumber) {
		this.unitNumber = unitNumber;
	}

	public String getAccountType() {
		if (this.accountType != null) {
			this.accountType = CacheManager.getDataMapByKey("parentAccountType",this.accountType);
		}
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getSecuritiesBusiness() {
		if (this.securitiesBusiness != null) {
			this.securitiesBusiness = CacheManager.getDataMapByKey("securitiesBusiness",this.securitiesBusiness);
		}
		
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

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
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

	public String getAllocationDateStr() {
		if (this.allocationDate != null) {
			this.allocationDateStr = TypeConvert.long1000ToDateStr(allocationDate);
		}
		return allocationDateStr;
	}

	public void setAllocationDateStr(String allocationDateStr) {
		this.allocationDateStr = allocationDateStr;
	}

	public Integer getNewAndOldState() {
		return newAndOldState;
	}

	public void setNewAndOldState(Integer newAndOldState) {
		this.newAndOldState = newAndOldState;
	}

	public String getMultipleStr() {
		if (this.multipleStart != null && this.multipleEnd != null) {
			this.multipleStr = "[" + this.multipleStart + " - " + this.multipleEnd + "]";
		}
		return multipleStr;
	}

	public void setMultipleStr(String multipleStr) {
		this.multipleStr = multipleStr;
	}

	public String getAmountStr() {
		if (this.amountStart != null && this.amountEnd != null) {
			this.amountStr = "[" + this.amountStart + " - " + this.amountEnd + "]";
		}
		return amountStr;
	}

	public void setAmountStr(String amountStr) {
		this.amountStr = amountStr;
	}

	public String getNewAndOldStateStr() {
		
		if (this.newAndOldState != null) {
			if (this.newAndOldState == 1) {
				newAndOldStateStr = "新用户";
			}
			else if (this.newAndOldState == 2) {
				newAndOldStateStr = "老用户";
			}
			else if (this.newAndOldState == 3) {
				newAndOldStateStr = "所有";
			}
		}
		
		return newAndOldStateStr;
	}

	public void setNewAndOldStateStr(String newAndOldStateStr) {
		this.newAndOldStateStr = newAndOldStateStr;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
}

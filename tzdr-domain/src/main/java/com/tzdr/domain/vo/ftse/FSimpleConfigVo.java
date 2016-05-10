package com.tzdr.domain.vo.ftse;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

import jodd.util.ObjectUtil;

import com.tzdr.common.utils.Dates;

/**
 * A50方案参数设置  VO
 * @author wucholiang
 * 2015年9月28日 上午11:23:50
 */
public class FSimpleConfigVo implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String id;
	
	/**
	 * 交易品种类型【0.富时A50；1：富时A50方案参数设置】
	 */
	private Integer type;
	
	/**
	 * 交易品种名称
	 */
	private String typeName;
	
	/**
	 * 手续费
	 */
	private BigDecimal tranFees;
	
	/**
	 * 管理费
	 */
	private BigDecimal feeManage;
	
	/**
	 * 推荐开仓手数
	 */
	private String tranLever;

	/**
	 * 单手保证金(人民币)
	 */
	private BigDecimal traderBond;
	
	/**
	 * 单手操盘金(美元)
	 */
	private BigDecimal traderMoney;
	
	/**
	 * 单手平仓线(美元)
	 */
	private BigDecimal lineLoss;
	
	/**
	 * 入金金额(美元)
	 */
	private BigDecimal goldenMoney;
	
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

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public BigDecimal getTranFees() {
		return tranFees;
	}

	public void setTranFees(BigDecimal tranFees) {
		this.tranFees = tranFees;
	}

	public BigDecimal getFeeManage() {
		return feeManage;
	}

	public void setFeeManage(BigDecimal feeManage) {
		this.feeManage = feeManage;
	}

	public String getTranLever() {
		return tranLever;
	}

	public void setTranLever(String tranLever) {
		this.tranLever = tranLever;
	}

	public BigDecimal getTraderBond() {
		return traderBond;
	}

	public void setTraderBond(BigDecimal traderBond) {
		this.traderBond = traderBond;
	}

	public BigDecimal getTraderMoney() {
		return traderMoney;
	}

	public void setTraderMoney(BigDecimal traderMoney) {
		this.traderMoney = traderMoney;
	}

	public BigDecimal getLineLoss() {
		return lineLoss;
	}

	public void setLineLoss(BigDecimal lineLoss) {
		this.lineLoss = lineLoss;
	}

	public BigDecimal getGoldenMoney() {
		return goldenMoney;
	}

	public void setGoldenMoney(BigDecimal goldenMoney) {
		this.goldenMoney = goldenMoney;
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
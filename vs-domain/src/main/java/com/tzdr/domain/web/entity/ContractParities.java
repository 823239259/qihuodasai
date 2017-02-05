package com.tzdr.domain.web.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.tzdr.common.domain.BaseCrudEntity;
@Entity
@Table(name = "w_contract_parities")
public class ContractParities extends BaseCrudEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1222404164724899239L;
	
	/**
	 * 合约名称
	 */
	private String typeName;
	
	/**  
	 * 业务类型【0.富时A50  1.沪金     2.沪银   3.沪铜   4.橡胶  6.原油    7. 恒指   8.综合操盘（app） 9.小恒指】
	 */
	private Integer businessType;
	
	/**
	 * 合约内容
	 */
	private String contract;

	
	@Column(name = "type_name")
	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	
	@Column(name = "business_type")
	public Integer getBusinessType() {
		return businessType;
	}

	public void setBusinessType(Integer businessType) {
		this.businessType = businessType;
	}
	
	@Column(name = "contract")
	public String getContract() {
		return contract;
	}

	public void setContract(String contract) {
		this.contract = contract;
	}
	
	
}

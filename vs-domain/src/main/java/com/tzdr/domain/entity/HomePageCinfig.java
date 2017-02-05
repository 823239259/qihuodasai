package com.tzdr.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.tzdr.common.domain.BaseCrudEntity;

/**
 * 
 * @author WangPinQun 首页配置数据
 * @date 2015年12月20日
 */

@Entity
@Table(name = "w_home_page_cinfig")
public class HomePageCinfig extends BaseCrudEntity {

	private static final long serialVersionUID = 838028999390597368L;

	/**
	 * 业务类型key
	 */
	@Column(name = "type_key", length = 100)
	private String typeKey;
	
	/**
	 * 业务类型中文名称
	 */
	@Column(name = "type_name", length = 300)
	private String typeName;

	/**
	 * 值key
	 */
	@Column(name = "value_key", length = 100)
	private String valueKey;

	/**
	 * 值中文名称
	 */
	@Column(name = "value_name", length = 300)
	private String valueName;
	
	/**
	 * 值
	 */
	@Column(name = "value_data", length = 100)
	private String valueData;

	 /**
     * 排序权重
     */
    private Integer weight;
    
    @Transient
    private Boolean isUserUpdate = false;
    
    public String getTypeKey() {
		return typeKey;
	}

	public void setTypeKey(String typeKey) {
		this.typeKey = typeKey;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
    
	public String getValueKey() {
		return valueKey;
	}

	public void setValueKey(String valueKey) {
		this.valueKey = valueKey;
	}

	public String getValueName() {
		return valueName;
	}

	public void setValueName(String valueName) {
		this.valueName = valueName;
	}

	public String getValueData() {
		return valueData;
	}

	public void setValueData(String valueData) {
		this.valueData = valueData;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public HomePageCinfig() {

	}

	public HomePageCinfig(String typeKey,String typeName,String valueKey, String valueName,String valueData) {
		super();
		this.typeKey = typeKey;
		this.typeName = typeName;
		this.valueKey = valueKey;
		this.valueName = valueName;
		this.valueData =  valueData;
	}

	public Boolean getIsUserUpdate() {
		return isUserUpdate;
	}

	public void setIsUserUpdate(Boolean isUserUpdate) {
		this.isUserUpdate = isUserUpdate;
	}
}
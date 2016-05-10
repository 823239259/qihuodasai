package com.tzdr.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.tzdr.common.domain.BaseCrudEntity;

/**
 * 
 * @author Lin Feng 数据字典
 * @date 2014年12月26日
 */

@Entity
@Table(name = "data_map")
public class DataMap extends BaseCrudEntity {

	private static final long serialVersionUID = 838028999390597368L;

	@Column(name = "value_key", length = 100)
	private String valueKey;

	@Column(name = "value_name")
	private String valueName;

	@Column(name = "type_key", length = 100)
	private String typeKey;

	@Column(name = "type_name", length = 300)
	private String typeName;
	
	 /**
     * 排序权重
     */
    private Integer weight;
    
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

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public DataMap() {

	}

	public DataMap(String valueKey, String valueName, String typeKey,
			String typeName) {
		super();
		this.valueKey = valueKey;
		this.valueName = valueName;
		this.typeKey = typeKey;
		this.typeName = typeName;
	}
	
	
	
	
}
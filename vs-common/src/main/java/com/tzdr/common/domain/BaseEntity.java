package com.tzdr.common.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author Lin Feng
 * @date 2014年11月27日
 */
@MappedSuperclass
public abstract class BaseEntity extends AbstractEntity<String> implements Serializable {
	/** UID */
	private static final long serialVersionUID = -5797027386139497608L;
	@Id
	@GenericGenerator(name = "hibernate-uuid", strategy = "uuid")
	@GeneratedValue(generator = "hibernate-uuid")
	@Column(name = "id", length = 32, nullable = false)
	protected String id;

	

	 /**
     * 逻辑删除flag
     */
    private Boolean deleted = Boolean.FALSE;
    
    
	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Version
	private Long version;

	
	/**
	 * @return the version
	 */
	public Long getVersion() {
		return version;
	}

	/**
	 * @param version
	 *            the version to set
	 */
	public void setVersion(Long version) {
		this.version = version;
	}

	

}

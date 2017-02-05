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
public abstract class BaseCrudEntity extends AbstractEntity<String> implements Serializable {
	/** UID */
	private static final long serialVersionUID = -5797027386139497608L;
	@Id
	@GenericGenerator(name = "hibernate-uuid", strategy = "uuid")
	@GeneratedValue(generator = "hibernate-uuid")
	@Column(name = "id", length = 32, nullable = false)
	protected String id;

   /**
    * 创建人id
    */
   private Long  createUserId;
	 /**
     * 创建人username
     */
    private  String createUser;
    
    /**
     * 创建时间
     */
    private  Long  createTime;
    
    /**
     * 更新人（包括删除） id
     */
    private  Long updateUserId;
    /**
     * 更新人username（包括删除）
     */
    private  String updateUser;
    
    /**
     * 更新时间
     */
    private  Long  updateTime;

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

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public Long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Long updateTime) {
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

	public Long getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(Long createUserId) {
		this.createUserId = createUserId;
	}

	public Long getUpdateUserId() {
		return updateUserId;
	}

	public void setUpdateUserId(Long updateUserId) {
		this.updateUserId = updateUserId;
	}

	

}

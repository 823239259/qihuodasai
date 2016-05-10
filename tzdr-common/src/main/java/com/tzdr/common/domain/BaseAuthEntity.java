package com.tzdr.common.domain;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
* 只用于权限模块,普通模块请用 BaseEntity.java
* @author Lin Feng
* @date 2014年12月8日
*/
@MappedSuperclass
public abstract class BaseAuthEntity<ID extends Serializable> extends AbstractEntity<ID> {

    /**
	 * 
	 */
	private static final long serialVersionUID = 8622433043955756235L;
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private ID id;

    @Override
    public ID getId() {
        return id;
    }

    @Override
    public void setId(ID id) {
        this.id = id;
    }
    
    /**
     * 创建人id
     */
    private Long  createUserId;
    
    /**
     * 创建人
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
     * 更新人（包括删除）
     */
    private  String updateUser;
    
    /**
     * 更新事件
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

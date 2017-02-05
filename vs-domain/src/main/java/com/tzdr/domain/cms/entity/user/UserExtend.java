package com.tzdr.domain.cms.entity.user;

import com.tzdr.common.domain.BaseAuthEntity;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;

/**
 * 系统用户拓展表
 * @ClassName UserExtend
 * @author L.Y
 * @email liuyuan@peigubao.com
 * @date 2015年6月15日
 */
@Entity
@Table(name = "sys_user_extend")
public class UserExtend extends BaseAuthEntity<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1093448229866049744L;
	
    /**
     * 操作的管理员
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    @JoinColumn(name = "sys_user_id", nullable = false)
    private User sysUser;
    
    /**
     * 前端用户id
     */
    @Column(name = "uid", nullable = false, length = 32)
    private String uid;
    
    /**
     * 活动类型
     */
    @Column(name = "activity_type")
    private Integer activityType;
    
    /**
     * 逻辑删除flag
     */
    @Column(name = "deleted", nullable = false)
    private Boolean deleted = Boolean.FALSE;

    /**
     * 备注信息
     */
    @Column(name = "remark", length = 255)
    private String remark;

	public User getSysUser() {
		return sysUser;
	}

	public void setSysUser(User sysUser) {
		this.sysUser = sysUser;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}
	
	public Integer getActivityType() {
		return activityType;
	}

	public void setActivityType(Integer activityType) {
		this.activityType = activityType;
	}

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
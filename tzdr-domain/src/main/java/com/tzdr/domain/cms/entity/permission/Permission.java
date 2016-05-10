
package com.tzdr.domain.cms.entity.permission;


import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.tzdr.common.domain.BaseAuthEntity;

/**
 * 权限表
 * <p>User: Lin Feng
 * <p>Version: 1.0
 */
@Entity
@Table(name = "sys_permission")
@Cacheable
public class Permission extends BaseAuthEntity<Long> {

    /**
	 * 
	 */
	private static final long serialVersionUID = 8079614079583168978L;
	/**
     * 前端显示名称
     */
    private String name;
    /**
     * 系统中验证时使用的权限标识
     */
    private String permission;

    /**
     * 详细描述
     */
    private String description;

    /**
     * 是否显示 也表示是否可用 为了统一 都使用这个
     */
    @Column(name = "is_show")
    private Boolean show = Boolean.FALSE;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getShow() {
        return show;
    }

    public void setShow(Boolean show) {
        this.show = show;
    }
}

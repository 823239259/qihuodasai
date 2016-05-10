
package com.tzdr.domain.cms.entity.permission;

import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Lists;
import com.tzdr.common.domain.BaseAuthEntity;

/**
 * 角色表
 * <p>User: Lin Feng
 * <p>Version: 1.0
 */
@Entity
@Table(name = "sys_role")
@Cacheable
public class Role extends BaseAuthEntity<Long> {

    /**
	 * 
	 */
	private static final long serialVersionUID = 988959060628949397L;
	/**
     * 前端显示名称
     */
	@NotBlank(message="{role.name.not.null}")
    private String name;
    /**
     * 系统中验证时使用的角色标识
     */
	@NotBlank(message="{role.mark.not.null}")
    private String role;

    /**
     * 详细描述
     */
	@NotBlank(message="{role.description.not.null}")
    private String description;


    /**
     * 逻辑删除flag
     */
    private Boolean deleted = Boolean.FALSE;
    
    /**
     * 用户 组织机构 工作职务关联表
     * @JsonIgnore  返回json字符串时 不加载
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = RoleResourcePermission.class, mappedBy = "role", orphanRemoval = true)
    @Fetch(FetchMode.SELECT)
    @Basic(optional = true, fetch = FetchType.EAGER)
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)//集合缓存
    @OrderBy
    @JsonIgnore
    private List<RoleResourcePermission> resourcePermissions;

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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<RoleResourcePermission> getResourcePermissions() {
        if (resourcePermissions == null) {
            resourcePermissions = Lists.newArrayList();
        }
        return resourcePermissions;
    }

    public void setResourcePermissions(List<RoleResourcePermission> resourcePermissions) {
        this.resourcePermissions = resourcePermissions;
    }

    public void addResourcePermission(RoleResourcePermission roleResourcePermission) {
        roleResourcePermission.setRole(this);
        getResourcePermissions().add(roleResourcePermission);
    }

    public Boolean getShow() {
        return show;
    }

    public void setShow(Boolean show) {
        this.show = show;
    }

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}
    
    
}

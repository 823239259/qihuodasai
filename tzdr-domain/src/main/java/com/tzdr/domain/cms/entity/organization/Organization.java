
package com.tzdr.domain.cms.entity.organization;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Formula;
import org.hibernate.validator.constraints.NotBlank;

import com.tzdr.common.domain.BaseAuthEntity;

/**
 * 组织机构树
 * <p>User: Lin Feng
 * <p>Version: 1.0
 */
@Entity
@Table(name = "sys_organization")
public class Organization extends BaseAuthEntity<Long>{

	/**
	 * 默认的职能部门 权限控制  code 长度
	 */
	public static final int DEFAULT_CODE_LENGTH = 12;
	
    /**
	 * 
	 */
	private static final long serialVersionUID = -3693453028531201274L;

	/**
     * 标题
     */
	@NotBlank(message="{organization.name.not.null}")
    private String name;

    /**
     * 父路径
     */
    @Column(name = "parent_id")
    private Long parentId;

    /**
     * 排序权重
     */
    private Integer weight;

    /**
     * 是否有叶子节点
     */
    @Formula(value = "(select count(*) from sys_organization f_t where f_t.parent_id = id)")
    private boolean hasChildren;

    /**
     * 是否显示
     */
    @Column(name = "is_show")
    private Boolean show = Boolean.FALSE;
    
    /**
     * 逻辑删除flag
     */
    private Boolean deleted = Boolean.FALSE;
    
    /**
     * 树形标志
     * 一级：001
     * 二级：001001
     * 三级：001001001
     */
    @Column(name = "code")
    private String code ;

    public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Organization() {
    }

    public Organization(Long id) {
        setId(id);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    /**
     * 是否有根节点
     */
    public boolean isRoot() {
        if (getParentId() != null && getParentId() == 0) {
            return true;
        }
        return false;
    }


    /**
     * 是否是叶子
     */
    public boolean isLeaf() {
        if (isRoot()) {
            return false;
        }
        if (isHasChildren()) {
            return false;
        }

        return true;
    }

    public boolean isHasChildren() {
        return hasChildren;
    }

    public void setHasChildren(boolean hasChildren) {
        this.hasChildren = hasChildren;
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

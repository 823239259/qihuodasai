/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.tzdr.common.domain.plugin;

import java.io.Serializable;

/**
 * <p>实体实现该接口表示想要实现树结构
 * <p/>
 * <p>User: Lin Feng
 * <p>Version: 1.0
 */
public interface Treeable<ID extends Serializable> {

    public void setName(String name);

    public String getName();

    /**
     * 父路径
     *
     * @return
     */
    public ID getParentId();

    public void setParentId(ID parentId);

    /**
     * 所有父路径 如1,2,3,
     *
     * @return
     */
    public String getParentIds();

    public void setParentIds(String parentIds);

    /**
     * 获取 parentIds 之间的分隔符
     *
     * @return
     */
    public String getSeparator();

    /**
     * 把自己构造出新的父节点路径
     *
     * @return
     */
    public String makeSelfAsNewParentIds();

    /**
     * 权重 用于排序 越小越排在前边
     *
     * @return
     */
    public Integer getWeight();

    public void setWeight(Integer weight);

    /**
     * 是否是根节点
     *
     * @return
     */
    public boolean isRoot();

    /**
     * 是否是叶子节点
     *
     * @return
     */
    public boolean isLeaf();

    /**
     * 是否有孩子节点
     *
     * @return
     */
    public boolean isHasChildren();

}

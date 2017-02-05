package com.tzdr.common.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;

import com.tzdr.common.auth.Searchable;
import com.tzdr.common.domain.PageInfo;

/**
 * 针对单个Entity对象的操作,不依赖于具体ORM实现方案.
 * 
 * @author Lin Feng
 * @date 2014年11月27日
 */
public interface BaseDao<T> {
	void create(T o);

	T get(Serializable id);

	void update(T o);

	void saves(List<T> entities);

	void remove(T o);

	void removeById(Serializable id);

	void removes(Serializable... ids);

	List<T> getAll();

	PageInfo<T> query(PageInfo<T> pageInfo, Map<String, Object> map,
			Map<String, Boolean> sortMap);

	List<T> query(Map<String, Object> map, Map<String, Boolean> sortMap);
	
	
	
	
	
	 /**
     * 根据条件查询所有
     * 条件 + 分页 + 排序 用于权限
     *
     * @param searchable
     * @return
     */
    public Page<T> findAll(Searchable searchable);
    

    /**
     * 根据条件统计所有记录数
     *
     * @param searchable 用于权限
     * @return
     */
    public long count(Searchable searchable);
    

}

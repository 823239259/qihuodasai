package com.tzdr.domain.dao.resource;

import java.util.List;

import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.QueryHints;

import com.tzdr.common.dao.BaseJpaDao;
import com.tzdr.domain.cms.entity.resource.Resource;



/**
 * <p>User: Lin Feng
 * <p>Version: 1.0
 */
public interface ResourceDao extends BaseJpaDao<Resource, Long> {
	
	@QueryHints({ @QueryHint(name = "org.hibernate.cacheable", value ="true") })
	public Resource findById(Long id);
	
	/**
	 * 根据parentId 查询资源
	 * @param parentId
	 * @return
	 */
	List <Resource> findByParentIdAndShowOrderByWeightAsc(Long parentId,Boolean show);
}

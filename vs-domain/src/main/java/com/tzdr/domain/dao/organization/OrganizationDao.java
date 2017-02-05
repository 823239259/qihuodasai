package com.tzdr.domain.dao.organization;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.tzdr.common.dao.BaseJpaDao;
import com.tzdr.domain.cms.entity.organization.Organization;

/**
 * <p>User: Lin Feng
 * <p>Version: 1.0
 */
public interface OrganizationDao extends BaseJpaDao<Organization, Long> {
	
	/**
	 * 根据parentId 查询下面的组织机构
	 * @param parentId
	 * @return
	 */
	List <Organization> findByParentIdAndShowAndDeletedFalseOrderByWeightAsc(Long parentId,Boolean show);
	
	@Query("select count(1) from Organization where parentId=?1")
	Long   getChildCountByParentId(Long parentId);
	
	
	/**
	 * 查找可用的组织
	 * @return
	 */
	List <Organization> findByShowTrueAndDeletedFalseOrderByWeightAsc();
	
	

	/**
	 * 根据parentId 查询下面的未删除的组织机构
	 * @param parentId
	 * @return
	 */
	List <Organization> findByParentIdAndDeletedFalseOrderByWeightAsc(Long parentId);
}

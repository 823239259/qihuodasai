package com.tzdr.business.cms.service.organization;


import java.io.Serializable;
import java.util.List;

import jodd.util.ObjectUtil;
import jodd.util.StringUtil;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tzdr.business.cms.exception.OrganizationException;
import com.tzdr.business.cms.service.auth.AuthService;
import com.tzdr.common.baseservice.BaseServiceImpl;
import com.tzdr.common.exception.BusinessException;
import com.tzdr.common.utils.Dates;
import com.tzdr.domain.cms.entity.organization.Organization;
import com.tzdr.domain.cms.entity.user.User;
import com.tzdr.domain.dao.organization.OrganizationDao;

/**
 * <p>User: Lin Feng
 * <p>Version: 1.0
 */
@Service
public class OrganizationService extends BaseServiceImpl<Organization, OrganizationDao>{
	
	@Autowired
	private AuthService authService;
	
	public List <Organization> findByParentId(Long parentId){
		return getEntityDao().findByParentIdAndShowAndDeletedFalseOrderByWeightAsc(parentId,true);
	}
	
	@Override
	public void save(Organization org) throws BusinessException {
		Long parentId = org.getParentId();
		Organization parent = getEntityDao().findOne(parentId);
		Long code = NumberUtils.toLong(parent.getCode());
		Long count = getEntityDao().getChildCountByParentId(parentId);
		code = code*1000+(count+1);
		org.setCode("00"+code);
		
		setOperateLog(org,"新增组织","edit");
		
		super.save(org);
	}
	
	@Override
	public void update(Organization org) throws BusinessException {
		Long organizationId = org.getId();
		Organization oldOrg = getEntityDao().get(organizationId);
		if (ObjectUtil.equals(oldOrg, null)){
			throw new OrganizationException("business.update.not.found.data",null);
		}
		oldOrg.setName(org.getName());
		oldOrg.setWeight(org.getWeight());
		oldOrg.setShow(org.getShow());
		
		setOperateLog(oldOrg,"更新组织","edit");
		
		super.update(oldOrg);
	}
	
	public List<Organization> findAvailableOrganizations(){
		return getEntityDao().findByShowTrueAndDeletedFalseOrderByWeightAsc();
	} 
	
	public List<Organization> findDeleteFalseOrganizations(Long parentId){
		return getEntityDao().findByParentIdAndDeletedFalseOrderByWeightAsc(parentId);
	} 
	
	@Override
	public void removeById(Serializable id) throws BusinessException {
		Organization  organization = getEntityDao().get(Long.valueOf(id.toString()));
		if (ObjectUtil.equals(organization, null)){
			throw new OrganizationException("business.delete.not.found.data",null);
		}
		organization.setDeleted(Boolean.TRUE);
		
		setOperateLog(organization,"删除","edit");
		super.update(organization);
	}
	
	private void setOperateLog(Organization  organization,String operateContent,String operateType){
		User   loginUser = authService.getCurrentUser();	
		organization.setOperateContent(operateContent);
		if (StringUtil.equals(operateType,"edit")){
			organization.setUpdateTime(Dates.getCurrentLongDate());
			organization.setUpdateUser(loginUser.getRealname());
			organization.setUpdateUserOrg(loginUser.getOrganization().getName());
			organization.setUpdateUserId(loginUser.getId());
			return ;
		}
		
		organization.setCreateUserId(loginUser.getId());
		organization.setCreateTime(Dates.getCurrentLongDate());
		organization.setCreateUser(loginUser.getRealname());
		organization.setCreateUserOrg(loginUser.getOrganization().getName());
		return ;
	}
}

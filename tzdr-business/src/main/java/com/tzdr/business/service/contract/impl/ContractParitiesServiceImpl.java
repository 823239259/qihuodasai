package com.tzdr.business.service.contract.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.tzdr.business.cms.service.auth.AuthService;
import com.tzdr.business.service.contract.ContractParitiesService;
import com.tzdr.common.baseservice.BaseServiceImpl;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.utils.Dates;
import com.tzdr.common.web.support.EasyUiPageInfo;
import com.tzdr.common.web.support.JsonResult;
import com.tzdr.common.web.support.MultiListParam;
import com.tzdr.domain.cms.entity.user.User;
import com.tzdr.domain.dao.contract.ContractParitiesDao;
import com.tzdr.domain.vo.ContractParitiesVo;
import com.tzdr.domain.web.entity.ContractParities;

/**
 * 
 * @author LiuYang
 *
 * 2015年10月20日 上午10:57:57
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class ContractParitiesServiceImpl extends BaseServiceImpl<ContractParities, ContractParitiesDao> implements ContractParitiesService{
	
	private static Logger log = LoggerFactory.getLogger(ContractParitiesServiceImpl.class);
	
	@Autowired
	private AuthService authService;
	/**
	 * 查询合约配置列表
	 * @param easyUiPage
	 * @param searchParams
	 * @return
	 * @throws Exception
	 */
	public PageInfo<Object> getPageDatas(EasyUiPageInfo easyUiPage,Map<String, Object> searchParams) throws Exception {

		PageInfo<Object> pageInfo = new PageInfo<Object>(easyUiPage.getRows(),easyUiPage.getPage());
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT c.id,\n");
		sql.append("		c.create_time as createTime,\n");
		sql.append("		c.create_user as createUser,\n");
		sql.append("		c.create_user_id as createUserId,\n");
		sql.append("		c.create_user_org as createUserOrg,\n");
		sql.append("		c.update_time as updateTime,\n");
		sql.append("		c.update_user as updateUser,\n");
		sql.append("		c.update_user_id as updateUserId,\n");
		sql.append("		c.update_user_org as updateUserOrg,\n");
		
		sql.append("		c.type_name as typeName,\n");
		sql.append("		c.contract as contract,\n");
		sql.append("		c.business_type as businessType\n");
		sql.append("  FROM w_contract_parities c\n");
		
		List<Object> params = Lists.newArrayList();
		MultiListParam multilistParam = new MultiListParam(easyUiPage,searchParams, params, sql.toString());
		pageInfo = multiListPageQuery(multilistParam, ContractParitiesVo.class);
		
		return pageInfo;
	}
	
	/**
	 * 新增方案配置，或者更新方案配置
	 * @param ContractParities
	 * @return
	 * @throws Exception
	 */
	public JsonResult saveOrUpdateConfig(ContractParitiesVo ContractParities){
		log.info("保存或更新主力合约配置信息。。。");
		User currentUser = this.authService.getCurrentUser();
		ContractParities newConfig = this.get(ContractParities.getId());
		newConfig.setContract(ContractParities.getContract());
		newConfig.setUpdateUser(currentUser.getRealname());
		newConfig.setUpdateTime(Dates.getCurrentLongDate());
		newConfig.setUpdateUserId(currentUser.getId());
		newConfig.setUpdateUserOrg(currentUser.getCreateUserOrg());
		this.update(newConfig);
		return new JsonResult(true,"修改成功！");
	}
	
	
	
	
	
}

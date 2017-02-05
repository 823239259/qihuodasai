package com.tzdr.business.service.userTrade.impl;


import java.util.List;
import java.util.Map;

import jodd.util.StringUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.tzdr.business.cms.service.auth.AuthService;
import com.tzdr.business.service.userTrade.FSimpleParitiesService;
import com.tzdr.common.baseservice.BaseServiceImpl;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.utils.Dates;
import com.tzdr.common.web.support.EasyUiPageInfo;
import com.tzdr.common.web.support.JsonResult;
import com.tzdr.common.web.support.MultiListParam;
import com.tzdr.domain.cms.entity.user.User;
import com.tzdr.domain.dao.userTrade.FSimpleParitiesDao;
import com.tzdr.domain.vo.ftse.FSimpleParitiesVo;
import com.tzdr.domain.web.entity.FSimpleParities;

/**
 * 
 * <p></p>
 * @author WangPinQun
 * @see 
 * FSimpleParitiesServiceImpl
 * @version 2.0
 * 2015年9月16日下午14:33:13
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class FSimpleParitiesServiceImpl extends
		BaseServiceImpl<FSimpleParities, FSimpleParitiesDao> implements FSimpleParitiesService {

	private static Logger log = LoggerFactory.getLogger(FSimpleParitiesServiceImpl.class);
	
	@Autowired
	private AuthService authService;

	@Override
	public FSimpleParities getFSimpleParities(Integer type){
		
		FSimpleParities fSimpleParities = null;
		
		List<FSimpleParities> list = this.getEntityDao().findFSimpleParitiess(type);
		if(list != null && !list.isEmpty()){
			fSimpleParities = list.get(0);
		}
		
		return fSimpleParities;
	}
	@Override
	public PageInfo<Object> queryParitiesDatas(EasyUiPageInfo easyUiPage,Map<String, Object> searchParams)  throws Exception{
		PageInfo<Object> pageInfo = new PageInfo<Object>(easyUiPage.getRows(),easyUiPage.getPage());
		
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT id, \n");
		sql.append("	create_time as createTime, \n");
		sql.append("	create_user as createUser, \n");
		sql.append("	create_user_id as createUserId, \n");
		sql.append("	create_user_org as createUserOrg, \n");
		sql.append("	deleted, \n");
		sql.append("	operate_content as operateContent, \n");
		sql.append("	update_time as updateTime, \n");
		sql.append("	update_user as updateUser, \n");
		sql.append("	update_user_id as updateUserId, \n");
		sql.append("	update_user_org as updateUserOrg, \n");
		sql.append("	version, \n");
		sql.append("	parities, \n");
		sql.append("	type, \n");
		sql.append("	type_name as typeName, \n");
		sql.append("	add_time as addTime \n");
		sql.append("FROM f_simple_parities where 1 = 1");
		
		List<Object> params = Lists.newArrayList();
		MultiListParam multilistParam = new MultiListParam(easyUiPage,searchParams, params, sql.toString());

		pageInfo = multiListPageQuery(multilistParam, FSimpleParitiesVo.class);
		log.info("汇率维护列表查询......business over.....");
		return pageInfo;
	}

	@Override
	public JsonResult createParities(FSimpleParitiesVo simpleParitiesVo) throws Exception {
		//1.新增汇率信息
		Long dateLong = Dates.getCurrentLongDate();
		User user = authService.getCurrentUser();
		if(simpleParitiesVo.getId()==null){
			FSimpleParities simpleParities = new FSimpleParities(); 
			simpleParities.setType(simpleParitiesVo.getType());
			simpleParities.setTypeName(simpleParitiesVo.getTypeName());
			simpleParities.setParities(simpleParitiesVo.getParities());
			simpleParities.setAddTime(dateLong);
			simpleParities.setCreateTime(dateLong);
			simpleParities.setCreateUser(user.getUsername());
			simpleParities.setCreateUserId(user.getId());
			simpleParities.setCreateUserOrg(user.getOrganization().getName());
			simpleParities.setOperateContent("新增汇率记录");
			this.save(simpleParities);
			return new JsonResult(true,"新增汇率记录成功");
		}else{//2.修改汇率信息
			if(StringUtil.isBlank(simpleParitiesVo.getId())){
				return new JsonResult(true,"修改汇率记录ID为空！");
			}
			FSimpleParities simpleParities = this.get(simpleParitiesVo.getId());
			simpleParities.setParities(simpleParitiesVo.getParities());
			simpleParities.setOperateContent("修改汇率记录");
			simpleParities.setUpdateTime(dateLong);
			simpleParities.setUpdateUser(user.getUsername());
			simpleParities.setUpdateUserId(user.getId());
			simpleParities.setUpdateUserOrg(user.getOrganization().getName());
			this.update(simpleParities);
			return new JsonResult(true,"修改汇率记录成功");
		}
	}
}

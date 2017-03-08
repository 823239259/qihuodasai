package com.tzdr.business.service.userTrade.impl;

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
import com.tzdr.business.service.userTrade.FPoundageParitiesService;
import com.tzdr.common.baseservice.BaseServiceImpl;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.utils.Dates;
import com.tzdr.common.web.support.EasyUiPageInfo;
import com.tzdr.common.web.support.JsonResult;
import com.tzdr.common.web.support.MultiListParam;
import com.tzdr.domain.cms.entity.user.User;
import com.tzdr.domain.dao.userTrade.FPoundageParitiesDao;
import com.tzdr.domain.vo.ftse.FSimpleParitiesVo;
import com.tzdr.domain.web.entity.FPoundageParities;
import jodd.util.StringUtil;
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class FPoundageParitiesServiceImpl extends BaseServiceImpl<FPoundageParities, FPoundageParitiesDao> implements FPoundageParitiesService {
	private static Logger log = LoggerFactory.getLogger(FPoundageParitiesServiceImpl.class);
	@Autowired
	private AuthService authService;
	@Override
	public FPoundageParities getFPoundageParities(Integer type) {
		FPoundageParities fPoundageParities = null;
		
		List<FPoundageParities> list = this.getEntityDao().findFPoundageParitiess(type);
		if(list != null && !list.isEmpty()){
			fPoundageParities = list.get(0);
		}
		return fPoundageParities;
	}

	@Override
	public PageInfo<Object> queryParitiesDatas(EasyUiPageInfo easyUiPage, Map<String, Object> searchParams)
			throws Exception {
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
		sql.append("	currency_no as currencyNo, \n");
		sql.append("	add_time as addTime \n");
		sql.append("FROM f_poundage_parities where 1 = 1");
		
		List<Object> params = Lists.newArrayList();
		MultiListParam multilistParam = new MultiListParam(easyUiPage,searchParams, params, sql.toString());

		pageInfo = multiListPageQuery(multilistParam, FSimpleParitiesVo.class);
		log.info("结算手续费汇率维护列表查询......business over.....");
		return pageInfo;
	}

	@Override
	public JsonResult createParities(FSimpleParitiesVo simpleParitiesVo) throws Exception {
		//1.新增汇率信息
		Long dateLong = Dates.getCurrentLongDate();
		User user = authService.getCurrentUser();
		if(simpleParitiesVo.getId()==null){
			List<FPoundageParities> findFPoundageParitiess = this.getEntityDao().findFPoundageParitiess(simpleParitiesVo.getType());
			if(findFPoundageParitiess.size() != 0){
				return new JsonResult(false,"已经存在该种汇率，请点击修改按钮进行修改操作");
			}
			FPoundageParities poundageParities = new FPoundageParities(); 
			poundageParities.setType(simpleParitiesVo.getType());
			poundageParities.setTypeName(simpleParitiesVo.getTypeName());
			poundageParities.setCurrencyNo(simpleParitiesVo.getCurrencyNo());
			poundageParities.setParities(simpleParitiesVo.getParities());
			poundageParities.setParitiesYs(simpleParitiesVo.getParitiesYs());
			poundageParities.setAddTime(dateLong);
			poundageParities.setCreateTime(dateLong);
			poundageParities.setCreateUser(user.getUsername());
			poundageParities.setCreateUserId(user.getId());
			poundageParities.setCreateUserOrg(user.getOrganization().getName());
			poundageParities.setOperateContent("新增汇率记录");
			this.save(poundageParities);
			return new JsonResult(true,"新增汇率记录成功");
		}else{//2.修改汇率信息
			if(StringUtil.isBlank(simpleParitiesVo.getId())){
				return new JsonResult(true,"修改汇率记录ID为空！");
			}
			FPoundageParities poundageParities = this.get(simpleParitiesVo.getId());
			poundageParities.setParities(simpleParitiesVo.getParities());
			poundageParities.setParitiesYs(simpleParitiesVo.getParitiesYs());
			poundageParities.setOperateContent("修改汇率记录");
			poundageParities.setUpdateTime(dateLong);
			poundageParities.setUpdateUser(user.getUsername());
			poundageParities.setUpdateUserId(user.getId());
			poundageParities.setUpdateUserOrg(user.getOrganization().getName());
			this.update(poundageParities);
			return new JsonResult(true,"修改汇率记录成功");
		}
	}

}

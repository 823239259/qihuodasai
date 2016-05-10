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
import com.tzdr.business.service.userTrade.FSimpleConfigService;
import com.tzdr.common.baseservice.BaseServiceImpl;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.utils.Dates;
import com.tzdr.common.web.support.EasyUiPageInfo;
import com.tzdr.common.web.support.JsonResult;
import com.tzdr.common.web.support.MultiListParam;
import com.tzdr.domain.cms.entity.user.User;
import com.tzdr.domain.dao.userTrade.FSimpleConfigDao;
import com.tzdr.domain.vo.ftse.FSimpleConfigVo;
import com.tzdr.domain.web.entity.FSimpleConfig;

/**
 * 
 * <p></p>
 * @author WangPinQun
 * @see 
 * FSimpleConfigServiceImpl
 * @version 2.0
 * 2015年9月16日下午14:33:13
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class FSimpleConfigServiceImpl extends BaseServiceImpl<FSimpleConfig, FSimpleConfigDao> implements FSimpleConfigService {

	private static Logger log = LoggerFactory.getLogger(FSimpleConfigServiceImpl.class);
	@Autowired
	private AuthService authService;
	
	@Override
	public FSimpleConfig getFSimpleConfigByType(Integer type) {
		if(type == null){
			return null;
		}
		List<FSimpleConfig> fSimpleConfigList = this.getEntityDao().findByType(type);
		FSimpleConfig fSimpleConfig = null;
		if(fSimpleConfigList != null && !fSimpleConfigList.isEmpty()){
			fSimpleConfig = fSimpleConfigList.get(0);
		}
		return fSimpleConfig;
	}

	@Override
	public List<FSimpleConfig> findFSimpleConfigsByType(Integer type) {
		
		if(type == null){
			return null;
		}
		return this.getEntityDao().findFSimpleConfigsByType(type);
	}

	@Override
	public FSimpleConfig getFSimpleConfig(Integer type, String tranLever) {
		
		return this.getEntityDao().getFSimpleConfig(type, tranLever);
	}

	@Override
	public JsonResult saveOrUpdateConfig(FSimpleConfigVo simpleConfig,int type) throws Exception {
		log.info("保存或更新富时A50方案/国际原油/恒生指数 配置信息。。。");
		User currentUser = this.authService.getCurrentUser();
		if(!StringUtil.isBlank(simpleConfig.getId())){//ID 存在更新
			FSimpleConfig newConfig = this.get(simpleConfig.getId());
			newConfig.setTranLever(simpleConfig.getTranLever());
			newConfig.setTranFees(simpleConfig.getTranFees());
			newConfig.setFeeManage(simpleConfig.getFeeManage());
			newConfig.setTraderBond(simpleConfig.getTraderBond());
			newConfig.setTraderMoney(simpleConfig.getTraderMoney());
			newConfig.setLineLoss(simpleConfig.getLineLoss());
			newConfig.setGoldenMoney(simpleConfig.getGoldenMoney());
			newConfig.setUpdateUser(currentUser.getRealname());
			newConfig.setUpdateTime(Dates.getCurrentLongDate());
			newConfig.setUpdateUserId(currentUser.getId());
			newConfig.setUpdateUserOrg(currentUser.getCreateUserOrg());
			this.update(newConfig);
			return new JsonResult(true,"修改成功！");
		}else{ //ID 不存在，为新增
			FSimpleConfig newConfig = new FSimpleConfig();
			newConfig.setTranLever(simpleConfig.getTranLever());
			newConfig.setTranFees(simpleConfig.getTranFees());
			newConfig.setFeeManage(simpleConfig.getFeeManage());
			newConfig.setTraderBond(simpleConfig.getTraderBond());
			newConfig.setTraderMoney(simpleConfig.getTraderMoney());
			newConfig.setLineLoss(simpleConfig.getLineLoss());
			newConfig.setGoldenMoney(simpleConfig.getGoldenMoney());
			newConfig.setCreateUser(currentUser.getRealname());
			newConfig.setCreateTime(Dates.getCurrentLongDate());
			newConfig.setCreateUserId(currentUser.getId());
			newConfig.setCreateUserOrg(currentUser.getCreateUserOrg());
			
			newConfig.setUpdateUser(currentUser.getRealname());
			newConfig.setUpdateTime(Dates.getCurrentLongDate());
			newConfig.setUpdateUserId(currentUser.getId());
			newConfig.setUpdateUserOrg(currentUser.getCreateUserOrg());
			newConfig.setType(type);
			String name="";
			if(type == 5){
				name = "富时A50";
			}else if(type == 6){
				name = "国际原油";
			}else if(type == 7){
				name = "恒生指数";
			}
			newConfig.setTypeName(name);
			this.save(newConfig);
			return new JsonResult(true,"新增成功！");
		}
	}

	@Override
	public JsonResult deleteConfig(FSimpleConfigVo simpleConfig) throws Exception {
		this.removeById(simpleConfig.getId());
		return new JsonResult(true,"删除成功！");
	}

	@Override
	public PageInfo<Object> getPageDatas(EasyUiPageInfo easyUiPage, Map<String, Object> searchParams,int type) throws Exception {

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
		sql.append("		c.fee_manage as feeManage,\n");
		sql.append("		c.line_loss as lineLoss,\n");
		sql.append("		c.trader_bond as traderBond,\n");
		sql.append("		c.trader_money as traderMoney,\n");
		sql.append("		c.tran_fees as tranFees,\n");
		sql.append("		c.tran_lever as tranLever,\n");
		sql.append("		c.type,\n");
		sql.append("		c.type_name as typeName,\n");
		sql.append("		c.golden_money as goldenMoney\n");
		//sql.append("		c.tran_fees_array as tranFeesArray\n");
		sql.append("FROM f_simple_config c\n");
		sql.append("WHERE c.type = "+type);
		
		List<Object> params = Lists.newArrayList();
		MultiListParam multilistParam = new MultiListParam(easyUiPage,searchParams, params, sql.toString());
		pageInfo = multiListPageQuery(multilistParam, FSimpleConfigVo.class);
		
		return pageInfo;
	}
}

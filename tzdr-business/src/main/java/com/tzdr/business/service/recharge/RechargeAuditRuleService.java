package com.tzdr.business.service.recharge;

import java.util.List;

import javax.transaction.Transactional;

import jodd.util.ObjectUtil;
import jodd.util.StringUtil;

import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.tzdr.business.cms.service.auth.AuthService;
import com.tzdr.business.cms.service.user.UserService;
import com.tzdr.business.exception.RechargeAuditRuleException;
import com.tzdr.common.baseservice.BaseServiceImpl;
import com.tzdr.common.exception.BusinessException;
import com.tzdr.common.utils.Dates;
import com.tzdr.domain.cms.entity.user.User;
import com.tzdr.domain.dao.rechargelist.RechargeAuditRuleDao;
import com.tzdr.domain.web.entity.RechargeAuditRule;

/**
 * @author zhouchen
 * @version 创建时间：2015年1月12日 下午4:58:44
 * 充值审核规则
 */
@Service
@Transactional
public class RechargeAuditRuleService extends BaseServiceImpl<RechargeAuditRule,RechargeAuditRuleDao>{	
	
	private static Logger log = LoggerFactory.getLogger(RechargeAuditRuleService.class);
	
	
	@Autowired
	private AuthService authService;
	
	@Autowired
	private UserService userService;
	
	/**
	 * 保存数据
	 * @param additionalVo
	 */
	@Override
	public void save(RechargeAuditRule auditRule) throws BusinessException {
		
		String auditorId = auditRule.getAuditorId();
		auditorId=StringUtil.remove(auditorId,"U");
		auditRule.setAuditorId(auditorId);
		User user = userService.get(NumberUtils.toLong(auditorId));
		if (ObjectUtil.equals(null, user)){
			log.error("根据审核人id无法找到对应的用户信息！");
			throw new RechargeAuditRuleException("business.update.not.found.data",null);
		}
		
		auditRule.setAuditorName(user.getRealname());
		setOperateLog(auditRule,"新增-充值审核金额", "add");
		
		super.save(auditRule);
	}
	
	
	@Override
	public void update(RechargeAuditRule auditRule) throws BusinessException {
	
		RechargeAuditRule  dbAuditRule = this.get(auditRule.getId());
		if (ObjectUtil.equals(null, dbAuditRule)){
			log.error("根据id无法找到对应的信息，无法进行修改！");
			throw new RechargeAuditRuleException("business.update.not.found.data",null);
		}
		
		dbAuditRule.setBeginMoney(auditRule.getBeginMoney());
		dbAuditRule.setEndMoney(auditRule.getEndMoney());
		
		if (!StringUtil.equals(auditRule.getAuditorName(),auditRule.getAuditorId())){
			String auditorId = auditRule.getAuditorId();
			auditorId=StringUtil.remove(auditorId,"U");
			dbAuditRule.setAuditorId(auditorId);
			User user = userService.get(NumberUtils.toLong(auditorId));
			
			if (ObjectUtil.equals(null, user)){
				log.error("根据审核人id无法找到对应的用户信息！");
				throw new RechargeAuditRuleException("business.update.not.found.data",null);
			}
			dbAuditRule.setAuditorName(user.getRealname());
		}
		setOperateLog(dbAuditRule,"修改-充值审核规则", "edit");
		super.update(dbAuditRule);
	}
	
	/**
	 * 根据当前登录人的id 查找审核规则记录
	 * @param loginUserId
	 * @return
	 */
	public RechargeAuditRule  queryAuditRuleByLoginUserId(String loginUserId){
		List<RechargeAuditRule> auditRules = getEntityDao().findByAuditorId(loginUserId);
		if (CollectionUtils.isEmpty(auditRules)){
			return null;
		}
		return auditRules.get(0);
	}
	
	/**
	 * 设置操作记录
	 * @param additional
	 * @param operateContent
	 * @param operateType
	 */
	private void setOperateLog(RechargeAuditRule rechargeAuditRule,String operateContent,String operateType){
		User   loginUser = authService.getCurrentUser();	
		rechargeAuditRule.setOperateContent(operateContent);
		if (StringUtil.equals(operateType,"edit")){
			rechargeAuditRule.setUpdateTime(Dates.getCurrentLongDate());
			rechargeAuditRule.setUpdateUser(loginUser.getRealname());
			rechargeAuditRule.setUpdateUserOrg(loginUser.getOrganization().getName());
			rechargeAuditRule.setUpdateUserId(loginUser.getId());
			return ;
		}
		
		rechargeAuditRule.setCreateTime(Dates.getCurrentLongDate());
		rechargeAuditRule.setCreateUser(loginUser.getRealname());
		rechargeAuditRule.setCreateUserId(loginUser.getId());
		rechargeAuditRule.setCreateUserOrg(loginUser.getOrganization().getName());
		return ;
	}
}

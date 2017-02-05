package com.tzdr.business.service.questionnaire.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tzdr.business.service.questionnaire.QuestionnaireService;
import com.tzdr.business.service.thread.SMSSenderThread;
import com.tzdr.business.service.wuser.WUserService;
import com.tzdr.common.baseservice.BaseServiceImpl;
import com.tzdr.domain.dao.questionnaire.QuestionnaireDao;
import com.tzdr.domain.web.entity.Questionnaire;
import com.tzdr.domain.web.entity.WUser;

/**
 * 活动调查
 * @author Administrator
 *
 */

@Service("questionnaireService")
@Transactional
public class QuestionnaireServiceImpl extends BaseServiceImpl<Questionnaire,QuestionnaireDao> implements QuestionnaireService{
	public static final Logger logger = LoggerFactory.getLogger(QuestionnaireServiceImpl.class);
	@Autowired
	private WUserService wUserService;
	/*
	 * (non-Javadoc)
	 * @see com.tzdr.business.service.questionnaire.QuestionnaireService#insertEntity(com.tzdr.domain.web.entity.Questionnaire)
	 */
	@Override
	public Questionnaire insertEntity(Questionnaire entity) {
		return getEntityDao().save(entity);
		
	}

	@Override
	public void sendSms(String mobile, String module, String code) {
		Map<String,String> map= new HashMap<String,String>();
		map.put("module", module);
		map.put("code", code);
		new SMSSenderThread(mobile,
				"ihuyi.verification.code.template", map).start();
	}

	@Override
	public WUser saveActivityWUser(WUser wuser, Questionnaire entity,String ip) {
		wuser.setRegIp(ip);
		wuser=wUserService.saveActivityWUser(wuser);
		String uid=wuser.getId();
		entity.setUid(uid);
		this.update(entity);
		return wuser;
		
	}

	@Override
	public WUser saveWUser(WUser wuser, Questionnaire entity, String ip) {
		//更新对应数据
		List<WUser> cmsWusers = this.wUserService.queryByUserType("-2");
		if (cmsWusers != null && cmsWusers.size() > 0) {
			wuser.setParentNode(cmsWusers.get(0));
		}
		wuser=wUserService.saveUser(wuser);
		String uid=wuser.getId();
		entity.setUid(uid);
		this.update(entity);
		return wuser;
	}

}

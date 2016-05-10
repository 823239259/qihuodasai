package com.tzdr.business.service.questionnaire;

import com.tzdr.common.baseservice.BaseService;
import com.tzdr.domain.web.entity.Questionnaire;
import com.tzdr.domain.web.entity.WUser;


/**
 * 问卷调查
 * @author 张军
 *
 */
public interface QuestionnaireService extends BaseService<Questionnaire>{
	
	/**
	 * 新增
	 * @param entity
	 * @return
	 * @date 2015年2月12日
	 * @author zhangjun
	 */
	public Questionnaire insertEntity(Questionnaire entity);

	/**
	 * 发送短信
	 * @param mobile
	 * @param mode
	 * @param code
	 * @date 2015年2月12日
	 * @author zhangjun
	 */
	public void sendSms(String mobile, String model, String code);
	
	/**
	 * 生成活动用户账号
	 * @param wuser
	 * @param entity
	 * @date 2015年2月12日
	 * @author zhangjun
	 */
	public WUser saveActivityWUser(WUser wuser, Questionnaire entity,String ip);
	
	/**
	 * 保存普通用户账号
	 * @param wuser
	 * @param entity
	 * @param ip
	 * @return
	 * @date 2015年3月23日
	 * @author zhangjun
	 */
	public WUser saveWUser(WUser wuser, Questionnaire entity,String ip);
}

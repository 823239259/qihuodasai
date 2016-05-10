package com.tzdr.business.cms.quartz;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.Lists;
import com.tzdr.business.cms.exception.QuartzCrudException;
import com.tzdr.common.baseservice.BaseServiceImpl;
import com.tzdr.common.utils.Dates;
import com.tzdr.domain.cms.entity.auth.Auth;
import com.tzdr.domain.dao.auth.AuthDao;
import com.tzdr.domain.vo.QuartzVo;

/**
 * <p>
 * User: Lin Feng
 * <p>
 * Version: 1.0
 */
@Service
public class QuartzCrudService extends BaseServiceImpl<Auth, AuthDao> {

	public List<QuartzVo> getQuartzTriggers(){
		
		String sql = "select TRIGGER_NAME triggerName,JOB_NAME jobName from QRTZ_TRIGGERS";
		List<QuartzVo> quartzVos = nativeQuery(sql,null,QuartzVo.class);
		
		if (CollectionUtils.isEmpty(quartzVos)){
			return null;
		}
		return quartzVos;
	}
	
	
	@Transactional
	public void updateQuartz(String triggerName,String nextFireTime){
		String sql = "select TRIGGER_NAME triggerName,JOB_NAME jobName from QRTZ_TRIGGERS where TRIGGER_NAME=?";
		List<Object> params = Lists.newArrayList();
		params.add(triggerName);
		List<QuartzVo> quartzVos = nativeQuery(sql,params,QuartzVo.class);
		if (CollectionUtils.isEmpty(quartzVos)){
			throw new QuartzCrudException("business.update.not.found.data",null);
		}
	
		params.clear();
		Date date = Dates.parse(nextFireTime,Dates.CHINESE_DATETIME_FORMAT_LINE);
		params.add(date.getTime());
		params.add(triggerName);
		
		String updateSQL = "UPDATE QRTZ_TRIGGERS set NEXT_FIRE_TIME = ? where TRIGGER_NAME=?";
		nativeUpdate(updateSQL, params);
	}
}

package com.tzdr.business.api.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tzdr.common.baseservice.BaseServiceImpl;
import com.tzdr.common.utils.Dates;
import com.tzdr.domain.api.constants.Constant;
import com.tzdr.domain.api.dao.AppTokenDao;
import com.tzdr.domain.api.entity.ApiToken;


/**
 * TOKEN 服务service
 * @author zhouchen
 * 2015年5月21日 下午12:02:06
 */
@Service
@Transactional
public class ApiTokenService extends BaseServiceImpl<ApiToken,AppTokenDao> {
	public static final Logger logger = LoggerFactory.getLogger(ApiTokenService.class);	

	/**
	 * 生成token
	 * @param userName
	 * @param password
	 * @return
	 */
	public ApiToken create(ApiToken appToken){
		ApiToken dbAppToken = get(appToken.getId());
		dbAppToken.setToken(appToken.getToken());
		dbAppToken.setCreateTime(Dates.getCurrentLongDate());
		dbAppToken.setInvalidTime(Dates.getCurrentLongDate()+Constant.TOKEN_INVALID_TIME);
		super.update(dbAppToken);
		return dbAppToken;
	}
	
	/**
	 * 查找token是否存在
	 * @param token
	 * @return
	 */
	public ApiToken findByToken(String token,String ipAddr){
		return getEntityDao().findByTokenAndIpAddr(token,ipAddr);
	}
	
	/**
	 * 获取用户token
	 * @param userName
	 * @param ipAddr
	 * @return
	 */
	public ApiToken  getTokenByUserName(String userName,String ipAddr,String source){
		return getEntityDao().findByUserNameAndIpAddrAndSource(userName,ipAddr,source);
	}
}

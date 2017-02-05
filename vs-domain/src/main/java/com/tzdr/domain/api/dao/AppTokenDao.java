package com.tzdr.domain.api.dao;

import com.tzdr.common.dao.BaseJpaDao;
import com.tzdr.domain.api.entity.ApiToken;


/**
 * app TOKEN 
 * @author zhouchen
 * 2015年5月21日 上午11:59:29
 */
public interface AppTokenDao extends BaseJpaDao<ApiToken, String> {

	public ApiToken findByUserNameAndIpAddrAndSource(String userName,String ipAddr,String  source);
	
	public ApiToken findByTokenAndIpAddr(String token,String ipAddr);
}

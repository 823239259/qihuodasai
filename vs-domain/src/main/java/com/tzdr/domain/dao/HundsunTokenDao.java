package com.tzdr.domain.dao;

import java.util.List;

import com.tzdr.common.dao.BaseJpaDao;
import com.tzdr.domain.entity.HundsunToken;

/**
*
* @author Lin Feng
* @date 2014年12月26日
* 
*/
public interface HundsunTokenDao extends BaseJpaDao<HundsunToken, String>  {
	
	public List<HundsunToken> findByAccount(String account);

}

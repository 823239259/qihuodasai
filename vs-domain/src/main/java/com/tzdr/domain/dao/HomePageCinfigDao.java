package com.tzdr.domain.dao;

import java.util.List;

import com.tzdr.common.dao.BaseJpaDao;
import com.tzdr.domain.entity.HomePageCinfig;

/**
*
* @author WangPinQun
* @date 2015年12月20日
* 
*/
public interface HomePageCinfigDao extends BaseJpaDao<HomePageCinfig, String>  {
	
	public List<HomePageCinfig> findByDeletedFalse();
	
	public List<HomePageCinfig> findByDeletedFalseAndTypeKeyAndValueKey(String typeKey,String valueKey);

	public List<HomePageCinfig>  findByTypeKey(String typeKey);
	
	public List<HomePageCinfig>  findByValueKey(String valueKey);
}

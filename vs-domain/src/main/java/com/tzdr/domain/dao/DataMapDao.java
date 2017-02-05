package com.tzdr.domain.dao;

import java.util.List;

import com.tzdr.common.dao.BaseJpaDao;
import com.tzdr.domain.entity.DataMap;

/**
*
* @author Lin Feng
* @date 2014年12月26日
* 
*/
public interface DataMapDao extends BaseJpaDao<DataMap, String>  {
	
	public List<DataMap> findByDeletedFalse();
	
	public List<DataMap> findByDeletedFalseAndTypeKeyAndValueKey(String typeKey,String valueKey);

	public List<DataMap>  findByTypeKey(String typeKey);
}

package com.tzdr.domain.dao.crudeActive;

import java.util.List;

import com.tzdr.common.dao.BaseJpaDao;
import com.tzdr.domain.web.entity.CrudeActive;

public interface CrudeActiveDao extends BaseJpaDao<CrudeActive, String>{
	List<CrudeActive> findByUidAndType(String id,int type);
}

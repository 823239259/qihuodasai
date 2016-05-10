package com.tzdr.domain.dao.ftseActive;

import java.util.List;

import com.tzdr.common.dao.BaseJpaDao;
import com.tzdr.domain.web.entity.CrudeActive;
import com.tzdr.domain.web.entity.FtseActive;

public interface FtseActiveDao extends BaseJpaDao<FtseActive, String>{
	List<FtseActive> findByUidAndType(String id,int type);

	List<FtseActive> findByUid(String uid);
}

package com.tzdr.olog.db.service.impl;

import java.util.Date;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tzdr.common.baseservice.BaseServiceImpl;
import com.tzdr.olog.db.dao.OlogDao;
import com.tzdr.olog.db.domain.Olog;
import com.tzdr.olog.db.service.OlogService;

@Service("ologService")
public class OlogServiceImpl extends BaseServiceImpl<Olog, OlogDao> implements
		OlogService {

	@Transactional
	@Override
	public void cleanup(Date beforeDate) {
		getEntityDao().cleanup(beforeDate);
	}

}

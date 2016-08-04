package com.tzdr.business.service.extension.imp;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tzdr.business.service.extension.LuckDrawService;
import com.tzdr.common.baseservice.BaseServiceImpl;
import com.tzdr.domain.dao.extension.LuckDrawDao;
import com.tzdr.domain.web.entity.LuckDraw;
@Transactional(propagation=Propagation.REQUIRED)
@Service("luckDrawService")
public class LuckDrawServiceImp  extends BaseServiceImpl<LuckDraw, LuckDrawDao> implements LuckDrawService{

	@Override
	public LuckDraw findByUid(String uid, String activity) {
		return super.getEntityDao().findByUidAndActivity(uid, activity);
	}
}

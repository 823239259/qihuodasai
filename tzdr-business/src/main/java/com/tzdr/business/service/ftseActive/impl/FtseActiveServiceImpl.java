package com.tzdr.business.service.ftseActive.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hundsun.t2sdk.common.util.CollectionUtils;
import com.tzdr.business.service.datamap.DataMapService;
import com.tzdr.business.service.ftseActive.FtseActiveService;
import com.tzdr.common.baseservice.BaseServiceImpl;
import com.tzdr.common.utils.Dates;
import com.tzdr.domain.dao.ftseActive.FtseActiveDao;
import com.tzdr.domain.entity.DataMap;
import com.tzdr.domain.web.entity.FtseActive;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class FtseActiveServiceImpl extends BaseServiceImpl<FtseActive, FtseActiveDao> implements FtseActiveService{
	@Autowired
	private FtseActiveDao ftseActiveDao;
	
	@Autowired
	private DataMapService dataMapService;
	@Override
	public void apply(FtseActive ftseactive ) {
		DataMap dataMap = dataMapService.findByTypeKey("ftseActive").get(0);
		int num = Integer.parseInt(dataMap.getValueKey());
		dataMap.setValueKey(String.valueOf(num-1));
		dataMapService.updateInfo(dataMap);
		this.save(ftseactive);
		
	}

	@Override
	public void use(String uid) {
		
		List<FtseActive> list = ftseActiveDao.findByUidAndType(uid, 1);
		FtseActive ftseActive = list.get(0);
		ftseActive.setType(2);
		ftseActive.setUpdateTime(Dates.getCurrentLongDate());
		super.update(ftseActive);
		
	}

	@Override
	public boolean isAgain(String uid) {
		List<FtseActive> list = ftseActiveDao.findByUidAndType(uid, 1);
		if (CollectionUtils.isEmpty(list)) {
			return false;
		}else{
			return true;
		}
	}

	@Override
	public boolean isOut(String uid) {
		List<FtseActive> list = ftseActiveDao.findByUidAndType(uid, 1);
		if (CollectionUtils.isEmpty(list)) {
			return true;
		}else{
			return false;
		}
	}
	
}

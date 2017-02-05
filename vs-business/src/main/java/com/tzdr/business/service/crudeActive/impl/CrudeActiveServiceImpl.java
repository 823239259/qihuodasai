package com.tzdr.business.service.crudeActive.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hundsun.t2sdk.common.util.CollectionUtils;
import com.tzdr.business.service.crudeActive.CrudeActiveService;
import com.tzdr.business.service.datamap.DataMapService;
import com.tzdr.common.baseservice.BaseServiceImpl;
import com.tzdr.common.utils.Dates;
import com.tzdr.domain.dao.crudeActive.CrudeActiveDao;
import com.tzdr.domain.dao.ftseActive.FtseActiveDao;
import com.tzdr.domain.entity.DataMap;
import com.tzdr.domain.web.entity.CrudeActive;
import com.tzdr.domain.web.entity.FtseActive;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class CrudeActiveServiceImpl extends BaseServiceImpl<CrudeActive, CrudeActiveDao> implements CrudeActiveService{
	@Autowired
	private CrudeActiveDao crudeActiveDao;
	@Autowired
	private FtseActiveDao ftseActiveDao;
	
	@Autowired
	private DataMapService dataMapService;
	@Override
	public void apply(CrudeActive crudeActive ) {
		DataMap dataMap = dataMapService.findByTypeKey("crudeActive").get(0);
		int num = Integer.parseInt(dataMap.getValueKey());
		dataMap.setValueKey(String.valueOf(num-1));
		dataMapService.updateInfo(dataMap);
		this.save(crudeActive);
		
	}

	@Override
	public void use(String uid) {
		
		List<CrudeActive> list = crudeActiveDao.findByUidAndType(uid, 1);
		CrudeActive CrudeActive = list.get(0);
		CrudeActive.setType(2);
		CrudeActive.setUpdateTime(Dates.getCurrentLongDate());
		super.update(CrudeActive);
		
	}

	@Override
	public boolean isAgain(String uid) {
		List<CrudeActive> list = crudeActiveDao.findByUidAndType(uid, 1);
		if (CollectionUtils.isEmpty(list)) {
			return false;
		}else{
			return true;
		}
	}

	@Override
	public boolean isOut(String uid) {
		List<CrudeActive> list = crudeActiveDao.findByUidAndType(uid, 1);
		if (CollectionUtils.isEmpty(list)) {
			return true;
		}else{
			return false;
		}
	}

	@Override
	public boolean isFtseActive(String uid) {
		List<FtseActive> list = ftseActiveDao.findByUid(uid);
		if (CollectionUtils.isEmpty(list)) {
			return false;
		}else{
			return true;
		}
	}
	
}

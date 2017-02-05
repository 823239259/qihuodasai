package com.tzdr.business.hkstock.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.tzdr.common.baseservice.BaseServiceImpl;
import com.tzdr.domain.hkstock.dao.HkStockParamsDao;
import com.tzdr.domain.hkstock.entity.HkStockParams;

/**
 * 
 * @author zhouchen
 * 2015年10月16日 上午11:45:32
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class HkStockParamsService extends BaseServiceImpl<HkStockParams,HkStockParamsDao> {
	
	/**
	 * 获取港股操盘参数
	 * @return
	 */
	public HkStockParams getParams(){
		List<HkStockParams> listParams = this.getAll();
		if (CollectionUtils.isEmpty(listParams)){
			return null;
		}
		return listParams.get(0);
	}
}

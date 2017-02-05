package com.tzdr.business.api.service;

import java.util.List;

import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.tzdr.common.baseservice.BaseServiceImpl;
import com.tzdr.domain.api.constants.Constant;
import com.tzdr.domain.dao.DataMapDao;
import com.tzdr.domain.entity.DataMap;

/**
 * 
 * @zhouchen 2014年12月26日
 */
@Service
@Transactional
public class ApiDataMapService extends BaseServiceImpl<DataMap, DataMapDao> {
	public static final Logger logger = LoggerFactory
			.getLogger(ApiDataMapService.class);

	/**
	 * 获取当日最大操盘个数
	 * @return
	 */
	public int getHoldMaxTradeNum() {
		List<DataMap> maps = getEntityDao()
				.findByDeletedFalseAndTypeKeyAndValueKey(
						Constant.HOLD_MAX_TRADENUM_TYPE_KEY,
						Constant.HOLD_MAX_TRADENUM_VALUE_KEY);
		if (CollectionUtils.isEmpty(maps)) {
			return Constant.DEFAULT_HOLD_MAX_TRADENUM;
		}
		return NumberUtils.toInt(maps.get(0).getValueName());
	}
}

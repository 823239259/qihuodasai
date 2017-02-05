package com.tzdr.business.service.togetherTrade;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.tzdr.common.baseservice.BaseServiceImpl;
import com.tzdr.domain.dao.togetherTrade.TogetherConfigDao;
import com.tzdr.domain.web.entity.TogetherConfig;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class TogetherConfigService extends BaseServiceImpl<TogetherConfig, TogetherConfigDao> {
	/**
	 * 获取合买操盘参数
	 * @return
	 */
	public TogetherConfig getParams(){
		List<TogetherConfig> listParams = this.getAll();
		if (CollectionUtils.isEmpty(listParams)){
			return null;
		}
		return listParams.get(0);
	}
}

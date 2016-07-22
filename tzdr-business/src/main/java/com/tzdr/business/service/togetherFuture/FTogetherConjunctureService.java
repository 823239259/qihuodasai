package com.tzdr.business.service.togetherFuture;

import java.util.List;

import com.tzdr.common.baseservice.BaseService;
import com.tzdr.domain.web.entity.FTogetherConjuncture;

/**
 * Created by huangkai on 2016/5/20.
 */
public interface FTogetherConjunctureService extends BaseService<FTogetherConjuncture> {
	
	/**
	 * 查询对应方案的行情点数
	 * @param tradeID
	 * @return
	 */
	public List<FTogetherConjuncture> findByTradeID(String tradeID);
}

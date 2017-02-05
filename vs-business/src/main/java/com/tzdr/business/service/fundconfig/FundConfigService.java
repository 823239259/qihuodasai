package com.tzdr.business.service.fundconfig;

import java.util.List;

import com.tzdr.common.baseservice.BaseService;
import com.tzdr.domain.web.entity.FundConfig;

/**
 * 配资倍数和金额 service接口
 * @ClassName FundConfigService
 * @author L.Y
 * @email meiluohai@163.com
 * @date 2015年4月27日
 */
public interface FundConfigService extends BaseService<FundConfig> {
	
	public int findMaxLever(double money);
	
	public double findAmountByTimes(int times);
	
	public List<FundConfig> findOrderByTimesAsc();
}
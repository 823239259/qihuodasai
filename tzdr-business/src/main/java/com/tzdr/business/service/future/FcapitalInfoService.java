package com.tzdr.business.service.future;

import java.math.BigDecimal;

import com.tzdr.common.baseservice.BaseService;
import com.tzdr.domain.web.entity.future.FcapitalInfo;


/**
 * 
 * 
 * <p></p>
 * @author LiuQing
 * @see
 * @version 2.0
 * 2015年7月23日下午4:14:15
 */
public interface FcapitalInfoService extends BaseService<FcapitalInfo>  {
	
	public BigDecimal queryCumulativeTrans(String faid);
	
}

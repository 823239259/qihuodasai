package com.tzdr.business.service.future;

import java.math.BigDecimal;

import com.tzdr.common.baseservice.BaseService;
import com.tzdr.domain.web.entity.future.FuserTrade;


/**
 * 
 * 
 * <p></p>
 * @author LiuQing
 * @see
 * @version 2.0
 * 2015年7月23日下午4:14:15
 */
public interface FuserTradeService extends BaseService<FuserTrade>  {
	
	public BigDecimal queryCautionMoney(String faid, Integer state, Integer actualType);
	
	public int getRowCount(String faid, Integer state, Integer actualType);
	
	public BigDecimal queryBusinessMoney(String faid, Integer state, Integer actualType);
	
	public BigDecimal queryGainMoney(String faid, Integer state, Integer actualType);

}

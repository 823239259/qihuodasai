package com.tzdr.business.service.userTrade;



import java.util.Map;

import com.tzdr.common.baseservice.BaseService;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.web.support.EasyUiPageInfo;
import com.tzdr.domain.web.entity.FHandleFtseUserTrade;
import com.tzdr.domain.web.entity.FSimpleFtseUserTrade;

/**
 * 
 * 
 * <p></p>
 * @author WangPinQun
 * @see 
 * FHandleFtseUserTradeService
 * @version 2.0
 * 2015年9月24日下午17:33:13
 */
public interface FHandleFtseUserTradeService extends BaseService<FHandleFtseUserTrade> {
	/**
	 * 页面列表的分页查询
	 * @param easyUiPage
	 * @param searchParams
	 * @param type
	 * @return
	 */
	PageInfo<Object> queryPageDatas(EasyUiPageInfo easyUiPage,Map<String, Object> searchParams,int businessType) throws Exception;
	
	/**
	 * 在开户中；操盘中；审核不通过；结算的时候，保存方案操作记录
	 * @param simpleFtseUserTrade
	 */
//	void saveHandleFtseUserTrade(FSimpleFtseUserTrade simpleFtseUserTrade) throws Exception;
}

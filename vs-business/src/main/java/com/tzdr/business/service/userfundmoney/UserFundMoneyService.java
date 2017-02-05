package com.tzdr.business.service.userfundmoney;

import com.tzdr.common.baseservice.BaseService;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.domain.vo.UserFundMoneyVo;
import com.tzdr.domain.web.entity.UserFund;

/**
 * 
 * <P>title:@CautionMoneyService.java</p>																								
 * <P>Description：</p>
 * <p>Copyright: Copyright (c) 2015 tzdr</p>
 * <p>Company: 上海信宏</p>
 * History：
 * @author:zhangjun
 * @date 2015年3月31日
 * @version 1.0
 */
public interface UserFundMoneyService extends BaseService<UserFund>{

	PageInfo<UserFundMoneyVo> queryData(PageInfo<UserFundMoneyVo> dataPage,
			UserFundMoneyVo userFundMoneyVo,String type);

	
}


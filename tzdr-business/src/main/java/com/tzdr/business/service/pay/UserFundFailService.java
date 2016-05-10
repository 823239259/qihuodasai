package com.tzdr.business.service.pay;


import java.util.List;

import com.tzdr.common.baseservice.BaseService;
import com.tzdr.domain.web.entity.UserFundFail;


/**
 * 明细备份失败Service
 * <P>title:@PayService.java</p>																								
 * <P>Description：</p>
 * <p>Copyright: Copyright (c) 2014 Tzdr</p>
 * <p>Company: 上海信闳</p>
 * History：
 * @author:WangPinQun
 * @date 2015年06月25日
 * @version 1.0
 */
public interface UserFundFailService extends BaseService<UserFundFail>{

	/**
	 * 根据类别处理明细失败备份数据
	 * @param type  类别
	 */
	public void saveUserFundByStatusAndType(int type);
}

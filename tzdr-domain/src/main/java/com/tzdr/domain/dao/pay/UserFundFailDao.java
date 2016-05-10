package com.tzdr.domain.dao.pay;



import java.util.List;

import com.tzdr.common.dao.BaseJpaDao;
import com.tzdr.domain.web.entity.UserFundFail;

/**
 * 明细备份失败dao
 * <P>title:@SecurityInfoDao.java</p>																								
 * <P>Description：</p>userId
 * <p>Copyright: Copyright (c) 2014 tzdr</p>
 * <p>Company: 上海信闳</p>
 * History：
 * @author:wangpinqun
 * @date 2015年06月25日
 * @version 1.0
 */
public interface UserFundFailDao extends BaseJpaDao<UserFundFail, String> {

	/**
	 * 根据处理状态和类别获取明细失败备份数据
	 * @param type  类别
	 * @param runStatus  0：未处理 ，1：已处理
	 * @return List<UserFundFail>
	 */
	public List<UserFundFail> findByRunStatusAndType(short runStatus,int type);
}

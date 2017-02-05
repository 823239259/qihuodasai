package com.tzdr.domain.dao.securityInfo;


import com.tzdr.common.dao.BaseJpaDao;
import com.tzdr.domain.web.entity.UserVerified;
import com.tzdr.domain.web.entity.WUser;

/**
 * 安全信息dao
 * <P>title:@SecurityInfoDao.java</p>																								
 * <P>Description：</p>userId
 * <p>Copyright: Copyright (c) 2014 tzdr</p>
 * <p>Company: 上海信闳</p>
 * History：
 * @author:zhangjun
 * @date 2014年12月24日
 * @version 1.0
 */
public interface SecurityInfoDao extends BaseJpaDao<UserVerified, String> {

	 UserVerified findByWuser(WUser user);

	 /**
	  * 根据用户idcard查用户
	  * @param cardNo
	  * @return
	  * @date 2014年12月25日
	  * @author zhangjun
	  */
	UserVerified findByIdcard(String cardNo);



}

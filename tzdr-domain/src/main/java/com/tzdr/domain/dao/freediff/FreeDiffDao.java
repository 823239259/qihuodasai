package com.tzdr.domain.dao.freediff;


import org.springframework.data.jpa.repository.Query;

import com.tzdr.common.dao.BaseJpaDao;
import com.tzdr.domain.web.entity.FreeDiff;
import com.tzdr.domain.web.entity.FreezeFailInfo;

/**
 * 佣金差dao
 * <P>title:@FreeDiffDao.java</p>																								
 * <P>Description：</p>userId
 * <p>Copyright: Copyright (c) 2014 tzdr</p>
 * <p>Company: 上海信闳</p>
 * History：
 * @author:zhangjun
 * @date 2014年12月24日
 * @version 1.0
 */
public interface FreeDiffDao extends BaseJpaDao<FreeDiff, String> {
	@Query("from FreeDiff where account=?1 and addtime=?2 and type=?3")
	FreeDiff findData(String account,long time,String type);

}

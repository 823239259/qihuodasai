package com.tzdr.domain.dao.drawBlackList;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.tzdr.common.dao.BaseJpaDao;
import com.tzdr.domain.web.entity.DrawBlackList;

/**
 * 
 * <P>title:@DrawBlackListDao.java</p>																								
 * <P>Description：</p>
 * <p>Copyright: Copyright (c) 2015 tzdr</p>
 * <p>Company: 上海信宏</p>
 * History：
 * @author:zhangjun
 * @date 2015年5月22日
 * @version 1.0
 */
public interface DrawBlackListDao extends BaseJpaDao<DrawBlackList, String>{

	public DrawBlackList findByUid(String uid);

	@Query("from DrawBlackList where uid=?1")
	public List<DrawBlackList> getEntityByUid(String uid);
}


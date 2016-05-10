package com.tzdr.domain.dao.togetherTrade;

import java.util.List;

import com.tzdr.common.dao.BaseJpaDao;
import com.tzdr.domain.web.entity.TogetherUserList;

public interface TogetherUserListDao extends BaseJpaDao<TogetherUserList, String>{

	List<TogetherUserList> findByGidOrderByBuyTimeDesc(String groupId);

}

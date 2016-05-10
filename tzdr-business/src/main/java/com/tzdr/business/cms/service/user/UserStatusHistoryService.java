package com.tzdr.business.cms.service.user;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.tzdr.common.auth.Searchable;
import com.tzdr.common.baseservice.BaseServiceImpl;
import com.tzdr.domain.cms.entity.user.User;
import com.tzdr.domain.cms.entity.user.UserStatus;
import com.tzdr.domain.cms.entity.user.UserStatusHistory;
import com.tzdr.domain.dao.user.UserStatusHistoryDao;

/**
 * <p>
 * User: Lin Feng
 * <p>
 * Version: 1.0
 */
@Service
public class UserStatusHistoryService extends
		BaseServiceImpl<UserStatusHistory, UserStatusHistoryDao> {

	public void log(User opUser, User user, UserStatus newStatus, String reason) {
		UserStatusHistory history = new UserStatusHistory();
		history.setUser(user);
		history.setOpUser(opUser);
		history.setOpDate(new Date());
		history.setStatus(newStatus);
		history.setReason(reason);
		save(history);
	}

	public UserStatusHistory findLastHistory(final User user) {
		Searchable searchable = Searchable.newSearchable()
				.addSearchParam("user_eq", user)
				.addSort(Sort.Direction.DESC, "opDate").setPage(0, 1);

		Page<UserStatusHistory> page = findAll(searchable);

		if (page.hasContent()) {
			return page.getContent().get(0);
		}
		return null;
	}

	public String getLastReason(User user) {
		UserStatusHistory history = findLastHistory(user);

		if (history == null) {
			return "";
		}
		return history.getReason();
	}
}

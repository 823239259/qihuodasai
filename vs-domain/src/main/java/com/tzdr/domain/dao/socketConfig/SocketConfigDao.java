package com.tzdr.domain.dao.socketConfig;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.tzdr.common.dao.BaseJpaDao;
import com.tzdr.domain.web.entity.SocketConfig;

public interface SocketConfigDao extends BaseJpaDao<SocketConfig, String>{
	/**
	 * app版本号获取socketconfig
	 * @param appVersion
	 * @return
	 */
	@Query("from SocketConfig where appVersion = ?1 and deleted = false")
	public List<SocketConfig> findByAppVersion(String appVersion);
}

package com.tzdr.business.service.socketconfig;

import java.util.List;
import java.util.Map;

import com.tzdr.common.baseservice.BaseService;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.web.support.EasyUiPageInfo;
import com.tzdr.domain.web.entity.SocketConfig;

public interface SocketConfigService extends BaseService<SocketConfig>{
	/**
	 * 根据app版本号socketconfig
	 * @param version
	 * @return
	 */
	public List<SocketConfig> findSocketConfigByAppVersion(String version);
	/**
	 * 获取socket配置
	 * @return
	 */
	public PageInfo<Object> doGetSocketConfig(EasyUiPageInfo easyUiPage,Map<String, Object> map);
}

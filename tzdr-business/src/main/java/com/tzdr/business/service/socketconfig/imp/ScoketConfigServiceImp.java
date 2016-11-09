package com.tzdr.business.service.socketconfig.imp;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.tzdr.business.service.socketconfig.SocketConfigService;
import com.tzdr.common.baseservice.BaseServiceImpl;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.web.support.EasyUiPageInfo;
import com.tzdr.common.web.support.MultiListParam;
import com.tzdr.domain.dao.socketConfig.SocketConfigDao;
import com.tzdr.domain.vo.SocketConfigVo;
import com.tzdr.domain.web.entity.SocketConfig;

@Service("socketConfigService")
@Transactional
public class ScoketConfigServiceImp extends BaseServiceImpl<SocketConfig, SocketConfigDao> implements SocketConfigService{
	@Override
	public List<SocketConfig> findSocketConfigByAppVersion(String appVersion) {
		return getEntityDao().findByAppVersion(appVersion);
	}
	@Override
	public PageInfo<Object> doGetSocketConfig(EasyUiPageInfo easyUiPage, Map<String, Object> map) {
		PageInfo<Object> pageInfo = new PageInfo<Object>(easyUiPage.getRows(),easyUiPage.getPage());
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT c.id,\n");
		sql.append("		c.app_version as appVersion,\n");
		sql.append("		c.socket_url as socketUrl,\n");
		sql.append("		c.socket_version as socketVersion,\n");
		sql.append("		c.is_model as isModel\n");
		sql.append("  FROM socket_config c\n");
		
		List<Object> params = Lists.newArrayList();
		MultiListParam multilistParam = new MultiListParam(easyUiPage,map, params, sql.toString());
		pageInfo = multiListPageQuery(multilistParam, SocketConfigVo.class);
		return pageInfo;
	}
}

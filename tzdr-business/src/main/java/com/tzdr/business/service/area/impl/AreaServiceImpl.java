package com.tzdr.business.service.area.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import jodd.util.StringUtil;

import com.tzdr.business.service.area.AreaService;
import com.tzdr.common.baseservice.BaseServiceImpl;
import com.tzdr.domain.dao.area.AreaDao;
import com.tzdr.domain.web.entity.Area;

/**
* @Description: TODO(区域业务信息管理业务实现层)
* @ClassName: AreaServiceImpl
* @author wangpinqun
* @date 2014年12月30日 上午9:49:33
 */
@Service("areaService")
public class AreaServiceImpl extends BaseServiceImpl<Area, AreaDao> implements AreaService {

	@Override
	public List<Area> findByPidOrderBySortAsc(String pid) {
		if(StringUtil.isBlank(pid)){
			return null;
		}
		return getEntityDao().findByPidOrderBySortAsc(pid);
	}

	@Override
	public List<Area> findAreaByIds(List<String> ids) {
		return getEntityDao().findByIdIn(ids);
	}
}

package com.tzdr.domain.dao.area;

import java.util.List;

import com.tzdr.common.dao.BaseJpaDao;
import com.tzdr.domain.web.entity.Area;

/**
* @Description: TODO(区域业务信息管理持久层)
* @ClassName: AreaDao
* @author wangpinqun
* @date 2014年12月30日 上午9:43:42
 */
public interface AreaDao extends BaseJpaDao<Area, Long>{
	
	/**
	* @Description: TODO(根据父编号获取区域列表信息)
	* @Title: findByPidOrderBySortAsc
	* @param pid    父节点编号
	* @return List<Area>    返回类型
	 */
	public List<Area> findByPidOrderBySortAsc(String pid);
	
	/**
	* @Description: TODO(根据区域编号获取区域信息)
	* @Title: findByIdIn
	* @param ids  区域编号集
	* @return List<Area>    返回类型
	 */
	public List<Area> findByIdIn(List<String> ids);

}

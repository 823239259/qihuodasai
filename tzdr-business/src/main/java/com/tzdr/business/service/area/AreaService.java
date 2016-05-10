package com.tzdr.business.service.area;

import java.util.List;

import com.tzdr.common.baseservice.BaseService;
import com.tzdr.domain.web.entity.Area;
/**
* @Description: TODO(区域业务信息管理业务接口层)
* @ClassName: AreaService
* @author wangpinqun
* @date 2014年12月30日 上午9:47:51
 */
public interface AreaService  extends BaseService<Area>{

	/**
	* @Description: TODO(根据父编号获取区域列表信息)
	* @Title: findByPidOrderBySortAsc
	* @param pid    父节点编号
	* @return List<Area>    返回类型
	 */
	public List<Area> findByPidOrderBySortAsc(String pid);
	
	/**
	* @Description: TODO(根据区域编号集获取区域信息)
	* @Title: findAreaByIds
	* @param ids   区域编号集
	* @return List<Area>    返回类型
	 */
	public List<Area> findAreaByIds(List<String> ids);
}

package com.tzdr.business.service.generalize;




import java.util.List;
import java.util.Map;

import com.tzdr.common.baseservice.BaseService;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.domain.vo.AgentChildVo;
import com.tzdr.domain.web.entity.GeneralizeVisit;

/**
* @Description: TODO(推广业务信息管理接口层)
* @ClassName: GeneralizeService
* @author wangpinqun
* @date 2015年1月9日 下午3:05:46
 */
public interface GeneralizeService extends BaseService<GeneralizeVisit>{

	/**
	* @Description: TODO(保存访问推广信息)
	* @Title: saveGeneralizeVisit
	* @param generalizeVisit   访问推广信息
	* @return void    返回类型
	 */
	public void saveGeneralizeVisit(GeneralizeVisit generalizeVisit);
	
	/**
	* @Description: TODO(根据条件，获取某用户的下级用户总量)
	* @Title: getWuserTotalChild
	* @param uid   用户编号
	* @return Integer    返回类型
	 */
	public Integer getWuserTotalChild(String uid);
	
	/**
	* @Description: TODO(根据条件，获取某用户推广的总访问量)
	* @Title: getGeneralizeVisitCount
	* @param uid  用户编号
	* @return Long    返回类型
	 */
	public Long getGeneralizeVisitCount(String uid);
	
	/**
	* @Description: TODO(根据条件，获取某用户推广的总IP量)
	* @Title: getGeneralizeVisitClieantIpCount
	* @param uid  用户编号
	* @return Long    返回类型
	 */
	public Long getGeneralizeVisitClieantIpCount(String uid);
	
	/**
	 * 
	* @Description: TODO(根据条件，获取某用户的下级用户信息)
	* @Title: queryPageGeneralizeVisitUserVo
	* @param parentId        用户父节点
	* @param pageInfo  分页信息
	* @return PageInfo<Object>    返回类型
	 */
	public PageInfo<Object> queryPageGeneralizeVisitUserVo(String parentId,PageInfo<Object> pageInfo);
	
	/**
	* @Description: TODO(根据条件，按分页获取某用户推广的ip访问信息)
	* @Title: queryPageGeneralizeVisitVo
	* @param uid   用户编号
	* @param pageInfo  分页信息
	* @return PageInfo<Object>    返回类型
	 */
	public PageInfo<Object> queryPageGeneralizeVisitVo(String uid,PageInfo<Object> pageInfo);
	
	/**
	 * 查询直属下级数量
	 * @param parentId String
	 * @return List<AgentChildVo>
	 */
	public List<AgentChildVo> queryChildsCountByParentId(String parentId); 
	
	/**
	 * 查询出所有下级数量
	 * @param parentId String
	 * @return int
	 */
	public int queryChildsSize(String parentId);
	
	
	/**
	 * 
	 * @param parentId
	 * @return
	 */
	public Map<String,String> allChildUids(String parentId);
	
	
}

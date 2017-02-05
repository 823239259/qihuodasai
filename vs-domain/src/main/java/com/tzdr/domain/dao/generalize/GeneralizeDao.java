package com.tzdr.domain.dao.generalize;

import org.springframework.data.jpa.repository.Query;

import com.tzdr.common.dao.BaseJpaDao;
import com.tzdr.domain.web.entity.GeneralizeVisit;
/**
* @Description: TODO(推广业务信息管理持久层)
* @ClassName: GeneralizeDao
* @author wangpinqun
* @date 2015年1月9日 下午3:02:40
 */
public interface GeneralizeDao extends BaseJpaDao<GeneralizeVisit, String>{

	
	/**
	* @Description: TODO(根据条件，获取某用户推广的总访问量)
	* @Title: getCountByWuser
	* @param wuser      用户信息
	* @return Long    返回类型
	 */
	@Query("select count(g.id) from GeneralizeVisit g where g.generalizeId=?1")
	public Long getCountByGeneralizeId(String generalizeId);
}

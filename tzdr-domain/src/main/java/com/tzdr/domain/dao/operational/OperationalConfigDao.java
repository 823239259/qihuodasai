package com.tzdr.domain.dao.operational;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.tzdr.common.dao.BaseJpaDao;
import com.tzdr.domain.web.entity.OperationalConfig;

/**
 * 
 * @author Administrator
 *
 */
public interface OperationalConfigDao extends BaseJpaDao<OperationalConfig, String>{

	List<OperationalConfig> findByIsEnableTrueAndDeletedFalseAndType(int type);

	List<OperationalConfig> findByIsEnableTrueAndDeletedFalseAndTypeAndParentConfig(int type,
			OperationalConfig config);

	OperationalConfig getOperationalConfigByIsEnableTrueAndDeletedFalseAndName(String colname);

	/**
	 * 查询banner
	 * @param type
	 * @param status
	 * @return
	 * @date 2015年2月9日
	 * @author zhangjun
	 */
	@Query("from OperationalConfig where type=?1 and isEnable='1' and deleted='0'  and valueType=?2")
	List<OperationalConfig> findBanners(int type, int status);
 
	@Query("from OperationalConfig where type=?1 and isEnable='1' and deleted='0' order by valueType asc")
	List<OperationalConfig> findBanner(int type);
	
	/**
	 * 根据类型查询 并且 名称不再范围内的 数据
	 * @param type
	 * @param names
	 * @return
	 */
	List<OperationalConfig> findByIsEnableTrueAndDeletedFalseAndTypeAndNameNotIn(int type,List<String> names);
}

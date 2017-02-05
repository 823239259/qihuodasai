package com.tzdr.domain.hkstock.dao;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tzdr.common.dao.BaseJpaDao;
import com.tzdr.domain.hkstock.entity.HkFreeDiff;

/**
 * 涌金过户费
 * @author zhouchen
 * 2015年10月16日 上午11:18:57
 */
public interface HkFreeDiffDao extends BaseJpaDao<HkFreeDiff, String> {
	@Query("from HkFreeDiff where account=?1 and addtime=?2 and type=?3")
	HkFreeDiff findData(String account,long time,String type);

	/**
	 * 以账户、生产时间和类型为条件，获取记录条数
	 * @param account
	 * @param addtime
	 * @param type
	 * @return
	 */
	@Query(value="SELECT COUNT(0) FROM hk_free_diff WHERE account=:account AND addtime=:addtime AND type=:type", nativeQuery=true)
	public int getCount(@Param("account")String account, @Param("addtime")Long addtime, @Param("type")String type);
}

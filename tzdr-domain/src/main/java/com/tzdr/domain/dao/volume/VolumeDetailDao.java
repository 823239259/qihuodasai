package com.tzdr.domain.dao.volume;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tzdr.common.dao.BaseJpaDao;
import com.tzdr.domain.web.entity.VolumeDetail;

/**
 * 
 * 
 * <p></p>
 * @author LiuQing
 * @see
 * @version 2.0
 * 2015年4月27日下午5:39:58
 */
public interface VolumeDetailDao extends BaseJpaDao<VolumeDetail, String> {
	
	@Query("from VolumeDetail  where volumeDeductible.dealStartDateValue<= :time and volumeDeductible.dealEndDateValue>= :time and volumeDeductible.endDateValue> :time and volumeDeductible.stateValue=1 and (uid IS NULL OR uid='')")
	public List<VolumeDetail> findNewVolumeDetail(@Param("time")long time);
	
}

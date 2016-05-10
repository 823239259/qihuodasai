package com.tzdr.domain.dao.spot;

import java.util.List;

import com.tzdr.common.dao.BaseJpaDao;
import com.tzdr.domain.web.entity.SpotBooking;

/**
 * 现货预约 持久层
 * @ClassName SpotBookingDao
 * @author L.Y
 * @email liuyuan@peigubao.com
 * @date 2015年10月9日
 */
public interface SpotBookingDao extends BaseJpaDao<SpotBooking, String> {
	
	/**
	 * 查询是否有预约过
	 * @MethodName findByUid
	 * @author L.Y
	 * @date 2015年10月9日
	 * @param uid
	 * @return
	 */
	List<SpotBooking> findByUid(String uid);
	
//	public List<SpotBooking> findByIdIsNotNullOrderByTimesAsc();

}
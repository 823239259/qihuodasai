package com.tzdr.domain.dao.spot;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.tzdr.common.dao.BaseJpaDao;
import com.tzdr.domain.web.entity.SpotSalesman;

/**
 * 现货销售员持久层
 * @ClassName SpotSalesmanDao
 * @author L.Y
 * @email liuyuan@peigubao.com
 * @date 2015年10月8日
 */
public interface SpotSalesmanDao extends BaseJpaDao<SpotSalesman, String> {

	/**
	 * 根据手机号查询
	 * @MethodName findByMobile
	 * @author L.Y
	 * @date 2015年10月9日
	 * @param mobile 手机号
	 * @return
	 */
	SpotSalesman findByMobile(String mobile);
	
	/**
	 * 获取ctn 最小的记录
	 * @MethodName findOrderByCtnAsc
	 * @author L.Y
	 * @date 2015年10月9日
	 * @param pagable 分页对象
	 * @return
	 */
	List<SpotSalesman> findByDeletedFalseOrderByCtnAsc(Pageable pagable);

}
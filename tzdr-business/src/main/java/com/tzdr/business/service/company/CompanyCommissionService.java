package com.tzdr.business.service.company;

import java.util.List;

import com.tzdr.common.baseservice.BaseService;
import com.tzdr.domain.web.entity.CompanyCommission;

/**
* @Description: TODO(公司员工佣金排行榜数据)
* @ClassName: CompanyCommissionDao
* @author wangpinqun
* @date 2015年3月11日 下午5:31:05
 */
public interface CompanyCommissionService extends BaseService<CompanyCommission>{

	public List<CompanyCommission> findOrderByTotalCommissionDesc();
}

package com.tzdr.domain.dao.company;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.tzdr.common.dao.BaseJpaDao;
import com.tzdr.domain.web.entity.CompanyCommission;

/**
* @Description: TODO(公司员工佣金排行榜数据)
* @ClassName: CompanyCommissionDao
* @author wangpinqun
* @date 2015年3月11日 下午5:31:05
 */
public interface CompanyCommissionDao extends BaseJpaDao<CompanyCommission, String>,JpaSpecificationExecutor<CompanyCommission> {

	public List<CompanyCommission> findAllByOrderByTotalCommissionDesc();
}

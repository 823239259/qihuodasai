package com.tzdr.business.service.company.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tzdr.business.service.company.CompanyCommissionService;
import com.tzdr.common.baseservice.BaseServiceImpl;
import com.tzdr.domain.dao.company.CompanyCommissionDao;
import com.tzdr.domain.web.entity.CompanyCommission;

/**
* @Description: TODO(公司员工佣金排行榜数据)
* @ClassName: CompanyCommissionDao
* @author wangpinqun
* @date 2015年3月11日 下午5:31:05
 */
@Service("companyCommissionService")
@Transactional
public class CompanyCommissionServiceImpl extends BaseServiceImpl<CompanyCommission, CompanyCommissionDao> implements CompanyCommissionService {

	@Override
	public List<CompanyCommission> findOrderByTotalCommissionDesc() {
		
		return getEntityDao().findAllByOrderByTotalCommissionDesc();
	}

}

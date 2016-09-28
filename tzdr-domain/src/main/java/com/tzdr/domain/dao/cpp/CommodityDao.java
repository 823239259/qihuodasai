package com.tzdr.domain.dao.cpp;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.tzdr.common.dao.BaseJpaDao;
import com.tzdr.domain.web.entity.Commodity;

public interface CommodityDao extends BaseJpaDao<Commodity, String>,JpaSpecificationExecutor<Commodity>{

}

package com.tzdr.domain.dao.cpp;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.tzdr.common.dao.BaseJpaDao;
import com.tzdr.domain.web.entity.Currency;

public interface CurrencyDao extends BaseJpaDao<Currency, String>,JpaSpecificationExecutor<Currency>{

}

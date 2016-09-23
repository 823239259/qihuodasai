package com.tzdr.domain.dao.cpp;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.tzdr.common.dao.BaseJpaDao;
import com.tzdr.domain.web.entity.Cpp;

public interface CppDao extends BaseJpaDao<Cpp, String>,JpaSpecificationExecutor<Cpp>{
	/*@Query(value = "SELECT ID as id,CurrencyNo currencyNo,CurrencyName currencyName,ExchangeRate exchangeRate FROM a_currency_list where id = ?1",nativeQuery = true)
	public List<Cpp> findList(Integer id);*/
}

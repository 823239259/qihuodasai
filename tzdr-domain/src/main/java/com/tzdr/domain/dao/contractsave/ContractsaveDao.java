package com.tzdr.domain.dao.contractsave;


import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.tzdr.common.dao.BaseJpaDao;
import com.tzdr.domain.web.entity.Contractsave;

public interface ContractsaveDao extends BaseJpaDao<Contractsave, String> {
	@Query("from Contractsave where programNo=?1 and uid=?2")
	List<Contractsave> findSafeData(String programNo, String uid);

}

package com.tzdr.olog.db.dao;

import java.util.Date;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.tzdr.common.dao.BaseJpaDao;
import com.tzdr.olog.db.domain.Olog;

/**
 * 操作日志 JPA Dao
 * 
 * 
 * @author Lin Feng
 * 
 */
public interface OlogDao extends BaseJpaDao<Olog, String> {

	@Modifying
	@Query("delete from Olog where operateTime < ?1")
	void cleanup(Date beforeDate);

}

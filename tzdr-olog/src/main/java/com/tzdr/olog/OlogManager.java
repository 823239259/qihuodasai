package com.tzdr.olog;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.tzdr.common.domain.PageInfo;
import com.tzdr.olog.db.domain.Olog;

/**
 * Olog持久化和管理接口
 * 
 * @author LinFeng
 * 
 */
public interface OlogManager {

	void logger(Olog olog);

	List<Olog> query(Map<String, Object> conditions);

	PageInfo<Olog> query(Map<String, Object> conditions, PageInfo<Olog> pageInfo);

	PageInfo<Olog> query(Map<String, Object> conditions,
			PageInfo<Olog> pageInfo, Map<String, Boolean> sorts);

	void cleanup(Date beforeDate);

}

package com.tzdr.olog.db.service;

import java.util.Date;

import com.tzdr.common.baseservice.BaseService;
import com.tzdr.olog.db.domain.Olog;

/**
 * 操作日志 Service
 *
 * Date: 2013-02-28 22:59:16
 *
 * @author Lin Feng
 *
 */
public interface OlogService extends BaseService<Olog> {

	void cleanup(Date beforeDate);
}

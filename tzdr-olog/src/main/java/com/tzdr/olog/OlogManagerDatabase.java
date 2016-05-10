package com.tzdr.olog;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.exception.BusinessException;
import com.tzdr.olog.db.domain.Olog;
import com.tzdr.olog.db.service.OlogService;

public class OlogManagerDatabase implements OlogManager {

	private static final Logger logger = LoggerFactory
			.getLogger("OlogManagerDatabase");

	@Autowired
	OlogService ologServcie;

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	@Override
	public void logger(Olog olog) {
		try {
			ologServcie.save(olog);
		} catch (Exception e) {
			logger.warn("写入Olog失败:" + e.getMessage());
		}
	}

	@Override
	public List<Olog> query(Map<String, Object> conditions) {
		try {
			return ologServcie.query(conditions, null);
		} catch (Exception e) {
			logger.warn("查询Olog失败:" + e.getMessage());
			throw new BusinessException(e.getMessage());
		}
	}

	@Override
	public PageInfo<Olog> query(Map<String, Object> conditions,
			PageInfo<Olog> pageInfo) {
		try {
			return ologServcie.query(pageInfo, conditions, null);
		} catch (Exception e) {
			logger.warn("分页查询Olog失败:" + e.getMessage());
			throw new BusinessException(e.getMessage());
		}
	}

	@Override
	public PageInfo<Olog> query(Map<String, Object> conditions,
			PageInfo<Olog> pageInfo, Map<String, Boolean> sorts) {
		try {
			return ologServcie.query(pageInfo, conditions, sorts);
		} catch (Exception e) {
			logger.warn("分页查询Olog失败:" + e.getMessage());
			throw new BusinessException(e.getMessage());
		}
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	@Override
	public void cleanup(Date beforeDate) {
		try {
			ologServcie.cleanup(beforeDate);
		} catch (Exception e) {
			logger.warn("数据清除失败:" + e.getMessage());
		}
	}

}

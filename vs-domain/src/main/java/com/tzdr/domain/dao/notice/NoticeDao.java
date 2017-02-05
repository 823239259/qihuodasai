package com.tzdr.domain.dao.notice;

import java.util.List;

import com.tzdr.common.dao.BaseJpaDao;
import com.tzdr.domain.web.entity.Notice;

/**
 * 系统公告数据操作
 * @Description: TODO(支持增删改查) 
 * @author liuhaichuan
 * @date 2015年4月28日
 *
 */
public interface NoticeDao extends BaseJpaDao<Notice, String>{

	List<Notice> findByDeletedFalseAndStatus(Integer status);
	
	/**
	 * 配股宝公告下查询
	 */
	List<Notice> findByDeletedFalseAndStatusAndDiff(Integer status,Integer diff);
}

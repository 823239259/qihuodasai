package com.tzdr.business.service.notice;

import java.util.List;

import com.tzdr.common.baseservice.BaseService;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.domain.vo.NoticeVo;
import com.tzdr.domain.web.entity.Notice;

/**
 * 系统公告业务操作
 * @Description: TODO(增删改查、分页查询) 
 * @author liuhaichuan
 * @date 2015年4月28日
 *
 */
public interface NoticeService extends BaseService<Notice> {
	
	/**
	 * 修改公告
	 * @param notice
	 */
	public void updateNotice(Notice notice);
	
	/**
	 * 分页查询
	 * @param data
	 * @param notice
	 * @return
	 */
	public PageInfo<NoticeVo> findByPage(PageInfo<NoticeVo> data, NoticeVo notice);

	/**
	 * 查询公告
	 * @MethodName findNotices
	 * @author L.Y
	 * @date 2015年4月30日
	 * @param status 状态
	 * @return
	 */
	public List<Notice> findNotices(Integer status);
	
	/**
	 * @author ZHAOZHAO
	 * @param status  状态
	 * @param diff 1.投资达人    2.配股宝
	 * @return
	 */
	public List<Notice> findPGBNotices(Integer status,Integer diff);
}

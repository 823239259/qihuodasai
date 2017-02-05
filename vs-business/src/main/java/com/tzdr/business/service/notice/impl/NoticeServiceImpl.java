package com.tzdr.business.service.notice.impl;


import java.util.List;

import jodd.util.StringUtil;

import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.tzdr.business.service.notice.NoticeService;
import com.tzdr.common.baseservice.BaseServiceImpl;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.domain.cms.entity.organization.Organization;
import com.tzdr.domain.cms.entity.user.User;
import com.tzdr.domain.dao.notice.NoticeDao;
import com.tzdr.domain.vo.NoticeVo;
import com.tzdr.domain.web.entity.Notice;

/**
 * 系统公告业务操作实现
 * @Description: TODO(实现增删改查、分页查询) 
 * @author liuhaichuan
 * @date 2015年4月28日
 *
 */
@Service("noticeServiceImpl")
public class NoticeServiceImpl extends BaseServiceImpl<Notice, NoticeDao> implements NoticeService {

	@Override
	public void updateNotice(Notice notice) {
		// TODO Auto-generated method stub
		super.update(notice);
	}

	@SuppressWarnings("unchecked")
	@Override
	public PageInfo<NoticeVo> findByPage(PageInfo<NoticeVo> data,
			NoticeVo notice) {
		// TODO Auto-generated method stub
		String sql = "SELECT n.id, n.content, n.status,n.diff FROM w_notice n WHERE 1=1";
		List<Object> params = Lists.newArrayList();
		if (StringUtil.isNotBlank(notice.getContent())) {
			sql+="  and n.content like ?";
			params.add("%"+notice.getContent()+"%");
		}
		int status=notice.getStatus();
		if(status==1){
			sql+=" and n.status=?";
			params.add(status);
		}else if(status==2){
			sql+=" and n.status=?";
			params.add(status);
		}
		return getEntityDao().queryPageBySql(data, sql, NoticeVo.class, null, params.toArray());
	}

	@Override
	public List<Notice> findNotices(Integer status) {
		return getEntityDao().findByDeletedFalseAndStatusAndDiff(status, 1);
	}
	
	@Override
	public List<Notice> findPGBNotices(Integer status, Integer diff) {
		return getEntityDao().findByDeletedFalseAndStatusAndDiff(status, diff);
	}
}

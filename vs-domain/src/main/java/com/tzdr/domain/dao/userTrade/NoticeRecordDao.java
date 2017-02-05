package com.tzdr.domain.dao.userTrade;

import java.util.List;

import com.tzdr.common.dao.BaseJpaDao;
import com.tzdr.domain.web.entity.NoticeRecord;

/**
 * 通知记录
 * @zhouchen
 * 2015年1月12日
 */
public interface NoticeRecordDao extends BaseJpaDao<NoticeRecord, String> {
	
	
	public  List<NoticeRecord>  findByGroupIdAndType(String groupId,Integer type);
	
	/**
	 * 根据用户id、时间段和类型查找此时间是否 过这个类型的短信
	 * @param startTime
	 * @param endTime
	 * @param type
	 * @return
	 */
	public  List<NoticeRecord>  findByNoticeTimeBetweenAndTypeAndUid(Long startTime,Long endTime,Integer type,String uid);
}

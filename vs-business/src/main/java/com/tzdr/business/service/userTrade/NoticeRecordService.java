package com.tzdr.business.service.userTrade;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.tzdr.common.baseservice.BaseServiceImpl;
import com.tzdr.common.utils.ConnditionVo;
import com.tzdr.common.utils.Dates;
import com.tzdr.domain.dao.userTrade.NoticeRecordDao;
import com.tzdr.domain.vo.ArrearsEndDetail;
import com.tzdr.domain.web.entity.NoticeRecord;

/**
 * @author zhouchen
 * @version 创建时间：2015年1月12日 下午4:58:44
 * 补仓提醒、余额不足提醒 通知记录
 */
@Service
public class NoticeRecordService extends BaseServiceImpl<NoticeRecord,NoticeRecordDao> {
	
	public List<NoticeRecord> getByGroupIdAndType(String groupId,Integer type){
		return getEntityDao().findByGroupIdAndType(groupId, type);
	}
	
	/**
	 * 根据类型和用户id 查询当天是否发送过短信
	 * @param type
	 * @return
	 */
	public List<NoticeRecord> getByTypeAndUid(String uid,Integer type){
		return getEntityDao().findByNoticeTimeBetweenAndTypeAndUid(Dates.getCurrentLongDay(),Dates.getLastLongDay(), type,uid);
	}
	
	
public List<ArrearsEndDetail> list(ConnditionVo connVo){

		Map<String, Object> map=new HashMap<String, Object>();
		map.put("groupId", connVo.getValueStr("groupId"));
		
		StringBuffer sqlBuf = new StringBuffer();
		sqlBuf.append("SELECT FROM_UNIXTIME(wuf.addtime,'%Y-%m-%d %H:%i:%S') as time, ABS(wuf.money) AS money "
				+ "FROM w_user_trade wut,w_user_fund wuf "
				+ "WHERE wuf.lid = wut.group_id AND wuf.pay_status = 0 AND wuf.money < 0 AND wut.status = 1 AND wut.group_id=:groupId");
		return  this.getEntityDao().queryDataByParamsSql(sqlBuf.toString(),
				ArrearsEndDetail.class,map, connVo);
	}
}

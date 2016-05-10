package com.tzdr.business.service.api.yjfinance;

import java.util.HashMap;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.tzdr.common.baseservice.BaseServiceImpl;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.utils.ConnditionVo;
import com.tzdr.common.utils.TypeConvert;
import com.tzdr.domain.dao.identity.IdentityCardHistoryDao;
import com.tzdr.domain.web.entity.IdentityCardHistory;


/**
 * @LinFeng
 * 2015年4月14日
 */
@Service
@Transactional
public class IdentityCardHistoryService extends BaseServiceImpl<IdentityCardHistory,IdentityCardHistoryDao>{
	

	public PageInfo list(ConnditionVo connVo,PageInfo page){
		
		String starttimeStr_start = connVo.getValueStr("starttimeStr_start");
		String starttimeStr_end = connVo.getValueStr("starttimeStr_end");
		long starttimeLong_start=0;
		long starttimeLong_end=0;
		StringBuffer sqlBuf = new StringBuffer();
		Map<String, Object> map=new HashMap<String, Object>();	
		sqlBuf.append(" SELECT id_card,name,sex,birthday,result,FROM_UNIXTIME(create_time, '%Y-%m-%d %H:%i:%S') as create_time_string  "
				+ " FROM identity_card_history where 1=1 ");
		if (starttimeStr_start != null && starttimeStr_end != null) {
			starttimeLong_start=TypeConvert.strToZeroDate(starttimeStr_start,0).getTime()/1000;
			starttimeLong_end=TypeConvert.strToZeroDate(starttimeStr_end,1,-1).getTime()/1000;
			sqlBuf.append(" and create_time>=:beginTime and create_time<=:endTime");
			map.put("beginTime", starttimeLong_start);
			map.put("endTime", starttimeLong_end);
		}
		sqlBuf.append(" ORDER BY create_time DESC ");
		
	
		return  this.getEntityDao().queryDataByParamsSql(page,sqlBuf.toString(),
				IdentityCardHistory.class,map, connVo);
	}

}
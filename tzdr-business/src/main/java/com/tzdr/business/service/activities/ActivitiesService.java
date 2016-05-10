package com.tzdr.business.service.activities;

import java.util.List;
import java.util.Map;

import com.tzdr.domain.vo.ActivityProfitRecordVo;


/**
 * 活动页面接口
 * <P>title:@ActivitiesService.java</p>																								
 * <P>Description：</p>
 * <p>Copyright: Copyright (c) 2015 tzdr</p>
 * <p>Company: 上海信闳</p>
 * History：
 * @author:zhangjun
 * @date 2015年2月7日
 * @version 1.0
 */
public interface ActivitiesService {

	/**
	 * 排名
	 * @param date
	 * @return
	 * @date 2015年2月10日
	 * @author zhangjun
	 */
	public List<ActivityProfitRecordVo> getActivities(Long date);

	/**
	 * 收益增幅
	 * @param yesterday 昨天
	 * @param beforeyesterday 前天
	 * @return
	 * @date 2015年2月10日
	 * @author zhangjun
	 */
	public 	List<Map<String,String>> getGrowActivities(Long yesterday,
			Long beforeyesterday);

	/**
	 * 查询热门股
	 * @return
	 * @date 2015年2月10日
	 * @author zhangjun
	 */
	public List<ActivityProfitRecordVo> getStockData();
	
}

package com.tzdr.business.service.activity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jodd.util.StringUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tzdr.common.baseservice.BaseServiceImpl;
import com.tzdr.common.utils.Dates;
import com.tzdr.domain.dao.activity.ActivityUserDao;
import com.tzdr.domain.web.entity.activity.ActivityUser;

/**
 * 微信、web开箱壕礼参赛用户实现类
 * <P>
 * title:@ActivityUserServiceImpl.java
 * </p>
 * <P>
 * Description：
 * </p>
 * <p>
 * Copyright: Copyright (c) 2016 tzdr
 * </p>
 * <p>
 * Company: 上海信闳
 * </p>
 * History：
 * 
 * @author:WangPinQun
 * @date 2016年01月08日
 * @version 1.0
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class ActivityUserService extends BaseServiceImpl<ActivityUser, ActivityUserDao> {

	private static final Logger logger = LoggerFactory.getLogger(ActivityUserService.class);

	/**
	 * 获取某用户参赛信息
	 * 
	 * @param userId
	 *            用户编号
	 * @param activityType
	 *            活动类型：1-微信抽奖；2-web抽奖
	 * @return
	 */
	public ActivityUser getActivityUser(String userId, int activityType) {
		if (StringUtil.isBlank(userId)) {
			return null;
		}
		ActivityUser activityUser = null;
		List<ActivityUser> activityUserList = this.getEntityDao().findByUserIdAndActivityTypeAndDeletedFalse(userId, activityType);
		if (activityUserList != null && !activityUserList.isEmpty()) {
			activityUser = activityUserList.get(0);
		}
		return activityUser;
	}

	/**
	 * 判断该用户本周日是否持有方案，本周一是否有新开方案；返回map：code=3表示本周日没有开箱机会，code=4表示本周一没有开箱机会，code=
	 * 2表示开奖时间未到，code= 1表示开箱成功
	 * 
	 * @param activityType
	 * @param userId
	 * @return
	 */
	public Map<String, Object> isUnpackingUser(int activityType, String userId) {
		Map<String, Object> result = new HashMap<String, Object>();

		StringBuffer sql = new StringBuffer();
		sql.append(" select id, activity_type activityType, user_id userId, user_phone userPhone, unpacking_time unpackingTime, yesterday_trad_num yesterdayTradNum, unpacking_num unpackingNum ");
		sql.append(" from w_activity_user where activity_type = ? and user_id = ? and find_in_set(?, unpacking_time) limit 1 ");

		List<Object> params = new ArrayList<Object>();
		params.add(activityType);
		params.add(userId);
		params.add(Dates.format(new Date(), "yyyyMMdd"));

		Calendar cal = Calendar.getInstance();
		cal.clear();
		cal.setTime(new Date());
		int w = cal.get(Calendar.DAY_OF_WEEK);

		List<Map<String, Object>> list = this.getEntityDao().queryMapBySql(sql.toString(), new Object[] { activityType, userId, Dates.format(new Date(), "yyyyMMdd") });
		if (null == list || list.isEmpty()) {
			return null;
		}

		Map<String, Object> map = list.get(0);
		if (!"".equals(map.get("id").toString())) {
			result.put("code", 1);
			result.put("message", "开箱成功！");
			result.put("unpackingNum", map.get("unpackingNum"));
		} else {
			result.put("code", (w == 1 ? 3 : (w == 3 ? 4 : 2)));
			result.put("message", (w == 1 ? "您在本周日未持有操盘方案，没有开箱机会！" : (w == 3 ? "您在本周一没有新开操盘方案，没有开箱机会！" : "开箱时间未到！")));
		}

		return result;
	}

}

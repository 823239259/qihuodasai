package com.tzdr.business.service.activity;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import jodd.util.StringUtil;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.Lists;
import com.tzdr.business.cms.service.auth.AuthService;
import com.tzdr.business.service.datamap.DataMapService;
import com.tzdr.business.service.tradecalendar.TradeDayService;
import com.tzdr.common.baseservice.BaseServiceImpl;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.utils.Dates;
import com.tzdr.common.web.support.EasyUiPageInfo;
import com.tzdr.common.web.support.JsonResult;
import com.tzdr.common.web.support.MultiListParam;
import com.tzdr.domain.cache.CacheSIFActivitesManager;
import com.tzdr.domain.cms.entity.user.User;
import com.tzdr.domain.dao.activity.ActivityKudoDao;
import com.tzdr.domain.entity.DataMap;
import com.tzdr.domain.vo.WuserActivityVo;
import com.tzdr.domain.vo.WuserListVo;
import com.tzdr.domain.vo.WuserVo;
import com.tzdr.domain.vo.activity.ActivityCmsVo;
import com.tzdr.domain.vo.activity.ActivityKudoWebVo;
import com.tzdr.domain.web.entity.activity.ActivityKudo;
import com.tzdr.domain.web.entity.activity.ActivityUser;

/**
 * 微信、web开箱壕礼参赛奖品实现类
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
public class ActivityKudoService extends BaseServiceImpl<ActivityKudo, ActivityKudoDao> {

	private static final Logger logger = LoggerFactory.getLogger(ActivityKudoService.class);

	@Autowired
	private TradeDayService tradeDayService;

	@Autowired
	private ActivityUserService activityUserService;

	@Autowired
	private DataMapService dataMapService;

	@Autowired
	AuthService authService;

	/**
	 * 获取某用户获取的奖品信息
	 * 
	 * @param userId
	 *            用户编号
	 * @param activityType
	 *            活动类型：1-微信抽奖；2-web抽奖
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ActivityKudoWebVo> findActivityKudoWebVos(String userId, int activityType) {

		if (StringUtil.isBlank(userId)) {
			return null;
		}

		List<Object> params = new ArrayList<Object>();

		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT k.activity_type,k.user_id,k.id,k.kudo_name,k.kudo_type,k.kudo_status,k.kudo_get_time ");
		sql.append(" from w_activity_kudo k ");
		sql.append(" WHERE k.user_id=? AND k.activity_type=? ORDER BY k.kudo_get_time DESC ");
		params.add(userId);
		params.add(activityType);
		// 获取用户奖品信息
		List<ActivityKudoWebVo> ActivityKudoWebVoList = this.getEntityDao().queryListBySql(sql.toString(), ActivityKudoWebVo.class, null, new Object[] { userId, activityType });
		return ActivityKudoWebVoList;
	}

	/**
	 * 【外盘WEB开箱壕礼】判断是否是活动时间内:1、活动日期；2、活动时间；3：交易日时间；
	 * 
	 * @param activityTimeStart
	 *            活动开始日期
	 * @param activityTimeEnd
	 *            活动结束日期
	 * @param lotteryTimeStart
	 *            活动开始时间
	 * @param lotteryTimeEnd
	 *            活动结束时间
	 * @return -1:未到活动日期;-2:活动已结束;-3:未到活动时间;-4:非交易日时间 ;1:可以参与
	 * @throws Exception
	 */
	public int isLotteryTime(String activityTimeStart, String activityTimeEnd, String lotteryTimeStart, String lotteryTimeEnd) throws Exception {

		// 1:判断活动日期
		Date activityTimeStartDate = Dates.toDate(activityTimeStart); // 活动开始日期

		Date activityTimeEndDate = Dates.toDate(activityTimeEnd); // 活动结束日期

		Date nowTime = new Date(); // 系统当前时间
		Date dateTime = Dates.toDate(Dates.format(nowTime, Dates.CHINESE_DATE_FORMAT_LINE)); // 系统当前日期

		// 判断是否是不在活动日期之内
		if (!(dateTime.getTime() >= activityTimeStartDate.getTime() && dateTime.getTime() <= activityTimeEndDate.getTime())) {
			if (dateTime.getTime() < activityTimeStartDate.getTime()) { // 未到活动日期
				return -1;
			} else { // 活动已结束
				return -2;
			}
		}

		// 2:判断活动时间
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

		Calendar lotteryTimeStartCal = Calendar.getInstance(); // 活动开始时间
		lotteryTimeStartCal.setTime(sdf.parse(lotteryTimeStart));

		// 获取开始时间秒数=小时*60*60+分钟*60+秒
		long lotteryTimeStartLong = lotteryTimeStartCal.get(Calendar.HOUR_OF_DAY) * 60 * 60 + lotteryTimeStartCal.get(Calendar.MINUTE) * 60 + lotteryTimeStartCal.get(Calendar.SECOND);

		Calendar lotteryTimeEndCal = Calendar.getInstance(); // 活动结束时间
		lotteryTimeEndCal.setTime(sdf.parse(lotteryTimeEnd));

		// 获取结束时间秒数=小时*60*60+分钟*60+秒
		long lotteryTimeEndLong = lotteryTimeEndCal.get(Calendar.HOUR_OF_DAY) * 60 * 60 + lotteryTimeEndCal.get(Calendar.MINUTE) * 60 + lotteryTimeEndCal.get(Calendar.SECOND);

		Calendar c = Calendar.getInstance(); // 系统时间
		c.setTime(nowTime);
		// 获取结束时间秒数=小时*60*60+分钟*60+秒
		long cLong = c.get(Calendar.HOUR_OF_DAY) * 60 * 60 + c.get(Calendar.MINUTE) * 60 + c.get(Calendar.SECOND);

		// 判断是否在活动时间之内
		if (!(cLong >= lotteryTimeStartLong && cLong <= lotteryTimeEndLong)) { // 未到活动时间
			return -3;
		}

		// 3:交易日时间
		// 从缓存取出交易日信息
		Object isTradeDay = CacheSIFActivitesManager.getTradeDayCacheMap(Dates.format(new Date(), Dates.CHINESE_DATE_FORMAT_LONG));
		if (isTradeDay == null) {
			if (!tradeDayService.isTradeDay(Dates.format(nowTime, Dates.CHINESE_DATE_FORMAT_LONG))) {
				CacheSIFActivitesManager.addTradeDayCacheMap(Dates.format(new Date(), Dates.CHINESE_DATE_FORMAT_LONG), false);
				return -4;
			}
			CacheSIFActivitesManager.addTradeDayCacheMap(Dates.format(new Date(), Dates.CHINESE_DATE_FORMAT_LONG), true);
		} else if (!(boolean) isTradeDay) {
			return -4;
		}

		return 1;
	}

	/**
	 * 抽奖【外盘开箱壕礼奖品】
	 * 
	 * @param uid
	 *            用户编号
	 * @param mobile
	 *            用户手机号码
	 * @return Map<String,Object>
	 *         reusltMap：[key:reusltCode,value:-1(没有参赛资格)/-2(抽奖次数已使用完
	 *         )/-3(未抽到奖品)/200(成功)]、
	 *         (前置条件reusltCode-value:200)[unpackingNum:剩余抽奖次数
	 *         ]、(前置条件reusltCode-value:200)[activityKudoWebVo:奖品信息]
	 */
	public Map<Object, Object> updateLotteryPrizeOfWeb(String uid, String mobile) {

		// 操作结果
		Map<Object, Object> reusltMap = new HashMap<Object, Object>();

		// 一、判断是否可以参与抽奖：1、参赛资格；2、次数；
		ActivityUser activityUser = activityUserService.getActivityUser(uid, 2);
		if (activityUser == null) { // 没有参赛资格
			reusltMap.put("reusltCode", -1);
			return reusltMap;
		} else if (activityUser != null && activityUser.getUnpackingNum() <= 0) { // 抽奖次数已使用完
			reusltMap.put("reusltCode", -2);
			return reusltMap;
		}

		// 二、判断用户是否有限制奖品：黑名单
		boolean isLotteryBlacklist = this.isLotteryBlacklistOfWeb(mobile, "SIFActivityBlackList");

		// 三、开始抽取奖品
		reusltMap = this.updatePrizeOfWeb(activityUser, uid, mobile, isLotteryBlacklist);

		return reusltMap;
	}

	/**
	 * 【外盘开箱壕礼奖品】是否是抽奖黑名单
	 * 
	 * @param mobile
	 *            手机号码
	 * @param typeKey
	 *            黑名单类型
	 * @return true:是；false:否
	 */
	private boolean isLotteryBlacklistOfWeb(String mobile, String typeKey) {

		boolean result = false;

		DataMap dataMap = null;

		String blacklistStr = null;

		// 从缓存取出黑名单
		Object blacklistObject = CacheSIFActivitesManager.getblackListCacheMap(Dates.format(new Date(), Dates.CHINESE_DATE_FORMAT_LONG));

		if (blacklistObject == null) { // 缓存不存在黑名单
			List<DataMap> blacklist = dataMapService.findByTypeKey(typeKey); // 从数据库查询黑名单
			if (blacklist == null || blacklist.isEmpty()) {
				CacheSIFActivitesManager.addBlackListCacheMap(Dates.format(new Date(), Dates.CHINESE_DATE_FORMAT_LONG), "");
				return result;
			}
			dataMap = blacklist.get(0); // 取出黑名单信息
			if (dataMap == null) {
				CacheSIFActivitesManager.addBlackListCacheMap(Dates.format(new Date(), Dates.CHINESE_DATE_FORMAT_LONG), "");
				return result;
			}
			blacklistStr = dataMap.getValueName(); // 取出黑名单
			if (StringUtil.isBlank(blacklistStr)) { // 黑名单为空，明说没有
				CacheSIFActivitesManager.addBlackListCacheMap(Dates.format(new Date(), Dates.CHINESE_DATE_FORMAT_LONG), "");
			} else {
				CacheSIFActivitesManager.addBlackListCacheMap(Dates.format(new Date(), Dates.CHINESE_DATE_FORMAT_LONG), blacklistStr);
			}
		} else { // 缓存存在黑名单
			blacklistStr = (String) blacklistObject;
		}

		if (StringUtil.isBlank(blacklistStr)) {
			return result;
		}

		String[] blacklistArray = blacklistStr.split(",");

		for (String black : blacklistArray) {
			if (black.equals(mobile)) {
				result = true;
				break;
			}
		}

		return result;
	}

	/**
	 * 抽奖【外盘WEB开箱壕礼】
	 * 
	 * @param activityUser
	 *            参与用户信息
	 * @param uid
	 *            用户编号
	 * @param isLotteryBlacklist
	 *            是否是黑名单用户
	 * @return Map<String,Object>
	 *         reusltMap：[key:reusltCode,value:-3(未抽到奖品)/200(成功)]、
	 *         (前置条件reusltCode
	 *         -value:200)[unpackingNum:剩余抽奖次数]、(前置条件reusltCode-value
	 *         :200)[activityKudoWebVo:奖品信息]
	 */
	public Map<Object, Object> updatePrizeOfWeb(ActivityUser activityUser, String uid, String mobile, boolean isLotteryBlacklist) {

		// 操作结果
		Map<Object, Object> reusltMap = new HashMap<Object, Object>();

		// 抽取未抓取的奖品
		ActivityKudo activityKudo = this.getWebOfNewActivityKudo(isLotteryBlacklist, uid, mobile);

		// 判断抽奖是否有未抓取的奖品：有奖品
		if (activityKudo == null) { // 未抽到奖品
			reusltMap.put("reusltCode", -3);
			return reusltMap;
		}

		// 抓取新奖品
		activityKudo.setUserId(uid);
		activityKudo.setKudoStatus(1);
		activityKudo.setUserPhone(mobile);
		activityKudo.setKudoGetTime(new Date().getTime() / 1000);
		this.update(activityKudo); // 更新奖品信息

		// 剩余抽奖次数
		int unpackingNum = activityUser.getUnpackingNum() - 1;

		// 更新用户抽奖次数
		activityUser.setUnpackingNum(unpackingNum);

		// 更新用户抽奖信息
		activityUserService.update(activityUser);

		ActivityKudoWebVo activityKudoWebVo = new ActivityKudoWebVo(); // 组装奖品信息
		activityKudoWebVo.setActivityType(activityKudo.getActivityType());
		activityKudoWebVo.setUserId(activityKudo.getUserId());
		activityKudoWebVo.setId(activityKudo.getId());
		activityKudoWebVo.setKudoName(activityKudo.getKudoName());
		activityKudoWebVo.setKudoType(activityKudo.getKudoType());
		activityKudoWebVo.setKudoStatus(activityKudo.getKudoStatus());
		activityKudoWebVo.setKudoGetTime(activityKudo.getKudoGetTime());

		reusltMap.put("reusltCode", 200); // 抽奖成功
		reusltMap.put("unpackingNum", unpackingNum); // 剩余抽奖次数
		reusltMap.put("activityKudoWebVo", activityKudoWebVo); // 抽到奖品信息

		return reusltMap;
	}

	/**
	 * 获取未抓取的奖品信息【外盘WEB开箱壕礼】
	 * 
	 * @param isLotteryBlacklist
	 *            是否是黑名单用户
	 * @param uid
	 *            用户编号
	 * @return
	 */
	public ActivityKudo getWebOfNewActivityKudo(boolean isLotteryBlacklist, String uid, String mobile) {

		List<ActivityKudo> activityKudoList = null;

		// 该用户当天是否已经抽走现金红包
		boolean isHaveTodayOfRedPacket = true;
		// 缓存取用户当天红包：key
		String todayUid = Dates.format(new Date(), Dates.CHINESE_DATE_FORMAT_LONG) + uid;
		// 取缓存红包信息
		Object haveTodayOfRedPacketObject = CacheSIFActivitesManager.getRedPacketCacheMap(todayUid);
		if (haveTodayOfRedPacketObject == null) { // 当前缓存未存放用户红包信息
			isHaveTodayOfRedPacket = this.isHaveTodayOfRedPacket(uid); // 从数据库取用户当天红包信息
			if (isHaveTodayOfRedPacket) { // 用户当天有红包、放入缓存
				CacheSIFActivitesManager.addRedPacketCacheMap(todayUid);
			}
		}

		List<Integer> kudoTypes = new ArrayList<Integer>(); // 排除奖品类型集合

		if (isLotteryBlacklist) { // 判断是否是黑名单用户
			if (isHaveTodayOfRedPacket) { // 排除现金红包奖品
				kudoTypes.add(2);
				kudoTypes.add(6);
				activityKudoList = this.getEntityDao().findActivityKudoOfWeb(Dates.getCurrentLongDay(0, 0, 0), Dates.getCurrentLongDay(23, 59, 59), kudoTypes);
				// 获取黑名单用户可抽奖品
			} else {
				kudoTypes.add(6);
				activityKudoList = this.getEntityDao().findActivityKudoOfWeb(Dates.getCurrentLongDay(0, 0, 0), Dates.getCurrentLongDay(23, 59, 59), kudoTypes);
			}
		} else { // 获取非黑名单用户可抽奖品
			if (isHaveTodayOfRedPacket) { // 排除现金红包奖品
				kudoTypes.add(2);
				activityKudoList = this.getEntityDao().findActivityKudoOfWeb(Dates.getCurrentLongDay(0, 0, 0), Dates.getCurrentLongDay(23, 59, 59), kudoTypes);
			} else {
				kudoTypes.add(-1);
				activityKudoList = this.getEntityDao().findActivityKudoOfWeb(Dates.getCurrentLongDay(0, 0, 0), Dates.getCurrentLongDay(23, 59, 59), kudoTypes);
			}
		}

		if (activityKudoList == null || activityKudoList.isEmpty()) { // 判断是否有未抽取奖品
			return null;
		}

		Random rand = new Random(); // 从奖品列表中随机抽奖任意奖品
		int randNum = rand.nextInt(activityKudoList.size()); // 随机数范围【0-activityKudoList.size()-1】

		ActivityKudo activityKudo = activityKudoList.get(randNum); // 随机抽奖任意奖品

		return activityKudo;
	}

	/**
	 * 该用户当天是否已经抽走现金红包【外盘WEB开箱壕礼】
	 * 
	 * @param uid
	 *            用户编号
	 * @return true:是；false:否；
	 */
	public boolean isHaveTodayOfRedPacket(String uid) {

		boolean isHaveTodayOfRedPacket = false;

		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT COUNT(*) ");
		sql.append(" from w_activity_kudo k ");
		sql.append(" WHERE k.kudo_get_time>=? AND k.kudo_get_time<=? AND k.user_id=? AND k.kudo_type=2 AND k.activity_type=2 ");

		List<Object> params = new ArrayList<Object>();
		params.add(Dates.getCurrentLongDay(0, 0, 0));
		params.add(Dates.getCurrentLongDay(23, 59, 59));
		params.add(uid);
		// 获取用户当天抽取到的红包
		BigInteger redPacketOfCount = (BigInteger) this.nativeQueryOne(sql.toString(), params);
		if (redPacketOfCount == null || redPacketOfCount.intValue() <= 0) {
			return isHaveTodayOfRedPacket;
		}
		isHaveTodayOfRedPacket = true;

		return isHaveTodayOfRedPacket;
	}

	public PageInfo<Object> queryList(EasyUiPageInfo easyUiPage, Map<String, Object> searchParams) {
		PageInfo<Object> pageInfo = new PageInfo<Object>(easyUiPage.getRows(), easyUiPage.getPage());
		String sql = "SELECT ak.id id,ak.user_id uid,usr.mobile userPhone,uv.tname realName,ak.kudo_name kudoName,ak.kudo_get_time kudoGetTime,"
				+ "ak.kudo_use_time kudoUseTime,ak.activity_type activityType,ak.kudo_status kudoStatus"
				+ " FROM `w_activity_kudo` ak INNER JOIN w_user usr ON usr.id = ak.user_id INNER JOIN w_user_verified uv ON uv.uid = usr.id where ak.kudo_status <> 0 order by kudoStatus asc,kudoGetTime desc";
		// params 查询参数 依次 存入
		List<Object> params = Lists.newArrayList();
		MultiListParam multilistParam = new MultiListParam(easyUiPage, searchParams, params, sql);
		pageInfo = multiListPageQuery(multilistParam, ActivityCmsVo.class);
		return pageInfo;
	}

	public void usekudos(String[] idArray) {
		for (String id : idArray) {
			ActivityKudo activityKudo = super.get(id);
			if (2 == activityKudo.getKudoStatus()) {
				continue;
			}
			activityKudo.setKudoStatus(2);
			activityKudo.setUpdateTime(Dates.getCurrentLongDate());
			activityKudo.setKudoUseTime(Dates.getCurrentLongDate());
			User loginUser = authService.getCurrentUser();
			activityKudo.setUpdateUserId(loginUser.getId());
			activityKudo.setUpdateUser(loginUser.getRealname());
			activityKudo.setOperateContent("已使用奖品记录");
			super.update(activityKudo);
		}
	}
	
	
	public void chagnekudos(String[] idArray) {
		for (String id : idArray) {
			ActivityKudo activityKudo = super.get(id);
			if (2 == activityKudo.getKudoStatus()) {
				continue;
			}
			activityKudo.setKudoStatus(1);
			activityKudo.setUpdateTime(Dates.getCurrentLongDate());
			User loginUser = authService.getCurrentUser();
			activityKudo.setUpdateUserId(loginUser.getId());
			activityKudo.setUpdateUser(loginUser.getRealname());
			activityKudo.setOperateContent("奖品状态变更为未使用");
			super.update(activityKudo);
		}
	}
	

	private Lock lock = new ReentrantLock();

	/**
	 * 开箱操作：1，开箱成功-获得奖品；2-开箱时间未到；3，本周日未持有方案；4，
	 * 本周一没有新开方案；5-本次开启宝箱机会已使用，敬请期待下一次；6-奖品已送完
	 * 
	 * @param activityType
	 * @param userId
	 * @param userPhone
	 * @return
	 */
	public void unpacking(JsonResult jsonResult, int activityType, String userId, String userPhone) {
		lock.lock();

		try {
			// Map<String, Object> result = new HashMap<String, Object>();
			//
			// // 查询用户开箱资格
			// result = this.activityUserService.isUnpackingUser(activityType,
			// userId);
			//
			// if (null == result || result.isEmpty()) {
			// jsonResult.setCode("3");
			// jsonResult.setMessage("user.null.");
			// return;
			// }
			//
			// if ("3".equals(ObjectUtils.toString(result.get("code")))) {
			// jsonResult.setCode("3");
			// jsonResult.setMessage("sunday.user.null.");
			// return;
			// }
			//
			// if ("4".equals(ObjectUtils.toString(result.get("code")))) {
			// jsonResult.setCode("4");
			// jsonResult.setMessage("tuesday.user.null.");
			// return;
			// }

			ActivityUser activityUser = this.activityUserService.getActivityUser(userId, activityType);

			if (null == activityUser) { // 没有开箱资格
				jsonResult.setCode("3");
				jsonResult.setMessage("user.null.");
				return;
			} else if (activityUser.getUnpackingNum() <= 0) { // 抽奖次数已使用完/已开箱
				jsonResult.setCode("5");
				jsonResult.setMessage("user.unpacked.");
				return;
			} else if (activityUser.getUnpackingTime().indexOf(Dates.format(Dates.CHINESE_DATE_FORMAT_LONG)) < 0) {
				Calendar cal = Calendar.getInstance();
				cal.clear();
				cal.setTime(new Date());
				int w = cal.get(Calendar.DAY_OF_WEEK);

				jsonResult.setCode(w == 1 ? "3" : (w == 3 ? "4" : "2"));
				jsonResult.setMessage(w == 1 ? "sunday.user.null." : (w == 3 ? "tuesday.user.null." : "unpacking.time.yet.to.com."));
				return;
			}

			if (this.isUnpacked(activityType, userId)) {
				jsonResult.setCode("5");
				jsonResult.setMessage("user.unpacked.");
				return;
			}

			// if ("1".equals(ObjectUtils.toString(result.get("code")))) {
			// ActivityKudo ak = this.getActivityKudoRandom(activityType,
			// userId);
			//
			// if (null == ak || "".equals(ak.getId())) {
			// jsonResult.setCode("6");
			// jsonResult.setMessage("kudo.null.");
			// } else {
			// ak.setKudoStatus(1);
			// ak.setKudoGetTime(Dates.getCurrentLongDate());
			// ak.setUserId(userId);
			// ak.setUserPhone(userPhone);
			// this.update(ak);
			//
			// jsonResult.setCode("1");
			// jsonResult.setMessage("success!");
			// jsonResult.setObj(ak);
			// }
			// }

			ActivityKudo ak = this.getActivityKudoRandom(activityType, userId);

			if (null == ak || "".equals(ak.getId())) {
				jsonResult.setCode("6");
				jsonResult.setMessage("kudo.null.");
			} else {
				ak.setKudoStatus(1);
				ak.setKudoGetTime(Dates.getCurrentLongDate());
				ak.setUserId(userId);
				ak.setUserPhone(userPhone);
				this.update(ak);

				// 更新用户抽奖次数
				activityUser.setUnpackingNum(0);
				// 更新用户抽奖信息
				activityUserService.update(activityUser);

				jsonResult.setCode("1");
				jsonResult.setMessage("success!");
				jsonResult.setObj(ak);
			}
		} finally {
			lock.unlock();
		}
	}

	/**
	 * 随机获取一条未被抽到的奖品
	 * 
	 * @param activityType
	 * @return
	 */
	public ActivityKudo getActivityKudoRandom(int activityType, String userId) {
		/*
		 * StringBuffer sql = new StringBuffer();
		 * 
		 * sql.append(
		 * " select id from w_activity_kudo where activity_type = ? and kudo_status = 0 and user_id is null "
		 * ); sql.append(
		 * " and kudo_type not in (select 1 from w_activity_kudo where activity_type = ? and kudo_type = 1 and user_id = ?) "
		 * ); sql.append(" order by rand() limit 1 ");
		 * 
		 * List<Object> params = new ArrayList<Object>();
		 * params.add(activityType); params.add(activityType);
		 * params.add(userId);
		 * 
		 * String id = (String) this.nativeQueryOne(sql.toString(), params);
		 * 
		 * ActivityKudo ak = this.get(id);
		 */

		int kudoType = -10;
		List<ActivityKudo> userSWs = this.getEntityDao().findActivityKudoSW(activityType, userId);
		if (!CollectionUtils.isEmpty(userSWs)) {
			kudoType = 1;
		}
		userSWs = this.getEntityDao().findActivityKudoOfWeixin(activityType, kudoType);
		if (CollectionUtils.isEmpty(userSWs)) {
			return null;
		}

		return userSWs.get(0);
	}

	/**
	 * 是否已开箱
	 * 
	 * @param activityType
	 * @param userid
	 * @return
	 */
	public boolean isUnpacked(int activityType, String userId) {
		StringBuffer sql = new StringBuffer();
		sql.append(" select id, activity_type activityType, kudo_name kudoName, kudo_type kudoType, kudo_status kudoStatus, user_id userId, kudo_get_time kudoGetTime ");
		sql.append(" from w_activity_kudo where activity_type = ? and user_id = ? ");
		sql.append(" and kudo_get_time >= ? ");
		sql.append(" and kudo_get_time < ? ");

		List<Object> params = new ArrayList<Object>();
		params.add(activityType);
		params.add(userId);

		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.setTime(Dates.parse(Dates.format(Dates.CHINESE_DATE_FORMAT_LINE)));
		long currDateStartTime = calendar.getTimeInMillis() / 1000;
		calendar.setTime(Dates.addDay(Dates.parse(Dates.format(Dates.CHINESE_DATE_FORMAT_LINE))));
		long currDateEndTime = calendar.getTimeInMillis() / 1000;

		params.add(currDateStartTime);
		params.add(currDateEndTime);

		List<Map<String, Object>> list = this.getEntityDao().queryMapBySql(sql.toString(), new Object[] { activityType, userId, currDateStartTime, currDateEndTime });
		if (null == list || list.isEmpty()) {
			return false;
		}

		return true;
	}
	
	/**
	 * 领取我的新春大礼包：0-未登录，1-领取成功，2-老用户不能领取新春大礼包，3-已领过新春大礼包
	 * 
	 * @param activityType
	 * @param userId
	 * @param userPhone
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int recivieMyActivityKudo(int activityType, String userId, String userPhone) throws Exception {
		if (StringUtil.isBlank(userId)) {
			return 0;
		}

		List<Object> params = new ArrayList<Object>();

		StringBuffer sql = new StringBuffer();
		sql.append(" select id from w_user where id = ? and ctime >= ? and ctime <= ? ");
		params.add(userId);
		long startTime = Dates.toDate("2016-02-15 00:00:00").getTime() / 1000;
		long endTime = Dates.toDate("2016-02-29 23:59:59").getTime() / 1000;
		params.add(startTime);
		params.add(endTime);

		List<WuserListVo> result = this.nativeQuery(sql.toString(), params, WuserListVo.class);
		if (CollectionUtils.isEmpty(result)) {
			return 2;
		}

		if (isRecivieActivityKudo(activityType, userId)) {
			return 3;
		}

		this.generateNewYearGifts(activityType, userId, userPhone);
		return 1;
	}

	/**
	 * 是否领取过新春大礼包
	 * 
	 * @param activityType
	 * @param userId
	 * @return
	 */
	public boolean isRecivieActivityKudo(int activityType, String userId) {
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();

		sql.append(" select count(id) from w_activity_kudo where kudo_status > 0 and activity_type = ? and user_id = ? ");
		params.add(activityType);
		params.add(userId);

		Object result = this.nativeQueryOne(sql.toString(), params);
		String resultStr = ObjectUtils.toString(result);
		if ("".equals(resultStr) || "0".equals(resultStr)) {
			return false;
		}

		return true;
	}

	/**
	 * 生成新春大礼包
	 * 
	 * @param activityType
	 * @param userId
	 * @param userPhone
	 */
	public void generateNewYearGifts(int activityType, String userId, String userPhone) throws Exception {
		String[] kudoNames = { "A50新手礼", "国际原油新手礼", "恒指期货新手礼", "商品期货新手礼", "A50进阶礼", "国际原油进阶礼", "恒指进阶礼", "商品期货进阶礼" };
		String[] kudoUseConditions = { "A50交易1手", "国际原油交易1手", "恒指期货交易1手", "商品期货交易1手", "A50交易10手", "国际原油交易10手", "恒指期货交易10手", "商品期货交易10手" };
		int[] kudoMoneys = { 5, 10, 8, 5, 68, 118, 108, 66 };
		List<ActivityKudo> list = new ArrayList<>();

		for (int i = 0; i < kudoNames.length; i++) {
			ActivityKudo ak = new ActivityKudo();
			ak.setCreateTime(Dates.getCurrentLongDate());
			ak.setActivityType(activityType);
			ak.setUserId(userId);
			ak.setUserPhone(userPhone);
			ak.setKudoName(kudoNames[i]);
			ak.setKudoType(7);
			ak.setKudoStatus(1);
			ak.setKudoGetTime(Dates.getCurrentLongDate());
			ak.setKudoMoney(kudoMoneys[i]+0.0);
			ak.setKudoUseCondition(kudoUseConditions[i]);

			list.add(ak);
		}

		this.saves(list);
	}

	/**
	 * 获取我的新春大礼包
	 * 
	 * @param activityType
	 * @param userId
	 * @param userPhone
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ActivityKudoWebVo> getMyActivityKudo(int activityType, String userId, String userPhone) {

		if (StringUtil.isBlank(userId)) {
			return null;
		}
		List<Object> params = new ArrayList<Object>();

		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT k.id, k.kudo_name, k.kudo_money, k.kudo_use_condition, k.kudo_status ");
		sql.append(" from w_activity_kudo k ");
		sql.append(" WHERE k.user_id=? AND k.activity_type=? ORDER BY k.kudo_get_time DESC ");
		params.add(userId);
		params.add(activityType);

		// 获取用户奖品信息
		List<ActivityKudoWebVo> ActivityKudoWebVoList = this.getEntityDao().queryListBySql(sql.toString(), ActivityKudoWebVo.class, null, new Object[] { userId, activityType });
		return ActivityKudoWebVoList;
	}

	/**
	 * 申请使用我的新春大礼包
	 * 
	 * @param id
	 */
	public void useMyActivityKudo(String id) throws Exception {
		ActivityKudo ak = this.get(id);
		ak.setKudoStatus(3);
		ak.setOperateContent("用户申请使用大礼包");
		ak.setUpdateTime(Dates.getCurrentLongDate());
		ak.setUpdateUser(ak.getUserPhone());

		this.save(ak);
	}

}

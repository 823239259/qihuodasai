package com.tzdr.business.hkstock.service;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import jodd.util.ObjectUtil;
import jodd.util.StringUtil;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tzdr.business.cms.service.auth.AuthService;
import com.tzdr.business.hkstock.exception.HkTradeCalendarException;
import com.tzdr.common.baseservice.BaseServiceImpl;
import com.tzdr.common.utils.Dates;
import com.tzdr.domain.cms.entity.user.User;
import com.tzdr.domain.hkstock.dao.HkTradeCalendarDao;
import com.tzdr.domain.hkstock.entity.HkTradeCalendar;
import com.tzdr.domain.vo.UserTradeCmsVo;

/**
 * 
 * @author zhouchen
 * 2015年10月16日 上午11:45:32
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class HkTradeCalendarService extends BaseServiceImpl<HkTradeCalendar,HkTradeCalendarDao> {
	
	@Autowired
	private HkTradeCalendarDao hktradeDayDao;

	@Autowired
	private AuthService authService;

	@PersistenceContext
	private EntityManager em;

	/**
	 * 获取 配资到期日 (交易日)
	 * 
	 * @param startDate
	 *            交易开始时间
	 * @param useDays
	 *            使用天数 必须大于1
	 * @return
	 */
	public String getExpirationDate(String startDate, int useDays) {
		if (StringUtil.isBlank(startDate)) {
			return null;
		}

		if (useDays < 1) {
			return null;
		}
		String sql = " SELECT  date  from hk_trade_calendar WHERE  is_trade=1 and date>=? ORDER BY date ASC LIMIT ?,1 ";
		Query query = em.createNativeQuery(sql);
		query.setParameter(1, startDate);
		query.setParameter(2, useDays - 1);

		String resultDate = String.valueOf(query.getSingleResult());
		return resultDate;
	}

	/**
	 * 获取 配资到期日 (自然日)
	 * 
	 * @param startDate
	 *            交易开始时间
	 * @param useDays
	 *            使用天数 必须大于1
	 * @return
	 */
	public String getEndDate(String startDate, int useDays) {
		if (StringUtil.isBlank(startDate)) {
			return null;
		}

		if (useDays < 1) {
			return null;
		}
		String sql = " SELECT  date  from hk_trade_calendar WHERE  date>=? ORDER BY date ASC LIMIT ?,1 ";
		Query query = em.createNativeQuery(sql);
		query.setParameter(1, startDate);
		query.setParameter(2, useDays - 1);

		String resultDate = String.valueOf(query.getSingleResult());
		return resultDate;
	}

	/**
	 * 获取 交易日14:45前都认为是
	 * 
	 * @return
	 */
	public String getTradeDay() {
		String currentDay = Dates.format(new Date(),
				Dates.CHINESE_DATE_FORMAT_LONG);
		HkTradeCalendar tradeDay = hktradeDayDao.findByIsTradeTrueAndDate(NumberUtils
				.toLong(currentDay));

		if (ObjectUtil.equals(null, tradeDay)) {
			return getNextTradeDay();
		}

		Calendar colseTime = Calendar.getInstance();
		colseTime.set(Calendar.HOUR_OF_DAY, 15);
		colseTime.set(Calendar.MINUTE, 45);
		colseTime.set(Calendar.SECOND, 0);

		if (colseTime.getTime().after(new Date())) {
			return currentDay;
		}

		return getNextTradeDay();
	}

	/**
	 * 获取下一个交易日
	 * 
	 * @return
	 */
	public String getNextTradeDay() {
		String currentDay = Dates.format(new Date(),
				Dates.CHINESE_DATE_FORMAT_LONG);
		String sql = "  SELECT  date  from hk_trade_calendar WHERE  is_trade=1 and date>? ORDER BY date ASC LIMIT 0,1 ";
		Query query = em.createNativeQuery(sql);
		query.setParameter(1, currentDay);
		String resultDate = String.valueOf(query.getSingleResult());
		return resultDate;

	}

	/**
	 * 
	 * 当前 日 是否交易日
	 * 
	 * @return
	 */
	public boolean isTradeDay(String currentDay) {
		HkTradeCalendar tradeDay = hktradeDayDao.findByIsTradeTrueAndDate(NumberUtils
				.toLong(currentDay));

		if (ObjectUtil.equals(null, tradeDay)) {
			return Boolean.FALSE;
		}
		return Boolean.TRUE;
	}

	/**
	 * 判断当前时间是否交易时间 交易时间：9：00-15：00
	 * 
	 * @return true 是交易时间
	 */
	public boolean isTradeTime() {
		String currentDay = Dates.format(new Date(),
				Dates.CHINESE_DATE_FORMAT_LONG);
		if (!isTradeDay(currentDay)) {
			return false;
		}
		
		// 交易结束时间
		Calendar colseTime = Calendar.getInstance();
		colseTime.set(Calendar.HOUR_OF_DAY, 16);
		colseTime.set(Calendar.MINUTE, 0);
		colseTime.set(Calendar.SECOND, 0);

		// 交易开始时间
		Calendar beginTime = Calendar.getInstance();
		beginTime.set(Calendar.HOUR_OF_DAY, 9);
		beginTime.set(Calendar.MINUTE, 30);
		beginTime.set(Calendar.SECOND, 0);

		Date currentTime = new Date();
		if (currentTime.after(beginTime.getTime())
				&& currentTime.before(colseTime.getTime())) {
			return true;
		}

		return false;
	}
	
	
	/**
	 * 判断当前时间是否为冻结时间 00:00：00-15：30
	 * 
	 * @return true 是否为冻结时间
	 */
	public boolean isTradeFreezeTime() {
		String currentDay = Dates.format(new Date(),
				Dates.CHINESE_DATE_FORMAT_LONG);
		if (!isTradeDay(currentDay)) {
			return false;
		}

		// 交易结束时间
		Calendar colseTime = Calendar.getInstance();
		colseTime.set(Calendar.HOUR_OF_DAY, 15);
		colseTime.set(Calendar.MINUTE, 30);
		colseTime.set(Calendar.SECOND, 0);

		// 交易开始时间
		Calendar beginTime = Calendar.getInstance();
		beginTime.set(Calendar.HOUR_OF_DAY, 0);
		beginTime.set(Calendar.MINUTE, 0);
		beginTime.set(Calendar.SECOND, 0);

		Date currentTime = new Date();
		if (currentTime.after(beginTime.getTime())
				&& currentTime.before(colseTime.getTime())) {
			return true;
		}

		return false;
	}

	/**
	 * 
	 * 获取 交易开始时间 到结束时间的自然日数
	 * 
	 * @return
	 */
	public Long getNaturalDays(String startDate, String endDate) {
		if (StringUtil.isBlank(startDate) || StringUtil.isBlank(endDate)) {
			return 0L;
		}

		Date start = Dates.parse(startDate, Dates.CHINESE_DATE_FORMAT_LONG);
		Date end = Dates.parse(endDate, Dates.CHINESE_DATE_FORMAT_LONG);
		return (end.getTime() - start.getTime()) / 86400000 + 1L;
	}

	/**
	 * 获取两个时间内的交易日
	 * 
	 * @param startDate
	 *            格式: 20150103
	 * @param endDate
	 *            格式: 20150103
	 * @return
	 */
	public Integer getTradeDays(String startDate, String endDate) {
		if (StringUtil.isBlank(startDate) || StringUtil.isBlank(endDate)) {
			return null;
		}

		String sql = "SELECT COUNT(0) FROM hk_trade_calendar WHERE is_trade = 1 AND date >= ? AND date <= ?";
		Query query = em.createNativeQuery(sql);
		query.setParameter(1, startDate);
		query.setParameter(2, endDate);

		return NumberUtils.toInt(String.valueOf(query.getSingleResult()));
	}

	@Override
	public void update(HkTradeCalendar tradeDay) {
		String tradeDayId = tradeDay.getId();
		HkTradeCalendar dbTradeDay = getEntityDao().get(tradeDayId);
		if (ObjectUtil.equals(dbTradeDay, null)) {
			throw new HkTradeCalendarException("business.update.not.found.data",
					null);
		}

		dbTradeDay.setIsTrade(tradeDay.getIsTrade());

		setOperateLog(dbTradeDay, "更新是否交易日", "edit");
		super.update(dbTradeDay);
	}

	/**
	 * 判断 当年是否已经创建过日历了 即是否 存在记录如：20150101
	 * 
	 * @param year
	 * @return
	 */
	public boolean isCreate(int year) {
		HkTradeCalendar tradeDay = getEntityDao().findByDate(year * 10000L + 101);
		if (ObjectUtil.equals(null, tradeDay)) {
			return false;
		}
		return true;
	}

	/**
	 * 生成一年的 日期数据
	 * 
	 * @param year
	 *            需要生成日历的年份
	 */
	public void createCalendar(int year) {

		// 定义一个日历，变量作为年初
		Calendar calendar = Calendar.getInstance();
		// 定义一个日历，变量作为年末
		Calendar calendarEnd = Calendar.getInstance();

		// 设置年初的日期为1月1日
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.MONTH, 0);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);

		// 设置年末的日期为12月31日
		calendarEnd.set(Calendar.YEAR, year);
		calendarEnd.set(Calendar.MONTH, 11);
		calendarEnd.set(Calendar.DAY_OF_MONTH, 31);
		calendarEnd.set(Calendar.HOUR_OF_DAY, 0);
		calendarEnd.set(Calendar.MINUTE, 0);
		calendarEnd.set(Calendar.SECOND, 0);

		while (calendar.getTime().getTime() <= calendarEnd.getTime().getTime()) {

			String tempDate = Dates.format(calendar.getTime(),
					Dates.CHINESE_DATE_FORMAT_LONG);
			HkTradeCalendar tradeCalendar = new HkTradeCalendar(Long.valueOf(tempDate));
			tradeCalendar.setDateTime(calendar.getTimeInMillis() / 1000);
			int week = calendar.get(Calendar.DAY_OF_WEEK) - 1;
			// 0代表周日，6代表周六
			if (week == 6 || week == 0) {
				tradeCalendar.setIsTrade(Boolean.FALSE);
			}
			// 保存日期
			hktradeDayDao.save(tradeCalendar);
			calendar.add(Calendar.DAY_OF_MONTH, 1);
		}

	}

	private void setOperateLog(HkTradeCalendar tradeDay, String operateContent,
			String operateType) {
		User loginUser = authService.getCurrentUser();
		tradeDay.setOperateContent(operateContent);
		if (StringUtil.equals(operateType, "edit")) {
			tradeDay.setUpdateTime(Dates.getCurrentLongDate());
			tradeDay.setUpdateUser(loginUser.getRealname());
			tradeDay.setUpdateUserOrg(loginUser.getOrganization().getName());
			tradeDay.setUpdateUserId(loginUser.getId());
			return;
		}
		tradeDay.setCreateUserId(loginUser.getId());
		tradeDay.setCreateTime(Dates.getCurrentLongDate());
		tradeDay.setCreateUser(loginUser.getRealname());
		tradeDay.setCreateUserOrg(loginUser.getOrganization().getName());
		return;
	}

	/**
	 * 计算交易日
	 * @param tradeCmsVo
	 * @return
	 */
	public Long getTradeDays(UserTradeCmsVo tradeCmsVo) {

		if (tradeCmsVo.getStatus() == 1) {
			String today = Dates.format(Dates.CHINESE_DATE_FORMAT_LONG);
			String startDay = Dates.parseBigInteger2Date(
					tradeCmsVo.getStarttime(), Dates.CHINESE_DATE_FORMAT_LONG);
			long tradingDays = getTradeDays(startDay, today);
			return tradingDays;
		}

		String startDay = Dates.parseBigInteger2Date(tradeCmsVo.getStarttime(),
				Dates.CHINESE_DATE_FORMAT_LONG);
		String endDay = Dates.parseBigInteger2Date(tradeCmsVo.getEndtime(),
				Dates.CHINESE_DATE_FORMAT_LONG);
		long tradingDays = getTradeDays(startDay, endDay);
		return tradingDays;
	}
	
	/**
	 * 计算交易日
	 * @param startTime 开始交易时间
	 * @param endTime 结束交易时间
	 * @return
	 */
	public Integer getTradeDays(BigInteger startTime, BigInteger endTime) {
		if(!ObjectUtil.equals(null, startTime) && !ObjectUtil.equals(null, endTime)) {
			Date today = new Date();
			Date startDay = Dates.parseLong2Date(startTime.longValue());
			Date endDay = Dates.parseLong2Date(endTime.longValue());
			
			if(startDay.before(today) && today.before(endDay)) { //在开始交易时间 结束交易时间之间
				return Dates.daysBetween2(today, endDay);
			} else if(today.before(startDay)) { //开始交易之前
				return Dates.daysBetween2(startDay, endDay);
			}
		}
		return 0;
	}
	
	/**
	 * 根据帐号分配日期，计算用户距离现在的已操盘时间。hourLimit 为 NULL 则不做时间限制。
	 * 1.在 hourLimit 之前分配的交易账号，已操盘时间+1；
	 * 2.在 hourLimit 之后分配的交易账号，已操盘天数为0。
	 * @param appStartTime 分配给用户帐号的日期
	 * @param hourLimit 24小时时间制
	 * @return 已操盘时间
	 */
	public int getTradeDayNum(Long appStartTime,Integer hourLimit){
		if(appStartTime==null){
			return 0;
		}
		Date date = Dates.parseLong2Date(appStartTime);
		String startDate =  new SimpleDateFormat("yyyyMMdd").format(date);
		String endDate =  new SimpleDateFormat("yyyyMMdd").format(new Date());
		int useTranDay =  this.getTradeDays(startDate, endDate);
		
		if(this.isTradeDay(startDate)){ // 是否为交易日
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			int hour = calendar.get(Calendar.HOUR_OF_DAY); //分配时间 小时
			if(hourLimit!=null){
				if(hour >= hourLimit){
					useTranDay = useTranDay > 0 ? useTranDay - 1 : 0;
				}
			}
		}
		return useTranDay;
	}
}

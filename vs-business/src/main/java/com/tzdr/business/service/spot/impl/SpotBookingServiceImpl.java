package com.tzdr.business.service.spot.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import jodd.util.ObjectUtil;
import jodd.util.StringUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.tzdr.business.cms.service.auth.AuthService;
import com.tzdr.business.exception.SpotBookingException;
import com.tzdr.business.service.datamap.DataMapService;
import com.tzdr.business.service.spot.SpotBookingService;
import com.tzdr.business.service.spot.SpotSalesmanService;
import com.tzdr.common.api.ihuyi.SMSSender;
import com.tzdr.common.baseservice.BaseServiceImpl;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.exception.BusinessException;
import com.tzdr.common.utils.BeanUtils;
import com.tzdr.common.utils.Dates;
import com.tzdr.common.web.support.EasyUiPageInfo;
import com.tzdr.domain.cms.entity.user.User;
import com.tzdr.domain.dao.spot.SpotBookingDao;
import com.tzdr.domain.vo.SpotBookingVo;
import com.tzdr.domain.web.entity.SpotBooking;
import com.tzdr.domain.web.entity.SpotSalesman;

/**
 * 现货销售员 service实现层
 * @ClassName SpotBookingServiceImpl
 * @author L.Y
 * @email liuyuan@peigubao.com
 * @date 2015年10月8日
 */
@Service
@Transactional
public class SpotBookingServiceImpl extends BaseServiceImpl<SpotBooking, SpotBookingDao> implements SpotBookingService {
	@Autowired
	private DataMapService dataMapService;
	
	public static final Logger logger = LoggerFactory
			.getLogger(SpotBookingServiceImpl.class);
	
	@Autowired
	private AuthService authService;
	
	@Autowired
	private SpotSalesmanService spotSalesmanService;
	
	@Autowired
	private SpotBookingDao spotBookingDao;

	@Override
	public PageInfo<Object> getData(EasyUiPageInfo easyUiPage,
			Map<String, Object> searchParams) {
		String sql = "SELECT "
						+ "FROM_UNIXTIME(b.create_time, '%Y-%m-%d %H:%i') createTime, "
						+ "b.autonym, "
						+ "b.mobile, "
						+ "s.name salesmanName, "
						+ "s.mobile salesmanMobile "
					+ "FROM "
						+ "s_spot_booking b "
					+ "LEFT JOIN s_spot_salesman s ON s.id = b.sid "
					+ "WHERE "
						+ "1 = 1 "
					+ "AND b.deleted = FALSE";
		
		return multiListPageQuery(easyUiPage, searchParams, sql, SpotBookingVo.class);
	}

	@Override
	public void update(SpotBooking spotBooking) throws BusinessException {
		SpotBooking sb = null;
		
		if (ObjectUtil.equals(null, spotBooking) 
				|| ObjectUtil.equals(null, sb = getEntityDao().get(spotBooking.getId()))) {
			throw new SpotBookingException("business.update.not.found.data", null);
		}
		
//		sb.setName(spotBooking.getName());
		BeanUtils.copyProperties(spotBooking, sb);
		setOperateLog(sb, "更新现货预约", "edit");
		super.update(sb);
	}

	@Override
	public void removes(Serializable... ids) throws BusinessException {
		for (Serializable id : ids) {
			SpotBooking sb = getEntityDao().get(id);
			if (ObjectUtil.equals(sb, null)) {
				throw new SpotBookingException("business.delete.not.found.data",
						null);
			}
			sb.setDeleted(Boolean.TRUE);
			setOperateLog(sb, "删除现货预约", "edit");
			super.update(sb);
//			super.removeById(id);
		}
	}
	
	@Override
	public void save(SpotBooking sb) throws BusinessException {
		SpotSalesman ss = spotSalesmanService.findOneByMinCtn();
		if(!ObjectUtil.equals(null, ss)) {
			sb.setSid(ss.getId());
//			setOperateLog(sb, "新增现货预约", "add");
			sb.setUpdateTime(Dates.getCurrentLongDate());
			sb.setUpdateUser("web用户,ID:" + sb.getUid());
			sb.setUpdateUserOrg("客户");
			sb.setCreateTime(Dates.getCurrentLongDate());
			sb.setCreateUser("web用户,ID:" + sb.getUid());
			sb.setCreateUserOrg("客户");
			super.save(sb);
			//接单数量加 1
			ss.setCtn((ObjectUtil.equals(null, ss.getCtn()) ? 0 : ss.getCtn()) + 1);
			spotSalesmanService.update(ss);
			//给销售人员发送短信
			SMSSender.getInstance().sendByTemplateClasspath(dataMapService.getSmsContentOthers(),
					"spot", ss.getMobile(), sb.getAutonym(), sb.getMobile());
		}
	}
	
	/**
	 * 记录日志
	 * @MethodName setOperateLog
	 * @author L.Y
	 * @date 2015年10月8日
	 * @param spotBooking
	 * @param operateContent
	 * @param operateType
	 */
	private void setOperateLog(SpotBooking spotBooking, String operateContent,
			String operateType) {
		User loginUser = authService.getCurrentUser();
		spotBooking.setOperateContent(operateContent);
		if (StringUtil.equals(operateType, "edit")) {
			spotBooking.setUpdateTime(Dates.getCurrentLongDate());
			spotBooking.setUpdateUser(loginUser.getRealname());
			spotBooking.setUpdateUserOrg(loginUser.getOrganization().getName());
			spotBooking.setUpdateUserId(loginUser.getId());
			return;
		}

		spotBooking.setCreateTime(Dates.getCurrentLongDate());
		spotBooking.setCreateUser(loginUser.getRealname());
		spotBooking.setCreateUserOrg(loginUser.getOrganization().getName());
		spotBooking.setCreateUserId(loginUser.getId());
		return;
	}

	/**
	 * 查询是否已预约
	 */
	@Override
	public SpotBooking findByUid(String uid) {
		List<SpotBooking> spotBookings = spotBookingDao.findByUid(uid);
		if(!CollectionUtils.isEmpty(spotBookings)) {
			return spotBookings.get(0);
		}
		return null;
	}
}
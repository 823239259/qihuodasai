package com.tzdr.business.service.wuser.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import jodd.util.ObjectUtil;
import jodd.util.StringUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.Lists;
import com.tzdr.business.cms.exception.UserException;
import com.tzdr.business.cms.service.auth.AuthService;
import com.tzdr.business.cms.service.user.PasswordService;
import com.tzdr.business.cms.service.user.UserExtendService;
import com.tzdr.business.exception.WuserException;
import com.tzdr.business.service.datamap.DataMapService;
import com.tzdr.business.service.extension.ActivityRewardService;
import com.tzdr.business.service.extension.LuckDrawService;
import com.tzdr.business.service.pay.UserFundService;
import com.tzdr.business.service.securityInfo.impl.SecurityInfoServiceImpl;
import com.tzdr.business.service.thread.SMSPgbSendForContentThread;
import com.tzdr.business.service.thread.SMSSenderThread;
import com.tzdr.business.service.userTrade.NoticeRecordService;
import com.tzdr.business.service.volume.impl.VolumeDetailServiceImpl;
import com.tzdr.business.service.wuser.WUserService;
import com.tzdr.common.api.ihuyi.SMSSender;
import com.tzdr.common.baseservice.BaseServiceImpl;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.exception.EmailExceptionHandler;
import com.tzdr.common.utils.ConnditionVo;
import com.tzdr.common.utils.Dates;
import com.tzdr.common.utils.MessageUtils;
import com.tzdr.common.utils.RandomCodeUtil;
import com.tzdr.common.utils.TypeConvert;
import com.tzdr.domain.cache.CacheManager;
import com.tzdr.domain.cache.DataDicKeyConstants;
import com.tzdr.domain.cms.entity.user.User;
import com.tzdr.domain.cms.entity.user.UserExtend;
import com.tzdr.domain.constants.ExtensionConstants;
import com.tzdr.domain.dao.pay.UserFundDao;
import com.tzdr.domain.dao.wuser.WUserDao;
import com.tzdr.domain.vo.ActivityWuserVo;
import com.tzdr.domain.vo.AgentVo;
import com.tzdr.domain.vo.SumSingleVo;
import com.tzdr.domain.vo.TransferVo;
import com.tzdr.domain.vo.UserVerifiedVo;
import com.tzdr.domain.vo.WuserListVo;
import com.tzdr.domain.vo.WuserParentVo;
import com.tzdr.domain.vo.WuserVo;
import com.tzdr.domain.web.entity.ActivityReward;
import com.tzdr.domain.web.entity.LuckDraw;
import com.tzdr.domain.web.entity.NoticeRecord;
import com.tzdr.domain.web.entity.UserFund;
import com.tzdr.domain.web.entity.UserVerified;
import com.tzdr.domain.web.entity.VolumeDeductible;
import com.tzdr.domain.web.entity.VolumeDetail;
import com.tzdr.domain.web.entity.WUser;

/**
 * @Description: TODO(用户帐号业务信息管理业务实现层)
 * @ClassName: WUserServiceImpl
 * @author wangpinqun
 * @date 2014年12月25日 下午1:43:06
 */
@Transactional(propagation = Propagation.REQUIRED)
@Service("wUserService")
public class WUserServiceImpl extends BaseServiceImpl<WUser, WUserDao> implements WUserService {

	private static Logger log = LoggerFactory.getLogger(WUserServiceImpl.class);

	@Autowired
	private UserFundDao userFundDao;

	@Autowired
	private UserFundService userFundService;

	@Autowired
	private PasswordService passwordService;

	@Autowired
	private SecurityInfoServiceImpl securityInfoService;

	@Autowired
	private NoticeRecordService noticeRecordService;

	@Autowired
	private VolumeDetailServiceImpl volumeDetailServiceImpl;

	@Autowired
	private AuthService authService;

	@Autowired
	private UserExtendService userExtendService;

	@Autowired
	private DataMapService dataMapService;

	@Autowired
	private LuckDrawService luckDrawService;

	@Autowired
	private ActivityRewardService activityRewardService;

	@Override
	public WUser getWUserByMobile(String mobile) {
		if (StringUtil.isBlank(mobile)) {
			return null;
		}
		return getEntityDao().findByMobile(mobile);
	}

	@Override
	public void saveWUser(WUser wUser) {
		wUser.randomSalt();
		wUser.setPassword(passwordService.encryptPassword(wUser.getPassword(), wUser.getLoginSalt()));
		wUser.setGeneralizeId(this.getNewGeneralizeId());
		WUser parentNode = wUser.getParentNode();
		if (parentNode != null && parentNode.getLevel() != null) {
			wUser.setLevel(parentNode.getLevel() + 1);
		}
		super.save(wUser);
		UserVerified userVerified = new UserVerified();
		userVerified.setWuser(wUser);
		userVerified.setMoblieActive(Short.valueOf("1"));
		securityInfoService.saveUserVerified(userVerified);
		try {
			VolumeDetail newVolumeDetail = volumeDetailServiceImpl.getNewVolumeDetail(wUser.getId());
			if (newVolumeDetail != null) {
				wUser.setVolumeNum(1);
				VolumeDeductible volumeDeductible = newVolumeDetail.getVolumeDeductible();
				if (volumeDeductible != null) {
					wUser.setVolumePrice(volumeDeductible.getMoney());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			String dataDetail = "user:id:" + wUser.getId() + "|mobile:" + wUser.getMobile() + "|异常：" + e.getMessage();
			log.error(dataDetail);
			EmailExceptionHandler.getInstance().HandleExceptionWithData(e, "注册获取抵扣劵失败异常",
					this.getClass().getName() + ":saveWUser", dataDetail);
		}
	}

	@Override
	public WUser login(String loginName, String password) {
		if (StringUtil.isBlank(loginName) || StringUtil.isBlank(password)) {
			return null;
		}
		WUser wUser = getEntityDao().findByMobileOrEmail(loginName);
		if (wUser == null
				|| !wUser.getPassword().equals(passwordService.encryptPassword(password, wUser.getLoginSalt()))
				|| "-1".equals(wUser.getUserType()) || "-2".equals(wUser.getUserType())
				|| "-3".equals(wUser.getUserType())) {
			return null;
		}
		return wUser;
	}

	@Override
	public void updateUser(WUser wUser) {
		super.update(wUser);
	}

	@Override
	public WUser getUser(String id) {
		return getEntityDao().get(id);
	}

	@Override
	public WUser getUserByEmail(String email) {
		return getEntityDao().findByEmail(email);

	}

	@Override
	public List<WUser> queryAll() {
		return this.getEntityDao().findAll();
	}

	@Override
	public PageInfo<WUser> queryDataPage(PageInfo<WUser> page) {
		page.setCurrentPage(page.getCurrentPage() - 1);
		page = this.getEntityDao().queryDataPageByConndition(page, null, null, null, null, null);
		return page;
	}

	@Override
	public PageInfo<WUser> queryDataPage(PageInfo<WUser> page, WUser wuser) {
		page.setCurrentPage(page.getCurrentPage() - 1);
		Map<String, Object> equals = new HashMap<String, Object>();
		Map<String, String> isLike = new HashMap<String, String>();
		if (wuser != null) {
			wuser.getMobile();
			// TODO do liu qing
			// if (wuser.getActivityType() != null) {
			// equals.put("activityType", wuser.getActivityType());
			// }
			if (wuser.getMobile() != null && !"".equals(wuser.getMobile())) {
				isLike.put("mobile", wuser.getMobile() + "%");
			}
		}
		return this.getEntityDao().queryDataPageByConndition(page, equals, isLike, null, null, null);
	}

	@Override
	public PageInfo<WUser> queryDataPageByConndition(PageInfo<WUser> page, final Map<String, Object> equals,
			final Map<String, String> isLike, final Map<String, Boolean> orders) {

		Page<WUser> pageData = this.getEntityDao().findAll(new Specification<WUser>() {
			@Override
			public Predicate toPredicate(Root<WUser> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

				List<Predicate> pres = new ArrayList<Predicate>();
				if (equals != null && equals.size() > 0) {
					for (Map.Entry<String, Object> equalObj : equals.entrySet()) {
						String[] names = StringUtil.split(equalObj.getKey(), ".");
						if (names.length > 1) {
							Path expression = root.get(names[0]);
							expression = expression.get(names[1]);
							Predicate pre = cb.equal(expression, equalObj.getValue());
							pres.add(pre);
						} else {
							Path<Object> path = root.get(equalObj.getKey());
							Predicate pre = cb.equal(path, equalObj.getValue());
							pres.add(pre);
						}
					}
				}
				if (isLike != null && isLike.size() > 0) {
					for (Map.Entry<String, String> isLikeObj : isLike.entrySet()) {
						String[] names = StringUtil.split(isLikeObj.getKey(), ".");
						if (names.length > 1) {
							Path expression = root.get(names[0]);
							expression = expression.get(names[1]);
							Predicate pre = cb.like(expression, "%" + isLikeObj.getValue() + "%");
							pres.add(pre);
						} else {
							Path<String> path = root.get(isLikeObj.getKey());
							Predicate pre = cb.like(path, "%" + isLikeObj.getValue() + "%");
							pres.add(pre);
						}
					}
				}
				List<Order> queryOrders = new ArrayList<Order>();
				if (orders != null && orders.size() > 0) {
					for (Map.Entry<String, Boolean> order : orders.entrySet()) {
						if (order.getValue()) {
							queryOrders.add(cb.asc(root.get(order.getKey())));
						} else {
							queryOrders.add(cb.desc(root.get(order.getKey())));
						}
					}
				}
				if (queryOrders.size() > 0) {
					query.orderBy(queryOrders);
				}
				cb.createQuery();
				return cb.and(pres.toArray(new Predicate[pres.size()]));

			}
		}, new PageRequest(page.getCurrentPage(), page.getCountOfCurrentPage()));
		page.setTotalCount(pageData.getTotalElements());
		page.setPageResults(pageData.getContent());
		return page;
	}

	@Override
	public PageInfo<WUser> queryDataPageByWuserOne(PageInfo<WUser> page, WUser wuser) {
		Map<String, Object> equals = new HashMap<String, Object>();
		Map<String, String> isLike = new HashMap<String, String>();
		Map<String, Boolean> orders = new HashMap<String, Boolean>();
		equals.put("userVerified.status", UserVerified.Idcard.NOPASS);
		return this.queryDataPageByConndition(page, equals, isLike, orders);
	}

	@Override
	public PageInfo<WUser> queryDataPageByWuserTwo(PageInfo<WUser> page, WUser wuser) {
		Map<String, Object> equals = new HashMap<String, Object>();
		Map<String, String> isLike = new HashMap<String, String>();
		Map<String, Boolean> orders = new HashMap<String, Boolean>();
		equals.put("userVerified.status", UserVerified.Idcard.UPLOADSTATUS);
		return this.queryDataPageByConndition(page, equals, isLike, orders);
	}

	@Override
	public PageInfo<WUser> queryDataPageByWuserThree(PageInfo<WUser> page, WUser wuser) {
		Map<String, Object> equals = new HashMap<String, Object>();
		Map<String, String> isLike = new HashMap<String, String>();
		Map<String, Boolean> orders = new HashMap<String, Boolean>();
		equals.put("userVerified.status", UserVerified.Idcard.NOAUTHPASS);
		return this.queryDataPageByConndition(page, equals, isLike, orders);
	}

	@SuppressWarnings("unchecked")
	@Override
	public PageInfo<UserVerifiedVo> queryDataPageByUserVerifiedVoThree(PageInfo<UserVerifiedVo> dataPage,
			ConnditionVo connVo) {

		StringBuffer sql = new StringBuffer(" SELECT u.id,u.mobile,v.tname,v.idCard,v.`status`, ");
		sql.append(" v.updat_user_id,v.not_by_reason,u.ctime,v.last_submit_verified_time ");
		sql.append(" FROM w_user u , w_user_verified v WHERE u.id= v.uid ");
		Object mobileObj = connVo.getValue("mobile");
		String mobile = mobileObj == null ? null : mobileObj.toString();
		Object statusObj = connVo.getValue("status");
		List<Object> params = new ArrayList<Object>();
		if (statusObj != null && StringUtil.isNotBlank(statusObj.toString())) {
			sql.append(" AND v.`status`=? ");
			params.add(statusObj.toString());
		} else {
			sql.append(" AND v.`status` in(?,?) ");
			params.add(1);
			params.add(2);
		}
		if (!StringUtil.isBlank(mobile)) {
			sql.append(" AND u.mobile like ? ");
			params.add("%" + mobile + "%");
		}
		sql.append(" ORDER BY v.last_submit_verified_time DESC ");

		dataPage = this.getEntityDao().queryPageBySql(dataPage, sql.toString(), UserVerifiedVo.class, null,
				params.toArray());
		return dataPage;
	}

	@Override
	public WUser queryWuserByUsername(String username) {
		Map<String, Object> equals = new HashMap<String, Object>();
		Map<String, String> isLike = new HashMap<String, String>();
		Map<String, Boolean> orders = new HashMap<String, Boolean>();
		equals.put("mobile", username);
		List<WUser> datas = this.queryByConndition(equals, isLike, orders);
		if (datas != null && datas.size() > 0) {
			return datas.get(0);
		}
		return null;
	}

	@Override
	public List<WUser> queryByConndition(final Map<String, Object> equals, final Map<String, String> isLike,
			final Map<String, Boolean> orders) {

		List<WUser> data = this.getEntityDao().findAll(new Specification<WUser>() {
			@Override
			public Predicate toPredicate(Root<WUser> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

				List<Predicate> pres = new ArrayList<Predicate>();
				if (equals != null && equals.size() > 0) {
					for (Map.Entry<String, Object> equalObj : equals.entrySet()) {
						String[] names = StringUtil.split(equalObj.getKey(), ".");
						if (names.length > 1) {
							Path expression = root.get(names[0]);
							expression = expression.get(names[1]);
							Predicate pre = cb.equal(expression, equalObj.getValue());
							pres.add(pre);
						} else {
							Path<Object> path = root.get(equalObj.getKey());
							Predicate pre = cb.equal(path, equalObj.getValue());
							pres.add(pre);
						}
					}
				}
				if (isLike != null && isLike.size() > 0) {
					for (Map.Entry<String, String> isLikeObj : isLike.entrySet()) {
						String[] names = StringUtil.split(isLikeObj.getKey(), ".");
						if (names.length > 1) {
							Path expression = root.get(names[0]);
							expression = expression.get(names[1]);
							Predicate pre = cb.like(expression, "%" + isLikeObj.getValue() + "%");
							pres.add(pre);
						} else {
							Path<String> path = root.get(isLikeObj.getKey());
							Predicate pre = cb.like(path, "%" + isLikeObj.getValue() + "%");
							pres.add(pre);
						}
					}
				}
				List<Order> queryOrders = new ArrayList<Order>();
				if (orders != null && orders.size() > 0) {
					for (Map.Entry<String, Boolean> order : orders.entrySet()) {
						if (order.getValue()) {
							queryOrders.add(cb.asc(root.get(order.getKey())));
						} else {
							queryOrders.add(cb.desc(root.get(order.getKey())));
						}
					}
				}
				if (queryOrders.size() > 0) {
					query.orderBy(queryOrders);
				}
				cb.createQuery();
				return cb.and(pres.toArray(new Predicate[pres.size()]));

			}
		});
		return data;
	}

	@Override
	public PageInfo<WUser> queryDataPageByAgents(PageInfo<WUser> page, AgentVo agentVo) {

		try {
			Map<String, Object> equals = new HashMap<String, Object>();
			Map<String, String> isLike = new HashMap<String, String>();
			Map<String, Boolean> orders = new HashMap<String, Boolean>();
			orders.put("userType", false);
			if (agentVo != null) {
				if (agentVo.getMobile() != null && !"".equals(agentVo.getMobile())) {
					isLike.put("mobile", agentVo.getMobile());
				}
				if (agentVo.getUname() != null && !"".equals(agentVo.getUname())) {
					isLike.put("userVerified.tname", agentVo.getUname() + "%");
				}
			}
			// equals.put("userType","1");
			return this.queryDataPageByConndition(page, equals, isLike, orders);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<WUser> queryByUserType(String userType) {
		return this.getEntityDao().findByUserType(userType);
	}

	@Override
	public List<WUser> queryByCommissionDesc() {
		PageInfo<WUser> pageInfo = new PageInfo<WUser>(10, 1);
		Map<String, Boolean> orderMap = new HashMap<String, Boolean>();
		orderMap.put("totalCommission", false);
		pageInfo = this.query(pageInfo, null, orderMap);
		return pageInfo.getPageResults();
	}

	@Override
	public void changeNoticeStatus(String[] uids, Integer noticeStatus) {
		for (String uid : uids) {
			WUser wUser = this.get(uid);
			if (ObjectUtil.equals(null, wUser)) {
				throw new WuserException("user.trade.not.enough.balance.change.notice.fail", null);
			}

			wUser.setNoticeStatus(noticeStatus);
			update(wUser);

			// 保存通知记录
			noticeRecordService.save(new NoticeRecord(uid,
					"次日余额不足电话通知用户：" + wUser.getUserVerified().getTname() + " " + CacheManager
							.getDataMapByKey(DataDicKeyConstants.CALL_NOTICE_STATUS, String.valueOf(noticeStatus)),
					1, noticeStatus));
		}
	}

	@Override
	public List<WUser> findByIdIn(Collection<String> ids) {
		return this.getEntityDao().findByIdIn(ids);
	}

	@Override
	public void warehouseExplosion(String userId, UserFund arrearsUserFund) {
		String groupId = arrearsUserFund.getLid();

		if (userId == null || "".equals(userId)) {
			return;
		}
		if (arrearsUserFund == null || arrearsUserFund.getMoney() == 0) {
			return;
		}
		// 注意（欠费计算前取绝对值）
		WUser wuser = this.get(userId);
		// 可用余额
		Double avlBal = wuser.getAvlBal();
		BigDecimal avlBalBig = new BigDecimal(avlBal.toString());
		BigDecimal moneyBig = new BigDecimal(Math.abs(arrearsUserFund.getMoney()) + "");
		// 1)查询出UserFund 配置配资欠费记录
		// 2)用欠费金额从大到小排序【因欠费存的数据都为负数-34、-50、-80】未支付
		List<UserFund> unPaids = this.userFundDao.findByUidAndPayStatusAndTypeOrderByMoneyDesc(userId,
				TypeConvert.UN_PAID, 18);
		unPaids.add(arrearsUserFund);
		if ((unPaids == null || unPaids.size() == 0)
				&& avlBalBig.subtract(moneyBig).compareTo(new BigDecimal("0")) >= 0) {
			arrearsUserFund.setPayStatus(TypeConvert.PAID);
			arrearsUserFund.setUid(wuser.getId());
			this.userFundService.rechargeOperation(arrearsUserFund, TypeConvert.TAKE_DEPOSIT_TYPE_OUTSTORE);
			return;
		}

		// Withdrawal
		// 取款余额
		Double withdrawalMoney = arrearsUserFund.getMoney();
		// 1)判断是否有余额【没有时】
		// avlBal <= 0
		if (avlBalBig.compareTo(new BigDecimal("0")) <= 0) {
			UserFund addUserFund = new UserFund();
			addUserFund.setMoney(withdrawalMoney);
			addUserFund.setType(TypeConvert.USER_FUND_C_TYPE_ARREARS);
			addUserFund.setStatus(TypeConvert.UN_PAID);
			addUserFund.setUid(wuser.getId());
			addUserFund.setLid(groupId);
			addUserFund.setRemark(arrearsUserFund.getRemark());
			this.userFundService.rechargeOperation(addUserFund, TypeConvert.TAKE_DEPOSIT_TYPE_OUTSTORE);
		} else {
			// 有余额的情况下
			int i = 1;
			for (UserFund userFund : unPaids) {

				// 欠费金额
				Double arrearsMoney = Math.abs(userFund.getMoney());
				BigDecimal arrearsMoneyBig = new BigDecimal(arrearsMoney.toString());
				if (avlBalBig.compareTo(arrearsMoneyBig) == 0) {
					userFund.setPayStatus(TypeConvert.PAID);
					userFund.setRemark(TypeConvert.payRemark("欠费", userFund.getMoney()));
					userFund.setUid(wuser.getId());
					// 取出余额支付
					this.userFundService.rechargeOperation(userFund, TypeConvert.TAKE_DEPOSIT_TYPE_OUTSTORE);
					break;
				} else if (avlBalBig.compareTo(arrearsMoneyBig) > 0) {
					// avlBal > arrearsMoney
					// avlBal -= arrearsMoney;
					avlBalBig = avlBalBig.subtract(arrearsMoneyBig);
					userFund.setPayStatus(TypeConvert.PAID);
					userFund.setRemark(TypeConvert.payRemark("欠费", userFund.getMoney()));
					userFund.setUid(wuser.getId());
					// 取出余额 ==支付==>爆仓欠费
					this.userFundService.rechargeOperation(userFund, TypeConvert.TAKE_DEPOSIT_TYPE_OUTSTORE);
				} else {
					// 余额不足的情况
					// avlBal > 0
					if (avlBalBig.compareTo(new BigDecimal("0")) > 0) {
						userFund.setAddtime(Dates.getCurrentLongDate() + i);
						userFund.setUptime(Dates.getCurrentLongDate() + i);
						// arrearsMoney -= avlBal;
						arrearsMoneyBig = TypeConvert.scale(arrearsMoneyBig.subtract(avlBalBig), 2);
						userFund.setMoney(avlBalBig.doubleValue());
						userFund.setType(TypeConvert.USER_FUND_C_TYPE_ARREARS);
						userFund.setPayStatus(TypeConvert.PAID);
						userFund.setRemark(TypeConvert.payRemark("欠费", userFund.getMoney()));
						userFund.setLid(groupId);
						userFund.setUid(wuser.getId());
						// 取出余额支付欠费
						this.userFundService.rechargeOperation(userFund, TypeConvert.TAKE_DEPOSIT_TYPE_OUTSTORE);
						i++;
						UserFund addUserFund = new UserFund();
						addUserFund.setType(TypeConvert.USER_FUND_C_TYPE_ARREARS);
						addUserFund.setMoney(-arrearsMoneyBig.doubleValue());
						addUserFund.setRemark(TypeConvert.payRemark("配资欠费", addUserFund.getMoney()));
						addUserFund.setAddtime(Dates.getCurrentLongDate() + i);
						addUserFund.setUptime(Dates.getCurrentLongDate() + i);
						addUserFund.setPayStatus(TypeConvert.UN_PAID);
						addUserFund.setTypeStatus(1);
						addUserFund.setUnpayFlag(Boolean.TRUE);
						addUserFund.setUid(wuser.getId());
						addUserFund.setLid(groupId);
						// 取出余额支付欠费
						this.userFundService.rechargeOperation(addUserFund, TypeConvert.TAKE_DEPOSIT_TYPE_OUTSTORE);
					}
					break;
				}
			}
			i++;
		}

	}

	@Override
	public List<TransferVo> queryUserTradeTodayAndOneBy() {
		String sql = " SELECT t.id, t.parent_account_no parentAccountNo,t.combine_id combineId,t.finished_money finishedMoney"
				+ " FROM w_user_trade t" + " WHERE  t.endtime >= ? AND t.endtime < ? AND t.fee_type IN (0,1) "
				+ " OR t.transfer_state IS NULL " + " AND t.transfer_state != " + TypeConvert.TRANSFER_STATE_FAIL
				+ " AND t.transfer_state != " + TypeConvert.TRANSFER_STATE_SUCCESSFUL;
		// 获取前一天临界时间
		Long start = TypeConvert.longCriticalTimeDay(-1);
		Long end = TypeConvert.longCriticalTimeDay(0);
		return this.getEntityDao().queryBySql(sql, TransferVo.class, start, end);
	}

	@Override
	public long countUser() {
		return this.getEntityDao().count();
	}

	@Override
	public List<WuserVo> findProfit(int topnum) {
		String sql = "select u.mobile,t.accruals from " + "(select uid,sum(accrual) as accruals from w_user_trade "
				+ " s where s.status=2 group by uid  HAVING sum(accrual) >0 " + "ORDER BY sum(accrual) desc  limit  "
				+ topnum + ") t, w_user u where u.id=t.uid";
		List<Object> params = Lists.newArrayList();
		List<WuserVo> users = nativeQuery(sql, params, WuserVo.class);
		return users;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tzdr.business.service.wuser.WUserService#getUserCount()
	 */
	@Override
	public List<WuserVo> getUserCount() {
		String sql = " select count(*) as usercount from w_user where user_type='" + WUser.UserType.TRADE + "'";
		List<Object> params = Lists.newArrayList();
		List<WuserVo> users = nativeQuery(sql, params, WuserVo.class);
		return users;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.tzdr.business.service.wuser.WUserService#getUserTradecount()
	 */
	@Override
	public List<WuserVo> getUserTradecount() {
		String sql = "select sum(t.money) as totalMoney from w_user_trade t";
		List<Object> params = Lists.newArrayList();
		List<WuserVo> users = nativeQuery(sql, params, WuserVo.class);
		return users;
	}

	@Override
	public WUser findByGeneralizeId(String generalizeId) {
		return this.getEntityDao().findByGeneralizeId(generalizeId);
	}

	@Override
	public WUser findByMobileOrEmail(String loginName) {

		return getEntityDao().findByMobileOrEmail(loginName);
	}

	@Override
	public PageInfo<ActivityWuserVo> queryActivityDataPage(PageInfo<ActivityWuserVo> page,
			ActivityWuserVo activityUserVo) {
		StringBuffer sqlBuf = new StringBuffer(
				" SELECT w.id,w.mobile,v.tname uname,v.idcard idcard,w.activity_type activityType"
						+ " ,w.user_type userType,w.ctime,w.last_login_time lastLoginTime, s.realname sname "
						+ " FROM w_user w LEFT JOIN w_user_verified v ON w.id=v.uid "
						+ " LEFT JOIN sys_user_extend e ON e.uid=w.id " + " LEFT JOIN sys_user s ON s.id=e.sys_user_id "
						+ " WHERE w.activity_type IN (" + TypeConvert.EVENT_ACTIVITY_TYPE + ", "
						+ TypeConvert.EVENT_ACTIVITY_TYPE_6600 + ") ");
		List<Object> params = new ArrayList<Object>();
		if (activityUserVo != null) {
			if (activityUserVo.getMobile() != null && !"".equals(activityUserVo.getMobile())) {
				sqlBuf.append(" AND w.mobile like ?");
				params.add(activityUserVo.getMobile() + "%");
			}
			if (activityUserVo.getLastLoginStateValue() != null
					&& !"".equals(activityUserVo.getLastLoginStateValue())) {
				if ("1".equals(activityUserVo.getLastLoginStateValue())) {
					sqlBuf.append(" AND w.last_login_time IS NOT NULL");
				} else {
					sqlBuf.append(" AND w.last_login_time IS NULL");
				}
			}
			if (activityUserVo.getAssetStateValue() != null && !"".equals(activityUserVo.getAssetStateValue())) {
				params.add(new Integer(activityUserVo.getAssetStateValue()));
				sqlBuf.append(" AND w.user_type = ?");
			} else {
				sqlBuf.append(" AND w.user_type >= 0");
			}

			// 增加6600 | 8800 活动用户类型过滤条件
			if (null != activityUserVo.getEventTypeStateValue()
					&& !"".equals(activityUserVo.getEventTypeStateValue())) {
				params.add(Integer.valueOf(activityUserVo.getEventTypeStateValue()));
				sqlBuf.append(" AND w.activity_type = ?");
			}

			// 增加所属销售过滤条件
			if (null != activityUserVo.getSname() && !"".equals(activityUserVo.getSname())) {
				params.add(activityUserVo.getSname());
				sqlBuf.append(" AND s.realname = ?");
			}
		} else {
			sqlBuf.append(" AND w.user_type >= 0");
		}
		return this.getEntityDao().queryDataPageBySql(page, sqlBuf.toString(), ActivityWuserVo.class, params.toArray());
	}

	@Override
	public List<ActivityWuserVo> queryActivityData() {
		StringBuffer sqlBuf = new StringBuffer(
				" SELECT w.id,w.mobile,w.uname,v.idcard idcard,w.activity_type activityType"
						+ " ,w.user_type userType,w.ctime,w.last_login_time lastLoginTime"
						+ " FROM w_user w LEFT JOIN w_user_verified v ON w.id=v.uid " + " WHERE w.activity_type = "
						+ TypeConvert.EVENT_ACTIVITY_TYPE);
		sqlBuf.append(" AND w.user_type >= 0");
		return this.getEntityDao().queryBySql(sqlBuf.toString(), ActivityWuserVo.class, null);
	}

	@Override
	public List<WUser> findByActivityTypeAndUserType(int activityType, String userType) {
		return getEntityDao().findByActivityTypeAndUserType(activityType, userType);
	}

	/*
	 * 
	 * 8800活动 结束前一天发短信
	 */
	@Override
	public void executeEndDay() throws InterruptedException {
		List<WUser> list = findByActivityTypeAndUserType(WUser.ActivityType.ACTIVITY_8800, WUser.UserType.TRADE);
		for (WUser wUser : list) {
			Thread.sleep(500);
			new SMSSenderThread(wUser.getMobile(), "8800.template", null).start();
		}
	}

	@Override
	public int activityWuserCount() {
		List<SumSingleVo> list = this.getEntityDao()
				.queryBySql("SELECT count(*) from w_user w WHERE w.activity_type = 1", SumSingleVo.class, null);
		return list.get(0).getTotal().intValue();
	}

	@Override
	public WUser saveActivityWUser(WUser wuser) {
		return saveActivityWUser(wuser, true);
	}

	@Override
	public WUser saveActivityWUser(WUser wuser, boolean isSms) {
		wuser.setUserType(WUser.UserType.WEB_REGIST);
		// 活动账户
		wuser.setActivityType(1);
		wuser.setCtime(TypeConvert.dbDefaultDate());

		String password = "t" + RandomCodeUtil.randStr(6);
		String plainText = new String(password);
		wuser.setPassword(password);
		this.saveWUser(wuser);
		// 活动默认充值800
		UserFund userFund = new UserFund();
		userFund.setUid(wuser.getId());
		userFund.setMoney(TypeConvert.EVENT_ACTIVITY_DEFAULT_AVL_MONEY);
		userFund.setType(TypeConvert.USER_FUND_C_TYPE_EVENT_AVL);
		userFund.setRemark("8800活动赠送" + TypeConvert.EVENT_ACTIVITY_DEFAULT_AVL_MONEY + "元");
		this.userFundService.rechargeOperation(userFund, TypeConvert.TAKE_DEPOSIT_TYPE_INSTORE);
		WUser tempWuser = new WUser();
		tempWuser.setId(wuser.getId());
		tempWuser.setPassword(plainText);
		tempWuser.setMobile(wuser.getMobile());
		if (isSms) {
			SMSSender.getInstance().sendByTemplateClasspath(dataMapService.getSmsContentOthers(), "activity002",
					wuser.getMobile(), wuser.getMobile(), tempWuser.getPassword());
		}

		return tempWuser;
	}

	/**
	 * @Description: TODO(生成推广码)
	 * @Title: getNewGeneralizeId
	 * @return String 返回类型
	 */
	public String getNewGeneralizeId() {
		String newGeneralizeId = RandomCodeUtil.randStr(6);
		WUser generalizeWuser = this.findByGeneralizeId(newGeneralizeId);
		if (generalizeWuser != null) {
			return this.getNewGeneralizeId();
		}
		return newGeneralizeId;
	}

	@Override
	public void updateWUserByUserId(String userId, String ip) {
		WUser wuser = getEntityDao().get(userId);
		wuser.setLastbeforeloginTime(wuser.getLastLoginTime());
		wuser.setLastLoginTime(TypeConvert.dbDefaultDate());
		wuser.setLastLoginIp(ip);
		getEntityDao().update(wuser);
	}

	@Override
	public PageInfo<WuserListVo> queryDataPageWuserListVo(PageInfo<WuserListVo> page, ConnditionVo connVo) {

		StringBuffer sqlBuf = new StringBuffer(
				" SELECT w.id,v.tname,w.uname,w.email,w.mobile,w.ctime,w.user_type userType"
						+ " ,v.idcard,w.last_login_time lastLoginTime,w.avl_bal avlBal,"
						+ " (SELECT SUM(t.money)FROM w_user_trade t WHERE t.uid=w.id AND t.`status`=1) allocationMoney"
						+ " , w.frz_bal frzBal,v.alipay_account alipayAccount,w.source,w.channel,w.keyword, "
						+ "(SELECT SUM(l.actual_money) FROM w_recharge_list l WHERE l.uid=w.id AND l.type>=2 AND (l.type<=4 OR sys_type = '1')) totalCharge,"
						+ "(SELECT SUM(f.trader_bond + f.append_trader_bond) FROM f_simple_ftse_user_trade f WHERE f.uid=w.id AND (f.state_type <> 1 OR f.state_type <> 5)) totalOperate,"
						+ "(SELECT SUM(f.tran_actual_lever) FROM f_simple_ftse_user_trade f WHERE f.uid=w.id  AND business_type = 7  AND (f.state_type <> 1 OR f.state_type <> 5)) htranActualLever,"
						+ "(SELECT SUM(f.tran_actual_lever) FROM f_simple_ftse_user_trade f WHERE f.uid=w.id AND  business_type = 6 AND  (f.state_type <> 1 OR f.state_type <> 5) ) ytranActualLever,"
						+ "(SELECT SUM(f.tran_actual_lever) FROM f_simple_ftse_user_trade f WHERE f.uid=w.id AND  business_type = 0 AND (f.state_type <> 1 OR f.state_type <> 5)) atranActualLever,"
						+ "(SELECT SUM(f.small_crude_oil_market_lever+f.ame_copper_market_lever+f.ame_silver_market_lever+f.ag_tran_actual_lever+f.crude_tran_actual_lever+f.daxtran_actual_lever+f.hsi_tran_actual_lever+f.h_stock_market_lever"
                        +"+f.lhsi_tran_actual_lever+f.mbtran_actual_lever+f.mdtran_actual_lever+f.mntran_actual_lever+f.nikkei_tran_actual_lever+f.tran_actual_lever+f.xhstock_market_lever) "
                        +"FROM f_simple_ftse_user_trade f WHERE f.uid=w.id AND business_type = 8 AND (f.state_type <> 1 OR f.state_type <> 5)) interActualLever,"
						+ "(select SUM(d.money) from w_draw_list d where d.uid=w.id and d.`status`=31) withDrawMoney"
						+ " FROM w_user w LEFT JOIN w_user_verified v ON w.id=v.uid WHERE 1=1 ");
		List<Object> params = new ArrayList<Object>();
		if (connVo != null) {
			Object ctimeStrStartObj = connVo.getValue("ctimeStr_start");
			Object ctimeStrEndObj = connVo.getValue("ctimeStr_end");
			Object lastLoginTimeStrStartObj = connVo.getValue("lastLoginTimeStr_start");
			Object lastLoginTimeStrEndObj = connVo.getValue("lastLoginTimeStr_end");
			Object mobileObj = connVo.getValue("mobile");
			Object mailObj = connVo.getValue("mail");
			Object sourceObj = connVo.getValue("source");
			Object tnameObj = connVo.getValue("tname");
			Object alipayAccountObj = connVo.getValue("alipayAccount");
			Object channel = connVo.getValue("channel");
			Object keyword = connVo.getValue("keyword");
			if (ctimeStrStartObj != null && ctimeStrEndObj != null) {
				if (TypeConvert.objToStrIsNotNull(ctimeStrStartObj) != null
						&& TypeConvert.objToStrIsNotNull(ctimeStrEndObj) != null) {
					sqlBuf.append(" AND (w.ctime >= ? AND w.ctime <= ?)");
					params.add(TypeConvert.strToDatetime1000Long(ctimeStrStartObj));
					params.add(TypeConvert.strToDatetime1000Long(ctimeStrEndObj));
				}
			}
			if (lastLoginTimeStrStartObj != null && lastLoginTimeStrEndObj != null) {
				if (TypeConvert.objToStrIsNotNull(lastLoginTimeStrStartObj) != null
						&& TypeConvert.objToStrIsNotNull(lastLoginTimeStrEndObj) != null) {
					sqlBuf.append(" AND (w.last_login_time >= ? AND w.last_login_time <= ?)");
					params.add(TypeConvert.strToDatetime1000Long(lastLoginTimeStrStartObj));
					params.add(TypeConvert.strToDatetime1000Long(lastLoginTimeStrEndObj));
				}
			}
			if (mobileObj != null) {
				if (TypeConvert.objToStrIsNotNull(mobileObj) != null) {
					sqlBuf.append(" AND w.mobile like ?");
					params.add(String.valueOf(mobileObj) + "%");
				}
			}
			if (mailObj != null) {
				if (TypeConvert.objToStrIsNotNull(mailObj) != null) {
					sqlBuf.append(" AND w.email like ?");
					params.add(String.valueOf(mailObj) + "%");
				}
			}
			if (channel != null) {
				if (TypeConvert.objToStrIsNotNull(channel) != null) {
					sqlBuf.append(" AND w.channel like ?");
					params.add(String.valueOf(channel) + "%");
				}
			}
			if (keyword != null) {
				if (TypeConvert.objToStrIsNotNull(keyword) != null) {
					sqlBuf.append(" AND w.keyword like ?");
					params.add(String.valueOf(keyword) + "%");
				}
			}
			if (sourceObj != null) {
				if (TypeConvert.objToStrIsNotNull(sourceObj) != null) {
					StringBuilder whereBuilder = new StringBuilder();
					int source = Integer.parseInt(String.valueOf(sourceObj));
					switch (source) {
					case 2:
					case 5:
					case 6:
					case 7:
						whereBuilder.append(" and w.source = ?");
						params.add(source);
						break;
					case 1:
						whereBuilder.append(" and w.source not in (?,?,?,?)");
						params.add(2);
						params.add(5);
						params.add(6);
						params.add(7);
						break;
					}
					/*
					 * String sqlSource = source == 1 ? " AND w.source IN(?,?)"
					 * : " AND w.source NOT IN(?,?)";
					 */
					sqlBuf.append(whereBuilder.toString());
					// params.add(2);
					// params.add(5);
				}
			}

			if (tnameObj != null) {
				if (TypeConvert.objToStrIsNotNull(tnameObj) != null) {
					sqlBuf.append(" AND v.tname like ?");
					params.add(String.valueOf(tnameObj) + "%");
				}
			}
			if (alipayAccountObj != null) {
				if (TypeConvert.objToStrIsNotNull(alipayAccountObj) != null) {
					sqlBuf.append(" AND v.alipay_account like ?");
					params.add(String.valueOf(alipayAccountObj) + "%");
				}
			}
		}
		sqlBuf.append(" ORDER BY w.ctime DESC ");

		return this.getEntityDao().queryDataPageBySql(page, sqlBuf.toString(), WuserListVo.class, params.toArray());
	}

	@Override
	public List<TransferVo> queryUserTradeTodayAndOneBySuccessful() {
		String sql = " SELECT t.id, t.parent_account_no parentAccountNo,t.combine_id combineId,t.finished_money finishedMoney"
				+ " FROM w_user_trade t" + " WHERE  t.endtime >= ? AND t.endtime < ?" + " AND t.transfer_state = "
				+ TypeConvert.TRANSFER_STATE_SUCCESSFUL;

		// 获取前一天临界时间
		Long start = TypeConvert.longCriticalTimeDay(-1);
		Long end = TypeConvert.longCriticalTimeDay(0);
		return this.getEntityDao().queryBySql(sql, TransferVo.class, start, end);
	}

	@Override
	public int queryWuserCountByMobile(String mobile) {

		Map<String, Object> equals = new HashMap<String, Object>();
		equals.put("mobile", mobile);
		List<WUser> wusers = this.getEntityDao().queryBySimple(equals, null, null);
		if (wusers != null && wusers.size() > 0) {
			return wusers.size();
		}
		return 0;
	}

	@Override
	public int findUsersByIp(String ip) {
		List<SumSingleVo> list = this.getEntityDao().queryBySql(
				"SELECT count(*) from " + "w_user w WHERE w.activity_type = 1 and reg_ip='" + ip + "'",
				SumSingleVo.class, null);
		return list.get(0).getTotal().intValue();

	}

	/*
	 * 生成非活动用户(non-Javadoc)
	 * 
	 * @see
	 * com.tzdr.business.service.wuser.WUserService#saveUser(com.tzdr.domain.web
	 * .entity.WUser)
	 */
	@Override
	public WUser saveUser(WUser wuser) {
		wuser.setUserType(WUser.UserType.WEB_REGIST);
		// 非活动账户
		wuser.setActivityType(0);
		wuser.setCtime(TypeConvert.dbDefaultDate());

		String password = "t" + RandomCodeUtil.randStr(6);
		String plainText = new String(password);
		wuser.setPassword(password);
		this.saveWUser(wuser);

		WUser tempWuser = new WUser();
		tempWuser.setId(wuser.getId());
		tempWuser.setPassword(plainText);
		tempWuser.setMobile(wuser.getMobile());

		SMSSender.getInstance().sendByTemplateClasspath(dataMapService.getSmsContentOthers(), "activity002",
				wuser.getMobile(), wuser.getMobile(), tempWuser.getPassword());

		return tempWuser;
	}

	@Override
	public List<WUser> queryNotSystemWuser() {
		Map<String, Object> notEquals = new HashMap<String, Object>();
		// notEquals.put("userType", "-1");
		// notEquals.put("userType", "-2");
		// notEquals.put("userType", "-3");
		return this.getEntityDao().queryBySimple(null, notEquals, null, null);
	}

	@Override
	public void updateAgentTree(String newSuperiorMobile, String subordinateUid) throws Exception {
		// 新上级用户信息
		WUser newsuperiorWuser = this.findByMobileOrEmail(newSuperiorMobile);
		// 下级用户信息
		WUser subordinateWuser = this.getEntityDao().get(subordinateUid);
		if (newsuperiorWuser != null && subordinateWuser != null) {
			Double newsuperiorRebate = newsuperiorWuser.getRebate(); // 新上级用户返点
			int newsuperiorLevel = newsuperiorWuser.getLevel(); // 新上级用户返点
			// 修改代理用户
			List<WUser> subordinateWuseList = new ArrayList<WUser>();
			// 处理待迁移代理信息
			subordinateWuser.setParentNode(newsuperiorWuser);
			this.recursionSubordinateWuser(subordinateWuser, subordinateWuseList, newsuperiorRebate, newsuperiorLevel);
			for (WUser wUser : subordinateWuseList) {
				if (newSuperiorMobile.equals(wUser.getMobile())) {
					throw new RuntimeException(
							"新上级【" + newSuperiorMobile + "】已经是【" + subordinateWuser.getMobile() + "】的代理用户,修改代理树失败！");
				}
			}
			for (WUser wUser : subordinateWuseList) {
				this.update(wUser); // 迁移代理树
			}
		}
	}

	/**
	 * 迭代代理
	 * 
	 * @param subordinateWuser
	 *            当前用户
	 * @param subordinateWuseList
	 *            迁移代理
	 * @param superiorRebate
	 *            上级返点
	 * @param superiorLevel
	 *            上级等级
	 * @return
	 */
	private void recursionSubordinateWuser(WUser subordinateWuser, List<WUser> subordinateWuseList,
			Double superiorRebate, int superiorLevel) {

		int subordinate = superiorLevel + 1;
		subordinateWuser.setLevel(subordinate); // 重置等级
		Double rebate = subordinateWuser.getRebate(); // 返点
		Double subordinateDefaultRebate = subordinateWuser.getSubordinateDefaultRebate(); // 默认返点

		if (rebate > superiorRebate) {
			rebate = superiorRebate;
			subordinateWuser.setRebate(rebate); // 重置返点
		}

		if (subordinateDefaultRebate > superiorRebate) {
			subordinateWuser.setSubordinateDefaultRebate(superiorRebate); // 重置默认返点
		}

		subordinateWuseList.add(subordinateWuser); // 迁移

		List<WUser> childs = subordinateWuser.getChilds();
		if (childs != null && !childs.isEmpty()) {
			for (WUser wuser : childs) {
				this.recursionSubordinateWuser(wuser, subordinateWuseList, rebate, subordinate);
			}
		}
	}

	@Override
	public void smsNoticeUser(String[] uids, int noticeStatus) {
		for (String uid : uids) {
			WUser wUser = this.get(uid);
			String content = MessageUtils.message("fee.fill.deduction.balance.next.day.not.enough");
			new SMSPgbSendForContentThread(wUser.getMobile(), content, 1000).start();

			if (ObjectUtil.equals(null, wUser)) {
				throw new WuserException("user.trade.not.enough.balance.change.notice.fail", null);
			}

			wUser.setNoticeStatus(noticeStatus);
			update(wUser);

			// 保存通知记录
			noticeRecordService.save(new NoticeRecord(uid,
					"次日余额不足手动触发短信通知用户：" + wUser.getUserVerified().getTname() + " " + CacheManager
							.getDataMapByKey(DataDicKeyConstants.CALL_NOTICE_STATUS, String.valueOf(noticeStatus)),
					1, noticeStatus));
		}

	}

	@Override
	public void updateActivityUser(String uid) throws Exception {
		Boolean deleted = Boolean.FALSE;

		UserExtend userExtend = userExtendService.findUserExtendIgnoreDeleted(uid, WUser.ActivityType.ACTIVITY_6600);
		if (!ObjectUtil.equals(null, userExtend) && !ObjectUtil.equals(null, deleted = userExtend.getDeleted())
				&& !userExtend.getDeleted()) {
			throw new UserException("business.add.data.is.exists", null);
		}
		// WUser wu = new WUser(uid);
		// wu.setActivityType(WUser.ActivityType.ACTIVITY_6600); //6600活动用户
		// 更新为6600用户
		nativeUpdate("UPDATE w_user SET activity_type=" + WUser.ActivityType.ACTIVITY_6600 + ", user_type=0 WHERE id=?",
				Arrays.asList((Object) uid));
		// update(wu);

		/**
		 * 已存在 则更新
		 */
		if (!ObjectUtil.equals(null, userExtend) && !ObjectUtil.equals(null, deleted) && deleted) {
			// 标记为未删除
			userExtend.setDeleted(Boolean.FALSE);
			userExtendService.update(userExtend);
		} else {
			User opUser = authService.getCurrentUser();
			userExtend = new UserExtend();
			userExtend.setCreateTime(Dates.getCurrentLongDate());
			userExtend.setUid(uid); // 前台用户id
			userExtend.setRemark("标记用户所属销售");
			// 设置所属销售
			userExtend.setSysUser(new User(opUser.getId()));
			// 所属销售名称
			userExtend.setCreateUser(opUser.getRealname());
			userExtend.setActivityType(WUser.ActivityType.ACTIVITY_6600);
			userExtend.setDeleted(Boolean.FALSE);
			userExtendService.save(userExtend);
		}

		// 资金明细插入对应有一条“活动赠送”收入600元的记录
		UserFund userFund = new UserFund();
		userFund.setUid(uid);
		userFund.setMoney(TypeConvert.EVENT_ACTIVITY_6600_AVL_MONEY);
		userFund.setType(TypeConvert.USER_FUND_C_TYPE_EVENT_AVL);
		userFund.setRemark("6600活动赠送" + TypeConvert.EVENT_ACTIVITY_6600_AVL_MONEY + "元");
		this.userFundService.rechargeOperation(userFund, TypeConvert.TAKE_DEPOSIT_TYPE_INSTORE);
	}

	@Override
	public List<WuserParentVo> queryUserParents(String uid) {
		List<WuserParentVo> results = Lists.newArrayList();
		return queryUserAgentInfo(uid, results);
	}

	/**
	 * 查询用户代理信息
	 * 
	 * @param uid
	 * @return
	 */
	private List<WuserParentVo> queryUserAgentInfo(String uid, List<WuserParentVo> results) {
		String sql = "SELECT usr.parent_id parentid,ver.tname,usr.mobile,usr.`level`,usr.rebate from w_user usr,w_user_verified ver where ver.uid=usr.id AND uid = ?";
		List<Object> params = Lists.newArrayList();
		params.add(uid);
		List<WuserParentVo> lists = nativeQuery(sql, params, WuserParentVo.class);
		if (CollectionUtils.isEmpty(lists)) {
			return results;
		}

		for (WuserParentVo wuserParentVo : lists) {
			results.add(wuserParentVo);
			if (wuserParentVo.getLevel() <= 3) {
				return results;
			}
			queryUserAgentInfo(wuserParentVo.getParentid(), results);
		}
		return results;
	}

	/**
	 * 调用存储过程
	 * 
	 * @param mobile
	 * @return
	 */
	public void lowerUserRebates(String mobile) {
		super.nativeQuery(mobile);

	}

	@Override
	public boolean luckDrawUpdateUser(Double money, String uid, String rewardId) {
		boolean flag = true;
		String activity = ExtensionConstants.ACTIVITY_TYPE;
		ActivityReward activityReward = activityRewardService.doGetById(rewardId);
		if (activityReward != null) {
			// 验证提交的金额是否和后台随机的金额一致
			if (Double.compare(activityReward.getMoney(), money) == 0) {
				WUser user = getUser(uid);
				if (user != null) {
					user.setFund(user.getFund() + money);
					// 更新用户的账户余额
					update(user);
					// 增加用户的抽奖记录
					LuckDraw draw = new LuckDraw();
					draw.setCreateTime(new Date().getTime() / 1000);
					draw.setUid(user.getId());
					draw.setMoney(money);
					draw.setActivity(activity);
					luckDrawService.save(draw);
					// 增加充值记录
					UserFund userFund = new UserFund();
					userFund.setUid(user.getId());
					userFund.setMoney(money);
					userFund.setType(TypeConvert.USER_FUND_C_TYPE_ARTIFICIAL_RECHARGE);
					userFund.setRemark("首次亏损抽奖：" + money + "元");
					userFundService.rechargeOperation(userFund, TypeConvert.TAKE_DEPOSIT_TYPE_INSTORE);
					// 更新用户抽奖权限表--标识为此抽奖次数已被使用
					activityReward.setIsvalid(true);
					activityReward.setMoney(money);
					activityRewardService.update(activityReward);
				} else {
					flag = false;
				}
			} else {
				flag = false;
			}
		} else {
			flag = false;
		}
		return flag;
	}

	public static void main(String[] args) {
	}
}

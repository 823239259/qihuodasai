package com.tzdr.business.service.future.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import jodd.util.StringUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.tzdr.business.cms.service.auth.AuthService;
import com.tzdr.business.service.future.FSimpleCouponService;
import com.tzdr.business.service.recharge.RechargeListService;
import com.tzdr.common.baseservice.BaseServiceImpl;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.exception.BusinessException;
import com.tzdr.common.utils.Dates;
import com.tzdr.common.utils.TypeConvert;
import com.tzdr.common.web.support.EasyUiPageInfo;
import com.tzdr.common.web.support.MultiListParam;
import com.tzdr.domain.cms.entity.user.User;
import com.tzdr.domain.dao.future.FSimpleCouponDao;
import com.tzdr.domain.vo.future.FSimpleCouponManageVo;
import com.tzdr.domain.vo.future.FSimpleCouponUseVo;
import com.tzdr.domain.vo.future.FSimpleCouponWebVo;
import com.tzdr.domain.web.entity.FSimpleFtseUserTrade;
import com.tzdr.domain.web.entity.UserTrade;
import com.tzdr.domain.web.entity.future.FSimpleCoupon;

/**
 * 期货优惠券 类说明
 * 
 * @author zhaozhao
 * @date 2016年3月1日下午1:53:54
 * @version 1.0
 */
@Service
@Transactional
public class FSimpleCouponServiceImpl extends BaseServiceImpl<FSimpleCoupon, FSimpleCouponDao> implements FSimpleCouponService {
		
	@Autowired
	private RechargeListService rechargeListService;
	
	@Autowired
	private AuthService authService;
		@Override
		public void saves(List<FSimpleCoupon> ts) throws BusinessException {
			
			super.saves(ts);
		}
		@Override
		public Integer checkName(String name)  {
			if(name==null){
				return null;
			}
			return this.getEntityDao().checkName(name);
		}
		
		public PageInfo<Object> queryData(EasyUiPageInfo  easyUiPage,Map<String, Object> searchParams) {
			PageInfo<Object> pageInfo = new PageInfo<Object>(easyUiPage.getRows(), easyUiPage.getPage());
			
			String sql = " SELECT sc.id id,sc.platform platform,sc.name name,sc.type type,sc.scope scope,sc.money money,sc.cycle cycle,IF(ISNULL(sc.cycle),sc.deadline,NULL) deadline,sc.create_user createUser,sc.create_time createTime,COUNT(sc.name) numToHave,count(case when sc.status in (2,3)  then sc. NAME end) numToLost FROM  f_simple_coupon sc group by sc.name";
			//params  查询参数  依次 存入
			MultiListParam  multilistParam  = new MultiListParam(easyUiPage, searchParams,null, sql);
			pageInfo = oldMultiListPageQuery(multilistParam,FSimpleCouponManageVo.class);
			return pageInfo;
		}


	@Override
	public List<Map<String, Object>> queryCouponByUserId(String userId, int type, int scope) {
		if ("".equals(userId)) {
			return new ArrayList<Map<String, Object>>();
		}
		StringBuffer sql = new StringBuffer(" select id, money from f_simple_coupon ");
		sql.append(" where user_id = ? and type = ? and status = 2 and find_in_set(?, scope) ");
		sql.append(" and ifnull(deadline, unix_timestamp(date_add(from_unixtime(grant_time),interval cycle day))) > ? ");
		sql.append(" order by money desc ");
		scope = (1 == scope || 2 == scope || 3 == scope || 4 == scope || 20 == scope) ? 5 : scope;
		switch (scope) {
		// 小恒指
		case 9:
			scope = 13;
			break;
		default:
			break;
		}
		return this.getEntityDao().queryMapBySql(sql.toString(), userId, type, scope, Dates.getCurrentLongDate());

	}
	
	/**
	 * 
	 */
	@Override
	public List<Map<String, Object>> queryCouponByUserId(String userId, int[] type, int scope) {
		if ("".equals(userId)) {
			return new ArrayList<Map<String, Object>>();
		}
		String tp = " and type in(";
		for(int i : type){
			tp = tp + i + ","; 
		}
		tp = tp.substring(0, tp.length()-1) + ") ";
		StringBuffer sql = new StringBuffer(" select * from f_simple_coupon fsc ");
		sql.append(" where user_id = ? and status = 2 and find_in_set(?, scope) " +tp);
		sql.append(" and ifnull(deadline, unix_timestamp(date_add(from_unixtime(grant_time),interval cycle day))) > unix_timestamp(NOW()) ");
		sql.append(" GROUP BY fsc.name");
		sql.append(" order by money desc ");
		scope = (1 == scope || 2 == scope || 3 == scope || 4 == scope || 20 == scope) ? 5 : scope;
		scope = 9 == scope ? 13 : scope;
		return this.getEntityDao().queryMapBySql(sql.toString(), userId,  scope);
		
	}
	
	@Override
	public List<FSimpleCoupon> findByName(String name) {
		// TODO Auto-generated method stub
		return this.getEntityDao().findByName(name);
	}
	@Override
	public List<FSimpleCoupon> queryCouponToGive() {
		// TODO Auto-generated method stub
		return this.getEntityDao().queryCouponToGive();
	}


	@Override
	public boolean isCouponValid(FSimpleCoupon coupon, int type, int scope) {
		// 匹配优惠券类型，状态和范围
		scope = (1 == scope || 2 == scope || 3 == scope || 4 == scope || 20 ==scope) ? 5 : scope;
		scope = scope == 9 ? 13 :scope;
		if (null == coupon || type != coupon.getType() || coupon.getStatus() != 2 || coupon.getScope().indexOf(scope + "") < 0) {
			return false;
		}
		// 判断代金券是否过期
		Long deadLine = coupon.getDeadline(); // 截止日期
		if (null == deadLine) {
			Long grantTime = coupon.getGrantTime(); // 发放时间
			Integer cycle = coupon.getCycle(); // 截止周期
			if (null == grantTime || null == cycle || 0 > cycle) {
				return false;
			}
			deadLine = Dates.toDate(Dates.dateAddDay(Dates.parseLong2Date(grantTime), cycle)).getTime() / 1000;
		}
		if (deadLine < Dates.getCurrentLongDate()) {
			return false;
		}
		return true;
	}

	
	
	
	@SuppressWarnings("unchecked")
	@Override
	public PageInfo<FSimpleCouponWebVo> findDataList(String pageIndex,
			String perPage, String uid, String platform) {
		PageInfo<FSimpleCouponWebVo> pageInfo= new PageInfo<FSimpleCouponWebVo>(Integer.valueOf(perPage),Integer.valueOf(pageIndex)+1);
		
		StringBuffer sql = new StringBuffer(" SELECT * FROM f_simple_coupon f WHERE f.user_id = ? and f.type in(1,2,3,6) and f.platform like '%"+platform+"%' ORDER BY ");
		sql.append(" if(UNIX_TIMESTAMP(NOW()) > f.deadline,4,f.`status`) ");
		sql.append(" ASC,f.grant_time DESC ");
		
		pageInfo = this.getEntityDao().queryPageBySql(pageInfo,sql.toString(),
				FSimpleCouponWebVo.class,null,uid);
		return pageInfo;
	}
	
	@Override
	public void updateFSimpleCoupon(FSimpleCoupon fSimpleCoupon, 
			String mobile,String userName, BigDecimal income, String uid) throws Exception {
		
		fSimpleCoupon.setUseTime(TypeConvert.dbDefaultDate());
		
		fSimpleCoupon.setStatus((short)3);
		
		fSimpleCoupon.setUserId(uid);
		
		fSimpleCoupon.setUserPhone(mobile);
		
		fSimpleCoupon.setUserName(userName);
		
		this.update(fSimpleCoupon);   //标志已经使用
		
		//划账
		rechargeListService.futureHandlerSaveRechargeStateWeb("GT"+ fSimpleCoupon.getId(),mobile, income.toString(), fSimpleCoupon.getName()+"现金红包。", TypeConvert.SYS_TYPE_ADJUSTMENT_OF_ACCOUNTS);
	}
	
	@Override
	public FSimpleCoupon getFSimpleCoupon(String id, String userId) {
		
		if(StringUtil.isBlank(id) || StringUtil.isBlank(userId)){
			return null;
		}
		
		List<FSimpleCoupon> fSimpleCouponList = this.getEntityDao().findByIdAndUserId(id, userId);
		
		FSimpleCoupon fSimpleCoupon = null;
		
		if(!CollectionUtils.isEmpty(fSimpleCouponList)){
			fSimpleCoupon = fSimpleCouponList.get(0);
		}
		
		return fSimpleCoupon;
	}
	
	
	
	public PageInfo<Object> queryList(EasyUiPageInfo  easyUiPage,Map<String, Object> searchParams) {
		PageInfo<Object> pageInfo = new PageInfo<Object>(easyUiPage.getRows(), easyUiPage.getPage());
		
		String sql = " SELECT sc.id id, sc.`name`, sc.type type, sc.scope scope, sc.platform platform, sc.money money, sc.create_time createTime, IF (( sc.`status` = 2 AND UNIX_TIMESTAMP( DATE_FORMAT(NOW(), '%Y-%m-%d %H:%m:%s')) > DATE_FORMAT(sc.deadline,'%Y-%m-%d %H:%m:%s') ),'4', CAST(sc.`status` AS CHAR)) `status`, sc.grant_time grantTime, sc.use_time useTime, sc.user_id userId, sc.user_name userName, sc.user_phone userPhone FROM f_simple_coupon sc where sc.status <> 1 order by status,grant_time desc ";
		//params  查询参数  依次 存入
		MultiListParam  multilistParam  = new MultiListParam(easyUiPage, searchParams,null, sql);
		pageInfo = multiListPageQuery(multilistParam,FSimpleCouponUseVo.class);
		return pageInfo;
	}
	@Override
	public List<FSimpleCoupon> findByStatusAndName(short status, String name) {
		// TODO Auto-generated method stub
		return this.getEntityDao().findByStatusAndName(status, name);
	}
	
	
	/**
	 * 新增优惠券
	 */
	public void addCoupons(FSimpleCouponManageVo fSimpleCouponManageVo,String name){
		List<FSimpleCoupon> ts=new ArrayList<FSimpleCoupon>();
		BigInteger numToHave = fSimpleCouponManageVo.getNumToHave();
		//numToHave为新增个数，循环进行新增
		for ( int i=1;i<=numToHave.intValue();i++) {
			FSimpleCoupon fSimpleCoupon = new FSimpleCoupon();
			fSimpleCoupon.setMoney(fSimpleCouponManageVo.getMoney());
			fSimpleCoupon.setType(fSimpleCouponManageVo.getType());
			fSimpleCoupon.setScope(fSimpleCouponManageVo.getScope());
			fSimpleCoupon.setPlatform("维胜");//fSimpleCouponManageVo.getPlatform()
			fSimpleCoupon.setName(name);
			//判断使用的是截止日期还是使用周期
			if(fSimpleCouponManageVo.getDeadline()!=null){//截止日期
				fSimpleCoupon.setDeadline(fSimpleCouponManageVo.getDeadline().longValue());
			}
			if(fSimpleCouponManageVo.getCycle()!=0&&fSimpleCouponManageVo.getCycle()!=null){//周期
				fSimpleCoupon.setCycle(fSimpleCouponManageVo.getCycle());
			}
			//设置创建人、创建日期等数据
			setOperateLog(fSimpleCoupon, "添加期货优惠券", "add");
			ts.add(fSimpleCoupon);
		}
		this.saves(ts);
	}
	
	/**
	 * 修改优惠券
	 */
	public List<FSimpleCoupon> edits(List<FSimpleCoupon> fS,String type,Long deadline,Integer cycle,Integer numToHave){
		List<FSimpleCoupon> ts=new ArrayList<FSimpleCoupon>();
		//fS是查询出来的优惠券集合
		if(type=="add"){
			//新增个数不为0
			for (int i = 0; i < numToHave; i++) {
				FSimpleCoupon fSimpleCoupon = new FSimpleCoupon();
				fSimpleCoupon.setMoney(fS.get(0).getMoney());
				fSimpleCoupon.setType(fS.get(0).getType());
				fSimpleCoupon.setScope(fS.get(0).getScope());
				fSimpleCoupon.setPlatform(fS.get(0).getPlatform());
				fSimpleCoupon.setName(fS.get(0).getName());
				for (int j = 0; j <fS.size(); j++) {
					if(deadline != null && deadline.toString() != "" && cycle == 0){
						fSimpleCoupon.setDeadline(deadline);
						//如果原来的优惠券截止日期不为空，则对其重新设置截止日期
						fS.get(j).setDeadline(deadline);
					}
					if(cycle != null && cycle != 0 && cycle.toString() != ""){
						fSimpleCoupon.setCycle(cycle);
						//如果原来的优惠券使用周期不为空，则对其重新设置使用周期，同时计算出其截止日期（根据发放时间GrantTime）
						fS.get(j).setCycle(cycle);
						if(fS.get(j).getGrantTime()!=null){
							Long deadLine = Dates.toDate(Dates.dateAddDay(Dates.parseLong2Date(fS.get(j).getGrantTime()),cycle)).getTime() / 1000;
							fS.get(j).setDeadline(deadLine);
						};
					}
				}
				//设置创建人、创建日期等数据
				setOperateLog(fSimpleCoupon, "修改优惠券-新增个数", "add");
				ts.add(fSimpleCoupon);
			}
		}else if(type=="edit"){
			//不新增，只修改截止日期或使用周期
			for (int j = 0; j <fS.size(); j++) {
				//当有截止日期时，对原来的截止日期进行覆盖
				if(deadline!=null&&deadline.toString()!=""&&cycle==0){
					fS.get(j).setDeadline(deadline);
				}
				//当有使用周期时，对其覆盖并重新计算截止日期
				if(cycle!=null&&cycle!=0&&cycle.toString()!=""){
					fS.get(j).setCycle(cycle);
					if(fS.get(j).getGrantTime()!=null){
						Long deadLine = Dates.toDate(Dates.dateAddDay(Dates.parseLong2Date(fS.get(j).getGrantTime()),cycle)).getTime() / 1000;
						fS.get(j).setDeadline(deadLine);
					};
				}
			}
		}
		return ts;
		
		
	}
	
	
	
	private void setOperateLog(FSimpleCoupon fSimpleCoupon,String operateContent,String operateType){
		User   loginUser = authService.getCurrentUser();	
		fSimpleCoupon.setOperateContent(operateContent);
		if (StringUtil.equals(operateType,"edit")){
			fSimpleCoupon.setUpdateTime(Dates.getCurrentLongDate());
			fSimpleCoupon.setUpdateUser(loginUser.getRealname());
			fSimpleCoupon.setUpdateUserOrg(loginUser.getOrganization().getName());
			fSimpleCoupon.setUpdateUserId(loginUser.getId());
			return ;
		}
		
		fSimpleCoupon.setCreateTime(Dates.getCurrentLongDate());
		fSimpleCoupon.setCreateUserId(loginUser.getId());
		fSimpleCoupon.setCreateUser(loginUser.getRealname());
		fSimpleCoupon.setCreateUserOrg(loginUser.getOrganization().getName());
		return ;
	}
	
	
	
	@Override
	public FSimpleCoupon haveCoupon(String uid, String name) {

		List<FSimpleCoupon> fSimpleCouponList=this.getEntityDao().findByUserIdAndName(uid,"%"+name+"%");
		if(fSimpleCouponList.size()==0||fSimpleCouponList.isEmpty()){
			return null;
		}
		FSimpleCoupon fSimpleCoupon =fSimpleCouponList.get(0);
		return fSimpleCoupon;
	}

	@Override
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

		return 1;
	}
	/**
	 * 抽奖 周年庆福利
	 *
	 * @param uid
	 *            用户编号
	 * @return
	 */
	public String updatePrizeOfWeb(String uid, String mobile) {

		// 操作结果
		Map<Object, Object> reusltMap = new HashMap<Object, Object>();

		// 得到抽取的奖品
		FSimpleCoupon fSimpleCoupon = this.getWebOfNewActivityKudo(uid);

		// 判断抽奖是否有未抓取的奖品：有奖品
		if (fSimpleCoupon == null) { // 未抽到奖品

			return "奖品已经抽完";
		}


		fSimpleCoupon.setUserId(uid);
		fSimpleCoupon.setStatus(Short.parseShort("1"));
		fSimpleCoupon.setUpdateTime(new Date().getTime()/1000);
		fSimpleCoupon.setUserPhone(mobile);
		this.update(fSimpleCoupon);

		return fSimpleCoupon.getName();

	}

	/**
	 * 获取该用户能抽取的奖品
	 *
	 * @param uid
	 *            用户编号
	 * @return
	 */
	public FSimpleCoupon getWebOfNewActivityKudo(String uid) {

		List<FSimpleCoupon> fSimpleCoupons = null;

		List<Object> params = new ArrayList<>();

		int qualification = this.getTardeInfo(uid);

		String sql;
		switch (qualification){
			case 1:
				sql = "select * from f_simple_coupon where type = 4 and name like %周年抽奖活动％";
				fSimpleCoupons = this.nativeQuery(sql,null,FSimpleCoupon.class);
				if(0 ==fSimpleCoupons.size() ||fSimpleCoupons.isEmpty()){
					sql = "select * from f_simple_coupon where type = 5 and name like %周年抽奖活动％";
					fSimpleCoupons = this.nativeQuery(sql,null,FSimpleCoupon.class);
					if(0 == fSimpleCoupons.size() || fSimpleCoupons.isEmpty()){
						sql = "select * from f_simple_coupon where type = 2 and name like %周年抽奖活动％";
						fSimpleCoupons = this.nativeQuery(sql,null,FSimpleCoupon.class);
					}
				}
			case 2:
				sql = "select * from f_simple_coupon where type = 2 and name like %周年抽奖活动％";
				fSimpleCoupons = this.nativeQuery(sql,null,FSimpleCoupon.class);

			case 3:
				sql = "select * from f_simple_coupon where type = 5 and name like %周年抽奖活动％";
				fSimpleCoupons = this.nativeQuery(sql,null,FSimpleCoupon.class);
				if(0 == fSimpleCoupons.size() ||fSimpleCoupons.isEmpty()){
					sql = "select * from f_simple_coupon where type = 2 and name like %周年抽奖活动％";
					fSimpleCoupons = this.nativeQuery(sql,null,FSimpleCoupon.class);
				}
			case 4:
				sql = "select * from f_simple_coupon where type = 2 and name like %周年抽奖活动％";
				fSimpleCoupons = this.nativeQuery(sql,null,FSimpleCoupon.class);
		}
		if(0 == fSimpleCoupons.size() ||fSimpleCoupons.isEmpty()){
			return null;
		}


		Random rand = new Random(); // 从奖品列表中随机抽奖任意奖品
		int randNum = rand.nextInt(fSimpleCoupons.size());

		FSimpleCoupon fSimpleCoupon = fSimpleCoupons.get(randNum); // 随机抽奖任意奖品

		return fSimpleCoupon;
	}


	/**
	 * 判断用户是否有过操盘记录及其状态
	 * @param uid
	 * @return 1：期货和股票都在 2:股票有过记录 3：期货有过记录 4无任何记录
	 */
	public int getTardeInfo(String uid){

		List<UserTrade> userTrades = this.getEntityDao().findByWUserAndStatus(uid,Short.parseShort("2"));
		List<FSimpleFtseUserTrade> ftseUserTrades =this.getEntityDao().findByUidAndStateType(uid,4);
		if(0 != userTrades.size() && !userTrades.isEmpty() && 0 != ftseUserTrades.size() && !ftseUserTrades.isEmpty() ){
			return 1;
		}
		List<UserTrade> userTrade = this.getEntityDao().findByWUserAndStatus(uid);
		List<FSimpleFtseUserTrade> ftseUserTrade =this.getEntityDao().findByUidAndStateType(uid);
		if(0 != userTrade.size() && !userTrade.isEmpty()){
			return 2;
		}
		if(0 != ftseUserTrade.size() && !ftseUserTrade.isEmpty()){
			return 3;
		}

		return 4;
	}
	
	/*
	 * 返回空表示奖品已抽完
	 *  (non-Javadoc)
	 * @see com.tzdr.business.service.future.FSimpleCouponService#unpackDistilCoupon(java.lang.String, java.lang.String, java.lang.String)
	 */
	public String unpackDistilCoupon(String uid, String userName, String userPhone, long anniversaryTime) {
		int index = this.distilCouponIndex(uid, anniversaryTime);
		List<Map<String, Object>> result = new ArrayList<>();
		
		if(2 == index) {
			result = this.queryCouponByTypeRandOne(4);
			if(null == result || result.isEmpty()) {
				result = this.queryCouponByTypeRandOne(5);
				if(null == result || result.isEmpty()) {
					result = this.queryCouponByTypeRandOne(2);
				}
			}
		} else if(1 == index) {
			result = this.queryCouponByTypeRandOne(5);
			if(null == result || result.isEmpty()) {
				result = this.queryCouponByTypeRandOne(2);
			}
		} else {
			result = this.queryCouponByTypeRandOne(2);
		}
		
		if(null == result || result.isEmpty()) {
			return ""; // 奖品已抽完
		}
		
		String couponId = result.get(0).get("id").toString();
		FSimpleCoupon coupon = this.get(couponId);
		coupon.setUserId(uid);
		coupon.setUserName(userName);
		coupon.setUserPhone(userPhone);
		coupon.setStatus((short) 2);
		long currTime = Dates.getCurrentLongDate();
		coupon.setGrantTime(currTime);
		coupon.setUpdateTime(currTime);
		// 设置过期时间
		Long deadLine = coupon.getDeadline(); // 截止日期
		if (null == deadLine || "".equals(deadLine)) {
			Long grantTime = coupon.getGrantTime(); // 发放时间
			Integer cycle = coupon.getCycle(); // 截止周期
			deadLine = Dates.toDate(Dates.dateAddDay(Dates.parseLong2Date(grantTime), cycle)).getTime() / 1000;
			coupon.setDeadline(deadLine);
		}
		this.update(coupon);
		
		return coupon.getName();
	}
	
	/**
	 * 用户抽奖顺序：2-优先抽实物，抽完再抽手数增送券，抽完再抽88元代金券； 1-优先抽手数增送券，抽完再抽88元代金券； 0-默认抽88元代金券。
	 * @param uid
	 * @param anniversaryTime
	 * @return
	 */
	public int distilCouponIndex(String uid, long anniversaryTime) {
		int index = 0; // 周年抽奖活动-用户抽奖顺序

		StringBuffer sql = new StringBuffer();
		sql.append(" select count(1) cnt from ");
		sql.append(" (select fsfut.id from f_simple_ftse_user_trade fsfut ");
		sql.append(" where fsfut.uid = ? ");
		sql.append(" and fsfut.state_type in (2,3,4) ");
		sql.append(" and ? < ifnull(fsfut.end_time, unix_timestamp()) ");
		sql.append(" union all ");
		sql.append(" select wut.id from w_user_trade wut ");
		sql.append(" where wut.uid = ? ");
		sql.append(" and wut.status = 1 ");
		sql.append(" and ? < ifnull(wut.endtime, unix_timestamp()) ");
		sql.append(" union all ");
		sql.append(" select hkut.id from hk_user_trade hkut ");
		sql.append(" where hkut.uid = ? ");
		sql.append(" and hkut.status = 1 ");
		sql.append(" and ? < ifnull(hkut.endtime, unix_timestamp())) t ");
		
		List<Map<String, Object>> result = new ArrayList<>();
		result = this.getEntityDao().queryMapBySql(sql.toString(), uid, anniversaryTime, uid, anniversaryTime, uid, anniversaryTime);
		if(result.isEmpty() || "0".equals(result.get(0).get("cnt").toString())) {
			sql.setLength(0);
			sql.append(" select count(1) cnt from f_simple_ftse_user_trade fsfut ");
			sql.append(" where fsfut.uid = ? ");
			sql.append(" and fsfut.state_type > 1 ");
			sql.append(" and fsfut.state_type <> 5 ");
			result = this.getEntityDao().queryMapBySql(sql.toString(), uid);
			
			if(result.isEmpty() || "0".equals(result.get(0).get("cnt").toString())) {
				index = 0;
			} else {
				index = 1;
			}
		} else {
			index = 2;
		}
		
		return index;
	}
	
	/**
	 * 根据奖品类型，随机抽取未领的周年活动奖品
	 * @param type
	 * @return
	 */
	public List<Map<String, Object>> queryCouponByTypeRandOne(int type) {
		StringBuffer sql = new StringBuffer();
		sql.append(" select id from f_simple_coupon fsc ");
		sql.append(" where 1 = 1 ");
		sql.append(" and (fsc.user_id is null or fsc.user_id = '') ");
		sql.append(" and fsc.name like '周年抽奖活动-%' ");
		sql.append(" and fsc.type = ? ");
		sql.append(" order by rand() limit 1 ");

		return this.getEntityDao().queryMapBySql(sql.toString(), type);
	}

	public List<Map<String, Object>> getFSC(String userId,int scope){
		if ("".equals(userId)) {
			return new ArrayList<Map<String, Object>>();
		}
		StringBuffer sql = new StringBuffer(" select id, type,money from f_simple_coupon ");
		sql.append(" where user_id = ? and type in(2,3,6) and status = 2 and find_in_set(?, scope) ");
		sql.append(" and ifnull(deadline, unix_timestamp(date_add(from_unixtime(grant_time),interval cycle day))) > ? ");
		sql.append(" order by money desc ");
		return this.getEntityDao().queryMapBySql(sql.toString(),userId,scope ,Dates.getCurrentLongDate());
	}


	@Override
	public boolean isCouponValided(FSimpleCoupon coupon, int scope) {
		// 匹配优惠券类型，状态和范围
		scope = (1 == scope || 2 == scope || 3 == scope || 4 == scope || 20 ==scope) ? 5 : scope;
		if (null == coupon  || coupon.getStatus() != 2 || coupon.getScope().indexOf(scope + "") < 0) {
			return false;
		}
		// 判断代金券是否过期
		Long deadLine = coupon.getDeadline(); // 截止日期
		if (null == deadLine) {
			Long grantTime = coupon.getGrantTime(); // 发放时间
			Integer cycle = coupon.getCycle(); // 截止周期
			if (null == grantTime || null == cycle || 0 > cycle) {
				return false;
			}
			deadLine = Dates.toDate(Dates.dateAddDay(Dates.parseLong2Date(grantTime), cycle)).getTime() / 1000;
		}
		if (deadLine < Dates.getCurrentLongDate()) {
			return false;
		}
		return true;
	}
}

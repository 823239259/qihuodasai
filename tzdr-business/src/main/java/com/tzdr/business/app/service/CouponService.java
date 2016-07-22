package com.tzdr.business.app.service;

import java.math.BigDecimal;
import java.util.List;

import jodd.util.StringUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.tzdr.business.service.recharge.RechargeListService;
import com.tzdr.common.baseservice.BaseServiceImpl;
import com.tzdr.common.utils.TypeConvert;
import com.tzdr.domain.app.vo.UserCouponVo;
import com.tzdr.domain.dao.future.FSimpleCouponDao;
import com.tzdr.domain.web.entity.future.FSimpleCoupon;

/**  
 * @Title: CouponService.java     
 * @Description: 优惠劵业务信息管理类    
 * @author： WangPinQun 
 * @E-mail：wangpinqun@tzdr.com
 * @company： 上海信闳投资管理有限公司重庆分公司
 * @address：重庆市渝北区黄山大道中段70号两江新界3栋13楼
 * @date： 2016年3月24日 下午2:50:34    
 * @version： V1.0  
 */
@Service("couponService")
@Transactional(propagation = Propagation.REQUIRED)
public class CouponService extends BaseServiceImpl<FSimpleCoupon, FSimpleCouponDao> {

	@Autowired
	private RechargeListService rechargeListService;
	
	/**
	* @Title: findUserCouponVos    
	* @Description: 获取用户优惠劵列表信息 
	* @param uid 用户编号
	* @return
	 */
	@SuppressWarnings("unchecked")
	public List<UserCouponVo> findUserCouponVos(String uid) {
		
		if(StringUtil.isBlank(uid)){
			return null;
		}
		
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT f.id,f.type,f.money,f.deadline,f.grant_time,f.`name`,if(UNIX_TIMESTAMP(NOW()) > f.deadline,4,f.`status`) `status` ");
		sql.append(" from f_simple_coupon f ");
		sql.append(" WHERE f.user_id=? and f.type in(1,2,3) ORDER BY ");
		sql.append(" status ASC,f.grant_time DESC ");  
		
		List<UserCouponVo> dataList =this.getEntityDao().queryListBySql(sql.toString(), UserCouponVo.class, null, new Object[]{uid});
		
		return dataList;
	}
	
	/**
	* @Title: updateFSimpleCoupon    
	* @Description: 用户使用现金红包
	* @param fSimpleCoupon
	* @param mobile
	* @param userName
	* @param income
	* @param uid
	* @throws Exception
	 */
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
	
	/**
	* @Title: getFSimpleCoupon    
	* @Description: 根据条件获取用户优惠劵信息 
	* @param id   优惠劵编号
	* @param userId  用户编号
	* @return
	 */
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
	
	/**
	 * @Description 根据适用范围获取用户优惠券 
	 * @param scope
	 * @param userId
	 * @return
	 */
	/*@SuppressWarnings("unchecked")
	public List<FSimpleCoupon> getFSimpleCouponByScope(String scope,String userId){
		if(StringUtil.isBlank(scope) || StringUtil.isBlank(userId)){
			return null;
		}
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT * ");
		sql.append(" FROM f_simple_coupon f ");
		sql.append(" WHERE f.user_id=? AND f.scope like ? AND F.AND fsc.status = 2 ");
		sql.append(" AND fsc.deadline >= UNIX_TIMESTAMP(NOW()) ");
		sql.append(" AND fsc.deleted = 0 GROUP BY fsc.name");
		sql.append(" ORDER BY f.grant_time DESC ");
		List<FSimpleCoupon> dataList =this.getEntityDao().queryListBySql(sql.toString(), FSimpleCoupon.class, null, new Object[]{userId,scope});
		return dataList; 
	}*/
}

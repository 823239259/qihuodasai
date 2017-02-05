package com.tzdr.business.service.future;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.tzdr.common.baseservice.BaseService;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.web.support.EasyUiPageInfo;
import com.tzdr.domain.vo.future.FSimpleCouponManageVo;
import com.tzdr.domain.vo.future.FSimpleCouponWebVo;
import com.tzdr.domain.web.entity.future.FSimpleCoupon;

/**
 * 期货优惠券 类说明
 * 
 * @author zhaozhao
 * @date 2016年3月1日下午1:49:33
 * @version 1.0
 */
public interface FSimpleCouponService extends BaseService<FSimpleCoupon> {
	/**
	 * 名称查询
	 * @param name
	 * @return
	 */
	public Integer checkName(String name);

	/**
	 * 查询用户已发放并未过期的优惠券
	 * 
	 * @param userId
	 * @param type 优惠券类型：1-现金红包，2-代金券，3-折扣券 4-实物 5 抵扣卷
	 * @param scope 使用范围：存储国际期货的业务ID 【0.富时A50  （1.沪金     2.沪银   3.沪铜   4.橡胶）=5.商品期货  6.原油    7. 恒指  8.国际综合 9.小恒指】
	 * @return
	 */
	List<Map<String, Object>> queryCouponByUserId(String userId, int type, int scope);

	List<Map<String, Object>> queryCouponByUserId(String userId, int[] type, int scope);
	
	/**
	 * 判断优惠券是否有效
	 * 
	 * @param coupon
	 * @param type 优惠券类型：1-现金红包，2-代金券，3-折扣券
	 * @param scope 使用范围：存储国际期货的业务ID 【0.富时A50  （1.沪金     2.沪银   3.沪铜   4.橡胶）=5.商品期货  6.原油    7. 恒指  8.国际综合 9，商品综合，10港股，11 A股 12 股票合买 13 小恒指】
	 * @return
	 */
	boolean isCouponValid(FSimpleCoupon coupon, int type, int scope);

	/**
	 * 查询未发放优惠券
	 * @return
	 */
	public List<FSimpleCoupon> queryCouponToGive();
	
	
	public List<FSimpleCoupon> findByName(String name);
	
	/**
	* @Title: findDataList    
	* @Description: 获取用户的优惠劵列表信息 
	* @param pageIndex 每页步长
	* @param perPage   当前页码
	* @param uid  用户编号
	* @param platform 平台
	* @return
	 */
	public PageInfo<FSimpleCouponWebVo> findDataList(String pageIndex,String perPage,String uid,String platform);

	/**
	* @Title: updateFSimpleCoupon    
	* @Description: 修改红包为使用状态，并且用户账号收入红包 
	* @param fSimpleCoupon  红包对象
	* @param mobile   用户手机号码
	* @param userName   用户姓名
	* @param income  钱
	* @param uid      用户编号
	* @return
	 */
	public void updateFSimpleCoupon(FSimpleCoupon fSimpleCoupon, String mobile,String userName,BigDecimal income ,String uid) throws Exception;
	
	/**
	* @Title: getFSimpleCoupon    
	* @Description: 根据条件获取优惠券信息 
	* @param id  优惠券编号
	* @param userId   用户编号
	* @return
	 */
	public FSimpleCoupon getFSimpleCoupon(String id,String userId);
	
	/**
	 * 优惠券详情数据查询
	 * @param easyUiPage
	 * @param searchParams
	 * @return
	 */
	public PageInfo<Object> queryList(EasyUiPageInfo  easyUiPage,Map<String, Object> searchParams);
	
	
	/**
	 * 查询未发放的优惠券
	 */
	public List<FSimpleCoupon> findByStatusAndName(short status,String name);


	/**
	 * 判断用户是否已经抽过奖
	 * @param uid 用户id
	 * @param name 优惠卷名称
	 * @return
	 */
	public FSimpleCoupon haveCoupon(String uid,String name);

	/**
	 * 【周年庆】判断是否是活动时间内:1、活动日期；2、活动时间；
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
	public int isLotteryTime(String activityTimeStart, String activityTimeEnd, String lotteryTimeStart, String lotteryTimeEnd) throws Exception;


	/**
	 * 获取该用户能抽取的奖品
	 *
	 * @param uid
	 *            用户编号
	 * @return
	 */
	public FSimpleCoupon getWebOfNewActivityKudo(String uid);

	/**
	 * 判断用户是否有过操盘记录及其状态
	 * @param uid
	 * @return 1：期货和股票都在 2:股票有过记录 3：期货有过记录 4无任何记录
	 */
	public int getTardeInfo(String uid);

	/**
	 * 抽奖 周年庆福利
	 *
	 * @param uid
	 *            用户编号
	 * @return
	 */
	public String updatePrizeOfWeb(String uid, String mobile);
	
	/**
	 * 周年抽奖活动-抽奖
	 * @param uid
	 * @param userName
	 * @param userPhone
	 * @param anniversaryTime 周年抽奖开始时间
	 * @return
	 */
	public String unpackDistilCoupon(String uid, String userName, String userPhone, long anniversaryTime);

	/**
	 * 得到用户未过去的所有优惠券
	 * @param userId
	 * @param scope
	 * @return
	 */
	public List<Map<String,Object>> getFSC(String userId,int scope);

	public boolean isCouponValided(FSimpleCoupon coupon, int scope);

}

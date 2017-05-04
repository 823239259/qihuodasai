package com.tzdr.domain.dao.pay;


import java.util.List;

import com.tzdr.common.dao.BaseJpaDao;
import com.tzdr.domain.web.entity.UserFund;

/**
 * 用户充值记录dao
 * <P>title:@SecurityInfoDao.java</p>																								
 * <P>Description：</p>userId
 * <p>Copyright: Copyright (c) 2014 tzdr</p>
 * <p>Company: 上海信闳</p>
 * History：
 * @author:zhangjun
 * @date 2014年12月24日
 * @version 1.0
 */
public interface UserFundDao extends BaseJpaDao<UserFund, String> {

	/**
	 *  查询账户是否有未支付 记录
	 * @param uid
	 * @param payStatus
	 * @return
	 */
	public List<UserFund>  findByUidAndPayStatusAndNoOrderByMoneyAsc(String uid,short payStatus,String no);
	
	/**
	 *  查询账户是否有未支付 记录
	 * @param uid
	 * @param payStatus
	 * @return
	 */
	public List<UserFund>  findByUidAndPayStatusAndLidAndMoneyLessThanAndAddtimeLessThanOrderByAddtimeAsc(String uid,short payStatus,String lid,Double money,Long currentDate);
	
	/**
	 * 按支付状态查找 记录
	 * @param payStatus
	 * @return
	 */
	public List<UserFund>  findByPayStatusAndMoneyLessThanOrderByMoneyAsc(short payStatus,Double money);

	/**
	 * 根据订单号和用户id查询充值记录
	 * @param no String
	 * @param userId String
	 * @return UserFund
	 * @date 2015年1月13日
	 * @author zhangjun
	 */
	public UserFund findByNoAndUid(String no, String userId);
	
	/**
	 * 查询扣费类型
	 * @param lid String
	 * @param type Integer 类型：1:充值,2:提现,,3:人工充值,4:人工扣款,5:投标冻结,6:投标成功,10:配资支出,11:利息支出 ,12：扣取管理费（新增）
	 * @return List<UserFund>
	 */
	public List<UserFund> findByLidAndType(String lid,Integer type);
	
	/**
	 * 查询扣费类型
	 * @param lid String
	 * @param type Integer 类型：1:充值,2:提现,,3:人工充值,4:人工扣款,5:投标冻结,6:投标成功,10:配资支出,11:利息支出 ,12：扣取管理费（新增）
	 * @return List<UserFund>
	 */
	public List<UserFund> findByLidAndTypeOrderByAddtimeDesc(String lid,Integer type);
	
	
	public List<UserFund> findByUidAndLidAndTypeIn(String userId,String groupId,List<Integer> type);
	
	/**
	 * 查询出支付结果
	 * @param uid String
	 * @param payStatus Short
	 * @param type Integer
	 * @return List<UserFund>
	 */
	public List<UserFund> findByUidAndPayStatusAndTypeOrderByMoneyDesc(String uid,Short payStatus,Integer type);
	
	/**
	 * 根据  no 和 type 查询 list
	 * @param no
	 * @param type
	 * @return
	 */
	public List<UserFund> findByNoAndTypeAndAddtimeGreaterThan(String no,Integer type,Long currentDate);
	
	/**
	 * 获取方案资金明细列表信息
	 * @param userId  用户编号
	 * @param programNo   配资方案号-->programNo
	 * @param payStatus   支付状态
	 * @param types   类型
	 * @return
	 */
	public List<UserFund> findByUidAndPayStatusAndRidAndTypeIn(String userId,Short payStatus,String programNo,List<Integer> types);


	/**
	 * 按支付状态和用户信息查找 记录
	 * @param payStatus
	 * @return
	 */
	public List<UserFund>  findByPayStatusAndUidAndMoneyLessThanOrderByMoneyAsc(short payStatus,String uid,Double money);

}

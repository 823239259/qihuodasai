package com.tzdr.business.service.pay;

import java.util.List;

import com.tzdr.common.baseservice.BaseService;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.utils.ConnditionVo;
import com.tzdr.domain.vo.BillingFeeVo;
import com.tzdr.domain.vo.HandUserFundTotalVo;
import com.tzdr.domain.vo.HandUserFundVo;
import com.tzdr.domain.vo.HandUserFundVoNew;
import com.tzdr.domain.vo.UserFundVo;
import com.tzdr.domain.vo.UserFundWebVo;
import com.tzdr.domain.vo.UserTradeArrearageVo;
import com.tzdr.domain.web.entity.UserFund;
import com.tzdr.domain.web.entity.WUser;


/**
 * 充值记录Service
 * <P>title:@PayService.java</p>																								
 * <P>Description：</p>
 * <p>Copyright: Copyright (c) 2014 Tzdr</p>
 * <p>Company: 上海信闳</p>
 * History：
 * @author:ZhangJun
 * @author Qing.Liu
 * @date 2014年12月23日
 * @version 1.0
 */
public interface UserFundService extends BaseService<UserFund>{
	
	/**
	 * 查找用户  未支付记录
	 * @param uid
	 * @param payStatus
	 * @return
	 */
	public List<UserFund>  findByUidAndPayStatus(String uid,short payStatus,String tradeId);
	
	/**
	 * 扣除费用  管理或  利息 专用
	 * @param userFund
	 * @param userId
	 */
	public void deductionMoney(UserFund  userFund,String userId);
	
	/**
	 * 查看组合欠费
	 * @param uid
	 * @param payStatus
	 * @param currentDate
	 * @param groupid
	 * @return
	 */
	public List<UserFund> queryGroupArrears(String uid,String groupid,short payStatus,Long currentDate);
	
	
	/**
	 *  查找未支付的数据
	 * @param payStatus
	 * @return
	 */
	public List<UserFund>  findByPayStatus(short payStatus);
	
	/**
	 * 查看只有当天欠费的列表数据
	 * @return
	 */
	public List<UserFundVo> findTodayArrears();
	
	/**
	 * 查询用户充值记录
	 * @param no 订单号
	 * @param userId 用户id
	 * @return
	 * @date 2015年1月13日
	 * @author zhangjun
	 */
	public UserFund  findUserfundByNo(String no,String userId);
	
	/**
	 * 按用户分组，获取昨天缴纳的管理费
	 * @return
	 */
	public List<UserFundVo> getYestodayFee();
	
	/**
	 * 查询出欠费信息
	 * @param lid String
	 * @return List<UserFund>
	 */
	public void deleteArrearsByLid(String lid);
	

	/**
	 * 分页查询资金明细
	 * @param pageIndex
	 * @param perPage
	 * @param id
	 * @return
	 * @date 2015年1月14日
	 * @author zhangjun
	 */
	public PageInfo<UserFund> findData(String pageIndex, String perPage,
			String userId,String type,Long mintime,Long maxtime);
	
	/**
	 * 
	 * @param page PageInfo<UserFund>
	 * @return PageInfo<UserFund>
	 */
	public PageInfo<UserFundWebVo> queryUserFundWebVoByConn(PageInfo<UserFundWebVo> page,ConnditionVo conn);
	
	
	
	/**
	 * 
	 * @param page PageInfo<UserFund>
	 * @return PageInfo<UserFund>
	 */
	public PageInfo<HandUserFundVo> queryHandlerRecharge(PageInfo<HandUserFundVo> page,ConnditionVo conn);
	

	/**
	 * 查询合计
	 * @param conn ConnditionVo
	 * @return HandUserFundTotalVo
	 */
	public HandUserFundTotalVo queryHandlerRechargeTotal(ConnditionVo conn);
	
	
	/**
	 * 查询资金明细
	 * @param page PageInfo<UserFund>
	 * @return PageInfo<UserFund>
	 */
	public PageInfo<HandUserFundVoNew> queryUserFundVoList(PageInfo<HandUserFundVoNew> page,ConnditionVo conn);
	
	
	/**
	 * 查询合计值
	 * @param groupId String
	 * 类型：1:充值,2:提现,,3:人工充值,4:人工扣款,5:投标冻结,6:投标成功,10:配资支出,11:利息支出 ,12：扣取管理费（新增）
	 * @return List<BillingFeeVo>
	 */
	public List<BillingFeeVo> totalMoneyByGroupId(String groupId);
	
	
	/**
	 * 按用户方案分组，获取管理费和利息
	 * @return
	 */
	public List<UserFund> findByUidAndLidAndTypeIn(String userId,String groupId,List<Integer> type);
	/**
	 * 按用户方案分组，获取管理费和利息 的总数
	 * @return
	 */
	public Double sumMoneyByUidAndLidAndTypeIn(String userId,String groupId,List<Integer> type);

	/**
	 *资金明细统计查询
	 * @param type
	 * @return
	 * @date 2015年1月16日
	 * @author zhangjun
	 */
	public List<UserFundVo> getFundbytype(String type,String userId);
	
	/**
	 * 充值接口[新增及自动添加默认值]
	 * 自动维护充值内容
	 * @param wuser WUser
	 * @param userFund UserFund 
	 * @param takeDepositType 存取类型 1） 存、 2）取、3）冻结
	 */
	/*public void rechargeOperation(WUser wuser,UserFund userFund,int takeDepositType);*/
	
	/**
	 * 当用户充值时如果存在欠费先扣除欠费
	 * 
	 * @param userId String
	 * @param arrearsUserFund UserFund
	 */
	public void arrearsProcess(UserFund arrearsUserFund);
	
	/**
	 * 存操作
	 * @param userFund UserFund
	 */
	public void depositOperation(UserFund userFund);	
	/**
	 * 充值接口[新增及自动添加默认值]
	 * 自动维护充值内容
	 * @param wuser WUser
	 * @param userFund UserFund 
	 * @param takeDepositType 存取类型 1） 存、 2）取、3）冻结
	 */
	public void rechargeOperation(UserFund userFund,int takeDepositType);
	
	/**
	 * 根据方案id 判断当天是否已经扣除管理费
	 * @param no
	 * @return
	 */
	public boolean isDeductionTodayFee(String no);

	/**
	 * 查询收入和支出多少笔
	 * @param starttime 开始时间
	 * @param endtime 结束时间
	 * @param type 类型
	 * @param uid 用户id
	 * @return
	 * @date 2015年2月26日
	 * @author zhangjun
	 */
	public List<UserFundVo> getTitleData(Long starttime, Long endtime,
			String type, String uid);
	
	
	/**
	 * 根据方案groupid 即lid 和 类型查询 明细
	 * @return
	 */
	public List<UserFund> getByLidAndType(String lid,Integer type);
	
	/**
	 * 根据方案groupid 即lid 和 类型查询 明细
	 * @return
	 */
	public List<UserFund> findByLidAndTypeOrderByAddtimeDesc(String lid,Integer type);
	
	/**
	 * 根据方案groupid 即lid 和 类型查询合计
	 * @param groupId
	 * @param type
	 * @return
	 */
	public Double sumMoneyByLidAndType(String groupId,Integer type);

	/**
	 * 查询是否抵扣了利息
	 * @param groupId
	 * @param uid
	 * @param tickettype
	 * @return
	 */
	public List<UserFund> getVolumeDetail(String groupId, String uid, int tickettype);
	
	/**
	 * 获取A股欠费方案列表信息
	 * @param uid  用户编号
	 * @param tradeId  配资方案号-->programNo
	 * @param types 欠费类型
	 * @return
	 */
	public List<UserTradeArrearageVo> queryUserTradeArrearageVo(String uid,String tradeId,Integer ...types);
	
	/**
	 * 获取港股欠费方案列表信息
	 * @param uid  用户编号
	 * @param tradeId  配资方案号-->programNo
	 * @param types 欠费类型
	 * @return
	 */
	public List<UserTradeArrearageVo> queryHkUserTradeArrearageVo(String uid,String tradeId,Integer ...types);
	
	/**
	 * 获取欠费记录
	 * @param uid  欠费用户编号
	 * @param tradeId 欠费配资方案号-->programNo
	 * @param types 欠费类型
	 * @return
	 */
	public List<UserFund> queryUserFunds(String uid,String tradeId,Integer ...types);
	
	/**
	 * 补费（管理费+利息）
	 * @param wuser   用户信息
	 * @param tradeId  tradeId  配资方案号-->programNo
	 */
	public void updateUserFund(WUser wuser,String tradeId);
	
	/**
	 * 按用户分组，根据时间区间获取缴纳的管理费
	 * @param startTime   开始时间区间
	 * @param endTime     结束时间区间
	 * @return
	 */
	public List<UserFundVo> getManagementFee(Long startTime,Long endTime);
	
	/**
	 * 查找用户欠费记录
	 * @param payStatus
	 * @param uid
	 * @return
	 */
	public List<UserFund> findByPayStatusAndUid(short payStatus,String uid);

}

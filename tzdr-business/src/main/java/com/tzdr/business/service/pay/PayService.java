package com.tzdr.business.service.pay;

import java.util.Map;

import com.tzdr.common.api.bbpay.vo.PayParamsObject;
import com.tzdr.common.baseservice.BaseService;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.domain.web.entity.RechargeList;
import com.tzdr.domain.web.entity.UserVerified;
import com.tzdr.domain.web.entity.WUser;


/**
 * 支付service
 * <P>title:@PayService.java</p>																								
 * <P>Description：</p>
 * <p>Copyright: Copyright (c) 2014 tzdr</p>
 * <p>Company: 上海信闳</p>
 * History：
 * @author:zhangjun
 * @date 2014年12月23日
 * @version 1.0
 */
public interface PayService extends BaseService<RechargeList>{
	/**
	 * 根据用户id查询用户安全信息
	 * @param userId 用户id
	 * @return
	 * @date 2014年12月30日
	 * @author zhangjun
	 */
	public UserVerified findByUserId(String userId);

	/**
	 * 插入数据
	 * 
	 * @param source 来源
	 * @param user 当前登陆用户
	 * @param bankname 银行名称
	 * @param status 状态
	 * @param bankCard 银行卡
	 * @param paymoney 充值金额
	 * @param ip ip地址
	 * @param paytype 支付方式
	 * @param serialnum 流水号
	 * @date 2014年12月30日
	 * @author zhangjun
	 */
	public String insertEntity(int source,WUser user,String bankname,Integer status, 
			String bankCard, String paymoney,String ip,String paytype,String serialnum);
	
	/**
	 * 充值成功后更新状态
	 * @param orderId id
	 * @param tradeNo  交易号
	 * @param orderNumber 交易号
	 * @param tradeState 交易状态
	 * @param payDate 交易时间
	 * @param status 状态
	 * @return
	 * @date 2014年12月31日
	 * @author zhangjun
	 */
	public void updateEntity(String orderId, String  tradeNo,String  tradeState,String  payDate,int status);

	/**
	 * 根据id查询用户
	 * @param userId
	 * @return
	 * @date 2014年12月31日
	 * @author zhangjun
	 */
	public WUser getUser(String userId);

	/**
	 * 分页查询数据
	 * @param pageIndex 当前页
	 * @param perPage 每页显示条数
	 * @param userId 用户id
	 * @return
	 * @date 2015年1月3日
	 * @author zhangjun
	 */
	public PageInfo<RechargeList> findData(String pageIndex, String perPage,String userId);
	
	/**
	 * 补录充值表单
	 * @param user
	 * @param bankname
	 * @param status
	 * @param bankCard
	 * @param paymoney
	 * @param ip
	 * @param paytype
	 * @param serialnum
	 * @return
	 */
	public RechargeList saveRechargeRecord(String uid,String bankname,Integer status,
			String bankCard, Double paymoney,String ip,String paytype,String serialnum,Integer source);
	
	/**
	 * 根据订单号查找充值订单
	 * @param orderId
	 * @return
	 */
	public RechargeList findByNo(String orderId);
	/**
	 * 币币支付处理
	 * @param user 用户信息
	 * @param sendObject 币币支付参数设置
	 * @param status 充值状态
	 * @param amount 充值金额
	 * @return
	 */
	public Map<String,Object>  bbPay(WUser user,PayParamsObject sendObject,int source);
	
	/**
	 * 币币支付处理
	 * @param user 用户信息
	 * @param sendObject 币币支付参数设置
	 * @param status 充值状态
	 * @param amount 充值金额
	 * @param sreturl 商户前端返回地址【备注：app使用】
	 * @return
	 */
	public Map<String,Object>  bbPay(WUser user,PayParamsObject sendObject,int source,String sreturl);
}

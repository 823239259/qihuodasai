package com.tzdr.business.service.recharge;

import java.util.Map;

import com.tzdr.common.baseservice.BaseService;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.utils.ConnditionVo;
import com.tzdr.domain.vo.AllRechargeListVo;
import com.tzdr.domain.vo.AllRechargeTotalVo;
import com.tzdr.domain.vo.RechargeBankListVo;
import com.tzdr.domain.web.entity.RechargeList;

/**
 * 
 * <p>支付充值审核</p>
 * @author LiuQing
 * @see
 * @version 2.0
 * 2015年1月5日下午7:37:39
 */
public interface RechargeListService extends BaseService<RechargeList>{
	
	
	
	/**
	 * 充值更新信息
	 * @param rechargeList
	 * @return
	 */
	public RechargeList addUpdateRechargeList(RechargeList rechargeList);
	
	/**
	 * 类型充值更新信息
	 * @param rechargeList RechargeList
	 * @param rechargeList Integer
	 * @return
	 */
	public RechargeList addUpdateRechargeList(RechargeList rechargeList,Integer userType);
	
	/**
	 * 类型充值更新信息
	 * @param rechargeList RechargeList
	 * @param rechargeList Integer
	 * @return
	 */
	public RechargeList addUpdateRechargeList(RechargeList rechargeList,Integer userType,String remark);
	
	/**
	 * 类型充值更新信息
	 * @param rechargeList RechargeList
	 * @param rechargeList Integer
	 * @param businessType Integer
	 * @return
	 */
	public RechargeList addUpdateRechargeList(RechargeList rechargeList,Integer userType,String remark,Integer businessType);
	
	/**
	 * 类型充值更新信息
	 * @param rechargeList RechargeList
	 * @param rechargeList Integer
	 * @return
	 */
	public RechargeList addUpdateRechargeListWeb(RechargeList rechargeList,Integer userType,String remark);
	
	/**
	 * 类型充值更新信息
	 * @param rechargeList RechargeList
	 * @param rechargeList Integer
	 * @return
	 */
	public RechargeList addUpdateRechargeListWeb(RechargeList rechargeList,Integer userType,String remark,Integer businessType);
	
	/**
	 * 查询支付宝
	 * @param page DataPage<RechargeList>
	 * @return DataPage<RechargeList>
	 * 建议使用 public PageInfo<RechargeList> queryAlipayListRecharge(PageInfo<RechargeList> page);
	 */
	@Deprecated
	public PageInfo<RechargeList> queryAlipayRecharge(PageInfo<RechargeList> page);
	
	/**
	 * 查询支付宝
	 * @param page DataPage<RechargeList>
	 * @return DataPage<RechargeList>
	 */
	public PageInfo<RechargeBankListVo> queryAlipayListRecharge(PageInfo<RechargeBankListVo> page,ConnditionVo connVo);
	
	
	/**
	 * 查询银行卡充值
	 * @param page DataPage<RechargeList>
	 * @return DataPage<RechargeList>
	 */
	@Deprecated
	public PageInfo<RechargeList> queryBankRecharge(PageInfo<RechargeList> page);
	
	
	/**
	 * 查询出银行列表
	 * @param page PageInfo<RechargeBankListVo>
	 * @return PageInfo<RechargeBankListVo>
	 */
	public PageInfo<RechargeBankListVo> queryBankListRecharge(PageInfo<RechargeBankListVo> page);
	/**
	 * 查询网银充值审核列表
	 * @param page
	 * @return
	 */
	public PageInfo<RechargeBankListVo> queryNetBankListRecharge(PageInfo<RechargeBankListVo> page);
	/**
	 * 查询微信支付审核列表信息
	 * @param page
	 * @return
	 */
	public PageInfo<RechargeBankListVo> queryWechatListRecharge(PageInfo<RechargeBankListVo> page);
	/**
	 * 查询银行卡充值
	 * @param page DataPage<RechargeList>
	 * @param 
	 * @return DataPage<RechargeList>
	 */
	public PageInfo<AllRechargeListVo> queryRecharge(PageInfo<AllRechargeListVo> page,ConnditionVo conn);
	
	/**
	 * 查询银行卡充值所有记录合计
	 * @param conn ConnditionVo
	 * @return AllRechargeTotalVo
	 */
	public AllRechargeTotalVo queryRechargeAllTotal(ConnditionVo conn);
	
	
	
	/**
	 * 已经查询支付宝
	 * @param page DataPage<RechargeList>
	 * @return DataPage<RechargeList>
	 */
	public PageInfo<RechargeList> queryAlipayHaveRecharge(PageInfo<RechargeList> page);
	
	/**
	 * 已经查询银行卡充值
	 * @param page DataPage<RechargeList>
	 * @return DataPage<RechargeList>
	 */
	public PageInfo<RechargeList> queryBankHaveRecharge(PageInfo<RechargeList> page);
	
	/**
	 * 手工现金充值
	 * @param page DataPage<RechargeList>
	 * @return DataPage<RechargeList>
	 */
	//public PageInfo<RechargeList> queryHandlerRecharge(PageInfo<RechargeList> page);
	
	/**
	 * 
	 * @param page DataPage<WUser> 
	 * @param equals Map<String,Object> 等值查询
	 * @param isLike Map<String,String> like 查询
	 * @param orders Map<String,Boolean> 排序查询
	 * @return DataPage<WUser>
	 */
	public PageInfo<RechargeList> queryDataPageByConndition(PageInfo<RechargeList> page,
			Map<String,Object> equals,Map<String,String> isLike,Map<String,Boolean> orders);
	
	
	/**
	 * 
	 * @param tradeNo 交易号
	 * @param mobileNo 手机号
	 * @param actualMoneyStr 金额
	 * @param remark 备注
	 * @param sysType 充值类开 
	 *   TypeConvert.SYS_TYPE_ADJUSTMENT_OF_ACCOUNTS 系统调账、 
	 *   TypeConvert.SYS_TYPE_OFFSET_ACCOUNTS：系统冲账
	 * @param no 流注
	 */
	public void futureHandlerSaveRechargeState(
			String tradeNo,String mobileNo,String actualMoneyStr,String remark,String sysType) throws Exception;
	
	/**
	 * 
	 * @param tradeNo 交易号
	 * @param mobileNo 手机号
	 * @param actualMoneyStr 金额
	 * @param remark 备注
	 * @param sysType 充值类开 
	 *   TypeConvert.SYS_TYPE_ADJUSTMENT_OF_ACCOUNTS 系统调账、 
	 *   TypeConvert.SYS_TYPE_OFFSET_ACCOUNTS：系统冲账
	 * @param businessType 业务类型 
	 * @param no 流注
	 */
	public void futureHandlerSaveRechargeState(
			String tradeNo,String mobileNo,String actualMoneyStr,String remark,String sysType,Integer businessType) throws Exception;
	
	/**
	 * 
	 * @param tradeNo 交易号
	 * @param mobileNo 手机号
	 * @param actualMoneyStr 金额
	 * @param remark 备注
	 * @param sysType 充值类开 
	 *   TypeConvert.SYS_TYPE_ADJUSTMENT_OF_ACCOUNTS 系统调账、 
	 *   TypeConvert.SYS_TYPE_OFFSET_ACCOUNTS：系统冲账
	 */
	public void futureHandlerSaveRechargeStateWeb(
			String tradeNo,String mobileNo,String actualMoneyStr,String remark,String sysType) throws Exception;
	
	/**
	 * 
	 * @param tradeNo 交易号
	 * @param mobileNo 手机号
	 * @param actualMoneyStr 金额
	 * @param remark 备注
	 * @param sysType 充值类开 
	 *   TypeConvert.SYS_TYPE_ADJUSTMENT_OF_ACCOUNTS 系统调账、 
	 *   TypeConvert.SYS_TYPE_OFFSET_ACCOUNTS：系统冲账
	 * @param businessType 业务类型 
	 */
	public void futureHandlerSaveRechargeStateWeb(
			String tradeNo,String mobileNo,String actualMoneyStr,String remark,String sysType,Integer businessType) throws Exception;

}

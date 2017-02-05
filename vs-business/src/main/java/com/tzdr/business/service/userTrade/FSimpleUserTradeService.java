package com.tzdr.business.service.userTrade;

import java.math.BigDecimal;
import java.util.List;

import com.tzdr.common.baseservice.BaseService;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.utils.ConnditionVo;
import com.tzdr.domain.vo.FSimpleUserTradeVo;
import com.tzdr.domain.vo.FSimpleUserTradeWebVo;
import com.tzdr.domain.web.entity.FSimpleUserTrade;
import com.tzdr.domain.web.entity.WUser;

/**
 * 
 * 
 * <p></p>
 * @author QingLiu
 * @see 
 * FSimpleUserTradeService
 * @version 2.0
 * 2015年2月5日下午7:33:13
 */
public interface FSimpleUserTradeService extends BaseService<FSimpleUserTrade> {
	
	/**
	 * 获取申请列表VO
	 * @param page PageInfo<VolumeDetailVo>
	 * @param connVo ConnditionVo
	 * @return PageInfo<VolumeDetailVo>
	 */
	public PageInfo<FSimpleUserTradeVo> queryApplyListVo(PageInfo<FSimpleUserTradeVo> pageInfo,
			ConnditionVo connVo);
	
	/**
	 * 获取结算列表VO
	 * @param page PageInfo<VolumeDetailVo>
	 * @param connVo ConnditionVo
	 * @return PageInfo<VolumeDetailVo>
	 */
	public PageInfo<FSimpleUserTradeVo> queryBalanceListVo(PageInfo<FSimpleUserTradeVo> pageInfo,
			ConnditionVo connVo);
	
	
	/**
	 * 获取结算列表VO
	 * @param connVo ConnditionVo
	 * @return List<VolumeDetailVo>
	 */
	public List<FSimpleUserTradeVo> queryBalanceListVo(ConnditionVo connVo);
	
	
	/**
	 * 
	 * @param fsimpleUserTrade FSimpleUserTrade
	 * @param mobile String
	 * @return FSimpleUserTrade
	 */
	public FSimpleUserTrade executePayable(FSimpleUserTrade fsimpleUserTrade,
			String mobile,BigDecimal payable)  throws Exception;
	
	
	public PageInfo<FSimpleUserTradeWebVo> findData(String pageIndex,String perPage,String type,String uid);
	
	
	public PageInfo<FSimpleUserTradeWebVo> findDataList(String pageIndex,String perPage,String type,String uid);
	
	/**
	 * 追加保证金
	 * @param fSimpleUserTrade  方案信息
	 * @param appendMoney      追加金额
	 * @param wuser    用户帐号信息
	 * @throws Exception
	 */
	public void addAppendTraderBond(FSimpleUserTrade fSimpleUserTrade,Double appendMoney,WUser wuser) throws Exception;
	
	/**
	 * 结算方案
	 * @param fSimpleUserTrade 方案信息
	 * @param returnFeeManage  返还管理费
	 * @param wuser  用户信息
	 */
	public void updateFSimpleUserTrade(FSimpleUserTrade fSimpleUserTrade,BigDecimal returnFeeManage,WUser wuser) throws Exception;
	
	/**
	 * 拒绝用户申请
	 * @param fSimpleUserTrade 方案信息
	 * @param tradeNo  方案号TG+ID号
	 * @param mobileNo 手机号码
	 * @param actualMoneyStr 金额
	 * @param remark  备注
	 * @param sysType  系统调账
	 * @throws Exception
	 */
	public void updateFSimpleUserTrade(FSimpleUserTrade fSimpleUserTrade,String tradeNo,String mobileNo,String actualMoneyStr,String remark,String sysType) throws Exception;
}

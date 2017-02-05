package com.tzdr.business.service.userTrade;



import java.math.BigDecimal;
import java.util.Map;

import com.tzdr.common.baseservice.BaseService;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.exception.BusinessException;
import com.tzdr.common.web.support.EasyUiPageInfo;
import com.tzdr.domain.vo.FSimpleProductUserTradeWebVo;
import com.tzdr.domain.web.entity.FSimpleFtseUserTrade;
import com.tzdr.domain.web.entity.WUser;
import com.tzdr.domain.web.entity.future.FSimpleCoupon;

/**
 * 
 * @author LiuYang
 *
 * 2015年9月17日 下午4:58:32
 */
public interface FSimpleProductUserTradeService extends BaseService<FSimpleFtseUserTrade> {
	

	/**
	 * 
	 * @param fSimpleFtseUserTrade FSimpleFtseUserTrade
	 * @param mobile String
	 * @return FSimpleFtseUserTrade
	 */
	public FSimpleFtseUserTrade executePayable(FSimpleFtseUserTrade fSimpleFtseUserTrade,
			String mobile,BigDecimal payable)  throws Exception;
	
	/**
	 * 使用了优惠券
	 * @param fSimpleFtseUserTrade
	 * @param voucher
	 * @param mobile
	 * @param payable
	 * @return
	 * @throws Exception
	 */
	public FSimpleFtseUserTrade executePayable(FSimpleFtseUserTrade fSimpleFtseUserTrade, FSimpleCoupon voucher,
			String mobile,BigDecimal payable)  throws Exception;

	/**
	 * 提供后台获取商品期货申请数据
	 * @param easyUiPage
	 * @param searchParams
	 * @return
	 */
	public PageInfo<Object> getApplyData(EasyUiPageInfo easyUiPage, Map<String, Object> searchParams);
	
	public PageInfo<FSimpleProductUserTradeWebVo> findDataList(String pageIndex,String perPage,String uid);


	/**
	 * 追加保证金
	 * @param fSimpleUserTrade  方案信息
	 * @param appendMoney      追加金额
	 * @param wuser    用户帐号信息
	 * @throws Exception
	 */
	public void addAppendTraderBond(FSimpleFtseUserTrade fSimpleFtseUserTrade,Double appendMoney,WUser wuser) throws Exception;
	

	/**
	 * 提供后台获取商品期货方案数据
	 * @param easyUiPage
	 * @param searchParams
	 * @return
	 */
	public PageInfo<Object> getPlanData(EasyUiPageInfo easyUiPage, Map<String, Object> searchParams);
	
	/**
	 * 提供后台获取商品期货收益报表数据
	 * @param easyUiPage
	 * @param searchParams
	 * @return
	 */
	public PageInfo<Object> getEarningData(EasyUiPageInfo easyUiPage, Map<String, Object> searchParams);
	
	/**
	 * cms拒绝申请，保证金自动划款至用户账户余额，并在资金明细中备注为：“【沪金期货】开户申请不通过，保证金返还”。
	 * @param id
	 * @throws Exception
	 */
	public void refuseApply(String id) throws Exception;
	
	/**
	 * cms分配账号，新增成功后自动发送短信，短信为“您的【沪金期货】操盘开户成功，请登录投资达人www.tzdr.com查看您的操盘账号”，而修改不需要发送短信
	 * @param id
	 * @param tranAccount	商品期货操盘账号
	 * @param tranPassword	商品期货操盘密码
	 * @param type			操作类型（1：新增；2：修改）
	 * @throws Exception
	 */
	public void editAccount(String id, String tranAccount, String tranPassword, int type) throws Exception;
	
	/**
	 * cms方案结果录入
	 * 结算金额=保证金 +交易盈亏 -交易手续费
	 * @param id
	 * @param tranProfitLoss
	 * @param tranFeesTotal
	 */
	public void editResult(String id, BigDecimal tranProfitLoss, BigDecimal tranFeesTotal);
	
	/**
	 * cms商品期货——方案管理结算
	 * 采用自动划账返还用户账户，备注为：“【沪金期货】操盘结算”。结算金额为负，自动扣取用户余额，用户余额不够扣取为负，备注为：“【沪金期货】操盘穿仓欠费”。
	 * 【沪金期货】为变量，展示相应的交易品种
	 * @param id
	 */
	public String settle(String id) throws Exception;
	
	/**
	 * 终止操盘和更新优惠券
	 * @param fSimpleFtseUserTrade
	 * @param coupon
	 * @throws Exception
	 */
	void updateFSimpleFtseUserTradeAndFSimpleCoupon(FSimpleFtseUserTrade fSimpleFtseUserTrade, FSimpleCoupon coupon) throws BusinessException;

}

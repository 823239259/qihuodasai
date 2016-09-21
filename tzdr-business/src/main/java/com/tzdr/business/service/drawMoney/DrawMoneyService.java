package com.tzdr.business.service.drawMoney;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.domain.pgb.entity.PGBPaymentSupportBank;
import com.tzdr.domain.web.entity.DrawList;
import com.tzdr.domain.web.entity.PaymentSupportBank;
import com.tzdr.domain.web.entity.UserBank;
import com.tzdr.domain.web.entity.UserVerified;
import com.tzdr.domain.web.entity.WUser;

/**
 * 提现service
 * <P>
 * title:@DrawMoneyService.java
 * </p>
 * <P>
 * Description：
 * </p>
 * <p>
 * Copyright: Copyright (c) 2014 tzdr
 * </p>
 * <p>
 * Company: 上海信闳
 * </p>
 * History：
 * 
 * @author:zhangjun
 * @date 2014年12月23日
 * @version 1.0
 */
public interface DrawMoneyService { 
	/**
	 * 根据用户id查询用户安全信息
	 * 
	 * @param userId
	 *            用户id
	 * @return
	 * @date 2014年12月30日
	 * @author zhangjun
	 */
	public UserVerified findByUserId(String userId);

	/**
	 * 根据id查询用户
	 * 
	 * @param userId
	 * @return
	 * @date 2014年12月31日
	 * @author zhangjun
	 */
	public WUser getUser(String userId);

	/**
	 * 分页查询数据
	 * 
	 * @param pageIndex
	 *            当前页
	 * @param perPage
	 *            每页显示条数
	 * @param userId
	 *            用户id
	 * @return
	 * @date 2015年1月3日
	 * @author zhangjun
	 */
	public PageInfo<DrawList> findData(String pageIndex, String perPage, String userId);

	/**
	 * 获取用户绑定银行卡
	 * 
	 * @param id
	 * @return
	 * @date 2015年1月7日
	 * @author zhangjun
	 */
	public List<UserBank> findUserBankbyUserId(String id);

	/**
	 * 根据输入的银行卡查询绑定的银行卡
	 * 
	 * @param bankcard
	 *            输入的银行卡
	 * @param userId
	 *            用户id
	 * @return
	 * @date 2015年1月8日
	 * @author zhangjun
	 */
	public UserBank findUsercardbycard(String bankcard, String userId);

	/**
	 * 提现
	 * 
	 * @param user
	 *            当前用户
	 * @param money
	 *            提现金额
	 * @param bankcard
	 *            银行卡
	 * @param bankname
	 *            银行名称
	 * @param ip
	 *            登陆ip
	 * @param moneyval
	 *            获取设置的取款金额，如果取款金额大于设置的取款金额后台需要审核
	 * @param maxAuditMoney
	 *            提现审核最大值 1.将提现金额设置为1000-100000 2.小于1000（不含）的金额由联动优势自动转账。
	 *            3.1000（含-100000（含）由人工审核后调用联动优势接口打款。
	 *            4.如用户提现金额大于100000(不含)，则走线下打款。
	 * @date 2015年1月8日
	 * @author zhangjun
	 */
	public void insertDraw(WUser user, String money, String bankcard, String bankname, String ip, String orderId,
			String moneyval, String maxAuditMoney);

	/**
	 * 随机生成订单号
	 * 
	 * @param length
	 * @return
	 * @date 2015年3月26日
	 * @author zhangjun
	 */
	public String getRandomStr(int length);

	/**
	 * 保存银行卡
	 * 
	 * @param user
	 *            用户
	 * @param bankname
	 *            银行卡名称
	 * @param card
	 *            卡号
	 * @param imgpath
	 *            图片路径
	 * @return
	 * @date 2015年1月12日
	 * @author zhangjun
	 */
	public UserBank saveCard(WUser user, String bankname, String card, String imgpath, String accAddress,String provinceCity);

	/**
	 * 根据订单号取款
	 * 
	 * @param orderId
	 *            订单id
	 * @param tradeNo
	 *            交易号
	 * @param date
	 *            交易完成时间
	 * @param tradestatus
	 *            状态1 WAIT_BUYER_PAY 交易创建，等待买家付款。 2TRADE_SUCCESS 交易成功，不能再次进行交易 3
	 *            TRADE_CLOSED 交易关闭 在指定时间段内未支付时关闭的交易；
	 *            交易关闭，商户支付已经过期的订单后，订单状态才会改变为交易关闭 4 TRADE_CANCEL 交易撤销 5
	 *            TRADE_FAIL 交易失败
	 * @date 2015年1月12日
	 * @author zhangjun
	 */
	public void updatDraw(String orderId, String aremark, String tradeNo, String tradestatus, String date);

	/**
	 * 删除银行卡
	 * 
	 * @param cardId
	 * @date 2015年1月12日
	 * @author zhangjun
	 */
	public void delCard(String cardId);

	/**
	 * 设置默认银行卡
	 * 
	 * @param cardId
	 * @date 2015年1月12日
	 * @author zhangjun
	 */
	public void setDefaulcard(String cardId, String userId);

	/**
	 * 查询当天的取款次数
	 * 
	 * @param user
	 * @return
	 * @date 2015年1月13日
	 * @author zhangjun
	 */
	public List<DrawList> findDrawCount(WUser user);

	/**
	 * 根据取款表的id进行提款
	 * 
	 * @param drawId
	 *            取款表id
	 * @return
	 * @date 2015年3月10日
	 * @author zhangjun
	 */
	public JSONObject drawMoney(String drawId);

	/**
	 * 检查银行卡是否在提现中
	 * 
	 * @param cardId
	 * @return
	 * @date 2015年3月30日
	 * @author zhangjun
	 */
	public boolean checkCard(String cardId, String userId);

	/**
	 * 检查有没有暴仓的方案
	 * 
	 * @param uid
	 * @return
	 * @date 2015年4月17日
	 * @author LinFeng
	 */
	public List<String> findUserTradeByUid(String uid);

	/**
	 * 插入取款数据
	 * 
	 * @param user
	 * @param money
	 * @param bankcard
	 * @param bankname
	 * @param ip
	 * @param orderId
	 */
	public void insertDraw(WUser user, String money, String bankcard, String bankname, String ip, String orderId);

	/**
	 * 根据查询条件获取单个提现记录
	 * 
	 * @param id
	 *            提现记录编号
	 * @param userId
	 *            用户编号
	 * @return
	 */
	public DrawList getDrawList(String id, String userId);

	/**
	 * 取消提现
	 * 
	 * @param drawList
	 *            提现记录
	 * @param tradestatus
	 *            取消状态（3）
	 */
	public void updatDraw(DrawList drawList, String tradestatus);

	/**
	 * 根据取款表的id调用币币支付接口提款
	 * 
	 * @param drawId
	 *            取款表id
	 * @return
	 * @date 2015年11月30日
	 * @author zhouchen
	 */
	public JSONObject bbDrawMoney(String drawId);

	/**
	 * 新增支付渠道时 插入取款数据
	 * 
	 * @param source
	 *            来源
	 * @param user
	 * @param money
	 * @param bankcard
	 * @param paymentSupportBank
	 * @param ip
	 * @param orderId
	 * @param withdrawSetting
	 */
	public DrawList insertDraw(int source, WUser user, String money, String bankcard,
			PaymentSupportBank paymentSupportBank, String ip, String orderId, int withdrawSetting,String accAddress);

	public boolean doGoWithdrawals(int source, WUser user, String money, String bankcard,
			PaymentSupportBank paymentSupportBank, String ip, String orderId, int withdrawSetting);

	/**
	 * 更新币币订单结算成功后的币币订单id
	 * 
	 * @param id
	 * @param bbOrderId
	 */
	public void updatDrawBybbOrderId(String id, String bbOrderId);

	/**
	 * 配股宝专用 提现插入取款数据
	 * 
	 * @param source
	 *            来源
	 * @param user
	 * @param money
	 * @param bankcard
	 * @param PGBPaymentSupportBank
	 * @param ip
	 * @param orderId
	 * @param withdrawSetting
	 */
	public DrawList insertDraw(int source, WUser user, String money, String bankcard,
			PGBPaymentSupportBank paymentSupportBank, String ip, String orderId, int withdrawSetting);

	/**
	 * 更新易支付商户相关信息
	 * 
	 * @param id
	 * @param vmid
	 *            商户号
	 * @param secret
	 *            商户秘钥
	 */
	public void updatDrawPayeaseInfo(String id, String vmid, String secret);
	/**
	 * 计算提现手续费
	 * @param userid 用户id
	 * @return 
	 */
	public Double drawFee(String userid,Double money);
}

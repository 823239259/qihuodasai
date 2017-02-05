package com.tzdr.business.service.drawMoney;

import java.util.List;

import com.tzdr.domain.web.entity.UserBank;


/**
 * 银行卡绑定service
 * <P>title:@UserBankService.java</p>																								
 * <P>Description：</p>
 * <p>Copyright: Copyright (c) 2015 tzdr</p>
 * <p>Company: 上海信闳</p>
 * History：
 * @author:zhangjun
 * @date 2015年1月7日
 * @version 1.0
 */
public interface UserBankService {
	/**
	 * 用户id查询绑定的银行卡
	 * @param userId
	 * @return
	 * @date 2015年1月7日
	 * @author zhangjun
	 */
	List<UserBank> findUserBankByuserId(String userId);
	
	/**
	 * 根据银行卡查询绑定的银行卡
	 * @param bankcard 银行卡
	 * @userid 用户id
	 * @return
	 * @date 2015年1月8日
	 * @author zhangjun
	 */
	UserBank findUsercardbycard(String bankcard,String userid);

	/**
	 * 报错银行卡
	 * @param bank
	 * @return
	 * @date 2015年1月9日
	 * @author zhangjun
	 */
	UserBank saveCard(UserBank bank);

	/**
	 * 银行卡删除
	 * @param cardId 银行卡id
	 * @date 2015年1月12日
	 * @author zhangjun
	 */
	void delCard(String cardId);

	/**
	 * 设置默认银行卡
	 * @param cardId
	 * @date 2015年1月12日
	 * @author zhangjun
	 */
	void setDefaulcard(String cardId,String userId);

	String findCard(String cardId);
	
	/**
	 * 查询默认的银行卡
	 * @param id
	 * @return
	 */
	List<UserBank> findUserDefaultBankByuserId(String id);
	

}

package com.tzdr.domain.dao.drawmoney;


import com.tzdr.common.dao.BaseJpaDao;
import com.tzdr.domain.web.entity.UserBank;

/**
 * 银行卡绑定dao
 * <P>title:@SecurityInfoDao.java</p>																								
 * <P>Description：</p>userId
 * <p>Copyright: Copyright (c) 2014 tzdr</p>
 * <p>Company: 上海信闳</p>
 * History：
 * @author:zhangjun
 * @date 2014年12月24日
 * @version 1.0
 */
public interface UserBankDao extends BaseJpaDao<UserBank, String> {
	/**
	 * 根据用户id和输入的银行卡查询银行卡
	 * @param card 银行卡
	 * @param userId 用户id
	 * @return
	 * @date 2015年1月8日
	 * @author zhangjun
	 */
	UserBank findByCardAndUid(String card,String userId);
	
	
	/**
	 * 根据用户id和输入的银行卡id银行卡
	 * @param card 银行卡
	 * @param userId 用户id
	 * @return
	 * @date 2015年1月8日
	 * @author zhangjun
	 */
	UserBank findByIdAndUid(String bankId,String userId);
	
}

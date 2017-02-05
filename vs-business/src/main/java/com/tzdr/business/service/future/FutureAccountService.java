package com.tzdr.business.service.future;

import java.math.BigDecimal;

import com.tzdr.domain.vo.future.FutureAccountVo;
import com.tzdr.domain.web.entity.WUser;
import com.tzdr.domain.web.entity.future.FaccountInfo;

public interface FutureAccountService {

	public FutureAccountVo getFuturesAccountVo(String userID);
	
	public FaccountInfo getFaccountInfo(String userID);
	
	/**
	 * 判断金额是否在平台账户的余额范围内
	 * @param wUser
	 * @param money
	 * @return
	 */
	public boolean isRange(WUser wUser, BigDecimal money);
	
	
	/**
	 * 判断金额是否在期货账户的余额范围内
	 * @param faccountInfo
	 * @param money
	 * @return
	 */
	public boolean isRange(FaccountInfo faccountInfo, BigDecimal money);
	
	/**
	 * 划转
	 * @param userID
	 * @param isOut 1：划出；2：划入
	 * @param money
	 */
	public void transferMoney(FaccountInfo faccountInfo, WUser wUser , boolean isOut, BigDecimal money);
	
	/**
	 * 判断指定的股指期货账户是否存在，不存在则新增一个默认的账户
	 * @param userId
	 * @return
	 */
	public FaccountInfo findOrSave(String userId);
}

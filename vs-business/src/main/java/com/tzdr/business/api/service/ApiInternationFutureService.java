package com.tzdr.business.api.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.tzdr.common.baseservice.BaseServiceImpl;
import com.tzdr.domain.api.vo.InternationFutureVo;
import com.tzdr.domain.dao.userTrade.FSimpleFtseUserTradeDao;
import com.tzdr.domain.web.entity.FSimpleFtseUserTrade;


/**
 * 国际期货apiservice
 * @zhouchen
 * 2015年12月2日
 */
@Service
@Transactional
public class ApiInternationFutureService extends BaseServiceImpl<FSimpleFtseUserTrade,FSimpleFtseUserTradeDao> {
	public static final Logger logger = LoggerFactory.getLogger(ApiInternationFutureService.class);	
	/**
	 * 根据支付宝帐号 查找用户
	 * @param uid
	 * @return
	 */
	public List<InternationFutureVo>  findByUid(String uid){
		String sql = " SELECT tran_account account,business_type businessType from f_simple_ftse_user_trade where uid=? AND state_type=4 AND business_type IN(0,6,7,8)  ";
		List<Object> params = Lists.newArrayList();	
		params.add(uid);
		List<InternationFutureVo> list = nativeQuery(sql,params, InternationFutureVo.class);		
		return list;
	}
}

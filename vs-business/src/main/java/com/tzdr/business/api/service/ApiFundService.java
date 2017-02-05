package com.tzdr.business.api.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.tzdr.common.baseservice.BaseServiceImpl;
import com.tzdr.common.utils.TypeConvert;
import com.tzdr.domain.api.vo.ApiFundVo;
import com.tzdr.domain.dao.pay.UserFundDao;
import com.tzdr.domain.web.entity.UserFund;


/**
 * userfund 服务service
 * @author zhouchen
 * 2015年5月21日 下午12:02:06
 */
@Service
@Transactional
public class ApiFundService extends BaseServiceImpl<UserFund,UserFundDao> {
	public static final Logger logger = LoggerFactory.getLogger(ApiFundService.class);	
	
	public List<ApiFundVo> queryManegeFee(String groupId,String uid){
		String sql = " SELECT money,type,pay_status payStatus,uptime payTime,addtime subTime from w_user_fund where type=12 and  uid=? and lid=? ORDER BY addtime desc ";
		List<Object> params = Lists.newArrayList();	
		params.add(uid);
		params.add(groupId);
		@SuppressWarnings("unchecked")
		List<ApiFundVo> list = nativeQuery(sql,params, ApiFundVo.class);
		return list;
	}
	

	/**
	 * 获取资金明细记录
	 * @param type
	 * @param uid
	 * @return
	 */
	public List<ApiFundVo> queryFundList(int type,String uid){
		String sql = " SELECT money,type,pay_status payStatus,uptime payTime,addtime subTime from w_user_fund where  uid=?  ORDER BY addtime desc ";
		List<Object> params = Lists.newArrayList();	
		params.add(uid);
		if (type==0){
			sql = " SELECT money,type,pay_status payStatus,uptime payTime,addtime subTime from w_user_fund where  uid=? and addtime>=? and addtime<=?  ORDER BY addtime desc ";
			long [] dateParams=TypeConvert.dbDefaultMonthStartEndTime();
			params.add(dateParams[0]);
			params.add(dateParams[1]);
		}
		@SuppressWarnings("unchecked")
		List<ApiFundVo> list = nativeQuery(sql,params, ApiFundVo.class);
		return list;
	}
}

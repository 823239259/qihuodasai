package com.tzdr.business.api.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.Lists;
import com.tzdr.common.baseservice.BaseServiceImpl;
import com.tzdr.domain.api.vo.ApiUserVo;
import com.tzdr.domain.api.vo.UserInfoVo;
import com.tzdr.domain.dao.wuser.WUserDao;
import com.tzdr.domain.web.entity.WUser;


/**
 * TOKEN 服务service
 * @author zhouchen
 * 2015年5月21日 下午12:02:06
 */
@Service
@Transactional
public class ApiUserService extends BaseServiceImpl<WUser,WUserDao> {
	public static final Logger logger = LoggerFactory.getLogger(ApiUserService.class);	
	
	public ApiUserVo  findByMobile(String mobile){
		String sql = " SELECT us.id uid,ver.tname,us.email,us.mobile from w_user us,w_user_verified ver where ver.uid=us.id and us.mobile=? ";
		List<Object> params = Lists.newArrayList();	
		params.add(mobile);
		List<ApiUserVo> list = nativeQuery(sql,params, ApiUserVo.class);
		if (CollectionUtils.isEmpty(list)){
			return null;
		}
		return list.get(0);
	}
	
	public ApiUserVo  findByUid(String uid){
		String sql = " SELECT us.id uid,us.email,us.mobile,us.password from w_user us where  us.id=? ";
		List<Object> params = Lists.newArrayList();	
		params.add(uid);
		List<ApiUserVo> list = nativeQuery(sql,params, ApiUserVo.class);
		if (CollectionUtils.isEmpty(list)){
			return null;
		}
		return list.get(0);
	}
	
	/**
	 * 获取账户信息
	 * @param uid
	 * @return
	 */
	public UserInfoVo  queryUserInfo(String uid){
		String sql = "SELECT us.mobile, if(ver.draw_money_pwd IS NULL,0,1) withDrawPwd, us.generalize_id generalizeId,ver.alipay_account  alipayAccount,ver.tname name, us.avl_bal balance, us.frz_bal freezeFund FROM w_user us, w_user_verified ver WHERE ver.uid = us.id AND us.id =? ";
		List<Object> params = Lists.newArrayList();	
		params.add(uid);
		List<UserInfoVo> list = nativeQuery(sql,params, UserInfoVo.class);
		if (CollectionUtils.isEmpty(list)){
			return null;
		}
		return list.get(0);
	}
	
	
	/**
	 * 根据支付宝帐号 查找用户
	 * @param uid
	 * @return
	 */
	public List<ApiUserVo>  findByAlipay(String alipay){
		String sql = " SELECT us.id uid,us.email,us.mobile,us.`password`,ver.tname from w_user us,w_user_verified ver where  ver.uid=us.id and ver.alipay_account=? ";
		List<Object> params = Lists.newArrayList();	
		params.add(alipay);
		List<ApiUserVo> list = nativeQuery(sql,params, ApiUserVo.class);		
		return list;
	}
}

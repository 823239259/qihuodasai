package com.tzdr.business.service.wuser;

import org.springframework.stereotype.Service;

import com.tzdr.common.baseservice.BaseServiceImpl;
import com.tzdr.domain.dao.wuser.LoginLogDao;
import com.tzdr.domain.web.entity.LoginLog;
/**
 * 
 * @author LiuYang
 *
 * 2015年6月18日 上午10:40:33
 */
@Service
public class LoginLogService extends BaseServiceImpl<LoginLog, LoginLogDao> {

	
	public void saveLog (String ip,String city,String uid){
		super.save(new LoginLog(uid, ip, city));
	}
}

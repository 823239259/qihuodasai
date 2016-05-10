package com.tzdr.domain.dao.userInfo;

import com.tzdr.common.dao.BaseJpaDao;
import com.tzdr.domain.web.entity.UserInfo;
import com.tzdr.domain.web.entity.WUser;

/**
* @Description: TODO(用户基本信息业务管理持久层)
* @ClassName: UserInfoDao
* @author wangpinqun
* @date 2014年12月27日 下午5:14:10
 */
public interface UserInfoDao extends BaseJpaDao<UserInfo, String>{

	/**
	* @Description: TODO(根据用户帐号编号获取用户基本信息)
	* @Title: findByUid  
	* @param uId    用户帐号编号
	* @return UserInfo    返回类型
	 */
	public UserInfo findByWuser(WUser wuser);
}

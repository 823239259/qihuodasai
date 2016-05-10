package com.tzdr.business.service.userInfo;

import com.tzdr.common.baseservice.BaseService;
import com.tzdr.domain.web.entity.UserInfo;
import com.tzdr.domain.web.entity.WUser;

/**
* @Description: TODO(用户基本信息业务管理业务接口层)
* @ClassName: UserInfoService
* @author wangpinqun
* @date 2014年12月27日 下午5:14:46
 */
public interface UserInfoService extends BaseService<UserInfo>{

	/**
	* @Description: TODO(获取用户基本信息)
	* @Title: getUserInfoByUId
	* @param uId   用户帐号编号
	* @return UserInfo    返回类型
	 */
	public UserInfo getUserInfoByUId(String uId);
	
	/**
	* @Description: TODO(修改用户基本信息)
	* @Title: updateUserInfo
	* @param userInfo
	* @return void    返回类型
	 */
	public void updateUserInfo(UserInfo userInfo);
	
	/**
	* @Description: TODO(保存用户基本信息)
	* @Title: saveUserInfo  
	* @param userInfo   用户基本信息
	* @return void    返回类型
	 */
	public void saveUserInfo(UserInfo userInfo);
	
	/**
	 * @Description: 调用论坛信息接口
	 * @param userInfo  用户基本信息
	 * @param wuser     用户信息
	 * @return
	 */
	public boolean saveInfoByUrl(UserInfo userInfo,WUser wuser);
}

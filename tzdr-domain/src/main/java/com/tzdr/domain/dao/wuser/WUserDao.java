package com.tzdr.domain.dao.wuser;

import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tzdr.common.dao.BaseJpaDao;
import com.tzdr.domain.web.entity.WUser;

/**
* @Description: TODO(用户帐号业务信息管理持久层)
* @ClassName: WUserDao
* @author wangpinqun
* @date 2014年12月25日 下午1:34:26
 */
public interface WUserDao extends BaseJpaDao<WUser, String>,JpaSpecificationExecutor<WUser>{

	/**
	* @Description: TODO(根据手机号码获取用户帐号信息)
	* @Title: findByMobile
	* @param mobile   手机号码
	* @return WUser    返回类型
	 */
	WUser findByMobile(String mobile);
	/**
	* @Description: TODO(根据邮箱获取用户帐号信息)
	* @Title: findByMobile
	* @param email   邮箱
	* @return WUser    返回类型
	 */
	WUser findByEmail(String email);
	
	/**
	 * 
	 * @param email String
	 * @return int
	 */
	int countByEmail(String email);
	
	/**
	* @Description: TODO(根据帐号获取用户帐号信息)
	* @Title: findByMobileOrEmail  
	* @param loginName  用户帐号
	* @return WUser    返回类型
	 */
	@Query("from WUser where mobile= :loginName or email = :loginName")
	WUser findByMobileOrEmail(@Param("loginName") String loginName);
	
	/**
	 * 
	 * @param email String
	 * @param pageable Pageable
	 * @return List<WUser>
	 */
	public List<WUser> findByEmail(String email,Pageable pageable);
	
	/**
	* @Description: TODO(根据用户类型获取用户列表信息)
	* @Title: findByUserType
	* @param userType  用户类型
	* @return List<WUser>    返回类型
	 */
	public List<WUser> findByUserType(String userType);
	
	/**
	* @Description: TODO(根据用户编号获取用户列表信息)
	* @Title: findByIdIn
	* @param ids  用户编号  
	* @return List<WUser>    返回类型
	 */
	public List<WUser> findByIdIn(Collection<String> ids);
	
	/**
	* @Description: TODO(根据用户推广编号获取用户信息)
	* @Title: findByGeneralizeId
	* @param generalizeId     推广编号
	* @return WUser    返回类型
	 */
	public WUser findByGeneralizeId(String generalizeId);
	
	/**
	* @Description:(根据用户编号获取用户列表信息)
	* @Title: 找8800活动
	* @param activityType  
	* @return List<WUser>    返回类型
	 */
	public List<WUser> findByActivityTypeAndUserType(int activityType,String userType);
}

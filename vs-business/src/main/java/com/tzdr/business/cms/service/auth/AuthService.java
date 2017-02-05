package com.tzdr.business.cms.service.auth;

import java.util.List;
import java.util.Set;

import jodd.util.ObjectUtil;
import jodd.util.StringUtil;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.google.common.collect.Lists;
import com.tzdr.business.cms.service.user.UserService;
import com.tzdr.common.baseservice.BaseServiceImpl;
import com.tzdr.common.utils.Dates;
import com.tzdr.domain.cms.entity.auth.Auth;
import com.tzdr.domain.cms.entity.user.User;
import com.tzdr.domain.dao.auth.AuthDao;

/**
 * <p>
 * User: Lin Feng
 * <p>
 * Version: 1.0
 */
@Service
public class AuthService extends BaseServiceImpl<Auth, AuthDao> {

	@Autowired
	private UserService userService;


	public void addUserAuth(Set<Long> userIds, Set<Long> roleIds) {

		if (CollectionUtils.isEmpty(userIds)) {
			return;
		}

		for (Long userId : userIds) {

			User user = userService.get(userId);
			if (user == null) {
				continue;
			}

			Auth auth = getEntityDao().findByUserId(userId);
			User loginUser =  getCurrentUser();
			if (ObjectUtil.equals(null, auth)){
				Auth  tempAuth = 	new Auth(userId, roleIds);
				tempAuth.setCreateTime(Dates.getCurrentLongDate());
				tempAuth.setCreateUser(loginUser.getRealname());
				tempAuth.setCreateUserOrg(loginUser.getOrganization().getName());
				tempAuth.setOperateContent("授权用户");
				tempAuth.setCreateUserId(loginUser.getId());
				save(tempAuth);
				continue;
			}
			
			auth.setRoleIds(roleIds);
			auth.setUpdateTime(Dates.getCurrentLongDate());
			auth.setUpdateUser(loginUser.getRealname());
			auth.setUpdateUserOrg(loginUser.getOrganization().getName());
			auth.setOperateContent("更新授权用户");
			auth.setUpdateUserId(loginUser.getId());
			update(auth);
		}
	}

	/**
	 * 获取当前登录的用户信息
	 * @return
	 */
	public User getCurrentUser(){
		Subject subject = SecurityUtils.getSubject();
		PrincipalCollection currentUserName = subject.getPrincipals();
		String userName = StringUtil.toString(currentUserName);
		User user  = userService.findByUsername(userName);
		return user;
	}

	/**
	 * 获取登录用户 所属组织的 code
	 * @return
	 */
	public List<Object> getLoginUserOrgCodeParams(){
		User user =  getCurrentUser();
		List<Object> params = Lists.newArrayList();
		params.add(user.getOrganization().getCode()+"%");
		return params;
	}
	/**
	 * 根据用户信息获取 角色 1.1、用户 根据用户绝对匹配 
	 *
	 * @param userId
	 *            必须有
	 * @return
	 */
	public Set<Long> findRoleIds(Long userId) {
		return getEntityDao().findRoleIds(userId);
	}
}

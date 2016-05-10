package com.tzdr.business.cms.service.user;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jodd.util.ObjectUtil;
import jodd.util.StringUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.tzdr.business.cms.exception.UserBlockedException;
import com.tzdr.business.cms.exception.UserException;
import com.tzdr.business.cms.exception.UserNotExistsException;
import com.tzdr.business.cms.exception.UserPasswordNotMatchException;
import com.tzdr.business.cms.service.auth.AuthService;
import com.tzdr.business.cms.utils.UserLogUtils;
import com.tzdr.common.auth.SearchOperator;
import com.tzdr.common.auth.Searchable;
import com.tzdr.common.baseservice.BaseServiceImpl;
import com.tzdr.common.domain.PageInfo;
import com.tzdr.common.exception.BusinessException;
import com.tzdr.common.utils.Dates;
import com.tzdr.common.web.support.EasyUiPageInfo;
import com.tzdr.common.web.support.MultiListParam;
import com.tzdr.domain.cms.entity.organization.Organization;
import com.tzdr.domain.cms.entity.user.User;
import com.tzdr.domain.cms.entity.user.UserStatus;
import com.tzdr.domain.dao.user.UserDao;
import com.tzdr.domain.vo.TestVo;

/**
 * <p>
 * User: Lin Feng
 * <p>
 * Version: 1.0
 */
@Service
public class UserService extends BaseServiceImpl<User, UserDao> {

	@Autowired
	private AuthService authService;
	
	@Autowired
	private UserStatusHistoryService userStatusHistoryService;

	@Autowired
	private PasswordService passwordService;

	public void setPasswordService(PasswordService passwordService) {
		this.passwordService = passwordService;
	}
	
	@Override
	public void save(User user) {
		user.setUsername(user.getEmail());
		User dbUser = findByUsername(user.getRealname());
		if (!ObjectUtil.equals(null, dbUser)){
			throw new UserException("user.username.exist",new Object[]{user.getRealname()});
		}
		if (user.getCreateDate() == null) {
			user.setCreateDate(new Date());
		}
		user.randomSalt();
		user.setPassword(passwordService.encryptPassword(user.getRealname(),
				user.getPassword(), user.getSalt()));
		setOperateLog(user,"新建用户","add");
		super.save(user);
	}

	@Override
	public void update(User user) {
		Long userid = user.getId();
		User oldUser = getEntityDao().get(userid);
		if (ObjectUtil.equals(oldUser, null)){
			throw new UserException("business.update.not.found.data",null);
		}
		
		oldUser.setUsername(user.getEmail());
		oldUser.setEmail(user.getEmail());
		oldUser.setOrganization(user.getOrganization());
		oldUser.setMobilePhoneNumber(user.getMobilePhoneNumber());
		setOperateLog(oldUser,"更新用户信息","edit");
		
		super.update(oldUser);
	}

	public User findByUsername(String username) {
		if (StringUtil.isEmpty(username)) {
			return null;
		}
		return getEntityDao().findByUsername(username);
	}

	public User findByEmail(String email) {
		if (StringUtil.isEmpty(email)) {
			return null;
		}
		return getEntityDao().findByEmail(email);
	}

	public User findByMobilePhoneNumber(String mobilePhoneNumber) {
		if (StringUtil.isEmpty(mobilePhoneNumber)) {
			return null;
		}
		return getEntityDao().findByMobilePhoneNumber(mobilePhoneNumber);
	}

	public User changePassword(User user, String newPassword) {
		user.randomSalt();
		user.setPassword(passwordService.encryptPassword(user.getRealname(),
				newPassword, user.getSalt()));
		setOperateLog(user,"更新用户密码","edit");
		getEntityDao().update(user);
		return user;
	}

	public User changeStatus(User opUser, User user, UserStatus newStatus,
			String reason) {
		user.setStatus(newStatus);
		getEntityDao().update(user);
		userStatusHistoryService.log(opUser, user, newStatus, reason);
		return user;
	}

	public User login(String username, String password) {

		if (StringUtil.isEmpty(username) || StringUtil.isEmpty(password)) {
			UserLogUtils.log(username, "loginError", "username is empty");
			throw new UserNotExistsException();
		}
		// 密码如果不在指定范围内 肯定错误
		if (password.length() < User.PASSWORD_MIN_LENGTH
				|| password.length() > User.PASSWORD_MAX_LENGTH) {
			UserLogUtils.log(username, "loginError",
					"password length error! password is between {} and {}",
					User.PASSWORD_MIN_LENGTH, User.PASSWORD_MAX_LENGTH);

			throw new UserPasswordNotMatchException();
		}

		User user = null;

		if (maybeUsername(username)) {
			user = this.getEntityDao().findByUsernameAndDeletedFalse(username);
		}
		if (user == null || Boolean.TRUE.equals(user.getDeleted())) {
			UserLogUtils.log(username, "loginError", "user is not exists!");

			throw new UserNotExistsException();
		}

		if (user.getStatus() == UserStatus.blocked) {
			UserLogUtils.log(username, "loginError", "user is blocked!");
			throw new UserBlockedException(
					userStatusHistoryService.getLastReason(user));
		}
		passwordService.validate(user, password);
		UserLogUtils.log(username, "loginSuccess", "");
		return user;
	}

	private boolean maybeUsername(String username) {
		/*if (!username.matches(User.USERNAME_PATTERN)) {
			return false;
		}*/
		// 如果用户名不在指定范围内也是错误的
		if (username.length() < User.USERNAME_MIN_LENGTH
				|| username.length() > User.USERNAME_MAX_LENGTH) {
			return false;
		}

		return true;
	}

	public void resetPassword(Set<Long> ids, String newPassword) {
		for (Long id : ids) {
			User user = get(id);
			this.changePassword(user, newPassword);
			UserLogUtils.log(user.getRealname(), "changePassword",
					"admin user {} change password!", user.getRealname());

		}
	}

	public void changeStatus(User opUser, Long[] ids, UserStatus newStatus,
			String reason) {
		for (Long id : ids) {
			User user = get(id);
			this.changeStatus(opUser, user, newStatus, reason);
			UserLogUtils.log(user.getRealname(), "changeStatus",
					"admin user {} change status!", opUser.getRealname());
		}
	}

	public Set<Map<String, Object>> findIdAndNames(Searchable searchable,
			String usernme) {

		searchable.addSearchFilter("username", SearchOperator.like, usernme);
		searchable.addSearchFilter("deleted", SearchOperator.eq, false);

		return Sets.newHashSet(Lists.transform(
				findAll(searchable).getContent(),
				new Function<User, Map<String, Object>>() {
					@Override
					public Map<String, Object> apply(User input) {
						Map<String, Object> data = Maps.newHashMap();
						data.put("label", input.getUsername());
						data.put("value", input.getId());
						return data;
					}
				}));
	}
	
	 @Override
	    public void removes(Serializable... ids) throws BusinessException {
	    	for (Serializable id : ids){
	    		User  user = getEntityDao().get(Long.valueOf(id.toString()));
	    		if (ObjectUtil.equals(user, null)){
	    			throw new UserException("business.delete.not.found.data",null);
	    		}
	    		user.setDeleted(Boolean.TRUE);
	    		setOperateLog(user,"删除用户","edit");
	    		super.update(user);
	    	}
	    }
	 
	public List<User> findByOrganization(Long organizationID){
		 return getEntityDao().findByOrganization(new Organization(organizationID));
	 }
	
	
	public List<User> findAvailableUsers (Long organizationID){
		 return getEntityDao().findByDeletedFalseAndOrganization(new Organization(organizationID));
	 }
	
	
	private void setOperateLog(User user,String operateContent,String operateType){
		User   loginUser = authService.getCurrentUser();	
		user.setOperateContent(operateContent);
		if (StringUtil.equals(operateType,"edit")){
			user.setUpdateTime(Dates.getCurrentLongDate());
			user.setUpdateUser(loginUser.getRealname());
			user.setUpdateUserOrg(loginUser.getOrganization().getName());
			user.setUpdateUserId(loginUser.getId());
			return ;
		}
		
		user.setCreateTime(Dates.getCurrentLongDate());
		user.setCreateUserId(loginUser.getId());
		user.setCreateUser(loginUser.getRealname());
		user.setCreateUserOrg(loginUser.getOrganization().getName());
		return ;
	}
	public PageInfo<Object> multilistPageQuery(EasyUiPageInfo  easyUiPage,Map<String, Object> searchParams){
		//查询数据
		PageInfo<Object> pageInfo = new PageInfo<Object>(easyUiPage.getRows(), easyUiPage.getPage());
		String sql = "SELECT  us.username,us.email,org.`name`,org.`code` from sys_user us,sys_organization org where us.organization_id=org.id and us.id=?";
		
		//params  查询参数  依次 存入
		List<Object> params = Lists.newArrayList();
		
		MultiListParam  multilistParam  = new MultiListParam(easyUiPage, searchParams, params, sql);
		pageInfo = multiListPageQuery(multilistParam,TestVo.class);
		return pageInfo;
		
	}
	

	public List<User> findByUsersDeleteFalse(){
		return getEntityDao().findByDeletedFalse();
	}



}

package com.tzdr.business.cms.service.user;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tzdr.business.cms.service.auth.AuthService;
import com.tzdr.common.baseservice.BaseServiceImpl;
import com.tzdr.domain.cms.entity.user.User;
import com.tzdr.domain.cms.entity.user.UserExtend;
import com.tzdr.domain.dao.user.UserExtendDao;

/**
 * 用户拓展信息dao
 * @ClassName UserExtendService
 * @author L.Y
 * @email liuyuan@peigubao.com
 * @date 2015年6月15日
 */
@Service
public class UserExtendService extends BaseServiceImpl<UserExtend, UserExtendDao> {

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
	@Transactional
	public void save(UserExtend ue) {
//		setOperateLog(user,"新建用户","add");
		super.save(ue);
	}

	@Override
	@Transactional
	public void update(UserExtend ue) {
//		Long id = ue.getId();
//		UserExtend oue = getEntityDao().get(id);
//		if (ObjectUtil.equals(oue, null)){
//			throw new UserException("business.update.not.found.data",null);
//		}
//		
//		oue.setUid(ue.getUid());
//		oue.setRemark(ue.getRemark());
//		oue.setSysUser(ue.getSysUser());
//		setOperateLog(oue,"更新用户信息","edit");
		super.update(ue);
	}
	
	@Transactional
	public int remove(String uid, Integer activityType) {
		return nativeUpdate("UPDATE sys_user_extend SET deleted="+Boolean.TRUE+" WHERE uid=? AND activity_type=?", Arrays.asList((Object)uid, activityType));
	}
	
	
	
	/**
	 * 根据用户id查询
	 * @MethodName findUserExtends
	 * @author L.Y
	 * @date 2015年6月15日
	 * @param uid 用户id
	 * @return
	 */
	public List<UserExtend> findUserExtends(String uid) {
		return getEntityDao().findByUid(uid);
	}
	
	/**
	 * 根据活动类型和用户id查询 且未删除
	 * @MethodName findUserExtends
	 * @author L.Y
	 * @date 2015年6月17日
	 * @param uid 用户id
	 * @param activityType 活动类型
	 * @return
	 */
	public List<UserExtend> findUserExtends(String uid, Integer activityType) {
		return getEntityDao().findByUidAndActivityTypeAndDeletedFalse(uid, activityType);
	}
	
	/**
	 * 根据活动类型和用户id & 系统用户id 查询 且未删除
	 * @MethodName findUserExtends
	 * @author L.Y
	 * @date 2015年6月17日
	 * @param uid 用户id
	 * @param sysUser 系统用户
	 * @param activityType 活动类型
	 * @return
	 */
	public List<UserExtend> findUserExtends(String uid, User sysUser, Integer activityType) {
		return getEntityDao().findByUidAndSysUserAndActivityTypeAndDeletedFalse(uid, sysUser, activityType);
	}
	
	/**
	 * 根据活动类型和用户id & 系统用户id 查询
	 * @MethodName findUserExtendIgnoreDeleted
	 * @author L.Y
	 * @date 2015年6月17日
	 * @param uid 用户id
	 * @param activityType 活动类型
	 * @return
	 */
	public UserExtend findUserExtendIgnoreDeleted(String uid, Integer activityType) {
		return getEntityDao().findByUidAndActivityType(uid, activityType);
	}

//	private void setOperateLog(User user,String operateContent,String operateType){
//		User   loginUser = authService.getCurrentUser();	
//		user.setOperateContent(operateContent);
//		if (StringUtil.equals(operateType,"edit")){
//			user.setUpdateTime(Dates.getCurrentLongDate());
//			user.setUpdateUser(loginUser.getRealname());
//			user.setUpdateUserOrg(loginUser.getOrganization().getName());
//			user.setUpdateUserId(loginUser.getId());
//			return ;
//		}
//		
//		user.setCreateTime(Dates.getCurrentLongDate());
//		user.setCreateUserId(loginUser.getId());
//		user.setCreateUser(loginUser.getRealname());
//		user.setCreateUserOrg(loginUser.getOrganization().getName());
//		return ;
//	}
}